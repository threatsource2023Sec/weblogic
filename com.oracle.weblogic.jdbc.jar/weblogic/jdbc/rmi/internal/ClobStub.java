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

public class ClobStub extends RMIStubWrapperImpl implements Serializable, StubDelegateInfo {
   private static final long serialVersionUID = 5514150181551731451L;
   Clob remoteC;
   private RmiDriverSettings rmiSettings = null;

   public void init(Clob c, RmiDriverSettings settings) {
      this.remoteC = c;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public void internalClose() {
      this.remoteC.internalClose();
   }

   public Object readResolve() throws ObjectStreamException {
      ClobStub stub = null;

      try {
         stub = (ClobStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ClobStub", this.remoteC, false);
         stub.init(this.remoteC, this.rmiSettings);
         return (java.sql.Clob)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.remoteC;
      }
   }

   public InputStream getAsciiStream() throws SQLException {
      InputStreamHandler ish = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getAsciiStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getAsciiStream";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         BlockGetter bg = this.getBlockGetter();
         int blockid = this.registerStream(1);
         ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, ish);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ish;
   }

   public Reader getCharacterStream() throws SQLException {
      ReaderHandler rdr = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getCharacterStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getCharacterStream";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ReaderBlockGetter rbg = this.getReaderBlockGetter();
         int blockid = this.registerStream(2);
         rdr = new ReaderHandler();
         rdr.setReaderBlockGetter(rbg, blockid);
         this.postInvocationHandler(methodName, params, rdr);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return rdr;
   }

   public int registerStream(int stype) throws SQLException {
      return this.remoteC.registerStream(stype);
   }

   public BlockGetter getBlockGetter() throws SQLException {
      return this.remoteC.getBlockGetter();
   }

   public ReaderBlockGetter getReaderBlockGetter() throws SQLException {
      return this.remoteC.getReaderBlockGetter();
   }

   public Object getStubDelegate() {
      return this.remoteC;
   }
}
