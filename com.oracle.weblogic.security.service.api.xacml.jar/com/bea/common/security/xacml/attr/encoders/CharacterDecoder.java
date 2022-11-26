package com.bea.common.security.xacml.attr.encoders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class CharacterDecoder {
   abstract int bytesPerAtom();

   abstract int bytesPerLine();

   void decodeBufferPrefix(InputStream aStream, OutputStream bStream) throws IOException {
   }

   void decodeBufferSuffix(InputStream aStream, OutputStream bStream) throws IOException {
   }

   int decodeLinePrefix(InputStream aStream, OutputStream bStream) throws IOException {
      return this.bytesPerLine();
   }

   void decodeLineSuffix(InputStream aStream, OutputStream bStream) throws IOException {
   }

   void decodeAtom(InputStream aStream, OutputStream bStream, int l) throws IOException {
      throw new CEStreamExhausted();
   }

   protected int readFully(InputStream in, byte[] buffer, int offset, int len) throws IOException {
      for(int i = 0; i < len; ++i) {
         int q = in.read();
         if (q == -1) {
            return i == 0 ? -1 : i;
         }

         buffer[i + offset] = (byte)q;
      }

      return len;
   }

   public void decodeBuffer(InputStream aStream, OutputStream bStream) throws IOException {
      int totalBytes = 0;
      this.decodeBufferPrefix(aStream, bStream);

      while(true) {
         try {
            int length = this.decodeLinePrefix(aStream, bStream);

            int i;
            for(i = 0; i + this.bytesPerAtom() < length; i += this.bytesPerAtom()) {
               this.decodeAtom(aStream, bStream, this.bytesPerAtom());
               totalBytes += this.bytesPerAtom();
            }

            if (i + this.bytesPerAtom() == length) {
               this.decodeAtom(aStream, bStream, this.bytesPerAtom());
               totalBytes += this.bytesPerAtom();
            } else {
               this.decodeAtom(aStream, bStream, length - i);
               totalBytes += length - i;
            }

            this.decodeLineSuffix(aStream, bStream);
         } catch (CEStreamExhausted var7) {
            this.decodeBufferSuffix(aStream, bStream);
            return;
         }
      }
   }

   public byte[] decodeBuffer(String inputString) throws IOException {
      byte[] inputBuffer = new byte[inputString.length()];
      inputString.getBytes(0, inputString.length(), inputBuffer, 0);
      ByteArrayInputStream inStream = new ByteArrayInputStream(inputBuffer);
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      this.decodeBuffer(inStream, outStream);
      return outStream.toByteArray();
   }

   public byte[] decodeBuffer(InputStream in) throws IOException {
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      this.decodeBuffer(in, outStream);
      return outStream.toByteArray();
   }
}
