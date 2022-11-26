package weblogic.jms.client;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NoSuchObjectException;
import java.rmi.UnknownHostException;
import java.rmi.UnmarshalException;
import javax.jms.JMSException;
import javax.naming.NameNotFoundException;
import weblogic.jms.common.DestroyConnectionException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.LostServerException;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.utils.NestedThrowable;

abstract class ReconnectController {
   private static final int STATE_NO_RETRY = -2304;
   private static final int STATE_USER_CLOSED = -1280;
   private static final int STATE_CONNECTED = 0;
   private static final int STATE_HAVE_RECON_INFO_PHYSICAL_CLOSE_DONE = 512;
   private static final int STATE_HAVE_RECON_INFO_PEERGONEE_IN_PROGRESS = 1028;
   private static final int STATE_HAVE_RECON_INFO_CLOSE_IN_PROGRESS = 1040;
   private static final int STATE_RECON_SCHEDULED = 1280;
   private static final int STATE_RECON_IN_PROGRESS = 1536;
   private int state;
   private Throwable lastProblem;
   ReconnectController firstChild;
   ReconnectController lastChild;
   ReconnectController prevChild;
   ReconnectController nextChild;
   ReconnectController parent;
   private volatile Reconnectable physical;
   private static int RETRIES = 12;
   private static long LARGE_TIME_FOR_EXCEPTION_MSG = 14400000L;
   static boolean TODOREMOVEDebug = false;

   protected ReconnectController(ReconnectController parent, Reconnectable reconnectable) {
      this.physical = reconnectable;

      assert this.getState() == 0;

      if (parent != null) {
         int parentState = parent.getState();
         if (parentState != -1280) {
            this.parent = parent;
            this.setState(parentState);
            parent.addChild(this);
         }
      }

   }

   abstract Object getConnectionStateLock();

   protected abstract ReconnectController getParent();

   protected abstract JMSConnection getPhysicalJMSConnection();

   protected abstract WLConnectionImpl getWLConnectionImpl();

   int getState() {
      return this.state;
   }

   private void setState(int arg) {
      this.state = arg;
   }

   Throwable getLastProblem() {
      return this.lastProblem;
   }

   protected void setLastProblem(Throwable arg) {
      this.lastProblem = arg;
      if (TODOREMOVEDebug) {
         if (arg == null) {
            System.err.println("Debug ReconnectController lastProblem cleared");
         } else {
            JMSConnection.displayExceptionCauses("Debug ReconnectController lastProblem set", arg);
         }
      }

   }

   protected Reconnectable getPhysical() {
      return this.physical;
   }

   boolean isClosed() {
      synchronized(this.getConnectionStateLock()) {
         return this.getState() == -1280;
      }
   }

   protected void addChild(ReconnectController child) {
      assert child != this;

      synchronized(this.getConnectionStateLock()) {
         if (this.getState() != -1280) {
            if (this.lastChild == null) {
               this.firstChild = this.lastChild = child;
            } else {
               child.prevChild = this.lastChild;
               this.lastChild.nextChild = child;
               this.lastChild = child;
            }

         }
      }
   }

   protected void removeChild(ReconnectController child) {
      if (child != this) {
         if (child.prevChild == null) {
            this.firstChild = child.nextChild;
         } else {
            child.prevChild.nextChild = child.nextChild;
         }

         if (child.nextChild == null) {
            this.lastChild = child.prevChild;
         } else {
            child.nextChild.prevChild = child.prevChild;
         }

         child.nextChild = child.prevChild = null;
      }
   }

   protected void setRecursiveStateUserClosed() {
      this.setState(-1280);

      while(true) {
         ReconnectController child = this.firstChild;
         if (child == null) {
            return;
         }

         this.removeChild(child);
         child.setRecursiveStateUserClosed();
      }
   }

   protected void setRecursiveState(int newState) {
      assert newState != -1280;

      if (TODOREMOVEDebug) {
         if (newState == -2304) {
            (new Exception("DEBUG ReconnectController STATE_NO_RETRY from " + this.getStringState(this.getState()))).printStackTrace();
         } else {
            System.err.println("DEBUG ReconnectController stateBecomes " + this.getStringState(newState) + " " + this.getPhysical());
         }
      }

      synchronized(this.getConnectionStateLock()) {
         this.setStateChildrenInternal(newState);
      }
   }

   private void setStateChildrenInternal(int newState) {
      if (this.getState() != -1280 && this.getState() != -2304) {
         this.setState(newState);

         for(ReconnectController child = this.firstChild; child != null; child = child.nextChild) {
            child.setStateChildrenInternal(newState);
         }

      }
   }

