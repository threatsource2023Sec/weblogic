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

public interface OutboundResourceadapterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OutboundResourceadapterType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("outboundresourceadaptertyped467type");

   ConnectionDefinitionType[] getConnectionDefinitionArray();

   ConnectionDefinitionType getConnectionDefinitionArray(int var1);

   int sizeOfConnectionDefinitionArray();

   void setConnectionDefinitionArray(ConnectionDefinitionType[] var1);

   void setConnectionDefinitionArray(int var1, ConnectionDefinitionType var2);

   ConnectionDefinitionType insertNewConnectionDefinition(int var1);

   ConnectionDefinitionType addNewConnectionDefinition();

   void removeConnectionDefinition(int var1);

   TransactionSupportType getTransactionSupport();

   boolean isSetTransactionSupport();

   void setTransactionSupport(TransactionSupportType var1);

   TransactionSupportType addNewTransactionSupport();

   void unsetTransactionSupport();

   AuthenticationMechanismType[] getAuthenticationMechanismArray();

   AuthenticationMechanismType getAuthenticationMechanismArray(int var1);

   int sizeOfAuthenticationMechanismArray();

   void setAuthenticationMechanismArray(AuthenticationMechanismType[] var1);

   void setAuthenticationMechanismArray(int var1, AuthenticationMechanismType var2);

   AuthenticationMechanismType insertNewAuthenticationMechanism(int var1);

   AuthenticationMechanismType addNewAuthenticationMechanism();

   void removeAuthenticationMechanism(int var1);

   TrueFalseType getReauthenticationSupport();

   boolean isSetReauthenticationSupport();

   void setReauthenticationSupport(TrueFalseType var1);

   TrueFalseType addNewReauthenticationSupport();

   void unsetReauthenticationSupport();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static OutboundResourceadapterType newInstance() {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().newInstance(OutboundResourceadapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceadapterType newInstance(XmlOptions options) {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().newInstance(OutboundResourceadapterType.type, options);
      }

      public static OutboundResourceadapterType parse(java.lang.String xmlAsString) throws XmlException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundResourceadapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceadapterType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundResourceadapterType.type, options);
      }

      public static OutboundResourceadapterType parse(File file) throws XmlException, IOException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(file, OutboundResourceadapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceadapterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(file, OutboundResourceadapterType.type, options);
      }

      public static OutboundResourceadapterType parse(URL u) throws XmlException, IOException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(u, OutboundResourceadapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceadapterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(u, OutboundResourceadapterType.type, options);
      }

      public static OutboundResourceadapterType parse(InputStream is) throws XmlException, IOException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(is, OutboundResourceadapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceadapterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(is, OutboundResourceadapterType.type, options);
      }

      public static OutboundResourceadapterType parse(Reader r) throws XmlException, IOException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(r, OutboundResourceadapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceadapterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(r, OutboundResourceadapterType.type, options);
      }

      public static OutboundResourceadapterType parse(XMLStreamReader sr) throws XmlException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(sr, OutboundResourceadapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceadapterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(sr, OutboundResourceadapterType.type, options);
      }

      public static OutboundResourceadapterType parse(Node node) throws XmlException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(node, OutboundResourceadapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceadapterType parse(Node node, XmlOptions options) throws XmlException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(node, OutboundResourceadapterType.type, options);
      }

      /** @deprecated */
      public static OutboundResourceadapterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(xis, OutboundResourceadapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OutboundResourceadapterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OutboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(xis, OutboundResourceadapterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundResourceadapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundResourceadapterType.type, options);
      }

      private Factory() {
      }
   }
}
