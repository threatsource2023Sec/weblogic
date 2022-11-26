package com.bea.connector.monitoring1Dot0;

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

public interface NativeLibdirDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NativeLibdirDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("nativelibdiraea2doctype");

   String getNativeLibdir();

   XmlString xgetNativeLibdir();

   void setNativeLibdir(String var1);

   void xsetNativeLibdir(XmlString var1);

   public static final class Factory {
      public static NativeLibdirDocument newInstance() {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().newInstance(NativeLibdirDocument.type, (XmlOptions)null);
      }

      public static NativeLibdirDocument newInstance(XmlOptions options) {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().newInstance(NativeLibdirDocument.type, options);
      }

      public static NativeLibdirDocument parse(String xmlAsString) throws XmlException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, NativeLibdirDocument.type, (XmlOptions)null);
      }

      public static NativeLibdirDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, NativeLibdirDocument.type, options);
      }

      public static NativeLibdirDocument parse(File file) throws XmlException, IOException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(file, NativeLibdirDocument.type, (XmlOptions)null);
      }

      public static NativeLibdirDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(file, NativeLibdirDocument.type, options);
      }

      public static NativeLibdirDocument parse(URL u) throws XmlException, IOException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(u, NativeLibdirDocument.type, (XmlOptions)null);
      }

      public static NativeLibdirDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(u, NativeLibdirDocument.type, options);
      }

      public static NativeLibdirDocument parse(InputStream is) throws XmlException, IOException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(is, NativeLibdirDocument.type, (XmlOptions)null);
      }

      public static NativeLibdirDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(is, NativeLibdirDocument.type, options);
      }

      public static NativeLibdirDocument parse(Reader r) throws XmlException, IOException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(r, NativeLibdirDocument.type, (XmlOptions)null);
      }

      public static NativeLibdirDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(r, NativeLibdirDocument.type, options);
      }

      public static NativeLibdirDocument parse(XMLStreamReader sr) throws XmlException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(sr, NativeLibdirDocument.type, (XmlOptions)null);
      }

      public static NativeLibdirDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(sr, NativeLibdirDocument.type, options);
      }

      public static NativeLibdirDocument parse(Node node) throws XmlException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(node, NativeLibdirDocument.type, (XmlOptions)null);
      }

      public static NativeLibdirDocument parse(Node node, XmlOptions options) throws XmlException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(node, NativeLibdirDocument.type, options);
      }

      /** @deprecated */
      public static NativeLibdirDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(xis, NativeLibdirDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NativeLibdirDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NativeLibdirDocument)XmlBeans.getContextTypeLoader().parse(xis, NativeLibdirDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NativeLibdirDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NativeLibdirDocument.type, options);
      }

      private Factory() {
      }
   }
}
