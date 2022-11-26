package com.rsa.certj.spi.db;

import com.rsa.certj.NotSupportedException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Date;
import java.util.Vector;

/** @deprecated */
public interface DatabaseInterface {
   void insertCertificate(Certificate var1) throws NotSupportedException, DatabaseException;

   void insertCRL(CRL var1) throws NotSupportedException, DatabaseException;

   void insertPrivateKeyByCertificate(Certificate var1, JSAFE_PrivateKey var2) throws NotSupportedException, DatabaseException;

   void insertPrivateKeyByPublicKey(JSAFE_PublicKey var1, JSAFE_PrivateKey var2) throws NotSupportedException, DatabaseException;

   int selectCertificateByIssuerAndSerialNumber(X500Name var1, byte[] var2, Vector var3) throws NotSupportedException, DatabaseException;

   int selectCertificateBySubject(X500Name var1, Vector var2) throws NotSupportedException, DatabaseException;

   int selectCertificateByExtensions(X500Name var1, X509V3Extensions var2, Vector var3) throws NotSupportedException, DatabaseException;

   boolean isCertificateIteratorSetup() throws NotSupportedException, DatabaseException;

   void setupCertificateIterator() throws NotSupportedException, DatabaseException;

   Certificate firstCertificate() throws NotSupportedException, DatabaseException;

   Certificate nextCertificate() throws NotSupportedException, DatabaseException;

   boolean hasMoreCertificates() throws NotSupportedException, DatabaseException;

   int selectCRLByIssuerAndTime(X500Name var1, Date var2, Vector var3) throws NotSupportedException, DatabaseException;

   boolean isCRLIteratorSetup() throws NotSupportedException, DatabaseException;

   void setupCRLIterator() throws NotSupportedException, DatabaseException;

   CRL firstCRL() throws NotSupportedException, DatabaseException;

   CRL nextCRL() throws NotSupportedException, DatabaseException;

   boolean hasMoreCRLs() throws NotSupportedException, DatabaseException;

   JSAFE_PrivateKey selectPrivateKeyByCertificate(Certificate var1) throws NotSupportedException, DatabaseException;

   JSAFE_PrivateKey selectPrivateKeyByPublicKey(JSAFE_PublicKey var1) throws NotSupportedException, DatabaseException;

   boolean isPrivateKeyIteratorSetup() throws NotSupportedException, DatabaseException;

   void setupPrivateKeyIterator() throws NotSupportedException, DatabaseException;

   JSAFE_PrivateKey firstPrivateKey() throws NotSupportedException, DatabaseException;

   JSAFE_PrivateKey nextPrivateKey() throws NotSupportedException, DatabaseException;

   boolean hasMorePrivateKeys() throws NotSupportedException, DatabaseException;

   void deleteCertificate(X500Name var1, byte[] var2) throws NotSupportedException, DatabaseException;

   void deleteCRL(X500Name var1, Date var2) throws NotSupportedException, DatabaseException;

   void deletePrivateKeyByCertificate(Certificate var1) throws NotSupportedException, DatabaseException;

   void deletePrivateKeyByPublicKey(JSAFE_PublicKey var1) throws NotSupportedException, DatabaseException;
}
