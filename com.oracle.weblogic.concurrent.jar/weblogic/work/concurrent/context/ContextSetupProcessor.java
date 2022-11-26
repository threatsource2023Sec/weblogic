package weblogic.work.concurrent.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.work.concurrent.spi.ContextProviderManager;

public class ContextSetupProcessor implements ContextProvider {
   private static final long serialVersionUID = 575102790285645002L;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentContext");
   protected final int concurrentType;
   private List defaultProviderList;
   private ContextProvider submittingCompStateChecker;

   public ContextSetupProcessor(String appId, String moduleId, int concurrentType) {
      this(concurrentType, DefaultContextsProvider.getContextServiceInstance(), DefaultContextsProvider.getNonContextServiceInstance(), CICContextProvider.getInstance(), new SubmittingCompStateCheckerProvider(appId), ClassLoaderContextProvider.getInstance(), JndiContextProvider.getInstance(), SecurityContextProvider.getInstance(), WorkAreaContextProvider.getInstance());
   }

   public ContextSetupProcessor(String appId, String moduleId, String contextServiceID, String contextServiceInfo) {
      this(1, DefaultContextsProvider.getContextServiceInstance(), DefaultContextsProvider.getNonContextServiceInstance(), CICContextProvider.getInstance(), new StateCheckerProvider(contextServiceID, contextServiceInfo), new SubmittingCompStateCheckerProvider(appId), ClassLoaderContextProvider.getInstance(), JndiContextProvider.getInstance(), SecurityContextProvider.getInstance(), WorkAreaContextProvider.getInstance());
   }

