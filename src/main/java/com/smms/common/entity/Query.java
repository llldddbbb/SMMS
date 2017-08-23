package com.smms.common.entity;

import com.smms.common.xss.SqlFilter;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 封装表格查询参数
 */
public class Query extends LinkedHashMap<String,Object>{
    //当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String,Object> params){
        this.putAll(params);

        this.page=Integer.parseInt(params.get("page").toString());
        this.limit=Integer.parseInt(params.get("limit").toString());
        this.put("offset",(page-1)*limit);
        this.put("page",page);
        this.put("limit",limit);
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String)params.get("sidx");
        String order = (String)params.get("order");
        if(!StringUtils.isEmpty(sidx)){
            this.put("sidx", com.smms.common.util.StringUtils.underscoreName(sidx));
        }
        if(!StringUtils.isEmpty(order)){
            this.put("order", SqlFilter.sqlInject(order));
        }

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
