package weblogic.management.deploy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.management.deploy.internal.ApplicationPollerLogger;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;
import weblogic.work.WorkManagerFactory;

public abstract class GenericAppPoller {
   protected static boolean debug = false;
   protected static boolean methodTrace = false;
   protected boolean verbose;
   private boolean searchRecursively;
   private File startDir;
   private HashMap currentRunFileMap;
   private HashMap lastRunFileMap;
   private ArrayList activateFileList;
   private ArrayList deactivateFileList;
   private long pollInterval;
   private boolean runOnce;
   private Timer pollerTimer;
   private String lastRunFilename;
   protected boolean startDirFound;

   public GenericAppPoller(File startDir, boolean searchRecursively, long pollInterval) {
      this(startDir, searchRecursively, pollInterval, (String)null);
   }

   public GenericAppPoller(File startDir, boolean searchRecursively, long pollInterval, String runFileName) {
      this.verbose = false;
      this.lastRunFilename = null;
      this.startDirFound = true;
      if (methodTrace) {
         Debug.say("<init>");
      }

      this.searchRecursively = searchRecursively;
      this.startDir = startDir;
      if (!startDir.exists()) {
         this.startDirFound = false;
      }

      if (pollInterval == 0L) {
         this.runOnce = true;
      } else {
         this.runOnce = false;
      }

      this.pollInterval = pollInterval;
      this.lastRunFilename = runFileName;
   }

   public abstract void doActivate();

   public abstract void doDeactivate();

   public final void runInSameThread() {
      if (methodTrace) {
         Debug.say("run");
      }

      if (!this.runOnce) {
         if (this.verbose) {
            Debug.say("running in polling mode");
         }

         while(true) {
            while(true) {
               try {
                  if (this.verbose) {
                     Debug.say("evaluating " + this.startDir.getName());
                  }

                  this.doit();
                  if (this.verbose) {
                     Debug.say("done evaluating " + this.startDir.getName());
                  }

                  Thread.sleep(this.pollInterval);
               } catch (InterruptedException var2) {
               } catch (Throwable var3) {
                  ApplicationPollerLogger.logUncaughtThrowable(var3);
               }
            }
         }
      }

      if (this.verbose) {
         Debug.say("running in runOnce mode");
      }

      this.doit();
   }

   public void start() {
      if (methodTrace) {
         Debug.say("run");
      }

      if (this.runOnce) {
         Runnable request = new Runnable() {
            public void run() {
               if (GenericAppPoller.this.verbose) {
                  Debug.say("running in runOnce mode");
               }

               GenericAppPoller.this.doit();
            }
         };
         WorkManagerFactory.getInstance().getSystem().schedule(request);
      } else {
         if (this.verbose) {
            Debug.say("running in polling mode");
         }

         this.startPollerTimerListener();
      }

   }

   public void setSleepInterval(long pollInterval) {
      this.pollInterval = pollInterval;
   }

   protected boolean shouldActivate(File file) {
      boolean ret = false;
      String fileCanPath = file.getAbsolutePath();

      try {
         file = file.getCanonicalFile();
         fileCanPath = file.getCanonicalPath();
      } catch (IOException var6) {
         ApplicationPollerLogger.logIOException(var6);
      }

      if (debug) {
         Debug.say("SHOULD ACTIVATE: " + fileCanPath);
      }

      if (this.lastRunFileMap.containsKey(fileCanPath)) {
         long ts = (Long)this.lastRunFileMap.get(fileCanPath);
         if (file.lastModified() > ts) {
            ret = true;
         }
      } else {
         ret = true;
      }

      return ret;
   }

   protected boolean shouldDeactivate(File file) {
      String fileCanPath = file.getAbsolutePath();

      try {
         file = file.getCanonicalFile();
         fileCanPath = file.getCanonicalPath();
      } catch (IOException var4) {
         ApplicationPollerLogger.logIOException(var4);
      }

      if (debug) {
         Debug.say("SHOULD DEACTIVATE: " + file);
      }

      return !this.currentRunFileMap.containsKey(fileCanPath);
   }

   protected final ArrayList getActivateFileList() {
      if (methodTrace) {
         Debug.say("getActivateFileList");
      }

      return this.activateFileList;
   }

   protected final ArrayList getDeactivateFileList() {
      if (methodTrace) {
         Debug.say("getDeactivateFileList");
      }

      return this.deactivateFileList;
   }

