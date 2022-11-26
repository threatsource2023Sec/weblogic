package org.python.bouncycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.x509.CertificatePair;
import org.python.bouncycastle.jce.X509LDAPCertStoreParameters;

public class X509LDAPCertStoreSpi extends CertStoreSpi {
   private X509LDAPCertStoreParameters params;
   private static String LDAP_PROVIDER = "com.sun.jndi.ldap.LdapCtxFactory";
   private static String REFERRALS_IGNORE = "ignore";
   private static final String SEARCH_SECURITY_LEVEL = "none";
   private static final String URL_CONTEXT_PREFIX = "com.sun.jndi.url";

   public X509LDAPCertStoreSpi(CertStoreParameters var1) throws InvalidAlgorithmParameterException {
      super(var1);
      if (!(var1 instanceof X509LDAPCertStoreParameters)) {
         throw new InvalidAlgorithmParameterException(X509LDAPCertStoreSpi.class.getName() + ": parameter must be a " + X509LDAPCertStoreParameters.class.getName() + " object\n" + var1.toString());
      } else {
         this.params = (X509LDAPCertStoreParameters)var1;
      }
   }

   private DirContext connectLDAP() throws NamingException {
      Properties var1 = new Properties();
      var1.setProperty("java.naming.factory.initial", LDAP_PROVIDER);
      var1.setProperty("java.naming.batchsize", "0");
      var1.setProperty("java.naming.provider.url", this.params.getLdapURL());
      var1.setProperty("java.naming.factory.url.pkgs", "com.sun.jndi.url");
      var1.setProperty("java.naming.referral", REFERRALS_IGNORE);
      var1.setProperty("java.naming.security.authentication", "none");
      InitialDirContext var2 = new InitialDirContext(var1);
      return var2;
   }

   private String parseDN(String var1, String var2) {
      int var4 = var1.toLowerCase().indexOf(var2.toLowerCase());
      String var3 = var1.substring(var4 + var2.length());
      int var5 = var3.indexOf(44);
      if (var5 == -1) {
         var5 = var3.length();
      }

      while(var3.charAt(var5 - 1) == '\\') {
         var5 = var3.indexOf(44, var5 + 1);
         if (var5 == -1) {
            var5 = var3.length();
         }
      }

      var3 = var3.substring(0, var5);
      var4 = var3.indexOf(61);
      var3 = var3.substring(var4 + 1);
      if (var3.charAt(0) == ' ') {
         var3 = var3.substring(1);
      }

      if (var3.startsWith("\"")) {
         var3 = var3.substring(1);
      }

      if (var3.endsWith("\"")) {
         var3 = var3.substring(0, var3.length() - 1);
      }

      return var3;
   }

   public Collection engineGetCertificates(CertSelector var1) throws CertStoreException {
      if (!(var1 instanceof X509CertSelector)) {
         throw new CertStoreException("selector is not a X509CertSelector");
      } else {
         X509CertSelector var2 = (X509CertSelector)var1;
         HashSet var3 = new HashSet();
         Set var4 = this.getEndCertificates(var2);
         var4.addAll(this.getCACertificates(var2));
         var4.addAll(this.getCrossCertificates(var2));
         Iterator var5 = var4.iterator();

         try {
            CertificateFactory var6 = CertificateFactory.getInstance("X.509", "BC");

            while(true) {
               byte[] var7;
               do {
                  do {
                     if (!var5.hasNext()) {
                        return var3;
                     }

                     var7 = (byte[])((byte[])var5.next());
                  } while(var7 == null);
               } while(var7.length == 0);

               ArrayList var8 = new ArrayList();
               var8.add(var7);

               try {
                  CertificatePair var9 = CertificatePair.getInstance((new ASN1InputStream(var7)).readObject());
                  var8.clear();
                  if (var9.getForward() != null) {
                     var8.add(var9.getForward().getEncoded());
                  }

                  if (var9.getReverse() != null) {
                     var8.add(var9.getReverse().getEncoded());
                  }
               } catch (IOException var13) {
               } catch (IllegalArgumentException var14) {
               }

               Iterator var16 = var8.iterator();

               while(var16.hasNext()) {
                  ByteArrayInputStream var10 = new ByteArrayInputStream((byte[])((byte[])var16.next()));

                  try {
                     Certificate var11 = var6.generateCertificate(var10);
                     if (var2.match(var11)) {
                        var3.add(var11);
                     }
                  } catch (Exception var12) {
                  }
               }
            }
         } catch (Exception var15) {
            throw new CertStoreException("certificate cannot be constructed from LDAP result: " + var15);
         }
      }
   }

   private Set certSubjectSerialSearch(X509CertSelector var1, String[] var2, String var3, String var4) throws CertStoreException {
      HashSet var5 = new HashSet();

      try {
         if (var1.getSubjectAsBytes() == null && var1.getSubjectAsString() == null && var1.getCertificate() == null) {
            var5.addAll(this.search(var3, "*", var2));
         } else {
            String var6 = null;
            String var7 = null;
            if (var1.getCertificate() != null) {
               var6 = var1.getCertificate().getSubjectX500Principal().getName("RFC1779");
               var7 = var1.getCertificate().getSerialNumber().toString();
            } else if (var1.getSubjectAsBytes() != null) {
               var6 = (new X500Principal(var1.getSubjectAsBytes())).getName("RFC1779");
            } else {
               var6 = var1.getSubjectAsString();
            }

            String var8 = this.parseDN(var6, var4);
            var5.addAll(this.search(var3, "*" + var8 + "*", var2));
            if (var7 != null && this.params.getSearchForSerialNumberIn() != null) {
               var3 = this.params.getSearchForSerialNumberIn();
               var5.addAll(this.search(var3, "*" + var7 + "*", var2));
            }
         }

         return var5;
      } catch (IOException var9) {
         throw new CertStoreException("exception processing selector: " + var9);
      }
   }

