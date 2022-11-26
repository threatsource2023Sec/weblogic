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

public interface ProcessorAssociationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProcessorAssociationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("processorassociationtype39ectype");

   String getType();

   XmlString xgetType();

   void setType(String var1);

   void xsetType(XmlString var1);

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   public static final class Factory {
      public static ProcessorAssociationType newInstance() {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().newInstance(ProcessorAssociationType.type, (XmlOptions)null);
      }

      public static ProcessorAssociationType newInstance(XmlOptions options) {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().newInstance(ProcessorAssociationType.type, options);
      }

      public static ProcessorAssociationType parse(String xmlAsString) throws XmlException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProcessorAssociationType.type, (XmlOptions)null);
      }

      public static ProcessorAssociationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProcessorAssociationType.type, options);
      }

      public static ProcessorAssociationType parse(File file) throws XmlException, IOException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(file, ProcessorAssociationType.type, (XmlOptions)null);
      }

      public static ProcessorAssociationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(file, ProcessorAssociationType.type, options);
      }

      public static ProcessorAssociationType parse(URL u) throws XmlException, IOException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(u, ProcessorAssociationType.type, (XmlOptions)null);
      }

      public static ProcessorAssociationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(u, ProcessorAssociationType.type, options);
      }

      public static ProcessorAssociationType parse(InputStream is) throws XmlException, IOException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(is, ProcessorAssociationType.type, (XmlOptions)null);
      }

      public static ProcessorAssociationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(is, ProcessorAssociationType.type, options);
      }

      public static ProcessorAssociationType parse(Reader r) throws XmlException, IOException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(r, ProcessorAssociationType.type, (XmlOptions)null);
      }

      public static ProcessorAssociationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(r, ProcessorAssociationType.type, options);
      }

      public static ProcessorAssociationType parse(XMLStreamReader sr) throws XmlException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(sr, ProcessorAssociationType.type, (XmlOptions)null);
      }

      public static ProcessorAssociationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(sr, ProcessorAssociationType.type, options);
      }

      public static ProcessorAssociationType parse(Node node) throws XmlException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(node, ProcessorAssociationType.type, (XmlOptions)null);
      }

      public static ProcessorAssociationType parse(Node node, XmlOptions options) throws XmlException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(node, ProcessorAssociationType.type, options);
      }

      /** @deprecated */
      public static ProcessorAssociationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(xis, ProcessorAssociationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProcessorAssociationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProcessorAssociationType)XmlBeans.getContextTypeLoader().parse(xis, ProcessorAssociationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProcessorAssociationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProcessorAssociationType.type, options);
      }

      private Factory() {
      }
   }
}
