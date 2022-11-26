package com.rsa.certj.provider.pki;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.PKCS10CertRequest;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.attributes.V3ExtensionAttribute;
import com.rsa.certj.cert.attributes.VeriSignCRSFailureInfo;
import com.rsa.certj.cert.attributes.VeriSignCRSMessageType;
import com.rsa.certj.cert.attributes.VeriSignCRSPKIStatus;
import com.rsa.certj.cert.attributes.VeriSignCRSRecipientNonce;
import com.rsa.certj.cert.attributes.VeriSignCRSSenderNonce;
import com.rsa.certj.cert.attributes.VeriSignCRSTransactionID;
import com.rsa.certj.cert.attributes.VeriSignCRSVersion;
import com.rsa.certj.cert.attributes.X501Attribute;
import com.rsa.certj.pkcs7.ContentInfo;
import com.rsa.certj.pkcs7.Data;
import com.rsa.certj.pkcs7.EnvelopedData;
import com.rsa.certj.pkcs7.PKCS7Exception;
import com.rsa.certj.pkcs7.RecipientInfo;
import com.rsa.certj.pkcs7.SignedData;
import com.rsa.certj.pkcs7.SignerInfo;
import com.rsa.certj.provider.TransportImplementation;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIInterface;
import com.rsa.certj.spi.pki.PKIMessage;
import com.rsa.certj.spi.pki.PKIRequestMessage;
import com.rsa.certj.spi.pki.PKIResponseMessage;
import com.rsa.certj.spi.pki.PKIResult;
import com.rsa.certj.spi.pki.PKIStatusInfo;
import com.rsa.certj.spi.pki.PKITransportException;
import com.rsa.certj.spi.pki.POPGenerationInfo;
import com.rsa.certj.spi.pki.POPValidationInfo;
import com.rsa.certj.spi.pki.ProtectInfo;
import com.rsa.certj.spi.pki.ProtectInfoPublicKey;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MessageDigest;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_Recode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

/** @deprecated */
public class CRS extends Provider implements PKIDebug {
   /** @deprecated */
   public static final int POP_TYPE_CSR = 1;
   private static final int PKCS7_SIGNED_DATA_VERSION = 1;
   private static final int PKCS7_ENVELOPED_DATA_VERSION = 0;
   private static final String CSR_HEADER = "-----BEGIN NEW CERTIFICATE REQUEST-----";
   private static final String CSR_FOOTER = "-----END NEW CERTIFICATE REQUEST-----";
   private static final String MIME_TYPE_CRS_REQ = "application/octet-stream";
   private static final String MIME_TYPE_CRS_RES = "application/x-crs-message";
   private static final int CRS_VERSION_SUPPORTED = 0;
   private static final int CRS_MSGTYPE_CERTREQ = 19;
   private static final int CRS_MSGTYPE_GETCERTINITIAL = 20;
   private static final int CRS_MSGTYPE_CERTRES = 3;
   private static final String PKI_CRS_PROFILE_NAME_VERISIGN = "VeriSign";
   private static final int FAILINFO_COUNT = 76;
   private static final int[] FAILINFO_VALUE = new int[]{256, 261, 262, 263, 264, 266, 268, 269, 272, 276, 277, 288, 293, 294, 296, 298, 306, 307, 316, 317, 325, 326, 327, 328, 330, 4101, 12353, 12359, 12380, 12384, 12385, 12393, 12420, 14593, 14594, 14595, 14596, 14597, 14598, 14599, 14600, 14601, 14602, 14603, 14604, 14605, 14606, 14607, 14608, 14609, 14610, 14611, 14612, 14613, 14614, 14615, 14617, 14619, 14620, 14623, 14624, 14625, 14626, 14627, 14628, 14629, 14630, 14631, 14632, 14633, 14634, 14639, 14640, 14641, 14642, 14643};
   private static final int[] FAILINFO_FLAG = new int[]{2097152, 67108864, 67108864, 67108864, 67108864, 67108864, 1073741824, 67108864, 67108864, Integer.MIN_VALUE, Integer.MIN_VALUE, 1048576, Integer.MIN_VALUE, Integer.MIN_VALUE, 536870912, 1073741824, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, 67108864, 67108864, 67108864, 67108864, 67108864, 134217728, 1048576, 1048576, 1048576, 134217728, 134217728, 16777216, 16777216, 67108864, 67108864, 67108864, 67108864, 1073741824, 1073741824, 67108864, 67108864, 67108864, 67108864, 67108864, 67108864, 67108864, 1048576, 1048576, 1048576, 1048576, 1048576, 1048576, 67108864, 67108864, 67108864, 67108864, 67108864, 134217728, 67108864, 134217728, 134217728, 67108864, 1048576, 67108864, 67108864, 67108864, 67108864, 67108864, 67108864, 67108864, 134217728, 67108864, 67108864, 67108864, 67108864, 134217728};
   private static final int NONCE_LEN = 16;
   private Hashtable configProperties;

