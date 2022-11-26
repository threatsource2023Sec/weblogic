package com.bea.core.repackaged.aspectj.weaver.tools;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jdk14Trace extends AbstractTrace {
   private Logger logger;
   private String name;

   public Jdk14Trace(Class clazz) {
      super(clazz);
      this.name = clazz.getName();
      this.logger = Logger.getLogger(this.name);
   }

   public void enter(String methodName, Object thiz, Object[] args) {
      if (this.logger.isLoggable(Level.FINE)) {
         this.logger.entering(this.name, methodName, this.formatObj(thiz));
         if (args != null && this.logger.isLoggable(Level.FINER)) {
            this.logger.entering(this.name, methodName, this.formatObjects(args));
         }
      }

   }

   public void enter(String methodName, Object thiz) {
      this.enter(methodName, thiz, (Object[])null);
   }

   public void exit(String methodName, Object ret) {
      if (this.logger.isLoggable(Level.FINE)) {
         this.logger.exiting(this.name, methodName, this.formatObj(ret));
      }

   }

   public void exit(String methodName, Throwable th) {
      if (this.logger.isLoggable(Level.FINE)) {
         this.logger.exiting(this.name, methodName, th);
      }

   }

   public void exit(String methodName) {
      if (this.logger.isLoggable(Level.FINE)) {
         this.logger.exiting(this.name, methodName);
      }

   }

   public void event(String methodName, Object thiz, Object[] args) {
      if (this.logger.isLoggable(Level.FINE)) {
         this.logger.logp(Level.FINER, this.name, methodName, "EVENT", this.formatObj(thiz));
         if (args != null && this.logger.isLoggable(Level.FINER)) {
            this.logger.logp(Level.FINER, this.name, methodName, "EVENT", this.formatObjects(args));
         }
      }

   }

   public void event(String methodName) {
      if (this.logger.isLoggable(Level.FINE)) {
         this.logger.logp(Level.FINER, this.name, methodName, "EVENT");
      }

   }

   public boolean isTraceEnabled() {
      return this.logger.isLoggable(Level.FINER);
   }

   public void setTraceEnabled(boolean b) {
      if (b) {
         this.logger.setLevel(Level.FINER);
         Handler[] handlers = this.logger.getHandlers();
         if (handlers.length == 0) {
            Logger parent = this.logger.getParent();
            if (parent != null) {
               handlers = parent.getHandlers();
            }
         }

         for(int i = 0; i < handlers.length; ++i) {
            Handler handler = handlers[i];
            handler.setLevel(Level.FINER);
         }
      } else {
         this.logger.setLevel(Level.INFO);
      }

   }

   public void debug(String message) {
      if (this.logger.isLoggable(Level.FINE)) {
         this.logger.fine(message);
      }

   }

   public void info(String message) {
      if (this.logger.isLoggable(Level.INFO)) {
         this.logger.info(message);
      }

   }

   public void warn(String message, Throwable th) {
      if (this.logger.isLoggable(Level.WARNING)) {
         this.logger.log(Level.WARNING, message, th);
      }

   }

   public void error(String message, Throwable th) {
      if (this.logger.isLoggable(Level.SEVERE)) {
         this.logger.log(Level.SEVERE, message, th);
      }

   }

   public void fatal(String message, Throwable th) {
      if (this.logger.isLoggable(Level.SEVERE)) {
         this.logger.log(Level.SEVERE, message, th);
      }

   }
}
