package weblogic.nodemanager.util;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class UnixProcessControl extends ProcessControl {
   private static final int REAPER_INTERVAL_MS = 10000;
   private boolean initialized;
   private Set childPIDs;
   private Timer reaperTimer;

   UnixProcessControl() throws UnsatisfiedLinkError {
      System.loadLibrary("nodemanager");
      this.childPIDs = new HashSet();
      this.reaperTimer = new Timer("NM Reaper", true);
      this.reaperTimer.schedule(new ReaperTask(), 10000L, 10000L);
   }

   public String getProcessId() {
      return String.valueOf(this.getProcessId0());
   }

   public boolean changeFileOwnership(File file, String owner, String group) {
      if (owner == null && group == null) {
         return true;
      } else {
         return file != null ? this.changeFileOwnership0(file.getAbsolutePath(), owner, group) : false;
      }
   }

   public boolean killProcess(String pid) {
      try {
         return this.killProcess0(Integer.parseInt(pid));
      } catch (NumberFormatException var3) {
         throw new IllegalArgumentException("Invalid pid format: " + pid);
      }
   }

   public boolean isProcessAlive(String pid) {
      int ipid = Integer.parseInt(pid);
      if (ipid <= 0) {
         return false;
      } else {
         synchronized(this.childPIDs) {
            if (this.childPIDs.contains(ipid)) {
               int ret = this.waitNonBlocking0(ipid);
               if (ret == ipid) {
                  this.childPIDs.remove(ipid);
                  return false;
               }
            }
         }

         try {
            return this.isProcessAlive0(Integer.parseInt(pid));
         } catch (NumberFormatException var6) {
            throw new IllegalArgumentException("Invalid pid format: " + pid);
         }
      }
   }

   public String createProcess(String[] cmd, Map env, File dir, File out) throws IOException {
      String dirPath = null;
      String outPath = null;
      byte[] envBlock = null;
      if (cmd.length < 1) {
         throw new IndexOutOfBoundsException();
      } else {
         if (dir != null) {
            dirPath = dir.getAbsolutePath();
         }

         if (out != null) {
            outPath = out.getAbsolutePath();
         }

         if (env != null) {
            envBlock = this.getEnvironmentBlock(env);
         }

         int pid = this.createProcess0(this.getArgumentBlock(cmd), cmd.length, envBlock, env != null ? env.size() : 0, dirPath, outPath);
         if (pid <= 0) {
            throw new IOException("Invalid pid [" + pid + "] returned while creating process for " + Arrays.toString(cmd));
         } else {
            this.addChild(pid);
            return String.valueOf(pid);
         }
      }
   }

   private byte[] getArgumentBlock(String[] args) {
      int count = args.length;

      for(int i = 0; i < args.length; ++i) {
         count += args[i].getBytes().length;
      }

      ByteBuffer bb = ByteBuffer.wrap(new byte[count]);

      for(int i = 0; i < args.length; ++i) {
         bb.put(args[i].getBytes());
         bb.put((byte)0);
      }

      return bb.array();
   }

   private byte[] getEnvironmentBlock(Map env) {
      int count = env.size() * 2;

      Map.Entry e;
      for(Iterator it = env.entrySet().iterator(); it.hasNext(); count += ((String)e.getValue()).getBytes().length) {
         e = (Map.Entry)it.next();
         count += ((String)e.getKey()).getBytes().length;
      }

      ByteBuffer bb = ByteBuffer.wrap(new byte[count]);
      Iterator it = env.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry e = (Map.Entry)it.next();
         bb.put(((String)e.getKey()).getBytes());
         bb.put((byte)61);
         bb.put(((String)e.getValue()).getBytes());
         bb.put((byte)0);
      }

      return bb.array();
   }

   private void addChild(int pid) {
      synchronized(this.childPIDs) {
         this.childPIDs.add(pid);
      }
   }

   private native int getProcessId0();

   private native boolean killProcess0(int var1);

   private native boolean isProcessAlive0(int var1);

   private native boolean changeFileOwnership0(String var1, String var2, String var3);

   private native int createProcess0(byte[] var1, int var2, byte[] var3, int var4, String var5, String var6) throws IOException;

   private native int waitNonBlocking0(int var1);

   class ReaperTask extends TimerTask {
      public void run() {
         synchronized(UnixProcessControl.this.childPIDs) {
            if (UnixProcessControl.this.childPIDs.size() != 0) {
               Iterator pids = UnixProcessControl.this.childPIDs.iterator();

               while(pids.hasNext()) {
                  Integer pid = (Integer)pids.next();
                  int ret = UnixProcessControl.this.waitNonBlocking0(pid);
                  if (ret != 0 && ret != -1 && ret == pid) {
                     pids.remove();
                  }
               }

            }
         }
      }
   }
}
