package org.triber.demo.demo.api.loginUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.triber.demo.demo.common.R;
import org.triber.demo.demo.common.RedisUtil;
import org.triber.demo.demo.service.loginUser.ITScLoginUserService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:TODO(系统用户表接口api)
 * @author: mayong
 * @date: 2020-04-02
 */
@RestController
@RequestMapping("/tScLoginUser")
@Api(tags = "用户查询")
public class TScLoginUserController {
    @Autowired
    private ITScLoginUserService tScLoginUserService;

    @Resource
    private RedisUtil redisUtil;

    public static void main(String[] args) {

    }

    @ApiOperation(value = "获取数据", httpMethod = "GET", notes = "获取分页数据")
    @ApiImplicitParam(name = "page", value = "页数", dataType = "String", paramType = "query", defaultValue = "1", required = true)
    @RequestMapping("/page")
    public R selectPage(@RequestParam(value = "page") String page) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        Page page1 = tScLoginUserService.selectUserPage(map);
        redisUtil.set("username", "mayong");
        System.out.println("redis 获取：" + redisUtil.get("username"));
        redisUtil.delete("username");
        System.out.println("redis 删除key");
        return new R<>(page1.getRecords(), page1.getTotal());
    }

    @ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户测试登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户账号", paramType = "query", dataType = "String", defaultValue = "侯加林", required = true),
            @ApiImplicitParam(name = "userPass", value = "用户密码", paramType = "query", dataType = "String", defaultValue = "password", required = true)
    })
    @RequestMapping("/getUser")
    public Map getUser(@RequestParam(value = "userName") String userName, @RequestParam(value = "userPass") String userPass) {
        return tScLoginUserService.loadLoginUser(userName, userPass);
    }
}
