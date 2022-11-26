package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
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

public interface RunAsRoleAssignmentType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RunAsRoleAssignmentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("runasroleassignmenttypeb446type");

   RoleNameType getRoleName();

   void setRoleName(RoleNameType var1);

   RoleNameType addNewRoleName();

   String getRunAsPrincipalName();

   void setRunAsPrincipalName(String var1);

   String addNewRunAsPrincipalName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RunAsRoleAssignmentType newInstance() {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().newInstance(RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      public static RunAsRoleAssignmentType newInstance(XmlOptions options) {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().newInstance(RunAsRoleAssignmentType.type, options);
      }

      public static RunAsRoleAssignmentType parse(java.lang.String xmlAsString) throws XmlException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      public static RunAsRoleAssignmentType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RunAsRoleAssignmentType.type, options);
      }

      public static RunAsRoleAssignmentType parse(File file) throws XmlException, IOException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(file, RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      public static RunAsRoleAssignmentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(file, RunAsRoleAssignmentType.type, options);
      }

      public static RunAsRoleAssignmentType parse(URL u) throws XmlException, IOException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(u, RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      public static RunAsRoleAssignmentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(u, RunAsRoleAssignmentType.type, options);
      }

      public static RunAsRoleAssignmentType parse(InputStream is) throws XmlException, IOException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(is, RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      public static RunAsRoleAssignmentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(is, RunAsRoleAssignmentType.type, options);
      }

      public static RunAsRoleAssignmentType parse(Reader r) throws XmlException, IOException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(r, RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      public static RunAsRoleAssignmentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(r, RunAsRoleAssignmentType.type, options);
      }

      public static RunAsRoleAssignmentType parse(XMLStreamReader sr) throws XmlException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(sr, RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      public static RunAsRoleAssignmentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(sr, RunAsRoleAssignmentType.type, options);
      }

      public static RunAsRoleAssignmentType parse(Node node) throws XmlException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(node, RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      public static RunAsRoleAssignmentType parse(Node node, XmlOptions options) throws XmlException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(node, RunAsRoleAssignmentType.type, options);
      }

      /** @deprecated */
      public static RunAsRoleAssignmentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xis, RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RunAsRoleAssignmentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RunAsRoleAssignmentType)XmlBeans.getContextTypeLoader().parse(xis, RunAsRoleAssignmentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RunAsRoleAssignmentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RunAsRoleAssignmentType.type, options);
      }

      private Factory() {
      }
   }
}
