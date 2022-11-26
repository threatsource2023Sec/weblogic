package com.bea.common.security.legacy.spi;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public interface LegacyEncryptorSpi {
   boolean isEncrypted(String var1);

   boolean isEncrypted(char[] var1);

   boolean isEncrypted(byte[] var1);

   String encryptString(String var1) throws BadPaddingException, IllegalBlockSizeException;

   String decryptString(String var1) throws BadPaddingException, IllegalBlockSizeException;

   String encryptChar(char[] var1) throws BadPaddingException, IllegalBlockSizeException;

   char[] decryptChar(String var1) throws BadPaddingException, IllegalBlockSizeException;

   byte[] encryptBytes(byte[] var1) throws BadPaddingException, IllegalBlockSizeException;

   byte[] decryptBytes(byte[] var1) throws BadPaddingException, IllegalBlockSizeException;
}
