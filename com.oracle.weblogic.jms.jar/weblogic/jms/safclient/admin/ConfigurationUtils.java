package weblogic.jms.safclient.admin;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;
import javax.jms.JMSException;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.jms.common.SecHelper;
import weblogic.jms.safclient.ClientSAFDelegate;
import weblogic.jms.safclient.agent.AgentManager;
import weblogic.jms.safclient.agent.DestinationImpl;
import weblogic.jms.safclient.agent.internal.Agent;
import weblogic.jms.safclient.agent.internal.ErrorHandler;
import weblogic.jms.safclient.agent.internal.RemoteContext;
import weblogic.jms.safclient.agent.internal.RuntimeHandlerImpl;
import weblogic.jms.safclient.jms.ConnectionFactoryImpl;
import weblogic.jms.safclient.jndi.ContextImpl;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Sequence;

public final class ConfigurationUtils {
   private static final String ROOT_TAG = "weblogic-client-jms";
   private static final String STORE_TAG = "persistent-store";
   private static final String DIRPATH_TAG = "directory-path";
   private static final String SWPOLICY_TAG = "synchronous-write-policy";
   private static final String CF_TAG = "connection-factory";
   private static final String JNDI_TAG = "jndi-name";
   private static final String LOCAL_JNDI_TAG = "local-jndi-name";
   private static final String REMOTE_JNDI_TAG = "remote-jndi-name";
   private static final String NAME_ATT = "name";
   private static final String DDP_TAG = "default-delivery-params";
   private static final String DFDM_TAG = "default-delivery-mode";
   private static final String DFTD_TAG = "default-time-to-deliver";
   private static final String DFTTL_TAG = "default-time-to-live";
   private static final String DFPRI_TAG = "default-priority";
   private static final String DFRED_TAG = "default-redelivery-delay";
   private static final String DFSND_TAG = "send-timeout";
   private static final String DFCMP_TAG = "default-compression-threshold";
   private static final String DFUOO_TAG = "default-unit-of-order";
   private static final String CLIENT_TAG = "client-params";
   private static final String CID_TAG = "client-id";
   private static final String MM_TAG = "messages-maximum";
   private static final String MOR_TAG = "multicast-overrun-policy";
   private static final String SEC_TAG = "security-params";
   private static final String ATT_TAG = "attach-jmsx-user-id";
   private static final String SAFGROUP_TAG = "saf-imported-destinations";
   private static final String QUEUE_TAG = "saf-queue";
   private static final String TOPIC_TAG = "saf-topic";
   private static final String AGENT_TAG = "saf-agent";
   private static final String BYMAX_TAG = "bytes-maximum";
   private static final String MMS_TAG = "maximum-message-size";
   private static final String MBS_TAG = "message-buffer-size";
   private static final String SRC_TAG = "saf-remote-context";
   private static final String CMP_TAG = "compression-threshold";
   private static final String LOG_TAG = "saf-login-context";
   private static final String URL_TAG = "loginURL";
   private static final String UNAME_TAG = "username";
   private static final String PW_TAG = "password-encrypted";
   private static final String RDB_TAG = "default-retry-delay-base";
   private static final String RDM_TAG = "default-retry-delay-maximum";
   private static final String RDX_TAG = "default-retry-delay-multiplier";
   private static final String WIN_TAG = "window-size";
   private static final String LOE_TAG = "logging-enabled";
   private static final String WINI_TAG = "window-interval";
   private static final String SAFG_TAG = "saf-imported-destinations";
   private static final String NONQ_TAG = "non-persistent-qos";
   private static final String ERH_TAG = "saf-error-handling";
   private static final String POL_TAG = "policy";
   private static final String LOGF_TAG = "log-format";
   private static final String SED_TAG = "saf-error-destination";
   private static final String SEHG_TAG = "saf-error-handling";
   private static final String EOLBP_TAG = "exactly-once-load-balancing-policy";
   private static final String DEFAULT_CF_NAME = "weblogic.jms.safclient.ConnectionFactory";
   private static final String PREFIX_NAME = "ClientSAF_";
   public static final String EXACTLY_ONCE = "Exactly-Once";
   private static final String AT_LEAST_ONCE = "At-Least-Once";
   private static final String AT_MOST_ONCE = "At-Most-Once";

