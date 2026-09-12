package com.studydemo.server.controller;

import com.studydemo.server.common.Result;
import com.studydemo.server.dto.MessageSaveDTO;
import com.studydemo.server.dto.MessageVO;
import com.studydemo.server.service.MessageService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 留言板接口。
 *
 * <p>统一前缀 /api，与前端 Vite 代理（^/api/）对应；再往下走 Nginx 反向代理时也一致。</p>
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public Result<List<MessageVO>> list() {
        return Result.success(messageService.listMessages());
    }

    @PostMapping
    public Result<MessageVO> create(@Valid @RequestBody MessageSaveDTO dto) {
        return Result.success(messageService.createMessage(dto));
    }

    @PutMapping("/{id}")
    public Result<MessageVO> update(@PathVariable Long id, @Valid @RequestBody MessageSaveDTO dto) {
        return Result.success(messageService.updateMessage(id, dto));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return Result.success();
    }
}
