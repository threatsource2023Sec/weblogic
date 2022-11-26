package org.python.bouncycastle.est;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERPrintableString;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.est.CsrAttrs;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cmc.CMCException;
import org.python.bouncycastle.cmc.SimplePKIResponse;
import org.python.bouncycastle.operator.ContentSigner;
import org.python.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.python.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.python.bouncycastle.util.Selector;
import org.python.bouncycastle.util.Store;
import org.python.bouncycastle.util.encoders.Base64;

public class ESTService {
   protected static final String CACERTS = "/cacerts";
   protected static final String SIMPLE_ENROLL = "/simpleenroll";
   protected static final String SIMPLE_REENROLL = "/simplereenroll";
   protected static final String FULLCMC = "/fullcmc";
   protected static final String SERVERGEN = "/serverkeygen";
   protected static final String CSRATTRS = "/csrattrs";
   protected static final Set illegalParts = new HashSet();
   private final String server;
   private final ESTClientProvider clientProvider;
   private static final Pattern pathInvalid;

   ESTService(String var1, String var2, ESTClientProvider var3) {
      var1 = this.verifyServer(var1);
      if (var2 != null) {
         var2 = this.verifyLabel(var2);
         this.server = "https://" + var1 + "/.well-known/est/" + var2;
      } else {
         this.server = "https://" + var1 + "/.well-known/est";
      }

      this.clientProvider = var3;
   }

   public static X509CertificateHolder[] storeToArray(Store var0) {
      return storeToArray(var0, (Selector)null);
   }

   public static X509CertificateHolder[] storeToArray(Store var0, Selector var1) {
      Collection var2 = var0.getMatches(var1);
      return (X509CertificateHolder[])var2.toArray(new X509CertificateHolder[var2.size()]);
   }

   public CACertsResponse getCACerts() throws Exception {
      ESTResponse var1 = null;
      Exception var2 = null;
      CACertsResponse var3 = null;
      URL var4 = null;
      boolean var5 = false;

      try {
         var4 = new URL(this.server + "/cacerts");
         ESTClient var6 = this.clientProvider.makeClient();
         ESTRequest var7 = (new ESTRequestBuilder("GET", var4)).withClient(var6).build();
         var1 = var6.doRequest(var7);
         Store var8 = null;
         Store var9 = null;
         if (var1.getStatusCode() == 200) {
            if (!"application/pkcs7-mime".equals(var1.getHeaders().getFirstValue("Content-Type"))) {
               String var23 = var1.getHeaders().getFirstValue("Content-Type") != null ? " got " + var1.getHeaders().getFirstValue("Content-Type") : " but was not present.";
               throw new ESTException("Response : " + var4.toString() + "Expecting application/pkcs7-mime " + var23, (Throwable)null, var1.getStatusCode(), var1.getInputStream());
            }

            try {
               if (var1.getContentLength() != null && var1.getContentLength() > 0L) {
                  ASN1InputStream var10 = new ASN1InputStream(var1.getInputStream());
                  SimplePKIResponse var11 = new SimplePKIResponse(ContentInfo.getInstance((ASN1Sequence)var10.readObject()));
                  var8 = var11.getCertificates();
                  var9 = var11.getCRLs();
               }
            } catch (Throwable var20) {
               throw new ESTException("Decoding CACerts: " + var4.toString() + " " + var20.getMessage(), var20, var1.getStatusCode(), var1.getInputStream());
            }
         } else if (var1.getStatusCode() != 204) {
            throw new ESTException("Get CACerts: " + var4.toString(), (Throwable)null, var1.getStatusCode(), var1.getInputStream());
         }

         var3 = new CACertsResponse(var8, var9, var7, var1.getSource(), this.clientProvider.isTrusted());
      } catch (Throwable var21) {
         var5 = true;
         if (var21 instanceof ESTException) {
            throw (ESTException)var21;
         }

         throw new ESTException(var21.getMessage(), var21);
      } finally {
         if (var1 != null) {
            try {
               var1.close();
            } catch (Exception var19) {
               var2 = var19;
            }
         }

      }

      if (var2 != null) {
         if (var2 instanceof ESTException) {
            throw var2;
         } else {
            throw new ESTException("Get CACerts: " + var4.toString(), var2, var1.getStatusCode(), (InputStream)null);
         }
      } else {
         return var3;
      }
   }

