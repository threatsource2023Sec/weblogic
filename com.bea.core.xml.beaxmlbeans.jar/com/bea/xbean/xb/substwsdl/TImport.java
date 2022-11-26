package com.bea.xbean.xb.substwsdl;

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

public interface TImport extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TImport.class.getClassLoader(), "schemacom_bea_xml.system.sXMLTOOLS").resolveHandle("timport22datype");

   String getNamespace();

   XmlAnyURI xgetNamespace();

   void setNamespace(String var1);

   void xsetNamespace(XmlAnyURI var1);

   String getLocation();

   XmlAnyURI xgetLocation();

   void setLocation(String var1);

   void xsetLocation(XmlAnyURI var1);

   public static final class Factory {
      public static TImport newInstance() {
         return (TImport)XmlBeans.getContextTypeLoader().newInstance(TImport.type, (XmlOptions)null);
      }

      public static TImport newInstance(XmlOptions options) {
         return (TImport)XmlBeans.getContextTypeLoader().newInstance(TImport.type, options);
      }

      public static TImport parse(String xmlAsString) throws XmlException {
         return (TImport)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, TImport.type, (XmlOptions)null);
      }

      public static TImport parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TImport)XmlBeans.getContextTypeLoader().parse(xmlAsString, TImport.type, options);
      }

      public static TImport parse(File file) throws XmlException, IOException {
         return (TImport)XmlBeans.getContextTypeLoader().parse((File)file, TImport.type, (XmlOptions)null);
      }

      public static TImport parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TImport)XmlBeans.getContextTypeLoader().parse(file, TImport.type, options);
      }

      public static TImport parse(URL u) throws XmlException, IOException {
         return (TImport)XmlBeans.getContextTypeLoader().parse((URL)u, TImport.type, (XmlOptions)null);
      }

      public static TImport parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TImport)XmlBeans.getContextTypeLoader().parse(u, TImport.type, options);
      }

      public static TImport parse(InputStream is) throws XmlException, IOException {
         return (TImport)XmlBeans.getContextTypeLoader().parse((InputStream)is, TImport.type, (XmlOptions)null);
      }

      public static TImport parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TImport)XmlBeans.getContextTypeLoader().parse(is, TImport.type, options);
      }

      public static TImport parse(Reader r) throws XmlException, IOException {
         return (TImport)XmlBeans.getContextTypeLoader().parse((Reader)r, TImport.type, (XmlOptions)null);
      }

      public static TImport parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TImport)XmlBeans.getContextTypeLoader().parse(r, TImport.type, options);
      }

      public static TImport parse(XMLStreamReader sr) throws XmlException {
         return (TImport)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, TImport.type, (XmlOptions)null);
      }

      public static TImport parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TImport)XmlBeans.getContextTypeLoader().parse(sr, TImport.type, options);
      }

      public static TImport parse(Node node) throws XmlException {
         return (TImport)XmlBeans.getContextTypeLoader().parse((Node)node, TImport.type, (XmlOptions)null);
      }

      public static TImport parse(Node node, XmlOptions options) throws XmlException {
         return (TImport)XmlBeans.getContextTypeLoader().parse(node, TImport.type, options);
      }

      /** @deprecated */
      public static TImport parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TImport)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, TImport.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TImport parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TImport)XmlBeans.getContextTypeLoader().parse(xis, TImport.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TImport.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TImport.type, options);
      }

      private Factory() {
      }
   }
}
