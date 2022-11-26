package org.python.core.io;

import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.Iterator;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;

public class UniversalIOWrapper extends TextIOBase {
   private boolean skipNextLF = false;
   private EnumSet newlineTypes = EnumSet.noneOf(Newline.class);

   public UniversalIOWrapper(BufferedIOBase bufferedIO) {
      super(bufferedIO);
   }

   public String read(int size) {
      if (size < 0) {
         return this.readall();
      } else {
         char[] builderArray = new char[size];
         int builderPos = 0;

         int readaheadPos;
         label55:
         do {
            byte[] readaheadArray = this.readahead.array();
            readaheadPos = this.readahead.position();

            while(true) {
               while(true) {
                  if (readaheadPos >= this.readahead.limit() || builderPos >= size) {
                     continue label55;
                  }

                  char next = (char)(readaheadArray[readaheadPos++] & 255);
                  switch (next) {
                     case '\n':
                        if (this.skipNextLF) {
                           this.skipNextLF = false;
                           this.newlineTypes.add(UniversalIOWrapper.Newline.CRLF);
                           continue;
                        }

                        this.newlineTypes.add(UniversalIOWrapper.Newline.LF);
                        break;
                     case '\r':
                        next = '\n';
                        if (readaheadPos == this.readahead.limit()) {
                           if (this.readChunk() == 0) {
                              this.newlineTypes.add(UniversalIOWrapper.Newline.CR);
                              builderArray[builderPos++] = next;
                              return new String(builderArray, 0, builderPos);
                           }

                           readaheadArray = this.readahead.array();
                           readaheadPos = this.readahead.position();
                        }

                        this.skipNextLF = true;
                        break;
                     default:
                        if (this.skipNextLF) {
                           this.skipNextLF = false;
                           this.newlineTypes.add(UniversalIOWrapper.Newline.CR);
                        }
                  }

                  builderArray[builderPos++] = next;
               }
            }
         } while(builderPos < size && this.readChunk(size - builderPos) > 0);

         if (this.readahead.hasRemaining()) {
            this.readahead.position(readaheadPos);
         }

         this.packReadahead();
         return new String(builderArray, 0, builderPos);
      }
   }

   public String readall() {
      ByteBuffer remaining = this.bufferedIO.readall();
      char[] all = new char[this.readahead.remaining() + remaining.remaining()];
      int length = this.readLoop(this.readahead.array(), this.readahead.position(), all, 0, this.readahead.remaining());
      this.readahead.position(this.readahead.limit());
      length += this.readLoop(remaining.array(), remaining.position(), all, length, remaining.remaining());
      if (this.skipNextLF) {
         this.skipNextLF = false;
         this.newlineTypes.add(UniversalIOWrapper.Newline.CR);
      }

      return new String(all, 0, length);
   }

   private int readLoop(byte[] src, int srcPos, char[] dest, int destPos, int length) {
      int destStartPos = destPos;
      int srcEndPos = srcPos + length;

      while(true) {
         while(srcPos < srcEndPos) {
            char next = (char)(src[srcPos++] & 255);
            switch (next) {
               case '\n':
                  if (this.skipNextLF) {
                     this.skipNextLF = false;
                     this.newlineTypes.add(UniversalIOWrapper.Newline.CRLF);
                     continue;
                  }

                  this.newlineTypes.add(UniversalIOWrapper.Newline.LF);
                  break;
               case '\r':
                  next = '\n';
                  this.skipNextLF = true;
                  break;
               default:
                  if (this.skipNextLF) {
                     this.skipNextLF = false;
                     this.newlineTypes.add(UniversalIOWrapper.Newline.CR);
                  }
            }

            dest[destPos++] = next;
         }

         return destPos - destStartPos;
      }
   }

   public String readline(int size) {
      int readaheadPos;
      do {
         byte[] readaheadArray = this.readahead.array();
         readaheadPos = this.readahead.position();
         int interimBuilderPos = 0;

         while(readaheadPos < this.readahead.limit() && (size < 0 || this.builder.length() + interimBuilderPos < size)) {
            char next = (char)(readaheadArray[readaheadPos++] & 255);
            switch (next) {
               case '\n':
                  if (!this.skipNextLF) {
                     this.newlineTypes.add(UniversalIOWrapper.Newline.LF);
                     this.interimBuilder[interimBuilderPos++] = next;
                     this.builder.append(this.interimBuilder, 0, interimBuilderPos);
                     this.readahead.position(readaheadPos);
                     return this.drainBuilder();
                  }

                  this.skipNextLF = false;
                  this.newlineTypes.add(UniversalIOWrapper.Newline.CRLF);
                  break;
               case '\r':
                  char next = 10;
                  if (readaheadPos == this.readahead.limit()) {
                     if (this.readChunk() == 0) {
                        this.newlineTypes.add(UniversalIOWrapper.Newline.CR);
                        this.interimBuilder[interimBuilderPos++] = (char)next;
                        this.builder.append(this.interimBuilder, 0, interimBuilderPos);
                        return this.drainBuilder();
                     }

                     readaheadPos = this.readahead.position();
                  }

                  this.skipNextLF = true;
                  this.interimBuilder[interimBuilderPos++] = (char)next;
                  this.builder.append(this.interimBuilder, 0, interimBuilderPos);
                  this.readahead.position(readaheadPos);
                  return this.drainBuilder();
               default:
                  if (this.skipNextLF) {
                     this.skipNextLF = false;
                     this.newlineTypes.add(UniversalIOWrapper.Newline.CR);
                  }

                  this.interimBuilder[interimBuilderPos++] = next;
            }
         }

         this.builder.append(this.interimBuilder, 0, interimBuilderPos);
      } while((size < 0 || this.builder.length() < size) && this.readChunk() > 0);

      if (this.readahead.hasRemaining()) {
         this.readahead.position(readaheadPos);
      }

      return this.drainBuilder();
   }

   public int write(String buf) {
      this.checkClosed();
      this.checkWritable();
      return -1;
   }

   public long seek(long pos, int whence) {
      pos = super.seek(pos, whence);
      this.skipNextLF = false;
      return pos;
   }

   public long tell() {
      long pos = super.tell();
      if (this.skipNextLF && !this.atEOF()) {
         int readaheadPos = this.readahead.position();
         if (this.readahead.get(readaheadPos) == 10) {
            this.skipNextLF = false;
            this.newlineTypes.add(UniversalIOWrapper.Newline.CRLF);
            ++readaheadPos;
            this.readahead.position(readaheadPos);
            ++pos;
         }
      }

      return pos;
   }

   public PyObject getNewlines() {
      int size = this.newlineTypes.size();
      if (size == 0) {
         return Py.None;
      } else if (size == 1) {
         Newline newline = (Newline)this.newlineTypes.iterator().next();
         return new PyString(newline.getValue());
      } else {
         PyObject[] newlines = new PyObject[size];
         int i = 0;

         Newline newline;
         for(Iterator var4 = this.newlineTypes.iterator(); var4.hasNext(); newlines[i++] = new PyString(newline.getValue())) {
            newline = (Newline)var4.next();
         }

         return new PyTuple(newlines);
      }
   }

   private static enum Newline {
      CR("\r"),
      LF("\n"),
      CRLF("\r\n");

      private final String value;

      public String getValue() {
         return this.value;
      }

      private Newline(String value) {
         this.value = value;
      }
   }
}