   public EnrollmentResponse simpleEnroll(EnrollmentResponse var1) throws Exception {
      if (!this.clientProvider.isTrusted()) {
         throw new IllegalStateException("No trust anchors.");
      } else {
         ESTResponse var2 = null;

         EnrollmentResponse var4;
         try {
            ESTClient var3 = this.clientProvider.makeClient();
            var2 = var3.doRequest((new ESTRequestBuilder(var1.getRequestToRetry())).withClient(var3).build());
            var4 = this.handleEnrollResponse(var2);
         } catch (Throwable var8) {
            if (var8 instanceof ESTException) {
               throw (ESTException)var8;
            }

            throw new ESTException(var8.getMessage(), var8);
         } finally {
            if (var2 != null) {
               var2.close();
            }

         }

         return var4;
      }
   }

   public EnrollmentResponse simpleEnroll(boolean var1, PKCS10CertificationRequest var2, ESTAuth var3) throws IOException {
      if (!this.clientProvider.isTrusted()) {
         throw new IllegalStateException("No trust anchors.");
      } else {
         ESTResponse var4 = null;

         EnrollmentResponse var9;
         try {
            byte[] var5 = this.annotateRequest(var2.getEncoded()).getBytes();
            URL var6 = new URL(this.server + (var1 ? "/simplereenroll" : "/simpleenroll"));
            ESTClient var7 = this.clientProvider.makeClient();
            ESTRequestBuilder var8 = (new ESTRequestBuilder("POST", var6)).withData(var5).withClient(var7);
            var8.addHeader("Content-Type", "application/pkcs10");
            var8.addHeader("Content-Length", "" + var5.length);
            var8.addHeader("Content-Transfer-Encoding", "base64");
            if (var3 != null) {
               var3.applyAuth(var8);
            }

            var4 = var7.doRequest(var8.build());
            var9 = this.handleEnrollResponse(var4);
         } catch (Throwable var13) {
            if (var13 instanceof ESTException) {
               throw (ESTException)var13;
            }

            throw new ESTException(var13.getMessage(), var13);
         } finally {
            if (var4 != null) {
               var4.close();
            }

         }

         return var9;
      }
   }

   public EnrollmentResponse simpleEnrollPoP(boolean var1, final PKCS10CertificationRequestBuilder var2, final ContentSigner var3, ESTAuth var4) throws IOException {
      if (!this.clientProvider.isTrusted()) {
         throw new IllegalStateException("No trust anchors.");
      } else {
         ESTResponse var5 = null;

         EnrollmentResponse var9;
         try {
            URL var6 = new URL(this.server + (var1 ? "/simplereenroll" : "/simpleenroll"));
            ESTClient var7 = this.clientProvider.makeClient();
            ESTRequestBuilder var8 = (new ESTRequestBuilder("POST", var6)).withClient(var7).withConnectionListener(new ESTSourceConnectionListener() {
               public ESTRequest onConnection(Source var1, ESTRequest var2x) throws IOException {
                  if (var1 instanceof TLSUniqueProvider && ((TLSUniqueProvider)var1).isTLSUniqueAvailable()) {
                     PKCS10CertificationRequestBuilder var3x = new PKCS10CertificationRequestBuilder(var2);
                     ByteArrayOutputStream var4 = new ByteArrayOutputStream();
                     byte[] var5 = ((TLSUniqueProvider)var1).getTLSUnique();
                     var3x.setAttribute(PKCSObjectIdentifiers.pkcs_9_at_challengePassword, (ASN1Encodable)(new DERPrintableString(Base64.toBase64String(var5))));
                     var4.write(ESTService.this.annotateRequest(var3x.build(var3).getEncoded()).getBytes());
                     var4.flush();
                     ESTRequestBuilder var6 = (new ESTRequestBuilder(var2x)).withData(var4.toByteArray());
                     var6.setHeader("Content-Type", "application/pkcs10");
                     var6.setHeader("Content-Transfer-Encoding", "base64");
                     var6.setHeader("Content-Length", Long.toString((long)var4.size()));
                     return var6.build();
                  } else {
                     throw new IOException("Source does not supply TLS unique.");
                  }
               }
            });
            if (var4 != null) {
               var4.applyAuth(var8);
            }

            var5 = var7.doRequest(var8.build());
            var9 = this.handleEnrollResponse(var5);
         } catch (Throwable var13) {
            if (var13 instanceof ESTException) {
               throw (ESTException)var13;
            }

            throw new ESTException(var13.getMessage(), var13);
         } finally {
            if (var5 != null) {
               var5.close();
            }

         }

         return var9;
      }
   }

