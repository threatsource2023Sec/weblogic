package org.apache.openjpa.jdbc.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.SchemaGroup;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.meta.MetaDataContext;
import org.apache.openjpa.util.MetaDataException;
import serp.util.Strings;

public abstract class MappingInfo implements Serializable {
   public static final int JOIN_NONE = 0;
   public static final int JOIN_FORWARD = 1;
   public static final int JOIN_INVERSE = 2;
   private static final Object NULL = new Object();
   private static final Localizer _loc = Localizer.forPackage(MappingInfo.class);
   private String _strategy = null;
   private List _cols = null;
   private Index _idx = null;
   private Unique _unq = null;
   private ForeignKey _fk = null;
   private boolean _canIdx = true;
   private boolean _canUnq = true;
   private boolean _canFK = true;
   private int _join = 0;
   private ColumnIO _io = null;

   public String getStrategy() {
      return this._strategy;
   }

   public void setStrategy(String strategy) {
      this._strategy = strategy;
   }

   public List getColumns() {
      return this._cols == null ? Collections.EMPTY_LIST : this._cols;
   }

   public void setColumns(List cols) {
      this._cols = cols;
   }

   public Index getIndex() {
      return this._idx;
   }

   public void setIndex(Index idx) {
      this._idx = idx;
   }

   public boolean canIndex() {
      return this._canIdx;
   }

   public void setCanIndex(boolean indexable) {
      this._canIdx = indexable;
   }

   public ForeignKey getForeignKey() {
      return this._fk;
   }

   public void setForeignKey(ForeignKey fk) {
      this._fk = fk;
      if (fk != null && this._join == 0) {
         this._join = 1;
      }

   }

   public boolean canForeignKey() {
      return this._canFK;
   }

   public void setCanForeignKey(boolean fkable) {
      this._canFK = fkable;
   }

   public Unique getUnique() {
      return this._unq;
   }

   public void setUnique(Unique unq) {
      this._unq = unq;
   }

   public boolean canUnique() {
      return this._canUnq;
   }

   public void setCanUnique(boolean uniquable) {
      this._canUnq = uniquable;
   }

   public ColumnIO getColumnIO() {
      return this._io;
   }

   public void setColumnIO(ColumnIO io) {
      this._io = io;
   }

   public int getJoinDirection() {
      return this._join;
   }

   public void setJoinDirection(int join) {
      this._join = join;
   }

   public void clear() {
      this.clear(true);
   }

   protected void clear(boolean canFlags) {
      this._strategy = null;
      this._cols = null;
      this._io = null;
      this._idx = null;
      this._unq = null;
      this._fk = null;
      this._join = 0;
      if (canFlags) {
         this._canIdx = true;
         this._canFK = true;
         this._canUnq = true;
      }

   }

   public void copy(MappingInfo info) {
      if (this._strategy == null) {
         this._strategy = info.getStrategy();
      }

      if (this._canIdx && this._idx == null) {
         if (info.getIndex() != null) {
            this._idx = info.getIndex();
         } else {
            this._canIdx = info.canIndex();
         }
      }

      if (this._canUnq && this._unq == null) {
         if (info.getUnique() != null) {
            this._unq = info.getUnique();
         } else {
            this._canUnq = info.canUnique();
         }
      }

      if (this._canFK && this._fk == null) {
         if (info.getForeignKey() != null) {
            this._fk = info.getForeignKey();
         } else {
            this._canFK = info.canForeignKey();
         }
      }

      List cols = this.getColumns();
      List icols = info.getColumns();
      if (!icols.isEmpty() && (((List)cols).isEmpty() || ((List)cols).size() == icols.size())) {
         if (((List)cols).isEmpty()) {
            cols = new ArrayList(icols.size());
         }

         for(int i = 0; i < icols.size(); ++i) {
            if (((List)cols).size() == i) {
               ((List)cols).add(new Column());
            }

            ((Column)((List)cols).get(i)).copy((Column)icols.get(i));
         }

         this.setColumns((List)cols);
      }

   }

   public boolean hasSchemaComponents() {
      return this._cols != null && !this._cols.isEmpty() || this._idx != null || this._unq != null || this._fk != null || !this._canIdx || !this._canFK || !this._canUnq;
   }

   public void assertNoSchemaComponents(MetaDataContext context, boolean die) {
      if (this._cols != null && !this._cols.isEmpty()) {
         Localizer.Message msg = _loc.get("unexpected-cols", (Object)context);
         if (die) {
            throw new MetaDataException(msg);
         } else {
            context.getRepository().getLog().warn(msg);
         }
      } else {
         this.assertNoIndex(context, die);
         this.assertNoUnique(context, die);
         this.assertNoForeignKey(context, die);
      }
   }

   public void assertStrategy(MetaDataContext context, Object contextStrat, Object expected, boolean die) {
      if (contextStrat != expected) {
         String strat;
         if (contextStrat == null) {
            if (this._strategy == null) {
               return;
            }

            if (this._strategy.equals(expected.getClass().getName())) {
               return;
            }

            if (expected instanceof Strategy && this._strategy.equals(((Strategy)expected).getAlias())) {
               return;
            }

            strat = this._strategy;
         } else if (contextStrat instanceof Strategy) {
            strat = ((Strategy)contextStrat).getAlias();
         } else {
            strat = contextStrat.getClass().getName();
         }

         Localizer.Message msg = _loc.get("unexpected-strategy", context, expected, strat);
         if (die) {
            throw new MetaDataException(msg);
         } else {
            context.getRepository().getLog().warn(msg);
         }
      }
   }

