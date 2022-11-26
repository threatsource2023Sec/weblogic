package org.apache.openjpa.jdbc.kernel.exps;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.XMLMetaData;
import org.apache.openjpa.util.UserException;

public class PCPath extends AbstractVal implements JDBCPath {
   private static final int PATH = 0;
   private static final int BOUND_VAR = 1;
   private static final int UNBOUND_VAR = 2;
   private static final int UNACCESSED_VAR = 3;
   private static final int XPATH = 4;
   private static final Localizer _loc = Localizer.forPackage(PCPath.class);
   private final ClassMapping _candidate;
   private ClassMapping _class = null;
   private LinkedList _actions = null;
   private boolean _key = false;
   private int _type = 0;
   private String _varName = null;
   private Class _cast = null;
   private boolean _cid = false;
   private FieldMetaData _xmlfield = null;

   public PCPath(ClassMapping type) {
      this._candidate = type;
   }

   public PCPath(ClassMapping candidate, Variable var) {
      this._candidate = candidate;
      this._actions = new LinkedList();
      PCPath other = var.getPCPath();
      Action action = new Action();
      if (other == null) {
         this._type = 2;
         action.op = 5;
         action.data = var;
      } else {
         this._type = 3;
         this._actions.addAll(other._actions);
         this._key = other._key;
         action.op = 3;
         action.data = var.getName();
      }

      this._actions.add(action);
      this._cast = var.getType();
   }

   public PCPath(SubQ sub) {
      this._candidate = sub.getCandidate();
      this._actions = new LinkedList();
      Action action = new Action();
      action.op = 4;
      action.data = sub.getCandidateAlias();
      this._actions.add(action);
      this._cast = sub.getType();
      this._varName = sub.getCandidateAlias();
   }

   public void addVariableAction(Variable var) {
      this._varName = var.getName();
   }

   public boolean isUnaccessedVariable() {
      return this._type == 3;
   }

   public boolean isVariablePath() {
      return this._type != 0;
   }

   public synchronized void setContainsId(String id) {
      if (!this._cid) {
         Action action = new Action();
         action.op = 3;
         action.data = id;
         if (this._actions == null) {
            this._actions = new LinkedList();
         }

         this._actions.add(action);
         this._cid = true;
      }
   }

   public ClassMetaData getMetaData() {
      return this._class;
   }

   public void setMetaData(ClassMetaData meta) {
      this._class = (ClassMapping)meta;
   }

   public boolean isKey() {
      return this._key;
   }

   public boolean isXPath() {
      return this._type == 4;
   }

   public String getXPath() {
      StringBuffer xpath = new StringBuffer();
      Iterator itr = this._actions.iterator();

      Action action;
      do {
         action = (Action)itr.next();
      } while(action.op != 7);

      while(itr.hasNext()) {
         action = (Action)itr.next();
         if (((XMLMetaData)action.data).getXmlname() != null) {
            xpath.append(((XMLMetaData)action.data).getXmlname());
         } else {
            xpath.append("*");
         }

         if (itr.hasNext()) {
            xpath.append("/");
         }
      }

      return xpath.toString();
   }

   public String getPath() {
      if (this._actions == null) {
         return this._varName == null ? "" : this._varName + ".";
      } else {
         StringBuffer path = new StringBuffer();

         for(Iterator itr = this._actions.iterator(); itr.hasNext(); path.append('.')) {
            Action action = (Action)itr.next();
            if (action.op != 3 && action.op != 4) {
               if (action.op == 5) {
                  path.append(((Variable)action.data).getName());
               } else {
                  path.append(((FieldMapping)action.data).getName());
               }
            } else {
               path.append(action.data);
            }
         }

         if (this._varName != null) {
            path.append(this._varName).append('.');
         }

         return path.toString();
      }
   }

