package org.cryptacular;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.cryptacular.util.ByteUtil;

public class CiphertextHeader {
   private final byte[] nonce;
   private String keyName;
   private int length;

   public CiphertextHeader(byte[] nonce) {
      this(nonce, (String)null);
   }

   public CiphertextHeader(byte[] nonce, String keyName) {
      this.nonce = nonce;
      this.length = 8 + nonce.length;
      if (keyName != null) {
         this.length += 4 + keyName.getBytes().length;
         this.keyName = keyName;
      }

   }

   public int getLength() {
      return this.length;
   }

   public byte[] getNonce() {
      return this.nonce;
   }

   public String getKeyName() {
      return this.keyName;
   }

   public byte[] encode() {
      ByteBuffer bb = ByteBuffer.allocate(this.length);
      bb.order(ByteOrder.BIG_ENDIAN);
      bb.putInt(this.length);
      bb.putInt(this.nonce.length);
      bb.put(this.nonce);
      if (this.keyName != null) {
         byte[] b = this.keyName.getBytes();
         bb.putInt(b.length);
         bb.put(b);
      }

      return bb.array();
   }

   public static CiphertextHeader decode(byte[] data) throws EncodingException {
      ByteBuffer bb = ByteBuffer.wrap(data);
      bb.order(ByteOrder.BIG_ENDIAN);
      int length = bb.getInt();
      if (length < 0) {
         throw new EncodingException("Invalid ciphertext header length: " + length);
      } else {
         int nonceLen = 0;

         byte[] nonce;
         try {
            nonceLen = bb.getInt();
            nonce = new byte[nonceLen];
            bb.get(nonce);
         } catch (BufferUnderflowException | IndexOutOfBoundsException var10) {
            throw new EncodingException("Invalid nonce length: " + nonceLen);
         }

         String keyName = null;
         if (length > nonce.length + 8) {
            int keyLen = 0;

            try {
               keyLen = bb.getInt();
               byte[] b = new byte[keyLen];
               bb.get(b);
               keyName = new String(b);
            } catch (BufferUnderflowException | IndexOutOfBoundsException var9) {
               throw new EncodingException("Invalid key length: " + keyLen);
            }
         }

         return new CiphertextHeader(nonce, keyName);
      }
   }

   public static CiphertextHeader decode(InputStream input) throws EncodingException, StreamException {
      int length = ByteUtil.readInt(input);
      if (length < 0) {
         throw new EncodingException("Invalid ciphertext header length: " + length);
      } else {
         int nonceLen = 0;

         byte[] nonce;
         try {
            nonceLen = ByteUtil.readInt(input);
            nonce = new byte[nonceLen];
            input.read(nonce);
         } catch (ArrayIndexOutOfBoundsException var10) {
            throw new EncodingException("Invalid nonce length: " + nonceLen);
         } catch (IOException var11) {
            throw new StreamException(var11);
         }

         String keyName = null;
         if (length > nonce.length + 8) {
            int keyLen = 0;

            byte[] b;
            try {
               keyLen = ByteUtil.readInt(input);
               b = new byte[keyLen];
               input.read(b);
            } catch (ArrayIndexOutOfBoundsException var8) {
               throw new EncodingException("Invalid key length: " + keyLen);
            } catch (IOException var9) {
               throw new StreamException(var9);
            }

            keyName = new String(b);
         }

         return new CiphertextHeader(nonce, keyName);
      }
   }
}
