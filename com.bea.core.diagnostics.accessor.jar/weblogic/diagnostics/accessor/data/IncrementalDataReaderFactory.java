package weblogic.diagnostics.accessor.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public class IncrementalDataReaderFactory implements TimerListener {
   private static final String TIMER_MANAGER = IncrementalDataReaderFactory.class.getName();
   private static final long TIMER_INTERVAL = 300000L;
   private Map registry;

   public static IncrementalDataReaderFactory getInstance() {
      return IncrementalDataReaderFactory.SingletonWrapper.INSTANCE;
   }

   private IncrementalDataReaderFactory() {
      this.registry = new HashMap();
      TimerManagerFactory tmf = TimerManagerFactory.getTimerManagerFactory();
      TimerManager tm = tmf.getTimerManager(TIMER_MANAGER);
      tm.scheduleAtFixedRate(this, 0L, 300000L);
   }

   public synchronized void timerExpired(Timer timer) {
      Iterator var2 = this.registry.keySet().iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         IncrementalDataReader reader = (IncrementalDataReader)this.registry.get(key);
         if (this.hasTimedout(reader)) {
            try {
               this.closeIncrementalDataReader(key);
            } catch (IOException var6) {
            }
         }
      }

   }

   private boolean hasTimedout(IncrementalDataReader ti) {
      return System.currentTimeMillis() - ti.getLastAccessedTimestamp() > 300000L;
   }

   public synchronized IncrementalDataReader initializeIncrementalDataReader(String key, InputStream inputStream) {
      if (!this.registry.containsKey(key) && inputStream != null) {
         IncrementalDataReader reader = new IncrementalDataReader(inputStream);
         this.registry.put(key, reader);
         return reader;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public synchronized IncrementalDataReader getIncrementalDataReader(String key) {
      return (IncrementalDataReader)this.registry.get(key);
   }

   public synchronized void closeIncrementalDataReader(String key) throws IOException {
      IncrementalDataReader reader = (IncrementalDataReader)this.registry.remove(key);
      if (reader != null) {
         reader.close();
      }

   }

   // $FF: synthetic method
   IncrementalDataReaderFactory(Object x0) {
      this();
   }

   private static final class SingletonWrapper {
      private static final IncrementalDataReaderFactory INSTANCE = new IncrementalDataReaderFactory();
   }
}
