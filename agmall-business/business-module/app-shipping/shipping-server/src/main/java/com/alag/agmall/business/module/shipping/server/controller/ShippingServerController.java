package com.alag.agmall.business.module.shipping.server.controller;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.ResponseCode;
import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.shipping.api.model.Shipping;
import com.alag.agmall.business.module.shipping.server.service.ShippingService;
import com.alag.agmall.business.module.user.api.model.User;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingServerController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping("add")
    public ServerResponse add(HttpSession session, @RequestBody Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "请登录后再操作");
        }
        return shippingService.add(user.getId(), shipping);
    }

    @DeleteMapping("del")
    public ServerResponse del(HttpSession session, @RequestParam("id") Integer id) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "请登录后再操作");
        }
        return shippingService.del(user.getId(), id);
    }

    @PutMapping("update")
    public ServerResponse del(HttpSession session, @RequestBody Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "请登录后再操作");
        }
        return shippingService.update(user.getId(), shipping);
    }

    @GetMapping("select")
    public ServerResponse<Shipping> select(HttpSession session, @RequestParam("id") Integer id) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "请登录后再操作");
        }
        return shippingService.select(user.getId(), id);
    }

    @GetMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "请登录后再操作");
        }
        return shippingService.list(user.getId(), pageNum, pageSize);
    }


    //    ================feign=================
    @GetMapping("get_shipping")
    public ServerResponse<Shipping> getShipping(@RequestParam("id") Integer id) {
        return shippingService.getShippingById(id);
    }
}

