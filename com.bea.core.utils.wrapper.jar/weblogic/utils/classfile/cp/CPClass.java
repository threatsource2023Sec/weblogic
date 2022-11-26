package weblogic.utils.classfile.cp;

import weblogic.utils.classfile.Descriptor;
import weblogic.utils.classfile.DoubleKey;
import weblogic.utils.classfile.MalformedClassException;

public class CPClass extends CPInfo {
   public CPUtf8 name;

   public CPClass() {
      this.setTag(7);
   }

   public void init(Object o) {
      DoubleKey key = (DoubleKey)o;
      this.name = (CPUtf8)key.two;
   }

   public Class asClass() throws MalformedClassException {
      try {
         return Class.forName(this.name.getValue());
      } catch (ClassNotFoundException var2) {
         throw new MalformedClassException("Class not found for " + this.name.getValue());
      }
   }

   public String getPackage() {
      String n = this.getName();
      return n.substring(0, n.lastIndexOf(46));
   }

   public String getSimpleName() {
      String n = this.getName();
      return n.substring(n.lastIndexOf(46) + 1);
   }

   public void setName(String nameStr) {
      this.name.setValue(nameStr.replace('.', '/'));
   }

   public String getName() {
      return Descriptor.getClassType(this.name.getValue());
   }

   /** @deprecated */
   @Deprecated
   public String fullName() {
      return this.getName();
   }

   public String toString() {
      return this.getName();
   }

   public boolean equals(Object o) {
      return !(o instanceof CPClass) ? false : this.name.equals(((CPClass)o).name);
   }

   public int hashCode() {
      return this.name.hashCode();
   }
}
