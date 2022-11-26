package weblogic.messaging.dispatcher;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.InvocableManagerDelegate;
import weblogic.messaging.ID;
import weblogic.messaging.common.IDImpl;
import weblogic.messaging.common.MessagingUtilities;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.AsyncResultListener;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.work.WorkManager;

public abstract class Request extends Response implements Runnable, AsyncResultListener, Externalizable {
   static final long serialVersionUID = -3580248041850964617L;
   protected ID invocableId;
   protected int methodId;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int INVOCABLE_ID_MASK = 256;
   private static final int CORRELATION_ID_MASK = 512;
   public static final int START = 0;
   public static final int FINISH = 2;
   public static final int COMPLETED = Integer.MAX_VALUE;
   public static final int REMOTE = -42;
   private int state;
   private Response result;
   private Throwable throwableResponse;
   private Request parent;
   private Request child;
   protected Request next;
   private int numWaiting;
   private int numChildren;
   private boolean isCollocated;
   private boolean running;
   private WorkManager workManager;
   private boolean isSyncRequest;
   private AsyncResult asyncResult;
   private FutureResponse futureResponse;
   private boolean needFillInStackTrace;
   private int tranInfo;
   public static final int NO_TRAN = 0;
   public static final int HAVE_TRAN = 1;
   public static final int DONT_KNOW_IF_TRAN = 2;
   private Transaction transaction;
   private Invocable invocable;
   private InvocableMonitor invocableMonitor;
   private InteropJMSVoidResponsePreDiablo appVoidResponse;
   private boolean shortened;
   private volatile boolean parentResumeNewThread;
   private CompletionListener completionListener;
   private transient DispatcherPartition4rmic dispatcherPartition4rmic;
   static boolean TODOSAGREMOVEreportOnce = true;
   private static SimpleDateFormat sdf = new SimpleDateFormat("(EEE MMM dd, HH:mm:ss.SSS)");
   public static final int RMI_TRANSACTION = 1;
   public static final int RMI_FUTURE_RESPONSE = 2;
   public static final int RMI_SYNC = 16;
   public static final int RMI_ASYNC_RESULT = 32;
   public static final int RMI_ONEWAY = 64;

   public Request(ID invocableId, int methodId) {
      this(invocableId, methodId, new VoidResponse());
   }

   public Request(ID invocableId, int methodId, InteropJMSVoidResponsePreDiablo appVoidResponse) {
      this.invocableId = invocableId;
      this.methodId = methodId;
      this.result = this;
      this.isCollocated = true;
      this.appVoidResponse = appVoidResponse;
   }

   public final void setAppVoidResponse(InteropJMSVoidResponsePreDiablo appVoidResponse) {
      this.appVoidResponse = appVoidResponse;
   }

   InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public final void setInvocableId(ID invocableId) {
      this.invocableId = invocableId;
   }

   public final ID getInvocableId() {
      return this.invocableId;
   }

   public final void setMethodId(int methodId) {
      this.methodId = methodId;
   }

   public final int getMethodId() {
      return this.methodId;
   }

   public final void clearResult() {
      this.result = this;
      this.throwableResponse = null;
   }

   public final void clearState() {
      this.clearResult();
      this.setNumChildren(0);
   }

   private int getNumChildren() {
      return this.numChildren;
   }

   private void setNumChildren(int val) {
      this.numChildren = val;
   }

   int incNumChildren() {
      return ++this.numChildren;
   }

   private int decNumChildren() {
      return --this.numChildren;
   }

   public boolean isServerOneWay() {
      return false;
   }

   public boolean isServerToServer() {
      return false;
   }

   public void setDispatcherPartition4rmic(DispatcherPartition4rmic dispatcherPartition4rmic) {
      if (this.dispatcherPartition4rmic == null) {
         this.dispatcherPartition4rmic = dispatcherPartition4rmic;
      }

   }

   public void clearDispatcherPartition4rmic() {
      this.dispatcherPartition4rmic = null;
   }

