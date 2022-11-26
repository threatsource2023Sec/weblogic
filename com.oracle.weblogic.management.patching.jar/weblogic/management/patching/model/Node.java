package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import weblogic.management.patching.ApplicationProperties;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;

public class Node implements TargetGroup, AdminAware, Serializable {
   private String nodeName;
   private MachineInfo machineInfo;
   private SortedMap serverGroups;
   DomainModel domainModel;
   private List noStageApplicationProperties;
   private boolean isTargeted;

   public Node(String nodeName) {
      this.isTargeted = false;
      this.nodeName = nodeName;
      this.machineInfo = new MachineInfo(nodeName);
      this.serverGroups = new TreeMap();
      this.noStageApplicationProperties = new ArrayList();
   }

   public Node(MachineInfo machineInfo) {
      this(machineInfo.getMachineName());
      this.machineInfo = machineInfo;
      this.noStageApplicationProperties = new ArrayList();
   }

   public String getNodeName() {
      return this.nodeName;
   }

   public void addServerGroup(ServerGroup serverGroup) {
      if (this.serverGroups.containsKey(serverGroup.getGroupName())) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().nodeContainsServerGroup(this.nodeName, serverGroup.getGroupName()));
      } else {
         this.serverGroups.put(serverGroup.getGroupName(), serverGroup);
         serverGroup.setNode(this);
      }
   }

   public Map getServerGroups() {
      return this.serverGroups;
   }

   public ServerGroup getServerGroup(String serverGroupName) {
      return (ServerGroup)this.serverGroups.get(serverGroupName);
   }

   public boolean hasServerGroup(String serverGroupName) {
      return this.serverGroups.containsKey(serverGroupName);
   }

   public boolean isAdmin() {
      boolean isAdmin = false;
      Iterator var2 = this.serverGroups.values().iterator();

      while(var2.hasNext()) {
         ServerGroup serverGroup = (ServerGroup)var2.next();
         if (serverGroup.isAdmin()) {
            isAdmin = true;
            break;
         }
      }

      return isAdmin;
   }

   public Server getAdminServer() {
      Server adminServer = null;
      Iterator var2 = this.serverGroups.values().iterator();

      while(var2.hasNext()) {
         ServerGroup serverGroup = (ServerGroup)var2.next();
         if (serverGroup.isAdmin()) {
            adminServer = serverGroup.getAdminServer();
            break;
         }
      }

      return adminServer;
   }

   public void setTargeted(boolean isTargeted) {
      this.isTargeted = isTargeted;
   }

   public boolean isTargeted() {
      return this.isTargeted;
   }

   public int getNumTargetedMembers() {
      int count = 0;
      Iterator var2 = this.serverGroups.values().iterator();

      while(var2.hasNext()) {
         ServerGroup g = (ServerGroup)var2.next();
         if (g.isTargeted()) {
            ++count;
         }
      }

      return count;
   }

   public int getNumUntargetedMembers() {
      int count = 0;
      Iterator var2 = this.serverGroups.values().iterator();

      while(var2.hasNext()) {
         ServerGroup g = (ServerGroup)var2.next();
         if (!g.isTargeted()) {
            ++count;
         }
      }

      return count;
   }

   public void applyTargetedAndPropagateValue() {
      this.setTargeted(true);
   }

   public void setDomain(DomainModel domainModel) {
      this.domainModel = domainModel;
   }

   public DomainModel getDomainModel() {
      return this.domainModel;
   }

   public MachineInfo getMachineInfo() {
      return this.machineInfo;
   }

   public List getNoStageApplicationProperties() {
      return this.noStageApplicationProperties;
   }

   public void addNoStageApplicationProperties(ApplicationProperties app) {
      this.noStageApplicationProperties.add(app);
   }
}
