package org.python.bouncycastle.cert.path;

import java.util.Collections;
import java.util.Set;

public class CertPathValidationResult {
   private final boolean isValid;
   private final CertPathValidationException cause;
   private final Set unhandledCriticalExtensionOIDs;
   private int[] certIndexes;

   public CertPathValidationResult(CertPathValidationContext var1) {
      this.unhandledCriticalExtensionOIDs = Collections.unmodifiableSet(var1.getUnhandledCriticalExtensionOIDs());
      this.isValid = this.unhandledCriticalExtensionOIDs.isEmpty();
      this.cause = null;
   }

   public CertPathValidationResult(CertPathValidationContext var1, int var2, int var3, CertPathValidationException var4) {
      this.unhandledCriticalExtensionOIDs = Collections.unmodifiableSet(var1.getUnhandledCriticalExtensionOIDs());
      this.isValid = false;
      this.cause = var4;
   }

   public CertPathValidationResult(CertPathValidationContext var1, int[] var2, int[] var3, CertPathValidationException[] var4) {
      this.unhandledCriticalExtensionOIDs = Collections.unmodifiableSet(var1.getUnhandledCriticalExtensionOIDs());
      this.isValid = false;
      this.cause = var4[0];
      this.certIndexes = var2;
   }

   public boolean isValid() {
      return this.isValid;
   }

   public Exception getCause() {
      if (this.cause != null) {
         return this.cause;
      } else {
         return !this.unhandledCriticalExtensionOIDs.isEmpty() ? new CertPathValidationException("Unhandled Critical Extensions") : null;
      }
   }

   public Set getUnhandledCriticalExtensionOIDs() {
      return this.unhandledCriticalExtensionOIDs;
   }

   public boolean isDetailed() {
      return this.certIndexes != null;
   }
}
