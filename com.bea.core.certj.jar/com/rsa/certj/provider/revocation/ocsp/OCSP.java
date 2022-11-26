package com.rsa.certj.provider.revocation.ocsp;

import com.rsa.asn1.ASN_Exception;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.provider.pki.PKICommonImplementation;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIResult;
import com.rsa.certj.spi.pki.PKIStatusInfo;
import com.rsa.certj.spi.revocation.CertRevocationInfo;
import com.rsa.certj.spi.revocation.CertStatusException;
import com.rsa.certj.spi.revocation.CertStatusInterface;
import com.rsa.certj.x.e;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

/** @deprecated */
public final class OCSP extends Provider {
   private OCSPResponder[] configedResponders;
   private OCSPResponderInternal[] responders;
   static final int SUPPORTED_VERSION = 0;
   static final int NONCE_LEN = 16;
   private static final String MIME_TYPE_OCSP_REQ = "application/ocsp-request";
   private static final String MIME_CONTENT_TYPE = "Content-type";
   private static final String MIME_CONTENT_LENGTH = "Content-length";
   private static final String MIME_USER_AGENT = "User-Agent";
   private static final String MIME_USER_AGENT_VALUE = "Cert-J/3.1";
   private boolean dbgWriteDERs;
   private Hashtable configProperties;

   /** @deprecated */
   public void setDebugWriteDERs(boolean var1) {
      this.dbgWriteDERs = var1;
   }

   /** @deprecated */
   public OCSP(String var1, OCSPResponder var2) throws InvalidParameterException, CertificateException, NameException {
      super(2, var1);
      if (var2 == null) {
         throw new InvalidParameterException("responder == null");
      } else {
         this.configedResponders = new OCSPResponder[1];
         this.configedResponders[0] = new OCSPResponder(var2);
      }
   }

   /** @deprecated */
   public OCSP(String var1, OCSPResponder[] var2) throws InvalidParameterException, CertificateException, NameException {
      super(2, var1);
      if (var2 == null) {
         throw new InvalidParameterException("responder == null");
      } else {
         int var3 = var2.length;
         this.configedResponders = new OCSPResponder[var3];

         for(int var4 = 0; var4 < var3; ++var4) {
            if (var2[var4] == null) {
               throw new InvalidParameterException("responders[" + var4 + "] == null");
            }

            this.configedResponders[var4] = new OCSPResponder(var2[var4]);
         }

      }
   }

   /** @deprecated */
   public OCSP(String var1, OCSPResponder var2, InputStream var3) throws InvalidParameterException, CertificateException, NameException {
      this(var1, var2);
      if (var3 == null) {
         throw new InvalidParameterException("OCSP: configStream should not be null.");
      } else {
         this.configProperties = PKICommonImplementation.loadProperties(var3);
      }
   }

   /** @deprecated */
   public OCSP(String var1, OCSPResponder var2, File var3) throws InvalidParameterException, CertificateException, NameException {
      this(var1, var2);
      if (var3 == null) {
         throw new InvalidParameterException("OCSP: configFile should not be null.");
      } else {
         FileInputStream var4 = null;

         try {
            var4 = new FileInputStream(var3);
            this.configProperties = PKICommonImplementation.loadProperties(var4);
         } catch (FileNotFoundException var13) {
            throw new InvalidParameterException("OCSP: " + var3.toString() + " does not exist.");
         } finally {
            if (var4 != null) {
               try {
                  var4.close();
               } catch (IOException var12) {
               }
            }

         }

      }
   }

   /** @deprecated */
   public OCSP(String var1, OCSPResponder[] var2, InputStream var3) throws InvalidParameterException, CertificateException, NameException {
      this(var1, var2);
      if (var3 == null) {
         throw new InvalidParameterException("OCSP: configStream should not be null.");
      } else {
         this.configProperties = PKICommonImplementation.loadProperties(var3);
      }
   }

