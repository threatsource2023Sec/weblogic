package weblogic.servlet.ejb2jsp.dd;

class BaseEJB {
   String retType;
   String methodName;
   String argType;

   BaseEJB(String retType, String methodName, String argType) {
      this.retType = retType;
      this.methodName = methodName;
      this.argType = argType;
   }

   boolean matchesDescriptor(EJBMethodDescriptor m) {
      if (!this.retType.equals(m.getReturnType())) {
         return false;
      } else if (!this.methodName.equals(m.getName())) {
         return false;
      } else {
         MethodParamDescriptor[] p = m.getParams();
         if (this.argType != null) {
            return p != null && p.length == 1 ? this.argType.equals(p[0].getType()) : false;
         } else {
            return p == null || p.length == 0;
         }
      }
   }
}
