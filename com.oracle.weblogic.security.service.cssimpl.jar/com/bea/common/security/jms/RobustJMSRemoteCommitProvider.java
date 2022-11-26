package com.bea.common.security.jms;

import java.util.Timer;
import java.util.TimerTask;
import javax.jms.JMSException;
import org.apache.openjpa.event.RemoteCommitEvent;
import org.apache.openjpa.lib.util.Localizer;

public class RobustJMSRemoteCommitProvider extends JMSRemoteCommitProvider {
   private static final int STATE_INIT = 0;
   private static final int STATE_ACTIVE = 1;
   private static final int STATE_FAILED = 2;
   private static final String INTERVAL_KEY = "com.bea.common.security.jms.initial_connection_interval";
   private static final String ATTEMPTS_KEY = "com.bea.common.security.jms.initial_connection_attempts";
   private static final String DEFAULT_INTERVAL = "1000";
   private static final String DEFAULT_ATTEMPTS = "3600";
   private static Localizer _loc = Localizer.forPackage(RobustJMSRemoteCommitProvider.class);
   private boolean _failFast = false;
   private Integer _state = new Integer(0);

   public void setFailFast(boolean fast) {
      this._failFast = fast;
   }

   public void endConfiguration() {
      super.endConfiguration();
      this.connect();
      if (this._state != 1) {
         this.startConnectionTask();
      }

   }

   private void startConnectionTask() {
      final Timer timer = new Timer("JMSRemoteCommitProvider_Timer");
      long interval = Long.parseLong(System.getProperty("com.bea.common.security.jms.initial_connection_interval", "1000"));
      final int maximumAttempts = Integer.parseInt(System.getProperty("com.bea.common.security.jms.initial_connection_attempts", "3600"));
      TimerTask task = new TimerTask() {
         private int attemptCounts = 0;

         public void run() {
            RobustJMSRemoteCommitProvider.this.connect();
            ++this.attemptCounts;
            if (RobustJMSRemoteCommitProvider.this._state == 1) {
               timer.cancel();
               if (RobustJMSRemoteCommitProvider.this.log.isInfoEnabled()) {
                  RobustJMSRemoteCommitProvider.this.log.info(RobustJMSRemoteCommitProvider._loc.get("jms-reconnected", RobustJMSRemoteCommitProvider.this.getTopic()));
               }
            } else if (this.attemptCounts == maximumAttempts) {
               timer.cancel();
               RobustJMSRemoteCommitProvider.this.transitState(2, (Throwable)null);
               if (RobustJMSRemoteCommitProvider.this.log.isErrorEnabled()) {
                  RobustJMSRemoteCommitProvider.this.log.error(RobustJMSRemoteCommitProvider._loc.get("jms-cant-reconnect", RobustJMSRemoteCommitProvider.this.getTopic(), String.valueOf(maximumAttempts)));
               }
            }

         }
      };
      timer.schedule(task, interval);
   }

   public void broadcast(RemoteCommitEvent event) {
      this.connect();
      this.doBroadcast(event);
   }

   public void onException(JMSException ex) {
      this.transitState(2, ex);
      String topicName = this.getTopic();
      int reconnectAttempts = this.getReconnectAttempts();
      if (this.log.isWarnEnabled()) {
         this.log.warn(_loc.get("jms-listener-error", topicName), ex);
      }

      if (reconnectAttempts > 0) {
         this.close();
         boolean connected = false;

         for(int i = 0; !connected && i < reconnectAttempts; ++i) {
            try {
               if (this.log.isInfoEnabled()) {
                  this.log.info(_loc.get("jms-reconnect-attempt", topicName, String.valueOf(i + 1)));
               }

               this.connect();
               if (this._state == 1) {
                  connected = true;
               } else {
                  if (this.log.isInfoEnabled()) {
                     this.log.info(_loc.get("jms-reconnect-fail", topicName));
                  }

                  Thread.sleep(1000L);
               }
            } catch (InterruptedException var7) {
               break;
            }
         }

         if (!connected && this.log.isErrorEnabled()) {
            this.log.error(_loc.get("jms-cant-reconnect", topicName, String.valueOf(reconnectAttempts)));
         } else if (connected && this.log.isInfoEnabled()) {
            this.log.info(_loc.get("jms-reconnected", topicName));
         }

      }
   }

   protected void connect() {
      synchronized(this._state) {
         try {
            if (this._state != 1) {
               super.connect();
               this.afterConnected();
               this.transitState(1, (Throwable)null);
            }
         } catch (Throwable var4) {
            this.transitState(2, var4);
         }

      }
   }

   private void transitState(int newState, Throwable ex) {
      if (ex != null && this._failFast) {
         throw new RuntimeException(ex);
      } else {
         if (this._state != newState) {
            this.log.info(_loc.get("transit-state", this.toString(this._state), this.toString(newState)).getMessage());
            this._state = new Integer(newState);
         }

      }
   }

   String toString(int state) {
      switch (state) {
         case 0:
            return "INIT";
         case 1:
            return "ACTIVE";
         case 2:
            return "FAILED";
         default:
            return "UNKNOWN";
      }
   }

   protected void afterConnected() {
   }

   protected void doBroadcast(RemoteCommitEvent event) {
      try {
         this.getPublisher().publish(this.createMessage(event));
         if (this.log.isTraceEnabled()) {
            this.log.trace(_loc.get("jms-sent-update", this.getTopic()));
         }
      } catch (Throwable var3) {
         this.log.warn(_loc.get("no-broadcast"));
         this.transitState(2, var3);
      }

   }
}
