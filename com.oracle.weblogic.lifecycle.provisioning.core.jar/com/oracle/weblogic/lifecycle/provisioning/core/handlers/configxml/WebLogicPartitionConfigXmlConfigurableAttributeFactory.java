package com.oracle.weblogic.lifecycle.provisioning.core.handlers.configxml;

import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeFactory;
import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeLiteral;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component("WebLogic")
@PerLookup
@Service
public final class WebLogicPartitionConfigXmlConfigurableAttributeFactory implements ConfigurableAttributeFactory {
   public final Collection getConfigurableAttributes(@WebLogicConfigXml ProvisioningEvent event) {
      String className = WebLogicPartitionConfigXmlConfigurableAttributeFactory.class.getName();
      String methodName = "getConfigurableAttributes";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConfigurableAttributes", event);
      }

      Collection returnValue = null;
      if (event != null) {
         Document payload = event.getDocument();
         if (payload != null) {
            NodeList partitionElements = payload.getElementsByTagNameNS("*", "partition");
            if (partitionElements != null) {
               int partitionElementsSize = partitionElements.getLength();

               for(int i = 0; i < partitionElementsSize; ++i) {
                  Node partitionNode = partitionElements.item(i);
                  if (partitionNode instanceof Element) {
                     Element partitionElement = (Element)partitionNode;
                     NodeList resourceGroupNodes = partitionElement.getElementsByTagNameNS("*", "resource-group");
                     int jdbcSystemResourceOverrideElementsSize;
                     Element jdbcSystemResourceOverrideElement;
                     Node targetNode;
                     String dataSourceName;
                     if (resourceGroupNodes != null) {
                        int size = resourceGroupNodes.getLength();

                        for(jdbcSystemResourceOverrideElementsSize = 0; jdbcSystemResourceOverrideElementsSize < size; ++jdbcSystemResourceOverrideElementsSize) {
                           Element rgElement = (Element)resourceGroupNodes.item(jdbcSystemResourceOverrideElementsSize);
                           if (rgElement != null) {
                              NodeList names = rgElement.getElementsByTagNameNS("*", "name");
                              if (names != null && names.getLength() == 1) {
                                 jdbcSystemResourceOverrideElement = (Element)names.item(0);
                                 if (jdbcSystemResourceOverrideElement != null) {
                                    String rgName = jdbcSystemResourceOverrideElement.getTextContent();
                                    if (rgName != null && !rgName.isEmpty()) {
                                       rgName = rgName.trim();
                                       if (!rgName.isEmpty()) {
                                          NodeList targetNodes = rgElement.getElementsByTagNameNS("*", "target");
                                          if (targetNodes != null) {
                                             int targetNodesSize = targetNodes.getLength();
                                             if (targetNodesSize > 0) {
                                                for(int k = 0; k < targetNodesSize; ++k) {
                                                   targetNode = targetNodes.item(k);
                                                   if (targetNode != null) {
                                                      dataSourceName = targetNode.getTextContent();
                                                      if (dataSourceName == null || dataSourceName.isEmpty()) {
                                                         if (returnValue == null) {
                                                            returnValue = new ArrayList();
                                                         }

                                                         returnValue.add(new ConfigurableAttributeLiteral("resource-group." + rgName + ".target"));
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }

                     NodeList jdbcSystemResourceOverrideElements = partitionElement.getElementsByTagNameNS("*", "jdbc-system-resource-override");
                     if (jdbcSystemResourceOverrideElements != null) {
                        jdbcSystemResourceOverrideElementsSize = jdbcSystemResourceOverrideElements.getLength();

                        for(int j = 0; j < jdbcSystemResourceOverrideElementsSize; ++j) {
                           Node jdbcSystemResourceOverrideNode = jdbcSystemResourceOverrideElements.item(j);
                           if (jdbcSystemResourceOverrideNode instanceof Element) {
                              jdbcSystemResourceOverrideElement = (Element)jdbcSystemResourceOverrideNode;
                              NodeList names = jdbcSystemResourceOverrideElement.getElementsByTagNameNS("*", "name");
                              if (names != null && names.getLength() == 1) {
                                 Node nameElement = names.item(0);
                                 if (nameElement != null) {
                                    String name = nameElement.getTextContent();
                                    if (name != null && !name.isEmpty()) {
                                       name = name.trim();
                                       if (!name.isEmpty()) {
                                          NodeList dataSourceNames = jdbcSystemResourceOverrideElement.getElementsByTagNameNS("*", "data-source-name");
                                          if (dataSourceNames != null && dataSourceNames.getLength() == 1) {
                                             targetNode = dataSourceNames.item(0);
                                             if (targetNode != null) {
                                                dataSourceName = targetNode.getTextContent();
                                                if (dataSourceName != null && !dataSourceName.isEmpty()) {
                                                   dataSourceName = dataSourceName.trim();
                                                   if (!dataSourceName.isEmpty()) {
                                                      if (returnValue == null) {
                                                         returnValue = new ArrayList();
                                                      }

                                                      ConfigurableAttribute ca = createAttribute(jdbcSystemResourceOverrideElement, name, dataSourceName, "url", "url", false);
                                                      if (ca != null) {
                                                         returnValue.add(ca);
                                                      }

                                                      ca = createAttribute(jdbcSystemResourceOverrideElement, name, dataSourceName, "user", "user", false);
                                                      if (ca != null) {
                                                         returnValue.add(ca);
                                                      }

                                                      ca = createAttribute(jdbcSystemResourceOverrideElement, name, dataSourceName, "password", "password", true);
                                                      if (ca != null) {
                                                         returnValue.add(ca);
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      Object returnValue;
      if (returnValue == null) {
         returnValue = Collections.emptySet();
      } else {
         returnValue = Collections.unmodifiableCollection(returnValue);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getConfigurableAttributes", returnValue);
      }

      return (Collection)returnValue;
   }

   private static final ConfigurableAttribute createAttribute(Element jdbcSystemResourceOverrideElement, String jdbcSystemResourceOverrideName, String dataSourceName, String tagLocalName, String shortAttributeName, boolean sensitive) {
      Objects.requireNonNull(jdbcSystemResourceOverrideElement);
      Objects.requireNonNull(jdbcSystemResourceOverrideName);
      Objects.requireNonNull(dataSourceName);
      Objects.requireNonNull(tagLocalName);
      Objects.requireNonNull(shortAttributeName);
      ConfigurableAttribute returnValue = null;
      NodeList requestedElements = jdbcSystemResourceOverrideElement.getElementsByTagNameNS("*", tagLocalName);
      if (requestedElements != null && requestedElements.getLength() == 1) {
         Node requestedNode = requestedElements.item(0);
         if (requestedNode instanceof Element && !requestedNode.hasChildNodes()) {
            String requestedAttributeName = "jdbc-data-source." + dataSourceName + "." + shortAttributeName;
            returnValue = new ConfigurableAttributeLiteral(requestedAttributeName, "", "", sensitive);
         }
      }

      return returnValue;
   }
}
