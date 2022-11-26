package monfox.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import monfox.toolkit.snmp.SnmpException;

public class SimpleLogger extends Logger {
   static String a = a("WwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWwNXlWw");
   public static final int LEVEL_OFF = 10;
   public static final int LEVEL_ERROR = 7;
   public static final int LEVEL_WARN = 6;
   public static final int LEVEL_INFO = 5;
   public static final int LEVEL_CONFIG = 4;
   public static final int LEVEL_COMMS = 3;
   public static final int LEVEL_DEBUG = 2;
   public static final int LEVEL_DETAILED = 1;
   public static final int LEVEL_ALL = 0;
   public static final int LEVEL_UNSPECIFIED = -1;
   private int b = -1;
   private String c = null;
   private String d = null;
   private String e = null;
   private Provider f;

   SimpleLogger(String var1, Provider var2) {
      this.e = var1;
      this.f = var2;
      this.b = -1;
   }

   public void setLevel(int var1) {
      this.b = var1;
   }

   public int getLevel() {
      return this.b;
   }

   public boolean isErrorEnabled() {
      if (this.b == -1) {
         return this.f.isActive() && this.f.isErrorEnabled();
      } else {
         return this.f.isActive() && this.b <= 7;
      }
   }

   public boolean isWarnEnabled() {
      if (this.b == -1) {
         return this.f.isActive() && this.f.isWarnEnabled();
      } else {
         return this.f.isActive() && this.b <= 6;
      }
   }

   public boolean isInfoEnabled() {
      if (this.b == -1) {
         return this.f.isActive() && this.f.isInfoEnabled();
      } else {
         return this.f.isActive() && this.b <= 5;
      }
   }

   public boolean isConfigEnabled() {
      if (this.b == -1) {
         return this.f.isActive() && this.f.isConfigEnabled();
      } else {
         return this.f.isActive() && this.b <= 4;
      }
   }

   public boolean isCommsEnabled() {
      if (this.b == -1) {
         return this.f.isActive() && this.f.isCommsEnabled();
      } else {
         return this.f.isActive() && this.b <= 3;
      }
   }

   public boolean isDebugEnabled() {
      if (this.b == -1) {
         return this.f.isActive() && this.f.isDebugEnabled();
      } else {
         return this.f.isActive() && this.b <= 2;
      }
   }

   public boolean isDetailedEnabled() {
      if (this.b == -1) {
         return this.f.isActive() && this.f.isDetailedEnabled();
      } else {
         return this.f.isActive() && this.b <= 1;
      }
   }

   public boolean isEnabled() {
      if (this.b == -1) {
         return this.f.isActive() && this.f.isEnabled();
      } else {
         return this.f.isActive() && this.b != 10;
      }
   }

   public void info(Object var1) {
      if (this.isInfoEnabled()) {
         this.f.println(this.a(a("3\u0014%:a"), var1, (Throwable)null));
      }

   }

   public void info(Object var1, Throwable var2) {
      if (this.isInfoEnabled()) {
         this.f.println(this.a(a("3\u0014%:a"), var1, var2));
      }

   }

   public void debug(Object var1) {
      if (this.isDebugEnabled()) {
         this.f.println(this.a(a(">\u001f! \u0006"), var1, (Throwable)null));
      }

   }

   public void debug(Object var1, Throwable var2) {
      if (this.isDebugEnabled()) {
         this.f.println(this.a(a(">\u001f! \u0006"), var1, var2));
      }

   }

   public void warn(Object var1) {
      if (this.isWarnEnabled()) {
         this.f.println(this.a(a("-\u001b1;a"), var1, (Throwable)null));
      }

   }

   public void warn(Object var1, Throwable var2) {
      if (this.isWarnEnabled()) {
         this.f.println(this.a(a("-\u001b1;a"), var1, var2));
      }

   }

