package org.python.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1String;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x500.RDN;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x500.style.BCStyle;
import org.python.bouncycastle.asn1.x509.BasicConstraints;
import org.python.bouncycastle.asn1.x509.CRLDistPoint;
import org.python.bouncycastle.asn1.x509.DistributionPoint;
import org.python.bouncycastle.asn1.x509.DistributionPointName;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.GeneralNames;
import org.python.bouncycastle.asn1.x509.GeneralSubtree;
import org.python.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.python.bouncycastle.asn1.x509.NameConstraints;
import org.python.bouncycastle.asn1.x509.PolicyInformation;
import org.python.bouncycastle.asn1.x509.ReasonFlags;
import org.python.bouncycastle.jcajce.PKIXCRLStore;
import org.python.bouncycastle.jcajce.PKIXCRLStoreSelector;
import org.python.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.python.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.python.bouncycastle.jcajce.PKIXExtendedParameters;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.python.bouncycastle.util.Arrays;

class RFC3280CertPathUtilities {
   private static final PKIXCRLUtil CRL_UTIL = new PKIXCRLUtil();
   public static final String CERTIFICATE_POLICIES;
   public static final String POLICY_MAPPINGS;
   public static final String INHIBIT_ANY_POLICY;
   public static final String ISSUING_DISTRIBUTION_POINT;
   public static final String FRESHEST_CRL;
   public static final String DELTA_CRL_INDICATOR;
   public static final String POLICY_CONSTRAINTS;
   public static final String BASIC_CONSTRAINTS;
   public static final String CRL_DISTRIBUTION_POINTS;
   public static final String SUBJECT_ALTERNATIVE_NAME;
   public static final String NAME_CONSTRAINTS;
   public static final String AUTHORITY_KEY_IDENTIFIER;
   public static final String KEY_USAGE;
   public static final String CRL_NUMBER;
   public static final String ANY_POLICY = "2.5.29.32.0";
   protected static final int KEY_CERT_SIGN = 5;
   protected static final int CRL_SIGN = 6;
   protected static final String[] crlReasons;

