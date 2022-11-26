package org.apache.openjpa.lib.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiLogFactory implements LogFactory {
   private List _delegates;

   public MultiLogFactory(LogFactory d1, LogFactory d2) {
      this(new LogFactory[]{d1, d2});
   }

   public MultiLogFactory(LogFactory d1, LogFactory d2, LogFactory d3) {
      this(new LogFactory[]{d1, d2, d3});
   }

   public MultiLogFactory(LogFactory[] delegates) {
      this._delegates = new CopyOnWriteArrayList(Arrays.asList(delegates));
   }

   public void addLogFactory(LogFactory factory) {
      this._delegates.add(factory);
   }

   public void removeLogFactory(LogFactory factory) {
      this._delegates.remove(factory);
   }

   public LogFactory[] getDelegates() {
      return (LogFactory[])((LogFactory[])this._delegates.toArray(new LogFactory[0]));
   }

   public synchronized Log getLog(String channel) {
      List logs = new ArrayList(this._delegates.size());
      Iterator i = this._delegates.iterator();

      while(i.hasNext()) {
         LogFactory f = (LogFactory)i.next();
         if (f != null) {
            Log l = f.getLog(channel);
            if (l != null) {
               logs.add(l);
            }
         }
      }

      return new MultiLog((Log[])((Log[])logs.toArray(new Log[logs.size()])));
   }

   private static class MultiLog implements Log {
      private Log[] _logs;

      public MultiLog(Log[] logs) {
         this._logs = logs;
      }

      public Log[] getDelegates() {
         return this._logs;
      }

      public void trace(Object msg) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].trace(msg);
         }

      }

      public void trace(Object msg, Throwable t) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].trace(msg, t);
         }

      }

      public void info(Object msg) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].info(msg);
         }

      }

      public void info(Object msg, Throwable t) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].info(msg, t);
         }

      }

      public void warn(Object msg) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].warn(msg);
         }

      }

      public void warn(Object msg, Throwable t) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].warn(msg, t);
         }

      }

      public void error(Object msg) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].error(msg);
         }

      }

      public void error(Object msg, Throwable t) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].error(msg, t);
         }

      }

      public void fatal(Object msg) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].fatal(msg);
         }

      }

      public void fatal(Object msg, Throwable t) {
         for(int i = 0; i < this._logs.length; ++i) {
            this._logs[i].fatal(msg, t);
         }

      }

      public boolean isTraceEnabled() {
         for(int i = 0; i < this._logs.length; ++i) {
            if (this._logs[i].isTraceEnabled()) {
               return true;
            }
         }

         return false;
      }

      public boolean isInfoEnabled() {
         for(int i = 0; i < this._logs.length; ++i) {
            if (this._logs[i].isInfoEnabled()) {
               return true;
            }
         }

         return false;
      }

      public boolean isWarnEnabled() {
         for(int i = 0; i < this._logs.length; ++i) {
            if (this._logs[i].isWarnEnabled()) {
               return true;
            }
         }

         return false;
      }

      public boolean isErrorEnabled() {
         for(int i = 0; i < this._logs.length; ++i) {
            if (this._logs[i].isErrorEnabled()) {
               return true;
            }
         }

         return false;
      }

      public boolean isFatalEnabled() {
         for(int i = 0; i < this._logs.length; ++i) {
            if (this._logs[i].isFatalEnabled()) {
               return true;
            }
         }

         return false;
      }
   }
}
