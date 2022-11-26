package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface SequenceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SequenceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("sequencetypeef59type");

   public static final class Factory {
      public static SequenceType newInstance() {
         return (SequenceType)XmlBeans.getContextTypeLoader().newInstance(SequenceType.type, (XmlOptions)null);
      }

      public static SequenceType newInstance(XmlOptions options) {
         return (SequenceType)XmlBeans.getContextTypeLoader().newInstance(SequenceType.type, options);
      }

      public static SequenceType parse(String xmlAsString) throws XmlException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SequenceType.type, (XmlOptions)null);
      }

      public static SequenceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SequenceType.type, options);
      }

      public static SequenceType parse(File file) throws XmlException, IOException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(file, SequenceType.type, (XmlOptions)null);
      }

      public static SequenceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(file, SequenceType.type, options);
      }

      public static SequenceType parse(URL u) throws XmlException, IOException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(u, SequenceType.type, (XmlOptions)null);
      }

      public static SequenceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(u, SequenceType.type, options);
      }

      public static SequenceType parse(InputStream is) throws XmlException, IOException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(is, SequenceType.type, (XmlOptions)null);
      }

      public static SequenceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(is, SequenceType.type, options);
      }

      public static SequenceType parse(Reader r) throws XmlException, IOException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(r, SequenceType.type, (XmlOptions)null);
      }

      public static SequenceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(r, SequenceType.type, options);
      }

      public static SequenceType parse(XMLStreamReader sr) throws XmlException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(sr, SequenceType.type, (XmlOptions)null);
      }

      public static SequenceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(sr, SequenceType.type, options);
      }

      public static SequenceType parse(Node node) throws XmlException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(node, SequenceType.type, (XmlOptions)null);
      }

      public static SequenceType parse(Node node, XmlOptions options) throws XmlException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(node, SequenceType.type, options);
      }

      /** @deprecated */
      public static SequenceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(xis, SequenceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SequenceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SequenceType)XmlBeans.getContextTypeLoader().parse(xis, SequenceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SequenceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SequenceType.type, options);
      }

      private Factory() {
      }
   }
}
