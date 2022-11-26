package serp.bytecode;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

class PrimitiveState extends State {
   private final Class _type;
   private final NameCache _names;

   public PrimitiveState(Class type, NameCache names) {
      this._type = type;
      this._names = names;
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
      return this._names.getExternalForm(this._type.getName(), false);
   }

   public String getSuperclassName() {
      return null;
   }

   public String getComponentName() {
      return null;
   }

   public boolean isPrimitive() {
      return true;
   }

   public boolean isArray() {
      return false;
   }
}
