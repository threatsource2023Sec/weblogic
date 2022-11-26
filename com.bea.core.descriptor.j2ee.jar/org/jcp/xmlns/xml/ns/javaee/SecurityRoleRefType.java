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

public interface SecurityRoleRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityRoleRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("securityrolereftype1fb4type");

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

   RoleNameType getRoleLink();

   boolean isSetRoleLink();

   void setRoleLink(RoleNameType var1);

   RoleNameType addNewRoleLink();

   void unsetRoleLink();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SecurityRoleRefType newInstance() {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().newInstance(SecurityRoleRefType.type, (XmlOptions)null);
      }

      public static SecurityRoleRefType newInstance(XmlOptions options) {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().newInstance(SecurityRoleRefType.type, options);
      }

      public static SecurityRoleRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityRoleRefType.type, (XmlOptions)null);
      }

      public static SecurityRoleRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityRoleRefType.type, options);
      }

      public static SecurityRoleRefType parse(File file) throws XmlException, IOException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(file, SecurityRoleRefType.type, (XmlOptions)null);
      }

      public static SecurityRoleRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(file, SecurityRoleRefType.type, options);
      }

      public static SecurityRoleRefType parse(URL u) throws XmlException, IOException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(u, SecurityRoleRefType.type, (XmlOptions)null);
      }

      public static SecurityRoleRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(u, SecurityRoleRefType.type, options);
      }

      public static SecurityRoleRefType parse(InputStream is) throws XmlException, IOException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(is, SecurityRoleRefType.type, (XmlOptions)null);
      }

      public static SecurityRoleRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(is, SecurityRoleRefType.type, options);
      }

      public static SecurityRoleRefType parse(Reader r) throws XmlException, IOException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(r, SecurityRoleRefType.type, (XmlOptions)null);
      }

      public static SecurityRoleRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(r, SecurityRoleRefType.type, options);
      }

      public static SecurityRoleRefType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(sr, SecurityRoleRefType.type, (XmlOptions)null);
      }

      public static SecurityRoleRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(sr, SecurityRoleRefType.type, options);
      }

      public static SecurityRoleRefType parse(Node node) throws XmlException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(node, SecurityRoleRefType.type, (XmlOptions)null);
      }

      public static SecurityRoleRefType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(node, SecurityRoleRefType.type, options);
      }

      /** @deprecated */
      public static SecurityRoleRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(xis, SecurityRoleRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityRoleRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityRoleRefType)XmlBeans.getContextTypeLoader().parse(xis, SecurityRoleRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityRoleRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityRoleRefType.type, options);
      }

      private Factory() {
      }
   }
}
