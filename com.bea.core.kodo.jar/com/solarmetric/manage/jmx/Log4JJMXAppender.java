package com.solarmetric.manage.jmx;

import javax.management.Notification;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Closeable;

class Log4JJMXAppender extends AppenderSkeleton implements LogAppender, Closeable {
   private long _seqNum = 0L;
   private boolean _isOpen = true;
   private LogMBean _mbean;
   private NotificationBroadcasterImpl _bcastSupport;

   public void init(LogMBean mbean, NotificationBroadcasterImpl bcastSupport, Log log) {
      this._mbean = mbean;
      this._bcastSupport = bcastSupport;
   }

   public void start() {
      Logger.getRootLogger().addAppender(this);
      this._isOpen = true;
   }

   public void stop() {
      this.close();
      Logger.getRootLogger().removeAppender(this);
      this._isOpen = false;
   }

   protected void append(LoggingEvent event) {
      if (this._isOpen) {
         Notification notif = new Notification(event.getLevel().toString(), this._mbean, this._seqNum, System.currentTimeMillis(), event.getRenderedMessage());
         notif.setUserData(event.getLoggerName());
         this._bcastSupport.sendNotification(notif);
         ++this._seqNum;
      }
   }

   public boolean requiresLayout() {
      return true;
   }

   public boolean isOpen() {
      return this._isOpen;
   }

   public void close() {
      this._isOpen = false;
   }
}
