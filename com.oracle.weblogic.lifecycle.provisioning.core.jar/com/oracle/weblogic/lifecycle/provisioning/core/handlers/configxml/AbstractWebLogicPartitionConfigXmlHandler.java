package com.oracle.weblogic.lifecycle.provisioning.core.handlers.configxml;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractWebLogicPartitionConfigXmlHandler {
   private static XPathExpression resourceGroupXPathExpression;
   private static XPathExpression jdbcSystemResourceOverrideXPathExpression;
   private static XPathExpressionException xpathFailure;
   private final String partitionName;

   protected AbstractWebLogicPartitionConfigXmlHandler(String partitionName) throws XPathExpressionException {
      if (xpathFailure != null) {
         throw xpathFailure;
      } else {
         this.partitionName = partitionName;
      }
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   protected Logger getLogger() {
      return Logger.getLogger(this.getClass().getName());
   }

   public static final String getResourceGroupName(Node resourceGroupNode) throws ProvisioningException, XPathExpressionException {
      if (xpathFailure != null) {
         throw xpathFailure;
      } else if (!(resourceGroupNode instanceof Element)) {
         throw new ProvisioningException("!(resourceGroupNode instanceof Element): " + resourceGroupNode);
      } else {
         return getResourceGroupName((Element)resourceGroupNode);
      }
   }

   public static final String getResourceGroupName(Element resourceGroupElement) throws ProvisioningException, XPathExpressionException {
      if (xpathFailure != null) {
         throw xpathFailure;
      } else if (resourceGroupElement == null) {
         throw new ProvisioningException(new IllegalArgumentException("resourceGroupElement == null"));
      } else {
         NodeList names = resourceGroupElement.getElementsByTagNameNS("*", "name");
         if (names == null) {
            throw new ProvisioningException("resourceGroupElement.getElementsByTagNameNS(\"*\", \"name\") == null");
         } else if (names.getLength() != 1) {
            throw new ProvisioningException("resourceGroupElement.getElementsByTagNameNS(\"*\", \"name\").getLength() != 1: " + names.getLength());
         } else {
            Element nameElement = (Element)names.item(0);
            if (nameElement == null) {
               throw new ProvisioningException("resourceGroupElement.getElementsByTagNameNS(\"*\", \"name\").item(0) == null");
            } else {
               String resourceGroupName = nameElement.getTextContent();
               if (resourceGroupName == null) {
                  throw new ProvisioningException("resourceGroupElement.getElementsByTagNameNS(\"*\", \"name\").item(0).getTextContent() == null");
               } else if (resourceGroupName.isEmpty()) {
                  throw new ProvisioningException("resourceGroupElement.getElementsByTagNameNS(\"*\", \"name\").item(0).getTextContent().isEmpty()");
               } else {
                  resourceGroupName = resourceGroupName.trim();
                  if (resourceGroupName.isEmpty()) {
                     throw new ProvisioningException("resourceGroupElement.getElementsByTagNameNS(\"*\", \"name\").item(0).getTextContent().trim().isEmpty()");
                  } else {
                     assert resourceGroupName != null;

                     return resourceGroupName;
                  }
               }
            }
         }
      }
   }

   public static final String getResourceGroupTemplateName(Element resourceGroupElement) throws ProvisioningException, XPathExpressionException {
      if (xpathFailure != null) {
         throw xpathFailure;
      } else if (resourceGroupElement == null) {
         throw new ProvisioningException(new IllegalArgumentException("resourceGroupElement == null"));
      } else {
         String rgtName = null;
         NodeList resourceGroupTemplates = resourceGroupElement.getElementsByTagNameNS("*", "resource-group-template");
         if (resourceGroupTemplates != null) {
            int length = resourceGroupTemplates.getLength();
            if (length == 1) {
               Node rgtNode = resourceGroupTemplates.item(0);
               if (!(rgtNode instanceof Element)) {
                  throw new ProvisioningException("resourceGroupElement.getElementsByTagName(\"*\", \"resource-group-template\").item(0) == null");
               }

               Element rgtElement = (Element)rgtNode;
               rgtName = rgtElement.getTextContent();
               if (rgtName == null) {
                  throw new ProvisioningException("resourceGroupElement.getElementsByTagName(\"*\", \"resource-group-template\").item(0).getTextContent() == null");
               }

               rgtName = rgtName.trim();
               if (rgtName.isEmpty()) {
                  throw new ProvisioningException("resourceGroupElement.getElementsByTagName(\"*\", \"resource-group-template\").item(0).getTextContent().trim().isEmpty()");
               }
            } else if (length > 1) {
               throw new ProvisioningException("resourceGroupElement.getElementsByTagName(\"*\", \"resource-group-template\").getLength() > 1: " + resourceGroupTemplates.getLength());
            }
         }

         return rgtName;
      }
   }

   public static final Boolean getAdministrative(Element resourceGroupElement) throws ProvisioningException, XPathExpressionException {
      return getScalarBooleanProperty(resourceGroupElement, "administrative");
   }

   public static final Boolean getAutoTargetAdminServer(Element resourceGroupElement) throws ProvisioningException, XPathExpressionException {
      return getScalarBooleanProperty(resourceGroupElement, "auto-target-admin-server");
   }

   public static final Boolean getUseDefaultTarget(Element resourceGroupElement) throws ProvisioningException, XPathExpressionException {
      return getScalarBooleanProperty(resourceGroupElement, "use-default-target");
   }

   public static final void validateTargeting(Collection targets, Boolean useDefaultTarget, Boolean administrative, Boolean autoTargetAdminServer) throws ProvisioningException, XPathExpressionException {
      if (xpathFailure != null) {
         throw xpathFailure;
      } else if (targets != null && !targets.isEmpty() && Boolean.TRUE.equals(useDefaultTarget)) {
         throw new IllegalResourceGroupTargetingSpecificationException(targets, useDefaultTarget);
      }
   }

   public static final Collection getTargets(Element resourceGroupElement) throws ProvisioningException {
      if (resourceGroupElement == null) {
         throw new ProvisioningException(new IllegalArgumentException("resourceGroupElement == null"));
      } else {
         Collection targets = new ArrayList();
         NodeList targetNodes = resourceGroupElement.getElementsByTagNameNS("*", "target");
         if (targetNodes != null) {
            int targetNodesSize = targetNodes.getLength();
            if (targetNodesSize > 0) {
               for(int j = 0; j < targetNodesSize; ++j) {
                  Node targetNode = targetNodes.item(j);
                  if (targetNode != null) {
                     String targetNodeTextContent = targetNode.getTextContent();
                     if (targetNodeTextContent != null && !targetNodeTextContent.isEmpty()) {
                        String[] targetNodeTextTokens = targetNodeTextContent.split("\\s*,\\s*");
                        if (targetNodeTextTokens != null && targetNodeTextTokens.length > 0) {
                           String[] var8 = targetNodeTextTokens;
                           int var9 = targetNodeTextTokens.length;

                           for(int var10 = 0; var10 < var9; ++var10) {
                              String token = var8[var10];
                              if (token != null && !token.isEmpty()) {
                                 token = token.trim();
                                 if (!token.isEmpty()) {
                                    targets.add(token);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return targets;
      }
   }

   public static final NodeList getJdbcSystemResourceOverrideNodes(Document configXml) throws XPathExpressionException {
      if (xpathFailure != null) {
         throw xpathFailure;
      } else {
         assert jdbcSystemResourceOverrideXPathExpression != null;

         NodeList returnValue;
         synchronized(jdbcSystemResourceOverrideXPathExpression) {
            returnValue = (NodeList)jdbcSystemResourceOverrideXPathExpression.evaluate(configXml, XPathConstants.NODESET);
         }

         assert returnValue != null;

         return returnValue;
      }
   }

   public static final NodeList getResourceGroupNodes(Document configXml) throws XPathExpressionException {
      if (xpathFailure != null) {
         throw xpathFailure;
      } else {
         assert resourceGroupXPathExpression != null;

         NodeList returnValue;
         synchronized(resourceGroupXPathExpression) {
            returnValue = (NodeList)resourceGroupXPathExpression.evaluate(configXml, XPathConstants.NODESET);
         }

         assert returnValue != null;

         return returnValue;
      }
   }

   protected static final Map getValidResourceGroupElements(NodeList resourceGroupNodes) throws ProvisioningException, XPathExpressionException {
      if (xpathFailure != null) {
         throw xpathFailure;
      } else {
         String cn = AbstractWebLogicPartitionConfigXmlHandler.class.getName();
         String mn = "getValidResourceGroupElements";
         Logger logger = Logger.getLogger(cn);
         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.entering(cn, "getValidResourceGroupElements", resourceGroupNodes);
         }

         Objects.requireNonNull(resourceGroupNodes);
         Map returnValue = new HashMap();
         int size = resourceGroupNodes.getLength();

         for(int i = 0; i < size; ++i) {
            Node rgNode = resourceGroupNodes.item(i);
            if (rgNode instanceof Element) {
               Element resourceGroupElement = (Element)rgNode;
               String resourceGroupName = getResourceGroupName(resourceGroupElement);

               assert resourceGroupName != null;

               assert !resourceGroupName.isEmpty();

               returnValue.put(resourceGroupName, resourceGroupElement);
            }
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(cn, "getValidResourceGroupElements", returnValue);
         }

         return returnValue;
      }
   }

   protected static final Boolean getScalarBooleanProperty(Element root, String elementLocalName) throws ProvisioningException, XPathExpressionException {
      Objects.requireNonNull(root);
      Objects.requireNonNull(elementLocalName);
      if (xpathFailure != null) {
         throw xpathFailure;
      } else {
         Boolean returnValue = null;
         NodeList nodes = root.getElementsByTagNameNS("*", elementLocalName);
         if (nodes != null) {
            int nodesSize = nodes.getLength();
            if (nodesSize == 1) {
               Node node = nodes.item(0);
               if (node != null) {
                  String nodeTextContent = node.getTextContent();
                  if (nodeTextContent != null) {
                     nodeTextContent = nodeTextContent.trim();
                     if (!nodeTextContent.isEmpty()) {
                        returnValue = Boolean.valueOf(nodeTextContent);
                     }
                  }
               }
            } else if (nodesSize > 1) {
               throw new ProvisioningException("getElementsByTagNameNS(\"*\", \"" + elementLocalName + "\").getLength() > 1: " + nodesSize);
            }
         }

         return returnValue;
      }
   }

   protected static final String getScalarTextProperty(Element root, String elementLocalName) {
      Objects.requireNonNull(root);
      Objects.requireNonNull(elementLocalName);
      NodeList elements = root.getElementsByTagNameNS("*", elementLocalName);
      String returnValue;
      if (elements != null && elements.getLength() == 1) {
         Element element = (Element)elements.item(0);
         if (element == null) {
            returnValue = null;
         } else {
            returnValue = element.getTextContent();
         }
      } else {
         returnValue = null;
      }

      return returnValue;
   }

   static {
      try {
         Class var1 = XPathFactory.class;
         XPathFactory xpathFactory;
         synchronized(XPathFactory.class) {
            xpathFactory = XPathFactory.newInstance();

            assert xpathFactory != null;
         }

         XPath xpath = xpathFactory.newXPath();

         assert xpath != null;

         resourceGroupXPathExpression = xpath.compile("//*[local-name() = 'partition']/*[local-name() = 'resource-group']");
         jdbcSystemResourceOverrideXPathExpression = xpath.compile("//*[local-name() = 'partition']/*[local-name() = 'jdbc-system-resource-override']");
      } catch (XPathExpressionException var4) {
         xpathFailure = var4;
      }

   }
}
