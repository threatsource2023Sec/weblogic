package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.Proxy;

public abstract class StoreCollectionFieldStrategy extends ContainerFieldStrategy {
   protected abstract ForeignKey getJoinForeignKey(ClassMapping var1);

   protected abstract void selectElement(Select var1, ClassMapping var2, JDBCStore var3, JDBCFetchConfiguration var4, int var5, Joins var6);

   protected abstract Object loadElement(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, Result var4, Joins var5) throws SQLException;

   protected abstract Joins joinElementRelation(Joins var1, ClassMapping var2);

   protected abstract Joins join(Joins var1, ClassMapping var2);

   protected abstract Proxy newLRSProxy();

   protected Collection toCollection(Object val) {
      return (Collection)(this.field.getTypeCode() == 12 ? (Collection)val : JavaTypes.toList(val, this.field.getElement().getType(), false));
   }

   protected void add(JDBCStore store, Object coll, Object obj) {
      ((Collection)coll).add(obj);
   }

   private ClassMapping getDefaultElementMapping(boolean traverse) {
      ClassMapping[] elems = this.getIndependentElementMappings(traverse);
      return elems.length == 0 ? null : elems[0];
   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      if (this.field.isLRS()) {
         return 0;
      } else if (type == 2) {
         return Math.max(1, this.getIndependentElementMappings(true).length);
      } else if (type != 0 && type != 1) {
         return 0;
      } else if (this.getIndependentElementMappings(true).length > 1) {
         return 0;
      } else {
         return type != 0 && !store.getDBDictionary().canOuterJoin(sel.getJoinSyntax(), this.getJoinForeignKey(this.getDefaultElementMapping(false))) ? 0 : 1;
      }
   }

   public void selectEagerParallel(SelectExecutor sel, final OpenJPAStateManager sm, final JDBCStore store, final JDBCFetchConfiguration fetch, final int eagerMode) {
      if (!(sel instanceof Union)) {
         this.selectEager((Select)sel, this.getDefaultElementMapping(true), sm, store, fetch, eagerMode, true, false);
      } else {
         final ClassMapping[] elems = this.getIndependentElementMappings(true);
         Union union = (Union)sel;
         if (fetch.getSubclassFetchMode(this.field.getElementMapping().getTypeMapping()) != 1) {
            union.abortUnion();
         }

         union.select(new Union.Selector() {
            public void select(Select sel, int idx) {
               StoreCollectionFieldStrategy.this.selectEager(sel, elems[idx], sm, store, fetch, eagerMode, true, false);
            }
         });
      }

   }

   public void selectEagerJoin(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      boolean outer = this.field.getNullValue() != 2;
      if (fetch.hasFetchInnerJoin(this.field.getFullName(false))) {
         outer = false;
      }

      this.selectEager(sel, this.getDefaultElementMapping(true), sm, store, fetch, 1, false, outer);
   }

   public boolean isEagerSelectToMany() {
      return true;
   }

   private void selectEager(Select sel, ClassMapping elem, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode, boolean selectOid, boolean outer) {
      if (eagerMode == 2) {
         if (sel.hasJoin(true)) {
            sel.setDistinct(true);
         } else if (!sel.isDistinct()) {
            sel.setDistinct(false);
         }
      }

      if (selectOid) {
         sel.orderByPrimaryKey(this.field.getDefiningMapping(), true, true);
      }

      Joins joins = sel.newJoins().setVariable("*");
      joins = this.join(joins, elem);
      if (this.field.getOrderColumn() != null || this.field.getOrders().length > 0 || !selectOid) {
         if (outer) {
            joins = sel.outer(joins);
         }

         if (!selectOid) {
            Column[] refs = this.getJoinForeignKey(elem).getColumns();
            sel.orderBy(refs, true, joins, true);
         }

         this.field.orderLocal(sel, elem, joins);
      }

      joins = this.joinElementRelation(joins, elem);
      if (outer) {
         joins = sel.outer(joins);
      }

      this.field.orderRelation(sel, elem, joins);
      this.selectElement(sel, elem, store, fetch, eagerMode, joins);
   }

   public Object loadEagerParallel(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Object res) throws SQLException {
      Map rels;
      if (res instanceof Result) {
         rels = this.processEagerParallelResult(sm, store, fetch, (Result)res);
      } else {
         rels = (Map)res;
      }

      Object coll = rels.remove(sm.getObjectId());
      if (this.field.getTypeCode() == 11) {
         sm.storeObject(this.field.getIndex(), JavaTypes.toArray((Collection)coll, this.field.getElement().getType()));
      } else {
         if (coll == null) {
            coll = sm.newProxy(this.field.getIndex());
         }

         sm.storeObject(this.field.getIndex(), coll);
      }

      return rels;
   }

