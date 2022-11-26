package com.bea.core.repackaged.springframework.core.type.filter;

import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;

public class AnnotationTypeFilter extends AbstractTypeHierarchyTraversingFilter {
   private final Class annotationType;
   private final boolean considerMetaAnnotations;

   public AnnotationTypeFilter(Class annotationType) {
      this(annotationType, true, false);
   }

   public AnnotationTypeFilter(Class annotationType, boolean considerMetaAnnotations) {
      this(annotationType, considerMetaAnnotations, false);
   }

   public AnnotationTypeFilter(Class annotationType, boolean considerMetaAnnotations, boolean considerInterfaces) {
      super(annotationType.isAnnotationPresent(Inherited.class), considerInterfaces);
      this.annotationType = annotationType;
      this.considerMetaAnnotations = considerMetaAnnotations;
   }

   public final Class getAnnotationType() {
      return this.annotationType;
   }

   protected boolean matchSelf(MetadataReader metadataReader) {
      AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
      return metadata.hasAnnotation(this.annotationType.getName()) || this.considerMetaAnnotations && metadata.hasMetaAnnotation(this.annotationType.getName());
   }

   @Nullable
   protected Boolean matchSuperClass(String superClassName) {
      return this.hasAnnotation(superClassName);
   }

   @Nullable
   protected Boolean matchInterface(String interfaceName) {
      return this.hasAnnotation(interfaceName);
   }

   @Nullable
   protected Boolean hasAnnotation(String typeName) {
      if (Object.class.getName().equals(typeName)) {
         return false;
      } else {
         if (typeName.startsWith("java")) {
            if (!this.annotationType.getName().startsWith("java")) {
               return false;
            }

            try {
               Class clazz = ClassUtils.forName(typeName, this.getClass().getClassLoader());
               return (this.considerMetaAnnotations ? AnnotationUtils.getAnnotation((AnnotatedElement)clazz, (Class)this.annotationType) : clazz.getAnnotation(this.annotationType)) != null;
            } catch (Throwable var3) {
            }
         }

         return null;
      }
   }
}
