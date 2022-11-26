package weblogic.jms.client;

import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.JMSDebug;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.common.JMSCICHelper;

class JMSExceptionContext extends JMSContext {
   private ExceptionListener listener;
   private static final Map inFlightListeners = new HashMap();

   JMSExceptionContext(ExceptionListener el) {
      this(el, false);
   }

   JMSExceptionContext(ExceptionListener el, boolean isInbound) {
      super(isInbound);
      this.listener = null;
      if (el != null) {
         this.setClassLoader(el.getClass().getClassLoader());
      }

      this.listener = el;
   }

   JMSExceptionContext(ExceptionListener el, ClassLoader cl) {
      this.listener = null;
      this.setClassLoader(cl);
      this.listener = el;
   }

   ExceptionListener getExceptionListener() {
      return this.listener;
   }

   void blockTillIdleThenRunExclusively(JMSException stableException) throws Exception {
      this.invokeListener(stableException, true);
   }

   boolean invokeListenerIfItIsIdle(JMSException stableException) throws Exception {
      return this.invokeListener(stableException, false);
   }

   private boolean invokeListener(JMSException stableException, boolean blockingOK) throws Exception {
      ListenerState listenerState = this.getUniqueListenerState(this.listener);

      boolean var4;
      try {
         if (listenerState.localListener != null) {
            while(true) {
               synchronized(listenerState) {
                  if (!listenerState.running) {
                     listenerState.running = true;
                     break;
                  }

                  if (!blockingOK) {
                     boolean var5 = true;
                     return var5;
                  }

                  try {
                     listenerState.wait(1000L);
                  } catch (InterruptedException var11) {
                     JMSClientExceptionLogger.logStackTrace(var11);
                  }
               }
            }

            this.callExceptionListener(listenerState, stableException);
            var4 = false;
            return var4;
         }

         var4 = false;
      } finally {
         this.releaseUniqueListenerState(listenerState);
      }

      return var4;
   }

   private ListenerState getUniqueListenerState(ExceptionListener localListener) {
      if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
         JMSDebug.JMSMessagePath.debug("JMSExceptionContext.getUniqueListenerState begin");
      }

      ListenerState listenerState;
      boolean create;
      int debugCount;
      String key;
      synchronized(inFlightListeners) {
         int hashCode = System.identityHashCode(localListener);
         String manyInstances = hashCode + localListener.getClass().getName();
         key = manyInstances.intern();
         listenerState = (ListenerState)inFlightListeners.get(key);
         create = listenerState == null;
         if (create) {
            listenerState = new ListenerState(localListener, key);
            inFlightListeners.put(key, listenerState);
         }

         synchronized(listenerState) {
            debugCount = ++listenerState.simultaneousThreadCount;
         }
      }

      if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
         JMSDebug.JMSMessagePath.debug("JMSExceptionContext.getUniqueListenerState completed, it " + (create ? "created" : "reused") + " an entry with count " + debugCount + " named " + key);
      }

      return listenerState;
   }

   private void releaseUniqueListenerState(ListenerState listenerState) {
      if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
         JMSDebug.JMSMessagePath.debug("JMSExceptionContext.removeReference begin");
      }

      int debugCount;
      synchronized(inFlightListeners) {
         synchronized(listenerState) {
            listenerState.running = false;
            debugCount = --listenerState.simultaneousThreadCount;
            if (listenerState.simultaneousThreadCount == 0) {
               inFlightListeners.remove(listenerState.key);
            } else {
               listenerState.notifyAll();
            }
         }
      }

      if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
         JMSDebug.JMSMessagePath.debug("JMSExceptionContext.removeReference end with count=" + debugCount);
      }

   }

   private void callExceptionListener(final ListenerState listenerState, final JMSException exc) {
      try {
         this.subject.doAs(this.getKernelId(), new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               ClassLoader savedCl = null;
               if (KernelStatus.isServer()) {
                  savedCl = Thread.currentThread().getContextClassLoader();
                  Thread.currentThread().setContextClassLoader(JMSExceptionContext.this.getClassLoader());
               }

               try {
                  ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(JMSExceptionContext.this.getComponentInvocationContext());
                  Throwable var3 = null;

                  try {
                     listenerState.localListener.onException(exc);
                  } catch (Throwable var19) {
                     var3 = var19;
                     throw var19;
                  } finally {
                     if (mic != null) {
                        if (var3 != null) {
                           try {
                              mic.close();
                           } catch (Throwable var18) {
                              var3.addSuppressed(var18);
                           }
                        } else {
                           mic.close();
                        }
                     }

                  }
               } finally {
                  if (KernelStatus.isServer()) {
                     Thread.currentThread().setContextClassLoader(savedCl);
                  }

               }

               return null;
            }
         });
      } catch (Throwable var4) {
         JMSClientExceptionLogger.logStackTrace(var4);
      }

   }

   private static class ListenerState {
      final String key;
      private final ExceptionListener localListener;
      int simultaneousThreadCount;
      boolean running;

      ListenerState(ExceptionListener listener, String key) {
         this.key = key;
         this.localListener = listener;
      }
   }
}
