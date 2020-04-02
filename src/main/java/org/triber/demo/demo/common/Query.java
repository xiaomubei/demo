package org.triber.demo.demo.common;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName:  Query   
 * @Description:TODO(查询query)   
 * @author: wl
 * @date:   2018年10月9日 下午2:06:55   
 *
 */
public class Query<T> extends Page<T> {
	private static final String PAGE = "page";
	private static final String LIMIT = "size";
	private static final String ORDER_BY_FIELD = "orderByField";
	private static final String IS_ASC = "isAsc";
	//保存条件
	private Map<String, Object> condition;
	

	public Query(Map<String, Object> params) {
		super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString()),Integer.parseInt(params.getOrDefault(LIMIT, 10).toString()));
		//	
		String orderByField = params.getOrDefault(ORDER_BY_FIELD, "").toString();
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault(IS_ASC, Boolean.TRUE).toString());
		if (StrUtil.isNotEmpty(orderByField)) {
			if(isAsc) { //升序
				this.setAsc(orderByField);
			}else{
				this.setDesc(orderByField);
			}
		}
		params.remove(PAGE);
		params.remove(LIMIT);
		params.remove(ORDER_BY_FIELD);
		params.remove(IS_ASC);
		this.setCondition(params);
	}

	public Map<String, Object> getCondition() {
		
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		Map<String, Object> cond=new HashMap<String,Object>();
		//自动转驼峰 
		for(String key : condition.keySet()) {
			cond.put(StringUtils.camelToUnderline(key), condition.get(key));
		}
		this.condition = cond;
	}
	
}
