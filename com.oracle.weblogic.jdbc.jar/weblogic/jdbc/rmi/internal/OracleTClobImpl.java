package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.sql.SQLException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.BlockGetterImpl;
import weblogic.jdbc.common.internal.JDBCOutputStreamImpl;
import weblogic.jdbc.common.internal.JDBCWriterImpl;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ReaderBlockGetter;
import weblogic.jdbc.common.internal.ReaderBlockGetterImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.JDBCWrapperImpl;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.server.UnicastRemoteObject;

public class OracleTClobImpl extends RMISkelWrapperImpl implements InteropWriteReplaceable {
   private java.sql.Clob t2_clob = null;
   RmiDriverSettings rmiSettings = null;
   private BlockGetterImpl bg = null;
   private ReaderBlockGetterImpl rbg = null;
   public static final int ASCII_STREAM = 1;
   public static final int CHAR_STREAM = 2;
   transient Object interop = null;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof Writer) {
               Writer wtr = (Writer)ret;
               JDBCWriterImpl jwtr = new JDBCWriterImpl(wtr, this.rmiSettings.isVerbose(), this.rmiSettings.getChunkSize());
               ret = jwtr;
            } else if (ret instanceof OutputStream) {
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

   public void init(java.sql.Clob b, RmiDriverSettings settings) {
      this.t2_clob = b;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      if (this.interop == null) {
         Object stub = StubFactory.getStub((Remote)this);
         this.interop = new OracleTClobStub((OracleTClob)stub, this.rmiSettings);
      }

      return this.interop;
   }

   public static java.sql.Clob makeOracleTClobImpl(java.sql.Clob anClob, RmiDriverSettings rmiDriverSettings) {
      OracleTClobImpl rmi_clob = (OracleTClobImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTClobImpl", anClob, true);
      rmi_clob.init(anClob, rmiDriverSettings);
      return (java.sql.Clob)rmi_clob;
   }

   public int registerStream(int streamType) throws SQLException {
      return this.registerStream(streamType, (Object[])null);
   }

   public int registerStream(int streamType, Object[] params) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : registerStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      synchronized(this) {
         if (streamType == 1) {
            if (this.bg == null) {
               this.bg = new BlockGetterImpl();
            }
         } else {
            if (streamType != 2) {
               throw new SQLException("Invalid stream type: " + streamType);
            }

            if (this.rbg == null) {
               this.rbg = new ReaderBlockGetterImpl();
            }
         }
      }

      InputStream is;
      int blockid;
      if (streamType == 1) {
         is = this.t2_clob.getAsciiStream();
         blockid = this.bg.register(is, this.rmiSettings.getChunkSize());
      } else {
         is = null;
         Reader rdr;
         if (params == null) {
            rdr = this.t2_clob.getCharacterStream();
         } else {
            rdr = this.t2_clob.getCharacterStream((Long)params[0], (Long)params[1]);
         }

         blockid = this.rbg.register(rdr, this.rmiSettings.getChunkSize());
      }

      return blockid;
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

   public ReaderBlockGetter getReaderBlockGetter() throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getBlockGetter";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      synchronized(this) {
         if (this.rbg == null) {
            this.rbg = new ReaderBlockGetterImpl();
         }
      }

      return this.rbg;
   }

   public void internalClose() {
      try {
         UnicastRemoteObject.unexportObject(this, true);
         this.t2_clob = null;
         this.rmiSettings = null;
      } catch (NoSuchObjectException var2) {
      }

   }

   public long position(java.sql.Clob searchstr, long start) throws SQLException {
      long ret = -1L;
      String methodName = "position";
      Object[] params = new Object[]{searchstr, start};

      try {
         this.preInvocationHandler(methodName, params);
         if (searchstr instanceof JDBCWrapperImpl) {
            ret = ((java.sql.Clob)this.getVendorObj()).position((java.sql.Clob)((JDBCWrapperImpl)searchstr).getVendorObj(), start);
         } else {
            ret = ((java.sql.Clob)this.getVendorObj()).position(searchstr, start);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }
}
