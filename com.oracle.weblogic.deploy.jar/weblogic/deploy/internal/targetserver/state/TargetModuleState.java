package weblogic.deploy.internal.targetserver.state;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.utils.PlatformConstants;

public final class TargetModuleState implements Externalizable {
   private static final long serialVersionUID = 6815593282455552332L;
   private static final String EOL;
   private String moduleId;
   private String targetName;
   private String targetType;
   private String serverName;
   private String curState;
   private String type;
   private String submoduleId;

   public TargetModuleState() {
      this.submoduleId = null;
   }

   TargetModuleState(String module, String submodule, String type, String target, String targetType, String serverName) {
      this(createName(module, submodule), type, target, targetType, serverName);
      this.submoduleId = submodule;
   }

   public TargetModuleState(String module, String type, String target, String targetType, String serverName) {
      this.submoduleId = null;
      this.moduleId = module;
      this.targetName = target;
      this.targetType = targetType;
      this.serverName = serverName;
      this.type = type;
   }

   public static String createName(String module, String submodule) {
      return submodule + "[" + module + "]";
   }

   public static String extractModule(String m) {
      int s = m.indexOf("[");
      return s == -1 ? m : m.substring(s + 1, m.indexOf("]"));
   }

   public static String extractSubmodule(String m) {
      int s = m.indexOf("[");
      return s == -1 ? null : m.substring(0, s);
   }

   public String getCurrentState() {
      return this.curState;
   }

   public void setCurrentState(String state) {
      this.curState = state;
   }

   public String getModuleId() {
      return this.moduleId;
   }

   public String getTargetName() {
      return this.targetName;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getTargetType() {
      return this.targetType;
   }

   public String getType() {
      return this.type;
   }

   public String getSubmoduleId() {
      return this.submoduleId;
   }

   public boolean isSubmodule() {
      return this.getSubmoduleId() != null;
   }

   public String getParentModuleId() {
      return !this.isSubmodule() ? null : extractModule(this.getModuleId());
   }

   public boolean isLogicalTarget() {
      return !this.serverName.equals(this.targetName);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.moduleId);
      out.writeObject(this.targetName);
      out.writeObject(this.targetType);
      out.writeObject(this.serverName);
      out.writeObject(this.curState);
      out.writeObject(this.type);
      out.writeObject(this.submoduleId);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.moduleId = (String)in.readObject();
      this.targetName = (String)in.readObject();
      this.targetType = (String)in.readObject();
      this.serverName = (String)in.readObject();
      this.curState = (String)in.readObject();
      this.type = (String)in.readObject();
      this.submoduleId = (String)in.readObject();
   }

   public void pretty(StringBuffer sb, int tabLevel) {
      ApplicationUtils.printTabs(sb, tabLevel);
      sb.append("Module Id: " + this.moduleId + EOL);
      if (this.submoduleId != null) {
         ApplicationUtils.printTabs(sb, tabLevel);
         sb.append("Sub-Module Id: " + this.submoduleId + EOL);
      }

      if (this.type != null) {
         ApplicationUtils.printTabs(sb, tabLevel);
         sb.append("Module type: " + this.type + EOL);
      }

      ApplicationUtils.printTabs(sb, tabLevel);
      sb.append("Module target: " + this.targetName + EOL);
      ApplicationUtils.printTabs(sb, tabLevel);
      sb.append("Target type: " + this.targetType + EOL);
      if (this.isLogicalTarget()) {
         ApplicationUtils.printTabs(sb, tabLevel);
         sb.append("Server name: " + this.serverName + EOL);
      }

      ApplicationUtils.printTabs(sb, tabLevel);
      sb.append("Current state: " + this.curState + EOL);
   }

   public String toString() {
      StringBuffer result = (new StringBuffer("TargetModuleState[")).append("ModuleId=").append(this.moduleId).append(",TargetName=").append(this.targetName).append("/").append(this.targetType);
      if (this.isLogicalTarget()) {
         result.append("[").append(this.serverName).append("]");
      }

      result.append(",State=").append(this.curState);
      return result.toString();
   }

   static {
      EOL = PlatformConstants.EOL;
   }
}
