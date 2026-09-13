package com.studydemo.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studydemo.server.common.PageVO;
import com.studydemo.server.domain.Article;
import com.studydemo.server.dto.ArticleQueryDTO;
import com.studydemo.server.dto.ArticleSaveDTO;
import com.studydemo.server.dto.ArticleVO;
import com.studydemo.server.mapper.ArticleMapper;
import com.studydemo.server.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章业务实现。
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public PageVO<ArticleVO> listArticles(ArticleQueryDTO query) {
        // @TableLogic 自动追加 is_deleted = 0；次级排序用 id 保证翻页稳定
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .like(StringUtils.isNotBlank(query.getTitle()), Article::getTitle, query.getTitle())
                .eq(query.getStatus() != null, Article::getStatus, query.getStatus())
                .orderByDesc(Article::getCreateTime)
                .orderByDesc(Article::getId);

        Page<Article> page = new Page<>(query.resolvePageNum(), query.resolvePageSize());
        // 分页由 MybatisPlusConfig 注册的 PaginationInnerInterceptor 落地
        Page<Article> result = articleMapper.selectPage(page, wrapper);

        // 列表不下发明文，节省带宽
        List<ArticleVO> records = result.getRecords().stream()
                .map(this::toListVo)
                .collect(Collectors.toList());
        return PageVO.of(result, records);
    }

    @Override
    public ArticleVO getArticle(Long id) {
        Article entity = articleMapper.selectById(id);
        if (entity == null) {
            throw new IllegalArgumentException("文章不存在或已删除");
        }
        return toDetailVo(entity);
    }

    @Override
    public ArticleVO createArticle(ArticleSaveDTO dto) {
        Article entity = new Article();
        entity.setTitle(dto.getTitle());
        entity.setSummary(dto.getSummary());
        entity.setContent(dto.getContent());
        entity.setCover(dto.getCover());
        entity.setAuthor(dto.getAuthor());
        entity.setStatus(dto.getStatus());
        // views 默认 0，由数据库默认值兜底，这里不显式设置
        articleMapper.insert(entity);
        return toDetailVo(entity);
    }

    @Override
    public ArticleVO updateArticle(Long id, ArticleSaveDTO dto) {
        Article exist = articleMapper.selectById(id);
        if (exist == null) {
            throw new IllegalArgumentException("文章不存在或已删除");
        }
        // summary/content 等允许清空（空字符串是合法值），故只判 null 决定是否覆盖
        if (dto.getTitle() != null) exist.setTitle(dto.getTitle());
        if (dto.getSummary() != null) exist.setSummary(dto.getSummary());
        if (dto.getContent() != null) exist.setContent(dto.getContent());
        if (dto.getCover() != null) exist.setCover(dto.getCover());
        if (dto.getAuthor() != null) exist.setAuthor(dto.getAuthor());
        if (dto.getStatus() != null) exist.setStatus(dto.getStatus());
        articleMapper.updateById(exist);
        return toDetailVo(exist);
    }

    @Override
    public void deleteArticle(Long id) {
        // 逻辑删除：@TableLogic 把 is_deleted 置 1
        if (articleMapper.deleteById(id) == 0) {
            throw new IllegalArgumentException("文章不存在或已删除");
        }
    }

    /** 列表视图对象（不含正文） */
    private ArticleVO toListVo(Article entity) {
        ArticleVO vo = new ArticleVO();
        vo.setId(entity.getId());
        vo.setTitle(entity.getTitle());
        vo.setSummary(entity.getSummary());
        vo.setCover(entity.getCover());
        vo.setAuthor(entity.getAuthor());
        vo.setStatus(entity.getStatus());
        vo.setStatusText(entity.getStatus() != null && entity.getStatus() == 1 ? "已发布" : "草稿");
        vo.setViews(entity.getViews());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    /** 详情视图对象（含正文） */
    private ArticleVO toDetailVo(Article entity) {
        ArticleVO vo = toListVo(entity);
        vo.setContent(entity.getContent());
        return vo;
    }
}
