package com.studydemo.server.service;

import com.studydemo.server.dto.HelloVO;

/**
 * 打招呼业务接口。
 */
public interface HelloService {

    /**
     * 生成打招呼信息。
     *
     * @param name 称呼，可空
     * @return 打招呼视图对象
     */
    HelloVO sayHello(String name);
}