   public void assertNoIndex(MetaDataContext context, boolean die) {
      if (this._idx != null) {
         Localizer.Message msg = _loc.get("unexpected-index", (Object)context);
         if (die) {
            throw new MetaDataException(msg);
         } else {
            context.getRepository().getLog().warn(msg);
         }
      }
   }

   public void assertNoUnique(MetaDataContext context, boolean die) {
      if (this._unq != null) {
         Localizer.Message msg = _loc.get("unexpected-unique", (Object)context);
         if (die) {
            throw new MetaDataException(msg);
         } else {
            context.getRepository().getLog().warn(msg);
         }
      }
   }

   public void assertNoForeignKey(MetaDataContext context, boolean die) {
      if (this._fk != null) {
         Localizer.Message msg = _loc.get("unexpected-fk", (Object)context);
         if (die) {
            throw new MetaDataException(msg);
         } else {
            context.getRepository().getLog().warn(msg);
         }
      }
   }

   public void assertNoJoin(MetaDataContext context, boolean die) {
      boolean join = false;
      if (this._cols != null) {
         for(int i = 0; !join && i < this._cols.size(); ++i) {
            Column col = (Column)this._cols.get(i);
            if (col.getTarget() != null) {
               join = true;
            }
         }
      }

      if (join) {
         Localizer.Message msg = _loc.get("unexpected-join", (Object)context);
         if (die) {
            throw new MetaDataException(msg);
         } else {
            context.getRepository().getLog().warn(msg);
         }
      }
   }

   public Table createTable(MetaDataContext context, TableDefaults def, String schemaName, String given, boolean adapt) {
      MappingRepository repos = (MappingRepository)context.getRepository();
      if (given == null && (def == null || !adapt && !repos.getMappingDefaults().defaultMissingInfo())) {
         throw new MetaDataException(_loc.get("no-table", (Object)context));
      } else {
         if (schemaName == null) {
            schemaName = Schemas.getNewTableSchema((JDBCConfiguration)repos.getConfiguration());
         }

         SchemaGroup group = repos.getSchemaGroup();
         Schema schema = null;
         if (given == null) {
            schema = group.getSchema(schemaName);
            if (schema == null) {
               schema = group.addSchema(schemaName);
            }

            given = def.get(schema);
         }

         int dotIdx = given.lastIndexOf(46);
         String fullName;
         if (dotIdx == -1) {
            fullName = schemaName == null ? given : schemaName + "." + given;
         } else {
            fullName = given;
            schema = null;
            schemaName = given.substring(0, dotIdx);
            given = given.substring(dotIdx + 1);
         }

         Table table = group.findTable(fullName);
         if (table != null) {
            return table;
         } else if (!adapt) {
            throw new MetaDataException(_loc.get("bad-table", given, context));
         } else {
            if (schema == null) {
               schema = group.getSchema(schemaName);
               if (schema == null) {
                  schema = group.addSchema(schemaName);
               }
            }

            table = schema.getTable(given);
            if (table == null) {
               table = schema.addTable(given);
            }

            return table;
         }
      }
   }

   protected Column[] createColumns(MetaDataContext context, String prefix, Column[] tmplates, Table table, boolean adapt) {
      assertTable(context, table);
      if (prefix == null) {
         prefix = "generic";
      }

      List given = this.getColumns();
      boolean fill = ((MappingRepository)context.getRepository()).getMappingDefaults().defaultMissingInfo();
      if ((!given.isEmpty() || !adapt && !fill) && given.size() != tmplates.length) {
         throw new MetaDataException(_loc.get(prefix + "-num-cols", context, String.valueOf(tmplates.length), String.valueOf(given.size())));
      } else {
         Column[] cols = new Column[tmplates.length];
         this._io = null;

         for(int i = 0; i < tmplates.length; ++i) {
            Column col = given.isEmpty() ? null : (Column)given.get(i);
            cols[i] = mergeColumn(context, prefix, tmplates[i], true, col, table, adapt, fill);
            this.setIOFromColumnFlags(col, i);
         }

         return cols;
      }
   }

   private void setIOFromColumnFlags(Column col, int i) {
      if (col != null && (col.getFlag(2) || col.getFlag(4))) {
         if (this._io == null) {
            this._io = new ColumnIO();
         }

         this._io.setInsertable(i, !col.getFlag(2));
         this._io.setUpdatable(i, !col.getFlag(4));
      }
   }

   private static void assertTable(MetaDataContext context, Table table) {
      if (table == null) {
         throw new MetaDataException(_loc.get("unmapped", (Object)context));
      }
   }

