package weblogic.jaxrs.monitoring.resource.data;

public class JaxRsResourceMethodBase extends JaxRsMonitoringInfo {
   private String path;
   private String methodName;
   private String[] parameterTypes;
   private String returnType;

   public void setPath(String path) {
      this.path = path;
   }

   public String getPath() {
      return this.path;
   }

   public void setMethodName(String methodName) {
      this.methodName = methodName;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public void setParameterTypes(String[] parameterTypes) {
      this.parameterTypes = parameterTypes;
   }

   public String[] getParameterTypes() {
      return this.parameterTypes;
   }

   public void setReturnType(String returnType) {
      this.returnType = returnType;
   }

   public String getReturnType() {
      return this.returnType;
   }
}
