package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import serp.bytecode.lowlevel.ComplexEntry;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.lowlevel.UTF8Entry;

public abstract class BCMember extends Annotated {
   private BCClass _owner = null;
   private int _access = 2;
   private int _nameIndex = 0;
   private int _descriptorIndex = 0;
   private Collection _attrs = new LinkedList();

   BCMember(BCClass owner) {
      this._owner = owner;
   }

   public BCClass getDeclarer() {
      return this._owner;
   }

   public int getAccessFlags() {
      return this._access;
   }

   public void setAccessFlags(int access) {
      this._access = access;
   }

   public boolean isPublic() {
      return (this.getAccessFlags() & 1) > 0;
   }

   public void makePublic() {
      this.setAccessFlags(this.getAccessFlags() | 1);
      this.setAccessFlags(this.getAccessFlags() & -3);
      this.setAccessFlags(this.getAccessFlags() & -5);
   }

   public boolean isProtected() {
      return (this.getAccessFlags() & 4) > 0;
   }

   public void makeProtected() {
      this.setAccessFlags(this.getAccessFlags() & -2);
      this.setAccessFlags(this.getAccessFlags() & -3);
      this.setAccessFlags(this.getAccessFlags() | 4);
   }

   public boolean isPrivate() {
      return (this.getAccessFlags() & 2) > 0;
   }

   public void makePrivate() {
      this.setAccessFlags(this.getAccessFlags() & -2);
      this.setAccessFlags(this.getAccessFlags() | 2);
      this.setAccessFlags(this.getAccessFlags() & -5);
   }

   public boolean isPackage() {
      boolean hasAccess = false;
      hasAccess |= (this.getAccessFlags() & 2) > 0;
      hasAccess |= (this.getAccessFlags() & 4) > 0;
      hasAccess |= (this.getAccessFlags() & 1) > 0;
      return !hasAccess;
   }

   public void makePackage() {
      this.setAccessFlags(this.getAccessFlags() & -2);
      this.setAccessFlags(this.getAccessFlags() & -3);
      this.setAccessFlags(this.getAccessFlags() & -5);
   }

   public boolean isFinal() {
      return (this.getAccessFlags() & 16) > 0;
   }

   public void setFinal(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 16);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -17);
      }

   }

   public boolean isStatic() {
      return (this.getAccessFlags() & 8) > 0;
   }

   public void setStatic(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 8);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -9);
      }

   }

   public boolean isSynthetic() {
      return (this.getAccessFlags() & 4096) > 0 || this.getAttribute("Synthetic") != null;
   }

   public void setSynthetic(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 4096);
         this.addAttribute("Synthetic");
      } else {
         this.setAccessFlags(this.getAccessFlags() & -4097);
         this.removeAttribute("Synthetic");
      }

   }

   public int getNameIndex() {
      return this._nameIndex;
   }

   public void setNameIndex(int index) {
      String origName = this.getName();
      this._nameIndex = index;
      this.setEntry(origName, this.getDescriptor());
   }

   public int getDescriptorIndex() {
      return this._descriptorIndex;
   }

   public void setDescriptorIndex(int index) {
      String origDesc = this.getDescriptor();
      this._descriptorIndex = index;
      this.setEntry(this.getName(), origDesc);
   }

   public String getName() {
      return ((UTF8Entry)this.getPool().getEntry(this._nameIndex)).getValue();
   }

   public void setName(String name) {
      String origName = this.getName();
      this._nameIndex = this.getPool().findUTF8Entry(name, true);
      this.setEntry(origName, this.getDescriptor());
   }

   public String getDescriptor() {
      return ((UTF8Entry)this.getPool().getEntry(this._descriptorIndex)).getValue();
   }

   public void setDescriptor(String desc) {
      String origDesc = this.getDescriptor();
      desc = this.getProject().getNameCache().getInternalForm(desc, true);
      this._descriptorIndex = this.getPool().findUTF8Entry(desc, true);
      this.setEntry(this.getName(), origDesc);
   }

   private void setEntry(String origName, String origDesc) {
      String owner = this.getProject().getNameCache().getInternalForm(this._owner.getName(), false);
      ConstantPool pool = this.getPool();
      int index;
      if (this instanceof BCField) {
         index = pool.findFieldEntry(origName, origDesc, owner, false);
      } else if (!this._owner.isInterface()) {
         index = pool.findMethodEntry(origName, origDesc, owner, false);
      } else {
         index = pool.findInterfaceMethodEntry(origName, origDesc, owner, false);
      }

      if (index != 0) {
         ComplexEntry complex = (ComplexEntry)pool.getEntry(index);
         int ntIndex = pool.findNameAndTypeEntry(this.getName(), this.getDescriptor(), true);
         complex.setNameAndTypeIndex(ntIndex);
      }

   }

   public boolean isDeprecated() {
      return this.getAttribute("Deprecated") != null;
   }

   public void setDeprecated(boolean on) {
      if (!on) {
         this.removeAttribute("Deprecated");
      } else if (!this.isDeprecated()) {
         this.addAttribute("Deprecated");
      }

   }

   public Project getProject() {
      return this._owner.getProject();
   }

   public ConstantPool getPool() {
      return this._owner.getPool();
   }

   public ClassLoader getClassLoader() {
      return this._owner.getClassLoader();
   }

   public boolean isValid() {
      return this._owner != null;
   }

   Collection getAttributesHolder() {
      return this._attrs;
   }

   void initialize(String name, String descriptor) {
      this._nameIndex = this.getPool().findUTF8Entry(name, true);
      this._descriptorIndex = this.getPool().findUTF8Entry(descriptor, true);
   }

   BCClass getBCClass() {
      return this._owner;
   }

   void invalidate() {
      this._owner = null;
   }

   void read(DataInput in) throws IOException {
      this._access = in.readUnsignedShort();
      this._nameIndex = in.readUnsignedShort();
      this._descriptorIndex = in.readUnsignedShort();
      this.readAttributes(in);
   }

   void write(DataOutput out) throws IOException {
      out.writeShort(this._access);
      out.writeShort(this._nameIndex);
      out.writeShort(this._descriptorIndex);
      this.writeAttributes(out);
   }
}
