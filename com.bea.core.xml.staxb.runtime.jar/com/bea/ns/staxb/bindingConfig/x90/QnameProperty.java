package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlQName;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface QnameProperty extends BindingProperty {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(QnameProperty.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("qnameproperty52e3type");

   QName getQname();

   XmlQName xgetQname();

   void setQname(QName var1);

   void xsetQname(XmlQName var1);

   boolean getAttribute();

   XmlBoolean xgetAttribute();

   boolean isSetAttribute();

   void setAttribute(boolean var1);

   void xsetAttribute(XmlBoolean var1);

   void unsetAttribute();

   boolean getMultiple();

   XmlBoolean xgetMultiple();

   boolean isSetMultiple();

   void setMultiple(boolean var1);

   void xsetMultiple(XmlBoolean var1);

   void unsetMultiple();

   boolean getNillable();

   XmlBoolean xgetNillable();

   boolean isSetNillable();

   void setNillable(boolean var1);

   void xsetNillable(XmlBoolean var1);

   void unsetNillable();

   boolean getOptional();

   XmlBoolean xgetOptional();

   boolean isSetOptional();

   void setOptional(boolean var1);

   void xsetOptional(XmlBoolean var1);

   void unsetOptional();

   String getDefault();

   XmlString xgetDefault();

   boolean isSetDefault();

   void setDefault(String var1);

   void xsetDefault(XmlString var1);

   void unsetDefault();

   public static final class Factory {
      public static QnameProperty newInstance() {
         return (QnameProperty)XmlBeans.getContextTypeLoader().newInstance(QnameProperty.type, (XmlOptions)null);
      }

      public static QnameProperty newInstance(XmlOptions options) {
         return (QnameProperty)XmlBeans.getContextTypeLoader().newInstance(QnameProperty.type, options);
      }

      public static QnameProperty parse(String xmlAsString) throws XmlException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(xmlAsString, QnameProperty.type, (XmlOptions)null);
      }

      public static QnameProperty parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(xmlAsString, QnameProperty.type, options);
      }

      public static QnameProperty parse(File file) throws XmlException, IOException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(file, QnameProperty.type, (XmlOptions)null);
      }

      public static QnameProperty parse(File file, XmlOptions options) throws XmlException, IOException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(file, QnameProperty.type, options);
      }

      public static QnameProperty parse(URL u) throws XmlException, IOException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(u, QnameProperty.type, (XmlOptions)null);
      }

      public static QnameProperty parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(u, QnameProperty.type, options);
      }

      public static QnameProperty parse(InputStream is) throws XmlException, IOException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(is, QnameProperty.type, (XmlOptions)null);
      }

      public static QnameProperty parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(is, QnameProperty.type, options);
      }

      public static QnameProperty parse(Reader r) throws XmlException, IOException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(r, QnameProperty.type, (XmlOptions)null);
      }

      public static QnameProperty parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(r, QnameProperty.type, options);
      }

      public static QnameProperty parse(XMLStreamReader sr) throws XmlException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(sr, QnameProperty.type, (XmlOptions)null);
      }

      public static QnameProperty parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(sr, QnameProperty.type, options);
      }

      public static QnameProperty parse(Node node) throws XmlException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(node, QnameProperty.type, (XmlOptions)null);
      }

      public static QnameProperty parse(Node node, XmlOptions options) throws XmlException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(node, QnameProperty.type, options);
      }

      /** @deprecated */
      public static QnameProperty parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(xis, QnameProperty.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static QnameProperty parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (QnameProperty)XmlBeans.getContextTypeLoader().parse(xis, QnameProperty.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QnameProperty.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QnameProperty.type, options);
      }

      private Factory() {
      }
   }
}
