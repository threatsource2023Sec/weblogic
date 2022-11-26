package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PartitionPlan extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PartitionPlan.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("partitionplan6f24type");

   Properties[] getPropertiesArray();

   Properties getPropertiesArray(int var1);

   int sizeOfPropertiesArray();

   void setPropertiesArray(Properties[] var1);

   void setPropertiesArray(int var1, Properties var2);

   Properties insertNewProperties(int var1);

   Properties addNewProperties();

   void removeProperties(int var1);

   java.lang.String getPartitions();

   XmlString xgetPartitions();

   boolean isSetPartitions();

   void setPartitions(java.lang.String var1);

   void xsetPartitions(XmlString var1);

   void unsetPartitions();

   java.lang.String getThreads();

   XmlString xgetThreads();

   boolean isSetThreads();

   void setThreads(java.lang.String var1);

   void xsetThreads(XmlString var1);

   void unsetThreads();

   public static final class Factory {
      public static PartitionPlan newInstance() {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().newInstance(PartitionPlan.type, (XmlOptions)null);
      }

      public static PartitionPlan newInstance(XmlOptions options) {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().newInstance(PartitionPlan.type, options);
      }

      public static PartitionPlan parse(java.lang.String xmlAsString) throws XmlException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(xmlAsString, PartitionPlan.type, (XmlOptions)null);
      }

      public static PartitionPlan parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(xmlAsString, PartitionPlan.type, options);
      }

      public static PartitionPlan parse(File file) throws XmlException, IOException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(file, PartitionPlan.type, (XmlOptions)null);
      }

      public static PartitionPlan parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(file, PartitionPlan.type, options);
      }

      public static PartitionPlan parse(URL u) throws XmlException, IOException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(u, PartitionPlan.type, (XmlOptions)null);
      }

      public static PartitionPlan parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(u, PartitionPlan.type, options);
      }

      public static PartitionPlan parse(InputStream is) throws XmlException, IOException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(is, PartitionPlan.type, (XmlOptions)null);
      }

      public static PartitionPlan parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(is, PartitionPlan.type, options);
      }

      public static PartitionPlan parse(Reader r) throws XmlException, IOException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(r, PartitionPlan.type, (XmlOptions)null);
      }

      public static PartitionPlan parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(r, PartitionPlan.type, options);
      }

      public static PartitionPlan parse(XMLStreamReader sr) throws XmlException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(sr, PartitionPlan.type, (XmlOptions)null);
      }

      public static PartitionPlan parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(sr, PartitionPlan.type, options);
      }

      public static PartitionPlan parse(Node node) throws XmlException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(node, PartitionPlan.type, (XmlOptions)null);
      }

      public static PartitionPlan parse(Node node, XmlOptions options) throws XmlException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(node, PartitionPlan.type, options);
      }

      /** @deprecated */
      public static PartitionPlan parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(xis, PartitionPlan.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PartitionPlan parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PartitionPlan)XmlBeans.getContextTypeLoader().parse(xis, PartitionPlan.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PartitionPlan.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PartitionPlan.type, options);
      }

      private Factory() {
      }
   }
}
