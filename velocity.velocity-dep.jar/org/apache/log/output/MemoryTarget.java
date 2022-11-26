package org.apache.log.output;

import org.apache.log.LogEvent;
import org.apache.log.LogTarget;
import org.apache.log.Priority;

public class MemoryTarget extends AbstractTarget {
   private final LogEvent[] m_buffer;
   private Priority m_threshold;
   private LogTarget m_target;
   private int m_used;
   private int m_index;
   private boolean m_overwrite;

   public MemoryTarget(LogTarget target, int size, Priority threshold) {
      this.m_target = target;
      this.m_buffer = new LogEvent[size];
      this.m_threshold = threshold;
      this.open();
   }

   protected synchronized void setOverwrite(boolean overwrite) {
      this.m_overwrite = overwrite;
   }

   protected synchronized void doProcessEvent(LogEvent event) {
      if (this.isFull()) {
         if (!this.m_overwrite) {
            this.getErrorHandler().error("Memory buffer is full", (Throwable)null, event);
            return;
         }

         --this.m_used;
      }

      if (0 == this.m_used) {
         this.m_index = 0;
      } else {
         this.m_index = (this.m_index + 1) % this.m_buffer.length;
      }

      this.m_buffer[this.m_index] = event;
      ++this.m_used;
      if (this.shouldPush(event)) {
         this.push();
      }

   }

   public final synchronized boolean isFull() {
      return this.m_buffer.length == this.m_used;
   }

   protected synchronized boolean shouldPush(LogEvent event) {
      return this.m_threshold.isLowerOrEqual(event.getPriority()) || this.isFull();
   }

   public synchronized void push() {
      if (null == this.m_target) {
         this.getErrorHandler().error("Can not push events to a null target", (Throwable)null, (LogEvent)null);
      } else {
         try {
            int size = this.m_used;
            int base = this.m_index - this.m_used + 1;
            if (base < 0) {
               base += this.m_buffer.length;
            }

            for(int i = 0; i < size; ++i) {
               int index = (base + i) % this.m_buffer.length;
               this.m_target.processEvent(this.m_buffer[index]);
               this.m_buffer[index] = null;
               --this.m_used;
            }
         } catch (Throwable var5) {
            this.getErrorHandler().error("Unknown error pushing events.", var5, (LogEvent)null);
         }

      }
   }
}
