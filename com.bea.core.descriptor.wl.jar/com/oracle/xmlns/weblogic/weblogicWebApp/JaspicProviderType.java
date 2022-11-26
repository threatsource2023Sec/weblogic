package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface JaspicProviderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JaspicProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("jaspicprovidertype1231type");

   TrueFalseType getEnabled();

   boolean isSetEnabled();

   void setEnabled(TrueFalseType var1);

   TrueFalseType addNewEnabled();

   void unsetEnabled();

   String getAuthConfigProviderName();

   XmlString xgetAuthConfigProviderName();

   boolean isSetAuthConfigProviderName();

   void setAuthConfigProviderName(String var1);

   void xsetAuthConfigProviderName(XmlString var1);

   void unsetAuthConfigProviderName();

   public static final class Factory {
      public static JaspicProviderType newInstance() {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().newInstance(JaspicProviderType.type, (XmlOptions)null);
      }

      public static JaspicProviderType newInstance(XmlOptions options) {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().newInstance(JaspicProviderType.type, options);
      }

      public static JaspicProviderType parse(String xmlAsString) throws XmlException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JaspicProviderType.type, (XmlOptions)null);
      }

      public static JaspicProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JaspicProviderType.type, options);
      }

      public static JaspicProviderType parse(File file) throws XmlException, IOException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(file, JaspicProviderType.type, (XmlOptions)null);
      }

      public static JaspicProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(file, JaspicProviderType.type, options);
      }

      public static JaspicProviderType parse(URL u) throws XmlException, IOException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(u, JaspicProviderType.type, (XmlOptions)null);
      }

      public static JaspicProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(u, JaspicProviderType.type, options);
      }

      public static JaspicProviderType parse(InputStream is) throws XmlException, IOException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(is, JaspicProviderType.type, (XmlOptions)null);
      }

      public static JaspicProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(is, JaspicProviderType.type, options);
      }

      public static JaspicProviderType parse(Reader r) throws XmlException, IOException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(r, JaspicProviderType.type, (XmlOptions)null);
      }

      public static JaspicProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(r, JaspicProviderType.type, options);
      }

      public static JaspicProviderType parse(XMLStreamReader sr) throws XmlException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(sr, JaspicProviderType.type, (XmlOptions)null);
      }

      public static JaspicProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(sr, JaspicProviderType.type, options);
      }

      public static JaspicProviderType parse(Node node) throws XmlException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(node, JaspicProviderType.type, (XmlOptions)null);
      }

      public static JaspicProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(node, JaspicProviderType.type, options);
      }

      /** @deprecated */
      public static JaspicProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(xis, JaspicProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JaspicProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JaspicProviderType)XmlBeans.getContextTypeLoader().parse(xis, JaspicProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JaspicProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JaspicProviderType.type, options);
      }

      private Factory() {
      }
   }
}
