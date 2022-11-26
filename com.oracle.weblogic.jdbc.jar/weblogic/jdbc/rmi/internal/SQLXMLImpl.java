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
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.server.UnicastRemoteObject;

public class SQLXMLImpl extends RMISkelWrapperImpl implements InteropWriteReplaceable {
   public static final int BINARY_STREAM = 1;
   public static final int CHARACTER_STREAM = 2;
   private java.sql.SQLXML t2_sqlxml = null;
   private RmiDriverSettings rmiDriverSettings = null;
   private BlockGetterImpl bg = null;
   private ReaderBlockGetterImpl rbg = null;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof Writer) {
               Writer wtr = (Writer)ret;
               JDBCWriterImpl jwtr = new JDBCWriterImpl(wtr, this.rmiDriverSettings.isVerbose(), this.rmiDriverSettings.getChunkSize());
               ret = jwtr;
            } else if (ret instanceof OutputStream) {
               OutputStream os = (OutputStream)ret;
               JDBCOutputStreamImpl jos = new JDBCOutputStreamImpl(os, this.rmiDriverSettings.isVerbose(), this.rmiDriverSettings.getChunkSize());
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

   public void init(java.sql.SQLXML xmlObject, RmiDriverSettings settings) {
      this.t2_sqlxml = xmlObject;
      this.rmiDriverSettings = new RmiDriverSettings(settings);
   }

   public static java.sql.SQLXML makeSQLXMLImpl(java.sql.SQLXML xmlObject, RmiDriverSettings rmiDriverSettings) {
      SQLXMLImpl rmi_sqlxml = (SQLXMLImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.SQLXMLImpl", xmlObject, true);
      rmi_sqlxml.init(xmlObject, rmiDriverSettings);
      return (java.sql.SQLXML)rmi_sqlxml;
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      Object stub = StubFactory.getStub((Remote)this);
      return new SQLXMLStub((SQLXML)stub, this.rmiDriverSettings);
   }

   public int registerStream(int streamType) throws SQLException {
      if (this.rmiDriverSettings.isVerbose()) {
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

      int blockid;
      if (streamType == 1) {
         InputStream is = this.t2_sqlxml.getBinaryStream();
         blockid = this.bg.register(is, this.rmiDriverSettings.getChunkSize());
      } else {
         Reader rdr = this.t2_sqlxml.getCharacterStream();
         blockid = this.rbg.register(rdr, this.rmiDriverSettings.getChunkSize());
      }

      return blockid;
   }

   public BlockGetter getBlockGetter() throws SQLException {
      if (this.rmiDriverSettings.isVerbose()) {
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
      if (this.rmiDriverSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getReaderBlockGetter";
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
         this.t2_sqlxml = null;
         this.rmiDriverSettings = null;
      } catch (NoSuchObjectException var2) {
      }

   }
}
