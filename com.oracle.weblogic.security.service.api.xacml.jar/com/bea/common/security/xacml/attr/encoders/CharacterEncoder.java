package com.bea.common.security.xacml.attr.encoders;

import com.bea.common.security.ApiLogger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public abstract class CharacterEncoder {
   protected PrintStream pStream;

   abstract int bytesPerAtom();

   abstract int bytesPerLine();

   void encodeBufferPrefix(OutputStream aStream) throws IOException {
      this.pStream = new PrintStream(aStream);
   }

   void encodeBufferSuffix(OutputStream aStream) throws IOException {
   }

   void encodeLinePrefix(OutputStream aStream, int aLength) throws IOException {
   }

   void encodeLineSuffix(OutputStream aStream) throws IOException {
      this.pStream.println();
   }

   abstract void encodeAtom(OutputStream var1, byte[] var2, int var3, int var4) throws IOException;

   protected int readFully(InputStream in, byte[] buffer) throws IOException {
      for(int i = 0; i < buffer.length; ++i) {
         int q = in.read();
         if (q == -1) {
            return i;
         }

         buffer[i] = (byte)q;
      }

      return buffer.length;
   }

   public void encodeBuffer(InputStream inStream, OutputStream outStream) throws IOException {
      byte[] tmpbuffer = new byte[this.bytesPerLine()];
      this.encodeBufferPrefix(outStream);

      int numBytes;
      do {
         numBytes = this.readFully(inStream, tmpbuffer);
         if (numBytes == -1) {
            break;
         }

         this.encodeLinePrefix(outStream, numBytes);

         for(int j = 0; j < numBytes; j += this.bytesPerAtom()) {
            if (j + this.bytesPerAtom() <= numBytes) {
               this.encodeAtom(outStream, tmpbuffer, j, this.bytesPerAtom());
            } else {
               this.encodeAtom(outStream, tmpbuffer, j, numBytes - j);
            }
         }

         this.encodeLineSuffix(outStream);
      } while(numBytes >= this.bytesPerLine());

      this.encodeBufferSuffix(outStream);
   }

   public void encodeBuffer(byte[] aBuffer, OutputStream aStream) throws IOException {
      ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
      this.encodeBuffer((InputStream)inStream, aStream);
   }

   public String encodeBuffer(byte[] aBuffer) {
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);

      try {
         this.encodeBuffer((InputStream)inStream, outStream);
      } catch (Exception var7) {
         throw new Error(ApiLogger.getMethodInternalError("CharacterEncoder.encodeBuffer 1"));
      }

      try {
         String tempString = outStream.toString("UTF8");
         return tempString;
      } catch (UnsupportedEncodingException var6) {
         throw new Error(ApiLogger.getMethodInternalError("CharacterEncoder.encodeBuffer 1"));
      }
   }
}
