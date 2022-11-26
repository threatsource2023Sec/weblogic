package com.bea.wls.redef;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.classloaders.BeaHomeHolder;

public class AttachUtils {
   public static final String ATTACH_PROPERTY = "com.bea.wls.redef.Attach";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugRedefAttach");
   private static final String CURRENT_ATTACH_ID = genId();
   private static final String FASTSWAP_PROPERTIES = "META-INF/fastswap.properties";
   private static final String WEBLOGIC_MODULES_HOME = "wlserver/modules/com.bea.core.redefagent.jar";

   private AttachUtils() {
   }

   private static String genId() {
      UUID result = UUID.randomUUID();
      return result.toString();
   }

   private static VirtualMachine attachToCurrentVMUsingSystemPropertyBasedApproach() throws ClassRedefInitializationException {
      List l = VirtualMachine.list();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("\n");
         DEBUG.debug("Found VirtualMachineDescriptor List size: " + l.size());
         DEBUG.debug("VirtualMachineDescriptor List contents: " + Arrays.toString(l.toArray()));
      }

      StringBuilder attachFailureMessage = new StringBuilder();

      try {
         for(int i = 0; i < l.size(); ++i) {
            VirtualMachineDescriptor d = (VirtualMachineDescriptor)l.get(i);
            VirtualMachine machine = null;
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("\n");
               DEBUG.debug(i + 1 + " of " + l.size() + ": Attempt to find VirtualMachine where ATTACH_PROPERTY system property was set");
               DEBUG.debug("for loop: VirtualMachineDescriptor id: " + d.id());
               DEBUG.debug("for loop: VirtualMachineDescriptor displayName: " + d.displayName());
               DEBUG.debug("for loop: JVM RuntimeMXBean name: " + ManagementFactory.getRuntimeMXBean().getName());
            }

            try {
               machine = VirtualMachine.attach(d);
            } catch (Exception var8) {
               attachFailureMessage.append("Attach failed for VirtualMachine ").append("id:").append(d.id()).append("displayName:").append(d.displayName()).append(StackTraceUtils.throwable2StackTrace(var8));
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Unable to attach to VM, id:" + d.id() + " name:" + d.displayName(), var8);
               }
               continue;
            }

            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug(i + 1 + " of " + l.size() + ": Successfully attached to the VirtualMachine. VirtualMachineDescriptor id: " + d.id());
            }

            String value = null;

            try {
               Properties p = machine.getSystemProperties();
               value = p.getProperty("com.bea.wls.redef.Attach");
            } catch (IOException var7) {
               attachFailureMessage.append("getSystemProperties() failed for VirtualMachine ").append("id:").append(d.id()).append("displayName:").append(d.displayName()).append(StackTraceUtils.throwable2StackTrace(var7));
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Unable to get system properties from VM, id:" + d.id() + " name:" + d.displayName(), var7);
               }
               continue;
            }

            if (value != null && CURRENT_ATTACH_ID.equals(value)) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Found VirtualMachine having our ATTACH_PROPERTY. machine.id(): " + machine.id());
                  DEBUG.debug("Found VirtualMachine having our ATTACH_PROPERTY. JVM RuntimeMXBean.getName(): " + ManagementFactory.getRuntimeMXBean().getName());
               }

               return machine;
            }

            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("This VirtualMachine does NOT have our ATTACH_PROPERTY. machine.id(): " + machine.id() + " and JVM RuntimeMXBean.getName(): " + ManagementFactory.getRuntimeMXBean().getName());
            }

            machine.detach();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Detached from VirtualMachine machine.id(): " + machine.id());
            }
         }
      } catch (IOException var9) {
         throw new ClassRedefInitializationException(var9);
      }

      if (attachFailureMessage.length() == 0) {
         attachFailureMessage.append("No VirtualMachine having ATTACH_PROPERTY found. JVM RuntimeMXBean.getName(): " + ManagementFactory.getRuntimeMXBean().getName() + " VirtualMachineDescriptor List contents: " + Arrays.toString(l.toArray()) + " System.getProperty(ATTACH_PROPERTY): " + System.getProperty("com.bea.wls.redef.Attach"));
      }

      throw new ClassRedefInitializationException(attachFailureMessage.toString());
   }

   private static VirtualMachine attachToCurrentVMUsingPIDApproach() throws ClassRedefInitializationException {
      String jvmName = ManagementFactory.getRuntimeMXBean().getName();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("ManagementFactory.getRuntimeMXBean().getName() returned: " + jvmName);
      }

      int i = jvmName.indexOf(64);
      String pidString = null;
      if (i != -1) {
         pidString = jvmName.substring(0, i);
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("PID String found to be: " + pidString);
      }

      try {
         Integer.parseInt(pidString);
      } catch (NumberFormatException var6) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("NumberFormatException thrown for pidString: " + pidString);
         }

         throw new ClassRedefInitializationException(var6);
      }

      VirtualMachine vm = null;

      try {
         vm = VirtualMachine.attach(pidString);
         return vm;
      } catch (Exception var5) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Exception thrown by VirtualMachine.attach(pidString) for pidString: " + pidString + ", Exception is: " + var5);
         }

         throw new ClassRedefInitializationException(var5);
      }
   }

   private static VirtualMachine attachToVMUsingPIDApproach(String pidString) throws ClassRedefInitializationException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("PID String using: " + pidString);
      }

      try {
         Integer.parseInt(pidString);
      } catch (NumberFormatException var4) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("NumberFormatException thrown for pidString: " + pidString);
         }

         throw new ClassRedefInitializationException(var4);
      }

      VirtualMachine vm = null;

      try {
         vm = VirtualMachine.attach(pidString);
         return vm;
      } catch (Exception var3) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Exception thrown by VirtualMachine.attach(pidString) for pidString: " + pidString + ", Exception is: " + var3);
         }

         throw new ClassRedefInitializationException(var3);
      }
   }

   public static void loadRedefinitionAgent(VirtualMachine machine) throws ClassRedefInitializationException {
      String beaHome = BeaHomeHolder.getBeaHome();
      if (beaHome == null) {
         throw new AssertionError("Could not determine the value of BEA_HOME");
      } else {
         File agent = new File(beaHome, "wlserver/modules/com.bea.core.redefagent.jar");
         if (!agent.exists()) {
            throw new ClassRedefInitializationException("Cannot find agent jar :" + agent.getAbsolutePath());
         } else {
            try {
               machine.loadAgent(agent.getAbsolutePath());
            } catch (AgentLoadException var4) {
               throw new ClassRedefInitializationException(var4);
            } catch (AgentInitializationException var5) {
               throw new ClassRedefInitializationException(var5);
            } catch (IOException var6) {
               throw new ClassRedefInitializationException(var6);
            }
         }
      }
   }

   private static String getAgentVersion() throws ClassRedefInitializationException {
      Properties p = new Properties();
      ClassLoader loader = AttachUtils.class.getClassLoader();
      InputStream in = null;

      try {
         in = loader.getResourceAsStream("META-INF/fastswap.properties");
         if (in == null) {
            throw new ClassRedefInitializationException("Cannot find resource META-INF/fastswap.properties");
         }

         p.load(in);
      } catch (IOException var11) {
         throw new ClassRedefInitializationException(var11);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var10) {
            }
         }

      }

      return p.getProperty("Version", "Unknown");
   }

   private static VirtualMachine findVirtualMachine(VirtualMachineDescriptor d) throws ClassRedefInitializationException {
      try {
         VirtualMachine machine = null;

         try {
            machine = VirtualMachine.attach(d);
            Properties p = machine.getSystemProperties();
            String value = p.getProperty("com.bea.wls.redef.Attach");
            if (value != null && CURRENT_ATTACH_ID.equals(value)) {
               return machine;
            }

            machine.detach();
         } catch (Exception var4) {
            throw new ClassRedefInitializationException(var4);
         }

         return null;
      } catch (Throwable var5) {
         throw new ClassRedefInitializationException(var5);
      }
   }

   public static void attachAndLoadAgent() throws ClassRedefInitializationException {
      if (!System.getProperty("java.version").startsWith("1.")) {
         attachExternalApproach();
      } else {
         VirtualMachine machine = null;

         try {
            try {
               machine = attachToCurrentVMUsingPIDApproach();
            } catch (ClassRedefInitializationException var10) {
               DEBUG.debug("Finding current VirtualMachine using PID to attach to it did not work. Using fallback mechanism of system property reading.");
            }

            if (machine == null) {
               machine = attachToCurrentVMUsingSystemPropertyBasedApproach();
            }

            loadRedefinitionAgent(machine);
            machine.detach();
         } catch (IOException var11) {
            throw new ClassRedefInitializationException(var11);
         } finally {
            try {
               if (machine != null) {
                  machine.detach();
               }
            } catch (IOException var9) {
            }

         }

      }
   }

   private static void attachExternalApproach() throws ClassRedefInitializationException {
      try {
         Class c = Class.forName("java.lang.ProcessHandle");
         Method pid = c.getMethod("pid");
         if (pid == null) {
            pid = c.getMethod("getPid");
         }

         Method current = c.getMethod("current");
         String pidString = null;
         if (pid != null && current != null) {
            pidString = pid.invoke(current.invoke((Object)null)).toString();
         }

         if (pidString == null) {
            throw new ClassRedefInitializationException("Failed to get pid for process");
         } else {
            String cmd = System.getProperty("java.home") + File.separatorChar + "bin" + File.separatorChar;
            if (System.getProperty("os.name", "").toLowerCase(Locale.US).contains("windows")) {
               cmd = cmd + "java.exe";
            } else {
               cmd = cmd + "java";
            }

            String strClassPath = System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder(new String[]{cmd, "-classpath", strClassPath, "com.bea.wls.redef.AttachUtils", pidString});
            Process p = pb.start();
            int ret = p.waitFor();
            if (ret != 0) {
               throw new ClassRedefInitializationException("FAST SWAP agent returned " + ret);
            }
         }
      } catch (ClassRedefInitializationException var9) {
         throw var9;
      } catch (Exception var10) {
         throw new ClassRedefInitializationException("Failed to start FAST SWAP agent");
      }
   }

   public static void main(String[] args) {
      if (args.length != 1) {
         System.err.println("Usage: java com.bea.wls.redef.AttachUtils pid");
         System.exit(1);
      }

      VirtualMachine machine = null;

      try {
         machine = attachToVMUsingPIDApproach(args[0]);
         loadRedefinitionAgent(machine);
         machine.detach();
      } catch (Exception var11) {
         System.err.println("Failed to attach to FAST SWAP agent");
         System.exit(1);
      } finally {
         try {
            if (machine != null) {
               machine.detach();
            }
         } catch (IOException var10) {
         }

      }

   }

   static {
      try {
         System.setProperty("com.bea.wls.redef.Attach", CURRENT_ATTACH_ID);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("\n");
            DEBUG.debug("ATTACH_PROPERTY system property set to: " + System.getProperty("com.bea.wls.redef.Attach"));
            DEBUG.debug("RuntimeMXBean.getName() from JVM where ATTACH_PROPERTY is set: " + ManagementFactory.getRuntimeMXBean().getName());
         }
      } catch (Exception var1) {
         DEBUG.debug("Could not set ATTACH_PROPERTY=" + CURRENT_ATTACH_ID + " because of exception: " + var1.getMessage());
         var1.printStackTrace();
      }

   }
}
