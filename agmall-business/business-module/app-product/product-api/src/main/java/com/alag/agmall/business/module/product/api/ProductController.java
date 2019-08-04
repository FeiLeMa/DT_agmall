package com.alag.agmall.business.module.product.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public interface ProductController {


    @GetMapping("/detail/{productId}")
    ServerResponse<ProductDetailVo> getDetail(@PathVariable("productId") Integer productId);

    @GetMapping("/list/{keyword}/{categoryId}/{pageNum}/{pageSize}")
    ServerResponse<PageInfo> list(@PathVariable(value = "keyword") String keyword,
                                  @PathVariable(value = "categoryId") Integer categoryId,
                                  @PathVariable(value = "pageNum") Integer pageNum,
                                  @PathVariable(value = "pageSize") Integer pageSize);
}