   /** @deprecated */
   public OCSP(String var1, OCSPResponder[] var2, File var3) throws InvalidParameterException, CertificateException, NameException {
      this(var1, var2);
      if (var3 == null) {
         throw new InvalidParameterException("OCSP: configFile should not be null.");
      } else {
         FileInputStream var4 = null;

         try {
            var4 = new FileInputStream(var3);
            this.configProperties = PKICommonImplementation.loadProperties(var4);
         } catch (FileNotFoundException var13) {
            throw new InvalidParameterException("OCSP: " + var3.toString() + " does not exist.");
         } finally {
            if (var4 != null) {
               try {
                  var4.close();
               } catch (IOException var12) {
               }
            }

         }

      }
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      int var2 = this.configedResponders.length;

      try {
         this.responders = new OCSPResponderInternal[var2];

         for(int var3 = 0; var3 < var2; ++var3) {
            this.responders[var3] = new OCSPResponderInternal(var1, this.configedResponders[var3]);
            this.configedResponders[var3] = null;
         }

         return new a(var1, this.getName());
      } catch (InvalidParameterException var4) {
         throw new ProviderManagementException("OCSP.instantiate.", var4);
      } catch (CertificateException var5) {
         throw new ProviderManagementException("OCSP.instantiate.", var5);
      } catch (NameException var6) {
         throw new ProviderManagementException("OCSP.instantiate.", var6);
      } catch (PKIException var7) {
         throw new ProviderManagementException("OCSP.instantiate.", var7);
      }
   }

   private final class a extends PKICommonImplementation implements CertStatusInterface {
      private a(CertJ var2, String var3) throws InvalidParameterException, PKIException {
         super(var2, var3);
         if (OCSP.this.configProperties != null) {
            this.loadConfig(OCSP.this.configProperties);
         }

      }

      public CertRevocationInfo checkCertRevocation(CertPathCtx var1, Certificate var2) throws NotSupportedException, CertStatusException {
         if (var1 == null) {
            throw new NotSupportedException("pathCtx==null");
         } else {
            Certificate[] var3 = new Certificate[]{var2};
            CertRevocationInfo[] var4 = this.a(var1, var3);
            return var4[0];
         }
      }

      private void a(String var1, byte[] var2) throws NotSupportedException {
         FileOutputStream var3 = null;

         try {
            var3 = new FileOutputStream(var1);
            var3.write(var2);
            var3.close();
         } catch (Exception var12) {
            throw new NotSupportedException("Could not write to file " + var1 + ": " + var12.getMessage());
         } finally {
            if (var3 != null) {
               try {
                  var3.close();
               } catch (IOException var11) {
               }
            }

         }

      }

