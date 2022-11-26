package com.bea.xbean.common;

import com.bea.xml.SystemProperties;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xml.sax.EntityResolver;

public class ResolverUtil {
   private static EntityResolver _entityResolver = null;

   public static EntityResolver getGlobalEntityResolver() {
      return _entityResolver;
   }

   public static EntityResolver resolverForCatalog(String catalogFile) {
      if (catalogFile == null) {
         return null;
      } else {
         try {
            Class cmClass = Class.forName("org.apache.xml.resolver.CatalogManager");
            Constructor cstrCm = cmClass.getConstructor();
            Object cmObj = cstrCm.newInstance();
            Method cmMethod = cmClass.getMethod("setCatalogFiles", String.class);
            cmMethod.invoke(cmObj, catalogFile);
            Class crClass = Class.forName("org.apache.xml.resolver.tools.CatalogResolver");
            Constructor cstrCr = crClass.getConstructor(cmClass);
            Object crObj = cstrCr.newInstance(cmObj);
            return (EntityResolver)crObj;
         } catch (Exception var8) {
            return null;
         }
      }
   }

   static {
      try {
         String erClassName = SystemProperties.getProperty("xmlbean.entityResolver");
         if (erClassName != null) {
            Object o = Class.forName(erClassName).newInstance();
            _entityResolver = (EntityResolver)o;
         }
      } catch (Exception var2) {
         _entityResolver = null;
      }

   }
}
