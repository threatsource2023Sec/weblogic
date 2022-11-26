package weblogic.jdbc.rmi.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.rmi.RMIStubWrapperImpl;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class ParameterMetaDataStub extends RMIStubWrapperImpl implements Serializable {
   ParameterMetaData remoteParameterMetaData;
   private RmiDriverSettings rmiDriverSettings;

   public ParameterMetaDataStub() {
   }

   public ParameterMetaDataStub(ParameterMetaData pmd, RmiDriverSettings settings) {
      this.init(pmd, settings);
   }

   public void init(ParameterMetaData pmd, RmiDriverSettings settings) {
      this.remoteParameterMetaData = pmd;
      this.rmiDriverSettings = settings;
   }

   public Object readResolve() throws ObjectStreamException {
      ParameterMetaDataStub stub = null;

      try {
         stub = (ParameterMetaDataStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ParameterMetaDataStub", this.remoteParameterMetaData, false);
         stub.init(this.remoteParameterMetaData, this.rmiDriverSettings);
         return (java.sql.ParameterMetaData)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.remoteParameterMetaData;
      }
   }
}
