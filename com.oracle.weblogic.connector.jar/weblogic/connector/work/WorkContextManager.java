package weblogic.connector.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.resource.spi.work.WorkCompletedException;
import javax.resource.spi.work.WorkContext;
import weblogic.connector.common.Debug;
import weblogic.connector.security.SubjectStack;
import weblogic.connector.security.layer.WorkContextImpl;
import weblogic.connector.security.layer.WorkContextWrapper;
import weblogic.connector.security.layer.WorkImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.StackTraceUtils;

public class WorkContextManager {
   private WorkContextValidator validator = new WorkContextValidator();
   private List processors = new ArrayList(3);
   private Map processorMap = new HashMap();

   public void registerContext(WorkContextProcessor p) {
      if (this.processors.contains(p)) {
         throw new RuntimeException("WorkContextManager: cannot register duplicate ContextProcessor:" + p);
      } else {
         this.processors.add(p);
         this.processorMap.put(p.getSupportedContextClass(), p);
         this.validator.registerSupportedContext(p.getSupportedContextClass());
      }
   }

   public void unregisterContext(WorkContextProcessor p) {
      if (!this.processors.contains(p)) {
         throw new RuntimeException("WorkContextManager: cannot unregister unknown ContextProcessor:" + p);
      } else {
         this.processors.remove(p);
         this.processorMap.remove(p.getSupportedContextClass());
         this.validator.unregisterSupportedContext(p.getSupportedContextClass());
      }
   }

   public List getProcessors() {
      return this.processors;
   }

   public WorkContextValidator getValidator() {
      return this.validator;
   }

   private WorkContextProcessor getProcessor(WorkContextWrapper wc) {
      Class wcClass = this.getValidator().getSupportedClass(wc);
      return (WorkContextProcessor)this.processorMap.get(wcClass);
   }

   public WorkContextProcessor getProcessor(Class cls) {
      Class wcClass = this.getValidator().getSupportedClass(cls);
      return (WorkContextProcessor)this.processorMap.get(wcClass);
   }

