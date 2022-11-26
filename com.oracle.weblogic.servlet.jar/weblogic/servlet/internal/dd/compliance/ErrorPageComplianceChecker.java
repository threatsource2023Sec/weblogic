package weblogic.servlet.internal.dd.compliance;

import java.util.HashSet;
import java.util.Set;
import weblogic.j2ee.descriptor.ErrorPageBean;
import weblogic.utils.ErrorCollectionException;

public class ErrorPageComplianceChecker extends BaseComplianceChecker {
   private Set errorCodeSet;
   private Set exceptionTypeSet;

   public void check(DeploymentInfo info) throws ErrorCollectionException {
      ErrorPageBean[] errorPages = info.getWebAppBean().getErrorPages();
      if (errorPages != null) {
         for(int i = 0; i < errorPages.length; ++i) {
            this.checkErrorPage(errorPages[i], info);
         }

         this.checkForExceptions();
      }
   }

   private void checkErrorPage(ErrorPageBean error, DeploymentInfo info) throws ErrorCollectionException {
      int code = error.getErrorCode();
      String type = error.getExceptionType();
      String eCode = null;
      boolean hasErrorType = false;
      if (code < 1 && type != null && type.length() > 0) {
         this.addDescriptorError(this.fmt.MULTIPLE_DEFINES_ERROR_PAGE("" + code, type));
         this.checkForExceptions();
      }

      if (!this.addErrorCode("" + code)) {
         this.addDescriptorError(this.fmt.DUPLICATE_ERROR_DEF("<error-code>", (String)eCode));
      }

      if (type != null && code < 1) {
         hasErrorType = true;
         ClassLoader cl = info.getClassLoader();
         if (cl != null) {
            this.isClassAssignable(cl, "<exception-type>", type, "java.lang.Throwable");
         }

         if (!this.addExceptionType(type)) {
            this.addDescriptorError(this.fmt.DUPLICATE_ERROR_DEF("<exception-type>", type));
         }
      }

      String loc = error.getLocation();
      if (loc == null || loc.length() == 0) {
         if (hasErrorType) {
            this.addDescriptorError(this.fmt.NO_ERROR_PAGE_LOCATION_TYPE(type));
         } else {
            this.addDescriptorError(this.fmt.NO_ERROR_PAGE_LOCATION_CODE("" + code));
         }
      }

      this.checkForExceptions();
   }

   private boolean addErrorCode(String code) {
      if (this.errorCodeSet == null) {
         this.errorCodeSet = new HashSet();
      }

      return this.errorCodeSet.add(code);
   }

   private boolean addExceptionType(String type) {
      if (this.exceptionTypeSet == null) {
         this.exceptionTypeSet = new HashSet();
      }

      return this.exceptionTypeSet.add(type);
   }
}
