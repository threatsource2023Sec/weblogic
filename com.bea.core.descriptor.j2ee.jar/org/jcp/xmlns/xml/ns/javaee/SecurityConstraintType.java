package org.jcp.xmlns.xml.ns.javaee;

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

public interface SecurityConstraintType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityConstraintType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("securityconstrainttypef273type");

   DisplayNameType[] getDisplayNameArray();

   DisplayNameType getDisplayNameArray(int var1);

   int sizeOfDisplayNameArray();

   void setDisplayNameArray(DisplayNameType[] var1);

   void setDisplayNameArray(int var1, DisplayNameType var2);

   DisplayNameType insertNewDisplayName(int var1);

   DisplayNameType addNewDisplayName();

   void removeDisplayName(int var1);

   WebResourceCollectionType[] getWebResourceCollectionArray();

   WebResourceCollectionType getWebResourceCollectionArray(int var1);

   int sizeOfWebResourceCollectionArray();

   void setWebResourceCollectionArray(WebResourceCollectionType[] var1);

   void setWebResourceCollectionArray(int var1, WebResourceCollectionType var2);

   WebResourceCollectionType insertNewWebResourceCollection(int var1);

   WebResourceCollectionType addNewWebResourceCollection();

   void removeWebResourceCollection(int var1);

   AuthConstraintType getAuthConstraint();

   boolean isSetAuthConstraint();

   void setAuthConstraint(AuthConstraintType var1);

   AuthConstraintType addNewAuthConstraint();

   void unsetAuthConstraint();

   UserDataConstraintType getUserDataConstraint();

   boolean isSetUserDataConstraint();

   void setUserDataConstraint(UserDataConstraintType var1);

   UserDataConstraintType addNewUserDataConstraint();

   void unsetUserDataConstraint();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SecurityConstraintType newInstance() {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().newInstance(SecurityConstraintType.type, (XmlOptions)null);
      }

      public static SecurityConstraintType newInstance(XmlOptions options) {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().newInstance(SecurityConstraintType.type, options);
      }

      public static SecurityConstraintType parse(java.lang.String xmlAsString) throws XmlException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityConstraintType.type, (XmlOptions)null);
      }

      public static SecurityConstraintType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityConstraintType.type, options);
      }

      public static SecurityConstraintType parse(File file) throws XmlException, IOException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(file, SecurityConstraintType.type, (XmlOptions)null);
      }

      public static SecurityConstraintType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(file, SecurityConstraintType.type, options);
      }

      public static SecurityConstraintType parse(URL u) throws XmlException, IOException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(u, SecurityConstraintType.type, (XmlOptions)null);
      }

      public static SecurityConstraintType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(u, SecurityConstraintType.type, options);
      }

      public static SecurityConstraintType parse(InputStream is) throws XmlException, IOException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(is, SecurityConstraintType.type, (XmlOptions)null);
      }

      public static SecurityConstraintType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(is, SecurityConstraintType.type, options);
      }

      public static SecurityConstraintType parse(Reader r) throws XmlException, IOException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(r, SecurityConstraintType.type, (XmlOptions)null);
      }

      public static SecurityConstraintType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(r, SecurityConstraintType.type, options);
      }

      public static SecurityConstraintType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(sr, SecurityConstraintType.type, (XmlOptions)null);
      }

      public static SecurityConstraintType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(sr, SecurityConstraintType.type, options);
      }

      public static SecurityConstraintType parse(Node node) throws XmlException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(node, SecurityConstraintType.type, (XmlOptions)null);
      }

      public static SecurityConstraintType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(node, SecurityConstraintType.type, options);
      }

      /** @deprecated */
      public static SecurityConstraintType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(xis, SecurityConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityConstraintType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityConstraintType)XmlBeans.getContextTypeLoader().parse(xis, SecurityConstraintType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityConstraintType.type, options);
      }

      private Factory() {
      }
   }
}