   protected static void processCRLB2(DistributionPoint var0, Object var1, X509CRL var2) throws AnnotatedException {
      IssuingDistributionPoint var3 = null;

      try {
         var3 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var2, ISSUING_DISTRIBUTION_POINT));
      } catch (Exception var13) {
         throw new AnnotatedException("Issuing distribution point extension could not be decoded.", var13);
      }

      if (var3 != null) {
         DistributionPointName var4;
         if (var3.getDistributionPoint() != null) {
            var4 = IssuingDistributionPoint.getInstance(var3).getDistributionPoint();
            ArrayList var5 = new ArrayList();
            if (var4.getType() == 0) {
               GeneralName[] var6 = GeneralNames.getInstance(var4.getName()).getNames();

               for(int var7 = 0; var7 < var6.length; ++var7) {
                  var5.add(var6[var7]);
               }
            }

            if (var4.getType() == 1) {
               ASN1EncodableVector var16 = new ASN1EncodableVector();

               try {
                  Enumeration var18 = ASN1Sequence.getInstance(PrincipalUtils.getIssuerPrincipal(var2)).getObjects();

                  while(var18.hasMoreElements()) {
                     var16.add((ASN1Encodable)var18.nextElement());
                  }
               } catch (Exception var14) {
                  throw new AnnotatedException("Could not read CRL issuer.", var14);
               }

               var16.add(var4.getName());
               var5.add(new GeneralName(X500Name.getInstance(new DERSequence(var16))));
            }

            boolean var17 = false;
            int var8;
            GeneralName[] var19;
            if (var0.getDistributionPoint() != null) {
               var4 = var0.getDistributionPoint();
               var19 = null;
               if (var4.getType() == 0) {
                  var19 = GeneralNames.getInstance(var4.getName()).getNames();
               }

               if (var4.getType() == 1) {
                  if (var0.getCRLIssuer() != null) {
                     var19 = var0.getCRLIssuer().getNames();
                  } else {
                     var19 = new GeneralName[1];

                     try {
                        var19[0] = new GeneralName(X500Name.getInstance(PrincipalUtils.getEncodedIssuerPrincipal(var1).getEncoded()));
                     } catch (Exception var12) {
                        throw new AnnotatedException("Could not read certificate issuer.", var12);
                     }
                  }

                  for(var8 = 0; var8 < var19.length; ++var8) {
                     Enumeration var9 = ASN1Sequence.getInstance(var19[var8].getName().toASN1Primitive()).getObjects();
                     ASN1EncodableVector var10 = new ASN1EncodableVector();

                     while(var9.hasMoreElements()) {
                        var10.add((ASN1Encodable)var9.nextElement());
                     }

                     var10.add(var4.getName());
                     var19[var8] = new GeneralName(X500Name.getInstance(new DERSequence(var10)));
                  }
               }

               if (var19 != null) {
                  for(var8 = 0; var8 < var19.length; ++var8) {
                     if (var5.contains(var19[var8])) {
                        var17 = true;
                        break;
                     }
                  }
               }

               if (!var17) {
                  throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
               }
            } else {
               if (var0.getCRLIssuer() == null) {
                  throw new AnnotatedException("Either the cRLIssuer or the distributionPoint field must be contained in DistributionPoint.");
               }

               var19 = var0.getCRLIssuer().getNames();

               for(var8 = 0; var8 < var19.length; ++var8) {
                  if (var5.contains(var19[var8])) {
                     var17 = true;
                     break;
                  }
               }

               if (!var17) {
                  throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
               }
            }
         }

         var4 = null;

         BasicConstraints var15;
         try {
            var15 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)var1, BASIC_CONSTRAINTS));
         } catch (Exception var11) {
            throw new AnnotatedException("Basic constraints extension could not be decoded.", var11);
         }

         if (var1 instanceof X509Certificate) {
            if (var3.onlyContainsUserCerts() && var15 != null && var15.isCA()) {
               throw new AnnotatedException("CA Cert CRL only contains user certificates.");
            }

            if (var3.onlyContainsCACerts() && (var15 == null || !var15.isCA())) {
               throw new AnnotatedException("End CRL only contains CA certificates.");
            }
         }

         if (var3.onlyContainsAttributeCerts()) {
            throw new AnnotatedException("onlyContainsAttributeCerts boolean is asserted.");
         }
      }

   }

   protected static void processCRLB1(DistributionPoint var0, Object var1, X509CRL var2) throws AnnotatedException {
      ASN1Primitive var3 = CertPathValidatorUtilities.getExtensionValue(var2, ISSUING_DISTRIBUTION_POINT);
      boolean var4 = false;
      if (var3 != null && IssuingDistributionPoint.getInstance(var3).isIndirectCRL()) {
         var4 = true;
      }

      byte[] var5;
      try {
         var5 = PrincipalUtils.getIssuerPrincipal(var2).getEncoded();
      } catch (IOException var11) {
         throw new AnnotatedException("Exception encoding CRL issuer: " + var11.getMessage(), var11);
      }

      boolean var6 = false;
      if (var0.getCRLIssuer() != null) {
         GeneralName[] var7 = var0.getCRLIssuer().getNames();

         for(int var8 = 0; var8 < var7.length; ++var8) {
            if (var7[var8].getTagNo() == 4) {
               try {
                  if (Arrays.areEqual(var7[var8].getName().toASN1Primitive().getEncoded(), var5)) {
                     var6 = true;
                  }
               } catch (IOException var10) {
                  throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", var10);
               }
            }
         }

         if (var6 && !var4) {
            throw new AnnotatedException("Distribution point contains cRLIssuer field but CRL is not indirect.");
         }

         if (!var6) {
            throw new AnnotatedException("CRL issuer of CRL does not match CRL issuer of distribution point.");
         }
      } else if (PrincipalUtils.getIssuerPrincipal(var2).equals(PrincipalUtils.getEncodedIssuerPrincipal(var1))) {
         var6 = true;
      }

      if (!var6) {
         throw new AnnotatedException("Cannot find matching CRL issuer for certificate.");
      }
   }

   protected static ReasonsMask processCRLD(X509CRL var0, DistributionPoint var1) throws AnnotatedException {
      IssuingDistributionPoint var2 = null;

      try {
         var2 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var0, ISSUING_DISTRIBUTION_POINT));
      } catch (Exception var4) {
         throw new AnnotatedException("Issuing distribution point extension could not be decoded.", var4);
      }

      if (var2 != null && var2.getOnlySomeReasons() != null && var1.getReasons() != null) {
         return (new ReasonsMask(var1.getReasons())).intersect(new ReasonsMask(var2.getOnlySomeReasons()));
      } else {
         return (var2 == null || var2.getOnlySomeReasons() == null) && var1.getReasons() == null ? ReasonsMask.allReasons : (var1.getReasons() == null ? ReasonsMask.allReasons : new ReasonsMask(var1.getReasons())).intersect(var2 == null ? ReasonsMask.allReasons : new ReasonsMask(var2.getOnlySomeReasons()));
      }
   }

   protected static Set processCRLF(X509CRL var0, Object var1, X509Certificate var2, PublicKey var3, PKIXExtendedParameters var4, List var5, JcaJceHelper var6) throws AnnotatedException {
      X509CertSelector var7 = new X509CertSelector();

      try {
         byte[] var8 = PrincipalUtils.getIssuerPrincipal(var0).getEncoded();
         var7.setSubject(var8);
      } catch (IOException var23) {
         throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate for CRL could not be set.", var23);
      }

      PKIXCertStoreSelector var24 = (new PKIXCertStoreSelector.Builder(var7)).build();

      Collection var9;
      try {
         var9 = CertPathValidatorUtilities.findCertificates(var24, var4.getCertificateStores());
         var9.addAll(CertPathValidatorUtilities.findCertificates(var24, var4.getCertStores()));
      } catch (AnnotatedException var22) {
         throw new AnnotatedException("Issuer certificate for CRL cannot be searched.", var22);
      }

      var9.add(var2);
      Iterator var10 = var9.iterator();
      ArrayList var11 = new ArrayList();
      ArrayList var12 = new ArrayList();

      while(var10.hasNext()) {
         X509Certificate var13 = (X509Certificate)var10.next();
         if (var13.equals(var2)) {
            var11.add(var13);
            var12.add(var3);
         } else {
            try {
               PKIXCertPathBuilderSpi var14 = new PKIXCertPathBuilderSpi();
               X509CertSelector var15 = new X509CertSelector();
               var15.setCertificate(var13);
               PKIXExtendedParameters.Builder var16 = (new PKIXExtendedParameters.Builder(var4)).setTargetConstraints((new PKIXCertStoreSelector.Builder(var15)).build());
               if (var5.contains(var13)) {
                  var16.setRevocationEnabled(false);
               } else {
                  var16.setRevocationEnabled(true);
               }

               PKIXExtendedBuilderParameters var17 = (new PKIXExtendedBuilderParameters.Builder(var16.build())).build();
               List var18 = var14.engineBuild(var17).getCertPath().getCertificates();
               var11.add(var13);
               var12.add(CertPathValidatorUtilities.getNextWorkingKey(var18, 0, var6));
            } catch (CertPathBuilderException var19) {
               throw new AnnotatedException("CertPath for CRL signer failed to validate.", var19);
            } catch (CertPathValidatorException var20) {
               throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", var20);
            } catch (Exception var21) {
               throw new AnnotatedException(var21.getMessage());
            }
         }
      }

      HashSet var25 = new HashSet();
      AnnotatedException var26 = null;

      for(int var27 = 0; var27 < var11.size(); ++var27) {
         X509Certificate var28 = (X509Certificate)var11.get(var27);
         boolean[] var29 = var28.getKeyUsage();
         if (var29 == null || var29.length >= 7 && var29[6]) {
            var25.add(var12.get(var27));
         } else {
            var26 = new AnnotatedException("Issuer certificate key usage extension does not permit CRL signing.");
         }
      }

      if (var25.isEmpty() && var26 == null) {
         throw new AnnotatedException("Cannot find a valid issuer certificate.");
      } else if (var25.isEmpty() && var26 != null) {
         throw var26;
      } else {
         return var25;
      }
   }

   protected static PublicKey processCRLG(X509CRL var0, Set var1) throws AnnotatedException {
      Exception var2 = null;
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         PublicKey var4 = (PublicKey)var3.next();

         try {
            var0.verify(var4);
            return var4;
         } catch (Exception var6) {
            var2 = var6;
         }
      }

      throw new AnnotatedException("Cannot verify CRL.", var2);
   }

   protected static X509CRL processCRLH(Set var0, PublicKey var1) throws AnnotatedException {
      Exception var2 = null;
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         X509CRL var4 = (X509CRL)var3.next();

         try {
            var4.verify(var1);
            return var4;
         } catch (Exception var6) {
            var2 = var6;
         }
      }

      if (var2 != null) {
         throw new AnnotatedException("Cannot verify delta CRL.", var2);
      } else {
         return null;
      }
   }

   protected static Set processCRLA1i(Date var0, PKIXExtendedParameters var1, X509Certificate var2, X509CRL var3) throws AnnotatedException {
      HashSet var4 = new HashSet();
      if (var1.isUseDeltasEnabled()) {
         CRLDistPoint var5 = null;

         try {
            var5 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var2, FRESHEST_CRL));
         } catch (AnnotatedException var11) {
            throw new AnnotatedException("Freshest CRL extension could not be decoded from certificate.", var11);
         }

         if (var5 == null) {
            try {
               var5 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, FRESHEST_CRL));
            } catch (AnnotatedException var10) {
               throw new AnnotatedException("Freshest CRL extension could not be decoded from CRL.", var10);
            }
         }

         if (var5 != null) {
            ArrayList var6 = new ArrayList();
            var6.addAll(var1.getCRLStores());

            try {
               var6.addAll(CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint(var5, var1.getNamedCRLStoreMap()));
            } catch (AnnotatedException var9) {
               throw new AnnotatedException("No new delta CRL locations could be added from Freshest CRL extension.", var9);
            }

            try {
               var4.addAll(CertPathValidatorUtilities.getDeltaCRLs(var0, var3, var1.getCertStores(), var6));
            } catch (AnnotatedException var8) {
               throw new AnnotatedException("Exception obtaining delta CRLs.", var8);
            }
         }
      }

      return var4;
   }

   protected static Set[] processCRLA1ii(Date var0, PKIXExtendedParameters var1, X509Certificate var2, X509CRL var3) throws AnnotatedException {
      HashSet var4 = new HashSet();
      X509CRLSelector var5 = new X509CRLSelector();
      var5.setCertificateChecking(var2);

      try {
         var5.addIssuerName(PrincipalUtils.getIssuerPrincipal(var3).getEncoded());
      } catch (IOException var11) {
         throw new AnnotatedException("Cannot extract issuer from CRL." + var11, var11);
      }

      PKIXCRLStoreSelector var6 = (new PKIXCRLStoreSelector.Builder(var5)).setCompleteCRLEnabled(true).build();
      Date var7 = var0;
      if (var1.getDate() != null) {
         var7 = var1.getDate();
      }

      Set var8 = CRL_UTIL.findCRLs(var6, var7, var1.getCertStores(), var1.getCRLStores());
      if (var1.isUseDeltasEnabled()) {
         try {
            var4.addAll(CertPathValidatorUtilities.getDeltaCRLs(var7, var3, var1.getCertStores(), var1.getCRLStores()));
         } catch (AnnotatedException var10) {
            throw new AnnotatedException("Exception obtaining delta CRLs.", var10);
         }
      }

      return new Set[]{var8, var4};
   }

   protected static void processCRLC(X509CRL var0, X509CRL var1, PKIXExtendedParameters var2) throws AnnotatedException {
      if (var0 != null) {
         IssuingDistributionPoint var3 = null;

         try {
            var3 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var1, ISSUING_DISTRIBUTION_POINT));
         } catch (Exception var12) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", var12);
         }

         if (var2.isUseDeltasEnabled()) {
            if (!PrincipalUtils.getIssuerPrincipal(var0).equals(PrincipalUtils.getIssuerPrincipal(var1))) {
               throw new AnnotatedException("Complete CRL issuer does not match delta CRL issuer.");
            }

            IssuingDistributionPoint var4 = null;

            try {
               var4 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var0, ISSUING_DISTRIBUTION_POINT));
            } catch (Exception var11) {
               throw new AnnotatedException("Issuing distribution point extension from delta CRL could not be decoded.", var11);
            }

            boolean var5 = false;
            if (var3 == null) {
               if (var4 == null) {
                  var5 = true;
               }
            } else if (var3.equals(var4)) {
               var5 = true;
            }

            if (!var5) {
               throw new AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
            }

            ASN1Primitive var6 = null;

            try {
               var6 = CertPathValidatorUtilities.getExtensionValue(var1, AUTHORITY_KEY_IDENTIFIER);
            } catch (AnnotatedException var10) {
               throw new AnnotatedException("Authority key identifier extension could not be extracted from complete CRL.", var10);
            }

            ASN1Primitive var7 = null;

            try {
               var7 = CertPathValidatorUtilities.getExtensionValue(var0, AUTHORITY_KEY_IDENTIFIER);
            } catch (AnnotatedException var9) {
               throw new AnnotatedException("Authority key identifier extension could not be extracted from delta CRL.", var9);
            }

            if (var6 == null) {
               throw new AnnotatedException("CRL authority key identifier is null.");
            }

            if (var7 == null) {
               throw new AnnotatedException("Delta CRL authority key identifier is null.");
            }

            if (!var6.equals(var7)) {
               throw new AnnotatedException("Delta CRL authority key identifier does not match complete CRL authority key identifier.");
            }
         }

      }
   }

   protected static void processCRLI(Date var0, X509CRL var1, Object var2, CertStatus var3, PKIXExtendedParameters var4) throws AnnotatedException {
      if (var4.isUseDeltasEnabled() && var1 != null) {
         CertPathValidatorUtilities.getCertStatus(var0, var1, var2, var3);
      }

   }

   protected static void processCRLJ(Date var0, X509CRL var1, Object var2, CertStatus var3) throws AnnotatedException {
      if (var3.getCertStatus() == 11) {
         CertPathValidatorUtilities.getCertStatus(var0, var1, var2, var3);
      }

   }

   protected static PKIXPolicyNode prepareCertB(CertPath var0, int var1, List[] var2, PKIXPolicyNode var3, int var4) throws CertPathValidatorException {
      List var5 = var0.getCertificates();
      X509Certificate var6 = (X509Certificate)var5.get(var1);
      int var7 = var5.size();
      int var8 = var7 - var1;
      ASN1Sequence var9 = null;

      try {
         var9 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var6, POLICY_MAPPINGS));
      } catch (AnnotatedException var28) {
         throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", var28, var0, var1);
      }

      PKIXPolicyNode var10 = var3;
      if (var9 != null) {
         ASN1Sequence var11 = var9;
         HashMap var12 = new HashMap();
         HashSet var13 = new HashSet();

         for(int var14 = 0; var14 < var11.size(); ++var14) {
            ASN1Sequence var15 = (ASN1Sequence)var11.getObjectAt(var14);
            String var16 = ((ASN1ObjectIdentifier)var15.getObjectAt(0)).getId();
            String var17 = ((ASN1ObjectIdentifier)var15.getObjectAt(1)).getId();
            if (!var12.containsKey(var16)) {
               HashSet var18 = new HashSet();
               var18.add(var17);
               var12.put(var16, var18);
               var13.add(var16);
            } else {
               Set var35 = (Set)var12.get(var16);
               var35.add(var17);
            }
         }

         Iterator var29 = var13.iterator();

         while(true) {
            while(true) {
               List var20;
               PKIXPolicyNode var22;
               String var30;
               boolean var32;
               Iterator var34;
               PKIXPolicyNode var36;
               label123:
               do {
                  label112:
                  while(var29.hasNext()) {
                     var30 = (String)var29.next();
                     if (var4 > 0) {
                        var32 = false;
                        var34 = var2[var8].iterator();

                        while(var34.hasNext()) {
                           var36 = (PKIXPolicyNode)var34.next();
                           if (var36.getValidPolicy().equals(var30)) {
                              var32 = true;
                              var36.expectedPolicies = (Set)var12.get(var30);
                              continue label123;
                           }
                        }
                        continue label123;
                     }

                     if (var4 <= 0) {
                        Iterator var31 = var2[var8].iterator();

                        while(true) {
                           PKIXPolicyNode var33;
                           do {
                              if (!var31.hasNext()) {
                                 continue label112;
                              }

                              var33 = (PKIXPolicyNode)var31.next();
                           } while(!var33.getValidPolicy().equals(var30));

                           var36 = (PKIXPolicyNode)var33.getParent();
                           var36.removeChild(var33);
                           var31.remove();

                           for(int var19 = var8 - 1; var19 >= 0; --var19) {
                              var20 = var2[var19];

                              for(int var21 = 0; var21 < var20.size(); ++var21) {
                                 var22 = (PKIXPolicyNode)var20.get(var21);
                                 if (!var22.hasChildren()) {
                                    var10 = CertPathValidatorUtilities.removePolicyNode(var10, var2, var22);
                                    if (var10 == null) {
                                       break;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  return var10;
               } while(var32);

               var34 = var2[var8].iterator();

               while(var34.hasNext()) {
                  var36 = (PKIXPolicyNode)var34.next();
                  if ("2.5.29.32.0".equals(var36.getValidPolicy())) {
                     Set var37 = null;
                     var20 = null;

                     ASN1Sequence var38;
                     try {
                        var38 = (ASN1Sequence)CertPathValidatorUtilities.getExtensionValue(var6, CERTIFICATE_POLICIES);
                     } catch (AnnotatedException var27) {
                        throw new ExtCertPathValidatorException("Certificate policies extension could not be decoded.", var27, var0, var1);
                     }

                     Enumeration var39 = var38.getObjects();

                     while(var39.hasMoreElements()) {
                        var22 = null;

                        PolicyInformation var40;
                        try {
                           var40 = PolicyInformation.getInstance(var39.nextElement());
                        } catch (Exception var26) {
                           throw new CertPathValidatorException("Policy information could not be decoded.", var26, var0, var1);
                        }

                        if ("2.5.29.32.0".equals(var40.getPolicyIdentifier().getId())) {
                           try {
                              var37 = CertPathValidatorUtilities.getQualifierSet(var40.getPolicyQualifiers());
                              break;
                           } catch (CertPathValidatorException var25) {
                              throw new ExtCertPathValidatorException("Policy qualifier info set could not be decoded.", var25, var0, var1);
                           }
                        }
                     }

                     boolean var41 = false;
                     if (var6.getCriticalExtensionOIDs() != null) {
                        var41 = var6.getCriticalExtensionOIDs().contains(CERTIFICATE_POLICIES);
                     }

                     PKIXPolicyNode var23 = (PKIXPolicyNode)var36.getParent();
                     if ("2.5.29.32.0".equals(var23.getValidPolicy())) {
                        PKIXPolicyNode var24 = new PKIXPolicyNode(new ArrayList(), var8, (Set)var12.get(var30), var23, var37, var30, var41);
                        var23.addChild(var24);
                        var2[var8].add(var24);
                     }
                     break;
                  }
               }
            }
         }
      } else {
         return var10;
      }
   }

   protected static void prepareNextCertA(CertPath var0, int var1) throws CertPathValidatorException {
      List var2 = var0.getCertificates();
      X509Certificate var3 = (X509Certificate)var2.get(var1);
      ASN1Sequence var4 = null;

      try {
         var4 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, POLICY_MAPPINGS));
      } catch (AnnotatedException var11) {
         throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", var11, var0, var1);
      }

      if (var4 != null) {
         ASN1Sequence var5 = var4;

         for(int var6 = 0; var6 < var5.size(); ++var6) {
            ASN1ObjectIdentifier var7 = null;
            ASN1ObjectIdentifier var8 = null;

            try {
               ASN1Sequence var9 = DERSequence.getInstance(var5.getObjectAt(var6));
               var7 = ASN1ObjectIdentifier.getInstance(var9.getObjectAt(0));
               var8 = ASN1ObjectIdentifier.getInstance(var9.getObjectAt(1));
            } catch (Exception var10) {
               throw new ExtCertPathValidatorException("Policy mappings extension contents could not be decoded.", var10, var0, var1);
            }

            if ("2.5.29.32.0".equals(var7.getId())) {
               throw new CertPathValidatorException("IssuerDomainPolicy is anyPolicy", (Throwable)null, var0, var1);
            }

            if ("2.5.29.32.0".equals(var8.getId())) {
               throw new CertPathValidatorException("SubjectDomainPolicy is anyPolicy,", (Throwable)null, var0, var1);
            }
         }
      }

   }

   protected static void processCertF(CertPath var0, int var1, PKIXPolicyNode var2, int var3) throws CertPathValidatorException {
      if (var3 <= 0 && var2 == null) {
         throw new ExtCertPathValidatorException("No valid policy tree found when one expected.", (Throwable)null, var0, var1);
      }
   }

   protected static PKIXPolicyNode processCertE(CertPath var0, int var1, PKIXPolicyNode var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      ASN1Sequence var5 = null;

      try {
         var5 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var4, CERTIFICATE_POLICIES));
      } catch (AnnotatedException var7) {
         throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", var7, var0, var1);
      }

      if (var5 == null) {
         var2 = null;
      }

      return var2;
   }

   protected static void processCertBC(CertPath var0, int var1, PKIXNameConstraintValidator var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      int var5 = var3.size();
      int var6 = var5 - var1;
      if (!CertPathValidatorUtilities.isSelfIssued(var4) || var6 >= var5) {
         X500Name var7 = PrincipalUtils.getSubjectPrincipal(var4);

         ASN1Sequence var8;
         try {
            var8 = DERSequence.getInstance(var7.getEncoded());
         } catch (Exception var20) {
            throw new CertPathValidatorException("Exception extracting subject name when checking subtrees.", var20, var0, var1);
         }

         try {
            var2.checkPermittedDN(var8);
            var2.checkExcludedDN(var8);
         } catch (PKIXNameConstraintValidatorException var19) {
            throw new CertPathValidatorException("Subtree check for certificate subject failed.", var19, var0, var1);
         }

         GeneralNames var9 = null;

         try {
            var9 = GeneralNames.getInstance(CertPathValidatorUtilities.getExtensionValue(var4, SUBJECT_ALTERNATIVE_NAME));
         } catch (Exception var18) {
            throw new CertPathValidatorException("Subject alternative name extension could not be decoded.", var18, var0, var1);
         }

         RDN[] var10 = X500Name.getInstance(var8).getRDNs(BCStyle.EmailAddress);

         for(int var11 = 0; var11 != var10.length; ++var11) {
            String var12 = ((ASN1String)var10[var11].getFirst().getValue()).getString();
            GeneralName var13 = new GeneralName(1, var12);

            try {
               var2.checkPermitted(var13);
               var2.checkExcluded(var13);
            } catch (PKIXNameConstraintValidatorException var17) {
               throw new CertPathValidatorException("Subtree check for certificate subject alternative email failed.", var17, var0, var1);
            }
         }

         if (var9 != null) {
            GeneralName[] var21 = null;

            try {
               var21 = var9.getNames();
            } catch (Exception var16) {
               throw new CertPathValidatorException("Subject alternative name contents could not be decoded.", var16, var0, var1);
            }

            for(int var22 = 0; var22 < var21.length; ++var22) {
               try {
                  var2.checkPermitted(var21[var22]);
                  var2.checkExcluded(var21[var22]);
               } catch (PKIXNameConstraintValidatorException var15) {
                  throw new CertPathValidatorException("Subtree check for certificate subject alternative name failed.", var15, var0, var1);
               }
            }
         }
      }

   }

   protected static PKIXPolicyNode processCertD(CertPath var0, int var1, Set var2, PKIXPolicyNode var3, List[] var4, int var5) throws CertPathValidatorException {
      List var6 = var0.getCertificates();
      X509Certificate var7 = (X509Certificate)var6.get(var1);
      int var8 = var6.size();
      int var9 = var8 - var1;
      ASN1Sequence var10 = null;

      try {
         var10 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var7, CERTIFICATE_POLICIES));
      } catch (AnnotatedException var26) {
         throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", var26, var0, var1);
      }

      if (var10 != null && var3 != null) {
         Enumeration var11 = var10.getObjects();
         HashSet var12 = new HashSet();

         PolicyInformation var13;
         while(var11.hasMoreElements()) {
            var13 = PolicyInformation.getInstance(var11.nextElement());
            ASN1ObjectIdentifier var14 = var13.getPolicyIdentifier();
            var12.add(var14.getId());
            if (!"2.5.29.32.0".equals(var14.getId())) {
               Set var15 = null;

               try {
                  var15 = CertPathValidatorUtilities.getQualifierSet(var13.getPolicyQualifiers());
               } catch (CertPathValidatorException var25) {
                  throw new ExtCertPathValidatorException("Policy qualifier info set could not be build.", var25, var0, var1);
               }

               boolean var16 = CertPathValidatorUtilities.processCertD1i(var9, var4, var14, var15);
               if (!var16) {
                  CertPathValidatorUtilities.processCertD1ii(var9, var4, var14, var15);
               }
            }
         }

         if (!var2.isEmpty() && !var2.contains("2.5.29.32.0")) {
            Iterator var27 = var2.iterator();
            HashSet var29 = new HashSet();

            while(var27.hasNext()) {
               Object var32 = var27.next();
               if (var12.contains(var32)) {
                  var29.add(var32);
               }
            }

            var2.clear();
            var2.addAll(var29);
         } else {
            var2.clear();
            var2.addAll(var12);
         }

         PKIXPolicyNode var17;
         Set var30;
         List var33;
         int var34;
         if (var5 > 0 || var9 < var8 && CertPathValidatorUtilities.isSelfIssued(var7)) {
            var11 = var10.getObjects();

            label130:
            while(var11.hasMoreElements()) {
               var13 = PolicyInformation.getInstance(var11.nextElement());
               if ("2.5.29.32.0".equals(var13.getPolicyIdentifier().getId())) {
                  var30 = CertPathValidatorUtilities.getQualifierSet(var13.getPolicyQualifiers());
                  var33 = var4[var9 - 1];
                  var34 = 0;

                  label124:
                  while(true) {
                     if (var34 >= var33.size()) {
                        break label130;
                     }

                     var17 = (PKIXPolicyNode)var33.get(var34);
                     Iterator var18 = var17.getExpectedPolicies().iterator();

                     while(true) {
                        String var20;
                        while(true) {
                           if (!var18.hasNext()) {
                              ++var34;
                              continue label124;
                           }

                           Object var19 = var18.next();
                           if (var19 instanceof String) {
                              var20 = (String)var19;
                              break;
                           }

                           if (var19 instanceof ASN1ObjectIdentifier) {
                              var20 = ((ASN1ObjectIdentifier)var19).getId();
                              break;
                           }
                        }

                        boolean var21 = false;
                        Iterator var22 = var17.getChildren();

                        while(var22.hasNext()) {
                           PKIXPolicyNode var23 = (PKIXPolicyNode)var22.next();
                           if (var20.equals(var23.getValidPolicy())) {
                              var21 = true;
                           }
                        }

                        if (!var21) {
                           HashSet var39 = new HashSet();
                           var39.add(var20);
                           PKIXPolicyNode var24 = new PKIXPolicyNode(new ArrayList(), var9, var39, var17, var30, var20, false);
                           var17.addChild(var24);
                           var4[var9].add(var24);
                        }
                     }
                  }
               }
            }
         }

         PKIXPolicyNode var28 = var3;

         for(int var31 = var9 - 1; var31 >= 0; --var31) {
            var33 = var4[var31];

            for(var34 = 0; var34 < var33.size(); ++var34) {
               var17 = (PKIXPolicyNode)var33.get(var34);
               if (!var17.hasChildren()) {
                  var28 = CertPathValidatorUtilities.removePolicyNode(var28, var4, var17);
                  if (var28 == null) {
                     break;
                  }
               }
            }
         }

         var30 = var7.getCriticalExtensionOIDs();
         if (var30 != null) {
            boolean var35 = var30.contains(CERTIFICATE_POLICIES);
            List var36 = var4[var9];

            for(int var37 = 0; var37 < var36.size(); ++var37) {
               PKIXPolicyNode var38 = (PKIXPolicyNode)var36.get(var37);
               var38.setCritical(var35);
            }
         }

         return var28;
      } else {
         return null;
      }
   }

   protected static void processCertA(CertPath var0, PKIXExtendedParameters var1, int var2, PublicKey var3, boolean var4, X500Name var5, X509Certificate var6, JcaJceHelper var7) throws ExtCertPathValidatorException {
      List var8 = var0.getCertificates();
      X509Certificate var9 = (X509Certificate)var8.get(var2);
      if (!var4) {
         try {
            CertPathValidatorUtilities.verifyX509Certificate(var9, var3, var1.getSigProvider());
         } catch (GeneralSecurityException var15) {
            throw new ExtCertPathValidatorException("Could not validate certificate signature.", var15, var0, var2);
         }
      }

      try {
         var9.checkValidity(CertPathValidatorUtilities.getValidCertDateFromValidityModel(var1, var0, var2));
      } catch (CertificateExpiredException var12) {
         throw new ExtCertPathValidatorException("Could not validate certificate: " + var12.getMessage(), var12, var0, var2);
      } catch (CertificateNotYetValidException var13) {
         throw new ExtCertPathValidatorException("Could not validate certificate: " + var13.getMessage(), var13, var0, var2);
      } catch (AnnotatedException var14) {
         throw new ExtCertPathValidatorException("Could not validate time of certificate.", var14, var0, var2);
      }

      if (var1.isRevocationEnabled()) {
         try {
            checkCRLs(var1, var9, CertPathValidatorUtilities.getValidCertDateFromValidityModel(var1, var0, var2), var6, var3, var8, var7);
         } catch (AnnotatedException var16) {
            Object var11 = var16;
            if (null != var16.getCause()) {
               var11 = var16.getCause();
            }

            throw new ExtCertPathValidatorException(var16.getMessage(), (Throwable)var11, var0, var2);
         }
      }

      if (!PrincipalUtils.getEncodedIssuerPrincipal(var9).equals(var5)) {
         throw new ExtCertPathValidatorException("IssuerName(" + PrincipalUtils.getEncodedIssuerPrincipal(var9) + ") does not match SubjectName(" + var5 + ") of signing certificate.", (Throwable)null, var0, var2);
      }
   }

   protected static int prepareNextCertI1(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      ASN1Sequence var5 = null;

      try {
         var5 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var4, POLICY_CONSTRAINTS));
      } catch (Exception var9) {
         throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", var9, var0, var1);
      }

      if (var5 != null) {
         Enumeration var7 = var5.getObjects();

         while(var7.hasMoreElements()) {
            try {
               ASN1TaggedObject var8 = ASN1TaggedObject.getInstance(var7.nextElement());
               if (var8.getTagNo() == 0) {
                  int var6 = ASN1Integer.getInstance(var8, false).getValue().intValue();
                  if (var6 < var2) {
                     return var6;
                  }
                  break;
               }
            } catch (IllegalArgumentException var10) {
               throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", var10, var0, var1);
            }
         }
      }

      return var2;
   }

   protected static int prepareNextCertI2(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      ASN1Sequence var5 = null;

      try {
         var5 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var4, POLICY_CONSTRAINTS));
      } catch (Exception var9) {
         throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", var9, var0, var1);
      }

      if (var5 != null) {
         Enumeration var7 = var5.getObjects();

         while(var7.hasMoreElements()) {
            try {
               ASN1TaggedObject var8 = ASN1TaggedObject.getInstance(var7.nextElement());
               if (var8.getTagNo() == 1) {
                  int var6 = ASN1Integer.getInstance(var8, false).getValue().intValue();
                  if (var6 < var2) {
                     return var6;
                  }
                  break;
               }
            } catch (IllegalArgumentException var10) {
               throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", var10, var0, var1);
            }
         }
      }

      return var2;
   }

   protected static void prepareNextCertG(CertPath var0, int var1, PKIXNameConstraintValidator var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      NameConstraints var5 = null;

      try {
         ASN1Sequence var6 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var4, NAME_CONSTRAINTS));
         if (var6 != null) {
            var5 = NameConstraints.getInstance(var6);
         }
      } catch (Exception var12) {
         throw new ExtCertPathValidatorException("Name constraints extension could not be decoded.", var12, var0, var1);
      }

      if (var5 != null) {
         GeneralSubtree[] var13 = var5.getPermittedSubtrees();
         if (var13 != null) {
            try {
               var2.intersectPermittedSubtree(var13);
            } catch (Exception var11) {
               throw new ExtCertPathValidatorException("Permitted subtrees cannot be build from name constraints extension.", var11, var0, var1);
            }
         }

         GeneralSubtree[] var7 = var5.getExcludedSubtrees();
         if (var7 != null) {
            for(int var8 = 0; var8 != var7.length; ++var8) {
               try {
                  var2.addExcludedSubtree(var7[var8]);
               } catch (Exception var10) {
                  throw new ExtCertPathValidatorException("Excluded subtrees cannot be build from name constraints extension.", var10, var0, var1);
               }
            }
         }
      }

   }

   private static void checkCRL(DistributionPoint var0, PKIXExtendedParameters var1, X509Certificate var2, Date var3, X509Certificate var4, PublicKey var5, CertStatus var6, ReasonsMask var7, List var8, JcaJceHelper var9) throws AnnotatedException {
      Date var10 = new Date(System.currentTimeMillis());
      if (var3.getTime() > var10.getTime()) {
         throw new AnnotatedException("Validation time is in future.");
      } else {
         Set var11 = CertPathValidatorUtilities.getCompleteCRLs(var0, var2, var10, var1);
         boolean var12 = false;
         AnnotatedException var13 = null;
         Iterator var14 = var11.iterator();

         while(var14.hasNext() && var6.getCertStatus() == 11 && !var7.isAllReasons()) {
            try {
               X509CRL var15 = (X509CRL)var14.next();
               ReasonsMask var16 = processCRLD(var15, var0);
               if (var16.hasNewReasons(var7)) {
                  Set var17 = processCRLF(var15, var2, var4, var5, var1, var8, var9);
                  PublicKey var18 = processCRLG(var15, var17);
                  X509CRL var19 = null;
                  Date var20 = var10;
                  if (var1.getDate() != null) {
                     var20 = var1.getDate();
                  }

                  Set var21;
                  if (var1.isUseDeltasEnabled()) {
                     var21 = CertPathValidatorUtilities.getDeltaCRLs(var20, var15, var1.getCertStores(), var1.getCRLStores());
                     var19 = processCRLH(var21, var18);
                  }

                  if (var1.getValidityModel() != 1 && var2.getNotAfter().getTime() < var15.getThisUpdate().getTime()) {
                     throw new AnnotatedException("No valid CRL for current time found.");
                  }

                  processCRLB1(var0, var2, var15);
                  processCRLB2(var0, var2, var15);
                  processCRLC(var19, var15, var1);
                  processCRLI(var3, var19, var2, var6, var1);
                  processCRLJ(var3, var15, var2, var6);
                  if (var6.getCertStatus() == 8) {
                     var6.setCertStatus(11);
                  }

                  var7.addReasons(var16);
                  var21 = var15.getCriticalExtensionOIDs();
                  HashSet var23;
                  if (var21 != null) {
                     var23 = new HashSet(var21);
                     var23.remove(Extension.issuingDistributionPoint.getId());
                     var23.remove(Extension.deltaCRLIndicator.getId());
                     if (!var23.isEmpty()) {
                        throw new AnnotatedException("CRL contains unsupported critical extensions.");
                     }
                  }

                  if (var19 != null) {
                     var21 = var19.getCriticalExtensionOIDs();
                     if (var21 != null) {
                        var23 = new HashSet(var21);
                        var23.remove(Extension.issuingDistributionPoint.getId());
                        var23.remove(Extension.deltaCRLIndicator.getId());
                        if (!var23.isEmpty()) {
                           throw new AnnotatedException("Delta CRL contains unsupported critical extension.");
                        }
                     }
                  }

                  var12 = true;
               }
            } catch (AnnotatedException var22) {
               var13 = var22;
            }
         }

         if (!var12) {
            throw var13;
         }
      }
   }

   protected static void checkCRLs(PKIXExtendedParameters var0, X509Certificate var1, Date var2, X509Certificate var3, PublicKey var4, List var5, JcaJceHelper var6) throws AnnotatedException {
      AnnotatedException var7 = null;
      CRLDistPoint var8 = null;

      try {
         var8 = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(var1, CRL_DISTRIBUTION_POINTS));
      } catch (Exception var21) {
         throw new AnnotatedException("CRL distribution point extension could not be read.", var21);
      }

      PKIXExtendedParameters.Builder var9 = new PKIXExtendedParameters.Builder(var0);

      try {
         List var10 = CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint(var8, var0.getNamedCRLStoreMap());
         Iterator var11 = var10.iterator();

         while(var11.hasNext()) {
            var9.addCRLStore((PKIXCRLStore)var11.next());
         }
      } catch (AnnotatedException var22) {
         throw new AnnotatedException("No additional CRL locations could be decoded from CRL distribution point extension.", var22);
      }

      CertStatus var23 = new CertStatus();
      ReasonsMask var24 = new ReasonsMask();
      PKIXExtendedParameters var12 = var9.build();
      boolean var13 = false;
      DistributionPoint[] var14;
      if (var8 != null) {
         var14 = null;

         try {
            var14 = var8.getDistributionPoints();
         } catch (Exception var20) {
            throw new AnnotatedException("Distribution points could not be read.", var20);
         }

         if (var14 != null) {
            for(int var15 = 0; var15 < var14.length && var23.getCertStatus() == 11 && !var24.isAllReasons(); ++var15) {
               try {
                  checkCRL(var14[var15], var12, var1, var2, var3, var4, var23, var24, var5, var6);
                  var13 = true;
               } catch (AnnotatedException var19) {
                  var7 = var19;
               }
            }
         }
      }

      if (var23.getCertStatus() == 11 && !var24.isAllReasons()) {
         try {
            var14 = null;

            ASN1Primitive var25;
            try {
               var25 = (new ASN1InputStream(PrincipalUtils.getEncodedIssuerPrincipal(var1).getEncoded())).readObject();
            } catch (Exception var17) {
               throw new AnnotatedException("Issuer from certificate for CRL could not be reencoded.", var17);
            }

            DistributionPoint var27 = new DistributionPoint(new DistributionPointName(0, new GeneralNames(new GeneralName(4, var25))), (ReasonFlags)null, (GeneralNames)null);
            PKIXExtendedParameters var16 = (PKIXExtendedParameters)var0.clone();
            checkCRL(var27, var16, var1, var2, var3, var4, var23, var24, var5, var6);
            var13 = true;
         } catch (AnnotatedException var18) {
            var7 = var18;
         }
      }

      if (!var13) {
         if (var7 instanceof AnnotatedException) {
            throw var7;
         } else {
            throw new AnnotatedException("No valid CRL found.", var7);
         }
      } else if (var23.getCertStatus() != 11) {
         SimpleDateFormat var26 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
         var26.setTimeZone(TimeZone.getTimeZone("UTC"));
         String var28 = "Certificate revocation after " + var26.format(var23.getRevocationDate());
         var28 = var28 + ", reason: " + crlReasons[var23.getCertStatus()];
         throw new AnnotatedException(var28);
      } else {
         if (!var24.isAllReasons() && var23.getCertStatus() == 11) {
            var23.setCertStatus(12);
         }

         if (var23.getCertStatus() == 12) {
            throw new AnnotatedException("Certificate status could not be determined.");
         }
      }
   }

   protected static int prepareNextCertJ(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      ASN1Integer var5 = null;

      try {
         var5 = ASN1Integer.getInstance(CertPathValidatorUtilities.getExtensionValue(var4, INHIBIT_ANY_POLICY));
      } catch (Exception var7) {
         throw new ExtCertPathValidatorException("Inhibit any-policy extension cannot be decoded.", var7, var0, var1);
      }

      if (var5 != null) {
         int var6 = var5.getValue().intValue();
         if (var6 < var2) {
            return var6;
         }
      }

      return var2;
   }

   protected static void prepareNextCertK(CertPath var0, int var1) throws CertPathValidatorException {
      List var2 = var0.getCertificates();
      X509Certificate var3 = (X509Certificate)var2.get(var1);
      BasicConstraints var4 = null;

      try {
         var4 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(var3, BASIC_CONSTRAINTS));
      } catch (Exception var6) {
         throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", var6, var0, var1);
      }

      if (var4 != null) {
         if (!var4.isCA()) {
            throw new CertPathValidatorException("Not a CA certificate");
         }
      } else {
         throw new CertPathValidatorException("Intermediate certificate lacks BasicConstraints");
      }
   }

   protected static int prepareNextCertL(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      if (!CertPathValidatorUtilities.isSelfIssued(var4)) {
         if (var2 <= 0) {
            throw new ExtCertPathValidatorException("Max path length not greater than zero", (Throwable)null, var0, var1);
         } else {
            return var2 - 1;
         }
      } else {
         return var2;
      }
   }

   protected static int prepareNextCertM(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      BasicConstraints var5 = null;

      try {
         var5 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(var4, BASIC_CONSTRAINTS));
      } catch (Exception var8) {
         throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", var8, var0, var1);
      }

      if (var5 != null) {
         BigInteger var6 = var5.getPathLenConstraint();
         if (var6 != null) {
            int var7 = var6.intValue();
            if (var7 < var2) {
               return var7;
            }
         }
      }

      return var2;
   }

   protected static void prepareNextCertN(CertPath var0, int var1) throws CertPathValidatorException {
      List var2 = var0.getCertificates();
      X509Certificate var3 = (X509Certificate)var2.get(var1);
      boolean[] var4 = var3.getKeyUsage();
      if (var4 != null && !var4[5]) {
         throw new ExtCertPathValidatorException("Issuer certificate keyusage extension is critical and does not permit key signing.", (Throwable)null, var0, var1);
      }
   }

   protected static void prepareNextCertO(CertPath var0, int var1, Set var2, List var3) throws CertPathValidatorException {
      List var4 = var0.getCertificates();
      X509Certificate var5 = (X509Certificate)var4.get(var1);
      Iterator var6 = var3.iterator();

      while(var6.hasNext()) {
         try {
            ((PKIXCertPathChecker)var6.next()).check(var5, var2);
         } catch (CertPathValidatorException var8) {
            throw new CertPathValidatorException(var8.getMessage(), var8.getCause(), var0, var1);
         }
      }

      if (!var2.isEmpty()) {
         throw new ExtCertPathValidatorException("Certificate has unsupported critical extension: " + var2, (Throwable)null, var0, var1);
      }
   }

   protected static int prepareNextCertH1(CertPath var0, int var1, int var2) {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      return !CertPathValidatorUtilities.isSelfIssued(var4) && var2 != 0 ? var2 - 1 : var2;
   }

   protected static int prepareNextCertH2(CertPath var0, int var1, int var2) {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      return !CertPathValidatorUtilities.isSelfIssued(var4) && var2 != 0 ? var2 - 1 : var2;
   }

   protected static int prepareNextCertH3(CertPath var0, int var1, int var2) {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      return !CertPathValidatorUtilities.isSelfIssued(var4) && var2 != 0 ? var2 - 1 : var2;
   }

   protected static int wrapupCertA(int var0, X509Certificate var1) {
      if (!CertPathValidatorUtilities.isSelfIssued(var1) && var0 != 0) {
         --var0;
      }

      return var0;
   }

   protected static int wrapupCertB(CertPath var0, int var1, int var2) throws CertPathValidatorException {
      List var3 = var0.getCertificates();
      X509Certificate var4 = (X509Certificate)var3.get(var1);
      ASN1Sequence var5 = null;

      try {
         var5 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(var4, POLICY_CONSTRAINTS));
      } catch (AnnotatedException var11) {
         throw new ExtCertPathValidatorException("Policy constraints could not be decoded.", var11, var0, var1);
      }

      if (var5 != null) {
         Enumeration var6 = var5.getObjects();

         while(var6.hasMoreElements()) {
            ASN1TaggedObject var7 = (ASN1TaggedObject)var6.nextElement();
            switch (var7.getTagNo()) {
               case 0:
                  int var8;
                  try {
                     var8 = ASN1Integer.getInstance(var7, false).getValue().intValue();
                  } catch (Exception var10) {
                     throw new ExtCertPathValidatorException("Policy constraints requireExplicitPolicy field could not be decoded.", var10, var0, var1);
                  }

                  if (var8 == 0) {
                     return 0;
                  }
            }
         }
      }

      return var2;
   }

   protected static void wrapupCertF(CertPath var0, int var1, List var2, Set var3) throws CertPathValidatorException {
      List var4 = var0.getCertificates();
      X509Certificate var5 = (X509Certificate)var4.get(var1);
      Iterator var6 = var2.iterator();

      while(var6.hasNext()) {
         try {
            ((PKIXCertPathChecker)var6.next()).check(var5, var3);
         } catch (CertPathValidatorException var8) {
            throw new ExtCertPathValidatorException("Additional certificate path checker failed.", var8, var0, var1);
         }
      }

      if (!var3.isEmpty()) {
         throw new ExtCertPathValidatorException("Certificate has unsupported critical extension: " + var3, (Throwable)null, var0, var1);
      }
   }

   protected static PKIXPolicyNode wrapupCertG(CertPath var0, PKIXExtendedParameters var1, Set var2, int var3, List[] var4, PKIXPolicyNode var5, Set var6) throws CertPathValidatorException {
      int var7 = var0.getCertificates().size();
      PKIXPolicyNode var8;
      if (var5 == null) {
         if (var1.isExplicitPolicyRequired()) {
            throw new ExtCertPathValidatorException("Explicit policy requested but none available.", (Throwable)null, var0, var3);
         }

         var8 = null;
      } else {
         HashSet var9;
         int var10;
         List var11;
         int var12;
         PKIXPolicyNode var13;
         Iterator var14;
         Iterator var16;
         PKIXPolicyNode var17;
         int var18;
         String var19;
         List var20;
         int var21;
         PKIXPolicyNode var22;
         if (CertPathValidatorUtilities.isAnyPolicy(var2)) {
            if (var1.isExplicitPolicyRequired()) {
               if (var6.isEmpty()) {
                  throw new ExtCertPathValidatorException("Explicit policy requested but none available.", (Throwable)null, var0, var3);
               }

               var9 = new HashSet();
               var10 = 0;

               label152:
               while(true) {
                  if (var10 >= var4.length) {
                     var16 = var9.iterator();

                     while(var16.hasNext()) {
                        var17 = (PKIXPolicyNode)var16.next();
                        var19 = var17.getValidPolicy();
                        if (!var6.contains(var19)) {
                        }
                     }

                     if (var5 == null) {
                        break;
                     }

                     var18 = var7 - 1;

                     while(true) {
                        if (var18 < 0) {
                           break label152;
                        }

                        var20 = var4[var18];

                        for(var21 = 0; var21 < var20.size(); ++var21) {
                           var22 = (PKIXPolicyNode)var20.get(var21);
                           if (!var22.hasChildren()) {
                              var5 = CertPathValidatorUtilities.removePolicyNode(var5, var4, var22);
                           }
                        }

                        --var18;
                     }
                  }

                  var11 = var4[var10];

                  for(var12 = 0; var12 < var11.size(); ++var12) {
                     var13 = (PKIXPolicyNode)var11.get(var12);
                     if ("2.5.29.32.0".equals(var13.getValidPolicy())) {
                        var14 = var13.getChildren();

                        while(var14.hasNext()) {
                           var9.add(var14.next());
                        }
                     }
                  }

                  ++var10;
               }
            }

            var8 = var5;
         } else {
            var9 = new HashSet();

            for(var10 = 0; var10 < var4.length; ++var10) {
               var11 = var4[var10];

               for(var12 = 0; var12 < var11.size(); ++var12) {
                  var13 = (PKIXPolicyNode)var11.get(var12);
                  if ("2.5.29.32.0".equals(var13.getValidPolicy())) {
                     var14 = var13.getChildren();

                     while(var14.hasNext()) {
                        PKIXPolicyNode var15 = (PKIXPolicyNode)var14.next();
                        if (!"2.5.29.32.0".equals(var15.getValidPolicy())) {
                           var9.add(var15);
                        }
                     }
                  }
               }
            }

            var16 = var9.iterator();

            while(var16.hasNext()) {
               var17 = (PKIXPolicyNode)var16.next();
               var19 = var17.getValidPolicy();
               if (!var2.contains(var19)) {
                  var5 = CertPathValidatorUtilities.removePolicyNode(var5, var4, var17);
               }
            }

            if (var5 != null) {
               for(var18 = var7 - 1; var18 >= 0; --var18) {
                  var20 = var4[var18];

                  for(var21 = 0; var21 < var20.size(); ++var21) {
                     var22 = (PKIXPolicyNode)var20.get(var21);
                     if (!var22.hasChildren()) {
                        var5 = CertPathValidatorUtilities.removePolicyNode(var5, var4, var22);
                     }
                  }
               }
            }

            var8 = var5;
         }
      }

      return var8;
   }

   static {
      CERTIFICATE_POLICIES = Extension.certificatePolicies.getId();
      POLICY_MAPPINGS = Extension.policyMappings.getId();
      INHIBIT_ANY_POLICY = Extension.inhibitAnyPolicy.getId();
      ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
      FRESHEST_CRL = Extension.freshestCRL.getId();
      DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
      POLICY_CONSTRAINTS = Extension.policyConstraints.getId();
      BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
      CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
      SUBJECT_ALTERNATIVE_NAME = Extension.subjectAlternativeName.getId();
      NAME_CONSTRAINTS = Extension.nameConstraints.getId();
      AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();
      KEY_USAGE = Extension.keyUsage.getId();
      CRL_NUMBER = Extension.cRLNumber.getId();
      crlReasons = new String[]{"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
   }
}
