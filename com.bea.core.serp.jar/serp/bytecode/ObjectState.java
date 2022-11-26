package serp.bytecode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.lowlevel.ConstantPool;

class ObjectState extends State {
   private final ConstantPool _pool = new ConstantPool();
   private final NameCache _names;
   private int _index = 0;
   private int _superclassIndex = 0;
   private int _magic = -889275714;
   private int _major = 45;
   private int _minor = 3;
   private int _access = 33;
   private final List _interfaces = new ArrayList();
   private final List _fields = new ArrayList();
   private final List _methods = new ArrayList();
   private final List _attributes = new ArrayList();

   public ObjectState(NameCache names) {
      this._names = names;
   }

   public int getMagic() {
      return this._magic;
   }

   public void setMagic(int magic) {
      this._magic = magic;
   }

   public int getMajorVersion() {
      return this._major;
   }

   public void setMajorVersion(int major) {
      this._major = major;
   }

   public int getMinorVersion() {
      return this._minor;
   }

   public void setMinorVersion(int minor) {
      this._minor = minor;
   }

   public int getAccessFlags() {
      return this._access;
   }

   public void setAccessFlags(int access) {
      this._access = access;
   }

   public int getIndex() {
      return this._index;
   }

   public void setIndex(int index) {
      this._index = index;
   }

   public int getSuperclassIndex() {
      return this._superclassIndex;
   }

   public void setSuperclassIndex(int index) {
      this._superclassIndex = index;
   }

   public List getInterfacesHolder() {
      return this._interfaces;
   }

   public List getFieldsHolder() {
      return this._fields;
   }

   public List getMethodsHolder() {
      return this._methods;
   }

   public Collection getAttributesHolder() {
      return this._attributes;
   }

   public ConstantPool getPool() {
      return this._pool;
   }

   public String getName() {
      return this._index == 0 ? null : this._names.getExternalForm(((ClassEntry)this._pool.getEntry(this._index)).getNameEntry().getValue(), false);
   }

   public String getSuperclassName() {
      return this._superclassIndex == 0 ? null : this._names.getExternalForm(((ClassEntry)this._pool.getEntry(this._superclassIndex)).getNameEntry().getValue(), false);
   }

   public String getComponentName() {
      return null;
   }

   public boolean isPrimitive() {
      return false;
   }

   public boolean isArray() {
      return false;
   }
}
