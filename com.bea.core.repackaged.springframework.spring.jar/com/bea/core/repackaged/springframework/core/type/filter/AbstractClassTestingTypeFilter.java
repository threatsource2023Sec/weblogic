package com.bea.core.repackaged.springframework.core.type.filter;

import com.bea.core.repackaged.springframework.core.type.ClassMetadata;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReaderFactory;
import java.io.IOException;

public abstract class AbstractClassTestingTypeFilter implements TypeFilter {
   public final boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
      return this.match(metadataReader.getClassMetadata());
   }

   protected abstract boolean match(ClassMetadata var1);
}