   protected static Column mergeColumn(MetaDataContext context, String prefix, Column tmplate, boolean compat, Column given, Table table, boolean adapt, boolean fill) {
      assertTable(context, table);
      String colName = given == null ? null : given.getName();
      if (colName == null && !adapt && !fill) {
         throw new MetaDataException(_loc.get(prefix + "-no-col-name", (Object)context));
      } else {
         if (colName == null) {
            colName = tmplate.getName();
         }

         int dotIdx = colName.lastIndexOf(46);
         if (dotIdx == 0) {
            colName = colName.substring(1);
         } else if (dotIdx != -1) {
            findTable(context, colName.substring(0, dotIdx), table, (Table)null, (ClassMapping)null);
            colName = colName.substring(dotIdx + 1);
         }

         Column col = table.getColumn(colName);
         boolean existingCol = col != null;
         if (!existingCol && !adapt) {
            throw new MetaDataException(_loc.get(prefix + "-bad-col-name", context, colName, table));
         } else {
            MappingRepository repos = (MappingRepository)context.getRepository();
            DBDictionary dict = repos.getDBDictionary();
            int type = tmplate.getType();
            int size = tmplate.getSize();
            if (type == 1111) {
               type = dict.getJDBCType(tmplate.getJavaType(), size == -1);
            }

            boolean ttype = true;
            int otype = type;
            String typeName = tmplate.getTypeName();
            Boolean notNull = null;
            if (tmplate.isNotNullExplicit()) {
               notNull = tmplate.isNotNull() ? Boolean.TRUE : Boolean.FALSE;
            }

            int decimals = tmplate.getDecimalDigits();
            String defStr = tmplate.getDefaultString();
            boolean autoAssign = tmplate.isAutoAssigned();
            boolean relationId = tmplate.isRelationId();
            String targetField = tmplate.getTargetField();
            if (given != null) {
               if (given.getType() != 1111) {
                  ttype = false;
                  if (compat && !given.isCompatible(type, typeName, size, decimals)) {
                     Log log = repos.getLog();
                     if (log.isWarnEnabled()) {
                        log.warn(_loc.get(prefix + "-incompat-col", context, colName, Schemas.getJDBCName(type)));
                     }
                  }

                  otype = given.getType();
                  type = dict.getPreferredType(otype);
               }

               typeName = given.getTypeName();
               size = given.getSize();
               decimals = given.getDecimalDigits();
               if (given.isNotNullExplicit()) {
                  notNull = given.isNotNull() ? Boolean.TRUE : Boolean.FALSE;
               }

               if (given.getDefaultString() != null) {
                  defStr = given.getDefaultString();
               }

               if (given.isAutoAssigned()) {
                  autoAssign = true;
               }

               if (given.isRelationId()) {
                  relationId = true;
               }
            }

            if (size == 0 && (otype == 12 || otype == 1)) {
               size = dict.characterColumnSize;
            }

            if (col == null) {
               col = table.addColumn(colName);
               col.setType(type);
            } else if ((compat || !ttype) && !col.isCompatible(type, typeName, size, decimals)) {
               Localizer.Message msg = _loc.get(prefix + "-bad-col", context, Schemas.getJDBCName(type), col.getDescription());
               if (!adapt) {
                  throw new MetaDataException(msg);
               }

               Log log = repos.getLog();
               if (log.isWarnEnabled()) {
                  log.warn(msg);
               }

               col.setType(type);
            } else if (given != null && given.getType() != 1111) {
               col.setType(type);
            }

            if (compat) {
               col.setJavaType(tmplate.getJavaType());
            } else if (col.getJavaType() == 8) {
               if (given != null && given.getJavaType() != 8) {
                  col.setJavaType(given.getJavaType());
               } else {
                  col.setJavaType(JavaTypes.getTypeCode(Schemas.getJavaType(col.getType(), col.getSize(), col.getDecimalDigits())));
               }
            }

            col.setAutoAssigned(autoAssign);
            col.setRelationId(relationId);
            col.setTargetField(targetField);
            if (defStr != null) {
               col.setDefaultString(defStr);
            }

            if (notNull != null && (!existingCol || notNull)) {
               col.setNotNull(notNull);
            }

            if (adapt) {
               if (typeName != null) {
                  col.setTypeName(typeName);
               }

               if (size != 0) {
                  col.setSize(size);
               }

               if (decimals != 0) {
                  col.setDecimalDigits(decimals);
               }
            }

            if (tmplate.hasComment()) {
               col.setComment(tmplate.getComment());
            }

            return col;
         }
      }
   }

   private static Table findTable(MetaDataContext context, String name, Table expected, Table inverse, ClassMapping rel) {
      if (expected == null && rel != null) {
         expected = rel.getTable();
      }

      if (expected != null && isTableName(name, expected)) {
         return expected;
      } else if (inverse != null && isTableName(name, inverse)) {
         return inverse;
      } else {
         if (rel != null) {
            rel = rel.getJoinablePCSuperclassMapping();
         }

         while(rel != null) {
            if (isTableName(name, rel.getTable())) {
               return rel.getTable();
            }

            rel = rel.getJoinablePCSuperclassMapping();
         }

         throw new MetaDataException(_loc.get("col-wrong-table", context, expected, name));
      }
   }

   private static boolean isTableName(String name, Table table) {
      return name.equalsIgnoreCase(table.getName()) || name.equalsIgnoreCase(table.getFullName());
   }

