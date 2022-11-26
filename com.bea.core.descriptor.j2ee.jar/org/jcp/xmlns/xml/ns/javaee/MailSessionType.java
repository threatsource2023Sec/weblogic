package org.jcp.xmlns.xml.ns.javaee;

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

public interface MailSessionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MailSessionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("mailsessiontype098btype");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   JndiNameType getName();

   void setName(JndiNameType var1);

   JndiNameType addNewName();

   String getStoreProtocol();

   boolean isSetStoreProtocol();

   void setStoreProtocol(String var1);

   String addNewStoreProtocol();

   void unsetStoreProtocol();

   FullyQualifiedClassType getStoreProtocolClass();

   boolean isSetStoreProtocolClass();

   void setStoreProtocolClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewStoreProtocolClass();

   void unsetStoreProtocolClass();

   String getTransportProtocol();

   boolean isSetTransportProtocol();

   void setTransportProtocol(String var1);

   String addNewTransportProtocol();

   void unsetTransportProtocol();

   FullyQualifiedClassType getTransportProtocolClass();

   boolean isSetTransportProtocolClass();

   void setTransportProtocolClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewTransportProtocolClass();

   void unsetTransportProtocolClass();

   String getHost();

   boolean isSetHost();

   void setHost(String var1);

   String addNewHost();

   void unsetHost();

   String getUser();

   boolean isSetUser();

   void setUser(String var1);

   String addNewUser();

   void unsetUser();

   String getPassword();

   boolean isSetPassword();

   void setPassword(String var1);

   String addNewPassword();

   void unsetPassword();

   String getFrom();

   boolean isSetFrom();

   void setFrom(String var1);

   String addNewFrom();

   void unsetFrom();

   PropertyType[] getPropertyArray();

   PropertyType getPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(PropertyType[] var1);

   void setPropertyArray(int var1, PropertyType var2);

   PropertyType insertNewProperty(int var1);

   PropertyType addNewProperty();

   void removeProperty(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MailSessionType newInstance() {
         return (MailSessionType)XmlBeans.getContextTypeLoader().newInstance(MailSessionType.type, (XmlOptions)null);
      }

      public static MailSessionType newInstance(XmlOptions options) {
         return (MailSessionType)XmlBeans.getContextTypeLoader().newInstance(MailSessionType.type, options);
      }

      public static MailSessionType parse(java.lang.String xmlAsString) throws XmlException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MailSessionType.type, (XmlOptions)null);
      }

      public static MailSessionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MailSessionType.type, options);
      }

      public static MailSessionType parse(File file) throws XmlException, IOException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(file, MailSessionType.type, (XmlOptions)null);
      }

      public static MailSessionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(file, MailSessionType.type, options);
      }

      public static MailSessionType parse(URL u) throws XmlException, IOException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(u, MailSessionType.type, (XmlOptions)null);
      }

      public static MailSessionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(u, MailSessionType.type, options);
      }

      public static MailSessionType parse(InputStream is) throws XmlException, IOException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(is, MailSessionType.type, (XmlOptions)null);
      }

      public static MailSessionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(is, MailSessionType.type, options);
      }

      public static MailSessionType parse(Reader r) throws XmlException, IOException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(r, MailSessionType.type, (XmlOptions)null);
      }

      public static MailSessionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(r, MailSessionType.type, options);
      }

      public static MailSessionType parse(XMLStreamReader sr) throws XmlException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(sr, MailSessionType.type, (XmlOptions)null);
      }

      public static MailSessionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(sr, MailSessionType.type, options);
      }

      public static MailSessionType parse(Node node) throws XmlException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(node, MailSessionType.type, (XmlOptions)null);
      }

      public static MailSessionType parse(Node node, XmlOptions options) throws XmlException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(node, MailSessionType.type, options);
      }

      /** @deprecated */
      public static MailSessionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(xis, MailSessionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MailSessionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MailSessionType)XmlBeans.getContextTypeLoader().parse(xis, MailSessionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MailSessionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MailSessionType.type, options);
      }

      private Factory() {
      }
   }
}
