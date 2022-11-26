package weblogic.jdbc.rmi.internal;

import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.sql.SQLException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.BlockGetter;
import weblogic.jdbc.common.internal.InputStreamHandler;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.rmi.RMIStubWrapperImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.server.StubDelegateInfo;

public class OracleTBlobStub extends RMIStubWrapperImpl implements Serializable, StubDelegateInfo {
   private static final long serialVersionUID = 9079787653929248555L;
   OracleTBlob remoteB;
   private RmiDriverSettings rmiSettings = null;

   public OracleTBlobStub() {
   }

   public OracleTBlobStub(OracleTBlob b, RmiDriverSettings settings) {
      this.init(b, settings);
   }

   public void init(OracleTBlob b, RmiDriverSettings settings) {
      this.remoteB = b;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public static java.sql.Blob makeOracleTBlobStub(java.sql.Blob anBlob, RmiDriverSettings settings) {
      OracleTBlobStub blob_stub = (OracleTBlobStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTBlobStub", anBlob, false);
      blob_stub.init((OracleTBlob)anBlob, settings);
      return (java.sql.Blob)blob_stub;
   }

   public void internalClose() {
      this.remoteB.internalClose();
   }

   public Object readResolve() throws ObjectStreamException {
      OracleTBlobStub stub = null;

      try {
         stub = (OracleTBlobStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTBlobStub", this.remoteB, false);
         stub.init(this.remoteB, this.rmiSettings);
         return (java.sql.Blob)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.remoteB;
      }
   }

   public InputStream getBinaryStream() throws SQLException {
      InputStreamHandler ish = null;
      String methodName = "getBinaryStream";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         if (this.rmiSettings.isVerbose()) {
            String msg = "time=" + System.currentTimeMillis() + " : getBinaryStream";
            JdbcDebug.JDBCRMIInternal.debug(msg);
         }

         BlockGetter bg = this.remoteB.getBlockGetter();
         int blockid = this.remoteB.registerStream(3);
         ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, ish);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ish;
   }

   public Object getStubDelegate() {
      return this.remoteB;
   }
}
