package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
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

public interface CredentialInterfaceType extends FullyQualifiedClassType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CredentialInterfaceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("credentialinterfacetype5630type");
   Enum JAVAX_RESOURCE_SPI_SECURITY_PASSWORD_CREDENTIAL = CredentialInterfaceType.Enum.forString("javax.resource.spi.security.PasswordCredential");
   Enum ORG_IETF_JGSS_GSS_CREDENTIAL = CredentialInterfaceType.Enum.forString("org.ietf.jgss.GSSCredential");
   Enum JAVAX_RESOURCE_SPI_SECURITY_GENERIC_CREDENTIAL = CredentialInterfaceType.Enum.forString("javax.resource.spi.security.GenericCredential");
   int INT_JAVAX_RESOURCE_SPI_SECURITY_PASSWORD_CREDENTIAL = 1;
   int INT_ORG_IETF_JGSS_GSS_CREDENTIAL = 2;
   int INT_JAVAX_RESOURCE_SPI_SECURITY_GENERIC_CREDENTIAL = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static CredentialInterfaceType newInstance() {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().newInstance(CredentialInterfaceType.type, (XmlOptions)null);
      }

      public static CredentialInterfaceType newInstance(XmlOptions options) {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().newInstance(CredentialInterfaceType.type, options);
      }

      public static CredentialInterfaceType parse(java.lang.String xmlAsString) throws XmlException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CredentialInterfaceType.type, (XmlOptions)null);
      }

      public static CredentialInterfaceType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CredentialInterfaceType.type, options);
      }

      public static CredentialInterfaceType parse(File file) throws XmlException, IOException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(file, CredentialInterfaceType.type, (XmlOptions)null);
      }

      public static CredentialInterfaceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(file, CredentialInterfaceType.type, options);
      }

      public static CredentialInterfaceType parse(URL u) throws XmlException, IOException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(u, CredentialInterfaceType.type, (XmlOptions)null);
      }

      public static CredentialInterfaceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(u, CredentialInterfaceType.type, options);
      }

      public static CredentialInterfaceType parse(InputStream is) throws XmlException, IOException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(is, CredentialInterfaceType.type, (XmlOptions)null);
      }

      public static CredentialInterfaceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(is, CredentialInterfaceType.type, options);
      }

      public static CredentialInterfaceType parse(Reader r) throws XmlException, IOException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(r, CredentialInterfaceType.type, (XmlOptions)null);
      }

      public static CredentialInterfaceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(r, CredentialInterfaceType.type, options);
      }

      public static CredentialInterfaceType parse(XMLStreamReader sr) throws XmlException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(sr, CredentialInterfaceType.type, (XmlOptions)null);
      }

      public static CredentialInterfaceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(sr, CredentialInterfaceType.type, options);
      }

      public static CredentialInterfaceType parse(Node node) throws XmlException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(node, CredentialInterfaceType.type, (XmlOptions)null);
      }

      public static CredentialInterfaceType parse(Node node, XmlOptions options) throws XmlException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(node, CredentialInterfaceType.type, options);
      }

      /** @deprecated */
      public static CredentialInterfaceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(xis, CredentialInterfaceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CredentialInterfaceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CredentialInterfaceType)XmlBeans.getContextTypeLoader().parse(xis, CredentialInterfaceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CredentialInterfaceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CredentialInterfaceType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_JAVAX_RESOURCE_SPI_SECURITY_PASSWORD_CREDENTIAL = 1;
      static final int INT_ORG_IETF_JGSS_GSS_CREDENTIAL = 2;
      static final int INT_JAVAX_RESOURCE_SPI_SECURITY_GENERIC_CREDENTIAL = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("javax.resource.spi.security.PasswordCredential", 1), new Enum("org.ietf.jgss.GSSCredential", 2), new Enum("javax.resource.spi.security.GenericCredential", 3)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(java.lang.String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(java.lang.String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
