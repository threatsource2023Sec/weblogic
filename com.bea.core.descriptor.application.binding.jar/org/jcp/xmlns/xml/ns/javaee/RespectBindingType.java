package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface RespectBindingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RespectBindingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("respectbindingtype017ftype");

   TrueFalseType getEnabled();

   boolean isSetEnabled();

   void setEnabled(TrueFalseType var1);

   TrueFalseType addNewEnabled();

   void unsetEnabled();

   public static final class Factory {
      public static RespectBindingType newInstance() {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().newInstance(RespectBindingType.type, (XmlOptions)null);
      }

      public static RespectBindingType newInstance(XmlOptions options) {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().newInstance(RespectBindingType.type, options);
      }

      public static RespectBindingType parse(java.lang.String xmlAsString) throws XmlException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RespectBindingType.type, (XmlOptions)null);
      }

      public static RespectBindingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RespectBindingType.type, options);
      }

      public static RespectBindingType parse(File file) throws XmlException, IOException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(file, RespectBindingType.type, (XmlOptions)null);
      }

      public static RespectBindingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(file, RespectBindingType.type, options);
      }

      public static RespectBindingType parse(URL u) throws XmlException, IOException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(u, RespectBindingType.type, (XmlOptions)null);
      }

      public static RespectBindingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(u, RespectBindingType.type, options);
      }

      public static RespectBindingType parse(InputStream is) throws XmlException, IOException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(is, RespectBindingType.type, (XmlOptions)null);
      }

      public static RespectBindingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(is, RespectBindingType.type, options);
      }

      public static RespectBindingType parse(Reader r) throws XmlException, IOException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(r, RespectBindingType.type, (XmlOptions)null);
      }

      public static RespectBindingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(r, RespectBindingType.type, options);
      }

      public static RespectBindingType parse(XMLStreamReader sr) throws XmlException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(sr, RespectBindingType.type, (XmlOptions)null);
      }

      public static RespectBindingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(sr, RespectBindingType.type, options);
      }

      public static RespectBindingType parse(Node node) throws XmlException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(node, RespectBindingType.type, (XmlOptions)null);
      }

      public static RespectBindingType parse(Node node, XmlOptions options) throws XmlException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(node, RespectBindingType.type, options);
      }

      /** @deprecated */
      public static RespectBindingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(xis, RespectBindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RespectBindingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RespectBindingType)XmlBeans.getContextTypeLoader().parse(xis, RespectBindingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RespectBindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RespectBindingType.type, options);
      }

      private Factory() {
      }
   }
}
