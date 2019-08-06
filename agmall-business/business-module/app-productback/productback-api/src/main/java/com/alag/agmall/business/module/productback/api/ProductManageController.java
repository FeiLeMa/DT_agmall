package com.alag.agmall.business.module.productback.api;


import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.ProductDetailVo;
import com.alag.agmall.business.module.product.api.model.Product;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/manage/product")
public interface ProductManageController {


    @PostMapping("save_product")
    ServerResponse saveProduct(@RequestBody Product product);

    @PostMapping("set_sale_status")
    ServerResponse<String> setSaleStatus(@RequestParam("productId") Integer productId,
                                         @RequestParam("status") Integer status);

    @GetMapping("get_detail")
    ServerResponse<ProductDetailVo> getDetail(@RequestParam("productId") Integer productId);

    @GetMapping("list")
    ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize);

    @GetMapping("search")
    ServerResponse<PageInfo> search(@RequestParam(value = "productName") String productName,
                                    @RequestParam(value = "productId") Integer productId,
                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize);

    @PostMapping("upload")
    ServerResponse<Map> upload(@RequestParam(value = "upload_file", required = true) MultipartFile file);

    @PostMapping("richtext_img_upload")
    Map richtextImgUpload(
            @RequestParam(value = "upload_file", required = true) MultipartFile file);

}
