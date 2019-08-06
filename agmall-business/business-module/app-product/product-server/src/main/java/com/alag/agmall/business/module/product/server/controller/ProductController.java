package com.alag.agmall.business.module.product.server.controller;


import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.ProductDetailVo;
import com.alag.agmall.business.module.product.api.model.Product;
import com.alag.agmall.business.module.product.server.service.ProductService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/detail/{productId}")
    public ServerResponse<ProductDetailVo> getDetail(@PathVariable Integer productId) {
        return productService.getProductDetail(productId);
    }

    @GetMapping("/list/{keyword}/{categoryId}/{pageNum}/{pageSize}")
    public ServerResponse<PageInfo> list(@PathVariable(value = "keyword") String keyword,
                                         @PathVariable(value = "categoryId") Integer categoryId,
                                         @PathVariable(value = "pageNum") Integer pageNum,
                                         @PathVariable(value = "pageSize") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        return productService.getListByKeyword(keyword, categoryId, pageNum, pageSize);
    }

    //===========back-product

    @PostMapping("add")
    public ServerResponse saveProduct(HttpSession session, @RequestBody Product product) {
        log.info(product.toString());
        return productService.saveProduct(product);
    }

    @PutMapping("set_sale_status")
    public ServerResponse<String> setSaleStatus(HttpSession session,
                                                @RequestParam("productId") Integer productId,
                                                @RequestParam("status") Integer status) {

        return productService.setSaleStatus(productId, status);
    }

    @GetMapping("list")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return productService.list(pageNum, pageSize);
    }

    @GetMapping("search")
    public ServerResponse<PageInfo> search(HttpSession session,
                                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(value = "productName") String productName,
                                           @RequestParam(value = "productId") Integer productId) {
        return productService.getProductByIdAndName(pageNum, pageSize, productName, productId);
    }

    @GetMapping("get_pj_prodcut")
    public ServerResponse<Product> getPJPById(@RequestParam("id") Integer id) {
        return productService.getPOJOProductById(id);
    }
}
