package org.cryptacular.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.SecretKey;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.CryptoException;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.adapter.AEADBlockCipherAdapter;
import org.cryptacular.adapter.BlockCipherAdapter;
import org.cryptacular.adapter.BufferedBlockCipherAdapter;
import org.cryptacular.generator.Nonce;

public final class CipherUtil {
   private static final int MAC_SIZE_BITS = 128;

   private CipherUtil() {
   }

   public static byte[] encrypt(AEADBlockCipher cipher, SecretKey key, Nonce nonce, byte[] data) throws CryptoException {
      byte[] iv = nonce.generate();
      byte[] header = (new CiphertextHeader(iv)).encode();
      cipher.init(true, new AEADParameters(new KeyParameter(key.getEncoded()), 128, iv, header));
      return encrypt(new AEADBlockCipherAdapter(cipher), header, data);
   }

   public static void encrypt(AEADBlockCipher cipher, SecretKey key, Nonce nonce, InputStream input, OutputStream output) throws CryptoException, StreamException {
      byte[] iv = nonce.generate();
      byte[] header = (new CiphertextHeader(iv)).encode();
      cipher.init(true, new AEADParameters(new KeyParameter(key.getEncoded()), 128, iv, header));
      writeHeader(header, output);
      process(new AEADBlockCipherAdapter(cipher), input, output);
   }

   public static byte[] decrypt(AEADBlockCipher cipher, SecretKey key, byte[] data) throws CryptoException, EncodingException {
      CiphertextHeader header = CiphertextHeader.decode(data);
      byte[] nonce = header.getNonce();
      byte[] hbytes = header.encode();
      cipher.init(false, new AEADParameters(new KeyParameter(key.getEncoded()), 128, nonce, hbytes));
      return decrypt(new AEADBlockCipherAdapter(cipher), data, header.getLength());
   }

   public static void decrypt(AEADBlockCipher cipher, SecretKey key, InputStream input, OutputStream output) throws CryptoException, EncodingException, StreamException {
      CiphertextHeader header = CiphertextHeader.decode(input);
      byte[] nonce = header.getNonce();
      byte[] hbytes = header.encode();
      cipher.init(false, new AEADParameters(new KeyParameter(key.getEncoded()), 128, nonce, hbytes));
      process(new AEADBlockCipherAdapter(cipher), input, output);
   }

   public static byte[] encrypt(BlockCipher cipher, SecretKey key, Nonce nonce, byte[] data) throws CryptoException {
      byte[] iv = nonce.generate();
      byte[] header = (new CiphertextHeader(iv)).encode();
      PaddedBufferedBlockCipher padded = new PaddedBufferedBlockCipher(cipher, new PKCS7Padding());
      padded.init(true, new ParametersWithIV(new KeyParameter(key.getEncoded()), iv));
      return encrypt(new BufferedBlockCipherAdapter(padded), header, data);
   }

   public static void encrypt(BlockCipher cipher, SecretKey key, Nonce nonce, InputStream input, OutputStream output) throws CryptoException, StreamException {
      byte[] iv = nonce.generate();
      byte[] header = (new CiphertextHeader(iv)).encode();
      PaddedBufferedBlockCipher padded = new PaddedBufferedBlockCipher(cipher, new PKCS7Padding());
      padded.init(true, new ParametersWithIV(new KeyParameter(key.getEncoded()), iv));
      writeHeader(header, output);
      process(new BufferedBlockCipherAdapter(padded), input, output);
   }

   public static byte[] decrypt(BlockCipher cipher, SecretKey key, byte[] data) throws CryptoException, EncodingException {
      CiphertextHeader header = CiphertextHeader.decode(data);
      PaddedBufferedBlockCipher padded = new PaddedBufferedBlockCipher(cipher, new PKCS7Padding());
      padded.init(false, new ParametersWithIV(new KeyParameter(key.getEncoded()), header.getNonce()));
      return decrypt(new BufferedBlockCipherAdapter(padded), data, header.getLength());
   }

   public static void decrypt(BlockCipher cipher, SecretKey key, InputStream input, OutputStream output) throws CryptoException, EncodingException, StreamException {
      CiphertextHeader header = CiphertextHeader.decode(input);
      PaddedBufferedBlockCipher padded = new PaddedBufferedBlockCipher(cipher, new PKCS7Padding());
      padded.init(false, new ParametersWithIV(new KeyParameter(key.getEncoded()), header.getNonce()));
      process(new BufferedBlockCipherAdapter(padded), input, output);
   }

   private static byte[] encrypt(BlockCipherAdapter cipher, byte[] header, byte[] data) {
      int outSize = header.length + cipher.getOutputSize(data.length);
      byte[] output = new byte[outSize];
      System.arraycopy(header, 0, output, 0, header.length);
      int outOff = header.length;
      outOff += cipher.processBytes(data, 0, data.length, output, outOff);
      cipher.doFinal(output, outOff);
      cipher.reset();
      return output;
   }

   private static byte[] decrypt(BlockCipherAdapter cipher, byte[] data, int inOff) {
      int len = data.length - inOff;
      int outSize = cipher.getOutputSize(len);
      byte[] output = new byte[outSize];
      int outOff = cipher.processBytes(data, inOff, len, output, 0);
      outOff += cipher.doFinal(output, outOff);
      cipher.reset();
      if (outOff < output.length) {
         byte[] temp = new byte[outOff];
         System.arraycopy(output, 0, temp, 0, outOff);
         return temp;
      } else {
         return output;
      }
   }

   private static void process(BlockCipherAdapter cipher, InputStream input, OutputStream output) {
      int inSize = true;
      int outSize = cipher.getOutputSize(1024);
      byte[] inBuf = new byte[1024];
      byte[] outBuf = new byte[outSize > 1024 ? outSize : 1024];

      try {
         int readLen;
         int writeLen;
         while((readLen = input.read(inBuf)) > 0) {
            writeLen = cipher.processBytes(inBuf, 0, readLen, outBuf, 0);
            output.write(outBuf, 0, writeLen);
         }

         writeLen = cipher.doFinal(outBuf, 0);
         output.write(outBuf, 0, writeLen);
      } catch (IOException var10) {
         throw new StreamException(var10);
      }
   }

   private static void writeHeader(byte[] header, OutputStream output) {
      try {
         output.write(header, 0, header.length);
      } catch (IOException var3) {
         throw new StreamException(var3);
      }
   }
}
