package com.bea.core.repackaged.springframework.core.type.filter;

import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReaderFactory;
import java.io.IOException;

@FunctionalInterface
public interface TypeFilter {
   boolean match(MetadataReader var1, MetadataReaderFactory var2) throws IOException;
}
