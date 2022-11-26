package com.bea.xbeanmarshal.runtime.internal.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Verbose {
   private static boolean verboseOn = false;
   private static HashSet startsWith;
   private static HashSet equals;
   private static PrintStream out;
   private static DateFormat df;
   private static boolean allwaysOn;
   private static final String WEBSERVICE = "webservice";
   private static boolean timestamp;

   private static void init() {
      timestamp = Boolean.getBoolean("weblogic.wsee.verbose.timestamp");
      String property = System.getProperty("weblogic.wsee.verbose");
      if (property == null) {
         property = System.getProperty("com.bea.xbeanmarshal.verbose");
      }

      if (property != null) {
         verboseOn = true;
      }

      if (verboseOn) {
         banner("staxb.xbeanmarshal running in Verbose mode");
         parseProperty(property);
      }

   }

   public static void setVerbose(boolean flag) {
      allwaysOn = flag;
   }

   public static void log(Object s, Throwable e) {
      log(s, 2);
      log(e, 2);
      e.printStackTrace();
   }

   public static void log(Object s) {
      Object s = expand(s);
      log(s, 2);
   }

   public static void logException(Throwable e) {
      flush();
      if (e != null) {
         log(e, 2);
         e.printStackTrace(out);
         out.flush();
      }
   }

   private static String fitIn(Object arg) {
      String message = arg == null ? "null" : arg.toString();
      if (message.length() > 70) {
         message = message.substring(0, 67) + "...";
      }

      return message;
   }

   public static PrintStream getOut() {
      return out;
   }

   public static String expand(Object s) {
      if (s == null) {
         return "null";
      } else if (!s.getClass().isArray()) {
         return s.toString();
      } else {
         int length = Array.getLength(s);
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < length; ++i) {
            sb.append("[").append(i).append("]");
            sb.append(Array.get(s, i));
         }

         return sb.toString();
      }
   }

   public static void banner(String msg) {
      flush();
      if (timestamp) {
         msg = "XBEANMARSHAL>[" + df.format(new Date()) + "]";
      } else {
         msg = "<XBEANMARSHAL> " + msg;
      }

      if (msg.length() > 60) {
         msg = msg.substring(0, 58) + "..";
      }

      out.print("  ");

      int i;
      for(i = 0; i < msg.length() + 4; ++i) {
         out.print("-");
      }

      out.println();
      out.print(" |  ");
      out.print(msg);
      out.println("  |");
      out.print("  ");

      for(i = 0; i < msg.length() + 4; ++i) {
         out.print("-");
      }

      out.println();
   }

   private static void log(Object s, int stack) {
      flush();
      StackTraceElement[] st = (new Exception()).getStackTrace();
      out.print("<XBEANMARSHAL>");
      if (timestamp) {
         out.print("[");
         out.print(df.format(new Date()));
         out.print("]");
      }

      out.print(s);
      out.print("<");
      out.print(shortName(st[stack].getClassName()));
      out.print(".");
      out.print(st[stack].getMethodName());
      int line = st[stack].getLineNumber();
      if (line > -1) {
         out.print(":");
         out.print(line);
      }

      out.println(">");
      out.flush();
   }

   private static void flush() {
      System.out.flush();
      System.err.flush();
      out.flush();
   }

   public static void say(String message) {
      flush();
      if (timestamp) {
         out.print("[");
         out.print(df.format(new Date()));
         out.print("]");
      }

      out.println(message);
   }

   private static String shortName(String className) {
      int index = className.lastIndexOf(46);
      if (index != -1) {
         className = className.substring(index + 1, className.length());
      }

      return className;
   }

   private static void parseProperty(String property) {
      StringTokenizer st = new StringTokenizer(property, ",");

      while(st.hasMoreTokens()) {
         String token = st.nextToken().trim();
         if (token.startsWith("file=")) {
            setOutput(token.substring("file=".length()));
         } else if (token.endsWith("*")) {
            String tag = token.substring(0, token.length() - 1);
            addStartsWith(tag);
         } else {
            addEquals(token);
         }
      }

   }

   private static void setOutput(String s) {
      File file = new File(s);

      try {
         out = new PrintStream(new FileOutputStream(file), true);
      } catch (FileNotFoundException var3) {
         logException(var3);
         log("Log to System.out");
         out = System.out;
      }

   }

   private static void addEquals(String token) {
      if (equals == null) {
         equals = new HashSet();
      }

      equals.add(token);
   }

   private static void addStartsWith(String s) {
      if (startsWith == null) {
         startsWith = new HashSet();
      }

      startsWith.add(s);
   }

   public static boolean isVerbose(Class clazz) {
      if (allwaysOn) {
         return true;
      } else if (!verboseOn) {
         return false;
      } else {
         String name = clazz.getName();
         if (equals != null && equals.contains(name)) {
            return true;
         } else {
            if (startsWith != null) {
               Iterator it = startsWith.iterator();

               while(it.hasNext()) {
                  if (name.startsWith((String)it.next())) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   public String toString() {
      ToStringWriter writer = new ToStringWriter();
      this.toString(writer);
      return writer.toString();
   }

   public void toString(ToStringWriter writer) {
      writer.start(this);
      writer.end();
   }

   public static void here() {
      log("Called ........", 2);
   }

   static {
      out = System.out;
      df = DateFormat.getInstance();
      allwaysOn = false;
      init();
      timestamp = false;
   }
}
