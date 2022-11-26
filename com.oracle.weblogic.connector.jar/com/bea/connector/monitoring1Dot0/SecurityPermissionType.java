package com.bea.connector.monitoring1Dot0;

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

public interface SecurityPermissionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityPermissionType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("securitypermissiontype6adatype");

   String[] getDescriptionArray();

   String getDescriptionArray(int var1);

   XmlString[] xgetDescriptionArray();

   XmlString xgetDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(String[] var1);

   void setDescriptionArray(int var1, String var2);

   void xsetDescriptionArray(XmlString[] var1);

   void xsetDescriptionArray(int var1, XmlString var2);

   void insertDescription(int var1, String var2);

   void addDescription(String var1);

   XmlString insertNewDescription(int var1);

   XmlString addNewDescription();

   void removeDescription(int var1);

   String getSecurityPermissionSpec();

   XmlString xgetSecurityPermissionSpec();

   void setSecurityPermissionSpec(String var1);

   void xsetSecurityPermissionSpec(XmlString var1);

   public static final class Factory {
      public static SecurityPermissionType newInstance() {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().newInstance(SecurityPermissionType.type, (XmlOptions)null);
      }

      public static SecurityPermissionType newInstance(XmlOptions options) {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().newInstance(SecurityPermissionType.type, options);
      }

      public static SecurityPermissionType parse(String xmlAsString) throws XmlException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPermissionType.type, (XmlOptions)null);
      }

      public static SecurityPermissionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPermissionType.type, options);
      }

      public static SecurityPermissionType parse(File file) throws XmlException, IOException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(file, SecurityPermissionType.type, (XmlOptions)null);
      }

      public static SecurityPermissionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(file, SecurityPermissionType.type, options);
      }

      public static SecurityPermissionType parse(URL u) throws XmlException, IOException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(u, SecurityPermissionType.type, (XmlOptions)null);
      }

      public static SecurityPermissionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(u, SecurityPermissionType.type, options);
      }

      public static SecurityPermissionType parse(InputStream is) throws XmlException, IOException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(is, SecurityPermissionType.type, (XmlOptions)null);
      }

      public static SecurityPermissionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(is, SecurityPermissionType.type, options);
      }

      public static SecurityPermissionType parse(Reader r) throws XmlException, IOException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(r, SecurityPermissionType.type, (XmlOptions)null);
      }

      public static SecurityPermissionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(r, SecurityPermissionType.type, options);
      }

      public static SecurityPermissionType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(sr, SecurityPermissionType.type, (XmlOptions)null);
      }

      public static SecurityPermissionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(sr, SecurityPermissionType.type, options);
      }

      public static SecurityPermissionType parse(Node node) throws XmlException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(node, SecurityPermissionType.type, (XmlOptions)null);
      }

      public static SecurityPermissionType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(node, SecurityPermissionType.type, options);
      }

      /** @deprecated */
      public static SecurityPermissionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(xis, SecurityPermissionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityPermissionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityPermissionType)XmlBeans.getContextTypeLoader().parse(xis, SecurityPermissionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPermissionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPermissionType.type, options);
      }

      private Factory() {
      }
   }
}
