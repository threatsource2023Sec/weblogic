package weblogic.apache.org.apache.velocity.runtime.parser.node;

import weblogic.apache.org.apache.velocity.runtime.RuntimeLogger;
import weblogic.apache.org.apache.velocity.util.introspection.Introspector;

public class BooleanPropertyExecutor extends PropertyExecutor {
   public BooleanPropertyExecutor(RuntimeLogger rlog, Introspector is, Class clazz, String property) {
      super(rlog, is, clazz, property);
   }

   protected void discover(Class clazz, String property) {
      try {
         Object[] params = new Object[0];
         StringBuffer sb = new StringBuffer("is");
         sb.append(property);
         char c = sb.charAt(2);
         if (Character.isLowerCase(c)) {
            sb.setCharAt(2, Character.toUpperCase(c));
         }

         super.methodUsed = sb.toString();
         super.method = super.introspector.getMethod(clazz, super.methodUsed, params);
         if (super.method != null) {
            if (super.method.getReturnType() == Boolean.TYPE) {
               return;
            }

            super.method = null;
         }
      } catch (Exception var6) {
         super.rlog.error("PROGRAMMER ERROR : BooleanPropertyExector() : " + var6);
      }

   }
}
