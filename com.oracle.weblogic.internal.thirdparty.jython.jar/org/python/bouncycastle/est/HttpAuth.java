package org.python.bouncycastle.est;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.python.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.util.Strings;
import org.python.bouncycastle.util.encoders.Base64;
import org.python.bouncycastle.util.encoders.Hex;

public class HttpAuth implements ESTAuth {
   private static final DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder = new DefaultDigestAlgorithmIdentifierFinder();
   private final String realm;
   private final String username;
   private final char[] password;
   private final SecureRandom nonceGenerator;
   private final DigestCalculatorProvider digestCalculatorProvider;
   private static final Set validParts;

   public HttpAuth(String var1, char[] var2) {
      this((String)null, var1, var2, (SecureRandom)null, (DigestCalculatorProvider)null);
   }

   public HttpAuth(String var1, String var2, char[] var3) {
      this(var1, var2, var3, (SecureRandom)null, (DigestCalculatorProvider)null);
   }

   public HttpAuth(String var1, char[] var2, SecureRandom var3, DigestCalculatorProvider var4) {
      this((String)null, var1, var2, var3, var4);
   }

   public HttpAuth(String var1, String var2, char[] var3, SecureRandom var4, DigestCalculatorProvider var5) {
      this.realm = var1;
      this.username = var2;
      this.password = var3;
      this.nonceGenerator = var4;
      this.digestCalculatorProvider = var5;
   }

   public void applyAuth(ESTRequestBuilder var1) {
      var1.withHijacker(new ESTHijacker() {
         public ESTResponse hijack(ESTRequest var1, Source var2) throws IOException {
            ESTResponse var3 = new ESTResponse(var1, var2);
            if (var3.getStatusCode() == 401) {
               String var4 = var3.getHeader("WWW-Authenticate");
               if (var4 == null) {
                  throw new ESTException("Status of 401 but no WWW-Authenticate header");
               } else {
                  var4 = Strings.toLowerCase(var4);
                  if (var4.startsWith("digest")) {
                     var3 = HttpAuth.this.doDigestFunction(var3);
                  } else {
                     if (!var4.startsWith("basic")) {
                        throw new ESTException("Unknown auth mode: " + var4);
                     }

                     var3.close();
                     Map var5 = HttpUtil.splitCSL("Basic", var3.getHeader("WWW-Authenticate"));
                     if (HttpAuth.this.realm != null && !HttpAuth.this.realm.equals(var5.get("realm"))) {
                        throw new ESTException("Supplied realm '" + HttpAuth.this.realm + "' does not match server realm '" + (String)var5.get("realm") + "'", (Throwable)null, 401, (InputStream)null);
                     }

                     ESTRequestBuilder var6 = (new ESTRequestBuilder(var1)).withHijacker((ESTHijacker)null);
                     if (HttpAuth.this.realm != null && HttpAuth.this.realm.length() > 0) {
                        var6.setHeader("WWW-Authenticate", "Basic realm=\"" + HttpAuth.this.realm + "\"");
                     }

                     if (HttpAuth.this.username.contains(":")) {
                        throw new IllegalArgumentException("User must not contain a ':'");
                     }

                     String var7 = HttpAuth.this.username + ":" + new String(HttpAuth.this.password);
                     var6.setHeader("Authorization", "Basic " + Base64.toBase64String(var7.getBytes()));
                     var3 = var1.getClient().doRequest(var6.build());
                  }

                  return var3;
               }
            } else {
               return var3;
            }
         }
      });
   }

