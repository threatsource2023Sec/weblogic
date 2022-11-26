package oracle.jrockit.jfr.events;

import com.oracle.jrockit.jfr.EventToken;
import com.oracle.jrockit.jfr.RequestableEvent;
import java.lang.reflect.Constructor;
import java.util.TimerTask;
import oracle.jrockit.jfr.JFRImpl;
import oracle.jrockit.jfr.StringConstantPool;
import oracle.jrockit.jfr.settings.EventSetting;

public abstract class EventHandlerImpl extends EventHandler {
   private TimerTask requestTask;
   private boolean enabled;
   private boolean stacktraceEnabled;
   private long thresholdTicks = 0L;
   private long period = -1L;
   private long nanoThreshold = -1L;
   protected final JFRImpl jfr;
   protected final StringConstantPool[] pools;

   public EventHandlerImpl(JFRImpl jfr, JavaEventDescriptor descriptor, StringConstantPool[] pools) {
      super(descriptor);
      this.jfr = jfr;
      this.pools = pools;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public boolean isStackTraceEnabled() {
      return this.stacktraceEnabled;
   }

   public void setStackTraceEnabled(boolean stacktraceOn) {
      this.stacktraceEnabled = stacktraceOn;
   }

   public long getThresholdTicks() {
      return this.thresholdTicks;
   }

   public long getThreshold() {
      return this.nanoThreshold;
   }

   public void setThreshold(long nanoThreshold) {
      if (this.isTimed()) {
         this.nanoThreshold = nanoThreshold;
         this.thresholdTicks = this.jfr.nanoToCounter(nanoThreshold);
      }

   }

   public long getPeriod() {
      return this.period;
   }

   public long counterTime() {
      return this.jfr.counterTime();
   }

   protected final long stackTraceID() {
      return !this.isStackTraceEnabled() ? 0L : this.jfr.stackTraceID(5);
   }

   public void setPeriod(long period) {
      if (this.isRequestable()) {
         if (this.period != period) {
            if (this.requestTask != null) {
               this.requestTask.cancel();
            }

            this.period = period;
            if (period != -1L && period > 0L) {
               final RequestableEvent tmp = null;
               Class rc = this.descriptor.getEventClass().asSubclass(RequestableEvent.class);

               try {
                  Constructor c = rc.getConstructor(EventToken.class);
                  Constructor tc = EventToken.class.getDeclaredConstructor(EventHandler.class);
                  tc.setAccessible(true);
                  EventToken t = (EventToken)tc.newInstance(this);
                  tmp = (RequestableEvent)c.newInstance(t);
               } catch (Exception var10) {
                  try {
                     tmp = (RequestableEvent)rc.newInstance();
                  } catch (InstantiationException var8) {
                  } catch (IllegalAccessException var9) {
                  }
               }

               if (tmp != null) {
                  this.requestTask = new TimerTask() {
                     public void run() {
                        tmp.request();
                        tmp.commit();
                     }
                  };
                  this.jfr.getTimer().schedule(this.requestTask, period, period);
               }
            }
         }

      }
   }

   public void apply(EventSetting s) {
      this.setEnabled(s.isEnabled());
      this.setStackTraceEnabled(s.isStacktraceEnabled());
      this.setThreshold(s.getThreshold());
      this.setPeriod(s.getPeriod());
   }
}
