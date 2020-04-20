package org.triber.demo.demo.dao.mapper.loginUser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.triber.demo.demo.model.loginUser.TScLoginUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author mayong
 * @since 2020-04-02
 */
@Mapper
public interface TScLoginUserMapper extends BaseMapper<TScLoginUser> {
    List selectUserList(Page page, @Param("params") Map<String, Object> map);

    Map<String, String> loadLoginUser(String userName, String userPass);

}
