package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface LoginConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LoginConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("loginconfigtypedbd3type");

   AuthMethodType getAuthMethod();

   boolean isSetAuthMethod();

   void setAuthMethod(AuthMethodType var1);

   AuthMethodType addNewAuthMethod();

   void unsetAuthMethod();

   String getRealmName();

   boolean isSetRealmName();

   void setRealmName(String var1);

   String addNewRealmName();

   void unsetRealmName();

   FormLoginConfigType getFormLoginConfig();

   boolean isSetFormLoginConfig();

   void setFormLoginConfig(FormLoginConfigType var1);

   FormLoginConfigType addNewFormLoginConfig();

   void unsetFormLoginConfig();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static LoginConfigType newInstance() {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().newInstance(LoginConfigType.type, (XmlOptions)null);
      }

      public static LoginConfigType newInstance(XmlOptions options) {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().newInstance(LoginConfigType.type, options);
      }

      public static LoginConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoginConfigType.type, (XmlOptions)null);
      }

      public static LoginConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoginConfigType.type, options);
      }

      public static LoginConfigType parse(File file) throws XmlException, IOException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(file, LoginConfigType.type, (XmlOptions)null);
      }

      public static LoginConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(file, LoginConfigType.type, options);
      }

      public static LoginConfigType parse(URL u) throws XmlException, IOException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(u, LoginConfigType.type, (XmlOptions)null);
      }

      public static LoginConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(u, LoginConfigType.type, options);
      }

      public static LoginConfigType parse(InputStream is) throws XmlException, IOException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(is, LoginConfigType.type, (XmlOptions)null);
      }

      public static LoginConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(is, LoginConfigType.type, options);
      }

      public static LoginConfigType parse(Reader r) throws XmlException, IOException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(r, LoginConfigType.type, (XmlOptions)null);
      }

      public static LoginConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(r, LoginConfigType.type, options);
      }

      public static LoginConfigType parse(XMLStreamReader sr) throws XmlException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(sr, LoginConfigType.type, (XmlOptions)null);
      }

      public static LoginConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(sr, LoginConfigType.type, options);
      }

      public static LoginConfigType parse(Node node) throws XmlException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(node, LoginConfigType.type, (XmlOptions)null);
      }

      public static LoginConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(node, LoginConfigType.type, options);
      }

      /** @deprecated */
      public static LoginConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(xis, LoginConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LoginConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LoginConfigType)XmlBeans.getContextTypeLoader().parse(xis, LoginConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoginConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoginConfigType.type, options);
      }

      private Factory() {
      }
   }
}
