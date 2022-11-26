package serp.bytecode;

import java.util.Collection;
import java.util.List;
import serp.bytecode.lowlevel.ConstantPool;

class State {
   public static final State INVALID = new State();

   public int getMagic() {
      throw new UnsupportedOperationException();
   }

   public void setMagic(int magic) {
      throw new UnsupportedOperationException();
   }

   public int getMajorVersion() {
      throw new UnsupportedOperationException();
   }

   public void setMajorVersion(int major) {
      throw new UnsupportedOperationException();
   }

   public int getMinorVersion() {
      throw new UnsupportedOperationException();
   }

   public void setMinorVersion(int minor) {
      throw new UnsupportedOperationException();
   }

   public int getAccessFlags() {
      throw new UnsupportedOperationException();
   }

   public void setAccessFlags(int access) {
      throw new UnsupportedOperationException();
   }

   public int getIndex() {
      throw new UnsupportedOperationException();
   }

   public void setIndex(int index) {
      throw new UnsupportedOperationException();
   }

   public int getSuperclassIndex() {
      throw new UnsupportedOperationException();
   }

   public void setSuperclassIndex(int index) {
      throw new UnsupportedOperationException();
   }

   public List getInterfacesHolder() {
      throw new UnsupportedOperationException();
   }

   public List getFieldsHolder() {
      throw new UnsupportedOperationException();
   }

   public List getMethodsHolder() {
      throw new UnsupportedOperationException();
   }

   public Collection getAttributesHolder() {
      throw new UnsupportedOperationException();
   }

   public ConstantPool getPool() {
      throw new UnsupportedOperationException();
   }

   public String getName() {
      throw new UnsupportedOperationException();
   }

   public String getSuperclassName() {
      throw new UnsupportedOperationException();
   }

   public String getComponentName() {
      throw new UnsupportedOperationException();
   }

   public boolean isPrimitive() {
      throw new UnsupportedOperationException();
   }

   public boolean isArray() {
      throw new UnsupportedOperationException();
   }
}
