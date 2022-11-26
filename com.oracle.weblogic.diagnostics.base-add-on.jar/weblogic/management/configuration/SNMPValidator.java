package weblogic.management.configuration;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.i18n.logging.Loggable;
import weblogic.kernel.Kernel;
import weblogic.management.WebLogicMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceClient;
import weblogic.management.provider.beaninfo.BeanInfoAccess;

@Service
@ContractsProvided({DomainMBeanValidator.class})
public class SNMPValidator implements DomainMBeanValidator {
   private static final int AUTH_FLAG = 1;
   private static final int PRIV_FLAG = 2;
   private static final boolean DEBUG = false;
   private static BeanInfoAccess beanInfoAccess;

   public static void validateSNMPAgentDeployments(DomainMBean domain) {
      SNMPAgentDeploymentMBean[] snmpAgentDeployments = domain.getSNMPAgentDeployments();
      if (snmpAgentDeployments != null) {
         Map targetMap = new HashMap();
         ServerMBean[] domainServers = domain.getServers();

         int i;
         for(i = 0; i < domainServers.length; ++i) {
            targetMap.put(domainServers[i].getName(), (Object)null);
         }

         for(i = 0; i < snmpAgentDeployments.length; ++i) {
            SNMPAgentDeploymentMBean snmpAgentDeployment = snmpAgentDeployments[i];
            validateCommunityPrefix(snmpAgentDeployment);
            TargetMBean[] targets = snmpAgentDeployment.getTargets();
            ServerMBean[] servers = WLDFValidator.getServersInTargets(targets);
            if (servers != null) {
               for(int j = 0; j < servers.length; ++j) {
                  String name = servers[j].getName();
                  String agentName = (String)targetMap.get(name);
                  if (agentName != null) {
                     throw new IllegalArgumentException(SNMPLogger.logSNMPAgentDeployedToServerLoggable(agentName, name).getMessage());
                  }

                  targetMap.put(name, snmpAgentDeployment.getName());
               }
            }
         }

      }
   }

   private static void validateCommunityPrefix(SNMPAgentDeploymentMBean snmpAgentDeployment) {
      if (snmpAgentDeployment.isCommunityBasedAccessEnabled()) {
         String community = snmpAgentDeployment.getCommunityPrefix();
         if (community == null || community.isEmpty() || community.equals("public") || community.equals("private")) {
            throw new IllegalArgumentException(SNMPLogger.logInvalidCommunityPrefixLoggable().getMessage());
         }
      }

   }

   public static void validateSNMPProxy(SNMPProxyMBean proxy) {
      if (proxy != null && proxy.getParent() != null) {
         WebLogicMBean parent = proxy.getParent();
         if (parent instanceof SNMPAgentMBean) {
            validateProxy(proxy, (SNMPAgentMBean)parent);
         }
      }

   }

   private static void validateProxy(SNMPProxyMBean proxy, SNMPAgentMBean agent) {
      String securityName = proxy.getSecurityName();
      String proxySecurityLevel = proxy.getSecurityLevel();
      if (securityName != null && securityName.length() != 0) {
         int agentSecLevel = getSecurityLevel(agent);
         int proxySecLevel = 0;
         if (proxySecurityLevel.equals("authNoPriv")) {
            proxySecLevel = 1;
         } else if (proxySecurityLevel.equals("authPriv")) {
            proxySecLevel = 3;
         }

         if (proxySecLevel > agentSecLevel) {
            throw new IllegalArgumentException(SNMPLogger.logSNMPProxyInvalidSecurityLevelLoggable(agent.getName(), getAgentSecurityLevelString(agentSecLevel), proxy.getName(), proxySecurityLevel).getMessage());
         }
      }

   }

   public static void validateSNMPAgent(SNMPAgentMBean agent) {
      String authProtocol;
      if (agent.getSNMPTrapVersion() == 1 && agent.isInformEnabled()) {
         authProtocol = SNMPLogger.logSNMPInvalidTrapVersionLoggable().getMessageBody();
         throw new IllegalArgumentException(authProtocol);
      } else {
         authProtocol = agent.getAuthenticationProtocol();
         String privProtocol = agent.getPrivacyProtocol();
         if (authProtocol.equals("noAuth") && !privProtocol.equals("noPriv")) {
            String s = SNMPLogger.logInvalidAuthenticationProtocolLoggable(agent.getName(), privProtocol).getMessageBody();
            throw new IllegalArgumentException(s);
         } else {
            int agentSecLevel = getSecurityLevel(agent);
            String agentLevel = getAgentSecurityLevelString(agentSecLevel);
            SNMPTrapDestinationMBean[] traps = agent.getSNMPTrapDestinations();
            if (traps != null) {
               for(int i = 0; i < traps.length; ++i) {
                  String securityLevel = traps[i].getSecurityLevel();
                  int trapSecLevel = 0;
                  if (securityLevel.equals("authNoPriv")) {
                     trapSecLevel = 1;
                  } else if (securityLevel.equals("authPriv")) {
                     trapSecLevel = 3;
                  }

                  if (agentSecLevel < trapSecLevel) {
                     String s = SNMPLogger.logInvalidSNMPTrapDestinationSecurityLevelLoggable(traps[i].getName(), traps[i].getSecurityLevel(), agentLevel).getMessageBody();
                     throw new IllegalArgumentException(s);
                  }

                  validateSNMPTrapDestination(traps[i]);
               }
            }

         }
      }
   }

