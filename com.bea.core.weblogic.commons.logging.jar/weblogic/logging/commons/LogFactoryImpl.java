package weblogic.logging.commons;

import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

public class LogFactoryImpl extends LogFactory {
   private Map loggers = new WeakHashMap();

   public Object getAttribute(String string) {
      return null;
   }

   public String[] getAttributeNames() {
      return null;
   }

   public Log getInstance(Class class0) throws LogConfigurationException {
      return this.getInstance(class0.getName());
   }

   public Log getInstance(String name) throws LogConfigurationException {
      Log log = null;
      synchronized(this.loggers) {
         LogImpl compareKey = new LogImpl(name, false);
         log = (Log)this.loggers.get(compareKey);
         if (log == null) {
            log = new LogImpl(name);
            this.loggers.put(log, log);
         }

         return (Log)log;
      }
   }

   public void release() {
   }

   public void removeAttribute(String string) {
   }

   public void setAttribute(String string, Object object) {
   }
}
