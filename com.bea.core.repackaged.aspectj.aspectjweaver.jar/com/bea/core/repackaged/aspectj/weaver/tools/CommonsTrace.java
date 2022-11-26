package com.bea.core.repackaged.aspectj.weaver.tools;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;

public class CommonsTrace extends AbstractTrace {
   private Log log;
   private String className;

   public CommonsTrace(Class clazz) {
      super(clazz);
      this.log = LogFactory.getLog(clazz);
      this.className = this.tracedClass.getName();
   }

   public void enter(String methodName, Object thiz, Object[] args) {
      if (this.log.isDebugEnabled()) {
         this.log.debug(this.formatMessage(">", this.className, methodName, thiz, args));
      }

   }

   public void enter(String methodName, Object thiz) {
      if (this.log.isDebugEnabled()) {
         this.log.debug(this.formatMessage(">", this.className, methodName, thiz, (Object[])null));
      }

   }

   public void exit(String methodName, Object ret) {
      if (this.log.isDebugEnabled()) {
         this.log.debug(this.formatMessage("<", this.className, methodName, ret, (Object[])null));
      }

   }

   public void exit(String methodName, Throwable th) {
      if (this.log.isDebugEnabled()) {
         this.log.debug(this.formatMessage("<", this.className, methodName, th, (Object[])null));
      }

   }

   public void exit(String methodName) {
      if (this.log.isDebugEnabled()) {
         this.log.debug(this.formatMessage("<", this.className, methodName, (Object)null, (Object[])null));
      }

   }

   public void event(String methodName, Object thiz, Object[] args) {
      if (this.log.isDebugEnabled()) {
         this.log.debug(this.formatMessage("-", this.className, methodName, thiz, args));
      }

   }

   public void event(String methodName) {
      if (this.log.isDebugEnabled()) {
         this.log.debug(this.formatMessage("-", this.className, methodName, (Object)null, (Object[])null));
      }

   }

   public boolean isTraceEnabled() {
      return this.log.isDebugEnabled();
   }

   public void setTraceEnabled(boolean b) {
   }

   public void debug(String message) {
      if (this.log.isDebugEnabled()) {
         this.log.debug(message);
      }

   }

   public void info(String message) {
      if (this.log.isInfoEnabled()) {
         this.log.info(message);
      }

   }

   public void warn(String message, Throwable th) {
      if (this.log.isWarnEnabled()) {
         this.log.warn(message, th);
      }

   }

   public void error(String message, Throwable th) {
      if (this.log.isErrorEnabled()) {
         this.log.error(message, th);
      }

   }

   public void fatal(String message, Throwable th) {
      if (this.log.isFatalEnabled()) {
         this.log.fatal(message, th);
      }

   }
}
