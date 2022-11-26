package weblogic.connector.deploy;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;

public class TransportableJNDIHandler extends JNDIHandler {
   public Object getObjectInstance(Object refObj, Name name, Context ctx, Hashtable env) throws Exception {
      Reference ref = null;
      String classFactory = null;
      if (!(refObj instanceof Reference)) {
         return null;
      } else {
         ref = (Reference)refObj;
         classFactory = ref.getFactoryClassName();
         return !classFactory.equals(TransportableJNDIHandler.class.getName()) ? null : this.lookupObject(refObj, name, ctx, env, ref, classFactory);
      }
   }
}
