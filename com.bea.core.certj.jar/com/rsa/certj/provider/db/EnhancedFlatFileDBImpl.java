package com.rsa.certj.provider.db;

import com.rsa.certj.CertJ;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.ProviderImplementation;
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
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class EnhancedFlatFileDBImpl extends ProviderImplementation implements DatabaseInterface {
   private List a;
   private List b;
   private List c;
   private int d;
   private int e;
   private int f;
   private final Lock g = new ReentrantLock();
   private final Lock h = new ReentrantLock();
   private final Lock i = new ReentrantLock();
   private EnhancedFlatFileDBAccess j;

   EnhancedFlatFileDBImpl(EnhancedFlatFileDB var1, CertJ var2, String var3) throws InvalidParameterException {
      super(var2, var3);
      this.j = (EnhancedFlatFileDBAccess)EnhancedFlatFileDB.accessHash.get(var1.path);
      if (this.j == null) {
         try {
            this.j = new EnhancedFlatFileDBAccess(var1.path, var1.passphrase, var1.salt);
         } catch (DatabaseException var5) {
            throw new InvalidParameterException("Could not create database using the specified path");
         }

         EnhancedFlatFileDB.accessHash.put(var1.path, this.j);
      }

      ++this.j.references;
   }

   EnhancedFlatFileDBImpl(FlatFileDB var1, CertJ var2, String var3) throws InvalidParameterException {
      super(var2, var3);
      this.j = (FlatFileDBAccess)FlatFileDB.accessHash.get(var1.path);
      if (this.j == null) {
         try {
            this.j = new FlatFileDBAccess(var1.path, var1.passphrase, var1.baseNameLen, var1.prefixLen);
         } catch (DatabaseException var5) {
            throw new InvalidParameterException("Could not create database using the specified path");
         }

         FlatFileDB.accessHash.put(var1.path, this.j);
      }

      ++this.j.references;
   }

   /** @deprecated */
   public void unregister() {
      --this.j.references;
      if (this.j.references == 0) {
         if (this.j instanceof FlatFileDBAccess) {
            FlatFileDB.accessHash.remove(this.j.path);
         } else if (this.j instanceof EnhancedFlatFileDBAccess) {
            EnhancedFlatFileDB.accessHash.remove(this.j.path);
         }
      }

   }

   /** @deprecated */
   public void insertCertificate(Certificate var1) throws DatabaseException {
      if (!(var1 instanceof X509Certificate)) {
         throw new DatabaseException("Error: cert should be an instance of X509Certificate.");
      } else {
         this.j.insertCertificate((X509Certificate)var1, this.certJ);
      }
   }

   /** @deprecated */
   public void insertCRL(CRL var1) throws DatabaseException {
      if (!(var1 instanceof X509CRL)) {
         throw new DatabaseException("Error: crl should be an instance of X509CRL.");
      } else {
         this.j.insertCRL((X509CRL)var1, this.certJ);
      }
   }

   /** @deprecated */
   public void insertPrivateKeyByCertificate(Certificate var1, JSAFE_PrivateKey var2) throws DatabaseException {
      if (var1 == null) {
         throw new DatabaseException("Error: cert should not be null.");
      } else {
         try {
            this.insertPrivateKeyByPublicKey(var1.getSubjectPublicKey(this.certJ.getDevice()), var2);
         } catch (CertificateException var4) {
            throw new DatabaseException("Error: insertPrivateKeyByCertificate.", var4);
         }
      }
   }

   /** @deprecated */
   public void insertPrivateKeyByPublicKey(JSAFE_PublicKey var1, JSAFE_PrivateKey var2) throws DatabaseException {
      if (var1 != null && var2 != null) {
         this.j.insertKey(var1, var2, this.certJ);
      } else {
         throw new DatabaseException("Error: insertPrivateKeyByPublicKey: neither publicKey nor privateKey should be null.");
      }
   }

   /** @deprecated */
   public int selectCertificateByIssuerAndSerialNumber(X500Name var1, byte[] var2, Vector var3) throws DatabaseException {
      if (var3 == null) {
         throw new DatabaseException("Error: certList should not be null.");
      } else if (var1 != null && var2 != null) {
         return this.j.selectCertificate(var1, var2, var3, this.certJ);
      } else {
         throw new DatabaseException("Error: neither issuerName nor serialNumber should be null.");
      }
   }

   /** @deprecated */
   public int selectCertificateBySubject(X500Name var1, Vector var2) throws DatabaseException {
      if (var2 == null) {
         throw new DatabaseException("Error: certList should not be null.");
      } else if (var1 == null) {
         throw new DatabaseException("Error: subjectName should not be null.");
      } else {
         return this.j.selectCertificate(var1, var2);
      }
   }

   /** @deprecated */
   public int selectCertificateByExtensions(X500Name var1, X509V3Extensions var2, Vector var3) throws DatabaseException {
      if (var3 == null) {
         throw new DatabaseException("Error: certList should not be null.");
      } else if (var1 == null && var2 == null) {
         throw new DatabaseException("Error: Either baseName or extensions should have a non-null value.");
      } else {
         return this.j.selectCertificate(var1, var2, var3);
      }
   }

   public boolean isCertificateIteratorSetup() {
      return this.a != null;
   }

   public void setupCertificateIterator() {
      this.g.lock();
      this.a = this.j.allCerts();
      this.d = 0;
      this.g.unlock();
   }

   /** @deprecated */
   public Certificate firstCertificate() throws DatabaseException {
      this.g.lock();

      Certificate var1;
      try {
         this.setupCertificateIterator();
         var1 = this.nextCertificate();
      } finally {
         this.g.unlock();
      }

      return var1;
   }

   /** @deprecated */
   public Certificate nextCertificate() throws DatabaseException {
      this.g.lock();

      X509Certificate var1;
      try {
         if (!this.isCertificateIteratorSetup()) {
            this.setupCertificateIterator();
         }

         if (this.hasMoreCertificates()) {
            var1 = EnhancedFlatFileDBAccess.loadCertFromFile((File)this.a.get(this.d++));
            return var1;
         }

         this.a = null;
         var1 = null;
      } finally {
         this.g.unlock();
      }

      return var1;
   }

   /** @deprecated */
   public boolean hasMoreCertificates() {
      this.g.lock();

      boolean var1;
      try {
         if (!this.isCertificateIteratorSetup()) {
            this.setupCertificateIterator();
         }

         var1 = this.d < this.a.size();
      } finally {
         this.g.unlock();
      }

      return var1;
   }

   /** @deprecated */
   public int selectCRLByIssuerAndTime(X500Name var1, Date var2, Vector var3) throws DatabaseException {
      if (var3 == null) {
         throw new DatabaseException("Error: crlList should not be null.");
      } else if (var1 != null && var2 != null) {
         return this.j.selectCRL(var1, var2, var3, this.certJ);
      } else {
         throw new DatabaseException("Error: neither issuerName nor time should be null.");
      }
   }

   public boolean isCRLIteratorSetup() {
      return this.b != null;
   }

   public void setupCRLIterator() {
      this.h.lock();
      this.b = this.j.allCRLs();
      this.e = 0;
      this.h.unlock();
   }

   /** @deprecated */
   public CRL firstCRL() throws DatabaseException {
      this.h.lock();

      CRL var1;
      try {
         this.setupCRLIterator();
         var1 = this.nextCRL();
      } finally {
         this.h.unlock();
      }

      return var1;
   }

   /** @deprecated */
   public CRL nextCRL() throws DatabaseException {
      this.h.lock();

      X509CRL var1;
      try {
         if (!this.isCRLIteratorSetup()) {
            this.setupCRLIterator();
         }

         if (!this.hasMoreCRLs()) {
            this.b = null;
            var1 = null;
            return var1;
         }

         var1 = EnhancedFlatFileDBAccess.loadCRLFromFile((File)this.b.get(this.e++));
      } finally {
         this.h.unlock();
      }

      return var1;
   }

   /** @deprecated */
   public boolean hasMoreCRLs() {
      this.h.lock();

      boolean var1;
      try {
         if (!this.isCRLIteratorSetup()) {
            this.setupCRLIterator();
         }

         var1 = this.e < this.b.size();
      } finally {
         this.h.unlock();
      }

      return var1;
   }

   /** @deprecated */
   public JSAFE_PrivateKey selectPrivateKeyByCertificate(Certificate var1) throws DatabaseException {
      if (var1 == null) {
         throw new DatabaseException("Error: cert should not be null.");
      } else {
         try {
            return this.selectPrivateKeyByPublicKey(var1.getSubjectPublicKey(this.certJ.getDevice()));
         } catch (CertificateException var3) {
            throw new DatabaseException("Error: selectPrivateKeyByCertificate.", var3);
         }
      }
   }

   /** @deprecated */
   public JSAFE_PrivateKey selectPrivateKeyByPublicKey(JSAFE_PublicKey var1) throws DatabaseException {
      if (var1 == null) {
         throw new DatabaseException("Error: publicKey should not be null.");
      } else {
         return this.j.selectPrivateKey(var1, this.certJ);
      }
   }

   public boolean isPrivateKeyIteratorSetup() {
      return this.c != null;
   }

   public void setupPrivateKeyIterator() {
      this.i.lock();
      this.c = this.j.allKeys();
      this.f = 0;
      this.i.unlock();
   }

   /** @deprecated */
   public JSAFE_PrivateKey firstPrivateKey() throws DatabaseException {
      this.i.lock();

      JSAFE_PrivateKey var1;
      try {
         this.setupPrivateKeyIterator();
         var1 = this.nextPrivateKey();
      } finally {
         this.i.unlock();
      }

      return var1;
   }

   /** @deprecated */
   public JSAFE_PrivateKey nextPrivateKey() throws DatabaseException {
      this.i.lock();

      JSAFE_PrivateKey var1;
      try {
         if (!this.isPrivateKeyIteratorSetup()) {
            this.setupPrivateKeyIterator();
         }

         if (this.hasMorePrivateKeys()) {
            var1 = this.j.loadPrivateKeyFromFile((File)this.c.get(this.f++), this.certJ);
            return var1;
         }

         this.c = null;
         var1 = null;
      } finally {
         this.i.unlock();
      }

      return var1;
   }

   /** @deprecated */
   public boolean hasMorePrivateKeys() {
      this.i.lock();

      boolean var1;
      try {
         if (!this.isPrivateKeyIteratorSetup()) {
            this.setupPrivateKeyIterator();
         }

         var1 = this.f < this.c.size();
      } finally {
         this.i.unlock();
      }

      return var1;
   }

   /** @deprecated */
   public void deleteCertificate(X500Name var1, byte[] var2) throws DatabaseException {
      if (var1 != null && var2 != null) {
         this.j.deleteCertificate(var1, var2, this.certJ);
      } else {
         throw new DatabaseException("Error: neither issuerName nor serialNumber is null.");
      }
   }

   /** @deprecated */
   public void deleteCRL(X500Name var1, Date var2) throws DatabaseException {
      if (var1 != null && var2 != null) {
         this.j.deleteCRL(var1, var2, this.certJ);
      } else {
         throw new DatabaseException("Error: neither issuerName nor lastUpdate should be null.");
      }
   }

   /** @deprecated */
   public void deletePrivateKeyByCertificate(Certificate var1) throws DatabaseException {
      if (var1 == null) {
         throw new DatabaseException("Error: cert should not be null.");
      } else {
         try {
            this.deletePrivateKeyByPublicKey(var1.getSubjectPublicKey(this.certJ.getDevice()));
         } catch (CertificateException var3) {
            throw new DatabaseException("Error: deletePrivateKeyByCertificate", var3);
         }
      }
   }

   /** @deprecated */
   public void deletePrivateKeyByPublicKey(JSAFE_PublicKey var1) throws DatabaseException {
      if (var1 == null) {
         throw new DatabaseException("Error: publicKey should not be null.");
      } else {
         this.j.deleteKey(var1, this.certJ);
      }
   }

   /** @deprecated */
   public String toString() {
      return "Enhanced Flat File database provider named: " + super.getName();
   }
}
