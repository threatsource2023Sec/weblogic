package com.bea.xml_.impl.jam.internal;

import com.bea.xml_.impl.jam.provider.JamLogger;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class JamLoggerImpl implements JamLogger {
   private boolean mShowWarnings = true;
   private Set mVerboseClasses = null;
   private PrintWriter mOut;

   public JamLoggerImpl() {
      this.mOut = new PrintWriter(System.out, true);
   }

   protected void setOut(PrintWriter out) {
      this.mOut = out;
   }

   public boolean isVerbose(Object o) {
      if (this.mVerboseClasses == null) {
         return false;
      } else {
         Iterator i = this.mVerboseClasses.iterator();

         Class c;
         do {
            if (!i.hasNext()) {
               return false;
            }

            c = (Class)i.next();
         } while(!c.isAssignableFrom(o.getClass()));

         return true;
      }
   }

   public boolean isVerbose(Class aClass) {
      if (this.mVerboseClasses == null) {
         return false;
      } else {
         Iterator i = this.mVerboseClasses.iterator();

         Class c;
         do {
            if (!i.hasNext()) {
               return false;
            }

            c = (Class)i.next();
         } while(!c.isAssignableFrom(aClass));

         return true;
      }
   }

   public void setVerbose(Class c) {
      if (c == null) {
         throw new IllegalArgumentException();
      } else {
         if (this.mVerboseClasses == null) {
            this.mVerboseClasses = new HashSet();
         }

         this.mVerboseClasses.add(c);
      }
   }

   public void setShowWarnings(boolean b) {
      this.mShowWarnings = b;
   }

   public void verbose(String msg, Object o) {
      if (this.isVerbose(o)) {
         this.verbose(msg);
      }

   }

   public void verbose(Throwable t, Object o) {
      if (this.isVerbose(o)) {
         this.verbose(t);
      }

   }

   public void verbose(String msg) {
      this.printVerbosePrefix();
      this.mOut.println(msg);
   }

   public void verbose(Throwable t) {
      this.printVerbosePrefix();
      this.mOut.println();
      t.printStackTrace(this.mOut);
   }

   public void warning(Throwable t) {
      if (this.mShowWarnings) {
         this.mOut.println("[JAM] Warning: unexpected exception thrown: ");
         t.printStackTrace();
      }

   }

   public void warning(String w) {
      if (this.mShowWarnings) {
         this.mOut.print("[JAM] Warning: ");
         this.mOut.println(w);
      }

   }

   public void error(Throwable t) {
      this.mOut.println("[JAM] Error: unexpected exception thrown: ");
      t.printStackTrace(this.mOut);
   }

   public void error(String msg) {
      this.mOut.print("[JAM] Error: ");
      this.mOut.println(msg);
   }

   public void setVerbose(boolean v) {
      this.setVerbose(Object.class);
   }

   public boolean isVerbose() {
      return this.mVerboseClasses != null;
   }

   private void printVerbosePrefix() {
      StackTraceElement[] st = (new Exception()).getStackTrace();
      this.mOut.println("[JAM] Verbose: ");
      this.mOut.print('(');
      this.mOut.print(shortName(st[2].getClassName()));
      this.mOut.print('.');
      this.mOut.print(st[2].getMethodName());
      this.mOut.print(':');
      this.mOut.print(st[2].getLineNumber());
      this.mOut.print(")  ");
   }

   private static String shortName(String className) {
      int index = className.lastIndexOf(46);
      if (index != -1) {
         className = className.substring(index + 1, className.length());
      }

      return className;
   }
}
