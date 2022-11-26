package org.apache.xmlbeans.impl.jam.internal.elements;

import java.lang.reflect.Modifier;
import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.JElement;
import org.apache.xmlbeans.impl.jam.JMember;
import org.apache.xmlbeans.impl.jam.mutable.MMember;

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
