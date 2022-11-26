package org.python.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.python.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.python.bouncycastle.jcajce.PKIXExtendedParameters;
import org.python.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.python.bouncycastle.x509.ExtendedPKIXParameters;
import org.python.bouncycastle.x509.X509AttributeCertStoreSelector;
import org.python.bouncycastle.x509.X509AttributeCertificate;

public class PKIXAttrCertPathValidatorSpi extends CertPathValidatorSpi {
   private final JcaJceHelper helper = new BCJcaJceHelper();

   public CertPathValidatorResult engineValidate(CertPath var1, CertPathParameters var2) throws CertPathValidatorException, InvalidAlgorithmParameterException {
      if (!(var2 instanceof ExtendedPKIXParameters) && !(var2 instanceof PKIXExtendedParameters)) {
         throw new InvalidAlgorithmParameterException("Parameters must be a " + ExtendedPKIXParameters.class.getName() + " instance.");
      } else {
         Object var3 = new HashSet();
         Object var4 = new HashSet();
         Object var5 = new HashSet();
         HashSet var6 = new HashSet();
         PKIXExtendedParameters var9;
         if (var2 instanceof PKIXParameters) {
            PKIXExtendedParameters.Builder var7 = new PKIXExtendedParameters.Builder((PKIXParameters)var2);
            if (var2 instanceof ExtendedPKIXParameters) {
               ExtendedPKIXParameters var8 = (ExtendedPKIXParameters)var2;
               var7.setUseDeltasEnabled(var8.isUseDeltasEnabled());
               var7.setValidityModel(var8.getValidityModel());
               var3 = var8.getAttrCertCheckers();
               var4 = var8.getProhibitedACAttributes();
               var5 = var8.getNecessaryACAttributes();
            }

            var9 = var7.build();
         } else {
            var9 = (PKIXExtendedParameters)var2;
         }

         PKIXCertStoreSelector var16 = var9.getTargetConstraints();
         if (!(var16 instanceof X509AttributeCertStoreSelector)) {
            throw new InvalidAlgorithmParameterException("TargetConstraints must be an instance of " + X509AttributeCertStoreSelector.class.getName() + " for " + this.getClass().getName() + " class.");
         } else {
            X509AttributeCertificate var17 = ((X509AttributeCertStoreSelector)var16).getAttributeCert();
            CertPath var10 = RFC3281CertPathUtilities.processAttrCert1(var17, var9);
            CertPathValidatorResult var11 = RFC3281CertPathUtilities.processAttrCert2(var1, var9);
            X509Certificate var12 = (X509Certificate)var1.getCertificates().get(0);
            RFC3281CertPathUtilities.processAttrCert3(var12, var9);
            RFC3281CertPathUtilities.processAttrCert4(var12, var6);
            RFC3281CertPathUtilities.processAttrCert5(var17, var9);
            RFC3281CertPathUtilities.processAttrCert7(var17, var1, var10, var9, (Set)var3);
            RFC3281CertPathUtilities.additionalChecks(var17, (Set)var4, (Set)var5);
            Date var13 = null;

            try {
               var13 = CertPathValidatorUtilities.getValidCertDateFromValidityModel(var9, (CertPath)null, -1);
            } catch (AnnotatedException var15) {
               throw new ExtCertPathValidatorException("Could not get validity date from attribute certificate.", var15);
            }

            RFC3281CertPathUtilities.checkCRLs(var17, var9, var12, var13, var1.getCertificates(), this.helper);
            return var11;
         }
      }
   }
}
