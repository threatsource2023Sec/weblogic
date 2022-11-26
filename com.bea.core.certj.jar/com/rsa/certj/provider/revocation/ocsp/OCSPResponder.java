package com.rsa.certj.provider.revocation.ocsp;

import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.X509Certificate;

/** @deprecated */
public class OCSPResponder implements Cloneable {
   /** @deprecated */
   public static final int PROFILE_GENERIC = 0;
   /** @deprecated */
   public static final int PROFILE_VALICERT = 1;
   /** @deprecated */
   public static final int PROFILE_RSAKCA = 2;
   /** @deprecated */
   public static final int PROFILE_VERISIGN = 3;
   private static final int PROFILE_MAX = 3;
   /** @deprecated */
   public static final int FLAG_DISABLE_NONCES = 1;
   /** @deprecated */
   public static final int FLAG_DISABLE_CERT_SEND = 2;
   /** @deprecated */
   public static final int FLAG_ENABLE_CHAIN_SEND = 4;
   /** @deprecated */
   public static final int FLAG_RESPONDER_NOCHECK = 8;
   private int profile;
   private int flags;
   private String[] destList;
   private String[] proxyList;
   private OCSPRequestControl requestControl;
   private X509Certificate responderCert;
   private X509Certificate[] responderCACerts;
   private DatabaseService database;
   private int timeTolerance;

   /** @deprecated */
   public OCSPResponder(int var1, int var2, String[] var3, String[] var4, OCSPRequestControl var5, X509Certificate var6, X509Certificate[] var7, DatabaseService var8, int var9) throws InvalidParameterException {
      if (var1 >= 0 && var1 <= 3) {
         this.profile = var1;
         if ((var2 & 4) != 0 && (var2 & 2) != 0) {
            throw new InvalidParameterException("FLAG_ENABLE_CHAIN_SEND && FLAG_DISABLE_CERT_SEND incompatible");
         } else {
            this.flags = var2;
            int var10;
            if (var3 == null) {
               this.destList = null;
            } else {
               this.destList = new String[var3.length];

               for(var10 = 0; var10 < var3.length; ++var10) {
                  if (var3[var10] != null) {
                     this.destList[var10] = var3[var10];
                  }
               }
            }

            if (var4 == null) {
               this.proxyList = null;
            } else {
               this.proxyList = new String[var4.length];

               for(var10 = 0; var10 < var4.length; ++var10) {
                  if (var4[var10] != null) {
                     this.proxyList[var10] = var4[var10];
                  }
               }
            }

            try {
               this.requestControl = var5 == null ? null : (OCSPRequestControl)var5.clone();
               this.responderCert = var6 == null ? null : (X509Certificate)var6.clone();
               if (var7 == null) {
                  throw new InvalidParameterException("responderCACerts == null");
               }

               this.responderCACerts = new X509Certificate[var7.length];

               for(var10 = 0; var10 < var7.length; ++var10) {
                  if (var7[var10] == null) {
                     throw new InvalidParameterException("responderCACerts[" + var10 + "] == null");
                  }

                  this.responderCACerts[var10] = (X509Certificate)var7[var10].clone();
               }
            } catch (CloneNotSupportedException var11) {
               throw new InvalidParameterException(var11);
            }

            this.database = var8;
            this.timeTolerance = var9;
         }
      } else {
         throw new InvalidParameterException("OCSPResponder.profile");
      }
   }

   /** @deprecated */
   public OCSPResponder(OCSPRequestControl var1, X509Certificate[] var2) throws InvalidParameterException {
      try {
         this.profile = 0;
         this.flags = 0;
         this.requestControl = var1 == null ? null : (OCSPRequestControl)var1.clone();
         if (var2 == null) {
            throw new InvalidParameterException("responderCACerts == null");
         }

         this.responderCACerts = new X509Certificate[var2.length];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if (var2[var3] == null) {
               throw new InvalidParameterException("responderCACerts[" + var3 + "] == null");
            }

            this.responderCACerts[var3] = (X509Certificate)var2[var3].clone();
         }
      } catch (CloneNotSupportedException var4) {
         throw new InvalidParameterException(var4);
      }