   protected EnrollmentResponse handleEnrollResponse(ESTResponse var1) throws IOException {
      ESTRequest var2 = var1.getOriginalRequest();
      Store var3 = null;
      if (var1.getStatusCode() == 202) {
         String var14 = var1.getHeader("Retry-After");
         if (var14 == null) {
            throw new ESTException("Got Status 202 but not Retry-After header from: " + var2.getURL().toString());
         } else {
            long var5 = -1L;

            try {
               var5 = System.currentTimeMillis() + Long.parseLong(var14) * 1000L;
            } catch (NumberFormatException var12) {
               try {
                  SimpleDateFormat var8 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                  var8.setTimeZone(TimeZone.getTimeZone("GMT"));
                  var5 = var8.parse(var14).getTime();
               } catch (Exception var11) {
                  throw new ESTException("Unable to parse Retry-After header:" + var2.getURL().toString() + " " + var11.getMessage(), (Throwable)null, var1.getStatusCode(), var1.getInputStream());
               }
            }

            return new EnrollmentResponse((Store)null, var5, var2, var1.getSource());
         }
      } else if (var1.getStatusCode() == 200) {
         ASN1InputStream var4 = new ASN1InputStream(var1.getInputStream());
         SimplePKIResponse var9 = null;

         try {
            var9 = new SimplePKIResponse(ContentInfo.getInstance(var4.readObject()));
         } catch (CMCException var13) {
            throw new ESTException(var13.getMessage(), var13.getCause());
         }

         var3 = var9.getCertificates();
         return new EnrollmentResponse(var3, -1L, (ESTRequest)null, var1.getSource());
      } else {
         throw new ESTException("Simple Enroll: " + var2.getURL().toString(), (Throwable)null, var1.getStatusCode(), var1.getInputStream());
      }
   }