   protected final void doit() {
      if (methodTrace) {
         Debug.say("doit");
      }

      try {
         this.getLastRunFileMap();
         this.activateFileList = new ArrayList();
         this.deactivateFileList = new ArrayList();
         this.currentRunFileMap = new HashMap();
         if (debug) {
            Debug.say("START DIR " + this.startDir);
         }

         this.setCurrentRunMap(this.startDir);
      } catch (IOException var4) {
         ApplicationPollerLogger.logIOException(var4);
      }

      this.generateActivateFileList();
      this.generateDeactivateFileList();
      if (debug) {
         this.dumpHashMap("CURRENT RUN FILELIST", this.currentRunFileMap);
         this.dumpArrayList("ACTIVATE FILE LIST: ", this.activateFileList);
         this.dumpHashMap("LAST RUN FILELIST", this.lastRunFileMap);
         this.dumpArrayList("DEACTIVATE FILE LIST: ", this.deactivateFileList);
      }

      try {
         this.doActivate();
         this.doDeactivate();
      } catch (Throwable var3) {
         ApplicationPollerLogger.logUncaughtThrowable(var3);
      }

      try {
         this.setLastRunFileMap();
      } catch (IOException var2) {
         ApplicationPollerLogger.logIOException(var2);
      }

   }

   protected File getStartDir() {
      return this.startDir;
   }

   private void setCurrentRunMap(File startDir) throws IOException {
      if (methodTrace) {
         Debug.say("setCurrentRunMap");
      }

      File[] ls = startDir.listFiles();

      for(int i = 0; ls != null && i < ls.length; ++i) {
         File file = ls[i];
         String fileCanPath = file.getAbsolutePath();
         file = file.getCanonicalFile();
         fileCanPath = file.getCanonicalPath();
         if (this.searchRecursively && file.isDirectory()) {
            this.setCurrentRunMap(file);
         }

         if (!this.ignoreFile(file)) {
            long ts = file.lastModified();
            this.currentRunFileMap.put(fileCanPath, new Long(ts));
         }
      }

   }

   void removeFileFromMap(File file) {
      String fileCanPath = file.getAbsolutePath();

      try {
         file = file.getCanonicalFile();
         fileCanPath = file.getCanonicalPath();
      } catch (IOException var4) {
         if (debug) {
            Debug.say("Problem getting canonical file and path for file '" + file + "'\nwill try to remove: " + fileCanPath + "\nexception message: " + var4.getMessage());
         }
      }

      Object val = this.currentRunFileMap.remove(fileCanPath);
      if (val == null && debug) {
         this.dumpHashMap("Current run file map does not contain key: " + fileCanPath, this.currentRunFileMap);
      }

   }

   public long getPollInterval() {
      return this.pollInterval;
   }

   private void generateActivateFileList() {
      if (methodTrace) {
         Debug.say("generateActivateFileList");
      }

      Set keyset = this.currentRunFileMap.keySet();
      Iterator i = keyset.iterator();

      while(i.hasNext()) {
         String fn = (String)i.next();
         if (this.shouldActivate(new File(fn))) {
            this.activateFileList.add(fn);
         }
      }

   }

   private void generateDeactivateFileList() {
      if (methodTrace) {
         Debug.say("generateDeactivateFileList");
      }

      String fn = null;
      Set keyset = this.lastRunFileMap.keySet();
      Iterator i = keyset.iterator();

      while(i.hasNext()) {
         fn = (String)i.next();
         if (this.shouldDeactivate(new File(fn))) {
            this.deactivateFileList.add(fn);
         }
      }

   }

   private boolean ignoreFile(File f) {
      if (methodTrace) {
         Debug.say("ignoreFile");
      }

      return f.getName().equals(this.lastRunFilename);
   }

   private void getLastRunFileMap() throws IOException {
      if (methodTrace) {
         Debug.say("getLastRunFileMap");
      }

      if (this.lastRunFileMap == null && this.lastRunFilename != null) {
         this.lastRunFileMap = new HashMap();
         File lastRunFile = new File(this.lastRunFilename);
         if (!lastRunFile.exists()) {
            if (debug) {
               Debug.say("no previous delete file.");
            }
         } else {
            if (debug) {
               Debug.say("reading file " + this.lastRunFilename);
            }

            BufferedReader reader = null;

            try {
               reader = new BufferedReader(new FileReader(new File(this.lastRunFilename)));
               String data = null;
               boolean done = false;

               while(!done) {
                  data = reader.readLine();
                  if (data == null) {
                     done = true;
                  } else {
                     String[] tmp = StringUtils.split(data, '\t');
                     String fn = new String(tmp[0]);
                     long ts = Long.parseLong(tmp[1]);
                     this.lastRunFileMap.put(fn, new Long(ts));
                  }
               }
            } finally {
               if (reader != null) {
                  try {
                     reader.close();
                  } catch (Exception var14) {
                  }
               }

            }
         }
      }

   }

