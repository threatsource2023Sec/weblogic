package com.sun.java.xml.ns.javaee;

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

public interface AuthConstraintType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AuthConstraintType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("authconstrainttype2addtype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   RoleNameType[] getRoleNameArray();

   RoleNameType getRoleNameArray(int var1);

   int sizeOfRoleNameArray();

   void setRoleNameArray(RoleNameType[] var1);

   void setRoleNameArray(int var1, RoleNameType var2);

   RoleNameType insertNewRoleName(int var1);

   RoleNameType addNewRoleName();

   void removeRoleName(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AuthConstraintType newInstance() {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().newInstance(AuthConstraintType.type, (XmlOptions)null);
      }

      public static AuthConstraintType newInstance(XmlOptions options) {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().newInstance(AuthConstraintType.type, options);
      }

      public static AuthConstraintType parse(java.lang.String xmlAsString) throws XmlException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthConstraintType.type, (XmlOptions)null);
      }

      public static AuthConstraintType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthConstraintType.type, options);
      }

      public static AuthConstraintType parse(File file) throws XmlException, IOException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(file, AuthConstraintType.type, (XmlOptions)null);
      }

      public static AuthConstraintType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(file, AuthConstraintType.type, options);
      }

      public static AuthConstraintType parse(URL u) throws XmlException, IOException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(u, AuthConstraintType.type, (XmlOptions)null);
      }

      public static AuthConstraintType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(u, AuthConstraintType.type, options);
      }

      public static AuthConstraintType parse(InputStream is) throws XmlException, IOException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(is, AuthConstraintType.type, (XmlOptions)null);
      }

      public static AuthConstraintType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(is, AuthConstraintType.type, options);
      }

      public static AuthConstraintType parse(Reader r) throws XmlException, IOException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(r, AuthConstraintType.type, (XmlOptions)null);
      }

      public static AuthConstraintType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(r, AuthConstraintType.type, options);
      }

      public static AuthConstraintType parse(XMLStreamReader sr) throws XmlException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(sr, AuthConstraintType.type, (XmlOptions)null);
      }

      public static AuthConstraintType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(sr, AuthConstraintType.type, options);
      }

      public static AuthConstraintType parse(Node node) throws XmlException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(node, AuthConstraintType.type, (XmlOptions)null);
      }

      public static AuthConstraintType parse(Node node, XmlOptions options) throws XmlException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(node, AuthConstraintType.type, options);
      }

      /** @deprecated */
      public static AuthConstraintType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(xis, AuthConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AuthConstraintType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AuthConstraintType)XmlBeans.getContextTypeLoader().parse(xis, AuthConstraintType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthConstraintType.type, options);
      }

      private Factory() {
      }
   }
}
