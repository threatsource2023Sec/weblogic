package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

class Variable extends AbstractVal {
   private final String _name;
   private final Class _type;
   private ClassMetaData _meta;
   private PCPath _path = null;
   private Class _cast = null;

   public Variable(String name, Class type) {
      this._name = name;
      this._type = type;
   }

   public String getName() {
      return this._name;
   }

   public boolean isBound() {
      return this._path != null;
   }

   public PCPath getPCPath() {
      return this._path;
   }

   public void setPCPath(PCPath path) {
      this._path = path;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public boolean isVariable() {
      return true;
   }

   public Class getType() {
      return this._cast != null ? this._cast : this._type;
   }

   public void setImplicitType(Class type) {
      this._cast = type;
      if (this._path != null) {
         this._path.setImplicitType(type);
      }

   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      if (this._path != null) {
         this._path.addVariableAction(this);
         return this._path.initialize(sel, ctx, flags | 4);
      } else {
         return ExpState.NULL;
      }
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
   }

   public void groupBy(Select sel, ExpContext ctx, ExpState state) {
   }

   public void orderBy(Select sel, ExpContext ctx, ExpState state, boolean asc) {
   }

   public Object load(ExpContext ctx, ExpState state, Result res) throws SQLException {
      return null;
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
      if (this._path != null) {
         this._path.calculateValue(sel, ctx, state, other, otherState);
      }

   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 0;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
   }

   public void appendIsEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
   }

   public void appendIsNotEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
   }

   public void appendSize(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
   }

   public void appendIsNull(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
   }

   public void appendIsNotNull(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      if (this._path != null) {
         this._path.acceptVisit(visitor);
      }

      visitor.exit((Value)this);
   }
}
