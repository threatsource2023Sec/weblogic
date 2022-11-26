package weblogic.management.utils.jmsdlb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DLServerStatus {
   private static final int DEPLOY_FAILURE_CNT = Integer.getInteger("weblogic.management.utild.dlb.suspect.deploycnt", 10);
   private static final int SUSPECT_TIMEOUT = Integer.getInteger("weblogic.management.utild.dlb.suspect.timeout", 300);
   private final boolean supportSuspect;
   private final String name;
   private final DLId serverID;
   private int version;
   private State state;
   private long stateChangeTime;
   private long knownSuspectTime;
   private long knownStartTime;
   private long lastPlacementTime;
   private int deployFailure;
   Map assignedGroups = new HashMap();
   Set deployFailures = new HashSet();
   private String serverIdentity;
   private DLLoadValue load;
   private static final int DEFAULT_LOAD = 100;

   public DLServerStatus(String name, DLContext context) {
      this.name = name;
      this.supportSuspect = context.getSupportSuspect();
      this.serverID = context.getServerID(name);
      this.knownStartTime = System.currentTimeMillis();
      this.state = DLServerStatus.State.RUNNING;
      this.load = new SimpleLoad();
      this.version = 1;
   }

   public int getVersion() {
      return this.version;
   }

   public boolean matches(String name) {
      return this.name.equals(name);
   }

   public boolean isRunning() {
      this.checkSuspect();
      return this.state == DLServerStatus.State.RUNNING;
   }

   private void checkSuspect() {
      if (this.state == DLServerStatus.State.SUSPECT && System.currentTimeMillis() > this.knownSuspectTime + (long)SUSPECT_TIMEOUT) {
         this.setState(DLServerStatus.State.RUNNING);
      }

   }

   public boolean isSuspect() {
      this.checkSuspect();
      return this.state == DLServerStatus.State.SUSPECT;
   }

   public long suspectTimeout() {
      return this.state == DLServerStatus.State.SUSPECT ? this.knownSuspectTime + (long)SUSPECT_TIMEOUT : 0L;
   }

   public void place(String groupname) {
      long time = System.currentTimeMillis();
      this.lastPlacementTime = time;
      this.assignedGroups.put(groupname, time);
   }

   public void clear(String groupname) {
      this.assignedGroups.remove(groupname);
   }

   public long getLastPlacementTime() {
      return this.lastPlacementTime;
   }

   public long getLastPlacementTime(String groupname) {
      Long l = (Long)this.assignedGroups.remove(groupname);
      return l == null ? -1L : l;
   }

   public void success(String group) {
      this.deployFailures.remove(group);
      this.deployFailure = 0;
   }

   public void fail(String group) {
      this.deployFailures.add(group);
      ++this.deployFailure;
      if (this.supportSuspect && this.deployFailure > DEPLOY_FAILURE_CNT && this.deployFailures.size() > 1) {
         this.setState(DLServerStatus.State.SUSPECT);
      }

   }

   public DLId getServerID() {
      return this.serverID;
   }

   public String getName() {
      return this.name;
   }

   public State getState() {
      return this.state;
   }

   public String toString() {
      return "DLServerTarget{name='" + this.name + '\'' + ", serverID=" + this.serverID + ", state=" + this.state + ", stateChangeTime=" + this.stateChangeTime + ", knownSuspectTime=" + this.knownSuspectTime + ", knownStartTime=" + this.knownStartTime + '}';
   }

   public long getStartTime() {
      return this.knownStartTime;
   }

   public void setState(State newstate) {
      switch (newstate) {
         case RUNNING:
            if (this.state != DLServerStatus.State.RUNNING) {
               ++this.version;
            }

            if (this.knownStartTime == 0L) {
               this.knownStartTime = System.currentTimeMillis();
            }

            if (this.state == DLServerStatus.State.SUSPECT) {
               this.knownSuspectTime = 0L;
            }
            break;
         case DOWN:
         case DOWN_UNKNOWN:
         case REMOVED:
            this.assignedGroups.clear();
            this.knownStartTime = 0L;
            this.knownSuspectTime = 0L;
            this.lastPlacementTime = 0L;
            break;
         case SUSPECT:
            if (this.knownSuspectTime == 0L) {
               this.knownSuspectTime = System.currentTimeMillis();
            }
      }

      if (this.state != newstate) {
         this.stateChangeTime = System.currentTimeMillis();
         this.state = newstate;
      }

   }

   public DLLoadValue getLoad() {
      return this.load;
   }

   public void setLoad(DLLoadValue load) {
      this.load = load;
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return o instanceof DLServerStatus ? ((DLServerStatus)o).name.equals(this.name) : false;
      }
   }

   public String getServerIdentity() {
      return this.serverIdentity;
   }

   public void setServerIdentity(String serverIdentity) {
      this.serverIdentity = serverIdentity;
   }

   private static class SimpleLoad implements DLLoadValue {
      private SimpleLoad() {
      }

      public int getLoad() {
         return 100;
      }

      // $FF: synthetic method
      SimpleLoad(Object x0) {
         this();
      }
   }

   public static enum State {
      RUNNING,
      SUSPECT,
      DOWN_UNKNOWN,
      DOWN,
      REMOVED;
   }
}
