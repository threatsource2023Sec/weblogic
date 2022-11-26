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

public class BlobStub extends RMIStubWrapperImpl implements Serializable, StubDelegateInfo {
   private static final long serialVersionUID = 6113511803561203420L;
   Blob remoteB;
   private RmiDriverSettings rmiSettings = null;

   public void init(Blob b, RmiDriverSettings settings) {
      this.remoteB = b;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public void internalClose() {
      this.remoteB.internalClose();
   }

   public Object readResolve() throws ObjectStreamException {
      BlobStub stub = null;

      try {
         stub = (BlobStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.BlobStub", this.remoteB, false);
         stub.init(this.remoteB, this.rmiSettings);
         return (java.sql.Blob)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.remoteB;
      }
   }

   public InputStream getBinaryStream() throws SQLException {
      InputStreamHandler ish = null;
      String methodName;
      if (this.rmiSettings.isVerbose()) {
         methodName = "time=" + System.currentTimeMillis() + " : getBinaryStream";
         JdbcDebug.JDBCRMIInternal.debug(methodName);
      }

      methodName = "getBinaryStream";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         BlockGetter bg = this.getBlockGetter();
         int blockid = this.registerStream(3);
         ish = new InputStreamHandler();
         ish.setBlockGetter(bg, blockid);
         this.postInvocationHandler(methodName, params, ish);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ish;
   }

   public int registerStream(int stype) throws SQLException {
      return this.remoteB.registerStream(stype);
   }

   public BlockGetter getBlockGetter() throws SQLException {
      return this.remoteB.getBlockGetter();
   }

   public Object getStubDelegate() {
      return this.remoteB;
   }
}
