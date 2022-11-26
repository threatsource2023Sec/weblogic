package org.glassfish.soteria.authorization;

import javax.ejb.EJBContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.soteria.Utils;

public final class EJB {
   private EJB() {
   }

   public static EJBContext getEJBContext() {
      try {
         return (EJBContext)(new InitialContext()).lookup("java:comp/EJBContext");
      } catch (NamingException var1) {
         return null;
      }
   }

   public static String getCurrentEJBName(EJBContext ejbContext) {
      try {
         switch (ejbContext.getClass().getName()) {
            case "com.sun.ejb.containers.SessionContextImpl":
            case "com.sun.ejb.containers.SingletonContextImpl":
               String toString = ejbContext.toString();
               int firstIndex = toString.indexOf(";");
               if (firstIndex != -1) {
                  return toString.substring(0, firstIndex);
               }
               break;
            case "org.jboss.as.ejb3.context.SessionContextImpl":
               return Utils.getELProcessor("ejbContext", ejbContext).eval("ejbContext.component.componentName").toString();
         }
      } catch (Exception var5) {
      }

      return null;
   }
}
