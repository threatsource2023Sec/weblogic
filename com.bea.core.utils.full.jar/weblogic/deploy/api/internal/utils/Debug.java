package weblogic.deploy.api.internal.utils;

import java.io.PrintStream;

public class Debug {
   public static final String DEBUG_PROP = "weblogic.deployer.debug";
   public static final String CONFIG = "config";
   public static final String DEPLOY = "deploy";
   public static final String STATUS = "status";
   public static final String FACTORY = "factory";
   public static final String MODEL = "model";
   public static final String UTILS = "utils";
   public static final String ALL = "all";
   public static final String INTERNAL = "internal";
   private static String flags;
   private static final PrintStream out;
   private static final boolean DEBUG = false;
   private static final boolean FORCE_DEBUG = false;
   private static final boolean CLASSINFO = true;
   private static final String DEBUG_DEBUG = "";

   public static boolean isDebug(String val) {
      if (flags.indexOf("all") != -1 && !"internal".equals(val)) {
         return true;
      } else {
         return flags.indexOf(val) != -1;
      }
   }

   public static void say(String msg) {
      try {
         out.println((new StackTrace()).location(0).tag(0) + msg);
      } catch (Throwable var2) {
         out.println("[unknown]" + msg);
      }

   }

   static {
      out = System.err;
      flags = System.getProperty("weblogic.deployer.debug", "");
      if ("".equals(flags)) {
         flags = "";
      }

   }

   static final class StackTrace {
      private final Location[] stack;

      StackTrace() {
         StackTraceElement[] elements = (new Exception()).getStackTrace();
         this.stack = new Location[elements.length - 2];

         for(int i = 2; i < elements.length; ++i) {
            this.stack[i - 2] = new Location(elements[i]);
         }

      }

      public Location location(int level) throws ArrayIndexOutOfBoundsException {
         return this.stack[level];
      }

      public void dump(PrintStream out, String msg) {
         out.println(msg);

         for(int i = 0; i < this.stack.length; ++i) {
            out.flush();
            out.println("  " + this.location(i).dump());
         }

      }
   }

   static final class Location {
      private static final String UNKNOWN = "<unknown>";
      private final String pkg;
      private final String clazz;
      private final String method;
      private final String linenum;
      private final String fullClass;
      private final String sourcefile;

      Location(StackTraceElement element) {
         this.fullClass = element.getClassName();
         this.method = element.getMethodName();
         int end = this.fullClass.lastIndexOf(".");
         if (end == -1) {
            this.pkg = "<unknown>";
            this.clazz = this.fullClass;
         } else {
            this.pkg = this.fullClass.substring(0, end);
            this.clazz = this.fullClass.substring(end + 1);
         }

         this.sourcefile = element.getFileName();
         int line = element.getLineNumber();
         this.linenum = line > 0 ? String.valueOf(line) : "<unknown>";
      }

      public String tag(int level) {
         return "[" + this.clazz + "." + this.method + "():" + this.linenum + "] " + (level != 0 ? "(" + level + ")" : "") + ": ";
      }

      public String dump() {
         return this.fullname() + '(' + this.sourcefile + ':' + this.linenum + ')';
      }

      public String caller() {
         return this.fullname() + "(), line " + this.linenum;
      }

      private String fullname() {
         return this.fullClass + '.' + this.method;
      }
   }
}
