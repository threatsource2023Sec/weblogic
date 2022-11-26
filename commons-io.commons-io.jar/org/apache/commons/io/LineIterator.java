package org.apache.commons.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LineIterator implements Iterator, Closeable {
   private final BufferedReader bufferedReader;
   private String cachedLine;
   private boolean finished = false;

   public LineIterator(Reader reader) throws IllegalArgumentException {
      if (reader == null) {
         throw new IllegalArgumentException("Reader must not be null");
      } else {
         if (reader instanceof BufferedReader) {
            this.bufferedReader = (BufferedReader)reader;
         } else {
            this.bufferedReader = new BufferedReader(reader);
         }

      }
   }

   public boolean hasNext() {
      if (this.cachedLine != null) {
         return true;
      } else if (this.finished) {
         return false;
      } else {
         try {
            String line;
            do {
               line = this.bufferedReader.readLine();
               if (line == null) {
                  this.finished = true;
                  return false;
               }
            } while(!this.isValidLine(line));

            this.cachedLine = line;
            return true;
         } catch (IOException var4) {
            try {
               this.close();
            } catch (IOException var3) {
               var4.addSuppressed(var3);
            }

            throw new IllegalStateException(var4);
         }
      }
   }

   protected boolean isValidLine(String line) {
      return true;
   }

   public String next() {
      return this.nextLine();
   }

   public String nextLine() {
      if (!this.hasNext()) {
         throw new NoSuchElementException("No more lines");
      } else {
         String currentLine = this.cachedLine;
         this.cachedLine = null;
         return currentLine;
      }
   }

   public void close() throws IOException {
      this.finished = true;
      this.cachedLine = null;
      if (this.bufferedReader != null) {
         this.bufferedReader.close();
      }

   }

   public void remove() {
      throw new UnsupportedOperationException("Remove unsupported on LineIterator");
   }

   /** @deprecated */
   @Deprecated
   public static void closeQuietly(LineIterator iterator) {
      try {
         if (iterator != null) {
            iterator.close();
         }
      } catch (IOException var2) {
      }

   }
}
