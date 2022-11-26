package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface Partition extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Partition.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("partitionc28dtype");

   PartitionMapper getMapper();

   boolean isSetMapper();

   void setMapper(PartitionMapper var1);

   PartitionMapper addNewMapper();

   void unsetMapper();

   PartitionPlan getPlan();

   boolean isSetPlan();

   void setPlan(PartitionPlan var1);

   PartitionPlan addNewPlan();

   void unsetPlan();

   Collector getCollector();

   boolean isSetCollector();

   void setCollector(Collector var1);

   Collector addNewCollector();

   void unsetCollector();

   Analyzer getAnalyzer();

   boolean isSetAnalyzer();

   void setAnalyzer(Analyzer var1);

   Analyzer addNewAnalyzer();

   void unsetAnalyzer();

   PartitionReducer getReducer();

   boolean isSetReducer();

   void setReducer(PartitionReducer var1);

   PartitionReducer addNewReducer();

   void unsetReducer();

   public static final class Factory {
      public static Partition newInstance() {
         return (Partition)XmlBeans.getContextTypeLoader().newInstance(Partition.type, (XmlOptions)null);
      }

      public static Partition newInstance(XmlOptions options) {
         return (Partition)XmlBeans.getContextTypeLoader().newInstance(Partition.type, options);
      }

      public static Partition parse(java.lang.String xmlAsString) throws XmlException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(xmlAsString, Partition.type, (XmlOptions)null);
      }

      public static Partition parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(xmlAsString, Partition.type, options);
      }

      public static Partition parse(File file) throws XmlException, IOException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(file, Partition.type, (XmlOptions)null);
      }

      public static Partition parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(file, Partition.type, options);
      }

      public static Partition parse(URL u) throws XmlException, IOException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(u, Partition.type, (XmlOptions)null);
      }

      public static Partition parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(u, Partition.type, options);
      }

      public static Partition parse(InputStream is) throws XmlException, IOException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(is, Partition.type, (XmlOptions)null);
      }

      public static Partition parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(is, Partition.type, options);
      }

      public static Partition parse(Reader r) throws XmlException, IOException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(r, Partition.type, (XmlOptions)null);
      }

      public static Partition parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(r, Partition.type, options);
      }

      public static Partition parse(XMLStreamReader sr) throws XmlException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(sr, Partition.type, (XmlOptions)null);
      }

      public static Partition parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(sr, Partition.type, options);
      }

      public static Partition parse(Node node) throws XmlException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(node, Partition.type, (XmlOptions)null);
      }

      public static Partition parse(Node node, XmlOptions options) throws XmlException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(node, Partition.type, options);
      }

      /** @deprecated */
      public static Partition parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(xis, Partition.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Partition parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Partition)XmlBeans.getContextTypeLoader().parse(xis, Partition.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Partition.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Partition.type, options);
      }

      private Factory() {
      }
   }
}
