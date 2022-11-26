package weblogic.management.jmx;

import java.util.HashSet;
import java.util.Set;

class InvocationHandlerObjectNameManager extends ObjectNameManagerBase {
   private static String[] classes = new String[]{"weblogic.descriptor.DescriptorBean", "weblogic.management.WebLogicMBean", "weblogic.management.commo.StandardInterface", "weblogic.management.mbeanservers.Service"};

   public InvocationHandlerObjectNameManager() {
      super(getClassArray());
   }

   private static Class[] getClassArray() {
      Set s = new HashSet();

      for(int i = 0; i < classes.length; ++i) {
         try {
            s.add(Class.forName(classes[i]));
         } catch (ClassNotFoundException var3) {
         }
      }

      return (Class[])((Class[])s.toArray(new Class[0]));
   }
}
