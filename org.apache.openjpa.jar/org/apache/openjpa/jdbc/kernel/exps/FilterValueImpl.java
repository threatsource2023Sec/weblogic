package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.meta.XMLMetaData;

class FilterValueImpl implements FilterValue {
   private final Select _sel;
   private final ExpContext _ctx;
   private final ExpState _state;
   private final Val _val;

   public FilterValueImpl(Select sel, ExpContext ctx, ExpState state, Val val) {
      this._sel = sel;
      this._ctx = ctx;
      this._state = state;
      this._val = val;
   }

   public Class getType() {
      return this._val.getType();
   }

   public int length() {
      return this._val.length(this._sel, this._ctx, this._state);
   }

   public void appendTo(SQLBuffer buf) {
      this.appendTo(buf, 0);
   }

   public void appendTo(SQLBuffer buf, int index) {
      this._val.appendTo(this._sel, this._ctx, this._state, buf, index);
   }

   public String getColumnAlias(Column col) {
      return this._sel.getColumnAlias(col, this._state.joins);
   }

   public String getColumnAlias(String col, Table table) {
      return this._sel.getColumnAlias(col, table, this._state.joins);
   }

   public Object toDataStoreValue(Object val) {
      return this._val.toDataStoreValue(this._sel, this._ctx, this._state, val);
   }

   public boolean isConstant() {
      return this._val instanceof Const;
   }

   public Object getValue() {
      return this.isConstant() ? ((Const)this._val).getValue(this._ctx.params) : null;
   }

   public Object getSQLValue() {
      return this.isConstant() ? ((Const)this._val).getSQLValue(this._sel, this._ctx, this._state) : null;
   }

   public boolean isPath() {
      return this._val instanceof PCPath;
   }

   public ClassMapping getClassMapping() {
      return this.isPath() ? ((PCPath)this._val).getClassMapping(this._state) : null;
   }

   public FieldMapping getFieldMapping() {
      return this.isPath() ? ((PCPath)this._val).getFieldMapping(this._state) : null;
   }

   public PCPath getXPath() {
      return this.isPath() && ((PCPath)this._val).isXPath() ? (PCPath)this._val : null;
   }

   public XMLMetaData getXmlMapping() {
      return this.getXPath() == null ? null : this.getXPath().getXmlMapping();
   }
}
