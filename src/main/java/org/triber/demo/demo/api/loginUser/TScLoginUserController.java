package org.triber.demo.demo.api.loginUser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triber.demo.demo.common.R;
import org.triber.demo.demo.service.loginUser.ITScLoginUserService;

/**
 * @Description:TODO(系统用户表接口api)
 * @author: mayong
 * @date: 2020-04-02
 */
@RestController
@RequestMapping("/tScLoginUser")
public class TScLoginUserController {
    @Autowired
    private ITScLoginUserService tScLoginUserService;

    @ApiOperation(value = "获取数据", httpMethod = "GET", notes = "获取分页数据")
    @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query")
    @RequestMapping("/page")
    public R selectPage() {
        Page page = tScLoginUserService.selectUserPage(null);
        return new R<>(page.getRecords(), page.getTotal());
    }
}
