package weblogic.application.naming;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;

public class MessageDestinationObjectFactory implements ObjectFactory {
   public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable env) throws NamingException {
      if (!(obj instanceof MessageDestinationReference)) {
         throw new AssertionError("ObjectFactory should have been referenced only from EnvReference");
      } else {
         return ((MessageDestinationReference)obj).lookupMessageDestination();
      }
   }
}