   private ESTResponse doDigestFunction(ESTResponse var1) throws IOException {
      var1.close();
      ESTRequest var2 = var1.getOriginalRequest();
      Map var3 = null;

      try {
         var3 = HttpUtil.splitCSL("Digest", var1.getHeader("WWW-Authenticate"));
      } catch (Throwable var27) {
         throw new ESTException("Parsing WWW-Authentication header: " + var27.getMessage(), var27, var1.getStatusCode(), new ByteArrayInputStream(var1.getHeader("WWW-Authenticate").getBytes()));
      }

      String var4 = null;

      try {
         var4 = var2.getURL().toURI().getPath();
      } catch (Exception var26) {
         throw new IOException("unable to process URL in request: " + var26.getMessage());
      }

      Iterator var5 = var3.keySet().iterator();

      while(var5.hasNext()) {
         Object var6 = var5.next();
         if (!validParts.contains(var6)) {
            throw new ESTException("Unrecognised entry in WWW-Authenticate header: '" + var6 + "'");
         }
      }

      String var28 = var2.getMethod();
      String var29 = (String)var3.get("realm");
      String var7 = (String)var3.get("nonce");
      String var8 = (String)var3.get("opaque");
      String var9 = (String)var3.get("algorithm");
      String var10 = (String)var3.get("qop");
      ArrayList var11 = new ArrayList();
      if (this.realm != null && !this.realm.equals(var29)) {
         throw new ESTException("Supplied realm '" + this.realm + "' does not match server realm '" + var29 + "'", (Throwable)null, 401, (InputStream)null);
      } else {
         if (var9 == null) {
            var9 = "MD5";
         }

         if (var9.length() == 0) {
            throw new ESTException("WWW-Authenticate no algorithm defined.");
         } else {
            var9 = Strings.toUpperCase(var9);
            if (var10 == null) {
               throw new ESTException("Qop is not defined in WWW-Authenticate header.");
            } else if (var10.length() == 0) {
               throw new ESTException("QoP value is empty.");
            } else {
               var10 = Strings.toLowerCase(var10);
               String[] var12 = var10.split(",");

               for(int var13 = 0; var13 != var12.length; ++var13) {
                  if (!var12[var13].equals("auth") && !var12[var13].equals("auth-int")) {
                     throw new ESTException("QoP value unknown: '" + var13 + "'");
                  }

                  String var14 = var12[var13].trim();
                  if (!var11.contains(var14)) {
                     var11.add(var14);
                  }
               }

               AlgorithmIdentifier var30 = this.lookupDigest(var9);
               if (var30 != null && var30.getAlgorithm() != null) {
                  DigestCalculator var31 = this.getDigestCalculator(var9, var30);
                  OutputStream var32 = var31.getOutputStream();
                  String var15 = this.makeNonce(10);
                  this.update(var32, this.username);
                  this.update(var32, ":");
                  this.update(var32, var29);
                  this.update(var32, ":");
                  this.update(var32, this.password);
                  var32.close();
                  byte[] var16 = var31.getDigest();
                  if (var9.endsWith("-SESS")) {
                     DigestCalculator var17 = this.getDigestCalculator(var9, var30);
                     OutputStream var18 = var17.getOutputStream();
                     String var19 = Hex.toHexString(var16);
                     this.update(var18, var19);
                     this.update(var18, ":");
                     this.update(var18, var7);
                     this.update(var18, ":");
                     this.update(var18, var15);
                     var18.close();
                     var16 = var17.getDigest();
                  }

                  String var33 = Hex.toHexString(var16);
                  DigestCalculator var34 = this.getDigestCalculator(var9, var30);
                  OutputStream var35 = var34.getOutputStream();
                  if (((String)var11.get(0)).equals("auth-int")) {
                     DigestCalculator var20 = this.getDigestCalculator(var9, var30);
                     OutputStream var21 = var20.getOutputStream();
                     var2.writeData(var21);
                     var21.close();
                     byte[] var22 = var20.getDigest();
                     this.update(var35, var28);
                     this.update(var35, ":");
                     this.update(var35, var4);
                     this.update(var35, ":");
                     this.update(var35, Hex.toHexString(var22));
                  } else if (((String)var11.get(0)).equals("auth")) {
                     this.update(var35, var28);
                     this.update(var35, ":");
                     this.update(var35, var4);
                  }

                  var35.close();
                  String var36 = Hex.toHexString(var34.getDigest());
                  DigestCalculator var37 = this.getDigestCalculator(var9, var30);
                  OutputStream var38 = var37.getOutputStream();
                  if (var11.contains("missing")) {
                     this.update(var38, var33);
                     this.update(var38, ":");
                     this.update(var38, var7);
                     this.update(var38, ":");
                     this.update(var38, var36);
                  } else {
                     this.update(var38, var33);
                     this.update(var38, ":");
                     this.update(var38, var7);
                     this.update(var38, ":");
                     this.update(var38, "00000001");
                     this.update(var38, ":");
                     this.update(var38, var15);
                     this.update(var38, ":");
                     if (((String)var11.get(0)).equals("auth-int")) {
                        this.update(var38, "auth-int");
                     } else {
                        this.update(var38, "auth");
                     }

                     this.update(var38, ":");
                     this.update(var38, var36);
                  }

                  var38.close();
                  String var23 = Hex.toHexString(var37.getDigest());
                  HashMap var24 = new HashMap();
                  var24.put("username", this.username);
                  var24.put("realm", var29);
                  var24.put("nonce", var7);
                  var24.put("uri", var4);
                  var24.put("response", var23);
                  if (((String)var11.get(0)).equals("auth-int")) {
                     var24.put("qop", "auth-int");
                     var24.put("nc", "00000001");
                     var24.put("cnonce", var15);
                  } else if (((String)var11.get(0)).equals("auth")) {
                     var24.put("qop", "auth");
                     var24.put("nc", "00000001");
                     var24.put("cnonce", var15);
                  }

                  var24.put("algorithm", var9);
                  if (var8 == null || var8.length() == 0) {
                     var24.put("opaque", this.makeNonce(20));
                  }

                  ESTRequestBuilder var25 = (new ESTRequestBuilder(var2)).withHijacker((ESTHijacker)null);
                  var25.setHeader("Authorization", HttpUtil.mergeCSL("Digest", var24));
                  return var2.getClient().doRequest(var25.build());
               } else {
                  throw new IOException("auth digest algorithm unknown: " + var9);
               }
            }
         }
      }
   }

   private DigestCalculator getDigestCalculator(String var1, AlgorithmIdentifier var2) throws IOException {
      try {
         DigestCalculator var3 = this.digestCalculatorProvider.get(var2);
         return var3;
      } catch (OperatorCreationException var5) {
         throw new IOException("cannot create digest calculator for " + var1 + ": " + var5.getMessage());
      }
   }

   private AlgorithmIdentifier lookupDigest(String var1) {
      if (var1.endsWith("-SESS")) {
         var1 = var1.substring(0, var1.length() - "-SESS".length());
      }

      return var1.equals("SHA-512-256") ? new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512_256, DERNull.INSTANCE) : digestAlgorithmIdentifierFinder.find(var1);
   }

   private void update(OutputStream var1, char[] var2) throws IOException {
      var1.write(Strings.toUTF8ByteArray(var2));
   }

   private void update(OutputStream var1, String var2) throws IOException {
      var1.write(Strings.toUTF8ByteArray(var2));
   }

   private String makeNonce(int var1) {
      byte[] var2 = new byte[var1];
      this.nonceGenerator.nextBytes(var2);
      return Hex.toHexString(var2);
   }

   static {
      HashSet var0 = new HashSet();
      var0.add("realm");
      var0.add("nonce");
      var0.add("opaque");
      var0.add("algorithm");
      var0.add("qop");
      validParts = Collections.unmodifiableSet(var0);
   }
}
