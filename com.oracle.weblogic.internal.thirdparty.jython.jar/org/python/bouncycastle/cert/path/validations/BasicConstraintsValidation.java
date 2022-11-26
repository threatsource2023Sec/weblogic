package org.python.bouncycastle.cert.path.validations;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.x509.BasicConstraints;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cert.path.CertPathValidation;
import org.python.bouncycastle.cert.path.CertPathValidationContext;
import org.python.bouncycastle.cert.path.CertPathValidationException;
import org.python.bouncycastle.util.Memoable;

public class BasicConstraintsValidation implements CertPathValidation {
   private boolean isMandatory;
   private BasicConstraints bc;
   private int pathLengthRemaining;
   private BigInteger maxPathLength;

   public BasicConstraintsValidation() {
      this(true);
   }

   public BasicConstraintsValidation(boolean var1) {
      this.isMandatory = var1;
   }

   public void validate(CertPathValidationContext var1, X509CertificateHolder var2) throws CertPathValidationException {
      if (this.maxPathLength != null && this.pathLengthRemaining < 0) {
         throw new CertPathValidationException("BasicConstraints path length exceeded");
      } else {
         var1.addHandledExtension(Extension.basicConstraints);
         BasicConstraints var3 = BasicConstraints.fromExtensions(var2.getExtensions());
         if (var3 != null) {
            if (this.bc != null) {
               if (var3.isCA()) {
                  BigInteger var4 = var3.getPathLenConstraint();
                  if (var4 != null) {
                     int var5 = var4.intValue();
                     if (var5 < this.pathLengthRemaining) {
                        this.pathLengthRemaining = var5;
                        this.bc = var3;
                     }
                  }
               }
            } else {
               this.bc = var3;
               if (var3.isCA()) {
                  this.maxPathLength = var3.getPathLenConstraint();
                  if (this.maxPathLength != null) {
                     this.pathLengthRemaining = this.maxPathLength.intValue();
                  }
               }
            }
         } else if (this.bc != null) {
            --this.pathLengthRemaining;
         }

         if (this.isMandatory && this.bc == null) {
            throw new CertPathValidationException("BasicConstraints not present in path");
         }
      }
   }

   public Memoable copy() {
      BasicConstraintsValidation var1 = new BasicConstraintsValidation(this.isMandatory);
      var1.bc = this.bc;
      var1.pathLengthRemaining = this.pathLengthRemaining;
      return var1;
   }

   public void reset(Memoable var1) {
      BasicConstraintsValidation var2 = (BasicConstraintsValidation)var1;
      this.isMandatory = var2.isMandatory;
      this.bc = var2.bc;
      this.pathLengthRemaining = var2.pathLengthRemaining;
   }
}
