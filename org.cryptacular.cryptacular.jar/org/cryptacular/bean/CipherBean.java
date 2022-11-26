package org.cryptacular.bean;

import java.io.InputStream;
import java.io.OutputStream;
import org.cryptacular.CryptoException;
import org.cryptacular.StreamException;

public interface CipherBean {
   byte[] encrypt(byte[] var1) throws CryptoException;

   void encrypt(InputStream var1, OutputStream var2) throws CryptoException, StreamException;

   byte[] decrypt(byte[] var1) throws CryptoException;

   void decrypt(InputStream var1, OutputStream var2) throws CryptoException, StreamException;
}