   public ClassMapping getClassMapping(ExpState state) {
      PathExpState pstate = (PathExpState)state;
      if (pstate.field == null) {
         return this._class;
      } else if (this._key) {
         return pstate.field.getKey().getTypeCode() == 15 ? pstate.field.getKeyMapping().getTypeMapping() : null;
      } else if (pstate.field.getElement().getTypeCode() == 15) {
         return pstate.field.getElementMapping().getTypeMapping();
      } else {
         return pstate.field.getTypeCode() == 15 ? pstate.field.getTypeMapping() : null;
      }
   }

   public FieldMapping getFieldMapping(ExpState state) {
      return ((PathExpState)state).field;
   }

   public Column[] getColumns(ExpState state) {
      PathExpState pstate = (PathExpState)state;
      if (pstate.cols == null) {
         pstate.cols = this.calculateColumns(pstate);
      }

      return pstate.cols;
   }

   private Column[] calculateColumns(PathExpState pstate) {
      if (!this._key) {
         if (pstate.field != null) {
            switch (pstate.field.getTypeCode()) {
               case 11:
               case 12:
               case 13:
                  ValueMapping elem = pstate.field.getElementMapping();
                  if (pstate.joinedRel && elem.getTypeCode() == 15) {
                     return elem.getTypeMapping().getPrimaryKeyColumns();
                  } else {
                     if (elem.getColumns().length > 0) {
                        return elem.getColumns();
                     }

                     return pstate.field.getColumns();
                  }
               case 14:
               default:
                  return pstate.field.getColumns();
               case 15:
                  return pstate.joinedRel ? pstate.field.getTypeMapping().getPrimaryKeyColumns() : pstate.field.getColumns();
            }
         } else {
            return this._class == null ? Schemas.EMPTY_COLUMNS : this._class.getPrimaryKeyColumns();
         }
      } else {
         if (!pstate.joinedRel && pstate.field.getKey().getValueMappedBy() != null) {
            this.joinRelation(pstate, this._key, false, false);
         } else if (pstate.joinedRel && pstate.field.getKey().getTypeCode() == 15) {
            return pstate.field.getKeyMapping().getTypeMapping().getPrimaryKeyColumns();
         }

         return pstate.field.getKeyMapping().getColumns();
      }
   }

   public boolean isVariable() {
      if (this._actions == null) {
         return false;
      } else {
         Action action = (Action)this._actions.getLast();
         return action.op == 5 || action.op == 3;
      }
   }

   public void get(FieldMetaData field, boolean nullTraversal) {
      if (this._actions == null) {
         this._actions = new LinkedList();
      }

      Action action = new Action();
      action.op = nullTraversal ? 1 : 0;
      action.data = field;
      this._actions.add(action);
      if (this._type == 3) {
         this._type = 1;
      }

      this._cast = null;
      this._key = false;
   }

   public void get(FieldMetaData fmd, XMLMetaData meta) {
      if (this._actions == null) {
         this._actions = new LinkedList();
      }

      Action action = new Action();
      action.op = 7;
      action.data = meta;
      this._actions.add(action);
      this._cast = null;
      this._key = false;
      this._type = 4;
      this._xmlfield = fmd;
   }

   public void get(XMLMetaData meta, String name) {
      Action action = new Action();
      action.op = 7;
      action.data = meta.getFieldMapping(name);
      this._actions.add(action);
      this._cast = null;
      this._key = false;
      this._type = 4;
   }

   public XMLMetaData getXmlMapping() {
      Action act = (Action)this._actions.getLast();
      return act != null ? (XMLMetaData)act.data : null;
   }

   public synchronized void getKey() {
      if (!this._cid) {
         Action action = (Action)this._actions.getLast();
         action.op = 2;
         this._cast = null;
         this._key = true;
      }
   }

   public FieldMetaData last() {
      Action act = this.lastFieldAction();
      return act == null ? null : (this.isXPath() ? this._xmlfield : (FieldMetaData)act.data);
   }

   private Action lastFieldAction() {
      if (this._actions == null) {
         return null;
      } else if (this.isXPath()) {
         return (Action)this._actions.getLast();
      } else {
         ListIterator itr = this._actions.listIterator(this._actions.size());

         Action prev;
         do {
            if (!itr.hasPrevious()) {
               return null;
            }

            prev = (Action)itr.previous();
         } while(prev.op != 0 && prev.op != 1 && prev.op != 2);

         return prev;
      }
   }

