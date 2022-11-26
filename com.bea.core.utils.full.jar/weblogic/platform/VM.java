package weblogic.platform;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketImpl;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class VM {
   private static final String HEAP_RATIO_REQUIREMENT = "com.sun.management.HotSpotDiagnosticMXBean";
   private static final boolean DEBUG = false;
   private static final GarbageCollectionEvent MINOR_GC_EVENT = new GarbageCollectionEvent(1);
   private static final GarbageCollectionEvent MAJOR_GC_EVENT = new GarbageCollectionEvent(0);
   private static Field sockImplField;
   private static Field fileDesField;
   private static Field fdField;
   private static Field rawSocketField;
   private static Class layeredSocketClass;
   private static Method getFDValMethod;
   private static boolean postJdk8 = false;
   private final ArrayList list = new ArrayList();
   private final boolean hotSpot;
   private static VM vm;
   private static VM vm15Delegate;

   public VM() {
      boolean isHotSpot;
      try {
         Class.forName("com.sun.management.HotSpotDiagnosticMXBean", false, ClassLoader.getSystemClassLoader());
         isHotSpot = true;
      } catch (Throwable var3) {
         isHotSpot = false;
      }

      this.hotSpot = isHotSpot;
   }

   public int getFD(Socket sock) throws IOException {
      try {
         if (postJdk8) {
            throw new IOException("Invalid call on post-JDK8");
         } else {
            SocketChannel ch = sock.getChannel();
            if (ch != null && getFDValMethod != null) {
               return (Integer)getFDValMethod.invoke(ch);
            } else {
               SocketImpl sockImpl = (SocketImpl)sockImplField.get(this.getRawSocket(sock));
               FileDescriptor fileDes = (FileDescriptor)fileDesField.get(sockImpl);
               Object fdValue = fdField.get(fileDes);
               if (fdValue instanceof Integer) {
                  return (Integer)fdValue;
               } else if (fdValue instanceof Long) {
                  return ((Long)fdValue).intValue();
               } else {
                  throw new IOException("Invalid fd");
               }
            }
         }
      } catch (ThreadDeath var6) {
         throw var6;
      } catch (Throwable var7) {
         String msg = "Cannot get fd for sock=" + sock + ": " + var7.getMessage();
         throw new IOException(msg);
      }
   }

   Socket getRawSocket(Socket sock) throws IllegalAccessException {
      if (layeredSocketClass != null && layeredSocketClass.isInstance(sock) && rawSocketField != null) {
         sock = (Socket)rawSocketField.get(sock);
      }

      return sock;
   }

   public void threadDump() {
      System.err.println("***** This VM does not support thread dumps *****");
   }

   public void threadDump(String filename) throws IOException {
      PrintStream pos = new PrintStream(new FileOutputStream(filename));
      Throwable var3 = null;

      try {
         pos.println("***** This VM does not support thread dumps *****");
         pos.close();
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (pos != null) {
            if (var3 != null) {
               try {
                  pos.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               pos.close();
            }
         }

      }

   }

   public void threadDump(FileDescriptor fd) throws IOException {
      PrintStream pos = new PrintStream(new FileOutputStream(fd));
      Throwable var3 = null;

      try {
         pos.println("***** This VM does not support thread dumps *****");
         pos.close();
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (pos != null) {
            if (var3 != null) {
               try {
                  pos.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               pos.close();
            }
         }

      }

   }

   public void threadDump(File f) throws IOException {
      PrintStream pos = new PrintStream(new FileOutputStream(f));
      Throwable var3 = null;

      try {
         pos.println("***** This VM does not support thread dumps *****");
         pos.close();
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (pos != null) {
            if (var3 != null) {
               try {
                  pos.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               pos.close();
            }
         }

      }

   }

   public void threadDump(PrintWriter pw) {
      pw.println("***** This VM does not support thread dumps *****");
      pw.flush();
   }

   public String threadDumpAsString(Thread thread) {
      return vm15Delegate != null ? vm15Delegate.threadDumpAsString(thread) : null;
   }

   public String threadDumpAsString() {
      return vm15Delegate != null ? vm15Delegate.threadDumpAsString() : null;
   }

   public String threadDumpAsString(boolean lockedMonitors, boolean lockedSynchronizers) {
      return vm15Delegate != null ? vm15Delegate.threadDumpAsString(lockedMonitors, lockedSynchronizers) : null;
   }

   public String dumpDeadlockedThreads() {
      return vm15Delegate != null ? vm15Delegate.dumpDeadlockedThreads() : null;
   }

   public boolean isNativeThreads() {
      return true;
   }

   public String getName() {
      return "UnknownVM";
   }

   public static synchronized VM getVM() {
      if (vm != null) {
         return vm;
      } else {
         String vendor = System.getProperty("java.vm.vendor");
         if (vendor == null) {
            vendor = "";
         }

         vendor = vendor.toLowerCase();
         String javavmname = System.getProperty("java.vm.name");
         if (javavmname == null) {
            javavmname = "";
         }

         javavmname = javavmname.toLowerCase();
         String version = System.getProperty("java.version");
         if (version == null) {
            version = "";
         }

         version = version.toLowerCase();
         String os = System.getProperty("os.name");
         if (os == null) {
            os = "";
         }

         os = os.toLowerCase();
         String arch = System.getProperty("os.arch");
         if (arch == null) {
            arch = "";
         }

         arch = arch.toLowerCase();
         vm = getVM(vendor, javavmname, version, os, arch);
         return vm;
      }
   }

   static VM getVM(String vendor, String javavmname, String version, String os, String arch) {
      initVM15Delegate(version);
      VM vm = null;
      if (!vendor.contains("sun") && !vendor.contains("apple")) {
         if (vendor.toLowerCase().contains("digital equi")) {
            if (arch.toLowerCase().contains("alpha")) {
               try {
                  vm = (VM)Class.forName("weblogic.platform.SunVM").newInstance();
               } catch (Throwable var8) {
               }
            }
         } else if (vendor.contains("oracle")) {
            try {
               vm = (VM)Class.forName("weblogic.platform.SunVM").newInstance();
            } catch (Throwable var7) {
            }
         }
      } else {
         try {
            vm = (VM)Class.forName("weblogic.platform.SunVM").newInstance();
         } catch (Throwable var9) {
         }
      }

      if (vm == null) {
         vm = new VM();
      }

      return vm;
   }

   private static void initVM15Delegate(String version) {
      try {
         vm15Delegate = (VM)Class.forName("weblogic.platform.VM15").newInstance();
      } catch (Throwable var2) {
         System.err.println("***** FATAL: Unable to initialize weblogic.platform.VM15 *****");
      }

   }

   public final void addGCListener(GCListener listener) {
      synchronized(this.list) {
         if (listener != null) {
            this.list.add(listener);
         }

      }
   }

   protected final void sendMinorGCEvent() {
      this.sendGCEvent(MINOR_GC_EVENT);
   }

   protected final void sendMajorGCEvent() {
      this.sendGCEvent(MAJOR_GC_EVENT);
   }

   private void sendGCEvent(GarbageCollectionEvent gce) {
      synchronized(this.list) {
         Iterator var3 = this.list.iterator();

         while(var3.hasNext()) {
            Object aList = var3.next();
            GCListener listener = (GCListener)aList;
            listener.onGarbageCollection(gce);
         }

      }
   }

   public boolean isHotSpot() {
      return this.hotSpot;
   }

   static {
      try {
         String version = System.getProperty("java.version");
         if (version != null && version.startsWith("1.")) {
            sockImplField = Socket.class.getDeclaredField("impl");
            sockImplField.setAccessible(true);
            fileDesField = SocketImpl.class.getDeclaredField("fd");
            fileDesField.setAccessible(true);
            getFDValMethod = Class.forName("sun.nio.ch.SocketChannelImpl").getDeclaredMethod("getFDVal");
            getFDValMethod.setAccessible(true);
            fdField = FileDescriptor.class.getDeclaredField("fd");
            fdField.setAccessible(true);
            layeredSocketClass = Class.forName("javax.net.ssl.impl.SSLLayeredSocket");
            rawSocketField = layeredSocketClass.getDeclaredField("socket");
            rawSocketField.setAccessible(true);
         } else {
            postJdk8 = true;
         }
      } catch (NoSuchFieldException var1) {
      } catch (NoSuchMethodException var2) {
      } catch (ClassNotFoundException | RuntimeException var3) {
      }

   }
}
