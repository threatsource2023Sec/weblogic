package com.rsa.certj.spi.path;

import com.rsa.certj.DatabaseService;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.extensions.X509V3Extension;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** @deprecated */
public final class CertPathCtx {
   /** @deprecated */
   public static final int PF_NONE = 0;
   /** @deprecated */
   public static final int PF_IGNORE_SIGNATURE = 1;
   /** @deprecated */
   public static final int PF_IGNORE_VALIDATION_TIME = 2;
   /** @deprecated */
   public static final int PF_IGNORE_REVOCATION = 4;
   /** @deprecated */
   public static final int PF_IGNORE_NAME_CHAINING = 8;
   /** @deprecated */
   public static final int PF_IGNORE_NAME_CONSTRAINTS = 16;
   /** @deprecated */
   public static final int PF_IGNORE_BASIC_CONSTRAINTS = 32;
   /** @deprecated */
   public static final int PF_IGNORE_KEY_USAGE = 64;
   /** @deprecated */
   public static final int PF_IGNORE_CRITICALITY = 128;
   /** @deprecated */
   public static final int PF_IGNORE_UID_CHAINING = 256;
   /** @deprecated */
   public static final int PF_IGNORE_KEY_ID_CHAINING = 512;
   /** @deprecated */
   public static final int PF_IGNORE_CRL_DP = 1024;
   /** @deprecated */
   public static final int PF_IGNORE_AIA = 2048;
   /** @deprecated */
   public static final int PF_IGNORE_CRL_NUMBER = 4096;
   /** @deprecated */
   public static final int PF_IGNORE_DELTA_CRL = 8192;
   /** @deprecated */
   public static final int PF_IGNORE_CRL_IDP = 16384;
   /** @deprecated */
   public static final int PF_INITIAL_EXPLICIT_POLICY = 32768;
   /** @deprecated */
   public static final int PF_INITIAL_POLICY_MAPPING_INHIBIT = 65536;
   /** @deprecated */
   public static final int PF_INITIAL_ANY_POLICY_INHIBIT = 131072;
   /** @deprecated */
   public static final int PF_IGNORE_OBSOLETE_CRL = 262144;
   private int pathOptions;
   private Certificate[] trustedCerts;
   private byte[][] policies;
   private Date validationTime;
   private DatabaseService database;
   private Map attributes = new HashMap();

   /** @deprecated */
   public CertPathCtx(int var1, Certificate[] var2, byte[][] var3, Date var4, DatabaseService var5) {
      this.pathOptions = var1;
      this.trustedCerts = var2;
      this.validationTime = var4;
      this.database = var5;
      if (var3 == null) {
         this.policies = new byte[][]{X509V3Extension.ANY_POLICY_OID};
      } else {
         this.policies = var3;
      }

   }

   /** @deprecated */
   public int getPathOptions() {
      return this.pathOptions;
   }

   /** @deprecated */
   public Certificate[] getTrustedCerts() {
      return this.trustedCerts == null ? null : (Certificate[])((Certificate[])this.trustedCerts.clone());
   }

   /** @deprecated */
   public byte[][] getPolicies() {
      return this.policies == null ? (byte[][])null : (byte[][])((byte[][])this.policies.clone());
   }

   /** @deprecated */
   public Date getValidationTime() {
      return this.validationTime;
   }

   /** @deprecated */
   public DatabaseService getDatabase() {
      return this.database;
   }

   /** @deprecated */
   public boolean isFlagRaised(int var1) {
      return (var1 & this.getPathOptions()) != 0;
   }

   /** @deprecated */
   public void setAttribute(Object var1, Object var2) {
      if (var1 != null && var2 != null) {
         this.attributes.put(var1, var2);
      }
   }

   /** @deprecated */
   public Object getAttribute(Object var1) {
      return this.attributes.get(var1);
   }

   /** @deprecated */
   public void removeAttribute(Object var1) {
      this.attributes.remove(var1);
   }
}
