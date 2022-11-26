package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.server.UnicastRemoteObject;

public class ArrayImpl extends RMISkelWrapperImpl implements InteropWriteReplaceable {
   private transient ArrayStub arrayStub = null;
   private RmiDriverSettings rmiDriverSettings = null;
   private java.sql.Array t2Array = null;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof java.sql.ResultSet) {
               java.sql.ResultSet rs = (java.sql.ResultSet)ret;
               ResultSetImpl rmi_rs = (ResultSetImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ResultSetImpl", rs, true);
               rmi_rs.init(rs, this.rmiDriverSettings);
               ret = (java.sql.ResultSet)rmi_rs;
            }
         } catch (Exception var6) {
            JDBCLogger.logStackTrace(var6);
            throw var6;
         }

         super.postInvocationHandler(methodName, params, ret);
         return ret;
      }
   }

   public void init(java.sql.Array anArray, RmiDriverSettings settings) {
      this.t2Array = anArray;
      this.rmiDriverSettings = new RmiDriverSettings(settings);
   }

   public static java.sql.Array makeArrayImpl(java.sql.Array anArray, RmiDriverSettings rmiDriverSettings) {
      ArrayImpl rmi_array = (ArrayImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ArrayImpl", anArray, true);
      rmi_array.init(anArray, rmiDriverSettings);
      return (java.sql.Array)rmi_array;
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      Object stub = StubFactory.getStub((Remote)this);
      return new ArrayStub((Array)stub, this.rmiDriverSettings);
   }

   public void internalClose() {
      try {
         UnicastRemoteObject.unexportObject(this, true);
         this.t2Array = null;
         this.rmiDriverSettings = null;
      } catch (NoSuchObjectException var2) {
      }

   }
}
