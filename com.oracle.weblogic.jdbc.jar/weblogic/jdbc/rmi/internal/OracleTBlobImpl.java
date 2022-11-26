package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.sql.SQLException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.BlockGetterImpl;
import weblogic.jdbc.common.internal.JDBCOutputStreamImpl;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.server.UnicastRemoteObject;

public class OracleTBlobImpl extends RMISkelWrapperImpl implements InteropWriteReplaceable {
   private java.sql.Blob t2_blob = null;
   private BlockGetterImpl bg = null;
   private RmiDriverSettings rmiSettings = null;
   public static final int BINARY_STREAM = 3;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof OutputStream) {
               OutputStream os = (OutputStream)ret;
               JDBCOutputStreamImpl jos = new JDBCOutputStreamImpl(os, this.rmiSettings.isVerbose(), this.rmiSettings.getChunkSize());
               ret = jos;
            }
         } catch (Exception var6) {
            JDBCLogger.logStackTrace(var6);
            throw var6;
         }

         super.postInvocationHandler(methodName, params, ret);
         return ret;
      }
   }

   public void init(java.sql.Blob b, RmiDriverSettings settings) {
      this.t2_blob = b;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      Object stub = StubFactory.getStub((Remote)this);
      return new OracleTBlobStub((OracleTBlob)stub, this.rmiSettings);
   }

   public static java.sql.Blob makeOracleTBlobImpl(java.sql.Blob anBlob, RmiDriverSettings rmiDriverSettings) {
      OracleTBlobImpl rmi_blob = (OracleTBlobImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTBlobImpl", anBlob, true);
      rmi_blob.init(anBlob, rmiDriverSettings);
      return (java.sql.Blob)rmi_blob;
   }

   public int registerStream(int streamType) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : registerStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      synchronized(this) {
         if (this.bg == null) {
            this.bg = new BlockGetterImpl();
         }
      }

      if (streamType == 3) {
         InputStream is = this.t2_blob.getBinaryStream();
         int var3 = this.bg.register(is, this.rmiSettings.getChunkSize());
         return var3;
      } else {
         throw new SQLException("Invalid stream type: " + streamType);
      }
   }

   public BlockGetter getBlockGetter() throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getBlockGetter";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      synchronized(this) {
         if (this.bg == null) {
            this.bg = new BlockGetterImpl();
         }
      }

      return this.bg;
   }

   public void internalClose() {
      try {
         UnicastRemoteObject.unexportObject(this, true);
         this.t2_blob = null;
         this.rmiSettings = null;
      } catch (NoSuchObjectException var2) {
      }

   }
}
