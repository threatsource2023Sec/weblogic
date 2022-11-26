package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.Type;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public class VisibilityPredicate implements Predicate {
   private boolean protectedOk;
   private String pkg;
   private boolean samePackageOk;

   public VisibilityPredicate(Class source, boolean protectedOk) {
      this.protectedOk = protectedOk;
      this.samePackageOk = source.getClassLoader() != null;
      this.pkg = TypeUtils.getPackageName(Type.getType(source));
   }

   public boolean evaluate(Object arg) {
      Member member = (Member)arg;
      int mod = member.getModifiers();
      if (Modifier.isPrivate(mod)) {
         return false;
      } else if (Modifier.isPublic(mod)) {
         return true;
      } else if (Modifier.isProtected(mod) && this.protectedOk) {
         return true;
      } else {
         return this.samePackageOk && this.pkg.equals(TypeUtils.getPackageName(Type.getType(member.getDeclaringClass())));
      }
   }
}