   protected Index createIndex(MetaDataContext context, String prefix, Index tmplate, Column[] cols, boolean adapt) {
      if (prefix == null) {
         prefix = "generic";
      }

      if (cols != null && cols.length != 0) {
         Table table = cols[0].getTable();
         Index[] idxs = table.getIndexes();
         Index exist = null;

         for(int i = 0; i < idxs.length; ++i) {
            if (idxs[i].columnsMatch(cols)) {
               exist = idxs[i];
               break;
            }
         }

         if (!this._canIdx) {
            if (exist == null) {
               return null;
            } else if (!adapt) {
               throw new MetaDataException(_loc.get(prefix + "-index-exists", (Object)context));
            } else {
               table.removeIndex(exist);
               return null;
            }
         } else if (exist != null) {
            if (this._idx != null && this._idx.isUnique() && !exist.isUnique()) {
               if (!adapt) {
                  throw new MetaDataException(_loc.get(prefix + "-index-not-unique", (Object)context));
               }

               exist.setUnique(true);
            }

            return exist;
         } else {
            MappingRepository repos = (MappingRepository)context.getRepository();
            boolean fill = repos.getMappingDefaults().defaultMissingInfo();
            if (this._idx == null && (tmplate == null || !adapt && !fill)) {
               return null;
            } else {
               String name = null;
               boolean unq;
               if (this._idx != null) {
                  name = this._idx.getName();
                  unq = this._idx.isUnique();
                  if (this._idx.getColumns() != null && this._idx.getColumns().length > 1) {
                     cols = this._idx.getColumns();
                  }
               } else {
                  unq = tmplate.isUnique();
               }

               if (name == null) {
                  if (tmplate != null) {
                     name = tmplate.getName();
                  } else {
                     name = cols[0].getName();
                     name = repos.getDBDictionary().getValidIndexName(name, table);
                  }
               }

               Index idx = table.addIndex(name);
               idx.setUnique(unq);
               idx.setColumns(cols);
               return idx;
            }
         }
      } else if (this._idx != null) {
         throw new MetaDataException(_loc.get(prefix + "-no-index-cols", (Object)context));
      } else {
         return null;
      }
   }

   protected Unique createUnique(MetaDataContext context, String prefix, Unique tmplate, Column[] cols, boolean adapt) {
      if (prefix == null) {
         prefix = "generic";
      }

      if (cols != null && cols.length != 0) {
         Table table = cols[0].getTable();
         Unique[] unqs = table.getUniques();
         Unique exist = null;

         for(int i = 0; i < unqs.length; ++i) {
            if (unqs[i].columnsMatch(cols)) {
               exist = unqs[i];
               break;
            }
         }

         if (!this._canUnq) {
            if (exist == null) {
               return null;
            } else if (!adapt) {
               throw new MetaDataException(_loc.get(prefix + "-unique-exists", (Object)context));
            } else {
               table.removeUnique(exist);
               return null;
            }
         } else if (tmplate == null && this._unq == null) {
            return exist;
         } else {
            MappingRepository repos = (MappingRepository)context.getRepository();
            if (exist != null) {
               if (this._unq != null && this._unq.isDeferred() && !exist.isDeferred()) {
                  Log log = repos.getLog();
                  if (log.isWarnEnabled()) {
                     log.warn(_loc.get(prefix + "-defer-unique", (Object)context));
                  }
               }

               return exist;
            } else {
               DBDictionary dict = repos.getDBDictionary();
               if (this._unq != null && !dict.supportsUniqueConstraints) {
                  Log log = repos.getLog();
                  if (log.isWarnEnabled()) {
                     log.warn(_loc.get(prefix + "-unique-support", (Object)context));
                  }

                  return null;
               } else {
                  boolean fill = repos.getMappingDefaults().defaultMissingInfo();
                  if (!adapt && !fill && this._unq == null) {
                     return null;
                  } else {
                     String name;
                     boolean deferred;
                     if (this._unq != null) {
                        name = this._unq.getName();
                        deferred = this._unq.isDeferred();
                     } else {
                        name = tmplate.getName();
                        deferred = tmplate.isDeferred();
                     }

                     if (deferred && !dict.supportsDeferredConstraints) {
                        Log log = repos.getLog();
                        if (log.isWarnEnabled()) {
                           log.warn(_loc.get(prefix + "-create-defer-unique", context, dict.platform));
                        }

                        deferred = false;
                     }

                     Unique unq = table.addUnique(name);
                     unq.setDeferred(deferred);
                     unq.setColumns(cols);
                     return unq;
                  }
               }
            }
         }
      } else if (this._unq == null && tmplate == null) {
         return null;
      } else {
         throw new MetaDataException(_loc.get(prefix + "-no-unique-cols", (Object)context));
      }
   }

