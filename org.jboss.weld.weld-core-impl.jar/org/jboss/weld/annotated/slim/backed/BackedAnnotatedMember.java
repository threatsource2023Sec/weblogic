package org.jboss.weld.annotated.slim.backed;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Iterator;
import javax.enterprise.inject.spi.AnnotatedMember;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.resources.ReflectionCache;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.serialization.AbstractSerializableHolder;
import org.jboss.weld.util.reflection.Reflections;

public abstract class BackedAnnotatedMember extends BackedAnnotated implements AnnotatedMember {
   private BackedAnnotatedType declaringType;

   public BackedAnnotatedMember(Type baseType, BackedAnnotatedType declaringType, SharedObjectCache sharedObjectCache) {
      super(baseType, sharedObjectCache);
      this.declaringType = declaringType;
   }

   public boolean isStatic() {
      return Reflections.isStatic(this.getJavaMember());
   }

   public BackedAnnotatedType getDeclaringType() {
      return this.declaringType;
   }

   protected ReflectionCache getReflectionCache() {
      return this.getDeclaringType().getReflectionCache();
   }

   protected abstract static class BackedAnnotatedMemberSerializationProxy implements Serializable {
      private static final long serialVersionUID = 450947485748828056L;
      protected final BackedAnnotatedType type;
      private final AbstractSerializableHolder memberHolder;

      public BackedAnnotatedMemberSerializationProxy(BackedAnnotatedType type, AbstractSerializableHolder memberHolder) {
         this.type = type;
         this.memberHolder = memberHolder;
      }

      protected AnnotatedMember resolve() {
         Iterator var1 = this.getCandidates().iterator();

         AnnotatedMember annotatedMember;
         do {
            if (!var1.hasNext()) {
               throw BeanLogger.LOG.unableToLoadMember(this.memberHolder.get());
            }

            annotatedMember = (AnnotatedMember)var1.next();
         } while(!annotatedMember.getJavaMember().equals(this.memberHolder.get()));

         return annotatedMember;
      }

      protected abstract Iterable getCandidates();
   }
}
