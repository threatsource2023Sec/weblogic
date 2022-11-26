package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.Reader;
import java.rmi.NoSuchObjectException;
import java.util.Hashtable;
import weblogic.jdbc.JDBCLogger;
import weblogic.rmi.server.UnicastRemoteObject;

public class ReaderBlockGetterImpl implements ReaderBlockGetter {
   private int lastId = 0;
   private int block_size = 1024;
   private Hashtable readers = new Hashtable();

   public int register(Reader rdr, int block_size) {
      ReaderContainer rdc = new ReaderContainer(rdr, block_size);
      synchronized(this) {
         ++this.lastId;
         this.readers.put("" + this.lastId, rdc);
         return this.lastId;
      }
   }

   public Reader getReader(int id) {
      ReaderContainer rsc = null;
      Reader rdr = null;
      synchronized(this) {
         rsc = (ReaderContainer)this.readers.get("" + id);
      }

      if (rsc != null) {
         rdr = rsc.rdr;
      }

      return rdr;
   }

   public char[] getBlock(int id) {
      ReaderContainer rdc = null;
      synchronized(this) {
         rdc = (ReaderContainer)this.readers.get("" + id);
      }

      if (rdc == null) {
         return null;
      } else {
         synchronized(rdc) {
            char[] chars = new char[rdc.block_size];
            int read = -1;

            try {
               read = rdc.rdr.read(chars);
            } catch (IOException var8) {
               JDBCLogger.logStackTrace(var8);
            }

            if (read < 0) {
               return null;
            } else if (read >= rdc.block_size) {
               return chars;
            } else {
               char[] new_chars = new char[read];
               System.arraycopy(chars, 0, new_chars, 0, new_chars.length);
               return new_chars;
            }
         }
      }
   }

   public int getBlockSize() {
      return this.block_size;
   }

   public boolean markSupported(int id) {
      Reader myReader = this.getReader(id);
      return myReader.markSupported();
   }

   public void mark(int id, int readLimit) throws IOException {
      Reader myReader = this.getReader(id);
      myReader.mark(readLimit);
   }

   public boolean ready(int id) throws IOException {
      Reader myReader = this.getReader(id);
      return myReader.ready();
   }

   public void reset(int id) throws IOException {
      Reader myReader = this.getReader(id);
      myReader.reset();
   }

   public void close(int id) {
      ReaderContainer rdc = null;
      synchronized(this) {
         rdc = (ReaderContainer)this.readers.remove("" + id);
      }

      try {
         if (rdc != null) {
            synchronized(rdc) {
               rdc.rdr.close();
            }
         }
      } catch (IOException var7) {
      }

   }

   public void close() {
      try {
         UnicastRemoteObject.unexportObject(this, true);
      } catch (NoSuchObjectException var2) {
      }

   }
}
