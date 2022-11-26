package weblogic.nodemanager.common;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.logging.Level;
import weblogic.nodemanager.server.NMServer;
import weblogic.nodemanager.util.ConcurrentFile;
import weblogic.server.ServerStates;

public class StateInfo implements ServerStates {
   private String state;
   private boolean started;
   private boolean failed;
   private boolean startupAborted;
   private byte[] buf = new byte[256];
   private boolean dirty = false;
   private static final String EOL;
   private static final String SEPARATOR = ":";
   public static final String ABORTED_STARTUP_SUFFIX = "_ON_ABORTED_STARTUP";

   public synchronized void setState(String state) {
      this.state = state;
      if (state != null && state.endsWith("_ON_ABORTED_STARTUP")) {
         this.startupAborted = true;
      }

      this.dirty = true;
   }

   public synchronized void setTemporaryState(String state) {
      this.state = state;
      if (state != null && state.endsWith("_ON_ABORTED_STARTUP")) {
         this.startupAborted = true;
      }

      this.dirty = false;
   }

   public synchronized void set(String state, boolean started, boolean failed) {
      this.state = state;
      this.started = started;
      this.failed = failed;
      if (state != null && state.endsWith("_ON_ABORTED_STARTUP")) {
         this.startupAborted = true;
      }

      this.dirty = true;
   }

   public synchronized String getState() {
      return this.state != null && this.state.endsWith("_ON_ABORTED_STARTUP") ? this.state.substring(0, this.state.indexOf("_ON_ABORTED_STARTUP")) : this.state;
   }

   public synchronized void setStarted(boolean started) {
      this.started = started;
      this.dirty = true;
   }

   public synchronized boolean isStarted() {
      return this.started;
   }

   public synchronized boolean isStartupAborted() {
      return this.startupAborted;
   }

   public synchronized void setFailed(boolean failed) {
      this.failed = failed;
      this.dirty = true;
   }

   public synchronized boolean isFailed() {
      return this.failed;
   }

   public synchronized void load(ConcurrentFile file) throws IOException {
      if (!this.dirty) {
         if (Level.ALL.equals(NMServer.nmLog.getLevel())) {
            NMServer.nmLog.finest("Reading StateFile: " + file + " for server state");
         }

         int len = file.read(ByteBuffer.wrap(this.buf));
         String s = (new String(this.buf, 0, len)).trim();
         String[] fields = s.split(":");
         if (fields.length != 3) {
            throw new IOException("Invalid state file format. State file contents: " + s);
         } else {
            this.state = fields[0].toUpperCase(Locale.ENGLISH);
            this.started = fields[1].toUpperCase(Locale.ENGLISH).equals("Y");
            this.failed = fields[2].toUpperCase(Locale.ENGLISH).equals("Y");
            if (this.state.endsWith("_ON_ABORTED_STARTUP")) {
               this.startupAborted = true;
            }

         }
      }
   }

   public synchronized void save(ConcurrentFile file) throws IOException {
      StateInfoWriter.writeStateInfo(file, this.state, this.started, this.failed);
      this.dirty = false;
   }

   public synchronized void reset() {
      this.state = null;
      this.started = false;
      this.failed = false;
      this.dirty = false;
   }

   public String toString() {
      return "[state=" + this.state + ",started=" + Boolean.toString(this.started) + ",failed=" + Boolean.toString(this.failed) + "]";
   }

   public static boolean adjustedShutDownState(StateInfo stateInfo) {
      boolean shuttingdown = "SHUTTING_DOWN".equals(stateInfo.getState()) || "FORCE_SHUTTING_DOWN".equals(stateInfo.getState());
      if (!stateInfo.isFailed()) {
         if (shuttingdown) {
            stateInfo.setState("SHUTDOWN");
            return true;
         }

         if ("FAILED".equals(stateInfo.getState())) {
            stateInfo.setState("FAILED_NOT_RESTARTABLE");
            return true;
         }
      }

      return false;
   }

   public static boolean adjustedFailedStateWhenProcessDied(StateInfo stateInfo) {
      if (!"SHUTDOWN".equals(stateInfo.getState()) && !"FAILED_NOT_RESTARTABLE".equals(stateInfo.getState())) {
         stateInfo.setState("FAILED_NOT_RESTARTABLE");
         stateInfo.setFailed(true);
         return true;
      } else {
         return false;
      }
   }

   static {
      EOL = StateInfoWriter.EOL;
   }
}
