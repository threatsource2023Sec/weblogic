package weblogic.jdbc.rmi.internal;

import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.SQLException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.InputStreamHandler;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ReaderBlockGetter;
import weblogic.jdbc.common.internal.ReaderHandler;
import weblogic.jdbc.rmi.RMIStubWrapperImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.server.StubDelegateInfo;

public class OracleTClobStub extends RMIStubWrapperImpl implements Serializable, StubDelegateInfo {
   private static final long serialVersionUID = 2758119947660786307L;
   OracleTClob remoteC;
   RmiDriverSettings rmiSettings = null;

   public OracleTClobStub() {
   }

   public OracleTClobStub(OracleTClob c, RmiDriverSettings settings) {
      this.init(c, settings);
   }

   public void init(OracleTClob c, RmiDriverSettings settings) {
      this.remoteC = c;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public static java.sql.Clob makeOracleTClobStub(java.sql.Clob anClob, RmiDriverSettings settings) {
      OracleTClobStub clob_stub = (OracleTClobStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTClobStub", anClob, false);
      clob_stub.init((OracleTClob)anClob, settings);
      return (java.sql.Clob)clob_stub;
   }

   public void internalClose() {
      this.remoteC.internalClose();
   }

   public Object readResolve() throws ObjectStreamException {
      OracleTClobStub stub = null;

      try {
         stub = (OracleTClobStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTClobStub", this.remoteC, false);
         stub.init(this.remoteC, this.rmiSettings);
         return (java.sql.Clob)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.remoteC;
      }
   }

   public InputStream getAsciiStream() throws SQLException {
      InputStreamHandler ish = null;
      String methodName = "getAsciiStream";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         if (this.rmiSettings.isVerbose()) {
            String msg = "time=" + System.currentTimeMillis() + " : " + methodName;
            JdbcDebug.JDBCRMIInternal.debug(msg);
         }

         BlockGetter bg = this.remoteC.getBlockGetter();
         int blockid = this.remoteC.registerStream(1);
         ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, ish);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ish;
   }

   public Reader getCharacterStream() throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      ReaderHandler rdr = null;
      String methodName = "getCharacterStream";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ReaderBlockGetter rbg = this.remoteC.getReaderBlockGetter();
         int blockid = this.remoteC.registerStream(2);
         rdr = new ReaderHandler();
         rdr.setReaderBlockGetter(rbg, blockid);
         this.postInvocationHandler(methodName, params, rdr);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return rdr;
   }

   public Reader getCharacterStream(long pos, long len) throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : getCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      ReaderHandler rdr = null;
      String methodName = "getCharacterStream";
      Object[] params = new Object[]{pos, len};

      try {
         this.preInvocationHandler(methodName, params);
         ReaderBlockGetter rbg = this.remoteC.getReaderBlockGetter();
         int blockid = this.remoteC.registerStream(2, new Object[]{pos, len});
         rdr = new ReaderHandler();
         rdr.setReaderBlockGetter(rbg, blockid);
         this.postInvocationHandler(methodName, params, rdr);
      } catch (Exception var10) {
         this.invocationExceptionHandler(methodName, params, var10);
      }

      return rdr;
   }

   public Object getStubDelegate() {
      return this.remoteC;
   }
}
