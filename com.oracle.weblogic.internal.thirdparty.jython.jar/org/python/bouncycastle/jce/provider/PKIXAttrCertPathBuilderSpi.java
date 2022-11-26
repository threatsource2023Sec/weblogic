package org.python.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Principal;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidator;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.python.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.python.bouncycastle.jce.exception.ExtCertPathBuilderException;
import org.python.bouncycastle.util.Store;
import org.python.bouncycastle.util.StoreException;
import org.python.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import org.python.bouncycastle.x509.ExtendedPKIXParameters;
import org.python.bouncycastle.x509.X509AttributeCertStoreSelector;
import org.python.bouncycastle.x509.X509AttributeCertificate;
import org.python.bouncycastle.x509.X509CertStoreSelector;

public class PKIXAttrCertPathBuilderSpi extends CertPathBuilderSpi {
   private Exception certPathException;

   public CertPathBuilderResult engineBuild(CertPathParameters var1) throws CertPathBuilderException, InvalidAlgorithmParameterException {
      if (!(var1 instanceof PKIXBuilderParameters) && !(var1 instanceof ExtendedPKIXBuilderParameters) && !(var1 instanceof PKIXExtendedBuilderParameters)) {
         throw new InvalidAlgorithmParameterException("Parameters must be an instance of " + PKIXBuilderParameters.class.getName() + " or " + PKIXExtendedBuilderParameters.class.getName() + ".");
      } else {
         Object var2 = new ArrayList();
         PKIXExtendedBuilderParameters var5;
         if (var1 instanceof PKIXBuilderParameters) {
            PKIXExtendedBuilderParameters.Builder var3 = new PKIXExtendedBuilderParameters.Builder((PKIXBuilderParameters)var1);
            if (var1 instanceof ExtendedPKIXParameters) {
               ExtendedPKIXBuilderParameters var4 = (ExtendedPKIXBuilderParameters)var1;
               var3.addExcludedCerts(var4.getExcludedCerts());
               var3.setMaxPathLength(var4.getMaxPathLength());
               var2 = var4.getStores();
            }

            var5 = var3.build();
         } else {
            var5 = (PKIXExtendedBuilderParameters)var1;
         }

         ArrayList var6 = new ArrayList();
         PKIXCertStoreSelector var7 = var5.getBaseParameters().getTargetConstraints();
         if (!(var7 instanceof X509AttributeCertStoreSelector)) {
            throw new CertPathBuilderException("TargetConstraints must be an instance of " + X509AttributeCertStoreSelector.class.getName() + " for " + this.getClass().getName() + " class.");
         } else {
            Collection var18;
            try {
               var18 = findCertificates((X509AttributeCertStoreSelector)var7, (List)var2);
            } catch (AnnotatedException var15) {
               throw new ExtCertPathBuilderException("Error finding target attribute certificate.", var15);
            }

            if (var18.isEmpty()) {
               throw new CertPathBuilderException("No attribute certificate found matching targetContraints.");
            } else {
               CertPathBuilderResult var8 = null;
               Iterator var19 = var18.iterator();

               while(var19.hasNext() && var8 == null) {
                  X509AttributeCertificate var9 = (X509AttributeCertificate)var19.next();
                  X509CertStoreSelector var10 = new X509CertStoreSelector();
                  Principal[] var11 = var9.getIssuer().getPrincipals();
                  HashSet var12 = new HashSet();

                  for(int var13 = 0; var13 < var11.length; ++var13) {
                     try {
                        if (var11[var13] instanceof X500Principal) {
                           var10.setSubject(((X500Principal)var11[var13]).getEncoded());
                        }

                        PKIXCertStoreSelector var14 = (new PKIXCertStoreSelector.Builder(var10)).build();
                        var12.addAll(CertPathValidatorUtilities.findCertificates(var14, var5.getBaseParameters().getCertStores()));
                        var12.addAll(CertPathValidatorUtilities.findCertificates(var14, var5.getBaseParameters().getCertificateStores()));
                     } catch (AnnotatedException var16) {
                        throw new ExtCertPathBuilderException("Public key certificate for attribute certificate cannot be searched.", var16);
                     } catch (IOException var17) {
                        throw new ExtCertPathBuilderException("cannot encode X500Principal.", var17);
                     }
                  }

                  if (var12.isEmpty()) {
                     throw new CertPathBuilderException("Public key certificate for attribute certificate cannot be found.");
                  }

                  for(Iterator var20 = var12.iterator(); var20.hasNext() && var8 == null; var8 = this.build(var9, (X509Certificate)var20.next(), var5, var6)) {
                  }
               }

               if (var8 == null && this.certPathException != null) {
                  throw new ExtCertPathBuilderException("Possible certificate chain could not be validated.", this.certPathException);
               } else if (var8 == null && this.certPathException == null) {
                  throw new CertPathBuilderException("Unable to find certificate chain.");
               } else {
                  return var8;
               }
            }
         }
      }
   }

