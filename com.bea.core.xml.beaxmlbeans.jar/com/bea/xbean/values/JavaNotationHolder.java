package com.bea.xbean.values;

import com.bea.xbean.schema.BuiltinSchemaTypeSystem;
import com.bea.xml.SchemaType;

public abstract class JavaNotationHolder extends XmlQNameImpl {
   public SchemaType schemaType() {
      return BuiltinSchemaTypeSystem.ST_NOTATION;
   }
}
