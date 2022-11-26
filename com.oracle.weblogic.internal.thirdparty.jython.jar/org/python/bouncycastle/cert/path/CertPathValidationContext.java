package org.python.bouncycastle.cert.path;

import java.util.HashSet;
import java.util.Set;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.util.Memoable;

public class CertPathValidationContext implements Memoable {
   private Set criticalExtensions;
   private Set handledExtensions = new HashSet();
   private boolean endEntity;
   private int index;

   public CertPathValidationContext(Set var1) {
      this.criticalExtensions = var1;
   }

   public void addHandledExtension(ASN1ObjectIdentifier var1) {
      this.handledExtensions.add(var1);
   }

   public void setIsEndEntity(boolean var1) {
      this.endEntity = var1;
   }

   public Set getUnhandledCriticalExtensionOIDs() {
      HashSet var1 = new HashSet(this.criticalExtensions);
      var1.removeAll(this.handledExtensions);
      return var1;
   }

   public boolean isEndEntity() {
      return this.endEntity;
   }

   public Memoable copy() {
      return null;
   }

   public void reset(Memoable var1) {
   }
}
