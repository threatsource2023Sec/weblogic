package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class ImportDefinition implements BeanMetadataElement {
   private final String importedResource;
   @Nullable
   private final Resource[] actualResources;
   @Nullable
   private final Object source;

   public ImportDefinition(String importedResource) {
      this(importedResource, (Resource[])null, (Object)null);
   }

   public ImportDefinition(String importedResource, @Nullable Object source) {
      this(importedResource, (Resource[])null, source);
   }

   public ImportDefinition(String importedResource, @Nullable Resource[] actualResources, @Nullable Object source) {
      Assert.notNull(importedResource, (String)"Imported resource must not be null");
      this.importedResource = importedResource;
      this.actualResources = actualResources;
      this.source = source;
   }

   public final String getImportedResource() {
      return this.importedResource;
   }

   @Nullable
   public final Resource[] getActualResources() {
      return this.actualResources;
   }

   @Nullable
   public final Object getSource() {
      return this.source;
   }
}
