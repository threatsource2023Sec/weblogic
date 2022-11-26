package com.oracle.weblogic.lifecycle.provisioning.core.handlers.configmappingxml;

import com.oracle.cie.domain.xml.configmapping.AutoUrlGenType;
import com.oracle.cie.domain.xml.configmapping.BindAttributeType;
import com.oracle.cie.domain.xml.configmapping.BindElementType;
import com.oracle.cie.domain.xml.configmapping.BindOutputType;
import com.oracle.cie.domain.xml.configmapping.BindType;
import com.oracle.cie.domain.xml.configmapping.ConfigMappingDocument;
import com.oracle.cie.domain.xml.configmapping.ConfigMappingType;
import com.oracle.cie.domain.xml.configmapping.EntryType;
import com.oracle.cie.domain.xml.configmapping.InputDataSrcType;
import com.oracle.cie.domain.xml.configmapping.PublishAttributeType;
import com.oracle.cie.domain.xml.configmapping.PublishOutputType;
import com.oracle.cie.domain.xml.configmapping.ServiceTableType;
import com.oracle.cie.domain.xml.configmapping.ServiceType;
import com.oracle.cie.domain.xml.configmapping.TargetUrlType;
import com.oracle.cie.domain.xml.configmapping.TokenBindType;
import com.oracle.cie.domain.xml.configmapping.Type;
import com.oracle.cie.domain.xml.configmapping.UrlType;
import com.oracle.cie.domain.xml.configmapping.UrlsType;
import com.oracle.cie.domain.xml.configmapping.XmlBindType;
import com.oracle.cie.domain.xml.configmapping.XmlDocumentType;
import com.oracle.cie.domain.xml.configmapping.ConfigMappingDocument.Factory;
import com.oracle.cie.servicetable.external.BindingMetadata;
import com.oracle.cie.servicetable.external.EndPoint;
import com.oracle.cie.servicetable.external.EndPointConnection;
import com.oracle.cie.servicetable.external.JaxWSPolicy;
import com.oracle.cie.servicetable.external.Policy;
import com.oracle.cie.servicetable.external.ServiceTable;
import com.oracle.cie.servicetable.external.ServiceTableFactory;
import com.oracle.cie.servicetable.external.T3Policy;
import com.oracle.cie.servicetable.external.JaxWSPolicy.JaxWSPolicyName;
import com.oracle.cie.servicetable.external.T3Policy.T3PolicyName;
import com.oracle.cie.servicetable.util.ServiceTableUtil;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.deploy.utils.ApplicationConnectionInfo;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.utils.XXEUtils;

public abstract class AbstractConfigMappingXmlHandler {
   private final String partitionName;
   private final Provider partitionFileSystemUnderConstructionProvider;
   private final String T3_PROTOCOL = "t3";
   private final String T3S_PROTOCOL = "t3s";
   private final String HTTP_PROTOCOL = "http";
   private final String HTTPS_PROTOCOL = "https";
   private final String PORT_DELIM = ":";
   private final String PROTOCOL_DELIM = "://";
   private final String CLUSTER = "cluster";
   private final String DOMAIN = "DOMAIN";
   private final String LOCAL_HOST = "localhost";
   private final String CLUSTER_T3 = "cluster:t3://";
   private final String FAIL_ON_ERROR = "fail-on-error";
   private final String POLICY_NAME = "[policy-name='";
   private final String PROPERTY_NAME = "property[name='";
   private final String CCW_PROPERTIES = "properties";
   private final String PLAIN_URL = "plain";
   private final String SSL_URL = "ssl";
   private final String DEFAULT_URL = "default";
   private final String CLUSTER_URL = "cluster";
   private final String FORWARD_SLASH = "/";
   private final String FORWARD_DOUBLE_SLASH = "//";

   protected AbstractConfigMappingXmlHandler(String partitionName, Provider partitionFileSystemUnderConstructionProvider) {
      this.partitionName = partitionName;
      this.partitionFileSystemUnderConstructionProvider = partitionFileSystemUnderConstructionProvider;
   }

   protected String getPartitionName() {
      return this.partitionName;
   }

   protected void deleteServices(Document configMappingXml) {
      Logger logger = this.getLogger();
      if (configMappingXml == null) {
         logger.log(Level.WARNING, "CCW: config-mapping in deleteServices is null");
      } else {
         ServiceTable serviceTable = null;

         try {
            ConfigMappingType configMapping = this.parseConfigMapping(configMappingXml);
            if (configMapping.getPublish() != null) {
               Collection allServices = this.getNewServices(configMapping.getPublish().getServiceArray());
               if (allServices != null && !allServices.isEmpty()) {
                  serviceTable = ServiceTableFactory.createServiceTable();
               }

               Iterator var6 = allServices.iterator();

               while(var6.hasNext()) {
                  ServiceType service = (ServiceType)var6.next();
                  String decoratedServiceId = service.getServiceId() + ApplicationUtils.getServiceIdPartitionPrefix() + this.partitionName;
                  logger.log(Level.FINER, "CCW: Deleting the Service with ID " + decoratedServiceId);
                  serviceTable.delete(new URI[]{URI.create(decoratedServiceId)});
               }
            }
         } catch (Exception var9) {
            logger.log(Level.FINER, "CCW: Exception in deleteServices - " + var9.getMessage());
         }

      }
   }