   public void error(Object var1) {
      if (this.isErrorEnabled()) {
         this.f.println(this.a(a("?\b1:\u0013"), var1, (Throwable)null));
      }

   }

   public void error(Object var1, Throwable var2) {
      if (this.isErrorEnabled()) {
         this.f.println(this.a(a("?\b1:\u0013"), var1, var2));
      }

   }

   public void config(Object var1) {
      if (this.isConfigEnabled()) {
         this.f.println(this.a(a("9\u0015-3\b="), var1, (Throwable)null));
      }

   }

   public void config(Object var1, Throwable var2) {
      if (this.isConfigEnabled()) {
         this.f.println(this.a(a("9\u0015-3\b="), var1, var2));
      }

   }

   public void comms(Object var1) {
      if (this.isCommsEnabled()) {
         this.f.println(this.a(a("9\u0015.8\u0012"), var1, (Throwable)null));
      }

   }

   public void comms(Object var1, Throwable var2) {
      if (this.isCommsEnabled()) {
         this.f.println(this.a(a("9\u0015.8\u0012"), var1, var2));
      }

   }

   public void detailed(Object var1) {
      if (this.isDetailedEnabled()) {
         this.f.println(this.a(a(">\u001f74\b6\u001f'"), var1, (Throwable)null));
      }

   }

   public void detailed(Object var1, Throwable var2) {
      if (this.isDetailedEnabled()) {
         this.f.println(this.a(a(">\u001f74\b6\u001f'"), var1, var2));
      }

   }

   public void setGroup(String var1) {
      this.d = var1;
   }

   public void setApi(String var1) {
      this.c = var1;
   }

   public String getGroup() {
      return this.d;
   }

   public String getApi() {
      return this.c;
   }

