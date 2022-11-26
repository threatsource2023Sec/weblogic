package weblogic.utils.agent;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import weblogic.diagnostics.debug.DebugLogger;

public final class AttachUtils {
   private static final String ATTACH_PROPERTY = "com.bea.agent.Attach";
   private static final String CURRENT_ATTACH_ID = genId();
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugAttach");
   private static final Random RANDOM = new Random();

   private static String genId() {
      int val = RANDOM.nextInt(257);
      long t = System.nanoTime();
      long result = (long)val ^ t;
      String id = "" + result;

      try {
         System.setProperty("com.bea.agent.Attach", id);
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return id;
   }

   private static VirtualMachine findVirtualMachine(VirtualMachineDescriptor d) throws Exception {
      VirtualMachine machine = VirtualMachine.attach(d);
      if (machine != null) {
         Properties p = machine.getSystemProperties();
         String value = p.getProperty("com.bea.agent.Attach");
         if (value != null && CURRENT_ATTACH_ID.equals(value)) {
            return machine;
         }

         machine.detach();
      }

      return null;
   }

   private static VirtualMachine attachToCurrentVM() throws Exception {
      List l = VirtualMachine.list();
      VirtualMachine machine = null;
      Iterator var2 = l.iterator();

      do {
         if (!var2.hasNext()) {
            return null;
         }

         VirtualMachineDescriptor d = (VirtualMachineDescriptor)var2.next();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Trying machine: " + d.id() + " : " + d.displayName());
         }

         machine = findVirtualMachine(d);
      } while(machine == null);

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Found Machine: " + machine.id());
      }

      return machine;
   }

   public static boolean loadAgent(File agentFile) {
      String agentPath = agentFile.getAbsolutePath();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Attempting to attach agent: " + agentPath);
      }

      if (!agentFile.exists()) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Agent " + agentPath + " does not exist.");
         }

         return false;
      } else {
         try {
            VirtualMachine machine = attachToCurrentVM();
            if (machine != null) {
               machine.loadAgent(agentPath);
               machine.detach();
               return true;
            }

            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Could not find VM to attach.");
            }
         } catch (Exception var3) {
            var3.printStackTrace();
         }

         return false;
      }
   }
}
