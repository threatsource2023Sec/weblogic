package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.asm.ClassReader;
import com.bea.core.repackaged.springframework.core.NestedIOException;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.ClassMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

final class SimpleMetadataReader implements MetadataReader {
   private final Resource resource;
   private final ClassMetadata classMetadata;
   private final AnnotationMetadata annotationMetadata;

   SimpleMetadataReader(Resource resource, @Nullable ClassLoader classLoader) throws IOException {
      InputStream is = new BufferedInputStream(resource.getInputStream());

      ClassReader classReader;
      try {
         classReader = new ClassReader(is);
      } catch (IllegalArgumentException var9) {
         throw new NestedIOException("ASM ClassReader failed to parse class file - probably due to a new Java class file version that isn't supported yet: " + resource, var9);
      } finally {
         is.close();
      }

      AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor(classLoader);
      classReader.accept(visitor, 2);
      this.annotationMetadata = visitor;
      this.classMetadata = visitor;
      this.resource = resource;
   }

   public Resource getResource() {
      return this.resource;
   }

   public ClassMetadata getClassMetadata() {
      return this.classMetadata;
   }

   public AnnotationMetadata getAnnotationMetadata() {
      return this.annotationMetadata;
   }
}
