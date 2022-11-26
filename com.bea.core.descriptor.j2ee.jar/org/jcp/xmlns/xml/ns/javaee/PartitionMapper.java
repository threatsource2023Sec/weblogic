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

public interface PartitionMapper extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PartitionMapper.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("partitionmapper114ctype");

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
      public static PartitionMapper newInstance() {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().newInstance(PartitionMapper.type, (XmlOptions)null);
      }

      public static PartitionMapper newInstance(XmlOptions options) {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().newInstance(PartitionMapper.type, options);
      }

      public static PartitionMapper parse(java.lang.String xmlAsString) throws XmlException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(xmlAsString, PartitionMapper.type, (XmlOptions)null);
      }

      public static PartitionMapper parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(xmlAsString, PartitionMapper.type, options);
      }

      public static PartitionMapper parse(File file) throws XmlException, IOException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(file, PartitionMapper.type, (XmlOptions)null);
      }

      public static PartitionMapper parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(file, PartitionMapper.type, options);
      }

      public static PartitionMapper parse(URL u) throws XmlException, IOException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(u, PartitionMapper.type, (XmlOptions)null);
      }

      public static PartitionMapper parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(u, PartitionMapper.type, options);
      }

      public static PartitionMapper parse(InputStream is) throws XmlException, IOException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(is, PartitionMapper.type, (XmlOptions)null);
      }

      public static PartitionMapper parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(is, PartitionMapper.type, options);
      }

      public static PartitionMapper parse(Reader r) throws XmlException, IOException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(r, PartitionMapper.type, (XmlOptions)null);
      }

      public static PartitionMapper parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(r, PartitionMapper.type, options);
      }

      public static PartitionMapper parse(XMLStreamReader sr) throws XmlException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(sr, PartitionMapper.type, (XmlOptions)null);
      }

      public static PartitionMapper parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(sr, PartitionMapper.type, options);
      }

      public static PartitionMapper parse(Node node) throws XmlException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(node, PartitionMapper.type, (XmlOptions)null);
      }

      public static PartitionMapper parse(Node node, XmlOptions options) throws XmlException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(node, PartitionMapper.type, options);
      }

      /** @deprecated */
      public static PartitionMapper parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(xis, PartitionMapper.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PartitionMapper parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PartitionMapper)XmlBeans.getContextTypeLoader().parse(xis, PartitionMapper.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PartitionMapper.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PartitionMapper.type, options);
      }

      private Factory() {
      }
   }
}
