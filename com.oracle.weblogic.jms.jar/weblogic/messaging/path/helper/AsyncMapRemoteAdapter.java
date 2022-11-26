package weblogic.messaging.path.helper;

import java.io.Serializable;
import java.rmi.RemoteException;
import weblogic.common.CompletionRequest;
import weblogic.messaging.path.AsyncMapRemote;
import weblogic.messaging.path.AsyncMapWithId;
import weblogic.messaging.path.CompletionAsyncResultAdapter;
import weblogic.messaging.path.ExceptionAdapter;

class AsyncMapRemoteAdapter implements AsyncMapWithId {
   private final AsyncMapRemote asyncMapRemote;
   private final ExceptionAdapter exceptionAdapter;
   private final PathHelper pathHelper;
   private final String jndiName;

   AsyncMapRemoteAdapter(PathHelper pathHelper, String jndiName, AsyncMapRemote asyncMapRemote, ExceptionAdapter exceptionAdapter) {
      this.pathHelper = pathHelper;
      this.asyncMapRemote = asyncMapRemote;
      this.exceptionAdapter = exceptionAdapter;
      this.jndiName = jndiName;
   }

   public String getJndiName() {
      return this.jndiName;
   }

   private void emergencyClose(Exception exception) {
      this.pathHelper.handleException(exception, this);
   }

   public void putIfAbsent(Object key, Object value, CompletionRequest completionRequest) {
      if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
         PathHelper.PathSvcVerbose.debug("RemoteAdapter Key:" + key + ", value:" + value);
      }

      try {
         this.asyncMapRemote.putIfAbsent((Serializable)key, (Serializable)value, (new RemoteCatcher(completionRequest)).getCallbackableResult());
      } catch (RuntimeException var5) {
         notifyCaller(completionRequest, var5, this.exceptionAdapter);
         throw var5;
      } catch (Error var6) {
         notifyCaller(completionRequest, var6, this.exceptionAdapter);
         throw var6;
      } catch (RemoteException var7) {
         this.emergencyClose(var7);
         notifyCaller(completionRequest, var7, this.exceptionAdapter);
      }
   }

   private static void notifyCaller(CompletionRequest completionRequest, Throwable throwable, ExceptionAdapter exceptionAdapter) {
      synchronized(completionRequest) {
         if (completionRequest.hasResult()) {
            return;
         }
      }

      completionRequest.setResult(exceptionAdapter.wrapException(throwable));
   }

   public void put(Object key, Object value, CompletionRequest completionRequest) {
      try {
         this.asyncMapRemote.put((Serializable)key, (Serializable)value, (new RemoteCatcher(completionRequest)).getCallbackableResult());
      } catch (RuntimeException var5) {
         notifyCaller(completionRequest, var5, this.exceptionAdapter);
         throw var5;
      } catch (Error var6) {
         notifyCaller(completionRequest, var6, this.exceptionAdapter);
         throw var6;
      } catch (RemoteException var7) {
         this.emergencyClose(var7);
         notifyCaller(completionRequest, var7, this.exceptionAdapter);
      }
   }

   public void get(Object key, CompletionRequest completionRequest) {
      try {
         this.asyncMapRemote.get((Serializable)key, (new RemoteCatcher(completionRequest)).getCallbackableResult());
      } catch (RuntimeException var4) {
         notifyCaller(completionRequest, var4, this.exceptionAdapter);
         throw var4;
      } catch (Error var5) {
         notifyCaller(completionRequest, var5, this.exceptionAdapter);
         throw var5;
      } catch (RemoteException var6) {
         this.emergencyClose(var6);
         notifyCaller(completionRequest, var6, this.exceptionAdapter);
      }
   }

   public void remove(Object key, Object oldValue, CompletionRequest completionRequest) {
      try {
         this.asyncMapRemote.remove((Serializable)key, (Serializable)oldValue, (new RemoteCatcher(completionRequest)).getCallbackableResult());
      } catch (RuntimeException var5) {
         notifyCaller(completionRequest, var5, this.exceptionAdapter);
         throw var5;
      } catch (Error var6) {
         notifyCaller(completionRequest, var6, this.exceptionAdapter);
         throw var6;
      } catch (RemoteException var7) {
         this.emergencyClose(var7);
         notifyCaller(completionRequest, var7, this.exceptionAdapter);
      }
   }

   private class RemoteCatcher extends CompletionAsyncResultAdapter {
      AsyncMapRemoteAdapter remoteAdapter = AsyncMapRemoteAdapter.this;

      RemoteCatcher(CompletionRequest completionRequest) {
         super(completionRequest);
      }

      void exceptionMonitor(Throwable t) {
         if (t instanceof RemoteException) {
            this.remoteAdapter.emergencyClose((RemoteException)t);
         }

      }
   }
}
