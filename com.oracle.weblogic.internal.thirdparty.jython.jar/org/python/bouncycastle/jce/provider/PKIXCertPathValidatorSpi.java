package org.python.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.python.bouncycastle.jcajce.PKIXExtendedParameters;
import org.python.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.python.bouncycastle.x509.ExtendedPKIXParameters;

public class PKIXCertPathValidatorSpi extends CertPathValidatorSpi {
   private final JcaJceHelper helper = new BCJcaJceHelper();

   public CertPathValidatorResult engineValidate(CertPath var1, CertPathParameters var2) throws CertPathValidatorException, InvalidAlgorithmParameterException {
      PKIXExtendedParameters var5;
      if (var2 instanceof PKIXParameters) {
         PKIXExtendedParameters.Builder var3 = new PKIXExtendedParameters.Builder((PKIXParameters)var2);
         if (var2 instanceof ExtendedPKIXParameters) {
            ExtendedPKIXParameters var4 = (ExtendedPKIXParameters)var2;
            var3.setUseDeltasEnabled(var4.isUseDeltasEnabled());
            var3.setValidityModel(var4.getValidityModel());
         }

         var5 = var3.build();
      } else if (var2 instanceof PKIXExtendedBuilderParameters) {
         var5 = ((PKIXExtendedBuilderParameters)var2).getBaseParameters();
      } else {
         if (!(var2 instanceof PKIXExtendedParameters)) {
            throw new InvalidAlgorithmParameterException("Parameters must be a " + PKIXParameters.class.getName() + " instance.");
         }

         var5 = (PKIXExtendedParameters)var2;
      }

      if (var5.getTrustAnchors() == null) {
         throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
      } else {
         List var35 = var1.getCertificates();
         int var36 = var35.size();
         if (var35.isEmpty()) {
            throw new CertPathValidatorException("Certification path is empty.", (Throwable)null, var1, -1);
         } else {
            Set var6 = var5.getInitialPolicies();

            TrustAnchor var7;
            try {
               var7 = CertPathValidatorUtilities.findTrustAnchor((X509Certificate)var35.get(var35.size() - 1), var5.getTrustAnchors(), var5.getSigProvider());
            } catch (AnnotatedException var34) {
               throw new CertPathValidatorException(var34.getMessage(), var34, var1, var35.size() - 1);
            }

            if (var7 == null) {
               throw new CertPathValidatorException("Trust anchor for certification path not found.", (Throwable)null, var1, -1);
            } else {
               var5 = (new PKIXExtendedParameters.Builder(var5)).setTrustAnchor(var7).build();
               boolean var9 = false;
               ArrayList[] var10 = new ArrayList[var36 + 1];

               for(int var11 = 0; var11 < var10.length; ++var11) {
                  var10[var11] = new ArrayList();
               }

               HashSet var38 = new HashSet();
               var38.add("2.5.29.32.0");
               PKIXPolicyNode var12 = new PKIXPolicyNode(new ArrayList(), 0, var38, (PolicyNode)null, new HashSet(), "2.5.29.32.0", false);
               var10[0].add(var12);
               PKIXNameConstraintValidator var13 = new PKIXNameConstraintValidator();
               HashSet var14 = new HashSet();
               int var15;
               if (var5.isExplicitPolicyRequired()) {
                  var15 = 0;
               } else {
                  var15 = var36 + 1;
               }

               int var16;
               if (var5.isAnyPolicyInhibited()) {
                  var16 = 0;
               } else {
                  var16 = var36 + 1;
               }

               int var17;
               if (var5.isPolicyMappingInhibited()) {
                  var17 = 0;
               } else {
                  var17 = var36 + 1;
               }

               X509Certificate var18 = var7.getTrustedCert();

               X500Name var19;
               PublicKey var20;
               try {
                  if (var18 != null) {
                     var19 = PrincipalUtils.getSubjectPrincipal(var18);
                     var20 = var18.getPublicKey();
                  } else {
                     var19 = PrincipalUtils.getCA(var7);
                     var20 = var7.getCAPublicKey();
                  }
               } catch (IllegalArgumentException var33) {
                  throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", var33, var1, -1);
               }

               AlgorithmIdentifier var21 = null;

               try {
                  var21 = CertPathValidatorUtilities.getAlgorithmIdentifier(var20);
               } catch (CertPathValidatorException var32) {
                  throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", var32, var1, -1);
               }

               ASN1ObjectIdentifier var22 = var21.getAlgorithm();
               ASN1Encodable var23 = var21.getParameters();
               int var24 = var36;
               if (var5.getTargetConstraints() != null && !var5.getTargetConstraints().match((Certificate)((X509Certificate)var35.get(0)))) {
                  throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", (Throwable)null, var1, 0);
               } else {
                  List var25 = var5.getCertPathCheckers();
                  Iterator var8 = var25.iterator();

                  while(var8.hasNext()) {
                     ((PKIXCertPathChecker)var8.next()).init(false);
                  }

                  X509Certificate var26 = null;

                  int var37;
                  for(var37 = var35.size() - 1; var37 >= 0; --var37) {
                     int var27 = var36 - var37;
                     var26 = (X509Certificate)var35.get(var37);
                     boolean var28 = var37 == var35.size() - 1;
                     RFC3280CertPathUtilities.processCertA(var1, var5, var37, var20, var28, var19, var18, this.helper);
                     RFC3280CertPathUtilities.processCertBC(var1, var37, var13);
                     var12 = RFC3280CertPathUtilities.processCertD(var1, var37, var14, var12, var10, var16);
                     var12 = RFC3280CertPathUtilities.processCertE(var1, var37, var12);
                     RFC3280CertPathUtilities.processCertF(var1, var37, var12, var15);
                     if (var27 != var36) {
                        if (var26 != null && var26.getVersion() == 1) {
                           throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", (Throwable)null, var1, var37);
                        }

                        RFC3280CertPathUtilities.prepareNextCertA(var1, var37);
                        var12 = RFC3280CertPathUtilities.prepareCertB(var1, var37, var10, var12, var17);
                        RFC3280CertPathUtilities.prepareNextCertG(var1, var37, var13);
                        var15 = RFC3280CertPathUtilities.prepareNextCertH1(var1, var37, var15);
                        var17 = RFC3280CertPathUtilities.prepareNextCertH2(var1, var37, var17);
                        var16 = RFC3280CertPathUtilities.prepareNextCertH3(var1, var37, var16);
                        var15 = RFC3280CertPathUtilities.prepareNextCertI1(var1, var37, var15);
                        var17 = RFC3280CertPathUtilities.prepareNextCertI2(var1, var37, var17);
                        var16 = RFC3280CertPathUtilities.prepareNextCertJ(var1, var37, var16);
                        RFC3280CertPathUtilities.prepareNextCertK(var1, var37);
                        var24 = RFC3280CertPathUtilities.prepareNextCertL(var1, var37, var24);
                        var24 = RFC3280CertPathUtilities.prepareNextCertM(var1, var37, var24);
                        RFC3280CertPathUtilities.prepareNextCertN(var1, var37);
                        Set var29 = var26.getCriticalExtensionOIDs();
                        HashSet var41;
                        if (var29 != null) {
                           var41 = new HashSet(var29);
                           var41.remove(RFC3280CertPathUtilities.KEY_USAGE);
                           var41.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                           var41.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                           var41.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                           var41.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                           var41.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                           var41.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                           var41.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                           var41.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                           var41.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                        } else {
                           var41 = new HashSet();
                        }

                        RFC3280CertPathUtilities.prepareNextCertO(var1, var37, var41, var25);
                        var18 = var26;
                        var19 = PrincipalUtils.getSubjectPrincipal(var26);

                        try {
                           var20 = CertPathValidatorUtilities.getNextWorkingKey(var1.getCertificates(), var37, this.helper);
                        } catch (CertPathValidatorException var31) {
                           throw new CertPathValidatorException("Next working key could not be retrieved.", var31, var1, var37);
                        }

                        var21 = CertPathValidatorUtilities.getAlgorithmIdentifier(var20);
                        var22 = var21.getAlgorithm();
                        var23 = var21.getParameters();
                     }
                  }

                  var15 = RFC3280CertPathUtilities.wrapupCertA(var15, var26);
                  var15 = RFC3280CertPathUtilities.wrapupCertB(var1, var37 + 1, var15);
                  Set var39 = var26.getCriticalExtensionOIDs();
                  HashSet var40;
                  if (var39 != null) {
                     var40 = new HashSet(var39);
                     var40.remove(RFC3280CertPathUtilities.KEY_USAGE);
                     var40.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                     var40.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                     var40.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                     var40.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                     var40.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                     var40.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                     var40.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                     var40.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                     var40.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                     var40.remove(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
                     var40.remove(Extension.extendedKeyUsage.getId());
                  } else {
                     var40 = new HashSet();
                  }

                  RFC3280CertPathUtilities.wrapupCertF(var1, var37 + 1, var25, var40);
                  PKIXPolicyNode var42 = RFC3280CertPathUtilities.wrapupCertG(var1, var5, var6, var37 + 1, var10, var12, var14);
                  if (var15 <= 0 && var42 == null) {
                     throw new CertPathValidatorException("Path processing failed on policy.", (Throwable)null, var1, var37);
                  } else {
                     return new PKIXCertPathValidatorResult(var7, var42, var26.getPublicKey());
                  }
               }
            }
         }
      }
   }
}
