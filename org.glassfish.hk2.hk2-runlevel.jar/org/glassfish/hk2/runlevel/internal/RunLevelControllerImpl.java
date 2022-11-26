package org.glassfish.hk2.runlevel.internal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.runlevel.CurrentlyRunningException;
import org.glassfish.hk2.runlevel.RunLevelController;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;

@Service
@ContractsProvided({RunLevelController.class})
@Visibility(DescriptorVisibility.LOCAL)
public class RunLevelControllerImpl implements RunLevelController {
   @Inject
   private AsyncRunLevelContext context;

   public void proceedTo(int runLevel) {
      RunLevelFuture future = this.context.proceedTo(runLevel);

      try {
         future.get();
      } catch (InterruptedException var5) {
         throw new MultiException(var5);
      } catch (ExecutionException var6) {
         Throwable cause = var6.getCause();
         if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
         } else {
            throw new MultiException(cause);
         }
      }
   }

   public RunLevelFuture proceedToAsync(int runLevel) throws CurrentlyRunningException, IllegalStateException {
      if (this.context.getPolicy().equals(RunLevelController.ThreadingPolicy.USE_NO_THREADS)) {
         throw new IllegalStateException("Cannot use proceedToAsync if the threading policy is USE_NO_THREADS");
      } else {
         return this.context.proceedTo(runLevel);
      }
   }

   public RunLevelFuture getCurrentProceeding() {
      return this.context.getCurrentFuture();
   }

   public void cancel() {
      RunLevelFuture rlf = this.getCurrentProceeding();
      if (rlf != null) {
         rlf.cancel(false);
      }
   }

   public int getCurrentRunLevel() {
      return this.context.getCurrentLevel();
   }

   public void setMaximumUseableThreads(int maximumThreads) {
      if (maximumThreads < 1) {
         throw new IllegalArgumentException("maximumThreads must be at least 1, but it is " + maximumThreads);
      } else {
         this.context.setMaximumThreads(maximumThreads);
      }
   }

   public int getMaximumUseableThreads() {
      return this.context.getMaximumThreads();
   }

   public void setThreadingPolicy(RunLevelController.ThreadingPolicy policy) {
      if (policy == null) {
         throw new IllegalArgumentException();
      } else {
         this.context.setPolicy(policy);
      }
   }

   public RunLevelController.ThreadingPolicy getThreadingPolicy() {
      return this.context.getPolicy();
   }

   public void setExecutor(Executor executor) {
      this.context.setExecutor(executor);
   }

   public Executor getExecutor() {
      return this.context.getExecutor();
   }

   public long getCancelTimeoutMilliseconds() {
      return this.context.getCancelTimeout();
   }

   public void setCancelTimeoutMilliseconds(long cancelTimeout) {
      if (cancelTimeout < 1L) {
         throw new IllegalArgumentException();
      } else {
         this.context.setCancelTimeout(cancelTimeout);
      }
   }

   public Integer getValidationOverride() {
      return this.context.getModeOverride();
   }

   public void setValidationOverride(Integer validationMode) {
      if (validationMode != null && validationMode != 0 && validationMode != 1) {
         throw new IllegalArgumentException("validationMode must either be validating or non validating: " + validationMode);
      } else {
         this.context.setModeOverride(validationMode);
      }
   }
}