   protected ForeignKey createForeignKey(MetaDataContext context, String prefix, List given, ForeignKeyDefaults def, Table table, ClassMapping cls, ClassMapping rel, boolean inversable, boolean adapt) {
      assertTable(context, table);
      if (prefix == null) {
         prefix = "generic";
      }

      Object[][] joins = this.createJoins(context, prefix, table, cls, rel, given, def, inversable, adapt);
      this._join = 1;
      Table local = table;
      Table foreign = rel.getTable();
      boolean constant = false;
      boolean localSet = false;

      for(int i = 0; i < joins.length; ++i) {
         if (joins[i][1] instanceof Column) {
            Table tmp = ((Column)joins[i][0]).getTable();
            if (!localSet) {
               local = tmp;
               localSet = true;
            } else if (tmp != local) {
               throw new MetaDataException(_loc.get(prefix + "-mult-fk-tables", context, local, tmp));
            }

            foreign = ((Column)joins[i][1]).getTable();
            if (joins[i][2] == Boolean.TRUE) {
               this._join = 2;
            }
         } else {
            constant = true;
         }
      }

      ForeignKey exist = null;
      int delAction;
      if (!constant && local.getForeignKeys().length > 0) {
         Column[] cols = new Column[joins.length];
         Column[] pks = new Column[joins.length];

         for(int i = 0; i < joins.length; ++i) {
            cols[i] = (Column)joins[i][0];
            pks[i] = (Column)joins[i][1];
         }

         ForeignKey[] fks = local.getForeignKeys();

         for(delAction = 0; delAction < fks.length; ++delAction) {
            if (fks[delAction].getConstantColumns().length == 0 && fks[delAction].getConstantPrimaryKeyColumns().length == 0 && fks[delAction].columnsMatch(cols, pks)) {
               exist = fks[delAction];
               break;
            }
         }
      }

      MappingRepository repos = (MappingRepository)context.getRepository();
      DBDictionary dict = repos.getDBDictionary();
      if (exist != null) {
         if (!this._canFK) {
            if (exist.getDeleteAction() != 1 && !adapt) {
               throw new MetaDataException(_loc.get(prefix + "-fk-exists", (Object)context));
            }

            exist.setDeleteAction(1);
         }

         if (this._fk != null && this._fk.isDeferred() && !exist.isDeferred()) {
            Log log = repos.getLog();
            if (log.isWarnEnabled()) {
               log.warn(_loc.get(prefix + "-defer-fk", (Object)context));
            }
         }

         if (adapt && this._fk != null) {
            if (this._fk.getUpdateAction() != 1) {
               exist.setUpdateAction(this._fk.getUpdateAction());
            }

            if (this._fk.getDeleteAction() != 1) {
               exist.setDeleteAction(this._fk.getDeleteAction());
            }
         }

         this.setIOFromJoins(exist, joins);
         return exist;
      } else {
         String name = null;
         delAction = 1;
         int upAction = 1;
         boolean deferred = false;
         boolean fill = repos.getMappingDefaults().defaultMissingInfo();
         ForeignKey tmplate = def == null ? null : def.get(local, foreign, this._join == 2);
         if (this._fk == null || tmplate != null && (adapt || fill)) {
            if (this._canFK && (adapt || fill)) {
               if (this._fk == null && tmplate != null) {
                  name = tmplate.getName();
                  delAction = tmplate.getDeleteAction();
                  upAction = tmplate.getUpdateAction();
                  deferred = tmplate.isDeferred();
               } else if (this._fk != null && tmplate != null) {
                  name = this._fk.getName();
                  if (name == null && tmplate.getName() != null) {
                     name = tmplate.getName();
                  }

                  delAction = this._fk.getDeleteAction();
                  if (delAction == 1) {
                     delAction = tmplate.getDeleteAction();
                  }

                  upAction = this._fk.getUpdateAction();
                  if (upAction == 1) {
                     upAction = tmplate.getUpdateAction();
                  }

                  deferred = this._fk.isDeferred();
               }
            }
         } else {
            name = this._fk.getName();
            delAction = this._fk.getDeleteAction();
            upAction = this._fk.getUpdateAction();
            deferred = this._fk.isDeferred();
         }

         Log log;
         if (!dict.supportsDeleteAction(delAction) || !dict.supportsUpdateAction(upAction)) {
            log = repos.getLog();
            if (log.isWarnEnabled()) {
               log.warn(_loc.get(prefix + "-unsupported-fk-action", (Object)context));
            }

            delAction = 1;
            upAction = 1;
         }

         if (deferred && !dict.supportsDeferredConstraints) {
            log = repos.getLog();
            if (log.isWarnEnabled()) {
               log.warn(_loc.get(prefix + "-create-defer-fk", context, dict.platform));
            }

            deferred = false;
         }

         ForeignKey fk = local.addForeignKey(name);
         fk.setDeleteAction(delAction);
         fk.setUpdateAction(upAction);
         fk.setDeferred(deferred);

         for(int i = 0; i < joins.length; ++i) {
            Column col = (Column)joins[i][0];
            if (joins[i][1] instanceof Column) {
               fk.join(col, (Column)joins[i][1]);
            } else if (joins[i][2] == Boolean.TRUE != (this._join == 2)) {
               fk.joinConstant(joins[i][1], col);
            } else {
               fk.joinConstant(col, joins[i][1]);
            }
         }

         this.setIOFromJoins(fk, joins);
         return fk;
      }
   }

   private void setIOFromJoins(ForeignKey fk, Object[][] joins) {
      List cols = this.getColumns();
      this._io = null;
      if (!cols.isEmpty()) {
         int constIdx = 0;

         for(int i = 0; i < joins.length; ++i) {
            int idx;
            if (joins[i][1] instanceof Column) {
               idx = i - constIdx;
            } else {
               if (joins[i][2] == Boolean.TRUE != (this._join == 2)) {
                  continue;
               }

               idx = fk.getColumns().length + constIdx++;
            }

            this.setIOFromColumnFlags((Column)cols.get(i), idx);
         }

      }
   }

