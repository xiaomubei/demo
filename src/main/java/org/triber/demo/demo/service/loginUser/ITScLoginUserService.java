package org.triber.demo.demo.service.loginUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.triber.demo.demo.model.loginUser.TScLoginUser;

import java.util.Map;

/**
 * 系统用户表服务类
 *
 * @author mayong
 * @since 2020-04-02
 */
public interface ITScLoginUserService extends IService<TScLoginUser> {
    Page selectUserPage(Map<String, Object> map);
}