   private String a(String var1, Object var2, Throwable var3) {
      StringBuffer var4 = new StringBuffer();
      if (this.getApi() != null && this.f.isApiLogged()) {
         var4.append("[").append(this.getApi()).append("]");
      }

      if (this.getGroup() != null && this.f.isGroupLogged()) {
         var4.append("[").append(this.getGroup()).append("]");
      }

      if (this.f.isCategoryLogged()) {
         var4.append("[").append(this.e).append("]");
      }

      if (this.f.isTypeLogged()) {
         var4.append("[").append(var1).append("]");
      }

      if (this.f.isDateLogged()) {
         var4.append("[").append(new Date()).append("]");
      }

      if (this.f.isAppTimeLogged()) {
         var4.append("[").append(this.f.getAppTime()).append("]");
      }

      label34: {
         var4.append(a("@z"));
         if (var2 == null) {
            var4.append(a("\u0014/\u000f\u0019"));
            if (Logger.j == 0) {
               break label34;
            }
         }

         var4.append(var2.toString());
      }

      if (var3 != null) {
         var4.append(a("Zr&-\u0002?\n7<\u000e4`")).append(var3).append(")");
         StringWriter var5 = new StringWriter();
         PrintWriter var6 = new PrintWriter(var5);
         var3.printStackTrace(var6);
         var4.append(var5.getBuffer());
      }

      return var4.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 122;
               break;
            case 1:
               var10003 = 90;
               break;
            case 2:
               var10003 = 99;
               break;
            case 3:
               var10003 = 117;
               break;
            default:
               var10003 = 65;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class Provider implements Logger.Provider {
      private Socket a;
      private LogServer b;
      private Thread c;
      private boolean d;
      private Object e;
      private PrintStream f;
      private String g;
      private long h;
      private long i;
      private boolean j;
      private boolean k;
      private boolean l;
      private boolean m;
      private boolean n;
      private boolean o;
      private Hashtable p;
      private int q;
      private a r;
      private long s;

      public Provider(String var1) {
         this();
         if (var1 != null && !var1.trim().equals("")) {
            if (b(";\u0002V\u0019D\u0005UJ\u0018U").equals(var1)) {
               this.f = System.out;
            } else if (b(";\u0002V\u0019D\u0005U@\u001fS").equals(var1)) {
               this.f = System.err;
            } else {
               String var2 = Logger.resolveVariables(var1);

               try {
                  FileOutputStream var3 = new FileOutputStream(var2);
                  this.f = new PrintStream(var3);
                  this.g = var2;
                  this.println(SimpleLogger.a);
                  this.println(b("EV\u00056") + Logger.getApplicationName() + b("5A\u0005") + new Date());
                  this.println(SimpleLogger.a);
               } catch (IOException var4) {
                  System.err.println(b("bq\u0005MM\u0007\u001cB\bSH\u001eW\u001fN\u001aA\u0005\u000e@\u0006\u0015J\u0019\u0001\u0007\u000b@\u0003\u0001\u0007\u000eQ\u000bH\u0004\u001e\u001fM") + var1);
               }

            }
         } else {
            this.f = null;
         }
      }

      public Provider(String var1, int var2) {
         this(var1);
         if (var2 > 0) {
            try {
               this.startListening(var2);
            } catch (Exception var4) {
               System.err.println(b("\u0004\u0014B\nD\u001a[@\u001fS\u0007\t\u001fMB\t\u0015K\u0002UH\u0014U\bOH\bJ\u000eJ\r\u000f"));
            }
         }

      }

      public Provider(PrintStream var1) {
         this();
         this.f = var1;
      }

      private Provider() {
         this.a = null;
         this.b = null;
         this.d = false;
         this.e = new Object();
         this.f = System.err;
         this.g = null;
         this.h = 0L;
         this.i = -1L;
         this.j = true;
         this.k = true;
         this.l = true;
         this.m = true;
         this.n = false;
         this.o = false;
         this.p = new Hashtable();
         this.q = 0;
         this.r = new a();
         this.s = System.currentTimeMillis();
         this.r.setProperty(b("\t\u000bU\u0001"), Logger.getApplicationName());
         this.r.setProperty(b("\t\u000bU\u0001O\t\u0016@"), Logger.getApplicationName());
         this.r.setProperty(b("\t\u000bU\u0003@\u0005\u001e"), Logger.getApplicationName());
      }

      public void enableAll() {
         this.q = 0;
      }

      public void disableAll() {
         this.q = 10;
      }

      public void setLevel(int var1) {
         this.q = var1;
      }

      public boolean isErrorEnabled() {
         return this.q <= 7;
      }

      public boolean isWarnEnabled() {
         return this.q <= 6;
      }

      public boolean isInfoEnabled() {
         return this.q <= 5;
      }

      public boolean isConfigEnabled() {
         return this.q <= 4;
      }

      public boolean isCommsEnabled() {
         return this.q <= 3;
      }

      public boolean isDebugEnabled() {
         return this.q <= 2;
      }

      public boolean isDetailedEnabled() {
         return this.q <= 1;
      }

      public boolean isEnabled() {
         return this.q != 10;
      }

      public void setCategoryLogged(boolean var1) {
         this.j = var1;
      }

      public void setApiLogged(boolean var1) {
         this.k = var1;
      }

      public void setGroupLogged(boolean var1) {
         this.l = var1;
      }

      public void setTypeLogged(boolean var1) {
         this.m = var1;
      }

      public void setDateLogged(boolean var1) {
         this.n = var1;
      }

      public void setAppTimeLogged(boolean var1) {
         this.o = var1;
      }

      public boolean isCategoryLogged() {
         return this.j;
      }

      public boolean isApiLogged() {
         return this.k;
      }

      public boolean isGroupLogged() {
         return this.l;
      }

      public boolean isTypeLogged() {
         return this.m;
      }

      public boolean isDateLogged() {
         return this.n;
      }

      public boolean isAppTimeLogged() {
         return this.o;
      }

      public synchronized Logger getInstance(String var1) {
         Object var2 = (Logger)this.p.get(var1);
         if (var2 == null) {
            var2 = new SimpleLogger(var1, this);
            this.p.put(var1, var2);
         }

         return (Logger)var2;
      }

      public double getAppTime() {
         long var1 = System.currentTimeMillis();
         long var3 = var1 - this.s;
         return (double)var3 / 1000.0;
      }

      public int getLevel() {
         return this.q;
      }

      public void setRolloverSize(long var1) {
         if (var1 < 1000L) {
            var1 = 1000L;
         }

         this.i = var1;
      }

      public long getRolloverSize() {
         return this.i;
      }

      public void startListening(int var1) throws IOException {
         LogServer var2 = this.b;
         if (var2 != null) {
            var2.shutdown();
         }

         this.b = new LogServer(var1);
      }

      public void stopListening() {
         LogServer var1 = this.b;
         if (var1 != null) {
            var1.shutdown();
            this.b = null;
         }

      }

      private void a(String var1, boolean var2) {
         Socket var3 = this.a;
         if (var3 != null) {
            synchronized(var3) {
               try {
                  var3.getOutputStream().write(var1.getBytes());
                  if (var2) {
                     var3.getOutputStream().write("\n".getBytes());
                  }
               } catch (Exception var9) {
                  try {
                     var3.close();
                  } catch (Exception var8) {
                  }

                  this.a = null;
               }
            }
         }

      }

      public void print(String var1) {
         if (this.f != null) {
            synchronized(this.e) {
               this.a(var1);
               this.f.print(var1);
            }
         }

         this.a(var1, false);
      }

      public void println(String var1) {
         if (this.f != null) {
            synchronized(this.e) {
               this.a(var1);
               this.f.println(var1);
            }
         }

         this.a(var1, true);
      }

      private synchronized void a(String var1) {
         if (this.i > 0L && this.g != null) {
            this.h += (long)var1.length();
            if (this.h > this.i) {
               try {
                  this.f.close();
                  File var2 = new File(this.g);
                  File var3 = new File(this.g + b("F\u0017D\u001eU"));
                  if (var3.exists()) {
                     var3.delete();
                  }

                  var2.renameTo(var3);
               } catch (Exception var5) {
                  System.err.println(b("\u0004\u0014B\nH\u0006\u001c\u0005\bS\u001a\u0014WW\u0001\u000b\u001aK\u0003N\u001c[F\u001fD\t\u000f@MS\u0007\u0017I\u0002W\r\t\u0005\u000bH\u0004\u001e\u0005J") + this.g + b("F\u0017D\u001eUOU\u0005ED\u0010A\u0005") + var5 + ")");
               }

               try {
                  FileOutputStream var6 = new FileOutputStream(this.g);
                  this.f = new PrintStream(var6);
                  this.h = 0L;
                  StringBuffer var7 = new StringBuffer();
                  var7.append(SimpleLogger.a);
                  var7.append(b("bV\bMz")).append(Logger.getApplicationName()).append(b("5A\u0005")).append(new Date()).append("\n");
                  var7.append(SimpleLogger.a);
                  this.f.println(var7.toString());
                  this.h = (long)var7.toString().length();
               } catch (Exception var4) {
                  System.err.println(b("\u0004\u0014B\nH\u0006\u001c\u0005\bS\u001a\u0014WW\u0001\u000b\u001aK\u0003N\u001c[F\u001fD\t\u000f@MM\u0007\u001c\u0005\u000bH\u0004\u001e\u0005J") + this.g + b("OU\u0005ED\u0010A\u0005") + var4 + ")");
               }
            }

         }
      }

      public boolean isActive() {
         return this.f != null || this.a != null;
      }

      public void startLoggingMemory(final int var1) {
         Thread var2 = this.c;
         if (var2 != null) {
            var2.interrupt();
         }

         this.d = true;
         var2 = new Thread() {
            public void run() {
               int var10 = Logger.j;
               Runtime var1x = Runtime.getRuntime();

               try {
                  long var2 = 0L;

                  while(Provider.this.d) {
                     var1x.gc();
                     long var4 = var1x.totalMemory();
                     long var6 = var1x.freeMemory();
                     long var8 = var4 - var6;
                     if (var10 != 0) {
                        break;
                     }

                     if (var8 > var2) {
                        var2 = var8;
                     }

                     Provider.this.println(a("\u0002\u0017}Q%\u000b\u0003e&J") + var4 / 1024L + a("\u0012zLs\u001e86\u0014<") + var8 / 1024L + a("\u0012zMo\u000f=v\u0018") + var6 / 1024L + a("\u0012z^n\u000f<v\u0018") + var2 / 1024L + a("y\u0011\u0018l\u000f81"));
                     Thread.sleep((long)var1);
                     if (var10 != 0) {
                        break;
                     }
                  }
               } catch (Exception var11) {
               }

            }

            private static String a(String var0) {
               char[] var1x = var0.toCharArray();
               int var2 = var1x.length;

               for(int var3 = 0; var3 < var2; ++var3) {
                  char var10002 = var1x[var3];
                  byte var10003;
                  switch (var3 % 5) {
                     case 0:
                        var10003 = 89;
                        break;
                     case 1:
                        var10003 = 90;
                        break;
                     case 2:
                        var10003 = 56;
                        break;
                     case 3:
                        var10003 = 28;
                        break;
                     default:
                        var10003 = 106;
                  }

                  var1x[var3] = (char)(var10002 ^ var10003);
               }

               return new String(var1x);
            }
         };
         var2.setDaemon(true);
         var2.start();
      }

      public void stopLoggingMemory() {
         Thread var1 = this.c;
         this.d = false;
         if (var1 != null) {
            var1.interrupt();
         }

      }

      private static String b(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 104;
                  break;
               case 1:
                  var10003 = 123;
                  break;
               case 2:
                  var10003 = 37;
                  break;
               case 3:
                  var10003 = 109;
                  break;
               default:
                  var10003 = 33;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }

      class SocketReader extends Thread {
         Socket a;

         SocketReader(Socket var2) {
            this.a = var2;
         }

         public void run() {
            int var10 = Logger.j;

            try {
               StringBuffer var1 = new StringBuffer();

               while(true) {
                  int var2;
                  do {
                     var2 = this.a.getInputStream().read();
                     if (var2 < 0) {
                        if (var10 == 0) {
                           throw new IOException(a("kfF\u001d\fu2W\u0014\u0002kwP"));
                        }
                     } else if (var2 >= 32 && var2 <= 126) {
                        break;
                     }

                     StringTokenizer var3 = new StringTokenizer(var1.toString(), a("8/\u000eTg\u0011\u001f"), false);
                     int var4 = var3.countTokens();
                     if (var4 > 0) {
                        label194: {
                           String var5 = var3.nextToken();
                           if (var5.equalsIgnoreCase(a("|w@\u0019\u0004twP")) || var5.equalsIgnoreCase("1")) {
                              Provider.this.println(a("T]s?(J(\u0014\u001b\u0005y|S\u0011\u0003\u007f2X\u001d\u001b}~\u0014ES8vQ\f\fq~Q\u001c"));
                              Provider.this.setLevel(1);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("|wV\r\n")) || var5.equalsIgnoreCase("2")) {
                              Provider.this.println(a("T]s?(J(\u0014\u001b\u0005y|S\u0011\u0003\u007f2X\u001d\u001b}~\u0014ES8vQ\u001a\u0018\u007f"));
                              Provider.this.setLevel(2);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("{}Y\u0015\u001e")) || var5.equalsIgnoreCase("3")) {
                              Provider.this.println(a("T]s?(J(\u0014\u001b\u0005y|S\u0011\u0003\u007f2X\u001d\u001b}~\u0014ES8q[\u0015\u0000k"));
                              Provider.this.setLevel(3);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("{}Z\u001e\u0004\u007f")) || var5.equalsIgnoreCase("4")) {
                              Provider.this.println(a("T]s?(J(\u0014\u001b\u0005y|S\u0011\u0003\u007f2X\u001d\u001b}~\u0014ES8q[\u0016\u000bqu"));
                              Provider.this.setLevel(4);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("q|R\u0017")) || var5.equalsIgnoreCase("5")) {
                              Provider.this.println(a("T]s?(J(\u0014\u001b\u0005y|S\u0011\u0003\u007f2X\u001d\u001b}~\u0014ES8{Z\u001e\u0002"));
                              Provider.this.setLevel(5);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("osF\u0016")) || var5.equalsIgnoreCase("6")) {
                              Provider.this.println(a("T]s?(J(\u0014\u001b\u0005y|S\u0011\u0003\u007f2X\u001d\u001b}~\u0014ES8eU\n\u0003"));
                              Provider.this.setLevel(6);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("}`F\u0017\u001f")) || var5.equalsIgnoreCase("7")) {
                              Provider.this.println(a("T]s?(J(\u0014\u001b\u0005y|S\u0011\u0003\u007f2X\u001d\u001b}~\u0014ES8wF\n\u0002j"));
                              Provider.this.setLevel(7);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("y~X")) || var5.equalsIgnoreCase("0")) {
                              Provider.this.println(a("T]s?(J(\u0014\u001b\u0005y|S\u0011\u0003\u007f2X\u001d\u001b}~\u0014ES8sX\u0014"));
                              Provider.this.setLevel(0);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("wtR")) || var5.equalsIgnoreCase(a(")\""))) {
                              Provider.this.println(a("T]s?(J(\u0014\u001b\u0005y|S\u0011\u0003\u007f2X\u001d\u001b}~\u0014ES8}R\u001e"));
                              Provider.this.setLevel(10);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("ybD\f\u0004uw"))) {
                              Provider.this.println(a("T]s?(J(\u0014\u0019\u000e{wD\f\b|2U\b\u001dl{Y\u001d"));
                              Provider.this.setAppTimeLogged(true);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("v}U\b\u001dl{Y\u001d"))) {
                              Provider.this.println(a("T]s?(J(\u0014\u0019\u000e{wD\f\b|2Z\u0017\fhb@\u0011\u0000}"));
                              Provider.this.setAppTimeLogged(false);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("|s@\u001d"))) {
                              Provider.this.println(a("T]s?(J(\u0014\u0019\u000e{wD\f\b|2P\u0019\u0019}"));
                              Provider.this.setDateLogged(true);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("v}P\u0019\u0019}"))) {
                              Provider.this.println(a("T]s?(J(\u0014\u0019\u000e{wD\f\b|2Z\u0017\tyfQ"));
                              Provider.this.setDateLogged(false);
                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("wbQ\u0016\u0001wu"))) {
                              if (var4 <= 1) {
                                 break label194;
                              }

                              String var6 = var3.nextToken();

                              try {
                                 label97: {
                                    var6 = Provider.this.r.resolveVars(var6);
                                    File var7 = new File(var6);
                                    if (!var7.exists()) {
                                       FileOutputStream var8 = new FileOutputStream(var6);
                                       PrintStream var9 = new PrintStream(var8);
                                       if (Provider.this.f != null && Provider.this.f != System.out && Provider.this.f != System.err) {
                                          Provider.this.f.close();
                                       }

                                       Provider.this.f = var9;
                                       if (var10 == 0) {
                                          break label97;
                                       }
                                    }

                                    Provider.this.println(a("T]s?(J(\u0014\u001d\u001fj}FX\u0004v2Z\u001d\u001a~{X\u001dW82R\u0011\u0001}2Q\u0000\u0004kfG"));
                                 }
                              } catch (IOException var11) {
                                 Provider.this.println(a("T]s?(J(\u0014\u0016\bot]\u0014\b8wF\n\u0002j(\u0014") + var11);
                              }

                              if (var10 == 0) {
                                 break label194;
                              }
                           }

                           if (var5.equalsIgnoreCase(a("{~[\u000b\bt}S"))) {
                              if (Provider.this.f != null && Provider.this.f != System.out && Provider.this.f != System.err) {
                                 Provider.this.f.close();
                              }

                              Provider.this.f = null;
                           }
                        }
                     }

                     var1 = new StringBuffer();
                  } while(var10 == 0);

                  var1.append((char)var2);
                  if (var1.length() > 50) {
                     var1 = new StringBuffer();
                  }
               }
            } catch (Exception var12) {
            }
         }

         private static String a(String var0) {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               char var10002 = var1[var3];
               byte var10003;
               switch (var3 % 5) {
                  case 0:
                     var10003 = 24;
                     break;
                  case 1:
                     var10003 = 18;
                     break;
                  case 2:
                     var10003 = 52;
                     break;
                  case 3:
                     var10003 = 120;
                     break;
                  default:
                     var10003 = 109;
               }

               var1[var3] = (char)(var10002 ^ var10003);
            }

            return new String(var1);
         }
      }

      class LogServer {
         private Thread a;
         private ServerSocket b;
         private boolean c;

         LogServer(int var2) throws IOException {
            int var3 = Logger.j;
            super();
            this.a = null;
            this.b = null;
            this.c = true;
            this.b = new ServerSocket(var2);
            this.a = new Thread() {
               public void run() {
                  int var4 = Logger.j;

                  try {
                     while(LogServer.this.c) {
                        Socket var1 = LogServer.this.b.accept();
                        Provider.this.a(a(".~rUjh;\u00152\u000fvNr\u0014>P\u0011?\u0005>A\u0010r\u0016%J\u001a7\u0016>\u0004\u0012 \u001a'\u001eT") + var1 + a(".~"), false);
                        Socket var2 = Provider.this.a;
                        if (var4 != 0) {
                           break;
                        }

                        label29: {
                           if (var2 != null) {
                              try {
                                 var1.getOutputStream().write(a(".~rUjp;\u001dU\u0007e:\u000bU\tk:\u001c0\tp=\u001d;\u0019\u0004~X").getBytes());
                                 var1.close();
                                 break label29;
                              } catch (Exception var5) {
                                 if (var4 == 0) {
                                    break label29;
                                 }
                              }
                           }

                           Provider.this.a = var1;
                           SocketReader var3 = Provider.this.new SocketReader(var1);
                           var3.setDaemon(true);
                           var3.start();
                        }

                        if (var4 != 0) {
                           break;
                        }
                     }
                  } catch (Exception var6) {
                  }

               }

               private static String a(String var0) {
                  char[] var1 = var0.toCharArray();
                  int var2 = var1.length;

                  for(int var3 = 0; var3 < var2; ++var3) {
                     char var10002 = var1[var3];
                     byte var10003;
                     switch (var3 % 5) {
                        case 0:
                           var10003 = 36;
                           break;
                        case 1:
                           var10003 = 116;
                           break;
                        case 2:
                           var10003 = 82;
                           break;
                        case 3:
                           var10003 = 117;
                           break;
                        default:
                           var10003 = 74;
                     }

                     var1[var3] = (char)(var10002 ^ var10003);
                  }

                  return new String(var1);
               }
            };
            this.a.setDaemon(true);
            this.a.start();
            if (var3 != 0) {
               SnmpException.b = !SnmpException.b;
            }

         }

         public void shutdown() {
            Socket var1 = Provider.this.a;
            if (var1 != null) {
               try {
                  var1.close();
               } catch (Exception var4) {
               }
            }

            Provider.this.a = null;
            this.c = false;
            this.a.interrupt();

            try {
               this.b.close();
            } catch (Exception var3) {
            }

         }
      }
   }
}
