package weblogic.corba.orb;

import java.util.Hashtable;
import java.util.Properties;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.Service;
import weblogic.corba.j2ee.naming.EndPointSelector;
import weblogic.corba.j2ee.naming.ORBHelper;
import weblogic.corba.j2ee.naming.WlsClient;

@WlsClient
@Service
@Rank(10)
public class ORBHelperImpl extends ORBHelper {
   public static final String ORB_CLASS = "weblogic.corba.orb.ORB";

   public ORBHelperImpl() {
      WlsIIOPInitialization.initialize();
   }

   public String getORBClass() {
      return "weblogic.corba.orb.ORB";
   }

   protected boolean useWlsIiopClient() {
      return true;
   }

   protected Properties createProperties(Hashtable env, EndPointSelector epi, String protocol, String initialRef) {
      Properties props = super.createProperties(env, epi, protocol, initialRef);
      props.put("org.omg.CORBA.ORBInitRef", initialRef);
      return props;
   }
}
