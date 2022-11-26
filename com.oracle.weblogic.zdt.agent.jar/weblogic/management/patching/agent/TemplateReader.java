package weblogic.management.patching.agent;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Map;

public class TemplateReader extends Reader {
   protected Map tokens;
   protected Reader sourceReader;
   private String currentValue;
   private int index;

   public TemplateReader(Reader sourceReader) {
      this(sourceReader, (Map)null);
   }

   public TemplateReader(Reader sourceReader, Map tokens) {
      this.tokens = null;
      this.sourceReader = null;
      this.currentValue = null;
      this.index = -1;
      this.sourceReader = sourceReader;
      this.tokens = tokens;
   }

   public void setTokens(Map tokens) {
      this.tokens = tokens;
   }

   public int read() throws IOException {
      int ch = -1;
      boolean emptyToken = false;
      if (this.currentValue != null) {
         if (this.index < this.currentValue.length()) {
            ch = this.currentValue.charAt(this.index++);
         }

         if (this.index >= this.currentValue.length()) {
            this.currentValue = null;
            this.index = -1;
         }
      } else {
         int[] tempChar = new int[]{this.sourceReader.read(), 0};
         if (tempChar[0] == 36) {
            tempChar[1] = this.sourceReader.read();
            if (tempChar[1] == 123) {
               StringBuilder builder = new StringBuilder();
               boolean readToken = true;

               label54:
               while(true) {
                  while(true) {
                     if (!readToken) {
                        break label54;
                     }

                     int readChar = this.sourceReader.read();
                     if (readChar == 125) {
                        String tokenName = builder.toString();
                        if (this.tokens != null && this.tokens.containsKey(tokenName)) {
                           this.currentValue = (String)this.tokens.get(tokenName);
                           if (this.currentValue == null || this.currentValue.length() == 0) {
                              this.currentValue = null;
                              readToken = false;
                              emptyToken = true;
                              break label54;
                           }
                        } else {
                           this.currentValue = "" + (char)tempChar[0] + (char)tempChar[1] + builder.toString() + (char)readChar;
                        }

                        this.index = 0;
                        ch = this.currentValue.charAt(this.index++);
                        readToken = false;
                     } else if (readChar == -1) {
                        this.currentValue = "" + (char)tempChar[0] + (char)tempChar[1] + builder.toString();
                        this.index = 0;
                        ch = this.currentValue.charAt(this.index++);
                        readToken = false;
                     } else {
                        builder.append((char)readChar);
                     }
                  }
               }
            } else {
               this.currentValue = "" + (char)tempChar[0] + (char)tempChar[1];
               this.index = 0;
               ch = this.currentValue.charAt(this.index++);
            }
         } else {
            ch = tempChar[0];
         }
      }

      if (emptyToken) {
         ch = this.read();
      }

      return ch;
   }

   public int read(CharBuffer target) throws IOException {
      int ch;
      int numRead;
      for(numRead = 0; (ch = this.read()) != -1; ++numRead) {
         target.append((char)ch);
      }

      return numRead;
   }

   public int read(char[] cbuf) throws IOException {
      return this.read(cbuf, 0, cbuf.length);
   }

   public int read(char[] cbuf, int offset, int length) throws IOException {
      int pos = offset;

      int ch;
      int numRead;
      for(numRead = 0; pos < offset + length && pos < cbuf.length && (ch = this.read()) != -1; ++numRead) {
         cbuf[pos++] = (char)ch;
      }

      return numRead;
   }

   public void close() throws IOException {
      this.sourceReader.close();
   }

   public long skip(long n) throws IOException {
      throw new UnsupportedOperationException();
   }

   public boolean ready() throws IOException {
      return this.sourceReader.ready();
   }

   public boolean markSupported() {
      return false;
   }

   public void mark(int readAheadLimit) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void reset() throws IOException {
      throw new UnsupportedOperationException();
   }
}
