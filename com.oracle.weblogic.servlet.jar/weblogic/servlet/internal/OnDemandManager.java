package weblogic.servlet.internal;

import weblogic.management.DeploymentException;
import weblogic.servlet.utils.ServletMapping;

public final class OnDemandManager {
   private ServletMapping contextTable = new ServletMapping();

   public synchronized void registerOnDemandContextPaths(String[] contextPaths, OnDemandListener listener, String appName, boolean displayRefresh) {
      synchronized(this.contextTable) {
         ServletMapping newTable = (ServletMapping)this.contextTable.clone();

         for(int i = 0; i < contextPaths.length; ++i) {
            String cp = contextPaths[i];
            Object o = this.contextTable.get(cp);
            if (o != null) {
               throw new IllegalArgumentException("Context path " + cp + " is already registered");
            }

            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Registering OnDemandContext with context-root: '" + cp + "'");
            }

            OnDemandContext newCtx = new OnDemandContext(cp, listener, appName, displayRefresh);
            newTable.put(toPattern(cp), newCtx);
         }

         this.contextTable = newTable;
      }
   }

   public synchronized void unregisterOnDemandContextPaths(String[] contextPaths) {
      synchronized(this.contextTable) {
         ServletMapping newTable = (ServletMapping)this.contextTable.clone();

         for(int i = 0; i < contextPaths.length; ++i) {
            String cp = contextPaths[i];
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Unregistering OnDemandContext with context-root: '" + cp + "'");
            }

            OnDemandContext ctx = (OnDemandContext)this.contextTable.get(toPattern(cp));
            if (ctx != null) {
               newTable.remove(toPattern(cp));
            }
         }

         this.contextTable = newTable;
      }
   }

   public OnDemandContext lookupOnDemandContext(String uri) {
      return uri == null ? null : (OnDemandContext)this.contextTable.get(uri);
   }

   public void loadOnDemandURI(OnDemandContext ctx, boolean loadAsynchronously) throws DeploymentException {
      if (ctx != null) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Loading on demand context: " + ctx.getAppName() + " load async: " + loadAsynchronously);
         }

         OnDemandListener listener = ctx.getListener();
         listener.OnDemandURIAccessed(ctx.getContextPath(), ctx.getAppName(), loadAsynchronously);
      }
   }

   private static String toPattern(String path) {
      if (!path.equals("") && !path.equals("/")) {
         if (!path.startsWith("/") && !path.startsWith("*.")) {
            path = "/" + path;
         }

         return path.endsWith("/") ? path + "*" : path + "/*";
      } else {
         return "/";
      }
   }
}
