package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Collection;
import java.util.Map;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.Parameter;
import org.apache.openjpa.util.ImplHelper;

public class Param extends Const implements Parameter {
   private final String _name;
   private Class _type = null;
   private int _idx = -1;
   private boolean _container = false;

   public Param(String name, Class type) {
      this._name = name;
      this.setImplicitType(type);
   }

   public String getName() {
      return this._name;
   }

   public String getParameterName() {
      return this.getName();
   }

   public Class getType() {
      return this._type;
   }

   public void setImplicitType(Class type) {
      this._type = type;
      this._container = (this.getMetaData() == null || !ImplHelper.isManagedType(this.getMetaData().getRepository().getConfiguration(), type)) && (Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type));
   }

   public int getIndex() {
      return this._idx;
   }

   public void setIndex(int idx) {
      this._idx = idx;
   }

   public Object getValue(Object[] params) {
      return Filters.convert(params[this._idx], this.getType());
   }

   public Object getSQLValue(Select sel, ExpContext ctx, ExpState state) {
      return ((ParamExpState)state).sqlValue;
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return new ParamExpState();
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
      super.calculateValue(sel, ctx, state, other, otherState);
      Object val = this.getValue(ctx.params);
      ParamExpState pstate = (ParamExpState)state;
      if (other != null && !this._container) {
         pstate.sqlValue = other.toDataStoreValue(sel, ctx, otherState, val);
         pstate.otherLength = other.length(sel, ctx, otherState);
      } else if (ImplHelper.isManageable(val)) {
         ClassMapping mapping = ctx.store.getConfiguration().getMappingRepositoryInstance().getMapping(val.getClass(), ctx.store.getContext().getClassLoader(), true);
         pstate.sqlValue = mapping.toDataStoreValue(val, mapping.getPrimaryKeyColumns(), ctx.store);
         pstate.otherLength = mapping.getPrimaryKeyColumns().length;
      } else {
         pstate.sqlValue = val;
      }

   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      ParamExpState pstate = (ParamExpState)state;
      if (pstate.otherLength > 1) {
         sql.appendValue(((Object[])((Object[])pstate.sqlValue))[index], pstate.getColumn(index));
      } else {
         sql.appendValue(pstate.sqlValue, pstate.getColumn(index));
      }

   }

   public static class ParamExpState extends Const.ConstExpState {
      public Object sqlValue = null;
      public int otherLength = 1;
   }
}
