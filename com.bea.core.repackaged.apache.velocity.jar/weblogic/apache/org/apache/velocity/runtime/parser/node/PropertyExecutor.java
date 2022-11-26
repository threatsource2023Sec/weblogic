package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.lang.reflect.InvocationTargetException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeLogger;
import weblogic.apache.org.apache.velocity.util.introspection.Introspector;

public class PropertyExecutor extends AbstractExecutor {
   protected Introspector introspector = null;
   protected String methodUsed = null;

   public PropertyExecutor(RuntimeLogger r, Introspector ispctr, Class clazz, String property) {
      super.rlog = r;
      this.introspector = ispctr;
      this.discover(clazz, property);
   }

   protected void discover(Class clazz, String property) {
      try {
         Object[] params = new Object[0];
         StringBuffer sb = new StringBuffer("get");
         sb.append(property);
         this.methodUsed = sb.toString();
         super.method = this.introspector.getMethod(clazz, this.methodUsed, params);
         if (super.method != null) {
            return;
         }

         sb = new StringBuffer("get");
         sb.append(property);
         char c = sb.charAt(3);
         if (Character.isLowerCase(c)) {
            sb.setCharAt(3, Character.toUpperCase(c));
         } else {
            sb.setCharAt(3, Character.toLowerCase(c));
         }

         this.methodUsed = sb.toString();
         super.method = this.introspector.getMethod(clazz, this.methodUsed, params);
         if (super.method != null) {
            return;
         }
      } catch (Exception var6) {
         super.rlog.error("PROGRAMMER ERROR : PropertyExector() : " + var6);
      }

   }

   public Object execute(Object o) throws IllegalAccessException, InvocationTargetException {
      return super.method == null ? null : super.method.invoke(o, (Object[])null);
   }
}
