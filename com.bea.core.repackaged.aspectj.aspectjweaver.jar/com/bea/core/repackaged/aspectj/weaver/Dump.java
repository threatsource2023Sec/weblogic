package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHolder;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import com.bea.core.repackaged.aspectj.weaver.tools.Traceable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Dump {
   public static final String DUMP_CONDITION_PROPERTY = "com.bea.core.repackaged.aspectj.weaver.Dump.condition";
   public static final String DUMP_DIRECTORY_PROPERTY = "com.bea.core.repackaged.aspectj.dump.directory";
   private static final String FILENAME_PREFIX = "ajcore";
   private static final String FILENAME_SUFFIX = "txt";
   public static final String UNKNOWN_FILENAME = "Unknown";
   public static final String DUMP_EXCLUDED = "Excluded";
   public static final String NULL_OR_EMPTY = "Empty";
   private static Class exceptionClass;
   private static IMessage.Kind conditionKind;
   private static File directory;
   private String reason;
   private String fileName;
   private PrintStream print;
   private static String[] savedCommandLine;
   private static List savedFullClasspath;
   private static IMessageHolder savedMessageHolder;
   private static String lastDumpFileName;
   private static boolean preserveOnNextReset;
   private static Trace trace;

   public static void preserveOnNextReset() {
      preserveOnNextReset = true;
   }

   public static void reset() {
      if (preserveOnNextReset) {
         preserveOnNextReset = false;
      } else {
         savedMessageHolder = null;
      }
   }

   public static String dump(String reason) {
      String fileName = "Unknown";
      Dump dump = null;

      try {
         dump = new Dump(reason);
         fileName = dump.getFileName();
         dump.dumpDefault();
      } finally {
         if (dump != null) {
            dump.close();
         }

      }

      return fileName;
   }

   public static String dumpWithException(Throwable th) {
      return dumpWithException(savedMessageHolder, th);
   }

   public static String dumpWithException(IMessageHolder messageHolder, Throwable th) {
      if (!getDumpOnException()) {
         return null;
      } else {
         if (trace.isTraceEnabled()) {
            trace.enter("dumpWithException", (Object)null, (Object[])(new Object[]{messageHolder, th}));
         }

         String fileName = "Unknown";
         Dump dump = null;

         try {
            dump = new Dump(th.getClass().getName());
            fileName = dump.getFileName();
            dump.dumpException(messageHolder, th);
         } finally {
            if (dump != null) {
               dump.close();
            }

         }

         if (trace.isTraceEnabled()) {
            trace.exit("dumpWithException", (Object)fileName);
         }

         return fileName;
      }
   }

   public static String dumpOnExit() {
      return dumpOnExit(savedMessageHolder, false);
   }

   public static String dumpOnExit(IMessageHolder messageHolder, boolean reset) {
      if (!getDumpOnException()) {
         return null;
      } else {
         if (trace.isTraceEnabled()) {
            trace.enter("dumpOnExit", (Object)null, (Object)messageHolder);
         }

         String fileName = "Unknown";
         if (!shouldDumpOnExit(messageHolder)) {
            fileName = "Excluded";
         } else {
            Dump dump = null;

            try {
               dump = new Dump(conditionKind.toString());
               fileName = dump.getFileName();
               dump.dumpDefault(messageHolder);
            } finally {
               if (dump != null) {
                  dump.close();
               }

            }
         }

         if (reset) {
            messageHolder.clearMessages();
         }

         if (trace.isTraceEnabled()) {
            trace.exit("dumpOnExit", (Object)fileName);
         }

         return fileName;
      }
   }

   private static boolean shouldDumpOnExit(IMessageHolder messageHolder) {
      if (trace.isTraceEnabled()) {
         trace.enter("shouldDumpOnExit", (Object)null, (Object)messageHolder);
      }

      if (trace.isTraceEnabled()) {
         trace.event("shouldDumpOnExit", (Object)null, (Object)conditionKind);
      }

      boolean result = messageHolder == null || messageHolder.hasAnyMessage(conditionKind, true);
      if (trace.isTraceEnabled()) {
         trace.exit("shouldDumpOnExit", result);
      }

      return result;
   }

   public static void setDumpOnException(boolean b) {
      if (b) {
         exceptionClass = Throwable.class;
      } else {
         exceptionClass = null;
      }

   }

   public static boolean setDumpDirectory(String directoryName) {
      if (trace.isTraceEnabled()) {
         trace.enter("setDumpDirectory", (Object)null, (Object)directoryName);
      }

      boolean success = false;
      File newDirectory = new File(directoryName);
      if (newDirectory.exists()) {
         directory = newDirectory;
         success = true;
      }

      if (trace.isTraceEnabled()) {
         trace.exit("setDumpDirectory", success);
      }

      return success;
   }

   public static boolean getDumpOnException() {
      return exceptionClass != null;
   }

   public static boolean setDumpOnExit(IMessage.Kind condition) {
      if (trace.isTraceEnabled()) {
         trace.event("setDumpOnExit", (Object)null, (Object)condition);
      }

      conditionKind = condition;
      return true;
   }

   public static boolean setDumpOnExit(String condition) {
      Iterator i$ = IMessage.KINDS.iterator();

      IMessage.Kind kind;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         kind = (IMessage.Kind)i$.next();
      } while(!kind.toString().equals(condition));

      return setDumpOnExit(kind);
   }

   public static IMessage.Kind getDumpOnExit() {
      return conditionKind;
   }

   public static String getLastDumpFileName() {
      return lastDumpFileName;
   }

   public static void saveCommandLine(String[] args) {
      savedCommandLine = new String[args.length];
      System.arraycopy(args, 0, savedCommandLine, 0, args.length);
   }

   public static void saveFullClasspath(List list) {
      savedFullClasspath = list;
   }

   public static void saveMessageHolder(IMessageHolder holder) {
      savedMessageHolder = holder;
   }

   private Dump(String reason) {
      if (trace.isTraceEnabled()) {
         trace.enter("<init>", this, (Object)reason);
      }

      this.reason = reason;
      this.openDump();
      this.dumpAspectJProperties();
      this.dumpDumpConfiguration();
      if (trace.isTraceEnabled()) {
         trace.exit("<init>", (Object)this);
      }

   }

   public String getFileName() {
      return this.fileName;
   }

   private void dumpDefault() {
      this.dumpDefault(savedMessageHolder);
   }

   private void dumpDefault(IMessageHolder holder) {
      this.dumpSytemProperties();
      this.dumpCommandLine();
      this.dumpFullClasspath();
      this.dumpCompilerMessages(holder);
   }

   private void dumpException(IMessageHolder messageHolder, Throwable th) {
      this.println((Object)"---- Exception Information ---");
      this.println(th);
      this.dumpDefault(messageHolder);
   }

   private void dumpAspectJProperties() {
      this.println((Object)"---- AspectJ Properties ---");
      this.println((Object)"AspectJ Compiler 1.8.9 built on Monday Mar 14, 2016 at 21:18:16 GMT");
   }

   private void dumpDumpConfiguration() {
      this.println((Object)"---- Dump Properties ---");
      this.println((Object)("Dump file: " + this.fileName));
      this.println((Object)("Dump reason: " + this.reason));
      this.println((Object)("Dump on exception: " + (exceptionClass != null)));
      this.println((Object)("Dump at exit condition: " + conditionKind));
   }

   private void dumpFullClasspath() {
      this.println((Object)"---- Full Classpath ---");
      if (savedFullClasspath != null && savedFullClasspath.size() > 0) {
         Iterator i$ = savedFullClasspath.iterator();

         while(i$.hasNext()) {
            String fileName = (String)i$.next();
            File file = new File(fileName);
            this.println(file);
         }
      } else {
         this.println((Object)"Empty");
      }

   }

   private void dumpSytemProperties() {
      this.println((Object)"---- System Properties ---");
      Properties props = System.getProperties();
      this.println(props);
   }

   private void dumpCommandLine() {
      this.println((Object)"---- Command Line ---");
      this.println((Object[])savedCommandLine);
   }

   private void dumpCompilerMessages(IMessageHolder messageHolder) {
      this.println((Object)"---- Compiler Messages ---");
      if (messageHolder != null) {
         Iterator i = messageHolder.getUnmodifiableListView().iterator();

         while(i.hasNext()) {
            IMessage message = (IMessage)i.next();
            this.println((Object)message.toString());
         }
      } else {
         this.println((Object)"Empty");
      }

   }

   private void openDump() {
      if (this.print == null) {
         Date now = new Date();
         this.fileName = "ajcore." + (new SimpleDateFormat("yyyyMMdd")).format(now) + "." + (new SimpleDateFormat("HHmmss.SSS")).format(now) + "." + "txt";

         try {
            File file = new File(directory, this.fileName);
            this.print = new PrintStream(new FileOutputStream(file), true);
            trace.info("Dumping to " + file.getAbsolutePath());
         } catch (Exception var3) {
            this.print = System.err;
            trace.info("Dumping to stderr");
            this.fileName = "Unknown";
         }

         lastDumpFileName = this.fileName;
      }
   }

   public void close() {
      this.print.close();
   }

   private void println(Object obj) {
      this.print.println(obj);
   }

   private void println(Object[] array) {
      if (array == null) {
         this.println((Object)"Empty");
      } else {
         for(int i = 0; i < array.length; ++i) {
            this.print.println(array[i]);
         }

      }
   }

   private void println(Properties props) {
      Iterator iter = props.keySet().iterator();

      while(iter.hasNext()) {
         String key = (String)iter.next();
         String value = props.getProperty(key);
         this.print.println(key + "=" + value);
      }

   }

   private void println(Throwable th) {
      th.printStackTrace(this.print);
   }

   private void println(File file) {
      this.print.print(file.getAbsolutePath());
      if (!file.exists()) {
         this.println((Object)"(missing)");
      } else if (file.isDirectory()) {
         int count = file.listFiles().length;
         this.println((Object)("(" + count + " entries)"));
      } else {
         this.println((Object)("(" + file.length() + " bytes)"));
      }

   }

   private void println(List list) {
      if (list != null && !list.isEmpty()) {
         Iterator i = list.iterator();

         while(i.hasNext()) {
            Object o = i.next();
            if (o instanceof Exception) {
               this.println((Throwable)((Exception)o));
            } else {
               this.println((Object)o.toString());
            }
         }
      } else {
         this.println((Object)"Empty");
      }

   }

   private static Object formatObj(Object obj) {
      if (obj != null && !(obj instanceof String) && !(obj instanceof Number) && !(obj instanceof Boolean) && !(obj instanceof Exception) && !(obj instanceof Character) && !(obj instanceof Class) && !(obj instanceof File) && !(obj instanceof StringBuffer) && !(obj instanceof URL)) {
         try {
            if (obj instanceof Traceable) {
               Traceable t = (Traceable)obj;
               return t.toTraceString();
            } else {
               return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
            }
         } catch (Exception var2) {
            return obj.getClass().getName() + "@FFFFFFFF";
         }
      } else {
         return obj;
      }
   }

   static {
      conditionKind = IMessage.ABORT;
      directory = new File(".");
      lastDumpFileName = "Unknown";
      preserveOnNextReset = false;
      trace = TraceFactory.getTraceFactory().getTrace(Dump.class);
      String exceptionName = System.getProperty("com.bea.core.repackaged.aspectj.weaver.Dump.exception", "true");
      if (!exceptionName.equals("false")) {
         setDumpOnException(true);
      }

      String conditionName = System.getProperty("com.bea.core.repackaged.aspectj.weaver.Dump.condition");
      if (conditionName != null) {
         setDumpOnExit(conditionName);
      }

      String directoryName = System.getProperty("com.bea.core.repackaged.aspectj.dump.directory");
      if (directoryName != null) {
         setDumpDirectory(directoryName);
      }

   }

   public interface IVisitor {
      void visitObject(Object var1);

      void visitList(List var1);
   }

   public interface INode {
      void accept(IVisitor var1);
   }
}
