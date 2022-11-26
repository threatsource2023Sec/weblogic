package weblogic.management.provider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BaseServiceImpl implements Service {
   private String name;
   private String type;
   private Service parent;
   private String parentAttribute;
   private int hashCode;
   private PropertyChangeSupport changeSupport;

   public BaseServiceImpl(String name, String type, Service parent, String parentAttribute) {
      this.changeSupport = new PropertyChangeSupport(this);
      this.name = name;
      this.type = type;
      this.parent = parent;
      this.parentAttribute = parentAttribute;
      this.hashCode = (name + type + this.getPath()).hashCode();
   }

   public BaseServiceImpl(String name, String type, Service parent) {
      this(name, type, parent, (String)null);
   }

   public int hashCode() {
      return this.hashCode;
   }

   public String getName() {
      return this.name;
   }

   public String getType() {
      return this.type;
   }

   public Service getParentService() {
      return this.parent;
   }

   private static void appendNode(StringBuffer result, String attribute, String key) {
      result.append("/");
      result.append(attribute);
      result.append("[");
      result.append(key);
      result.append("]");
   }

   public String getPath() {
      StringBuffer result = new StringBuffer();
      if (this.parent == null) {
         appendNode(result, this.getShortType(), this.name);
      } else {
         result.append(this.parent.getPath());
         appendNode(result, this.getParentAttribute(), this.name);
      }

      return result.toString();
   }

   public String getParentAttribute() {
      if (this.parentAttribute != null) {
         return this.parentAttribute;
      } else if (this.parent == null) {
         return null;
      } else {
         String shortType = this.getShortType();
         Class parentClass = this.parent.getClass();

         try {
            parentClass.getMethod("get" + shortType, (Class[])null);
            this.parentAttribute = shortType;
            return this.type;
         } catch (NoSuchMethodException var7) {
            String possibleParentAttribute = pluralize(shortType);

            try {
               parentClass.getMethod("get" + possibleParentAttribute, (Class[])null);
               this.parentAttribute = possibleParentAttribute;
            } catch (NoSuchMethodException var6) {
               throw new RuntimeException("Unable to determine parent type for " + shortType + " parent " + parentClass);
            }

            return this.parentAttribute;
         }
      }
   }

   private String getShortType() {
      String result = trimPackage(this.type);
      if (result.endsWith("MBean")) {
         result = result.substring(0, result.length() - 5);
      }

      return result;
   }

   private static String trimPackage(String className) {
      int index = className.lastIndexOf(46);
      int len = className.length();
      if (index != -1) {
         className = className.substring(index + 1, len);
      }

      return className;
   }

   private static String pluralize(String name) {
      String result = null;
      if (!name.endsWith("s") && !name.endsWith("ch") && !name.endsWith("x") && !name.endsWith("sh")) {
         if (name.endsWith("y") && !name.endsWith("ay") && !name.endsWith("ey") && !name.endsWith("iy") && !name.endsWith("oy") && !name.endsWith("uy")) {
            result = name.substring(0, name.length() - 1) + "ies";
         } else {
            result = name + "s";
         }
      } else {
         result = name + "es";
      }

      return result;
   }

   public boolean equals(Object object) {
      if (!(object instanceof Service)) {
         return false;
      } else {
         Service key = (Service)object;
         return key.getName().equals(this.name) && key.getType().equals(this.type) && (this.parent == null && key.getParentService() == null || this.parent != null && key.getParentService() != null && this.parent.equals(key.getParentService()) && this.parentAttribute != null && key.getParentAttribute() != null && this.parentAttribute.equals(key.getParentAttribute()));
      }
   }

   protected void _postSet(String propertyName, Object oldVal, Object newVal) {
      if (!newVal.equals(oldVal)) {
         if (this.changeSupport != null) {
            this.changeSupport.firePropertyChange(propertyName, oldVal, newVal);
         }

      }
   }

   public final void addPropertyChangeListener(PropertyChangeListener listener) {
      this.changeSupport.addPropertyChangeListener(listener);
   }

   public final void removePropertyChangeListener(PropertyChangeListener listener) {
      if (this.changeSupport != null) {
         this.changeSupport.removePropertyChangeListener(listener);
      }

   }
}
