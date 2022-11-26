package weblogic.protocol;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;
import weblogic.utils.UnsyncStringBuffer;
import weblogic.utils.net.InetAddressHelper;

@Service
public class ClusterAddressHelper implements ClusterAddressCollaborator {
   private static final String PARTITION_NAME_QUERY = "/?partitionName=";
   private static boolean debug = false;
   private boolean isInitialized = false;
   private boolean needsDynamicConstruction = false;
   private boolean needToAppendPort = false;
   private int membersInList = 3;
   private List members = Collections.synchronizedList(new ArrayList());
   private String clusterAddressConfigured;

   public String getClusterAddressURL(ServerChannel sc) {
      return sc.getProtocolPrefix() + "://" + this.getClusterAddress(sc);
   }

   final void initialize() {
      if (this.clusterAddressConfigured != null && this.clusterAddressConfigured.length() != 0) {
         this.verifyClusterAddress(this.clusterAddressConfigured);
      } else {
         this.needsDynamicConstruction = true;
      }

      if (this.needsDynamicConstruction) {
         this.members.add(LocalServerIdentity.getIdentity());
      }

      this.isInitialized = true;
      this.debugSay(" Initialized ClusterAddressHelper with clusterAddressConfigured to= " + this.clusterAddressConfigured + " : membersInList= " + this.membersInList);
   }

   private final String getClusterAddress(ServerChannel sc) {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      boolean isGlobalRuntime = cic.isGlobalRuntime();
      String clusterAddress;
      if (!this.isInitialized) {
         clusterAddress = sc.getPublicAddress() + ':' + sc.getPublicPort();
         if (!isGlobalRuntime) {
            clusterAddress = clusterAddress + "/?partitionName=" + cic.getPartitionName();
         }

         return clusterAddress;
      } else if (this.needsDynamicConstruction) {
         if (isGlobalRuntime) {
            return this.constructClusterAddress(sc);
         } else {
            clusterAddress = this.constructClusterAddress(sc);
            clusterAddress = clusterAddress + "/?partitionName=" + cic.getPartitionName();
            return clusterAddress;
         }
      } else if (!this.needToAppendPort) {
         return isGlobalRuntime ? this.clusterAddressConfigured : this.clusterAddressConfigured + "/?partitionName=" + cic.getPartitionName();
      } else {
         clusterAddress = this.clusterAddressConfigured + ':' + sc.getPublicPort();
         if (!isGlobalRuntime) {
            clusterAddress = clusterAddress + "/?partitionName=" + cic.getPartitionName();
         }

         return clusterAddress;
      }
   }

   private final String constructClusterAddress(ServerChannel sc) {
      List selected = this.selectServerListRandomly();
      List hostPortList = this.getHostPortInfo(selected, sc);
      HostPortInfo[] hostPortInfos = new HostPortInfo[hostPortList.size()];
      hostPortInfos = (HostPortInfo[])((HostPortInfo[])hostPortList.toArray(hostPortInfos));
      boolean hasMixedPorts = this.hasMixedPorts(hostPortInfos);
      UnsyncStringBuffer sb = new UnsyncStringBuffer();

      for(int i = 0; i < hostPortInfos.length; ++i) {
         if (i != 0) {
            sb.append(",");
         }

         if (hasMixedPorts) {
            sb.append(hostPortInfos[i].getHostPort());
         } else {
            sb.append(hostPortInfos[i].getHost());
         }
      }

      if (!hasMixedPorts) {
         sb.append(":").append(sc.getPublicPort()).toString();
      }

      this.debugSay(" +++ constructClusterAddress() : " + sb.toString());
      return sb.toString();
   }

   private boolean needToAppendPort() {
      return this.needToAppendPort;
   }

   private boolean hasMixedPorts(HostPortInfo[] hostPortInfos) {
      if (hostPortInfos.length == 1) {
         this.needToAppendPort = true;
         return false;
      } else {
         int firstPort = hostPortInfos[0].getPort();

         for(int i = 1; i < hostPortInfos.length; ++i) {
            if (firstPort != hostPortInfos[i].getPort()) {
               this.needToAppendPort = false;
               return true;
            }
         }

         this.needToAppendPort = true;
         return false;
      }
   }

