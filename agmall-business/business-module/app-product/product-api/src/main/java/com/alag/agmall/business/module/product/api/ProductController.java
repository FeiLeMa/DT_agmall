package com.alag.agmall.business.module.product.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.ProductDetailVo;
import com.alag.agmall.business.module.product.api.model.Product;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
public interface ProductController {


    @GetMapping("/detail/{productId}")
    ServerResponse<ProductDetailVo> getDetail(@PathVariable Integer productId);

    @GetMapping("/list/{keyword}/{categoryId}/{pageNum}/{pageSize}")
    ServerResponse<PageInfo> list(@PathVariable(value = "keyword") String keyword,
                                  @PathVariable(value = "categoryId") Integer categoryId,
                                  @PathVariable(value = "pageNum") Integer pageNum,
                                  @PathVariable(value = "pageSize") Integer pageSize);

    @PostMapping("add")
    ServerResponse saveProduct(@RequestBody Product product);

    @PutMapping("set_sale_status")
    ServerResponse<String> setSaleStatus(@RequestParam("productId") Integer productId,
                                         @RequestParam("status") Integer status);

    @GetMapping("list")
    ServerResponse list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize);

    @GetMapping("search")
    ServerResponse<PageInfo> search(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(value = "productName") String productName,
                                    @RequestParam(value = "productId") Integer productId);



    //    ==============feign================
    @GetMapping("get_pj_prodcut")
    ServerResponse<Product> getPJPById(@RequestParam("id") Integer id);

    @GetMapping("get_stock")
    ServerResponse<Integer> getStock(@RequestParam("id") Integer id);

    @PutMapping("modify")
    ServerResponse<Integer> modifyProduct(@RequestBody Product product);




}

