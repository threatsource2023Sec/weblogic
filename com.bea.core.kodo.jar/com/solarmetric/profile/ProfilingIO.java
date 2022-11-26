package com.solarmetric.profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class ProfilingIO {
   private static final Localizer _loc = Localizer.forPackage(ProfilingIO.class);

   public static void exportAgent(String versionString, Configuration conf, ProfilingAgentImpl agent, ObjectOutputStream oos) throws ProfilingExportException {
      agent.processQueue();

      try {
         oos.writeObject(versionString);
         oos.writeObject(conf.toProperties(false).toString());
         oos.writeObject(agent);
      } catch (Exception var12) {
         throw new ProfilingExportException(var12);
      } finally {
         try {
            oos.close();
         } catch (Exception var11) {
            throw new ProfilingExportException(var11);
         }
      }

   }

   public static void exportAgent(String versionString, Configuration conf, ProfilingAgentImpl agent, File file) throws ProfilingExportException {
      try {
         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
         exportAgent(versionString, conf, agent, oos);
      } catch (Exception var5) {
         throw new ProfilingExportException(var5);
      }
   }

   public static ProfilingAgentImpl importAgent(ObjectInputStream ois, Log log) throws Exception {
      String version = (String)ois.readObject();
      String props = (String)ois.readObject();
      if (log.isTraceEnabled()) {
         log.trace(_loc.get("read-prx-version", version, props));
      }

      ProfilingAgentImpl var5;
      try {
         ProfilingAgentImpl agent = (ProfilingAgentImpl)ois.readObject();
         var5 = agent;
      } catch (Exception var9) {
         log.error(_loc.get("cant-read-prx", version), var9);
         throw var9;
      } finally {
         ois.close();
      }

      return var5;
   }

   public static ProfilingAgentImpl importAgent(File file, Log log) throws Exception {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
      return importAgent(ois, log);
   }
}