   private CertPathBuilderResult build(X509AttributeCertificate var1, X509Certificate var2, PKIXExtendedBuilderParameters var3, List var4) {
      if (var4.contains(var2)) {
         return null;
      } else if (var3.getExcludedCerts().contains(var2)) {
         return null;
      } else if (var3.getMaxPathLength() != -1 && var4.size() - 1 > var3.getMaxPathLength()) {
         return null;
      } else {
         var4.add(var2);
         CertPathBuilderResult var5 = null;

         CertificateFactory var6;
         CertPathValidator var7;
         try {
            var6 = CertificateFactory.getInstance("X.509", "BC");
            var7 = CertPathValidator.getInstance("RFC3281", "BC");
         } catch (Exception var16) {
            throw new RuntimeException("Exception creating support classes.");
         }

         try {
            if (CertPathValidatorUtilities.findTrustAnchor(var2, var3.getBaseParameters().getTrustAnchors(), var3.getBaseParameters().getSigProvider()) != null) {
               CertPath var18;
               try {
                  var18 = var6.generateCertPath(var4);
               } catch (Exception var13) {
                  throw new AnnotatedException("Certification path could not be constructed from certificate list.", var13);
               }

               PKIXCertPathValidatorResult var19;
               try {
                  var19 = (PKIXCertPathValidatorResult)var7.validate(var18, var3);
               } catch (Exception var12) {
                  throw new AnnotatedException("Certification path could not be validated.", var12);
               }

               return new PKIXCertPathBuilderResult(var18, var19.getTrustAnchor(), var19.getPolicyTree(), var19.getPublicKey());
            }

            ArrayList var8 = new ArrayList();
            var8.addAll(var3.getBaseParameters().getCertificateStores());

            try {
               var8.addAll(CertPathValidatorUtilities.getAdditionalStoresFromAltNames(var2.getExtensionValue(Extension.issuerAlternativeName.getId()), var3.getBaseParameters().getNamedCertificateStoreMap()));
            } catch (CertificateParsingException var15) {
               throw new AnnotatedException("No additional X.509 stores can be added from certificate locations.", var15);
            }

            HashSet var10 = new HashSet();

            try {
               var10.addAll(CertPathValidatorUtilities.findIssuerCerts(var2, var3.getBaseParameters().getCertStores(), var8));
            } catch (AnnotatedException var14) {
               throw new AnnotatedException("Cannot find issuer certificate for certificate in certification path.", var14);
            }

            if (var10.isEmpty()) {
               throw new AnnotatedException("No issuer certificate for certificate in certification path found.");
            }

            Iterator var9 = var10.iterator();

            while(var9.hasNext() && var5 == null) {
               X509Certificate var11 = (X509Certificate)var9.next();
               if (!var11.getIssuerX500Principal().equals(var11.getSubjectX500Principal())) {
                  var5 = this.build(var1, var11, var3, var4);
               }
            }
         } catch (AnnotatedException var17) {
            this.certPathException = new AnnotatedException("No valid certification path could be build.", var17);
         }

         if (var5 == null) {
            var4.remove(var2);
         }

         return var5;
      }
   }

   protected static Collection findCertificates(X509AttributeCertStoreSelector var0, List var1) throws AnnotatedException {
      HashSet var2 = new HashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (var4 instanceof Store) {
            Store var5 = (Store)var4;

            try {
               var2.addAll(var5.getMatches(var0));
            } catch (StoreException var7) {
               throw new AnnotatedException("Problem while picking certificates from X.509 store.", var7);
            }
         }
      }

      return var2;
   }
}
