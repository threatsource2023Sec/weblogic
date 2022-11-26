package com.bea.common.security.utils;

import com.bea.common.security.utils.encoders.BASE64Decoder;
import com.bea.common.security.utils.encoders.BASE64Encoder;
import com.rsa.jsafe.JSAFE_IVException;
import com.rsa.jsafe.JSAFE_InputException;
import com.rsa.jsafe.JSAFE_InvalidUseException;
import com.rsa.jsafe.JSAFE_PaddingException;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.security.KeyException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class LegacyEncryptor {
   private LegacyEncryptorKey.KeyContext keyContextDefault;
   private LegacyEncryptorKey.KeyContextMap keyContextMap;
   private String encryptedPrefix;
   private char[] encryptedStartChars;
   private byte[] encryptedStartBytes;

   public LegacyEncryptor(LegacyEncryptorKey key) {
      this.keyContextMap = key.getKeyContextMap();
      this.encryptedPrefix = this.keyContextMap.getDefaultKeyContext();
      this.keyContextDefault = this.keyContextMap.getKeyContextFromString(this.encryptedPrefix);
      this.encryptedStartChars = "{".toCharArray();
      this.encryptedStartBytes = "{".getBytes();
   }

   private LegacyEncryptorKey.KeyContext getKeyContext(String prefix) {
      return this.keyContextMap.getKeyContextFromString(prefix);
   }

   public boolean isEncrypted(String clearOrEncryptedString) {
      String prefix = this.findPrefix(clearOrEncryptedString);
      return prefix == null ? false : this.keyContextMap.isKeyContextAvailable(prefix);
   }

   public boolean isEncrypted(char[] clearOrEncryptedChars) {
      if (clearOrEncryptedChars == null) {
         return false;
      } else if (clearOrEncryptedChars.length < this.encryptedStartChars.length) {
         return false;
      } else {
         for(int i = 0; i < this.encryptedStartChars.length; ++i) {
            if (clearOrEncryptedChars[i] != this.encryptedStartChars[i]) {
               return false;
            }
         }

         return this.isEncrypted(new String(clearOrEncryptedChars));
      }
   }

   public boolean isEncrypted(byte[] clearOrEncryptedBytes) {
      if (clearOrEncryptedBytes == null) {
         return false;
      } else if (clearOrEncryptedBytes.length < this.encryptedStartBytes.length) {
         return false;
      } else {
         for(int i = 0; i < this.encryptedStartBytes.length; ++i) {
            if (clearOrEncryptedBytes[i] != this.encryptedStartBytes[i]) {
               return false;
            }
         }

         return this.isEncrypted(new String(clearOrEncryptedBytes));
      }
   }

   public String encryptString(String clearOrEncryptedString) throws BadPaddingException, IllegalBlockSizeException {
      if (this.isEncrypted(clearOrEncryptedString)) {
         return clearOrEncryptedString;
      } else {
         byte[] encrypted = this.encrypt(this.keyContextDefault, getPlainTextBytes(clearOrEncryptedString));
         return this.encryptedPrefix + (new BASE64Encoder()).encodeBuffer(encrypted);
      }
   }

   public String decryptString(String clearOrEncryptedString) throws BadPaddingException, IllegalBlockSizeException {
      String prefix = this.findPrefix(clearOrEncryptedString);
      if (prefix == null) {
         return clearOrEncryptedString;
      } else if (!this.keyContextMap.isKeyContextAvailable(prefix)) {
         return clearOrEncryptedString;
      } else {
         String encodedEncrypted = clearOrEncryptedString.substring(prefix.length());

         try {
            byte[] encrypted = (new BASE64Decoder()).decodeBuffer(encodedEncrypted);
            return getPlainTextString(this.decrypt(this.getKeyContext(prefix), encrypted));
         } catch (IOException var6) {
            InvalidParameterException e = new InvalidParameterException(var6.getLocalizedMessage());
            e.initCause(var6);
            throw e;
         }
      }
   }

   public String encryptChar(char[] clearOrEncryptedChar) throws BadPaddingException, IllegalBlockSizeException {
      if (clearOrEncryptedChar == null) {
         return null;
      } else if (this.isEncrypted(clearOrEncryptedChar)) {
         return new String(clearOrEncryptedChar);
      } else {
         byte[] clearTextBytes = getPlainTextBytes(clearOrEncryptedChar);

         String var4;
         try {
            byte[] encrypted = this.encrypt(this.keyContextDefault, clearTextBytes);
            var4 = this.encryptedPrefix + (new BASE64Encoder()).encodeBuffer(encrypted);
         } finally {
            Arrays.fill(clearTextBytes, (byte)0);
         }

         return var4;
      }
   }

   public char[] decryptChar(String clearOrEncryptedString) throws BadPaddingException, IllegalBlockSizeException {
      String prefix = this.findPrefix(clearOrEncryptedString);
      if (prefix == null) {
         return clearOrEncryptedString.toCharArray();
      } else if (!this.keyContextMap.isKeyContextAvailable(prefix)) {
         return clearOrEncryptedString.toCharArray();
      } else {
         String encodedEncrypted = clearOrEncryptedString.substring(prefix.length());
         byte[] encrypted = null;

         byte[] encrypted;
         try {
            encrypted = (new BASE64Decoder()).decodeBuffer(encodedEncrypted);
         } catch (IOException var7) {
            InvalidParameterException e = new InvalidParameterException(var7.getLocalizedMessage());
            e.initCause(var7);
            throw e;
         }

         byte[] decrypted = this.decrypt(this.getKeyContext(prefix), encrypted);
         char[] plainText = getPlainTextChars(decrypted);
         Arrays.fill(decrypted, (byte)0);
         return plainText;
      }
   }

   public byte[] encryptBytes(byte[] clearOrEncryptedBytes) throws BadPaddingException, IllegalBlockSizeException {
      if (clearOrEncryptedBytes == null) {
         return null;
      } else if (this.isEncrypted(clearOrEncryptedBytes)) {
         return clearOrEncryptedBytes;
      } else {
         byte[] encrypted = this.encrypt(this.keyContextDefault, clearOrEncryptedBytes);
         String encodedEncrypted = (new BASE64Encoder()).encodeBuffer(encrypted);
         return (this.encryptedPrefix + encodedEncrypted).getBytes();
      }
   }

   public byte[] decryptBytes(byte[] clearOrEncryptedBytes) throws BadPaddingException, IllegalBlockSizeException {
      if (clearOrEncryptedBytes == null) {
         return null;
      } else if (clearOrEncryptedBytes.length < this.encryptedStartBytes.length) {
         return clearOrEncryptedBytes;
      } else {
         for(int i = 0; i < this.encryptedStartBytes.length; ++i) {
            if (clearOrEncryptedBytes[i] != this.encryptedStartBytes[i]) {
               return clearOrEncryptedBytes;
            }
         }

         String value = new String(clearOrEncryptedBytes);
         String prefix = this.findPrefix(value);
         if (prefix == null) {
            return clearOrEncryptedBytes;
         } else if (!this.keyContextMap.isKeyContextAvailable(prefix)) {
            return clearOrEncryptedBytes;
         } else {
            String encodedEncrypted = value.substring(prefix.length());
            byte[] encrypted = null;

            byte[] encrypted;
            try {
               encrypted = (new BASE64Decoder()).decodeBuffer(encodedEncrypted);
            } catch (IOException var8) {
               InvalidParameterException e = new InvalidParameterException(var8.getLocalizedMessage());
               e.initCause(var8);
               throw e;
            }

            return this.decrypt(this.getKeyContext(prefix), encrypted);
         }
      }
   }

   private String findPrefix(String value) {
      if (value != null && value.length() != 0) {
         if (value.charAt(0) != '{') {
            return null;
         } else {
            int endIdx = value.indexOf(125);
            return endIdx == -1 ? null : value.substring(0, endIdx + 1);
         }
      } else {
         return null;
      }
   }

   private static byte[] getPlainTextBytes(String plainText) {
      try {
         return plainText.getBytes("UTF-8");
      } catch (UnsupportedEncodingException var2) {
         throw getIllegalStateException(var2);
      }
   }

   private static byte[] getPlainTextBytes(char[] plainText) {
      try {
         ByteArrayOutputStream bos = new ByteArrayOutputStream(plainText.length * 2);
         OutputStreamWriter writer = new OutputStreamWriter(bos, "UTF-8");
         writer.write(plainText, 0, plainText.length);
         writer.flush();
         byte[] bytes = bos.toByteArray();
         bos.reset();

         for(int i = 0; i < bytes.length; ++i) {
            bos.write(0);
         }

         return bytes;
      } catch (UnsupportedEncodingException var5) {
         throw getIllegalStateException(var5);
      } catch (IOException var6) {
         throw getIllegalStateException(var6);
      }
   }

   private static String getPlainTextString(byte[] plainText) {
      try {
         return new String(plainText, "UTF-8");
      } catch (UnsupportedEncodingException var2) {
         throw getIllegalStateException(var2);
      }
   }

   private static char[] getPlainTextChars(byte[] plainBytes) {
      try {
         ByteArrayInputStream bos = new ByteArrayInputStream(plainBytes);
         InputStreamReader reader = new InputStreamReader(bos, "UTF-8");
         char[] plainText = new char[plainBytes.length];
         int charsRead = reader.read(plainText, 0, plainText.length);
         if (charsRead < plainText.length) {
            char[] temp = new char[charsRead];
            System.arraycopy(plainText, 0, temp, 0, charsRead);
            Arrays.fill(plainText, '0');
            plainText = temp;
         }

         return plainText;
      } catch (UnsupportedEncodingException var6) {
         throw getIllegalStateException(var6);
      } catch (IOException var7) {
         throw getIllegalStateException(var7);
      }
   }

   private byte[] encrypt(LegacyEncryptorKey.KeyContext kc, byte[] plainText) throws BadPaddingException, IllegalBlockSizeException {
      JSAFE_SymmetricCipher encryptCipher = null;

      byte[] var7;
      try {
         encryptCipher = kc.getEncryptCipher();
         int ivLen = kc.randomLen;
         byte[] cipherText = new byte[ivLen + encryptCipher.getOutputBufferSize(plainText.length)];
         if (ivLen > 0) {
            kc.getRandomIV(cipherText, 0, ivLen);
            encryptCipher.setIV(cipherText, 0, ivLen);
            encryptCipher.encryptReInit();
         }

         int partOut = encryptCipher.encryptUpdate(plainText, 0, plainText.length, cipherText, ivLen);
         encryptCipher.encryptFinal(cipherText, partOut + ivLen);
         var7 = cipherText;
      } catch (KeyException var15) {
         throw getIllegalStateException(var15);
      } catch (JSAFE_IVException var16) {
         throw getIllegalStateException(var16);
      } catch (JSAFE_InvalidUseException var17) {
         throw getIllegalStateException(var17);
      } catch (JSAFE_InputException var18) {
         IllegalBlockSizeException e = new IllegalBlockSizeException(var18.getLocalizedMessage());
         e.initCause(var18);
         throw e;
      } catch (JSAFE_PaddingException var19) {
         BadPaddingException bpe = new BadPaddingException(var19.getLocalizedMessage());
         bpe.initCause(var19);
         throw bpe;
      } finally {
         if (encryptCipher != null) {
            encryptCipher.clearSensitiveData();
         }

      }

      return var7;
   }

   private byte[] decrypt(LegacyEncryptorKey.KeyContext kc, byte[] cipherText) throws BadPaddingException, IllegalBlockSizeException {
      JSAFE_SymmetricCipher decryptCipher = null;

      byte[] text;
      try {
         decryptCipher = kc.getDecryptCipher();
         int ivLen = kc.randomLen;
         int cipherLen = cipherText.length - ivLen;
         if (cipherLen < 0) {
            throw new IllegalBlockSizeException("Invalid input length");
         }

         byte[] plainText = new byte[cipherLen];
         if (ivLen > 0) {
            decryptCipher.setIV(cipherText, 0, ivLen);
            decryptCipher.decryptReInit();
         }

         int partOut = decryptCipher.decryptUpdate(cipherText, ivLen, cipherLen, plainText, 0);
         int finalOut = decryptCipher.decryptFinal(plainText, partOut);
         int totalOut = partOut + finalOut;
         if (totalOut < plainText.length) {
            text = new byte[totalOut];
            System.arraycopy(plainText, 0, text, 0, totalOut);
            plainText = text;
         }

         text = plainText;
      } catch (KeyException var18) {
         throw getIllegalStateException(var18);
      } catch (JSAFE_IVException var19) {
         throw getIllegalStateException(var19);
      } catch (JSAFE_InvalidUseException var20) {
         throw getIllegalStateException(var20);
      } catch (JSAFE_InputException var21) {
         IllegalBlockSizeException e = new IllegalBlockSizeException(var21.getLocalizedMessage());
         e.initCause(var21);
         throw e;
      } catch (JSAFE_PaddingException var22) {
         BadPaddingException bpe = new BadPaddingException(var22.getLocalizedMessage());
         bpe.initCause(var22);
         throw bpe;
      } finally {
         if (decryptCipher != null) {
            decryptCipher.clearSensitiveData();
         }

      }

      return text;
   }

   private static final IllegalStateException getIllegalStateException(Exception ex) {
      IllegalStateException iex = new IllegalStateException(ex.getLocalizedMessage());
      iex.initCause(ex);
      return iex;
   }
}