   private Map processEagerParallelResult(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      ClassMapping elem = this.getDefaultElementMapping(true);
      Joins dataJoins = res.newJoins().setVariable("*");
      dataJoins = this.join(dataJoins, elem);
      dataJoins = this.joinElementRelation(dataJoins, elem);
      Joins orderJoins = null;
      if (this.field.getOrderColumn() != null) {
         orderJoins = res.newJoins().setVariable("*");
         orderJoins = this.join(orderJoins, elem);
      }

      Map rels = new HashMap();
      ClassMapping ownerMapping = this.field.getDefiningMapping();
      Object oid = null;
      Object coll = null;
      int seq = 0;

      while(res.next()) {
         Object nextOid = this.getNextObjectId(ownerMapping, store, res, oid);
         if (nextOid != oid) {
            if (seq != 0 && coll instanceof Proxy) {
               ((Proxy)coll).getChangeTracker().setNextSequence(seq);
            }

            oid = nextOid;
            seq = 0;
            if (this.field.getTypeCode() == 11) {
               coll = new ArrayList();
            } else {
               coll = sm.newProxy(this.field.getIndex());
            }

            rels.put(nextOid, coll);
         }

         if (this.field.getOrderColumn() != null) {
            seq = res.getInt(this.field.getOrderColumn(), orderJoins) + 1;
         }

         this.setMappedBy(oid, sm, coll, res);
         Object val = this.loadElement((OpenJPAStateManager)null, store, fetch, res, dataJoins);
         this.add(store, coll, val);
      }

      res.close();
      return rels;
   }

   private void setMappedBy(Object oid, OpenJPAStateManager sm, Object coll, Result res) {
      FieldMapping mappedByFieldMapping = this.field.getMappedByMapping();
      PersistenceCapable mappedByValue = null;
      if (mappedByFieldMapping != null) {
         ValueMapping val = mappedByFieldMapping.getValueMapping();
         ClassMetaData decMeta = val.getTypeMetaData();
         if (decMeta == null) {
            return;
         }

         if (oid.equals(sm.getObjectId())) {
            mappedByValue = sm.getPersistenceCapable();
            res.setMappedByFieldMapping(mappedByFieldMapping);
            res.setMappedByValue(mappedByValue);
         } else if (coll instanceof Collection && ((Collection)coll).size() > 0) {
            PersistenceCapable pc = (PersistenceCapable)((Collection)coll).iterator().next();
            OpenJPAStateManager sm1 = (OpenJPAStateManager)pc.pcGetStateManager();
            ClassMapping clm = (ClassMapping)sm1.getMetaData();
            FieldMapping fm = (FieldMapping)clm.getField(mappedByFieldMapping.getName());
            if (fm == mappedByFieldMapping) {
               res.setMappedByValue(sm1.fetchObject(fm.getIndex()));
            }
         } else {
            res.setMappedByValue((Object)null);
         }
      }

   }

   private Object getNextObjectId(ClassMapping owner, JDBCStore store, Result res, Object oid) throws SQLException {
      if (oid != null && owner.getIdentityType() == 1 && owner.isPrimaryKeyObjectId(true)) {
         long nid = res.getLong(owner.getPrimaryKeyColumns()[0]);
         long id = ((Id)oid).getId();
         return nid == id ? oid : store.newDataStoreId(nid, owner, true);
      } else {
         Object noid = owner.getObjectId(store, res, (ForeignKey)null, true, (Joins)null);
         if (noid == null) {
            return null;
         } else {
            return noid.equals(oid) ? oid : noid;
         }
      }
   }

   public void loadEagerJoin(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      Object coll;
      if (this.field.getTypeCode() == 11) {
         coll = new ArrayList();
      } else {
         coll = sm.newProxy(this.field.getIndex());
      }

      Joins dataJoins = null;
      Joins refJoins = res.newJoins().setVariable("*");
      this.join(refJoins, false);
      ClassMapping ownerMapping = this.field.getDefiningMapping();
      Object ref = null;
      int seq = 0;
      int typeIdx = res.indexOf();
      int i = 0;

      while(true) {
         ref = this.getNextRef(ownerMapping, store, res, ref, refJoins);
         if (ref == null) {
            if (seq != 0 && coll instanceof Proxy) {
               ((Proxy)coll).getChangeTracker().setNextSequence(seq);
            }

            if (i != 0) {
               res.pushBack();
            }
            break;
         }

         if (dataJoins == null) {
            dataJoins = res.newJoins().setVariable("*");
            dataJoins = this.join(dataJoins, false);
            dataJoins = this.joinRelation(dataJoins, false, false);
         }

         if (this.field.getOrderColumn() != null) {
            seq = res.getInt(this.field.getOrderColumn(), refJoins) + 1;
         }

         res.setBaseMapping((ClassMapping)null);
         this.add(store, coll, this.loadElement(sm, store, fetch, res, dataJoins));
         if (!res.next() || res.indexOf() != typeIdx) {
            res.pushBack();
            break;
         }

         ++i;
      }

      if (this.field.getTypeCode() == 11) {
         sm.storeObject(this.field.getIndex(), JavaTypes.toArray((Collection)coll, this.field.getElement().getType()));
      } else {
         sm.storeObject(this.field.getIndex(), coll);
      }

   }

