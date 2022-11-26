package weblogic.jdbc.rmi.internal;

import java.io.ObjectStreamException;
import java.sql.NClob;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class OracleTNClobStub extends OracleTClobStub {
   private static final long serialVersionUID = -3757015803239435378L;

   public OracleTNClobStub() {
   }

   public OracleTNClobStub(OracleTNClob nclob, RmiDriverSettings settings) {
      super(nclob, settings);
   }

   public static NClob makeOracleTNClobStub(NClob aNClob, RmiDriverSettings settings) {
      OracleTNClobStub nclob_stub = (OracleTNClobStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTNClobStub", aNClob, false);
      nclob_stub.init((OracleTNClob)aNClob, settings);
      return (NClob)nclob_stub;
   }

   public Object readResolve() throws ObjectStreamException {
      OracleTNClobStub stub = null;

      try {
         stub = (OracleTNClobStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTNClobStub", this.getStubDelegate(), false);
         stub.init((OracleTNClob)this.getStubDelegate(), this.rmiSettings);
         return (NClob)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.getStubDelegate();
      }
   }
}