   protected DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.dispatcherPartition4rmic;
   }

   protected WorkManager getWorkManager() {
      return this.workManager != null ? this.workManager : this.getDispatcherPartition4rmic().getDefaultWorkManager();
   }

   public void setWorkManager(WorkManager wm) {
      this.workManager = wm;
   }

   public final boolean hasResults() {
      return this.result != this || this.throwableResponse != null;
   }

   public final void setResult(Response result) {
      this.result = result;
   }

   final void notifyResult(Throwable throwable, boolean needFillInStackTrace) {
      Request resumeExecution;
      boolean switchThreads;
      InvocableMonitor invocableMonitor;
      synchronized(this) {
         resumeExecution = this.childResult(throwable, needFillInStackTrace);
         switchThreads = this.parentResumeNewThread;
         invocableMonitor = this.invocableMonitor;
      }

      this.doExecute(resumeExecution, switchThreads, invocableMonitor);
   }

   private void doExecute(Request resumeExecution, boolean switchThreads, InvocableMonitor invocableMonitor) {
      if (invocableMonitor != null) {
         this.clearInvocableMonitor();
      }

      if (resumeExecution == this) {
         if (switchThreads) {
            this.getWorkManager().schedule(this);
         } else {
            this.run();
         }
      } else if (resumeExecution != null) {
         resumeExecution.resumeExecution(switchThreads);
      }

   }

   Request childResult(Response candidate) {
      if (this.throwableResponse == null && (this.result == this || this.result == null && candidate != null || this.result instanceof InteropJMSVoidResponsePreDiablo && !(candidate instanceof InteropJMSVoidResponsePreDiablo))) {
         this.setResult(candidate);
      }

      return this.decrementNumChildren();
   }

   private Request childResult(Throwable throwableResponse, boolean needFillInStackTrace) {
      if (this.throwableResponse == null) {
         this.throwableResponse = throwableResponse;
         this.needFillInStackTrace = needFillInStackTrace;
         if (this.getNumChildren() > 1 || this.futureResponse != null) {
            this.setNumChildren(1);
         }
      }

      return this.decrementNumChildren();
   }

   void complete(Throwable throwable, boolean needFillInStackTrace) {
      this.setParentResumeNewThread(true);
      this.notifyResult(throwable, needFillInStackTrace);
   }

   private Request decrementNumChildren() {
      if (this.getNumChildren() > 0) {
         if (this.decNumChildren() == 0) {
            if (this.numWaiting > 0) {
               this.notifyAll();
            }

            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               JMSDebug.JMSDispatcher.debug("Request():: decrementNumChildren/true ZERO numChildren=" + this.getNumChildren() + " state=" + this.dbgState(this.state) + " running=" + this.isRunning() + " this=" + this + " parent=" + this.parent);
            }

            if (this.state == -42) {
               this.setState(Integer.MAX_VALUE);
               this.doCompletionListener(true);
               return this.parent;
            }

            if (this.isRunning()) {
               return null;
            }

            return this;
         }

         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("Request():: decrementNumChildren/true POSITIVE numChildren=" + this.getNumChildren() + " state=" + this.dbgState(this.state) + " running=" + this.isRunning() + " " + this);
         }
      } else {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("Request():: decrementNumChildren/true IGNORED numChildren=" + this.getNumChildren() + " state=" + this.dbgState(this.state) + " running=" + this.isRunning() + " " + this);
         }

         this.notifyAll();
      }

      return null;
   }

   boolean hasListener() {
      return this.completionListener != null;
   }

   void doCompletionListener(boolean scheduleCompletionListener) {
      final Response response;
      final Throwable throwable;
      final CompletionListener listener;
      synchronized(this) {
         if (this.completionListener == null) {
            return;
         }

         listener = this.completionListener;
         this.completionListener = null;
         response = this.result;
         throwable = this.throwableResponse;
         if (scheduleCompletionListener) {
            this.getWorkManager().schedule(new Runnable() {
               public void run() {
                  Request.runCompletionListener(listener, throwable, response);
               }
            });
            return;
         }
      }

      runCompletionListener(listener, throwable, response);
   }

   public void rememberChild(Request newChild) {
      if (this.child != null) {
         if (this.child.throwableResponse != null) {
            return;
         }

         if (newChild.throwableResponse == null && (this.child.result != null && newChild.result == null || this.child.result instanceof InteropJMSVoidResponsePreDiablo && !(newChild.result instanceof InteropJMSVoidResponsePreDiablo))) {
            return;
         }
      }

      this.child = newChild;
   }

   public Request getChild() {
      return this.child;
   }

   public synchronized void waitForNotRunningResult() {
      if (!this.hasResults() || this.isRunning() || this.getState() != Integer.MAX_VALUE && this.getState() != -42 || this.throwableResponse == null && this.getNumChildren() != 0) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("Request():: waitForNotRunningResult() state=" + this.dbgState(this.state) + ", numChildren=" + this.getNumChildren() + " running=" + this.isRunning() + " " + this);
         }

         this.sleepTillNotified(true);
      }
   }

   private String dbgState(int state) {
      String msg;
      switch (state) {
         case -42:
            msg = "REMOTE:" + state;
            break;
         case 0:
            msg = "START:" + state;
            break;
         case 1:
            msg = "CONTINUE:" + state;
            break;
         case 2:
            msg = "TRY || FINISH:" + state;
            break;
         case 3:
            msg = "RETRY:" + state;
            break;
         case 4:
            msg = "AFTER_START_IP:" + state;
            break;
         case 5:
            msg = "AFTER_POST_AUTH_IP:" + state;
            break;
         case 6:
            msg = "RETURN_FROM_START_IP:" + state;
            break;
         case 7:
            msg = "RETURN_FROM_POST_AUTH_IP:" + state;
            break;
         case 8:
            msg = "RELEASE_FANOUT:" + state;
            break;
         case 1101:
            msg = "BEExtension.SEND_WAIT_FOR_COMPLETE:" + state;
            break;
         case 1102:
            msg = "SEND_ISSUE_MESSAGE";
            break;
         case 1103:
            msg = "SEND_COMPLETE:" + state;
            break;
         case 1104:
            msg = "SEND_UNKNOWN:" + state;
            break;
         case Integer.MAX_VALUE:
            msg = "COMPLETED:" + state;
            break;
         default:
            msg = "unk:" + state;
      }

      return msg;
   }

   public synchronized Response getResult() throws Throwable {
      while(this.result == this && this.throwableResponse == null) {
         this.sleepTillNotified(true);
      }

      if (this.throwableResponse != null) {
         if (this.throwableResponse instanceof DispatcherException) {
            Throwable wrappedCause = this.throwableResponse.getCause();
            if (wrappedCause != null) {
               this.throwableResponse = wrappedCause;
            }
         }

         if (this.needFillInStackTrace) {
            this.needFillInStackTrace = false;
            this.throwableResponse = StackTraceUtilsClient.getThrowableWithCause(this.throwableResponse);
         }

         throw this.throwableResponse;
      } else {
         return this.result;
      }
   }

   final void sleepTillNotified(boolean needResult) {
      if (needResult) {
         if (this.hasResults()) {
            return;
         }
      } else if (this.getNumChildren() < 1) {
         return;
      }

      ++this.numWaiting;

      try {
         if (this.tranInfo == 1 && this.transaction == null) {
            this.forceSuspendTransaction();
         }

         while(true) {
            this.wait();
            if (needResult) {
               if (this.hasResults()) {
                  return;
               }
            } else if (this.getNumChildren() < 1) {
               return;
            }
         }
      } catch (InterruptedException var27) {
         RuntimeException rte = new RuntimeException(var27);
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug(rte.getMessage(), rte);
         }

         throw rte;
      } finally {
         --this.numWaiting;

         try {
            if (this.transaction != null) {
               this.resumeTransaction();
            }
         } finally {
            if (this.numWaiting > 0) {
               this.notify();
            }

         }

      }
   }

   public final synchronized void setState(int state) {
      this.state = state;
   }

   public final int getState() {
      return this.state;
   }

   public final void resumeRequest(Throwable throwable, boolean needFillInStackTrace) {
      this.notifyResult(throwable, needFillInStackTrace);
   }

   private final void resumeRequest(Response childResult) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("Request():: resumeRequest() " + this + ", has completionListener " + this.completionListener);
      }

      Request resumeExecution;
      boolean switchThreads;
      synchronized(this) {
         resumeExecution = this.childResult(childResult);
         switchThreads = this.parentResumeNewThread;
      }

      this.doExecute(resumeExecution, switchThreads, (InvocableMonitor)null);
   }

   public final void resumeExecution(boolean switchThreads) {
      Request resumeExecution;
      synchronized(this) {
         resumeExecution = this.decrementNumChildren();
         switchThreads |= this.parentResumeNewThread;
      }

      this.doExecute(resumeExecution, switchThreads, (InvocableMonitor)null);
   }

   protected abstract Throwable getAppException(String var1, Throwable var2);

   public final void handleResult(AsyncResult asyncResult) {
      Response result = null;
      Object obj = null;

      Throwable throwable;
      try {
         obj = asyncResult.getObject();
         result = (Response)obj;
         throwable = null;
      } catch (ClassCastException var6) {
         if (obj instanceof Throwable) {
            throwable = (Throwable)obj;
         } else {
            throwable = this.getAppException("Unexpected remote response" + result, var6);
         }
      } catch (Throwable var7) {
         throwable = var7;
      }

      if (!this.getParentResumeNewThread()) {
         this.setParentResumeNewThread(true);
         if (TODOSAGREMOVEreportOnce) {
            TODOSAGREMOVEreportOnce = false;
            (new Exception("messaging.Request TODOSAGREMOVEreportOnce " + this)).printStackTrace();
         }
      }

      if (throwable != null) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("Request.handleResult() : " + this + " " + result, throwable);
         }

         this.notifyResult(throwable, true);
      } else {
         this.resumeRequest(result);
      }

   }

   public final synchronized void needOutsideResult() {
      this.incNumChildren();
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("needOutside numChildren = " + this.getNumChildren() + " on " + this);
      }

   }

   public final synchronized boolean fanoutComplete(boolean voidResponse) {
      if (this.decNumChildren() == 0) {
         if (!this.hasResults() && voidResponse) {
            this.setResult((Response)this.appVoidResponse);
         }

         return false;
      } else {
         return true;
      }
   }

   public final synchronized boolean fanoutCompleteSuspendIfHaveChildren(boolean voidResponse) {
      if (this.decNumChildren() == 0) {
         if (!this.hasResults() && voidResponse) {
            this.setResult((Response)this.appVoidResponse);
         }

         return false;
      } else {
         if (this.transaction == null) {
            this.forceSuspendTransaction();
         }

         return true;
      }
   }

   final void setFutureResponse(FutureResponse futureResponse) {
      this.futureResponse = futureResponse;
   }

   public final void setNext(Request next) {
      this.next = next;
   }

   public final Request getNext() {
      return this.next;
   }

   final Request getParent() {
      return this.parent;
   }

   public boolean isCollocated() {
      return this.isCollocated;
   }

   boolean isSyncRequest() {
      return this.isSyncRequest;
   }

   void setSyncRequest(boolean val) {
      this.isSyncRequest = val;
   }

   void setRunning(boolean val) {
      this.running = val;
   }

   private boolean isRunning() {
      return this.running;
   }

   public void setListener(CompletionListener cl) {
      this.completionListener = cl;
   }

   final void setAsyncResult(AsyncResult asyncResult) {
      this.asyncResult = asyncResult;
   }

   private static synchronized String timeString() {
      return sdf.format(new Date(System.currentTimeMillis()));
   }

   private String requestName() {
      return this.requestThreadTime() + " " + this.getClass().getName() + ", state=" + this.dbgState(this.state) + ", numChildren= " + this.getNumChildren() + ", , hasResults()=" + this.hasResults() + ", " + this;
   }

   private String requestThreadTime() {
      return "@" + timeString() + " Request Thread:" + Thread.currentThread().getName();
   }

   public String toDbgString() {
      return this.requestName();
   }

   protected abstract int getInvocableTypeMask();

   final Response wrappedFiniteStateMachine() throws Exception {
      Throwable throwableResponseCopy = null;
      Response responseCopy = this;
      Request parentCopy = null;
      boolean switchThreads = false;
      CompletionListener localListener = null;
      InvocableMonitor invocableMonitorCopy = null;
      int localState;
      Request parentClone;
      synchronized(this) {
         if (this.isRunning()) {
            return this;
         }

         if (this.state == -42) {
            if (this.throwableResponse == null && (this.getNumChildren() != 0 || !this.hasResults())) {
               return this;
            }

            localState = Integer.MAX_VALUE;
            this.setState(Integer.MAX_VALUE);
         } else if (this.state == Integer.MAX_VALUE || this.throwableResponse != null && (this.futureResponse != null || this.asyncResult != null)) {
            localState = Integer.MAX_VALUE;
            invocableMonitorCopy = this.invocableMonitor;
         }

         if (this.transaction != null) {
            this.resumeTransaction();
         }

         this.setRunning(true);
         parentClone = this.parent;
         int localState = true;
      }

      Request rootParent = this;

      while(rootParent.isCollocated && rootParent.parent != null) {
         if (this == rootParent) {
            rootParent = parentClone;
         } else {
            rootParent = rootParent.parent;
         }
      }

      while(true) {
         parentCopy = null;

         try {
            if (this.invocable == null) {
               int typ = this.methodId & this.getInvocableTypeMask();
               DispatcherPartition4rmic dpc4rmic = this.getDispatcherPartition4rmic();

               InvocableManager im;
               try {
                  InvocableManagerDelegate invocableManagerDelegate = dpc4rmic.getInvocableManagerDelegate();
                  im = invocableManagerDelegate.getInvocableManager();
               } catch (Exception var36) {
                  Exception ex = new IOException(var36.getMessage() + "[methodId=" + this.methodId + ", type=" + typ + ", invocableId=" + this.invocableId + ", Request=" + this + "]", var36);
                  if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                     JMSDebug.JMSDispatcher.debug(ex.getMessage(), ex);
                  }

                  throw ex;
               }

               try {
                  this.invocable = im.invocableFind(typ, this.invocableId);
               } catch (Exception var35) {
                  Exception ex = new Exception(var35.getMessage() + "[methodId=" + this.methodId + ", type=" + typ + ", invocableId=" + this.invocableId + ", Request=" + this + "]", var35);
                  if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                     JMSDebug.JMSDispatcher.debug(ex.getMessage(), ex);
                  }

                  throw ex;
               }

               if (invocableMonitorCopy == null) {
                  invocableMonitorCopy = this.invocable.getInvocableMonitor();
                  if (invocableMonitorCopy != null) {
                     invocableMonitorCopy.increment();
                     synchronized(this) {
                        this.invocableMonitor = invocableMonitorCopy;
                     }
                  }
               }
            } else {
               this.getDispatcherPartition4rmic().getInvocableManagerDelegate();
            }

            localState = this.invocable.invoke(this);
         } catch (Throwable var37) {
            throwableResponseCopy = var37;
            localState = Integer.MAX_VALUE;
         }

         AsyncResult asyncResultCopy;
         FutureResponse futureResponseCopy;
         synchronized(this) {
            if (this.state == -42) {
               if (this.throwableResponse == null && (this.getNumChildren() != 0 || !this.hasResults())) {
                  this.setRunning(false);
                  if (this.transaction == null && (this.tranInfo != 0 || this.hasTransaction())) {
                     this.forceSuspendTransaction();
                  }

                  return this;
               }

               localState = Integer.MAX_VALUE;
               this.setState(Integer.MAX_VALUE);
               this.setRunning(false);
            }

            if (localState != Integer.MAX_VALUE && this.state != Integer.MAX_VALUE && this.throwableResponse == null) {
               if (this.getNumChildren() == 0) {
                  if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
                     JMSDebug.JMSDispatcher.debug(" *** run the fsm of " + this + " again ***");
                  }

                  if (this.transaction != null) {
                     this.resumeTransaction();
                  }
                  continue;
               }

               if (rootParent.isCollocated && rootParent.isSyncRequest) {
                  this.sleepTillNotified(false);
                  if (this.state != Integer.MAX_VALUE && this.throwableResponse == null) {
                     continue;
                  }
               }
            }

            if (localState != Integer.MAX_VALUE && this.state != Integer.MAX_VALUE && this.throwableResponse == null) {
               if (this.transaction == null && (this.tranInfo != 0 || this.hasTransaction())) {
                  this.forceSuspendTransaction();
               }

               this.setRunning(false);
               asyncResultCopy = null;
               futureResponseCopy = null;
            } else {
               localState = Integer.MAX_VALUE;
               this.setState(Integer.MAX_VALUE);
               this.setRunning(false);
               asyncResultCopy = this.asyncResult;
               this.asyncResult = null;
               futureResponseCopy = this.futureResponse;
               this.futureResponse = null;
               localListener = this.completionListener;
               this.completionListener = null;
               if (throwableResponseCopy != null) {
                  if (this.throwableResponse != null) {
                     throwableResponseCopy = this.throwableResponse;
                  } else {
                     this.throwableResponse = throwableResponseCopy;
                     if (this.getNumChildren() > 0) {
                        this.setNumChildren(0);
                     }
                  }
               } else if (this.throwableResponse != null) {
                  throwableResponseCopy = this.throwableResponse;
               } else {
                  responseCopy = this.result;
               }

               if (throwableResponseCopy != null) {
                  if (throwableResponseCopy instanceof DispatcherException) {
                     Throwable wrappedCause = throwableResponseCopy.getCause();
                     if (wrappedCause != null) {
                        throwableResponseCopy = wrappedCause;
                        this.throwableResponse = wrappedCause;
                     }
                  }

                  if (this.needFillInStackTrace) {
                     this.needFillInStackTrace = false;
                     throwableResponseCopy = StackTraceUtilsClient.getThrowableWithCause(throwableResponseCopy);
                  }
               }

               if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
                  JMSDebug.JMSDispatcher.debug("     -- COMPLETED " + this + "--");
               }

               if (this.parent != null) {
                  parentCopy = this.parent;
                  switchThreads = this.parentResumeNewThread;
               }

               if (this.numWaiting > 0) {
                  this.notifyAll();
               }
            }
         }

         if (localState != Integer.MAX_VALUE) {
            return (Response)responseCopy;
         }

         if (parentCopy != null) {
            parentCopy.resumeExecution(switchThreads || parentCopy.parentResumeNewThread);
         }

         try {
            runCompletionListener(localListener, throwableResponseCopy, (Response)responseCopy);
            Response var41;
            if (asyncResultCopy == null && futureResponseCopy == null) {
               if (throwableResponseCopy == null) {
                  Object var42 = responseCopy;
                  return (Response)var42;
               }

               try {
                  var41 = this.getResult();
                  return var41;
               } catch (RuntimeException var30) {
                  throw var30;
               } catch (Error var31) {
                  throw var31;
               } catch (Throwable var32) {
                  throw (Exception)var32;
               }
            }

            var41 = doRMIResponse(this, throwableResponseCopy, (Response)responseCopy, asyncResultCopy, futureResponseCopy);
            return var41;
         } finally {
            this.clearInvocableMonitor();
         }
      }
   }

   void setInvocable(Invocable invocable) {
      this.invocable = invocable;
   }

   void clearInvocableMonitor() {
      InvocableMonitor invocableMonitor;
      synchronized(this) {
         if (this.invocableMonitor == null) {
            return;
         }

         invocableMonitor = this.invocableMonitor;
         this.invocableMonitor = null;
      }

      invocableMonitor.decrement();
   }

   private static Response doRMIResponse(Request request, Throwable throwableResponseCopy, Response responseCopy, AsyncResult asyncResultCopy, FutureResponse futureResponseCopy) {
      try {
         if (throwableResponseCopy != null && futureResponseCopy != null) {
            futureResponseCopy.sendThrowable(new DispatcherException((Throwable)throwableResponseCopy));
         } else if (asyncResultCopy != null) {
            try {
               if (throwableResponseCopy != null) {
                  asyncResultCopy.setResult(new DispatcherException((Throwable)throwableResponseCopy));
               } else {
                  asyncResultCopy.setResult(responseCopy);
               }

               futureResponseCopy.send();
            } catch (Exception var9) {
               Exception e = var9;
               if (throwableResponseCopy == null) {
                  synchronized(request) {
                     throwableResponseCopy = e;
                     request.throwableResponse = e;
                  }
               }

               futureResponseCopy.sendThrowable(new DispatcherException((Throwable)throwableResponseCopy));
            }
         } else if (futureResponseCopy != null) {
            if (throwableResponseCopy != null) {
               futureResponseCopy.sendThrowable(new DispatcherException((Throwable)throwableResponseCopy));
            } else {
               futureResponseCopy.getMsgOutput().writeObject(responseCopy, responseCopy.getClass());
               futureResponseCopy.send();
            }
         }
      } catch (RemoteException var10) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            var10.printStackTrace();
         }
      } catch (IOException var11) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            var11.printStackTrace();
         }
      }

      return responseCopy;
   }

   private static void runCompletionListener(CompletionListener listener, Throwable throwable, Response response) {
      if (listener != null) {
         if (throwable != null) {
            listener.onException(throwable);
         } else {
            listener.onCompletion(response);
         }
      }

   }

   public abstract int remoteSignature();

   public abstract Response createResponse();

   final synchronized void setParentResumeNewThread(boolean val) {
      this.parentResumeNewThread = val;
   }

   public final boolean getParentResumeNewThread() {
      return this.parentResumeNewThread;
   }

   public void dispatchAsync(Dispatcher dispatcher, Request request) throws DispatcherException {
      synchronized(this) {
         request.clearDispatcherPartition4rmic();
         request.parent = this;
         this.incNumChildren();
      }

      if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatcherVerbose.debug("dispatchAsync:" + dispatcher.getId() + " request:" + request.requestName());
      }

      dispatcher.dispatchAsync(request);
   }

   public void dispatchAsyncWithId(Dispatcher dispatcher, Request request, int id) throws DispatcherException {
      synchronized(this) {
         request.clearDispatcherPartition4rmic();
         request.parent = this;
         this.incNumChildren();
      }

      if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatcherVerbose.debug("dispatchAsyncWithId:" + dispatcher.getId() + " request:" + request.requestName());
      }

      dispatcher.dispatchAsyncWithId(request, id);
   }

   public Request() {
      this.result = this;
      this.isCollocated = true;
   }

   void writeShortened(ObjectOutput out) throws IOException {
      this.shortened = true;
      this.writeExternal(out);
      this.shortened = false;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (!this.shortened && this.invocableId != null) {
         mask |= 256;
      }

      out.writeInt(mask);
      if (!this.shortened && this.invocableId != null) {
         this.invocableId.writeExternal(out);
      }

      out.writeInt(this.methodId);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.readExternal(in, new IDImpl());
   }

   protected final void readExternal(ObjectInput in, ID id) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw MessagingUtilities.versionIOException(version, 1, 1);
      } else {
         if ((mask & 256) != 0) {
            this.invocableId = id;
            this.invocableId.readExternal(in);
         }

         if ((mask & 512) != 0) {
            in.readLong();
         }

         this.methodId = in.readInt();
         this.isCollocated = false;
      }
   }

   private boolean hasTransaction() {
      if (this.tranInfo == 2) {
         try {
            if ((this.remoteSignature() & 1) != 0 && getTranManager().getTransaction() != null) {
               this.tranInfo = 1;
            } else {
               this.tranInfo = 0;
            }
         } catch (SystemException var2) {
            this.tranInfo = 0;
         }
      }

      return this.tranInfo == 1;
   }

   public final void setTranInfo(int tranInfo) {
      this.tranInfo = tranInfo;
   }

   private synchronized boolean forceSuspendTransaction() {
      if (this.transaction != null) {
         throw new Error("transaction suspended twice");
      } else if ((this.transaction = getTranManager().forceSuspend()) == null) {
         this.tranInfo = 0;
         return false;
      } else {
         this.tranInfo = 1;
         return true;
      }
   }

   private static ClientTransactionManager getTranManager() {
      return TransactionHelper.getTransactionHelper().getTransactionManager();
   }

   private synchronized void resumeTransaction() {
      if (this.transaction == null) {
         throw new Error("transaction resumed twice");
      } else {
         getTranManager().forceResume(this.transaction);
         this.transaction = null;
      }
   }

   static {
      sdf.setTimeZone(TimeZone.getDefault());
   }
}
