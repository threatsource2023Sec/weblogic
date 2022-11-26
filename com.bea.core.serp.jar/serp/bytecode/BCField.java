package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;
import serp.util.Strings;

public class BCField extends BCMember implements VisitAcceptor {
   BCField(BCClass owner) {
      super(owner);
      if (owner.isEnum()) {
         this.setEnum(true);
      }

   }

   public boolean isVolatile() {
      return (this.getAccessFlags() & 64) > 0;
   }

   public void setVolatile(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 64);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -65);
      }

   }

   public boolean isTransient() {
      return (this.getAccessFlags() & 128) > 0;
   }

   public void setTransient(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 128);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -129);
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

   public String getTypeName() {
      return this.getProject().getNameCache().getExternalForm(this.getDescriptor(), false);
   }

   public Class getType() {
      return Strings.toClass(this.getTypeName(), this.getClassLoader());
   }

   public BCClass getTypeBC() {
      return this.getProject().loadClass(this.getTypeName(), this.getClassLoader());
   }

   public void setType(String type) {
      this.setDescriptor(type);
   }

   public void setType(Class type) {
      this.setType(type.getName());
   }

   public void setType(BCClass type) {
      this.setType(type.getName());
   }

   public ConstantValue getConstantValue(boolean add) {
      ConstantValue constant = (ConstantValue)this.getAttribute("ConstantValue");
      if (add && constant == null) {
         if (constant == null) {
            constant = (ConstantValue)this.addAttribute("ConstantValue");
         }

         return constant;
      } else {
         return constant;
      }
   }

   public boolean removeConstantValue() {
      return this.removeAttribute("ConstantValue");
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterBCField(this);
      this.visitAttributes(visit);
      visit.exitBCField(this);
   }

   void initialize(String name, String descriptor) {
      super.initialize(name, descriptor);
      this.makePrivate();
   }
}
