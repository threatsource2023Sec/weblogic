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

public class SQLXMLStub extends RMIStubWrapperImpl implements Serializable {
   private SQLXML remoteSQLXML = null;
   private RmiDriverSettings rmiDriverSettings = null;

   public SQLXMLStub() {
   }

   public SQLXMLStub(SQLXML sqlxml, RmiDriverSettings settings) {
      this.init(sqlxml, settings);
   }

   public void init(SQLXML sqlxml, RmiDriverSettings settings) {
      this.remoteSQLXML = sqlxml;
      this.rmiDriverSettings = new RmiDriverSettings(settings);
   }

   public Object readResolve() throws ObjectStreamException {
      SQLXMLStub stub = null;

      try {
         stub = (SQLXMLStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.SQLXMLStub", this.remoteSQLXML, false);
         stub.init(this.remoteSQLXML, this.rmiDriverSettings);
         return (java.sql.SQLXML)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.remoteSQLXML;
      }
   }

   public void internalClose() {
      this.remoteSQLXML.internalClose();
   }

   public InputStream getBinaryStream() throws SQLException {
      InputStreamHandler ish = null;
      String methodName = "getBinaryStream";
      Object[] params = new Object[0];
      if (this.rmiDriverSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : " + methodName;
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      try {
         this.preInvocationHandler(methodName, params);
         BlockGetter bg = this.remoteSQLXML.getBlockGetter();
         int blockid = this.remoteSQLXML.registerStream(1);
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
      String methodName = "getCharacterStream";
      Object[] params = new Object[0];
      if (this.rmiDriverSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : " + methodName;
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      try {
         this.preInvocationHandler(methodName, params);
         ReaderBlockGetter rbg = this.remoteSQLXML.getReaderBlockGetter();
         int blockid = this.remoteSQLXML.registerStream(2);
         rdr = new ReaderHandler();
         rdr.setReaderBlockGetter(rbg, blockid);
         this.postInvocationHandler(methodName, params, rdr);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return rdr;
   }
}
