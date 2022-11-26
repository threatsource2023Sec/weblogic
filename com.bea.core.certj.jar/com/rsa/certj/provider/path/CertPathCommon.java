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
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.extensions.GeneralNames;
import com.rsa.certj.cert.extensions.GeneralSubtrees;
import com.rsa.certj.provider.revocation.CRLEvidence;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.certj.spi.path.CertPathInterface;
import com.rsa.certj.spi.path.CertPathResult;
import com.rsa.certj.spi.revocation.CertRevocationInfo;
import com.rsa.certj.spi.revocation.CertStatusException;
import com.rsa.certj.x.f;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

abstract class CertPathCommon extends ProviderImplementation implements CertPathInterface {
   /** @deprecated */
   protected static final boolean DEBUG_ON = f.a();

   /** @deprecated */
   protected CertPathCommon(CertJ var1, String var2) throws InvalidParameterException {
      super(var1, var2);
   }

   private boolean buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5, Vector var6, GeneralSubtrees var7, GeneralNames var8, CertPathResult var9) throws NotSupportedException, CertPathException {
      if (this.trustedCertsEmpty(var1)) {
         throw new CertPathException("TrustedCerts component of pathCtx should not be empty.");
      } else {
         f.a(DEBUG_ON, "________________Start Cert Path________________");
         if (var4 == null && var5 != null) {
            var5 = null;
         }

         Vector var10 = new Vector();
         if (var2 instanceof X509Certificate) {
            X509Certificate var11 = (X509Certificate)var2;
            if (!this.verifyCertValidityPeriod(var1, var11)) {
               if (!this.isTrusted(var1, var11)) {
                  if (DEBUG_ON) {
                     String var12 = var11.getSubjectName() == null ? "null" : var11.getSubjectName().toString();
                     f.a("Certificate " + var12 + " cannot be trusted.");
                     f.a("________________ End Cert Path ________________");
                  }

                  return false;
               }

               if (var3 != null && !var3.contains(var11)) {
                  var3.addElement(var11);
               }

               if (var9 != null) {
                  var9.setValidationResult(true);
               }

               if (DEBUG_ON) {
                  f.a("Certificate " + var11.getSubjectName().toString() + " is not in validity period but is trusted, so validation is successful.");
                  f.a("________________ End Cert Path ________________");
               }

               return true;
            }

            if (!this.certBuildPath(var1, var11, var10, var4, var5, var6, var7, var8, var9)) {
               return false;
            }
         } else {
            if (!(var2 instanceof X509CRL)) {
               throw new NotSupportedException("Does not support startObjects other than X509Certificate or X509CRL.");
            }

            if (!this.crlBuildPath(var1, (X509CRL)var2, var10, var4, var5, var6, var7, var8)) {
               f.a("________________ End Cert Path ________________");
               return false;
            }
         }

         CertJUtils.mergeLists(var3, var10);
         CertJUtils.subtractLists(var5, var3);
         f.a(DEBUG_ON, "________________ End Cert Path ________________");
         return true;
      }
   }

   public boolean buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5, Vector var6) throws NotSupportedException, CertPathException {
      return this.buildCertPath(var1, var2, var3, var4, var5, var6, (GeneralSubtrees)null, (GeneralNames)null, (CertPathResult)null);
   }

   public CertPathResult buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5) throws NotSupportedException, CertPathException {
      return this.buildCertPath(var1, var2, var3, var4, var5, (GeneralSubtrees)null, (GeneralNames)null);
   }

   public CertPathResult buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5, GeneralSubtrees var6, GeneralNames var7) throws NotSupportedException, CertPathException {
      CertPathResult var8 = this.createCertPathResult();
      this.buildCertPath(var1, var2, var3, var4, var5, (Vector)null, var6, var7, var8);
      return var8;
   }

   /** @deprecated */
   protected CertPathResult createCertPathResult() throws NotSupportedException {
      throw new NotSupportedException("The path provider does not support this functionality!");
   }

   public void getNextCertInPath(CertPathCtx var1, Object var2, Vector var3) throws NotSupportedException, CertPathException {
      if (!(var2 instanceof X509Certificate) && !(var2 instanceof X509CRL)) {
         throw new NotSupportedException("startObjects other than X509Certificate or X509CRL not supported.");
      } else {
         this.getNextCertInPathInternal(var1, var2, var3);
      }
   }

   /** @deprecated */
   protected void getNextCertInPathInternal(CertPathCtx var1, Object var2, Vector var3) throws CertPathException {
      Vector var4 = new Vector();
      this.getNextCertCandidates(var1, var2, var4);
      this.removeInvalidNextCerts(var1, var2, var4);
      CertJUtils.mergeLists(var3, var4);
   }

   public boolean validateCertificate(CertPathCtx var1, Certificate var2, JSAFE_PublicKey var3) throws NotSupportedException, CertPathException {
      if (!(var2 instanceof X509Certificate)) {
         throw new NotSupportedException("No certificate types other than X509Certificate supported.");
      } else if (!this.verifyCertValidityPeriod(var1, (X509Certificate)var2)) {
         return false;
      } else if ((var1.getPathOptions() & 1) != 0) {
         return true;
      } else {
         try {
            return var2.verifyCertificateSignature(this.certJ.getDevice(), (JSAFE_PublicKey)var3, this.certJ.getRandomObject());
         } catch (NoServiceException var5) {
            throw new CertPathException("No random service is registered.", var5);
         } catch (Exception var6) {
            return false;
         }
      }
   }

   private boolean certBuildPath(CertPathCtx var1, X509Certificate var2, Vector var3, Vector var4, Vector var5, Vector var6, GeneralSubtrees var7, GeneralNames var8, CertPathResult var9) throws CertPathException {
      if (DEBUG_ON) {
         f.a("Checking certificate " + var2.getSubjectName().toString() + " for cert path.");
      }

      if (var3.contains(var2)) {
         f.a(DEBUG_ON, "Certificate already in cert path, so ignore.");
         return false;
      } else {
         f.a(DEBUG_ON, "Adding certificate to cert path.");
         var3.addElement(var2);
         if (this.isTrusted(var1, var2)) {
            f.a(DEBUG_ON, "Certificate is a trusted one, verifying path now.");
            if (this.verifyPath(var1, var3, var4, var5, var6, var7, var8, var9)) {
               if (DEBUG_ON) {
                  f.a("Certificate path validation passed for certificate " + var2.getSubjectName().toString());
               }

               return true;
            } else {
               if (DEBUG_ON) {
                  f.a("Certificate path validation failed for certificate " + var2.getSubjectName().toString() + ", removing trusted cert from path.");
               }

               var3.removeElement(var2);
               return false;
            }
         } else {
            f.a(DEBUG_ON, "Certificate is not a trusted one, trying to find certificate candidates now.");
            Vector var10 = new Vector();
            this.getNextCertInPathInternal(var1, var2, var10);
            f.a(DEBUG_ON, "Found next " + var10.size() + " certificate candidates in context.");
            Iterator var11 = var10.iterator();

            Certificate var12;
            do {
               if (!var11.hasNext()) {
                  if (DEBUG_ON) {
                     f.a("Did not find any certificate candidates in path for certificate " + var2.getSubjectName().toString() + ", removing cert from path.");
                  }

                  var3.removeElement(var2);
                  return false;
               }

               var12 = (Certificate)var11.next();
            } while(!this.certBuildPath(var1, (X509Certificate)var12, var3, var4, var5, var6, var7, var8, var9));

            if (DEBUG_ON) {
               f.a("Certificate path validation passed for certificate " + var2.getSubjectName().toString());
            }

            return true;
         }
      }
   }

   private boolean crlBuildPath(CertPathCtx var1, X509CRL var2, Vector var3, Vector var4, Vector var5, Vector var6, GeneralSubtrees var7, GeneralNames var8) throws CertPathException {
      if (DEBUG_ON) {
         f.a("Checking CRL from issuer " + var2.getIssuerName().toString() + " for cert path.");
      }

      f.a(DEBUG_ON, "Trying to find issuing candidates for CRL now.");
      Vector var9 = new Vector();
      this.getNextCertInPathInternal(var1, var2, var9);
      f.a(DEBUG_ON, "Found next " + var9.size() + " issuing candidates for CRL in context.");
      Iterator var10 = var9.iterator();

      Certificate var11;
      do {
         if (!var10.hasNext()) {
            if (DEBUG_ON) {
               f.a("Did not find any issuing candidates in path for CRL issued by " + var2.getIssuerName().toString());
            }

            return false;
         }

         var11 = (Certificate)var10.next();
      } while(!this.certBuildPath(var1, (X509Certificate)var11, var3, var4, var5, var6, var7, var8, (CertPathResult)null));

      return true;
   }

   /** @deprecated */
   protected void getNextCertCandidates(CertPathCtx var1, Object var2, Vector var3) throws CertPathException {
      throw new CertPathException("Subclass should override this method.");
   }

   /** @deprecated */
   protected boolean verifyPath(CertPathCtx var1, Vector var2, Vector var3, Vector var4, Vector var5, GeneralSubtrees var6, GeneralNames var7, CertPathResult var8) throws CertPathException {
      throw new CertPathException("Subclass should override this method.");
   }

   public boolean verifyRevocation(CertPathCtx var1, X509Certificate var2, Vector var3, Vector var4) throws CertPathException {
      if ((var1.getPathOptions() & 4) != 0) {
         return true;
      } else {
         if (DEBUG_ON) {
            f.a("Running certificate revocation check for certificate " + var2.getSubjectName().toString());
         }

         CertRevocationInfo var5;
         try {
            var5 = this.certJ.checkCertRevocation(var1, var2);
         } catch (NoServiceException var8) {
            throw new CertPathException("No Certificate Status Service is registered.", var8);
         } catch (InvalidParameterException var9) {
            throw new CertPathException("Check certificate revocation parameters.", var9);
         } catch (CertStatusException var10) {
            throw new CertPathException(var10);
         }

         if (var5.getStatus() != 0) {
            return false;
         } else {
            switch (var5.getType()) {
               case 1:
                  if (DEBUG_ON) {
                     f.a("Certificate revocation check passed on CRL evidence for certificate " + var2.getSubjectName().toString());
                  }

                  CRLEvidence var6 = (CRLEvidence)var5.getEvidence();
                  if (var3 != null) {
                     CRL var7 = var6.getCRL();
                     if (var7 != null && !var3.contains(var7)) {
                        var3.addElement(var7);
                     }

                     if (DEBUG_ON && var7 instanceof X509CRL) {
                        f.a("    Revocation evidence CRL issuer:  " + ((X509CRL)var7).getIssuerName().toString());
                     }
                  }

                  CertJUtils.mergeLists(var3, var6.getCRLList());
                  CertJUtils.mergeLists(var4, var6.getCertList());
                  break;
               case 2:
                  f.a(DEBUG_ON, "Certificate revocation check passed on OCSP evidence.");
            }

            return true;
         }
      }
   }

   private void removeInvalidNextCerts(CertPathCtx var1, Object var2, Vector var3) throws CertPathException {
      int var5 = var3.size();

      while(var5 > 0) {
         --var5;
         X509Certificate var6 = (X509Certificate)var3.elementAt(var5);
         boolean var4 = this.verifyCertValidityPeriod(var1, var6);
         if (var4) {
            if (var2 instanceof X509Certificate) {
               var4 = this.verifyCertSignature(var1, (X509Certificate)var2, var6);
            } else {
               var4 = this.verifyCrlSignature(var1, (X509CRL)var2, var6);
            }
         }

         if (!var4) {
            var3.removeElementAt(var5);
         }
      }

   }

   private boolean verifyCertSignature(CertPathCtx var1, X509Certificate var2, X509Certificate var3) throws CertPathException {
      if ((var1.getPathOptions() & 1) != 0) {
         return true;
      } else {
         try {
            JSAFE_PublicKey var4 = var3.getSubjectPublicKey(this.certJ.getDevice());
            return var2.verifyCertificateSignature(this.certJ.getDevice(), var4, this.certJ.getRandomObject());
         } catch (NoServiceException var5) {
            throw new CertPathException(var5);
         } catch (Exception var6) {
            return false;
         }
      }
   }

   private boolean verifyCrlSignature(CertPathCtx var1, X509CRL var2, X509Certificate var3) throws CertPathException {
      if ((var1.getPathOptions() & 1) != 0) {
         return true;
      } else {
         String var4 = this.certJ.getDevice();

         try {
            JSAFE_PublicKey var5 = var3.getSubjectPublicKey(var4);
            return var2.verifyCRLSignature(var4, var5, this.certJ.getRandomObject());
         } catch (NoServiceException var6) {
            throw new CertPathException(var6);
         } catch (Exception var7) {
            return false;
         }
      }
   }

   private boolean verifyCertValidityPeriod(CertPathCtx var1, X509Certificate var2) {
      if ((var1.getPathOptions() & 2) != 0) {
         return true;
      } else {
         Date var3 = var1.getValidationTime();
         if (var3 == null) {
            var3 = new Date();
         }

         boolean var4 = var2.checkValidityDate(var3);
         if (DEBUG_ON) {
            String var5 = var2.getSubjectName() == null ? "null" : var2.getSubjectName().toString();
            f.a("Validity check of cert " + var5 + (var4 ? " succeeded" : " failed"));
         }

         return var4;
      }
   }

   /** @deprecated */
   protected boolean isTrusted(CertPathCtx var1, Certificate var2) {
      Certificate[] var3 = var1.getTrustedCerts();
      if (var3 == null) {
         return false;
      } else {
         for(int var4 = 0; var4 < var3.length; ++var4) {
            if (var2.equals(var3[var4])) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean trustedCertsEmpty(CertPathCtx var1) {
      Certificate[] var2 = var1.getTrustedCerts();
      return var2 == null || var2.length == 0;
   }

   /** @deprecated */
   protected void findCertBySubject(CertPathCtx var1, X500Name var2, Vector var3) throws CertPathException {
      Certificate[] var4 = var1.getTrustedCerts();
      if (var4 != null) {
         for(int var5 = 0; var5 < var4.length; ++var5) {
            Certificate var6 = var4[var5];
            if (var6 instanceof X509Certificate) {
               X509Certificate var7 = (X509Certificate)var6;
               if (var2 != null && var2.equals(var7.getSubjectName()) && !var3.contains(var7)) {
                  var3.addElement(var7);
               }
            }
         }
      }

      try {
         DatabaseService var10 = var1.getDatabase();
         if (var10 != null) {
            var10.selectCertificateBySubject(var2, var3);
         }

      } catch (NoServiceException var8) {
         throw new CertPathException(var8);
      } catch (DatabaseException var9) {
         throw new CertPathException(var9);
      }
   }
}
