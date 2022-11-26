package com.rsa.certj;

import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.db.DatabaseInterface;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/** @deprecated */
public final class DatabaseService extends Service {
   private int currentCertIterator = -1;
   private int currentCrlIterator = -1;

   /** @deprecated */
   public DatabaseService(CertJ var1) {
      super(var1);
   }

   /** @deprecated */
   public void insertCertificate(Certificate var1) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var2 = true;
      String var3 = "";

      for(int var4 = 0; var4 < this.getProviderCount(); ++var4) {
         try {
            ((DatabaseInterface)this.getProviderAt(var4)).insertCertificate(var1);
            var2 = false;
         } catch (NotSupportedException var6) {
            var3 = var3 + "/" + var6.getMessage();
         }
      }

      if (var2) {
         throw new NoServiceException("DatabaseService.insertCertificate: no provider is found to handle this method(" + var3.substring(1) + ").");
      }
   }

   /** @deprecated */
   public void insertCertificates(Certificate[] var1) throws NoServiceException, DatabaseException {
      if (var1 == null) {
         throw new DatabaseException("DatabaseService.insertCertificates: certs should not be null.");
      } else {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            this.insertCertificate(var1[var2]);
         }

      }
   }

   /** @deprecated */
   public void insertCRL(CRL var1) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var2 = true;
      String var3 = "";

      for(int var4 = 0; var4 < this.getProviderCount(); ++var4) {
         try {
            ((DatabaseInterface)this.getProviderAt(var4)).insertCRL(var1);
            var2 = false;
         } catch (NotSupportedException var6) {
            var3 = var3 + "/" + var6.getMessage();
         }
      }

      if (var2) {
         throw new NoServiceException("DatabaseService.insertCRL: no provider is found to handle this method(" + var3.substring(1) + ").");
      }
   }

   /** @deprecated */
   public void insertCRLs(CRL[] var1) throws NoServiceException, DatabaseException {
      if (var1 == null) {
         throw new DatabaseException("DatabaseService.insertCRLs: crls should not be null.");
      } else {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            this.insertCRL(var1[var2]);
         }

      }
   }

   /** @deprecated */
   public void insertPrivateKeyByCertificate(Certificate var1, JSAFE_PrivateKey var2) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var3 = true;
      String var4 = "";

      for(int var5 = 0; var5 < this.getProviderCount(); ++var5) {
         try {
            ((DatabaseInterface)this.getProviderAt(var5)).insertPrivateKeyByCertificate(var1, var2);
            var3 = false;
         } catch (NotSupportedException var7) {
            var4 = var4 + "/" + var7.getMessage();
         }
      }

      if (var3) {
         throw new NoServiceException("DatabaseService.insertPrivateKeyByCertificate: no provider is found to handle this method(" + var4.substring(1) + ").");
      }
   }

   /** @deprecated */
   public void insertPrivateKeyByPublicKey(JSAFE_PublicKey var1, JSAFE_PrivateKey var2) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var3 = true;
      String var4 = "";

      for(int var5 = 0; var5 < this.getProviderCount(); ++var5) {
         try {
            ((DatabaseInterface)this.getProviderAt(var5)).insertPrivateKeyByPublicKey(var1, var2);
            var3 = false;
         } catch (NotSupportedException var7) {
            var4 = var4 + "/" + var7.getMessage();
         }
      }

      if (var3) {
         throw new NoServiceException("DatabaseService.insertPrivateKeyByPublicKey: no provider is found to handle this method(" + var4.substring(1) + ").");
      }
   }

   /** @deprecated */
   public int selectCertificateByIssuerAndSerialNumber(X500Name var1, byte[] var2, Vector var3) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      if (var3 == null) {
         throw new DatabaseException("DatabaseService.selectCertificateByIssuerAndSerialNumber: certList should not be null.");
      } else {
         int var4 = var3.size();
         boolean var5 = true;
         String var6 = "";

         for(int var7 = 0; var7 < this.getProviderCount(); ++var7) {
            try {
               int var8 = ((DatabaseInterface)this.getProviderAt(var7)).selectCertificateByIssuerAndSerialNumber(var1, var2, var3);
               var5 = false;
               if (var8 > 0) {
                  return var3.size() - var4;
               }
            } catch (NotSupportedException var9) {
               var6 = var6 + "/" + var9.getMessage();
            }
         }

         if (var5) {
            throw new NoServiceException("DatabaseService.selectCertificateByIssuerAndSerialNumber: no provider is found to handle this method(" + var6.substring(1) + ").");
         } else {
            return 0;
         }
      }
   }

   /** @deprecated */
   public int selectCertificateBySubject(X500Name var1, Vector var2) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      if (var2 == null) {
         throw new DatabaseException("DatabaseService.selectCertificateBySubject: certList should not be null.");
      } else {
         int var3 = var2.size();
         boolean var4 = true;
         String var5 = "";

         for(int var6 = 0; var6 < this.getProviderCount(); ++var6) {
            try {
               ((DatabaseInterface)this.getProviderAt(var6)).selectCertificateBySubject(var1, var2);
               var4 = false;
            } catch (NotSupportedException var8) {
               var5 = var5 + "/" + var8.getMessage();
            }
         }

         if (var4) {
            throw new NoServiceException("DatabaseService.selectCertificateBySubject: no provider is found to handle this method(" + var5.substring(1) + ").");
         } else {
            return var2.size() - var3;
         }
      }
   }

   /** @deprecated */
   public int selectCertificateByExtensions(X500Name var1, X509V3Extensions var2, Vector var3) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      if (var3 == null) {
         throw new DatabaseException("DatabaseService.selectCertificateByExtensions: certList should not be null.");
      } else {
         int var4 = var3.size();
         boolean var5 = true;
         String var6 = "";

         for(int var7 = 0; var7 < this.getProviderCount(); ++var7) {
            try {
               ((DatabaseInterface)this.getProviderAt(var7)).selectCertificateByExtensions(var1, var2, var3);
               var5 = false;
            } catch (NotSupportedException var9) {
               var6 = var6 + "/" + var9.getMessage();
            }
         }

         if (var5) {
            throw new NoServiceException("DatabaseService.selectCertificateByExtensions: no provider is found to handle this method(" + var6.substring(1) + ").");
         } else {
            return var3.size() - var4;
         }
      }
   }

   /** @deprecated */
   public Certificate firstCertificate() throws NoServiceException, DatabaseException {
      this.setupCertificateIterator();
      return this.nextCertificate();
   }

   /** @deprecated */
   public Certificate nextCertificate() throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      if (!this.isCertificateIteratorSetup()) {
         throw new DatabaseException("DatabaseService.nextCertificate: No certificate iterator has been set!");
      } else {
         int var1 = this.getProviderCount();

         for(Certificate var2 = null; this.currentCertIterator < var1; ++this.currentCertIterator) {
            try {
               var2 = ((DatabaseInterface)this.getProviderAt(this.currentCertIterator)).nextCertificate();
            } catch (NotSupportedException var4) {
            }

            if (var2 != null) {
               return var2;
            }
         }

         return null;
      }
   }

   /** @deprecated */
   public boolean hasMoreCertificates() throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      if (this.currentCertIterator < 0) {
         this.setupCertificateIterator();
      }

      for(int var1 = this.getProviderCount(); this.currentCertIterator < var1; ++this.currentCertIterator) {
         try {
            if (((DatabaseInterface)this.getProviderAt(this.currentCertIterator)).hasMoreCertificates()) {
               return true;
            }
         } catch (NotSupportedException var3) {
         }
      }

      return false;
   }

   /** @deprecated */
   public int selectCRLByIssuerAndTime(X500Name var1, Date var2, Vector var3) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      if (var3 == null) {
         throw new DatabaseException("DatabaseService.selectCRLByIssuerAndTime: crlList should not be null.");
      } else {
         Vector var4 = new Vector();
         boolean var5 = true;
         String var6 = "";

         for(int var7 = 0; var7 < this.getProviderCount(); ++var7) {
            try {
               ((DatabaseInterface)this.getProviderAt(var7)).selectCRLByIssuerAndTime(var1, var2, var4);
               var5 = false;
            } catch (NotSupportedException var13) {
               var6 = var6 + "/" + var13.getMessage();
            }
         }

         if (var5) {
            throw new NoServiceException("DatabaseService.selectCRLByIssuerAndTime: no provider is found to handle this method(" + var6.substring(1) + ").");
         } else {
            Date var14 = new Date(0L);
            X509CRL var8 = null;
            Iterator var9 = var4.iterator();

            while(var9.hasNext()) {
               CRL var10 = (CRL)var9.next();
               X509CRL var11 = (X509CRL)var10;
               Date var12 = var11.getThisUpdate();
               if (var12.after(var14)) {
                  var14 = var12;
                  var8 = var11;
               }
            }

            if (var8 == null) {
               return 0;
            } else {
               if (!var3.contains(var8)) {
                  var3.addElement(var8);
               }

               return 1;
            }
         }
      }
   }

   /** @deprecated */
   public CRL firstCRL() throws NoServiceException, DatabaseException {
      this.setupCRLIterator();
      return this.nextCRL();
   }

   /** @deprecated */
   public CRL nextCRL() throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      if (!this.isCRLIteratorSetup()) {
         throw new DatabaseException("DatabaseService.nextCRL: No CRL iterator has been set up.");
      } else {
         int var1 = this.getProviderCount();

         for(CRL var2 = null; this.currentCrlIterator < var1; ++this.currentCrlIterator) {
            try {
               var2 = ((DatabaseInterface)this.getProviderAt(this.currentCrlIterator)).nextCRL();
            } catch (NotSupportedException var4) {
            }

            if (var2 != null) {
               return var2;
            }
         }

         return null;
      }
   }

   /** @deprecated */
   public boolean hasMoreCRLs() throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      if (this.currentCrlIterator < 0) {
         this.setupCRLIterator();
      }

      for(int var1 = this.getProviderCount(); this.currentCrlIterator < var1; ++this.currentCrlIterator) {
         try {
            if (((DatabaseInterface)this.getProviderAt(this.currentCrlIterator)).hasMoreCRLs()) {
               return true;
            }
         } catch (NotSupportedException var3) {
         }
      }

      return false;
   }

   /** @deprecated */
   public JSAFE_PrivateKey selectPrivateKeyByCertificate(Certificate var1) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var2 = true;
      String var3 = "";

      for(int var5 = 0; var5 < this.getProviderCount(); ++var5) {
         try {
            JSAFE_PrivateKey var4 = ((DatabaseInterface)this.getProviderAt(var5)).selectPrivateKeyByCertificate(var1);
            var2 = false;
            if (var4 != null) {
               return var4;
            }
         } catch (NotSupportedException var7) {
            var3 = var3 + "/" + var7.getMessage();
         }
      }

      if (var2) {
         throw new NoServiceException("DatabaseService.selectPrivateKeyByCertificate: no provider is found to handle this method(" + var3.substring(1) + ").");
      } else {
         return null;
      }
   }

   /** @deprecated */
   public JSAFE_PrivateKey selectPrivateKeyByPublicKey(JSAFE_PublicKey var1) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var2 = true;
      String var3 = "";

      for(int var5 = 0; var5 < this.getProviderCount(); ++var5) {
         try {
            JSAFE_PrivateKey var4 = ((DatabaseInterface)this.getProviderAt(var5)).selectPrivateKeyByPublicKey(var1);
            var2 = false;
            if (var4 != null) {
               return var4;
            }
         } catch (NotSupportedException var7) {
            var3 = var3 + "/" + var7.getMessage();
         }
      }

      if (var2) {
         throw new NoServiceException("DatabaseService.selectPrivateKeyByPublicKey: no provider is found to handle this method(" + var3.substring(1) + ").");
      } else {
         return null;
      }
   }

   /** @deprecated */
   public JSAFE_PrivateKey firstPrivateKey() throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var1 = true;
      String var2 = "";

      for(int var3 = 0; var3 < this.getProviderCount(); ++var3) {
         try {
            JSAFE_PrivateKey var4 = ((DatabaseInterface)this.getProviderAt(var3)).firstPrivateKey();
            var1 = false;
            if (var4 != null) {
               return var4;
            }
         } catch (NotSupportedException var5) {
            var2 = var2 + "/" + var5.getMessage();
         }
      }

      if (var1) {
         throw new NoServiceException("DatabaseService.firstPrivateKey: no provider is found to handle this method(" + var2.substring(1) + ").");
      } else {
         return null;
      }
   }

   /** @deprecated */
   public JSAFE_PrivateKey nextPrivateKey() throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      int var1 = this.findCurrentPrivateKeyIterator();
      if (var1 < 0) {
         throw new DatabaseException("DatabaseService.nextPrivateKey: no iterator is set up. Call firstPrivateKey first.");
      } else {
         JSAFE_PrivateKey var2;
         try {
            var2 = ((DatabaseInterface)this.getProviderAt(var1)).nextPrivateKey();
         } catch (NotSupportedException var6) {
            throw new DatabaseException("DatabaseService.nextPrivateKey: ", var6);
         }

         if (var2 != null) {
            return var2;
         } else {
            for(int var3 = var1 + 1; var3 < this.getProviderCount(); ++var3) {
               try {
                  var2 = ((DatabaseInterface)this.getProviderAt(var3)).firstPrivateKey();
                  if (var2 != null) {
                     return var2;
                  }
               } catch (NotSupportedException var5) {
               }
            }

            return null;
         }
      }
   }

   /** @deprecated */
   public boolean hasMorePrivateKeys() throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      int var1 = this.findCurrentPrivateKeyIterator();
      if (var1 < 0) {
         throw new DatabaseException("DatabaseService.hasMorePrivateKeys: no iterator is set up.");
      } else {
         boolean var2;
         try {
            var2 = ((DatabaseInterface)this.getProviderAt(var1)).hasMorePrivateKeys();
         } catch (NotSupportedException var7) {
            throw new NoServiceException("DatabaseService.hasMorePrivateKeys: a provider is not found to handle this method.");
         }

         if (var2) {
            return true;
         } else {
            for(int var3 = var1 + 1; var3 < this.getProviderCount(); ++var3) {
               DatabaseInterface var4 = (DatabaseInterface)this.getProviderAt(var3);

               try {
                  var4.setupPrivateKeyIterator();
               } catch (NotSupportedException var8) {
                  continue;
               } catch (DatabaseException var9) {
                  throw new NoServiceException("DatabaseService.hasMorePrivateKeys: unable to setup an iterator for a provider.", var9);
               }

               try {
                  if (var4.hasMorePrivateKeys()) {
                     return true;
                  }

                  var4.nextPrivateKey();
               } catch (NotSupportedException var6) {
                  throw new DatabaseException("DatabaseService.hasMorePrivateKeys: ", var6);
               }
            }

            return false;
         }
      }
   }

   /** @deprecated */
   public void deleteCertificate(X500Name var1, byte[] var2) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var3 = true;
      String var4 = "";

      for(int var5 = 0; var5 < this.getProviderCount(); ++var5) {
         try {
            ((DatabaseInterface)this.getProviderAt(var5)).deleteCertificate(var1, var2);
            var3 = false;
         } catch (NotSupportedException var7) {
            var4 = var4 + "/" + var7.getMessage();
         }
      }

      if (var3) {
         throw new NoServiceException("DatabaseService.deleteCertificate: no provider is found to handle this method(" + var4.substring(1) + ").");
      }
   }

   /** @deprecated */
   public void deleteCRL(X500Name var1, Date var2) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var3 = true;
      String var4 = "";

      for(int var5 = 0; var5 < this.getProviderCount(); ++var5) {
         try {
            ((DatabaseInterface)this.getProviderAt(var5)).deleteCRL(var1, var2);
            var3 = false;
         } catch (NotSupportedException var7) {
            var4 = var4 + "/" + var7.getMessage();
         }
      }

      if (var3) {
         throw new NoServiceException("DatabaseService.deleteCRL: no provider is found to handle this method(" + var4.substring(1) + ").");
      }
   }

   /** @deprecated */
   public void deletePrivateKeyByCertificate(Certificate var1) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var2 = true;
      String var3 = "";

      for(int var4 = 0; var4 < this.getProviderCount(); ++var4) {
         try {
            ((DatabaseInterface)this.getProviderAt(var4)).deletePrivateKeyByCertificate(var1);
            var2 = false;
         } catch (NotSupportedException var6) {
            var3 = var3 + "/" + var6.getMessage();
         }
      }

      if (var2) {
         throw new NoServiceException("DatabaseService.deletePrivateKeyByCertificate: no provider is found to handle this method(" + var3.substring(1) + ").");
      }
   }

   /** @deprecated */
   public void deletePrivateKeyByPublicKey(JSAFE_PublicKey var1) throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var2 = true;
      String var3 = "";

      for(int var4 = 0; var4 < this.getProviderCount(); ++var4) {
         try {
            ((DatabaseInterface)this.getProviderAt(var4)).deletePrivateKeyByPublicKey(var1);
            var2 = false;
         } catch (NotSupportedException var6) {
            var3 = var3 + "/" + var6.getMessage();
         }
      }

      if (var2) {
         throw new NoServiceException("DatabaseService.deletePrivateKeyByPublicKey: no provider is found to handle this method(" + var3.substring(1) + ").");
      }
   }

   /** @deprecated */
   public void setupCertificateIterator() throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var1 = true;

      for(int var2 = 0; var2 < this.getProviderCount(); ++var2) {
         try {
            ((DatabaseInterface)this.getProviderAt(var2)).setupCertificateIterator();
         } catch (NotSupportedException var4) {
            continue;
         }

         if (var1) {
            this.currentCertIterator = var2;
            var1 = false;
         }
      }

      if (var1) {
         throw new NoServiceException("DatabaseService.setupCertificateIterator: no provider is found to handle this method.");
      }
   }

   /** @deprecated */
   public void setupCRLIterator() throws NoServiceException, DatabaseException {
      this.checkForEmptyService();
      boolean var1 = true;

      for(int var2 = 0; var2 < this.getProviderCount(); ++var2) {
         try {
            ((DatabaseInterface)this.getProviderAt(var2)).setupCRLIterator();
         } catch (NotSupportedException var4) {
            continue;
         }

         if (var1) {
            this.currentCrlIterator = var2;
            var1 = false;
         }
      }

      if (var1) {
         throw new NoServiceException("DatabaseService.setupCRLIterator: no provider is found to handle this method.");
      }
   }

   private boolean isCertificateIteratorSetup() {
      return this.currentCertIterator >= 0;
   }

   private boolean isCRLIteratorSetup() {
      return this.currentCrlIterator >= 0;
   }

   private int findCurrentPrivateKeyIterator() throws NoServiceException, DatabaseException {
      boolean var1 = true;
      int var2 = -1;
      String var3 = "";

      for(int var4 = 0; var4 < this.getProviderCount(); ++var4) {
         try {
            boolean var5 = ((DatabaseInterface)this.getProviderAt(var4)).isPrivateKeyIteratorSetup();
            var1 = false;
            if (var5) {
               var2 = var4;
               break;
            }
         } catch (NotSupportedException var6) {
            var3 = var3 + "/" + var6.getMessage();
         }
      }

      if (var1) {
         throw new NoServiceException("DatabaseService.findCurrentPrivateKeyIterator: no provider is found to handle isPrivateKeyIteratorSetup method(" + var3.substring(1) + ").");
      } else {
         return var2;
      }
   }

   private void checkForEmptyService() throws NoServiceException {
      if (this.getProviderCount() == 0) {
         throw new NoServiceException("DatabaseService.checkForEmptyService: no Database provider is bound to this service. Use CertJ.registerService followed by CertJ.bindService or CertJ.bindServices to obtain a non-empty DatabaseService object.");
      }
   }
}
