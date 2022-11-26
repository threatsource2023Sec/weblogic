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

public interface ServletType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServletType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("servlettypeebd8type");

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

   ServletNameType getServletName();

   void setServletName(ServletNameType var1);

   ServletNameType addNewServletName();

   FullyQualifiedClassType getServletClass();

   boolean isSetServletClass();

   void setServletClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewServletClass();

   void unsetServletClass();

   JspFileType getJspFile();

   boolean isSetJspFile();

   void setJspFile(JspFileType var1);

   JspFileType addNewJspFile();

   void unsetJspFile();

   ParamValueType[] getInitParamArray();

   ParamValueType getInitParamArray(int var1);

   int sizeOfInitParamArray();

   void setInitParamArray(ParamValueType[] var1);

   void setInitParamArray(int var1, ParamValueType var2);

   ParamValueType insertNewInitParam(int var1);

   ParamValueType addNewInitParam();

   void removeInitParam(int var1);

   Object getLoadOnStartup();

   LoadOnStartupType xgetLoadOnStartup();

   boolean isSetLoadOnStartup();

   void setLoadOnStartup(Object var1);

   void xsetLoadOnStartup(LoadOnStartupType var1);

   void unsetLoadOnStartup();

   TrueFalseType getEnabled();

   boolean isSetEnabled();

   void setEnabled(TrueFalseType var1);

   TrueFalseType addNewEnabled();

   void unsetEnabled();

   TrueFalseType getAsyncSupported();

   boolean isSetAsyncSupported();

   void setAsyncSupported(TrueFalseType var1);

   TrueFalseType addNewAsyncSupported();

   void unsetAsyncSupported();

   RunAsType getRunAs();

   boolean isSetRunAs();

   void setRunAs(RunAsType var1);

   RunAsType addNewRunAs();

   void unsetRunAs();

   SecurityRoleRefType[] getSecurityRoleRefArray();

   SecurityRoleRefType getSecurityRoleRefArray(int var1);

   int sizeOfSecurityRoleRefArray();

   void setSecurityRoleRefArray(SecurityRoleRefType[] var1);

   void setSecurityRoleRefArray(int var1, SecurityRoleRefType var2);

   SecurityRoleRefType insertNewSecurityRoleRef(int var1);

   SecurityRoleRefType addNewSecurityRoleRef();

   void removeSecurityRoleRef(int var1);

   MultipartConfigType getMultipartConfig();

   boolean isSetMultipartConfig();

   void setMultipartConfig(MultipartConfigType var1);

   MultipartConfigType addNewMultipartConfig();

   void unsetMultipartConfig();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ServletType newInstance() {
         return (ServletType)XmlBeans.getContextTypeLoader().newInstance(ServletType.type, (XmlOptions)null);
      }

      public static ServletType newInstance(XmlOptions options) {
         return (ServletType)XmlBeans.getContextTypeLoader().newInstance(ServletType.type, options);
      }

      public static ServletType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletType.type, (XmlOptions)null);
      }

      public static ServletType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletType.type, options);
      }

      public static ServletType parse(File file) throws XmlException, IOException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(file, ServletType.type, (XmlOptions)null);
      }

      public static ServletType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(file, ServletType.type, options);
      }

      public static ServletType parse(URL u) throws XmlException, IOException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(u, ServletType.type, (XmlOptions)null);
      }

      public static ServletType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(u, ServletType.type, options);
      }

      public static ServletType parse(InputStream is) throws XmlException, IOException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(is, ServletType.type, (XmlOptions)null);
      }

      public static ServletType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(is, ServletType.type, options);
      }

      public static ServletType parse(Reader r) throws XmlException, IOException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(r, ServletType.type, (XmlOptions)null);
      }

      public static ServletType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(r, ServletType.type, options);
      }

      public static ServletType parse(XMLStreamReader sr) throws XmlException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(sr, ServletType.type, (XmlOptions)null);
      }

      public static ServletType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(sr, ServletType.type, options);
      }

      public static ServletType parse(Node node) throws XmlException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(node, ServletType.type, (XmlOptions)null);
      }

      public static ServletType parse(Node node, XmlOptions options) throws XmlException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(node, ServletType.type, options);
      }

      /** @deprecated */
      public static ServletType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(xis, ServletType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServletType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServletType)XmlBeans.getContextTypeLoader().parse(xis, ServletType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletType.type, options);
      }

      private Factory() {
      }
   }
}
