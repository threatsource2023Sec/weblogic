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

public interface AdministeredObjectType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdministeredObjectType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("administeredobjecttype0438type");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   JndiNameType getName();

   void setName(JndiNameType var1);

   JndiNameType addNewName();

   FullyQualifiedClassType getInterfaceName();

   boolean isSetInterfaceName();

   void setInterfaceName(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewInterfaceName();

   void unsetInterfaceName();

   FullyQualifiedClassType getClassName();

   void setClassName(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewClassName();

   String getResourceAdapter();

   void setResourceAdapter(String var1);

   String addNewResourceAdapter();

   PropertyType[] getPropertyArray();

   PropertyType getPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(PropertyType[] var1);

   void setPropertyArray(int var1, PropertyType var2);

   PropertyType insertNewProperty(int var1);

   PropertyType addNewProperty();

   void removeProperty(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AdministeredObjectType newInstance() {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().newInstance(AdministeredObjectType.type, (XmlOptions)null);
      }

      public static AdministeredObjectType newInstance(XmlOptions options) {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().newInstance(AdministeredObjectType.type, options);
      }

      public static AdministeredObjectType parse(java.lang.String xmlAsString) throws XmlException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdministeredObjectType.type, (XmlOptions)null);
      }

      public static AdministeredObjectType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdministeredObjectType.type, options);
      }

      public static AdministeredObjectType parse(File file) throws XmlException, IOException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(file, AdministeredObjectType.type, (XmlOptions)null);
      }

      public static AdministeredObjectType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(file, AdministeredObjectType.type, options);
      }

      public static AdministeredObjectType parse(URL u) throws XmlException, IOException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(u, AdministeredObjectType.type, (XmlOptions)null);
      }

      public static AdministeredObjectType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(u, AdministeredObjectType.type, options);
      }

      public static AdministeredObjectType parse(InputStream is) throws XmlException, IOException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(is, AdministeredObjectType.type, (XmlOptions)null);
      }

      public static AdministeredObjectType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(is, AdministeredObjectType.type, options);
      }

      public static AdministeredObjectType parse(Reader r) throws XmlException, IOException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(r, AdministeredObjectType.type, (XmlOptions)null);
      }

      public static AdministeredObjectType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(r, AdministeredObjectType.type, options);
      }

      public static AdministeredObjectType parse(XMLStreamReader sr) throws XmlException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(sr, AdministeredObjectType.type, (XmlOptions)null);
      }

      public static AdministeredObjectType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(sr, AdministeredObjectType.type, options);
      }

      public static AdministeredObjectType parse(Node node) throws XmlException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(node, AdministeredObjectType.type, (XmlOptions)null);
      }

      public static AdministeredObjectType parse(Node node, XmlOptions options) throws XmlException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(node, AdministeredObjectType.type, options);
      }

      /** @deprecated */
      public static AdministeredObjectType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(xis, AdministeredObjectType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdministeredObjectType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdministeredObjectType)XmlBeans.getContextTypeLoader().parse(xis, AdministeredObjectType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdministeredObjectType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdministeredObjectType.type, options);
      }

      private Factory() {
      }
   }
}
