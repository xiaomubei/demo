package org.triber.demo.demo;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void dom4jTest() {
        //创建待解析的XML文件，并指定目录
        File file = new File("E:\\gyxd.xml");
        //指定XML解析器SAXReader
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(file);
            Element root = document.getRootElement();
            //获取1节点 Schema name 值
            System.out.println(root.attributeValue("name"));
            List<Element> elements = root.elements();
            for (Element element : elements) {
                //获取2节点 Cube name 值（报文名称）
                if (element.attributeValue("enabled").equals("true"))
                    System.out.println(element.attributeValue("name"));
                //获取2节点 Table name 值（报文对应表名称）
                System.out.println(element.element("Table").attributeValue("name"));
                //获取2节点 Dimension 值 （维度信息）
                List<Element> elements1=element.elements("Dimension");
                for (Element element1 : elements1) {
                    //获取2节点 Dimension 值 （维度名称以及主键）
                    System.out.println(element1.attributeValue("name")+"-"+element1.attributeValue("foreignKey"));
                    //获取2节点 Dimension  Hierarchy 值 （维度名对应表）
                   Element e= element1.element("Hierarchy").element("Table");
                    System.out.println(e!=null?e.attributeValue("name"):element.element("Table").attributeValue("name")); // 表内不存在取实时表 group by
                    List<Element> elements2= element1.element("Hierarchy").elements("Level");
                    //获取3节点 Dimension  Hierarchy Level 值 （维度表对应字段）
                    for (Element element2 : elements2) {
                        System.out.println(element2.attributeValue("name")+"-"+element2.attributeValue("ordinalColumn")+"-"+element2.attributeValue("nameColumn")+"-"+element2.attributeValue("column"));
                    }
                }
                List<Element> elements3=element.elements("Measure");
                for (Element element3 : elements3) {
                    System.out.println(element3.attributeValue("name")+"-"+element3.attributeValue("column")+"-"+element3.attributeValue("aggregator")+"-"+element3.attributeValue("formatString")+"-"+element3.attributeValue("visible"));
                }
                List<Element> elements4=element.elements("CalculatedMember");
                for (Element element4 : elements4) {
                    System.out.println(element4.attributeValue("name")+"-"+element4.attributeValue("formatString")+"-"+element4.attributeValue("formula")+"-"+element4.attributeValue("dimension"));
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}
