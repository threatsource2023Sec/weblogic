package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.EmptyType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ApplicationSecurityRoleAssignmentType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationSecurityRoleAssignmentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("applicationsecurityroleassignmenttyped5aftype");

   String getRoleName();

   XmlString xgetRoleName();

   void setRoleName(String var1);

   void xsetRoleName(XmlString var1);

   String[] getPrincipalNameArray();

   String getPrincipalNameArray(int var1);

   XmlString[] xgetPrincipalNameArray();

   XmlString xgetPrincipalNameArray(int var1);

   int sizeOfPrincipalNameArray();

   void setPrincipalNameArray(String[] var1);

   void setPrincipalNameArray(int var1, String var2);

   void xsetPrincipalNameArray(XmlString[] var1);

   void xsetPrincipalNameArray(int var1, XmlString var2);

   void insertPrincipalName(int var1, String var2);

   void addPrincipalName(String var1);

   XmlString insertNewPrincipalName(int var1);

   XmlString addNewPrincipalName();

   void removePrincipalName(int var1);

   EmptyType getExternallyDefined();

   boolean isSetExternallyDefined();

   void setExternallyDefined(EmptyType var1);

   EmptyType addNewExternallyDefined();

   void unsetExternallyDefined();

   public static final class Factory {
      public static ApplicationSecurityRoleAssignmentType newInstance() {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().newInstance(ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static ApplicationSecurityRoleAssignmentType newInstance(XmlOptions options) {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().newInstance(ApplicationSecurityRoleAssignmentType.type, options);
      }

      public static ApplicationSecurityRoleAssignmentType parse(String xmlAsString) throws XmlException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static ApplicationSecurityRoleAssignmentType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationSecurityRoleAssignmentType.type, options);
      }

      public static ApplicationSecurityRoleAssignmentType parse(File file) throws XmlException, IOException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(file, ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static ApplicationSecurityRoleAssignmentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(file, ApplicationSecurityRoleAssignmentType.type, options);
      }

      public static ApplicationSecurityRoleAssignmentType parse(URL u) throws XmlException, IOException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(u, ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static ApplicationSecurityRoleAssignmentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(u, ApplicationSecurityRoleAssignmentType.type, options);
      }

      public static ApplicationSecurityRoleAssignmentType parse(InputStream is) throws XmlException, IOException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(is, ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static ApplicationSecurityRoleAssignmentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(is, ApplicationSecurityRoleAssignmentType.type, options);
      }

      public static ApplicationSecurityRoleAssignmentType parse(Reader r) throws XmlException, IOException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(r, ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static ApplicationSecurityRoleAssignmentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(r, ApplicationSecurityRoleAssignmentType.type, options);
      }

      public static ApplicationSecurityRoleAssignmentType parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static ApplicationSecurityRoleAssignmentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationSecurityRoleAssignmentType.type, options);
      }

      public static ApplicationSecurityRoleAssignmentType parse(Node node) throws XmlException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(node, ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      public static ApplicationSecurityRoleAssignmentType parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(node, ApplicationSecurityRoleAssignmentType.type, options);
      }

      /** @deprecated */
      public static ApplicationSecurityRoleAssignmentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationSecurityRoleAssignmentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationSecurityRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationSecurityRoleAssignmentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationSecurityRoleAssignmentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationSecurityRoleAssignmentType.type, options);
      }

      private Factory() {
      }
   }
}
