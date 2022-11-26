package weblogic.corba.rmi;

import java.io.IOException;
import java.rmi.Remote;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.Delegate;
import weblogic.corba.idl.DelegateFactory;
import weblogic.iiop.IIOPRemoteRef;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.ior.IOR;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.extensions.server.CollocatedRemoteReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.StubInfoIntf;

public class Stub extends javax.rmi.CORBA.Stub implements weblogic.rmi.extensions.server.Stub, Remote {
   private String[] ids;
   private final RemoteReference ror;
   private static final long serialVersionUID = -2889933658618898055L;

   private Stub() {
      this.ror = null;
   }

   public Stub(StubReference info) {
      this.ror = info.getRemoteRef();
      RemoteReference r = this.ror;
      IOR ior = null;
      Delegate delegate = null;
      if (r instanceof ClusterableRemoteRef) {
         r = ((ClusterableRemoteRef)this.ror).getPrimaryRef();
      }

      if (r instanceof CollocatedRemoteReference) {
         try {
            ior = (IOR)IIOPReplacer.getIIOPReplacer().replaceObject(r);
         } catch (IOException var6) {
            throw new INV_OBJREF(var6.getMessage());
         }

         delegate = DelegateFactory.createDelegate(ior);
      } else {
         ior = ((IIOPRemoteRef)r).getIOR();
         delegate = DelegateFactory.createDelegate(ior);
      }

      this._set_delegate(delegate);
      this.ids = new String[]{ior.getTypeId().toString()};
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof Stub) {
         Stub other = (Stub)obj;
         return this.ror.equals(other.ror);
      } else if (obj instanceof StubInfoIntf) {
         StubInfoIntf intf = (StubInfoIntf)obj;
         return this.ror.equals(intf.getStubInfo().getRemoteRef());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.ror.hashCode();
   }

   public void connect(ORB orb) {
   }

   public String[] _ids() {
      return this.ids;
   }

   public String toString() {
      return this._get_delegate().toString();
   }

   public RemoteReference getRemoteRef() {
      return this.ror;
   }
}
