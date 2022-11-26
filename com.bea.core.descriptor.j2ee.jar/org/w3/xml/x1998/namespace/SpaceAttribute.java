package org.w3.xml.x1998.namespace;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNCName;
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

public interface SpaceAttribute extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SpaceAttribute.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("space9344attrtypetype");

   Space.Enum getSpace();

   Space xgetSpace();

   boolean isSetSpace();

   void setSpace(Space.Enum var1);

   void xsetSpace(Space var1);

   void unsetSpace();

   public static final class Factory {
      public static SpaceAttribute newInstance() {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().newInstance(SpaceAttribute.type, (XmlOptions)null);
      }

      public static SpaceAttribute newInstance(XmlOptions options) {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().newInstance(SpaceAttribute.type, options);
      }

      public static SpaceAttribute parse(String xmlAsString) throws XmlException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(xmlAsString, SpaceAttribute.type, (XmlOptions)null);
      }

      public static SpaceAttribute parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(xmlAsString, SpaceAttribute.type, options);
      }

      public static SpaceAttribute parse(File file) throws XmlException, IOException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(file, SpaceAttribute.type, (XmlOptions)null);
      }

      public static SpaceAttribute parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(file, SpaceAttribute.type, options);
      }

      public static SpaceAttribute parse(URL u) throws XmlException, IOException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(u, SpaceAttribute.type, (XmlOptions)null);
      }

      public static SpaceAttribute parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(u, SpaceAttribute.type, options);
      }

      public static SpaceAttribute parse(InputStream is) throws XmlException, IOException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(is, SpaceAttribute.type, (XmlOptions)null);
      }

      public static SpaceAttribute parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(is, SpaceAttribute.type, options);
      }

      public static SpaceAttribute parse(Reader r) throws XmlException, IOException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(r, SpaceAttribute.type, (XmlOptions)null);
      }

      public static SpaceAttribute parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(r, SpaceAttribute.type, options);
      }

      public static SpaceAttribute parse(XMLStreamReader sr) throws XmlException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(sr, SpaceAttribute.type, (XmlOptions)null);
      }

      public static SpaceAttribute parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(sr, SpaceAttribute.type, options);
      }

      public static SpaceAttribute parse(Node node) throws XmlException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(node, SpaceAttribute.type, (XmlOptions)null);
      }

      public static SpaceAttribute parse(Node node, XmlOptions options) throws XmlException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(node, SpaceAttribute.type, options);
      }

      /** @deprecated */
      public static SpaceAttribute parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(xis, SpaceAttribute.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SpaceAttribute parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SpaceAttribute)XmlBeans.getContextTypeLoader().parse(xis, SpaceAttribute.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SpaceAttribute.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SpaceAttribute.type, options);
      }

      private Factory() {
      }
   }

   public interface Space extends XmlNCName {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Space.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("spaceb986attrtype");
      Enum DEFAULT = SpaceAttribute.Space.Enum.forString("default");
      Enum PRESERVE = SpaceAttribute.Space.Enum.forString("preserve");
      int INT_DEFAULT = 1;
      int INT_PRESERVE = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Space newValue(Object obj) {
            return (Space)SpaceAttribute.Space.type.newValue(obj);
         }

         public static Space newInstance() {
            return (Space)XmlBeans.getContextTypeLoader().newInstance(SpaceAttribute.Space.type, (XmlOptions)null);
         }

         public static Space newInstance(XmlOptions options) {
            return (Space)XmlBeans.getContextTypeLoader().newInstance(SpaceAttribute.Space.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_DEFAULT = 1;
         static final int INT_PRESERVE = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("default", 1), new Enum("preserve", 2)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }
}
