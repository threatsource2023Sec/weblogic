package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.rmi.Remote;
import java.sql.NClob;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.StubFactory;

public class OracleTNClobImpl extends OracleTClobImpl {
   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      if (this.interop == null) {
         Object stub = StubFactory.getStub((Remote)this);
         this.interop = new OracleTNClobStub((OracleTNClob)stub, this.rmiSettings);
      }

      return this.interop;
   }

   public static NClob makeOracleTNClobImpl(NClob aNClob, RmiDriverSettings rmiDriverSettings) {
      OracleTNClobImpl rmi_nclob = (OracleTNClobImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.OracleTNClobImpl", aNClob, true);
      rmi_nclob.init(aNClob, rmiDriverSettings);
      return (NClob)rmi_nclob;
   }
}
