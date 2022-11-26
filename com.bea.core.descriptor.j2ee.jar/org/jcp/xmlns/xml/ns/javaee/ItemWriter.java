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

public interface ItemWriter extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ItemWriter.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("itemwriter899ftype");

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
      public static ItemWriter newInstance() {
         return (ItemWriter)XmlBeans.getContextTypeLoader().newInstance(ItemWriter.type, (XmlOptions)null);
      }

      public static ItemWriter newInstance(XmlOptions options) {
         return (ItemWriter)XmlBeans.getContextTypeLoader().newInstance(ItemWriter.type, options);
      }

      public static ItemWriter parse(java.lang.String xmlAsString) throws XmlException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(xmlAsString, ItemWriter.type, (XmlOptions)null);
      }

      public static ItemWriter parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(xmlAsString, ItemWriter.type, options);
      }

      public static ItemWriter parse(File file) throws XmlException, IOException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(file, ItemWriter.type, (XmlOptions)null);
      }

      public static ItemWriter parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(file, ItemWriter.type, options);
      }

      public static ItemWriter parse(URL u) throws XmlException, IOException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(u, ItemWriter.type, (XmlOptions)null);
      }

      public static ItemWriter parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(u, ItemWriter.type, options);
      }

      public static ItemWriter parse(InputStream is) throws XmlException, IOException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(is, ItemWriter.type, (XmlOptions)null);
      }

      public static ItemWriter parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(is, ItemWriter.type, options);
      }

      public static ItemWriter parse(Reader r) throws XmlException, IOException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(r, ItemWriter.type, (XmlOptions)null);
      }

      public static ItemWriter parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(r, ItemWriter.type, options);
      }

      public static ItemWriter parse(XMLStreamReader sr) throws XmlException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(sr, ItemWriter.type, (XmlOptions)null);
      }

      public static ItemWriter parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(sr, ItemWriter.type, options);
      }

      public static ItemWriter parse(Node node) throws XmlException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(node, ItemWriter.type, (XmlOptions)null);
      }

      public static ItemWriter parse(Node node, XmlOptions options) throws XmlException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(node, ItemWriter.type, options);
      }

      /** @deprecated */
      public static ItemWriter parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(xis, ItemWriter.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ItemWriter parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ItemWriter)XmlBeans.getContextTypeLoader().parse(xis, ItemWriter.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ItemWriter.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ItemWriter.type, options);
      }

      private Factory() {
      }
   }
}
