package com.sun.java.xml.ns.j2Ee;

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

public interface MethodPermissionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MethodPermissionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("methodpermissiontypecbdctype");

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

   EmptyType getUnchecked();

   boolean isSetUnchecked();

   void setUnchecked(EmptyType var1);

   EmptyType addNewUnchecked();

   void unsetUnchecked();

   MethodType[] getMethodArray();

   MethodType getMethodArray(int var1);

   int sizeOfMethodArray();

   void setMethodArray(MethodType[] var1);

   void setMethodArray(int var1, MethodType var2);

   MethodType insertNewMethod(int var1);

   MethodType addNewMethod();

   void removeMethod(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MethodPermissionType newInstance() {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().newInstance(MethodPermissionType.type, (XmlOptions)null);
      }

      public static MethodPermissionType newInstance(XmlOptions options) {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().newInstance(MethodPermissionType.type, options);
      }

      public static MethodPermissionType parse(java.lang.String xmlAsString) throws XmlException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodPermissionType.type, (XmlOptions)null);
      }

      public static MethodPermissionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodPermissionType.type, options);
      }

      public static MethodPermissionType parse(File file) throws XmlException, IOException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(file, MethodPermissionType.type, (XmlOptions)null);
      }

      public static MethodPermissionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(file, MethodPermissionType.type, options);
      }

      public static MethodPermissionType parse(URL u) throws XmlException, IOException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(u, MethodPermissionType.type, (XmlOptions)null);
      }

      public static MethodPermissionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(u, MethodPermissionType.type, options);
      }

      public static MethodPermissionType parse(InputStream is) throws XmlException, IOException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(is, MethodPermissionType.type, (XmlOptions)null);
      }

      public static MethodPermissionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(is, MethodPermissionType.type, options);
      }

      public static MethodPermissionType parse(Reader r) throws XmlException, IOException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(r, MethodPermissionType.type, (XmlOptions)null);
      }

      public static MethodPermissionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(r, MethodPermissionType.type, options);
      }

      public static MethodPermissionType parse(XMLStreamReader sr) throws XmlException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(sr, MethodPermissionType.type, (XmlOptions)null);
      }

      public static MethodPermissionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(sr, MethodPermissionType.type, options);
      }

      public static MethodPermissionType parse(Node node) throws XmlException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(node, MethodPermissionType.type, (XmlOptions)null);
      }

      public static MethodPermissionType parse(Node node, XmlOptions options) throws XmlException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(node, MethodPermissionType.type, options);
      }

      /** @deprecated */
      public static MethodPermissionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(xis, MethodPermissionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MethodPermissionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MethodPermissionType)XmlBeans.getContextTypeLoader().parse(xis, MethodPermissionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodPermissionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodPermissionType.type, options);
      }

      private Factory() {
      }
   }
}
