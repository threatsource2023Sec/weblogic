package com.bea.xbeanmarshal.buildtime.internal.bts;

import javax.xml.namespace.QName;

public abstract class BuiltinBindingLoader extends BaseBindingLoader {
   private static final String XSNS = "http://www.w3.org/2001/XMLSchema";
   private static final String SOAPENC = "http://schemas.xmlsoap.org/soap/encoding/";

   public static BuiltinBindingLoader getInstance() {
      return getBuiltinBindingLoader();
   }

   public static BuiltinBindingLoader getBuiltinBindingLoader() {
      return XmlBeansBuiltinBindingLoader.getInstance();
   }

   private void addMapping(String xmlType, String javaName, boolean fromJavaDefault, boolean fromXmlDefault) {
      QName xml_name = new QName("http://www.w3.org/2001/XMLSchema".intern(), xmlType != null ? xmlType.intern() : null);
      this.addMapping(xml_name, javaName != null ? javaName.intern() : null, fromJavaDefault, fromXmlDefault);
   }

   private void addSoapMapping(String xmlType, String javaName, boolean fromJavaDefault, boolean fromXmlDefault) {
      QName xml_name = new QName("http://schemas.xmlsoap.org/soap/encoding/", xmlType);
      this.addMapping(xml_name, javaName != null ? javaName.intern() : null, fromJavaDefault, fromXmlDefault);
   }

   private void addMapping(QName xml_name, String javaName, boolean fromJavaDefault, boolean fromXmlDefault) {
      XmlTypeName xn = XmlTypeName.forTypeNamed(xml_name);
      JavaTypeName jn = JavaTypeName.forString(javaName);
      BindingTypeName btName = BindingTypeName.forPair(jn, xn);
      BindingType bType = new XmlBeanBuiltinType(btName);
      this.addBindingType(bType);
      if (fromJavaDefault) {
         if (bType.getName().getXmlName().getComponentType() == 101) {
            this.addElementFor(bType.getName().getJavaName(), bType.getName());
         } else {
            this.addTypeFor(bType.getName().getJavaName(), bType.getName());
         }
      }

      if (fromXmlDefault) {
         this.addPojoFor(bType.getName().getXmlName(), bType.getName());
      }

   }

   protected void addPojoTwoWay(String xmlType, String javaName) {
      this.addMapping(xmlType, javaName, true, true);
   }

   protected void addPojoXml(String xmlType, String javaName) {
      this.addMapping(xmlType, javaName, false, true);
   }

   protected void addPojoJava(String xmlType, String javaName) {
      this.addMapping(xmlType, javaName, true, false);
   }

   protected void addPojo(String xmlType, String javaName) {
      this.addMapping(xmlType, javaName, false, false);
   }

   protected void addSoapPojoXml(String xmlType, String javaName) {
      this.addSoapMapping(xmlType, javaName, false, true);
   }

   protected void addSoapPojoJava(String xmlType, String javaName) {
      this.addSoapMapping(xmlType, javaName, true, false);
   }

   protected void addSoapPojo(String xmlType, String javaName) {
      this.addSoapMapping(xmlType, javaName, false, false);
   }
}
