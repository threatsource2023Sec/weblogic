package weblogic.servlet.internal.dd.compliance;

import weblogic.j2ee.validation.IDescriptorError;
import weblogic.j2ee.validation.IDescriptorErrorInfo;

public class ComplianceException extends Exception implements IDescriptorError {
   private IDescriptorErrorInfo info;

   public ComplianceException(String error) {
      super(error);
      this.info = null;
   }

   public ComplianceException(String error, DescriptorErrorInfo info) {
      super(error);
      this.info = info;
   }

   public boolean hasErrorInfo() {
      return this.info != null;
   }

   public IDescriptorErrorInfo getErrorInfo() {
      return this.info;
   }

   public void setDescriptorErrorInfo(IDescriptorErrorInfo info) {
      this.info = info;
   }
}
