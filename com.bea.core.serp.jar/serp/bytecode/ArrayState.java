package serp.bytecode;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

class ArrayState extends State {
   private String _name = null;
   private String _componentName = null;

   public ArrayState(String name, String componentName) {
      this._name = name;
      this._componentName = componentName;
   }

   public int getMagic() {
      return -889275714;
   }

   public int getMajorVersion() {
      return 45;
   }

   public int getMinorVersion() {
      return 3;
   }

   public int getAccessFlags() {
      return 17;
   }

   public int getIndex() {
      return 0;
   }

   public int getSuperclassIndex() {
      return 0;
   }

   public List getInterfacesHolder() {
      return Collections.EMPTY_LIST;
   }

   public List getFieldsHolder() {
      return Collections.EMPTY_LIST;
   }

   public List getMethodsHolder() {
      return Collections.EMPTY_LIST;
   }

   public Collection getAttributesHolder() {
      return Collections.EMPTY_LIST;
   }

   public String getName() {
      return this._name;
   }

   public String getSuperclassName() {
      return Object.class.getName();
   }

   public String getComponentName() {
      return this._componentName;
   }

   public boolean isPrimitive() {
      return false;
   }

   public boolean isArray() {
      return true;
   }
}
