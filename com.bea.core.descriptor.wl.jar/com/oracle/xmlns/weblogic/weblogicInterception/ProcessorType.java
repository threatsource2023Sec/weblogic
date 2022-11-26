package com.oracle.xmlns.weblogic.weblogicInterception;

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

public interface ProcessorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProcessorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("processortype3f7btype");

   String getType();

   XmlString xgetType();

   void setType(String var1);

   void xsetType(XmlString var1);

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getMetadata();

   XmlString xgetMetadata();

   boolean isSetMetadata();

   void setMetadata(String var1);

   void xsetMetadata(XmlString var1);

   void unsetMetadata();

   public static final class Factory {
      public static ProcessorType newInstance() {
         return (ProcessorType)XmlBeans.getContextTypeLoader().newInstance(ProcessorType.type, (XmlOptions)null);
      }

      public static ProcessorType newInstance(XmlOptions options) {
         return (ProcessorType)XmlBeans.getContextTypeLoader().newInstance(ProcessorType.type, options);
      }

      public static ProcessorType parse(String xmlAsString) throws XmlException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProcessorType.type, (XmlOptions)null);
      }

      public static ProcessorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProcessorType.type, options);
      }

      public static ProcessorType parse(File file) throws XmlException, IOException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(file, ProcessorType.type, (XmlOptions)null);
      }

      public static ProcessorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(file, ProcessorType.type, options);
      }

      public static ProcessorType parse(URL u) throws XmlException, IOException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(u, ProcessorType.type, (XmlOptions)null);
      }

      public static ProcessorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(u, ProcessorType.type, options);
      }

      public static ProcessorType parse(InputStream is) throws XmlException, IOException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(is, ProcessorType.type, (XmlOptions)null);
      }

      public static ProcessorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(is, ProcessorType.type, options);
      }

      public static ProcessorType parse(Reader r) throws XmlException, IOException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(r, ProcessorType.type, (XmlOptions)null);
      }

      public static ProcessorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(r, ProcessorType.type, options);
      }

      public static ProcessorType parse(XMLStreamReader sr) throws XmlException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(sr, ProcessorType.type, (XmlOptions)null);
      }

      public static ProcessorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(sr, ProcessorType.type, options);
      }

      public static ProcessorType parse(Node node) throws XmlException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(node, ProcessorType.type, (XmlOptions)null);
      }

      public static ProcessorType parse(Node node, XmlOptions options) throws XmlException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(node, ProcessorType.type, options);
      }

      /** @deprecated */
      public static ProcessorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(xis, ProcessorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProcessorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProcessorType)XmlBeans.getContextTypeLoader().parse(xis, ProcessorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProcessorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProcessorType.type, options);
      }

      private Factory() {
      }
   }
}
