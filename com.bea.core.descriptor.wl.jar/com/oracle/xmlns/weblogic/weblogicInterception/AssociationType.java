package com.oracle.xmlns.weblogic.weblogicInterception;

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

public interface AssociationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AssociationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("associationtype57ectype");

   InterceptionPointType getInterceptionPoint();

   void setInterceptionPoint(InterceptionPointType var1);

   InterceptionPointType addNewInterceptionPoint();

   ProcessorAssociationType getProcessor();

   void setProcessor(ProcessorAssociationType var1);

   ProcessorAssociationType addNewProcessor();

   public static final class Factory {
      public static AssociationType newInstance() {
         return (AssociationType)XmlBeans.getContextTypeLoader().newInstance(AssociationType.type, (XmlOptions)null);
      }

      public static AssociationType newInstance(XmlOptions options) {
         return (AssociationType)XmlBeans.getContextTypeLoader().newInstance(AssociationType.type, options);
      }

      public static AssociationType parse(String xmlAsString) throws XmlException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AssociationType.type, (XmlOptions)null);
      }

      public static AssociationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AssociationType.type, options);
      }

      public static AssociationType parse(File file) throws XmlException, IOException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(file, AssociationType.type, (XmlOptions)null);
      }

      public static AssociationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(file, AssociationType.type, options);
      }

      public static AssociationType parse(URL u) throws XmlException, IOException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(u, AssociationType.type, (XmlOptions)null);
      }

      public static AssociationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(u, AssociationType.type, options);
      }

      public static AssociationType parse(InputStream is) throws XmlException, IOException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(is, AssociationType.type, (XmlOptions)null);
      }

      public static AssociationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(is, AssociationType.type, options);
      }

      public static AssociationType parse(Reader r) throws XmlException, IOException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(r, AssociationType.type, (XmlOptions)null);
      }

      public static AssociationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(r, AssociationType.type, options);
      }

      public static AssociationType parse(XMLStreamReader sr) throws XmlException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(sr, AssociationType.type, (XmlOptions)null);
      }

      public static AssociationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(sr, AssociationType.type, options);
      }

      public static AssociationType parse(Node node) throws XmlException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(node, AssociationType.type, (XmlOptions)null);
      }

      public static AssociationType parse(Node node, XmlOptions options) throws XmlException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(node, AssociationType.type, options);
      }

      /** @deprecated */
      public static AssociationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(xis, AssociationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AssociationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AssociationType)XmlBeans.getContextTypeLoader().parse(xis, AssociationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AssociationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AssociationType.type, options);
      }

      private Factory() {
      }
   }
}