   protected void processConfigMapping(String partitionConfigDirectory, List configMappingDocuments) {
      try {
         List bindConfigMappings = new ArrayList();
         Iterator var4 = configMappingDocuments.iterator();

         while(var4.hasNext()) {
            Document configMappingXml = (Document)var4.next();
            ConfigMappingType configMapping = this.parseConfigMapping(configMappingXml);
            if (configMapping.getPublish() != null) {
               Collection newServices = this.getNewServices(configMapping.getPublish().getServiceArray());
               this.processNewServices(newServices);
               Collection existingServices = this.getExistingServices(configMapping.getPublish().getServiceArray());
               this.processExistingServices(existingServices);
            } else {
               bindConfigMappings.add(configMapping);
            }
         }

         var4 = bindConfigMappings.iterator();

         while(var4.hasNext()) {
            ConfigMappingType configMapping = (ConfigMappingType)var4.next();
            BindType bind = configMapping.getBind();
            if (bind != null) {
               this.processTokenBinds(bind.getTokenBindArray());
               this.processXmlBinds(bind.getXmlBindArray(), partitionConfigDirectory);
            }
         }

      } catch (Exception var9) {
         throw new RuntimeException(var9);
      }
   }

   private ConfigMappingType parseConfigMapping(Document document) throws Exception {
      if (document == null) {
         Logger logger = this.getLogger();
         logger.log(Level.WARNING, "CCW: The config-mapping.xml is null in parseConfigMapping.");
         return null;
      } else {
         ConfigMappingType configMapping = null;
         ConfigMappingDocument configMappingDocument = Factory.parse(document);
         configMapping = configMappingDocument.getConfigMapping();
         return configMapping;
      }
   }

