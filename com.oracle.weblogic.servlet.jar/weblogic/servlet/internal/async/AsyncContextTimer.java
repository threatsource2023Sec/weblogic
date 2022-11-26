package weblogic.servlet.internal.async;

import java.util.Iterator;
import java.util.Set;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.collections.ConcurrentHashSet;
import weblogic.work.WorkManagerFactory;

public class AsyncContextTimer implements NakedTimerListener {
   private final WebAppServletContext context;
   private final Set pendingAsyncContexts;
   private final int scanInterval;
   private final TimerManager timerManager;
   private Timer timer;

   public AsyncContextTimer(WebAppServletContext servletContext) {
      this.context = servletContext;
      this.pendingAsyncContexts = new ConcurrentHashSet();
      this.scanInterval = this.context.getConfigManager().getAsyncTimeoutCheckIntervalSecs();
      String tmName = "AsyncContextTimer-ContextPath='" + this.context.getContextPath() + (this.context.getVersionId() != null ? "'-Version#" + this.context.getVersionId() : "");
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager(tmName, WorkManagerFactory.getInstance().getSystem());
      this.timer = this.timerManager.schedule(this, 0L, (long)(this.scanInterval * 1000));
   }

   public void destroy() {
      this.timer.cancel();
      this.timerManager.stop();
      this.pendingAsyncContexts.clear();
   }

   void put(AsyncContextImpl async) {
      this.pendingAsyncContexts.add(async);
   }

   void remove(AsyncContextImpl async) {
      this.pendingAsyncContexts.remove(async);
   }

   public void timerExpired(Timer timer) {
      if (this.context.isStarted() && !this.pendingAsyncContexts.isEmpty()) {
         ComponentInvocationContextManager cicManager = WebServerRegistry.getInstance().getContainerSupportProvider().getComponentInvocationContextManager();
         ComponentInvocationContext cic = this.context.getComponentInvocationContext();
         ManagedInvocationContext mic = cicManager.setCurrentComponentInvocationContext(cic);
         Throwable var5 = null;

         try {
            Iterator asyncCtxs = this.pendingAsyncContexts.iterator();
            long currTime = System.currentTimeMillis();

            while(asyncCtxs.hasNext()) {
               AsyncContextImpl async = (AsyncContextImpl)asyncCtxs.next();
               if (async != null && async.isTimeout(currTime)) {
                  try {
                     async.handleTimeout();
                  } finally {
                     this.remove(async);
                  }
               }
            }
         } catch (Throwable var24) {
            var5 = var24;
            throw var24;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var22) {
                     var5.addSuppressed(var22);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }
   }
}