      private CertRevocationInfo[] a(CertPathCtx var1, Certificate[] var2) throws NotSupportedException, CertStatusException {
         if (var2 == null) {
            return null;
         } else {
            int var6 = var2.length;
            CertRevocationInfo[] var4 = new CertRevocationInfo[var6];
            b[] var3 = new b[var6];

            int var5;
            for(var5 = 0; var5 < var6; ++var5) {
               if (var2[var5] == null) {
                  var3[var5] = null;
                  var4[var5] = null;
               } else {
                  var3[var5] = this.a(var1.getPathOptions(), (X509Certificate)var2[var5]);
               }
            }

            try {
               for(var5 = 0; var5 < var6; ++var5) {
                  String var7 = null;
                  if (var2[var5] != null) {
                     X509V3Extensions var8 = ((X509Certificate)var2[var5]).getExtensions();
                     if (var8 != null && var8.getExtensionByType(117) != null) {
                        var4[var5] = new CertRevocationInfo(0, 0, (Object)null);
                     } else {
                        var4[var5] = new CertRevocationInfo(2, 0, (Object)null);
                        if (var3[var5] != null) {
                           OCSPRequest var9 = new OCSPRequest(this.certJ, var3[var5].a, var2);
                           byte[] var10 = var9.encode(var1);
                           if (OCSP.this.dbgWriteDERs) {
                              byte[] var12 = var2[var5].getUniqueID();
                              var7 = var12 == null ? "" : new String(e.a(var12));
                              this.a("ocspreq." + var7 + ".der", var10);
                           }

                           byte[] var11;
                           try {
                              PKIResult var20 = this.a(var3[var5].b, var3[var5].c, var10);
                              var11 = var20.getEncodedResponse();
                              PKIStatusInfo var13 = var20.getStatusInfo();
                              if (var13.getStatus() != 0) {
                                 throw new CertStatusException("OCSP Transport status != 0 (" + var13.getStatus() + ")");
                              }

                              if (var13.getFailInfoAux() != 200) {
                                 String var23 = CertJUtils.objectArrayToString(var13.getStatusStrings(), ", ");
                                 throw new CertStatusException("OCSP Transport HTTP status != 200\n" + var23);
                              }

                              if (OCSP.this.dbgWriteDERs) {
                                 this.a("ocspresp." + var7 + ".der", var11);
                              }
                           } catch (PKIException var17) {
                              throw new CertStatusException(var17);
                           }

                           OCSPResponse var21 = new OCSPResponse(this.certJ, OCSP.this.responders[var5], (X509Certificate)var2[var5]);
                           var21.decode(var1, var11, var9);
                           CertRevocationInfo var22 = var21.getRevocationInfo(var9.getCertID(var5));
                           byte[] var14 = var9.getNonce();
                           if (var14 != null) {
                              byte[] var15 = var21.getNonce();
                              if (var15 == null) {
                                 OCSPEvidence var16 = null;
                                 if (var22 != null && var22.getType() == 2) {
                                    var16 = (OCSPEvidence)var22.getEvidence();
                                 }

                                 if (var16 != null) {
                                    var16.setFlags(var16.getFlags() | 1);
                                 }
                              } else if (!CertJUtils.byteArraysEqual(var14, var15)) {
                                 throw new NotSupportedException("OCSP nonce mismatch");
                              }
                           }

                           if (var22 != null) {
                              var4[var5] = var22;
                           }
                        }
                     }
                  }
               }

               return var4;
            } catch (ASN_Exception var18) {
               throw new CertStatusException(var18);
            } catch (CertificateException var19) {
               throw new NotSupportedException(var19);
            }
         }
      }

      private PKIResult a(String[] var1, String[] var2, byte[] var3) throws PKIException {
         boolean var4 = false;

         for(int var5 = 0; var5 < var1.length; ++var5) {
            String var6 = var1[var5];

            URL var7;
            try {
               var7 = new URL(var6);
            } catch (Exception var18) {
               continue;
            }

            String var8 = var7.getProtocol();
            if (var8.equals("http") || var8.equals("https")) {
               var4 = true;
               int var10 = 0;
               if (var2 != null) {
                  var10 = var2.length;
               }

               if (var3 == null) {
                  var3 = new byte[0];
               }

               PKIResult var9;
               if (var2 != null && var10 != 0) {
                  var9 = null;
                  int var11 = 0;

                  while(var11 < var10) {
                     String var13 = null;

                     try {
                        var13 = var2[var11];
                        URL var12 = new URL(var13);
                        String var15 = var12.getHost();
                        String var14 = "" + var12.getPort();
                        Properties var16 = System.getProperties();
                        var16.setProperty("http.proxyHost", var15);
                        var16.setProperty("http.proxyPort", var14);
                        var16.setProperty("https.proxyHost", var15);
                        var16.setProperty("https.proxyPort", var14);
                     } catch (MalformedURLException var17) {
                        throw new PKIException("OCSP.sendMessage: unable to parse proxy specification" + var13 + ".", var17);
                     }

                     try {
                        var9 = this.a(var7, var3);
                        break;
                     } catch (Exception var19) {
                        ++var11;
                     }
                  }
               } else {
                  var9 = this.a(var7, var3);
               }

               if (var9 != null) {
                  PKIStatusInfo var20 = var9.getStatusInfo();
                  boolean var21 = (var20.getFailInfo() & 2097152) != 0;
                  if (var20.getStatus() != 2 || !var21) {
                     return var9;
                  }
               }
            }
         }

         throw new PKIException(var4 ? "Unable to connect to an OCSP responder." : "Unable to choose an OCSP responder.");
      }

