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

public interface PartitionReducer extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PartitionReducer.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("partitionreducer36c3type");

   Properties getProperties();

   boolean isSetProperties();

   void setProperties(Properties var1);

   Properties addNewProperties();

   void unsetProperties();

   java.lang.String getRef();

   ArtifactRef xgetRef();

   void setRef(java.lang.String var1);

   void xsetRef(ArtifactRef var1);

   public static final class Factory {
      public static PartitionReducer newInstance() {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().newInstance(PartitionReducer.type, (XmlOptions)null);
      }

      public static PartitionReducer newInstance(XmlOptions options) {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().newInstance(PartitionReducer.type, options);
      }

      public static PartitionReducer parse(java.lang.String xmlAsString) throws XmlException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(xmlAsString, PartitionReducer.type, (XmlOptions)null);
      }

      public static PartitionReducer parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(xmlAsString, PartitionReducer.type, options);
      }

      public static PartitionReducer parse(File file) throws XmlException, IOException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(file, PartitionReducer.type, (XmlOptions)null);
      }

      public static PartitionReducer parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(file, PartitionReducer.type, options);
      }

      public static PartitionReducer parse(URL u) throws XmlException, IOException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(u, PartitionReducer.type, (XmlOptions)null);
      }

      public static PartitionReducer parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(u, PartitionReducer.type, options);
      }

      public static PartitionReducer parse(InputStream is) throws XmlException, IOException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(is, PartitionReducer.type, (XmlOptions)null);
      }

      public static PartitionReducer parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(is, PartitionReducer.type, options);
      }

      public static PartitionReducer parse(Reader r) throws XmlException, IOException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(r, PartitionReducer.type, (XmlOptions)null);
      }

      public static PartitionReducer parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(r, PartitionReducer.type, options);
      }

      public static PartitionReducer parse(XMLStreamReader sr) throws XmlException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(sr, PartitionReducer.type, (XmlOptions)null);
      }

      public static PartitionReducer parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(sr, PartitionReducer.type, options);
      }

      public static PartitionReducer parse(Node node) throws XmlException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(node, PartitionReducer.type, (XmlOptions)null);
      }

      public static PartitionReducer parse(Node node, XmlOptions options) throws XmlException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(node, PartitionReducer.type, options);
      }

      /** @deprecated */
      public static PartitionReducer parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(xis, PartitionReducer.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PartitionReducer parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PartitionReducer)XmlBeans.getContextTypeLoader().parse(xis, PartitionReducer.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PartitionReducer.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PartitionReducer.type, options);
      }

      private Factory() {
      }
   }
}
