package com.rsa.certj.provider.db;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.db.DatabaseInterface;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Date;
import java.util.Vector;

/** @deprecated */
public final class MemoryDB extends Provider {
   private Vector certStoreProvided;
   private Vector crlStoreProvided;
   private Vector privateKeyStoreProvided;
   private Vector publicKeyStoreProvided;

   /** @deprecated */
   public MemoryDB(String var1) throws InvalidParameterException {
      super(1, var1);
   }

   /** @deprecated */
   public MemoryDB(String var1, Vector var2, Vector var3, Vector var4, Vector var5) throws InvalidParameterException {
      super(1, var1);
      if (var4 != null && var5 != null && var4.size() == var5.size()) {
         if (var2 != null) {
            this.certStoreProvided = var2;
         }

         if (var3 != null) {
            this.crlStoreProvided = var3;
         }

         this.privateKeyStoreProvided = var4;
         this.publicKeyStoreProvided = var5;
      } else {
         throw new InvalidParameterException("MemoryDB.MemoryDB: privateKeys and publicKeys should have the same number of elements.");
      }
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new a(var1, this.getName());
      } catch (InvalidParameterException var3) {
         throw new ProviderManagementException("MemoryDB.instantiate.", var3);
      }
   }

   private final class a extends ProviderImplementation implements DatabaseInterface {
      private Vector b;
      private Vector c;
      private Vector d;
      private Vector e;
      private int f;
      private int g;
      private int h;
      private final Object i;
      private final Object j;
      private final Object k;

      private a(CertJ var2, String var3) throws InvalidParameterException {
         super(var2, var3);
         this.i = new Object();
         this.j = new Object();
         this.k = new Object();
         if (MemoryDB.this.certStoreProvided == null) {
            this.b = new Vector();
         } else {
            this.b = MemoryDB.this.certStoreProvided;
         }

         if (MemoryDB.this.crlStoreProvided == null) {
            this.c = new Vector();
         } else {
            this.c = MemoryDB.this.crlStoreProvided;
         }

         if (MemoryDB.this.privateKeyStoreProvided == null) {
            this.d = new Vector();
            this.e = new Vector();
         } else {
            this.d = MemoryDB.this.privateKeyStoreProvided;
            this.e = MemoryDB.this.publicKeyStoreProvided;
         }

         this.f = -1;
         this.g = -1;
         this.h = -1;
      }

      public void insertCertificate(Certificate var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("MemoryDBProvider.insertCertificate: cert should not be null.");
         } else {
            synchronized(this.b) {
               if (!this.b.contains(var1)) {
                  try {
                     this.b.addElement((Certificate)((X509Certificate)var1).clone());
                  } catch (CloneNotSupportedException var5) {
                     throw new DatabaseException("MemoryDBProvider.insertCertificate: Unable to clone the certificate.", var5);
                  }
               }

            }
         }
      }

      public void insertCRL(CRL var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("MemoryDBProvider.insertCRL: crl should not be null.");
         } else {
            synchronized(this.c) {
               if (!this.c.contains(var1)) {
                  try {
                     this.c.addElement((CRL)((X509CRL)var1).clone());
                  } catch (CloneNotSupportedException var5) {
                     throw new DatabaseException("MemoryDBProvider.insertCRL: Unable to clone the CRL.", var5);
                  }
               }

            }
         }
      }

      public void insertPrivateKeyByCertificate(Certificate var1, JSAFE_PrivateKey var2) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("MemoryDB$Implementation.insertPrivateKeyByCertificate: cert should not be null");
         } else {
            try {
               this.insertPrivateKeyByPublicKey(var1.getSubjectPublicKey(this.certJ.getDevice()), var2);
            } catch (CertificateException var4) {
               throw new DatabaseException("MemoryDB$Implementation.insertPrivateKeyByCertificate.", var4);
            }
         }
      }

      public void insertPrivateKeyByPublicKey(JSAFE_PublicKey var1, JSAFE_PrivateKey var2) throws DatabaseException {
         if (var1 != null && var2 != null) {
            synchronized(this.d) {
               if (!this.e.contains(var1)) {
                  try {
                     this.d.addElement((JSAFE_PrivateKey)var2.clone());
                     this.e.addElement((JSAFE_PublicKey)var1.clone());
                  } catch (CloneNotSupportedException var6) {
                     throw new DatabaseException("MemoryDBProvider.insertPrivateKeyByPublicKey: Unable to clone a key.", var6);
                  }
               }

            }
         } else {
            throw new DatabaseException("MemoryDBProvider.insertPrivateKeyByPublicKey: Neither publicKey nor privateKey should be null.");
         }
      }

      public int selectCertificateByIssuerAndSerialNumber(X500Name var1, byte[] var2, Vector var3) throws DatabaseException {
         if (var1 != null && var2 != null) {
            synchronized(this.b) {
               for(int var5 = 0; var5 < this.b.size(); ++var5) {
                  try {
                     X509Certificate var6 = (X509Certificate)this.b.elementAt(var5);
                     if (var1.equals(var6.getIssuerName()) && CertJUtils.byteArraysEqual(var2, var6.getSerialNumber())) {
                        if (!var3.contains(var6)) {
                           var3.addElement((Certificate)var6.clone());
                        }

                        byte var10000 = 1;
                        return var10000;
                     }
                  } catch (CloneNotSupportedException var8) {
                     throw new DatabaseException("MemoryDBProvider.selectCertificateByIssuerAndSerialNumber: Unable to clone a certificate.", var8);
                  } catch (ClassCastException var9) {
                  }
               }

               return 0;
            }
         } else {
            throw new DatabaseException("MemoryDBProvider.selectCertificateByIssuerAndSerialNumber: Neither issuerName nor serialNumber should be null.");
         }
      }

      public int selectCertificateBySubject(X500Name var1, Vector var2) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("MemoryDBProvider.selectCertificateBySubject: subjectName should not be null.");
         } else {
            int var3 = 0;
            synchronized(this.b) {
               for(int var5 = 0; var5 < this.b.size(); ++var5) {
                  try {
                     X509Certificate var6 = (X509Certificate)this.b.elementAt(var5);
                     X500Name var7 = var6.getSubjectName();
                     if (var7.equals(var1)) {
                        if (!var2.contains(var6)) {
                           var2.addElement((Certificate)var6.clone());
                        }

                        ++var3;
                     }
                  } catch (CloneNotSupportedException var9) {
                     throw new DatabaseException("MemoryDBProvider.selectCertificateByIssuerAndSerialNumber: Unable to clone a certificate.", var9);
                  } catch (ClassCastException var10) {
                  }
               }

               return var3;
            }
         }
      }

      public int selectCertificateByExtensions(X500Name var1, X509V3Extensions var2, Vector var3) throws DatabaseException {
         if (var1 == null && var2 == null) {
            throw new DatabaseException("MemoryDB.selectCertificateByExtensions: Either baseName or extensions should have a non-null value.");
         } else {
            int var4 = 0;
            synchronized(this.b) {
               try {
                  for(int var6 = 0; var6 < this.b.size(); ++var6) {
                     X509Certificate var7 = (X509Certificate)this.b.elementAt(var6);
                     if (var1 == null || var7.getSubjectName().contains(var1)) {
                        X509V3Extensions var8 = var7.getExtensions();
                        if (CertJUtils.compareExtensions(var2, var8)) {
                           if (!var3.contains(var7)) {
                              var3.addElement((Certificate)var7.clone());
                           }

                           ++var4;
                        }
                     }
                  }
               } catch (CloneNotSupportedException var10) {
                  throw new DatabaseException("MemoryDBProvider.selectCertificateByExtensions: Unable to clone a certificate.", var10);
               }

               return var4;
            }
         }
      }

      public boolean isCertificateIteratorSetup() {
         synchronized(this.i) {
            return this.f >= 0;
         }
      }

      public void setupCertificateIterator() {
         synchronized(this.i) {
            this.f = 0;
         }
      }

      public Certificate firstCertificate() throws DatabaseException {
         synchronized(this.i) {
            this.setupCertificateIterator();
            Certificate var10000;
            synchronized(this.b) {
               if (this.b.isEmpty()) {
                  this.f = -1;
                  var10000 = null;
                  return var10000;
               }

               try {
                  X509Certificate var3 = (X509Certificate)this.b.elementAt(this.f++);
                  var10000 = (Certificate)var3.clone();
               } catch (CloneNotSupportedException var6) {
                  throw new DatabaseException("MemoryDBProvider.firstCertificate: Unable to clone a certificate.", var6);
               }
            }

            return var10000;
         }
      }

      public Certificate nextCertificate() throws DatabaseException {
         synchronized(this.i) {
            if (!this.isCertificateIteratorSetup()) {
               this.setupCertificateIterator();
            }

            Certificate var10000;
            synchronized(this.b) {
               if (this.f >= this.b.size()) {
                  this.f = -1;
                  var10000 = null;
                  return var10000;
               }

               try {
                  X509Certificate var3 = (X509Certificate)this.b.elementAt(this.f++);
                  var10000 = (Certificate)var3.clone();
               } catch (CloneNotSupportedException var6) {
                  throw new DatabaseException("MemoryDBProvider.nextCertificate: Unable to clone a certificate.", var6);
               }
            }

            return var10000;
         }
      }

      public boolean hasMoreCertificates() {
         synchronized(this.i) {
            if (!this.isCertificateIteratorSetup()) {
               this.setupCertificateIterator();
            }

            boolean var10000;
            synchronized(this.b) {
               var10000 = this.f < this.b.size();
            }

            return var10000;
         }
      }

      public int selectCRLByIssuerAndTime(X500Name var1, Date var2, Vector var3) throws DatabaseException {
         if (var1 != null && var2 != null) {
            Date var4 = new Date(0L);
            X509CRL var5 = null;
            synchronized(this.c) {
               for(int var7 = 0; var7 < this.c.size(); ++var7) {
                  try {
                     X509CRL var8 = (X509CRL)this.c.elementAt(var7);
                     X500Name var9 = var8.getIssuerName();
                     if (var1.equals(var9)) {
                        Date var10 = var8.getThisUpdate();
                        if (!var10.after(var2) && var10.after(var4)) {
                           var4 = var10;
                           var5 = var8;
                        }
                     }
                  } catch (ClassCastException var13) {
                  }
               }

               if (var5 == null) {
                  return 0;
               } else {
                  if (!var3.contains(var5)) {
                     try {
                        var3.addElement((CRL)var5.clone());
                     } catch (CloneNotSupportedException var12) {
                        throw new DatabaseException("MemoryDBProvider.selectCRLByIssuerAndTime: Unable to clone a CRL.", var12);
                     }
                  }

                  return 1;
               }
            }
         } else {
            throw new DatabaseException("MemoryDBProvider.selectCRLByIssuerAndTime: Neither issuerName nor time should be null.");
         }
      }

      public boolean isCRLIteratorSetup() {
         synchronized(this.j) {
            return this.g >= 0;
         }
      }

      public void setupCRLIterator() {
         synchronized(this.j) {
            this.g = 0;
         }
      }

      public CRL firstCRL() throws DatabaseException {
         synchronized(this.j) {
            this.setupCRLIterator();
            CRL var10000;
            synchronized(this.c) {
               if (this.c.isEmpty()) {
                  this.g = -1;
                  var10000 = null;
                  return var10000;
               }

               try {
                  X509CRL var3 = (X509CRL)this.c.elementAt(this.g++);
                  var10000 = (CRL)var3.clone();
               } catch (CloneNotSupportedException var6) {
                  throw new DatabaseException("MemoryDBProvider.firstCRL: Unable to clone a CRL.", var6);
               }
            }

            return var10000;
         }
      }

      public CRL nextCRL() throws DatabaseException {
         synchronized(this.j) {
            if (!this.isCRLIteratorSetup()) {
               this.setupCRLIterator();
            }

            CRL var10000;
            synchronized(this.c) {
               if (this.g >= this.c.size()) {
                  this.g = -1;
                  var10000 = null;
                  return var10000;
               }

               try {
                  X509CRL var3 = (X509CRL)this.c.elementAt(this.g++);
                  var10000 = (CRL)var3.clone();
               } catch (CloneNotSupportedException var6) {
                  throw new DatabaseException("MemoryDBProvider.nextCRL: Unable to clone a CRL.", var6);
               }
            }

            return var10000;
         }
      }

      public boolean hasMoreCRLs() {
         synchronized(this.j) {
            if (!this.isCRLIteratorSetup()) {
               this.setupCRLIterator();
            }

            boolean var10000;
            synchronized(this.c) {
               var10000 = this.g < this.c.size();
            }

            return var10000;
         }
      }

      public JSAFE_PrivateKey selectPrivateKeyByCertificate(Certificate var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("MemoryDB$Implementation.selectPrivateKeyByCertificate: cert should not be null.");
         } else {
            try {
               return this.selectPrivateKeyByPublicKey(var1.getSubjectPublicKey(this.certJ.getDevice()));
            } catch (CertificateException var3) {
               throw new DatabaseException("MemoryDB$Implementation.selectPrivateKeyByCertificate.", var3);
            }
         }
      }

      public JSAFE_PrivateKey selectPrivateKeyByPublicKey(JSAFE_PublicKey var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("MemoryDB$Implementation.selectPrivateKeyByPublicKey: publicKey should not be null.");
         } else {
            synchronized(this.d) {
               for(int var3 = 0; var3 < this.e.size(); ++var3) {
                  if (var1.equals(this.e.elementAt(var3))) {
                     JSAFE_PrivateKey var10000;
                     try {
                        JSAFE_PrivateKey var4 = (JSAFE_PrivateKey)this.d.elementAt(var3);
                        var10000 = (JSAFE_PrivateKey)var4.clone();
                     } catch (CloneNotSupportedException var6) {
                        throw new DatabaseException("MemoryDB$Implementation.selectPrivateKeyByPublicKeyUnable to clone a private key().");
                     }

                     return var10000;
                  }
               }

               return null;
            }
         }
      }

      public boolean isPrivateKeyIteratorSetup() {
         synchronized(this.k) {
            return this.h >= 0;
         }
      }

      public void setupPrivateKeyIterator() {
         synchronized(this.k) {
            this.h = 0;
         }
      }

      public JSAFE_PrivateKey firstPrivateKey() throws DatabaseException {
         synchronized(this.k) {
            this.setupPrivateKeyIterator();
            JSAFE_PrivateKey var10000;
            synchronized(this.d) {
               if (this.d.isEmpty()) {
                  this.h = -1;
                  var10000 = null;
                  return var10000;
               }

               try {
                  JSAFE_PrivateKey var3 = (JSAFE_PrivateKey)this.d.elementAt(this.h++);
                  var10000 = (JSAFE_PrivateKey)var3.clone();
               } catch (CloneNotSupportedException var6) {
                  throw new DatabaseException("MemoryDB$Implementation.firstPrivateKey: Unable to clone a private key.", var6);
               }
            }

            return var10000;
         }
      }

      public JSAFE_PrivateKey nextPrivateKey() throws DatabaseException {
         synchronized(this.k) {
            if (!this.isPrivateKeyIteratorSetup()) {
               this.setupPrivateKeyIterator();
            }

            JSAFE_PrivateKey var10000;
            synchronized(this.d) {
               if (this.h >= this.d.size()) {
                  this.h = -1;
                  var10000 = null;
                  return var10000;
               }

               try {
                  JSAFE_PrivateKey var3 = (JSAFE_PrivateKey)this.d.elementAt(this.h++);
                  var10000 = (JSAFE_PrivateKey)var3.clone();
               } catch (CloneNotSupportedException var6) {
                  throw new DatabaseException("MemoryDB$Implementation.nextPrivateKey: Unable to clone a private key.", var6);
               }
            }

            return var10000;
         }
      }

      public boolean hasMorePrivateKeys() {
         synchronized(this.k) {
            if (!this.isPrivateKeyIteratorSetup()) {
               this.setupPrivateKeyIterator();
            }

            boolean var10000;
            synchronized(this.d) {
               var10000 = this.h < this.d.size();
            }

            return var10000;
         }
      }

      public void deleteCertificate(X500Name var1, byte[] var2) throws DatabaseException {
         if (var1 != null && var2 != null) {
            synchronized(this.b) {
               for(int var4 = 0; var4 < this.b.size(); ++var4) {
                  try {
                     X509Certificate var5 = (X509Certificate)this.b.elementAt(var4);
                     if (var1.equals(var5.getIssuerName()) && CertJUtils.byteArraysEqual(var2, var5.getSerialNumber())) {
                        this.b.removeElementAt(var4);
                     }
                  } catch (ClassCastException var7) {
                  }
               }

            }
         } else {
            throw new DatabaseException("MemoryDB$Implementation.deleteCertificate: Neither issuerName nor serialNumber should be null.");
         }
      }

      public void deleteCRL(X500Name var1, Date var2) throws DatabaseException {
         if (var1 != null && var2 != null) {
            synchronized(this.c) {
               for(int var4 = 0; var4 < this.c.size(); ++var4) {
                  try {
                     X509CRL var5 = (X509CRL)this.c.elementAt(var4);
                     if (var1.equals(var5.getIssuerName()) && var5.getThisUpdate().equals(var2)) {
                        this.c.removeElementAt(var4);
                     }
                  } catch (ClassCastException var8) {
                  }
               }

            }
         } else {
            throw new DatabaseException("MemoryDB$Implementation.deleteCRL: Neither issuerName nor lastUpdate should be null.");
         }
      }

      public void deletePrivateKeyByCertificate(Certificate var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("MemoryDB$Implementation.deletePrivateKeyByCertificate: cert should not be null.");
         } else {
            try {
               this.deletePrivateKeyByPublicKey(var1.getSubjectPublicKey(this.certJ.getDevice()));
            } catch (CertificateException var3) {
               throw new DatabaseException("MemoryDB$Implementation.deletePrivateKeyByCertificate.", var3);
            }
         }
      }

      public void deletePrivateKeyByPublicKey(JSAFE_PublicKey var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("MemoryDB$Implementation.deletePrivateKeyByPublicKey: publickKey should not be null.");
         } else {
            synchronized(this.d) {
               for(int var3 = 0; var3 < this.d.size(); ++var3) {
                  if (var1.equals(this.e.elementAt(var3))) {
                     this.d.removeElementAt(var3);
                     this.e.removeElementAt(var3);
                     return;
                  }
               }

            }
         }
      }

      public String toString() {
         return "In-memory database provider named: " + super.getName();
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException {
         this(var2, var3);
      }
   }
}
