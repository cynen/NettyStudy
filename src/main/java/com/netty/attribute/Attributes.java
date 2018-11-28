package com.netty.attribute;

import com.netty.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {

    // 是否登录
    AttributeKey<Boolean> LOGINON = AttributeKey.newInstance("loginon");
    // 登录后的session
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");


}
