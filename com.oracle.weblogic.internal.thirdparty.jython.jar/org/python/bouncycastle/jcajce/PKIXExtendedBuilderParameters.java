package org.python.bouncycastle.jcajce;

import java.security.InvalidParameterException;
import java.security.cert.CertPathParameters;
import java.security.cert.PKIXBuilderParameters;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PKIXExtendedBuilderParameters implements CertPathParameters {
   private final PKIXExtendedParameters baseParameters;
   private final Set excludedCerts;
   private final int maxPathLength;

   private PKIXExtendedBuilderParameters(Builder var1) {
      this.baseParameters = var1.baseParameters;
      this.excludedCerts = Collections.unmodifiableSet(var1.excludedCerts);
      this.maxPathLength = var1.maxPathLength;
   }

   public PKIXExtendedParameters getBaseParameters() {
      return this.baseParameters;
   }

   public Set getExcludedCerts() {
      return this.excludedCerts;
   }

   public int getMaxPathLength() {
      return this.maxPathLength;
   }

   public Object clone() {
      return this;
   }

   // $FF: synthetic method
   PKIXExtendedBuilderParameters(Builder var1, Object var2) {
      this(var1);
   }

   public static class Builder {
      private final PKIXExtendedParameters baseParameters;
      private int maxPathLength = 5;
      private Set excludedCerts = new HashSet();

      public Builder(PKIXBuilderParameters var1) {
         this.baseParameters = (new PKIXExtendedParameters.Builder(var1)).build();
         this.maxPathLength = var1.getMaxPathLength();
      }

      public Builder(PKIXExtendedParameters var1) {
         this.baseParameters = var1;
      }

      public Builder addExcludedCerts(Set var1) {
         this.excludedCerts.addAll(var1);
         return this;
      }

      public Builder setMaxPathLength(int var1) {
         if (var1 < -1) {
            throw new InvalidParameterException("The maximum path length parameter can not be less than -1.");
         } else {
            this.maxPathLength = var1;
            return this;
         }
      }

      public PKIXExtendedBuilderParameters build() {
         return new PKIXExtendedBuilderParameters(this);
      }
   }
}