   private Set getEndCertificates(X509CertSelector var1) throws CertStoreException {
      String[] var2 = new String[]{this.params.getUserCertificateAttribute()};
      String var3 = this.params.getLdapUserCertificateAttributeName();
      String var4 = this.params.getUserCertificateSubjectAttributeName();
      Set var5 = this.certSubjectSerialSearch(var1, var2, var3, var4);
      return var5;
   }

   private Set getCACertificates(X509CertSelector var1) throws CertStoreException {
      String[] var2 = new String[]{this.params.getCACertificateAttribute()};
      String var3 = this.params.getLdapCACertificateAttributeName();
      String var4 = this.params.getCACertificateSubjectAttributeName();
      Set var5 = this.certSubjectSerialSearch(var1, var2, var3, var4);
      if (var5.isEmpty()) {
         var5.addAll(this.search((String)null, "*", var2));
      }

      return var5;
   }

   private Set getCrossCertificates(X509CertSelector var1) throws CertStoreException {
      String[] var2 = new String[]{this.params.getCrossCertificateAttribute()};
      String var3 = this.params.getLdapCrossCertificateAttributeName();
      String var4 = this.params.getCrossCertificateSubjectAttributeName();
      Set var5 = this.certSubjectSerialSearch(var1, var2, var3, var4);
      if (var5.isEmpty()) {
         var5.addAll(this.search((String)null, "*", var2));
      }

      return var5;
   }

   public Collection engineGetCRLs(CRLSelector var1) throws CertStoreException {
      String[] var2 = new String[]{this.params.getCertificateRevocationListAttribute()};
      if (!(var1 instanceof X509CRLSelector)) {
         throw new CertStoreException("selector is not a X509CRLSelector");
      } else {
         X509CRLSelector var3 = (X509CRLSelector)var1;
         HashSet var4 = new HashSet();
         String var5 = this.params.getLdapCertificateRevocationListAttributeName();
         HashSet var6 = new HashSet();
         Iterator var7;
         String var9;
         if (var3.getIssuerNames() != null) {
            for(var7 = var3.getIssuerNames().iterator(); var7.hasNext(); var6.addAll(this.search(var5, "*" + var9 + "*", var2))) {
               Object var8 = var7.next();
               var9 = null;
               String var10;
               if (var8 instanceof String) {
                  var10 = this.params.getCertificateRevocationListIssuerAttributeName();
                  var9 = this.parseDN((String)var8, var10);
               } else {
                  var10 = this.params.getCertificateRevocationListIssuerAttributeName();
                  var9 = this.parseDN((new X500Principal((byte[])((byte[])var8))).getName("RFC1779"), var10);
               }
            }
         } else {
            var6.addAll(this.search(var5, "*", var2));
         }

         var6.addAll(this.search((String)null, "*", var2));
         var7 = var6.iterator();

         try {
            CertificateFactory var13 = CertificateFactory.getInstance("X.509", "BC");

            while(var7.hasNext()) {
               CRL var12 = var13.generateCRL(new ByteArrayInputStream((byte[])((byte[])var7.next())));
               if (var3.match(var12)) {
                  var4.add(var12);
               }
            }

            return var4;
         } catch (Exception var11) {
            throw new CertStoreException("CRL cannot be constructed from LDAP result " + var11);
         }
      }
   }

   private Set search(String var1, String var2, String[] var3) throws CertStoreException {
      String var4 = var1 + "=" + var2;
      if (var1 == null) {
         var4 = null;
      }

      DirContext var5 = null;
      HashSet var6 = new HashSet();

      try {
         var5 = this.connectLDAP();
         SearchControls var7 = new SearchControls();
         var7.setSearchScope(2);
         var7.setCountLimit(0L);

         for(int var8 = 0; var8 < var3.length; ++var8) {
            String[] var9 = new String[]{var3[var8]};
            var7.setReturningAttributes(var9);
            String var10 = "(&(" + var4 + ")(" + var9[0] + "=*))";
            if (var4 == null) {
               var10 = "(" + var9[0] + "=*)";
            }

            NamingEnumeration var11 = var5.search(this.params.getBaseDN(), var10, var7);

            while(var11.hasMoreElements()) {
               SearchResult var12 = (SearchResult)var11.next();
               NamingEnumeration var13 = ((Attribute)var12.getAttributes().getAll().next()).getAll();

               while(var13.hasMore()) {
                  Object var14 = var13.next();
                  var6.add(var14);
               }
            }
         }
      } catch (Exception var22) {
         throw new CertStoreException("Error getting results from LDAP directory " + var22);
      } finally {
         try {
            if (null != var5) {
               var5.close();
            }
         } catch (Exception var21) {
         }

      }

      return var6;
   }
}
