package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class PKIXValidationOptions {
   private boolean processEmptyCRLs = true;
   private boolean processExpiredCRLs = true;
   private boolean processCredentialCRLs = true;
   private Integer defaultVerificationDepth = new Integer(1);

   public boolean isProcessEmptyCRLs() {
      return this.processEmptyCRLs;
   }

   public void setProcessEmptyCRLs(boolean flag) {
      this.processEmptyCRLs = flag;
   }

   public boolean isProcessExpiredCRLs() {
      return this.processExpiredCRLs;
   }

   public void setProcessExpiredCRLs(boolean flag) {
      this.processExpiredCRLs = flag;
   }

   public boolean isProcessCredentialCRLs() {
      return this.processCredentialCRLs;
   }

   public void setProcessCredentialCRLs(boolean flag) {
      this.processCredentialCRLs = flag;
   }

   public Integer getDefaultVerificationDepth() {
      return this.defaultVerificationDepth;
   }

   public void setDefaultVerificationDepth(@Nonnull Integer depth) {
      Constraint.isNotNull(depth, "Default verification depth cannot be null");
      this.defaultVerificationDepth = depth;
   }
}
