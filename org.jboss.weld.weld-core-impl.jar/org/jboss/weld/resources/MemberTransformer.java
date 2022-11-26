package org.jboss.weld.resources;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedCallable;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMember;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.unbacked.UnbackedAnnotatedMember;
import org.jboss.weld.annotated.slim.unbacked.UnbackedAnnotatedType;
import org.jboss.weld.annotated.slim.unbacked.UnbackedMemberIdentifier;
import org.jboss.weld.bootstrap.api.BootstrapService;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.reflection.Reflections;

public class MemberTransformer implements BootstrapService {
   private final ClassTransformer transformer;
   private final ComputingCache unbackedAnnotatedMembersById;
   private final ComputingCache enhancedMemberCache;
   private final EnhancedFieldLoader enhancedFieldLoader;
   private final EnhancedMethodLoader enhancedMethodLoader;
   private final EnhancedConstructorLoader enhancedConstructorLoader;

   public MemberTransformer(ClassTransformer transformer) {
      ComputingCacheBuilder cacheBuilder = ComputingCacheBuilder.newBuilder();
      this.transformer = transformer;
      this.unbackedAnnotatedMembersById = cacheBuilder.build(new UnbackedMemberById());
      this.enhancedFieldLoader = new EnhancedFieldLoader();
      this.enhancedMethodLoader = new EnhancedMethodLoader();
      this.enhancedConstructorLoader = new EnhancedConstructorLoader();
      this.enhancedMemberCache = cacheBuilder.build(new EnhancedMemberLoaderFunction());
   }

   public UnbackedAnnotatedMember getUnbackedMember(UnbackedMemberIdentifier identifier) {
      return (UnbackedAnnotatedMember)this.unbackedAnnotatedMembersById.getCastValue(identifier);
   }

   public EnhancedAnnotatedMember loadEnhancedMember(AnnotatedMember member, String bdaId) {
      if (member instanceof EnhancedAnnotatedMember) {
         return (EnhancedAnnotatedMember)Reflections.cast(member);
      } else {
         EnhancedAnnotatedType declaringType = this.transformer.getEnhancedAnnotatedType(member.getDeclaringType(), bdaId);
         return (EnhancedAnnotatedMember)this.enhancedMemberCache.getCastValue(new MemberKey(declaringType, member));
      }
   }

   public EnhancedAnnotatedParameter loadEnhancedParameter(AnnotatedParameter parameter, String bdaId) {
      if (parameter instanceof EnhancedAnnotatedParameter) {
         return (EnhancedAnnotatedParameter)Reflections.cast(parameter);
      } else {
         EnhancedAnnotatedCallable callable = (EnhancedAnnotatedCallable)this.loadEnhancedMember(parameter.getDeclaringCallable(), bdaId);
         return (EnhancedAnnotatedParameter)callable.getEnhancedParameters().get(parameter.getPosition());
      }
   }

   public void cleanupAfterBoot() {
      this.enhancedMemberCache.clear();
   }

   public void cleanup() {
      this.cleanupAfterBoot();
      this.unbackedAnnotatedMembersById.clear();
   }

   private class EnhancedConstructorLoader extends AbstractEnhancedMemberLoader {
      private EnhancedConstructorLoader() {
         super(null);
      }

      public boolean equals(EnhancedAnnotatedConstructor member1, AnnotatedConstructor member2) {
         return AnnotatedTypes.compareAnnotatedCallable(member1, member2);
      }

      public Collection getMembersOfDeclaringType(MemberKey source) {
         return (Collection)Reflections.cast(source.type.getConstructors());
      }

      // $FF: synthetic method
      EnhancedConstructorLoader(Object x1) {
         this();
      }
   }

   private class EnhancedMethodLoader extends AbstractEnhancedMemberLoader {
      private EnhancedMethodLoader() {
         super(null);
      }

      public boolean equals(EnhancedAnnotatedMethod member1, AnnotatedMethod member2) {
         return AnnotatedTypes.compareAnnotatedCallable(member1, member2);
      }

      public Collection getMembersOfDeclaringType(MemberKey source) {
         return (Collection)Reflections.cast(source.type.getMethods());
      }

      // $FF: synthetic method
      EnhancedMethodLoader(Object x1) {
         this();
      }
   }

   private class EnhancedFieldLoader extends AbstractEnhancedMemberLoader {
      private EnhancedFieldLoader() {
         super(null);
      }

