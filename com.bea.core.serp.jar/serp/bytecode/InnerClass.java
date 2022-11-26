package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.lowlevel.UTF8Entry;
import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;
import serp.util.Strings;

public class InnerClass implements BCEntity, VisitAcceptor {
   private int _index = 0;
   private int _nameIndex = 0;
   private int _ownerIndex = 0;
   private int _access = 2;
   private InnerClasses _owner = null;

   InnerClass(InnerClasses owner) {
      this._owner = owner;
   }

   public InnerClasses getOwner() {
      return this._owner;
   }

   void invalidate() {
      this._owner = null;
   }

   public int getAccessFlags() {
      return this._access;
   }

   public void setAccessFlags(int accessFlags) {
      this._access = accessFlags;
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

   public boolean isInterface() {
      return (this.getAccessFlags() & 512) > 0;
   }

   public void setInterface(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 512);
         this.setAbstract(true);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -513);
      }

   }

   public boolean isAbstract() {
      return (this.getAccessFlags() & 1024) > 0;
   }

   public void setAbstract(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 512);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -513);
      }

   }

   public boolean isSynthetic() {
      return (this.getAccessFlags() & 4096) > 0;
   }

   public void setSynthetic(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 4096);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -4097);
      }

   }

   public boolean isAnnotation() {
      return (this.getAccessFlags() & 8192) > 0;
   }

   public void setAnnotation(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 8192);
         this.setAccessFlags(this.getAccessFlags() | 512);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -8193);
      }

   }

   public boolean isEnum() {
      return (this.getAccessFlags() & 16384) > 0;
   }

   public void setEnum(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 16384);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -16385);
      }

   }

   public int getNameIndex() {
      return this._nameIndex;
   }

   public void setNameIndex(int nameIndex) {
      this._nameIndex = nameIndex;
   }

   public String getName() {
      return this.getNameIndex() == 0 ? null : ((UTF8Entry)this.getPool().getEntry(this.getNameIndex())).getValue();
   }

   public void setName(String name) {
      if (name == null) {
         this.setNameIndex(0);
      } else {
         this.setNameIndex(this.getPool().findUTF8Entry(name, true));
      }

   }

   public int getTypeIndex() {
      return this._index;
   }

   public void setTypeIndex(int index) {
      this._index = index;
   }

   public String getTypeName() {
      if (this.getTypeIndex() == 0) {
         return null;
      } else {
         ClassEntry entry = (ClassEntry)this.getPool().getEntry(this.getTypeIndex());
         return this.getProject().getNameCache().getExternalForm(entry.getNameEntry().getValue(), false);
      }
   }

   public Class getType() {
      String type = this.getTypeName();
      return type == null ? null : Strings.toClass(type, this.getClassLoader());
   }

   public BCClass getTypeBC() {
      String type = this.getTypeName();
      return type == null ? null : this.getProject().loadClass(type, this.getClassLoader());
   }

   public void setType(String type) {
      if (type == null) {
         this.setTypeIndex(0);
      } else {
         type = this.getProject().getNameCache().getInternalForm(type, false);
         this.setTypeIndex(this.getPool().findClassEntry(type, true));
      }

   }

   public void setType(Class type) {
      if (type == null) {
         this.setType((String)null);
      } else {
         this.setType(type.getName());
      }

   }

   public void setType(BCClass type) {
      if (type == null) {
         this.setType((String)null);
      } else {
         this.setType(type.getName());
      }

   }

   public int getDeclarerIndex() {
      return this._ownerIndex;
   }

   public void setDeclarerIndex(int ownerIndex) {
      this._ownerIndex = ownerIndex;
   }

   public String getDeclarerName() {
      if (this.getDeclarerIndex() == 0) {
         return null;
      } else {
         ClassEntry entry = (ClassEntry)this.getPool().getEntry(this.getDeclarerIndex());
         return this.getProject().getNameCache().getExternalForm(entry.getNameEntry().getValue(), false);
      }
   }

   public Class getDeclarerType() {
      String type = this.getDeclarerName();
      return type == null ? null : Strings.toClass(type, this.getClassLoader());
   }

   public BCClass getDeclarerBC() {
      String type = this.getDeclarerName();
      return type == null ? null : this.getProject().loadClass(type, this.getClassLoader());
   }

   public void setDeclarer(String type) {
      if (type == null) {
         this.setDeclarerIndex(0);
      } else {
         type = this.getProject().getNameCache().getInternalForm(type, false);
         this.setDeclarerIndex(this.getPool().findClassEntry(type, true));
      }

   }

   public void setDeclarer(Class type) {
      if (type == null) {
         this.setDeclarer((String)null);
      } else {
         this.setDeclarer(type.getName());
      }

   }

   public void setDeclarer(BCClass type) {
      if (type == null) {
         this.setDeclarer((String)null);
      } else {
         this.setDeclarer(type.getName());
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

   public void acceptVisit(BCVisitor visit) {
      visit.enterInnerClass(this);
      visit.exitInnerClass(this);
   }

   void read(DataInput in) throws IOException {
      this.setTypeIndex(in.readUnsignedShort());
      this.setDeclarerIndex(in.readUnsignedShort());
      this.setNameIndex(in.readUnsignedShort());
      this.setAccessFlags(in.readUnsignedShort());
   }

   void write(DataOutput out) throws IOException {
      out.writeShort(this.getTypeIndex());
      out.writeShort(this.getDeclarerIndex());
      out.writeShort(this.getNameIndex());
      out.writeShort(this.getAccessFlags());
   }
}
