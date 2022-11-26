package com.rsa.certj.provider.pki;

import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIMessage;
import com.rsa.certj.spi.pki.PKIResponseMessage;
import com.rsa.certj.spi.pki.ProtectInfo;

/** @deprecated */
public interface PKIDebug {
   void saveMessage(byte[] var1, PKIMessage var2, ProtectInfo var3) throws PKIException;

   void saveCertificate(PKIResponseMessage var1) throws PKIException;

   void saveData(byte[] var1, String var2) throws PKIException;
}
