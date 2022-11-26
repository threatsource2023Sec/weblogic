package com.oracle.weblogic.lifecycle.provisioning.core.handlers.configxml;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.DeprovisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.InitialProvisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationProperty;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.xml.xpath.XPathExpressionException;
import org.glassfish.hk2.api.messaging.MessageReceiver;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.EditException;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.edit.EditTimedOutException;

@Component("WebLogic")
@com.oracle.weblogic.lifecycle.provisioning.api.annotations.Handler
@MessageReceiver({ProvisioningEvent.class})
@ProvisioningOperationScoped
@Service
public class WebLogicPartitionConfigXmlHandler extends AbstractWebLogicPartitionConfigXmlHandler {
   private static final String[] BOOLEAN_LONG_CLASSNAME_ARRAY = new String[]{Boolean.class.getName(), Long.class.getName()};
   private static final String[] SINGLE_STRING_CLASSNAME_ARRAY = new String[]{String.class.getName()};
   private static final String[] TWO_STRING_CLASSNAME_ARRAY = new String[]{String.class.getName(), String.class.getName()};
   private static final String[] SINGLE_LONG_CLASSNAME_ARRAY = new String[]{Long.class.getName()};
   private static final String[] TWO_INTEGER_CLASSNAME_ARRAY = new String[]{Integer.class.getName(), Integer.class.getName()};
   private static final String[] SINGLE_OBJECTNAME_CLASSNAME_ARRAY = new String[]{ObjectName.class.getName()};
   private String editSessionName;
   private final Integer startEditWaitTime;
   private final Integer startEditTimeout;
   private final Long resolutionTimeout;
   private final Long activationTimeout;

   /** @deprecated */
   @Deprecated
   public WebLogicPartitionConfigXmlHandler() throws XPathExpressionException {
      this((String)null, 60000, 120000, 120000L, 120000L);
   }

   @Inject
   public WebLogicPartitionConfigXmlHandler(@ProvisioningOperationProperty("wlsPartitionName") String partitionName, @ProvisioningOperationProperty(value = "startEditWaitTime",defaultValue = "60000") Integer startEditWaitTime, @ProvisioningOperationProperty(value = "startEditTimeout",defaultValue = "120000") Integer startEditTimeout, @ProvisioningOperationProperty(value = "resolutionTimeout",defaultValue = "120000") Long resolutionTimeout, @ProvisioningOperationProperty(value = "activationTimeout",defaultValue = "120000") Long activationTimeout) throws XPathExpressionException {
      super(partitionName);
      this.startEditWaitTime = startEditWaitTime == null ? 60000 : startEditWaitTime;
      this.startEditTimeout = startEditTimeout == null ? 120000 : startEditTimeout;
      this.resolutionTimeout = resolutionTimeout == null ? 120000L : resolutionTimeout;
      this.activationTimeout = activationTimeout == null ? 120000L : activationTimeout;
   }

   public String getEditSessionName() {
      return this.editSessionName;
   }

