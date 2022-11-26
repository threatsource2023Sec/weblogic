package weblogic.corba.application.binding;

import java.io.DataInputStream;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;
import weblogic.corba.j2ee.naming.ORBHelper;

public class ORBObjectFactory implements ObjectFactory {
   public Object getObjectInstance(Object ignore1, Name ignore2, Context ignore3, Hashtable ignore4) throws NamingException {
      try {
         return Class.forName((new DataInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/services/org.omg.CORBA.ORB"))).readLine()).newInstance();
      } catch (Exception var6) {
         return ORBHelper.getORBHelper().getLocalORB();
      }
   }
}
