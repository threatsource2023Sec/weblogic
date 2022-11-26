package com.solarmetric.profile;

import java.io.File;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public abstract class ProfilingInterfaceTimedExport implements ProfilingInterface {
   private static final Localizer s_loc = Localizer.forPackage(ProfilingInterfaceTimedExport.class);
   private Log _log;
   private ProfilingAgentImpl _agent;
   private long _intervalMillis = -1L;
   private String _basename = "profilingExport";
   private boolean _active = true;
   private Thread _thread = null;
   private boolean _unique = false;
   private long _startTime = 0L;
   private Configuration _conf;

   public abstract String getVersionString();

   public ProfilingInterfaceTimedExport(Configuration conf) {
      this._conf = conf;
      this._log = ProfilingLog.get(conf);
   }

   public void setProfilingAgent(ProfilingAgent agent) {
      if (this._agent == null) {
         this._agent = (ProfilingAgentImpl)agent;
      }

   }

   public void setIntervalMillis(long millis) {
      this._intervalMillis = millis;
   }

   public void setBasename(String basename) {
      this._basename = basename;
   }

   public void setUniqueNames(boolean unique) {
      this._unique = unique;
   }

   public void close() {
      this._active = false;
      if (this._thread != null) {
         this._thread.interrupt();
      }

   }

   public void init() {
   }

   public void run() {
      Runnable r = new Runnable() {
         public void run() {
            ProfilingInterfaceTimedExport.this.runLoop();
         }
      };
      (new Thread(r)).start();
   }

   private void runLoop() {
      if (this._agent == null) {
         this._log.error(s_loc.get("no-agent"));
      } else {
         this._thread = Thread.currentThread();
         this._startTime = System.currentTimeMillis();
         if (this._intervalMillis < 0L) {
            this._intervalMillis = Long.MAX_VALUE;
         }

         int count = 0;

         String filename;
         File file;
         while(this._active) {
            try {
               Thread.sleep(this._intervalMillis);
               if (this._unique) {
                  filename = this._basename + "_" + this._startTime + "_" + count + s_loc.get("prx-suffix");
               } else {
                  filename = this._basename + "_" + count + s_loc.get("prx-suffix");
               }

               file = new File(filename);
               ++count;
               ProfilingIO.exportAgent(this.getVersionString(), this._conf, this._agent, file);
            } catch (InterruptedException var5) {
            } catch (Exception var6) {
               this._log.error(s_loc.get("cant-export"), var6);
            }
         }

         if (this._unique) {
            filename = this._basename + "_" + this._startTime + "_final" + s_loc.get("prx-suffix");
         } else {
            filename = this._basename + "_final" + s_loc.get("prx-suffix");
         }

         try {
            file = new File(filename);
            ProfilingIO.exportAgent(this.getVersionString(), this._conf, this._agent, file);
         } catch (Exception var4) {
            this._log.error(s_loc.get("cant-export"), var4);
         }

      }
   }
}
