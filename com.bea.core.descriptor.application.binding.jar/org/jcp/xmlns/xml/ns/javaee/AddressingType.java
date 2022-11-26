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

public interface AddressingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AddressingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("addressingtypec89dtype");

   TrueFalseType getEnabled();

   boolean isSetEnabled();

   void setEnabled(TrueFalseType var1);

   TrueFalseType addNewEnabled();

   void unsetEnabled();

   TrueFalseType getRequired();

   boolean isSetRequired();

   void setRequired(TrueFalseType var1);

   TrueFalseType addNewRequired();

   void unsetRequired();

   AddressingResponsesType getResponses();

   boolean isSetResponses();

   void setResponses(AddressingResponsesType var1);

   AddressingResponsesType addNewResponses();

   void unsetResponses();

   public static final class Factory {
      public static AddressingType newInstance() {
         return (AddressingType)XmlBeans.getContextTypeLoader().newInstance(AddressingType.type, (XmlOptions)null);
      }

      public static AddressingType newInstance(XmlOptions options) {
         return (AddressingType)XmlBeans.getContextTypeLoader().newInstance(AddressingType.type, options);
      }

      public static AddressingType parse(java.lang.String xmlAsString) throws XmlException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AddressingType.type, (XmlOptions)null);
      }

      public static AddressingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AddressingType.type, options);
      }

      public static AddressingType parse(File file) throws XmlException, IOException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(file, AddressingType.type, (XmlOptions)null);
      }

      public static AddressingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(file, AddressingType.type, options);
      }

      public static AddressingType parse(URL u) throws XmlException, IOException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(u, AddressingType.type, (XmlOptions)null);
      }

      public static AddressingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(u, AddressingType.type, options);
      }

      public static AddressingType parse(InputStream is) throws XmlException, IOException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(is, AddressingType.type, (XmlOptions)null);
      }

      public static AddressingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(is, AddressingType.type, options);
      }

      public static AddressingType parse(Reader r) throws XmlException, IOException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(r, AddressingType.type, (XmlOptions)null);
      }

      public static AddressingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(r, AddressingType.type, options);
      }

      public static AddressingType parse(XMLStreamReader sr) throws XmlException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(sr, AddressingType.type, (XmlOptions)null);
      }

      public static AddressingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(sr, AddressingType.type, options);
      }

      public static AddressingType parse(Node node) throws XmlException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(node, AddressingType.type, (XmlOptions)null);
      }

      public static AddressingType parse(Node node, XmlOptions options) throws XmlException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(node, AddressingType.type, options);
      }

      /** @deprecated */
      public static AddressingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(xis, AddressingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AddressingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AddressingType)XmlBeans.getContextTypeLoader().parse(xis, AddressingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AddressingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AddressingType.type, options);
      }

      private Factory() {
      }
   }
}
