package com.alag.agmall.business.module.alipay.server.controller;


import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.ResponseCode;
import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.util.PropertiesUtil;
import com.alag.agmall.business.module.alipay.server.service.AlipayService;
import com.alag.agmall.business.module.user.api.model.User;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("alipay")
public class AlipayController {
    @Autowired
    private AlipayService alipayService;

    @RequestMapping("to_pay")
    public String pay(Model model, HttpSession session, @RequestParam(value = "orderNo", required = true) Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            model.addAttribute("errCode", ResponseCode.NEED_LOGIN.getCode());
            model.addAttribute("errMsg", "请先登陆上再操作");
        } else {
            ServerResponse<String> formRet = alipayService.pay(user.getId(), orderNo);
            if (!formRet.isSuccess()) {
                model.addAttribute("status", formRet.getStatus());
                model.addAttribute("errMsg", formRet.getMsg());
            }
            model.addAttribute("form", formRet.getData());
        }
        return "toAlipay";
    }

    @RequestMapping("alipay_callback")
    public String alipayCallback(Model model,HttpServletRequest request) {
        log.info("========================== callbackRequest ===========================");
        Map<String, String[]> parameterMap = request.getParameterMap();
        model.addAttribute("paramMap", parameterMap);
        return "alipay_call_back";
    }

    @RequestMapping("alipay_notify")
    @ResponseBody
    public Object alipayNotify(HttpServletRequest request) throws AlipayApiException {
        log.info("========================== notifyRequest ===========================");
        Map<String, String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }

            log.info("key:{}-----value:{}", name, valueStr);
            params.put(name, valueStr);

        }
        params.remove("sign_type");
        boolean signVerified = AlipaySignature.rsaCheckV2(params, PropertiesUtil.getProperty("alipay.alipay_public_key"), PropertiesUtil.getProperty("alipay.charset"), PropertiesUtil.getProperty("alipay.sign_type")); //调用SDK验证签名
        if (signVerified) {
            log.info("验签成功,正在处理业务...");
            ServerResponse response = alipayService.aliCallback(params);
            if (response.isSuccess()) {
                log.info("业务处理成功，返回支付宝成功");
                return Const.AlipayCallback.RESPONSE_SUCCESS;
            } else {
                log.info("业务处理失败返回支付宝失败");
                return Const.AlipayCallback.RESPONSE_FAILED;
            }
        } else {
            log.info("验签失败");
            return ServerResponse.createByErrorMessage("非法请求,验证不通过!");
        }
    }


    //测试alipayNotify接口
    @RequestMapping("ali_notify_back")
    @ResponseBody
    public ServerResponse alipayBack(@RequestBody Map<String,String> paramMap) {
        return alipayService.aliCallback(paramMap);
    }
















//    @RequestMapping("th")
//    public String index(Model model) {
//        model.addAttribute("msg", "okay");
//        return "index";
//    }
//
//
//    @RequestMapping("index")
//    @ResponseBody
//    public String index() {
//        return "return msg";
//    }

}
