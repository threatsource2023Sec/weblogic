package weblogic.ejb.container.deployer.mbimpl;

import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.MethodDescriptor;
import weblogic.j2ee.descriptor.MethodBean;
import weblogic.j2ee.descriptor.MethodParamsBean;

public final class MethodDescriptorImpl implements MethodDescriptor {
   private final String methodName;
   private final String[] methodParams;
   private final String ejbName;
   private final String methodIntf;

   public MethodDescriptorImpl(MethodBean m) {
      this.methodName = m.getMethodName();
      this.ejbName = m.getEjbName();
      this.methodIntf = m.getMethodIntf();
      MethodParamsBean params = m.getMethodParams();
      if (null != params) {
         this.methodParams = params.getMethodParams();
      } else {
         this.methodParams = null;
      }

   }

   public MethodDescriptorImpl(weblogic.j2ee.descriptor.wl.MethodBean m) {
      this.methodName = m.getMethodName();
      this.ejbName = m.getEjbName();
      this.methodIntf = m.getMethodIntf();
      weblogic.j2ee.descriptor.wl.MethodParamsBean params = m.getMethodParams();
      if (null != params) {
         this.methodParams = params.getMethodParams();
      } else {
         this.methodParams = null;
      }

   }

   public String toString() {
      StringBuilder sb = new StringBuilder("[MethodDescriptorImpl ");
      sb.append(this.methodName).append("(");
      if (this.methodParams != null) {
         String[] var2 = this.methodParams;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            sb.append(s).append(" ");
         }
      }

      sb.append(") ").append(this.ejbName);
      sb.append(", ").append(this.methodIntf);
      sb.append(" type= ").append(this.getMethodType()).append("]");
      return sb.toString();
   }

   public short getMethodType() {
      short result = 3;
      if (this.methodName.equals("*")) {
         result = 1;
      } else if (this.methodParams == null) {
         result = 2;
      }

      return result;
   }

   public String getMethodSignature() {
      return DDUtils.getMethodSignature(this.methodName, this.methodParams);
   }

   public String getEjbName() {
      return this.ejbName;
   }

   public String getMethodIntf() {
      return this.methodIntf;
   }
}
