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

public interface ProcessorTypeType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProcessorTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("processortypetypef861type");

   String getType();

   XmlString xgetType();

   void setType(String var1);

   void xsetType(XmlString var1);

   String getFactory();

   XmlString xgetFactory();

   void setFactory(String var1);

   void xsetFactory(XmlString var1);

   public static final class Factory {
      public static ProcessorTypeType newInstance() {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().newInstance(ProcessorTypeType.type, (XmlOptions)null);
      }

      public static ProcessorTypeType newInstance(XmlOptions options) {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().newInstance(ProcessorTypeType.type, options);
      }

      public static ProcessorTypeType parse(String xmlAsString) throws XmlException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProcessorTypeType.type, (XmlOptions)null);
      }

      public static ProcessorTypeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProcessorTypeType.type, options);
      }

      public static ProcessorTypeType parse(File file) throws XmlException, IOException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(file, ProcessorTypeType.type, (XmlOptions)null);
      }

      public static ProcessorTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(file, ProcessorTypeType.type, options);
      }

      public static ProcessorTypeType parse(URL u) throws XmlException, IOException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(u, ProcessorTypeType.type, (XmlOptions)null);
      }

      public static ProcessorTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(u, ProcessorTypeType.type, options);
      }

      public static ProcessorTypeType parse(InputStream is) throws XmlException, IOException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(is, ProcessorTypeType.type, (XmlOptions)null);
      }

      public static ProcessorTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(is, ProcessorTypeType.type, options);
      }

      public static ProcessorTypeType parse(Reader r) throws XmlException, IOException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(r, ProcessorTypeType.type, (XmlOptions)null);
      }

      public static ProcessorTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(r, ProcessorTypeType.type, options);
      }

      public static ProcessorTypeType parse(XMLStreamReader sr) throws XmlException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(sr, ProcessorTypeType.type, (XmlOptions)null);
      }

      public static ProcessorTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(sr, ProcessorTypeType.type, options);
      }

      public static ProcessorTypeType parse(Node node) throws XmlException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(node, ProcessorTypeType.type, (XmlOptions)null);
      }

      public static ProcessorTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(node, ProcessorTypeType.type, options);
      }

      /** @deprecated */
      public static ProcessorTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(xis, ProcessorTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProcessorTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProcessorTypeType)XmlBeans.getContextTypeLoader().parse(xis, ProcessorTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProcessorTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProcessorTypeType.type, options);
      }

      private Factory() {
      }
   }
}