   private Object[][] createJoins(MetaDataContext context, String prefix, Table table, ClassMapping cls, ClassMapping rel, List given, ForeignKeyDefaults def, boolean inversable, boolean adapt) {
      MappingRepository repos = (MappingRepository)context.getRepository();
      boolean fill = repos.getMappingDefaults().defaultMissingInfo();
      Object[][] joins;
      if (given.isEmpty()) {
         if (!adapt && !fill) {
            throw new MetaDataException(_loc.get(prefix + "-no-fk-cols", (Object)context));
         } else {
            Column[] targets = rel.getPrimaryKeyColumns();
            joins = new Object[targets.length][3];

            for(int i = 0; i < targets.length; ++i) {
               Column tmplate = new Column();
               tmplate.setName(targets[i].getName());
               tmplate.setJavaType(targets[i].getJavaType());
               tmplate.setType(targets[i].getType());
               tmplate.setTypeName(targets[i].getTypeName());
               tmplate.setSize(targets[i].getSize());
               tmplate.setDecimalDigits(targets[i].getDecimalDigits());
               if (def != null) {
                  def.populate(table, rel.getTable(), tmplate, targets[i], false, i, targets.length);
               }

               joins[i][0] = mergeColumn(context, prefix, tmplate, true, (Column)null, table, adapt, fill);
               joins[i][1] = targets[i];
            }

            return joins;
         }
      } else {
         joins = new Object[given.size()][3];

         for(int i = 0; i < joins.length; ++i) {
            Column col = (Column)given.get(i);
            this.mergeJoinColumn(context, prefix, col, joins, i, table, cls, rel, def, inversable && !col.getFlag(128), adapt, fill);
         }

         return joins;
      }
   }

   private void mergeJoinColumn(MetaDataContext context, String prefix, Column given, Object[][] joins, int idx, Table table, ClassMapping cls, ClassMapping rel, ForeignKeyDefaults def, boolean inversable, boolean adapt, boolean fill) {
      String name = given.getName();
      if (name == null && given != null && given.getFlag(128) && cls != null) {
         Column[] pks = cls.getPrimaryKeyColumns();
         if (pks.length == 1) {
            name = pks[0].getName();
         }
      }

      if (name == null && !adapt && !fill) {
         throw new MetaDataException(_loc.get(prefix + "-no-fkcol-name", (Object)context));
      } else {
         Table local = table;
         Table foreign = rel.getTable();
         boolean fullName = false;
         boolean inverse = false;
         if (name != null) {
            int dotIdx = name.lastIndexOf(46);
            if (dotIdx != -1) {
               if (dotIdx == 0) {
                  local = foreign;
               } else {
                  local = findTable(context, name.substring(0, dotIdx), table, foreign, (ClassMapping)null);
               }

               fullName = true;
               name = name.substring(dotIdx + 1);
               if (local != table) {
                  foreign = table;
                  inverse = true;
               }
            }
         }

         boolean forceInverse = !fullName && this._join == 2;
         if (forceInverse) {
            local = foreign;
            foreign = table;
            inverse = true;
         }

         String targetName = given.getTarget();
         Object target = null;
         Table ttable = null;
         boolean constant = false;
         boolean fullTarget = false;
         if (targetName == null && given.getTargetField() != null) {
            ClassMapping tcls = inverse ? cls : rel;
            String fieldName = given.getTargetField();
            int dotIdx = fieldName.lastIndexOf(46);
            fullTarget = dotIdx != -1;
            if (dotIdx == 0) {
               if (!inverse) {
                  tcls = cls;
               }

               fieldName = fieldName.substring(1);
            } else if (dotIdx > 0) {
               tcls = findClassMapping(context, fieldName.substring(0, dotIdx), cls, rel);
               fieldName = fieldName.substring(dotIdx + 1);
            }

            if (tcls == null) {
               throw new MetaDataException(_loc.get(prefix + "-bad-fktargetcls", context, fieldName, name));
            }

            FieldMapping field = tcls.getFieldMapping(fieldName);
            if (field == null) {
               throw new MetaDataException(_loc.get(prefix + "-bad-fktargetfield", new Object[]{context, fieldName, name, tcls}));
            }

            if (field.getColumns().length != 1) {
               throw new MetaDataException(_loc.get(prefix + "-fktargetfield-cols", context, fieldName, name));
            }

            ttable = field.getJoinForeignKey() != null ? field.getTable() : field.getDefiningMapping().getTable();
            targetName = field.getColumns()[0].getName();
         } else if (targetName != null) {
            if (targetName.charAt(0) == '\'') {
               constant = true;
               target = targetName.substring(1, targetName.length() - 1);
            } else if (targetName.charAt(0) != '-' && targetName.charAt(0) != '.' && !Character.isDigit(targetName.charAt(0))) {
               if ("null".equalsIgnoreCase(targetName)) {
                  constant = true;
               } else {
                  int dotIdx = targetName.lastIndexOf(46);
                  fullTarget = dotIdx != -1;
                  if (dotIdx == 0) {
                     if (!inverse) {
                        ttable = local;
                     }

                     targetName = targetName.substring(1);
                  } else if (dotIdx != -1) {
                     ttable = findTable(context, targetName.substring(0, dotIdx), foreign, local, inverse ? cls : rel);
                     targetName = targetName.substring(dotIdx + 1);
                  }
               }
            } else {
               constant = true;

               try {
                  if (targetName.indexOf(46) == -1) {
                     target = new Integer(targetName);
                  } else {
                     target = new Double(targetName);
                  }
               } catch (RuntimeException var28) {
                  throw new MetaDataException(_loc.get(prefix + "-bad-fkconst", context, targetName, name));
               }
            }
         }

         if (ttable == local && local != foreign) {
            if (fullName) {
               throw new MetaDataException(_loc.get(prefix + "-bad-fktarget-inverse", new Object[]{context, name, foreign, ttable}));
            }

            local = foreign;
            foreign = ttable;
         } else if (ttable != null) {
            foreign = ttable;
         }

         inverse = inverse || local != table || local == foreign && (fullName && !fullTarget || name == null && fullTarget);
         if (!inversable && !constant && inverse) {
            if (local == foreign) {
               throw new MetaDataException(_loc.get(prefix + "-bad-fk-self-inverse", context, local));
            } else {
               throw new MetaDataException(_loc.get(prefix + "-bad-fk-inverse", context, local, table));
            }
         } else if (name == null && constant) {
            throw new MetaDataException(_loc.get(prefix + "-no-fkcol-name-adapt", (Object)context));
         } else {
            PrimaryKey pk;
            if (name == null && targetName == null) {
               pk = foreign.getPrimaryKey();
               if (joins.length != 1 || pk == null || pk.getColumns().length != 1) {
                  throw new MetaDataException(_loc.get(prefix + "-no-fkcol-name-adapt", (Object)context));
               }

               targetName = pk.getColumns()[0].getName();
            } else if (name != null && targetName == null) {
               pk = foreign.getPrimaryKey();
               if (joins.length == 1 && pk != null && pk.getColumns().length == 1) {
                  targetName = pk.getColumns()[0].getName();
               } else {
                  if (foreign.getColumn(name) == null) {
                     throw new MetaDataException(_loc.get(prefix + "-no-fkcol-target-adapt", context, name));
                  }

                  targetName = name;
               }
            }

            Column tmplate = new Column();
            tmplate.setName(name);
            Column col;
            if (!constant) {
               col = foreign.getColumn(targetName);
               if (col == null) {
                  throw new MetaDataException(_loc.get(prefix + "-bad-fktarget", new Object[]{context, targetName, name, foreign}));
               }

               if (name == null) {
                  tmplate.setName(col.getName());
               }

               tmplate.setJavaType(col.getJavaType());
               tmplate.setType(col.getType());
               tmplate.setTypeName(col.getTypeName());
               tmplate.setSize(col.getSize());
               tmplate.setDecimalDigits(col.getDecimalDigits());
               target = col;
            } else if (target instanceof String) {
               tmplate.setJavaType(9);
            } else if (target instanceof Integer) {
               tmplate.setJavaType(5);
            } else if (target instanceof Double) {
               tmplate.setJavaType(3);
            }

            if (def != null) {
               def.populate(local, foreign, tmplate, target, inverse, idx, joins.length);
            }

            if (name != null) {
               tmplate.setName(name);
            }

            col = mergeColumn(context, prefix, tmplate, true, given, local, adapt, fill);
            joins[idx][0] = col;
            joins[idx][1] = target;
            if (inverse) {
               joins[idx][2] = Boolean.TRUE;
            }

         }
      }
   }

