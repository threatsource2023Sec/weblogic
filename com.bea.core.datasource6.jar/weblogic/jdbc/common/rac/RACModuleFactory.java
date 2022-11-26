package weblogic.jdbc.common.rac;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import weblogic.common.ResourceException;

public class RACModuleFactory {
   private static String factoryClassName = "weblogic.jdbc.common.rac.internal.UCPRACModuleImpl";

   public static void setFactoryClass(String classname) {
      factoryClassName = classname;
   }

   public static RACModule createInstance(RACModulePool pool) throws ResourceException {
      try {
         Class clss = Class.forName(factoryClassName);
         Constructor c = clss.getConstructor(RACModulePool.class);
         return (RACModule)c.newInstance(pool);
      } catch (ClassNotFoundException var3) {
         throw new ResourceException(var3);
      } catch (SecurityException var4) {
         throw new ResourceException(var4);
      } catch (NoSuchMethodException var5) {
         throw new ResourceException(var5);
      } catch (IllegalArgumentException var6) {
         throw new ResourceException(var6);
      } catch (InstantiationException var7) {
         throw new ResourceException(var7);
      } catch (IllegalAccessException var8) {
         throw new ResourceException(var8);
      } catch (InvocationTargetException var9) {
         throw new ResourceException(var9);
      }
   }
}
