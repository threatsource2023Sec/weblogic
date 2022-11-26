package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface ParentInstanceFactory extends JavaInstanceFactory {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ParentInstanceFactory.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("parentinstancefactory8180type");

   JavaMethodName getCreateObjectMethod();

   void setCreateObjectMethod(JavaMethodName var1);

   JavaMethodName addNewCreateObjectMethod();

   public static final class Factory {
      public static ParentInstanceFactory newInstance() {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().newInstance(ParentInstanceFactory.type, (XmlOptions)null);
      }

      public static ParentInstanceFactory newInstance(XmlOptions options) {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().newInstance(ParentInstanceFactory.type, options);
      }

      public static ParentInstanceFactory parse(String xmlAsString) throws XmlException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParentInstanceFactory.type, (XmlOptions)null);
      }

      public static ParentInstanceFactory parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParentInstanceFactory.type, options);
      }

      public static ParentInstanceFactory parse(File file) throws XmlException, IOException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(file, ParentInstanceFactory.type, (XmlOptions)null);
      }

      public static ParentInstanceFactory parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(file, ParentInstanceFactory.type, options);
      }

      public static ParentInstanceFactory parse(URL u) throws XmlException, IOException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(u, ParentInstanceFactory.type, (XmlOptions)null);
      }

      public static ParentInstanceFactory parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(u, ParentInstanceFactory.type, options);
      }

      public static ParentInstanceFactory parse(InputStream is) throws XmlException, IOException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(is, ParentInstanceFactory.type, (XmlOptions)null);
      }

      public static ParentInstanceFactory parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(is, ParentInstanceFactory.type, options);
      }

      public static ParentInstanceFactory parse(Reader r) throws XmlException, IOException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(r, ParentInstanceFactory.type, (XmlOptions)null);
      }

      public static ParentInstanceFactory parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(r, ParentInstanceFactory.type, options);
      }

      public static ParentInstanceFactory parse(XMLStreamReader sr) throws XmlException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(sr, ParentInstanceFactory.type, (XmlOptions)null);
      }

      public static ParentInstanceFactory parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(sr, ParentInstanceFactory.type, options);
      }

      public static ParentInstanceFactory parse(Node node) throws XmlException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(node, ParentInstanceFactory.type, (XmlOptions)null);
      }

      public static ParentInstanceFactory parse(Node node, XmlOptions options) throws XmlException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(node, ParentInstanceFactory.type, options);
      }

      /** @deprecated */
      public static ParentInstanceFactory parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(xis, ParentInstanceFactory.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ParentInstanceFactory parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ParentInstanceFactory)XmlBeans.getContextTypeLoader().parse(xis, ParentInstanceFactory.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParentInstanceFactory.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParentInstanceFactory.type, options);
      }

      private Factory() {
      }
   }
}
