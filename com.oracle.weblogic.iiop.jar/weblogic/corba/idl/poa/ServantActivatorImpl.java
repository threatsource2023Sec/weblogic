package weblogic.corba.idl.poa;

import org.omg.CORBA_2_3.portable.ObjectImpl;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantActivator;
import org.omg.PortableServer.ServantActivatorHelper;

public class ServantActivatorImpl extends ObjectImpl implements ServantActivator {
   public String[] _ids() {
      return new String[]{ServantActivatorHelper.id()};
   }

   public Servant incarnate(byte[] oid, POA adapter) throws ForwardRequest {
      return null;
   }

   public void etherealize(byte[] oid, POA adapter, Servant serv, boolean cleanup_in_progress, boolean remaining_activations) {
   }
}
