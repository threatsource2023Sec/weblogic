package com.rsa.certj.provider.path;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.provider.revocation.CRLEvidence;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.certj.spi.path.CertPathInterface;
import com.rsa.certj.spi.revocation.CertRevocationInfo;
import com.rsa.certj.spi.revocation.CertStatusException;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/** @deprecated */
public abstract class CertPathCommonImplementation extends ProviderImplementation implements CertPathInterface {
   private CertPathCtx pathCtx;
   private int pathOptions;
   /** @deprecated */
   protected Vector trustedCerts;
   private Vector policies;
   private Date validationTime;
   private DatabaseService database;

   /** @deprecated */
   public CertPathCommonImplementation(CertJ var1, String var2) throws InvalidParameterException {
      super(var1, var2);
   }

   /** @deprecated */
   public boolean buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5, Vector var6) throws NotSupportedException, CertPathException {
      this.savePathCtx(var1);
      if (var4 == null && var5 != null) {
         var5 = null;
      }

      Vector var7 = new Vector();
      if (var2 instanceof X509Certificate) {
         X509Certificate var8 = (X509Certificate)var2;
         if (!this.verifyCertValidityPeriod(var8)) {
            if (!this.trustedCerts.contains(var8)) {
               return false;
            }

            if (var3 == null) {
               return true;
            }

            if (!var3.contains(var8)) {
               var3.addElement(var8);
               return true;
            }
         }

         if (!this.certBuildPath(var8, var7, var4, var5, var6)) {
            return false;
         }
      } else {
         if (!(var2 instanceof X509CRL)) {
            throw new NotSupportedException("CertPathCommonImplementation.buildCertPath: does not support startObjects other than X509Certificate or X509CRL.");
         }

         if (!this.crlBuildPath((X509CRL)var2, var7, var4, var5, var6)) {
            return false;
         }
      }

      CertJUtils.mergeLists(var3, var7);
      CertJUtils.subtractLists(var5, var3);
      return true;
   }

   /** @deprecated */
   public void getNextCertInPath(CertPathCtx var1, Object var2, Vector var3) throws NotSupportedException, CertPathException {
      if (!(var2 instanceof X509Certificate) && !(var2 instanceof X509CRL)) {
         throw new NotSupportedException("CertPathCommonImplementation.getNextCertInPath: does not support startObjects other than X509Certificate or X509CRL.");
      } else {
         this.savePathCtx(var1);
         this.getNextCertInPathInternal(var2, var3);
      }
   }

   private void getNextCertInPathInternal(Object var1, Vector var2) throws CertPathException {
      Vector var3 = new Vector();
      this.getNextCertCandidates(var1, var3);
      this.removeInvalidNextCerts(var1, var3);
      CertJUtils.mergeLists(var2, var3);
   }

   /** @deprecated */
   public boolean validateCertificate(CertPathCtx var1, Certificate var2, JSAFE_PublicKey var3) throws NotSupportedException, CertPathException {
      this.savePathCtx(var1);
      if (!(var2 instanceof X509Certificate)) {
         throw new NotSupportedException("CertPathCommonImplementation.validateCertificate: does not support certificate types other than X509Certificate.");
      } else if (!this.verifyCertValidityPeriod((X509Certificate)var2)) {
         return false;
      } else if ((this.pathOptions & 1) != 0) {
         return true;
      } else {
         try {
            return var2.verifyCertificateSignature(this.certJ.getDevice(), (JSAFE_PublicKey)var3, this.certJ.getRandomObject());
         } catch (NoServiceException var5) {
            throw new CertPathException("CertPathCommonImplementation.validateCertificate: (no random service is registered)", var5);
         } catch (Exception var6) {
            return false;
         }
      }
   }

   /** @deprecated */
   public int getPathOptions() {
      return this.pathOptions;
   }

   /** @deprecated */
   public Vector getPolicies() {
      return this.policies;
   }

   /** @deprecated */
   public DatabaseService getDatabase() {
      return this.database;
   }

   private boolean certBuildPath(X509Certificate var1, Vector var2, Vector var3, Vector var4, Vector var5) throws CertPathException {
      if (var2.contains(var1)) {
         return false;
      } else {
         var2.addElement(var1);
         if (this.trustedCerts.contains(var1)) {
            if (this.verifyPath(var2, var3, var4, var5)) {
               return true;
            } else {
               var2.removeElement(var1);
               return false;
            }
         } else {
            Vector var6 = new Vector();
            this.getNextCertInPathInternal(var1, var6);
            Iterator var7 = var6.iterator();

            Certificate var8;
            do {
               if (!var7.hasNext()) {
                  var2.removeElement(var1);
                  return false;
               }

               var8 = (Certificate)var7.next();
            } while(!this.certBuildPath((X509Certificate)var8, var2, var3, var4, var5));

            return true;
         }
      }
   }

   private boolean crlBuildPath(X509CRL var1, Vector var2, Vector var3, Vector var4, Vector var5) throws CertPathException {
      Vector var6 = new Vector();
      this.getNextCertInPathInternal(var1, var6);
      Iterator var7 = var6.iterator();

      Certificate var8;
      do {
         if (!var7.hasNext()) {
            return false;
         }

         var8 = (Certificate)var7.next();
      } while(!this.certBuildPath((X509Certificate)var8, var2, var3, var4, var5));

      return true;
   }

   /** @deprecated */
   public void getNextCertCandidates(Object var1, Vector var2) throws CertPathException {
      throw new CertPathException("CertPathCommonImplementation.getNextCertCandidates: subclass should override this method.");
   }

   /** @deprecated */
   public boolean verifyPath(Vector var1, Vector var2, Vector var3, Vector var4) throws CertPathException {
      throw new CertPathException("CertPathCommonImplementation.verifyPath: subclass should overrides this method.");
   }

   /** @deprecated */
   public boolean verifyRevocation(X509Certificate var1, Vector var2, Vector var3) throws CertPathException {
      if ((this.pathOptions & 4) != 0) {
         return true;
      } else {
         CertRevocationInfo var4;
         try {
            var4 = this.certJ.checkCertRevocation(this.pathCtx, var1);
         } catch (NoServiceException var7) {
            throw new CertPathException("CertPathCommonImplementation.verifyRevocation: (no Certificate Status Service is registered)", var7);
         } catch (InvalidParameterException var8) {
            throw new CertPathException("CertPathCommonImplementation.verifyRevocation: (checkCertRevocation parameters)", var8);
         } catch (CertStatusException var9) {
            throw new CertPathException("CertPathCommonImplementation.verifyRevocation: ", var9);
         }

         if (var4.getStatus() != 0) {
            return false;
         } else if (var4.getType() != 1) {
            return true;
         } else {
            CRLEvidence var5 = (CRLEvidence)var4.getEvidence();
            if (var2 != null) {
               CRL var6 = var5.getCRL();
               if (!var2.contains(var6)) {
                  var2.addElement(var6);
               }
            }

            CertJUtils.mergeLists(var2, var5.getCRLList());
            CertJUtils.mergeLists(var3, var5.getCertList());
            return true;
         }
      }
   }

   private void removeInvalidNextCerts(Object var1, Vector var2) throws CertPathException {
      int var3 = var2.size();

      while(var3 > 0) {
         --var3;
         X509Certificate var4 = (X509Certificate)var2.elementAt(var3);
         if (var1 instanceof X509Certificate) {
            if (!this.verifyCertSignature((X509Certificate)var1, var4)) {
               var2.removeElementAt(var3);
            }
         } else if (!this.verifyCrlSignature((X509CRL)var1, var4)) {
            var2.removeElementAt(var3);
         }

         if (!this.verifyCertValidityPeriod(var4)) {
            var2.removeElementAt(var3);
         }
      }

   }

   private boolean verifyCertSignature(X509Certificate var1, X509Certificate var2) throws CertPathException {
      if ((this.pathOptions & 1) != 0) {
         return true;
      } else {
         try {
            JSAFE_PublicKey var3 = var2.getSubjectPublicKey(this.certJ.getDevice());
            return var1.verifyCertificateSignature(this.certJ.getDevice(), var3, this.certJ.getRandomObject());
         } catch (NoServiceException var4) {
            throw new CertPathException("CertPathCommonImplementation.verifyCertSignature:", var4);
         } catch (Exception var5) {
            return false;
         }
      }
   }

   private boolean verifyCrlSignature(X509CRL var1, X509Certificate var2) throws CertPathException {
      if ((this.pathOptions & 1) != 0) {
         return true;
      } else {
         String var3 = this.certJ.getDevice();

         try {
            JSAFE_PublicKey var4 = var2.getSubjectPublicKey(var3);
            return var1.verifyCRLSignature(var3, var4, this.certJ.getRandomObject());
         } catch (NoServiceException var5) {
            throw new CertPathException("CertPathCommonImplementation.verifyCrlSignature:", var5);
         } catch (Exception var6) {
            return false;
         }
      }
   }

   private boolean verifyCertValidityPeriod(X509Certificate var1) {
      if ((this.pathOptions & 2) != 0) {
         return true;
      } else {
         return !this.validationTime.before(var1.getStartDate()) && !this.validationTime.after(var1.getEndDate());
      }
   }

   private void savePathCtx(CertPathCtx var1) {
      this.pathCtx = var1;
      this.pathOptions = var1.getPathOptions();
      this.trustedCerts = new Vector();
      Certificate[] var2 = var1.getTrustedCerts();
      if (var2 != null) {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            this.trustedCerts.addElement(var2[var3]);
         }
      }

      byte[][] var5 = var1.getPolicies();
      if (var5 == null) {
         this.policies = null;
      } else {
         this.policies = new Vector();

         for(int var4 = 0; var4 < var5.length; ++var4) {
            this.policies.addElement(var5[var4]);
         }
      }

      this.validationTime = var1.getValidationTime();
      if (this.validationTime == null) {
         this.validationTime = new Date();
      }

      this.database = var1.getDatabase();
   }
}
