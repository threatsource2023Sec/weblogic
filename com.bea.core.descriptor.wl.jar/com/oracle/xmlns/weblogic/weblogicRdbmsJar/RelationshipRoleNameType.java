package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface RelationshipRoleNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RelationshipRoleNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("relationshiprolenametype7d1ctype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RelationshipRoleNameType newInstance() {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().newInstance(RelationshipRoleNameType.type, (XmlOptions)null);
      }

      public static RelationshipRoleNameType newInstance(XmlOptions options) {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().newInstance(RelationshipRoleNameType.type, options);
      }

      public static RelationshipRoleNameType parse(String xmlAsString) throws XmlException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipRoleNameType.type, (XmlOptions)null);
      }

      public static RelationshipRoleNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipRoleNameType.type, options);
      }

      public static RelationshipRoleNameType parse(File file) throws XmlException, IOException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(file, RelationshipRoleNameType.type, (XmlOptions)null);
      }

      public static RelationshipRoleNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(file, RelationshipRoleNameType.type, options);
      }

      public static RelationshipRoleNameType parse(URL u) throws XmlException, IOException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(u, RelationshipRoleNameType.type, (XmlOptions)null);
      }

      public static RelationshipRoleNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(u, RelationshipRoleNameType.type, options);
      }

      public static RelationshipRoleNameType parse(InputStream is) throws XmlException, IOException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(is, RelationshipRoleNameType.type, (XmlOptions)null);
      }

      public static RelationshipRoleNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(is, RelationshipRoleNameType.type, options);
      }

      public static RelationshipRoleNameType parse(Reader r) throws XmlException, IOException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(r, RelationshipRoleNameType.type, (XmlOptions)null);
      }

      public static RelationshipRoleNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(r, RelationshipRoleNameType.type, options);
      }

      public static RelationshipRoleNameType parse(XMLStreamReader sr) throws XmlException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipRoleNameType.type, (XmlOptions)null);
      }

      public static RelationshipRoleNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipRoleNameType.type, options);
      }

      public static RelationshipRoleNameType parse(Node node) throws XmlException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(node, RelationshipRoleNameType.type, (XmlOptions)null);
      }

      public static RelationshipRoleNameType parse(Node node, XmlOptions options) throws XmlException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(node, RelationshipRoleNameType.type, options);
      }

      /** @deprecated */
      public static RelationshipRoleNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipRoleNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RelationshipRoleNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RelationshipRoleNameType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipRoleNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipRoleNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipRoleNameType.type, options);
      }

      private Factory() {
      }
   }
}
