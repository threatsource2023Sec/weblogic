package org.jboss.weld.util.annotated;

import java.lang.reflect.Member;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedType;

public abstract class ForwardingAnnotatedMember extends ForwardingAnnotated implements AnnotatedMember {
   public Member getJavaMember() {
      return this.delegate().getJavaMember();
   }

   public boolean isStatic() {
      return this.delegate().isStatic();
   }

   public AnnotatedType getDeclaringType() {
      return this.delegate().getDeclaringType();
   }

   protected abstract AnnotatedMember delegate();
}
