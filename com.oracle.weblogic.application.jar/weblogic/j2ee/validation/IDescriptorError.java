package weblogic.j2ee.validation;

public interface IDescriptorError {
   boolean hasErrorInfo();

   IDescriptorErrorInfo getErrorInfo();

   void setDescriptorErrorInfo(IDescriptorErrorInfo var1);
}