   private List getHostPortInfo(List selected, ServerChannel sc) {
      List hostPortList = new ArrayList();
      Iterator iter = selected.iterator();

      while(iter.hasNext()) {
         ServerIdentity id = (ServerIdentity)iter.next();
         String eachHost;
         int eachPort;
         if (id.isLocal()) {
            this.debugSay(" +++ got local server channel : " + sc);
            eachHost = sc.getPublicAddress();
            eachPort = sc.getPublicPort();
         } else {
            ServerChannel eachSC = ServerChannelManager.findServerChannel(id, sc.getProtocol(), sc.getChannelName());
            this.debugSay(" +++ got server channel : " + eachSC);
            if (eachSC == null) {
               eachSC = ServerChannelManager.findServerChannel(id, sc.getProtocol());
               this.debugSay(" +++ got server channel[1] : " + eachSC);
               if (eachSC == null) {
                  eachSC = ServerChannelManager.findServerChannel(id);
                  this.debugSay(" +++ got server channel[2] : " + eachSC);
                  if (eachSC == null) {
                     throw new AssertionError("ServerChannel for id : " + id + " is null");
                  }
               }
            }

            eachHost = eachSC.getPublicAddress();
            eachPort = eachSC.getPublicPort();
         }

         this.debugSay(" +++ eachHost, eachPort : " + eachHost + "," + eachPort + " : for id --- " + id);
         hostPortList.add(this.getHostPortInfo(eachHost, eachPort, eachPort != -1));
      }

      this.dumpList(" +++ getHostPortInfo() : ", (List)hostPortList);
      return hostPortList;
   }

   private List selectServerListRandomly() {
      this.debugSay(" Selecting Server List Randomly...");
      ServerIdentity[] arrayOfClusterMembers = new ServerIdentity[this.members.size()];
      arrayOfClusterMembers = (ServerIdentity[])((ServerIdentity[])this.members.toArray(arrayOfClusterMembers));
      Random random = new Random(System.currentTimeMillis());
      int startIndex = random.nextInt(arrayOfClusterMembers.length);
      int counter = arrayOfClusterMembers.length <= this.membersInList ? arrayOfClusterMembers.length : this.membersInList;
      List selectedList = new ArrayList();
      int i = 0;

      for(int arrayCounter = startIndex; i < counter; ++i) {
         if (arrayCounter >= arrayOfClusterMembers.length) {
            arrayCounter = 0;
         }

         selectedList.add(arrayOfClusterMembers[arrayCounter]);
         ++arrayCounter;
      }

      this.dumpList(" +++ selectServerListRandomly() :", (List)selectedList);
      return selectedList;
   }

   private void verifyClusterAddress(String clusterAddress) {
      if (clusterAddress != null && clusterAddress.length() != 0) {
         int nodeNumber = false;
         String[] nodeAddress = StringUtils.splitCompletely(clusterAddress, ",", false);
         if (nodeAddress.length > 1) {
            try {
               List hostPortInfos = this.constructHostPortInfos(clusterAddress);
               boolean allHasPorts = this.allHasPorts(hostPortInfos);
               boolean allHasNoPorts = this.allHasNoPorts(hostPortInfos);
               if (!allHasPorts && !allHasNoPorts) {
                  this.needsDynamicConstruction = true;
                  return;
               }

               if (allHasPorts) {
                  this.needToAppendPort = false;
                  return;
               }

               if (allHasNoPorts) {
                  this.needToAppendPort = true;
                  return;
               }
            } catch (NumberFormatException var8) {
               ClusterLogger.logInvalidConfiguredClusterAddress(clusterAddress);
               this.needsDynamicConstruction = true;
            }
         } else {
            try {
               InetAddress.getByName(clusterAddress);
               this.needToAppendPort = true;
            } catch (UnknownHostException var7) {
               ClusterLogger.logCannotResolveClusterAddressWarning(clusterAddress);
               this.needsDynamicConstruction = true;
            }
         }
      }

   }

   List constructHostPortInfos(String clusterAddress) {
      List hostPortInfos = new ArrayList();
      int nodeNumber = 0;
      String[] nodeAddress = StringUtils.splitCompletely(clusterAddress, ",", false);
      if (nodeAddress.length <= 1) {
         throw new AssertionError("ClusterAddress has dns name when it isalready verified as non dns name");
      } else {
         while(nodeNumber < nodeAddress.length) {
            HostPortInfo hostPortInfo = this.constructHostPortInfo(nodeAddress[nodeNumber]);
            hostPortInfos.add(hostPortInfo);
            ++nodeNumber;
         }

         return hostPortInfos;
      }
   }

