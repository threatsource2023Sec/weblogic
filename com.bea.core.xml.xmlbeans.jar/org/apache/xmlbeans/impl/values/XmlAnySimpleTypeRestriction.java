package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;

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
