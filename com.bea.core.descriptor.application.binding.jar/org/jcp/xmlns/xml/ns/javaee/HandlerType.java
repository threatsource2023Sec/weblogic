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

public interface HandlerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HandlerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("handlertype7b33type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   DisplayNameType[] getDisplayNameArray();

   DisplayNameType getDisplayNameArray(int var1);

   int sizeOfDisplayNameArray();

   void setDisplayNameArray(DisplayNameType[] var1);

   void setDisplayNameArray(int var1, DisplayNameType var2);

   DisplayNameType insertNewDisplayName(int var1);

   DisplayNameType addNewDisplayName();

   void removeDisplayName(int var1);

   IconType[] getIconArray();

   IconType getIconArray(int var1);

   int sizeOfIconArray();

   void setIconArray(IconType[] var1);

   void setIconArray(int var1, IconType var2);

   IconType insertNewIcon(int var1);

   IconType addNewIcon();

   void removeIcon(int var1);

   String getHandlerName();

   void setHandlerName(String var1);

   String addNewHandlerName();

   FullyQualifiedClassType getHandlerClass();

   void setHandlerClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewHandlerClass();

   ParamValueType[] getInitParamArray();

   ParamValueType getInitParamArray(int var1);

   int sizeOfInitParamArray();

   void setInitParamArray(ParamValueType[] var1);

   void setInitParamArray(int var1, ParamValueType var2);

   ParamValueType insertNewInitParam(int var1);

   ParamValueType addNewInitParam();

   void removeInitParam(int var1);

   XsdQNameType[] getSoapHeaderArray();

   XsdQNameType getSoapHeaderArray(int var1);

   int sizeOfSoapHeaderArray();

   void setSoapHeaderArray(XsdQNameType[] var1);

   void setSoapHeaderArray(int var1, XsdQNameType var2);

   XsdQNameType insertNewSoapHeader(int var1);

   XsdQNameType addNewSoapHeader();

   void removeSoapHeader(int var1);

   String[] getSoapRoleArray();

   String getSoapRoleArray(int var1);

   int sizeOfSoapRoleArray();

   void setSoapRoleArray(String[] var1);

   void setSoapRoleArray(int var1, String var2);

   String insertNewSoapRole(int var1);

   String addNewSoapRole();

   void removeSoapRole(int var1);

   String[] getPortNameArray();

   String getPortNameArray(int var1);

   int sizeOfPortNameArray();

   void setPortNameArray(String[] var1);

   void setPortNameArray(int var1, String var2);

   String insertNewPortName(int var1);

   String addNewPortName();

   void removePortName(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static HandlerType newInstance() {
         return (HandlerType)XmlBeans.getContextTypeLoader().newInstance(HandlerType.type, (XmlOptions)null);
      }

      public static HandlerType newInstance(XmlOptions options) {
         return (HandlerType)XmlBeans.getContextTypeLoader().newInstance(HandlerType.type, options);
      }

      public static HandlerType parse(java.lang.String xmlAsString) throws XmlException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HandlerType.type, (XmlOptions)null);
      }

      public static HandlerType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HandlerType.type, options);
      }

      public static HandlerType parse(File file) throws XmlException, IOException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(file, HandlerType.type, (XmlOptions)null);
      }

      public static HandlerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(file, HandlerType.type, options);
      }

      public static HandlerType parse(URL u) throws XmlException, IOException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(u, HandlerType.type, (XmlOptions)null);
      }

      public static HandlerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(u, HandlerType.type, options);
      }

      public static HandlerType parse(InputStream is) throws XmlException, IOException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(is, HandlerType.type, (XmlOptions)null);
      }

      public static HandlerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(is, HandlerType.type, options);
      }

      public static HandlerType parse(Reader r) throws XmlException, IOException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(r, HandlerType.type, (XmlOptions)null);
      }

      public static HandlerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(r, HandlerType.type, options);
      }

      public static HandlerType parse(XMLStreamReader sr) throws XmlException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(sr, HandlerType.type, (XmlOptions)null);
      }

      public static HandlerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(sr, HandlerType.type, options);
      }

      public static HandlerType parse(Node node) throws XmlException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(node, HandlerType.type, (XmlOptions)null);
      }

      public static HandlerType parse(Node node, XmlOptions options) throws XmlException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(node, HandlerType.type, options);
      }

      /** @deprecated */
      public static HandlerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(xis, HandlerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HandlerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HandlerType)XmlBeans.getContextTypeLoader().parse(xis, HandlerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HandlerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HandlerType.type, options);
      }

      private Factory() {
      }
   }
}
