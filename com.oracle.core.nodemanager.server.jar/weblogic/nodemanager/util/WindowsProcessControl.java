package weblogic.nodemanager.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class WindowsProcessControl extends ProcessControl {
   WindowsProcessControl() throws UnsatisfiedLinkError {
      System.loadLibrary("nodemanager");
   }

   public String getProcessId() {
      return String.valueOf(this.getProcessId0());
   }

   public boolean killProcess(String pid) {
      try {
         return this.killProcess0(Integer.parseInt(pid));
      } catch (NumberFormatException var3) {
         throw new IllegalArgumentException("Invalid pid format");
      }
   }

   public boolean isProcessAlive(String pid) {
      try {
         return this.isProcessAlive0(Integer.parseInt(pid));
      } catch (NumberFormatException var3) {
         throw new IllegalArgumentException("Invalid pid format");
      }
   }

   public String createProcess(String[] cmd, Map env, File dir, File out) throws IOException {
      String dirPath = null;
      String outPath = null;
      String envBlock = null;
      if (cmd.length < 1) {
         throw new IndexOutOfBoundsException();
      } else {
         cmd[0] = (new File(cmd[0])).getPath();
         if (dir != null) {
            dirPath = dir.getAbsolutePath();
         }

         if (out != null) {
            outPath = out.getAbsolutePath();
         }

         if (env != null) {
            envBlock = this.getEnvironmentBlock(env);
         }

         int pid = this.createProcess0((String)null, this.getCommandLine(cmd), envBlock, dirPath, outPath);
         return String.valueOf(pid);
      }
   }

   private String getCommandLine(String[] cmd) {
      StringBuffer sb = new StringBuffer(256);
      cmd[0] = (new File(cmd[0])).getPath();

      for(int i = 0; i < cmd.length; ++i) {
         if (i > 0) {
            sb.append(' ');
         }

         String arg = cmd[i];
         if ((arg.indexOf(32) >= 0 || arg.indexOf(9) >= 0) && arg.indexOf(34) < 0) {
            sb.append('"').append(arg).append('"');
         } else {
            sb.append(arg);
         }
      }

      return sb.toString();
   }

   private String getEnvironmentBlock(Map env) {
      List list = new ArrayList(env.entrySet());
      Collections.sort(list, new Comparator() {
         public int compare(Object o1, Object o2) {
            Map.Entry e1 = (Map.Entry)o1;
            Map.Entry e2 = (Map.Entry)o2;
            return ((String)e1.getKey()).compareToIgnoreCase((String)e2.getKey());
         }
      });
      StringBuffer sb = new StringBuffer(list.size() * 32);
      Iterator it = list.iterator();

      while(it.hasNext()) {
         Map.Entry e = (Map.Entry)it.next();
         sb.append((String)e.getKey()).append('=').append((String)e.getValue()).append('\u0000');
      }

      sb.append('\u0000');
      return sb.toString();
   }

   private native int getProcessId0();

   private native boolean killProcess0(int var1);

   private native boolean isProcessAlive0(int var1);

   private native int createProcess0(String var1, String var2, String var3, String var4, String var5) throws IOException;
}
