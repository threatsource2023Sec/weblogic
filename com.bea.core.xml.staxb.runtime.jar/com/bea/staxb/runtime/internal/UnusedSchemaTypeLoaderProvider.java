package com.bea.staxb.runtime.internal;

import com.bea.xml.SchemaTypeLoader;

final class UnusedSchemaTypeLoaderProvider implements SchemaTypeLoaderProvider {
   private static final UnusedSchemaTypeLoaderProvider INSTANCE = new UnusedSchemaTypeLoaderProvider();

   private UnusedSchemaTypeLoaderProvider() {
   }

   static SchemaTypeLoaderProvider getInstance() {
      return INSTANCE;
   }

   public SchemaTypeLoader getSchemaTypeLoader() {
      throw new IllegalStateException("schema type system not available");
   }
}
