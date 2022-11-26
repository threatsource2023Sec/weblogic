package weblogic.security.SSL.jsseadapter;

import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

final class JaSSLEngineSynchronizer {
   private final ThreadLocal lockStateStack = new ThreadLocal() {
      protected Stack initialValue() {
         return new Stack();
      }
   };
   private final Lock inboundLock;
   private final Lock outboundLock;

   LockState getLockState() {
      Stack lockStack = (Stack)this.lockStateStack.get();
      LockState currentLockState;
      if (lockStack.empty()) {
         currentLockState = JaSSLEngineSynchronizer.LockState.NONE;
      } else {
         currentLockState = (LockState)lockStack.peek();
      }

      return currentLockState;
   }

   int getLockStateStackSize() {
      Stack lockStack = (Stack)this.lockStateStack.get();
      return lockStack.size();
   }

   void lock(LockState nextState) {
      if (null == nextState) {
         throw new IllegalArgumentException("Non-null nextState expected.");
      } else if (JaSSLEngineSynchronizer.LockState.NONE == nextState) {
         throw new IllegalArgumentException("NONE state may not be set; use unlock instead.");
      } else {
         LockState currentLockState = this.getLockState();
         if (currentLockState != nextState) {
            if (JaSSLEngineSynchronizer.LockState.NONE == currentLockState && JaSSLEngineSynchronizer.LockState.INBOUND == nextState) {
               this.inboundLock.lock();
            } else if (JaSSLEngineSynchronizer.LockState.NONE == currentLockState && JaSSLEngineSynchronizer.LockState.OUTBOUND == nextState) {
               this.outboundLock.lock();
            } else if (JaSSLEngineSynchronizer.LockState.INBOUND == currentLockState && JaSSLEngineSynchronizer.LockState.HANDSHAKE == nextState) {
               this.inboundLock.unlock();
               this.outboundLock.lock();
               this.inboundLock.lock();
            } else {
               if (JaSSLEngineSynchronizer.LockState.OUTBOUND != currentLockState || JaSSLEngineSynchronizer.LockState.HANDSHAKE != nextState) {
                  if (JaLogger.isLoggable(Level.FINE)) {
                     JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, "[{0}] Illegal state for lock: currentLockState={1}, nextState={2}.", this.getClass().getName(), currentLockState, nextState);
                  }

                  throw new IllegalStateException("currentLockState=" + currentLockState + ", nextState=" + nextState);
               }

               this.inboundLock.lock();
            }
         }

         ((Stack)this.lockStateStack.get()).push(nextState);
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "[{0}] lock completed, pushed lock state \"{1}\", previous lock state \"{2}\", post-push stack size={3}", this.getClass().getName(), nextState, currentLockState, ((Stack)this.lockStateStack.get()).size());
         }

      }
   }

   void unlock() {
      Stack lockStack = (Stack)this.lockStateStack.get();
      if (lockStack.empty()) {
         if (JaLogger.isLoggable(Level.FINE)) {
            JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, "[{0}] unlock called with empty stack. Not matched with lock?", this.getClass().getName());
         }

      } else {
         LockState poppedState = (LockState)lockStack.pop();
         LockState peekState = this.getLockState();
         if (poppedState == peekState) {
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "[{0}] unlock called with no state change, state={1}", this.getClass().getName(), poppedState);
            }

         } else {
            if (JaSSLEngineSynchronizer.LockState.HANDSHAKE == poppedState && JaSSLEngineSynchronizer.LockState.INBOUND == peekState) {
               this.outboundLock.unlock();
            } else if (JaSSLEngineSynchronizer.LockState.HANDSHAKE == poppedState && JaSSLEngineSynchronizer.LockState.OUTBOUND == peekState) {
               this.inboundLock.unlock();
            } else if (JaSSLEngineSynchronizer.LockState.INBOUND == poppedState && JaSSLEngineSynchronizer.LockState.NONE == peekState) {
               this.inboundLock.unlock();
            } else {
               if (JaSSLEngineSynchronizer.LockState.OUTBOUND != poppedState || JaSSLEngineSynchronizer.LockState.NONE != peekState) {
                  if (JaLogger.isLoggable(Level.FINE)) {
                     JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, "[{0}] Illegal state for unlock: poppedState={1}, lockStack.peek()={2}.", this.getClass().getName(), poppedState, peekState);
                  }

                  throw new IllegalStateException("poppedState=" + poppedState + ", lockStack.peek=" + peekState);
               }

               this.outboundLock.unlock();
            }

            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "[{0}] unlock completed, current lock state \"{1}\", popped lock state \"{2}\", post-pop stack size={3}", this.getClass().getName(), peekState, poppedState, ((Stack)this.lockStateStack.get()).size());
            }

         }
      }
   }

   JaSSLEngineSynchronizer() {
      this.inboundLock = new ReentrantLock();
      this.outboundLock = new ReentrantLock();
   }

   JaSSLEngineSynchronizer(Lock inbound, Lock outbound) {
      this.inboundLock = inbound;
      this.outboundLock = outbound;
   }

   static enum LockState {
      NONE,
      INBOUND,
      OUTBOUND,
      HANDSHAKE;
   }
}
