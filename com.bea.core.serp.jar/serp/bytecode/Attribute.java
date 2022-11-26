package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.lowlevel.UTF8Entry;
import serp.bytecode.visitor.VisitAcceptor;
import serp.util.Numbers;

public abstract class Attribute extends Attributes implements VisitAcceptor {
   private int _nameIndex = 0;
   private Attributes _owner = null;

   Attribute(int nameIndex, Attributes owner) {
      this._owner = owner;
      this._nameIndex = nameIndex;
   }

   static Attribute create(String name, Attributes owner) {
      int nameIndex = owner.getPool().findUTF8Entry(name, true);
      if (!"RuntimeVisibleAnnotations".equals(name) && !"RuntimeInvisibleAnnotations".equals(name)) {
         try {
            Class type = Class.forName("serp.bytecode." + name);
            Constructor cons = type.getDeclaredConstructor(Integer.TYPE, Attributes.class);
            return (Attribute)cons.newInstance(Numbers.valueOf(nameIndex), owner);
         } catch (Throwable var5) {
            return new UnknownAttribute(nameIndex, owner);
         }
      } else {
         return new Annotations(nameIndex, owner);
      }
   }

   public Attributes getOwner() {
      return this._owner;
   }

   public int getNameIndex() {
      return this._nameIndex;
   }

   public String getName() {
      return ((UTF8Entry)this.getPool().getEntry(this._nameIndex)).getValue();
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
      return Collections.EMPTY_LIST;
   }

   void invalidate() {
      this._owner = null;
   }

   int getLength() {
      return 0;
   }

   void read(Attribute other) {
   }

   void read(DataInput in, int length) throws IOException {
   }

   void write(DataOutput out, int length) throws IOException {
   }
}
