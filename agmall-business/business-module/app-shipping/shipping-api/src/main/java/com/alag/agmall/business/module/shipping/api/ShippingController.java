package com.alag.agmall.business.module.shipping.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.shipping.api.model.Shipping;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping/")
public interface ShippingController {


    @PostMapping("add")
    ServerResponse add(@RequestBody Shipping shipping);

    @DeleteMapping("del")
    ServerResponse del(@RequestParam(value = "id") Integer id);

    @PutMapping("update")
    ServerResponse del(@RequestBody Shipping shipping);

    @GetMapping("select")
    ServerResponse<Shipping> select(@RequestParam(value = "id") Integer id);

    @GetMapping("list")
    ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);


    @GetMapping("get_shipping")
    ServerResponse<Shipping> getShipping(@RequestParam("id") Integer id);

}
