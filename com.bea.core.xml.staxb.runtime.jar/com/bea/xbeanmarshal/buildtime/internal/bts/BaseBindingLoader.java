package com.bea.xbeanmarshal.buildtime.internal.bts;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseBindingLoader implements BindingLoader, Serializable {
   private final Map bindingTypes = new LinkedHashMap();
   private final Map xmlFromJava = new LinkedHashMap();
   private final Map xmlFromJavaElement = new LinkedHashMap();
   private final Map javaFromXmlPojo = new LinkedHashMap();
   private static final long serialVersionUID = 1L;

   public BindingType getBindingType(BindingTypeName btName) {
      return (BindingType)this.bindingTypes.get(btName);
   }

   public BindingTypeName lookupPojoFor(XmlTypeName xName) {
      return (BindingTypeName)this.javaFromXmlPojo.get(xName);
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

   public String dumpBindingTypes() {
      StringBuffer sb = new StringBuffer();
      sb.append("\n\n  DUMP of XML BEAN Binding Types\n");
      Iterator it = this.bindingTypes.keySet().iterator();
      int count = 0;

      while(it.hasNext()) {
         Object key = it.next();
         Object val = this.bindingTypes.get(key);
         sb.append(count++ + "  " + key.toString() + " -- " + val.toString() + "\n");
      }

      sb.append("\n DUMP END\n\n\n");
      return sb.toString();
   }

   protected void addBindingType(BindingType bType) {
      this.bindingTypes.put(bType.getName(), bType);
   }

   protected void addPojoFor(XmlTypeName xName, BindingTypeName btName) {
      assert !btName.getJavaName().isXmlObject();

      this.javaFromXmlPojo.put(xName, btName);
   }

   protected void addTypeFor(JavaTypeName jName, BindingTypeName btName) {
      assert btName.getXmlName().isSchemaType() || btName.getXmlName().isExceptionType();

      this.xmlFromJava.put(jName, btName);
   }

   protected void addElementFor(JavaTypeName jName, BindingTypeName btName) {
      assert btName.getXmlName().getComponentType() == 101 : "not an element: " + btName;

      this.xmlFromJavaElement.put(jName, btName);
   }
}
