package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface BindingProperty extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BindingProperty.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("bindingpropertyd27atype");

   String getXmlcomponent();

   XmlSignature xgetXmlcomponent();

   void setXmlcomponent(String var1);

   void xsetXmlcomponent(XmlSignature var1);

   String getJavatype();

   JavaClassName xgetJavatype();

   void setJavatype(String var1);

   void xsetJavatype(JavaClassName var1);

   JavaMethodName getGetter();

   boolean isSetGetter();

   void setGetter(JavaMethodName var1);

   JavaMethodName addNewGetter();

   void unsetGetter();

   JavaMethodName getSetter();

   boolean isSetSetter();

   void setSetter(JavaMethodName var1);

   JavaMethodName addNewSetter();

   void unsetSetter();

   JavaMethodName getIssetter();

   boolean isSetIssetter();

   void setIssetter(JavaMethodName var1);

   JavaMethodName addNewIssetter();

   void unsetIssetter();

   String getField();

   JavaFieldName xgetField();

   boolean isSetField();

   void setField(String var1);

   void xsetField(JavaFieldName var1);

   void unsetField();

   String getStatic();

   JavaFieldName xgetStatic();

   boolean isSetStatic();

   void setStatic(String var1);

   void xsetStatic(JavaFieldName var1);

   void unsetStatic();

   String getCollection();

   JavaClassName xgetCollection();

   boolean isSetCollection();

   void setCollection(String var1);

   void xsetCollection(JavaClassName var1);

   void unsetCollection();

   JavaInstanceFactory getFactory();

   boolean isSetFactory();

   void setFactory(JavaInstanceFactory var1);

   JavaInstanceFactory addNewFactory();

   void unsetFactory();

   int getConstructorParameterIndex();

   ConstructorParameterIndex xgetConstructorParameterIndex();

   boolean isSetConstructorParameterIndex();

   void setConstructorParameterIndex(int var1);

   void xsetConstructorParameterIndex(ConstructorParameterIndex var1);

   void unsetConstructorParameterIndex();

   public static final class Factory {
      /** @deprecated */
      public static BindingProperty newInstance() {
         return (BindingProperty)XmlBeans.getContextTypeLoader().newInstance(BindingProperty.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BindingProperty newInstance(XmlOptions options) {
         return (BindingProperty)XmlBeans.getContextTypeLoader().newInstance(BindingProperty.type, options);
      }

      public static BindingProperty parse(String xmlAsString) throws XmlException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(xmlAsString, BindingProperty.type, (XmlOptions)null);
      }

      public static BindingProperty parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(xmlAsString, BindingProperty.type, options);
      }

      public static BindingProperty parse(File file) throws XmlException, IOException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(file, BindingProperty.type, (XmlOptions)null);
      }

      public static BindingProperty parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(file, BindingProperty.type, options);
      }

      public static BindingProperty parse(URL u) throws XmlException, IOException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(u, BindingProperty.type, (XmlOptions)null);
      }

      public static BindingProperty parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(u, BindingProperty.type, options);
      }

      public static BindingProperty parse(InputStream is) throws XmlException, IOException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(is, BindingProperty.type, (XmlOptions)null);
      }

      public static BindingProperty parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(is, BindingProperty.type, options);
      }

      public static BindingProperty parse(Reader r) throws XmlException, IOException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(r, BindingProperty.type, (XmlOptions)null);
      }

      public static BindingProperty parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(r, BindingProperty.type, options);
      }

      public static BindingProperty parse(XMLStreamReader sr) throws XmlException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(sr, BindingProperty.type, (XmlOptions)null);
      }

      public static BindingProperty parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(sr, BindingProperty.type, options);
      }

      public static BindingProperty parse(Node node) throws XmlException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(node, BindingProperty.type, (XmlOptions)null);
      }

      public static BindingProperty parse(Node node, XmlOptions options) throws XmlException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(node, BindingProperty.type, options);
      }

      /** @deprecated */
      public static BindingProperty parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(xis, BindingProperty.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BindingProperty parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BindingProperty)XmlBeans.getContextTypeLoader().parse(xis, BindingProperty.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BindingProperty.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BindingProperty.type, options);
      }

      private Factory() {
      }
   }

   public interface ConstructorParameterIndex extends XmlInt {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConstructorParameterIndex.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("constructorparameterindex0913elemtype");

      public static final class Factory {
         public static ConstructorParameterIndex newValue(Object obj) {
            return (ConstructorParameterIndex)BindingProperty.ConstructorParameterIndex.type.newValue(obj);
         }

         public static ConstructorParameterIndex newInstance() {
            return (ConstructorParameterIndex)XmlBeans.getContextTypeLoader().newInstance(BindingProperty.ConstructorParameterIndex.type, (XmlOptions)null);
         }

         public static ConstructorParameterIndex newInstance(XmlOptions options) {
            return (ConstructorParameterIndex)XmlBeans.getContextTypeLoader().newInstance(BindingProperty.ConstructorParameterIndex.type, options);
         }

         private Factory() {
         }
      }
   }
}
