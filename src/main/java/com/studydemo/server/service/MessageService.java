package com.studydemo.server.service;

import com.studydemo.server.dto.MessageSaveDTO;
import com.studydemo.server.dto.MessageVO;

import java.util.List;

/**
 * 留言板业务接口。
 */
public interface MessageService {

    /**
     * 查询留言列表，按创建时间倒序。
     *
     * @return 留言视图对象列表
     */
    List<MessageVO> listMessages();

    /**
     * 新增一条留言。
     *
     * @param dto 称呼与内容
     * @return 新增后的留言（含主键与创建时间）
     */
    MessageVO createMessage(MessageSaveDTO dto);

    /**
     * 修改指定留言。
     *
     * @param id  留言主键
     * @param dto 新的称呼与内容
     * @return 修改后的留言
     */
    MessageVO updateMessage(Long id, MessageSaveDTO dto);

    /**
     * 逻辑删除指定留言。
     *
     * @param id 留言主键
     */
    void deleteMessage(Long id);
}
