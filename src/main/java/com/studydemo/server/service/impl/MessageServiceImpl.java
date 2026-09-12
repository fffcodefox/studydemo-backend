package com.studydemo.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.studydemo.server.domain.DemoMessage;
import com.studydemo.server.dto.MessageSaveDTO;
import com.studydemo.server.dto.MessageVO;
import com.studydemo.server.mapper.DemoMessageMapper;
import com.studydemo.server.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 留言板业务实现。
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final DemoMessageMapper demoMessageMapper;

    public MessageServiceImpl(DemoMessageMapper demoMessageMapper) {
        this.demoMessageMapper = demoMessageMapper;
    }

    @Override
    public List<MessageVO> listMessages() {
        // lambdaQuery 走方法引用，避免硬编码列名；@TableLogic 会自动追加 deleted=0
        List<DemoMessage> list = demoMessageMapper.selectList(
                Wrappers.<DemoMessage>lambdaQuery().orderByDesc(DemoMessage::getCreateTime));
        return list.stream().map(this::toVo).collect(Collectors.toList());
    }

    @Override
    public MessageVO createMessage(MessageSaveDTO dto) {
        DemoMessage entity = new DemoMessage();
        entity.setName(dto.getName());
        entity.setContent(dto.getContent());
        demoMessageMapper.insert(entity);
        return toVo(entity);
    }

    @Override
    public MessageVO updateMessage(Long id, MessageSaveDTO dto) {
        DemoMessage exist = demoMessageMapper.selectById(id);
        if (exist == null) {
            throw new IllegalArgumentException("消息不存在或已删除");
        }
        exist.setName(dto.getName());
        exist.setContent(dto.getContent());
        demoMessageMapper.updateById(exist);
        return toVo(exist);
    }

    @Override
    public void deleteMessage(Long id) {
        // 逻辑删除：MyBatis-Plus 按 @TableLogic 把 deleted 置 1，返回受影响行数
        if (demoMessageMapper.deleteById(id) == 0) {
            throw new IllegalArgumentException("消息不存在或已删除");
        }
    }

    /**
     * 实体转视图对象。
     *
     * @param entity 持久化实体
     * @return 前端视图对象
     */
    private MessageVO toVo(DemoMessage entity) {
        MessageVO vo = new MessageVO();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setContent(entity.getContent());
        vo.setCreateTime(entity.getCreateTime());
        return vo;
    }
}
