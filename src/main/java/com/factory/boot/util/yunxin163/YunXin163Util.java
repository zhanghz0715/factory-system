package com.factory.boot.util.yunxin163;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Firrela
 * @time 2016/3/7.
 */
@Slf4j
public class YunXin163Util {

    private static final String APPKEY = "da764fefdc245dbeb666cbc1d50b14a3"; // AppKey
    private static final String SECRET = "df5a00eed861"; // AppSecret

    /**
     * @description: 创建网易云信用户
     * @param {type}
     * @return:
     */
    public static String createUser(String accid, String name, String token) throws IOException {
        String url = "https://api.netease.im/nimserver/user/create.action";
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("accid", accid));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("token", token));

        // UTF-8编码,解决中文问题
        HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

        String res = NIMPost.postNIMServer(url, entity, APPKEY, SECRET);
        log.info("createUser httpRes: {}", res);
        return res;
    }
    
    /**
     * @description: 创建网易云信用户
     * @param {type}
     * @return:
     */
    public static String updateUinfo(String accid, String name) throws IOException {
        String url = "https://api.netease.im/nimserver/user/updateUinfo.action";
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("accid", accid));
        params.add(new BasicNameValuePair("name", name));

        // UTF-8编码,解决中文问题
        HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

        String res = NIMPost.postNIMServer(url, entity, APPKEY, SECRET);
        log.info("updateUinfo httpRes: {}", res);
        return res;
    }

    /**
     * @description: 网易云信添加好友聊天
     * @param {type}
     * @return:
     */
    public static String addFriend(String accid, String faccid, String msg) throws IOException {
        String url = "https://api.netease.im/nimserver/friend/add.action";
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("accid", accid));
        params.add(new BasicNameValuePair("faccid", faccid));
        params.add(new BasicNameValuePair("type", "1"));
        params.add(new BasicNameValuePair("msg", msg));

        // UTF-8编码,解决中文问题
        HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

        String res = NIMPost.postNIMServer(url, entity, APPKEY, SECRET);
        log.info("addFriend httpRes: {}", res);
        return res;
    }

    // @Test
    // public void name() throws IOException {
    //     createUser("21dc8f7f9d5c429d90a87016000b89ad", "15947775005", "8c399ba0120656dd09b42e70dc7e70b3");
    //     createUser("61bca21258084a9c8211de3ea427ea7d", "15044940361", "3391df4b14d6378dfa7f16e0ba6e4b5f");
    //     createUser("73adddebc3f0486cbcacddfaf0b26461", "13847412743", "6847126efeef82618c869b191fec7ad6");
    //     createUser("944af0133c264b9990d873b7bb057332", "15044940362", "f621a7073413328e3035c0b211e22a44");
    //     // addFriend("a", "aa", "我对你很感兴趣,我们聊聊？");
    // }
}
