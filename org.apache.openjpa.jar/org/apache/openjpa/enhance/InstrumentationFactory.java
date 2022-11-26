package org.apache.openjpa.enhance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.InvocationTargetException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.openjpa.lib.util.JavaVersions;

public class InstrumentationFactory {
   private static Instrumentation _inst;
   private static boolean _dynamicallyInstall = true;

   public static void setInstrumentation(Instrumentation inst) {
      _inst = inst;
   }

   public static synchronized void setDynamicallyInstallAgent(boolean val) {
      _dynamicallyInstall = val;
   }

   public static synchronized Instrumentation getInstrumentation() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
      if (_inst == null && _dynamicallyInstall) {
         if (JavaVersions.VERSION < 6) {
            return null;
         } else {
            String agentPath = getAgentJar();
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            String pid = runtime.getName();
            if (pid.indexOf("@") != -1) {
               pid = pid.substring(0, pid.indexOf("@"));
            }

            Class vmClass = Class.forName("com.sun.tools.attach.VirtualMachine");
            Object vm = vmClass.getMethod("attach", String.class).invoke((Object)null, pid);
            vm.getClass().getMethod("loadAgent", String.class).invoke(vm, agentPath);
            return _inst != null ? _inst : null;
         }
      } else {
         return _inst;
      }
   }

   private static String getAgentJar() throws IOException {
      File file = File.createTempFile(InstrumentationFactory.class.getName(), ".jar");
      file.deleteOnExit();
      ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(file));
      zout.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(zout));
      writer.println("Agent-Class: " + InstrumentationFactory.class.getName());
      writer.println("Can-Redefine-Classes: true");
      writer.println("Can-Retransform-Classes: true");
      writer.close();
      return file.getAbsolutePath();
   }

   public static void agentmain(String agentArgs, Instrumentation inst) {
      setInstrumentation(inst);
   }
}
