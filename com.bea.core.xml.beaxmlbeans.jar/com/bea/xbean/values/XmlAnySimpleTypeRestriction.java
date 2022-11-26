package com.bea.xbean.values;

import com.bea.xml.SchemaType;

public class XmlAnySimpleTypeRestriction extends XmlAnySimpleTypeImpl {
   private SchemaType _schemaType;

   public XmlAnySimpleTypeRestriction(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }
}
