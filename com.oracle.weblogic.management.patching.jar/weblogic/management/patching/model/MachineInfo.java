package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class MachineInfo implements Serializable, Comparable {
   private static final long serialVersionUID = 792360873179381755L;
   private String machineName;
   private ArrayList serverInfos;
   private boolean isAdminMachine;
   private boolean isClusterMachine;
   private String pathToOracleHome = "";
   private String listenAddress = null;
   public static final String OPEN_TYPE_NAME = "MachineInfo";
   public static final String OPEN_DESCRIPTION = "This object represents a Node.";
   public static final String ITEM_NODE_NAME = "NodeName";
   public static final String ITEM_OH_PATH = "OracleHomePath";
   public static final String ITEM_LISTEN_ADDRESS = "ListenAddress";
   private static String[] itemNames = new String[]{"NodeName", "OracleHomePath", "ListenAddress"};
   private static String[] itemDescriptions = new String[]{"The name of the node.", "The location of OracleHome on this node.", "The listen address of the NodeManager associated with this node."};

   public MachineInfo(String machineName) {
      this.machineName = machineName;
      this.serverInfos = new ArrayList();
      this.isAdminMachine = false;
   }

   public String getMachineName() {
      return this.machineName;
   }

   public void setMachineName(String machineName) {
      this.machineName = machineName;
   }

   public void addServerInfo(ServerInfo serverInfo) {
      this.serverInfos.add(serverInfo);
      Collections.sort(this.serverInfos);
   }

   public List getServerInfos() {
      return this.serverInfos;
   }

   public boolean isAdminMachine() {
      return this.isAdminMachine;
   }

   public void setAdminMachine(boolean isAdminMachine) {
      this.isAdminMachine = isAdminMachine;
   }

   public boolean isClusterMachine() {
      return this.isClusterMachine;
   }

   public void setClusterMachine(boolean isClusterMachine) {
      this.isClusterMachine = isClusterMachine;
   }

   public Object getPathToOracleHome() {
      return this.pathToOracleHome;
   }

   public void setPathToOracleHome(String path) {
      this.pathToOracleHome = path;
   }

   public String getListenAddress() {
      return this.listenAddress;
   }

   public void setListenAddress(String listenAddress) {
      this.listenAddress = listenAddress;
   }

   public int compareTo(MachineInfo other) {
      int result;
      if (other == null) {
         result = 1;
      } else if (this.machineName == null) {
         if (other.machineName == null) {
            result = 0;
         } else {
            result = -1;
         }
      } else {
         result = this.machineName.compareTo(other.machineName);
      }

      return result;
   }

   protected Map getCompositeDataMap() throws OpenDataException {
      Map data = new HashMap();
      data.put("NodeName", this.getMachineName());
      data.put("OracleHomePath", this.getPathToOracleHome());
      data.put("ListenAddress", this.getListenAddress());
      return data;
   }

   protected CompositeType getCompositeType() throws OpenDataException {
      OpenType[] itemTypes = new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.STRING};
      CompositeType ct = new CompositeType("MachineInfo", "This object represents a Node.", itemNames, itemDescriptions, itemTypes);
      return ct;
   }

   public CompositeData toCompositeData() throws OpenDataException {
      CompositeDataSupport cds = new CompositeDataSupport(this.getCompositeType(), this.getCompositeDataMap());
      return cds;
   }
}