   public Class getType() {
      if (this._cast != null) {
         return this._cast;
      } else {
         Action act = this.lastFieldAction();
         if (act != null && act.op == 7) {
            return ((XMLMetaData)act.data).getType();
         } else {
            FieldMetaData fld = act == null ? null : (FieldMetaData)act.data;
            boolean key = act != null && act.op == 2;
            if (fld != null) {
               switch (fld.getDeclaredTypeCode()) {
                  case 11:
                     if (fld.getDeclaredType() != byte[].class && fld.getDeclaredType() != Byte[].class && fld.getDeclaredType() != char[].class && fld.getDeclaredType() != Character[].class) {
                        return fld.getElement().getDeclaredType();
                     }

                     return fld.getDeclaredType();
                  case 12:
                     return fld.getElement().getDeclaredType();
                  case 13:
                     if (key) {
                        return fld.getKey().getDeclaredType();
                     }

                     return fld.getElement().getDeclaredType();
                  default:
                     return fld.getDeclaredType();
               }
            } else {
               return this._class != null ? this._class.getDescribedType() : Object.class;
            }
         }
      }
   }

   public void setImplicitType(Class type) {
      this._cast = type;
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      PathExpState pstate = new PathExpState(sel.newJoins());
      boolean key = false;
      boolean forceOuter = false;
      ClassMapping rel = this._candidate;
      Iterator itr = this._actions == null ? null : this._actions.iterator();

      while(itr != null && itr.hasNext()) {
         Action action = (Action)itr.next();
         if (action.op == 3) {
            pstate.joins = pstate.joins.setVariable((String)action.data);
         } else if (action.op == 4) {
            pstate.joins = pstate.joins.setSubselect((String)action.data);
         } else if (action.op == 5) {
            Variable var = (Variable)action.data;
            rel = (ClassMapping)var.getMetaData();
            pstate.joins = pstate.joins.setVariable(var.getName());
            pstate.joins = pstate.joins.crossJoin(this._candidate.getTable(), rel.getTable());
         } else {
            FieldMapping field = action.op == 7 ? (FieldMapping)this._xmlfield : (FieldMapping)action.data;
            if (pstate.field != null) {
               if (!itr.hasNext() && (flags & 4) == 0 && isJoinedField(pstate.field, key, field)) {
                  pstate.cmpfield = field;
                  break;
               }

               rel = this.traverseField(pstate, key, forceOuter, false);
            }

            key = action.op == 2;
            forceOuter |= action.op == 1;
            pstate.field = field;
            ClassMapping owner = pstate.field.getDefiningMapping();
            if (pstate.field.getManagement() != 3) {
               throw new UserException(_loc.get("non-pers-field", (Object)pstate.field));
            }

            if (rel != owner && rel != null) {
               ClassMapping from;
               ClassMapping to;
               if (rel.getDescribedType().isAssignableFrom(owner.getDescribedType())) {
                  from = owner;
                  to = rel;
               } else {
                  from = rel;
                  to = owner;
               }

               while(from != null && from != to) {
                  FieldMapping cast = from.getFieldMapping(pstate.field.getName());
                  if (cast != null) {
                     pstate.field = cast;
                  }

                  pstate.joins = from.joinSuperclass(pstate.joins, false);
                  from = from.getJoinablePCSuperclassMapping();
               }
            }

            if (action.op == 7) {
               break;
            }
         }
      }

      if (this._varName != null) {
         pstate.joins = pstate.joins.setVariable(this._varName);
      }

      if ((flags & 2) == 0) {
         this.traverseField(pstate, key, forceOuter, true);
      }

      pstate.joinedRel = false;
      if ((flags & 4) != 0) {
         this.joinRelation(pstate, key, forceOuter || (flags & 8) != 0, false);
      }

      return pstate;
   }