   private HostPortInfo constructHostPortInfo(String nodeAddressPort) {
      this.debugSay(" nodeAddressPort is " + nodeAddressPort);
      char delimiter = 58;
      int port = -1;
      int portIndex = -1;
      String nodeNameOrAddress = nodeAddressPort;
      boolean isIPv6 = false;
      int firstBraketPos = nodeAddressPort.indexOf(91);
      int lastBraketPos = nodeAddressPort.indexOf(93);
      if (firstBraketPos > -1 && lastBraketPos > 0) {
         portIndex = nodeAddressPort.indexOf(delimiter, lastBraketPos);
         isIPv6 = true;
      } else if (firstBraketPos == -1 && lastBraketPos == -1) {
         if (InetAddressHelper.isIPV6Address(nodeAddressPort)) {
            portIndex = nodeAddressPort.lastIndexOf(delimiter);
            isIPv6 = true;
         } else {
            portIndex = nodeAddressPort.indexOf(delimiter);
         }
      }

      this.debugSay(" portIndex is " + portIndex);
      if (portIndex > -1) {
         nodeNameOrAddress = nodeAddressPort.substring(0, portIndex);
         if (portIndex + 1 != nodeAddressPort.length()) {
            String nodePortString = nodeAddressPort.substring(portIndex + 1);

            try {
               port = Integer.parseInt(nodePortString);
            } catch (NumberFormatException var12) {
               NumberFormatException exception = var12;
               if (isIPv6) {
                  exception = new NumberFormatException("IPV6 addresses have to be specified in [a:b:c:d:e:f:g]:port format");
               }

               throw exception;
            }
         }
      }

      return this.getHostPortInfo(nodeNameOrAddress, port, port > 0);
   }

   private HostPortInfo getHostPortInfo(final String host, final int port, final boolean hasPort) {
      return new HostPortInfo() {
         public String getHost() {
            return host;
         }

         public int getPort() {
            return port;
         }

         public boolean hasPort() {
            return hasPort;
         }

         public String getHostPort() {
            return host + ":" + port;
         }

         public String toString() {
            return "HostPortInfo(" + this.getHost() + "," + this.getPort() + "," + this.hasPort() + ")";
         }
      };
   }

   private boolean allHasPorts(List allInfos) {
      Iterator iter = allInfos.iterator();

      do {
         if (!iter.hasNext()) {
            return true;
         }
      } while(((HostPortInfo)iter.next()).hasPort());

      return false;
   }

   private boolean allHasNoPorts(List allInfos) {
      Iterator iter = allInfos.iterator();

      do {
         if (!iter.hasNext()) {
            return true;
         }
      } while(!((HostPortInfo)iter.next()).hasPort());

      return false;
   }

   private void debugSay(String msg) {
      if (debug) {
         Debug.say(" --- " + msg);
      }

   }

   private void dumpList(String method, List list) {
      if (debug) {
         StringBuffer sb = new StringBuffer();
         if (method != null) {
            sb.append(method);
         }

         Iterator it = list.iterator();
         sb.append("List [");

         while(it.hasNext()) {
            sb.append(it.next().toString()).append(",");
         }

         String returnString = sb.toString().substring(0, sb.toString().lastIndexOf(44));
         returnString = returnString + "]";
         this.debugSay(returnString);
      }
   }

   private void dumpList(String method, Object[] arrayObj) {
      if (debug) {
         StringBuffer sb = new StringBuffer();
         if (method != null) {
            sb.append(method);
         }

         sb.append(" [");

         for(int i = 0; i < arrayObj.length; ++i) {
            if (i != 0) {
               sb.append(",");
            }

            sb.append(arrayObj[i].toString());
         }

         sb.append("]");
         this.debugSay(sb.toString());
      }
   }

   public final void configure(String clusterAddressURL, int numberOfServersToUseInDynamicClusterAddress) {
      this.clusterAddressConfigured = clusterAddressURL;
      this.membersInList = numberOfServersToUseInDynamicClusterAddress;
      this.initialize();
   }

   public void addMember(ServerIdentity memberId) {
      this.debugSay(" Adding a member to the list : " + memberId);
      this.members.add(memberId);
      this.dumpList(" +++ clusterMembersChanged() : ", this.members);
   }

   public void removeMember(ServerIdentity memberId) {
      this.debugSay(" Removing a member to the list : " + memberId);
      this.members.remove(memberId);
      this.dumpList(" +++ clusterMembersChanged() : ", this.members);
   }

   private interface HostPortInfo {
      String getHost();

      int getPort();

      boolean hasPort();

      String getHostPort();
   }
}
