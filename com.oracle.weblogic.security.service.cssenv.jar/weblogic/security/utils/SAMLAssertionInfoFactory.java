package weblogic.security.utils;

import com.bea.common.security.ApiLogger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.w3c.dom.Element;

public abstract class SAMLAssertionInfoFactory {
   private static Map classMap = new ConcurrentHashMap(3);
   private static final String wlsFactory = "weblogic.security.utils.SAMLAssertionInfoFactoryImpl";

   public static SAMLAssertionInfoFactory getInstance() throws Exception {
      return getFactory("weblogic.security.utils.SAMLAssertionInfoFactoryImpl");
   }

   public static SAMLAssertionInfoFactory getInstance(String factory) throws Exception {
      return getFactory(factory);
   }

   private static SAMLAssertionInfoFactory getFactory(String factory) throws Exception {
      if (factory != null && factory.length() >= 1) {
         try {
            Class clazz = (Class)classMap.get(factory);
            if (clazz == null) {
               clazz = Class.forName(factory);
               classMap.put(factory, clazz);
            }

            return (SAMLAssertionInfoFactory)clazz.newInstance();
         } catch (Exception var2) {
            throw new Exception(factory + ": " + var2.toString(), var2);
         }
      } else {
         throw new IllegalArgumentException(ApiLogger.getIllegalArgumentSpecified("getInstance", "factory", "null or empty"));
      }
   }

   public abstract SAMLAssertionInfo getSAMLAssertionInfo(String var1) throws Exception;

   public abstract SAMLAssertionInfo getSAMLAssertionInfo(Element var1) throws Exception;
}