   public void validate(List contexts, WorkRuntimeMetadata work) throws WorkCompletedException {
      if (Debug.isWorkEnabled()) {
         if (contexts.size() == 1) {
            Debug.work("WorkContextManager: validate: will validate 1 context: " + contexts.get(0));
         } else {
            Debug.work("WorkContextManager: validate: will validate " + contexts.size() + " contexts. dump begin:");
            Iterator var3 = contexts.iterator();

            while(var3.hasNext()) {
               WorkContextWrapper context = (WorkContextWrapper)var3.next();
               Debug.work("WorkContextManager: validate:   context: " + context);
            }

            Debug.work("WorkContextManager: validate: dump end: " + contexts.size() + " contexts.");
         }
      }

      List unsupported = this.validator.checkUnSupportedContexts(contexts);
      Iterator var5;
      WorkContextWrapper wc;
      if (!unsupported.isEmpty()) {
         String errMsg = "Following contexts are not supported: [";
         var5 = unsupported.iterator();

         while(var5.hasNext()) {
            wc = (WorkContextWrapper)var5.next();
            errMsg = errMsg + wc.getOriginalClass() + "; ";
            if (wc.supportWorkContextLifecycleListener()) {
               if (Debug.isWorkEventsEnabled()) {
                  Debug.workEvent("WorkContextManager: find unsupported context. calls contextSetupFailed(UNSUPPORTED_CONTEXT_TYPE) on WorkContextLifecycleListener " + wc);
               }

               wc.contextSetupFailed("1");
            }
         }

         errMsg = errMsg + "]";
         if (Debug.isWorkEnabled()) {
            Debug.work("WorkContextManager: validate: " + errMsg);
            Debug.work("WorkContextManager: validate: WorkCompletedException: set errorCode: WorkContextErrorCodes.UNSUPPORTED_CONTEXT_TYPE");
         }

         WorkCompletedException ex = new WorkCompletedException(errMsg);
         ex.setErrorCode("1");
         throw ex;
      } else {
         Map dupMap = this.validator.checkDuplicatedContexts(contexts);
         if (!dupMap.isEmpty()) {
            String errMsg = "Following contex(s) are duplicated: [";

            for(Iterator var17 = dupMap.entrySet().iterator(); var17.hasNext(); errMsg = errMsg + "] ") {
               Map.Entry entry = (Map.Entry)var17.next();
               Class cls = (Class)entry.getKey();
               errMsg = errMsg + " [context type " + cls + " is specified in following context instances: ";
               Iterator var22 = ((Set)entry.getValue()).iterator();

               while(var22.hasNext()) {
                  WorkContextWrapper wc = (WorkContextWrapper)var22.next();
                  errMsg = errMsg + wc.toString() + ";";
                  if (wc.supportWorkContextLifecycleListener()) {
                     errMsg = errMsg + wc.getOriginalClass() + "; ";
                     if (Debug.isWorkEventsEnabled()) {
                        Debug.workEvent("WorkContextManager: find duplicate contexts. calls contextSetupFailed(DUPLICATE_CONTEXTS) on WorkContextLifecycleListener " + wc);
                     }

                     wc.contextSetupFailed("2");
                  }
               }
            }

            errMsg = errMsg + "] ";
            if (Debug.isWorkEnabled()) {
               Debug.work("WorkContextManager: validate: " + errMsg);
               Debug.work("WorkContextManager: validate: WorkCompletedException: set errorCode: WorkContextErrorCodes.DUPLICATE_CONTEXTS");
            }

            WorkCompletedException ex = new WorkCompletedException(errMsg);
            ex.setErrorCode("2");
            throw ex;
         } else {
            var5 = contexts.iterator();

            while(var5.hasNext()) {
               wc = (WorkContextWrapper)var5.next();
               WorkContextProcessor p = this.getProcessor(wc);

               String errMsg;
               try {
                  if (Debug.isWorkEnabled()) {
                     Debug.work("WorkContextManager: validate: will validate context for " + wc + " using processor " + p);
                  }

                  errMsg = p.validate(wc, work);
               } catch (Throwable var12) {
                  if (Debug.isWorkEnabled()) {
                     Debug.work("WorkContextManager: validate: failed validate context for " + wc, var12);
                  }

                  errMsg = StackTraceUtils.throwable2StackTrace(var12);
               }

               if (errMsg != WorkContextProcessor.VALIDATION_OK) {
                  errMsg = "Failed to validate WorkContext [" + wc + "]: " + errMsg;
                  if (Debug.isWorkEnabled()) {
                     Debug.work("WorkContextManager: validate: WorkContext [" + wc + "] cannot be validated: " + errMsg);
                  }

                  if (wc.supportWorkContextLifecycleListener()) {
                     if (Debug.isWorkEventsEnabled()) {
                        Debug.workEvent("WorkContextManager: failed to validate context. calls contextSetupFailed(CONTEXT_SETUP_FAILED) on WorkContextLifecycleListener " + wc);
                     }

                     wc.contextSetupFailed("3");
                  }

                  WorkCompletedException ex = new WorkCompletedException(errMsg);
                  ex.setErrorCode("3");
                  if (Debug.isWorkEnabled()) {
                     Debug.work("WorkContextManager: validate: WorkCompletedException: set errorCode: WorkContextErrorCodes.CONTEXT_SETUP_FAILED");
                  }

                  throw ex;
               }

               if (Debug.isWorkEnabled()) {
                  Debug.work("WorkContextManager: validate: succeed validate context for " + wc);
               }
            }

         }
      }
   }

   public WorkContextProcessor.WMPreference getpreferredWM(List wcList) {
      WorkContextProcessor.WMPreference pref = WorkContextProcessor.WMPreference.defaultWM;
      if (wcList == null) {
         return pref;
      } else {
         Iterator var3 = wcList.iterator();

         while(var3.hasNext()) {
            WorkContextWrapper wc = (WorkContextWrapper)var3.next();
            WorkContextProcessor wcp = this.getProcessor(wc);
            if (wcp == null) {
               if (Debug.isWorkEnabled()) {
                  Debug.work("WorkContextManager: find unknown WorkContext [" + wc + "]. will use default WorkManager");
               }
               break;
            }

            WorkContextProcessor.WMPreference wmp = wcp.getpreferredWM(wc);
            if (WorkContextProcessor.WMPreference.defaultWM != wmp) {
               pref = wmp;
               break;
            }
         }

         return pref;
      }
   }

