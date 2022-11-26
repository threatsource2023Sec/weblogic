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

public interface ForeignConnectionFactoryType extends ForeignJndiObjectType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ForeignConnectionFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("foreignconnectionfactorytype6842type");

   String getUsername();

   XmlString xgetUsername();

   boolean isNilUsername();

   boolean isSetUsername();

   void setUsername(String var1);

   void xsetUsername(XmlString var1);

   void setNilUsername();

   void unsetUsername();

   String getPasswordEncrypted();

   XmlString xgetPasswordEncrypted();

   boolean isNilPasswordEncrypted();

   boolean isSetPasswordEncrypted();

   void setPasswordEncrypted(String var1);

   void xsetPasswordEncrypted(XmlString var1);

   void setNilPasswordEncrypted();

   void unsetPasswordEncrypted();

   EnabledDisabledType.Enum getConnectionHealthChecking();

   EnabledDisabledType xgetConnectionHealthChecking();

   boolean isSetConnectionHealthChecking();

   void setConnectionHealthChecking(EnabledDisabledType.Enum var1);

   void xsetConnectionHealthChecking(EnabledDisabledType var1);

   void unsetConnectionHealthChecking();

   public static final class Factory {
      public static ForeignConnectionFactoryType newInstance() {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().newInstance(ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ForeignConnectionFactoryType newInstance(XmlOptions options) {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().newInstance(ForeignConnectionFactoryType.type, options);
      }

      public static ForeignConnectionFactoryType parse(String xmlAsString) throws XmlException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ForeignConnectionFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignConnectionFactoryType.type, options);
      }

      public static ForeignConnectionFactoryType parse(File file) throws XmlException, IOException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(file, ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ForeignConnectionFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(file, ForeignConnectionFactoryType.type, options);
      }

      public static ForeignConnectionFactoryType parse(URL u) throws XmlException, IOException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(u, ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ForeignConnectionFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(u, ForeignConnectionFactoryType.type, options);
      }

      public static ForeignConnectionFactoryType parse(InputStream is) throws XmlException, IOException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(is, ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ForeignConnectionFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(is, ForeignConnectionFactoryType.type, options);
      }

      public static ForeignConnectionFactoryType parse(Reader r) throws XmlException, IOException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(r, ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ForeignConnectionFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(r, ForeignConnectionFactoryType.type, options);
      }

      public static ForeignConnectionFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ForeignConnectionFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ForeignConnectionFactoryType.type, options);
      }

      public static ForeignConnectionFactoryType parse(Node node) throws XmlException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(node, ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      public static ForeignConnectionFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(node, ForeignConnectionFactoryType.type, options);
      }

      /** @deprecated */
      public static ForeignConnectionFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ForeignConnectionFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ForeignConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ForeignConnectionFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignConnectionFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignConnectionFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
