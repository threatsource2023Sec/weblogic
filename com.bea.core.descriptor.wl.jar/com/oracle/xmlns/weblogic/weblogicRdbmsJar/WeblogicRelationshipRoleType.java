package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.EmptyType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicRelationshipRoleType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicRelationshipRoleType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicrelationshiproletype03d7type");

   RelationshipRoleNameType getRelationshipRoleName();

   void setRelationshipRoleName(RelationshipRoleNameType var1);

   RelationshipRoleNameType addNewRelationshipRoleName();

   GroupNameType getGroupName();

   boolean isSetGroupName();

   void setGroupName(GroupNameType var1);

   GroupNameType addNewGroupName();

   void unsetGroupName();

   RelationshipRoleMapType getRelationshipRoleMap();

   boolean isSetRelationshipRoleMap();

   void setRelationshipRoleMap(RelationshipRoleMapType var1);

   RelationshipRoleMapType addNewRelationshipRoleMap();

   void unsetRelationshipRoleMap();

   EmptyType getDbCascadeDelete();

   boolean isSetDbCascadeDelete();

   void setDbCascadeDelete(EmptyType var1);

   EmptyType addNewDbCascadeDelete();

   void unsetDbCascadeDelete();

   TrueFalseType getEnableQueryCaching();

   boolean isSetEnableQueryCaching();

   void setEnableQueryCaching(TrueFalseType var1);

   TrueFalseType addNewEnableQueryCaching();

   void unsetEnableQueryCaching();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WeblogicRelationshipRoleType newInstance() {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      public static WeblogicRelationshipRoleType newInstance(XmlOptions options) {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRelationshipRoleType.type, options);
      }

      public static WeblogicRelationshipRoleType parse(String xmlAsString) throws XmlException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      public static WeblogicRelationshipRoleType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRelationshipRoleType.type, options);
      }

      public static WeblogicRelationshipRoleType parse(File file) throws XmlException, IOException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      public static WeblogicRelationshipRoleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRelationshipRoleType.type, options);
      }

      public static WeblogicRelationshipRoleType parse(URL u) throws XmlException, IOException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      public static WeblogicRelationshipRoleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRelationshipRoleType.type, options);
      }

      public static WeblogicRelationshipRoleType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      public static WeblogicRelationshipRoleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRelationshipRoleType.type, options);
      }

      public static WeblogicRelationshipRoleType parse(Reader r) throws XmlException, IOException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      public static WeblogicRelationshipRoleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRelationshipRoleType.type, options);
      }

      public static WeblogicRelationshipRoleType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      public static WeblogicRelationshipRoleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRelationshipRoleType.type, options);
      }

      public static WeblogicRelationshipRoleType parse(Node node) throws XmlException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      public static WeblogicRelationshipRoleType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRelationshipRoleType.type, options);
      }

      /** @deprecated */
      public static WeblogicRelationshipRoleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicRelationshipRoleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicRelationshipRoleType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRelationshipRoleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRelationshipRoleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRelationshipRoleType.type, options);
      }

      private Factory() {
      }
   }
}
