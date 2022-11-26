package org.glassfish.admin.rest.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ObjectTypeInfo implements TypeInfo {
   private String displayName;
   private Set properties = new TreeSet();
   private Map nameToProp = new HashMap();

   public ObjectTypeInfo(String displayName) {
      this.displayName = displayName;
   }

   public PropertyInfo createProperty(String name, TypeInfo type, String description) throws Exception {
      PropertyInfo p = new PropertyInfo(this, name, type, description);
      this.nameToProp.put(name, p);
      this.properties.add(p);
      return p;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public boolean propertyExists(String name) {
      return this.getProperty(name) != null;
   }

   public PropertyInfo getProperty(String name) {
      return (PropertyInfo)this.nameToProp.get(name);
   }

   public Set getProperties() {
      return this.properties;
   }

   public String toString() {
      return "ObjectTypeInfo [displayName=" + this.getDisplayName() + ", properties=" + this.getProperties() + "]";
   }
}
