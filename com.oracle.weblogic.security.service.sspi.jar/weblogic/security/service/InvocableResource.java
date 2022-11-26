package weblogic.security.service;

import com.bea.common.security.SecurityLogger;
import weblogic.security.spi.Resource;

/** @deprecated */
@Deprecated
public abstract class InvocableResource implements Resource {
   private String methodName;
   private String[] methodParams;
   private String[] paramNames;

   protected InvocableResource() {
      this.reset();
   }

   public InvocableResource(String methodName, String[] methodParams, String[] paramNames) {
      this.initialize(methodName, methodParams, paramNames);
   }

   public void initialize(String methodName, String[] methodParams, String[] paramNames) {
      this.reset();
      this.methodName = methodName;
      this.methodParams = methodParams;
      this.paramNames = paramNames;
   }

   public void initialize(String definitionString) throws Exception {
      this.reset();
      throw new Exception(SecurityLogger.getInvResourceInitNYI());
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String[] getMethodParams() {
      return this.methodParams;
   }

   public String[] getParamNames() {
      return this.paramNames;
   }

   public boolean equals(Object another) {
      if (another != null && another instanceof InvocableResource) {
         InvocableResource that = (InvocableResource)another;
         if (that.hashCode() == this.hashCode()) {
            return true;
         }
      }

      return false;
   }

   protected void writeResourceString(StringBuffer buf) {
      if (this.methodName != null) {
         buf.append('/');
         buf.append(this.methodName);
         if (this.methodParams != null && this.methodParams.length > 0) {
            buf.append('?');

            for(int i = 0; i < this.methodParams.length; ++i) {
               buf.append(this.methodParams[i]);
               if (this.paramNames != null && i < this.paramNames.length) {
                  buf.append('=');
                  buf.append(this.paramNames[i]);
               }

               if (i != this.methodParams.length) {
                  buf.append('&');
               }
            }
         }

      }
   }

   public void reset() {
      this.methodName = null;
      this.methodParams = null;
      this.paramNames = null;
   }
}
