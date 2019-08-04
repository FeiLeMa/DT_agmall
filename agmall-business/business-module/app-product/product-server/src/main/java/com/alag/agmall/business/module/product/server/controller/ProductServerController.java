package com.alag.agmall.business.module.product.server.controller;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.ProductDetailVo;
import com.alag.agmall.business.module.product.server.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductServerController {

    @Autowired
    private ProductService productService;

    @GetMapping("/detail/{productId}")
    public ServerResponse<ProductDetailVo> getDetail(@PathVariable Integer productId){
        return productService.getProductDetail(productId);
    }

    @GetMapping("/list/{keyword}/{categoryId}/{pageNum}/{pageSize}")
    public ServerResponse<PageInfo> list(@PathVariable(value = "keyword")String keyword,
                                         @PathVariable(value = "categoryId")Integer categoryId,
                                         @PathVariable(value = "pageNum")Integer pageNum,
                                         @PathVariable(value = "pageSize")Integer pageSize){
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        return productService.getListByKeyword(keyword, categoryId, pageNum, pageSize);
    }
}
