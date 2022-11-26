package weblogic.protocol;

import java.net.MalformedURLException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.security.auth.login.LoginException;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServices.Locator;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;

public class ClusterURLImpl implements ClusterURL {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public String parseClusterURL(String url) throws MalformedURLException {
      if (!url.startsWith("cluster:")) {
         return url;
      } else {
         String protocol = this.extractProtocol(url);
         String clusterName = this.extractClusterName(url);
         String partitionName = this.extractPartitionName(url);
         ClusterMBean cluster = this.getClusterMBean(clusterName);
         if (cluster == null) {
            throw new MalformedURLException("No cluster named '" + clusterName + "' found.");
         } else {
            ServerMBean[] servers = cluster.getServers();
            if (servers != null && servers.length != 0) {
               ArrayList list = this.buildListOfAddresses(clusterName, servers, protocol);
               String clusterURL = this.buildClusterAddressURL(protocol, clusterName, list);
               if (partitionName != null) {
                  clusterURL = clusterURL + "/?partitionName=" + partitionName;
               }

               return clusterURL;
            } else {
               throw new MalformedURLException("No servers configured in cluster: " + clusterName);
            }
         }
      }
   }

   protected ArrayList buildListOfAddresses(String clusterName, ServerMBean[] servers, String protocol) throws MalformedURLException {
      if (servers != null && servers.length != 0) {
         boolean useSecurePort = protocol.endsWith("s");
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         DomainMBean domain = runtimeAccess.getDomain();
         ArrayList list = new ArrayList();

         for(int i = 0; i < servers.length; ++i) {
            ServerMBean server = servers[i];
            String listenAddress = null;
            listenAddress = server.getListenAddress();
            if (listenAddress == null || listenAddress.length() == 0) {
               MachineMBean machine = server.getMachine();
               if (machine != null) {
                  NodeManagerMBean nodeManager = machine.getNodeManager();
                  if (nodeManager != null) {
                     listenAddress = nodeManager.getListenAddress();
                  }
               }

               if ((listenAddress == null || listenAddress.length() == 0) && runtimeAccess.getServer().getCluster() != null) {
                  ClusterServices clusterServices = Locator.locate();
                  Collection remoteMembers = clusterServices.getRemoteMembers();
                  Iterator itr = remoteMembers.iterator();
                  String serverName = server.getName();

                  while(itr.hasNext()) {
                     ClusterMemberInfo info = (ClusterMemberInfo)itr.next();
                     if (info.serverName().equalsIgnoreCase(serverName)) {
                        listenAddress = info.machineName();
                        break;
                     }
                  }
               }
            }

            if (listenAddress != null && listenAddress.length() != 0) {
               int listenPort = server.getListenPort();
               if (protocol.equalsIgnoreCase("admin") && domain.isAdministrationPortEnabled() && this.isUserAdministrator()) {
                  listenPort = server.getAdministrationPort();
               } else if (useSecurePort) {
                  if (server.getSSL() == null) {
                     continue;
                  }

                  listenPort = server.getSSL().getListenPort();
               }

               StringBuilder strBuilder = new StringBuilder();
               strBuilder.append(listenAddress);
               strBuilder.append(":");
               strBuilder.append(listenPort);
               list.add(strBuilder.toString());
            }
         }

         return list;
      } else {
         throw new MalformedURLException("No servers configured in cluster: " + clusterName);
      }
   }

   protected String buildClusterAddressURL(String protocol, String clusterName, ArrayList list) throws MalformedURLException {
      if (list != null && !list.isEmpty()) {
         StringBuilder strBuilder = new StringBuilder();
         strBuilder.append(protocol);
         strBuilder.append("://");

         for(int i = 0; i < list.size(); ++i) {
            if (i > 0) {
               strBuilder.append(',');
            }

            strBuilder.append((String)list.get(i));
         }

         return strBuilder.toString();
      } else {
         throw new MalformedURLException("Unable to construct proper cluster address URL since no listening addresses found for any configured server(s) in cluster: " + clusterName);
      }
   }

   protected String extractClusterName(String url) {
      int idx = url.lastIndexOf("://");
      int lastSlash = url.indexOf(47, idx + 3);
      String clusterName = lastSlash < 0 ? url.substring(idx + 3, url.length()) : url.substring(idx + 3, lastSlash);
      return clusterName;
   }

   protected String extractPartitionName(String url) {
      int idx = url.lastIndexOf("://");
      int lastSlash = url.indexOf(47, idx + 3);
      String partitionName = null;
      if (lastSlash != -1) {
         partitionName = url.substring(lastSlash + 1, url.length());
      }

      return partitionName;
   }

   protected String extractProtocol(String url) {
      int beginIdx = url.indexOf(58);
      int endIdx = url.indexOf("://");
      String prototol = url.substring(beginIdx + 1, endIdx);
      return prototol;
   }

   protected ClusterMBean getClusterMBean(String clusterName) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ClusterMBean cluster = domain.lookupCluster(clusterName);
      return cluster;
   }

   protected boolean isUserAdministrator() {
      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
      return subject != null ? SubjectUtils.isUserAnAdministrator(subject) : false;
   }

   private static AuthenticatedSubject authenticateLocally(UserInfo user) throws LoginException {
      String realmName = "weblogicDEFAULT";
      PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelId, realmName, ServiceType.AUTHENTICATION);
      AuthenticatedSubject subject = null;
      if (user instanceof DefaultUserInfoImpl) {
         DefaultUserInfoImpl u = (DefaultUserInfoImpl)user;
         SimpleCallbackHandler handler = new SimpleCallbackHandler(u.getName(), u.getPassword());
         subject = pa.authenticate(handler);
      }

      return subject;
   }
}
