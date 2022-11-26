package org.apache.openjpa.persistence.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.ColumnResult;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityResult;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FieldResult;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SecondaryTable;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.DiscriminatorMappingInfo;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.MappingInfo;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.meta.QueryResultMapping;
import org.apache.openjpa.jdbc.meta.SequenceMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.meta.strats.EnumValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.persistence.AnnotationBuilder;
import org.apache.openjpa.persistence.AnnotationPersistenceMetaDataSerializer;
import org.apache.openjpa.persistence.PersistenceStrategy;
import serp.util.Strings;

public class AnnotationPersistenceMappingSerializer extends AnnotationPersistenceMetaDataSerializer {
   private static final int TYPE_RESULTMAP = 21;
   private List _results = null;
   private boolean _sync = false;
   private Map _rsmAnnos = null;

   public AnnotationPersistenceMappingSerializer(JDBCConfiguration conf) {
      super(conf);
   }

   public boolean getSyncMappingInfo() {
      return this._sync;
   }

   public void setSyncMappingInfo(boolean sync) {
      this._sync = sync;
   }

   public void addQueryResultMapping(QueryResultMapping meta) {
      if (this._results == null) {
         this._results = new ArrayList();
      }

      this._results.add(meta);
   }

   public boolean removeQueryResultMapping(QueryResultMapping meta) {
      return this._results != null && this._results.remove(meta);
   }

