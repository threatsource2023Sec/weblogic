package com.bea.xbean.config;

import com.bea.xbean.xb.xmlconfig.Usertypeconfig;
import com.bea.xml.UserType;
import com.bea.xml_.impl.jam.JamClassLoader;
import javax.xml.namespace.QName;

public class UserTypeImpl implements UserType {
   private QName _name;
   private String _javaName;
   private String _staticHandler;

   static UserTypeImpl newInstance(JamClassLoader loader, Usertypeconfig cfgXO) {
      UserTypeImpl result = new UserTypeImpl();
      result._name = cfgXO.getName();
      result._javaName = cfgXO.getJavaname();
      result._staticHandler = cfgXO.getStaticHandler();
      return result;
   }

   public String getJavaName() {
      return this._javaName;
   }

   public QName getName() {
      return this._name;
   }

   public String getStaticHandler() {
      return this._staticHandler;
   }
}
