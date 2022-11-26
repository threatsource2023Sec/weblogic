package com.bea.xml_.impl.jam.internal.elements;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JElement;
import com.bea.xml_.impl.jam.JMember;
import com.bea.xml_.impl.jam.mutable.MMember;
import java.lang.reflect.Modifier;

public abstract class MemberImpl extends AnnotatedElementImpl implements MMember {
   private int mModifiers = 0;

   protected MemberImpl(ElementImpl parent) {
      super(parent);
   }

   protected MemberImpl(ElementContext ctx) {
      super(ctx);
   }

   public JClass getContainingClass() {
      JElement p = this.getParent();
      if (p instanceof JClass) {
         return (JClass)p;
      } else {
         return p instanceof JMember ? ((JMember)p).getContainingClass() : null;
      }
   }

   public int getModifiers() {
      return this.mModifiers;
   }

   public boolean isPackagePrivate() {
      return !this.isPrivate() && !this.isPublic() && !this.isProtected();
   }

   public boolean isPrivate() {
      return Modifier.isPrivate(this.getModifiers());
   }

   public boolean isProtected() {
      return Modifier.isProtected(this.getModifiers());
   }

   public boolean isPublic() {
      return Modifier.isPublic(this.getModifiers());
   }

   public void setModifiers(int modifiers) {
      this.mModifiers = modifiers;
   }
}
