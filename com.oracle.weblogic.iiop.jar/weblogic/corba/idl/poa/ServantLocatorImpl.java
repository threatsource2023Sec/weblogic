package weblogic.corba.idl.poa;

import org.omg.CORBA_2_3.portable.ObjectImpl;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantLocator;
import org.omg.PortableServer.ServantLocatorHelper;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;

public class ServantLocatorImpl extends ObjectImpl implements ServantLocator {
   public String[] _ids() {
      return new String[]{ServantLocatorHelper.id()};
   }

   public void postinvoke(byte[] oid, POA adapter, String operation, Object the_cookie, Servant the_servant) {
   }

   public Servant preinvoke(byte[] oid, POA adapter, String operation, CookieHolder the_cookie) throws ForwardRequest {
      return null;
   }
}
