package net.shibboleth.utilities.java.support.httpclient;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.DestroyedComponentException;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.TimerSupport;
import org.apache.http.conn.HttpClientConnectionManager;

public class IdleConnectionSweeper implements DestructableComponent {
   private boolean destroyed;
   private boolean createdTimer;
   private final HttpClientConnectionManager connectionManager;
   private final Timer taskTimer;
   private final TimerTask sweeper;
   @Nullable
   private long executionTime;

   public IdleConnectionSweeper(@Nonnull HttpClientConnectionManager manager, long idleTimeout, long sweepInterval) {
      this(manager, idleTimeout, sweepInterval, new Timer(TimerSupport.getTimerName((String)IdleConnectionSweeper.class.getName(), (String)null), true));
      this.createdTimer = true;
   }

   public IdleConnectionSweeper(@Nonnull HttpClientConnectionManager manager, final long idleTimeout, long sweepInterval, @Nonnull Timer backgroundTimer) {
      this.connectionManager = (HttpClientConnectionManager)Constraint.isNotNull(manager, "HttpClientConnectionManager can not be null");
      this.taskTimer = (Timer)Constraint.isNotNull(backgroundTimer, "Sweeper task timer can not be null");
      this.sweeper = new TimerTask() {
         public void run() {
            IdleConnectionSweeper.this.executionTime = System.currentTimeMillis();
            IdleConnectionSweeper.this.connectionManager.closeIdleConnections(idleTimeout, TimeUnit.MILLISECONDS);
         }
      };
      this.taskTimer.schedule(this.sweeper, sweepInterval, sweepInterval);
   }

   public long scheduledExecutionTime() {
      if (this.isDestroyed()) {
         throw new DestroyedComponentException();
      } else {
         return this.executionTime != 0L ? this.executionTime : this.sweeper.scheduledExecutionTime();
      }
   }

   public boolean isDestroyed() {
      return this.destroyed;
   }

   public synchronized void destroy() {
      this.sweeper.cancel();
      if (this.createdTimer) {
         this.taskTimer.cancel();
      }

      this.destroyed = true;
   }
}