   private static boolean isJoinedField(FieldMapping src, boolean key, FieldMapping target) {
      Object vm;
      switch (src.getTypeCode()) {
         case 11:
         case 12:
            vm = src.getElementMapping();
            break;
         case 13:
            vm = key ? src.getKeyMapping() : src.getElementMapping();
            break;
         default:
            vm = src;
      }

      if (((ValueMapping)vm).getJoinDirection() != 0) {
         return false;
      } else {
         ForeignKey fk = ((ValueMapping)vm).getForeignKey();
         if (fk == null) {
            return false;
         } else {
            Column[] rels = fk.getColumns();
            Column[] pks = target.getColumns();
            if (rels.length != pks.length) {
               return false;
            } else {
               for(int i = 0; i < rels.length; ++i) {
                  if (fk.getPrimaryKeyColumn(rels[i]) != pks[i]) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   private ClassMapping traverseField(PathExpState pstate, boolean key, boolean forceOuter, boolean last) {
      if (pstate.field == null) {
         return null;
      } else {
         if (key) {
            pstate.joins = pstate.field.joinKey(pstate.joins, forceOuter);
         } else {
            pstate.joins = pstate.field.join(pstate.joins, forceOuter);
         }

         if (!last) {
            this.joinRelation(pstate, key, forceOuter, true);
         }

         if (key) {
            return pstate.field.getKeyMapping().getTypeMapping();
         } else {
            return pstate.field.getElement().getTypeCode() == 15 ? pstate.field.getElementMapping().getTypeMapping() : pstate.field.getTypeMapping();
         }
      }
   }

   private void joinRelation(PathExpState pstate, boolean key, boolean forceOuter, boolean traverse) {
      if (pstate.field != null) {
         if (key) {
            pstate.joins = pstate.field.joinKeyRelation(pstate.joins, forceOuter, traverse);
         } else {
            pstate.joins = pstate.field.joinRelation(pstate.joins, forceOuter, traverse);
         }

         pstate.joinedRel = true;
      }
   }

   public Object toDataStoreValue(Select sel, ExpContext ctx, ExpState state, Object val) {
      PathExpState pstate = (PathExpState)state;
      FieldMapping field = pstate.cmpfield != null ? pstate.cmpfield : pstate.field;
      if (this.isXPath()) {
         return val;
      } else if (field != null) {
         if (this._key) {
            return field.toKeyDataStoreValue(val, ctx.store);
         } else if (field.getElement().getDeclaredTypeCode() != 8) {
            return field.toDataStoreValue(val, ctx.store);
         } else {
            val = field.getExternalValue(val, ctx.store.getContext());
            return field.toDataStoreValue(val, ctx.store);
         }
      } else {
         return this._class.toDataStoreValue(val, this._class.getPrimaryKeyColumns(), ctx.store);
      }
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      this.selectColumns(sel, ctx, state, pks);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      ClassMapping mapping = this.getClassMapping(state);
      PathExpState pstate = (PathExpState)state;
      if (mapping != null && pstate.joinedRel) {
         if (pks) {
            sel.select(mapping.getPrimaryKeyColumns(), pstate.joins);
         } else {
            int subs = this._type == 2 ? 1 : 3;
            sel.select(mapping, subs, ctx.store, ctx.fetch, 0, sel.outer(pstate.joins));
         }
      } else {
         sel.select(this.getColumns(state), pstate.joins);
      }

   }

   public void groupBy(Select sel, ExpContext ctx, ExpState state) {
      ClassMapping mapping = this.getClassMapping(state);
      PathExpState pstate = (PathExpState)state;
      if (mapping != null && pstate.joinedRel) {
         int subs = this._type == 2 ? 1 : 3;
         sel.groupBy(mapping, subs, ctx.store, ctx.fetch, sel.outer(pstate.joins));
      } else {
         sel.groupBy(this.getColumns(state), sel.outer(pstate.joins));
      }

   }

   public void orderBy(Select sel, ExpContext ctx, ExpState state, boolean asc) {
      sel.orderBy(this.getColumns(state), asc, sel.outer(state.joins), false);
   }

   public Object load(ExpContext ctx, ExpState state, Result res) throws SQLException {
      return this.load(ctx, state, res, false);
   }

   Object load(ExpContext ctx, ExpState state, Result res, boolean pks) throws SQLException {
      ClassMapping mapping = this.getClassMapping(state);
      PathExpState pstate = (PathExpState)state;
      if (mapping == null || pstate.field != null && pstate.field.isEmbedded()) {
         Object ret;
         if (this._key) {
            ret = pstate.field.loadKeyProjection(ctx.store, ctx.fetch, res, pstate.joins);
         } else {
            ret = pstate.field.loadProjection(ctx.store, ctx.fetch, res, pstate.joins);
         }

         if (this._cast != null) {
            ret = Filters.convert(ret, this._cast);
         }

         return ret;
      } else {
         return pks ? mapping.getObjectId(ctx.store, res, (ForeignKey)null, true, pstate.joins) : res.load(mapping, ctx.store, ctx.fetch, pstate.joins);
      }
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return this.getColumns(state).length;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      Column col = this.getColumns(state)[index];
      if (sel == null) {
         sql.append(col.getName());
      } else if (this._type == 4) {
         sql.append(this.getXPath());
      } else {
         sql.append(sel.getColumnAlias(col, state.joins));
      }

   }

   public void appendIsEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      PathExpState pstate = (PathExpState)state;
      if (pstate.field == null) {
         sql.append("1 <> 1");
      } else {
         pstate.field.appendIsEmpty(sql, sel, pstate.joins);
      }

   }

   public void appendIsNotEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      PathExpState pstate = (PathExpState)state;
      if (pstate.field == null) {
         sql.append("1 <> 1");
      } else {
         pstate.field.appendIsNotEmpty(sql, sel, pstate.joins);
      }

   }

   public void appendSize(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      PathExpState pstate = (PathExpState)state;
      if (pstate.field == null) {
         sql.append("1");
      } else {
         pstate.field.appendSize(sql, sel, pstate.joins);
      }

   }

   public void appendIsNull(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      PathExpState pstate = (PathExpState)state;
      if (pstate.field == null) {
         sql.append("1 <> 1");
      } else {
         pstate.field.appendIsNull(sql, sel, pstate.joins);
      }

   }

   public void appendIsNotNull(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      PathExpState pstate = (PathExpState)state;
      if (pstate.field == null) {
         sql.append("1 = 1");
      } else {
         pstate.field.appendIsNotNull(sql, sel, pstate.joins);
      }

   }

   public int hashCode() {
      return this._actions == null ? this._candidate.hashCode() : this._candidate.hashCode() ^ this._actions.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof PCPath)) {
         return false;
      } else {
         PCPath path = (PCPath)other;
         return ObjectUtils.equals(this._candidate, path._candidate) && ObjectUtils.equals(this._actions, path._actions);
      }
   }

   private static class Action implements Serializable {
      public static final int GET = 0;
      public static final int GET_OUTER = 1;
      public static final int GET_KEY = 2;
      public static final int VAR = 3;
      public static final int SUBQUERY = 4;
      public static final int UNBOUND_VAR = 5;
      public static final int CAST = 6;
      public static final int GET_XPATH = 7;
      public int op;
      public Object data;

      private Action() {
         this.op = -1;
         this.data = null;
      }

      public String toString() {
         return this.op + "|" + this.data;
      }

      public int hashCode() {
         return this.data == null ? this.op : this.op ^ this.data.hashCode();
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else {
            Action a = (Action)other;
            return this.op == a.op && ObjectUtils.equals(this.data, a.data);
         }
      }

      // $FF: synthetic method
      Action(Object x0) {
         this();
      }
   }

   public static class PathExpState extends ExpState {
      public FieldMapping field = null;
      public FieldMapping cmpfield = null;
      public Column[] cols = null;
      public boolean joinedRel = false;

      public PathExpState(Joins joins) {
         super(joins);
      }
   }
}
