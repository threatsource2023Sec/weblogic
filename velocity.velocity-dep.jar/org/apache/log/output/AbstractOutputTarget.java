package org.apache.log.output;

import org.apache.log.LogEvent;
import org.apache.log.format.Formatter;

public abstract class AbstractOutputTarget extends AbstractTarget {
   /** @deprecated */
   protected Formatter m_formatter;

   public AbstractOutputTarget() {
   }

   public AbstractOutputTarget(Formatter formatter) {
      this.m_formatter = formatter;
   }

   /** @deprecated */
   public synchronized Formatter getFormatter() {
      return this.m_formatter;
   }

   /** @deprecated */
   public synchronized void setFormatter(Formatter formatter) {
      this.writeTail();
      this.m_formatter = formatter;
      this.writeHead();
   }

   protected void write(String data) {
      this.output(data);
   }

   /** @deprecated */
   protected void output(String data) {
   }

   protected void doProcessEvent(LogEvent event) {
      String data = this.format(event);
      this.write(data);
   }

   protected synchronized void open() {
      if (!this.isOpen()) {
         super.open();
         this.writeHead();
      }

   }

   public synchronized void close() {
      if (this.isOpen()) {
         this.writeTail();
         super.close();
      }

   }

   private String format(LogEvent event) {
      return null != this.m_formatter ? this.m_formatter.format(event) : event.toString();
   }

   private void writeHead() {
      if (this.isOpen()) {
         String head = this.getHead();
         if (null != head) {
            this.write(head);
         }

      }
   }

   private void writeTail() {
      if (this.isOpen()) {
         String tail = this.getTail();
         if (null != tail) {
            this.write(tail);
         }

      }
   }

   private String getHead() {
      return null;
   }

   private String getTail() {
      return null;
   }
}
