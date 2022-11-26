package org.apache.openjpa.lib.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class LogFactoryAdapter implements LogFactory {
   private Map _logs = new ConcurrentHashMap();

   public Log getLog(String channel) {
      Log log = (Log)this._logs.get(channel);
      if (log == null) {
         log = this.newLogAdapter(channel);
         this._logs.put(channel, log);
      }

      return log;
   }

   protected abstract Log newLogAdapter(String var1);
}
