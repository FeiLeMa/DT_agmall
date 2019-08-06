package com.alag.agmall.business.module.productback.server.controller;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.ProductDetailVo;
import com.alag.agmall.business.module.product.api.model.Product;
import com.alag.agmall.business.module.product.feign.controller.ProductFeignClient;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/product")
@Slf4j
public class ProductManageServerController {
    @Autowired
    private ProductFeignClient productFeignClient;

    @PostMapping("save_product")
    public ServerResponse saveProduct(@RequestBody Product product) {
        log.info(product.toString());
        return productFeignClient.saveProduct(product);
    }

    @PutMapping("set_sale_status")
    public ServerResponse<String> setSaleStatus(@RequestParam("productId") Integer productId,
                                                @RequestParam("status") Integer status) {
        return productFeignClient.setSaleStatus(productId, status);
    }

    @GetMapping("get_detail")
    public ServerResponse<ProductDetailVo> getDetail(@RequestParam("productId") Integer productId) {
        return productFeignClient.getDetail(productId);
    }

    @GetMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return productFeignClient.list(pageNum, pageSize);
    }

    @GetMapping("search")
    public ServerResponse<PageInfo> search(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(value = "productName") String productName,
                                           @RequestParam(value = "productId") Integer productId) {
        return productFeignClient.search(pageNum, pageSize, productName, productId);
    }

//    @PostMapping("upload")
//    public ServerResponse<Map> upload( @RequestParam(value = "upload_file", required = true) MultipartFile file) {
//        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "upload/";
//        String targetFileName = fileService.upload(file, path);
//        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//
//        Map fileMap = Maps.newHashMap();
//        fileMap.put("uri", targetFileName);
//        fileMap.put("url", url);
//        return ServerResponse.createBySuccess(fileMap);
//    }
//
//    @PostMapping("richtext_img_upload")
//    public Map richtextImgUpload( HttpServletResponse response,
//                                 @RequestParam(value = "upload_file", required = true) MultipartFile file) {
//        Map resultMap = Maps.newHashMap();
//        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "upload/";
//        String targetFileName = fileService.upload(file, path);
//        if (StringUtils.isBlank(targetFileName)) {
//            resultMap.put("success", false);
//            resultMap.put("msg", "上传失败");
//            return resultMap;
//        }
//        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//        resultMap.put("success", true);
//        resultMap.put("msg", "上传成功");
//        resultMap.put("file_path", url);
//        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
//        return resultMap;
//    }


}
