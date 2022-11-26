package com.bea.staxb.buildtime.internal.bts;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseBindingLoader implements BindingLoader, Serializable {
   private final Map bindingTypes = new LinkedHashMap();
   private final Map xmlFromJava = new LinkedHashMap();
   private final Map xmlFromJavaElement = new LinkedHashMap();
   private final Map javaFromXmlPojo = new LinkedHashMap();
   private final Map javaFromXmlObj = new LinkedHashMap();
   private static final long serialVersionUID = 1L;

   public BindingType getBindingType(BindingTypeName btName) {
      return (BindingType)this.bindingTypes.get(btName);
   }

   public BindingTypeName lookupPojoFor(XmlTypeName xName) {
      return (BindingTypeName)this.javaFromXmlPojo.get(xName);
   }

   public BindingTypeName lookupXmlObjectFor(XmlTypeName xName) {
      return (BindingTypeName)this.javaFromXmlObj.get(xName);
   }

   public BindingTypeName lookupTypeFor(JavaTypeName jName) {
      return (BindingTypeName)this.xmlFromJava.get(jName);
   }

   public BindingTypeName lookupElementFor(JavaTypeName jName) {
      return (BindingTypeName)this.xmlFromJavaElement.get(jName);
   }

   public Collection bindingTypes() {
      return this.bindingTypes.values();
   }

   public Collection typeMappedJavaTypes() {
      return this.xmlFromJava.keySet();
   }

   public Collection elementMappedJavaTypes() {
      return this.xmlFromJavaElement.keySet();
   }

   public Collection pojoMappedXmlTypes() {
      return this.javaFromXmlPojo.keySet();
   }

   public Collection xmlObjectMappedXmlTypes() {
      return this.javaFromXmlObj.keySet();
   }

   protected void addBindingType(BindingType bType) {
      this.bindingTypes.put(bType.getName(), bType);
   }

   protected void addPojoFor(XmlTypeName xName, BindingTypeName btName) {
      assert !btName.getJavaName().isXmlObject();

      this.javaFromXmlPojo.put(xName, btName);
   }

   protected void addXmlObjectFor(XmlTypeName xName, BindingTypeName btName) {
      assert btName.getJavaName().isXmlObject();

      this.javaFromXmlObj.put(xName, btName);
   }

   protected void addTypeFor(JavaTypeName jName, BindingTypeName btName) {
      this.xmlFromJava.put(jName, btName);
   }

   protected void addElementFor(JavaTypeName jName, BindingTypeName btName) {
      assert btName.getXmlName().getComponentType() == 101 : "not an element: " + btName;

      this.xmlFromJavaElement.put(jName, btName);
   }
}
