package org.python.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public interface Service {
   @CanIgnoreReturnValue
   Service startAsync();

   boolean isRunning();

   State state();

   @CanIgnoreReturnValue
   Service stopAsync();

   void awaitRunning();

   void awaitRunning(long var1, TimeUnit var3) throws TimeoutException;

   void awaitTerminated();

   void awaitTerminated(long var1, TimeUnit var3) throws TimeoutException;

   Throwable failureCause();

   void addListener(Listener var1, Executor var2);

   @Beta
   public abstract static class Listener {
      public void starting() {
      }

      public void running() {
      }

      public void stopping(State from) {
      }

      public void terminated(State from) {
      }

      public void failed(State from, Throwable failure) {
      }
   }

   @Beta
   public static enum State {
      NEW {
         boolean isTerminal() {
            return false;
         }
      },
      STARTING {
         boolean isTerminal() {
            return false;
         }
      },
      RUNNING {
         boolean isTerminal() {
            return false;
         }
      },
      STOPPING {
         boolean isTerminal() {
            return false;
         }
      },
      TERMINATED {
         boolean isTerminal() {
            return true;
         }
      },
      FAILED {
         boolean isTerminal() {
            return true;
         }
      };

      private State() {
      }

      abstract boolean isTerminal();

      // $FF: synthetic method
      State(Object x2) {
         this();
      }
   }
}
