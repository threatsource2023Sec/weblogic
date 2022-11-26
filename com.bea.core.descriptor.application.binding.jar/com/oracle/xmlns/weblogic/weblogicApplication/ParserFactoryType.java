package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface ParserFactoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ParserFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("parserfactorytypebff7type");

   String getSaxparserFactory();

   XmlString xgetSaxparserFactory();

   boolean isSetSaxparserFactory();

   void setSaxparserFactory(String var1);

   void xsetSaxparserFactory(XmlString var1);

   void unsetSaxparserFactory();

   String getDocumentBuilderFactory();

   XmlString xgetDocumentBuilderFactory();

   boolean isSetDocumentBuilderFactory();

   void setDocumentBuilderFactory(String var1);

   void xsetDocumentBuilderFactory(XmlString var1);

   void unsetDocumentBuilderFactory();

   String getTransformerFactory();

   XmlString xgetTransformerFactory();

   boolean isSetTransformerFactory();

   void setTransformerFactory(String var1);

   void xsetTransformerFactory(XmlString var1);

   void unsetTransformerFactory();

   String getXpathFactory();

   XmlString xgetXpathFactory();

   boolean isSetXpathFactory();

   void setXpathFactory(String var1);

   void xsetXpathFactory(XmlString var1);

   void unsetXpathFactory();

   String getSchemaFactory();

   XmlString xgetSchemaFactory();

   boolean isSetSchemaFactory();

   void setSchemaFactory(String var1);

   void xsetSchemaFactory(XmlString var1);

   void unsetSchemaFactory();

   String getXmlInputFactory();

   XmlString xgetXmlInputFactory();

   boolean isSetXmlInputFactory();

   void setXmlInputFactory(String var1);

   void xsetXmlInputFactory(XmlString var1);

   void unsetXmlInputFactory();

   String getXmlOutputFactory();

   XmlString xgetXmlOutputFactory();

   boolean isSetXmlOutputFactory();

   void setXmlOutputFactory(String var1);

   void xsetXmlOutputFactory(XmlString var1);

   void unsetXmlOutputFactory();

   String getXmlEventFactory();

   XmlString xgetXmlEventFactory();

   boolean isSetXmlEventFactory();

   void setXmlEventFactory(String var1);

   void xsetXmlEventFactory(XmlString var1);

   void unsetXmlEventFactory();

   public static final class Factory {
      public static ParserFactoryType newInstance() {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().newInstance(ParserFactoryType.type, (XmlOptions)null);
      }

      public static ParserFactoryType newInstance(XmlOptions options) {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().newInstance(ParserFactoryType.type, options);
      }

      public static ParserFactoryType parse(String xmlAsString) throws XmlException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParserFactoryType.type, (XmlOptions)null);
      }

      public static ParserFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParserFactoryType.type, options);
      }

      public static ParserFactoryType parse(File file) throws XmlException, IOException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(file, ParserFactoryType.type, (XmlOptions)null);
      }

      public static ParserFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(file, ParserFactoryType.type, options);
      }

      public static ParserFactoryType parse(URL u) throws XmlException, IOException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(u, ParserFactoryType.type, (XmlOptions)null);
      }

      public static ParserFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(u, ParserFactoryType.type, options);
      }

      public static ParserFactoryType parse(InputStream is) throws XmlException, IOException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(is, ParserFactoryType.type, (XmlOptions)null);
      }

      public static ParserFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(is, ParserFactoryType.type, options);
      }

      public static ParserFactoryType parse(Reader r) throws XmlException, IOException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(r, ParserFactoryType.type, (XmlOptions)null);
      }

      public static ParserFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(r, ParserFactoryType.type, options);
      }

      public static ParserFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ParserFactoryType.type, (XmlOptions)null);
      }

      public static ParserFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ParserFactoryType.type, options);
      }

      public static ParserFactoryType parse(Node node) throws XmlException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(node, ParserFactoryType.type, (XmlOptions)null);
      }

      public static ParserFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(node, ParserFactoryType.type, options);
      }

      /** @deprecated */
      public static ParserFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ParserFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ParserFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ParserFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ParserFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParserFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParserFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