   protected ContextSetupProcessor(int concurrentType, ContextProvider... contextProviders) {
      this.concurrentType = concurrentType;
      this.defaultProviderList = new ArrayList();
      ContextProvider[] var3 = contextProviders;
      int var4 = contextProviders.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ContextProvider cp = var3[var5];
         if (cp instanceof SubmittingCompStateCheckerProvider) {
            this.submittingCompStateChecker = cp;
         }

         cp = this.acceptContextProvider(cp);
         if (cp != null) {
            this.defaultProviderList.add(cp);
         }
      }

   }

   protected ContextProvider acceptContextProvider(ContextProvider cp) {
      if (cp == null) {
         return null;
      } else {
         String contextType = cp.getContextType();
         if (contextType == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("reject ContextProvider with null context type:" + cp);
            }

            return null;
         } else if ((cp.getConcurrentObjectType() & this.concurrentType) == 0) {
            debugLogger.debug("reject ContextProvider:" + cp + " with concurrentType " + cp.getConcurrentObjectType());
            return null;
         } else {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("accept ContextProvider:" + cp + " with contextType " + contextType);
            }

            return cp;
         }
      }
   }

   public ContextHandle save(Map executionProperties) {
      ContextSetupHandles handles = new ContextSetupHandles(this.defaultProviderList);
      this.addCustomProviderList(handles);
      if (executionProperties != null) {
         executionProperties = Collections.unmodifiableMap(executionProperties);
      }

      try {
         Iterator var3 = handles.getProviderList().iterator();

         while(var3.hasNext()) {
            ContextProvider cp = (ContextProvider)var3.next();
            handles.addContextHandle(cp.save(executionProperties));
         }

         return handles;
      } catch (Throwable var5) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(var5.getMessage(), var5);
         }

         ConcurrencyLogger.logSaveContextExp(var5);
         return null;
      }
   }

   public ContextHandle setup(ContextHandle handle) throws IllegalStateException {
      if (!(handle instanceof ContextSetupHandles)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip processor setup: " + handle);
         }

         return null;
      } else {
         ContextSetupHandles handles = (ContextSetupHandles)handle;
         List handleList = handles.getHandleList();
         List providerList = handles.getProviderList();
         int handleSize = handleList.size();
         int providerSize = providerList.size();
         if (handleSize != providerSize) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("skip processor setup, handle size=" + handleSize + " provider size=" + providerSize);
            }

            return null;
         } else {
            ContextSetupHandles successHandleList = new ContextSetupHandles(handles.getProviderList());
            boolean expThrown = false;
            boolean var23 = false;

            ContextSetupHandles var28;
            try {
               var23 = true;
               int pos = 0;

               while(true) {
                  if (pos >= providerSize) {
                     var28 = successHandleList;
                     var23 = false;
                     break;
                  }

                  successHandleList.addContextHandle(((ContextProvider)providerList.get(pos)).setup((ContextHandle)handleList.get(pos)));
                  ++pos;
               }
            } catch (Throwable var26) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(var26.getMessage(), var26);
               }

               ConcurrencyLogger.logSetupContextExp(var26);
               expThrown = true;
               if (var26 instanceof IllegalStateException) {
                  throw var26;
               }

               throw new IllegalStateException(var26);
            } finally {
               if (var23) {
                  if (expThrown) {
                     List succList = successHandleList.getHandleList();
                     int succSize = succList.size();

                     for(int pos = succSize; pos > 0; --pos) {
                        try {
                           ((ContextProvider)providerList.get(pos - 1)).reset((ContextHandle)succList.get(pos - 1));
                        } catch (Throwable var24) {
                           if (debugLogger.isDebugEnabled()) {
                              debugLogger.debug(var24.getMessage(), var24);
                           }

                           ConcurrencyLogger.logResetContextExp(var24);
                        }
                     }
                  }

               }
            }

            if (expThrown) {
               List succList = successHandleList.getHandleList();
               int succSize = succList.size();

               for(int pos = succSize; pos > 0; --pos) {
                  try {
                     ((ContextProvider)providerList.get(pos - 1)).reset((ContextHandle)succList.get(pos - 1));
                  } catch (Throwable var25) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug(var25.getMessage(), var25);
                     }

                     ConcurrencyLogger.logResetContextExp(var25);
                  }
               }
            }

            return var28;
         }
      }
   }

   public void reset(ContextHandle handle) {
      if (!(handle instanceof ContextSetupHandles)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip processor reset: " + handle);
         }

      } else {
         ContextSetupHandles handles = (ContextSetupHandles)handle;
         List handleList = handles.getHandleList();
         List providerList = handles.getProviderList();
         int handleSize = handleList.size();
         int providerSize = providerList.size();
         if (handleSize != providerSize) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("skip processor reset, handle size=" + handleSize + " provider size=" + providerSize);
            }

         } else {
            for(int pos = providerSize; pos > 0; --pos) {
               try {
                  ((ContextProvider)providerList.get(pos - 1)).reset((ContextHandle)handleList.get(pos - 1));
               } catch (Throwable var9) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(var9.getMessage(), var9);
                  }

                  ConcurrencyLogger.logResetContextExp(var9);
               }
            }

         }
      }
   }

   protected void addCustomProviderList(ContextSetupHandles handles) {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext invocation = manager.getCurrentComponentInvocationContext();
      List cps = ContextProviderManager.getContextProviders(invocation.getApplicationId(), invocation.getModuleName(), invocation.getComponentName());
      if (cps != null) {
         Iterator var5 = cps.iterator();

         while(var5.hasNext()) {
            ContextProvider provider = (ContextProvider)var5.next();
            ContextProvider cp = this.acceptContextProvider(provider);
            if (cp != null) {
               handles.addContextProvider(cp);
            }
         }

      }
   }

   public String getContextType() {
      return "internal";
   }

   public int getConcurrentObjectType() {
      return 13;
   }

   public ContextProvider getSubmittingCompStateChecker() {
      return this.submittingCompStateChecker;
   }

   public static class ContextSetupHandles implements ContextHandle {
      private static final long serialVersionUID = 6278632560109603478L;
      private List handleList = new ArrayList();
      private List providerList = new ArrayList();

      ContextSetupHandles(List providerList) {
         if (providerList != null) {
            Iterator var2 = providerList.iterator();

            while(var2.hasNext()) {
               ContextProvider cp = (ContextProvider)var2.next();
               if (cp != null) {
                  this.providerList.add(cp);
               }
            }
         }

      }

      public void addContextHandle(ContextHandle saveContext) {
         this.handleList.add(saveContext);
      }

      public List getHandleList() {
         return this.handleList;
      }

      public List getProviderList() {
         return this.providerList;
      }

      public void addContextProvider(ContextProvider cp) {
         if (cp != null) {
            this.providerList.add(cp);
         }

      }
   }
}
