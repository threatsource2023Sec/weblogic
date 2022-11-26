package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface SafLoginContextType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafLoginContextType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("saflogincontexttype3f52type");

   String getLoginURL();

   XmlString xgetLoginURL();

   void setLoginURL(String var1);

   void xsetLoginURL(XmlString var1);

   String getUsername();

   XmlString xgetUsername();

   boolean isSetUsername();

   void setUsername(String var1);

   void xsetUsername(XmlString var1);

   void unsetUsername();

   String getPasswordEncrypted();

   XmlString xgetPasswordEncrypted();

   boolean isSetPasswordEncrypted();

   void setPasswordEncrypted(String var1);

   void xsetPasswordEncrypted(XmlString var1);

   void unsetPasswordEncrypted();

   public static final class Factory {
      public static SafLoginContextType newInstance() {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().newInstance(SafLoginContextType.type, (XmlOptions)null);
      }

      public static SafLoginContextType newInstance(XmlOptions options) {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().newInstance(SafLoginContextType.type, options);
      }

      public static SafLoginContextType parse(String xmlAsString) throws XmlException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafLoginContextType.type, (XmlOptions)null);
      }

      public static SafLoginContextType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafLoginContextType.type, options);
      }

      public static SafLoginContextType parse(File file) throws XmlException, IOException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(file, SafLoginContextType.type, (XmlOptions)null);
      }

      public static SafLoginContextType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(file, SafLoginContextType.type, options);
      }

      public static SafLoginContextType parse(URL u) throws XmlException, IOException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(u, SafLoginContextType.type, (XmlOptions)null);
      }

      public static SafLoginContextType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(u, SafLoginContextType.type, options);
      }

      public static SafLoginContextType parse(InputStream is) throws XmlException, IOException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(is, SafLoginContextType.type, (XmlOptions)null);
      }

      public static SafLoginContextType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(is, SafLoginContextType.type, options);
      }

      public static SafLoginContextType parse(Reader r) throws XmlException, IOException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(r, SafLoginContextType.type, (XmlOptions)null);
      }

      public static SafLoginContextType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(r, SafLoginContextType.type, options);
      }

      public static SafLoginContextType parse(XMLStreamReader sr) throws XmlException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(sr, SafLoginContextType.type, (XmlOptions)null);
      }

      public static SafLoginContextType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(sr, SafLoginContextType.type, options);
      }

      public static SafLoginContextType parse(Node node) throws XmlException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(node, SafLoginContextType.type, (XmlOptions)null);
      }

      public static SafLoginContextType parse(Node node, XmlOptions options) throws XmlException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(node, SafLoginContextType.type, options);
      }

      /** @deprecated */
      public static SafLoginContextType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(xis, SafLoginContextType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SafLoginContextType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SafLoginContextType)XmlBeans.getContextTypeLoader().parse(xis, SafLoginContextType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafLoginContextType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafLoginContextType.type, options);
      }

      private Factory() {
      }
   }
}
