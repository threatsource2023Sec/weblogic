package weblogic.jdbc.rmi.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.rmi.RMIStubWrapperImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class ArrayStub extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = -3520000790072523739L;
   private Array remoteArray = null;
   private RmiDriverSettings rmiDriverSettings = null;

   public ArrayStub() {
   }

   public ArrayStub(Array array, RmiDriverSettings settings) {
      this.init(array, settings);
   }

   public void init(Array array, RmiDriverSettings settings) {
      this.remoteArray = array;
      this.rmiDriverSettings = new RmiDriverSettings(settings);
   }

   public Object readResolve() throws ObjectStreamException {
      ArrayStub stub = null;

      try {
         stub = (ArrayStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ArrayStub", this.remoteArray, false);
         stub.init(this.remoteArray, this.rmiDriverSettings);
         return (java.sql.Array)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.remoteArray;
      }
   }

   public void internalClose() {
      this.remoteArray.internalClose();
   }
}
