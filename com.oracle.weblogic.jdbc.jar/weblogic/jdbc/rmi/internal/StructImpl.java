package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.rmi.Remote;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.StubFactory;

public class StructImpl extends RMISkelWrapperImpl implements InteropWriteReplaceable {
   private transient StructStub structStub = null;
   private RmiDriverSettings rmiDriverSettings = null;
   private java.sql.Struct t2Struct = null;

   public void init(java.sql.Struct anStruct, RmiDriverSettings settings) {
      this.t2Struct = anStruct;
      this.rmiDriverSettings = new RmiDriverSettings(settings);
   }

   public static java.sql.Struct makeStructImpl(java.sql.Struct anStruct, RmiDriverSettings rmiDriverSettings) {
      StructImpl rmi_struct = (StructImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.StructImpl", anStruct, true);
      rmi_struct.init(anStruct, rmiDriverSettings);
      return (java.sql.Struct)rmi_struct;
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      Object stub = StubFactory.getStub((Remote)this);
      return new StructStub((Struct)stub, this.rmiDriverSettings);
   }
}
