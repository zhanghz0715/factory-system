package com.factory.boot.util;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description:
 * @Version V1.0
 * @Author zhanghz
 * Created on 2020/4/19.
 */
public class ToolUtil {


    /**
     * @param xml     要转换的xml字符串
     * @param charset 字符编码
     * @return 转换成map后返回结果
     * @throws UnsupportedEncodingException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(String xml, String charset) {

        Map<String, String> respMap = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(new ByteArrayInputStream(xml.getBytes(charset)));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        xmlToMap(root, respMap);
        return respMap;
    }

    public static Map<String, String> xmlToMap(Element tmpElement, Map<String, String> respMap) {
        ArrayList<String> strings = new ArrayList<String>();
        for (String s : strings
                ) {

        }
        if (tmpElement.isTextOnly()) {
            respMap.put(tmpElement.getName(), tmpElement.getText());
            return respMap;
        }

        @SuppressWarnings("unchecked")
        Iterator<Element> eItor = tmpElement.elementIterator();
        while (eItor.hasNext()) {
            Element element = eItor.next();
            xmlToMap(element, respMap);
        }
        return respMap;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }


}
