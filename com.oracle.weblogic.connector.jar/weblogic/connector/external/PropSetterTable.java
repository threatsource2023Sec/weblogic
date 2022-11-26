package weblogic.connector.external;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface PropSetterTable {
   void registerSetterProperty(String var1, String var2, Method var3);

   PropertyInjector getInjector(String var1, String var2);

   PropertyInjector getInjectorByName(String var1);

   boolean haveInjectorWithName(String var1);

   public interface PropertyInjector {
      void inject(Object var1, Object var2) throws IllegalAccessException, InvocationTargetException;

      String getName();

      String getType();
   }
}
