package weblogic.ejb.container.deployer;

import weblogic.j2ee.validation.IDescriptorError;
import weblogic.j2ee.validation.IDescriptorErrorInfo;
import weblogic.utils.NestedException;

public final class DeploymentDescriptorException extends NestedException implements IDescriptorError {
   private static final long serialVersionUID = -2315890241012901760L;
   private IDescriptorErrorInfo errorInfo;

   public DeploymentDescriptorException(String msg, IDescriptorErrorInfo errorInfo) {
      super(msg);
      this.errorInfo = errorInfo;
   }

   public DeploymentDescriptorException(Throwable th, IDescriptorErrorInfo errorInfo) {
      super(th);
      this.errorInfo = errorInfo;
   }

   public DeploymentDescriptorException(String msg, Throwable th, IDescriptorErrorInfo errorInfo) {
      super(msg, th);
      this.errorInfo = errorInfo;
   }

   public boolean hasErrorInfo() {
      return this.errorInfo != null;
   }

   public IDescriptorErrorInfo getErrorInfo() {
      return this.errorInfo;
   }

   public void setDescriptorErrorInfo(IDescriptorErrorInfo errorInfo) {
      this.errorInfo = errorInfo;
   }
}
