package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface ForeignJndiObjectType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ForeignJndiObjectType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("foreignjndiobjecttypea15etype");

   String getLocalJndiName();

   XmlString xgetLocalJndiName();

   void setLocalJndiName(String var1);

   void xsetLocalJndiName(XmlString var1);

   String getRemoteJndiName();

   XmlString xgetRemoteJndiName();

   void setRemoteJndiName(String var1);

   void xsetRemoteJndiName(XmlString var1);

   public static final class Factory {
      public static ForeignJndiObjectType newInstance() {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().newInstance(ForeignJndiObjectType.type, (XmlOptions)null);
      }

      public static ForeignJndiObjectType newInstance(XmlOptions options) {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().newInstance(ForeignJndiObjectType.type, options);
      }

      public static ForeignJndiObjectType parse(String xmlAsString) throws XmlException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignJndiObjectType.type, (XmlOptions)null);
      }

      public static ForeignJndiObjectType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignJndiObjectType.type, options);
      }

      public static ForeignJndiObjectType parse(File file) throws XmlException, IOException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(file, ForeignJndiObjectType.type, (XmlOptions)null);
      }

      public static ForeignJndiObjectType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(file, ForeignJndiObjectType.type, options);
      }

      public static ForeignJndiObjectType parse(URL u) throws XmlException, IOException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(u, ForeignJndiObjectType.type, (XmlOptions)null);
      }

      public static ForeignJndiObjectType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(u, ForeignJndiObjectType.type, options);
      }

      public static ForeignJndiObjectType parse(InputStream is) throws XmlException, IOException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(is, ForeignJndiObjectType.type, (XmlOptions)null);
      }

      public static ForeignJndiObjectType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(is, ForeignJndiObjectType.type, options);
      }

      public static ForeignJndiObjectType parse(Reader r) throws XmlException, IOException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(r, ForeignJndiObjectType.type, (XmlOptions)null);
      }

      public static ForeignJndiObjectType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(r, ForeignJndiObjectType.type, options);
      }

      public static ForeignJndiObjectType parse(XMLStreamReader sr) throws XmlException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(sr, ForeignJndiObjectType.type, (XmlOptions)null);
      }

      public static ForeignJndiObjectType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(sr, ForeignJndiObjectType.type, options);
      }

      public static ForeignJndiObjectType parse(Node node) throws XmlException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(node, ForeignJndiObjectType.type, (XmlOptions)null);
      }

      public static ForeignJndiObjectType parse(Node node, XmlOptions options) throws XmlException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(node, ForeignJndiObjectType.type, options);
      }

      /** @deprecated */
      public static ForeignJndiObjectType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(xis, ForeignJndiObjectType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ForeignJndiObjectType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ForeignJndiObjectType)XmlBeans.getContextTypeLoader().parse(xis, ForeignJndiObjectType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignJndiObjectType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignJndiObjectType.type, options);
      }

      private Factory() {
      }
   }
}
