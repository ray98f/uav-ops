package com.uav.ops.utils.task;

import com.uav.ops.service.DeptService;
import com.uav.ops.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author frp
 */
@Component
@Slf4j
public class SyncTask {

    @Resource
    private UserService userService;

    @Resource
    private DeptService deptService;

    @Scheduled(cron = "0 0 2 * * ?")
    @Async
    public void syncOrg() {
        deptService.syncDept();
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Async
    public void syncUser() {
        userService.syncUser();
    }
}
