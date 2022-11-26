package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface SafRemoteContextType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafRemoteContextType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("safremotecontexttype7599type");

   SafLoginContextType getSafLoginContext();

   boolean isSetSafLoginContext();

   void setSafLoginContext(SafLoginContextType var1);

   SafLoginContextType addNewSafLoginContext();

   void unsetSafLoginContext();

   int getCompressionThreshold();

   XmlInt xgetCompressionThreshold();

   boolean isSetCompressionThreshold();

   void setCompressionThreshold(int var1);

   void xsetCompressionThreshold(XmlInt var1);

   void unsetCompressionThreshold();

   String getReplyToSafRemoteContextName();

   XmlString xgetReplyToSafRemoteContextName();

   boolean isNilReplyToSafRemoteContextName();

   boolean isSetReplyToSafRemoteContextName();

   void setReplyToSafRemoteContextName(String var1);

   void xsetReplyToSafRemoteContextName(XmlString var1);

   void setNilReplyToSafRemoteContextName();

   void unsetReplyToSafRemoteContextName();

   public static final class Factory {
      public static SafRemoteContextType newInstance() {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().newInstance(SafRemoteContextType.type, (XmlOptions)null);
      }

      public static SafRemoteContextType newInstance(XmlOptions options) {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().newInstance(SafRemoteContextType.type, options);
      }

      public static SafRemoteContextType parse(String xmlAsString) throws XmlException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafRemoteContextType.type, (XmlOptions)null);
      }

      public static SafRemoteContextType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafRemoteContextType.type, options);
      }

      public static SafRemoteContextType parse(File file) throws XmlException, IOException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(file, SafRemoteContextType.type, (XmlOptions)null);
      }

      public static SafRemoteContextType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(file, SafRemoteContextType.type, options);
      }

      public static SafRemoteContextType parse(URL u) throws XmlException, IOException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(u, SafRemoteContextType.type, (XmlOptions)null);
      }

      public static SafRemoteContextType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(u, SafRemoteContextType.type, options);
      }

      public static SafRemoteContextType parse(InputStream is) throws XmlException, IOException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(is, SafRemoteContextType.type, (XmlOptions)null);
      }

      public static SafRemoteContextType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(is, SafRemoteContextType.type, options);
      }

      public static SafRemoteContextType parse(Reader r) throws XmlException, IOException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(r, SafRemoteContextType.type, (XmlOptions)null);
      }

      public static SafRemoteContextType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(r, SafRemoteContextType.type, options);
      }

      public static SafRemoteContextType parse(XMLStreamReader sr) throws XmlException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(sr, SafRemoteContextType.type, (XmlOptions)null);
      }

      public static SafRemoteContextType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(sr, SafRemoteContextType.type, options);
      }

      public static SafRemoteContextType parse(Node node) throws XmlException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(node, SafRemoteContextType.type, (XmlOptions)null);
      }

      public static SafRemoteContextType parse(Node node, XmlOptions options) throws XmlException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(node, SafRemoteContextType.type, options);
      }

      /** @deprecated */
      public static SafRemoteContextType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(xis, SafRemoteContextType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SafRemoteContextType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SafRemoteContextType)XmlBeans.getContextTypeLoader().parse(xis, SafRemoteContextType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafRemoteContextType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafRemoteContextType.type, options);
      }

      private Factory() {
      }
   }
}
