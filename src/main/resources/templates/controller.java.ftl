package ${package.Controller};

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${package.Service}.I${entity}Service;

import cn.hutool.core.collection.CollUtil;
import org.triber.dwbi.share.common.Query;
import org.triber.dwbi.share.common.R;
/**
 * 
 * @Description:TODO(${table.comment!}接口api)   
 * @author: ${author}
 * @date:   ${date} 
 *
 */
@RestController
@RequestMapping("/${table.entityPath}")
public class ${table.controllerName}{
	@Autowired 
	private I${entity!""}Service ${table.entityPath!""}Service;
	
   /**
        * 通过ID查询
    *
    * @param id ID
    * @return R
    */
    @GetMapping("/{id}")
    public R info(@PathVariable Integer id) {
        return new R<>(${table.entityPath!}Service.getById(id));
    }


    /**
        * 分页查询信息
    *
    * @param params 分页对象
    * @return 分页对象
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        Page page=(Page) ${table.entityPath!}Service.page(new Query<>(params), new QueryWrapper<>());
        return new R<>(page.getRecords(),page.getTotal()); 
    }

    /**
          * 添加
     * @param  ${table.entityPath}  实体
     * @return R
     */
    @PostMapping
    public R save(@RequestBody ${entity!} ${table.entityPath}) {
        return new R<>(${table.entityPath!}Service.save(${table.entityPath}));
    }


    /**
         * 编辑
     * @param  ${table.entityPath}  实体
     * @return R
     */
    @PutMapping
    public R update(@RequestBody ${entity} ${table.entityPath}) {
        return new R<>(${table.entityPath}Service.updateById(${table.entityPath}));
    }
    
    /**
          * 删除
     * @param id ID
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        return new R<>(${table.entityPath}Service.removeById(id));
    }
    /**
     * 
     * @Description: TODO(批量删除)   
     * @param: @param ids
     * @param: @return      
     * @return: R<Boolean>      
     * @throws
     */
    @RequestMapping("/dels")
    public R deletes(Integer[] ids){
    	if(ids.length>0) {
    		return new R<>(${table.entityPath}Service.removeByIds(CollUtil.newArrayList(ids)));
    	}else {
    		return new R<>(false);
    	}
    }
}
