package com.bea.logging;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogBufferHandler extends Handler {
   public static final int DEFAULT_LOG_BUFFER_SIZE_LIMIT = 50;
   private static final boolean DEBUG = false;
   private ConcurrentLinkedQueue buffer;
   private int bufferSizeLimit;
   private AtomicInteger bufferSize;
   private boolean closed;
   private volatile Handler delegate;

   public static LogBufferHandler getInstance() {
      return LogBufferHandler.LogBufferHandlerSingletonFactory.SINGLETON;
   }

   protected LogBufferHandler(int size) {
      this.buffer = new ConcurrentLinkedQueue();
      this.bufferSizeLimit = 50;
      this.bufferSize = new AtomicInteger();
      this.closed = false;
      this.delegate = null;
      this.bufferSizeLimit = size;
      this.setErrorManager(new LogErrorManager(this));
      this.setLevel(Level.ALL);
   }

   protected LogBufferHandler() {
      this(50);
   }

   public synchronized void close() {
      this.closed = true;
   }

   public void flush() {
      this.buffer.clear();
      this.bufferSize.set(0);
   }

   public synchronized void setDelegateAndClose(Handler h) {
      this.delegate = h;
      this.closed = true;
   }

   public synchronized void publish(LogRecord rec) {
      if (!this.closed && this.isLoggable((LogRecord)rec)) {
         if (!(rec instanceof BaseLogRecord)) {
            rec = new BaseLogRecord((LogRecord)rec);
         }

         this.bufferLogObject(rec);
      } else if (this.delegate != null) {
         this.delegate.publish((LogRecord)rec);
      }

   }

   private void bufferLogObject(Object entry) {
      this.buffer.add(entry);
      if (this.bufferSize.incrementAndGet() > this.bufferSizeLimit) {
         this.buffer.poll();
         this.bufferSize.decrementAndGet();
      }

   }

   public Iterator getLogBufferIterator() {
      return this.buffer.iterator();
   }

   public void dumpLogBuffer(OutputStream out, Formatter formatter) throws IOException {
      Writer writer = new BufferedWriter(new OutputStreamWriter(out));
      Iterator iter = this.buffer.iterator();

      while(iter.hasNext()) {
         LogRecord lr = (LogRecord)iter.next();
         this.updateStatsCounter(lr);
         String msg = formatter.format(lr);
         writer.write(msg);
      }

      writer.flush();
   }

   public void setBufferSizeLimit(int size) {
      if (size < 0) {
         throw new IllegalArgumentException();
      } else {
         this.bufferSizeLimit = size;

         while(this.bufferSize.get() > this.bufferSizeLimit) {
            this.buffer.poll();
            this.bufferSize.decrementAndGet();
         }

      }
   }

   private void updateStatsCounter(LogRecord rec) {
      Level level = rec.getLevel();
      int severity = LogLevel.getSeverity(level);
      AtomicInteger counter = (AtomicInteger)StatsHandler.getCountsBySeverity().get(severity);
      counter.getAndIncrement();
   }

   private static final class LogBufferHandlerSingletonFactory {
      private static final LogBufferHandler SINGLETON = new LogBufferHandler();
   }
}
