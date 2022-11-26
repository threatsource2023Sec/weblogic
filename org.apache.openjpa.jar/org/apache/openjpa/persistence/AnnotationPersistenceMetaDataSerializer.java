package org.apache.openjpa.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.AbstractCFMetaDataFactory;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataInheritanceComparator;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.InternalException;
import serp.util.Strings;

public class AnnotationPersistenceMetaDataSerializer implements AbstractCFMetaDataFactory.Serializer {
   protected static final int TYPE_SEQ = 10;
   protected static final int TYPE_QUERY = 20;
   protected static final int TYPE_META = 30;
   protected static final int TYPE_CLASS_SEQS = 40;
   protected static final int TYPE_CLASS_QUERIES = 50;
   private static final Localizer _loc = Localizer.forPackage(AnnotationPersistenceMetaDataSerializer.class);
   private Log _log = null;
   private final OpenJPAConfiguration _conf;
   private Map _metas = null;
   private Map _queries = null;
   private Map _seqs = null;
   private int _mode = 0;
   private SerializationComparator _comp = null;
   private Map _clsAnnos = null;
   private Map _fldAnnos = null;
   private Map _seqAnnos = null;
   private Map _qryAnnos = null;

   public AnnotationPersistenceMetaDataSerializer(OpenJPAConfiguration conf) {
      this._conf = conf;
      this.setLog(conf.getLog("openjpa.MetaData"));
      this.setMode(7);
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public Log getLog() {
      return this._log;
   }

   public void setLog(Log log) {
      this._log = log;
   }

   public int getMode() {
      return this._mode;
   }

   public void setMode(int mode) {
      this._mode = mode;
   }

   public void setMode(int mode, boolean on) {
      if (mode == 0) {
         this.setMode(0);
      } else if (on) {
         this.setMode(this._mode | mode);
      } else {
         this.setMode(this._mode & ~mode);
      }

   }

   protected boolean isMetaDataMode() {
      return (this._mode & 1) != 0;
   }

   protected boolean isQueryMode() {
      return (this._mode & 4) != 0;
   }

   protected boolean isMappingMode() {
      return (this._mode & 2) != 0;
   }

   protected boolean isMappingMode(ClassMetaData meta) {
      return this.isMappingMode() && (meta.getSourceMode() & 2) != 0 && (meta.getEmbeddingMetaData() != null || !meta.isEmbeddedOnly()) && (meta.getEmbeddingMetaData() == null || this.isMappingMode(meta.getEmbeddingMetaData()));
   }

   protected boolean isMappingMode(ValueMetaData vmd) {
      return this.isMappingMode(vmd.getFieldMetaData().getDefiningMetaData());
   }

   public void addMetaData(ClassMetaData meta) {
      if (meta != null) {
         if (this._metas == null) {
            this._metas = new HashMap();
         }

         this._metas.put(meta.getDescribedType().getName(), meta);
      }
   }

   public void addSequenceMetaData(SequenceMetaData meta) {
      if (meta != null) {
         List seqs = null;
         String defName = null;
         if (meta.getSourceScope() instanceof Class) {
            defName = ((Class)meta.getSourceScope()).getName();
         }

         if (this._seqs == null) {
            this._seqs = new HashMap();
         } else {
            seqs = (List)this._seqs.get(defName);
         }

         if (seqs == null) {
            List seqs = new ArrayList(3);
            seqs.add(meta);
            this._seqs.put(defName, seqs);
         } else if (!seqs.contains(meta)) {
            seqs.add(meta);
         }

      }
   }

   public void addQueryMetaData(QueryMetaData meta) {
      if (meta != null) {
         List queries = null;
         String defName = null;
         if (meta.getSourceScope() instanceof Class) {
            defName = ((Class)meta.getSourceScope()).getName();
         }

         if (this._queries == null) {
            this._queries = new HashMap();
         } else {
            queries = (List)this._queries.get(defName);
         }

         if (queries == null) {
            List queries = new ArrayList(3);
            queries.add(meta);
            this._queries.put(defName, queries);
         } else if (!queries.contains(meta)) {
            queries.add(meta);
         }

      }
   }

   public void addAll(MetaDataRepository repos) {
      if (repos != null) {
         ClassMetaData[] arr$ = repos.getMetaDatas();
         int len$ = arr$.length;

         int i$;
         for(i$ = 0; i$ < len$; ++i$) {
            ClassMetaData meta = arr$[i$];
            this.addMetaData(meta);
         }

         SequenceMetaData[] arr$ = repos.getSequenceMetaDatas();
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            SequenceMetaData seq = arr$[i$];
            this.addSequenceMetaData(seq);
         }

         QueryMetaData[] arr$ = repos.getQueryMetaDatas();
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            QueryMetaData query = arr$[i$];
            this.addQueryMetaData(query);
         }

      }
   }

   public boolean removeMetaData(ClassMetaData meta) {
      return this._metas != null && meta != null && this._metas.remove(meta.getDescribedType().getName()) != null;
   }

   public boolean removeSequenceMetaData(SequenceMetaData meta) {
      if (this._seqs != null && meta != null) {
         String defName = null;
         if (meta.getSourceScope() instanceof Class) {
            defName = ((Class)meta.getSourceScope()).getName();
         }

         List seqs = (List)this._seqs.get(defName);
         if (seqs == null) {
            return false;
         } else if (!seqs.remove(meta)) {
            return false;
         } else {
            if (seqs.isEmpty()) {
               this._seqs.remove(defName);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean removeQueryMetaData(QueryMetaData meta) {
      if (this._queries != null && meta != null) {
         String defName = null;
         if (meta.getSourceScope() instanceof Class) {
            defName = ((Class)meta.getSourceScope()).getName();
         }

         List queries = (List)this._queries.get(defName);
         if (queries == null) {
            return false;
         } else if (!queries.remove(meta)) {
            return false;
         } else {
            if (queries.isEmpty()) {
               this._queries.remove(defName);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean removeAll(MetaDataRepository repos) {
      if (repos == null) {
         return false;
      } else {
         boolean removed = false;
         ClassMetaData[] metas = repos.getMetaDatas();

         for(int i = 0; i < metas.length; ++i) {
            removed |= this.removeMetaData(metas[i]);
         }

         SequenceMetaData[] seqs = repos.getSequenceMetaDatas();

         for(int i = 0; i < seqs.length; ++i) {
            removed |= this.removeSequenceMetaData(seqs[i]);
         }

         QueryMetaData[] queries = repos.getQueryMetaDatas();

         for(int i = 0; i < queries.length; ++i) {
            removed |= this.removeQueryMetaData(queries[i]);
         }

         return removed;
      }
   }

   public void clear() {
      if (this._metas != null) {
         this._metas.clear();
      }

      if (this._seqs != null) {
         this._seqs.clear();
      }

      if (this._queries != null) {
         this._queries.clear();
      }

   }

   protected void addSystemMappingElements(Collection toSerialize) {
   }

   private void serializationSort(List objs) {
      if (objs != null && !objs.isEmpty()) {
         if (this._comp == null) {
            this._comp = this.newSerializationComparator();
         }

         Collections.sort(objs, this._comp);
      }
   }

   protected SerializationComparator newSerializationComparator() {
      return this._comp;
   }

   private void addSequenceMetaDatas(Collection all) {
      if (this._seqs != null) {
         Iterator i$ = this._seqs.entrySet().iterator();

         while(true) {
            while(i$.hasNext()) {
               Map.Entry entry = (Map.Entry)i$.next();
               if (entry.getKey() == null) {
                  all.addAll((List)entry.getValue());
               } else if (this._metas == null || !this._metas.containsKey(entry.getKey())) {
                  all.add(new ClassSeqs((List)entry.getValue()));
               }
            }

            return;
         }
      }
   }

   private void addQueryMetaDatas(Collection all) {
      if (this._queries != null) {
         Iterator i$ = this._queries.entrySet().iterator();

         while(true) {
            while(i$.hasNext()) {
               Map.Entry entry = (Map.Entry)i$.next();
               if (entry.getKey() == null) {
                  all.addAll((List)entry.getValue());
               } else if (this._mode == 4 || this._metas == null || !this._metas.containsKey(entry.getKey())) {
                  all.add(new ClassQueries((List)entry.getValue()));
               }
            }

            return;
         }
      }
   }

   protected AnnotationBuilder newAnnotationBuilder(Class annType) {
      return new AnnotationBuilder(annType);
   }

   protected void addAnnotation(AnnotationBuilder ab, Object meta) {
      if (meta instanceof ClassMetaData) {
         this.addAnnotation(ab, (ClassMetaData)meta);
      } else if (meta instanceof FieldMetaData) {
         this.addAnnotation(ab, (FieldMetaData)meta);
      } else if (meta instanceof SequenceMetaData) {
         this.addAnnotation(ab, (SequenceMetaData)meta);
      } else if (meta instanceof QueryMetaData) {
         this.addAnnotation(ab, (QueryMetaData)meta);
      }

   }

   protected void addAnnotation(AnnotationBuilder ab, ClassMetaData meta) {
      if (this._clsAnnos == null) {
         this._clsAnnos = new HashMap();
      }

      List list = (List)this._clsAnnos.get(meta);
      if (list == null) {
         list = new ArrayList();
         this._clsAnnos.put(meta, list);
      }

      ((List)list).add(ab);
   }

   protected void addAnnotation(AnnotationBuilder ab, FieldMetaData meta) {
      if (this._fldAnnos == null) {
         this._fldAnnos = new HashMap();
      }

      List list = (List)this._fldAnnos.get(meta);
      if (list == null) {
         list = new ArrayList();
         this._fldAnnos.put(meta, list);
      }

      ((List)list).add(ab);
   }

   protected void addAnnotation(AnnotationBuilder ab, SequenceMetaData meta) {
      if (this._seqAnnos == null) {
         this._seqAnnos = new HashMap();
      }

      List list = (List)this._seqAnnos.get(meta);
      if (list == null) {
         list = new ArrayList();
         this._seqAnnos.put(meta, list);
      }

      ((List)list).add(ab);
   }

   protected void addAnnotation(AnnotationBuilder ab, QueryMetaData meta) {
      if (this._qryAnnos == null) {
         this._qryAnnos = new HashMap();
      }

      List list = (List)this._qryAnnos.get(meta);
      if (list == null) {
         list = new ArrayList();
         this._qryAnnos.put(meta, list);
      }

      ((List)list).add(ab);
   }

   protected AnnotationBuilder addAnnotation(Class annType, ClassMetaData meta) {
      AnnotationBuilder ab = this.newAnnotationBuilder(annType);
      if (meta == null) {
         return ab;
      } else {
         this.addAnnotation(ab, meta);
         return ab;
      }
   }

   protected AnnotationBuilder addAnnotation(Class annType, FieldMetaData meta) {
      AnnotationBuilder ab = this.newAnnotationBuilder(annType);
      if (meta == null) {
         return ab;
      } else {
         this.addAnnotation(ab, meta);
         return ab;
      }
   }

   protected AnnotationBuilder addAnnotation(Class annType, SequenceMetaData meta) {
      AnnotationBuilder ab = this.newAnnotationBuilder(annType);
      if (meta == null) {
         return ab;
      } else {
         this.addAnnotation(ab, meta);
         return ab;
      }
   }

   protected AnnotationBuilder addAnnotation(Class annType, QueryMetaData meta) {
      AnnotationBuilder ab = this.newAnnotationBuilder(annType);
      if (meta == null) {
         return ab;
      } else {
         this.addAnnotation(ab, meta);
         return ab;
      }
   }

   protected void serialize(Collection objects) {
      Iterator i$ = objects.iterator();

      while(true) {
         label38:
         while(i$.hasNext()) {
            Object obj = i$.next();
            int type = this.type(obj);
            int len$;
            int i$;
            switch (type) {
               case 10:
                  if (this.isMappingMode()) {
                     this.serializeSequence((SequenceMetaData)obj);
                  }
               case 20:
                  this.serializeQuery((QueryMetaData)obj);
                  break;
               case 30:
                  this.serializeClass((ClassMetaData)obj);
                  break;
               case 40:
                  if (!this.isMappingMode()) {
                     break;
                  }

                  SequenceMetaData[] arr$ = ((ClassSeqs)obj).getSequences();
                  len$ = arr$.length;
                  i$ = 0;

                  while(true) {
                     if (i$ >= len$) {
                        continue label38;
                     }

                     SequenceMetaData seq = arr$[i$];
                     this.serializeSequence(seq);
                     ++i$;
                  }
               case 50:
                  QueryMetaData[] arr$ = ((ClassQueries)obj).getQueries();
                  len$ = arr$.length;

                  for(i$ = 0; i$ < len$; ++i$) {
                     QueryMetaData query = arr$[i$];
                     this.serializeQuery(query);
                  }
                  break;
               default:
                  if (this.isMappingMode()) {
                     this.serializeSystemMappingElement(obj);
                  }
            }
         }

         return;
      }
   }

   protected int type(Object o) {
      if (o instanceof ClassMetaData) {
         return 30;
      } else if (o instanceof QueryMetaData) {
         return 20;
      } else if (o instanceof SequenceMetaData) {
         return 10;
      } else if (o instanceof ClassQueries) {
         return 50;
      } else {
         return o instanceof ClassSeqs ? 40 : -1;
      }
   }

   protected void serializeSystemMappingElement(Object obj) {
   }

   private void serializeQuery(QueryMetaData meta) {
      Log log = this.getLog();
      if (log.isInfoEnabled()) {
         if (meta.getSourceScope() instanceof Class) {
            log.info(_loc.get("ser-cls-query", meta.getSourceScope(), meta.getName()));
         } else {
            log.info(_loc.get("ser-query", (Object)meta.getName()));
         }
      }

      Class ann = "openjpa.SQL".equals(meta.getLanguage()) ? NamedNativeQuery.class : NamedQuery.class;
      AnnotationBuilder abQry = this.addAnnotation(ann, meta);
      abQry.add("name", meta.getName());
      abQry.add("query", meta.getQueryString());
      if ("openjpa.SQL".equals(meta.getLanguage()) && meta.getResultType() != null) {
         abQry.add("resultClass", meta.getResultType());
      }

      this.serializeQueryHints(meta, abQry);
   }

   private void serializeQueryHints(QueryMetaData meta, AnnotationBuilder ab) {
      String[] hints = meta.getHintKeys();
      Object[] values = meta.getHintValues();

      for(int i = 0; i < hints.length; ++i) {
         AnnotationBuilder abHint = this.newAnnotationBuilder(QueryHint.class);
         abHint.add("name", hints[i]);
         abHint.add("value", String.valueOf(values[i]));
         ab.add("hints", abHint);
      }

   }

   protected void serializeSequence(SequenceMetaData meta) {
      Log log = this.getLog();
      if (log.isInfoEnabled()) {
         log.info(_loc.get("ser-sequence", (Object)meta.getName()));
      }

      AnnotationBuilder ab = this.addAnnotation(SequenceGenerator.class, meta);
      ab.add("name", meta.getName());
      String plugin = meta.getSequencePlugin();
      String clsName = Configurations.getClassName(plugin);
      String props = Configurations.getProperties(plugin);
      String ds = null;
      if (props != null) {
         Properties map = Configurations.parseProperties(props);
         ds = (String)map.remove("Sequence");
         if (ds != null) {
            props = Configurations.serializeProperties(map);
            plugin = Configurations.getPlugin(clsName, props);
         }
      }

      if (ds != null) {
         ab.add("sequenceName", ds);
      } else if (plugin != null && !"native".equals(plugin)) {
         ab.add("sequenceName", plugin);
      }

      if (meta.getInitialValue() != 0 && meta.getInitialValue() != -1) {
         ab.add("initialValue", meta.getInitialValue());
      }

      if (meta.getAllocate() != 50 && meta.getAllocate() != -1) {
         ab.add("allocationSize", meta.getAllocate());
      }

   }

   protected void serializeClass(ClassMetaData meta) {
      Log log = this.getLog();
      if (log.isInfoEnabled()) {
         log.info(_loc.get("ser-class", (Object)meta));
      }

      AnnotationBuilder abEntity = this.addAnnotation(getEntityAnnotationType(meta), meta);
      if (this.isMetaDataMode() && !meta.getTypeAlias().equals(Strings.getClassName(meta.getDescribedType()))) {
         abEntity.add("name", meta.getTypeAlias());
      }

      if (this.isMappingMode()) {
         this.addClassMappingAnnotations(meta);
      }

      if (this.isMappingMode()) {
         this.serializeClassMappingContent(meta);
      }

      if (this.isMetaDataMode()) {
         this.serializeIdClass(meta);
      }

      if (this.isMappingMode()) {
         this.serializeInheritanceContent(meta);
      }

      List queries;
      int i;
      if (this.isMappingMode()) {
         queries = this._seqs == null ? null : (List)this._seqs.get(meta.getDescribedType().getName());
         if (queries != null) {
            this.serializationSort(queries);

            for(i = 0; i < queries.size(); ++i) {
               this.serializeSequence((SequenceMetaData)queries.get(i));
            }
         }
      }

      if (this.isQueryMode()) {
         queries = this._queries == null ? null : (List)this._queries.get(meta.getDescribedType().getName());
         if (queries != null) {
            this.serializationSort(queries);

            for(i = 0; i < queries.size(); ++i) {
               this.serializeQuery((QueryMetaData)queries.get(i));
            }
         }

         if (this.isMappingMode()) {
            this.serializeQueryMappings(meta);
         }
      }

      List fields = new ArrayList(Arrays.asList(meta.getDefinedFieldsInListingOrder()));
      Collections.sort(fields, new FieldComparator());
      FieldMetaData orig;
      if (this.isMappingMode()) {
         Iterator it = fields.iterator();

         while(it.hasNext()) {
            orig = (FieldMetaData)it.next();
            if (meta.getDefinedSuperclassField(orig.getName()) != null) {
               FieldMetaData orig = meta.getPCSuperclassMetaData().getField(orig.getName());
               if (this.serializeAttributeOverride(orig, orig)) {
                  this.serializeAttributeOverrideContent(orig, orig);
               }

               it.remove();
            }
         }
      }

      FieldMetaData fmd;
      if (fields.size() > 0 && (this.isMetaDataMode() || this.isMappingMode())) {
         for(Iterator i$ = fields.iterator(); i$.hasNext(); this.serializeField(fmd, orig)) {
            fmd = (FieldMetaData)i$.next();
            if (fmd.getDeclaringType() != fmd.getDefiningMetaData().getDescribedType()) {
               orig = fmd.getDeclaringMetaData().getDeclaredField(fmd.getName());
            } else {
               orig = null;
            }
         }
      }

   }

   private static Class getEntityAnnotationType(ClassMetaData meta) {
      switch (getEntityTag(meta)) {
         case ENTITY:
            return Entity.class;
         case EMBEDDABLE:
            return Embeddable.class;
         case MAPPED_SUPERCLASS:
            return MappedSuperclass.class;
         default:
            throw new IllegalStateException();
      }
   }

   private static Class getFieldAnnotationType(FieldMetaData fmd, PersistenceStrategy strat) {
      Class ann = null;
      if (fmd.isPrimaryKey() && strat == PersistenceStrategy.EMBEDDED) {
         ann = EmbeddedId.class;
      } else if (fmd.isPrimaryKey()) {
         ann = Id.class;
      } else if (fmd.isVersion()) {
         ann = Version.class;
      } else {
         switch (strat) {
            case TRANSIENT:
               ann = Transient.class;
               break;
            case BASIC:
               ann = Basic.class;
               break;
            case EMBEDDED:
               ann = Embedded.class;
               break;
            case MANY_ONE:
               ann = ManyToOne.class;
               break;
            case ONE_ONE:
               ann = OneToOne.class;
               break;
            case ONE_MANY:
               ann = OneToMany.class;
               break;
            case MANY_MANY:
               ann = ManyToMany.class;
         }
      }

      return ann;
   }

   private static MetaDataTag getEntityTag(ClassMetaData meta) {
      if (meta.isEmbeddedOnly() && meta.getPrimaryKeyFields().length == 0) {
         return MetaDataTag.EMBEDDABLE;
      } else {
         return meta.isMapped() ? MetaDataTag.ENTITY : MetaDataTag.MAPPED_SUPERCLASS;
      }
   }

   protected void addClassMappingAnnotations(ClassMetaData mapping) {
   }

   private void serializeIdClass(ClassMetaData meta) {
      if (meta.getIdentityType() == 2 && !meta.isOpenJPAIdentity()) {
         ClassMetaData sup = meta.getPCSuperclassMetaData();
         Class oid = meta.getObjectIdType();
         if (oid != null && (sup == null || oid != sup.getObjectIdType())) {
            AnnotationBuilder ab = this.addAnnotation(IdClass.class, meta);
            ab.add((String)null, (Class)oid);
         }

      }
   }

   protected void serializeClassMappingContent(ClassMetaData mapping) {
   }

   protected void serializeInheritanceContent(ClassMetaData mapping) {
   }

   protected void serializeQueryMappings(ClassMetaData meta) {
   }

   private void serializeField(FieldMetaData fmd, FieldMetaData orig) {
      if (fmd.getManagement() == 3 || fmd.isExplicit()) {
         PersistenceStrategy strat = this.getStrategy(fmd);
         ValueMetaData cascades = null;
         AnnotationBuilder ab = this.addAnnotation(getFieldAnnotationType(fmd, strat), fmd);
         if ((!fmd.isPrimaryKey() || strat != PersistenceStrategy.EMBEDDED) && !fmd.isPrimaryKey() && !fmd.isVersion()) {
            switch (strat) {
               case BASIC:
                  if (this.isMetaDataMode()) {
                     this.addBasicAttributes(fmd, ab);
                  }
               case EMBEDDED:
               default:
                  break;
               case MANY_ONE:
                  if (this.isMetaDataMode()) {
                     this.addManyToOneAttributes(fmd, ab);
                  }

                  cascades = fmd;
                  break;
               case ONE_ONE:
                  if (this.isMetaDataMode()) {
                     this.addOneToOneAttributes(fmd, ab);
                  }

                  cascades = fmd;
                  break;
               case ONE_MANY:
                  if (this.isMetaDataMode()) {
                     this.addOneToManyAttributes(fmd, ab);
                  }

                  cascades = fmd.getElement();
                  break;
               case MANY_MANY:
                  if (this.isMetaDataMode()) {
                     this.addManyToManyAttributes(fmd, ab);
                  }

                  cascades = fmd.getElement();
            }

            if (this.isMappingMode()) {
               this.addStrategyMappingAttributes(fmd, ab);
            }
         }

         if (this.isMappingMode((ValueMetaData)fmd)) {
            this.addFieldMappingAttributes(fmd, orig, ab);
         }

         if (fmd.getOrderDeclaration() != null && !"#element asc".equals(fmd.getOrderDeclaration())) {
            this.addAnnotation(OrderBy.class, fmd).add((String)null, (String)fmd.getOrderDeclaration());
         }

         if (this.isMappingMode() && fmd.getKey().getValueMappedBy() != null) {
            AnnotationBuilder abMapKey = this.addAnnotation(MapKey.class, fmd);
            FieldMetaData mapBy = fmd.getKey().getValueMappedByMetaData();
            if (!mapBy.isPrimaryKey() || mapBy.getDefiningMetaData().getPrimaryKeyFields().length != 1) {
               abMapKey.add("name", fmd.getKey().getValueMappedBy());
            }
         }

         if (this.isMappingMode((ValueMetaData)fmd)) {
            this.serializeFieldMappingContent(fmd, strat, ab);
         }

         if (cascades != null && this.isMetaDataMode()) {
            this.serializeCascades((ValueMetaData)cascades, ab);
         }

      }
   }

   protected void addFieldMappingAttributes(FieldMetaData fmd, FieldMetaData orig, AnnotationBuilder ab) {
   }

   protected boolean serializeAttributeOverride(FieldMetaData fmd, FieldMetaData orig) {
      return false;
   }

   private void serializeAttributeOverrideContent(FieldMetaData fmd, FieldMetaData orig) {
      AnnotationBuilder ab = this.addAnnotation(AttributeOverride.class, fmd);
      ab.add("name", fmd.getName());
      this.serializeAttributeOverrideMappingContent(fmd, orig, ab);
   }

   protected void serializeAttributeOverrideMappingContent(FieldMetaData fmd, FieldMetaData orig, AnnotationBuilder ab) {
   }

   private void serializeCascades(ValueMetaData vmd, AnnotationBuilder ab) {
      EnumSet cascades = EnumSet.noneOf(CascadeType.class);
      if (vmd.getCascadePersist() == 1) {
         cascades.add(CascadeType.PERSIST);
      }

      if (vmd.getCascadeAttach() == 1) {
         cascades.add(CascadeType.MERGE);
      }

      if (vmd.getCascadeDelete() == 1) {
         cascades.add(CascadeType.REMOVE);
      }

      if (vmd.getCascadeRefresh() == 1) {
         cascades.add(CascadeType.REFRESH);
      }

      if (cascades.size() == 4) {
         cascades.clear();
         cascades.add(CascadeType.ALL);
      }

      if (!cascades.isEmpty()) {
         ab.add("cascade", cascades);
      }

   }

   protected PersistenceStrategy getStrategy(FieldMetaData fmd) {
      if (fmd.getManagement() == 0) {
         return PersistenceStrategy.TRANSIENT;
      } else if (!fmd.isSerialized() && fmd.getDeclaredType() != byte[].class && fmd.getDeclaredType() != Byte[].class && fmd.getDeclaredType() != char[].class && fmd.getDeclaredType() != Character[].class) {
         switch (fmd.getDeclaredTypeCode()) {
            case 11:
            case 12:
            case 13:
               FieldMetaData mappedBy = fmd.getMappedByMetaData();
               if (mappedBy != null && mappedBy.getTypeCode() == 15) {
                  return PersistenceStrategy.ONE_MANY;
               }

               return PersistenceStrategy.MANY_MANY;
            case 15:
               if (fmd.isEmbedded()) {
                  return PersistenceStrategy.EMBEDDED;
               } else if (fmd.getMappedBy() != null) {
                  return PersistenceStrategy.ONE_ONE;
               } else {
                  FieldMetaData[] inverses = fmd.getInverseMetaDatas();
                  if (inverses.length == 1 && inverses[0].getTypeCode() == 15 && inverses[0].getMappedByMetaData() == fmd) {
                     return PersistenceStrategy.ONE_ONE;
                  }

                  return PersistenceStrategy.MANY_ONE;
               }
            case 29:
               return PersistenceStrategy.EMBEDDED;
            default:
               return PersistenceStrategy.BASIC;
         }
      } else {
         return PersistenceStrategy.BASIC;
      }
   }

   private void addBasicAttributes(FieldMetaData fmd, AnnotationBuilder ab) {
      if (!fmd.isInDefaultFetchGroup()) {
         ab.add("fetch", (Enum)FetchType.LAZY);
      }

      if (fmd.getNullValue() == 2) {
         ab.add("optional", false);
      }

   }

   private void addManyToOneAttributes(FieldMetaData fmd, AnnotationBuilder ab) {
      if (!fmd.isInDefaultFetchGroup()) {
         ab.add("fetch", (Enum)FetchType.LAZY);
      }

      if (fmd.getNullValue() == 2) {
         ab.add("optional", false);
      }

   }

   private void addOneToOneAttributes(FieldMetaData fmd, AnnotationBuilder ab) {
      if (!fmd.isInDefaultFetchGroup()) {
         ab.add("fetch", (Enum)FetchType.LAZY);
      }

      if (fmd.getNullValue() == 2) {
         ab.add("optional", false);
      }

   }

   private void addOneToManyAttributes(FieldMetaData fmd, AnnotationBuilder ab) {
      if (fmd.isInDefaultFetchGroup()) {
         ab.add("fetch", (Enum)FetchType.EAGER);
      }

      this.addTargetEntityAttribute(fmd, ab);
   }

   private void addManyToManyAttributes(FieldMetaData fmd, AnnotationBuilder ab) {
      if (fmd.isInDefaultFetchGroup()) {
         ab.add("fetch", (Enum)FetchType.EAGER);
      }

      this.addTargetEntityAttribute(fmd, ab);
   }

   private void addTargetEntityAttribute(FieldMetaData fmd, AnnotationBuilder ab) {
      Member member = fmd.getBackingMember();
      Class[] types;
      if (member instanceof Field) {
         types = JavaVersions.getParameterizedTypes((Field)member);
      } else if (member instanceof Method) {
         types = JavaVersions.getParameterizedTypes((Method)member);
      } else {
         types = new Class[0];
      }

      switch (fmd.getDeclaredTypeCode()) {
         case 12:
            if (types.length != 1) {
               ab.add("targetEntity", fmd.getElement().getDeclaredType());
            }
            break;
         case 13:
            if (types.length != 2) {
               ab.add("targetEntity", fmd.getElement().getDeclaredType());
            }
      }

   }

   protected void serializeFieldMappingContent(FieldMetaData fmd, PersistenceStrategy strategy, AnnotationBuilder ab) {
   }

   protected void addStrategyMappingAttributes(FieldMetaData fmd, AnnotationBuilder ab) {
      if (fmd.getMappedBy() != null) {
         ab.add("mappedBy", fmd.getMappedBy());
      }

   }

   protected Collection getObjects() {
      List all = new ArrayList();
      if (this.isQueryMode()) {
         this.addQueryMetaDatas(all);
      }

      if (this.isMappingMode()) {
         this.addSequenceMetaDatas(all);
      }

      if ((this.isMetaDataMode() || this.isMappingMode()) && this._metas != null) {
         all.addAll(this._metas.values());
      }

      if (this.isMappingMode()) {
         this.addSystemMappingElements(all);
      }

      this.serializationSort(all);
      return all;
   }

   protected void writeAnnotations(Object meta, List builders, Map output) {
      List annos = new ArrayList();
      Iterator i$ = builders.iterator();

      while(i$.hasNext()) {
         AnnotationBuilder ab = (AnnotationBuilder)i$.next();
         annos.add(ab.toString());
      }

      output.put(meta, annos);
   }

   public void serialize(Map output, int flags) throws IOException {
      Collection all = this.getObjects();
      this.serialize(all);
      Iterator i$;
      if (this._clsAnnos != null) {
         i$ = this._clsAnnos.keySet().iterator();

         while(i$.hasNext()) {
            ClassMetaData meta = (ClassMetaData)i$.next();
            this.writeAnnotations(meta, (List)this._clsAnnos.get(meta), output);
         }
      }

      if (this._fldAnnos != null) {
         i$ = this._fldAnnos.keySet().iterator();

         while(i$.hasNext()) {
            FieldMetaData meta = (FieldMetaData)i$.next();
            this.writeAnnotations(meta, (List)this._fldAnnos.get(meta), output);
         }
      }

      if (this._seqAnnos != null) {
         i$ = this._seqAnnos.keySet().iterator();

         while(i$.hasNext()) {
            SequenceMetaData meta = (SequenceMetaData)i$.next();
            this.writeAnnotations(meta, (List)this._seqAnnos.get(meta), output);
         }
      }

      if (this._qryAnnos != null) {
         i$ = this._qryAnnos.keySet().iterator();

         while(i$.hasNext()) {
            QueryMetaData meta = (QueryMetaData)i$.next();
            this.writeAnnotations(meta, (List)this._qryAnnos.get(meta), output);
         }
      }

   }

   public void serialize(File file, int flags) throws IOException {
      try {
         FileWriter out = new FileWriter((String)AccessController.doPrivileged(J2DoPrivHelper.getCanonicalPathAction(file)), (flags & 2) > 0);
         this.serialize((Writer)out, flags);
         out.close();
      } catch (PrivilegedActionException var4) {
         throw (IOException)var4.getException();
      }
   }

   public void serialize(Writer out, int flags) throws IOException {
      Map output = new HashMap();
      this.serialize((Map)output, flags);
      Iterator i$ = output.keySet().iterator();

      while(i$.hasNext()) {
         Object meta = i$.next();
         out.write("--" + meta.toString());
         out.write("\n");
         List annos = (List)output.get(meta);
         Iterator i$ = annos.iterator();

         while(i$.hasNext()) {
            String ann = (String)i$.next();
            out.write("\t");
            out.write(ann);
            out.write("\n");
         }
      }

   }

   public void serialize(int flags) throws IOException {
      throw new UnsupportedOperationException();
   }

   private class FieldComparator implements Comparator {
      private FieldComparator() {
      }

      public int compare(Object o1, Object o2) {
         FieldMetaData fmd1 = (FieldMetaData)o1;
         FieldMetaData fmd2 = (FieldMetaData)o2;
         if (fmd1.isPrimaryKey()) {
            return fmd2.isPrimaryKey() ? fmd1.compareTo(fmd2) : -1;
         } else if (fmd2.isPrimaryKey()) {
            return 1;
         } else if (fmd1.isVersion()) {
            if (fmd2.isVersion()) {
               return this.compareListingOrder(fmd1, fmd2);
            } else {
               return AnnotationPersistenceMetaDataSerializer.this.getStrategy(fmd2) == PersistenceStrategy.BASIC ? 1 : -1;
            }
         } else if (fmd2.isVersion()) {
            return AnnotationPersistenceMetaDataSerializer.this.getStrategy(fmd1) == PersistenceStrategy.BASIC ? -1 : 1;
         } else {
            int stcmp = AnnotationPersistenceMetaDataSerializer.this.getStrategy(fmd1).compareTo(AnnotationPersistenceMetaDataSerializer.this.getStrategy(fmd2));
            return stcmp != 0 ? stcmp : this.compareListingOrder(fmd1, fmd2);
         }
      }

      private int compareListingOrder(FieldMetaData fmd1, FieldMetaData fmd2) {
         int lcmp = fmd1.getListingIndex() - fmd2.getListingIndex();
         return lcmp != 0 ? lcmp : fmd1.compareTo(fmd2);
      }

      // $FF: synthetic method
      FieldComparator(Object x1) {
         this();
      }
   }

   protected class SerializationComparator extends MetaDataInheritanceComparator {
      public int compare(Object o1, Object o2) {
         if (o1 == o2) {
            return 0;
         } else if (o1 == null) {
            return 1;
         } else if (o2 == null) {
            return -1;
         } else {
            int t1 = AnnotationPersistenceMetaDataSerializer.this.type(o1);
            int t2 = AnnotationPersistenceMetaDataSerializer.this.type(o2);
            if (t1 != t2) {
               return t1 - t2;
            } else {
               switch (t1) {
                  case 10:
                     return this.compare((SequenceMetaData)o1, (SequenceMetaData)o2);
                  case 20:
                     return this.compare((QueryMetaData)o1, (QueryMetaData)o2);
                  case 30:
                     return this.compare((ClassMetaData)o1, (ClassMetaData)o2);
                  case 40:
                     return ((Comparable)o1).compareTo(o2);
                  case 50:
                     return ((Comparable)o1).compareTo(o2);
                  default:
                     return this.compareUnknown(o1, o2);
               }
            }
         }
      }

      protected int compareUnknown(Object o1, Object o2) {
         throw new InternalException();
      }

      private int compare(ClassMetaData o1, ClassMetaData o2) {
         int li1 = o1.getListingIndex();
         int li2 = o2.getListingIndex();
         if (li1 == -1 && li2 == -1) {
            MetaDataTag t1 = AnnotationPersistenceMetaDataSerializer.getEntityTag(o1);
            MetaDataTag t2 = AnnotationPersistenceMetaDataSerializer.getEntityTag(o2);
            if (t1.compareTo(t2) != 0) {
               return t1.compareTo(t2);
            } else {
               int inher = super.compare(o1, o2);
               return inher != 0 ? inher : o1.getDescribedType().getName().compareTo(o2.getDescribedType().getName());
            }
         } else if (li1 == -1) {
            return 1;
         } else {
            return li2 == -1 ? -1 : li1 - li2;
         }
      }

      private int compare(QueryMetaData o1, QueryMetaData o2) {
         if (!StringUtils.equals(o1.getLanguage(), o2.getLanguage())) {
            return "openjpa.SQL".equals(o1.getLanguage()) ? 1 : -1;
         } else {
            return o1.getName().compareTo(o2.getName());
         }
      }

      private int compare(SequenceMetaData o1, SequenceMetaData o2) {
         return o1.getName().compareTo(o2.getName());
      }
   }

   private static class ClassQueries implements SourceTracker, Comparable, Comparator {
      private final QueryMetaData[] _queries;

      public ClassQueries(List queries) {
         if (queries != null && !queries.isEmpty()) {
            this._queries = (QueryMetaData[])((QueryMetaData[])queries.toArray(new QueryMetaData[queries.size()]));
            Arrays.sort(this._queries, this);
         } else {
            throw new InternalException();
         }
      }

      public QueryMetaData[] getQueries() {
         return this._queries;
      }

      public int compare(QueryMetaData o1, QueryMetaData o2) {
         if (!StringUtils.equals(o1.getLanguage(), o2.getLanguage())) {
            return "openjpa.SQL".equals(o1.getLanguage()) ? 1 : -1;
         } else {
            return o1.getName().compareTo(o2.getName());
         }
      }

      public File getSourceFile() {
         return this._queries[0].getSourceFile();
      }

      public Object getSourceScope() {
         return this._queries[0].getSourceScope();
      }

      public int getSourceType() {
         return this._queries[0].getSourceType();
      }

      public String getResourceName() {
         return this._queries[0].getResourceName();
      }

      public int compareTo(ClassQueries other) {
         if (other == this) {
            return 0;
         } else if (other == null) {
            return -1;
         } else {
            Class scope = (Class)this.getSourceScope();
            Class oscope = (Class)other.getSourceScope();
            return scope.getName().compareTo(oscope.getName());
         }
      }
   }

   private static class ClassSeqs implements SourceTracker, Comparable, Comparator {
      private final SequenceMetaData[] _seqs;

      public ClassSeqs(List seqs) {
         if (seqs != null && !seqs.isEmpty()) {
            this._seqs = (SequenceMetaData[])((SequenceMetaData[])seqs.toArray(new SequenceMetaData[seqs.size()]));
            Arrays.sort(this._seqs, this);
         } else {
            throw new InternalException();
         }
      }

      public SequenceMetaData[] getSequences() {
         return this._seqs;
      }

      public int compare(SequenceMetaData o1, SequenceMetaData o2) {
         return o1.getName().compareTo(o2.getName());
      }

      public File getSourceFile() {
         return this._seqs[0].getSourceFile();
      }

      public Object getSourceScope() {
         return this._seqs[0].getSourceScope();
      }

      public int getSourceType() {
         return this._seqs[0].getSourceType();
      }

      public String getResourceName() {
         return this._seqs[0].getResourceName();
      }

      public int compareTo(ClassSeqs other) {
         if (other == this) {
            return 0;
         } else if (other == null) {
            return -1;
         } else {
            Class scope = (Class)this.getSourceScope();
            Class oscope = (Class)other.getSourceScope();
            return scope.getName().compareTo(oscope.getName());
         }
      }
   }
}
