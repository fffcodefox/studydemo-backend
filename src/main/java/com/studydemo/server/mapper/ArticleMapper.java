package com.studydemo.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.server.domain.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表 Mapper。
 *
 * <p>只声明继承关系，增删改查与分页全部来自 MyBatis-Plus 的 {@link BaseMapper}，无需手写 XML。</p>
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
