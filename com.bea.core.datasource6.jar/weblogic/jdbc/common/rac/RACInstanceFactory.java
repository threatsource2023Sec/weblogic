package weblogic.jdbc.common.rac;

import java.util.Properties;

public abstract class RACInstanceFactory {
   private static final String FACTORYIMPL = "weblogic.jdbc.common.rac.internal.RACInstanceFactoryImpl";
   private static RACInstanceFactory instance;
   private static Exception error;

   public static RACInstanceFactory getInstance() {
      if (instance == null) {
         throw new TypeNotPresentException("weblogic.jdbc.common.rac.internal.RACInstanceFactoryImpl", error);
      } else {
         return instance;
      }
   }

   public abstract RACInstance create(Properties var1);

   public abstract RACInstance create(String var1, String var2, String var3);

   static {
      try {
         Class clss = Class.forName("weblogic.jdbc.common.rac.internal.RACInstanceFactoryImpl");
         instance = (RACInstanceFactory)clss.newInstance();
      } catch (Exception var1) {
         error = var1;
      }

   }
}
