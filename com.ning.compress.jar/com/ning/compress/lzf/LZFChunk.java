package com.ning.compress.lzf;

import java.io.IOException;
import java.io.OutputStream;

public class LZFChunk {
   public static final int MAX_LITERAL = 32;
   public static final int MAX_CHUNK_LEN = 65535;
   public static final int MAX_HEADER_LEN = 7;
   public static final int HEADER_LEN_COMPRESSED = 7;
   public static final int HEADER_LEN_NOT_COMPRESSED = 5;
   public static final byte BYTE_Z = 90;
   public static final byte BYTE_V = 86;
   public static final int BLOCK_TYPE_NON_COMPRESSED = 0;
   public static final int BLOCK_TYPE_COMPRESSED = 1;
   protected final byte[] _data;
   protected LZFChunk _next;

   private LZFChunk(byte[] data) {
      this._data = data;
   }

   public static LZFChunk createCompressed(int origLen, byte[] encData, int encPtr, int encLen) {
      byte[] result = new byte[encLen + 7];
      result[0] = 90;
      result[1] = 86;
      result[2] = 1;
      result[3] = (byte)(encLen >> 8);
      result[4] = (byte)encLen;
      result[5] = (byte)(origLen >> 8);
      result[6] = (byte)origLen;
      System.arraycopy(encData, encPtr, result, 7, encLen);
      return new LZFChunk(result);
   }

   public static int appendCompressedHeader(int origLen, int encLen, byte[] headerBuffer, int offset) {
      headerBuffer[offset++] = 90;
      headerBuffer[offset++] = 86;
      headerBuffer[offset++] = 1;
      headerBuffer[offset++] = (byte)(encLen >> 8);
      headerBuffer[offset++] = (byte)encLen;
      headerBuffer[offset++] = (byte)(origLen >> 8);
      headerBuffer[offset++] = (byte)origLen;
      return offset;
   }

   public static void writeCompressedHeader(int origLen, int encLen, OutputStream out, byte[] headerBuffer) throws IOException {
      headerBuffer[0] = 90;
      headerBuffer[1] = 86;
      headerBuffer[2] = 1;
      headerBuffer[3] = (byte)(encLen >> 8);
      headerBuffer[4] = (byte)encLen;
      headerBuffer[5] = (byte)(origLen >> 8);
      headerBuffer[6] = (byte)origLen;
      out.write(headerBuffer, 0, 7);
   }

   public static LZFChunk createNonCompressed(byte[] plainData, int ptr, int len) {
      byte[] result = new byte[len + 5];
      result[0] = 90;
      result[1] = 86;
      result[2] = 0;
      result[3] = (byte)(len >> 8);
      result[4] = (byte)len;
      System.arraycopy(plainData, ptr, result, 5, len);
      return new LZFChunk(result);
   }

   public static int appendNonCompressed(byte[] plainData, int ptr, int len, byte[] outputBuffer, int outputPtr) {
      outputBuffer[outputPtr++] = 90;
      outputBuffer[outputPtr++] = 86;
      outputBuffer[outputPtr++] = 0;
      outputBuffer[outputPtr++] = (byte)(len >> 8);
      outputBuffer[outputPtr++] = (byte)len;
      System.arraycopy(plainData, ptr, outputBuffer, outputPtr, len);
      return outputPtr + len;
   }

   public static int appendNonCompressedHeader(int len, byte[] headerBuffer, int offset) {
      headerBuffer[offset++] = 90;
      headerBuffer[offset++] = 86;
      headerBuffer[offset++] = 0;
      headerBuffer[offset++] = (byte)(len >> 8);
      headerBuffer[offset++] = (byte)len;
      return offset;
   }

   public static void writeNonCompressedHeader(int len, OutputStream out, byte[] headerBuffer) throws IOException {
      headerBuffer[0] = 90;
      headerBuffer[1] = 86;
      headerBuffer[2] = 0;
      headerBuffer[3] = (byte)(len >> 8);
      headerBuffer[4] = (byte)len;
      out.write(headerBuffer, 0, 5);
   }

   public void setNext(LZFChunk next) {
      this._next = next;
   }

   public LZFChunk next() {
      return this._next;
   }

   public int length() {
      return this._data.length;
   }

   public byte[] getData() {
      return this._data;
   }

   public int copyTo(byte[] dst, int ptr) {
      int len = this._data.length;
      System.arraycopy(this._data, 0, dst, ptr, len);
      return ptr + len;
   }
}
