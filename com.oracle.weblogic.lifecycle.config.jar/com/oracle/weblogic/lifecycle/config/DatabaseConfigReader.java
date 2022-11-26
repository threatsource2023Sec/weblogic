package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.LifecycleException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;
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
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weblogic.utils.XXEUtils;

public class DatabaseConfigReader {
   private static final String[] rootChildren = new String[]{"runtimes", "environments", "tenants/resources"};

   public Document load(Connection connection) throws SQLException, XPathExpressionException, LifecycleException, ParserConfigurationException {
      Objects.requireNonNull(connection);
      DocumentBuilderFactory builderFactory = XXEUtils.createDocumentBuilderFactoryInstance();
      DocumentBuilder builder = builderFactory.newDocumentBuilder();
      Document document = builder.newDocument();
      this.process(connection, document);
      return document;
   }

   private void process(Connection connection, Document document) throws SQLException, XPathExpressionException, LifecycleException {
      Objects.requireNonNull(connection);
      Objects.requireNonNull(document);
      Element rootElement = document.createElement("lifecycle-config");
      document.appendChild(rootElement);
      String[] var4 = rootChildren;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String rootChild = var4[var6];
         String[] nodes = rootChild.split("/");
         Node parentNode = rootElement;
         String[] var10 = nodes;
         int var11 = nodes.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            String node = var10[var12];
            Element child = document.createElement(node);
            parentNode.appendChild(child);
            parentNode = child;
         }
      }

      if (LifecycleConfigUtil.isDebugEnabled()) {
         LifecycleConfigUtil.debug("Reading Lifecycle config data tables from database");
      }

      this.readTable(connection, document, "LIFECYCLE_RUNTIME", "runtime", "runtimes", (String)null);
      this.readTable(connection, document, "LIFECYCLE_RUNTIME_PROPERTY", "property", "runtimes/runtime[@name=$name]");
      this.readTable(connection, document, "LIFECYCLE_PARTITION", "partition", "runtimes/runtime[@name=$runtime-name]");
      this.readTable(connection, document, "LIFECYCLE_PARTITION_PROPERTY", "property", "runtimes/runtime/partition[@id=$id]");
      this.readTable(connection, document, "LIFECYCLE_ENVIRONMENT", "environment", "environments", "associations");
      this.readTable(connection, document, "LIFECYCLE_PARTITION_REF", "partition-ref", "environments/environment[@name=$environment-name]");
      this.readTable(connection, document, "LIFECYCLE_ASSOCIATION", "association", "environments/environment[@name=$environment-name]/associations");
      this.readTable(connection, document, "LIFECYCLE_TENANT", "tenant", "tenants");
      this.readTable(connection, document, "LIFECYCLE_SERVICE", "service", "tenants/tenant[@id=$tenant-id]", "resources");
      this.readTable(connection, document, "LIFECYCLE_PDB", "pdb", "tenants/tenant/service[@id=$service-id]");
      this.readTable(connection, document, "LIFECYCLE_TENANT_RESOURCE", "resource", "tenants/resources");
      this.readTable(connection, document, "LIFECYCLE_SERVICE_RESOURCE", "resource", "tenants/tenant[@id=$tenant-id]/service[@id=$service-id]/resources");
      this.readTable(connection, document, "LIFECYCLE_TRESOURCE_PROPERTY", "property", "tenants/resources/resource[@name=$name]");
      this.readTable(connection, document, "LIFECYCLE_SRESOURCE_PROPERTY", "property", "tenants/tenant[@id=$tenant-id]/service[@id=$service-id]/resources/resource[@name=$resource-name]");
   }

   private void readTable(Connection connection, Document document, String table, String tag, String xpathExpression) throws SQLException, XPathExpressionException, LifecycleException {
      this.readTable(connection, document, table, tag, xpathExpression, (String)null);
   }

   private void fixNames(Element elem) {
      Objects.requireNonNull(elem);
      if (elem.getTagName().equalsIgnoreCase("property")) {
         String name = elem.getAttribute("propertyname");
         elem.removeAttribute("propertyname");
         elem.setAttribute("name", name);
         String value = elem.getAttribute("propertyvalue");
         elem.removeAttribute("propertyvalue");
         elem.setAttribute("value", value);
      }

   }

   private void readTable(Connection connection, Document document, String table, String tag, String xpathExpression, String additionalChildTag) throws SQLException, XPathExpressionException, LifecycleException {
      Objects.requireNonNull(connection);
      Objects.requireNonNull(document);
      Objects.requireNonNull(table);
      Objects.requireNonNull(tag);
      Objects.requireNonNull(xpathExpression);
      if (LifecycleConfigUtil.isDebugEnabled()) {
         LifecycleConfigUtil.debug("Reading Lifecycle config table : " + table);
      }

      PreparedStatement ps = connection.prepareStatement("SELECT * from " + table);
      Throwable var8 = null;

      try {
         ResultSet rs = ps.executeQuery();
         Throwable var10 = null;

         try {
            ResultSetMetaData meta = rs.getMetaData();

            while(rs.next()) {
               Element elem = document.createElement(tag);
               int columnCount = rs.getMetaData().getColumnCount();
               StringBuilder row = new StringBuilder();

               String replacedXPath;
               for(int j = 0; j < columnCount; ++j) {
                  String columnName = meta.getColumnName(j + 1).toLowerCase();
                  replacedXPath = rs.getString(j + 1);
                  if (replacedXPath != null) {
                     String attrName = columnName.replaceAll("_", "-");
                     elem.setAttribute(attrName, replacedXPath);
                     row.append(replacedXPath).append(" ");
                  }
               }

               XPath xpath = XPathFactory.newInstance().newXPath();
               int start = 0;
               replacedXPath = xpathExpression;

               while(true) {
                  start = replacedXPath.indexOf("$", start);
                  if (start < 0) {
                     if (LifecycleConfigUtil.isDebugEnabled()) {
                        LifecycleConfigUtil.debug(replacedXPath);
                     }

                     NodeList nodeList = (NodeList)xpath.evaluate("/lifecycle-config/" + replacedXPath, document, XPathConstants.NODE);
                     if (nodeList == null) {
                        throw new LifecycleException("Processing table : " + table + " Path : " + xpathExpression + " not found. Cannot add " + row);
                     }

                     this.fixNames(elem);
                     ((Element)nodeList).appendChild(elem);
                     if (additionalChildTag != null) {
                        Element childElem = document.createElement(additionalChildTag);
                        elem.appendChild(childElem);
                     }
                     break;
                  }

                  int end = replacedXPath.indexOf(";", start);
                  if (end < 0) {
                     end = replacedXPath.indexOf("]", start);
                  }

                  if (end < 0) {
                     throw new LifecycleException("Invalid xpath expression : " + xpathExpression);
                  }

                  String attr = replacedXPath.substring(start + 1, end);
                  String val = elem.getAttribute(attr);
                  if (val != null) {
                     replacedXPath = replacedXPath.replace("$" + attr, "'" + val + "'");
                     elem.removeAttribute(attr);
                  }
               }
            }
         } catch (Throwable var42) {
            var10 = var42;
            throw var42;
         } finally {
            if (rs != null) {
               if (var10 != null) {
                  try {
                     rs.close();
                  } catch (Throwable var41) {
                     var10.addSuppressed(var41);
                  }
               } else {
                  rs.close();
               }
            }

         }
      } catch (Throwable var44) {
         var8 = var44;
         throw var44;
      } finally {
         if (ps != null) {
            if (var8 != null) {
               try {
                  ps.close();
               } catch (Throwable var40) {
                  var8.addSuppressed(var40);
               }
            } else {
               ps.close();
            }
         }

      }

   }

   public static void printDocument(Document document, OutputStream out) throws IOException, TransformerException {
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty("omit-xml-declaration", "no");
      transformer.setOutputProperty("method", "xml");
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("encoding", "UTF-8");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      transformer.transform(new DOMSource(document), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
   }
}
