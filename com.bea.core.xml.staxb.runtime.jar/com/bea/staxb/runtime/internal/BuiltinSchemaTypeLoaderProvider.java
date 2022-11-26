package com.bea.staxb.runtime.internal;

import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlBeans;

final class BuiltinSchemaTypeLoaderProvider implements SchemaTypeLoaderProvider {
   private final SchemaTypeLoader builtinLoader = XmlBeans.getBuiltinTypeSystem();
   private static final BuiltinSchemaTypeLoaderProvider INSTANCE = new BuiltinSchemaTypeLoaderProvider();

   private BuiltinSchemaTypeLoaderProvider() {
      assert this.builtinLoader != null;

   }

   static SchemaTypeLoaderProvider getInstance() {
      return INSTANCE;
   }

   public SchemaTypeLoader getSchemaTypeLoader() {
      return this.builtinLoader;
   }
}