   private static ClassMapping findClassMapping(MetaDataContext context, String clsName, ClassMapping cls, ClassMapping rel) {
      if (isClassMappingName(clsName, cls)) {
         return cls;
      } else if (isClassMappingName(clsName, rel)) {
         return rel;
      } else {
         throw new MetaDataException(_loc.get("target-wrong-cls", new Object[]{context, clsName, cls, rel}));
      }
   }

   private static boolean isClassMappingName(String name, ClassMapping cls) {
      if (cls == null) {
         return false;
      } else {
         return !name.equals(cls.getDescribedType().getName()) && !name.equals(Strings.getClassName(cls.getDescribedType())) ? isClassMappingName(name, cls.getPCSuperclassMapping()) : true;
      }
   }

   protected void syncColumns(MetaDataContext context, Column[] cols, boolean forceJDBCType) {
      if (cols != null && cols.length != 0) {
         this._cols = new ArrayList(cols.length);

         for(int i = 0; i < cols.length; ++i) {
            Column col = syncColumn(context, cols[i], cols.length, forceJDBCType, cols[i].getTable(), (Table)null, (Object)null, false);
            this.setColumnFlagsFromIO(col, i);
            this._cols.add(col);
         }
      } else {
         this._cols = null;
      }

   }

   private void setColumnFlagsFromIO(Column col, int i) {
      if (this._io != null) {
         col.setFlag(4, !this._io.isUpdatable(i, false));
         col.setFlag(2, !this._io.isInsertable(i, false));
      }
   }

   protected void syncIndex(MetaDataContext context, Index idx) {
      if (idx == null) {
         this._idx = null;
      } else {
         this._canIdx = true;
         this._idx = new Index();
         this._idx.setName(idx.getName());
         this._idx.setUnique(idx.isUnique());
         if (idx.getColumns() != null && idx.getColumns().length > 1) {
            this._idx.setColumns(idx.getColumns());
         }

      }
   }

   protected void syncUnique(MetaDataContext context, Unique unq) {
      if (unq == null) {
         this._unq = null;
      } else {
         this._canUnq = true;
         this._unq = new Unique();
         this._unq.setName(unq.getName());
         this._unq.setDeferred(unq.isDeferred());
      }
   }

