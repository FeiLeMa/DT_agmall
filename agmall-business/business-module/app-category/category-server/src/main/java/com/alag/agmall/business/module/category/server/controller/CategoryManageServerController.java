package com.alag.agmall.business.module.category.server.controller;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.category.api.model.Category;
import com.alag.agmall.business.module.category.server.Interceptor.LoginRequired;
import com.alag.agmall.business.module.category.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/manage/category")
public class CategoryManageServerController {
    @Autowired
    private CategoryService categoryService;


    @PostMapping("add_category")
    @LoginRequired
    public ServerResponse addCategory(HttpSession session,
                                      @RequestParam(value = "categoryName") String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        return categoryService.addCategory(categoryName, parentId);
    }

    @PostMapping("modify_category_name")
    @LoginRequired
    public ServerResponse modifyCategoryName(HttpSession session,
                                             @RequestParam(value = "categoryId") Integer categoryId,
                                             @RequestParam(value = "categoryName") String categoryName) {
        return categoryService.modifyCategoryName(categoryId, categoryName);
    }

    @GetMapping("get_parallel_category")
    @LoginRequired
    public ServerResponse getParallelCategory(HttpSession session,
                                              @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return categoryService.getParallelCategoryByParentId(categoryId);
    }

    @GetMapping("get_deep_child_id")
    @LoginRequired
    public ServerResponse<List<Integer>> getDeepChildId(HttpSession session,
                                                        @RequestParam(value = "categoryId") Integer categoryId) {
        return categoryService.getAllDeepChildId(categoryId);
    }

    @GetMapping("getCategoryById")
    public ServerResponse<Category> getById(@RequestParam("categoryId") Integer categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

}
