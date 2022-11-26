package org.hibernate.validator.internal.metadata.core;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Member;
import java.util.Map;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class AnnotationProcessingOptionsImpl implements AnnotationProcessingOptions {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Map ignoreAnnotationDefaults = CollectionHelper.newHashMap();
   private final Map annotationIgnoresForClasses = CollectionHelper.newHashMap();
   private final Map annotationIgnoredForMembers = CollectionHelper.newHashMap();
   private final Map annotationIgnoresForReturnValues = CollectionHelper.newHashMap();
   private final Map annotationIgnoresForCrossParameter = CollectionHelper.newHashMap();
   private final Map annotationIgnoresForMethodParameter = CollectionHelper.newHashMap();

   public boolean areMemberConstraintsIgnoredFor(Member member) {
      Class clazz = member.getDeclaringClass();
      return this.annotationIgnoredForMembers.containsKey(member) ? (Boolean)this.annotationIgnoredForMembers.get(member) : this.areAllConstraintAnnotationsIgnoredFor(clazz);
   }

   public boolean areReturnValueConstraintsIgnoredFor(Member member) {
      return this.annotationIgnoresForReturnValues.containsKey(member) ? (Boolean)this.annotationIgnoresForReturnValues.get(member) : this.areMemberConstraintsIgnoredFor(member);
   }

   public boolean areCrossParameterConstraintsIgnoredFor(Member member) {
      return this.annotationIgnoresForCrossParameter.containsKey(member) ? (Boolean)this.annotationIgnoresForCrossParameter.get(member) : this.areMemberConstraintsIgnoredFor(member);
   }

   public boolean areParameterConstraintsIgnoredFor(Member member, int index) {
      ExecutableParameterKey key = new ExecutableParameterKey(member, index);
      return this.annotationIgnoresForMethodParameter.containsKey(key) ? (Boolean)this.annotationIgnoresForMethodParameter.get(key) : this.areMemberConstraintsIgnoredFor(member);
   }

   public boolean areClassLevelConstraintsIgnoredFor(Class clazz) {
      boolean ignoreAnnotation;
      if (this.annotationIgnoresForClasses.containsKey(clazz)) {
         ignoreAnnotation = (Boolean)this.annotationIgnoresForClasses.get(clazz);
      } else {
         ignoreAnnotation = this.areAllConstraintAnnotationsIgnoredFor(clazz);
      }

      if (LOG.isDebugEnabled() && ignoreAnnotation) {
         LOG.debugf("Class level annotation are getting ignored for %s.", clazz.getName());
      }

      return ignoreAnnotation;
   }

   public void merge(AnnotationProcessingOptions annotationProcessingOptions) {
      AnnotationProcessingOptionsImpl annotationProcessingOptionsImpl = (AnnotationProcessingOptionsImpl)annotationProcessingOptions;
      this.ignoreAnnotationDefaults.putAll(annotationProcessingOptionsImpl.ignoreAnnotationDefaults);
      this.annotationIgnoresForClasses.putAll(annotationProcessingOptionsImpl.annotationIgnoresForClasses);
      this.annotationIgnoredForMembers.putAll(annotationProcessingOptionsImpl.annotationIgnoredForMembers);
      this.annotationIgnoresForReturnValues.putAll(annotationProcessingOptionsImpl.annotationIgnoresForReturnValues);
      this.annotationIgnoresForCrossParameter.putAll(annotationProcessingOptionsImpl.annotationIgnoresForCrossParameter);
      this.annotationIgnoresForMethodParameter.putAll(annotationProcessingOptionsImpl.annotationIgnoresForMethodParameter);
   }

   public void ignoreAnnotationConstraintForClass(Class clazz, Boolean b) {
      if (b == null) {
         this.ignoreAnnotationDefaults.put(clazz, Boolean.TRUE);
      } else {
         this.ignoreAnnotationDefaults.put(clazz, b);
      }

   }

   public void ignoreConstraintAnnotationsOnMember(Member member, Boolean b) {
      this.annotationIgnoredForMembers.put(member, b);
   }

   public void ignoreConstraintAnnotationsForReturnValue(Member member, Boolean b) {
      this.annotationIgnoresForReturnValues.put(member, b);
   }

   public void ignoreConstraintAnnotationsForCrossParameterConstraint(Member member, Boolean b) {
      this.annotationIgnoresForCrossParameter.put(member, b);
   }

   public void ignoreConstraintAnnotationsOnParameter(Member member, int index, Boolean b) {
      ExecutableParameterKey key = new ExecutableParameterKey(member, index);
      this.annotationIgnoresForMethodParameter.put(key, b);
   }

   public void ignoreClassLevelConstraintAnnotations(Class clazz, boolean b) {
      this.annotationIgnoresForClasses.put(clazz, b);
   }

   private boolean areAllConstraintAnnotationsIgnoredFor(Class clazz) {
      return this.ignoreAnnotationDefaults.containsKey(clazz) && (Boolean)this.ignoreAnnotationDefaults.get(clazz);
   }

   public class ExecutableParameterKey {
      private final Member member;
      private final int index;

      public ExecutableParameterKey(Member member, int index) {
         this.member = member;
         this.index = index;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            ExecutableParameterKey that = (ExecutableParameterKey)o;
            if (this.index != that.index) {
               return false;
            } else {
               if (this.member != null) {
                  if (!this.member.equals(that.member)) {
                     return false;
                  }
               } else if (that.member != null) {
                  return false;
               }

               return true;
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.member != null ? this.member.hashCode() : 0;
         result = 31 * result + this.index;
         return result;
      }
   }
}
