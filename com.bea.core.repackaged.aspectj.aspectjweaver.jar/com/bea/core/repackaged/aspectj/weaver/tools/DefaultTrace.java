package com.bea.core.repackaged.aspectj.weaver.tools;

import java.io.PrintStream;

public class DefaultTrace extends AbstractTrace {
   private boolean traceEnabled = false;
   private PrintStream print;

   public DefaultTrace(Class clazz) {
      super(clazz);
      this.print = System.err;
   }

   public boolean isTraceEnabled() {
      return this.traceEnabled;
   }

   public void setTraceEnabled(boolean b) {
      this.traceEnabled = b;
   }

   public void enter(String methodName, Object thiz, Object[] args) {
      if (this.traceEnabled) {
         this.println(this.formatMessage(">", this.tracedClass.getName(), methodName, thiz, args));
      }

   }

   public void enter(String methodName, Object thiz) {
      if (this.traceEnabled) {
         this.println(this.formatMessage(">", this.tracedClass.getName(), methodName, thiz, (Object[])null));
      }

   }

   public void exit(String methodName, Object ret) {
      if (this.traceEnabled) {
         this.println(this.formatMessage("<", this.tracedClass.getName(), methodName, ret, (Object[])null));
      }

   }

   public void exit(String methodName) {
      if (this.traceEnabled) {
         this.println(this.formatMessage("<", this.tracedClass.getName(), methodName, (Object)null, (Object[])null));
      }

   }

   public void exit(String methodName, Throwable th) {
      if (this.traceEnabled) {
         this.println(this.formatMessage("<", this.tracedClass.getName(), methodName, th, (Object[])null));
      }

   }

   public void event(String methodName, Object thiz, Object[] args) {
      if (this.traceEnabled) {
         this.println(this.formatMessage("-", this.tracedClass.getName(), methodName, thiz, args));
      }

   }

   public void event(String methodName) {
      if (this.traceEnabled) {
         this.println(this.formatMessage("-", this.tracedClass.getName(), methodName, (Object)null, (Object[])null));
      }

   }

   public void debug(String message) {
      this.println(this.formatMessage("?", message, (Throwable)null));
   }

   public void info(String message) {
      this.println(this.formatMessage("I", message, (Throwable)null));
   }

   public void warn(String message, Throwable th) {
      this.println(this.formatMessage("W", message, th));
      if (th != null) {
         th.printStackTrace();
      }

   }

   public void error(String message, Throwable th) {
      this.println(this.formatMessage("E", message, th));
      if (th != null) {
         th.printStackTrace();
      }

   }

   public void fatal(String message, Throwable th) {
      this.println(this.formatMessage("X", message, th));
      if (th != null) {
         th.printStackTrace();
      }

   }

   protected void println(String s) {
      this.print.println(s);
   }

   public void setPrintStream(PrintStream printStream) {
      this.print = printStream;
   }
}