   public void provision(@InitialProvisioningOperation @SubscribeTo @WebLogicConfigXml ProvisioningEvent provisioningEvent) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "provision";
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "provision", new Object[]{provisioningEvent});
      }

      if (provisioningEvent != null) {
         this.handle(provisioningEvent, new Creator());
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "provision");
      }

   }

   public void deprovision(@DeprovisioningOperation @SubscribeTo @WebLogicConfigXml ProvisioningEvent provisioningEvent) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "deprovision";
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "deprovision", new Object[]{provisioningEvent});
      }

      if (provisioningEvent != null) {
         this.handle(provisioningEvent, new Destroyer());
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "deprovision");
      }

   }

   private final void handle(ProvisioningEvent provisioningEvent, Handler handler) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "handle";
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "handle", new Object[]{provisioningEvent, handler});
      }

      if (provisioningEvent != null && handler != null) {
         Document configXml = provisioningEvent.getDocument();
         if (configXml != null) {
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, cn, "handle", "Processing {0}", configXml.getDocumentURI());
            }

            String partitionName = this.getPartitionName();
            if (partitionName == null) {
               throw new NoSuchPartitionException("null");
            }

            NodeList resourceGroupNodes = null;

            try {
               resourceGroupNodes = getResourceGroupNodes(configXml);
            } catch (XPathExpressionException var40) {
               throw new ProvisioningException(var40);
            }

            assert resourceGroupNodes != null;

            NodeList jdbcSystemResourceOverrideNodes = null;

            try {
               jdbcSystemResourceOverrideNodes = getJdbcSystemResourceOverrideNodes(configXml);
            } catch (XPathExpressionException var39) {
               throw new ProvisioningException(var39);
            }

            assert jdbcSystemResourceOverrideNodes != null;

            String editSessionName = this.getEditSessionName();
            boolean clientManaged;
            if (editSessionName == null) {
               editSessionName = "com-oracle-weblogic-lifecycle-provisioning-core-" + Calendar.getInstance().getTimeInMillis();
               clientManaged = false;
            } else {
               clientManaged = true;
            }

            assert editSessionName != null;

            ObjectName editSessionManagerObjectName = null;
            ObjectName editSessionObjectName = null;
            ObjectName configurationManagerObjectName = null;
            JMXConnector editSessionConnector = null;
            JMXConnector domainRuntimeConnector = null;
            MBeanServerConnection domainRuntimeConnection = null;
            MBeanServerConnection editSessionConnection = null;

            try {
               domainRuntimeConnector = getConnector("weblogic.management.mbeanservers.domainruntime");

               assert domainRuntimeConnector != null;

               domainRuntimeConnection = domainRuntimeConnector.getMBeanServerConnection();

               assert domainRuntimeConnection != null;

               ObjectName domainRuntimeObjectName = (ObjectName)domainRuntimeConnection.getAttribute(ObjectName.getInstance(DomainRuntimeServiceMBean.OBJECT_NAME), "DomainRuntime");

               assert domainRuntimeObjectName != null;

               editSessionManagerObjectName = (ObjectName)domainRuntimeConnection.getAttribute(domainRuntimeObjectName, "EditSessionConfigurationManager");

               assert editSessionManagerObjectName != null;

               editSessionObjectName = (ObjectName)domainRuntimeConnection.invoke(editSessionManagerObjectName, "lookupEditSessionConfiguration", new String[]{editSessionName}, SINGLE_STRING_CLASSNAME_ARRAY);
               if (editSessionObjectName == null) {
                  if (clientManaged) {
                     throw new RuntimeException(editSessionName + " not found");
                  }

                  editSessionObjectName = (ObjectName)domainRuntimeConnection.invoke(editSessionManagerObjectName, "createEditSessionConfiguration", new String[]{editSessionName, "LCM Edit Session"}, TWO_STRING_CLASSNAME_ARRAY);
               } else if (Boolean.TRUE.equals((Boolean)domainRuntimeConnection.getAttribute(editSessionObjectName, "MergeNeeded"))) {
                  this.resolve(domainRuntimeConnection);
               }

               assert editSessionObjectName != null;

               editSessionConnector = getConnector((String)domainRuntimeConnection.getAttribute(editSessionObjectName, "EditSessionServerJndi"));

               assert editSessionConnector != null;

               editSessionConnection = editSessionConnector.getMBeanServerConnection();

               assert editSessionConnection != null;

               configurationManagerObjectName = (ObjectName)editSessionConnection.getAttribute(ObjectName.getInstance(EditServiceMBean.OBJECT_NAME), "ConfigurationManager");

               assert configurationManagerObjectName != null;

               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, cn, "handle", "Starting edit session (invoking \"startEdit\" on {0})", configurationManagerObjectName);
               }

               ObjectName domainObjectName = (ObjectName)editSessionConnection.invoke(configurationManagerObjectName, "startEdit", new Integer[]{this.startEditWaitTime, this.startEditTimeout}, TWO_INTEGER_CLASSNAME_ARRAY);

               assert domainObjectName != null;

               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, cn, "handle", "Edit session started ({0} returned from startEdit)", domainObjectName);
               }

               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, cn, "handle", "Looking up partition {0}", partitionName);
               }

               ObjectName partitionObjectName = (ObjectName)editSessionConnection.invoke(domainObjectName, "lookupPartition", new String[]{partitionName}, SINGLE_STRING_CLASSNAME_ARRAY);
               if (partitionObjectName == null) {
                  throw new NoSuchPartitionException(partitionName);
               }

               assert partitionObjectName != null;

               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, cn, "handle", "Retrieved partition {0}", partitionObjectName);
               }

               handler.initialize(editSessionConnection, domainObjectName, partitionObjectName, resourceGroupNodes, jdbcSystemResourceOverrideNodes);
               handler.call();
               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, cn, "handle", "Completed processing {0}.", configXml.getDocumentURI());
               }

               if (!clientManaged) {
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, cn, "handle", "Resolving...");
                  }

                  ObjectName resolutionTaskObjectName = (ObjectName)editSessionConnection.invoke(configurationManagerObjectName, "resolve", new Object[]{Boolean.TRUE, this.resolutionTimeout}, BOOLEAN_LONG_CLASSNAME_ARRAY);

                  assert resolutionTaskObjectName != null;

                  Exception error = (Exception)editSessionConnection.getAttribute(resolutionTaskObjectName, "Error");
                  if (error != null) {
                     throw error;
                  }

                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, cn, "handle", "Resolved: {0}", resolutionTaskObjectName);
                  }

                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, cn, "handle", "Saving...");
                  }

                  editSessionConnection.invoke(configurationManagerObjectName, "save", (Object[])null, (String[])null);
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, cn, "handle", "Saved.");
                  }

                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, cn, "handle", "Activating...");
                  }

                  ObjectName activationTaskObjectName = (ObjectName)editSessionConnection.invoke(configurationManagerObjectName, "activate", new Long[]{this.activationTimeout}, SINGLE_LONG_CLASSNAME_ARRAY);

                  assert activationTaskObjectName != null;

                  Object errorAttributeValue = editSessionConnection.getAttribute(activationTaskObjectName, "Error");
                  if (errorAttributeValue != null) {
                     if (errorAttributeValue instanceof Exception) {
                        throw (Exception)errorAttributeValue;
                     }

                     if (logger != null && logger.isLoggable(Level.SEVERE)) {
                        logger.logp(Level.SEVERE, cn, "handle", "{0}", errorAttributeValue);
                     }

                     throw new ProvisioningException("Activation failed because of " + errorAttributeValue);
                  }

                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, cn, "handle", "Activated.");
                  }
               }
            } catch (Exception var45) {
               if (!clientManaged && configurationManagerObjectName != null && editSessionConnection != null) {
                  try {
                     if (Boolean.TRUE.equals((Boolean)editSessionConnection.getAttribute(configurationManagerObjectName, "Editor"))) {
                        editSessionConnection.invoke(configurationManagerObjectName, "undo", (Object[])null, (String[])null);
                     }
                  } catch (Exception var44) {
                     if (logger != null && logger.isLoggable(Level.SEVERE)) {
                        logger.logp(Level.SEVERE, cn, "handle", "Exception while invoking \"undo\" operation on " + configurationManagerObjectName, var44);
                     }
                  }
               }

               if (var45 instanceof RuntimeException) {
                  throw (RuntimeException)var45;
               }

               throw new ProvisioningException(var45);
            } finally {
               if (editSessionObjectName != null && !clientManaged && domainRuntimeConnection != null) {
                  assert editSessionManagerObjectName != null;

                  try {
                     if (logger != null && logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, cn, "handle", "Destroying edit session {0}...", editSessionObjectName);
                     }

                     domainRuntimeConnection.invoke(editSessionManagerObjectName, "destroyEditSessionConfiguration", new Object[]{editSessionObjectName}, SINGLE_OBJECTNAME_CLASSNAME_ARRAY);
                     if (logger != null && logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, cn, "handle", "Destroyed.");
                     }
                  } catch (Exception var43) {
                     if (logger != null && logger.isLoggable(Level.SEVERE)) {
                        logger.logp(Level.SEVERE, cn, "handle", "Exception while invoking \"destroyEditSessionConfiguration\" operation on " + editSessionManagerObjectName, var43);
                     }
                  }
               }

               if (editSessionConnector != null) {
                  try {
                     if (logger != null && logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, cn, "handle", "Closing edit session connector {0}", editSessionConnector);
                     }

                     editSessionConnector.close();
                     if (logger != null && logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, cn, "handle", "Closed.");
                     }
                  } catch (IOException var42) {
                     if (logger != null && logger.isLoggable(Level.SEVERE)) {
                        logger.logp(Level.SEVERE, cn, "handle", "Exception while closing " + editSessionConnector, var42);
                     }
                  }
               }

               if (domainRuntimeConnector != null) {
                  try {
                     if (logger != null && logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, cn, "handle", "Closing domain runtime connector {0}", domainRuntimeConnector);
                     }

                     domainRuntimeConnector.close();
                     if (logger != null && logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, cn, "handle", "Closed.");
                     }
                  } catch (IOException var41) {
                     if (logger != null && logger.isLoggable(Level.SEVERE)) {
                        logger.logp(Level.SEVERE, cn, "handle", "Exception while closing " + domainRuntimeConnector, var41);
                     }
                  }
               }

            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "handle");
      }

   }

   private static final JMXConnector getConnector(String mBeanServerId) throws IOException {
      String cn = WebLogicPartitionConfigXmlHandler.class.getName();
      String mn = "getConnector";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "getConnector", mBeanServerId);
      }

      Objects.requireNonNull(mBeanServerId);
      Map properties = new HashMap();
      properties.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      String urlPath = "/jndi/" + mBeanServerId;
      JMXServiceURL serviceURL = new JMXServiceURL("wlx", (String)null, 0, urlPath);
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, cn, "getConnector", "Connecting to {0}", serviceURL);
      }

      JMXConnector returnValue = JMXConnectorFactory.connect(serviceURL, properties);
      if (logger != null) {
         if (logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "getConnector", "Connected to {0}", serviceURL);
         }

         if (logger.isLoggable(Level.FINER)) {
            logger.exiting(cn, "getConnector", returnValue);
         }
      }

      return returnValue;
   }

   private static final void createResourceGroups(MBeanServerConnection editSessionConnection, ObjectName domainObjectName, ObjectName partitionObjectName, NodeList resourceGroupNodes) throws AttributeNotFoundException, EditTimedOutException, IllegalResourceGroupTargetingSpecificationException, InstanceNotFoundException, InvalidAttributeValueException, IOException, MalformedObjectNameException, MBeanException, NoSuchAvailableTargetException, NoSuchResourceGroupTemplateException, ProvisioningException, ReflectionException, XPathExpressionException {
      String cn = WebLogicPartitionConfigXmlHandler.class.getName();
      String mn = "createResourceGroups";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "createResourceGroups", new Object[]{editSessionConnection, domainObjectName, partitionObjectName, resourceGroupNodes});
      }

      Objects.requireNonNull(editSessionConnection);
      Objects.requireNonNull(domainObjectName);
      Objects.requireNonNull(partitionObjectName);
      Objects.requireNonNull(resourceGroupNodes);
      Map rgtObjectNames = new HashMap();
      Map validRgElements = getValidResourceGroupElements(resourceGroupNodes);

      assert validRgElements != null;

      Set entrySet = validRgElements.entrySet();

      assert entrySet != null : "getValidResourceGroupElements(NodeList).entrySet() == null";

      Iterator var10 = entrySet.iterator();

      while(var10.hasNext()) {
         Map.Entry entry = (Map.Entry)var10.next();

         assert entry != null;

         Element rgElement = (Element)entry.getValue();

         assert rgElement != null;

         Element rgElement = (Element)entry.getValue();

         assert rgElement != null;

         ObjectName rgtObjectName = null;
         String rgtName = getResourceGroupTemplateName(rgElement);
         if (rgtName != null) {
            rgtObjectName = (ObjectName)rgtObjectNames.get(rgtName);
            if (rgtObjectName == null) {
               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, cn, "createResourceGroups", "Looking up resource group template {0}", rgtName);
               }

               rgtObjectName = (ObjectName)editSessionConnection.invoke(domainObjectName, "lookupResourceGroupTemplate", new String[]{rgtName}, SINGLE_STRING_CLASSNAME_ARRAY);
               if (rgtObjectName == null) {
                  throw new NoSuchResourceGroupTemplateException(rgtName);
               }

               rgtObjectNames.put(rgtName, rgtObjectName);
            }
         }

         Collection targets = getTargets(rgElement);

         assert targets != null;

         Boolean useDefaultTarget = getUseDefaultTarget(rgElement);
         Boolean administrative = getAdministrative(rgElement);
         Boolean autoTargetAdminServer = getAutoTargetAdminServer(rgElement);
         createResourceGroup(editSessionConnection, partitionObjectName, rgName, rgtObjectName, targets, useDefaultTarget, administrative, autoTargetAdminServer);
      }

      rgtObjectNames.clear();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "createResourceGroups");
      }

   }

   private static final void createResourceGroup(MBeanServerConnection editSessionConnection, ObjectName partitionObjectName, String rgName, ObjectName resourceGroupTemplateObjectName, Collection targets, Boolean useDefaultTarget, Boolean administrative, Boolean autoTargetAdminServer) throws AttributeNotFoundException, IllegalResourceGroupTargetingSpecificationException, InstanceNotFoundException, InvalidAttributeValueException, IOException, MBeanException, NoSuchAvailableTargetException, ProvisioningException, ReflectionException, XPathExpressionException {
      String cn = WebLogicPartitionConfigXmlHandler.class.getName();
      String mn = "createResourceGroup";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "createResourceGroup", new Object[]{editSessionConnection, partitionObjectName, rgName, resourceGroupTemplateObjectName, targets, useDefaultTarget, administrative, autoTargetAdminServer});
      }

      Objects.requireNonNull(editSessionConnection);
      Objects.requireNonNull(partitionObjectName);
      Objects.requireNonNull(rgName);
      validateTargeting(targets, useDefaultTarget, administrative, autoTargetAdminServer);
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, cn, "createResourceGroup", "Retrieving resource group {0}", rgName);
      }

      ObjectName resourceGroupObjectName = (ObjectName)editSessionConnection.invoke(partitionObjectName, "lookupResourceGroup", new String[]{rgName}, SINGLE_STRING_CLASSNAME_ARRAY);
      if (resourceGroupObjectName == null) {
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "createResourceGroup", "Creating resource group {0}", rgName);
         }

         resourceGroupObjectName = (ObjectName)editSessionConnection.invoke(partitionObjectName, "createResourceGroup", new String[]{rgName}, SINGLE_STRING_CLASSNAME_ARRAY);
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "createResourceGroup", "Resource group {0} created", rgName);
         }
      } else if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, cn, "createResourceGroup", "Resource group {0} retrieved", rgName);
      }

      assert resourceGroupObjectName != null;

      AttributeList attributeList = new AttributeList(5);
      if (resourceGroupTemplateObjectName != null) {
         attributeList.add(new Attribute("ResourceGroupTemplate", resourceGroupTemplateObjectName));
      }

      if (targets != null && !targets.isEmpty()) {
         Collection targetObjectNames = new ArrayList();
         Iterator var14 = targets.iterator();

         while(var14.hasNext()) {
            String target = (String)var14.next();
            if (target != null && !target.isEmpty()) {
               target = target.trim();
               if (!target.isEmpty()) {
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, cn, "createResourceGroup", "Looking up available target {0}", target);
                  }

                  ObjectName targetObjectName = (ObjectName)editSessionConnection.invoke(partitionObjectName, "lookupAvailableTarget", new String[]{target}, SINGLE_STRING_CLASSNAME_ARRAY);
                  if (targetObjectName == null) {
                     throw new NoSuchAvailableTargetException(target);
                  }

                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, cn, "createResourceGroup", "Found available target MBean: {0}", targetObjectName);
                  }

                  targetObjectNames.add(targetObjectName);
               }
            }
         }

         if (!targetObjectNames.isEmpty()) {
            attributeList.add(new Attribute("Targets", targetObjectNames.toArray(new ObjectName[targetObjectNames.size()])));
         }
      }

      if (useDefaultTarget != null) {
         attributeList.add(new Attribute("UseDefaultTarget", useDefaultTarget));
      }

      if (administrative != null) {
         attributeList.add(new Attribute("Administrative", administrative));
      }

      if (autoTargetAdminServer != null) {
         attributeList.add(new Attribute("AutoTargetAdminServer", autoTargetAdminServer));
      }

      assert attributeList != null;

      if (!attributeList.isEmpty()) {
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "createResourceGroup", "Setting attributes ({0}) on resource group {1}", new Object[]{attributeList, resourceGroupObjectName});
         }

         editSessionConnection.setAttributes(resourceGroupObjectName, attributeList);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "createResourceGroup");
      }

   }

   private static final void deleteResourceGroups(MBeanServerConnection editSessionConnection, ObjectName domainObjectName, ObjectName partitionObjectName, NodeList resourceGroupNodes) throws InstanceNotFoundException, IOException, MBeanException, ReflectionException {
      String cn = WebLogicPartitionConfigXmlHandler.class.getName();
      String mn = "deleteResourceGroups";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "deleteResourceGroups", new Object[]{editSessionConnection, domainObjectName, partitionObjectName, resourceGroupNodes});
      }

      Objects.requireNonNull(editSessionConnection);
      Objects.requireNonNull(domainObjectName);
      Objects.requireNonNull(partitionObjectName);
      Objects.requireNonNull(resourceGroupNodes);
      int size = resourceGroupNodes.getLength();

      for(int i = size - 1; i >= 0; --i) {
         Element rgElement = (Element)resourceGroupNodes.item(i);
         if (rgElement != null) {
            NodeList names = rgElement.getElementsByTagNameNS("*", "name");
            if (names != null && names.getLength() == 1) {
               Element nameElement = (Element)names.item(0);
               if (nameElement != null) {
                  String rgName = nameElement.getTextContent();
                  if (rgName != null && !rgName.isEmpty()) {
                     rgName = rgName.trim();
                     if (!rgName.isEmpty()) {
                        deleteResourceGroup(editSessionConnection, partitionObjectName, rgName);
                     }
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "deleteResourceGroups");
      }

   }

   private static final void deleteResourceGroup(MBeanServerConnection editSessionConnection, ObjectName partitionObjectName, String rgName) throws InstanceNotFoundException, IOException, MBeanException, ReflectionException {
      String cn = WebLogicPartitionConfigXmlHandler.class.getName();
      String mn = "deleteResourceGroup";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "deleteResourceGroup", new Object[]{editSessionConnection, partitionObjectName, rgName});
      }

      Objects.requireNonNull(editSessionConnection);
      Objects.requireNonNull(partitionObjectName);
      Objects.requireNonNull(rgName);
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, cn, "deleteResourceGroup", "Retrieving resource group {0}", rgName);
      }

      ObjectName resourceGroupObjectName = (ObjectName)editSessionConnection.invoke(partitionObjectName, "lookupResourceGroup", new String[]{rgName}, SINGLE_STRING_CLASSNAME_ARRAY);
      if (resourceGroupObjectName == null) {
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "deleteResourceGroup", "Resource group {0} not found; no action taken", rgName);
         }
      } else {
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "deleteResourceGroup", "Resource group {0} retrieved: {1}", new Object[]{rgName, resourceGroupObjectName});
         }

         editSessionConnection.invoke(partitionObjectName, "destroyResourceGroup", new Object[]{resourceGroupObjectName}, SINGLE_OBJECTNAME_CLASSNAME_ARRAY);
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "deleteResourceGroup", "Resource group {0} destroyed", rgName);
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "deleteResourceGroup");
      }

   }

   private static final void deleteJDBCSystemResourceOverrides(MBeanServerConnection editSessionConnection, ObjectName partitionObjectName, NodeList jdbcSystemResourceOverrideNodes) throws EditTimedOutException, InstanceNotFoundException, InvalidAttributeValueException, IOException, MalformedObjectNameException, MBeanException, ReflectionException {
      String cn = WebLogicPartitionConfigXmlHandler.class.getName();
      String mn = "deleteJDBCSystemResourceOverrides";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "deleteJDBCSystemResourceOverrides", new Object[]{editSessionConnection, partitionObjectName, jdbcSystemResourceOverrideNodes});
      }

      Objects.requireNonNull(editSessionConnection);
      Objects.requireNonNull(partitionObjectName);
      Objects.requireNonNull(jdbcSystemResourceOverrideNodes);
      int size = jdbcSystemResourceOverrideNodes.getLength();

      for(int i = size - 1; i >= 0; --i) {
         Element e = (Element)jdbcSystemResourceOverrideNodes.item(i);
         if (e != null) {
            String name = getScalarTextProperty(e, "name");
            if (name != null && !name.isEmpty()) {
               name = name.trim();
               if (!name.isEmpty()) {
                  deleteJDBCSystemResourceOverride(editSessionConnection, partitionObjectName, name);
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "deleteJDBCSystemResourceOverrides");
      }

   }

   private static final void createJDBCSystemResourceOverrides(MBeanServerConnection editSessionConnection, ObjectName partitionObjectName, NodeList jdbcSystemResourceOverrideNodes) throws EditTimedOutException, InstanceNotFoundException, InvalidAttributeValueException, IOException, MalformedObjectNameException, MBeanException, ReflectionException {
      String cn = WebLogicPartitionConfigXmlHandler.class.getName();
      String mn = "createJDBCSystemResourceOverrides";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "createJDBCSystemResourceOverrides", new Object[]{editSessionConnection, partitionObjectName, jdbcSystemResourceOverrideNodes});
      }

      Objects.requireNonNull(editSessionConnection);
      Objects.requireNonNull(partitionObjectName);
      Objects.requireNonNull(jdbcSystemResourceOverrideNodes);
      int size = jdbcSystemResourceOverrideNodes.getLength();

      for(int i = 0; i < size; ++i) {
         Element e = (Element)jdbcSystemResourceOverrideNodes.item(i);
         if (e != null) {
            String name = getScalarTextProperty(e, "name");
            if (name != null && !name.isEmpty()) {
               name = name.trim();
               if (!name.isEmpty()) {
                  String dataSourceName = getScalarTextProperty(e, "data-source-name");
                  String url = getScalarTextProperty(e, "url");
                  String user = getScalarTextProperty(e, "user");
                  String password = getScalarTextProperty(e, "password");
                  createJDBCSystemResourceOverride(editSessionConnection, partitionObjectName, name, dataSourceName, url, user, password);
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "createJDBCSystemResourceOverrides");
      }

   }

   private static final void createJDBCSystemResourceOverride(MBeanServerConnection editServerConnection, ObjectName partitionObjectName, String name, String dataSourceName, String url, String user, String password) throws InstanceNotFoundException, InvalidAttributeValueException, IOException, MBeanException, ReflectionException {
      String cn = WebLogicPartitionConfigXmlHandler.class.getName();
      String mn = "createJDBCSystemResourceOverride";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "createJDBCSystemResourceOverride", new Object[]{editServerConnection, partitionObjectName, name, dataSourceName, url, user, password});
      }

      Objects.requireNonNull(editServerConnection);
      Objects.requireNonNull(partitionObjectName);
      Objects.requireNonNull(name);
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, cn, "createJDBCSystemResourceOverride", "Looking up JDBC system resource override {0}", name);
      }

      ObjectName jdbcSystemResourceOverrideObjectName = (ObjectName)editServerConnection.invoke(partitionObjectName, "lookupJDBCSystemResourceOverride", new String[]{name}, SINGLE_STRING_CLASSNAME_ARRAY);
      if (jdbcSystemResourceOverrideObjectName == null) {
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "createJDBCSystemResourceOverride", "Creating JDBC system resource override {0}", name);
         }

         jdbcSystemResourceOverrideObjectName = (ObjectName)editServerConnection.invoke(partitionObjectName, "createJDBCSystemResourceOverride", new String[]{name}, SINGLE_STRING_CLASSNAME_ARRAY);
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "createJDBCSystemResourceOverride", "Created JDBC system resource override {0}", jdbcSystemResourceOverrideObjectName);
         }
      } else if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, cn, "createJDBCSystemResourceOverride", "Found JDBC system resource override {0}", jdbcSystemResourceOverrideObjectName);
      }

      assert jdbcSystemResourceOverrideObjectName != null;

      AttributeList attributeList = new AttributeList(4);
      attributeList.add(new Attribute("DataSourceName", dataSourceName));
      attributeList.add(new Attribute("URL", url));
      attributeList.add(new Attribute("User", user));
      attributeList.add(new Attribute("Password", password));
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, cn, "createJDBCSystemResourceOverride", "Setting attributes on the JDBC system resource override {0}: {1}", new Object[]{jdbcSystemResourceOverrideObjectName, attributeList});
      }

      editServerConnection.setAttributes(jdbcSystemResourceOverrideObjectName, attributeList);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "createJDBCSystemResourceOverride");
      }

   }

   private static final void deleteJDBCSystemResourceOverride(MBeanServerConnection editServerConnection, ObjectName partitionObjectName, String name) throws InstanceNotFoundException, IOException, MBeanException, ReflectionException {
      String cn = WebLogicPartitionConfigXmlHandler.class.getName();
      String mn = "deleteJDBCSystemResourceOverride";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "deleteJDBCSystemResourceOverride", new Object[]{editServerConnection, partitionObjectName, name});
      }

      Objects.requireNonNull(editServerConnection);
      Objects.requireNonNull(partitionObjectName);
      Objects.requireNonNull(name);
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, cn, "deleteJDBCSystemResourceOverride", "Looking up JDBC system resource override {0}", name);
      }

      ObjectName jdbcSystemResourceOverrideObjectName = (ObjectName)editServerConnection.invoke(partitionObjectName, "lookupJDBCSystemResourceOverride", new String[]{name}, SINGLE_STRING_CLASSNAME_ARRAY);
      if (jdbcSystemResourceOverrideObjectName == null) {
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "deleteJDBCSystemResourceOverride", "JDBC system resource override {0} not found; no action taken", name);
         }
      } else {
         assert jdbcSystemResourceOverrideObjectName != null;

         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "deleteJDBCSystemResourceOverride", "Found JDBC system resource override {0}", jdbcSystemResourceOverrideObjectName);
            logger.logp(Level.FINE, cn, "deleteJDBCSystemResourceOverride", "Destroying JDBC system resource override {0}", jdbcSystemResourceOverrideObjectName);
         }

         editServerConnection.invoke(partitionObjectName, "destroyJDBCSystemResourceOverride", new Object[]{jdbcSystemResourceOverrideObjectName}, SINGLE_OBJECTNAME_CLASSNAME_ARRAY);
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, cn, "deleteJDBCSystemResourceOverride", "Destroyed JDBC system resource override {0}", name);
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "deleteJDBCSystemResourceOverride");
      }

   }

   private final ObjectName resolve(MBeanServerConnection connection) throws AttributeNotFoundException, EditException, InstanceNotFoundException, IOException, MalformedObjectNameException, MBeanException, ReflectionException {
      String cn = this.getClass().getName();
      String mn = "resolve";
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "resolve", new Object[]{connection});
      }

      Objects.requireNonNull(connection);
      ObjectName configurationManagerObjectName = (ObjectName)connection.getAttribute(ObjectName.getInstance(EditServiceMBean.OBJECT_NAME), "ConfigurationManager");

      assert configurationManagerObjectName != null;

      ObjectName activationTaskObjectName = (ObjectName)connection.invoke(configurationManagerObjectName, "resolve", new Object[]{Boolean.TRUE, this.resolutionTimeout}, BOOLEAN_LONG_CLASSNAME_ARRAY);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "resolve", activationTaskObjectName);
      }

      return activationTaskObjectName;
   }

   private static final class Destroyer extends Handler {
      private Destroyer() {
      }

      public Void call() throws Exception {
         WebLogicPartitionConfigXmlHandler.deleteJDBCSystemResourceOverrides(this.editSessionConnection, this.partitionObjectName, this.jdbcSystemResourceOverrideNodes);
         WebLogicPartitionConfigXmlHandler.deleteResourceGroups(this.editSessionConnection, this.domainObjectName, this.partitionObjectName, this.resourceGroupNodes);
         return null;
      }

      // $FF: synthetic method
      Destroyer(Object x0) {
         this();
      }
   }

   private static final class Creator extends Handler {
      private Creator() {
      }

      public Void call() throws Exception {
         WebLogicPartitionConfigXmlHandler.createResourceGroups(this.editSessionConnection, this.domainObjectName, this.partitionObjectName, this.resourceGroupNodes);
         WebLogicPartitionConfigXmlHandler.createJDBCSystemResourceOverrides(this.editSessionConnection, this.partitionObjectName, this.jdbcSystemResourceOverrideNodes);
         return null;
      }

      // $FF: synthetic method
      Creator(Object x0) {
         this();
      }
   }

   private abstract static class Handler implements Callable {
      protected MBeanServerConnection editSessionConnection;
      protected ObjectName domainObjectName;
      protected ObjectName partitionObjectName;
      protected NodeList resourceGroupNodes;
      protected NodeList jdbcSystemResourceOverrideNodes;

      protected Handler() {
      }

      private final void initialize(MBeanServerConnection editSessionConnection, ObjectName domainObjectName, ObjectName partitionObjectName, NodeList resourceGroupNodes, NodeList jdbcSystemResourceOverrideNodes) {
         Objects.requireNonNull(editSessionConnection);
         Objects.requireNonNull(domainObjectName);
         Objects.requireNonNull(partitionObjectName);
         Objects.requireNonNull(resourceGroupNodes);
         Objects.requireNonNull(jdbcSystemResourceOverrideNodes);
         this.editSessionConnection = editSessionConnection;
         this.domainObjectName = domainObjectName;
         this.partitionObjectName = partitionObjectName;
         this.resourceGroupNodes = resourceGroupNodes;
         this.jdbcSystemResourceOverrideNodes = jdbcSystemResourceOverrideNodes;
      }
   }
}
