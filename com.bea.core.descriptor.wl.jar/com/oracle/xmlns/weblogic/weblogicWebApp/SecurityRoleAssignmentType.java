package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.EmptyType;
import com.sun.java.xml.ns.javaee.RoleNameType;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface SecurityRoleAssignmentType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityRoleAssignmentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("securityroleassignmenttype876atype");

   RoleNameType getRoleName();

   void setRoleName(RoleNameType var1);

   RoleNameType addNewRoleName();

   String[] getPrincipalNameArray();

   String getPrincipalNameArray(int var1);

   int sizeOfPrincipalNameArray();

   void setPrincipalNameArray(String[] var1);

   void setPrincipalNameArray(int var1, String var2);

   String insertNewPrincipalName(int var1);

   String addNewPrincipalName();

   void removePrincipalName(int var1);

   EmptyType getExternallyDefined();

   boolean isSetExternallyDefined();

   void setExternallyDefined(EmptyType var1);

   EmptyType addNewExternallyDefined();

   void unsetExternallyDefined();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SecurityRoleAssignmentType newInstance() {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().newInstance(SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static SecurityRoleAssignmentType newInstance(XmlOptions options) {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().newInstance(SecurityRoleAssignmentType.type, options);
      }

      public static SecurityRoleAssignmentType parse(java.lang.String xmlAsString) throws XmlException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static SecurityRoleAssignmentType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityRoleAssignmentType.type, options);
      }

      public static SecurityRoleAssignmentType parse(File file) throws XmlException, IOException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(file, SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static SecurityRoleAssignmentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(file, SecurityRoleAssignmentType.type, options);
      }

      public static SecurityRoleAssignmentType parse(URL u) throws XmlException, IOException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(u, SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static SecurityRoleAssignmentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(u, SecurityRoleAssignmentType.type, options);
      }

      public static SecurityRoleAssignmentType parse(InputStream is) throws XmlException, IOException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(is, SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static SecurityRoleAssignmentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(is, SecurityRoleAssignmentType.type, options);
      }

      public static SecurityRoleAssignmentType parse(Reader r) throws XmlException, IOException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(r, SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static SecurityRoleAssignmentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(r, SecurityRoleAssignmentType.type, options);
      }

      public static SecurityRoleAssignmentType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(sr, SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static SecurityRoleAssignmentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(sr, SecurityRoleAssignmentType.type, options);
      }

      public static SecurityRoleAssignmentType parse(Node node) throws XmlException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(node, SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static SecurityRoleAssignmentType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(node, SecurityRoleAssignmentType.type, options);
      }

      /** @deprecated */
      public static SecurityRoleAssignmentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xis, SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityRoleAssignmentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xis, SecurityRoleAssignmentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityRoleAssignmentType.type, options);
      }

      private Factory() {
      }
   }
}