      private PKIResult a(URL var1, byte[] var2) throws PKIException {
         byte[] var3 = null;
         OutputStream var4 = null;
         InputStream var5 = null;

         PKIResult var26;
         try {
            HttpURLConnection var6 = (HttpURLConnection)var1.openConnection();
            var6.setDoOutput(true);
            var6.setRequestMethod("POST");
            var6.setRequestProperty("User-Agent", "Cert-J/3.1");
            var6.setRequestProperty("Content-type", "application/ocsp-request");
            var6.setRequestProperty("Content-length", String.valueOf(var2.length));
            var4 = var6.getOutputStream();
            var4.write(var2);
            var4.flush();
            int var7 = var6.getResponseCode();
            int var8 = this.mapHTTPStatus(var7);
            int var9 = this.mapHTTPFailInfo(var7);
            if (var8 == 0) {
               var5 = var6.getInputStream();
               int var10 = var6.getContentLength();
               if (var10 == -1) {
                  var10 = Integer.MAX_VALUE;
               }

               int var11 = 0;
               int var12 = 0;

               for(var3 = new byte[var10]; var12 != -1 && var11 < var10; var11 += var12) {
                  var12 = var5.read(var3, var11, var3.length - var11);
               }
            }

            String[] var25 = new String[]{var6.getHeaderFields().toString()};
            var26 = new PKIResult(new PKIStatusInfo(var8, var9, var25, var7), var3);
         } catch (IOException var23) {
            throw new PKIException("OCSP.SendOCSPRequest: " + var23.getMessage());
         } finally {
            if (var4 != null) {
               try {
                  var4.close();
               } catch (IOException var22) {
               }
            }

            if (var5 != null) {
               try {
                  var5.close();
               } catch (IOException var21) {
               }
            }

         }

         return var26;
      }

      private b a(int var1, X509Certificate var2) {
         OCSPResponderInternal var4 = null;
         String var5 = null;
         if ((var1 & 2048) == 0) {
            var5 = OCSPutil.getAIALocation(var2);
         }

         OCSPResponderInternal var3;
         String[] var6;
         int var7;
         if (var5 == null) {
            for(var7 = 0; var7 < OCSP.this.responders.length; ++var7) {
               var3 = OCSP.this.responders[var7];
               if (var3.getResponderCACert(var2) != null) {
                  var6 = var3.getDestList();
                  if (var6 != null) {
                     return OCSP.this.new b(var3, var6, var3.getProxyList());
                  }
               }
            }

            return null;
         } else {
            var6 = new String[]{var5};

            for(var7 = 0; var7 < OCSP.this.responders.length; ++var7) {
               var3 = OCSP.this.responders[var7];
               if (var3.getResponderCACert(var2, var5) != null) {
                  return OCSP.this.new b(var3, var6, var3.getProxyList());
               }

               if (var3.getResponderCACert(var2) != null && var4 == null) {
                  var4 = var3;
               }
            }

            return var4 == null ? null : OCSP.this.new b(var4, var6, var4.getProxyList());
         }
      }

      public String toString() {
         return "OCSP Certificate Status provider named: " + this.getName();
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException, PKIException {
         this(var2, var3);
      }
   }

   private class b {
      OCSPResponderInternal a;
      String[] b;
      String[] c;

      protected b(OCSPResponderInternal var2, String[] var3, String[] var4) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
      }
   }
}
