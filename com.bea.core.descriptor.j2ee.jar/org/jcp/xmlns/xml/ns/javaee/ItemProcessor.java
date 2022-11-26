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

public interface ItemProcessor extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ItemProcessor.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("itemprocessor7a18type");

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
      public static ItemProcessor newInstance() {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().newInstance(ItemProcessor.type, (XmlOptions)null);
      }

      public static ItemProcessor newInstance(XmlOptions options) {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().newInstance(ItemProcessor.type, options);
      }

      public static ItemProcessor parse(java.lang.String xmlAsString) throws XmlException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(xmlAsString, ItemProcessor.type, (XmlOptions)null);
      }

      public static ItemProcessor parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(xmlAsString, ItemProcessor.type, options);
      }

      public static ItemProcessor parse(File file) throws XmlException, IOException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(file, ItemProcessor.type, (XmlOptions)null);
      }

      public static ItemProcessor parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(file, ItemProcessor.type, options);
      }

      public static ItemProcessor parse(URL u) throws XmlException, IOException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(u, ItemProcessor.type, (XmlOptions)null);
      }

      public static ItemProcessor parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(u, ItemProcessor.type, options);
      }

      public static ItemProcessor parse(InputStream is) throws XmlException, IOException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(is, ItemProcessor.type, (XmlOptions)null);
      }

      public static ItemProcessor parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(is, ItemProcessor.type, options);
      }

      public static ItemProcessor parse(Reader r) throws XmlException, IOException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(r, ItemProcessor.type, (XmlOptions)null);
      }

      public static ItemProcessor parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(r, ItemProcessor.type, options);
      }

      public static ItemProcessor parse(XMLStreamReader sr) throws XmlException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(sr, ItemProcessor.type, (XmlOptions)null);
      }

      public static ItemProcessor parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(sr, ItemProcessor.type, options);
      }

      public static ItemProcessor parse(Node node) throws XmlException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(node, ItemProcessor.type, (XmlOptions)null);
      }

      public static ItemProcessor parse(Node node, XmlOptions options) throws XmlException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(node, ItemProcessor.type, options);
      }

      /** @deprecated */
      public static ItemProcessor parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(xis, ItemProcessor.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ItemProcessor parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ItemProcessor)XmlBeans.getContextTypeLoader().parse(xis, ItemProcessor.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ItemProcessor.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ItemProcessor.type, options);
      }

      private Factory() {
      }
   }
}
