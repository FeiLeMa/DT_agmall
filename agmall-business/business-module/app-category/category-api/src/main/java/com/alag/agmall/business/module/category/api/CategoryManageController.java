package com.alag.agmall.business.module.category.api;


import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.category.api.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/manage/category")
public interface CategoryManageController {

    @GetMapping("getCategoryById")
    ServerResponse<Category> getById(@RequestParam("categoryId") Integer categoryId);

    @PostMapping("add_category")
    ServerResponse addCategory(@RequestParam(value = "categoryName") String categoryName,
                               @RequestParam(value = "parentId", defaultValue = "0") Integer parentId);

    @PostMapping("modify_category_name")
    ServerResponse modifyCategoryName(@RequestParam(value = "categoryId") Integer categoryId,
                                      @RequestParam(value = "categoryName") String categoryName);

    @GetMapping("get_parallel_category")
    ServerResponse getParallelCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId);

    @GetMapping("get_deep_child_id")
    ServerResponse<List<Integer>> getDeepChildId(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId);
}
