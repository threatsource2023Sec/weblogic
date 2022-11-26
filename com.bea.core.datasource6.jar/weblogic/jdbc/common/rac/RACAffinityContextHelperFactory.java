package weblogic.jdbc.common.rac;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RACAffinityContextHelperFactory {
   static RACAffinityContextHelper instance;

   public static void setInstance(RACAffinityContextHelper helper) {
      instance = helper;
   }

   public static RACAffinityContextHelper getInstance() throws RACAffinityContextException {
      if (instance != null) {
         return instance;
      } else {
         try {
            Class clss = Class.forName("weblogic.jdbc.common.rac.internal.RACAffinityContextHelperImpl");
            Method m = clss.getMethod("getInstance");
            instance = (RACAffinityContextHelper)m.invoke((Object)null);
            return instance;
         } catch (ClassNotFoundException var2) {
            throw new RACAffinityContextException(var2);
         } catch (SecurityException var3) {
            throw new RACAffinityContextException(var3);
         } catch (NoSuchMethodException var4) {
            throw new RACAffinityContextException(var4);
         } catch (IllegalArgumentException var5) {
            throw new RACAffinityContextException(var5);
         } catch (IllegalAccessException var6) {
            throw new RACAffinityContextException(var6);
         } catch (InvocationTargetException var7) {
            throw new RACAffinityContextException(var7);
         }
      }
   }
}
