package com.bea.xbean.piccolo.xml;

import com.bea.xbean.piccolo.io.IllegalCharException;
import java.io.IOException;
import java.io.Reader;

public final class XMLReaderReader extends XMLInputReader {
   private static final int BUFFER_SIZE = 8192;
   private Reader in;
   private boolean rewindDeclaration;
   private char[] cbuf;
   private int cbufPos;
   private int cbufEnd;
   private boolean eofReached;
   private boolean sawCR;
   private char[] oneCharBuf;

   public XMLReaderReader() {
      this.cbuf = new char[8192];
      this.cbufPos = 0;
      this.cbufEnd = 0;
      this.eofReached = false;
      this.sawCR = false;
      this.oneCharBuf = new char[1];
   }

   public XMLReaderReader(Reader in) throws IOException {
      this(in, true);
   }

   public XMLReaderReader(Reader in, boolean rewindDeclaration) throws IOException {
      this.cbuf = new char[8192];
      this.cbufPos = 0;
      this.cbufEnd = 0;
      this.eofReached = false;
      this.sawCR = false;
      this.oneCharBuf = new char[1];
      this.reset(in, rewindDeclaration);
   }

   public void reset(Reader in, boolean rewindDeclaration) throws IOException {
      super.resetInput();
      this.in = in;
      this.rewindDeclaration = rewindDeclaration;
      this.cbufPos = this.cbufEnd = 0;
      this.sawCR = false;
      this.eofReached = false;
      this.fillCharBuffer();
      this.processXMLDecl();
   }

   public void close() throws IOException {
      this.eofReached = true;
      this.cbufPos = this.cbufEnd = 0;
      if (this.in != null) {
         this.in.close();
      }

   }

   public void mark(int readAheadLimit) throws IOException {
      throw new UnsupportedOperationException("mark() not supported");
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      int n = this.read(this.oneCharBuf, 0, 1);
      return n <= 0 ? n : this.oneCharBuf[0];
   }

   public int read(char[] destbuf) throws IOException {
      return this.read(destbuf, 0, destbuf.length);
   }

   public int read(char[] destbuf, int off, int len) throws IOException {
      int charsRead = 0;

      while(charsRead < len) {
         if (this.cbufPos < this.cbufEnd) {
            char c = this.cbuf[this.cbufPos++];
            if (c < ' ') {
               switch (c) {
                  case '\t':
                     destbuf[off + charsRead++] = '\t';
                     break;
                  case '\n':
                     if (this.sawCR) {
                        this.sawCR = false;
                     } else {
                        destbuf[off + charsRead++] = '\n';
                     }
                     break;
                  case '\u000b':
                  case '\f':
                  default:
                     throw new IllegalCharException("Illegal XML character: 0x" + Integer.toHexString(c));
                  case '\r':
                     this.sawCR = true;
                     destbuf[off + charsRead++] = '\n';
               }
            } else {
               if (c > '\ud7ff' && (c < '\ud800' || c > '\udfff') && (c < '\ue000' || c > '�') && (c < 65536 || c > 1114111)) {
                  throw new IllegalCharException("Illegal XML Character: 0x" + Integer.toHexString(c));
               }

               this.sawCR = false;
               destbuf[off + charsRead++] = c;
            }
         } else {
            if (this.eofReached || charsRead != 0 && !this.in.ready()) {
               break;
            }

            this.fillCharBuffer();
         }
      }

      return charsRead == 0 && this.eofReached ? -1 : charsRead;
   }

   public boolean ready() throws IOException {
      return this.cbufEnd - this.cbufPos > 0 || this.in.ready();
   }

   public void reset() throws IOException {
      super.resetInput();
      this.in.reset();
      this.cbufPos = this.cbufEnd = 0;
      this.sawCR = false;
      this.eofReached = false;
   }

   public long skip(long n) throws IOException {
      int charsRead = 0;

      while(true) {
         if ((long)charsRead < n) {
            if (this.cbufPos < this.cbufEnd) {
               char c = this.cbuf[this.cbufPos++];
               if (c < ' ') {
                  switch (c) {
                     case '\t':
                        ++charsRead;
                        continue;
                     case '\n':
                        if (this.sawCR) {
                           this.sawCR = false;
                        } else {
                           ++charsRead;
                        }
                        continue;
                     case '\u000b':
                     case '\f':
                     default:
                        throw new IllegalCharException("Illegal XML character: 0x" + Integer.toHexString(c));
                     case '\r':
                        this.sawCR = true;
                        ++charsRead;
                        continue;
                  }
               }

               if (c > '\ud7ff' && (c < '\ud800' || c > '\udfff') && (c < '\ue000' || c > '�') && (c < 65536 || c > 1114111)) {
                  throw new IllegalCharException("Illegal XML Character: 0x" + Integer.toHexString(c));
               }

               this.sawCR = false;
               ++charsRead;
               continue;
            }

            if (!this.eofReached) {
               this.fillCharBuffer();
               continue;
            }
         }

         return (long)(charsRead == 0 && this.eofReached ? -1 : charsRead);
      }
   }

   private void fillCharBuffer() throws IOException {
      this.cbufPos = 0;
      this.cbufEnd = this.in.read(this.cbuf, 0, 8192);
      if (this.cbufEnd <= 0) {
         this.eofReached = true;
      }

   }

   private void processXMLDecl() throws IOException {
      int numCharsParsed = this.parseXMLDeclaration(this.cbuf, 0, this.cbufEnd);
      if (numCharsParsed > 0 && !this.rewindDeclaration) {
         this.cbufPos += numCharsParsed;
      }

   }
}