   protected void setRecursiveStateNotify(int newState) {
      Object connectionStateLock = this.getConnectionStateLock();
      synchronized(connectionStateLock) {
         this.setRecursiveState(newState);
         connectionStateLock.notifyAll();
      }
   }

   protected Reconnectable getPhysicalWaitForState(long startTime) {
      Object connectionStateLock = this.getConnectionStateLock();
      synchronized(connectionStateLock) {
         return this.waitForStateInternal(startTime);
      }
   }

   protected Reconnectable getPhysicalWaitForState() {
      return this.getPhysicalWaitForState(System.currentTimeMillis());
   }

   boolean stateNeedsWait() {
      return 1024 <= this.getState();
   }

   protected Reconnectable waitForStateInternal(long startTime) {
      while(true) {
         if (this.stateNeedsWait()) {
            long config = this.getWLConnectionImpl().getReconnectBlockingInternal();
            if (config != 0L && (config != -1L || this.getWLConnectionImpl().getLastReconnectTimer() != 0L)) {
               long waitArg = this.remainingMillis(startTime);
               if (waitArg >= 1L) {
                  if (TODOREMOVEDebug) {
                     System.err.println("ReconnectController Debug waiting to change " + this.getStringState(this.getState()) + " " + waitArg + " " + this.getPhysical());
                  } else if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     JMSDebug.JMSCommon.debug("ReconnectController waiting to change " + this.getStringState(this.getState()) + " " + waitArg);
                  }

                  try {
                     this.getConnectionStateLock().wait(waitArg);
                     continue;
                  } catch (InterruptedException var8) {
                     throw new RuntimeException(var8);
                  }
               }
            }
         }

         return this.getPhysical();
      }
   }

   private long remainingMillis(long startTime) {
      long config = this.getWLConnectionImpl().getReconnectBlockingInternal();
      if (config == -1L) {
         return LARGE_TIME_FOR_EXCEPTION_MSG;
      } else {
         long elapsed = System.currentTimeMillis() - startTime;
         return config - elapsed;
      }
   }

   Reconnectable waitForStateOrTime(long endWaitTime) {
      Object connectionStateLock = this.getConnectionStateLock();
      synchronized(connectionStateLock) {
         long remainingMillis = endWaitTime - System.currentTimeMillis();

         while(remainingMillis > 0L && this.stateNeedsWait()) {
            try {
               connectionStateLock.wait(remainingMillis);
            } catch (InterruptedException var9) {
               throw new RuntimeException(var9);
            }
         }

         return this.getPhysical();
      }
   }

   protected AssertionError wrongState(int state) {
      AssertionError assertionError = new AssertionError(this.wrongStateString(state));
      if (TODOREMOVEDebug) {
         assertionError.printStackTrace();
      }

      return assertionError;
   }

   String wrongStateString(int state) {
      return "ReconnectController unexpected state " + state + " is " + this.getStringState(state);
   }

   protected String getStringState(int state) {
      String stateString;
      switch (state) {
         case -2304:
            stateString = "STATE_NO_RETRY";
            break;
         case -1280:
            stateString = "STATE_USER_CLOSED";
            break;
         case 0:
            stateString = "STATE_CONNECTED";
            break;
         case 512:
            stateString = "STATE_HAVE_RECON_INFO_PHYSICAL_CLOSE_DONE";
            break;
         case 1028:
            stateString = "STATE_HAVE_RECON_INFO_PEERGONEE_IN_PROGRESS";
            break;
         case 1040:
            stateString = "STATE_HAVE_RECON_INFO_CLOSE_IN_PROGRESS";
            break;
         case 1280:
            stateString = "STATE_RECON_SCHEDULED";
            break;
         case 1536:
            stateString = "STATE_RECON_IN_PROGRESS";
            break;
         default:
            stateString = "Illegal State " + state;
      }

      return stateString;
   }

   void setPhysicalReconnectable(Reconnectable newReconnectable) {
      synchronized(this.getConnectionStateLock()) {
         int state = this.getState();
         switch (state) {
            case 0:
            case 512:
            case 1536:
               this.physical = newReconnectable;
            case -2304:
            case -1280:
            case 1028:
            case 1040:
            case 1280:
               return;
            default:
               throw this.wrongState(state);
         }
      }
   }

   public final void close() throws JMSException {
      this.getPhysical().isCloseAllowed("close");
      boolean var25 = false;

      try {
         var25 = true;
         if (this.getState() == 0) {
            if (this.getPhysicalJMSConnection().isConnected()) {
               this.getPhysical().close();
               var25 = false;
            } else {
               var25 = false;
            }
         } else {
            var25 = false;
         }
      } finally {
         if (var25) {
            Object connectionStateLock = this.getConnectionStateLock();
            synchronized(connectionStateLock) {
               try {
                  this.getPhysical().forgetReconnectState();
               } finally {
                  if (this.getState() != -1280) {
                     this.setRecursiveStateUserClosed();
                     if (this.parent != null) {
                        this.parent.removeChild(this);
                     }

                     connectionStateLock.notifyAll();
                  }

               }

            }
         }
      }

      Object connectionStateLock = this.getConnectionStateLock();
      synchronized(connectionStateLock) {
         try {
            this.getPhysical().forgetReconnectState();
         } finally {
            if (this.getState() != -1280) {
               this.setRecursiveStateUserClosed();
               if (this.parent != null) {
                  this.parent.removeChild(this);
               }

               connectionStateLock.notifyAll();
            }

         }

      }
   }

   private Reconnectable analyzeExceptionAndReconnect(long startTime, Reconnectable lastPhysical, boolean idempotent, weblogic.jms.common.JMSException jmse) throws JMSException {
      int state = -256;
      int lcv = RETRIES;

      while(true) {
         label82: {
            JMSConnection physicalJMSConnection;
            try {
               synchronized(this.getConnectionStateLock()) {
                  state = this.getState();
                  if (lcv == 0) {
                     return this.changedPhysicalOrThrow(lastPhysical, jmse);
                  }

                  if (lcv == RETRIES) {
                     this.throwNonRecoverableException(jmse, idempotent);
                     this.getWLConnectionImpl().updateLastReconnectTimer();
                  }

                  switch (state) {
                     case -2304:
                     case -1280:
                     case 0:
                        return this.changedPhysicalOrThrow(lastPhysical, jmse);
                     case 512:
                        if (this.getWLConnectionImpl().getReconnectPolicyInternal() == 0) {
                           this.getWLConnectionImpl().setRecursiveStateNotify(-2304);
                           return this.changedPhysicalOrThrow(lastPhysical, jmse);
                        }

                        physicalJMSConnection = this.getPhysicalJMSConnection();
                        state = this.setupReconnectSchedule(physicalJMSConnection, 1280);
                        break;
                     case 1028:
                     case 1040:
                     case 1280:
                     case 1536:
                        this.waitForStateInternal(startTime);
                        if (this.stateNeedsWait()) {
                           return this.changedPhysicalOrThrow(lastPhysical, jmse);
                        }
                        break label82;
                     default:
                        throw this.wrongState(state);
                  }
               }
            } catch (LostServerException var12) {
               if (jmse != null && var12.getCause() == null) {
                  throw this.attachReasonToException(var12, startTime, state);
               }

               throw var12;
            }

            assert state == 1280;

            JMSConnection.getReconnectWorkManager().schedule(physicalJMSConnection);
         }

         --lcv;
      }
   }

   protected int setupReconnectSchedule(JMSConnection physicalJMSConnection, int state) {
      physicalJMSConnection.setReplacementConnection((JMSConnection)null);
      this.getWLConnectionImpl().setRecursiveStateNotify(state);
      return state;
   }

   private Reconnectable changedPhysicalOrThrow(Reconnectable oldPhysical, weblogic.jms.common.JMSException jmse) throws weblogic.jms.common.JMSException {
      Reconnectable current = this.getPhysical();
      if (oldPhysical != null && oldPhysical.getFEPeerInfo() == current.getFEPeerInfo()) {
         throw jmse;
      } else {
         return current;
      }
   }

   private LostServerException attachReasonToException(LostServerException lse, long startTime, int state) {
      String reason;
      if (state == 0) {
         reason = null;
      } else {
         long remaining;
         if (this.getWLConnectionImpl().getReconnectBlockingInternal() > -1L && (remaining = this.remainingMillis(startTime)) <= 0L) {
            reason = "timed out in state " + this.getStringState(state) + " after " + (this.getWLConnectionImpl().getReconnectBlockingInternal() - remaining) + " milliseconds";
         } else {
            reason = "server connection in state " + this.getStringState(state);
         }
      }

      Throwable cause = this.getWLConnectionImpl().getLastProblem();
      LostServerException wrapLast;
      if (cause == null) {
         if (reason == null) {
            if (TODOREMOVEDebug) {
               System.err.println("DEBUG ReconnectController in " + this.getStringState(this.getState()));
            }

            return lse;
         }

         wrapLast = new LostServerException(reason);
      } else {
         wrapLast = new LostServerException(reason, cause);
      }

      wrapLast.setReplayLastException(true);
      lse.initCause(wrapLast);
      return lse;
   }

   protected JMSConnection computeJMSConnection(long startTime, Reconnectable reconnectable, weblogic.jms.common.JMSException jmse) throws JMSException {
      return (JMSConnection)this.analyzeExceptionAndReconnect(startTime, reconnectable, true, jmse);
   }

   protected JMSSession computeJMSSession(long startTime, Reconnectable reconnectable, weblogic.jms.common.JMSException jmse) throws JMSException {
      return (JMSSession)this.analyzeExceptionAndReconnect(startTime, reconnectable, true, jmse);
   }

   protected JMSProducer nonIdempotentJMSProducer(long startTime, Reconnectable reconnectable, weblogic.jms.common.JMSException jmse) throws JMSException {
      return (JMSProducer)this.analyzeExceptionAndReconnect(startTime, reconnectable, false, jmse);
   }

   protected JMSProducer idempotentJMSProducer(long startTime, Reconnectable reconnectable, weblogic.jms.common.JMSException jmse) throws JMSException {
      return (JMSProducer)this.analyzeExceptionAndReconnect(startTime, reconnectable, true, jmse);
   }

   protected JMSConsumer computeJMSConsumer(long startTime, Reconnectable reconnectable, weblogic.jms.common.JMSException jmse) throws JMSException {
      return (JMSConsumer)this.analyzeExceptionAndReconnect(startTime, reconnectable, true, jmse);
   }

   protected Reconnectable checkClosedReconnect(long startTime, Reconnectable oldPhysical) throws JMSException {
      try {
         oldPhysical.publicCheckClosed();
         return oldPhysical;
      } catch (weblogic.jms.common.JMSException var6) {
         Reconnectable newPhysical = this.analyzeExceptionAndReconnect(startTime, oldPhysical, true, var6);
         if (newPhysical != oldPhysical) {
            return newPhysical;
         } else {
            throw var6;
         }
      }
   }

   private void throwNonRecoverableException(weblogic.jms.common.JMSException jmse, boolean idempotent) throws JMSException {
      if (jmse != null) {
         int breakLoop = true;
         int lcv = 40;
         boolean hasReplayedLostServerException = false;

         Object nonJmse;
         for(nonJmse = jmse; nonJmse instanceof weblogic.jms.common.JMSException && lcv != 0; --lcv) {
            if (nonJmse instanceof DestroyConnectionException) {
               throw jmse;
            }

            if (!hasReplayedLostServerException && nonJmse instanceof LostServerException) {
               hasReplayedLostServerException = ((LostServerException)nonJmse).isReplayLastException();
            }

            nonJmse = ((Throwable)nonJmse).getCause();
         }

         lcv = 40;
         Throwable cause = nonJmse;

         while(true) {
            if (cause != null && lcv != 0) {
               if (!(cause instanceof ConnectException) && !(cause instanceof UnknownHostException) && !(cause instanceof ConnectIOException) && !(cause instanceof NoSuchObjectException)) {
                  if (cause instanceof DispatcherException && ((Throwable)cause).getCause() instanceof NameNotFoundException) {
                     if (TODOREMOVEDebug) {
                        System.err.println("DEBUG ReconnectController new recover NameNotFound " + this.getStringState(this.getState()));
                        JMSConnection.displayExceptionCauses("DEBUG ReconnectController nameNotFound", jmse);
                     }

                     return;
                  }

                  cause = ((Throwable)cause).getCause();
                  --lcv;
                  continue;
               }

               return;
            }

            if (jmse instanceof LostServerException && (nonJmse == null || hasReplayedLostServerException)) {
               return;
            }

            if (!idempotent || !(jmse instanceof LostServerException) || !(nonJmse instanceof Exception) || !(getCauseOrNested((Throwable)nonJmse) instanceof UnmarshalException) || !(getCauseOrNested(((Throwable)nonJmse).getCause()) instanceof IOException) && !(getCauseOrNested(((Throwable)nonJmse).getCause()) instanceof SocketException)) {
               if (TODOREMOVEDebug) {
                  System.err.println("DEBUG ReconnectController did not recover " + this.getStringState(this.getState()) + " preInvokeFailure " + (40 - lcv));
                  JMSConnection.displayExceptionCauses("DEBUG ReconnectController nonRecover", jmse);
               }

               throw jmse;
            }

            return;
         }
      }
   }

   private static Throwable getCauseOrNested(Throwable throwable) {
      if (throwable.getCause() != null) {
         return throwable.getCause();
      } else {
         if (throwable instanceof NestedThrowable) {
            Throwable nest = ((NestedThrowable)throwable).getNested();
            if (nest != throwable) {
               return nest;
            }
         }

         return null;
      }
   }
}
