package weblogic.j2ee.validation;

public final class ComplianceException extends Exception implements IDescriptorError {
   private static final long serialVersionUID = 1874199488237951559L;
   private IDescriptorErrorInfo errorInfo;

   public ComplianceException(String s) {
      super(s);
   }

   public ComplianceException(String s, IDescriptorErrorInfo errorInfo) {
      super(s);
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
