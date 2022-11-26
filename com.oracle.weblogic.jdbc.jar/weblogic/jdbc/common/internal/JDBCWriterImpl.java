package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.Writer;
import java.rmi.NoSuchObjectException;
import weblogic.rmi.extensions.server.SmartStubInfo;
import weblogic.rmi.server.UnicastRemoteObject;

public class JDBCWriterImpl extends Writer implements JDBCWriter, SmartStubInfo {
   Writer t2_wtr;
   boolean verbose = false;
   boolean closed = false;
   int block_size;
   private transient JDBCWriterStub wtrstub = null;

   public JDBCWriterImpl(Writer wtr, boolean verbose, int block_size) {
      this.block_size = block_size;
      this.verbose = verbose;
      this.t2_wtr = wtr;
   }

   public void writeBlock(char[] b) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else {
         this.t2_wtr.write(b);
      }
   }

   public void write(char[] b, int off, int len) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else {
         this.t2_wtr.write(b, off, len);
      }
   }

   public void flush() throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else {
         this.t2_wtr.flush();
      }
   }

   public void close() throws IOException {
      try {
         UnicastRemoteObject.unexportObject(this, true);
      } catch (NoSuchObjectException var2) {
      }

      if (!this.closed) {
         this.t2_wtr.close();
         this.closed = true;
         this.t2_wtr = null;
         this.wtrstub = null;
      }
   }

   public Object getSmartStub(Object stub) {
      if (this.wtrstub == null) {
         this.wtrstub = new JDBCWriterStub((JDBCWriter)stub, this.verbose, this.block_size);
      }

      return this.wtrstub;
   }
}
