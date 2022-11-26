package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface PermissionsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PermissionsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("permissionstype5439type");

   Permission[] getPermissionArray();

   Permission getPermissionArray(int var1);

   int sizeOfPermissionArray();

   void setPermissionArray(Permission[] var1);

   void setPermissionArray(int var1, Permission var2);

   Permission insertNewPermission(int var1);

   Permission addNewPermission();

   void removePermission(int var1);

   java.lang.String getVersion();

   DeweyVersionType xgetVersion();

   void setVersion(java.lang.String var1);

   void xsetVersion(DeweyVersionType var1);

   public static final class Factory {
      public static PermissionsType newInstance() {
         return (PermissionsType)XmlBeans.getContextTypeLoader().newInstance(PermissionsType.type, (XmlOptions)null);
      }

      public static PermissionsType newInstance(XmlOptions options) {
         return (PermissionsType)XmlBeans.getContextTypeLoader().newInstance(PermissionsType.type, options);
      }

      public static PermissionsType parse(java.lang.String xmlAsString) throws XmlException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PermissionsType.type, (XmlOptions)null);
      }

      public static PermissionsType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PermissionsType.type, options);
      }

      public static PermissionsType parse(File file) throws XmlException, IOException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(file, PermissionsType.type, (XmlOptions)null);
      }

      public static PermissionsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(file, PermissionsType.type, options);
      }

      public static PermissionsType parse(URL u) throws XmlException, IOException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(u, PermissionsType.type, (XmlOptions)null);
      }

      public static PermissionsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(u, PermissionsType.type, options);
      }

      public static PermissionsType parse(InputStream is) throws XmlException, IOException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(is, PermissionsType.type, (XmlOptions)null);
      }

      public static PermissionsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(is, PermissionsType.type, options);
      }

      public static PermissionsType parse(Reader r) throws XmlException, IOException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(r, PermissionsType.type, (XmlOptions)null);
      }

      public static PermissionsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(r, PermissionsType.type, options);
      }

      public static PermissionsType parse(XMLStreamReader sr) throws XmlException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(sr, PermissionsType.type, (XmlOptions)null);
      }

      public static PermissionsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(sr, PermissionsType.type, options);
      }

      public static PermissionsType parse(Node node) throws XmlException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(node, PermissionsType.type, (XmlOptions)null);
      }

      public static PermissionsType parse(Node node, XmlOptions options) throws XmlException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(node, PermissionsType.type, options);
      }

      /** @deprecated */
      public static PermissionsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(xis, PermissionsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PermissionsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PermissionsType)XmlBeans.getContextTypeLoader().parse(xis, PermissionsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PermissionsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PermissionsType.type, options);
      }

      private Factory() {
      }
   }

   public interface Permission extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Permission.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("permission50aeelemtype");

      FullyQualifiedClassType getClassName();

      void setClassName(FullyQualifiedClassType var1);

      FullyQualifiedClassType addNewClassName();

      String getName();

      boolean isSetName();

      void setName(String var1);

      String addNewName();

      void unsetName();

      String getActions();

      boolean isSetActions();

      void setActions(String var1);

      String addNewActions();

      void unsetActions();

      public static final class Factory {
         public static Permission newInstance() {
            return (Permission)XmlBeans.getContextTypeLoader().newInstance(PermissionsType.Permission.type, (XmlOptions)null);
         }

         public static Permission newInstance(XmlOptions options) {
            return (Permission)XmlBeans.getContextTypeLoader().newInstance(PermissionsType.Permission.type, options);
         }

         private Factory() {
         }
      }
   }
}
