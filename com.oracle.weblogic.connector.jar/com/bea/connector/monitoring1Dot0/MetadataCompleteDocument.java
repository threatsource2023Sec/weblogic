package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface MetadataCompleteDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MetadataCompleteDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("metadatacomplete7f09doctype");

   boolean getMetadataComplete();

   XmlBoolean xgetMetadataComplete();

   void setMetadataComplete(boolean var1);

   void xsetMetadataComplete(XmlBoolean var1);

   public static final class Factory {
      public static MetadataCompleteDocument newInstance() {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().newInstance(MetadataCompleteDocument.type, (XmlOptions)null);
      }

      public static MetadataCompleteDocument newInstance(XmlOptions options) {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().newInstance(MetadataCompleteDocument.type, options);
      }

      public static MetadataCompleteDocument parse(String xmlAsString) throws XmlException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MetadataCompleteDocument.type, (XmlOptions)null);
      }

      public static MetadataCompleteDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MetadataCompleteDocument.type, options);
      }

      public static MetadataCompleteDocument parse(File file) throws XmlException, IOException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(file, MetadataCompleteDocument.type, (XmlOptions)null);
      }

      public static MetadataCompleteDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(file, MetadataCompleteDocument.type, options);
      }

      public static MetadataCompleteDocument parse(URL u) throws XmlException, IOException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(u, MetadataCompleteDocument.type, (XmlOptions)null);
      }

      public static MetadataCompleteDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(u, MetadataCompleteDocument.type, options);
      }

      public static MetadataCompleteDocument parse(InputStream is) throws XmlException, IOException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(is, MetadataCompleteDocument.type, (XmlOptions)null);
      }

      public static MetadataCompleteDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(is, MetadataCompleteDocument.type, options);
      }

      public static MetadataCompleteDocument parse(Reader r) throws XmlException, IOException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(r, MetadataCompleteDocument.type, (XmlOptions)null);
      }

      public static MetadataCompleteDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(r, MetadataCompleteDocument.type, options);
      }

      public static MetadataCompleteDocument parse(XMLStreamReader sr) throws XmlException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(sr, MetadataCompleteDocument.type, (XmlOptions)null);
      }

      public static MetadataCompleteDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(sr, MetadataCompleteDocument.type, options);
      }

      public static MetadataCompleteDocument parse(Node node) throws XmlException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(node, MetadataCompleteDocument.type, (XmlOptions)null);
      }

      public static MetadataCompleteDocument parse(Node node, XmlOptions options) throws XmlException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(node, MetadataCompleteDocument.type, options);
      }

      /** @deprecated */
      public static MetadataCompleteDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(xis, MetadataCompleteDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MetadataCompleteDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MetadataCompleteDocument)XmlBeans.getContextTypeLoader().parse(xis, MetadataCompleteDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MetadataCompleteDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MetadataCompleteDocument.type, options);
      }

      private Factory() {
      }
   }
}
