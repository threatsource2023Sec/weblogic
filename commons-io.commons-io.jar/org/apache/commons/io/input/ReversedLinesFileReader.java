package org.apache.commons.io.input;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.Charsets;

public class ReversedLinesFileReader implements Closeable {
   private final int blockSize;
   private final Charset encoding;
   private final RandomAccessFile randomAccessFile;
   private final long totalByteLength;
   private final long totalBlockCount;
   private final byte[][] newLineSequences;
   private final int avoidNewlineSplitBufferSize;
   private final int byteDecrement;
   private FilePart currentFilePart;
   private boolean trailingNewlineOfFileSkipped;

   /** @deprecated */
   @Deprecated
   public ReversedLinesFileReader(File file) throws IOException {
      this(file, 4096, (Charset)Charset.defaultCharset());
   }

   public ReversedLinesFileReader(File file, Charset charset) throws IOException {
      this(file, 4096, (Charset)charset);
   }

   public ReversedLinesFileReader(File file, int blockSize, Charset encoding) throws IOException {
      this.trailingNewlineOfFileSkipped = false;
      this.blockSize = blockSize;
      this.encoding = encoding;
      Charset charset = Charsets.toCharset(encoding);
      CharsetEncoder charsetEncoder = charset.newEncoder();
      float maxBytesPerChar = charsetEncoder.maxBytesPerChar();
      if (maxBytesPerChar == 1.0F) {
         this.byteDecrement = 1;
      } else if (charset == StandardCharsets.UTF_8) {
         this.byteDecrement = 1;
      } else if (charset != Charset.forName("Shift_JIS") && charset != Charset.forName("windows-31j") && charset != Charset.forName("x-windows-949") && charset != Charset.forName("gbk") && charset != Charset.forName("x-windows-950")) {
         if (charset != StandardCharsets.UTF_16BE && charset != StandardCharsets.UTF_16LE) {
            if (charset == StandardCharsets.UTF_16) {
               throw new UnsupportedEncodingException("For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)");
            }

            throw new UnsupportedEncodingException("Encoding " + encoding + " is not supported yet (feel free to submit a patch)");
         }

         this.byteDecrement = 2;
      } else {
         this.byteDecrement = 1;
      }

      this.newLineSequences = new byte[][]{"\r\n".getBytes(encoding), "\n".getBytes(encoding), "\r".getBytes(encoding)};
      this.avoidNewlineSplitBufferSize = this.newLineSequences[0].length;
      this.randomAccessFile = new RandomAccessFile(file, "r");
      this.totalByteLength = this.randomAccessFile.length();
      int lastBlockLength = (int)(this.totalByteLength % (long)blockSize);
      if (lastBlockLength > 0) {
         this.totalBlockCount = this.totalByteLength / (long)blockSize + 1L;
      } else {
         this.totalBlockCount = this.totalByteLength / (long)blockSize;
         if (this.totalByteLength > 0L) {
            lastBlockLength = blockSize;
         }
      }

      this.currentFilePart = new FilePart(this.totalBlockCount, lastBlockLength, (byte[])null);
   }

   public ReversedLinesFileReader(File file, int blockSize, String encoding) throws IOException {
      this(file, blockSize, Charsets.toCharset(encoding));
   }

   public String readLine() throws IOException {
      String line;
      for(line = this.currentFilePart.readLine(); line == null; line = this.currentFilePart.readLine()) {
         this.currentFilePart = this.currentFilePart.rollOver();
         if (this.currentFilePart == null) {
            break;
         }
      }

      if ("".equals(line) && !this.trailingNewlineOfFileSkipped) {
         this.trailingNewlineOfFileSkipped = true;
         line = this.readLine();
      }

      return line;
   }

   public void close() throws IOException {
      this.randomAccessFile.close();
   }

   private class FilePart {
      private final long no;
      private final byte[] data;
      private byte[] leftOver;
      private int currentLastBytePos;

      private FilePart(long no, int length, byte[] leftOverOfLastFilePart) throws IOException {
         this.no = no;
         int dataLength = length + (leftOverOfLastFilePart != null ? leftOverOfLastFilePart.length : 0);
         this.data = new byte[dataLength];
         long off = (no - 1L) * (long)ReversedLinesFileReader.this.blockSize;
         if (no > 0L) {
            ReversedLinesFileReader.this.randomAccessFile.seek(off);
            int countRead = ReversedLinesFileReader.this.randomAccessFile.read(this.data, 0, length);
            if (countRead != length) {
               throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
            }
         }

         if (leftOverOfLastFilePart != null) {
            System.arraycopy(leftOverOfLastFilePart, 0, this.data, length, leftOverOfLastFilePart.length);
         }

         this.currentLastBytePos = this.data.length - 1;
         this.leftOver = null;
      }

      private FilePart rollOver() throws IOException {
         if (this.currentLastBytePos > -1) {
            throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
         } else if (this.no > 1L) {
            return ReversedLinesFileReader.this.new FilePart(this.no - 1L, ReversedLinesFileReader.this.blockSize, this.leftOver);
         } else if (this.leftOver != null) {
            throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=" + new String(this.leftOver, ReversedLinesFileReader.this.encoding));
         } else {
            return null;
         }
      }

      private String readLine() throws IOException {
         String line = null;
         boolean isLastFilePart = this.no == 1L;
         int i = this.currentLastBytePos;

         while(i > -1) {
            if (!isLastFilePart && i < ReversedLinesFileReader.this.avoidNewlineSplitBufferSize) {
               this.createLeftOver();
               break;
            }

            int newLineMatchByteCount;
            if ((newLineMatchByteCount = this.getNewLineMatchByteCount(this.data, i)) > 0) {
               int lineStart = i + 1;
               int lineLengthBytes = this.currentLastBytePos - lineStart + 1;
               if (lineLengthBytes < 0) {
                  throw new IllegalStateException("Unexpected negative line length=" + lineLengthBytes);
               }

               byte[] lineData = new byte[lineLengthBytes];
               System.arraycopy(this.data, lineStart, lineData, 0, lineLengthBytes);
               line = new String(lineData, ReversedLinesFileReader.this.encoding);
               this.currentLastBytePos = i - newLineMatchByteCount;
               break;
            }

            i -= ReversedLinesFileReader.this.byteDecrement;
            if (i < 0) {
               this.createLeftOver();
               break;
            }
         }

         if (isLastFilePart && this.leftOver != null) {
            line = new String(this.leftOver, ReversedLinesFileReader.this.encoding);
            this.leftOver = null;
         }

         return line;
      }

      private void createLeftOver() {
         int lineLengthBytes = this.currentLastBytePos + 1;
         if (lineLengthBytes > 0) {
            this.leftOver = new byte[lineLengthBytes];
            System.arraycopy(this.data, 0, this.leftOver, 0, lineLengthBytes);
         } else {
            this.leftOver = null;
         }

         this.currentLastBytePos = -1;
      }

      private int getNewLineMatchByteCount(byte[] data, int i) {
         byte[][] var3 = ReversedLinesFileReader.this.newLineSequences;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte[] newLineSequence = var3[var5];
            boolean match = true;

            for(int j = newLineSequence.length - 1; j >= 0; --j) {
               int k = i + j - (newLineSequence.length - 1);
               match &= k >= 0 && data[k] == newLineSequence[j];
            }

            if (match) {
               return newLineSequence.length;
            }
         }

         return 0;
      }

      // $FF: synthetic method
      FilePart(long x1, int x2, byte[] x3, Object x4) throws IOException {
         this(x1, x2, x3);
      }
   }
}