   private static final String getNestedSingleField(Element node, String tagName) {
      NodeList nodeList = node.getElementsByTagName(tagName);
      if (nodeList != null && nodeList.getLength() > 0) {
         Node singleNode = nodeList.item(0);
         return getTextContent_14(singleNode);
      } else {
         return null;
      }
   }

   public static String getTextContent_14(Node parent, boolean withchildren) {
      if (!parent.hasChildNodes()) {
         return "";
      } else {
         Pattern nwsPattern = Pattern.compile("[^ \t\r\n]");
         StringBuffer textcontentbuffer = new StringBuffer();

         for(Node node = parent.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!(node instanceof Text) && !(node instanceof CDATASection)) {
               textcontentbuffer.append(getTextContent_14(node, withchildren));
            } else {
               String value = node.getNodeValue();
               if (nwsPattern.matcher(value).find()) {
                  textcontentbuffer.append(value);
               }
            }
         }

         return textcontentbuffer.toString();
      }
   }

   public static String getTextContent_14(Node parent) {
      if (!(parent instanceof Document) && !(parent instanceof DocumentType) && !(parent instanceof Notation)) {
         if (!(parent instanceof Text) && !(parent instanceof CDATASection) && !(parent instanceof Comment) && !(parent instanceof ProcessingInstruction)) {
            return getTextContent_14(parent, true);
         } else {
            Pattern nwsPattern = Pattern.compile("[^ \t\r\n]");
            String value = parent.getNodeValue();
            return nwsPattern.matcher(value).find() ? value : "";
         }
      } else {
         return null;
      }
   }

   public static PersistentStoreBean getPersistentStore(Document configuration) throws JMSException {
      PersistentStoreBeanImpl retVal = new PersistentStoreBeanImpl();
      NodeList nodeList = configuration.getElementsByTagName("weblogic-client-jms");
      if (nodeList.getLength() != 1) {
         throw new JMSException("This document must contain a root node of weblogic-client-jms");
      } else {
         Element rootElement = (Element)nodeList.item(0);
         NodeList rootList = rootElement.getElementsByTagName("persistent-store");
         if (rootList != null && rootList.getLength() > 0) {
            Element node = (Element)rootList.item(0);
            String value = getNestedSingleField(node, "directory-path");
            if (value != null) {
               retVal.setStoreDirectory(value);
            }

            value = getNestedSingleField(node, "synchronous-write-policy");
            if (value != null) {
               retVal.setPolicy(value);
            }
         }

         return retVal;
      }
   }

   private static void addToJNDIMap(HashMap jndiMap, String key, Object value) throws JMSException {
      if (key != null) {
         if (jndiMap.containsKey(key)) {
            throw new JMSException(key + " is already bound into JNDI");
         } else {
            jndiMap.put(key, value);
         }
      }
   }

   public static void doJNDIConnectionFactories(Document configuration, ClientSAFDelegate delegate, HashMap jndiMap) throws JMSException {
      ConnectionFactoryImpl impl = new ConnectionFactoryImpl("weblogic.jms.safclient.ConnectionFactory", delegate);
      addToJNDIMap(jndiMap, "weblogic.jms.safclient.ConnectionFactory", impl);
      NodeList nodeList = configuration.getElementsByTagName("weblogic-client-jms");
      if (nodeList.getLength() != 1) {
         throw new JMSException("This document must contain a root node of weblogic-client-jms");
      } else {
         Element rootElement = (Element)nodeList.item(0);
         NodeList rootList = rootElement.getElementsByTagName("connection-factory");
         if (rootList != null && rootList.getLength() > 0) {
            for(int lcv = 0; lcv < rootList.getLength(); ++lcv) {
               Element cfElement = (Element)rootList.item(lcv);
               String name = cfElement.getAttribute("name");
               if (name == null) {
                  throw new JMSException("A connection factory does not have a name attribute");
               }

               String jndiKey = getNestedSingleField(cfElement, "jndi-name");
               String localJNDIKey = getNestedSingleField(cfElement, "local-jndi-name");
               if (jndiKey != null || localJNDIKey != null) {
                  impl = new ConnectionFactoryImpl(name, delegate);
                  NodeList ddpList = cfElement.getElementsByTagName("default-delivery-params");
                  if (ddpList != null && ddpList.getLength() > 0) {
                     Element ddpElement = (Element)ddpList.item(0);
                     String value = getNestedSingleField(ddpElement, "default-delivery-mode");
                     if (value != null) {
                        impl.setDefaultDeliveryMode(value);
                     }

                     value = getNestedSingleField(ddpElement, "default-time-to-deliver");
                     if (value != null) {
                        impl.setDefaultTimeToDeliver(value);
                     }

                     value = getNestedSingleField(ddpElement, "default-time-to-live");
                     if (value != null) {
                        impl.setDefaultTimeToLive(Long.parseLong(value));
                     }

                     value = getNestedSingleField(ddpElement, "default-priority");
                     if (value != null) {
                        impl.setDefaultPriority(Integer.parseInt(value));
                     }

                     value = getNestedSingleField(ddpElement, "default-redelivery-delay");
                     if (value != null) {
                        impl.setDefaultRedeliveryDelay(Long.parseLong(value));
                     }

                     value = getNestedSingleField(ddpElement, "send-timeout");
                     if (value != null) {
                        impl.setSendTimeout(Long.parseLong(value));
                     }

                     value = getNestedSingleField(ddpElement, "default-compression-threshold");
                     if (value != null) {
                        impl.setDefaultCompressionThreshold(Integer.parseInt(value));
                     }

                     value = getNestedSingleField(ddpElement, "default-unit-of-order");
                     if (value != null) {
                        impl.setDefaultUnitOfOrder(value);
                     }
                  }

                  NodeList clientList = cfElement.getElementsByTagName("client-params");
                  if (clientList != null && clientList.getLength() > 0) {
                     Element clientElement = (Element)clientList.item(0);
                     String value = getNestedSingleField(clientElement, "client-id");
                     if (value != null) {
                        impl.setClientId(value);
                     }

                     value = getNestedSingleField(clientElement, "messages-maximum");
                     if (value != null) {
                        impl.setMessagesMaximum(Integer.parseInt(value));
                     }

                     value = getNestedSingleField(clientElement, "multicast-overrun-policy");
                     if (value != null) {
                        impl.setMulticastOverrunPolicy(value);
                     }
                  }

                  NodeList securityList = cfElement.getElementsByTagName("security-params");
                  if (securityList != null && securityList.getLength() > 0) {
                     Element securityElement = (Element)securityList.item(0);
                     String value = getNestedSingleField(securityElement, "attach-jmsx-user-id");
                     if (value != null) {
                        boolean result = value.equalsIgnoreCase("true");
                        impl.setAttachJMSXUserId(result);
                     }
                  }

                  addToJNDIMap(jndiMap, jndiKey, impl);
                  addToJNDIMap(jndiMap, localJNDIKey, impl);
               }
            }

         }
      }
   }

   private static void doDestinations(String groupName, NodeList destinationList, boolean isQueue, HashMap destinationMap, HashMap jndiMap) throws JMSException {
      if (destinationList != null && destinationList.getLength() != 0) {
         for(int lcv = 0; lcv < destinationList.getLength(); ++lcv) {
            Element destinationElement = (Element)destinationList.item(lcv);
            String name = destinationElement.getAttribute("name");
            if (name == null) {
               throw new JMSException("A saf destination does not have a name attribute");
            }

            DestinationImpl destinationImpl = new DestinationImpl(groupName, name, isQueue);
            destinationMap.put(name, destinationImpl);
            String jndiKey = getNestedSingleField(destinationElement, "local-jndi-name");
            if (jndiKey == null) {
               jndiKey = getNestedSingleField(destinationElement, "remote-jndi-name");
            }

            if (jndiKey == null) {
               throw new JMSException("The saf destination " + name + " in saf group " + groupName + " does not have a JNDI name");
            }

            addToJNDIMap(jndiMap, jndiKey, destinationImpl);
         }

      }
   }

   public static void doJNDIDestinations(Document configuration, HashMap jmsMap, HashMap jndiMap) throws JMSException {
      NodeList nodeList = configuration.getElementsByTagName("weblogic-client-jms");
      if (nodeList.getLength() != 1) {
         throw new JMSException("This document must contain a root node of weblogic-client-jms");
      } else {
         Element rootElement = (Element)nodeList.item(0);
         NodeList rootList = rootElement.getElementsByTagName("saf-imported-destinations");
         if (rootList != null && rootList.getLength() > 0) {
            for(int lcv = 0; lcv < rootList.getLength(); ++lcv) {
               Element groupElement = (Element)rootList.item(lcv);
               String groupName = groupElement.getAttribute("name");
               if (groupName == null) {
                  throw new JMSException("A saf imported destination group does not have a name attribute");
               }

               HashMap destinationMap = new HashMap();
               jmsMap.put(groupName, destinationMap);
               NodeList queueList = groupElement.getElementsByTagName("saf-queue");
               doDestinations(groupName, queueList, true, destinationMap, jndiMap);
               NodeList topicList = groupElement.getElementsByTagName("saf-topic");
               doDestinations(groupName, topicList, false, destinationMap, jndiMap);
            }

         }
      }
   }

   public static void doAgent(Document configuration, Agent agent) throws JMSException {
      NodeList nodeList = configuration.getElementsByTagName("weblogic-client-jms");
      if (nodeList.getLength() != 1) {
         throw new JMSException("This document must contain a root node of weblogic-client-jms");
      } else {
         Element rootElement = (Element)nodeList.item(0);
         NodeList rootList = rootElement.getElementsByTagName("saf-agent");
         if (rootList != null && rootList.getLength() > 0) {
            Element agentElement = (Element)rootList.item(0);
            String value = getNestedSingleField(agentElement, "bytes-maximum");
            if (value != null) {
               agent.setBytesMaximum(Long.parseLong(value));
            }

            value = getNestedSingleField(agentElement, "messages-maximum");
            if (value != null) {
               agent.setMessagesMaximum(Long.parseLong(value));
            }

            value = getNestedSingleField(agentElement, "maximum-message-size");
            if (value != null) {
               agent.setMaximumMessageSize(Integer.parseInt(value));
            }

            value = getNestedSingleField(agentElement, "message-buffer-size");
            if (value != null) {
               agent.setMessageBufferSize((long)Integer.parseInt(value));
            }

            value = getNestedSingleField(agentElement, "default-retry-delay-base");
            if (value != null) {
               agent.setDefaultRetryDelayBase(Long.parseLong(value));
            }

            value = getNestedSingleField(agentElement, "default-retry-delay-maximum");
            if (value != null) {
               agent.setDefaultRetryDelayMaximum(Long.parseLong(value));
            }

            value = getNestedSingleField(agentElement, "default-retry-delay-multiplier");
            if (value != null) {
               agent.setDefaultRetryDelayMultiplier(Double.parseDouble(value));
            }

            value = getNestedSingleField(agentElement, "window-size");
            if (value != null) {
               agent.setWindowSize(Integer.parseInt(value));
            }

            value = getNestedSingleField(agentElement, "logging-enabled");
            if (value != null) {
               boolean result = value.equalsIgnoreCase("true");
               agent.setLoggingEnabled(result);
            }

            value = getNestedSingleField(agentElement, "window-interval");
            if (value != null) {
               agent.setWindowInterval(Integer.parseInt(value));
            }

         }
      }
   }

   public static void doRemoteContexts(Document configuration, Agent agent, HashMap remoteContextMap, char[] key) throws JMSException {
      NodeList nodeList = configuration.getElementsByTagName("weblogic-client-jms");
      if (nodeList.getLength() != 1) {
         throw new JMSException("This document must contain a root node of weblogic-client-jms");
      } else {
         Element rootElement = (Element)nodeList.item(0);
         NodeList rootList = rootElement.getElementsByTagName("saf-remote-context");
         if (rootList != null && rootList.getLength() > 0) {
            int length = rootList.getLength();

            for(int lcv = 0; lcv < length; ++lcv) {
               Element remoteContextElement = (Element)rootList.item(lcv);
               String name = remoteContextElement.getAttribute("name");
               if (name == null) {
                  throw new JMSException("A saf remote context does not have a name attribute");
               }

               RemoteContext remoteContext = new RemoteContext(name);
               String value = getNestedSingleField(remoteContextElement, "compression-threshold");
               if (value != null) {
                  remoteContext.setCompressionThreshold(Integer.parseInt(value));
               }

               NodeList loginList = remoteContextElement.getElementsByTagName("saf-login-context");
               if (loginList != null && loginList.getLength() > 0) {
                  Element loginElement = (Element)loginList.item(0);
                  value = getNestedSingleField(loginElement, "loginURL");
                  if (value != null) {
                     remoteContext.setLoginURL(value);
                  }

                  value = getNestedSingleField(loginElement, "username");
                  if (value != null) {
                     remoteContext.setUsername(value);
                  }

                  value = getNestedSingleField(loginElement, "password-encrypted");
                  if (value != null) {
                     char[] pw;
                     try {
                        pw = SecHelper.decryptString(key, value);
                     } catch (GeneralSecurityException var17) {
                        throw new weblogic.jms.common.JMSException("Invalid password key to unlock the passwords in the configuration file", var17);
                     } catch (IOException var18) {
                        throw new weblogic.jms.common.JMSException(var18);
                     }

                     remoteContext.setPassword(new String(pw));

                     for(int inner = 0; inner < pw.length; ++inner) {
                        pw[inner] = 'x';
                     }
                  }
               }

               remoteContext.setRetryDelayBase(agent.getDefaultRetryDelayBase());
               remoteContext.setRetryDelayMaximum(agent.getDefaultRetryDelayMaximum());
               remoteContext.setRetryDelayMultiplier(agent.getDefaultRetryDelayMultiplier());
               remoteContext.setWindowSize(agent.getWindowSize());
               remoteContext.setWindowInterval(agent.getWindowInterval());
               remoteContextMap.put(name, remoteContext);
            }

         }
      }
   }

   public static void doErrorHandlers(Document configuration, HashMap errorHandlerMap) throws JMSException {
      NodeList nodeList = configuration.getElementsByTagName("weblogic-client-jms");
      if (nodeList.getLength() != 1) {
         throw new JMSException("This document must contain a root node of weblogic-client-jms");
      } else {
         Element rootElement = (Element)nodeList.item(0);
         NodeList rootList = rootElement.getElementsByTagName("saf-error-handling");
         if (rootList != null && rootList.getLength() > 0) {
            int length = rootList.getLength();

            for(int lcv = 0; lcv < length; ++lcv) {
               Element remoteContextElement = (Element)rootList.item(lcv);
               String name = remoteContextElement.getAttribute("name");
               if (name == null) {
                  throw new JMSException("A saf remote context does not have a name attribute");
               }

               ErrorHandler errorHandler = new ErrorHandler(name);
               String value = getNestedSingleField(remoteContextElement, "policy");
               if (value != null) {
                  errorHandler.setPolicy(value);
               }

               value = getNestedSingleField(remoteContextElement, "log-format");
               if (value != null) {
                  errorHandler.setLogFormat(value);
               }

               value = getNestedSingleField(remoteContextElement, "log-format");
               if (value != null) {
                  errorHandler.setLogFormat(value);
               }

               value = getNestedSingleField(remoteContextElement, "saf-error-destination");
               if (value != null) {
                  errorHandler.setErrorDestinationName(value);
               }

               errorHandlerMap.put(name, errorHandler);
            }

         }
      }
   }

   private static int qosStringToInt(String sVal) {
      if ("Exactly-Once".equals(sVal)) {
         return 2;
      } else {
         return "At-Least-Once".equals(sVal) ? 3 : 1;
      }
   }

   private static void doImportedDestination(NodeList destinationList, String groupName, Agent agent, ContextImpl context, RemoteContext remoteContext, ErrorHandler defaultErrorHandler, HashMap errorHandlerMap, String exactlyOnceLoadBalancingPolicy) throws JMSException {
      int length = destinationList.getLength();

      for(int lcv = 0; lcv < length; ++lcv) {
         Element destinationElement = (Element)destinationList.item(lcv);
         String destinationName = destinationElement.getAttribute("name");
         if (destinationName == null) {
            throw new JMSException("A saf imported destination does not have a name attribute");
         }

         String remoteJNDIName = getNestedSingleField(destinationElement, "remote-jndi-name");
         if (remoteJNDIName == null) {
            throw new JMSException("The remote JNDI name field is not set for destination " + destinationName + " in saf group " + groupName);
         }

         String qos = getNestedSingleField(destinationElement, "non-persistent-qos");
         if (qos == null) {
            qos = "At-Most-Once";
         }

         String fullName = AgentManager.constructDestinationName(groupName, destinationName);
         String errorHandlerName = getNestedSingleField(destinationElement, "saf-error-handling");
         ErrorHandler realErrorHandler = defaultErrorHandler;
         if (errorHandlerName != null) {
            realErrorHandler = (ErrorHandler)errorHandlerMap.get(errorHandlerName);
            if (realErrorHandler == null) {
               throw new JMSException("There is no error handler named " + errorHandlerName + " in SAF destination " + fullName);
            }
         }

         Queue kernelQueue = agent.addConfiguredDestination(fullName);
         DestinationImpl destination = context.getDestination(groupName, destinationName);
         if (destination == null) {
            throw new JMSException("Could not find the configuration destination " + fullName);
         }

         destination.setKernelQueue(kernelQueue);
         destination.setSequenceName(getLatestSequenceName(getBaseSequenceName(fullName), kernelQueue));
         destination.setNonPersistentQOS(qos);
         destination.setLoggingEnabled(agent.isLoggingEnabled());
         destination.setErrorHandler(realErrorHandler);
         remoteContext.addForwarder(agent.getPersistentStore(), agent.getAsyncPushWorkManager(), new RuntimeHandlerImpl(agent.getName(), destinationName, remoteContext.getName()), kernelQueue, remoteJNDIName, qosStringToInt(qos), exactlyOnceLoadBalancingPolicy);
      }

   }

   public static void doImportedDestinationGroup(Document configuration, HashMap remoteContextMap, HashMap errorHandlerMap, Agent agent, ContextImpl context) throws JMSException {
      NodeList nodeList = configuration.getElementsByTagName("weblogic-client-jms");
      if (nodeList.getLength() != 1) {
         throw new JMSException("This document must contain a root node of weblogic-client-jms");
      } else {
         Element rootElement = (Element)nodeList.item(0);
         NodeList rootList = rootElement.getElementsByTagName("saf-imported-destinations");
         if (rootList != null && rootList.getLength() > 0) {
            int groupsLength = rootList.getLength();

            for(int lcv = 0; lcv < groupsLength; ++lcv) {
               Element safGroupElement = (Element)rootList.item(lcv);
               String groupName = safGroupElement.getAttribute("name");
               if (groupName == null) {
                  throw new JMSException("A saf imported destination group does not have a name attribute");
               }

               String remoteContextName = getNestedSingleField(safGroupElement, "saf-remote-context");
               if (remoteContextName != null) {
                  RemoteContext myRemoteContext = (RemoteContext)remoteContextMap.get(remoteContextName);
                  if (myRemoteContext == null) {
                     throw new JMSException("There is no remote context of name " + remoteContextName + " in saf destination group " + groupName);
                  }

                  String errorHandlingName = getNestedSingleField(safGroupElement, "saf-error-handling");
                  ErrorHandler defaultErrorHandler = null;
                  if (errorHandlingName != null) {
                     defaultErrorHandler = (ErrorHandler)errorHandlerMap.get(errorHandlingName);
                     if (defaultErrorHandler == null) {
                        throw new JMSException("There is no error handler of name " + errorHandlingName + " in saf destination group " + groupName);
                     }
                  }

                  String sysProp = JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY;
                  String policyFromProperty = null;

                  try {
                     policyFromProperty = System.getProperty(sysProp);
                  } catch (Exception var21) {
                  }

                  if (policyFromProperty != null && !JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER.equals(policyFromProperty) && !JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_JVM.equals(policyFromProperty)) {
                     System.out.println("The system property '" + JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY + "' has an invalid value '" + policyFromProperty + "', and is therefore ignored. The valid values are '" + JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER + "' or '" + JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_JVM + "'. The exactly-once-load-balancing-policy setting on the  saf-imported-destinations bean '" + groupName + "' will be used instead.");
                     policyFromProperty = null;
                  }

                  String exactlyOnceLoadBalancingPolicy = policyFromProperty;
                  if (policyFromProperty == null) {
                     exactlyOnceLoadBalancingPolicy = getNestedSingleField(safGroupElement, "exactly-once-load-balancing-policy");
                     if (exactlyOnceLoadBalancingPolicy == null) {
                        exactlyOnceLoadBalancingPolicy = JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER;
                     }
                  } else {
                     System.out.println("Imported Destinations '" + groupName + "' effective exactly-once LB policy is '" + policyFromProperty + "' due to system property '" + JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY + "'");
                  }

                  NodeList queueList = safGroupElement.getElementsByTagName("saf-queue");
                  if (queueList != null && queueList.getLength() > 0) {
                     doImportedDestination(queueList, groupName, agent, context, myRemoteContext, defaultErrorHandler, errorHandlerMap, exactlyOnceLoadBalancingPolicy);
                  }

                  NodeList topicList = safGroupElement.getElementsByTagName("saf-topic");
                  if (topicList != null && topicList.getLength() > 0) {
                     doImportedDestination(topicList, groupName, agent, context, myRemoteContext, defaultErrorHandler, errorHandlerMap, exactlyOnceLoadBalancingPolicy);
                  }
               }
            }

         }
      }
   }

   public static void resolveErrorDestinations(HashMap errorHandlerMap, ContextImpl context) throws JMSException {
      Iterator it = errorHandlerMap.keySet().iterator();

      while(it.hasNext()) {
         ErrorHandler errorHandler = (ErrorHandler)errorHandlerMap.get(it.next());
         String errorDestinationName = errorHandler.getErrorDestinationName();
         if (errorDestinationName != null) {
            int howMany = context.howManyDestinationsWithThisName(errorDestinationName);
            if (howMany > 1) {
               throw new JMSException("There are " + howMany + " SAF destinations with the name " + errorDestinationName + ".  Hence a destination with that name cannot be used as an error destination");
            }

            if (howMany < 1) {
               throw new JMSException("No error destination of name " + errorDestinationName + " was found");
            }

            DestinationImpl errorDestination = context.getDestination(errorDestinationName);
            errorHandler.setErrorDestination(errorDestination);
         }
      }

   }

   private static String versionedName(String sequenceName) {
      SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
      return sequenceName + fmt.format(new Date(System.currentTimeMillis()));
   }

   private static String getBaseSequenceName(String destName) {
      return "ClientSAF_" + destName + "@";
   }

   private static String getLatestSequenceName(String sequenceName, Queue kernelQueue) {
      Collection sequences = kernelQueue.getSequences();
      if (sequences != null && sequences.size() != 0) {
         Iterator itr = sequences.iterator();
         String latestSequenceName = sequenceName;

         while(itr.hasNext()) {
            Sequence sequence = (Sequence)itr.next();
            if (sequence.getName().contains(sequenceName) && sequence.getName().compareTo(latestSequenceName) > 0) {
               latestSequenceName = sequence.getName();
            }
         }

         return latestSequenceName;
      } else {
         return versionedName(sequenceName);
      }
   }

   public static String getSequenceNameFromQueue(Queue queue) {
      return getLatestSequenceName(getBaseSequenceName(queue.getName()), queue);
   }

   private static class PersistentStoreBeanImpl implements PersistentStoreBean {
      private static final String DEFAULT_DIRECTORY = "stores/default";
      private static final String DEFAULT_POLICY = "Direct-Write";
      private String storeDirectory;
      private String policy;

      private PersistentStoreBeanImpl() {
         this.storeDirectory = "stores/default";
         this.policy = "Direct-Write";
      }

      private void setStoreDirectory(String paramStoreDirectory) {
         this.storeDirectory = paramStoreDirectory;
      }

      public String getStoreDirectory() {
         return this.storeDirectory;
      }

      private void setPolicy(String paramPolicy) {
         this.policy = paramPolicy;
      }

      public String getSynchronousWritePolicy() {
         return this.policy;
      }

      // $FF: synthetic method
      PersistentStoreBeanImpl(Object x0) {
         this();
      }
   }
}
