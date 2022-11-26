package weblogic.connector.common;

import java.lang.reflect.Method;

public class PropertyItem {
   private final Method method;
   private final String property;
   private final Class cls;

   public PropertyItem(Method method, String property, Class cls) {
      this.method = method;
      this.property = property;
      this.cls = cls;
   }

   public Object invoke(Object administeredObjectBean) {
      try {
         return this.method.invoke(administeredObjectBean);
      } catch (Exception var3) {
         throw new IllegalStateException(String.format("Failed to call %s method of %s", this.method.getName(), this.cls.getName()), var3);
      }
   }

   public String getName() {
      return this.property;
   }
}
