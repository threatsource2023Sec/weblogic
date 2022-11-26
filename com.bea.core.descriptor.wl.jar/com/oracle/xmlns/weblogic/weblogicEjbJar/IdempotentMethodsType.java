package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface IdempotentMethodsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IdempotentMethodsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("idempotentmethodstype9494type");

   MethodType[] getMethodArray();

   MethodType getMethodArray(int var1);

   int sizeOfMethodArray();

   void setMethodArray(MethodType[] var1);

   void setMethodArray(int var1, MethodType var2);

   MethodType insertNewMethod(int var1);

   MethodType addNewMethod();

   void removeMethod(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static IdempotentMethodsType newInstance() {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().newInstance(IdempotentMethodsType.type, (XmlOptions)null);
      }

      public static IdempotentMethodsType newInstance(XmlOptions options) {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().newInstance(IdempotentMethodsType.type, options);
      }

      public static IdempotentMethodsType parse(String xmlAsString) throws XmlException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IdempotentMethodsType.type, (XmlOptions)null);
      }

      public static IdempotentMethodsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IdempotentMethodsType.type, options);
      }

      public static IdempotentMethodsType parse(File file) throws XmlException, IOException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(file, IdempotentMethodsType.type, (XmlOptions)null);
      }

      public static IdempotentMethodsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(file, IdempotentMethodsType.type, options);
      }

      public static IdempotentMethodsType parse(URL u) throws XmlException, IOException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(u, IdempotentMethodsType.type, (XmlOptions)null);
      }

      public static IdempotentMethodsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(u, IdempotentMethodsType.type, options);
      }

      public static IdempotentMethodsType parse(InputStream is) throws XmlException, IOException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(is, IdempotentMethodsType.type, (XmlOptions)null);
      }

      public static IdempotentMethodsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(is, IdempotentMethodsType.type, options);
      }

      public static IdempotentMethodsType parse(Reader r) throws XmlException, IOException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(r, IdempotentMethodsType.type, (XmlOptions)null);
      }

      public static IdempotentMethodsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(r, IdempotentMethodsType.type, options);
      }

      public static IdempotentMethodsType parse(XMLStreamReader sr) throws XmlException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(sr, IdempotentMethodsType.type, (XmlOptions)null);
      }

      public static IdempotentMethodsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(sr, IdempotentMethodsType.type, options);
      }

      public static IdempotentMethodsType parse(Node node) throws XmlException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(node, IdempotentMethodsType.type, (XmlOptions)null);
      }

      public static IdempotentMethodsType parse(Node node, XmlOptions options) throws XmlException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(node, IdempotentMethodsType.type, options);
      }

      /** @deprecated */
      public static IdempotentMethodsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(xis, IdempotentMethodsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IdempotentMethodsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IdempotentMethodsType)XmlBeans.getContextTypeLoader().parse(xis, IdempotentMethodsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IdempotentMethodsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IdempotentMethodsType.type, options);
      }

      private Factory() {
      }
   }
}
