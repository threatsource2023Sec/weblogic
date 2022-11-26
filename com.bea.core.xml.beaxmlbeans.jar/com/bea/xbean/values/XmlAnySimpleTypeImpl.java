package com.bea.xbean.values;

import com.bea.xbean.schema.BuiltinSchemaTypeSystem;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlObject;

public class XmlAnySimpleTypeImpl extends XmlObjectBase implements XmlAnySimpleType {
   private SchemaType _schemaType;
   String _textvalue = "";

   public XmlAnySimpleTypeImpl(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public XmlAnySimpleTypeImpl() {
      this._schemaType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   protected int get_wscanon_rule() {
      return 1;
   }

   protected String compute_text(NamespaceManager nsm) {
      return this._textvalue;
   }

   protected void set_text(String s) {
      this._textvalue = s;
   }

   protected void set_nil() {
      this._textvalue = null;
   }

   protected boolean equal_to(XmlObject obj) {
      return this._textvalue.equals(((XmlAnySimpleType)obj).getStringValue());
   }

   protected int value_hash_code() {
      return this._textvalue == null ? 0 : this._textvalue.hashCode();
   }
}
