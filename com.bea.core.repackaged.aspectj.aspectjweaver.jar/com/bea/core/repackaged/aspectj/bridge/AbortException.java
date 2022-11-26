package com.bea.core.repackaged.aspectj.bridge;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AbortException extends RuntimeException {
   private static final long serialVersionUID = -7211791639898586417L;
   private boolean isSilent;
   public static final String NO_MESSAGE_TEXT = "AbortException (no message)";
   private static final ArrayList porters = new ArrayList();
   protected IMessage message;
   protected boolean isPorter;

   public static AbortException borrowPorter(IMessage message) {
      AbortException result;
      synchronized(porters) {
         if (porters.size() > 0) {
            result = (AbortException)porters.get(0);
         } else {
            result = new AbortException();
            result.setIsSilent(false);
         }
      }

      result.setIMessage(message);
      result.isPorter = true;
      return result;
   }

   public static void returnPorter(AbortException porter) {
      synchronized(porters) {
         if (porters.contains(porter)) {
            throw new IllegalStateException("already have " + porter);
         } else {
            porters.add(porter);
         }
      }
   }

   private static String extractMessage(IMessage message) {
      if (null == message) {
         return "AbortException (no message)";
      } else {
         String m = message.getMessage();
         return null == m ? "AbortException (no message)" : m;
      }
   }

   public AbortException() {
      this("ABORT");
      this.isSilent = true;
   }

   public AbortException(String s) {
      super(null != s ? s : "AbortException (no message)");
      this.isSilent = false;
      this.message = null;
   }

   public AbortException(IMessage message) {
      super(extractMessage(message));
      this.isSilent = false;
      this.message = message;
   }

   public IMessage getIMessage() {
      return this.message;
   }

   public boolean isPorter() {
      return this.isPorter;
   }

   public Throwable getThrown() {
      Throwable result = null;
      IMessage m = this.getIMessage();
      if (null != m) {
         result = m.getThrown();
         if (result instanceof AbortException) {
            return ((AbortException)result).getThrown();
         }
      }

      return result;
   }

   private void setIMessage(IMessage message) {
      this.message = message;
   }

   public String getMessage() {
      String message = super.getMessage();
      if (null == message || "AbortException (no message)" == message) {
         IMessage m = this.getIMessage();
         if (null != m) {
            message = m.getMessage();
            if (null == message) {
               Throwable thrown = m.getThrown();
               if (null != thrown) {
                  message = thrown.getMessage();
               }
            }
         }

         if (null == message) {
            message = "AbortException (no message)";
         }
      }

      return message;
   }

   public void printStackTrace() {
      this.printStackTrace(System.out);
   }

   public void printStackTrace(PrintStream s) {
      IMessage m = this.getIMessage();
      Throwable thrown = null == m ? null : m.getThrown();
      if (this.isPorter() && null != thrown) {
         thrown.printStackTrace(s);
      } else {
         s.println("Message: " + m);
         super.printStackTrace(s);
      }

   }

   public void printStackTrace(PrintWriter s) {
      IMessage m = this.getIMessage();
      Throwable thrown = null == m ? null : m.getThrown();
      if (null == thrown) {
         if (this.isPorter()) {
            s.println("(Warning porter AbortException without thrown)");
         }

         s.println("Message: " + m);
         super.printStackTrace(s);
      } else {
         thrown.printStackTrace(s);
      }

   }

   public boolean isSilent() {
      return this.isSilent;
   }

   public void setIsSilent(boolean isSilent) {
      this.isSilent = isSilent;
   }
}