   private static String getAgentSecurityLevelString(int agentSecLevel) {
      String agentLevel = "noAuthNoPriv";
      switch (agentSecLevel) {
         case 1:
            agentLevel = "authNoPriv";
            break;
         case 3:
            agentLevel = "authPriv";
            break;
         default:
            agentLevel = "noAuthNoPriv";
      }

      return agentLevel;
   }

   private static void validateSNMPTrapDestination(SNMPTrapDestinationMBean trapDest) {
      SNMPAgentMBean agent = (SNMPAgentMBean)trapDest.getParent();
      if (agent.getSNMPTrapVersion() == 3 && (trapDest.getSecurityName() == null || trapDest.getSecurityName().length() == 0)) {
         String s = SNMPLogger.logSecurityNameNotSpecifiedForV3TrapDestinationLoggable(trapDest.getName()).getMessageBody();
         throw new IllegalArgumentException(s);
      }
   }

   public static void validateJMXMonitorMBean(SNMPJMXMonitorMBean mbean) {
      String type = mbean.getMonitoredMBeanType();
      String attr = mbean.getMonitoredAttributeName();
      BeanInfo bi = getBeanInfo(type);
      if (bi == null) {
         Loggable l = SNMPLogger.logInvalidTypeNameLoggable(type);
         throw new IllegalArgumentException(l.getMessageBody());
      } else {
         PropertyDescriptor[] propDescriptors = bi.getPropertyDescriptors();
         if (propDescriptors != null) {
            for(int i = 0; i < propDescriptors.length; ++i) {
               PropertyDescriptor pd = propDescriptors[i];
               String property = pd.getName();
               if (property != null && property.equals(attr)) {
                  return;
               }
            }
         }

         Loggable l = SNMPLogger.logInvalidAttributeNameLoggable(type, attr);
         throw new IllegalArgumentException(l.getMessageBody());
      }
   }

   public static void validateAttributeChangeMBean(SNMPAttributeChangeMBean mbean) {
      String type = mbean.getAttributeMBeanType();
      String attr = mbean.getAttributeName();
      BeanInfo bi = getBeanInfo(type);
      if (bi == null) {
         Loggable l = SNMPLogger.logInvalidTypeNameLoggable(type);
         throw new IllegalArgumentException(l.getMessageBody());
      } else {
         PropertyDescriptor[] propDescriptors = bi.getPropertyDescriptors();
         if (propDescriptors != null) {
            for(int i = 0; i < propDescriptors.length; ++i) {
               PropertyDescriptor pd = propDescriptors[i];
               String property = pd.getName();
               if (property != null && property.equals(attr)) {
                  return;
               }
            }
         }

         Loggable l = SNMPLogger.logInvalidAttributeNameLoggable(type, attr);
         throw new IllegalArgumentException(l.getMessageBody());
      }
   }

   private static BeanInfo getBeanInfo(String type) {
      if (beanInfoAccess == null) {
         beanInfoAccess = Kernel.isServer() ? ManagementService.getBeanInfoAccess() : ManagementServiceClient.getBeanInfoAccess();
      }

      String version = VersionInfo.theOne().getReleaseVersion();
      BeanInfo beanInfo = beanInfoAccess.getBeanInfoForInterface(type, true, version);
      String rtType;
      if (beanInfo == null && type.indexOf(".") == -1) {
         rtType = "weblogic.management.configuration." + type + "MBean";
         beanInfo = beanInfoAccess.getBeanInfoForInterface(rtType, true, version);
      }

      if (beanInfo == null && type.indexOf(".") == -1) {
         rtType = "weblogic.management.runtime." + type + "MBean";
         beanInfo = beanInfoAccess.getBeanInfoForInterface(rtType, true, version);
      }

      Boolean excludedType = (Boolean)beanInfo.getBeanDescriptor().getValue("exclude");
      if (excludedType != null && excludedType) {
         return null;
      } else {
         String obsoleteValue = (String)beanInfo.getBeanDescriptor().getValue("obsolete");
         return obsoleteValue != null && obsoleteValue.length() > 0 ? null : beanInfo;
      }
   }

   public static int getSecurityLevel(SNMPAgentMBean snmpAgentConfig) {
      if (snmpAgentConfig == null) {
         return 0;
      } else {
         int securityLevel = 0;
         String authProtocol = snmpAgentConfig.getAuthenticationProtocol();
         String privProtocol = snmpAgentConfig.getPrivacyProtocol();
         if (!authProtocol.equals("noAuth")) {
            securityLevel |= 1;
         }

         if (!privProtocol.equals("noPriv")) {
            securityLevel |= 2;
         }

         return securityLevel;
      }
   }

   public void validate(DomainMBean domain) {
      validateSNMPAgentDeployments(domain);
   }
}
