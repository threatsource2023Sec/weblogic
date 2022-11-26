package com.sun.java.xml.ns.javaee;

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

public interface ResourceEnvRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceEnvRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("resourceenvreftypebfa1type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   JndiNameType getResourceEnvRefName();

   void setResourceEnvRefName(JndiNameType var1);

   JndiNameType addNewResourceEnvRefName();

   FullyQualifiedClassType getResourceEnvRefType();

   boolean isSetResourceEnvRefType();

   void setResourceEnvRefType(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewResourceEnvRefType();

   void unsetResourceEnvRefType();

   XsdStringType getMappedName();

   boolean isSetMappedName();

   void setMappedName(XsdStringType var1);

   XsdStringType addNewMappedName();

   void unsetMappedName();

   InjectionTargetType[] getInjectionTargetArray();

   InjectionTargetType getInjectionTargetArray(int var1);

   int sizeOfInjectionTargetArray();

   void setInjectionTargetArray(InjectionTargetType[] var1);

   void setInjectionTargetArray(int var1, InjectionTargetType var2);

   InjectionTargetType insertNewInjectionTarget(int var1);

   InjectionTargetType addNewInjectionTarget();

   void removeInjectionTarget(int var1);

   XsdStringType getLookupName();

   boolean isSetLookupName();

   void setLookupName(XsdStringType var1);

   XsdStringType addNewLookupName();

   void unsetLookupName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResourceEnvRefType newInstance() {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().newInstance(ResourceEnvRefType.type, (XmlOptions)null);
      }

      public static ResourceEnvRefType newInstance(XmlOptions options) {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().newInstance(ResourceEnvRefType.type, options);
      }

      public static ResourceEnvRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceEnvRefType.type, (XmlOptions)null);
      }

      public static ResourceEnvRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceEnvRefType.type, options);
      }

      public static ResourceEnvRefType parse(File file) throws XmlException, IOException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(file, ResourceEnvRefType.type, (XmlOptions)null);
      }

      public static ResourceEnvRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(file, ResourceEnvRefType.type, options);
      }

      public static ResourceEnvRefType parse(URL u) throws XmlException, IOException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(u, ResourceEnvRefType.type, (XmlOptions)null);
      }

      public static ResourceEnvRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(u, ResourceEnvRefType.type, options);
      }

      public static ResourceEnvRefType parse(InputStream is) throws XmlException, IOException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(is, ResourceEnvRefType.type, (XmlOptions)null);
      }

      public static ResourceEnvRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(is, ResourceEnvRefType.type, options);
      }

      public static ResourceEnvRefType parse(Reader r) throws XmlException, IOException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(r, ResourceEnvRefType.type, (XmlOptions)null);
      }

      public static ResourceEnvRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(r, ResourceEnvRefType.type, options);
      }

      public static ResourceEnvRefType parse(XMLStreamReader sr) throws XmlException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(sr, ResourceEnvRefType.type, (XmlOptions)null);
      }

      public static ResourceEnvRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(sr, ResourceEnvRefType.type, options);
      }

      public static ResourceEnvRefType parse(Node node) throws XmlException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(node, ResourceEnvRefType.type, (XmlOptions)null);
      }

      public static ResourceEnvRefType parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(node, ResourceEnvRefType.type, options);
      }

      /** @deprecated */
      public static ResourceEnvRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(xis, ResourceEnvRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceEnvRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceEnvRefType)XmlBeans.getContextTypeLoader().parse(xis, ResourceEnvRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceEnvRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceEnvRefType.type, options);
      }

      private Factory() {
      }
   }
}
