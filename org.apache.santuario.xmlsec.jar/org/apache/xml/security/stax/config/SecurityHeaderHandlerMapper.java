package org.apache.xml.security.stax.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.xml.security.configuration.HandlerType;
import org.apache.xml.security.configuration.SecurityHeaderHandlersType;
import org.apache.xml.security.utils.ClassLoaderUtils;

public class SecurityHeaderHandlerMapper {
   private static Map handlerClassMap;

   private SecurityHeaderHandlerMapper() {
   }

   protected static synchronized void init(SecurityHeaderHandlersType securityHeaderHandlersType, Class callingClass) throws Exception {
      List handlerList = securityHeaderHandlersType.getHandler();
      handlerClassMap = new HashMap(handlerList.size() + 1);

      for(int i = 0; i < handlerList.size(); ++i) {
         HandlerType handlerType = (HandlerType)handlerList.get(i);
         QName qName = new QName(handlerType.getURI(), handlerType.getNAME());
         handlerClassMap.put(qName, ClassLoaderUtils.loadClass(handlerType.getJAVACLASS(), callingClass));
      }

   }

   public static Class getSecurityHeaderHandler(QName name) {
      return (Class)handlerClassMap.get(name);
   }
}