   private void setLastRunFileMap() throws IOException {
      if (methodTrace) {
         Debug.say("setLastRunFileMap");
      }

      if (!this.lastRunFileMap.equals(this.currentRunFileMap)) {
         this.lastRunFileMap = this.currentRunFileMap;
         if (this.lastRunFilename != null) {
            if (debug) {
               Debug.say("writing file " + this.lastRunFilename);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(this.lastRunFilename)));
            String data = new String();
            Set keyset = this.lastRunFileMap.keySet();

            for(Iterator i = keyset.iterator(); i.hasNext(); data = data + "\n") {
               String fn = (String)i.next();
               Long ts = (Long)this.lastRunFileMap.get(fn);
               data = data + fn;
               data = data + "\t";
               data = data + ts.toString();
            }

            writer.write(data);
            writer.flush();
            writer.close();
         }
      }

   }

   private void startPollerTimerListener() {
      if (this.pollInterval > 0L) {
         this.pollerTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new PollerTimerListener(), this.pollInterval, this.pollInterval);
      }
   }

   private void cancelPollerTimerListener() {
      if (this.pollerTimer != null) {
         this.pollerTimer.cancel();
      }

   }

   protected final void setCheckPoint(File f, long time) {
      this.currentRunFileMap.put(f.getPath(), new Long(time));
   }

   protected final Long getLastCheckPoint(File f) {
      return this.lastRunFileMap != null ? (Long)this.lastRunFileMap.get(f.getPath()) : null;
   }

   protected final void dumpArrayList(String msg, ArrayList rl) {
      StringBuffer sb = null;
      if (msg == null) {
         sb = new StringBuffer("DUMPING ARRAY LIST\n");
      } else {
         sb = new StringBuffer(msg + "\n");
      }

      sb.append("ARRAY LIST HAS: " + rl.size() + " ELEMENTS\n");
      Iterator i = rl.iterator();

      while(i.hasNext()) {
         Object o = i.next();
         sb.append(o.toString());
         sb.append("\n");
      }

      Debug.say(sb.toString());
   }

   protected final void dumpHashMap(String msg, Map hashmap) {
      StringBuffer sb = null;
      if (msg == null) {
         sb = new StringBuffer("DUMPING HASH SET\n");
      } else {
         sb = new StringBuffer(msg + "\n");
      }

      sb.append("HASH SET HAS: " + hashmap.size() + " ELEMENTS\n");
      Set keys = hashmap.keySet();
      Iterator i = keys.iterator();

      while(i.hasNext()) {
         Object key = i.next();
         sb.append(key.toString());
         sb.append(": ");
         sb.append(hashmap.get(key));
         sb.append("\n");
      }

      Debug.say(sb.toString());
   }

   protected final void dumpStringArray(String msg, String[] str) {
      StringBuffer sb = null;
      if (msg == null) {
         sb = new StringBuffer("DUMPING STRING ARRAY\n");
      } else {
         sb = new StringBuffer(msg + "\n");
      }

      for(int i = 0; i < str.length; ++i) {
         sb.append(" ");
         sb.append(str[i]);
         if (i + 1 < str.length) {
            sb.append("\n");
         }
      }

      Debug.say(sb.toString());
   }

   private class PollerTimerListener implements TimerListener {
      private PollerTimerListener() {
      }

      public final void timerExpired(Timer timer) {
         try {
            if (GenericAppPoller.this.verbose) {
               Debug.say("evaluating " + GenericAppPoller.this.startDir.getName());
            }

            GenericAppPoller.this.doit();
            if (GenericAppPoller.this.verbose) {
               Debug.say("done evaluating " + GenericAppPoller.this.startDir.getName());
            }
         } catch (Throwable var3) {
            ApplicationPollerLogger.logUncaughtThrowable(var3);
         }

      }

      // $FF: synthetic method
      PollerTimerListener(Object x1) {
         this();
      }
   }
}
