package com.studydemo.server.controller;

import com.studydemo.server.common.PageResult;
import com.studydemo.server.common.Result;
import com.studydemo.server.dto.ArticleQueryDTO;
import com.studydemo.server.dto.ArticleSaveDTO;
import com.studydemo.server.dto.ArticleVO;
import com.studydemo.server.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 文章管理接口。
 *
 * <p>列表查询与详情查询分离：GET /api/articles 走条件分页列表（不含正文），
 * GET /api/articles/{id} 走单条详情（含正文）。</p>
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 分页列表查询：支持 title 模糊、status 精确过滤。
     */
    @GetMapping
    public Result<PageResult<ArticleVO>> list(ArticleQueryDTO query) {
        return Result.success(articleService.listArticles(query));
    }

    /**
     * 详情查询（含正文）。
     */
    @GetMapping("/{id}")
    public Result<ArticleVO> detail(@PathVariable Long id) {
        return Result.success(articleService.getArticle(id));
    }

    /**
     * 新增。
     */
    @PostMapping
    public Result<ArticleVO> create(@Valid @RequestBody ArticleSaveDTO dto) {
        return Result.success(articleService.createArticle(dto));
    }

    /**
     * 修改。
     */
    @PutMapping("/{id}")
    public Result<ArticleVO> update(@PathVariable Long id, @Valid @RequestBody ArticleSaveDTO dto) {
        return Result.success(articleService.updateArticle(id, dto));
    }

    /**
     * 删除（逻辑删除）。
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Result.success();
    }
}
