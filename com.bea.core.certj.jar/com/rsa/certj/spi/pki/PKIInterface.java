package com.rsa.certj.spi.pki;

import com.rsa.certj.DatabaseService;
import com.rsa.certj.NotSupportedException;
import com.rsa.jsafe.JSAFE_PrivateKey;

/** @deprecated */
public interface PKIInterface {
   /** @deprecated */
   PKIResponseMessage readCertificationResponseMessage(byte[] var1, ProtectInfo var2) throws NotSupportedException, PKIException;

   /** @deprecated */
   byte[] writeCertificationRequestMessage(PKIRequestMessage var1, ProtectInfo var2) throws NotSupportedException, PKIException;

   /** @deprecated */
   PKIResponseMessage requestCertification(PKIRequestMessage var1, ProtectInfo var2, DatabaseService var3) throws NotSupportedException, PKIException;

   PKIResponseMessage sendRequest(PKIRequestMessage var1, ProtectInfo var2, DatabaseService var3) throws NotSupportedException, PKIException;

   /** @deprecated */
   PKIResult sendMessage(byte[] var1) throws NotSupportedException, PKIException;

   void generateProofOfPossession(PKIRequestMessage var1, JSAFE_PrivateKey var2, POPGenerationInfo var3) throws NotSupportedException, PKIException;

   boolean validateProofOfPossession(PKIMessage var1, POPValidationInfo var2) throws NotSupportedException, PKIException;

   void provideProofOfPossession(PKIRequestMessage var1, int var2, byte[] var3) throws NotSupportedException, PKIException;
}