      this.database = null;
      this.timeTolerance = 0;
   }

   /** @deprecated */
   public OCSPResponder(OCSPResponder var1) throws InvalidParameterException {
      if (var1 == null) {
         throw new InvalidParameterException("responder == null");
      } else {
         this.profile = var1.getProfile();
         this.flags = var1.getFlags();
         String[] var2 = var1.getDestList();
         int var4;
         if (var2 == null) {
            this.destList = null;
         } else {
            int var3 = var2.length;
            this.destList = new String[var3];

            for(var4 = 0; var4 < var3; ++var4) {
               if (var2[var4] != null) {
                  this.destList[var4] = var2[var4];
               }
            }
         }

         String[] var7 = var1.getProxyList();
         int var5;
         if (var7 == null) {
            this.proxyList = null;
         } else {
            var4 = var7.length;
            this.proxyList = new String[var4];

            for(var5 = 0; var5 < var4; ++var5) {
               if (var7[var5] != null) {
                  this.proxyList[var5] = var7[var5];
               }
            }
         }

         try {
            this.requestControl = var1.getRequestControl() == null ? null : (OCSPRequestControl)var1.getRequestControl().clone();
            this.responderCert = var1.getResponderCert() == null ? null : (X509Certificate)var1.getResponderCert().clone();
            X509Certificate[] var8 = var1.getResponderCACerts();
            if (var8 == null) {
               throw new InvalidParameterException("OCSPResponder.responderCACerts");
            }

            this.responderCACerts = new X509Certificate[var8.length];

            for(var5 = 0; var5 < var8.length; ++var5) {
               if (var8[var5] == null) {
                  throw new InvalidParameterException("responderCACerts[" + var5 + "] == null");
               }

               this.responderCACerts[var5] = (X509Certificate)var8[var5].clone();
            }
         } catch (CloneNotSupportedException var6) {
            throw new InvalidParameterException(var6);
         }

         this.database = var1.getDatabase();
         this.timeTolerance = var1.getTimeTolerance();
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      try {
         return new OCSPResponder(this);
      } catch (InvalidParameterException var2) {
         throw new CloneNotSupportedException(var2.getMessage());
      }
   }

   /** @deprecated */
   public int getProfile() {
      return this.profile;
   }

   /** @deprecated */
   public int getFlags() {
      return this.flags;
   }

   /** @deprecated */
   public String[] getDestList() {
      return this.destList;
   }

   /** @deprecated */
   public String[] getProxyList() {
      return this.proxyList;
   }

   /** @deprecated */
   public OCSPRequestControl getRequestControl() {
      return this.requestControl;
   }

   /** @deprecated */
   public X509Certificate getResponderCert() {
      return this.responderCert;
   }

   /** @deprecated */
   public X509Certificate[] getResponderCACerts() {
      return this.responderCACerts;
   }

   /** @deprecated */
   protected X509Certificate getResponderCACert(X509Certificate var1) {
      if (var1 == null) {
         return null;
      } else {
         for(int var2 = 0; var2 < this.responderCACerts.length; ++var2) {
            if (var1.getIssuerName().equals(this.responderCACerts[var2].getSubjectName())) {
               return this.responderCACerts[var2];
            }
         }

         return null;
      }
   }

   /** @deprecated */
   protected X509Certificate getResponderCACert(X509Certificate var1, String var2) {
      if (var1 != null && var2 != null && this.destList != null) {
         for(int var3 = 0; var3 < this.destList.length; ++var3) {
            if (var2.equals(this.destList[var3])) {
               X509Certificate var4 = this.getResponderCACert(var1);
               if (var4 != null) {
                  return var4;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   /** @deprecated */
   public void setProfile(int var1) throws InvalidParameterException {
      if (var1 >= 0 && var1 <= 3) {
         this.profile = var1;
      } else {
         throw new InvalidParameterException("OCSPResponder.profile");
      }
   }

   /** @deprecated */
   public void setFlags(int var1) throws InvalidParameterException {
      this.flags = var1;
   }

   /** @deprecated */
   public void setProxyList(String[] var1) throws InvalidParameterException {
      if (var1 == null) {
         this.proxyList = null;
      } else {
         this.proxyList = new String[var1.length];

         for(int var2 = 0; var2 < var1.length; ++var2) {
            if (var1[var2] == null) {
               throw new InvalidParameterException("proxyList[" + var2 + "] == null");
            }

            this.proxyList[var2] = var1[var2];
         }
      }

   }

   /** @deprecated */
   public void setRequestControl(OCSPRequestControl var1) throws InvalidParameterException {
      try {
         this.requestControl = var1 == null ? null : (OCSPRequestControl)var1.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InvalidParameterException(var3);
      }
   }

   /** @deprecated */
   public void setResponderCACerts(X509Certificate[] var1) throws InvalidParameterException {
      if (var1 == null) {
         throw new InvalidParameterException("responderCACerts");
      } else {
         try {
            for(int var2 = 0; var2 < var1.length; ++var2) {
               if (var1[var2] == null) {
                  throw new InvalidParameterException("responderCACerts[" + var2 + "] == null");
               }

               this.responderCACerts[var2] = (X509Certificate)var1[var2].clone();
            }

         } catch (CloneNotSupportedException var3) {
            throw new InvalidParameterException(var3);
         }
      }
   }

   /** @deprecated */
   public void setDatabase(DatabaseService var1) throws InvalidParameterException {
      this.database = var1;
   }

   /** @deprecated */
   public DatabaseService getDatabase() {
      return this.database;
   }

   /** @deprecated */
   public void setTimeTolerance(int var1) {
      this.timeTolerance = var1;
   }

   /** @deprecated */
   public int getTimeTolerance() {
      return this.timeTolerance;
   }
}
