package org.triber.demo.demo.service.impl.loginUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.triber.demo.demo.dao.mapper.loginUser.TScLoginUserMapper;
import org.triber.demo.demo.model.loginUser.TScLoginUser;
import org.triber.demo.demo.service.loginUser.ITScLoginUserService;

import java.util.Map;

/**
 * 系统用户表 服务实现类
 *
 * @author mayong
 * @since 2020-04-02
 */
@Service
public class TScLoginUserServiceImpl extends ServiceImpl<TScLoginUserMapper, TScLoginUser> implements ITScLoginUserService {

    @Override
    public Page selectUserPage(Map<String, Object> map) {
        Page page = new Page(1, 10);// 当前页，总条数
        return page.setRecords(this.baseMapper.selectUserList(page, map));
    }
}
