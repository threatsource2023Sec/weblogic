package weblogic.jdbc.rmi.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.rmi.RMIStubWrapperImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class RefStub extends RMIStubWrapperImpl implements Serializable {
   private Ref remoteRef = null;
   private RmiDriverSettings rmiDriverSettings = null;

   public RefStub() {
   }

   public RefStub(Ref ref, RmiDriverSettings settings) {
      this.init(ref, settings);
   }

   public void init(Ref ref, RmiDriverSettings settings) {
      this.remoteRef = ref;
      this.rmiDriverSettings = new RmiDriverSettings(settings);
   }

   public Object readResolve() throws ObjectStreamException {
      RefStub stub = null;

      try {
         stub = (RefStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.RefStub", this.remoteRef, false);
         stub.init(this.remoteRef, this.rmiDriverSettings);
         return (java.sql.Ref)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.remoteRef;
      }
   }
}
