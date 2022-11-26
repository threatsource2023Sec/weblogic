package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface SecurityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("securitytype139btype");

   String getRealmName();

   XmlString xgetRealmName();

   boolean isSetRealmName();

   void setRealmName(String var1);

   void xsetRealmName(XmlString var1);

   void unsetRealmName();

   ApplicationSecurityRoleAssignmentType[] getSecurityRoleAssignmentArray();

   ApplicationSecurityRoleAssignmentType getSecurityRoleAssignmentArray(int var1);

   int sizeOfSecurityRoleAssignmentArray();

   void setSecurityRoleAssignmentArray(ApplicationSecurityRoleAssignmentType[] var1);

   void setSecurityRoleAssignmentArray(int var1, ApplicationSecurityRoleAssignmentType var2);

   ApplicationSecurityRoleAssignmentType insertNewSecurityRoleAssignment(int var1);

   ApplicationSecurityRoleAssignmentType addNewSecurityRoleAssignment();

   void removeSecurityRoleAssignment(int var1);

   public static final class Factory {
      public static SecurityType newInstance() {
         return (SecurityType)XmlBeans.getContextTypeLoader().newInstance(SecurityType.type, (XmlOptions)null);
      }

      public static SecurityType newInstance(XmlOptions options) {
         return (SecurityType)XmlBeans.getContextTypeLoader().newInstance(SecurityType.type, options);
      }

      public static SecurityType parse(String xmlAsString) throws XmlException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityType.type, (XmlOptions)null);
      }

      public static SecurityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityType.type, options);
      }

      public static SecurityType parse(File file) throws XmlException, IOException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(file, SecurityType.type, (XmlOptions)null);
      }

      public static SecurityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(file, SecurityType.type, options);
      }

      public static SecurityType parse(URL u) throws XmlException, IOException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(u, SecurityType.type, (XmlOptions)null);
      }

      public static SecurityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(u, SecurityType.type, options);
      }

      public static SecurityType parse(InputStream is) throws XmlException, IOException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(is, SecurityType.type, (XmlOptions)null);
      }

      public static SecurityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(is, SecurityType.type, options);
      }

      public static SecurityType parse(Reader r) throws XmlException, IOException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(r, SecurityType.type, (XmlOptions)null);
      }

      public static SecurityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(r, SecurityType.type, options);
      }

      public static SecurityType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(sr, SecurityType.type, (XmlOptions)null);
      }

      public static SecurityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(sr, SecurityType.type, options);
      }

      public static SecurityType parse(Node node) throws XmlException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(node, SecurityType.type, (XmlOptions)null);
      }

      public static SecurityType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(node, SecurityType.type, options);
      }

      /** @deprecated */
      public static SecurityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(xis, SecurityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityType)XmlBeans.getContextTypeLoader().parse(xis, SecurityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityType.type, options);
      }

      private Factory() {
      }
   }
}
