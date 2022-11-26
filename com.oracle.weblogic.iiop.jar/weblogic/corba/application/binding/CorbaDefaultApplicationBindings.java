package weblogic.corba.application.binding;

import java.util.HashMap;
import java.util.Map;
import javax.naming.Reference;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.env.bindings.DefaultBindings;

@Service
public class CorbaDefaultApplicationBindings implements DefaultBindings {
   public static final String LOCAL_ORB_BINDING = "java:comp/ORB";
   private Map defaultBindings = new HashMap();

   public CorbaDefaultApplicationBindings() {
      this.defaultBindings.put("java:comp/ORB", new Reference("org.omg.CORBA.ORB", ORBObjectFactory.class.getName(), (String)null));
   }

   public Map getDefaultBindings() {
      return this.defaultBindings;
   }
}
