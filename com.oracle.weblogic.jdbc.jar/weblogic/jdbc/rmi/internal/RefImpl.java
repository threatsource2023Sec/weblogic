package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.rmi.Remote;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.StubFactory;

public class RefImpl extends RMISkelWrapperImpl implements InteropWriteReplaceable {
   private transient RefStub refStub = null;
   private RmiDriverSettings rmiDriverSettings = null;
   private java.sql.Ref t2Ref = null;

   public void init(java.sql.Ref anRef, RmiDriverSettings settings) {
      this.t2Ref = anRef;
      this.rmiDriverSettings = new RmiDriverSettings(settings);
   }

   public static java.sql.Ref makeRefImpl(java.sql.Ref anRef, RmiDriverSettings rmiDriverSettings) {
      RefImpl rmi_ref = (RefImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.RefImpl", anRef, true);
      rmi_ref.init(anRef, rmiDriverSettings);
      return (java.sql.Ref)rmi_ref;
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      Object stub = StubFactory.getStub((Remote)this);
      return new RefStub((Ref)stub, this.rmiDriverSettings);
   }
}
