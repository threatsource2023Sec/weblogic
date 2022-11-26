package jnr.netdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

final class NetDBIterator implements Iterator {
   private final BufferedReader reader;
   private NetDBEntry next = null;

   public NetDBIterator(Reader r) {
      this.reader = r instanceof BufferedReader ? (BufferedReader)r : new BufferedReader(r);
   }

   NetDBEntry readNextEntry() throws IOException {
      String s = null;

      while((s = this.reader.readLine()) != null) {
         String[] line = s.split("#", 2);
         if (line.length >= 1 && line[0].length() != 0) {
            String[] fields = line[0].trim().split("\\s+");
            if (fields.length >= 2 && fields[0] != null && fields[1] != null) {
               String serviceName = fields[0];
               String data = fields[1];
               Object aliases;
               if (fields.length > 2) {
                  aliases = new ArrayList(fields.length - 2);

                  for(int i = 2; i < fields.length; ++i) {
                     if (fields[i] != null) {
                        ((List)aliases).add(fields[i]);
                     }
                  }
               } else {
                  aliases = Collections.emptyList();
               }

               return new NetDBEntry(serviceName, data, (Collection)aliases);
            }
         }
      }

      return null;
   }

   public boolean hasNext() {
      try {
         return this.next != null || (this.next = this.readNextEntry()) != null;
      } catch (IOException var2) {
         return false;
      }
   }

   public NetDBEntry next() {
      try {
         NetDBEntry s = this.next != null ? this.next : this.readNextEntry();
         if (s == null) {
            throw new NoSuchElementException("not found");
         } else {
            this.next = null;
            return s;
         }
      } catch (IOException var2) {
         throw new NoSuchElementException(var2.getMessage());
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
