package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
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

public interface BooleanType extends XmlBoolean {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BooleanType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("booleantype8177type");

   public static final class Factory {
      public static BooleanType newValue(Object obj) {
         return (BooleanType)BooleanType.type.newValue(obj);
      }

      public static BooleanType newInstance() {
         return (BooleanType)XmlBeans.getContextTypeLoader().newInstance(BooleanType.type, (XmlOptions)null);
      }

      public static BooleanType newInstance(XmlOptions options) {
         return (BooleanType)XmlBeans.getContextTypeLoader().newInstance(BooleanType.type, options);
      }

      public static BooleanType parse(String xmlAsString) throws XmlException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BooleanType.type, (XmlOptions)null);
      }

      public static BooleanType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BooleanType.type, options);
      }

      public static BooleanType parse(File file) throws XmlException, IOException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(file, BooleanType.type, (XmlOptions)null);
      }

      public static BooleanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(file, BooleanType.type, options);
      }

      public static BooleanType parse(URL u) throws XmlException, IOException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(u, BooleanType.type, (XmlOptions)null);
      }

      public static BooleanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(u, BooleanType.type, options);
      }

      public static BooleanType parse(InputStream is) throws XmlException, IOException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(is, BooleanType.type, (XmlOptions)null);
      }

      public static BooleanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(is, BooleanType.type, options);
      }

      public static BooleanType parse(Reader r) throws XmlException, IOException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(r, BooleanType.type, (XmlOptions)null);
      }

      public static BooleanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(r, BooleanType.type, options);
      }

      public static BooleanType parse(XMLStreamReader sr) throws XmlException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(sr, BooleanType.type, (XmlOptions)null);
      }

      public static BooleanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(sr, BooleanType.type, options);
      }

      public static BooleanType parse(Node node) throws XmlException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(node, BooleanType.type, (XmlOptions)null);
      }

      public static BooleanType parse(Node node, XmlOptions options) throws XmlException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(node, BooleanType.type, options);
      }

      /** @deprecated */
      public static BooleanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(xis, BooleanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BooleanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BooleanType)XmlBeans.getContextTypeLoader().parse(xis, BooleanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BooleanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BooleanType.type, options);
      }

      private Factory() {
      }
   }
}
