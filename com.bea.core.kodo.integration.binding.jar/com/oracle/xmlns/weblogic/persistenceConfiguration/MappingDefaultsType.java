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

public interface MappingDefaultsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MappingDefaultsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("mappingdefaultstypec5a9type");

   public static final class Factory {
      public static MappingDefaultsType newInstance() {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().newInstance(MappingDefaultsType.type, (XmlOptions)null);
      }

      public static MappingDefaultsType newInstance(XmlOptions options) {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().newInstance(MappingDefaultsType.type, options);
      }

      public static MappingDefaultsType parse(String xmlAsString) throws XmlException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingDefaultsType.type, (XmlOptions)null);
      }

      public static MappingDefaultsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingDefaultsType.type, options);
      }

      public static MappingDefaultsType parse(File file) throws XmlException, IOException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(file, MappingDefaultsType.type, (XmlOptions)null);
      }

      public static MappingDefaultsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(file, MappingDefaultsType.type, options);
      }

      public static MappingDefaultsType parse(URL u) throws XmlException, IOException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(u, MappingDefaultsType.type, (XmlOptions)null);
      }

      public static MappingDefaultsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(u, MappingDefaultsType.type, options);
      }

      public static MappingDefaultsType parse(InputStream is) throws XmlException, IOException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(is, MappingDefaultsType.type, (XmlOptions)null);
      }

      public static MappingDefaultsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(is, MappingDefaultsType.type, options);
      }

      public static MappingDefaultsType parse(Reader r) throws XmlException, IOException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(r, MappingDefaultsType.type, (XmlOptions)null);
      }

      public static MappingDefaultsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(r, MappingDefaultsType.type, options);
      }

      public static MappingDefaultsType parse(XMLStreamReader sr) throws XmlException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(sr, MappingDefaultsType.type, (XmlOptions)null);
      }

      public static MappingDefaultsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(sr, MappingDefaultsType.type, options);
      }

      public static MappingDefaultsType parse(Node node) throws XmlException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(node, MappingDefaultsType.type, (XmlOptions)null);
      }

      public static MappingDefaultsType parse(Node node, XmlOptions options) throws XmlException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(node, MappingDefaultsType.type, options);
      }

      /** @deprecated */
      public static MappingDefaultsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xis, MappingDefaultsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MappingDefaultsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xis, MappingDefaultsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingDefaultsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingDefaultsType.type, options);
      }

      private Factory() {
      }
   }
}
