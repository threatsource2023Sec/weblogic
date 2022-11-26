package weblogic.servlet.http2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class StreamCleaner implements NakedTimerListener {
   private static StreamCleaner instance = new StreamCleaner();
   private List checkList = new ArrayList();
   private Timer timer;
   TimerManager timerManager;
   private AtomicBoolean start;
   private static final int timeoutSec = 30000;

   private StreamCleaner() {
      this.start = new AtomicBoolean(Boolean.FALSE);
   }

   public static StreamCleaner getStreamCleaner() {
      return instance;
   }

   public void start() {
      if (this.start.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
         this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("StreamCleaner-Timer", WorkManagerFactory.getInstance().getSystem());
         this.timer = this.timerManager.schedule(this, 0L, 30000L);
      }

   }

   public void timerExpired(Timer arg0) {
      Iterator var2 = this.checkList.iterator();

      while(var2.hasNext()) {
         HTTP2Connection conn = (HTTP2Connection)var2.next();
         conn.closeTimeoutStreams(30000);
         if (conn.isOnGoAway() && !conn.isConnectionClosed()) {
            conn.tryTeminateConnection();
         }
      }

   }

   public synchronized void addToCleaner(HTTP2Connection conn) {
      this.start();
      if (!this.checkList.contains(conn) && !conn.isConnectionClosed()) {
         this.checkList.add(conn);
      }

   }

   void stop() {
      this.timer.cancel();
      this.timerManager.stop();
   }
}
