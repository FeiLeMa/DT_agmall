package com.alag.agmall.business.module.order.server.service.impl;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.util.BigDecimalUtil;
import com.alag.agmall.business.core.util.DateTimeUtil;
import com.alag.agmall.business.core.util.IDGenerator;
import com.alag.agmall.business.core.util.PropertiesUtil;
import com.alag.agmall.business.core.vo.OrderItemVo;
import com.alag.agmall.business.core.vo.OrderProductVo;
import com.alag.agmall.business.core.vo.OrderVo;
import com.alag.agmall.business.core.vo.ShippingVo;
import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;
import com.alag.agmall.business.module.alipay.feign.AlipayFeign;
import com.alag.agmall.business.module.cart.api.model.Cart;
import com.alag.agmall.business.module.cart.feign.controller.CartFeignClient;
import com.alag.agmall.business.module.message.api.model.TransactionMessage;
import com.alag.agmall.business.module.message.feign.controller.TransactionMessageFeign;
import com.alag.agmall.business.module.order.api.model.Order;
import com.alag.agmall.business.module.order.api.model.OrderItem;
import com.alag.agmall.business.module.order.api.model.PayInfo;
import com.alag.agmall.business.module.order.server.mapper.OrderItemMapper;
import com.alag.agmall.business.module.order.server.mapper.OrderMapper;
import com.alag.agmall.business.module.order.server.mapper.PayInfoMapper;
import com.alag.agmall.business.module.order.server.service.OrderService;
import com.alag.agmall.business.module.product.api.model.Product;
import com.alag.agmall.business.module.product.feign.controller.ProductFeignClient;
import com.alag.agmall.business.module.shipping.api.model.Shipping;
import com.alag.agmall.business.module.shipping.feign.controller.ShippingFeignClient;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CartFeignClient cartFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private ShippingFeignClient shippingFeignClient;
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private TransactionMessageFeign transactionMessageFeign;
    @Autowired
    private AlipayFeign alipayFeign;

    @Override
    public ServerResponse<Order> selectOrderByOrderNoAndUserId(Long orderNo, Integer userId) {
        Order order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
        if (order == null) {
            return ServerResponse.createByErrorMessage("没找到该订单");
        }
        return ServerResponse.createBySuccess(order);
    }

    @Override
    public ServerResponse<List<OrderItem>> selectOrderItemByOrderNoAndUserId(Long orderNo, Integer userId) {
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoAndUserId(orderNo, userId);

        return ServerResponse.createBySuccess(orderItemList);
    }

    @Override
    public ServerResponse<Order> selectOrderByOrderNo(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("没有该订单");
        }
        return ServerResponse.createBySuccess(order);
    }

    @Override
    public ServerResponse<Integer> updateOrder(Order order) {
        int row = orderMapper.updateByPrimaryKeySelective(order);
        return ServerResponse.createBySuccess(row);
    }

    @Override
    public ServerResponse<Integer> insertPayInfo(PayInfo payInfo) {
        int row = payInfoMapper.insert(payInfo);
        return ServerResponse.createBySuccess(row);
    }

    @Override
    public ServerResponse<PayInfo> selectPayInfoByOrderNo(Long orderNo) {
        PayInfo payInfo = payInfoMapper.selectPayInfoByOrderNo(orderNo);
        return ServerResponse.createBySuccess(payInfo);
    }

    @Override
    @Transactional
    public ServerResponse modifyOrderStatusAndAddPayInfo(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);

        if (null == order) {
            log.info("订单没找到orderNo:{}",orderNo);
            return ServerResponse.createByErrorMessage("订单没找到");
        }

        if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()) {
            log.info(("幂等判断，订单状态已经修改，请忽略！"));
            transactionMessageFeign.deleteMessageByMessageId(Const.TMessage.ORDER_MSG_ID_PRE + orderNo);
            return ServerResponse.createBySuccess("幂等判断，订单状态已经修改，请忽略！");
        }


        AlipayInfo alipayInfo = alipayFeign.getByOrderNo(orderNo).getData();
        TransactionMessage message = TransactionMessage.setReturn(msg -> {
            msg.setCreateTime(new Date());
            msg.setUpdateTime(new Date());
            msg.setConsumerQueue(Const.TMessage.PAYINFO_QUEUE_NAME);
            msg.setCreator(Const.TMessage.CREATOR_NAME);
            msg.setEditor(Const.TMessage.EDITOR_NAME);
            msg.setId(IDGenerator.tMIDBuilder());
            msg.setMessageId(Const.TMessage.PAYINFO_MSG_ID_PRE + orderNo);
            msg.setMessageBody(JSONObject.toJSONString(alipayInfo));
            msg.setVersion(1);
            msg.setMessageDataType(Const.TMessage.MESSAGE_DATA_TYPE);
            msg.setRemark(Const.TMessage.REMARK);
        });
        ServerResponse response = transactionMessageFeign.saveMessageWaitingConfirm(message);
        if (response.isSuccess()) {
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            order.setUpdateTime(new Date());
            int row = orderMapper.updateByPrimaryKeySelective(order);
            String retMsg = "订单状态已经修改";
            if (row > 0) {
                transactionMessageFeign.deleteMessageByMessageId(Const.TMessage.ORDER_MSG_ID_PRE + orderNo).queue();
                transactionMessageFeign.confirmAndSendMessage(Const.TMessage.PAYINFO_MSG_ID_PRE + orderNo).queue();
            } else {
                retMsg = "修改订单状态失败";
            }
            return ServerResponse.createBySuccess(retMsg);
        }
        return ServerResponse.createByErrorMessage("预发送消息失败");
    }

    @Override
    @Transactional
    public ServerResponse addPayInfo(AlipayInfo alipayInfo) {
        PayInfo payInfo = payInfoMapper.selectPayInfoByOrderNo(alipayInfo.getOrderNo());
        if (payInfo != null) {
            log.info("幂等判断，payinfo已经插入，请忽略");
            transactionMessageFeign.deleteMessageByMessageId(Const.TMessage.PAYINFO_MSG_ID_PRE + alipayInfo.getOrderNo());
            return ServerResponse.createBySuccess("幂等判断，payinfo已经插入，请忽略");
        }
        payInfo = new PayInfo();
        payInfo.setUserId(alipayInfo.getUserId());
        payInfo.setOrderNo(alipayInfo.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(alipayInfo.getTradeNo());
        payInfo.setPlatformStatus(alipayInfo.getTradeStatus());
        int row = payInfoMapper.insert(payInfo);
        log.info("插入payInfo结果row{}",row);
        if (row > 0) {
            transactionMessageFeign.deleteMessageByMessageId(Const.TMessage.PAYINFO_MSG_ID_PRE + alipayInfo.getOrderNo()).queue();
            log.info("插入payInfo成功！");
            return ServerResponse.createBySuccess();
        }
        log.info("插入PayInfo失败");
        return ServerResponse.createByErrorMessage("插入PayInfo失败");
    }


    @Override
    public ServerResponse createOrder(Integer userId, Integer shippingId) {
        List<Cart> cartList = cartFeignClient.getCart(userId).getData();

        ServerResponse serverResponse = this.getOrderItemList(cartList);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }

        BigDecimal payment = this.calPayment((List<OrderItem>) serverResponse.getData());

        Order order = this.assembleOrder(userId, shippingId, payment);
        if (order == null) {
            return ServerResponse.createByErrorMessage("创建订单失败");
        }

        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }

        //批量插入Orderitem
        orderItemMapper.batchInsert(orderItemList);

        //减少库存
        this.reduceProductStock(orderItemList);

        //清空购物车
        this.cleanCart(cartList);

        //返回前端OrderVo
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);

        return ServerResponse.createBySuccess(orderVo);

    }

    @Override
    public ServerResponse canncelOrder(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
        if (order == null) {
            return ServerResponse.createByErrorMessage("该用户下没有此订单");
        }
        order.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        int rowCount = orderMapper.updateByPrimaryKeySelective(order);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("取消此订单成功");
        }
        return ServerResponse.createByErrorMessage("取消订单失败");
    }

    @Override
    public ServerResponse getCartProduct(Integer userId) {
        List<Cart> cartList = cartFeignClient.getCheckedCart(userId).getData();
        if (cartList == null) {
            return ServerResponse.createByErrorMessage("该用户购物车下没有勾选的商品");
        }
        ServerResponse response = this.getOrderItemList(cartList);
        if (!response.isSuccess()) {
            return response;
        }

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        BigDecimal payment = new BigDecimal(0);
        for (OrderItem orderItem : (List<OrderItem>) response.getData()) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(this.assembleOrderItemVo(orderItem));
        }

        OrderProductVo orderProductVo = new OrderProductVo();
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setProductTotalPrice(payment);

        return ServerResponse.createBySuccess(orderProductVo);
    }

    @Override
    public ServerResponse getDetail(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
        if (null == order) {
            return ServerResponse.createByErrorMessage("没找到该订单");
        }
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoAndUserId(orderNo, userId);
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);

        return ServerResponse.createBySuccess(orderVo);
    }

    @Override
    public ServerResponse list(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList);
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);

        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public void closeOrder(int hour) {
        Date closeTime = DateTimeUtil.addHours(new Date(), -hour);
        List<Order> needCloseOrderList = orderMapper.selectByTimeAndStatus(closeTime, Const.OrderStatusEnum.NO_PAY.getCode());
        for (Order order : needCloseOrderList) {
            //将订单状态改为交易关闭
            Long orderNo = order.getOrderNo();
            order.setStatus(Const.OrderStatusEnum.ORDER_CLOSE.getCode());
            order.setCloseTime(new Date());
            int row = orderMapper.updateByPrimaryKeySelective(order);
            if (row > 0) {
                log.info("订单{},已被关闭", orderNo);
            }
            List<OrderItem> orderItemList = orderItemMapper.selectProductIdByOrderNo(orderNo);
            for (OrderItem orderItem : orderItemList) {
                Integer id = orderItem.getProductId();
                Integer quantity = orderItem.getQuantity();
                Integer stock = productFeignClient.getStock(id).getData();

                Product product = new Product();
                product.setId(id);
                product.setStock(stock + quantity);

                row = productFeignClient.modifyProduct(product).getData();
                if (row > 0) {
                    log.info("产品id{},库存{},已经恢复至{}", id, stock, stock + orderItem.getQuantity());
                }
            }
        }
    }

    private List<OrderVo> assembleOrderVoList(List<Order> orderList) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orderList) {
            Long orderNo = order.getOrderNo();
            Integer userId = order.getUserId();
            List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoAndUserId(orderNo, userId);
            OrderVo orderVo = this.assembleOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }


    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPostage(order.getPostage());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setShippingId(order.getShippingId());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());

        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        Shipping shipping = shippingFeignClient.getShipping(order.getShippingId()).getData();
        if (shipping != null) {
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(this.assembleShippingVo(shipping));
        }

        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));


        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;

    }

    private OrderItemVo assembleOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }

    private ShippingVo assembleShippingVo(Shipping shipping) {
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        return shippingVo;
    }

    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartFeignClient.delCart(cart.getId());
        }
    }

    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productFeignClient.getPJPById(orderItem.getProductId()).getData();
            product.setStock(product.getStock() - orderItem.getQuantity());
            productFeignClient.modifyProduct(product);
        }
    }

    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        Order order = new Order();
        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setOrderNo(this.generateOrderNo());
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPostage(0);

        int rowCount = orderMapper.insert(order);
        if (rowCount > 0) {
            return order;
        }
        return null;
    }

    private BigDecimal calPayment(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    private ServerResponse getOrderItemList(List<Cart> cartList) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        for (Cart cartItem : cartList) {
            OrderItem orderItem = new OrderItem();
            Product product = productFeignClient.getPJPById(cartItem.getProductId()).getData();
            //检验产品是否下架
            if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
                return ServerResponse.createByErrorMessage("商品已下架");
            }
            //检验库存
            if (cartItem.getQuantity() > product.getStock()) {
                return ServerResponse.createByErrorMessage("库存不足，请修改数量");
            }

            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUserId(cartItem.getUserId());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setProductName(product.getName());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity()));

            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItemList);
    }

    private Long generateOrderNo() {
        Long nowTime = System.currentTimeMillis();
        return nowTime + new Random().nextInt(100);
    }

    @Override
    public ServerResponse<Boolean> getOrderStatusByOrderNoAndUserId(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
        if (order == null) {
            return ServerResponse.createByErrorMessage("没有该订单");
        }
        log.info(order.getStatus().toString());
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ServerResponse.createBySuccess(true);
        }

        return ServerResponse.createBySuccess(false);
    }

}