   private Object getNextRef(ClassMapping mapping, JDBCStore store, Result res, Object ref, Joins refJoins) throws SQLException {
      Column[] cols = this.getJoinForeignKey(this.getDefaultElementMapping(false)).getColumns();
      Object val;
      if (cols.length != 1) {
         Object[] refs = (Object[])((Object[])ref);
         if (refs == null) {
            refs = new Object[cols.length];
         }

         for(int i = 0; i < cols.length; ++i) {
            val = res.getObject(cols[i], (Object)null, refJoins);
            if (val == null) {
               return null;
            }

            if (refs[i] != null && !val.equals(refs[i])) {
               return null;
            }

            refs[i] = val;
         }

         return refs;
      } else {
         val = res.getObject(cols[0], (Object)null, refJoins);
         return val != null && (ref == null || val.equals(ref)) ? val : null;
      }
   }

   public void load(final OpenJPAStateManager sm, final JDBCStore store, final JDBCFetchConfiguration fetch) throws SQLException {
      if (this.field.isLRS()) {
         Proxy coll = this.newLRSProxy();
         if (this.field.getOrderColumn() != null) {
            Select sel = store.getSQLFactory().newSelect();
            sel.setAggregate(true);
            StringBuffer sql = new StringBuffer();
            sql.append("MAX(").append(sel.getColumnAlias(this.field.getOrderColumn())).append(")");
            sel.select((String)sql.toString(), (Object)this.field);
            ClassMapping rel = this.getDefaultElementMapping(false);
            sel.whereForeignKey(this.getJoinForeignKey(rel), sm.getObjectId(), this.field.getDefiningMapping(), store);
            Result res = sel.execute(store, fetch);

            try {
               res.next();
               coll.getChangeTracker().setNextSequence(res.getInt(this.field) + 1);
            } finally {
               res.close();
            }
         }

         sm.storeObjectField(this.field.getIndex(), coll);
      } else {
         final ClassMapping[] elems = this.getIndependentElementMappings(true);
         final Joins[] resJoins = new Joins[Math.max(1, elems.length)];
         Union union = store.getSQLFactory().newUnion(Math.max(1, elems.length));
         union.select(new Union.Selector() {
            public void select(Select sel, int idx) {
               ClassMapping elem = elems.length == 0 ? null : elems[idx];
               resJoins[idx] = StoreCollectionFieldStrategy.this.selectAll(sel, elem, sm, store, fetch, 2);
            }
         });
         ChangeTracker ct = null;
         Object coll;
         if (this.field.getTypeCode() == 11) {
            coll = new ArrayList();
         } else {
            coll = sm.newProxy(this.field.getIndex());
            if (coll instanceof Proxy) {
               ct = ((Proxy)coll).getChangeTracker();
            }
         }

         Result res = union.execute(store, fetch);

         try {
            int seq = -1;

            while(res.next()) {
               if (ct != null && this.field.getOrderColumn() != null) {
                  seq = res.getInt(this.field.getOrderColumn());
               }

               this.setMappedBy(sm.getObjectId(), sm, coll, res);
               this.add(store, coll, this.loadElement(sm, store, fetch, res, resJoins[res.indexOf()]));
            }

            if (ct != null && this.field.getOrderColumn() != null) {
               ct.setNextSequence(seq + 1);
            }
         } finally {
            res.close();
         }

         if (this.field.getTypeCode() == 11) {
            sm.storeObject(this.field.getIndex(), JavaTypes.toArray((Collection)coll, this.field.getElement().getType()));
         } else {
            sm.storeObject(this.field.getIndex(), coll);
         }

      }
   }

   protected Joins selectAll(Select sel, ClassMapping elem, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      sel.whereForeignKey(this.getJoinForeignKey(elem), sm.getObjectId(), this.field.getDefiningMapping(), store);
      this.field.orderLocal(sel, elem, (Joins)null);
      Joins joins = this.joinElementRelation(sel.newJoins(), elem);
      this.field.orderRelation(sel, elem, joins);
      this.selectElement(sel, elem, store, fetch, eagerMode, joins);
      return joins;
   }

   public Object loadProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return this.loadElement((OpenJPAStateManager)null, store, fetch, res, joins);
   }

   protected ForeignKey getJoinForeignKey() {
      return this.getJoinForeignKey(this.getDefaultElementMapping(false));
   }
}