   protected void syncForeignKey(MetaDataContext context, ForeignKey fk, Table local, Table target) {
      if (fk == null) {
         this._fk = null;
         this._cols = null;
         this._join = 0;
      } else {
         if (this._join == 0) {
            this._join = 1;
         }

         if (fk.isLogical()) {
            this._fk = null;
         } else {
            this._canFK = true;
            this._fk = new ForeignKey();
            this._fk.setName(fk.getName());
            this._fk.setDeleteAction(fk.getDeleteAction());
            this._fk.setUpdateAction(fk.getUpdateAction());
            this._fk.setDeferred(fk.isDeferred());
         }

         Column[] cols = fk.getColumns();
         Column[] pkCols = fk.getPrimaryKeyColumns();
         Column[] ccols = fk.getConstantColumns();
         Object[] cs = fk.getConstants();
         Column[] cpkCols = fk.getConstantPrimaryKeyColumns();
         Object[] cpks = fk.getPrimaryKeyConstants();
         int size = cols.length + ccols.length + cpkCols.length;
         this._cols = new ArrayList(size);

         Column col;
         for(int i = 0; i < cols.length; ++i) {
            col = syncColumn(context, cols[i], size, false, local, target, pkCols[i], this._join == 2);
            this.setColumnFlagsFromIO(col, i);
            this._cols.add(col);
         }

         int i;
         Object constant;
         for(i = 0; i < ccols.length; ++i) {
            constant = cs[i] == null ? NULL : cs[i];
            col = syncColumn(context, ccols[i], size, false, local, target, constant, this._join == 2);
            this.setColumnFlagsFromIO(col, cols.length + i);
            this._cols.add(col);
         }

         for(i = 0; i < cpkCols.length; ++i) {
            constant = cpks[i] == null ? NULL : cpks[i];
            this._cols.add(syncColumn(context, cpkCols[i], size, false, target, local, constant, this._join != 2));
         }

      }
   }

   protected static Column syncColumn(MetaDataContext context, Column col, int num, boolean forceJDBCType, Table colTable, Table targetTable, Object target, boolean inverse) {
      DBDictionary dict = ((MappingRepository)context.getRepository()).getDBDictionary();
      Column copy = new Column();
      if (col.getTable() == colTable && !inverse) {
         copy.setName(col.getName());
      } else {
         copy.setName(dict.getFullName(col.getTable(), true) + "." + col.getName());
      }

      if (target != null) {
         if (target == NULL) {
            copy.setTarget("null");
         } else if (target instanceof Column) {
            Column tcol = (Column)target;
            if (!inverse && tcol.getTable() != targetTable || inverse && tcol.getTable() != colTable) {
               copy.setTarget(dict.getFullName(tcol.getTable(), true) + "." + tcol.getName());
            } else if (!defaultTarget(col, tcol, num)) {
               copy.setTarget(tcol.getName());
            }
         } else if (target instanceof Number) {
            copy.setTarget(target.toString());
         } else {
            copy.setTarget("'" + target + "'");
         }
      } else if (num > 1) {
         copy.setTargetField(col.getTargetField());
      }

      if (col.getSize() != 0 && col.getSize() != dict.characterColumnSize && (col.getSize() != -1 || !col.isLob())) {
         copy.setSize(col.getSize());
      }

      if (col.getDecimalDigits() != 0) {
         copy.setDecimalDigits(col.getDecimalDigits());
      }

      if (col.getDefaultString() != null) {
         copy.setDefaultString(col.getDefaultString());
      }

      if (col.isNotNull() && !col.isPrimaryKey() && (!isPrimitive(col.getJavaType()) || isForeignKey(col))) {
         copy.setNotNull(true);
      }

      String typeName = col.getTypeName();
      if (typeName != null || copy.getSize() != 0 || copy.getDecimalDigits() != 0) {
         copy.setType(col.getType());
         String defName = dict.getTypeName(copy);
         copy.setType(1111);
         boolean defSized = defName.indexOf(40) != -1;
         if (!defSized) {
            if (copy.getSize() > 0) {
               copy.setSize(0);
            }

            copy.setDecimalDigits(0);
         }

         if (typeName != null) {
            if (typeName.indexOf(40) == -1 && defSized) {
               defName = defName.substring(0, defName.indexOf(40));
            }

            if (!typeName.equalsIgnoreCase(defName)) {
               copy.setTypeName(typeName);
            }
         }
      }

      if (forceJDBCType || target != null && !(target instanceof Column) && col.getType() != 12 || dict.getJDBCType(col.getJavaType(), false) != col.getType()) {
         copy.setType(col.getType());
      }

      return copy;
   }

   private static boolean isForeignKey(Column col) {
      if (col.getTable() == null) {
         return false;
      } else {
         ForeignKey[] fks = col.getTable().getForeignKeys();

         for(int i = 0; i < fks.length; ++i) {
            if (fks[i].containsColumn(col) || fks[i].containsConstantColumn(col)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean isPrimitive(int type) {
      switch (type) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
            return true;
         default:
            return false;
      }
   }

   private static boolean defaultTarget(Column col, Column targetCol, int num) {
      if (col.getName().equals(targetCol.getName())) {
         return true;
      } else if (num > 1) {
         return false;
      } else {
         PrimaryKey pk = targetCol.getTable().getPrimaryKey();
         if (pk != null && pk.getColumns().length == 1) {
            return targetCol == pk.getColumns()[0];
         } else {
            return false;
         }
      }
   }

   public interface ForeignKeyDefaults {
      ForeignKey get(Table var1, Table var2, boolean var3);

      void populate(Table var1, Table var2, Column var3, Object var4, boolean var5, int var6, int var7);
   }

   public interface TableDefaults {
      String get(Schema var1);
   }
}