   /** @deprecated */
   public CRS(String var1, InputStream var2) throws InvalidParameterException {
      super(4, var1);
      if (var2 == null) {
         throw new InvalidParameterException("CRS.CRS: configStream should not be null.");
      } else {
         this.configProperties = PKICommonImplementation.loadProperties(var2);
      }
   }

   /** @deprecated */
   public CRS(String var1, File var2) throws InvalidParameterException {
      super(4, var1);
      if (var2 == null) {
         throw new InvalidParameterException("CRS.CRS: configFile should not be null.");
      } else {
         FileInputStream var3 = null;

         try {
            var3 = new FileInputStream(var2);
            this.configProperties = PKICommonImplementation.loadProperties(var3);
         } catch (FileNotFoundException var12) {
            throw new InvalidParameterException("CRS.CRS: " + var2.toString() + " does not exist.");
         } finally {
            if (var3 != null) {
               try {
                  var3.close();
               } catch (IOException var11) {
               }
            }

         }

      }
   }

   /** @deprecated */
   public CRS(String var1, String var2) throws InvalidParameterException {
      super(4, var1);
      if (var2 == null) {
         throw new InvalidParameterException("CRS.CRS: configFileName should not be null.");
      } else {
         FileInputStream var3 = null;

         try {
            var3 = new FileInputStream(new File(var2));
            this.configProperties = PKICommonImplementation.loadProperties(var3);
         } catch (FileNotFoundException var12) {
            throw new InvalidParameterException("CRS.CRS: " + var2 + " does not exist.");
         } finally {
            if (var3 != null) {
               try {
                  var3.close();
               } catch (IOException var11) {
               }
            }

         }

      }
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new a(var1, this.getName());
      } catch (Exception var3) {
         throw new ProviderManagementException("CRS.instantiate: ", var3);
      }
   }

   /** @deprecated */
   public void saveMessage(byte[] var1, PKIMessage var2, ProtectInfo var3) throws PKIException {
   }

   /** @deprecated */
   public void saveCertificate(PKIResponseMessage var1) throws PKIException {
   }

   /** @deprecated */
   public void saveData(byte[] var1, String var2) throws PKIException {
   }

   class c {
      byte[] a;
      byte[] b;

      c(byte[] var2, byte[] var3) {
         this.b = var2;
         this.a = var3;
      }
   }

   class b {
      byte[] a;
      byte[] b;
      X509Certificate c;
      JSAFE_PrivateKey d;
      boolean e;
   }

   private final class a extends PKICommonImplementation implements PKIInterface {
      private a(CertJ var2, String var3) throws InvalidParameterException, PKIException {
         super(var2, var3);
         this.loadConfig(CRS.this.configProperties);
         if (this.profile == null) {
            this.profile = "VeriSign";
         }

      }

      /** @deprecated */
      public PKIResponseMessage readCertificationResponseMessage(byte[] var1, ProtectInfo var2) throws NotSupportedException, PKIException {
         if (!(var2 instanceof ProtectInfoPublicKey)) {
            throw new PKIException("CRS$Implementation.readCertificationResponseMessage:protectInfo should be an instance of ProtectInfoPublicKey.");
         } else {
            ProtectInfoPublicKey var3 = (ProtectInfoPublicKey)var2;
            SignedData var4 = new SignedData(this.certJ, var3.getCertPathCtx());

            boolean var5;
            try {
               var5 = var4.readInit(var1, 0, var1.length);
               if (var5) {
                  var5 = var4.readFinal();
               }
            } catch (Exception var8) {
               if (var1[0] == 48) {
                  throw new PKIException("CRS$Implementation.readCertificationResponseMessage:decoding response or signature verification failed.", var8);
               }

               String[] var7 = new String[]{new String(var1)};
               throw new PKITransportException("CRS$Implementation.readCertificationResponseMessage:error returned in HTML. See the value returned by thisException.getStatusInfo().getStatusStrings[0] for the contents of the HTML response." + new String(var1) + ").", new PKIStatusInfo(2, 2097152, var7, -1));
            }

            if (!var5) {
               throw new PKIException("CRS$Implementation.readCertificationResponseMessage: Decoding of SignedData failed.");
            } else {
               return this.a(var4, var3);
            }
         }
      }

      /** @deprecated */
      public byte[] writeCertificationRequestMessage(PKIRequestMessage var1, ProtectInfo var2) throws NotSupportedException, PKIException {
         if (!(var2 instanceof ProtectInfoPublicKey)) {
            throw new PKIException("CRS$Implementation.writeCertificationRequestMessage: ProtectInfo should be an instance of ProtectInfoPublicKey.");
         } else if (var1.getRegInfo() == null) {
            throw new PKIException("CRS$Implementation.writeCertificationRequestMessage: RegInfo of the request message should not be null.");
         } else if (var1.getSender() == null) {
            throw new PKIException("CRS$Implementation.writeCertificationRequestMessage: Sender information of the request message should not be null.");
         } else {
            b var3 = (b)var1.getProviderData();
            if (var3 != null && var3.b != null && var3.b.length != 0) {
               boolean var4 = var3.e;
               int var5 = var1.getWrapType();
               if (var5 != 1 && var5 != 4) {
                  throw new PKIException("CRS$Implementation.writeCertificationRequestMessage: Only WRAP_SIGN or WRAP_ENVELOPE_THEN_SIGN is supported.");
               } else {
                  if (var1.getVersion() == -1) {
                     var1.setVersion(0);
                  } else if (var1.getVersion() != 0) {
                     throw new PKIException("CRS$Implementation.writeCertificationRequestMessage: CRS version (" + var1.getVersion() + ") is not the same as supported version(" + 0 + ").");
                  }

                  ProtectInfoPublicKey var6 = (ProtectInfoPublicKey)var2;

                  Object var8;
                  byte[] var9;
                  try {
                     if (var4) {
                        var9 = this.a(var1, var6.getCertPathCtx());
                     } else {
                        var9 = var3.b;
                     }

                     byte[] var10 = this.a(var9, var1.getRegInfo());
                     Data var7 = new Data();
                     var7.setContent(var10, 0, var10.length);
                     if (var5 == 4) {
                        if (var1.getRecipient() == null) {
                           throw new PKIException("Implementation.writeCertificationRequestMessage: Recipent info of the request message should not be null.");
                        }

                        var8 = this.a(var6.getCertPathCtx(), var1, var7);
                     } else {
                        var8 = var7;
                     }

                     X501Attributes var11 = var1.getSender().getAuthenticatedAttrs();
                     if (var11 == null) {
                        var11 = new X501Attributes();
                     }

                     if (var11.getAttributeByType(12) == null) {
                        var11.addAttribute(new VeriSignCRSVersion(var1.getVersion()));
                     }

                     if (var11.getAttributeByType(6) == null) {
                        if (var3.e) {
                           var11.addAttribute(new VeriSignCRSMessageType(20));
                        } else {
                           var11.addAttribute(new VeriSignCRSMessageType(19));
                        }
                     }

                     if (var11.getAttributeByType(11) == null) {
                        if (var1.getTransactionID() == null) {
                           var1.setTransactionID(this.a(var7));
                        }

                        var11.addAttribute(new VeriSignCRSTransactionID(var1.getTransactionID(), 0, var1.getTransactionID().length));
                     }

                     if (var11.getAttributeByType(9) == null) {
                        if (var3.a == null) {
                           var3.a = this.a();
                        }

                        var11.addAttribute(new VeriSignCRSSenderNonce(var3.a, 0, var3.a.length));
                     }

                     var1.getSender().setAuthenticatedAttrs(var11);
                  } catch (PKCS7Exception var12) {
                     throw new PKIException("CRS$Implementation.writeCertificationRequestMessage: ", var12);
                  }

                  var9 = this.a(var6.getCertPathCtx(), var1, (ContentInfo)var8);
                  var1.setMessageTime(new Date());
                  return var9;
               }
            } else {
               throw new PKIException("CRS$Implementation.writeCertificationRequestMessage: POP of the request message should not be null.Use generateProofOfPossession to set it.");
            }
         }
      }

      public PKIResponseMessage sendRequest(PKIRequestMessage var1, ProtectInfo var2, DatabaseService var3) throws NotSupportedException, PKIException {
         byte[] var4 = this.writeCertificationRequestMessage(var1, var2);
         CRS.this.saveMessage(var4, var1, var2);
         PKIResult var5 = this.sendMessage(var4);
         PKIStatusInfo var6 = var5.getStatusInfo();
         if (var6.getStatus() != 0) {
            return new PKIResponseMessage(var6);
         } else {
            byte[] var7 = var5.getEncodedResponse();
            CRS.this.saveData(var7, "RespTemp.ber");
            PKIResponseMessage var8 = this.readCertificationResponseMessage(var7, var2);
            CRS.this.saveMessage(var7, var8, var2);
            var6 = var8.getStatusInfo();
            int var9 = var6.getStatus();
            if (var9 != 0 && var9 != 3 && (var6.getFailInfo() & 2097152) != 0) {
               return var8;
            } else {
               if (var9 == 0 || var9 == 3) {
                  if (!CertJUtils.byteArraysEqual(var1.getTransactionID(), var8.getTransactionID())) {
                     throw new PKIException("CRS$Implementation.sendRequest: Transaction IDs do not match.");
                  }

                  if (!this.a(var1, var8)) {
                     throw new PKIException("CRS$Implementation.sendRequest: Nonces do not match.");
                  }
               }

               if (var9 == 3) {
                  b var10 = (b)var1.getProviderData();
                  var10.e = true;
               }

               Certificate var11 = this.findCertificateReturned(var1, var8);
               if (var11 != null) {
                  var8.setCertificate(var11);
               }

               if (var3 != null) {
                  this.disperseCertsAndCRLs(var8, var3);
               }

               CRS.this.saveCertificate(var8);
               return var8;
            }
         }
      }

      /** @deprecated */
      public PKIResponseMessage requestCertification(PKIRequestMessage var1, ProtectInfo var2, DatabaseService var3) throws NotSupportedException, PKIException {
         return this.sendRequest(var1, var2, var3);
      }

      /** @deprecated */
      public PKIResult sendMessage(byte[] var1) throws NotSupportedException, PKIException {
         for(int var2 = 0; var2 < this.destList.length; ++var2) {
            String var3 = this.destList[var2];

            URL var4;
            try {
               var4 = new URL(var3);
            } catch (Exception var10) {
               continue;
            }

            String var5 = var4.getProtocol();
            if (var5.equals("http")) {
               String[] var6 = new String[]{TransportImplementation.MIME_USER_AGENT_STRING, "Content-type: application/octet-stream"};
               String[] var7 = new String[]{"Content-type: application/x-crs-message"};
               PKIResult var8 = this.sendAndReceiveHttp(var4, var6, this.proxyList, var1, var7);
               boolean var9 = (var8.getStatusInfo().getFailInfo() & 2097152) != 0;
               if (var8.getStatusInfo().getStatus() != 2 || !var9) {
                  return var8;
               }
            }
         }

         throw new PKIException("CRS$Implementation.sendMessage: Unable to connect to an auto-responder.");
      }

      public void generateProofOfPossession(PKIRequestMessage var1, JSAFE_PrivateKey var2, POPGenerationInfo var3) throws NotSupportedException, PKIException {
         if (var2 == null) {
            throw new PKIException("CRS$Implementation.generateProofOfPossession: privateKey cannot be null.");
         } else {
            int var4 = var1.getPopType();
            if (var4 == -1) {
               var1.setPopType(1);
            } else if (var4 != 1) {
               throw new PKIException("CRS$Implementation.generateProofOfPossession: POP type should be signature.");
            }

            PKCS10CertRequest var5 = new PKCS10CertRequest();
            X509Certificate var6 = (X509Certificate)var1.getCertificateTemplate();
            b var7 = CRS.this.new b();
            var1.setProviderData(var7);

            try {
               if (var6.getVersion() == -1) {
                  var5.setVersion(0);
               }

               if (var6.getSubjectName() == null) {
                  throw new PKIException("CRS$Implementation.generateProofOfPossession: Subject name is not specified.");
               }

               var5.setSubjectName(var6.getSubjectName());
               if (var6.getSubjectPublicKey(this.certJ.getDevice()) == null) {
                  throw new PKIException("CRS$Implementation.generateProofOfPossession: No public key in template.");
               }

               var5.setSubjectPublicKey(var6.getSubjectPublicKey(this.certJ.getDevice()));
               X509V3Extensions var8 = var6.getExtensions();
               if (var8 != null) {
                  X501Attributes var9 = new X501Attributes();
                  var9.addAttribute(new V3ExtensionAttribute(var8));
                  var5.setAttributes(var9);
               }

               try {
                  var5.signCertRequest("MD5/RSA/PKCS1Block01Pad", this.certJ.getDevice(), var2, this.certJ.getRandomObject());
               } catch (NoServiceException var11) {
                  throw new PKIException("CRS$Implementation.generateProofOfPossession: No Random service provider available.", var11);
               } catch (RandomException var12) {
                  throw new PKIException("CRS$Implementation.generateProofOfPossession.", var12);
               }

               byte[] var15 = new byte[var5.getDERLen(0)];
               var7.b = var15;
               var5.getDEREncoding(var15, 0, 0);
            } catch (CertificateException var13) {
               throw new PKIException("CRS$Implementation.generateProofOfPossession.", var13);
            }

            SignerInfo var14 = var1.getSender();
            if (var14 == null) {
               throw new PKIException("CRS$Implementation.generateProofOfPossession: Sender is not specified.");
            } else {
               try {
                  if (var14.getIssuerName().equals(var6.getSubjectName()) && CertJUtils.byteArraysEqual(var14.getSerialNumber(), var6.getSerialNumber())) {
                     this.a(var1, var2);
                  }

               } catch (PKCS7Exception var10) {
                  throw new PKIException("CRS$Implementation.generateProofOfPossession.", var10);
               }
            }
         }
      }

      public boolean validateProofOfPossession(PKIMessage var1, POPValidationInfo var2) throws NotSupportedException, PKIException {
         throw new NotSupportedException("CRS$Implementation.validateProofOfPossession: not supported.");
      }

      public void provideProofOfPossession(PKIRequestMessage var1, int var2, byte[] var3) throws PKIException {
         if (var2 != 1) {
            throw new PKIException("CRS$Implementation.provideProofOfPossession: Unsupported POP type(" + var2 + ").");
         } else if (var3 != null && var3.length != 0) {
            int var4 = this.a(var3, "-----BEGIN NEW CERTIFICATE REQUEST-----");
            int var5 = this.b(var3, "-----END NEW CERTIFICATE REQUEST-----");
            if (var5 < var4) {
               throw new PKIException("CRS$Implementation.provideProofOfPossession: Ill-formated POP data.");
            } else {
               byte[] var6 = this.a(var3, var4, var5);

               try {
                  new PKCS10CertRequest(var6, 0, 0);
               } catch (CertificateException var8) {
                  throw new PKIException("CRS$Implementation.provideProofOfPossession: POP is not in PKCS10 format.", var8);
               }

               b var7 = CRS.this.new b();
               var7.b = var6;
               var1.setProviderData(var7);
            }
         } else {
            throw new PKIException("CRS$Implementation.provideProofOfPossession: POP data is empty.");
         }
      }

      private byte[] a(PKIRequestMessage var1, CertPathCtx var2) throws PKIException {
         try {
            SignerInfo var5 = var1.getSender();
            Vector var6 = new Vector();
            DatabaseService var7 = var2.getDatabase();
            int var8 = var7.selectCertificateByIssuerAndSerialNumber(var5.getIssuerName(), var5.getSerialNumber(), var6);
            X500Name var3;
            if (var8 == 0) {
               var3 = new X500Name();
            } else {
               var3 = ((X509Certificate)var6.elementAt(0)).getSubjectName();
            }

            RecipientInfo var9 = var1.getRecipient();
            X500Name var4;
            if (var9 == null) {
               var4 = new X500Name();
            } else {
               var6 = new Vector();
               var8 = var7.selectCertificateByIssuerAndSerialNumber(var9.getIssuerName(), var9.getSerialNumber(), var6);
               if (var8 == 0) {
                  var4 = new X500Name();
               } else {
                  var4 = ((X509Certificate)var6.elementAt(0)).getSubjectName();
               }
            }

            byte[] var10 = new byte[var4.getDERLen(0)];
            var4.getDEREncoding(var10, 0, 0);
            byte[] var11 = new byte[var3.getDERLen(0)];
            var3.getDEREncoding(var11, 0, 0);
            SequenceContainer var12 = new SequenceContainer(0, true, 0);
            EndContainer var13 = new EndContainer();
            EncodedContainer var14 = new EncodedContainer(0, true, 0, var10, 0, var10.length);
            EncodedContainer var15 = new EncodedContainer(0, true, 0, var11, 0, var11.length);
            ASN1Container[] var16 = new ASN1Container[]{var12, var14, var15, var13};
            return ASN1.derEncode(var16);
         } catch (ASN_Exception var17) {
            throw new PKIException("CRS$Implementation.createCertInitialBodyDER: ", var17);
         } catch (CertJException var18) {
            throw new PKIException("CRS$Implementation.createCertInitialBodyDER: ", var18);
         } catch (NameException var19) {
            throw new PKIException("CRS$Implementation.createCertInitialBodyDER: ", var19);
         }
      }

      private byte[] a(byte[] var1, Properties var2) throws PKIException {
         try {
            String var3 = this.urlEncodeRegInfo(var2);
            byte[] var4 = var3.getBytes();
            OctetStringContainer var5 = new OctetStringContainer(0, true, 0, var4, 0, var4.length);
            SequenceContainer var6 = new SequenceContainer(0, true, 0);
            EndContainer var7 = new EndContainer();
            EncodedContainer var8 = new EncodedContainer(0, true, 0, var1, 0, var1.length);
            ASN1Container[] var9 = new ASN1Container[]{var6, var8, var5, var7};
            return ASN1.derEncode(var9);
         } catch (ASN_Exception var10) {
            throw new PKIException("CRS$Implementation.createCertReqBodyDER: ", var10);
         }
      }

      private boolean a(PKIRequestMessage var1, PKIResponseMessage var2) {
         b var3 = (b)var1.getProviderData();
         c var4 = (c)var2.getProviderData();
         return CertJUtils.byteArraysEqual(var3.a, var4.a);
      }

      private byte[] a(CertPathCtx var1, PKIRequestMessage var2, ContentInfo var3) throws PKIException {
         b var4 = (b)var2.getProviderData();
         DatabaseService var5 = var1.getDatabase();
         X509Certificate var6 = var4.c;
         if (var6 != null) {
            try {
               var5.insertCertificate(var6);
               var5.insertPrivateKeyByCertificate(var6, var4.d);
            } catch (Exception var16) {
               throw new PKIException("CRS$Implementation.signMessage: Storing EE signing key failed(", var16);
            }
         }

         try {
            SignedData var7 = new SignedData(this.certJ, var1);
            var7.setVersionNumber(1);
            SignerInfo var8 = var2.getSender();
            var7.addSignerInfo(var8);
            var7.setContentInfo(var3);
            Certificate[] var9 = var2.getExtraCerts();
            if (var9 != null) {
               for(int var10 = 0; var10 < var9.length; ++var10) {
                  var7.addCertificate((X509Certificate)var9[var10]);
               }
            }

            CRL[] var18 = var2.getExtraCRLs();
            if (var18 != null) {
               for(int var11 = 0; var11 < var18.length; ++var11) {
                  var7.addCRL((X509CRL)var18[var11]);
               }
            }

            DatabaseService var19 = var1.getDatabase();
            Vector var12 = new Vector();
            int var13 = var19.selectCertificateByIssuerAndSerialNumber(var8.getIssuerName(), var8.getSerialNumber(), var12);
            if (var13 > 0) {
               Certificate var14 = (Certificate)var12.elementAt(0);
               this.certJ.buildCertPath(var1, var14, var12, (Vector)null, (Vector)null, (Vector)null);

               for(int var15 = 0; var15 < var12.size(); ++var15) {
                  var7.addCertificate((X509Certificate)var12.elementAt(var15));
               }
            }

            var13 = var7.getContentInfoDERLen();
            byte[] var20 = new byte[var13];
            var7.writeMessage(var20, 0);
            return var20;
         } catch (CertJException var17) {
            throw new PKIException("CRS$Implementation.signMessage: ", var17);
         }
      }

      private EnvelopedData a(CertPathCtx var1, PKIRequestMessage var2, Data var3) throws PKIException {
         try {
            EnvelopedData var4 = new EnvelopedData(this.certJ, var1);
            var4.setVersionNumber(0);
            int[] var5 = var2.getEncryptionParams();
            var4.setEncryptionAlgorithm(var2.getEncryptionName(), var5 == null ? -1 : var5[0]);
            var4.setContentInfo(var3);
            var4.addRecipientInfo(var2.getRecipient());
            return var4;
         } catch (PKCS7Exception var6) {
            throw new PKIException("CRS$Implementation.envelopeMessage: ", var6);
         }
      }

      private byte[] a(Data var1) throws PKIException {
         try {
            byte[] var2 = new byte[var1.getContentInfoDERLen()];
            var1.writeMessage(var2, 0);
            JSAFE_MessageDigest var3 = h.a("MD5", this.certJ.getDevice(), this.context.b);
            byte[] var4 = new byte[var3.getDigestSize()];
            var3.digestInit();
            var3.digestUpdate(var2, 0, var2.length);
            var3.digestFinal(var4, 0);
            if ((var4[0] & 128) == 0) {
               return var4;
            } else {
               byte[] var5 = new byte[var4.length + 1];
               System.arraycopy(var4, 0, var5, 1, var4.length);
               var5[0] = 0;
               return var5;
            }
         } catch (PKCS7Exception var6) {
            throw new PKIException("CRS$Implementation.createTransactionID: ", var6);
         } catch (JSAFE_Exception var7) {
            throw new PKIException("CRS$Implementation.createTransactionID: ", var7);
         }
      }

      private byte[] a() throws PKIException {
         try {
            return this.certJ.getRandomObject().generateRandomBytes(16);
         } catch (NoServiceException var2) {
            throw new PKIException("CRS$Implementation.createNonce: No random provider available(", var2);
         } catch (RandomException var3) {
            throw new PKIException("CRS$Implementation.createNonce: ", var3);
         }
      }

      private PKIResponseMessage a(SignedData var1, ProtectInfoPublicKey var2) throws PKIException {
         ContentInfo var3 = var1.getContent();
         RecipientInfo var6 = null;
         byte var4;
         byte[] var5;
         Vector var7;
         switch (var3.getContentType()) {
            case 1:
               var4 = 1;
               var5 = ((Data)var3).getData();
               break;
            case 3:
               var4 = 4;
               var7 = ((EnvelopedData)var3).getRecipientInfos();
               if (var7 == null || var7.size() != 1) {
                  throw new PKIException("CRS$Implementation.readSignedResponse: Only one recipient is expected.");
               }

               var6 = (RecipientInfo)var7.elementAt(0);
               ContentInfo var8 = var3.getContent();
               if (var8.getContentType() != 1) {
                  throw new PKIException("CRS$Implementation.readSignedResponse: Content of EnvelopedData should be Data.");
               }

               var5 = ((Data)var8).getData();
               break;
            default:
               throw new PKIException("CRS$Implementation.readSignedResponse: Invalid response message foramt(" + var3.getContentType() + "). Valid formats are DATA and ENVELOPED_DATA.");
         }

         var7 = new Vector();
         Vector var21 = new Vector();
         this.a(var5, var2, var7, var21);
         SignerInfo var9 = this.a(var1);

         X501Attributes var10;
         try {
            var10 = var9.getAuthenticatedAttrs();
         } catch (PKCS7Exception var20) {
            throw new PKIException("CRS$Implementation.readSignedResponse: ", var20);
         }

         if (var10 == null) {
            throw new PKIException("CRS$Implementation.readSignedResponse: No authenticated attributes included.");
         } else {
            X501Attribute var12 = var10.getAttributeByType(12);
            if (var12 == null) {
               throw new PKIException("CRS$Implementation.readSignedResponse: Version attribute not found.");
            } else {
               int var11 = ((VeriSignCRSVersion)var12).getVersion();
               var12 = var10.getAttributeByType(6);
               if (var12 == null) {
                  throw new PKIException("CRS$Implementation.readSignedResponse: Message Type attribute not found.");
               } else {
                  int var13 = ((VeriSignCRSMessageType)var12).getMessageType();
                  if (var13 != 3) {
                     throw new PKIException("CRS$Implementation.readSignedResponse: Message Type CertResponse (3) is expected.");
                  } else {
                     var12 = var10.getAttributeByType(9);
                     if (var12 == null) {
                        throw new PKIException("CRS$Implementation.readSignedResponse: Sender nonce attribute not found.");
                     } else {
                        byte[] var14 = ((VeriSignCRSSenderNonce)var12).getSenderNonce();
                        var12 = var10.getAttributeByType(10);
                        if (var12 == null) {
                           throw new PKIException("CRS$Implementation.readSignedResponse: Recipient nonce attribute not found.");
                        } else {
                           byte[] var15 = ((VeriSignCRSRecipientNonce)var12).getRecipientNonce();
                           Certificate[] var16 = new Certificate[var7.size()];
                           var7.copyInto(var16);
                           CRL[] var17 = new CRL[var21.size()];
                           var21.copyInto(var17);
                           PKIResponseMessage var18 = new PKIResponseMessage(this.b(var10));
                           c var19 = CRS.this.new c(var14, var15);
                           var18.setProviderData(var19);
                           var18.setVersion(var11);
                           var18.setWrapInfo(var4, var9, var6, (String)null, (int[])null);
                           var18.setTransactionID(this.a(var10));
                           var18.setExtraCerts(var16);
                           var18.setExtraCRLs(var17);
                           return var18;
                        }
                     }
                  }
               }
            }
         }
      }

      private void a(PKIRequestMessage var1, JSAFE_PrivateKey var2) throws PKIException {
         String var3 = "MD5/RSA/PKCS1Block01Pad";
         X509Certificate var4 = (X509Certificate)var1.getCertificateTemplate();

         try {
            if (var4.getIssuerName() == null) {
               var4.setIssuerName(var4.getSubjectName());
            }

            long var5 = System.currentTimeMillis();
            Date var7 = var4.getStartDate();
            long var8 = 3600000L;
            if (var7 == null) {
               var7 = new Date(var5 - 720L * var8);
            }

            Date var10 = var4.getEndDate();
            if (var10 == null) {
               var10 = new Date(var5 + 8760L * var8);
            }

            var4.setValidity(var7, var10);
            var4.signCertificate(var3, this.certJ.getDevice(), var2, this.certJ.getRandomObject());
            b var12 = (b)var1.getProviderData();
            var12.d = var2;
            var12.c = var4;
            Certificate[] var13 = var1.getExtraCerts();
            int var14 = var13 == null ? 0 : var13.length;
            Certificate[] var15 = new Certificate[var14 + 1];
            if (var13 != null) {
               System.arraycopy(var13, 0, var15, 0, var14);
            }

            var15[var14] = var4;
            var1.setExtraCerts(var15);
         } catch (CertificateException var16) {
            throw new PKIException("CRS$Implementation.storeSelfSignedInfo: ", var16);
         } catch (CertJException var17) {
            throw new PKIException("CRS$Implementation.storeSelfSignedInfo: ", var17);
         }
      }

      private SignerInfo a(SignedData var1) throws PKIException {
         Vector var2 = var1.getSignerInfos();
         if (var2 != null && var2.size() <= 2 && !var2.isEmpty()) {
            return (SignerInfo)var2.elementAt(0);
         } else {
            throw new PKIException("CRS$Implementation.getSender: Expected exactly one signer.");
         }
      }

      private byte[] a(X501Attributes var1) throws PKIException {
         VeriSignCRSTransactionID var2 = (VeriSignCRSTransactionID)var1.getAttributeByType(11);
         if (var2 == null) {
            throw new PKIException("CRS$Implementation.extractTransactionID: Transaction ID attribute not found.");
         } else {
            return var2.getTransactionID();
         }
      }

      private PKIStatusInfo b(X501Attributes var1) throws PKIException {
         X501Attribute var2 = var1.getAttributeByType(7);
         if (var2 == null) {
            throw new PKIException("CRS$Implementation.extractStatusInfo: No status attribute found.");
         } else {
            int var3 = ((VeriSignCRSPKIStatus)var2).getPKIStatus();
            var3 = this.a(var3);
            int var4 = -1;
            int var5 = -1;
            if (var3 == 2) {
               var2 = var1.getAttributeByType(8);
               if (var2 == null) {
                  throw new PKIException("CRS$Implementation.extractStatusInfo: No Failure Info attribute found.");
               }

               var4 = ((VeriSignCRSFailureInfo)var2).getFailureInfo();
               var5 = this.b(var4);
            }

            return new PKIStatusInfo(var3, var5, (String[])null, var4);
         }
      }

      private int a(int var1) throws PKIException {
         switch (var1) {
            case 0:
               return 0;
            case 1:
               return 3;
            case 2:
               return 2;
            default:
               throw new PKIException("CRS$Implementation.mapStatusInfo: Status value(" + var1 + ") is not valid. Valid values are 0, 1, and 2.");
         }
      }

      private int b(int var1) {
         if (this.profile.equals("VeriSign")) {
            for(int var2 = 0; var2 < 76; ++var2) {
               if (CRS.FAILINFO_VALUE[var2] == var1) {
                  return CRS.FAILINFO_FLAG[var2];
               }
            }
         }

         return 536870912;
      }

      private Properties a(byte[] var1, ProtectInfoPublicKey var2, Vector var3, Vector var4) throws PKIException {
         if (var1 != null && var1.length != 0) {
            try {
               SequenceContainer var5 = new SequenceContainer(0);
               EndContainer var6 = new EndContainer();
               EncodedContainer var7 = new EncodedContainer(77824);
               OctetStringContainer var8 = new OctetStringContainer(0);
               ASN1Container[] var9 = new ASN1Container[]{var5, var7, var8, var6};
               ASN1.berDecode(var1, 0, var9);
               if (var7.dataLen != 0) {
                  SignedData var10 = new SignedData(this.certJ, var2.getCertPathCtx());
                  boolean var11 = var10.readInit(var7.data, var7.dataOffset, var7.dataLen);
                  if (var11) {
                     var11 = var10.readFinal();
                  }

                  if (!var11) {
                     throw new PKIException("CRS$Implementation.processResponseBody: Unable to decode degenerate PKCS7.");
                  }

                  Vector var12 = var10.getCertificates();
                  if (var12 != null) {
                     for(int var13 = 0; var13 < var12.size(); ++var13) {
                        var3.addElement((Certificate)var12.elementAt(var13));
                     }
                  }

                  Vector var17 = var10.getCRLs();
                  if (var12 != null) {
                     for(int var14 = 0; var14 < var17.size(); ++var14) {
                        var4.addElement(var17.elementAt(var14));
                     }
                  }
               }

               return this.urlDecodeRegInfo(new String(var8.data, var8.dataOffset, var8.dataLen));
            } catch (ASN_Exception var15) {
               throw new PKIException("CRS$Implementation.processResponseBody: Unable to decode innerDER.", var15);
            } catch (PKCS7Exception var16) {
               throw new PKIException("CRS$Implementation.processResponseBody: Unable to decode degenerated PKCS7.", var16);
            }
         } else {
            return null;
         }
      }

      private int a(byte[] var1, String var2) {
         int var3 = var1.length - var2.length();

         int var4;
         for(var4 = 0; var4 < var3; ++var4) {
            if (this.a(var1, var4, var2)) {
               var4 += var2.length();
               break;
            }
         }

         if (var4 == var3) {
            return 0;
         } else {
            if (var1[var4] == 13 || var1[var4] == 10) {
               ++var4;
               if (var1[var4] == 13 || var1[var4] == 10) {
                  ++var4;
               }
            }

            return var4;
         }
      }

      private int b(byte[] var1, String var2) {
         int var3 = var1.length - var2.length();

         int var4;
         for(var4 = 0; var4 < var3 && !this.a(var1, var4, var2); ++var4) {
         }

         return var4 == var3 ? var1.length : var4;
      }

      private boolean a(byte[] var1, int var2, String var3) {
         if (var1.length - var2 < var3.length()) {
            return false;
         } else {
            for(int var4 = 0; var4 < var3.length(); ++var4) {
               if (var1[var2 + var4] != (byte)var3.charAt(var4)) {
                  return false;
               }
            }

            return true;
         }
      }

      private byte[] a(byte[] var1, int var2, int var3) throws PKIException {
         try {
            JSAFE_Recode var4 = h.g("Base64-76", this.certJ.getDevice(), this.context.b);
            var4.decodeInit();
            int var5 = var4.getOutputBufferSize(var3);
            byte[] var6 = new byte[var5];
            int var7 = var4.decodeUpdate(var1, var2, var3, var6, 0);
            int var8 = var4.decodeFinal(var6, var7);
            int var9 = var7 + var8;
            if (var9 != var5) {
               byte[] var10 = new byte[var9];
               System.arraycopy(var6, 0, var10, 0, var9);
               return var10;
            } else {
               return var6;
            }
         } catch (JSAFE_Exception var11) {
            throw new PKIException("CRS$Implementation.base64Decode: ", var11);
         }
      }

      public String toString() {
         return "CRS provider named: " + super.getName();
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException, PKIException {
         this(var2, var3);
      }
   }
}
