package com.bea.core.repackaged.aspectj.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.security.PrivilegedActionException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class LangUtil {
   public static final String EOL;
   private static double vmVersion;

   public static boolean is13VMOrGreater() {
      return 1.3 <= vmVersion;
   }

   public static boolean is14VMOrGreater() {
      return 1.4 <= vmVersion;
   }

   public static boolean is15VMOrGreater() {
      return 1.5 <= vmVersion;
   }

   public static boolean is16VMOrGreater() {
      return 1.6 <= vmVersion;
   }

   public static boolean is17VMOrGreater() {
      return 1.7 <= vmVersion;
   }

   public static boolean is18VMOrGreater() {
      return 1.8 <= vmVersion;
   }

   public static final void throwIaxIfNull(Object var0, String var1) {
      if (null == var0) {
         String var2 = "null " + (null == var1 ? "input" : var1);
         throw new IllegalArgumentException(var2);
      }
   }

   public static final void throwIaxIfNotAssignable(Object[] var0, Class var1, String var2) {
      throwIaxIfNull(var0, var2);
      String var3 = null == var2 ? "input" : var2;

      for(int var4 = 0; var4 < var0.length; ++var4) {
         if (null == var0[var4]) {
            String var7 = " null " + var3 + "[" + var4 + "]";
            throw new IllegalArgumentException(var7);
         }

         if (null != var1) {
            Class var5 = var0[var4].getClass();
            if (!var1.isAssignableFrom(var5)) {
               String var6 = var3 + " not assignable to " + var1.getName();
               throw new IllegalArgumentException(var6);
            }
         }
      }

   }

   public static final void throwIaxIfNotAssignable(Object var0, Class var1, String var2) {
      throwIaxIfNull(var0, var2);
      if (null != var1) {
         Class var3 = var0.getClass();
         if (!var1.isAssignableFrom(var3)) {
            String var4 = var2 + " not assignable to " + var1.getName();
            throw new IllegalArgumentException(var4);
         }
      }

   }

   public static final void throwIaxIfFalse(boolean var0, String var1) {
      if (!var0) {
         throw new IllegalArgumentException(var1);
      }
   }

   public static boolean isEmpty(String var0) {
      return null == var0 || 0 == var0.length();
   }

   public static boolean isEmpty(Object[] var0) {
      return null == var0 || 0 == var0.length;
   }

   public static boolean isEmpty(byte[] var0) {
      return null == var0 || 0 == var0.length;
   }

   public static boolean isEmpty(Collection var0) {
      return null == var0 || 0 == var0.size();
   }

   public static boolean isEmpty(Map var0) {
      return null == var0 || 0 == var0.size();
   }

   public static String[] split(String var0) {
      return (String[])((String[])strings(var0).toArray(new String[0]));
   }

   public static List commaSplit(String var0) {
      return anySplit(var0, ",");
   }

   public static String[] splitClasspath(String var0) {
      if (isEmpty(var0)) {
         return new String[0];
      } else {
         StringTokenizer var1 = new StringTokenizer(var0, File.pathSeparator);
         ArrayList var2 = new ArrayList(var1.countTokens());

         while(var1.hasMoreTokens()) {
            String var3 = var1.nextToken();
            if (!isEmpty(var3)) {
               var2.add(var3);
            }
         }

         return (String[])((String[])var2.toArray(new String[0]));
      }
   }

   public static boolean getBoolean(String var0, boolean var1) {
      if (null != var0) {
         try {
            String var2 = System.getProperty(var0);
            if (null != var2) {
               return Boolean.valueOf(var2);
            }
         } catch (Throwable var3) {
         }
      }

      return var1;
   }

   public static List anySplit(String var0, String var1) {
      if (null == var0) {
         return Collections.emptyList();
      } else {
         ArrayList var2 = new ArrayList();
         if (!isEmpty(var1) && -1 != var0.indexOf(var1)) {
            StringTokenizer var3 = new StringTokenizer(var0, var1);

            while(var3.hasMoreTokens()) {
               var2.add(var3.nextToken().trim());
            }
         } else {
            var2.add(var0.trim());
         }

         return var2;
      }
   }

   public static List strings(String var0) {
      if (isEmpty(var0)) {
         return Collections.emptyList();
      } else {
         ArrayList var1 = new ArrayList();
         StringTokenizer var2 = new StringTokenizer(var0);

         while(var2.hasMoreTokens()) {
            var1.add(var2.nextToken());
         }

         return var1;
      }
   }

   public static List safeList(List var0) {
      return null == var0 ? Collections.emptyList() : Collections.unmodifiableList(var0);
   }

   public static String[][] copyStrings(String[][] var0) {
      String[][] var1 = new String[var0.length][];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = new String[var0[var2].length];
         System.arraycopy(var0[var2], 0, var1[var2], 0, var1[var2].length);
      }

      return var1;
   }

   public static String[] extractOptions(String[] var0, String[][] var1) {
      if (!isEmpty((Object[])var0) && !isEmpty((Object[])var1)) {
         BitSet var2 = new BitSet();
         String[] var3 = new String[var0.length];
         int var4 = 0;

         int var5;
         for(var5 = 0; var5 < var0.length; ++var5) {
            boolean var6 = false;

            for(int var7 = 0; !var6 && var7 < var1.length; ++var7) {
               String[] var8 = var1[var7];
               throwIaxIfFalse(!isEmpty((Object[])var8), "options");
               String var9 = var8[0];
               var6 = var9.equals(var0[var5]);
               if (var6) {
                  var2.set(var7);
                  int var10 = var8.length - 1;
                  if (0 < var10) {
                     int var11 = var5 + var10;
                     if (var11 >= var0.length) {
                        String var14 = "expecting " + var10 + " args after ";
                        throw new IllegalArgumentException(var14 + var0[var5]);
                     }

                     for(int var12 = 1; var12 < var8.length; ++var12) {
                        ++var5;
                        var8[var12] = var0[var5];
                     }
                  }
               }
            }

            if (!var6) {
               var3[var4++] = var0[var5];
            }
         }

         for(var5 = 0; var5 < var1.length; ++var5) {
            if (!var2.get(var5)) {
               var1[var5][0] = null;
            }
         }

         if (var4 < var0.length) {
            String[] var13 = new String[var4];
            System.arraycopy(var3, 0, var13, 0, var4);
            var0 = var13;
         }

         return var0;
      } else {
         return var0;
      }
   }

   public static Object[] safeCopy(Object[] var0, Object[] var1) {
      Class var2 = null == var1 ? Object.class : var1.getClass().getComponentType();
      int var3 = null == var0 ? 0 : var0.length;
      int var4 = null == var1 ? 0 : var1.length;
      ArrayList var5 = null;
      int var6;
      if (0 == var3) {
         var6 = 0;
      } else {
         var5 = new ArrayList(var3);

         for(int var7 = 0; var7 < var3; ++var7) {
            if (null != var0[var7] && var2.isAssignableFrom(var0[var7].getClass())) {
               var5.add(var0[var7]);
            }
         }

         var6 = var5.size();
      }

      if (var6 != var4) {
         var1 = (Object[])((Object[])Array.newInstance(var2, var5.size()));
      }

      if (0 < var6) {
         var1 = var5.toArray(var1);
      }

      return var1;
   }

   public static String unqualifiedClassName(Class var0) {
      if (null == var0) {
         return "null";
      } else {
         String var1 = var0.getName();
         int var2 = var1.lastIndexOf(".");
         if (-1 != var2) {
            var1 = var1.substring(1 + var2);
         }

         return var1;
      }
   }

   public static String unqualifiedClassName(Object var0) {
      return unqualifiedClassName(null == var0 ? null : var0.getClass());
   }

   public static String replace(String var0, String var1, String var2) {
      if (!isEmpty(var0) && !isEmpty(var1)) {
         StringBuffer var3 = new StringBuffer();
         int var4 = var1.length();

         int var5;
         int var6;
         for(var5 = 0; -1 != (var6 = var0.indexOf(var1, var5)); var5 = var6 + var4) {
            var3.append(var0.substring(var5, var6));
            if (!isEmpty(var2)) {
               var3.append(var2);
            }
         }

         var3.append(var0.substring(var5));
         return var3.toString();
      } else {
         return var0;
      }
   }

   public static String toSizedString(long var0, int var2) {
      String var3 = "" + var0;
      int var4 = var3.length();
      if (var2 > var4) {
         int var5 = "                                              ".length();
         if (var2 > var5) {
            var2 = var5;
         }

         int var6 = var2 - var4;
         var3 = "                                              ".substring(0, var6) + var3;
      }

      return var3;
   }

   public static String renderExceptionShort(Throwable var0) {
      return null == var0 ? "(Throwable) null" : "(" + unqualifiedClassName((Object)var0) + ") " + var0.getMessage();
   }

   public static String renderException(Throwable var0) {
      return renderException(var0, true);
   }

   public static String renderException(Throwable var0, boolean var1) {
      if (null == var0) {
         return "null throwable";
      } else {
         var0 = unwrapException(var0);
         StringBuffer var2 = stackToString(var0, false);
         if (var1) {
            elideEndingLines(LangUtil.StringChecker.TEST_PACKAGES, var2, 100);
         }

         return var2.toString();
      }
   }

   static void elideEndingLines(StringChecker var0, StringBuffer var1, int var2) {
      if (null != var0 && null != var1 && 0 != var1.length()) {
         LinkedList var3 = new LinkedList();
         StringTokenizer var4 = new StringTokenizer(var1.toString(), "\n\r");

         while(var4.hasMoreTokens()) {
            --var2;
            if (0 >= var2) {
               break;
            }

            var3.add(var4.nextToken());
         }

         var4 = null;
         int var5 = 0;

         while(!var3.isEmpty()) {
            String var6 = (String)var3.getLast();
            if (!var0.acceptString(var6)) {
               break;
            }

            ++var5;
            var3.removeLast();
         }

         if (var5 > 0 || var2 < 1) {
            int var7 = EOL.length();
            int var8 = 0;

            while(!var3.isEmpty()) {
               var8 += var7 + ((String)var3.getFirst()).length();
               var3.removeFirst();
            }

            if (var1.length() > var8) {
               var1.setLength(var8);
               if (var5 > 0) {
                  var1.append("    (... " + var5 + " lines...)");
               }
            }
         }

      }
   }

   public static StringBuffer stackToString(Throwable var0, boolean var1) {
      if (null == var0) {
         return new StringBuffer();
      } else {
         StringWriter var2 = new StringWriter();
         PrintWriter var3 = new PrintWriter(var2);
         if (!var1) {
            var3.println(var0.getMessage());
         }

         var0.printStackTrace(var3);

         try {
            var2.close();
         } catch (IOException var5) {
         }

         return var2.getBuffer();
      }
   }

   public static Throwable unwrapException(Throwable var0) {
      Object var1 = var0;

      for(Object var2 = null; var1 != null; var2 = null) {
         if (var1 instanceof InvocationTargetException) {
            var2 = ((InvocationTargetException)var1).getTargetException();
         } else if (var1 instanceof ClassNotFoundException) {
            var2 = ((ClassNotFoundException)var1).getException();
         } else if (var1 instanceof ExceptionInInitializerError) {
            var2 = ((ExceptionInInitializerError)var1).getException();
         } else if (var1 instanceof PrivilegedActionException) {
            var2 = ((PrivilegedActionException)var1).getException();
         } else if (var1 instanceof SQLException) {
            var2 = ((SQLException)var1).getNextException();
         }

         if (null == var2) {
            break;
         }

         var1 = var2;
      }

      return (Throwable)var1;
   }

   public static List arrayAsList(Object[] var0) {
      if (null != var0 && 1 <= var0.length) {
         ArrayList var1 = new ArrayList();
         var1.addAll(Arrays.asList(var0));
         return var1;
      } else {
         return Collections.emptyList();
      }
   }

   public static String makeClasspath(String var0, String var1, String var2, String var3) {
      StringBuffer var4 = new StringBuffer();
      addIfNotEmpty(var0, var4, File.pathSeparator);
      addIfNotEmpty(var1, var4, File.pathSeparator);
      if (!addIfNotEmpty(var2, var4, File.pathSeparator)) {
         addIfNotEmpty(var3, var4, File.pathSeparator);
      }

      return var4.toString();
   }

   private static boolean addIfNotEmpty(String var0, StringBuffer var1, String var2) {
      if (!isEmpty(var0) && null != var1) {
         var1.append(var0);
         if (!isEmpty(var2)) {
            var1.append(var2);
         }

         return true;
      } else {
         return false;
      }
   }

   public static ProcessController makeProcess(ProcessController var0, String var1, String var2, String[] var3) {
      File var4 = getJavaExecutable();
      ArrayList var5 = new ArrayList();
      var5.add(var4.getAbsolutePath());
      var5.add("-classpath");
      var5.add(var1);
      var5.add(var2);
      if (!isEmpty((Object[])var3)) {
         var5.addAll(Arrays.asList(var3));
      }

      String[] var6 = (String[])((String[])var5.toArray(new String[0]));
      if (null == var0) {
         var0 = new ProcessController();
      }

      var0.init(var6, var2);
      return var0;
   }

   public static File getJavaExecutable() {
      String var0 = null;
      File var1 = null;

      try {
         var0 = System.getProperty("java.home");
      } catch (Throwable var5) {
      }

      if (null != var0) {
         File var2 = new File(var0, "bin");
         if (var2.isDirectory() && var2.canRead()) {
            String[] var3 = new String[]{"java", "java.exe"};

            for(int var4 = 0; var4 < var3.length; ++var4) {
               var1 = new File(var2, var3[var4]);
               if (var1.canRead()) {
                  break;
               }
            }
         }
      }

      return var1;
   }

   public static boolean sleepUntil(long var0) {
      if (var0 == 0L) {
         return true;
      } else if (var0 < 0L) {
         throw new IllegalArgumentException("negative: " + var0);
      } else {
         long var2 = System.currentTimeMillis();

         for(int var4 = 0; var4 < 100 && var2 < var0; ++var4) {
            try {
               Thread.sleep(var0 - var2);
            } catch (InterruptedException var6) {
            }

            var2 = System.currentTimeMillis();
         }

         return var2 >= var0;
      }
   }

   static {
      StringWriter var0 = new StringWriter();
      PrintWriter var1 = new PrintWriter(var0);
      var1.println("");
      String var2 = "\n";

      try {
         var0.close();
         StringBuffer var3 = var0.getBuffer();
         if (var3 != null) {
            var2 = var0.toString();
         }
      } catch (Throwable var4) {
      }

      EOL = var2;

      try {
         String var7 = System.getProperty("java.version");
         if (var7 == null) {
            var7 = System.getProperty("java.runtime.version");
         }

         if (var7 == null) {
            var7 = System.getProperty("java.vm.version");
         }

         if (var7 == null) {
            (new RuntimeException("System properties appear damaged, cannot find: java.version/java.runtime.version/java.vm.version")).printStackTrace(System.err);
            vmVersion = 1.5;
         } else {
            try {
               String var8 = var7.substring(0, 3);
               Double var9 = new Double(Double.parseDouble(var8));
               vmVersion = var9;
            } catch (Exception var5) {
               if (!var7.startsWith("1.")) {
                  vmVersion = 1.91;
               } else {
                  vmVersion = 1.41;
               }
            }
         }
      } catch (Throwable var6) {
         (new RuntimeException("System properties appear damaged, cannot find: java.version/java.runtime.version/java.vm.version", var6)).printStackTrace(System.err);
         vmVersion = 1.5;
      }

   }

   public static class ProcessController {
      private String[] command;
      private String[] envp;
      private String label;
      private boolean init;
      private boolean started;
      private boolean completed;
      private boolean userStopped;
      private Process process;
      private FileUtil.Pipe errStream;
      private FileUtil.Pipe outStream;
      private FileUtil.Pipe inStream;
      private ByteArrayOutputStream errSnoop;
      private ByteArrayOutputStream outSnoop;
      private int result;
      private Thrown thrown;

      public final void reinit() {
         if (!this.init) {
            throw new IllegalStateException("must init(..) before reinit()");
         } else if (this.started && !this.completed) {
            throw new IllegalStateException("not completed - do stop()");
         } else {
            this.started = false;
            this.completed = false;
            this.result = Integer.MIN_VALUE;
            this.thrown = null;
            this.process = null;
            this.errStream = null;
            this.outStream = null;
            this.inStream = null;
         }
      }

      public final void init(String classpath, String mainClass, String[] args) {
         this.init(LangUtil.getJavaExecutable(), classpath, mainClass, args);
      }

      public final void init(File java, String classpath, String mainClass, String[] args) {
         LangUtil.throwIaxIfNull(java, "java");
         LangUtil.throwIaxIfNull(mainClass, "mainClass");
         LangUtil.throwIaxIfNull(args, "args");
         ArrayList cmd = new ArrayList();
         cmd.add(java.getAbsolutePath());
         cmd.add("-classpath");
         cmd.add(classpath);
         cmd.add(mainClass);
         if (!LangUtil.isEmpty((Object[])args)) {
            cmd.addAll(Arrays.asList(args));
         }

         this.init((String[])((String[])cmd.toArray(new String[0])), mainClass);
      }

      public final void init(String[] command, String label) {
         this.command = (String[])((String[])LangUtil.safeCopy(command, new String[0]));
         if (1 > this.command.length) {
            throw new IllegalArgumentException("empty command");
         } else {
            this.label = LangUtil.isEmpty(label) ? command[0] : label;
            this.init = true;
            this.reinit();
         }
      }

      public final void setEnvp(String[] envp) {
         this.envp = (String[])((String[])LangUtil.safeCopy(envp, new String[0]));
         if (1 > this.envp.length) {
            throw new IllegalArgumentException("empty envp");
         }
      }

      public final void setErrSnoop(ByteArrayOutputStream snoop) {
         this.errSnoop = snoop;
         if (null != this.errStream) {
            this.errStream.setSnoop(this.errSnoop);
         }

      }

      public final void setOutSnoop(ByteArrayOutputStream snoop) {
         this.outSnoop = snoop;
         if (null != this.outStream) {
            this.outStream.setSnoop(this.outSnoop);
         }

      }

      public final Thread start() {
         if (!this.init) {
            throw new IllegalStateException("not initialized");
         } else {
            synchronized(this) {
               if (this.started) {
                  throw new IllegalStateException("already started");
               }

               this.started = true;
            }

            try {
               this.process = Runtime.getRuntime().exec(this.command);
            } catch (IOException var3) {
               this.stop(var3, Integer.MIN_VALUE);
               return null;
            }

            this.errStream = new FileUtil.Pipe(this.process.getErrorStream(), System.err);
            if (null != this.errSnoop) {
               this.errStream.setSnoop(this.errSnoop);
            }

            this.outStream = new FileUtil.Pipe(this.process.getInputStream(), System.out);
            if (null != this.outSnoop) {
               this.outStream.setSnoop(this.outSnoop);
            }

            this.inStream = new FileUtil.Pipe(System.in, this.process.getOutputStream());
            Runnable processRunner = new Runnable() {
               public void run() {
                  Throwable thrown = null;
                  int result = Integer.MIN_VALUE;

                  try {
                     (new Thread(ProcessController.this.errStream)).start();
                     (new Thread(ProcessController.this.outStream)).start();
                     (new Thread(ProcessController.this.inStream)).start();
                     ProcessController.this.process.waitFor();
                     result = ProcessController.this.process.exitValue();
                  } catch (Throwable var7) {
                     thrown = var7;
                  } finally {
                     ProcessController.this.stop(thrown, result);
                  }

               }
            };
            Thread result = new Thread(processRunner, this.label);
            result.start();
            return result;
         }
      }

      public final synchronized void stop() {
         if (!this.completed) {
            this.userStopped = true;
            this.stop((Throwable)null, Integer.MIN_VALUE);
         }
      }

      public final String[] getCommand() {
         String[] toCopy = this.command;
         if (LangUtil.isEmpty((Object[])toCopy)) {
            return new String[0];
         } else {
            String[] result = new String[toCopy.length];
            System.arraycopy(toCopy, 0, result, 0, result.length);
            return result;
         }
      }

      public final boolean completed() {
         return this.completed;
      }

      public final boolean started() {
         return this.started;
      }

      public final boolean userStopped() {
         return this.userStopped;
      }

      public final Thrown getThrown() {
         return this.makeThrown((Throwable)null);
      }

      public final int getResult() {
         return this.result;
      }

      protected void doCompleting(Thrown thrown, int result) {
      }

      private final synchronized void stop(Throwable thrown, int result) {
         if (this.completed) {
            throw new IllegalStateException("already completed");
         } else if (null != this.thrown) {
            throw new IllegalStateException("already set thrown: " + thrown);
         } else {
            this.thrown = this.makeThrown(thrown);
            if (null != this.process) {
               this.process.destroy();
            }

            if (null != this.inStream) {
               this.inStream.halt(false, true);
               this.inStream = null;
            }

            if (null != this.outStream) {
               this.outStream.halt(true, true);
               this.outStream = null;
            }

            if (null != this.errStream) {
               this.errStream.halt(true, true);
               this.errStream = null;
            }

            if (Integer.MIN_VALUE != result) {
               this.result = result;
            }

            this.completed = true;
            this.doCompleting(this.thrown, result);
         }
      }

      private final synchronized Thrown makeThrown(Throwable processThrown) {
         return null != this.thrown ? this.thrown : new Thrown(processThrown, null == this.outStream ? null : this.outStream.getThrown(), null == this.errStream ? null : this.errStream.getThrown(), null == this.inStream ? null : this.inStream.getThrown());
      }

      public static class Thrown {
         public final Throwable fromProcess;
         public final Throwable fromErrPipe;
         public final Throwable fromOutPipe;
         public final Throwable fromInPipe;
         public final boolean thrown;

         private Thrown(Throwable fromProcess, Throwable fromOutPipe, Throwable fromErrPipe, Throwable fromInPipe) {
            this.fromProcess = fromProcess;
            this.fromErrPipe = fromErrPipe;
            this.fromOutPipe = fromOutPipe;
            this.fromInPipe = fromInPipe;
            this.thrown = null != fromProcess || null != fromInPipe || null != fromOutPipe || null != fromErrPipe;
         }

         public String toString() {
            StringBuffer sb = new StringBuffer();
            this.append(sb, this.fromProcess, "process");
            this.append(sb, this.fromOutPipe, " stdout");
            this.append(sb, this.fromErrPipe, " stderr");
            this.append(sb, this.fromInPipe, "  stdin");
            return 0 == sb.length() ? "Thrown (none)" : sb.toString();
         }

         private void append(StringBuffer sb, Throwable thrown, String label) {
            if (null != thrown) {
               sb.append("from " + label + ": ");
               sb.append(LangUtil.renderExceptionShort(thrown));
               sb.append(LangUtil.EOL);
            }

         }

         // $FF: synthetic method
         Thrown(Throwable x0, Throwable x1, Throwable x2, Throwable x3, Object x4) {
            this(x0, x1, x2, x3);
         }
      }
   }

   public static class StringChecker {
      static StringChecker TEST_PACKAGES = new StringChecker(new String[]{"com.bea.core.repackaged.aspectj.testing", "org.eclipse.jdt.internal.junit", "junit.framework.", "org.apache.tools.ant.taskdefs.optional.junit.JUnitTestRunner"});
      String[] infixes;

      StringChecker(String[] infixes) {
         this.infixes = infixes;
      }

      public boolean acceptString(String input) {
         boolean result = false;
         if (!LangUtil.isEmpty(input)) {
            for(int i = 0; !result && i < this.infixes.length; ++i) {
               result = -1 != input.indexOf(this.infixes[i]);
            }
         }

         return result;
      }
   }
}
