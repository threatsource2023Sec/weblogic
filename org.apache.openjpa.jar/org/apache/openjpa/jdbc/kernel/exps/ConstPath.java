package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.OpCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.XMLMetaData;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;

class ConstPath extends Const implements JDBCPath {
   private final Const _constant;
   private final LinkedList _actions = new LinkedList();

   public ConstPath(Const constant) {
      this._constant = constant;
   }

   public Class getType() {
      if (this._actions.isEmpty()) {
         ClassMetaData meta = this.getMetaData();
         return meta == null ? Object.class : meta.getDescribedType();
      } else {
         Object last = this._actions.getLast();
         if (last instanceof Class) {
            return (Class)last;
         } else {
            FieldMetaData fmd = (FieldMetaData)last;
            return fmd.getDeclaredType();
         }
      }
   }

   public void setImplicitType(Class type) {
      this._actions.add(type);
   }

   public void get(FieldMetaData field, boolean nullTraversal) {
      this._actions.add(field);
   }

   public void getKey() {
   }

   public FieldMetaData last() {
      ListIterator itr = this._actions.listIterator(this._actions.size());

      Object prev;
      do {
         if (!itr.hasPrevious()) {
            return null;
         }

         prev = itr.previous();
      } while(!(prev instanceof FieldMetaData));

      return (FieldMetaData)prev;
   }

   public Object getValue(Object[] params) {
      throw new InternalException();
   }

   public Object getValue(ExpContext ctx, ExpState state) {
      return ((ConstPathExpState)state).value;
   }

   public Object getSQLValue(Select sel, ExpContext ctx, ExpState state) {
      return ((ConstPathExpState)state).sqlValue;
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return new ConstPathExpState(this._constant.initialize(sel, ctx, 0));
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
      super.calculateValue(sel, ctx, state, other, otherState);
      ConstPathExpState cstate = (ConstPathExpState)state;
      this._constant.calculateValue(sel, ctx, cstate.constantState, (Val)null, (ExpState)null);
      cstate.value = this._constant.getValue(ctx, cstate.constantState);
      boolean failed = false;
      Broker tmpBroker = null;
      Iterator itr = this._actions.iterator();

      while(itr.hasNext()) {
         if (cstate.value == null) {
            failed = true;
            break;
         }

         Object action = itr.next();
         if (action instanceof Class) {
            try {
               cstate.value = Filters.convert(cstate.value, (Class)action);
            } catch (ClassCastException var17) {
               failed = true;
               break;
            }
         } else {
            OpenJPAStateManager sm = null;
            tmpBroker = null;
            if (ImplHelper.isManageable(cstate.value)) {
               sm = (OpenJPAStateManager)ImplHelper.toPersistenceCapable(cstate.value, this.getMetaData().getRepository().getConfiguration()).pcGetStateManager();
            }

            if (sm == null) {
               tmpBroker = ctx.store.getContext().getBroker();
               tmpBroker.transactional(cstate.value, false, (OpCallbacks)null);
               sm = tmpBroker.getStateManager(cstate.value);
            }

            try {
               cstate.value = sm.fetchField(((FieldMetaData)action).getIndex(), true);
            } finally {
               if (tmpBroker != null) {
                  tmpBroker.nontransactional(sm.getManagedInstance(), (OpCallbacks)null);
               }

            }
         }
      }

      if (failed) {
         cstate.value = null;
      }

      if (other != null) {
         cstate.sqlValue = other.toDataStoreValue(sel, ctx, otherState, cstate.value);
         cstate.otherLength = other.length(sel, ctx, otherState);
      } else {
         cstate.sqlValue = cstate.value;
      }

   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      ConstPathExpState cstate = (ConstPathExpState)state;
      if (cstate.otherLength > 1) {
         sql.appendValue(((Object[])((Object[])cstate.sqlValue))[index], cstate.getColumn(index));
      } else {
         sql.appendValue(cstate.sqlValue, cstate.getColumn(index));
      }

   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._constant.acceptVisit(visitor);
      visitor.exit((Value)this);
   }

   public void get(FieldMetaData fmd, XMLMetaData meta) {
   }

   public void get(XMLMetaData meta, String name) {
   }

   public XMLMetaData getXmlMapping() {
      return null;
   }

   private static class ConstPathExpState extends Const.ConstExpState {
      public final ExpState constantState;
      public Object value = null;
      public Object sqlValue = null;
      public int otherLength = 0;

      public ConstPathExpState(ExpState constantState) {
         this.constantState = constantState;
      }
   }
}
