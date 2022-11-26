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

public interface PermissionsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PermissionsDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("permissionsa263doctype");

   PermissionsType getPermissions();

   void setPermissions(PermissionsType var1);

   PermissionsType addNewPermissions();

   public static final class Factory {
      public static PermissionsDocument newInstance() {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().newInstance(PermissionsDocument.type, (XmlOptions)null);
      }

      public static PermissionsDocument newInstance(XmlOptions options) {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().newInstance(PermissionsDocument.type, options);
      }

      public static PermissionsDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PermissionsDocument.type, (XmlOptions)null);
      }

      public static PermissionsDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PermissionsDocument.type, options);
      }

      public static PermissionsDocument parse(File file) throws XmlException, IOException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(file, PermissionsDocument.type, (XmlOptions)null);
      }

      public static PermissionsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(file, PermissionsDocument.type, options);
      }

      public static PermissionsDocument parse(URL u) throws XmlException, IOException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(u, PermissionsDocument.type, (XmlOptions)null);
      }

      public static PermissionsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(u, PermissionsDocument.type, options);
      }

      public static PermissionsDocument parse(InputStream is) throws XmlException, IOException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(is, PermissionsDocument.type, (XmlOptions)null);
      }

      public static PermissionsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(is, PermissionsDocument.type, options);
      }

      public static PermissionsDocument parse(Reader r) throws XmlException, IOException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(r, PermissionsDocument.type, (XmlOptions)null);
      }

      public static PermissionsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(r, PermissionsDocument.type, options);
      }

      public static PermissionsDocument parse(XMLStreamReader sr) throws XmlException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(sr, PermissionsDocument.type, (XmlOptions)null);
      }

      public static PermissionsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(sr, PermissionsDocument.type, options);
      }

      public static PermissionsDocument parse(Node node) throws XmlException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(node, PermissionsDocument.type, (XmlOptions)null);
      }

      public static PermissionsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(node, PermissionsDocument.type, options);
      }

      /** @deprecated */
      public static PermissionsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(xis, PermissionsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PermissionsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PermissionsDocument)XmlBeans.getContextTypeLoader().parse(xis, PermissionsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PermissionsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PermissionsDocument.type, options);
      }

      private Factory() {
      }
   }
}
