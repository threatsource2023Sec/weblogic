package com.rsa.certj.provider.db;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchConstraints;
import com.novell.ldap.LDAPSearchResults;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.AttributeValueAssertion;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.db.DatabaseInterface;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/** @deprecated */
public final class LDAPDatabase extends Provider {
   private static final int CERT_TYPE = 0;
   private static final int CRL_TYPE = 1;
   private LDAPConfiguration configuration;
   private String userDN;
   private String password;
   private String[] certAttrs;
   private String[] crlAttrs;
   private String certAttrFilter;
   private String crlAttrFilter;

   /** @deprecated */
   public LDAPDatabase(String var1, LDAPConfiguration var2, String var3, String var4) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("LDAPDatabase.LDAPDatabase: configuration should not be null.");
      } else if (var2.getAuthType() != 1 || var3 != null && var4 != null) {
         this.configuration = var2;
         this.userDN = var3;
         this.password = var4;
         this.certAttrs = this.buildCertCRLAttrList(var2.getCertificateAttrs());
         this.crlAttrs = this.buildCertCRLAttrList(var2.getCertificateRevocationAttrs());
         this.certAttrFilter = this.buildAttrFilter(this.certAttrs);
         this.crlAttrFilter = this.buildAttrFilter(this.crlAttrs);
      } else {
         throw new InvalidParameterException("LDAPDatabase.LDAPDatabase: neither userDN nor password can be null if configuration.authType is LDAP_AUTH_SIMPLE.");
      }
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new a(var1, this.getName());
      } catch (InvalidParameterException var3) {
         throw new ProviderManagementException("LDAPDatabase.instantiate.", var3);
      }
   }

   private String[] buildCertCRLAttrList(String var1) {
      if (var1 != null && !var1.equals("")) {
         String[] var2 = var1.split(",");

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3] = var2[var3].trim();
         }

         return var2;
      } else {
         return null;
      }
   }

   private Vector retrieve(LDAPConnection var1, X500Name var2, boolean var3, String var4, String[] var5, int var6) throws DatabaseException {
      DatabaseException var7 = null;
      LDAPSearchResults var8 = null;
      if (var3) {
         try {
            Vector var9 = this.attributeSearch(var1, var2.toString(true), 0, var4, var5, var6);
            if (var9 != null && !var9.isEmpty()) {
               return var9;
            }
         } catch (DatabaseException var16) {
            var7 = var16;
         }
      }

      if (this.configuration.getSearchScope() == 1) {
         return null;
      } else {
         String var17 = this.buildBaseDN(this.configuration.getBaseDNAttrs(), var2);
         String var10 = this.buildFilter(this.configuration.getSearchFilterAttrs(), var2);
         Vector var11;
         if (!var17.equals("") && !var10.equals("")) {
            try {
               var8 = var1.search(var17, 2, var10, var5, false);
            } catch (LDAPException var15) {
               var7 = new DatabaseException("LDAPDatabase.retrieve: baseDN filter search failed.", var15);
            }

            if (var8 != null) {
               try {
                  var11 = this.processResults(var8, var5, var6);
                  var7 = null;
                  if (var11 != null && !var11.isEmpty()) {
                     return var11;
                  }
               } catch (DatabaseException var14) {
                  var7 = var14;
               }
            }
         }

         if (this.configuration.getSearchScope() == 2) {
            return null;
         } else {
            if (this.configuration.getSearchRoot() != null && !var10.equals("")) {
               try {
                  var8 = var1.search(this.configuration.getSearchRoot(), 2, var10, var5, false);
               } catch (LDAPException var13) {
                  var7 = new DatabaseException("LDAPDatabase.retrieve: subtree filter search failed.", var13);
               }

               if (var8 != null) {
                  try {
                     var11 = this.processResults(var8, var5, var6);
                     var7 = null;
                     if (var11 != null && !var11.isEmpty()) {
                        return var11;
                     }
                  } catch (DatabaseException var12) {
                     var7 = var12;
                  }
               }
            }

            if (this.configuration.getSearchScope() == 3) {
               return null;
            } else if (this.configuration.getSearchRoot() != null) {
               return this.exhaustiveSearch(var1, var4, var5, var6);
            } else if (var7 != null) {
               throw var7;
            } else {
               return null;
            }
         }
      }
   }

   private Vector exhaustiveSearch(LDAPConnection var1, String var2, String[] var3, int var4) throws DatabaseException {
      return this.attributeSearch(var1, this.configuration.getSearchRoot(), 2, var2, var3, var4);
   }

   private Vector attributeSearch(LDAPConnection var1, String var2, int var3, String var4, String[] var5, int var6) throws DatabaseException {
      LDAPSearchResults var7;
      try {
         var7 = var1.search(var2, var3, var4, var5, false);
      } catch (LDAPException var9) {
         throw new DatabaseException("LDAPDatabase.attributeSearch.", var9);
      }

      return this.processResults(var7, var5, var6);
   }

   private String buildAttrFilter(String[] var1) {
      StringBuffer var2 = new StringBuffer();
      var2.append("(|");

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2.append('(');
         var2.append(var1[var3]);
         var2.append("=*)");
      }

      var2.append(')');
      return new String(var2);
   }

   private String buildBaseDN(String var1, X500Name var2) throws DatabaseException {
      StringBuffer var3 = new StringBuffer();
      StringTokenizer var4 = new StringTokenizer(var1, ",");
      int var5 = var4.countTokens();

      for(int var6 = 0; var6 < var5; ++var6) {
         String var7 = var4.nextToken();
         String var8 = this.getNameValueForAttribute(var7.trim(), var2);
         if (var8 != null) {
            if (var3.length() != 0) {
               var3.append(",");
            }

            var3.append(var8);
         }
      }

      return var3.toString();
   }

   private String buildFilter(String var1, X500Name var2) throws DatabaseException {
      Vector var3 = new Vector();
      StringTokenizer var4 = new StringTokenizer(var1, ",");
      int var5 = var4.countTokens();

      for(int var6 = 0; var6 < var5; ++var6) {
         String var7 = var4.nextToken();
         String var8 = this.getNameValueForAttribute(var7.trim(), var2);
         if (var8 != null) {
            var3.addElement(var8);
         }
      }

      if (var3.isEmpty()) {
         return "";
      } else {
         StringBuffer var9 = new StringBuffer();
         if (var3.size() == 1) {
            var9.append('(');
            var9.append((String)var3.elementAt(0));
            var9.append(')');
         } else {
            var9.append("(&");

            for(int var10 = 0; var10 < var3.size(); ++var10) {
               var9.append('(');
               var9.append((String)var3.elementAt(var10));
               var9.append(')');
            }

            var9.append(')');
         }

         return var9.toString();
      }
   }

   private String getNameValueForAttribute(String var1, X500Name var2) throws DatabaseException {
      int var3 = AttributeValueAssertion.findAttributeType(var1);
      if (var3 == -1) {
         throw new DatabaseException("Attribute type string " + var1 + " not recognized.");
      } else {
         AttributeValueAssertion var4 = var2.getAttribute(var3);
         if (var4 == null) {
            return null;
         } else {
            try {
               return var1 + "=" + var4.getStringAttribute();
            } catch (NameException var6) {
               throw new DatabaseException("Could not get value for attribute " + var1);
            }
         }
      }
   }

   private Vector processResults(LDAPSearchResults var1, String[] var2, int var3) throws DatabaseException {
      if (var1 == null) {
         return null;
      } else {
         Vector var4 = new Vector();

         while(var1.hasMore()) {
            try {
               LDAPEntry var5 = var1.next();
               if (var5 instanceof LDAPEntry) {
                  LDAPEntry var6 = (LDAPEntry)var5;

                  for(int var7 = 0; var7 < var2.length; ++var7) {
                     LDAPAttribute var8 = var6.getAttribute(var2[var7]);
                     if (var8 != null) {
                        Enumeration var9 = var8.getByteValues();
                        if (var9 != null) {
                           while(var9.hasMoreElements()) {
                              byte[] var10 = (byte[])var9.nextElement();
                              if (var10 != null) {
                                 if (var3 == 0) {
                                    try {
                                       X509Certificate var11 = new X509Certificate(var10, 0, 0);
                                       var4.addElement(var11);
                                    } catch (Exception var12) {
                                    }
                                 } else if (var3 == 1) {
                                    try {
                                       X509CRL var15 = new X509CRL(var10, 0, 0);
                                       var4.addElement(var15);
                                    } catch (Exception var13) {
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            } catch (LDAPException var14) {
               switch (var14.getResultCode()) {
                  case 3:
                     throw new DatabaseException("LDAPDatabase.processResults: time limit exceeded.", var14);
                  case 4:
                     throw new DatabaseException("LDAPDatabase.processResults: size limit exceeded.", var14);
                  case 11:
                     throw new DatabaseException("LDAPDatabase.processResults: admin limit exceeded.", var14);
               }
            }
         }

         return var4;
      }
   }

   private final class a extends ProviderImplementation implements DatabaseInterface {
      private LDAPConnection b;
      private a c;
      private a d;
      private final Object e;
      private final Object f;

      private a(CertJ var2, String var3) throws InvalidParameterException {
         super(var2, var3);
         this.e = new Object();
         this.f = new Object();
         this.c = null;
         this.d = null;
         this.b = new LDAPConnection();
      }

      public void unregister() {
         try {
            this.b();
         } catch (Exception var2) {
         }

      }

      public void insertCertificate(Certificate var1) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.insertCertificate: LDAP database is read-only.");
      }

      public void insertCRL(CRL var1) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.insertCRL: LDAP database is read-only.");
      }

      public void insertPrivateKeyByCertificate(Certificate var1, JSAFE_PrivateKey var2) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.insertPrivateKeyByCertificate: LDAP database does not store privateKeys.");
      }

      public void insertPrivateKeyByPublicKey(JSAFE_PublicKey var1, JSAFE_PrivateKey var2) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.insertPrivateKeyByPublicKey: LDAP database does not store privateKeys.");
      }

      public int selectCertificateByIssuerAndSerialNumber(X500Name var1, byte[] var2, Vector var3) throws DatabaseException {
         if (var1 != null && var2 != null) {
            this.a();

            try {
               if (LDAPDatabase.this.configuration.getSearchRoot() == null) {
                  throw new DatabaseException("LDAPDatabase$Implementation.selectCertificateByIssuerAndSerialNumber: searchRoot can not be null to perform this search.");
               } else {
                  Vector var4 = LDAPDatabase.this.exhaustiveSearch(this.b, LDAPDatabase.this.certAttrFilter, LDAPDatabase.this.certAttrs, 0);
                  int var5 = 0;
                  Iterator var6 = var4.iterator();

                  while(var6.hasNext()) {
                     Object var7 = var6.next();
                     X509Certificate var8 = (X509Certificate)var7;
                     if (var1.equals(var8.getIssuerName()) && CertJUtils.byteArraysEqual(var2, var8.getSerialNumber())) {
                        ++var5;
                        if (!var3.contains(var8)) {
                           var3.addElement(var8);
                        }
                     }
                  }

                  int var12 = var5;
                  return var12;
               }
            } finally {
               if (LDAPDatabase.this.configuration.getDisconnectBeforeConnect()) {
                  this.b();
               }

            }
         } else {
            throw new DatabaseException("LDAPDatabase$Implementation.selectCertificateByIssuerAndSerialNumber: neither issuerName nor serialNumber should be null.");
         }
      }

      public int selectCertificateBySubject(X500Name var1, Vector var2) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("LDAPDatabase$Implementation.selectCertificateBySubject: subjectName should not be null.");
         } else {
            this.a();

            int var4;
            try {
               Vector var3 = LDAPDatabase.this.retrieve(this.b, var1, true, LDAPDatabase.this.certAttrFilter, LDAPDatabase.this.certAttrs, 0);
               if (var3 != null) {
                  var4 = 0;

                  int var5;
                  for(var5 = 0; var5 < var3.size(); ++var5) {
                     X509Certificate var6 = (X509Certificate)var3.elementAt(var5);
                     if (var1.equals(var6.getSubjectName())) {
                        if (!var2.contains(var6)) {
                           var2.addElement(var6);
                        }

                        ++var4;
                     }
                  }

                  var5 = var4;
                  return var5;
               }

               var4 = 0;
            } finally {
               if (LDAPDatabase.this.configuration.getDisconnectBeforeConnect()) {
                  this.b();
               }

            }

            return var4;
         }
      }

      public int selectCertificateByExtensions(X500Name var1, X509V3Extensions var2, Vector var3) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("LDAPDatabase.selectCertificateByExtensions: baseName should not be null.");
         } else {
            this.a();

            int var5;
            try {
               Vector var4 = LDAPDatabase.this.retrieve(this.b, var1, false, LDAPDatabase.this.certAttrFilter, LDAPDatabase.this.certAttrs, 0);
               if (var4 != null) {
                  var5 = 0;

                  int var6;
                  for(var6 = 0; var6 < var4.size(); ++var6) {
                     X509Certificate var7 = (X509Certificate)var4.elementAt(var6);
                     if (var7.getSubjectName().contains(var1)) {
                        X509V3Extensions var8 = var7.getExtensions();
                        if (CertJUtils.compareExtensions(var2, var8)) {
                           if (!var3.contains(var7)) {
                              var3.addElement(var7);
                           }

                           ++var5;
                        }
                     }
                  }

                  var6 = var5;
                  return var6;
               }

               var5 = 0;
            } finally {
               if (LDAPDatabase.this.configuration.getDisconnectBeforeConnect()) {
                  this.b();
               }

            }

            return var5;
         }
      }

      public boolean isCertificateIteratorSetup() {
         synchronized(this.e) {
            return this.c != null;
         }
      }

      public void setupCertificateIterator() throws DatabaseException {
         this.a();

         try {
            synchronized(this.e) {
               this.c = new a(LDAPDatabase.this.configuration.getSearchRoot(), LDAPDatabase.this.certAttrFilter, LDAPDatabase.this.certAttrs, 0);
            }
         } finally {
            if (LDAPDatabase.this.configuration.getDisconnectBeforeConnect()) {
               this.b();
            }

         }

      }

      public Certificate firstCertificate() throws DatabaseException {
         synchronized(this.e) {
            this.setupCertificateIterator();
            return this.nextCertificate();
         }
      }

      public Certificate nextCertificate() throws DatabaseException {
         synchronized(this.e) {
            if (!this.isCertificateIteratorSetup()) {
               this.setupCertificateIterator();
            }

            if (this.hasMoreCertificates()) {
               return (Certificate)this.c.nextElement();
            } else {
               this.c = null;
               return null;
            }
         }
      }

      public boolean hasMoreCertificates() throws DatabaseException {
         synchronized(this.e) {
            if (!this.isCertificateIteratorSetup()) {
               this.setupCertificateIterator();
            }

            return this.c.hasMoreElements();
         }
      }

      public int selectCRLByIssuerAndTime(X500Name var1, Date var2, Vector var3) throws DatabaseException {
         if (var1 != null && var2 != null) {
            this.a();

            byte var16;
            try {
               Vector var4 = LDAPDatabase.this.retrieve(this.b, var1, true, LDAPDatabase.this.crlAttrFilter, LDAPDatabase.this.crlAttrs, 1);
               if (var4 == null) {
                  byte var15 = 0;
                  return var15;
               }

               Date var5 = new Date(0L);
               X509CRL var6 = null;
               byte var7 = 0;

               for(int var8 = 0; var8 < var4.size(); ++var8) {
                  X509CRL var9 = (X509CRL)var4.elementAt(var8);
                  X500Name var10 = var9.getIssuerName();
                  if (var1.equals(var10)) {
                     Date var11 = var9.getThisUpdate();
                     if (!var11.after(var2) && var11.after(var5)) {
                        var5 = var11;
                        var6 = var9;
                     }
                  }
               }

               if (var6 != null) {
                  if (!var3.contains(var6)) {
                     var3.addElement(var6);
                  }

                  var7 = 1;
               }

               var16 = var7;
            } finally {
               if (LDAPDatabase.this.configuration.getDisconnectBeforeConnect()) {
                  this.b();
               }

            }

            return var16;
         } else {
            throw new DatabaseException("LDAPDatabase$Implementation.selectCRLByIssuerAndTime: neither issuerName nor time should be null.");
         }
      }

      public boolean isCRLIteratorSetup() {
         synchronized(this.f) {
            return this.d != null;
         }
      }

      public void setupCRLIterator() throws DatabaseException {
         this.a();

         try {
            synchronized(this.f) {
               this.d = new a(LDAPDatabase.this.configuration.getSearchRoot(), LDAPDatabase.this.crlAttrFilter, LDAPDatabase.this.crlAttrs, 1);
            }
         } finally {
            if (LDAPDatabase.this.configuration.getDisconnectBeforeConnect()) {
               this.b();
            }

         }

      }

      public CRL firstCRL() throws DatabaseException {
         this.setupCRLIterator();
         return this.nextCRL();
      }

      public CRL nextCRL() throws DatabaseException {
         synchronized(this.f) {
            if (!this.isCRLIteratorSetup()) {
               this.setupCRLIterator();
            }

            if (this.hasMoreCRLs()) {
               return (CRL)this.d.nextElement();
            } else {
               this.d = null;
               return null;
            }
         }
      }

      public boolean hasMoreCRLs() throws DatabaseException {
         synchronized(this.f) {
            if (!this.isCRLIteratorSetup()) {
               this.setupCRLIterator();
            }

            return this.d.hasMoreElements();
         }
      }

      public JSAFE_PrivateKey selectPrivateKeyByCertificate(Certificate var1) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.selectPrivateKeyByCertificate:  LDAP database does not store keys.");
      }

      public JSAFE_PrivateKey selectPrivateKeyByPublicKey(JSAFE_PublicKey var1) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.selectPrivateKeyByPublicKey:  LDAP database does not store keys.");
      }

      public boolean isPrivateKeyIteratorSetup() throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.isPrivateKeyIteratorSetup: LDAP database does not store keys.");
      }

      public void setupPrivateKeyIterator() throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.setupPrivateKeyIterator: LDAP database does not store keys.");
      }

      public JSAFE_PrivateKey firstPrivateKey() throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.firstPrivateKey: LDAP database does not store keys.");
      }

      public JSAFE_PrivateKey nextPrivateKey() throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.nextPrivateKey: LDAP database does not store keys.");
      }

      public boolean hasMorePrivateKeys() throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.hasMorePrivateKeys: LDAP database does not store keys.");
      }

      public void deleteCertificate(X500Name var1, byte[] var2) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.deleteCertificate: LDAP database is read-only.");
      }

      public void deleteCRL(X500Name var1, Date var2) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.deleteCRL: LDAP database is read-only.");
      }

      public void deletePrivateKeyByCertificate(Certificate var1) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.deletePrivateKeyByCertificate: LDAP database does not store keys.");
      }

      public void deletePrivateKeyByPublicKey(JSAFE_PublicKey var1) throws NotSupportedException {
         throw new NotSupportedException("LDAPDatabase$Implementation.deletePrivateKeyByPublicKey: LDAP database does not store keys.");
      }

      private void a() throws DatabaseException {
         if (!this.b.isConnected()) {
            try {
               this.b.connect(LDAPDatabase.this.configuration.getNetworkAddress().getHostName(), LDAPDatabase.this.configuration.getPortNumber());
            } catch (LDAPException var4) {
               throw new DatabaseException("LDAPDatabase$Implementation.connect: LDAP connect call failed.", var4);
            }

            LDAPSearchConstraints var1 = new LDAPSearchConstraints();
            var1.setTimeLimit(LDAPDatabase.this.configuration.getTimeLimit());
            var1.setMaxResults(LDAPDatabase.this.configuration.getSizeLimit());
            this.b.setConstraints(var1);

            try {
               if (LDAPDatabase.this.configuration.getAuthType() == 1) {
                  this.b.bind(3, LDAPDatabase.this.userDN, LDAPDatabase.this.password.getBytes());
               }

            } catch (LDAPException var3) {
               throw new DatabaseException("LDAPDatabase$Implementation.connect: LDAP authenticate call failed.", var3);
            }
         }
      }

      private void b() throws DatabaseException {
         if (this.b.isConnected()) {
            try {
               this.b.disconnect();
            } catch (LDAPException var2) {
               throw new DatabaseException("LDAPDatabase$Implementation.disconnect: LDAP disconnect call failed.", var2);
            }
         }
      }

      public String toString() {
         return "LDAP database provider named: " + super.getName();
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException {
         this(var2, var3);
      }

      final class a implements Enumeration {
         private int b;
         private Vector c;

         private a(String var2, String var3, String[] var4, int var5) throws DatabaseException {
            if (var2 == null) {
               throw new DatabaseException("LDAPDatabase$Iterator.Iterator: searchRoot can not be null to perform iteration operations.");
            } else {
               this.c = LDAPDatabase.this.exhaustiveSearch(a.this.b, var3, var4, var5);
               this.b = 0;
            }
         }

         public boolean hasMoreElements() {
            return this.b < this.c.size();
         }

         public Object nextElement() {
            return this.hasMoreElements() ? this.c.elementAt(this.b++) : null;
         }

         // $FF: synthetic method
         a(String var2, String var3, String[] var4, int var5, Object var6) throws DatabaseException {
            this(var2, var3, var4, var5);
         }
      }
   }
}
