package weblogic.jdbc.rmi.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.rmi.RMIStubWrapperImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class StructStub extends RMIStubWrapperImpl implements Serializable {
   private Struct remoteStruct = null;
   private RmiDriverSettings rmiDriverSettings = null;

   public StructStub() {
   }

   public StructStub(Struct struct, RmiDriverSettings settings) {
      this.init(struct, settings);
   }

   public void init(Struct struct, RmiDriverSettings settings) {
      this.remoteStruct = struct;
      this.rmiDriverSettings = new RmiDriverSettings(settings);
   }

   public Object readResolve() throws ObjectStreamException {
      StructStub stub = null;

      try {
         stub = (StructStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.StructStub", this.remoteStruct, false);
         stub.init(this.remoteStruct, this.rmiDriverSettings);
         return (java.sql.Struct)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.remoteStruct;
      }
   }
}