   public void addAll(MetaDataRepository repos) {
      super.addAll(repos);
      QueryResultMapping[] arr$ = ((MappingRepository)repos).getQueryResultMappings();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         QueryResultMapping res = arr$[i$];
         this.addQueryResultMapping(res);
      }

   }

   public boolean removeAll(MetaDataRepository repos) {
      boolean removed = super.removeAll(repos);
      QueryResultMapping[] arr$ = ((MappingRepository)repos).getQueryResultMappings();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         QueryResultMapping res = arr$[i$];
         removed |= this.removeQueryResultMapping(res);
      }

      return removed;
   }

   public void clear() {
      super.clear();
      if (this._results != null) {
         this._results.clear();
      }

   }

   protected void addAnnotation(AnnotationBuilder ab, QueryResultMapping meta) {
      if (this._rsmAnnos == null) {
         this._rsmAnnos = new HashMap();
      }

      List list = (List)this._rsmAnnos.get(meta);
      if (list == null) {
         list = new ArrayList();
         this._rsmAnnos.put(meta, list);
      }

      ((List)list).add(ab);
   }

   protected AnnotationBuilder addAnnotation(Class annType, QueryResultMapping meta) {
      AnnotationBuilder ab = this.newAnnotationBuilder(annType);
      if (meta == null) {
         return ab;
      } else {
         this.addAnnotation(ab, meta);
         return ab;
      }
   }

   protected void serializeClass(ClassMetaData meta) {
      if (this._sync && this.isMappingMode() && meta instanceof ClassMapping) {
         ClassMapping cls = (ClassMapping)meta;
         if ((cls.getResolve() & 2) != 0 && cls.isMapped()) {
            cls.syncMappingInfo();
            cls.getDiscriminator().syncMappingInfo();
            cls.getVersion().syncMappingInfo();
            FieldMapping[] fields;
            if (cls.getEmbeddingMetaData() == null) {
               fields = cls.getDefinedFieldMappings();
            } else {
               fields = cls.getFieldMappings();
            }

            FieldMapping[] arr$ = fields;
            int len$ = fields.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               FieldMapping f = arr$[i$];
               f.syncMappingInfo();
            }
         }
      }

      super.serializeClass(meta);
   }

   protected void serializeClassMappingContent(ClassMetaData mapping) {
      ClassMapping cls = (ClassMapping)mapping;
      ClassMappingInfo info = cls.getMappingInfo();
      AnnotationBuilder abTable = this.addAnnotation((Class)Table.class, (ClassMetaData)mapping);
      this.serializeTable(info.getTableName(), Strings.getClassName(mapping.getDescribedType()), (ClassMappingInfo)null, info.getUniques(), abTable);
      this.serializeColumns(info, AnnotationPersistenceMappingSerializer.ColType.PK_JOIN, (String)null, abTable, cls);
      String[] arr$ = info.getSecondaryTableNames();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String second = arr$[i$];
         AnnotationBuilder abSecTable = this.addAnnotation((Class)SecondaryTable.class, (ClassMetaData)mapping);
         this.serializeTable(second, (String)null, info, (org.apache.openjpa.jdbc.schema.Unique[])null, abSecTable);
      }

   }

   protected void serializeInheritanceContent(ClassMetaData mapping) {
      ClassMapping cls = (ClassMapping)mapping;
      ClassMappingInfo info = cls.getMappingInfo();
      DiscriminatorMappingInfo dinfo = cls.getDiscriminator().getMappingInfo();
      String strat = info.getHierarchyStrategy();
      if (null != strat) {
         String itypecls = Strings.getClassName(InheritanceType.class);
         AnnotationBuilder abInheritance = this.addAnnotation((Class)Inheritance.class, (ClassMetaData)mapping);
         if ("flat".equals(strat)) {
            abInheritance.add("strategy", itypecls + ".SINGLE_TABLE");
         } else if ("vertical".equals(strat)) {
            abInheritance.add("strategy", itypecls + ".JOINED");
         } else if ("full".equals(strat)) {
            abInheritance.add("strategy", itypecls + ".TABLE_PER_CLASS");
         }

         AnnotationBuilder abDiscCol;
         if (dinfo.getValue() != null) {
            abDiscCol = this.addAnnotation((Class)DiscriminatorValue.class, (ClassMetaData)mapping);
            abDiscCol.add((String)null, (String)dinfo.getValue());
         }

         abDiscCol = this.addAnnotation((Class)DiscriminatorColumn.class, (ClassMetaData)mapping);
         this.serializeColumns(dinfo, AnnotationPersistenceMappingSerializer.ColType.DISC, (String)null, abDiscCol, (Object)null);
      }
   }

   private void serializeTable(String table, String defaultName, ClassMappingInfo secondaryInfo, org.apache.openjpa.jdbc.schema.Unique[] uniques, AnnotationBuilder ab) {
      List cols = null;
      if (secondaryInfo != null) {
         cols = secondaryInfo.getSecondaryTableJoinColumns(table);
      }

      boolean print = cols != null && cols.size() > 0 || uniques != null && uniques.length > 0;
      if (table != null && (defaultName == null || !defaultName.equals(table))) {
         print = true;
         int index = table.indexOf(46);
         if (index < 0) {
            ab.add("name", table);
         } else {
            ab.add("schema", table.substring(0, index));
            ab.add("name", table.substring(index + 1));
         }
      }

      if (print) {
         if (cols != null) {
            Iterator i$ = cols.iterator();

            while(i$.hasNext()) {
               Column col = (Column)i$.next();
               this.serializeColumn(col, AnnotationPersistenceMappingSerializer.ColType.PK_JOIN, (String)null, false, ab, (Object)null);
            }
         }

         if (uniques != null) {
            org.apache.openjpa.jdbc.schema.Unique[] arr$ = uniques;
            int len$ = uniques.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               org.apache.openjpa.jdbc.schema.Unique unique = arr$[i$];
               AnnotationBuilder abUniqueConst = this.newAnnotationBuilder(UniqueConstraint.class);
               this.serializeUniqueConstraint(unique, abUniqueConst);
               ab.add("uniqueConstraints", abUniqueConst);
            }
         }
      }

   }

   protected boolean serializeAttributeOverride(FieldMetaData fmd, FieldMetaData orig) {
      if (orig != null && fmd != orig) {
         FieldMapping field = (FieldMapping)fmd;
         FieldMapping field2 = (FieldMapping)orig;
         if (!field.getMappingInfo().hasSchemaComponents() && !field2.getMappingInfo().hasSchemaComponents()) {
            ValueMappingInfo info = field.getValueInfo();
            List cols = info.getColumns();
            if (cols != null && cols.size() != 0) {
               ValueMappingInfo info2 = field2.getValueInfo();
               List cols2 = info2.getColumns();
               if (cols2 != null && cols2.size() == cols.size()) {
                  if (cols.size() != 1) {
                     return true;
                  } else {
                     for(int i = 0; i < cols.size(); ++i) {
                        Column col = (Column)cols.get(i);
                        Column col2 = (Column)cols2.get(i);
                        if (!StringUtils.equals(col.getName(), col2.getName())) {
                           return true;
                        }

                        if (!StringUtils.equals(col.getTypeName(), col2.getTypeName())) {
                           return true;
                        }

                        if (col.getSize() != col2.getSize()) {
                           return true;
                        }

                        if (col.getDecimalDigits() != col2.getDecimalDigits()) {
                           return true;
                        }

                        if (col.getFlag(2) != col2.getFlag(2)) {
                           return true;
                        }

                        if (col.getFlag(4) != col2.getFlag(4)) {
                           return true;
                        }
                     }

                     return false;
                  }
               } else {
                  return true;
               }
            } else {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   protected void serializeAttributeOverrideMappingContent(FieldMetaData fmd, FieldMetaData orig, AnnotationBuilder ab) {
      FieldMapping fm = (FieldMapping)fmd;
      this.serializeColumns(fm.getValueInfo(), AnnotationPersistenceMappingSerializer.ColType.COL, fm.getMappingInfo().getTableName(), ab, fmd);
   }

   protected PersistenceStrategy getStrategy(FieldMetaData fmd) {
      PersistenceStrategy strat = super.getStrategy(fmd);
      FieldMapping field = (FieldMapping)fmd;
      switch (strat) {
         case MANY_MANY:
            if (field.getMappedBy() != null || field.getMappingInfo().getJoinDirection() != 0 && field.getElementMapping().getValueInfo().getUnique() == null) {
               break;
            }

            return PersistenceStrategy.ONE_MANY;
         case MANY_ONE:
            if (field.getValueInfo().getJoinDirection() == 2 || field.getValueInfo().getUnique() != null) {
               return PersistenceStrategy.ONE_ONE;
            }

            List cols = field.getValueInfo().getColumns();
            boolean pkJoin = cols != null && cols.size() > 0;

            for(int i = 0; pkJoin && i < cols.size(); ++i) {
               pkJoin = ((Column)cols.get(i)).getFlag(128);
            }

            if (pkJoin) {
               return PersistenceStrategy.ONE_ONE;
            }
      }

      return strat;
   }

   protected void serializeFieldMappingContent(FieldMetaData fmd, PersistenceStrategy strategy, AnnotationBuilder ab) {
      if (fmd.getMappedBy() == null) {
         FieldMapping field = (FieldMapping)fmd;
         switch (strategy) {
            case MANY_MANY:
               break;
            case MANY_ONE:
            case ONE_ONE:
               this.serializeColumns(field.getValueInfo(), AnnotationPersistenceMappingSerializer.ColType.JOIN, field.getMappingInfo().getTableName(), (AnnotationBuilder)null, fmd);
               return;
            case ONE_MANY:
               if (field.getMappingInfo().getJoinDirection() == 0) {
                  this.serializeColumns(field.getElementMapping().getValueInfo(), AnnotationPersistenceMappingSerializer.ColType.JOIN, (String)null, (AnnotationBuilder)null, fmd);
                  return;
               }
               break;
            default:
               this.serializeColumns(field.getValueInfo(), AnnotationPersistenceMappingSerializer.ColType.COL, field.getMappingInfo().getTableName(), (AnnotationBuilder)null, fmd);
               if (strategy == PersistenceStrategy.BASIC && this.isLob(field)) {
                  this.addAnnotation((Class)Lob.class, (FieldMetaData)fmd);
               }

               TemporalType temporal = this.getTemporal(field);
               if (temporal != null) {
                  this.addAnnotation((Class)Temporal.class, (FieldMetaData)fmd).add((String)null, (Enum)temporal);
               }

               EnumType enumType = this.getEnumType(field);
               if (enumType != null && enumType != EnumType.ORDINAL) {
                  this.addAnnotation((Class)Enumerated.class, (FieldMetaData)fmd).add((String)null, (Enum)enumType);
               }

               return;
         }

         if (field.getMappingInfo().hasSchemaComponents() || field.getElementMapping().getValueInfo().hasSchemaComponents()) {
            AnnotationBuilder abJoinTbl = this.addAnnotation((Class)JoinTable.class, (FieldMetaData)fmd);
            String table = field.getMappingInfo().getTableName();
            if (table != null) {
               int index = table.indexOf(46);
               if (index < 0) {
                  abJoinTbl.add("name", table);
               } else {
                  abJoinTbl.add("schema", table.substring(0, index));
                  abJoinTbl.add("name", table.substring(index + 1));
               }
            }

            this.serializeColumns(field.getMappingInfo(), AnnotationPersistenceMappingSerializer.ColType.JOIN, (String)null, abJoinTbl, (Object)null);
            this.serializeColumns(field.getElementMapping().getValueInfo(), AnnotationPersistenceMappingSerializer.ColType.INVERSE, (String)null, abJoinTbl, (Object)null);
         }

      }
   }

   private boolean isLob(FieldMapping field) {
      Iterator i$ = field.getValueInfo().getColumns().iterator();

      Column col;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         col = (Column)i$.next();
      } while(col.getType() != 2004 && col.getType() != 2005);

      return true;
   }

   private TemporalType getTemporal(FieldMapping field) {
      if (field.getDeclaredTypeCode() != 14 && field.getDeclaredTypeCode() != 28) {
         return null;
      } else {
         DBDictionary dict = ((JDBCConfiguration)this.getConfiguration()).getDBDictionaryInstance();
         int def = dict.getJDBCType(field.getTypeCode(), false);
         Iterator i$ = field.getValueInfo().getColumns().iterator();

         while(i$.hasNext()) {
            Column col = (Column)i$.next();
            if (col.getType() != def) {
               switch (col.getType()) {
                  case 91:
                     return TemporalType.DATE;
                  case 92:
                     return TemporalType.TIME;
                  case 93:
                     return TemporalType.TIMESTAMP;
               }
            }
         }

         return null;
      }
   }

   protected EnumType getEnumType(FieldMapping field) {
      if (field.getDeclaredTypeCode() != 8) {
         return null;
      } else if (!(field.getHandler() instanceof EnumValueHandler)) {
         return null;
      } else {
         return ((EnumValueHandler)field.getHandler()).getStoreOrdinal() ? EnumType.ORDINAL : EnumType.STRING;
      }
   }

   private void serializeColumns(MappingInfo info, ColType type, String secondary, AnnotationBuilder ab, Object meta) {
      List cols = info.getColumns();
      if (cols != null) {
         AnnotationBuilder abContainer = ab;
         if (cols.size() > 1) {
            Class grpType = type.getColumnGroupAnnotationType();
            if (null != grpType) {
               AnnotationBuilder abGrp = this.newAnnotationBuilder(grpType);
               if (null == ab) {
                  this.addAnnotation((AnnotationBuilder)abGrp, (Object)meta);
               } else {
                  ab.add((String)null, (AnnotationBuilder)abGrp);
               }

               abContainer = abGrp;
            }
         }

         Iterator i$ = cols.iterator();

         while(i$.hasNext()) {
            Column col = (Column)i$.next();
            this.serializeColumn(col, type, secondary, info.getUnique() != null, abContainer, meta);
         }

      }
   }

   private void serializeColumn(Column col, ColType type, String secondary, boolean unique, AnnotationBuilder ab, Object meta) {
      FieldMetaData fmd = meta instanceof FieldMetaData ? (FieldMetaData)meta : null;
      AnnotationBuilder abCol = this.newAnnotationBuilder(type.getColumnAnnotationType());
      if (col.getName() != null && (null == fmd || !col.getName().equalsIgnoreCase(fmd.getName()))) {
         abCol.add("name", col.getName());
      }

      if (col.getTypeName() != null) {
         abCol.add("columnDefinition", col.getTypeName());
      }

      if (col.getTarget() != null && (type == AnnotationPersistenceMappingSerializer.ColType.JOIN || type == AnnotationPersistenceMappingSerializer.ColType.INVERSE || type == AnnotationPersistenceMappingSerializer.ColType.PK_JOIN)) {
         abCol.add("referencedColumnName", col.getTarget());
      }

      if (type == AnnotationPersistenceMappingSerializer.ColType.COL || type == AnnotationPersistenceMappingSerializer.ColType.JOIN || type == AnnotationPersistenceMappingSerializer.ColType.PK_JOIN) {
         if (unique) {
            abCol.add("unique", true);
         }

         if (col.isNotNull()) {
            abCol.add("nullable", false);
         }

         if (col.getFlag(2)) {
            abCol.add("insertable", false);
         }

         if (col.getFlag(4)) {
            abCol.add("updatable", false);
         }

         if (secondary != null) {
            abCol.add("table", secondary);
         }

         if (type == AnnotationPersistenceMappingSerializer.ColType.COL) {
            if (col.getSize() > 0 && col.getSize() != 255) {
               abCol.add("length", col.getSize());
            }

            if (col.getDecimalDigits() != 0) {
               abCol.add("scale", col.getDecimalDigits());
            }
         }
      }

      if (type != AnnotationPersistenceMappingSerializer.ColType.COL || abCol.hasComponents()) {
         if (null != ab) {
            String key = null;
            if (ab.getType() == JoinTable.class) {
               switch (type) {
                  case JOIN:
                     key = "joinColumns";
                     break;
                  case INVERSE:
                     key = "inverseJoinColumns";
               }
            }

            ab.add(key, abCol);
         } else {
            this.addAnnotation((AnnotationBuilder)abCol, (Object)meta);
         }
      }

   }

   private void serializeUniqueConstraint(org.apache.openjpa.jdbc.schema.Unique unique, AnnotationBuilder ab) {
      StringBuilder sb = new StringBuilder();
      Column[] columns = unique.getColumns();
      Column[] arr$ = columns;
      int len$ = columns.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Column column = arr$[i$];
         if (sb.length() > 0) {
            sb.append(", ");
         }

         sb.append(column.getName());
      }

      if (columns.length > 1) {
         sb.insert(0, "{").append("}");
      }

      ab.add("columnNames", sb.toString());
   }

   protected AnnotationPersistenceMetaDataSerializer.SerializationComparator newSerializationComparator() {
      return new MappingSerializationComparator();
   }

   protected void addSystemMappingElements(Collection toSerialize) {
      if (this.isQueryMode()) {
         toSerialize.addAll(this.getQueryResultMappings((ClassMetaData)null));
      }

   }

   protected int type(Object o) {
      int type = super.type(o);
      return type == -1 && o instanceof QueryResultMapping ? 21 : type;
   }

   private List getQueryResultMappings(ClassMetaData cm) {
      if (this._results != null && !this._results.isEmpty()) {
         List result = null;

         for(int i = 0; i < this._results.size(); ++i) {
            QueryResultMapping element = (QueryResultMapping)this._results.get(i);
            if ((cm != null || element.getSourceScope() == null) && (cm == null || element.getSourceScope() == cm.getDescribedType())) {
               if (result == null) {
                  result = new ArrayList(this._results.size() - i);
               }

               result.add(element);
            }
         }

         return (List)(result == null ? Collections.EMPTY_LIST : result);
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   protected void serializeSystemMappingElement(Object obj) {
      if (obj instanceof QueryResultMapping) {
         this.serializeQueryResultMapping((QueryResultMapping)obj, (ClassMetaData)null);
      }

   }

   protected void serializeQueryMappings(ClassMetaData meta) {
      Iterator i$ = this.getQueryResultMappings(meta).iterator();

      while(i$.hasNext()) {
         QueryResultMapping res = (QueryResultMapping)i$.next();
         this.serializeQueryResultMapping(res, meta);
      }

   }

   private void serializeQueryResultMapping(QueryResultMapping meta, ClassMetaData clsmeta) {
      AnnotationBuilder ab = this.addAnnotation(SqlResultSetMapping.class, meta);
      if (null != clsmeta) {
         this.addAnnotation((AnnotationBuilder)ab, (ClassMetaData)clsmeta);
      }

      ab.add("name", meta.getName());
      QueryResultMapping.PCResult[] arr$ = meta.getPCResults();
      int len$ = arr$.length;

      int i$;
      AnnotationBuilder abEntRes;
      for(i$ = 0; i$ < len$; ++i$) {
         QueryResultMapping.PCResult pc = arr$[i$];
         abEntRes = this.newAnnotationBuilder(EntityResult.class);
         ab.add("entities", abEntRes);
         abEntRes.add("entityClass", pc.getCandidateType());
         Object discrim = pc.getMapping("<discriminator>");
         if (discrim != null) {
            abEntRes.add("discriminatorColumn", discrim.toString());
         }

         String[] arr$ = pc.getMappingPaths();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String path = arr$[i$];
            AnnotationBuilder abFldRes = this.newAnnotationBuilder(FieldResult.class);
            abEntRes.add("fields", abFldRes);
            abFldRes.add("name", path);
            abFldRes.add("column", pc.getMapping(path).toString());
         }
      }

      Object[] arr$ = meta.getColumnResults();
      len$ = arr$.length;

      for(i$ = 0; i$ < len$; ++i$) {
         Object col = arr$[i$];
         abEntRes = this.newAnnotationBuilder(ColumnResult.class);
         abEntRes.add("name", col.toString());
      }

   }

   protected void serializeSequence(SequenceMetaData meta) {
      if ("value-table".equals(meta.getSequencePlugin())) {
         super.serializeSequence(meta);
      } else {
         AnnotationBuilder abTblGen = this.addAnnotation((Class)TableGenerator.class, (SequenceMetaData)meta);
         SequenceMapping seq = (SequenceMapping)meta;
         abTblGen.add("name", seq.getName());
         String table = seq.getTable();
         if (table != null) {
            int dotIdx = table.indexOf(46);
            if (dotIdx == -1) {
               abTblGen.add("table", table);
            } else {
               abTblGen.add("table", table.substring(dotIdx + 1));
               abTblGen.add("schema", table.substring(0, dotIdx));
            }
         }

         if (!StringUtils.isEmpty(seq.getPrimaryKeyColumn())) {
            abTblGen.add("pkColumnName", seq.getPrimaryKeyColumn());
         }

         if (!StringUtils.isEmpty(seq.getSequenceColumn())) {
            abTblGen.add("valueColumnName", seq.getSequenceColumn());
         }

         if (!StringUtils.isEmpty(seq.getPrimaryKeyValue())) {
            abTblGen.add("pkColumnValue", seq.getPrimaryKeyValue());
         }

         if (seq.getAllocate() != 50 && seq.getAllocate() != -1) {
            abTblGen.add("allocationSize", seq.getAllocate() + "");
         }

         if (seq.getInitialValue() != 0 && seq.getInitialValue() != -1) {
            abTblGen.add("initialValue", seq.getInitialValue() + "");
         }

      }
   }

   protected class MappingSerializationComparator extends AnnotationPersistenceMetaDataSerializer.SerializationComparator {
      protected MappingSerializationComparator() {
         super();
      }

      protected int compareUnknown(Object o1, Object o2) {
         if (!(o1 instanceof QueryResultMapping)) {
            return super.compareUnknown(o1, o2);
         } else {
            QueryResultMapping res1 = (QueryResultMapping)o1;
            QueryResultMapping res2 = (QueryResultMapping)o2;
            Object scope1 = res1.getSourceScope();
            Object scope2 = res2.getSourceScope();
            if (scope1 == null && scope2 != null) {
               return -1;
            } else if (scope1 != null && scope2 == null) {
               return 1;
            } else {
               int listingIndex1 = res1.getListingIndex();
               int listingIndex2 = res2.getListingIndex();
               return listingIndex1 != listingIndex2 ? listingIndex1 - listingIndex2 : res1.getName().compareTo(res2.getName());
            }
         }
      }
   }

   private static enum ColType {
      COL,
      JOIN,
      INVERSE,
      PK_JOIN,
      DISC;

      private Class getColumnAnnotationType() {
         switch (this) {
            case JOIN:
            case INVERSE:
               return JoinColumn.class;
            case COL:
               return javax.persistence.Column.class;
            case PK_JOIN:
               return PrimaryKeyJoinColumn.class;
            case DISC:
               return DiscriminatorColumn.class;
            default:
               return null;
         }
      }

      private Class getColumnGroupAnnotationType() {
         switch (this) {
            case JOIN:
            case INVERSE:
               return JoinColumns.class;
            case COL:
            default:
               return null;
            case PK_JOIN:
               return PrimaryKeyJoinColumns.class;
         }
      }
   }
}
