package weblogic.corba.idl.poa;

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.portable.Delegate;
import weblogic.corba.utils.RemoteInfo;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORDelegate;

class POADelegateImpl implements Delegate, IORDelegate {
   private final POAImpl poa;
   private final IOR ior;
   private final RemoteInfo rinfo;
   private final byte[] oid;

   POADelegateImpl(POAImpl poa, IOR ior, RemoteInfo rinfo, byte[] oid) {
      this.poa = poa;
      this.ior = ior;
      this.rinfo = rinfo;
      this.oid = oid;
   }

   public final IOR getIOR() {
      return this.ior;
   }

   public boolean non_existent(Servant self) {
      return false;
   }

   public byte[] object_id(Servant self) {
      return this.oid;
   }

   public boolean is_a(Servant self, String repository_id) {
      String[] myIds = self._all_interfaces(this.poa, this.oid);

      for(int i = 0; i < myIds.length; ++i) {
         if (repository_id.equals(myIds[i])) {
            return true;
         }
      }

      return repository_id.equals("IDL:omg.org/CORBA/Object:1.0");
   }

   public ORB orb(Servant self) {
      return this.poa._orb();
   }

   public Object get_interface_def(Servant self) {
      throw new NO_IMPLEMENT();
   }

   public Object this_object(Servant self) {
      try {
         return IIOPReplacer.createCORBAStub(this.ior, (Class)null, (Class)null);
      } catch (Exception var3) {
         throw (AssertionError)(new AssertionError()).initCause(var3);
      }
   }

   public POA default_POA(Servant Self) {
      return this.poa;
   }

   public POA poa(Servant Self) {
      return this.poa;
   }

   public String toString() {
      return this.ior.getTypeId() + "(" + System.identityHashCode(this) + "): " + this.rinfo;
   }
}
