package weblogic.servlet.http2;

import java.util.Map;
import weblogic.management.configuration.Http2ConfigMBean;

public class LocalSettings extends DefaultSettings {
   private boolean inPending = false;

   public LocalSettings(Http2ConfigMBean defaultSettings) {
      this.pending.put(1, (long)defaultSettings.getHeaderTableSize());
      this.pending.put(2, 0L);
      this.pending.put(3, (long)defaultSettings.getMaxConcurrentStreams());
      this.pending.put(4, (long)defaultSettings.getInitialWindowSize());
      this.pending.put(5, (long)defaultSettings.getMaxFrameSize());
      this.pending.put(6, (long)defaultSettings.getMaxHeaderListSize());
   }

   protected synchronized void set(Integer id, Long value) {
      this.checkIfInPending();
      if ((Long)this.current.get(id) == value) {
         this.pending.remove(id);
      } else {
         this.pending.put(id, value);
      }

   }

   public synchronized Map getPendingSettings() {
      this.inPending = true;
      return this.pending;
   }

   private void checkIfInPending() {
      if (this.inPending) {
         throw new IllegalStateException();
      }
   }

   public synchronized boolean onAck() {
      if (this.inPending) {
         this.inPending = false;
         this.current.putAll(this.pending);
         this.pending.clear();
         return true;
      } else {
         return false;
      }
   }

   void throwException(String msg, int errorCode) throws IllegalArgumentException {
      throw new IllegalArgumentException(msg);
   }
}