   public CSRRequestResponse getCSRAttributes() throws ESTException {
      if (!this.clientProvider.isTrusted()) {
         throw new IllegalStateException("No trust anchors.");
      } else {
         ESTResponse var1 = null;
         CSRAttributesResponse var2 = null;
         Exception var3 = null;
         URL var4 = null;

         try {
            var4 = new URL(this.server + "/csrattrs");
            ESTClient var5 = this.clientProvider.makeClient();
            ESTRequest var6 = (new ESTRequestBuilder("GET", var4)).withClient(var5).build();
            var1 = var5.doRequest(var6);
            switch (var1.getStatusCode()) {
               case 200:
                  try {
                     if (var1.getContentLength() != null && var1.getContentLength() > 0L) {
                        ASN1InputStream var7 = new ASN1InputStream(var1.getInputStream());
                        ASN1Sequence var8 = (ASN1Sequence)var7.readObject();
                        var2 = new CSRAttributesResponse(CsrAttrs.getInstance(var8));
                     }
                     break;
                  } catch (Throwable var17) {
                     throw new ESTException("Decoding CACerts: " + var4.toString() + " " + var17.getMessage(), var17, var1.getStatusCode(), var1.getInputStream());
                  }
               case 204:
                  var2 = null;
                  break;
               case 404:
                  var2 = null;
                  break;
               default:
                  throw new ESTException("CSR Attribute request: " + var6.getURL().toString(), (Throwable)null, var1.getStatusCode(), var1.getInputStream());
            }
         } catch (Throwable var18) {
            if (var18 instanceof ESTException) {
               throw (ESTException)var18;
            }

            throw new ESTException(var18.getMessage(), var18);
         } finally {
            if (var1 != null) {
               try {
                  var1.close();
               } catch (Exception var16) {
                  var3 = var16;
               }
            }

         }

         if (var3 != null) {
            if (var3 instanceof ESTException) {
               throw (ESTException)var3;
            } else {
               throw new ESTException(var3.getMessage(), var3, var1.getStatusCode(), (InputStream)null);
            }
         } else {
            return new CSRRequestResponse(var2, var1.getSource());
         }
      }
   }

   private String annotateRequest(byte[] var1) {
      int var2 = 0;
      StringWriter var3 = new StringWriter();
      PrintWriter var4 = new PrintWriter(var3);

      do {
         if (var2 + 48 < var1.length) {
            var4.print(Base64.toBase64String(var1, var2, 48));
            var2 += 48;
         } else {
            var4.print(Base64.toBase64String(var1, var2, var1.length - var2));
            var2 = var1.length;
         }

         var4.print('\n');
      } while(var2 < var1.length);

      var4.flush();
      return var3.toString();
   }

   private String verifyLabel(String var1) {
      while(var1.endsWith("/") && var1.length() > 0) {
         var1 = var1.substring(0, var1.length() - 1);
      }

      while(var1.startsWith("/") && var1.length() > 0) {
         var1 = var1.substring(1);
      }

      if (var1.length() == 0) {
         throw new IllegalArgumentException("Label set but after trimming '/' is not zero length string.");
      } else if (!pathInvalid.matcher(var1).matches()) {
         throw new IllegalArgumentException("Server path " + var1 + " contains invalid characters");
      } else if (illegalParts.contains(var1)) {
         throw new IllegalArgumentException("Label " + var1 + " is a reserved path segment.");
      } else {
         return var1;
      }
   }

   private String verifyServer(String var1) {
      try {
         while(var1.endsWith("/") && var1.length() > 0) {
            var1 = var1.substring(0, var1.length() - 1);
         }

         if (var1.contains("://")) {
            throw new IllegalArgumentException("Server contains scheme, must only be <dnsname/ipaddress>:port, https:// will be added arbitrarily.");
         } else {
            URL var2 = new URL("https://" + var1);
            if (var2.getPath().length() != 0 && !var2.getPath().equals("/")) {
               throw new IllegalArgumentException("Server contains path, must only be <dnsname/ipaddress>:port, a path of '/.well-known/est/<label>' will be added arbitrarily.");
            } else {
               return var1;
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var3;
         } else {
            throw new IllegalArgumentException("Scheme and host is invalid: " + var3.getMessage(), var3);
         }
      }
   }

   static {
      illegalParts.add("/cacerts".substring(1));
      illegalParts.add("/simpleenroll".substring(1));
      illegalParts.add("/simplereenroll".substring(1));
      illegalParts.add("/fullcmc".substring(1));
      illegalParts.add("/serverkeygen".substring(1));
      illegalParts.add("/csrattrs".substring(1));
      pathInvalid = Pattern.compile("^[0-9a-zA-Z_\\-.~!$&'()*+,;=]+");
   }
}
