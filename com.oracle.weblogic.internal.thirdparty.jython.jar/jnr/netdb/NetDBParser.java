package jnr.netdb;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

class NetDBParser implements Iterable, Closeable {
   private final Reader reader;

   public NetDBParser(Reader r) {
      this.reader = r;
   }

   public Iterator iterator() {
      return new NetDBIterator(this.reader);
   }

   public void close() throws IOException {
      this.reader.close();
   }
}
