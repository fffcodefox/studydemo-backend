package com.studydemo.server.controller;

import com.studydemo.server.common.ApiResponseVo;
import com.studydemo.server.common.PageVO;
import com.studydemo.server.dto.ArticleIdReq;
import com.studydemo.server.dto.ArticleQueryDTO;
import com.studydemo.server.dto.ArticleSaveDTO;
import com.studydemo.server.dto.ArticleVO;
import com.studydemo.server.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章管理接口（对齐 cdp_service 的 BasicPropertyManageController 风格）。
 *
 * <p>统一返回 {@link ApiResponseVo}；列表、详情、增、改、删均走 POST（入参放 body）；
 * 路径分组在 /api/console/articleManage 下，与现有 /api 代理约定保持一致，
 * 因此前端无需改动 vite proxy（请求 /api/console/articleManage/xxx 仍由 {@code ^/api/} 转发到 8080）。</p>
 */
@RestController
@Tag(name = "内容管理-文章管理API", description = "文章管理API")
@RequestMapping("/api/console/articleManage")
public class ArticleController {

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(summary = "文章管理列表查询接口", description = "文章管理列表查询接口")
    @PostMapping(value = "queryArticleList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseVo<PageVO<ArticleVO>> queryArticleList(@RequestBody ArticleQueryDTO reqVo) {
        try {
            return new ApiResponseVo<>(articleService.listArticles(reqVo));
        } catch (Exception e) {
            log.error("文章管理列表查询接口异常，异常信息：{}", e.getMessage(), e);
            return new ApiResponseVo<>(e.getClass().getName(), e.getMessage(), null);
        }
    }

    @Operation(summary = "文章详情查询接口", description = "文章详情查询接口（含正文 content）")
    @PostMapping(value = "getArticleDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseVo<ArticleVO> getArticleDetail(@RequestBody ArticleIdReq req) {
        try {
            return new ApiResponseVo<>(articleService.getArticle(req.getId()));
        } catch (Exception e) {
            log.error("文章详情查询接口异常，异常信息：{}", e.getMessage(), e);
            return new ApiResponseVo<>(e.getClass().getName(), e.getMessage(), null);
        }
    }

    @Operation(summary = "新增文章接口", description = "新增文章接口")
    @PostMapping(value = "addArticle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseVo<ArticleVO> addArticle(@RequestBody ArticleSaveDTO dto) {
        try {
            if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("标题不能为空");
            }
            if (dto.getStatus() == null) {
                throw new IllegalArgumentException("状态不能为空");
            }
            return new ApiResponseVo<>(articleService.createArticle(dto));
        } catch (Exception e) {
            log.error("新增文章接口异常，异常信息：{}", e.getMessage(), e);
            return new ApiResponseVo<>(e.getClass().getName(), e.getMessage(), null);
        }
    }

    @Operation(summary = "修改文章接口", description = "修改文章接口")
    @PostMapping(value = "updateArticle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseVo<ArticleVO> updateArticle(@RequestBody ArticleSaveDTO dto) {
        try {
            if (dto.getId() == null) {
                throw new IllegalArgumentException("文章ID不能为空");
            }
            if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("标题不能为空");
            }
            if (dto.getStatus() == null) {
                throw new IllegalArgumentException("状态不能为空");
            }
            return new ApiResponseVo<>(articleService.updateArticle(dto.getId(), dto));
        } catch (Exception e) {
            log.error("修改文章接口异常，异常信息：{}", e.getMessage(), e);
            return new ApiResponseVo<>(e.getClass().getName(), e.getMessage(), null);
        }
    }

    @Operation(summary = "删除文章接口", description = "删除文章接口（逻辑删除，is_deleted 置 1）")
    @PostMapping(value = "deleteArticle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseVo<Void> deleteArticle(@RequestBody ArticleIdReq req) {
        try {
            articleService.deleteArticle(req.getId());
            return new ApiResponseVo<>(null);
        } catch (Exception e) {
            log.error("删除文章接口异常，异常信息：{}", e.getMessage(), e);
            return new ApiResponseVo<>(e.getClass().getName(), e.getMessage(), null);
        }
    }
}
