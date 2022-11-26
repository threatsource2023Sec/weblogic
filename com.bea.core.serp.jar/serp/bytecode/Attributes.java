package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import serp.bytecode.lowlevel.UTF8Entry;
import serp.bytecode.visitor.BCVisitor;

public abstract class Attributes implements BCEntity {
   public Attribute[] getAttributes() {
      Collection attrs = this.getAttributesHolder();
      return (Attribute[])((Attribute[])attrs.toArray(new Attribute[attrs.size()]));
   }

   public Attribute getAttribute(String name) {
      Collection attrs = this.getAttributesHolder();
      Iterator itr = attrs.iterator();

      Attribute attr;
      do {
         if (!itr.hasNext()) {
            return null;
         }

         attr = (Attribute)itr.next();
      } while(!attr.getName().equals(name));

      return attr;
   }

   public Attribute[] getAttributes(String name) {
      List matches = new LinkedList();
      Collection attrs = this.getAttributesHolder();
      Iterator itr = attrs.iterator();

      while(itr.hasNext()) {
         Attribute attr = (Attribute)itr.next();
         if (attr.getName().equals(name)) {
            matches.add(attr);
         }
      }

      return (Attribute[])((Attribute[])matches.toArray(new Attribute[matches.size()]));
   }

   public void setAttributes(Attribute[] attrs) {
      this.clearAttributes();
      if (attrs != null) {
         for(int i = 0; i < attrs.length; ++i) {
            this.addAttribute(attrs[i]);
         }
      }

   }

   public Attribute addAttribute(Attribute attr) {
      Attribute newAttr = this.addAttribute(attr.getName());
      newAttr.read(attr);
      return newAttr;
   }

   public Attribute addAttribute(String name) {
      Attribute attr = Attribute.create(name, this);
      this.getAttributesHolder().add(attr);
      return attr;
   }

   public void clearAttributes() {
      Collection attrs = this.getAttributesHolder();
      Iterator itr = attrs.iterator();

      while(itr.hasNext()) {
         Attribute attr = (Attribute)itr.next();
         itr.remove();
         attr.invalidate();
      }

   }

   public boolean removeAttribute(String name) {
      return this.removeAttribute(this.getAttribute(name));
   }

   public boolean removeAttribute(Attribute attribute) {
      if (attribute != null && this.getAttributesHolder().remove(attribute)) {
         attribute.invalidate();
         return true;
      } else {
         return false;
      }
   }

   void visitAttributes(BCVisitor visit) {
      Iterator itr = this.getAttributesHolder().iterator();

      while(itr.hasNext()) {
         Attribute attr = (Attribute)itr.next();
         visit.enterAttribute(attr);
         attr.acceptVisit(visit);
         visit.exitAttribute(attr);
      }

   }

   void readAttributes(DataInput in) throws IOException {
      Collection attrs = this.getAttributesHolder();
      attrs.clear();

      for(int i = in.readUnsignedShort(); i > 0; --i) {
         String name = ((UTF8Entry)this.getPool().getEntry(in.readUnsignedShort())).getValue();
         Attribute attribute = this.addAttribute(name);
         attribute.read(in, in.readInt());
      }

   }

   void writeAttributes(DataOutput out) throws IOException {
      Collection attrs = this.getAttributesHolder();
      out.writeShort(attrs.size());
      Iterator itr = attrs.iterator();

      while(itr.hasNext()) {
         Attribute attribute = (Attribute)itr.next();
         out.writeShort(attribute.getNameIndex());
         int length = attribute.getLength();
         out.writeInt(length);
         attribute.write(out, length);
      }

   }

   abstract Collection getAttributesHolder();
}
