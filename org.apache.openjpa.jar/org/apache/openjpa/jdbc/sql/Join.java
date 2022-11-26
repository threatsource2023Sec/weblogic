package org.apache.openjpa.jdbc.sql;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;

public class Join implements Cloneable, JoinSyntaxes {
   public static final int TYPE_INNER = 0;
   public static final int TYPE_OUTER = 1;
   public static final int TYPE_CROSS = 2;
   private int _type = 0;
   private int _alias1;
   private int _alias2;
   private Table _table1;
   private Table _table2;
   private ForeignKey _fk;
   private ClassMapping _target;
   private int _subs;
   private Joins _joins;
   private boolean _inverse;

   Join(Table table1, int alias1, Table table2, int alias2, ForeignKey fk, boolean inverse) {
      this._table1 = table1;
      this._alias1 = alias1;
      this._table2 = table2;
      this._alias2 = alias2;
      this._fk = fk;
      this._inverse = inverse;
   }

   private Join() {
   }

   public int getType() {
      return this._type;
   }

   public void setType(int type) {
      this._type = type;
   }

   public String getAlias1() {
      return SelectImpl.toAlias(this._alias1);
   }

   public String getAlias2() {
      return SelectImpl.toAlias(this._alias2);
   }

   int getIndex1() {
      return this._alias1;
   }

   int getIndex2() {
      return this._alias2;
   }

   public Table getTable1() {
      return this._table1;
   }

   public Table getTable2() {
      return this._table2;
   }

   public ForeignKey getForeignKey() {
      return this._fk;
   }

   public boolean isForeignKeyInversed() {
      return this._inverse;
   }

   public ClassMapping getRelationTarget() {
      return this._target;
   }

   public int getSubclasses() {
      return this._subs;
   }

   public Joins getRelationJoins() {
      return this._joins;
   }

   public void setRelation(ClassMapping target, int subs, Joins joins) {
      this._target = target;
      this._subs = subs;
      this._joins = joins;
   }

   public Join reverse() {
      Join join = new Join();
      join._type = this._type;
      join._table1 = this._table2;
      join._alias1 = this._alias2;
      join._table2 = this._table1;
      join._alias2 = this._alias1;
      join._inverse = !this._inverse;
      join._fk = this._fk;
      join._target = this._target;
      join._subs = this._subs;
      join._joins = this._joins;
      return join;
   }

   public int hashCode() {
      return this._alias1 ^ this._alias2;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof Join)) {
         return false;
      } else {
         Join j = (Join)other;
         return this._alias1 == j._alias1 && this._alias2 == j._alias2 || this._alias1 == j._alias2 && this._alias2 == j._alias1;
      }
   }

   public String toString() {
      String typeString;
      if (this._type == 2) {
         typeString = "cross";
      } else if (this._type == 0) {
         typeString = "inner";
      } else {
         typeString = "outer";
      }

      return "<" + System.identityHashCode(this) + "> t" + this._alias1 + "->t" + this._alias2 + " (" + typeString + ")";
   }

   public Object clone() {
      try {
         return super.clone();
      } catch (Exception var2) {
         return null;
      }
   }
}
