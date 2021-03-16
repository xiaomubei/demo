package org.triber.demo.demo.helper;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.Token;
import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.runtime.ParserException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.domain.Select;
import org.nlpcn.es4sql.parse.ElasticSqlExprParser;
import org.nlpcn.es4sql.parse.SqlParser;
import org.nlpcn.es4sql.query.AggregationQueryAction;
import org.nlpcn.es4sql.query.DefaultQueryAction;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;

import java.net.InetAddress;
import java.util.Map;

/**
 * @author Yong
 * @DESCRIPTION:
 * @date 2020/10/18 22:41
 */
public class ElasticsearchConfig {
    public static void main(String[] args) {
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "elasticsearch")
                    .build();
            TransportClient client = new PreBuiltTransportClient(settings);
            client.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.88.132"), 9300));
            //创建sql查询对象
            SearchDao searchDao = new SearchDao(client);
            //searchDao.explain("select * from test ");
            //执行sql查询
            String sql="select * from toaf_info";
            //    SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) searchDao
            //        .explain(sql)
            //        .explain();
            ////使用阿里巴巴的json格式化工具，resultMap的值就是查询返回的值
            //Map resultMap = (Map) JSON.parse((select.get()).toString());
            //System.out.println(resultMap.get("hits"));

        SQLExprParser parser = new ElasticSqlExprParser(sql);
        SQLExpr expr = parser.expr();
        if (parser.getLexer().token() != Token.EOF) {
            throw new ParserException("illegal sql expr : " + sql);
        }
        SQLQueryExpr queryExpr=(SQLQueryExpr) expr;
        //通过抽象语法树，封装成自定义的Select，包含了select、from、where group、limit等
        Select select = new SqlParser().parseSelect(queryExpr);

        AggregationQueryAction action;
        DefaultQueryAction queryAction = null;
        if (select.isAgg) {
            //包含计算的的排序分组的
            //request.setSearchType(SearchType.DEFAULT);
            action= new AggregationQueryAction(client, select);
        } else {
            //封装成自己的Select对象
            queryAction = new DefaultQueryAction(client, select);
        }
        // 把属性封装在SearchRequestBuilder(client.prepareSearch()获取的即ES中获取的方法)对象中
        // 然后装饰了一下SearchRequestBuilder为自定义的SqlElasticSearchRequestBuilder
        SqlElasticSearchRequestBuilder requestBuilder = queryAction.explain();
        //之后就是对ES的操作
        SearchResponse response=(SearchResponse) requestBuilder.get();
        SearchHit[] hists = response.getHits().getHits();
        System.out.println(hists.length);
        for(SearchHit hit:hists){
            System.out.println(hit.getSourceAsString());
        }
        } catch (Exception ex) {
            System.out.println("查询异常-"+ ex);
        }
    }



}