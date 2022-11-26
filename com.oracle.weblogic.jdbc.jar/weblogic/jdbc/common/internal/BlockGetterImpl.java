package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NoSuchObjectException;
import java.util.HashMap;
import java.util.Map;
import weblogic.jdbc.JDBCLogger;
import weblogic.rmi.server.UnicastRemoteObject;

public class BlockGetterImpl implements BlockGetter {
   private int lastId = 0;
   private final Map streams = new HashMap();

   public int register(InputStream is, int block_size) {
      InputStreamContainer isc = new InputStreamContainer(is, block_size);
      synchronized(this) {
         ++this.lastId;
         this.streams.put(new Integer(this.lastId), isc);
         return this.lastId;
      }
   }

   public InputStream getStream(int id) {
      InputStreamContainer isc = null;
      InputStream stream = null;
      synchronized(this) {
         isc = (InputStreamContainer)this.streams.get(new Integer(id));
      }

      if (isc != null) {
         stream = isc.is;
      }

      return stream;
   }

   public byte[] getBlock(int id) {
      InputStreamContainer isc = null;
      synchronized(this) {
         isc = (InputStreamContainer)this.streams.get(new Integer(id));
      }

      if (isc == null) {
         return null;
      } else {
         synchronized(isc) {
            byte[] bytes = new byte[isc.block_size];
            int read = -1;

            try {
               read = isc.is.read(bytes);
            } catch (IOException var8) {
               JDBCLogger.logStackTrace(var8);
            }

            if (read < 0) {
               return null;
            } else if (read >= isc.block_size) {
               return bytes;
            } else {
               byte[] new_bytes = new byte[read];
               System.arraycopy(bytes, 0, new_bytes, 0, new_bytes.length);
               return new_bytes;
            }
         }
      }
   }

   public boolean markSupported(int id) {
      InputStream myStream = this.getStream(id);
      return myStream.markSupported();
   }

   public void mark(int id, int readLimit) {
      InputStream myStream = this.getStream(id);
      myStream.mark(readLimit);
   }

   public int available(int id) throws IOException {
      InputStream myStream = this.getStream(id);
      return myStream.available();
   }

   public void reset(int id) throws IOException {
      InputStream myStream = this.getStream(id);
      myStream.reset();
   }

   public void close(int id) {
      InputStreamContainer isc = null;
      synchronized(this) {
         isc = (InputStreamContainer)this.streams.remove(new Integer(id));
      }

      try {
         if (isc != null) {
            synchronized(isc) {
               isc.is.close();
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
