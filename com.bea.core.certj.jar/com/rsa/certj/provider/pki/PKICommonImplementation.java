package com.rsa.certj.provider.pki;

import com.rsa.certj.CertJ;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.provider.TransportImplementation;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIRequestMessage;
import com.rsa.certj.spi.pki.PKIResponseMessage;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/** @deprecated */
public abstract class PKICommonImplementation extends TransportImplementation {
   private static final char CR = '\r';
   private static final char LF = '\n';

   /** @deprecated */
   protected PKICommonImplementation(CertJ var1, String var2) throws InvalidParameterException {
      super(var1, var2);
   }

   /** @deprecated */
   public String urlEncodeRegInfo(Properties var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = var1.size();
         Enumeration var3 = var1.propertyNames();
         String[] var4 = new String[var2];

         for(int var5 = 0; var5 < var2; ++var5) {
            String var6 = (String)var3.nextElement();
            int var7 = 0;
            int var8 = var5 - 1;

            int var9;
            while(var8 >= var7) {
               var9 = (var7 + var8) / 2;
               int var10 = var6.compareTo(var4[var9]);
               if (var10 < 0) {
                  var8 = var9 - 1;
               } else {
                  var7 = var9 + 1;
               }
            }

            for(var9 = var5 - 1; var9 >= var7; --var9) {
               var4[var9 + 1] = var4[var9];
            }

            var4[var7] = var6;
         }

         StringBuffer var11 = new StringBuffer();

         for(int var12 = 0; var12 < var2; ++var12) {
            String var13 = var4[var12];
            var11.append(this.urlEncode(var13));
            var11.append('=');
            var11.append(this.urlEncode(var1.getProperty(var13)));
            var11.append('&');
         }

         var11.setLength(var11.length() - 1);
         return var11.toString();
      }
   }

   /** @deprecated */
   public Properties urlDecodeRegInfo(String var1) throws PKIException {
      Properties var2 = new Properties();
      StringTokenizer var3 = new StringTokenizer(var1, "&");

      while(var3.hasMoreTokens()) {
         String var4 = var3.nextToken();
         StringTokenizer var5 = new StringTokenizer(var4, "=");
         if (!var5.hasMoreTokens()) {
            throw new PKIException("PKICommonImplementation.urlDecodeRegInfo: no property found.");
         }

         String var6 = var5.nextToken();
         if (var5.hasMoreTokens()) {
            String var7 = var5.nextToken();
            if (var5.hasMoreTokens()) {
               throw new PKIException("PKICommonImplementation.urlDecodeRegInfo: property does not conform to the valid format (name=value).");
            }

            try {
               var2.put(this.urlDecode(var6), this.urlDecode(var7));
            } catch (Exception var9) {
               throw new PKIException("PKICommonImplementation.urlDecodeRegInfo: error in URL-decoding.", var9);
            }
         }
      }

      return var2;
   }

   /** @deprecated */
   protected Certificate findCertificateReturned(PKIRequestMessage var1, PKIResponseMessage var2) throws PKIException {
      try {
         Certificate var4 = var1.getCertificateTemplate();
         if (var4 == null) {
            return null;
         } else {
            JSAFE_PublicKey var3 = var4.getSubjectPublicKey(this.certJ.getDevice());
            Certificate[] var5 = var2.getExtraCerts();
            if (var5 != null) {
               for(int var6 = 0; var6 < var5.length; ++var6) {
                  X509Certificate var7 = (X509Certificate)var5[var6];
                  if (var3.equals(var7.getSubjectPublicKey(this.certJ.getDevice()))) {
                     return var7;
                  }
               }
            }

            return null;
         }
      } catch (CertificateException var8) {
         throw new PKIException("PKICommonImplementation.findCertificateReturned: ", var8);
      }
   }

   /** @deprecated */
   protected void disperseCertsAndCRLs(PKIResponseMessage var1, DatabaseService var2) throws PKIException {
      try {
         Certificate[] var3 = var1.getExtraCerts();
         if (var3 != null) {
            var2.insertCertificates(var3);
         }

         CRL[] var4 = var1.getExtraCRLs();
         if (var4 != null) {
            var2.insertCRLs(var1.getExtraCRLs());
         }

      } catch (NoServiceException var5) {
         throw new PKIException("PKICommonImplementation.disperseCertsAndCRLs: ", var5);
      } catch (DatabaseException var6) {
         throw new PKIException("PKICommonImplementation.disperseCertsAndCRLs: ", var6);
      }
   }

   /** @deprecated */
   protected void loadConfig(Hashtable var1) throws PKIException {
      Vector var2 = (Vector)var1.get("dest");
      if (var2 == null) {
         this.destList = new String[0];
      } else {
         this.destList = new String[var2.size()];
         var2.copyInto(this.destList);
      }

      var2 = (Vector)var1.get("http.proxy");
      if (var2 == null) {
         this.proxyList = new String[0];
      } else {
         this.proxyList = new String[var2.size()];
         var2.copyInto(this.proxyList);
      }

      var2 = (Vector)var1.get("profile");
      if (var2 != null && !var2.isEmpty()) {
         this.profile = (String)var2.elementAt(0);
      } else {
         this.profile = null;
      }

      var2 = (Vector)var1.get("timeoutSecs");
      if (var2 != null && !var2.isEmpty()) {
         String var3 = (String)var2.elementAt(0);
         this.timeoutSecs = 0;

         for(int var4 = 0; var4 < var3.length(); ++var4) {
            int var5 = Character.digit(var3.charAt(var4), 10);
            if (var5 == -1) {
               throw new PKIException("PKICommonImplementation.PKICommonImplementation: non-numeric character used in timeoutSecs.");
            }

            this.timeoutSecs = this.timeoutSecs * 10 + var5;
         }
      } else {
         this.timeoutSecs = -1;
      }

   }

   /** @deprecated */
   public static Hashtable loadProperties(InputStream var0) throws InvalidParameterException {
      Hashtable var1 = new Hashtable();

      while(true) {
         String var2;
         int var3;
         int var4;
         do {
            do {
               if ((var2 = readLine(var0)) == null) {
                  return var1;
               }

               var3 = var2.length();
               var4 = var2.indexOf(61, 0);
            } while(var4 < 0);
         } while(var4 == var3);

         String var5 = removeExtraSpaces(var2.substring(0, var4));
         Vector var6 = (Vector)var1.get(var5);
         if (var6 == null) {
            var6 = new Vector();
            var1.put(var5, var6);
         }

         String var7 = var2.substring(var4 + 1, var3);
         StringTokenizer var8 = new StringTokenizer(var7, ",");

         while(var8.hasMoreTokens()) {
            var6.addElement(removeExtraSpaces(var8.nextToken()));
         }
      }
   }

   private static String readLine(InputStream var0) throws InvalidParameterException {
      StringBuffer var1;
      try {
         int var2 = var0.read();
         if (var2 == -1) {
            return null;
         }

         for(var1 = new StringBuffer(); var2 != -1 && var2 != 10; var2 = var0.read()) {
            if (var2 != 13) {
               var1.append((char)var2);
            }
         }
      } catch (IOException var3) {
         throw new InvalidParameterException("PKICommonImplementation.readLine: reading from " + var0.toString() + " failed(", var3);
      }

      return var1.toString();
   }

   private static String removeExtraSpaces(String var0) {
      int var1 = 0;

      int var2;
      for(var2 = var0.length(); var0.charAt(var1) == ' '; ++var1) {
      }

      while(var0.charAt(var2 - 1) == ' ') {
         --var2;
      }

      return var0.substring(var1, var2);
   }

   private String urlDecode(String var1) throws UnsupportedEncodingException {
      return java.net.URLDecoder.decode(var1, "Cp1252");
   }

   private String urlEncode(String var1) {
      try {
         return URLEncoder.encode(var1, "Cp1252");
      } catch (UnsupportedEncodingException var3) {
         throw new RuntimeException(var3);
      }
   }
}
