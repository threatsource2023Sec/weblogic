package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PropertyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PropertyType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("propertytype82b8type");

   Property[] getPropertyArray();

   Property getPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(Property[] var1);

   void setPropertyArray(int var1, Property var2);

   Property insertNewProperty(int var1);

   Property addNewProperty();

   void removeProperty(int var1);

   public static final class Factory {
      public static PropertyType newInstance() {
         return (PropertyType)XmlBeans.getContextTypeLoader().newInstance(PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType newInstance(XmlOptions options) {
         return (PropertyType)XmlBeans.getContextTypeLoader().newInstance(PropertyType.type, options);
      }

      public static PropertyType parse(String xmlAsString) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertyType.type, options);
      }

      public static PropertyType parse(File file) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(file, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(file, PropertyType.type, options);
      }

      public static PropertyType parse(URL u) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(u, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(u, PropertyType.type, options);
      }

      public static PropertyType parse(InputStream is) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(is, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(is, PropertyType.type, options);
      }

      public static PropertyType parse(Reader r) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(r, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(r, PropertyType.type, options);
      }

      public static PropertyType parse(XMLStreamReader sr) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(sr, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(sr, PropertyType.type, options);
      }

      public static PropertyType parse(Node node) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(node, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(Node node, XmlOptions options) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(node, PropertyType.type, options);
      }

      /** @deprecated */
      public static PropertyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(xis, PropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PropertyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(xis, PropertyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertyType.type, options);
      }

      private Factory() {
      }
   }

   public interface Property extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Property.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("propertyf2e9elemtype");

      String getString();

      StringType xgetString();

      boolean isSetString();

      void setString(String var1);

      void xsetString(StringType var1);

      void unsetString();

      long getLong();

      LongType xgetLong();

      boolean isSetLong();

      void setLong(long var1);

      void xsetLong(LongType var1);

      void unsetLong();

      short getShort();

      ShortType xgetShort();

      boolean isSetShort();

      void setShort(short var1);

      void xsetShort(ShortType var1);

      void unsetShort();

      BigInteger getInt();

      IntType xgetInt();

      boolean isSetInt();

      void setInt(BigInteger var1);

      void xsetInt(IntType var1);

      void unsetInt();

      float getFloat();

      FloatType xgetFloat();

      boolean isSetFloat();

      void setFloat(float var1);

      void xsetFloat(FloatType var1);

      void unsetFloat();

      double getDouble();

      DoubleType xgetDouble();

      boolean isSetDouble();

      void setDouble(double var1);

      void xsetDouble(DoubleType var1);

      void unsetDouble();

      byte getByte();

      ByteType xgetByte();

      boolean isSetByte();

      void setByte(byte var1);

      void xsetByte(ByteType var1);

      void unsetByte();

      boolean getBoolean();

      BooleanType xgetBoolean();

      boolean isSetBoolean();

      void setBoolean(boolean var1);

      void xsetBoolean(BooleanType var1);

      void unsetBoolean();

      String getName();

      XmlString xgetName();

      void setName(String var1);

      void xsetName(XmlString var1);

      public static final class Factory {
         public static Property newInstance() {
            return (Property)XmlBeans.getContextTypeLoader().newInstance(PropertyType.Property.type, (XmlOptions)null);
         }

         public static Property newInstance(XmlOptions options) {
            return (Property)XmlBeans.getContextTypeLoader().newInstance(PropertyType.Property.type, options);
         }

         private Factory() {
         }
      }
   }
}