   public WorkContextProcessor.WMPreference getpreferredWM(WorkImpl work) {
      return work.supportWorkContextProvider() ? this.getpreferredWM(work.getWorkContexts()) : WorkContextProcessor.WMPreference.defaultWM;
   }

   public void setupContext(List contexts, WorkRuntimeMetadata work, List processedContexts) throws WorkCompletedException {
      Iterator var4 = contexts.iterator();

      while(var4.hasNext()) {
         WorkContextWrapper wc = (WorkContextWrapper)var4.next();
         WorkContextProcessor p = this.getProcessor(wc);

         try {
            if (Debug.isWorkEnabled()) {
               Debug.work("WorkContextManager: setupContext: will setup context for " + wc + " using processor " + p);
            }

            processedContexts.add(wc);
            p.setupContext(wc, work);
            if (Debug.isWorkEnabled()) {
               Debug.work("WorkContextManager: setupContext: succeed setup context for " + wc);
            }

            if (wc.supportWorkContextLifecycleListener()) {
               wc.contextSetupComplete();
            }
         } catch (Throwable var11) {
            String errorCode = null;
            if (var11 instanceof WorkCompletedException) {
               WorkCompletedException wce = (WorkCompletedException)var11;
               errorCode = wce.getErrorCode();
            }

            if (errorCode == null || errorCode.trim().length() == 0) {
               if (Debug.isWorkEnabled()) {
                  Debug.work("WorkContextManager: setupContext: using errorCode: WorkContextErrorCodes.CONTEXT_SETUP_FAILED: 3");
               }

               errorCode = "3";
            }

            String errMsg = "Failed to setup context for " + wc;
            if (Debug.isWorkEnabled()) {
               Debug.work("WorkContextManager: " + errMsg, var11);
            }

            if (wc.supportWorkContextLifecycleListener()) {
               wc.contextSetupFailed(errorCode);
            }

            WorkCompletedException ex = new WorkCompletedException(errMsg);
            ex.setErrorCode(errorCode);
            ex.initCause(var11);
            throw ex;
         }
      }

   }

   public List cleanupContext(List contexts, boolean executionSuccess, WorkRuntimeMetadata work) {
      List errors = new ArrayList();
      Iterator var5 = contexts.iterator();

      while(var5.hasNext()) {
         WorkContextWrapper wc = (WorkContextWrapper)var5.next();
         WorkContextProcessor p = this.getProcessor(wc);

         try {
            if (Debug.isWorkEnabled()) {
               Debug.work("WorkContextManager: cleanupContext: will cleanup context for " + wc + " using processor " + p);
            }

            p.cleanupContext(wc, executionSuccess, work);
            if (Debug.isWorkEnabled()) {
               Debug.work("WorkContextManager: cleanupContext: succeed cleanup context for " + wc);
            }
         } catch (Throwable var9) {
            if (Debug.isWorkEnabled()) {
               Debug.work("WorkContextManager: cleanupContext: Failed to cleanup context for " + wc + ": executionSuccess:" + executionSuccess, var9);
            }

            errors.add(var9);
         }
      }

      return errors;
   }

   public WorkContextWrapper createWrapper(WorkContext originalWorkContext, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      WorkContextProcessor p = this.getProcessor(originalWorkContext.getClass());
      Object wraper;
      if (p == null) {
         if (Debug.isWorkEnabled()) {
            Debug.work("WorkContextManager: createWrapper: find unknown WorkContext, use default wrapper. " + originalWorkContext.getClass());
         }

         wraper = new WorkContextImpl(originalWorkContext, adapterLayer, originalWorkContext.getClass().getName(), kernelId);
      } else {
         if (Debug.isWorkEnabled()) {
            Debug.work("WorkContextManager: createWrapper: find supported WorkContext " + originalWorkContext.getClass() + " as " + p.getSupportedContextClass());
         }

         wraper = p.createWrapper(originalWorkContext, adapterLayer, kernelId);
      }

      return (WorkContextWrapper)wraper;
   }
}
