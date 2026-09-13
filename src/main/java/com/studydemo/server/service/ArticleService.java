package com.studydemo.server.service;

import com.studydemo.server.common.PageResult;
import com.studydemo.server.dto.ArticleQueryDTO;
import com.studydemo.server.dto.ArticleSaveDTO;
import com.studydemo.server.dto.ArticleVO;

/**
 * 文章业务接口。
 */
public interface ArticleService {

    /**
     * 条件分页查询文章列表，按创建时间倒序。
     *
     * @param query 过滤条件（标题模糊、状态精确）+ 分页参数
     * @return 分页结果，records 为不含正文的文章视图对象
     */
    PageResult<ArticleVO> listArticles(ArticleQueryDTO query);

    /**
     * 查询单篇文章详情（含正文）。
     *
     * @param id 文章主键
     * @return 文章视图对象（含 content）
     */
    ArticleVO getArticle(Long id);

    /**
     * 新增文章。
     *
     * @param dto 标题/摘要/正文/封面/作者/状态
     * @return 新增后的文章（含主键与时间）
     */
    ArticleVO createArticle(ArticleSaveDTO dto);

    /**
     * 修改指定文章（只更新传入的非空字段）。
     *
     * @param id 文章主键
     * @param dto 新的字段值
     * @return 修改后的文章（含 content）
     */
    ArticleVO updateArticle(Long id, ArticleSaveDTO dto);

    /**
     * 逻辑删除指定文章。
     *
     * @param id 文章主键
     */
    void deleteArticle(Long id);
}
