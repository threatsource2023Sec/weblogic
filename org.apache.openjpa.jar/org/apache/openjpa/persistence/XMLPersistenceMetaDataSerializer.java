package org.apache.openjpa.persistence;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.CFMetaDataSerializer;
import org.apache.openjpa.lib.meta.SourceTracker;
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
import org.xml.sax.SAXException;
import serp.util.Strings;

public class XMLPersistenceMetaDataSerializer extends CFMetaDataSerializer implements AbstractCFMetaDataFactory.Serializer {
   protected static final int TYPE_SEQ = 10;
   protected static final int TYPE_QUERY = 20;
   protected static final int TYPE_META = 30;
   protected static final int TYPE_CLASS_SEQS = 40;
   protected static final int TYPE_CLASS_QUERIES = 50;
   private static final Localizer _loc = Localizer.forPackage(XMLPersistenceMetaDataSerializer.class);
   private final OpenJPAConfiguration _conf;
   private Map _metas = null;
   private Map _queries = null;
   private Map _seqs = null;
   private int _mode = 0;
   private boolean _annos = true;
   private SerializationComparator _comp = null;

   public XMLPersistenceMetaDataSerializer(OpenJPAConfiguration conf) {
      this._conf = conf;
      this.setLog(conf.getLog("openjpa.MetaData"));
      this.setMode(7);
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public boolean getSerializeAnnotations() {
      return this._annos;
   }

   public void setSerializeAnnotations(boolean annos) {
      this._annos = annos;
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

   protected File getSourceFile(Object obj) {
      File file = super.getSourceFile(obj);
      return file != null && !file.getName().endsWith(".java") && !file.getName().endsWith(".class") ? file : null;
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

   protected void serialize(Collection objects) throws SAXException {
      boolean unique = true;
      boolean fieldAccess = false;
      boolean propertyAccess = false;
      Iterator i$ = objects.iterator();

      while(i$.hasNext()) {
         Object meta = i$.next();
         switch (this.type(meta)) {
            case 30:
               ClassMetaData cls = (ClassMetaData)meta;
               if (cls.getAccessType() == 2) {
                  fieldAccess = true;
               } else {
                  propertyAccess = true;
               }
         }

         if (unique && this.getPackage() == null) {
            this.setPackage(this.getPackage(meta));
         } else if (unique) {
            unique = this.getPackage().equals(this.getPackage(meta));
            if (!unique) {
               this.setPackage((String)null);
            }
         }
      }

      this.serializeNamespaceAttributes();
      this.startElement("entity-mappings");
      if (this.getPackage() != null) {
         this.startElement("package");
         this.addText(this.getPackage());
         this.endElement("package");
      }

      if (fieldAccess != propertyAccess) {
         int def = this.getConfiguration().getMetaDataRepositoryInstance().getMetaDataFactory().getDefaults().getDefaultAccessType();
         String access = null;
         if (fieldAccess && def == 4) {
            access = "FIELD";
         } else if (propertyAccess && def == 2) {
            access = "PROPERTY";
         }

         if (access != null) {
            this.startElement("access");
            this.addText(access);
            this.endElement("access");
         }
      }

      i$ = objects.iterator();

      while(true) {
         label84:
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
                  this.serializeClass((ClassMetaData)obj, fieldAccess && propertyAccess);
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
                        continue label84;
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

         this.endElement("entity-mappings");
         return;
      }
   }

   protected String getPackage(Object obj) {
      int type = this.type(obj);
      switch (type) {
         case 10:
         case 20:
         case 40:
         case 50:
            SourceTracker st = (SourceTracker)obj;
            if (st.getSourceScope() instanceof Class) {
               return Strings.getPackageName((Class)st.getSourceScope());
            }

            return null;
         case 30:
            return Strings.getPackageName(((ClassMetaData)obj).getDescribedType());
         default:
            return null;
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

   private void serializeNamespaceAttributes() throws SAXException {
      this.addAttribute("xmlns", "http://java.sun.com/xml/ns/persistence/orm");
      this.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      this.addAttribute("xsi:schemaLocation", "http://java.sun.com/xml/ns/persistence/orm orm_1_0.xsd");
      this.addAttribute("version", "1.0");
   }

   protected void serializeSystemMappingElement(Object obj) throws SAXException {
   }

   private void serializeQuery(QueryMetaData meta) throws SAXException {
      if (this._annos || meta.getSourceType() != 1) {
         Log log = this.getLog();
         if (log.isInfoEnabled()) {
            if (meta.getSourceScope() instanceof Class) {
               log.info(_loc.get("ser-cls-query", meta.getSourceScope(), meta.getName()));
            } else {
               log.info(_loc.get("ser-query", (Object)meta.getName()));
            }
         }

         this.addComments(meta);
         this.addAttribute("name", meta.getName());
         this.addAttribute("query", meta.getQueryString());
         if ("openjpa.SQL".equals(meta.getLanguage())) {
            if (meta.getResultType() != null) {
               this.addAttribute("result-class", meta.getResultType().getName());
            }

            this.startElement("named-native-query");
            this.serializeQueryHints(meta);
            this.endElement("named-native-query");
         } else {
            this.startElement("named-query");
            this.serializeQueryHints(meta);
            this.endElement("named-query");
         }

      }
   }

   private void serializeQueryHints(QueryMetaData meta) throws SAXException {
      String[] hints = meta.getHintKeys();
      Object[] values = meta.getHintValues();

      for(int i = 0; i < hints.length; ++i) {
         this.addAttribute("name", hints[i]);
         this.addAttribute("value", String.valueOf(values[i]));
         this.startElement("query-hint");
         this.endElement("query-hint");
      }

   }

   protected void serializeSequence(SequenceMetaData meta) throws SAXException {
      if (this._annos || meta.getSourceType() != 1) {
         Log log = this.getLog();
         if (log.isInfoEnabled()) {
            log.info(_loc.get("ser-sequence", (Object)meta.getName()));
         }

         this.addComments(meta);
         this.addAttribute("name", meta.getName());
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
            this.addAttribute("sequence-name", ds);
         } else if (plugin != null && !"native".equals(plugin)) {
            this.addAttribute("sequence-name", plugin);
         }

         if (meta.getInitialValue() != 0 && meta.getInitialValue() != -1) {
            this.addAttribute("initial-value", String.valueOf(meta.getInitialValue()));
         }

         if (meta.getAllocate() != 50 && meta.getAllocate() != -1) {
            this.addAttribute("allocation-size", String.valueOf(meta.getAllocate()));
         }

         this.startElement("sequence-generator");
         this.endElement("sequence-generator");
      }
   }

   protected void serializeClass(ClassMetaData meta, boolean access) throws SAXException {
      if (this._annos || meta.getSourceType() != 1) {
         Log log = this.getLog();
         if (log.isInfoEnabled()) {
            log.info(_loc.get("ser-class", (Object)meta));
         }

         this.addComments(meta);
         this.addAttribute("class", this.getClassName(meta.getDescribedType().getName()));
         if (this.isMetaDataMode() && !meta.getTypeAlias().equals(Strings.getClassName(meta.getDescribedType()))) {
            this.addAttribute("name", meta.getTypeAlias());
         }

         String name = getEntityElementName(meta);
         if (this.isMetaDataMode()) {
            this.addClassAttributes(meta, access);
         }

         if (this.isMappingMode()) {
            this.addClassMappingAttributes(meta);
         }

         this.startElement(name);
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

         if (fields.size() > 0 && (this.isMetaDataMode() || this.isMappingMode())) {
            this.startElement("attributes");

            FieldMetaData fmd;
            for(Iterator i$ = fields.iterator(); i$.hasNext(); this.serializeField(fmd, orig)) {
               fmd = (FieldMetaData)i$.next();
               if (fmd.getDeclaringType() != fmd.getDefiningMetaData().getDescribedType()) {
                  orig = fmd.getDeclaringMetaData().getDeclaredField(fmd.getName());
               } else {
                  orig = null;
               }
            }

            this.endElement("attributes");
         }

         this.endElement(name);
      }
   }

   private static String getEntityElementName(ClassMetaData meta) {
      switch (getEntityTag(meta)) {
         case ENTITY:
            return "entity";
         case EMBEDDABLE:
            return "embeddable";
         case MAPPED_SUPERCLASS:
            return "mapped-superclass";
         default:
            throw new IllegalStateException();
      }
   }

   private static MetaDataTag getEntityTag(ClassMetaData meta) {
      if (meta.isEmbeddedOnly() && meta.getPrimaryKeyFields().length == 0) {
         return MetaDataTag.EMBEDDABLE;
      } else {
         return meta.isMapped() ? MetaDataTag.ENTITY : MetaDataTag.MAPPED_SUPERCLASS;
      }
   }

   private void addClassAttributes(ClassMetaData meta, boolean access) {
      if (access) {
         int def = this.getConfiguration().getMetaDataRepositoryInstance().getMetaDataFactory().getDefaults().getDefaultAccessType();
         if (meta.getAccessType() == 2 && def == 4) {
            this.addAttribute("access", "FIELD");
         } else if (meta.getAccessType() == 4 && def == 2) {
            this.addAttribute("access", "PROPERTY");
         }

      }
   }

   protected void addClassMappingAttributes(ClassMetaData mapping) throws SAXException {
   }

   private void serializeIdClass(ClassMetaData meta) throws SAXException {
      if (meta.getIdentityType() == 2 && !meta.isOpenJPAIdentity()) {
         ClassMetaData sup = meta.getPCSuperclassMetaData();
         Class oid = meta.getObjectIdType();
         if (oid != null && (sup == null || oid != sup.getObjectIdType())) {
            this.addAttribute("class", this.getClassName(oid.getName()));
            this.startElement("id-class");
            this.endElement("id-class");
         }

      }
   }

   protected void serializeClassMappingContent(ClassMetaData mapping) throws SAXException {
   }

   protected void serializeInheritanceContent(ClassMetaData mapping) throws SAXException {
   }

   protected void serializeQueryMappings(ClassMetaData meta) throws SAXException {
   }

   private void serializeField(FieldMetaData fmd, FieldMetaData orig) throws SAXException {
      if (fmd.getManagement() == 3 || fmd.isExplicit()) {
         this.addComments(fmd);
         this.addAttribute("name", fmd.getName());
         String strategy = null;
         PersistenceStrategy strat = this.getStrategy(fmd);
         ValueMetaData cascades = null;
         if (fmd.isPrimaryKey() && strat == PersistenceStrategy.EMBEDDED) {
            strategy = "embedded-id";
         } else if (fmd.isPrimaryKey()) {
            strategy = "id";
         } else if (fmd.isVersion()) {
            strategy = "version";
         } else {
            switch (strat) {
               case TRANSIENT:
                  strategy = "transient";
                  break;
               case BASIC:
                  if (this.isMetaDataMode()) {
                     this.addBasicAttributes(fmd);
                  }

                  strategy = "basic";
                  break;
               case EMBEDDED:
                  strategy = "embedded";
                  break;
               case MANY_ONE:
                  if (this.isMetaDataMode()) {
                     this.addManyToOneAttributes(fmd);
                  }

                  strategy = "many-to-one";
                  cascades = fmd;
                  break;
               case ONE_ONE:
                  if (this.isMetaDataMode()) {
                     this.addOneToOneAttributes(fmd);
                  }

                  strategy = "one-to-one";
                  cascades = fmd;
                  break;
               case ONE_MANY:
                  if (this.isMetaDataMode()) {
                     this.addOneToManyAttributes(fmd);
                  }

                  strategy = "one-to-many";
                  cascades = fmd.getElement();
                  break;
               case MANY_MANY:
                  if (this.isMetaDataMode()) {
                     this.addManyToManyAttributes(fmd);
                  }

                  strategy = "many-to-many";
                  cascades = fmd.getElement();
            }

            if (this.isMappingMode()) {
               this.addStrategyMappingAttributes(fmd);
            }
         }

         if (this.isMappingMode((ValueMetaData)fmd)) {
            this.addFieldMappingAttributes(fmd, orig);
         }

         this.startElement(strategy);
         if (fmd.getOrderDeclaration() != null) {
            this.startElement("order-by");
            if (!"#element asc".equals(fmd.getOrderDeclaration())) {
               this.addText(fmd.getOrderDeclaration());
            }

            this.endElement("order-by");
         }

         if (this.isMappingMode() && fmd.getKey().getValueMappedBy() != null) {
            FieldMetaData mapBy = fmd.getKey().getValueMappedByMetaData();
            if (!mapBy.isPrimaryKey() || mapBy.getDefiningMetaData().getPrimaryKeyFields().length != 1) {
               this.addAttribute("name", fmd.getKey().getValueMappedBy());
            }

            this.startElement("map-key");
            this.endElement("map-key");
         }

         if (this.isMappingMode((ValueMetaData)fmd)) {
            this.serializeFieldMappingContent(fmd, strat);
         }

         if (cascades != null && this.isMetaDataMode()) {
            this.serializeCascades((ValueMetaData)cascades);
         }

         if (this.isMappingMode() && strat == PersistenceStrategy.EMBEDDED) {
            ClassMetaData meta = fmd.getEmbeddedMetaData();
            ClassMetaData owner = this.getConfiguration().getMetaDataRepositoryInstance().getMetaData(meta.getDescribedType(), meta.getEnvClassLoader(), true);
            FieldMetaData[] arr$ = meta.getFields();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               FieldMetaData efmd = arr$[i$];
               FieldMetaData eorig = owner.getField(efmd.getName());
               if (this.serializeAttributeOverride(efmd, eorig)) {
                  this.serializeAttributeOverrideContent(efmd, eorig);
               }
            }
         }

         this.endElement(strategy);
      }
   }

   protected void addFieldMappingAttributes(FieldMetaData fmd, FieldMetaData orig) throws SAXException {
   }

   protected boolean serializeAttributeOverride(FieldMetaData fmd, FieldMetaData orig) {
      return false;
   }

   private void serializeAttributeOverrideContent(FieldMetaData fmd, FieldMetaData orig) throws SAXException {
      this.addAttribute("name", fmd.getName());
      this.startElement("attribute-override");
      this.serializeAttributeOverrideMappingContent(fmd, orig);
      this.endElement("attribute-override");
   }

   protected void serializeAttributeOverrideMappingContent(FieldMetaData fmd, FieldMetaData orig) throws SAXException {
   }

   private void serializeCascades(ValueMetaData vmd) throws SAXException {
      Collection cascades = null;
      if (vmd.getCascadePersist() == 1) {
         if (cascades == null) {
            cascades = new ArrayList();
         }

         cascades.add("cascade-persist");
      }

      if (vmd.getCascadeAttach() == 1) {
         if (cascades == null) {
            cascades = new ArrayList();
         }

         cascades.add("cascade-merge");
      }

      if (vmd.getCascadeDelete() == 1) {
         if (cascades == null) {
            cascades = new ArrayList();
         }

         cascades.add("cascade-remove");
      }

      if (vmd.getCascadeRefresh() == 1) {
         if (cascades == null) {
            cascades = new ArrayList();
         }

         cascades.add("cascade-refresh");
      }

      if (cascades != null && cascades.size() == 4) {
         cascades.clear();
         cascades.add("cascade-all");
      }

      if (cascades != null) {
         this.startElement("cascade");
         Iterator i$ = cascades.iterator();

         while(i$.hasNext()) {
            String cascade = (String)i$.next();
            this.startElement(cascade);
            this.endElement(cascade);
         }

         this.endElement("cascade");
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

   private void addBasicAttributes(FieldMetaData fmd) throws SAXException {
      if (!fmd.isInDefaultFetchGroup()) {
         this.addAttribute("fetch", "LAZY");
      }

      if (fmd.getNullValue() == 2) {
         this.addAttribute("optional", "false");
      }

   }

   private void addManyToOneAttributes(FieldMetaData fmd) throws SAXException {
      if (!fmd.isInDefaultFetchGroup()) {
         this.addAttribute("fetch", "LAZY");
      }

      if (fmd.getNullValue() == 2) {
         this.addAttribute("optional", "false");
      }

   }

   private void addOneToOneAttributes(FieldMetaData fmd) throws SAXException {
      if (!fmd.isInDefaultFetchGroup()) {
         this.addAttribute("fetch", "LAZY");
      }

      if (fmd.getNullValue() == 2) {
         this.addAttribute("optional", "false");
      }

   }

   private void addOneToManyAttributes(FieldMetaData fmd) throws SAXException {
      if (fmd.isInDefaultFetchGroup()) {
         this.addAttribute("fetch", "EAGER");
      }

      this.addTargetEntityAttribute(fmd);
   }

   private void addManyToManyAttributes(FieldMetaData fmd) throws SAXException {
      if (fmd.isInDefaultFetchGroup()) {
         this.addAttribute("fetch", "EAGER");
      }

      this.addTargetEntityAttribute(fmd);
   }

   private void addTargetEntityAttribute(FieldMetaData fmd) throws SAXException {
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
               this.addAttribute("target-entity", fmd.getElement().getDeclaredType().getName());
            }
            break;
         case 13:
            if (types.length != 2) {
               this.addAttribute("target-entity", fmd.getElement().getDeclaredType().getName());
            }
      }

   }

   protected void serializeFieldMappingContent(FieldMetaData fmd, PersistenceStrategy strategy) throws SAXException {
   }

   protected void addStrategyMappingAttributes(FieldMetaData fmd) throws SAXException {
      if (fmd.getMappedBy() != null) {
         this.addAttribute("mapped-by", fmd.getMappedBy());
      }

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
               return XMLPersistenceMetaDataSerializer.this.getStrategy(fmd2) == PersistenceStrategy.BASIC ? 1 : -1;
            }
         } else if (fmd2.isVersion()) {
            return XMLPersistenceMetaDataSerializer.this.getStrategy(fmd1) == PersistenceStrategy.BASIC ? -1 : 1;
         } else {
            int stcmp = XMLPersistenceMetaDataSerializer.this.getStrategy(fmd1).compareTo(XMLPersistenceMetaDataSerializer.this.getStrategy(fmd2));
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
            int t1 = XMLPersistenceMetaDataSerializer.this.type(o1);
            int t2 = XMLPersistenceMetaDataSerializer.this.type(o2);
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
            MetaDataTag t1 = XMLPersistenceMetaDataSerializer.getEntityTag(o1);
            MetaDataTag t2 = XMLPersistenceMetaDataSerializer.getEntityTag(o2);
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
