package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface CustomSeqType extends SequenceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomSeqType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customseqtypec917type");

   String getClassname();

   XmlString xgetClassname();

   boolean isNilClassname();

   boolean isSetClassname();

   void setClassname(String var1);

   void xsetClassname(XmlString var1);

   void setNilClassname();

   void unsetClassname();

   PropertiesType getProperties();

   boolean isNilProperties();

   boolean isSetProperties();

   void setProperties(PropertiesType var1);

   PropertiesType addNewProperties();

   void setNilProperties();

   void unsetProperties();

   public static final class Factory {
      public static CustomSeqType newInstance() {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().newInstance(CustomSeqType.type, (XmlOptions)null);
      }

      public static CustomSeqType newInstance(XmlOptions options) {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().newInstance(CustomSeqType.type, options);
      }

      public static CustomSeqType parse(String xmlAsString) throws XmlException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomSeqType.type, (XmlOptions)null);
      }

      public static CustomSeqType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomSeqType.type, options);
      }

      public static CustomSeqType parse(File file) throws XmlException, IOException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(file, CustomSeqType.type, (XmlOptions)null);
      }

      public static CustomSeqType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(file, CustomSeqType.type, options);
      }

      public static CustomSeqType parse(URL u) throws XmlException, IOException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(u, CustomSeqType.type, (XmlOptions)null);
      }

      public static CustomSeqType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(u, CustomSeqType.type, options);
      }

      public static CustomSeqType parse(InputStream is) throws XmlException, IOException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(is, CustomSeqType.type, (XmlOptions)null);
      }

      public static CustomSeqType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(is, CustomSeqType.type, options);
      }

      public static CustomSeqType parse(Reader r) throws XmlException, IOException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(r, CustomSeqType.type, (XmlOptions)null);
      }

      public static CustomSeqType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(r, CustomSeqType.type, options);
      }

      public static CustomSeqType parse(XMLStreamReader sr) throws XmlException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(sr, CustomSeqType.type, (XmlOptions)null);
      }

      public static CustomSeqType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(sr, CustomSeqType.type, options);
      }

      public static CustomSeqType parse(Node node) throws XmlException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(node, CustomSeqType.type, (XmlOptions)null);
      }

      public static CustomSeqType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(node, CustomSeqType.type, options);
      }

      /** @deprecated */
      public static CustomSeqType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(xis, CustomSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomSeqType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomSeqType)XmlBeans.getContextTypeLoader().parse(xis, CustomSeqType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomSeqType.type, options);
      }

      private Factory() {
      }
   }
}
