package weblogic.deploy.internal.targetserver.state;

import java.io.Serializable;

public final class ModuleTransition implements Serializable {
   private static final long serialVersionUID = 1L;
   private final String curState;
   private final String newState;
   private final String name;
   private final long gentime;
   private final TargetModuleState tm;

   public ModuleTransition(String curSt, String newSt, String name, long time, TargetModuleState t) {
      this.curState = curSt;
      this.newState = newSt;
      this.name = name;
      this.gentime = time;
      this.tm = t;
   }

   public String getCurrentState() {
      return this.curState;
   }

   public String getNewState() {
      return this.newState;
   }

   public String getName() {
      return this.name;
   }

   public long getGenerationTime() {
      return this.gentime;
   }

   public TargetModuleState getModule() {
      return this.tm;
   }
}
