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

public interface SecurityRoleType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityRoleType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("securityroletype52batype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   RoleNameType getRoleName();

   void setRoleName(RoleNameType var1);

   RoleNameType addNewRoleName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SecurityRoleType newInstance() {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().newInstance(SecurityRoleType.type, (XmlOptions)null);
      }

      public static SecurityRoleType newInstance(XmlOptions options) {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().newInstance(SecurityRoleType.type, options);
      }

      public static SecurityRoleType parse(java.lang.String xmlAsString) throws XmlException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityRoleType.type, (XmlOptions)null);
      }

      public static SecurityRoleType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityRoleType.type, options);
      }

      public static SecurityRoleType parse(File file) throws XmlException, IOException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(file, SecurityRoleType.type, (XmlOptions)null);
      }

      public static SecurityRoleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(file, SecurityRoleType.type, options);
      }

      public static SecurityRoleType parse(URL u) throws XmlException, IOException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(u, SecurityRoleType.type, (XmlOptions)null);
      }

      public static SecurityRoleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(u, SecurityRoleType.type, options);
      }

      public static SecurityRoleType parse(InputStream is) throws XmlException, IOException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(is, SecurityRoleType.type, (XmlOptions)null);
      }

      public static SecurityRoleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(is, SecurityRoleType.type, options);
      }

      public static SecurityRoleType parse(Reader r) throws XmlException, IOException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(r, SecurityRoleType.type, (XmlOptions)null);
      }

      public static SecurityRoleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(r, SecurityRoleType.type, options);
      }

      public static SecurityRoleType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(sr, SecurityRoleType.type, (XmlOptions)null);
      }

      public static SecurityRoleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(sr, SecurityRoleType.type, options);
      }

      public static SecurityRoleType parse(Node node) throws XmlException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(node, SecurityRoleType.type, (XmlOptions)null);
      }

      public static SecurityRoleType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(node, SecurityRoleType.type, options);
      }

      /** @deprecated */
      public static SecurityRoleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(xis, SecurityRoleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityRoleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityRoleType)XmlBeans.getContextTypeLoader().parse(xis, SecurityRoleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityRoleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityRoleType.type, options);
      }

      private Factory() {
      }
   }
}
