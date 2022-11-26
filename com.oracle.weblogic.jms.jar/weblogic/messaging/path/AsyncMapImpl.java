package weblogic.messaging.path;

import java.io.Serializable;
import java.security.AccessController;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class AsyncMapImpl implements AsyncMapRemote {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected String jndiName;
   private final AsyncMap asyncMapDelegate;
   protected final ExceptionAdapter exceptionAdapter;
   protected final PathHelper pathHelper;

   AsyncMapImpl(String jndiName, PathHelper pathHelper, AsyncMap asyncMapDelegate) {
      this.jndiName = jndiName;
      this.pathHelper = pathHelper;
      this.exceptionAdapter = pathHelper.jmsOrderExceptionAdapter();
      this.asyncMapDelegate = asyncMapDelegate;
   }

   private static void impossible(String method) {
      throw new Error("must not invoke this '" + method + "' signature on " + ManagementService.getRuntimeAccess(kernelId).getServerName());
   }

   public final void get(Serializable key, AsyncResult asyncResult) {
      impossible("get");
   }

   public final void get(Serializable key, AsyncResult asyncResult, FutureResponse futureResponse) {
      CompletionAsyncResultAdapter completionRequest = new CompletionAsyncResultAdapter(futureResponse, asyncResult, this.exceptionAdapter);
      if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
         PathHelper.PathSvcVerbose.debug("AsyncMapImpl get " + key);
      }

      try {
         this.asyncMapDelegate.get(key, completionRequest);
      } catch (RuntimeException var6) {
         notifyCaller(completionRequest, var6);
         throw var6;
      } catch (Error var7) {
         notifyCaller(completionRequest, var7);
         throw var7;
      }
   }

   private static void notifyCaller(CompletionAsyncResultAdapter completionRequest, Throwable throwable) {
      synchronized(completionRequest) {
         if (completionRequest.hasResult()) {
            return;
         }
      }

      completionRequest.setResult(throwable);
   }

   public void remove(Serializable key, Serializable value, AsyncResult asyncResult) {
      impossible("remove");
   }

   public void remove(Serializable key, Serializable value, AsyncResult asyncResult, FutureResponse futureResponse) {
      CompletionAsyncResultAdapter completionRequest = new CompletionAsyncResultAdapter(futureResponse, asyncResult, this.exceptionAdapter);
      if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
         PathHelper.PathSvcVerbose.debug("AsyncMapImpl remove " + key + ", value:" + value);
      }

      try {
         this.asyncMapDelegate.remove(key, value, completionRequest);
      } catch (RuntimeException var7) {
         notifyCaller(completionRequest, var7);
         throw var7;
      } catch (Error var8) {
         notifyCaller(completionRequest, var8);
         throw var8;
      }
   }

   public void put(Serializable key, Serializable value, AsyncResult asyncResult) {
      impossible("put");
   }

   public void put(Serializable key, Serializable value, AsyncResult asyncResult, FutureResponse futureResponse) {
      CompletionAsyncResultAdapter completionRequest = new CompletionAsyncResultAdapter(futureResponse, asyncResult, this.exceptionAdapter);
      if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
         PathHelper.PathSvcVerbose.debug("AsyncMapImpl put " + key + ", value:" + value);
      }

      try {
         this.asyncMapDelegate.put(key, value, completionRequest);
      } catch (RuntimeException var7) {
         notifyCaller(completionRequest, var7);
         throw var7;
      } catch (Error var8) {
         notifyCaller(completionRequest, var8);
         throw var8;
      }
   }

   public void putIfAbsent(Serializable key, Serializable value, AsyncResult asyncResult) {
      impossible("putIfAbsent");
   }

   public void putIfAbsent(Serializable key, Serializable value, AsyncResult asyncResult, FutureResponse futureResponse) {
      CompletionAsyncResultAdapter completionRequest = new CompletionAsyncResultAdapter(futureResponse, asyncResult, this.exceptionAdapter);
      if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
         PathHelper.PathSvcVerbose.debug("AsyncMapImpl putIfAbsent " + key + ", value:" + value);
      }

      try {
         this.asyncMapDelegate.putIfAbsent(key, value, completionRequest);
      } catch (RuntimeException var7) {
         notifyCaller(completionRequest, var7);
         throw var7;
      } catch (Error var8) {
         notifyCaller(completionRequest, var8);
         throw var8;
      }
   }

   PathHelper getPathHelper() {
      return this.pathHelper;
   }

   public String getJndiName() {
      return this.jndiName;
   }
}
