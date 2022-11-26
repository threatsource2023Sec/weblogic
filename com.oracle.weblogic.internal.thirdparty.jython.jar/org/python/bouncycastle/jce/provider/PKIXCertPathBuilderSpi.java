package org.python.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
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
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.jcajce.PKIXCertStore;
import org.python.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.python.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.python.bouncycastle.jcajce.PKIXExtendedParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.python.bouncycastle.jce.exception.ExtCertPathBuilderException;
import org.python.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import org.python.bouncycastle.x509.ExtendedPKIXParameters;

public class PKIXCertPathBuilderSpi extends CertPathBuilderSpi {
   private Exception certPathException;

   public CertPathBuilderResult engineBuild(CertPathParameters var1) throws CertPathBuilderException, InvalidAlgorithmParameterException {
      PKIXExtendedBuilderParameters var6;
      if (var1 instanceof PKIXBuilderParameters) {
         PKIXExtendedParameters.Builder var2 = new PKIXExtendedParameters.Builder((PKIXBuilderParameters)var1);
         PKIXExtendedBuilderParameters.Builder var5;
         if (!(var1 instanceof ExtendedPKIXParameters)) {
            var5 = new PKIXExtendedBuilderParameters.Builder((PKIXBuilderParameters)var1);
         } else {
            ExtendedPKIXBuilderParameters var3 = (ExtendedPKIXBuilderParameters)var1;
            Iterator var4 = var3.getAdditionalStores().iterator();

            while(var4.hasNext()) {
               var2.addCertificateStore((PKIXCertStore)var4.next());
            }

            var5 = new PKIXExtendedBuilderParameters.Builder(var2.build());
            var5.addExcludedCerts(var3.getExcludedCerts());
            var5.setMaxPathLength(var3.getMaxPathLength());
         }

         var6 = var5.build();
      } else {
         if (!(var1 instanceof PKIXExtendedBuilderParameters)) {
            throw new InvalidAlgorithmParameterException("Parameters must be an instance of " + PKIXBuilderParameters.class.getName() + " or " + PKIXExtendedBuilderParameters.class.getName() + ".");
         }

         var6 = (PKIXExtendedBuilderParameters)var1;
      }

      ArrayList var11 = new ArrayList();
      PKIXCertStoreSelector var7 = var6.getBaseParameters().getTargetConstraints();

      Collection var10;
      try {
         var10 = CertPathValidatorUtilities.findCertificates(var7, var6.getBaseParameters().getCertificateStores());
         var10.addAll(CertPathValidatorUtilities.findCertificates(var7, var6.getBaseParameters().getCertStores()));
      } catch (AnnotatedException var9) {
         throw new ExtCertPathBuilderException("Error finding target certificate.", var9);
      }

      if (var10.isEmpty()) {
         throw new CertPathBuilderException("No certificate found matching targetContraints.");
      } else {
         CertPathBuilderResult var8 = null;

         X509Certificate var12;
         for(Iterator var13 = var10.iterator(); var13.hasNext() && var8 == null; var8 = this.build(var12, var6, var11)) {
            var12 = (X509Certificate)var13.next();
         }

         if (var8 == null && this.certPathException != null) {
            if (this.certPathException instanceof AnnotatedException) {
               throw new CertPathBuilderException(this.certPathException.getMessage(), this.certPathException.getCause());
            } else {
               throw new CertPathBuilderException("Possible certificate chain could not be validated.", this.certPathException);
            }
         } else if (var8 == null && this.certPathException == null) {
            throw new CertPathBuilderException("Unable to find certificate chain.");
         } else {
            return var8;
         }
      }
   }

   protected CertPathBuilderResult build(X509Certificate var1, PKIXExtendedBuilderParameters var2, List var3) {
      if (var3.contains(var1)) {
         return null;
      } else if (var2.getExcludedCerts().contains(var1)) {
         return null;
      } else if (var2.getMaxPathLength() != -1 && var3.size() - 1 > var2.getMaxPathLength()) {
         return null;
      } else {
         var3.add(var1);
         CertPathBuilderResult var4 = null;

         CertificateFactory var5;
         PKIXCertPathValidatorSpi var6;
         try {
            var5 = new CertificateFactory();
            var6 = new PKIXCertPathValidatorSpi();
         } catch (Exception var15) {
            throw new RuntimeException("Exception creating support classes.");
         }

         try {
            ArrayList var7;
            HashSet var8;
            if (CertPathValidatorUtilities.findTrustAnchor(var1, var2.getBaseParameters().getTrustAnchors(), var2.getBaseParameters().getSigProvider()) != null) {
               var7 = null;
               var8 = null;

               CertPath var17;
               try {
                  var17 = var5.engineGenerateCertPath(var3);
               } catch (Exception var12) {
                  throw new AnnotatedException("Certification path could not be constructed from certificate list.", var12);
               }

               PKIXCertPathValidatorResult var18;
               try {
                  var18 = (PKIXCertPathValidatorResult)var6.engineValidate(var17, var2);
               } catch (Exception var11) {
                  throw new AnnotatedException("Certification path could not be validated.", var11);
               }

               return new PKIXCertPathBuilderResult(var17, var18.getTrustAnchor(), var18.getPolicyTree(), var18.getPublicKey());
            }

            var7 = new ArrayList();
            var7.addAll(var2.getBaseParameters().getCertificateStores());

            try {
               var7.addAll(CertPathValidatorUtilities.getAdditionalStoresFromAltNames(var1.getExtensionValue(Extension.issuerAlternativeName.getId()), var2.getBaseParameters().getNamedCertificateStoreMap()));
            } catch (CertificateParsingException var14) {
               throw new AnnotatedException("No additional X.509 stores can be added from certificate locations.", var14);
            }

            var8 = new HashSet();

            try {
               var8.addAll(CertPathValidatorUtilities.findIssuerCerts(var1, var2.getBaseParameters().getCertStores(), var7));
            } catch (AnnotatedException var13) {
               throw new AnnotatedException("Cannot find issuer certificate for certificate in certification path.", var13);
            }

            if (var8.isEmpty()) {
               throw new AnnotatedException("No issuer certificate for certificate in certification path found.");
            }

            X509Certificate var10;
            for(Iterator var9 = var8.iterator(); var9.hasNext() && var4 == null; var4 = this.build(var10, var2, var3)) {
               var10 = (X509Certificate)var9.next();
            }
         } catch (AnnotatedException var16) {
            this.certPathException = var16;
         }

         if (var4 == null) {
            var3.remove(var1);
         }

         return var4;
      }
   }
}
