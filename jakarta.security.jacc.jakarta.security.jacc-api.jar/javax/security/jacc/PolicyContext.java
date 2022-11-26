package javax.security.jacc;

import java.security.SecurityPermission;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class PolicyContext {
   private static ThreadLocal threadLocalContextID = new ThreadLocal();
   private static ThreadLocal threadLocalHandlerData = new ThreadLocal();
   private static Map handlerTable = new ConcurrentHashMap();

   private PolicyContext() {
   }

   public static void setContextID(String contextID) {
      checkSetPolicyPermission();
      threadLocalContextID.set(contextID);
   }

   public static String getContextID() {
      return (String)threadLocalContextID.get();
   }

   public static void setHandlerData(Object data) {
      checkSetPolicyPermission();
      threadLocalHandlerData.set(data);
   }

   public static void registerHandler(String key, PolicyContextHandler handler, boolean replace) throws PolicyContextException {
      if (handler != null && key != null) {
         if (!handler.supports(key)) {
            throw new IllegalArgumentException("handler does not support key");
         } else {
            checkSetPolicyPermission();
            if (handlerTable.containsKey(key) && !replace) {
               throw new IllegalArgumentException("handler exists");
            } else {
               handlerTable.put(key, handler);
            }
         }
      } else {
         throw new IllegalArgumentException("invalid (null) key or handler");
      }
   }

   public static Set getHandlerKeys() {
      return handlerTable.keySet();
   }

   public static Object getContext(String key) throws PolicyContextException {
      if (key == null) {
         throw new IllegalArgumentException("invalid key");
      } else {
         PolicyContextHandler handler = (PolicyContextHandler)handlerTable.get(key);
         if (handler != null && handler.supports(key)) {
            checkSetPolicyPermission();
            return handler.getContext(key, threadLocalHandlerData.get());
         } else {
            throw new IllegalArgumentException("unknown handler key");
         }
      }
   }

   private static void checkSetPolicyPermission() {
      SecurityManager securityManager = System.getSecurityManager();
      if (securityManager != null) {
         securityManager.checkPermission(new SecurityPermission("setPolicy"));
      }

   }
}
