package com.limou.backendservice.controller.inner;

import com.limou.backendmodel.model.entity.User;
import com.limou.backendservice.service.UserService;
import com.limou.backendserviceclient.service.UserFeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;

    @Override
    public User getById(long userId) {
        return userService.getById(userId);
    }

    @Override
    public List<User> listByIds(Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
