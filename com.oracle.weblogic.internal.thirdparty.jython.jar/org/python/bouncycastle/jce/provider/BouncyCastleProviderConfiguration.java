package org.python.bouncycastle.jce.provider;

import java.security.Permission;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.python.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission;
import org.python.bouncycastle.jce.spec.ECParameterSpec;

class BouncyCastleProviderConfiguration implements ProviderConfiguration {
   private static Permission BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission("BC", "threadLocalEcImplicitlyCa");
   private static Permission BC_EC_PERMISSION = new ProviderConfigurationPermission("BC", "ecImplicitlyCa");
   private static Permission BC_DH_LOCAL_PERMISSION = new ProviderConfigurationPermission("BC", "threadLocalDhDefaultParams");
   private static Permission BC_DH_PERMISSION = new ProviderConfigurationPermission("BC", "DhDefaultParams");
   private static Permission BC_EC_CURVE_PERMISSION = new ProviderConfigurationPermission("BC", "acceptableEcCurves");
   private static Permission BC_ADDITIONAL_EC_CURVE_PERMISSION = new ProviderConfigurationPermission("BC", "additionalEcParameters");
   private ThreadLocal ecThreadSpec = new ThreadLocal();
   private ThreadLocal dhThreadSpec = new ThreadLocal();
   private volatile ECParameterSpec ecImplicitCaParams;
   private volatile Object dhDefaultParams;
   private volatile Set acceptableNamedCurves = new HashSet();
   private volatile Map additionalECParameters = new HashMap();

   void setParameter(String var1, Object var2) {
      SecurityManager var3 = System.getSecurityManager();
      if (var1.equals("threadLocalEcImplicitlyCa")) {
         if (var3 != null) {
            var3.checkPermission(BC_EC_LOCAL_PERMISSION);
         }

         ECParameterSpec var4;
         if (!(var2 instanceof ECParameterSpec) && var2 != null) {
            var4 = EC5Util.convertSpec((java.security.spec.ECParameterSpec)var2, false);
         } else {
            var4 = (ECParameterSpec)var2;
         }

         if (var4 == null) {
            this.ecThreadSpec.remove();
         } else {
            this.ecThreadSpec.set(var4);
         }
      } else if (var1.equals("ecImplicitlyCa")) {
         if (var3 != null) {
            var3.checkPermission(BC_EC_PERMISSION);
         }

         if (!(var2 instanceof ECParameterSpec) && var2 != null) {
            this.ecImplicitCaParams = EC5Util.convertSpec((java.security.spec.ECParameterSpec)var2, false);
         } else {
            this.ecImplicitCaParams = (ECParameterSpec)var2;
         }
      } else if (var1.equals("threadLocalDhDefaultParams")) {
         if (var3 != null) {
            var3.checkPermission(BC_DH_LOCAL_PERMISSION);
         }

         if (!(var2 instanceof DHParameterSpec) && !(var2 instanceof DHParameterSpec[]) && var2 != null) {
            throw new IllegalArgumentException("not a valid DHParameterSpec");
         }

         if (var2 == null) {
            this.dhThreadSpec.remove();
         } else {
            this.dhThreadSpec.set(var2);
         }
      } else if (var1.equals("DhDefaultParams")) {
         if (var3 != null) {
            var3.checkPermission(BC_DH_PERMISSION);
         }

         if (!(var2 instanceof DHParameterSpec) && !(var2 instanceof DHParameterSpec[]) && var2 != null) {
            throw new IllegalArgumentException("not a valid DHParameterSpec or DHParameterSpec[]");
         }

         this.dhDefaultParams = var2;
      } else if (var1.equals("acceptableEcCurves")) {
         if (var3 != null) {
            var3.checkPermission(BC_EC_CURVE_PERMISSION);
         }

         this.acceptableNamedCurves = (Set)var2;
      } else if (var1.equals("additionalEcParameters")) {
         if (var3 != null) {
            var3.checkPermission(BC_ADDITIONAL_EC_CURVE_PERMISSION);
         }

         this.additionalECParameters = (Map)var2;
      }

   }

   public ECParameterSpec getEcImplicitlyCa() {
      ECParameterSpec var1 = (ECParameterSpec)this.ecThreadSpec.get();
      return var1 != null ? var1 : this.ecImplicitCaParams;
   }

   public DHParameterSpec getDHDefaultParameters(int var1) {
      Object var2 = this.dhThreadSpec.get();
      if (var2 == null) {
         var2 = this.dhDefaultParams;
      }

      if (var2 instanceof DHParameterSpec) {
         DHParameterSpec var3 = (DHParameterSpec)var2;
         if (var3.getP().bitLength() == var1) {
            return var3;
         }
      } else if (var2 instanceof DHParameterSpec[]) {
         DHParameterSpec[] var5 = (DHParameterSpec[])((DHParameterSpec[])var2);

         for(int var4 = 0; var4 != var5.length; ++var4) {
            if (var5[var4].getP().bitLength() == var1) {
               return var5[var4];
            }
         }
      }

      return null;
   }

   public Set getAcceptableNamedCurves() {
      return Collections.unmodifiableSet(this.acceptableNamedCurves);
   }

   public Map getAdditionalECParameters() {
      return Collections.unmodifiableMap(this.additionalECParameters);
   }
}