   private Collection getNewServices(ServiceType[] services) {
      Collection newServices = new ArrayList();
      if (services != null && services.length != 0) {
         ServiceType[] var3 = services;
         int var4 = services.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ServiceType service = var3[var5];
            if (service.getType() == Type.NEW) {
               newServices.add(service);
            }
         }

         return newServices;
      } else {
         return newServices;
      }
   }

   private void processNewServices(Collection services) throws Exception {
      Logger logger = this.getLogger();
      logger.log(Level.FINER, "CCW: Entering processNewServices() in AbstractConfigMappingXmlHandler ...");
      Iterator var3 = services.iterator();

      while(var3.hasNext()) {
         ServiceType service = (ServiceType)var3.next();
         boolean failOnError = service.getFailOnError();
         if (!service.isSetFailOnError()) {
            failOnError = Boolean.valueOf(this.getDefaultValue(service, "fail-on-error"));
         }

         try {
            String serviceId = service.getServiceId();
            String serviceType = service.getServiceType();
            logger.log(Level.FINER, "CCW: serviceId " + serviceId + ", serviceType " + serviceType);
            if (serviceId == null) {
               throw new AssertionError("Processing config-mapping.xml failed. Service ID is null, which is not valid.");
            }

            if (serviceType == null) {
               throw new AssertionError("Processing config-mapping.xml failed. Service Type is null, which is not valid.");
            }

            EndPoint endPoint = null;
            boolean oneAttrProcessed = false;
            EndPoint[] endPoints = this.getService(serviceId + ApplicationUtils.getServiceIdPartitionPrefix() + this.partitionName);
            if (endPoints != null && endPoints.length > 0) {
               endPoint = endPoints[0];
            } else {
               logger.log(Level.FINER, "CCW: Creating end point with service id " + serviceId + ApplicationUtils.getServiceIdPartitionPrefix() + this.partitionName);
               endPoint = ServiceTableFactory.createEndPoint(new URI(serviceId + ApplicationUtils.getServiceIdPartitionPrefix() + this.partitionName), serviceType);
            }

            PublishAttributeType[] pubAttrs = service.getPublishAttributeArray();
            PublishAttributeType[] var12 = pubAttrs;
            int var13 = pubAttrs.length;

            for(int var14 = 0; var14 < var13; ++var14) {
               PublishAttributeType pubAttr = var12[var14];
               boolean reprocess = pubAttr.getReprocess();
               boolean processed = pubAttr.getProcessed();
               if (!processed || reprocess) {
                  InputDataSrcType input = pubAttr.getInputDataSrc();
                  PublishOutputType output = pubAttr.getOutputDataTarget();
                  input = this.syncInputOutput(input, output);
                  Collection valueCol = this.processInput(input);
                  if (valueCol.size() > 0) {
                     endPoint = this.buildService(endPoint, output, (String[])valueCol.toArray(new String[valueCol.size()]));
                     oneAttrProcessed = true;
                     pubAttr.setProcessed(true);
                  }
               }
            }

            if (oneAttrProcessed) {
               endPoint = ServiceTableUtil.getEndPoint(new ByteArrayInputStream(ServiceTableUtil.getEndPointXMLString(endPoint).getBytes("UTF-8")));
               if (endPoint != null) {
                  this.publishOutput(endPoint);
               }
            }
         } catch (Exception var21) {
            if (failOnError) {
               throw new Exception(var21);
            }

            logger.log(Level.FINER, "CCW: An error occurred while processing the service - " + var21.getMessage());
         }
      }

      logger.log(Level.FINER, "CCW: Exiting processNewServices() in AbstractConfigMappingXmlHandler ...");
   }

   private InputDataSrcType syncInputOutput(InputDataSrcType input, PublishOutputType output) {
      if (input != null && input.getAutoUrlGen() != null) {
         AutoUrlGenType autoUrlGen = input.getAutoUrlGen();
         if (autoUrlGen.getProtocol() == null) {
            if (output.getEntryType() == EntryType.T_3) {
               autoUrlGen.setProtocol("t3");
            } else if (output.getEntryType() == EntryType.JAX_WS) {
               autoUrlGen.setProtocol("http");
            } else {
               autoUrlGen.setProtocol("t3");
            }
         }

         input.setAutoUrlGen(autoUrlGen);
      }

      return input;
   }

   private EndPoint[] getService(String serviceId) throws Exception {
      ServiceTable servicetable = ServiceTableFactory.createServiceTable();
      BindingMetadata bdgMetadata = ServiceTableFactory.createBindingMetaData(new URI(serviceId));
      EndPoint[] eps = servicetable.lookup(bdgMetadata);
      return eps;
   }

   private void publishOutput(EndPoint endpoint) throws Exception {
      ServiceTable servicetable = ServiceTableFactory.createServiceTable();
      servicetable.publish(endpoint);
   }

   private EndPoint buildService(EndPoint endPoint, PublishOutputType outType, String[] values) throws Exception {
      Logger logger = this.getLogger();
      if (outType == null) {
         throw new AssertionError("The element publish in config-mapping.xml is null");
      } else if (endPoint == null) {
         logger.log(Level.WARNING, "CCW: EndPoint is null in buildService");
         return endPoint;
      } else {
         EndPointConnection epConnection = endPoint.getConnection();
         EntryType.Enum entry = outType.getEntryType();
         String xpath = outType.getTargetXpath();
         String targetUrl = outType.getTargetUrl() != null ? outType.getTargetUrl().toString() : null;
         logger.log(Level.FINER, "CCW: targetUrl " + targetUrl);
         if (xpath == null && targetUrl == null) {
            return endPoint;
         } else {
            String clusterUrl;
            Object policy;
            Properties prop;
            String propName;
            Policy policy;
            if (entry == EntryType.T_3_POLICY) {
               if (xpath != null && !xpath.contains("properties")) {
                  policy = this.findPolicy(endPoint, values[0]);
                  if (policy == null) {
                     T3Policy t3Policy = ServiceTableFactory.createT3Policy(T3PolicyName.valueOf(values[0]));
                     this.updatePolicies(endPoint, t3Policy);
                  }
               } else if (xpath != null) {
                  clusterUrl = xpath.substring(xpath.indexOf("[policy-name='") + "[policy-name='".length(), xpath.indexOf("']"));
                  policy = this.findPolicy(endPoint, clusterUrl);
                  if (policy == null) {
                     policy = ServiceTableFactory.createT3Policy(T3PolicyName.valueOf(clusterUrl));
                  }

                  prop = ((Policy)policy).getProperties();
                  if (prop == null) {
                     prop = new Properties();
                  }

                  propName = xpath.substring(xpath.indexOf("property[name='") + "property[name='".length(), xpath.lastIndexOf("'"));
                  prop.put(propName, values[0]);
                  ((Policy)policy).setProperties(prop);
                  this.updatePolicies(endPoint, (Policy)policy);
               }
            } else if (entry == EntryType.JAX_WS_POLICY) {
               if (xpath != null && !xpath.contains("properties")) {
                  policy = this.findPolicy(endPoint, values[0]);
                  if (policy == null) {
                     JaxWSPolicy jaxWsPolicy = ServiceTableFactory.createJaxWSPolicy(JaxWSPolicyName.valueOf(values[0]));
                     this.updatePolicies(endPoint, jaxWsPolicy);
                  }
               } else if (xpath != null) {
                  clusterUrl = xpath.substring(xpath.indexOf("[policy-name='") + "[policy-name='".length(), xpath.indexOf("']"));
                  policy = this.findPolicy(endPoint, clusterUrl);
                  if (policy == null) {
                     policy = ServiceTableFactory.createJaxWSPolicy(JaxWSPolicyName.valueOf(clusterUrl));
                  }

                  prop = ((Policy)policy).getProperties();
                  if (prop == null) {
                     prop = new Properties();
                  }

                  propName = xpath.substring(xpath.indexOf("property[name='") + "property[name='".length(), xpath.lastIndexOf("'"));
                  prop.put(propName, values[0]);
                  ((Policy)policy).setProperties(prop);
                  this.updatePolicies(endPoint, (Policy)policy);
               }
            } else {
               if (epConnection == null) {
                  epConnection = ServiceTableFactory.createConnection();
               }

               if (entry == EntryType.T_3 || entry == EntryType.JAX_WS) {
                  if ((xpath == null || xpath.contains("properties")) && targetUrl == null) {
                     Properties prop = epConnection.getProperties();
                     if (prop == null) {
                        prop = new Properties();
                     }

                     String propName = xpath.substring(xpath.indexOf("property[name='") + "property[name='".length(), xpath.lastIndexOf("'"));
                     prop.put(propName, values[0]);
                     epConnection.setProperties(prop);
                  } else if ((xpath == null || xpath.indexOf("plain") == -1) && !TargetUrlType.PLAIN_URL.toString().equals(targetUrl)) {
                     if ((xpath == null || xpath.indexOf("ssl") == -1) && !TargetUrlType.SSL_URL.toString().equals(targetUrl)) {
                        if ((xpath == null || xpath.indexOf("default") == -1) && !TargetUrlType.DEFAULT_URL.toString().equals(targetUrl)) {
                           if ((xpath == null || xpath.indexOf("cluster") == -1) && !TargetUrlType.CLUSTER.toString().equals(targetUrl)) {
                              if (targetUrl != null && TargetUrlType.ALL_URL.toString().equals(targetUrl)) {
                                 if (values.length > 0) {
                                    clusterUrl = values[0];
                                    if (clusterUrl != null && clusterUrl.startsWith("t3://") && entry == EntryType.JAX_WS) {
                                       clusterUrl = clusterUrl.replace("t3://", "http://");
                                    }

                                    epConnection.setPlainURL(clusterUrl);
                                    epConnection.setURL(clusterUrl);
                                 }

                                 if (values.length > 1) {
                                    clusterUrl = values[1];
                                    if (clusterUrl != null && !clusterUrl.startsWith("cluster")) {
                                       if (clusterUrl.startsWith("t3s://") && entry == EntryType.JAX_WS) {
                                          clusterUrl = clusterUrl.replace("t3s://", "https://");
                                       }

                                       epConnection.setSSLURL(clusterUrl);
                                    } else if (clusterUrl != null && clusterUrl.startsWith("cluster:t3://") && entry == EntryType.JAX_WS) {
                                       epConnection.setClusterURL((String)null);
                                    } else {
                                       epConnection.setClusterURL(clusterUrl);
                                    }
                                 }

                                 if (values.length > 2) {
                                    clusterUrl = values[2];
                                    if (clusterUrl != null && clusterUrl.startsWith("cluster:t3://") && entry == EntryType.JAX_WS) {
                                       epConnection.setClusterURL((String)null);
                                    } else if (clusterUrl != null && !clusterUrl.startsWith("cluster")) {
                                       epConnection.setURL(clusterUrl);
                                    } else {
                                       epConnection.setClusterURL(clusterUrl);
                                    }
                                 }
                              }
                           } else {
                              clusterUrl = values[0];
                              if (clusterUrl != null && clusterUrl.startsWith("cluster:t3://") && entry == EntryType.JAX_WS) {
                                 epConnection.setClusterURL((String)null);
                              } else {
                                 epConnection.setClusterURL(clusterUrl);
                              }
                           }
                        } else {
                           clusterUrl = values[0];
                           if (clusterUrl != null && clusterUrl.startsWith("t3://") && entry == EntryType.JAX_WS) {
                              clusterUrl = clusterUrl.replace("t3://", "http://");
                           }

                           epConnection.setURL(clusterUrl);
                        }
                     } else {
                        clusterUrl = values[0];
                        if (clusterUrl != null && clusterUrl.startsWith("t3s://") && entry == EntryType.JAX_WS) {
                           clusterUrl = clusterUrl.replace("t3s://", "https://");
                        }

                        epConnection.setSSLURL(clusterUrl);
                     }
                  } else {
                     clusterUrl = values[0];
                     if (clusterUrl != null && clusterUrl.startsWith("t3://") && entry == EntryType.JAX_WS) {
                        clusterUrl = clusterUrl.replace("t3://", "http://");
                     }

                     epConnection.setPlainURL(clusterUrl);
                     if (epConnection.getURL() == null) {
                        epConnection.setURL(clusterUrl);
                     }
                  }

                  endPoint.setConnection(epConnection);
               }
            }

            if (endPoint != null) {
               EndPointConnection var18 = endPoint.getConnection();
            }

            return endPoint;
         }
      }
   }

   private Policy findPolicy(EndPoint endPoint, String policyName) {
      Policy[] policies = endPoint.getPolicies();
      if (policies != null) {
         Policy[] var4 = policies;
         int var5 = policies.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Policy policy = var4[var6];
            if (policy.getPolicyName() != null && policy.getPolicyName().equals(policyName)) {
               return policy;
            }
         }
      }

      return null;
   }

   private void updatePolicies(EndPoint endPoint, Policy policy) {
      Policy[] policies = endPoint.getPolicies();
      if (policies != null && policies.length > 0) {
         List policiesList = new ArrayList();
         policiesList.add(policy);
         Policy[] var5 = policies;
         int var6 = policies.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Policy p = var5[var7];
            if (!policy.getPolicyName().equals(p.getPolicyName())) {
               policiesList.add(p);
            }
         }

         endPoint.setPolicies((Policy[])policiesList.toArray(new Policy[policiesList.size()]));
      } else {
         endPoint.setPolicies(new Policy[]{policy});
      }

   }

   private String getDefaultValue(XmlObject object, String name) {
      String value = null;
      SchemaProperty[] props = object.schemaType().getProperties();
      SchemaProperty[] var5 = props;
      int var6 = props.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         SchemaProperty prop = var5[var7];
         if (prop.getName().getLocalPart().equals(name)) {
            value = prop.getDefaultValue() != null ? prop.getDefaultValue().getStringValue() : null;
         }
      }

      return value;
   }

   private Collection processInput(InputDataSrcType input) throws Exception {
      ApplicationConnectionInfo connectionInfo = null;
      Collection result = new ArrayList();
      String retString = null;
      if (input == null) {
         return Collections.emptyList();
      } else {
         Logger logger = this.getLogger();
         if (input.getAutoUrlGen() != null) {
            if (input.getAutoUrlGen().getContextPath() != null) {
               connectionInfo = ApplicationUtils.getWebServiceConnectionInfo(input.getAutoUrlGen().getAppSvcName(), input.getAutoUrlGen().getContextPath(), this.partitionName);
            } else {
               connectionInfo = ApplicationUtils.getApplicationConnectionInfo(input.getAutoUrlGen().getAppSvcName(), this.partitionName);
            }

            if (connectionInfo != null) {
               logger.log(Level.FINER, "CCW: Default Connection " + connectionInfo.getDefaultConnection());
               logger.log(Level.FINER, "CCW: Cluster Connection " + connectionInfo.getClusterConnection());
               logger.log(Level.FINER, "CCW: Plain Connection " + connectionInfo.getPlainConnection());
               logger.log(Level.FINER, "CCW: SSL Connection " + connectionInfo.getSSLConnection());
               result.add(connectionInfo.getDefaultConnection());
               result.add(connectionInfo.getClusterConnection());
               result.add(connectionInfo.getPlainConnection());
               result.add(connectionInfo.getSSLConnection());
            }
         } else {
            String serviceId;
            if (input.getXmlDocument() != null) {
               XmlDocumentType doc = input.getXmlDocument();
               serviceId = this.getInputFileName(doc);
               if (doc.getXpath() != null) {
                  retString = this.deriveValueFromXPath(serviceId, doc.getXpath());
               } else if (doc.getUrls() != null) {
                  retString = this.composeUrl(serviceId, doc.getUrls());
               }

               if (retString != null) {
                  result.add(retString);
               }
            } else if (input.isSetFixedValue()) {
               String fixedValue = input.getFixedValue();
               if (fixedValue == null) {
                  return result;
               }

               if (retString != null && !retString.equals("")) {
                  result.add(retString);
               }
            } else if (input.getServiceTable() != null) {
               ServiceTableType bindST = input.getServiceTable();
               serviceId = bindST.getServiceId();
               String decoratedServiceId = bindST.getServiceId();
               if (this.partitionName != null && !"DOMAIN".equals(this.partitionName) && !serviceId.endsWith(ApplicationUtils.getServiceIdPartitionPrefix() + this.partitionName)) {
                  decoratedServiceId = serviceId + ApplicationUtils.getServiceIdPartitionPrefix() + this.partitionName;
               }

               EndPoint[] endPoints = this.getService(decoratedServiceId);
               if (endPoints != null && endPoints.length > 0) {
                  String xml = ServiceTableUtil.getEndPointXMLString(endPoints[0]);
                  if (xml != null) {
                     retString = this.deriveValueFromXPath(bindST.getEntryType().toString(), new ByteArrayInputStream(xml.getBytes("UTF-8")), bindST.getXpath());
                     if ((bindST.getEntryType() == EntryType.T_3 || bindST.getEntryType() == EntryType.JAX_WS) && bindST.getBindElement() != null) {
                        retString = this.decomposeUrl(retString, bindST.getBindElement());
                     }
                  }
               }

               if (retString != null) {
                  result.add(retString);
               }
            }
         }

         return result;
      }
   }

   private String deriveValueFromXPath(String rootElement, InputStream is, String xpath) throws IOException, ParserConfigurationException, SAXException {
      Object result = null;
      if (is != null) {
         Document doc = this.parseInputStream(is);
         result = this.evaluateXPath(doc, rootElement, xpath);
      }

      return result != null ? result.toString() : "";
   }

   private String decomposeUrl(String result, BindElementType.Enum bindElement) {
      if (result != null && bindElement != null) {
         String[] urls = result.split(",");
         if (urls != null && urls.length > 0) {
            String finalUrl = urls[0];
            if (urls[0].startsWith("cluster://")) {
               finalUrl = urls[0].substring(urls[0].indexOf("cluster://") + "cluster://".length());
            }

            String[] urlParts = finalUrl.split(":");
            if (bindElement == BindElementType.PROTOCOL) {
               if (urlParts.length >= 1) {
                  result = urlParts[0];
               }
            } else if (bindElement == BindElementType.HOST) {
               if (urlParts.length >= 2) {
                  result = urlParts[1].substring(urlParts[1].indexOf("//") + 2, urlParts[1].length());
               }
            } else if (bindElement == BindElementType.PORT) {
               if (urlParts.length >= 3) {
                  result = urlParts[2];
                  if (result.contains("/")) {
                     result = result.substring(0, result.indexOf("/"));
                  }
               }
            } else if (bindElement == BindElementType.URL_NO_PROTOCOL) {
               result = result.substring(result.indexOf("//") + 2, result.length());
            }
         }

         this.getLogger().log(Level.FINER, "CCW: URL in decomposeUrl : " + result);
         return result;
      } else {
         return result;
      }
   }

   private String getXmlOutputFileName(XmlBindType bind) {
      String fileName = null;
      if (bind.getStdTargetFileLoc() != null) {
         fileName = bind.getStdTargetFileLoc().toString();
      } else {
         fileName = bind.getCustomTargetFileLoc();
      }

      return fileName;
   }

   private void processXmlBinds(XmlBindType[] xmlBinds, String partitionConfigDirectory) throws Exception {
      if (xmlBinds != null && xmlBinds.length > 0) {
         XmlBindType[] var3 = xmlBinds;
         int var4 = xmlBinds.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            XmlBindType xmlBind = var3[var5];
            boolean failOnError = xmlBind.getFailOnError();
            if (!xmlBind.isSetFailOnError()) {
               failOnError = Boolean.valueOf(this.getDefaultValue(xmlBind, "fail-on-error"));
            }

            try {
               Map keyValueMap = new HashMap();
               String outFileName = this.getXmlOutputFileName(xmlBind);
               if (outFileName == null) {
                  throw new AssertionError("The output file to write the binding information is null");
               }

               BindAttributeType[] bindAttrs = xmlBind.getBindAttributeArray();
               BindAttributeType[] var11 = bindAttrs;
               int var12 = bindAttrs.length;

               for(int var13 = 0; var13 < var12; ++var13) {
                  BindAttributeType bindAttr = var11[var13];
                  boolean reprocess = bindAttr.getReprocess();
                  boolean processed = bindAttr.getProcessed();
                  if (!processed || reprocess) {
                     InputDataSrcType input = bindAttr.getInputDataSrc();
                     BindOutputType output = bindAttr.getOutputDataTarget();
                     Collection valueCol = this.processInput(input);
                     keyValueMap = this.buildXmlBindKeyValueMap((Map)keyValueMap, output, (String[])valueCol.toArray(new String[valueCol.size()]));
                     bindAttr.setProcessed(true);
                  }
               }

               this.processXmlBindOutput((Map)keyValueMap, outFileName, partitionConfigDirectory);
            } catch (Exception var20) {
               if (failOnError) {
                  throw new Exception(var20.getMessage());
               }

               this.getLogger().log(Level.FINER, "CCW: An error occurred while processing XML binding " + var20.getMessage());
            }
         }
      }

   }

   private void processXmlBindOutput(Map keyValueMap, String outputFileName, String partitionConfigDirectory) throws Exception {
      if (outputFileName != null && keyValueMap != null && keyValueMap.size() != 0) {
         if (outputFileName.charAt(0) == '/') {
            outputFileName = outputFileName.substring(1);
         }

         String strippedOutputFile = outputFileName.replaceAll("^config/", "");
         String pathToCCWConfig = partitionConfigDirectory + File.separator + strippedOutputFile;
         if (this.partitionFileSystemUnderConstructionProvider != null) {
            pathToCCWConfig = ((Path)this.partitionFileSystemUnderConstructionProvider.get()).toString() + File.separator + strippedOutputFile;
         }

         this.getLogger().log(Level.FINER, "CCW: CCW Configuration File : " + pathToCCWConfig);
         File ccwConfigFile = new File(pathToCCWConfig);
         this.processFileForXPathSubs(ccwConfigFile, keyValueMap);
      }
   }

   private void processFileForXPathSubs(File file, Map keyValueMap) throws IOException, TransformerException, ParserConfigurationException, SAXException {
      Logger logger = this.getLogger();
      if (file == null) {
         logger.log(Level.WARNING, "CCW: The file in processFileForXPathSubs is null.");
      } else if (!file.exists()) {
         logger.log(Level.WARNING, "CCW: The file " + file.getAbsolutePath() + " does not exist.");
      } else if (!file.isFile()) {
         throw new AssertionError("The file " + file.getAbsolutePath() + " is not a file.");
      } else if (!file.canRead()) {
         throw new AssertionError("The file " + file.getAbsolutePath() + " cannot be read.");
      } else if (!file.canWrite()) {
         throw new AssertionError("The file " + file.getAbsolutePath() + " cannot be written");
      } else {
         this.substituteValueToXPath((String)null, file.getAbsolutePath(), keyValueMap);
      }
   }

   private void substituteValueToXPath(String rootElement, String file, Map xpathValueMap) throws IOException, TransformerException, ParserConfigurationException, SAXException {
      if (xpathValueMap == null) {
         this.getLogger().log(Level.WARNING, "CCW: xpathValueMap is null.");
      } else {
         Document doc = this.parseFile(file);
         Iterator var5 = xpathValueMap.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry set = (Map.Entry)var5.next();
            String xpath = (String)set.getKey();
            String value = (String)set.getValue();
            this.substituteXPath(doc, rootElement, xpath, value);
         }

         this.transformFile(doc, file);
      }
   }

   private Map buildXmlBindKeyValueMap(Map keyValueMap, BindOutputType outType, String[] values) {
      if (outType == null) {
         throw new AssertionError("The output type for the bind is null.");
      } else if (outType.getTargetKeyArray() != null && outType.getTargetKeyArray().length > 0) {
         throw new AssertionError("The target key for the output type is null.");
      } else {
         String[] keys = outType.getTargetXpathArray();
         String[] var5 = keys;
         int var6 = keys.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String key = var5[var7];
            if (values[0] != null && values[0].length() > 0) {
               keyValueMap.put(key, values[0]);
            }
         }

         return keyValueMap;
      }
   }

   private Object evaluateXPath(Document doc, String rootElement, String xpathExp) {
      if (xpathExp != null) {
         try {
            String e = xpathExp.trim();
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile(e);
            Object result = null;
            NodeList nodes;
            if (rootElement != null) {
               nodes = doc.getElementsByTagName(rootElement);
               result = expr.evaluate(nodes.item(0), XPathConstants.NODESET);
            } else {
               result = expr.evaluate(doc, XPathConstants.NODESET);
            }

            nodes = (NodeList)result;
            String[] array = new String[nodes.getLength()];

            for(int i = 0; i < nodes.getLength(); ++i) {
               Node node = nodes.item(i).getFirstChild();
               if (node != null && node.getNodeType() == 3) {
                  array[i] = node.getNodeValue();
               } else {
                  array[i] = nodes.item(i).getNodeValue();
               }
            }

            if (array.length == 0) {
               return null;
            }

            return array.length == 1 ? array[0] : array;
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      }

      return null;
   }

   private void substituteXPath(Document doc, String rootElement, String xpathExp, String value) {
      if (xpathExp != null) {
         try {
            String e = xpathExp.trim();
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile(e);
            Object result = null;
            NodeList nodes;
            if (rootElement != null) {
               nodes = doc.getElementsByTagName(rootElement);
               result = expr.evaluate(nodes.item(0), XPathConstants.NODESET);
            } else {
               result = expr.evaluate(doc, XPathConstants.NODESET);
            }

            nodes = (NodeList)result;

            for(int i = 0; i < nodes.getLength(); ++i) {
               Node node = nodes.item(i);
               if (node.getNodeType() == 3) {
                  node.setNodeValue(value);
               } else if (node.getFirstChild() != null) {
                  if (node.getFirstChild().getNodeType() == 3) {
                     node.getFirstChild().setNodeValue(value);
                  }
               } else {
                  Text txtNode = doc.createTextNode(value);
                  node.appendChild(txtNode);
               }
            }
         } catch (Exception var13) {
            var13.printStackTrace();
         }
      }

   }

   private void transformFile(Document doc, String xmlFileName) throws TransformerException {
      try {
         TransformerFactory te = TransformerFactory.newInstance();
         Transformer transformer = te.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(xmlFileName);
         transformer.setOutputProperty("indent", "yes");
         DocumentType doctype = doc.getDoctype();
         if (doctype != null) {
            if (doctype.getPublicId() != null) {
               transformer.setOutputProperty("doctype-public", doctype.getPublicId());
            }

            if (doctype.getSystemId() != null) {
               transformer.setOutputProperty("doctype-system", doctype.getSystemId());
            }
         }

         transformer.transform(source, result);
      } catch (TransformerException var8) {
         throw new TransformerException("Unable to transform xml file " + xmlFileName, var8);
      }
   }

   private Document parseInputStream(InputStream is) throws IOException, SAXException, ParserConfigurationException {
      if (is == null) {
         throw new IllegalArgumentException("An inputStream must be provided!");
      } else {
         Document doc = this.createParser().parse(is);
         return doc;
      }
   }

   private Document parseFile(String xmlFileName) throws IOException, SAXException, ParserConfigurationException {
      if (xmlFileName == null) {
         throw new IllegalArgumentException("A filename must be provided!");
      } else {
         File xmlFile = new File(xmlFileName);
         if (!xmlFile.exists()) {
            throw new IllegalArgumentException("The specified file '" + xmlFileName + "' does not exist.");
         } else if (!xmlFile.isFile()) {
            throw new IllegalArgumentException("The specified file '" + xmlFileName + "' is not a file.");
         } else {
            return this.createParser().parse(new File(xmlFileName));
         }
      }
   }

   private DocumentBuilder createParser() throws ParserConfigurationException {
      DocumentBuilderFactory x = XXEUtils.createDocumentBuilderFactoryInstance();
      DocumentBuilder parser = x.newDocumentBuilder();
      parser.setEntityResolver(new EntityResolver() {
         public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(new StringReader(""));
         }
      });
      return parser;
   }

   private void processTokenBinds(TokenBindType[] tokenBinds) throws Exception {
      if (tokenBinds != null && tokenBinds.length > 0) {
         TokenBindType[] var2 = tokenBinds;
         int var3 = tokenBinds.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TokenBindType tokenBind = var2[var4];
            boolean failOnError = tokenBind.getFailOnError();
            if (!tokenBind.isSetFailOnError()) {
               failOnError = Boolean.valueOf(this.getDefaultValue(tokenBind, "fail-on-error"));
            }

            try {
               String outFileName = tokenBind.getTargetFileLoc();
               if (outFileName == null) {
                  throw new AssertionError("The output file to write the binding information is null");
               }

               Map keyValueMap = new HashMap();
               BindAttributeType[] bindAttrs = tokenBind.getBindAttributeArray();
               BindAttributeType[] var10 = bindAttrs;
               int var11 = bindAttrs.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  BindAttributeType bindAttr = var10[var12];
                  boolean reprocess = bindAttr.getReprocess();
                  boolean processed = bindAttr.getProcessed();
                  if (!processed || reprocess) {
                     InputDataSrcType input = bindAttr.getInputDataSrc();
                     BindOutputType output = bindAttr.getOutputDataTarget();
                     Collection valueCol = this.processInput(input);
                     keyValueMap = this.buildTokenBindKeyValueMap((Map)keyValueMap, output, (String[])valueCol.toArray(new String[valueCol.size()]));
                     bindAttr.setProcessed(true);
                  }
               }

               this.processTokenBindOutput((Map)keyValueMap, outFileName);
            } catch (Exception var19) {
               if (failOnError) {
                  throw new Exception(var19.getMessage());
               }

               var19.printStackTrace();
            }
         }
      }

   }

   private Map buildTokenBindKeyValueMap(Map keyValueMap, BindOutputType outType, String[] values) throws Exception {
      if (outType == null) {
         throw new AssertionError("BindOutput is null");
      } else if (outType.getTargetXpathArray() != null && outType.getTargetXpathArray().length > 0) {
         throw new AssertionError("Target XPath array is empty");
      } else {
         String[] keys = outType.getTargetKeyArray();
         String[] var5 = keys;
         int var6 = keys.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String key = var5[var7];
            if (values[0] != null && values[0].length() > 0) {
               keyValueMap.put(key, values[0]);
            }
         }

         return keyValueMap;
      }
   }

   private void processTokenBindOutput(Map keyValueMap, String outputFileName) throws Exception {
      if (outputFileName != null && keyValueMap != null && keyValueMap.size() != 0) {
         this.processFileForTokenSubs(outputFileName, keyValueMap);
      }
   }

   private void processFileForTokenSubs(String fileName, Map keyValueMap) throws IOException {
      Logger logger = this.getLogger();
      if (fileName != null && !fileName.isEmpty()) {
         File file = new File(fileName);
         if (!file.exists()) {
            logger.log(Level.WARNING, "The file " + file.getAbsolutePath() + " does not exist in this session.");
         } else if (!file.isFile()) {
            throw new IOException("Invalid Output file: " + fileName + " file is a directory");
         } else if (!file.canRead()) {
            throw new IOException("Invalid Output file: " + fileName + " file is not readable");
         } else if (!file.canWrite()) {
            throw new IOException("Invalid Output file: " + fileName + " file is not writeable");
         } else {
            this.substituteTokens(file, keyValueMap);
         }
      } else {
         logger.log(Level.WARNING, "Invalid Output file: name is empty");
      }
   }

   private void substituteTokens(File file, Map keyValueMap) throws IOException {
      File origFile = file;
      File destFile = file;
      List buffer = new ArrayList();
      boolean dirtyFlag = !file.equals(file);

      try {
         BufferedReader reader;
         if (origFile.getName().toLowerCase().endsWith(".xml")) {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(origFile), "UTF-8"));
         } else {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(origFile)));
         }

         String inputLine;
         while((inputLine = reader.readLine()) != null) {
            buffer.add(inputLine);
         }

         reader.close();

         Iterator keyIter;
         String line;
         for(int parent = 0; parent < buffer.size(); ++parent) {
            inputLine = (String)buffer.get(parent);
            String out = inputLine;

            for(keyIter = keyValueMap.keySet().iterator(); keyIter.hasNext(); inputLine = substituteString(inputLine, line, (String)keyValueMap.get(line))) {
               line = (String)keyIter.next();
            }

            if (!inputLine.equals(out)) {
               dirtyFlag = true;
               buffer.set(parent, inputLine);
            }
         }

         if (dirtyFlag) {
            File destFileDir = destFile.getParentFile();
            if (!destFileDir.exists()) {
               destFileDir.mkdirs();
            }

            PrintWriter writer;
            if (origFile.getName().toLowerCase().endsWith(".xml")) {
               writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(destFile)), "UTF-8"));
            } else {
               writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(destFile))));
            }

            keyIter = buffer.iterator();

            while(keyIter.hasNext()) {
               line = (String)keyIter.next();
               writer.println(line);
            }

            writer.close();
         }

      } catch (IOException var13) {
         throw new IOException("Error performing global string substitution on file " + file + ".  " + var13);
      }
   }

   private static String substituteString(String inputString, String origString, String newString) {
      String prevString = "";
      int beginIdx = 0;

      String tempString2;
      int endIdx;
      for(tempString2 = inputString; (endIdx = inputString.indexOf(origString, beginIdx)) >= 0; tempString2 = prevString.concat(tempString2)) {
         String tempString = inputString.substring(beginIdx, endIdx);
         tempString = tempString.concat(newString);
         prevString = prevString + tempString;
         endIdx += origString.length();
         beginIdx = endIdx;
         tempString2 = inputString.substring(endIdx, inputString.length());
      }

      return tempString2;
   }

   private String deriveValueFromXPath(String filename, String xpt) throws Exception {
      Object result = this.processFileForXPathEval(filename, xpt);
      return result != null ? result.toString() : "";
   }

   private String processFileForXPathEval(String fileName, String xpath) throws IOException, ParserConfigurationException, SAXException {
      Logger logger = this.getLogger();
      if (fileName == null) {
         logger.log(Level.WARNING, "CCW: The file should not be null");
         return null;
      } else {
         File file = new File(fileName);
         if (!file.exists()) {
            logger.log(Level.WARNING, "The file " + file.getAbsolutePath() + " does not exist in this session.");
            return null;
         } else if (!file.isFile()) {
            throw new IOException("Invalid file:" + fileName + " is a directory");
         } else if (!file.canRead()) {
            throw new IOException("Invalid file:" + fileName + " is not readable");
         } else {
            return this.deriveValueFromXPath((String)null, new FileInputStream(file), xpath);
         }
      }
   }

   private String getInputFileName(XmlDocumentType doc) {
      String fileName = null;
      if (doc.getStdSrcFileLoc() != null) {
         fileName = doc.getStdSrcFileLoc().toString();
      } else {
         fileName = doc.getCustomSrcFileLoc();
      }

      return fileName;
   }

   private String composeUrl(String fileName, UrlsType urls) throws Exception {
      if (urls == null) {
         return null;
      } else {
         StringBuffer sb = new StringBuffer();
         String protocol = "t3";
         if (urls.getProtocol() != null) {
            protocol = urls.getProtocol();
         }

         sb.append(protocol);
         sb.append("://");
         UrlType[] urlArr = urls.getUrlArray();

         for(int i = 0; i < urlArr.length; ++i) {
            if (urlArr[i].getHostXpath() != null) {
               String host = this.deriveValueFromXPath(fileName, urlArr[i].getHostXpath());
               if (host == null) {
                  host = "localhost";
               }

               sb.append(host);
            }

            sb.append(":");
            if (urlArr[i].getPortXpath() != null) {
               String port = this.deriveValueFromXPath(fileName, urlArr[i].getPortXpath());
               sb.append(port);
            }

            if (i < urlArr.length - 1) {
               sb.append(",");
            }
         }

         return sb.toString();
      }
   }

   private Collection getExistingServices(ServiceType[] services) {
      Collection existingServices = new ArrayList();
      if (services != null && services.length != 0) {
         ServiceType[] var3 = services;
         int var4 = services.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ServiceType service = var3[var5];
            if (service.getType() == Type.EXISTING) {
               existingServices.add(service);
            }
         }

         return existingServices;
      } else {
         return existingServices;
      }
   }

   private void processExistingServices(Collection services) throws Exception {
      Iterator var2 = services.iterator();

      while(var2.hasNext()) {
         ServiceType service = (ServiceType)var2.next();
         boolean failOnError = service.getFailOnError();
         if (!service.isSetFailOnError()) {
            failOnError = Boolean.valueOf(this.getDefaultValue(service, "fail-on-error"));
         }

         try {
            EndPoint endPoint = null;
            String serviceId = service.getServiceId();
            EndPoint[] endPoints = this.getService(serviceId);
            if (endPoints != null && endPoints.length > 0) {
               endPoint = endPoints[0];
            } else {
               this.getLogger().log(Level.FINER, "The service " + serviceId + " not found");
            }

            boolean oneAttrProcessed = false;
            PublishAttributeType[] pubAttrs = service.getPublishAttributeArray();
            PublishAttributeType[] var10 = pubAttrs;
            int var11 = pubAttrs.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               PublishAttributeType pubAttr = var10[var12];
               boolean reprocess = pubAttr.getReprocess();
               boolean processed = pubAttr.getProcessed();
               if (!processed || reprocess) {
                  InputDataSrcType input = pubAttr.getInputDataSrc();
                  PublishOutputType output = pubAttr.getOutputDataTarget();
                  input = this.syncInputOutput(input, output);
                  Collection valueCol = this.processInput(input);
                  if (valueCol.size() > 0) {
                     endPoint = this.buildService(endPoint, output, (String[])valueCol.toArray(new String[valueCol.size()]));
                     oneAttrProcessed = true;
                     pubAttr.setProcessed(true);
                  }
               }
            }

            if (oneAttrProcessed) {
               endPoint = ServiceTableUtil.getEndPoint(new ByteArrayInputStream(ServiceTableUtil.getEndPointXMLString(endPoint).getBytes("UTF-8")));
               this.publishOutput(endPoint);
            }
         } catch (Exception var19) {
            if (failOnError) {
               throw new Exception(var19.getMessage());
            }

            var19.printStackTrace();
         }
      }

   }

   protected Logger getLogger() {
      return Logger.getLogger(this.getClass().getName());
   }
}
