package weblogic.diagnostics.debug;

import java.lang.reflect.Method;

public final class DebugAttributesDiscoveryHelper {
   private static final boolean DEBUG = false;

   public static void discoverDebugAttributes(Object debugBean, DebugAttributesDiscoveryCallback callback) throws DebugAttributesDiscoveryException {
      Method[] m = debugBean.getClass().getMethods();

      for(int i = 0; i < m.length; ++i) {
         if (m[i].getName().startsWith("get") && m[i].getParameterTypes().length == 0 && m[i].getReturnType() == Boolean.TYPE) {
            String attrName = m[i].getName().substring(3);

            try {
               Boolean enabled = (Boolean)m[i].invoke(debugBean, (Object[])null);
               callback.debugAttributeDiscovered(attrName, enabled);
            } catch (Exception var6) {
               throw new DebugAttributesDiscoveryException(var6.getMessage(), var6);
            }
         }
      }

   }
}
