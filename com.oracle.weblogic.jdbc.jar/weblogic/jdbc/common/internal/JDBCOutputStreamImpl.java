package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.rmi.NoSuchObjectException;
import weblogic.rmi.extensions.server.SmartStubInfo;
import weblogic.rmi.server.UnicastRemoteObject;

public class JDBCOutputStreamImpl extends OutputStream implements JDBCOutputStream, SmartStubInfo {
   OutputStream t2_os;
   boolean verbose = false;
   boolean closed = false;
   int block_size;
   private transient JDBCOutputStreamStub osstub = null;

   public JDBCOutputStreamImpl(OutputStream os, boolean verbose, int block_size) {
      this.t2_os = os;
      this.verbose = verbose;
      this.block_size = block_size;
   }

   public void write(int b) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR OutputStream has been closed and cannot be used");
      } else {
         this.t2_os.write(b);
      }
   }

   public void write(byte[] b) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR OutputStream has been closed and cannot be used");
      } else {
         this.t2_os.write(b);
      }
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR OutputStream has been closed and cannot be used");
      } else {
         this.t2_os.write(b, off, len);
      }
   }

   public void flush() throws IOException {
      if (this.closed) {
         throw new IOException("ERROR OutputStream has been closed and cannot be used");
      } else {
         this.t2_os.flush();
      }
   }

   public void close() throws IOException {
      try {
         UnicastRemoteObject.unexportObject(this, true);
      } catch (NoSuchObjectException var2) {
      }

      if (!this.closed) {
         this.t2_os.close();
         this.closed = true;
         this.t2_os = null;
         this.osstub = null;
      }
   }

   public void writeBlock(byte[] b) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR OutputStream has been closed and cannot be used");
      } else {
         this.t2_os.write(b);
      }
   }

   public Object getSmartStub(Object stub) {
      if (this.osstub == null) {
         this.osstub = new JDBCOutputStreamStub((JDBCOutputStream)stub, this.verbose, this.block_size);
      }

      return this.osstub;
   }
}
