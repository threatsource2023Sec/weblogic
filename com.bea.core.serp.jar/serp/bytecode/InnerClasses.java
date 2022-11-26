package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import serp.bytecode.visitor.BCVisitor;

public class InnerClasses extends Attribute {
   private List _innerClasses = new LinkedList();

   InnerClasses(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   public InnerClass[] getInnerClasses() {
      return (InnerClass[])((InnerClass[])this._innerClasses.toArray(new InnerClass[this._innerClasses.size()]));
   }

   public InnerClass getInnerClass(String name) {
      InnerClass[] inners = this.getInnerClasses();

      for(int i = 0; i < inners.length; ++i) {
         String inner = inners[i].getName();
         if (inner == null && name == null || inner != null && inner.equals(name)) {
            return inners[i];
         }
      }

      return null;
   }

   public InnerClass[] getInnerClasses(String name) {
      List matches = new LinkedList();
      InnerClass[] inners = this.getInnerClasses();

      for(int i = 0; i < inners.length; ++i) {
         String inner = inners[i].getName();
         if (inner == null && name == null || inner != null && inner.equals(name)) {
            matches.add(inners[i]);
         }
      }

      return (InnerClass[])((InnerClass[])matches.toArray(new InnerClass[matches.size()]));
   }

   public void setInnerClasses(InnerClass[] inners) {
      this.clear();
      if (inners != null) {
         for(int i = 0; i < inners.length; ++i) {
            this.addInnerClass(inners[i]);
         }
      }

   }

   public InnerClass addInnerClass(InnerClass inner) {
      InnerClass newInner = this.addInnerClass(inner.getName(), inner.getTypeName(), inner.getDeclarerName());
      newInner.setAccessFlags(inner.getAccessFlags());
      return newInner;
   }

   public InnerClass addInnerClass() {
      InnerClass inner = new InnerClass(this);
      this._innerClasses.add(inner);
      return inner;
   }

   public InnerClass addInnerClass(String name, String type, String owner) {
      InnerClass inner = this.addInnerClass();
      inner.setName(name);
      inner.setType(type);
      inner.setDeclarer(owner);
      return inner;
   }

   public InnerClass addInnerClass(String name, Class type, Class owner) {
      String typeName = type == null ? null : type.getName();
      String ownerName = owner == null ? null : owner.getName();
      return this.addInnerClass(name, typeName, ownerName);
   }

   public InnerClass addInnerClass(String name, BCClass type, BCClass owner) {
      String typeName = type == null ? null : type.getName();
      String ownerName = owner == null ? null : owner.getName();
      return this.addInnerClass(name, typeName, ownerName);
   }

   public void clear() {
      Iterator itr = this._innerClasses.iterator();

      while(itr.hasNext()) {
         InnerClass inner = (InnerClass)itr.next();
         itr.remove();
         inner.invalidate();
      }

   }

   public boolean removeInnerClass(String name) {
      return this.removeInnerClass(this.getInnerClass(name));
   }

   public boolean removeInnerClass(InnerClass innerClass) {
      if (innerClass != null && this._innerClasses.remove(innerClass)) {
         innerClass.invalidate();
         return true;
      } else {
         return false;
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterInnerClasses(this);
      InnerClass[] inners = this.getInnerClasses();

      for(int i = 0; i < inners.length; ++i) {
         inners[i].acceptVisit(visit);
      }

      visit.exitInnerClasses(this);
   }

   int getLength() {
      return 2 + 8 * this._innerClasses.size();
   }

   void read(Attribute other) {
      this.setInnerClasses(((InnerClasses)other).getInnerClasses());
   }

   void read(DataInput in, int length) throws IOException {
      this.clear();
      int numInnerClasses = in.readUnsignedShort();

      for(int i = 0; i < numInnerClasses; ++i) {
         InnerClass innerClass = this.addInnerClass();
         innerClass.read(in);
      }

   }

   void write(DataOutput out, int length) throws IOException {
      InnerClass[] inners = this.getInnerClasses();
      out.writeShort(inners.length);

      for(int i = 0; i < inners.length; ++i) {
         inners[i].write(out);
      }

   }
}
