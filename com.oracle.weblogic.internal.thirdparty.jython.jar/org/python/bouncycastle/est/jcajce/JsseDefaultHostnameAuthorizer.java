package org.python.bouncycastle.est.jcajce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLSession;
import org.python.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.python.bouncycastle.asn1.x500.RDN;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x500.style.BCStyle;
import org.python.bouncycastle.est.ESTException;
import org.python.bouncycastle.util.Strings;

public class JsseDefaultHostnameAuthorizer implements JsseHostnameAuthorizer {
   private final Set knownSuffixes;

   public JsseDefaultHostnameAuthorizer(Set var1) {
      this.knownSuffixes = var1;
   }

   public boolean verified(String var1, SSLSession var2) throws IOException {
      try {
         CertificateFactory var3 = CertificateFactory.getInstance("X509");
         X509Certificate var4 = (X509Certificate)var3.generateCertificate(new ByteArrayInputStream(var2.getPeerCertificates()[0].getEncoded()));
         return this.verify(var1, var4);
      } catch (Exception var5) {
         if (var5 instanceof ESTException) {
            throw (ESTException)var5;
         } else {
            throw new ESTException(var5.getMessage(), var5);
         }
      }
   }

   public boolean verify(String var1, X509Certificate var2) throws IOException {
      try {
         Collection var3 = var2.getSubjectAlternativeNames();
         if (var3 != null) {
            Iterator var11 = var3.iterator();

            while(var11.hasNext()) {
               List var12 = (List)var11.next();
               switch (((Number)var12.get(0)).intValue()) {
                  case 2:
                     if (isValidNameMatch(var1, var12.get(1).toString(), this.knownSuffixes)) {
                        return true;
                     }
                     break;
                  case 7:
                     if (InetAddress.getByName(var1).equals(InetAddress.getByName(var12.get(1).toString()))) {
                        return true;
                     }
                     break;
                  default:
                     throw new RuntimeException("Unable to handle ");
               }
            }

            return false;
         }
      } catch (Exception var9) {
         throw new ESTException(var9.getMessage(), var9);
      }

      if (var2.getSubjectX500Principal() == null) {
         return false;
      } else {
         RDN[] var10 = X500Name.getInstance(var2.getSubjectX500Principal().getEncoded()).getRDNs();

         for(int var4 = 0; var4 != var10.length; ++var4) {
            RDN var5 = var10[var4];
            AttributeTypeAndValue[] var6 = var5.getTypesAndValues();

            for(int var7 = 0; var7 != var6.length; ++var7) {
               AttributeTypeAndValue var8 = var6[var7];
               if (var8.getType().equals(BCStyle.CN)) {
                  return isValidNameMatch(var1, var5.getFirst().getValue().toString(), this.knownSuffixes);
               }
            }
         }

         return false;
      }
   }

   public static boolean isValidNameMatch(String var0, String var1, Set var2) throws IOException {
      if (var1.contains("*")) {
         int var3 = var1.indexOf(42);
         if (var3 == var1.lastIndexOf("*")) {
            if (!var1.contains("..") && var1.charAt(var1.length() - 1) != '*') {
               int var4 = var1.indexOf(46, var3);
               if (var2 != null && var2.contains(Strings.toLowerCase(var1.substring(var4)))) {
                  throw new IOException("Wildcard `" + var1 + "` matches known public suffix.");
               } else {
                  String var5 = Strings.toLowerCase(var1.substring(var3 + 1));
                  String var6 = Strings.toLowerCase(var0);
                  if (var6.equals(var5)) {
                     return false;
                  } else if (var5.length() > var6.length()) {
                     return false;
                  } else if (var3 > 0) {
                     if (var6.startsWith(var1.substring(0, var3 - 1)) && var6.endsWith(var5)) {
                        return var6.substring(var3, var6.length() - var5.length()).indexOf(46) < 0;
                     } else {
                        return false;
                     }
                  } else {
                     String var7 = var6.substring(0, var6.length() - var5.length());
                     return var7.indexOf(46) > 0 ? false : var6.endsWith(var5);
                  }
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return var0.equalsIgnoreCase(var1);
      }
   }
}
