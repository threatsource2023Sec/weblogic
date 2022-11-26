package weblogic.jdbc.common.rac;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import weblogic.common.ResourceException;
import weblogic.jdbc.common.internal.OracleHelper;
import weblogic.jdbc.common.internal.OraclePool;

public class OracleHelperFactory {
   static OracleHelper helper = null;

   public static OracleHelper createInstance(OraclePool pool) throws ResourceException {
      try {
         Class clss = Class.forName("weblogic.jdbc.common.rac.internal.OracleHelperImpl");
         Constructor c = clss.getConstructor(OraclePool.class);
         return (OracleHelper)c.newInstance(pool);
      } catch (SecurityException | NoSuchMethodException | IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException var3) {
         if (helper != null) {
            return helper;
         } else {
            throw new ResourceException(var3);
         }
      }
   }

   public static void setInstance(OracleHelper h) {
      helper = h;
   }
}
