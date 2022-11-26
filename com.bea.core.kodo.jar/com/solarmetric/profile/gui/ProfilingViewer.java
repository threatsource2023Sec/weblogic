package com.solarmetric.profile.gui;

import com.solarmetric.profile.ProfilingAgentImpl;
import com.solarmetric.profile.ProfilingIO;
import com.solarmetric.profile.ProfilingLog;
import java.io.File;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;

public abstract class ProfilingViewer extends ProfilingIde {
   private static final Localizer _loc = Localizer.forPackage(ProfilingViewer.class);
   private File _file;
   private ProfilingAgentImpl _agent;

   protected ProfilingViewer() {
   }

   protected ProfilingViewer(Configuration conf) {
      super(conf);
   }

   public String getName() {
      return "Profiling Viewer: " + this.getFile().getName();
   }

   public void setFile(File val) {
      this._file = val;
   }

   public File getFile() {
      return this._file;
   }

   public boolean isUpdatable() {
      return false;
   }

   public ProfilingAgentImpl getAgent() {
      if (this._agent == null) {
         try {
            Log l = ProfilingLog.get(this.getConfiguration());
            this._agent = ProfilingIO.importAgent(this._file, l);
         } catch (Exception var2) {
            throw new IllegalStateException(_loc.get("error-loading-agent", var2.getClass().getName(), var2.getMessage()).getMessage());
         }
      }

      return this._agent;
   }

   public static void loadFileFromArgs(ProfilingViewer viewer, String[] args) {
      Options opts = new Options();
      args = opts.setFromCmdLine(args);
      if (!opts.containsKey("help") && !opts.containsKey("-help") && args.length == 1) {
         File f = Files.getFile(args[0], (ClassLoader)null);
         if (!f.exists()) {
            System.err.println(_loc.get("no-such-file", args[0]));
         } else {
            viewer.setFile(f);
            main(viewer, args);
         }
      } else {
         System.err.println(_loc.get("viewer-usage"));
      }
   }
}
