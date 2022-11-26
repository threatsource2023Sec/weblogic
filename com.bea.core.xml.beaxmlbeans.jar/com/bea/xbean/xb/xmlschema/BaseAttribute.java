package com.bea.xbean.xb.xmlschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnyURI;
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

public interface BaseAttribute extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BaseAttribute.class.getClassLoader(), "schemacom_bea_xml.system.sXMLLANG").resolveHandle("basece23attrtypetype");

   String getBase();

   XmlAnyURI xgetBase();

   boolean isSetBase();

   void setBase(String var1);

   void xsetBase(XmlAnyURI var1);

   void unsetBase();

   public static final class Factory {
      public static BaseAttribute newInstance() {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().newInstance(BaseAttribute.type, (XmlOptions)null);
      }

      public static BaseAttribute newInstance(XmlOptions options) {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().newInstance(BaseAttribute.type, options);
      }

      public static BaseAttribute parse(String xmlAsString) throws XmlException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, BaseAttribute.type, (XmlOptions)null);
      }

      public static BaseAttribute parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse(xmlAsString, BaseAttribute.type, options);
      }

      public static BaseAttribute parse(File file) throws XmlException, IOException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse((File)file, BaseAttribute.type, (XmlOptions)null);
      }

      public static BaseAttribute parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse(file, BaseAttribute.type, options);
      }

      public static BaseAttribute parse(URL u) throws XmlException, IOException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse((URL)u, BaseAttribute.type, (XmlOptions)null);
      }

      public static BaseAttribute parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse(u, BaseAttribute.type, options);
      }

      public static BaseAttribute parse(InputStream is) throws XmlException, IOException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse((InputStream)is, BaseAttribute.type, (XmlOptions)null);
      }

      public static BaseAttribute parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse(is, BaseAttribute.type, options);
      }

      public static BaseAttribute parse(Reader r) throws XmlException, IOException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse((Reader)r, BaseAttribute.type, (XmlOptions)null);
      }

      public static BaseAttribute parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse(r, BaseAttribute.type, options);
      }

      public static BaseAttribute parse(XMLStreamReader sr) throws XmlException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, BaseAttribute.type, (XmlOptions)null);
      }

      public static BaseAttribute parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse(sr, BaseAttribute.type, options);
      }

      public static BaseAttribute parse(Node node) throws XmlException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse((Node)node, BaseAttribute.type, (XmlOptions)null);
      }

      public static BaseAttribute parse(Node node, XmlOptions options) throws XmlException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse(node, BaseAttribute.type, options);
      }

      /** @deprecated */
      public static BaseAttribute parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, BaseAttribute.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BaseAttribute parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BaseAttribute)XmlBeans.getContextTypeLoader().parse(xis, BaseAttribute.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BaseAttribute.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BaseAttribute.type, options);
      }

      private Factory() {
      }
   }
}
