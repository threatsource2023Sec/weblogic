package weblogic.messaging.path;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.AsyncResultFactory;
import weblogic.rmi.extensions.AsyncResultListener;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.utils.StackTraceUtils;

public class CompletionAsyncResultAdapter extends CompletionRequest implements CompletionListener, AsyncResultListener {
   private final AsyncResult asyncResult;
   private final FutureResponse futureResponse;
   private final CompletionRequest completionRequest;
   private final ExceptionAdapter exceptionAdapter;
   private static final NullResult singletonNullResult = new NullResult();

   public CompletionAsyncResultAdapter(FutureResponse futureResponse, AsyncResult asyncResult, ExceptionAdapter exceptionAdapter) {
      this.asyncResult = asyncResult;
      this.futureResponse = futureResponse;
      this.exceptionAdapter = exceptionAdapter;
      this.addListener(this);
      this.completionRequest = null;
   }

   public CompletionAsyncResultAdapter(CompletionRequest completionRequest) {
      this.completionRequest = completionRequest;
      this.addListener(this);
      this.asyncResult = null;
      this.futureResponse = null;
      this.exceptionAdapter = null;
   }

   public AsyncResult getCallbackableResult() {
      return AsyncResultFactory.getCallbackableResult(this);
   }

   public final void handleResult(AsyncResult asyncResultArgument) {
      Object result;
      try {
         result = asyncResultArgument.getObject();
         if (result instanceof NullResult) {
            result = null;
         }
      } catch (Throwable var4) {
         Throwable throwable = wrapException(this.exceptionAdapter, var4);
         this.completionRequest.setResult(throwable);
         this.exceptionMonitor(throwable);
         return;
      }

      if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
         PathHelper.PathSvcVerbose.debug("CompletionAsyncResultAdapter result " + result);
      }

      if (result instanceof Throwable) {
         result = wrapException(this.exceptionAdapter, (Throwable)result);
      }

      this.completionRequest.setResult(result);
   }

   static Throwable unwrapException(ExceptionAdapter exceptionAdapter, Throwable t) {
      if (exceptionAdapter != null) {
         t = exceptionAdapter.unwrapException(t);
      }

      return t;
   }

   static Throwable wrapException(ExceptionAdapter exceptionAdapter, Throwable t) {
      if (exceptionAdapter != null) {
         t = exceptionAdapter.wrapException(t);
      }

      return t;
   }

   Throwable wrapException(Throwable t) {
      return wrapException(this.exceptionAdapter, t);
   }

   void exceptionMonitor(Throwable t) {
   }

   public final void onException(CompletionRequest completionRequest, Throwable reason) {
      if (PathHelper.retired && PathHelper.PathSvcVerbose.isDebugEnabled()) {
         PathHelper.PathSvc.debug("debug setResult ", reason);
      }

      this.onCompletion(completionRequest, reason);
   }

   public final void onCompletion(CompletionRequest completionRequest, Object result) {
      try {
         Throwable throwable;
         if (result instanceof Throwable) {
            throwable = unwrapException(this.exceptionAdapter, (Throwable)result);
            throwable = StackTraceUtils.getThrowableWithCause(throwable);
            result = throwable;
         } else if (result == null) {
            throwable = null;
            result = singletonNullResult;
         } else {
            throwable = null;
         }

         if (throwable != null && this.futureResponse != null) {
            this.futureResponse.sendThrowable(throwable);
         } else if (this.asyncResult != null) {
            this.asyncResult.setResult(result);
            if (this.futureResponse != null) {
               this.futureResponse.send();
            }
         } else {
            this.futureResponse.getMsgOutput().writeObject(result, result.getClass());
            this.futureResponse.send();
         }
      } catch (RemoteException var5) {
         if (PathHelper.PathSvc.isDebugEnabled()) {
            PathHelper.PathSvc.debug(var5.getMessage(), var5);
         }
      } catch (IOException var6) {
         if (PathHelper.PathSvc.isDebugEnabled()) {
            PathHelper.PathSvc.debug(var6.getMessage(), var6);
         }
      }

   }

   public static final class NullResult implements Serializable {
      static final long serialVersionUID = -3666697078933257427L;
   }
}
