package com.studydemo.server.controller;

import com.studydemo.server.common.Result;
import com.studydemo.server.dto.HelloVO;
import com.studydemo.server.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打招呼接口，演示前后端分离下单接口联通。
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public Result<HelloVO> hello(@RequestParam(value = "name", required = false) String name) {
        HelloVO vo = helloService.sayHello(name);
        return Result.success(vo);
    }
}
