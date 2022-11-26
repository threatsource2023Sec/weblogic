package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;

public abstract class BaseComplianceChecker {
   private EJBComplianceTextFormatter fmt;

   protected String methodSig(String methodName) {
      return this.methodSig(methodName, (Class[])null);
   }

   protected String methodSig(Method method) {
      String methodName = null;
      Class[] methodArgs = null;
      if (null != method) {
         methodName = method.getName();
         methodArgs = method.getParameterTypes();
      }

      return this.methodSig(methodName, methodArgs);
   }

   protected String methodSig(String methodName, Class[] methodArgs) {
      StringBuilder sb = new StringBuilder(methodName + "(");
      if (methodArgs != null && methodArgs.length > 0) {
         for(int i = 0; i < methodArgs.length; ++i) {
            if (i != 0) {
               sb.append(",");
            }

            sb.append(methodArgs[i].getName());
         }
      }

      sb.append(")");
      return sb.toString();
   }

   protected EJBComplianceTextFormatter getFmt() {
      if (this.fmt == null) {
         this.fmt = EJBComplianceTextFormatter.getInstance();
      }

      return this.fmt;
   }
}
