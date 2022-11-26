package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNCName;
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

public interface TopLevelComplexType extends ComplexType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TopLevelComplexType.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("toplevelcomplextypee58atype");

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   public static final class Factory {
      public static TopLevelComplexType newInstance() {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().newInstance(TopLevelComplexType.type, (XmlOptions)null);
      }

      public static TopLevelComplexType newInstance(XmlOptions options) {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().newInstance(TopLevelComplexType.type, options);
      }

      public static TopLevelComplexType parse(String xmlAsString) throws XmlException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, TopLevelComplexType.type, (XmlOptions)null);
      }

      public static TopLevelComplexType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TopLevelComplexType.type, options);
      }

      public static TopLevelComplexType parse(File file) throws XmlException, IOException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse((File)file, TopLevelComplexType.type, (XmlOptions)null);
      }

      public static TopLevelComplexType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse(file, TopLevelComplexType.type, options);
      }

      public static TopLevelComplexType parse(URL u) throws XmlException, IOException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse((URL)u, TopLevelComplexType.type, (XmlOptions)null);
      }

      public static TopLevelComplexType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse(u, TopLevelComplexType.type, options);
      }

      public static TopLevelComplexType parse(InputStream is) throws XmlException, IOException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse((InputStream)is, TopLevelComplexType.type, (XmlOptions)null);
      }

      public static TopLevelComplexType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse(is, TopLevelComplexType.type, options);
      }

      public static TopLevelComplexType parse(Reader r) throws XmlException, IOException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse((Reader)r, TopLevelComplexType.type, (XmlOptions)null);
      }

      public static TopLevelComplexType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse(r, TopLevelComplexType.type, options);
      }

      public static TopLevelComplexType parse(XMLStreamReader sr) throws XmlException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, TopLevelComplexType.type, (XmlOptions)null);
      }

      public static TopLevelComplexType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse(sr, TopLevelComplexType.type, options);
      }

      public static TopLevelComplexType parse(Node node) throws XmlException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse((Node)node, TopLevelComplexType.type, (XmlOptions)null);
      }

      public static TopLevelComplexType parse(Node node, XmlOptions options) throws XmlException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse(node, TopLevelComplexType.type, options);
      }

      /** @deprecated */
      public static TopLevelComplexType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, TopLevelComplexType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TopLevelComplexType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TopLevelComplexType)XmlBeans.getContextTypeLoader().parse(xis, TopLevelComplexType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopLevelComplexType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopLevelComplexType.type, options);
      }

      private Factory() {
      }
   }
}