      public boolean equals(EnhancedAnnotatedField member1, AnnotatedField member2) {
         return AnnotatedTypes.compareAnnotatedField(member1, member2);
      }

      public Collection getMembersOfDeclaringType(MemberKey source) {
         return (Collection)Reflections.cast(source.type.getFields());
      }

      // $FF: synthetic method
      EnhancedFieldLoader(Object x1) {
         this();
      }
   }

   private abstract class AbstractEnhancedMemberLoader {
      private AbstractEnhancedMemberLoader() {
      }

      public EnhancedAnnotatedMember load(MemberKey source) {
         return this.findMatching(this.getMembersOfDeclaringType(source), source.member);
      }

      public EnhancedAnnotatedMember findMatching(Collection members, AnnotatedMember source) {
         Iterator var3 = members.iterator();

         EnhancedAnnotatedMember member;
         do {
            if (!var3.hasNext()) {
               throw BeanLogger.LOG.unableToLoadMember(source);
            }

            member = (EnhancedAnnotatedMember)var3.next();
         } while(!this.equals(member, source));

         return member;
      }

      public abstract boolean equals(EnhancedAnnotatedMember var1, AnnotatedMember var2);

      public abstract Collection getMembersOfDeclaringType(MemberKey var1);

      // $FF: synthetic method
      AbstractEnhancedMemberLoader(Object x1) {
         this();
      }
   }

   private class EnhancedMemberLoaderFunction implements Function {
      private EnhancedMemberLoaderFunction() {
      }

      public EnhancedAnnotatedMember apply(MemberKey from) {
         if (from.member instanceof AnnotatedField) {
            return MemberTransformer.this.enhancedFieldLoader.load((MemberKey)Reflections.cast(from));
         } else if (from.member instanceof AnnotatedMethod) {
            return MemberTransformer.this.enhancedMethodLoader.load((MemberKey)Reflections.cast(from));
         } else if (from.member instanceof AnnotatedConstructor) {
            return MemberTransformer.this.enhancedConstructorLoader.load((MemberKey)Reflections.cast(from));
         } else {
            throw BeanLogger.LOG.invalidAnnotatedMember(from);
         }
      }

      // $FF: synthetic method
      EnhancedMemberLoaderFunction(Object x1) {
         this();
      }
   }

   private static class UnbackedMemberById implements Function {
      private UnbackedMemberById() {
      }

      public UnbackedAnnotatedMember apply(UnbackedMemberIdentifier identifier) {
         return this.findMatchingMember(identifier.getType(), identifier.getMemberId());
      }

      private UnbackedAnnotatedMember findMatchingMember(UnbackedAnnotatedType type, String id) {
         Iterator var3 = type.getFields().iterator();

         AnnotatedField field;
         do {
            if (!var3.hasNext()) {
               var3 = type.getMethods().iterator();

               AnnotatedMethod method;
               do {
                  if (!var3.hasNext()) {
                     var3 = type.getConstructors().iterator();

                     AnnotatedConstructor constructor;
                     do {
                        if (!var3.hasNext()) {
                           throw BeanLogger.LOG.unableToLoadMember(id);
                        }

                        constructor = (AnnotatedConstructor)var3.next();
                     } while(!id.equals(AnnotatedTypes.createConstructorId(constructor.getJavaMember(), constructor.getAnnotations(), constructor.getParameters())));

                     return (UnbackedAnnotatedMember)Reflections.cast(constructor);
                  }

                  method = (AnnotatedMethod)var3.next();
               } while(!id.equals(AnnotatedTypes.createMethodId(method.getJavaMember(), method.getAnnotations(), method.getParameters())));

               return (UnbackedAnnotatedMember)Reflections.cast(method);
            }

            field = (AnnotatedField)var3.next();
         } while(!id.equals(AnnotatedTypes.createFieldId(field)));

         return (UnbackedAnnotatedMember)Reflections.cast(field);
      }

      // $FF: synthetic method
      UnbackedMemberById(Object x0) {
         this();
      }
   }

   private static class MemberKey {
      private final EnhancedAnnotatedType type;
      private final AnnotatedMember member;
      private final int hashCode;

      public MemberKey(EnhancedAnnotatedType type, AnnotatedMember member) {
         this.type = type;
         this.member = member;
         this.hashCode = Objects.hash(new Object[]{type, member});
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof MemberKey)) {
            return false;
         } else {
            MemberKey that = (MemberKey)obj;
            return Objects.equals(this.type, that.type) && Objects.equals(this.member, that.member);
         }
      }
   }
}
