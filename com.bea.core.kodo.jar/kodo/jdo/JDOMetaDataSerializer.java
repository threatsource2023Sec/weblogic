package kodo.jdo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.CFMetaDataSerializer;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.AbstractCFMetaDataFactory;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.Extensions;
import org.apache.openjpa.meta.FetchGroup;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataInheritanceComparator;
import org.apache.openjpa.meta.MetaDataModes;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.NonPersistentMetaData;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.meta.UpdateStrategies;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.meta.ValueStrategies;
import org.apache.openjpa.util.InternalException;
import org.xml.sax.SAXException;
import serp.util.Strings;

public class JDOMetaDataSerializer extends CFMetaDataSerializer implements AbstractCFMetaDataFactory.Serializer, MetaDataModes {
   public static final String ROOT_JDO = "jdo";
   public static final String ROOT_QUERY = "jdoquery";
   public static final String ROOT_ORM = "orm";
   private static final Localizer _loc = Localizer.forPackage(JDOMetaDataSerializer.class);
   private static final int TYPE_SEQ = 0;
   private static final int TYPE_META = 1;
   private static final int TYPE_PERSISTENCE_AWARE = 2;
   private static final int TYPE_NON_MAPPED_INTERFACE = 3;
   private static final int TYPE_QUERY = 4;
   private static final int TYPE_CLASS_QUERIES = 5;
   private final OpenJPAConfiguration _conf;
   private Map _metas = null;
   private Map _queries = null;
   private Map _seqs = null;
   private Set _pawares = null;
   private Set _nonMapped = null;
   private String _root = null;
   private int _mode = 0;

   public JDOMetaDataSerializer(OpenJPAConfiguration conf) {
      this._conf = conf;
      this.setLog(conf.getLog("openjpa.MetaData"));
      this.setMode(7);
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public int getMode() {
      return this._mode;
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

   public void setMode(int mode) {
      this._mode = mode;
      if (this.isMetaDataMode()) {
         this.setRoot("jdo");
      } else if (this.isMappingMode()) {
         this.setRoot("orm");
      } else if (this.isQueryMode()) {
         this.setRoot("jdoquery");
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

   public String getRoot() {
      return this._root;
   }

   public void setRoot(String root) {
      this._root = root;
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
         if (this._seqs == null) {
            this._seqs = new HashMap();
         }

         this._seqs.put(meta.getName(), meta);
      }
   }

   public void addQueryMetaData(QueryMetaData meta) {
      if (meta != null) {
         Collection queries = null;
         String defName = null;
         if (meta.getSourceScope() instanceof Class) {
            defName = ((Class)meta.getSourceScope()).getName();
         }

         if (this._queries == null) {
            this._queries = new HashMap();
         } else {
            queries = (Collection)this._queries.get(defName);
         }

         if (queries == null) {
            Collection queries = new ArrayList(3);
            queries.add(meta);
            this._queries.put(defName, queries);
         } else if (!queries.contains(meta)) {
            queries.add(meta);
         }

      }
   }

   public void addPersistenceAware(NonPersistentMetaData meta) {
      if (this._pawares == null) {
         this._pawares = new HashSet();
      }

      this._pawares.add(meta);
   }

   public void addNonMappedInterface(NonPersistentMetaData meta) {
      if (this._nonMapped == null) {
         this._nonMapped = new HashSet();
      }

      this._nonMapped.add(meta);
   }

   public void addAll(MetaDataRepository repos) {
      if (repos != null) {
         ClassMetaData[] metas = repos.getMetaDatas();

         for(int i = 0; i < metas.length; ++i) {
            this.addMetaData(metas[i]);
         }

         SequenceMetaData[] seqs = repos.getSequenceMetaDatas();

         for(int i = 0; i < seqs.length; ++i) {
            this.addSequenceMetaData(seqs[i]);
         }

         QueryMetaData[] queries = repos.getQueryMetaDatas();

         for(int i = 0; i < queries.length; ++i) {
            this.addQueryMetaData(queries[i]);
         }

         NonPersistentMetaData[] paClasses = repos.getPersistenceAwares();

         for(int i = 0; i < paClasses.length; ++i) {
            this.addPersistenceAware(paClasses[i]);
         }

         NonPersistentMetaData[] nonMapped = repos.getNonMappedInterfaces();

         for(int i = 0; i < nonMapped.length; ++i) {
            this.addNonMappedInterface(nonMapped[i]);
         }

      }
   }

   public boolean removeMetaData(ClassMetaData meta) {
      return this._metas != null && meta != null && this._metas.remove(meta.getDescribedType().getName()) != null;
   }

   public boolean removeSequenceMetaData(SequenceMetaData meta) {
      return this._seqs != null && meta != null && this._seqs.remove(meta.getName()) != null;
   }

   public boolean removeQueryMetaData(QueryMetaData meta) {
      if (this._queries != null && meta != null) {
         String defName = null;
         if (meta.getSourceScope() instanceof Class) {
            defName = ((Class)meta.getSourceScope()).getName();
         }

         Collection queries = (Collection)this._queries.get(defName);
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

   public boolean removePersistenceAware(NonPersistentMetaData meta) {
      return this._pawares != null && this._pawares.remove(meta);
   }

   public boolean removeNonMappedInterface(NonPersistentMetaData meta) {
      return this._nonMapped != null && this._nonMapped.remove(meta);
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

         NonPersistentMetaData[] paClasses = repos.getPersistenceAwares();

         for(int i = 0; i < paClasses.length; ++i) {
            removed |= this.removePersistenceAware(paClasses[i]);
         }

         NonPersistentMetaData[] nonMapped = repos.getNonMappedInterfaces();

         for(int i = 0; i < nonMapped.length; ++i) {
            removed |= this.removeNonMappedInterface(nonMapped[i]);
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

      if (this._pawares != null) {
         this._pawares.clear();
      }

      if (this._nonMapped != null) {
         this._nonMapped.clear();
      }

   }

   protected Collection getObjects() {
      List all = new ArrayList();
      if (this.isQueryMode()) {
         this.addQueryMetaDatas(all);
      }

      if (this.isMappingMode() && this._seqs != null) {
         all.addAll(this._seqs.values());
      }

      if ((this.isMetaDataMode() || this.isMappingMode()) && this._metas != null) {
         all.addAll(this._metas.values());
      }

      if (this.isMetaDataMode() && this._pawares != null) {
         all.addAll(this._pawares);
      }

      if (this.isMetaDataMode() && this._nonMapped != null) {
         all.addAll(this._nonMapped);
      }

      Collections.sort(all, JDOMetaDataSerializer.SerializationComparator.getInstance());
      return all;
   }

   private void addQueryMetaDatas(Collection all) {
      if (this._queries != null) {
         Iterator itr = this._queries.entrySet().iterator();

         while(true) {
            while(itr.hasNext()) {
               Map.Entry entry = (Map.Entry)itr.next();
               if (entry.getKey() == null) {
                  all.addAll((List)entry.getValue());
               } else if (this._mode == 4 || this._metas == null || !this._metas.containsKey(entry.getKey())) {
                  all.add(new ClassQueries((String)entry.getKey(), (List)entry.getValue()));
               }
            }

            return;
         }
      }
   }

   protected void serialize(Collection objs) throws SAXException {
      Map byPackage = this.groupByPackage(objs);
      this.startElement(this._root);
      Collection system = (Collection)byPackage.remove((Object)null);
      if (system != null) {
         this.serializePackage((String)null, system);
      }

      Iterator itr = byPackage.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         this.serializePackage((String)entry.getKey(), (Collection)entry.getValue());
      }

      this.endElement(this._root);
   }

   protected String getPackage(Object var1) {
      // $FF: Couldn't be decompiled
   }

   protected String getClassName(String name) {
      return PersistenceCapable.class.getName().equals(name) ? "PersistenceCapable" : super.getClassName(name);
   }

   private void serializePackage(String var1, Collection var2) throws SAXException {
      // $FF: Couldn't be decompiled
   }

   private void serializeQuery(QueryMetaData meta) throws SAXException {
      Log log = this.getLog();
      if (log.isInfoEnabled()) {
         if (meta.getSourceScope() instanceof Class) {
            log.info(_loc.get("ser-cls-query", meta.getSourceScope(), meta.getName()));
         } else {
            log.info(_loc.get("ser-query", meta.getName()));
         }
      }

      this.addComments(meta);
      this.addAttribute("name", meta.getName());
      if (meta.getLanguage() != null) {
         if ("openjpa.SQL".equals(meta.getLanguage())) {
            this.addAttribute("language", "javax.jdo.query.SQL");
         } else if (!"javax.jdo.query.JDOQL".equals(meta.getLanguage())) {
            this.addAttribute("language", meta.getLanguage());
         }
      }

      this.startElement("query");
      String[] hints = meta.getHintKeys();
      Object[] vals = meta.getHintValues();

      for(int i = 0; i < hints.length; ++i) {
         this.serializeExtension("openjpa", hints[i], String.valueOf(vals[i]), (Extensions)null);
      }

      this.addText(meta.getQueryString());
      this.endElement("query");
   }

   private void serializeQueries(ClassQueries meta) throws SAXException {
      this.addAttribute("name", this.getClassName(meta.definingTypeName));
      this.startElement("class");

      for(int i = 0; i < meta.queries.length; ++i) {
         this.serializeQuery(meta.queries[i]);
      }

      this.endElement("class");
   }

   private void serializeSequence(SequenceMetaData meta) throws SAXException {
      Log log = this.getLog();
      if (log.isInfoEnabled()) {
         log.info(_loc.get("ser-sequence", meta.getName()));
      }

      this.addComments(meta);
      this.addAttribute("name", Strings.getClassName(meta.getName()));
      switch (meta.getType()) {
         case 0:
         case 1:
            this.addAttribute("strategy", "nontransactional");
            break;
         case 2:
            this.addAttribute("strategy", "transactional");
            break;
         case 3:
            this.addAttribute("strategy", "contiguous");
      }

      String ds = meta.getSequence();
      if (ds != null) {
         this.addAttribute("datastore-sequence", ds);
      }

      String plugin = meta.getSequencePlugin();
      if (plugin != null && !"native".equals(plugin)) {
         this.addAttribute("factory-class", plugin);
      }

      this.startElement("sequence");
      this.endElement("sequence");
   }

   protected void serializeClass(ClassMetaData meta) throws SAXException {
      Log log = this.getLog();
      if (log.isInfoEnabled()) {
         log.info(_loc.get("ser-class", meta));
      }

      this.addComments(meta);
      this.addAttribute("name", this.getClassName(meta.getDescribedType().getName()));
      if (this.isMetaDataMode()) {
         this.addClassAttributes(meta);
      }

      if (this.isMappingMode(meta)) {
         this.addClassMappingAttributes(meta);
      }

      if (meta.isManagedInterface()) {
         if (this.isMetaDataMode()) {
            this.addAttribute("requires-extent", "true");
         }

         this.startElement("interface");
      } else {
         this.startElement("class");
      }

      if (this.isMetaDataMode()) {
         this.serializeKnownClassExtensions(meta);
      }

      if (this.isMappingMode(meta)) {
         this.serializeKnownClassMappingExtensions(meta);
      }

      if (this.isMetaDataMode()) {
         this.serializeExtensions(meta);
      }

      if (this.isMetaDataMode()) {
         this.serializeImplements(meta);
      }

      if (this.isMappingMode(meta)) {
         if (meta.getIdentityType() == 1 && this.needsDataStoreIdentitySerialization(meta)) {
            this.serializeDataStoreIdentity(meta);
         }

         this.serializeClassMappingContent(meta);
      }

      FieldMetaData[] fmds = meta.getDefinedFieldsInListingOrder();

      for(int i = 0; i < fmds.length; ++i) {
         FieldMetaData orig;
         if (fmds[i].getDeclaringType() != fmds[i].getDefiningMetaData().getDescribedType()) {
            orig = fmds[i].getDeclaringMetaData().getDeclaredField(fmds[i].getName());
         } else {
            orig = null;
         }

         Boolean ser = this.needsSerialization(fmds[i], orig);
         if (!Boolean.FALSE.equals(ser)) {
            this.serializeField(fmds[i], orig, ser == null);
         }
      }

      List queries = this._queries == null ? null : (List)this._queries.get(meta.getDescribedType().getName());
      if (queries != null) {
         for(int i = 0; i < queries.size(); ++i) {
            this.serializeQuery((QueryMetaData)queries.get(i));
         }
      }

      if (this.isMetaDataMode()) {
         FetchGroup[] fgs = meta.getDeclaredFetchGroups();

         for(int i = 0; i < fgs.length; ++i) {
            this.serializeFetchGroup(meta, fgs[i]);
         }
      }

      if (meta.isManagedInterface()) {
         this.endElement("interface");
      } else {
         this.endElement("class");
      }

   }

   private void addClassAttributes(ClassMetaData meta) {
      ClassMetaData sup = meta.getPCSuperclassMetaData();
      if (meta.getIdentityType() == 2) {
         Class oid = meta.getObjectIdType();
         if (meta.isOpenJPAIdentity() || oid == null || sup != null && oid == sup.getObjectIdType()) {
            this.addAttribute("identity-type", "application");
         } else {
            this.addAttribute("objectid-class", this.getClassName(oid.getName()));
         }
      }

      if (!meta.getDescribedType().isInterface() && (sup == null && !meta.getRequiresExtent() || sup != null && meta.getRequiresExtent() != sup.getRequiresExtent())) {
         this.addAttribute("requires-extent", String.valueOf(meta.getRequiresExtent()));
      }

      if (sup == null && meta.isEmbeddedOnly() || sup != null && meta.isEmbeddedOnly() != sup.isEmbeddedOnly()) {
         this.addAttribute("embedded-only", String.valueOf(meta.isEmbeddedOnly()));
      }

      boolean detach = meta.isDetachable();
      if (sup == null && detach || sup != null && detach != sup.isDetachable()) {
         this.addAttribute("detachable", detach ? "true" : "false");
      }

   }

   protected void addClassMappingAttributes(ClassMetaData mapping) throws SAXException {
   }

   protected void serializeKnownClassExtensions(ClassMetaData meta) throws SAXException {
      ClassMetaData sup = meta.getPCSuperclassMetaData();
      String val = meta.getDataCacheName();
      boolean neq = sup != null && !StringUtils.equals(val, sup.getDataCacheName());
      if (neq || sup == null && !"default".equals(val)) {
         if (val == null) {
            val = "false";
         } else if ("default".equals(val)) {
            val = "true";
         }

         this.serializeExtension("openjpa", "data-cache", val, (Extensions)null);
      }

      int ival = meta.getDataCacheTimeout();
      neq = sup != null && ival != sup.getDataCacheTimeout();
      if (neq || sup == null && ival != this._conf.getDataCacheTimeout()) {
         this.serializeExtension("openjpa", "data-cache-timeout", String.valueOf(ival), (Extensions)null);
      }

      val = meta.getDetachedState();
      neq = sup != null && !StringUtils.equals(val, sup.getDetachedState());
      if (neq || sup == null && meta.isDetachable() && !"`syn".equals(val)) {
         if (val == null) {
            val = "false";
         }

         this.serializeExtension("openjpa", "detached-state-field", val, (Extensions)null);
      }

   }

   protected void serializeKnownClassMappingExtensions(ClassMetaData mapping) throws SAXException {
   }

   protected void serializeClassMappingContent(ClassMetaData mapping) throws SAXException {
   }

   protected void serializeImplements(ClassMetaData meta) throws SAXException {
      Class[] ifaces = meta.getDeclaredInterfaces();

      for(int i = 0; i < ifaces.length; ++i) {
         this.addAttribute("name", ifaces[i].getName());
         this.startElement("implements");
         String[] props = meta.getInterfaceAliasedProperties(ifaces[i]);

         for(int j = 0; j < props.length; ++j) {
            this.addAttribute("name", props[j]);
            this.addAttribute("field-name", meta.getInterfacePropertyAlias(ifaces[i], props[j]));
            this.startElement("property");
            this.endElement("property");
         }

         this.endElement("implements");
      }

   }

   private boolean needsDataStoreIdentitySerialization(ClassMetaData meta) {
      if (this.hasMappedPCSuperclass(meta)) {
         return false;
      } else {
         return meta.getIdentityStrategy() != 1 && meta.getIdentityStrategy() != 0 || meta.getIdentitySequenceName() != null || this.hasDataStoreIdentityMappingInformation(meta);
      }
   }

   protected boolean hasMappedPCSuperclass(ClassMetaData meta) {
      return meta.getMappedPCSuperclassMetaData() != null;
   }

   protected boolean hasDataStoreIdentityMappingInformation(ClassMetaData meta) {
      return false;
   }

   private void serializeDataStoreIdentity(ClassMetaData meta) throws SAXException {
      this.addDataStoreIdentityAttributes(meta);
      this.startElement("datastore-identity");
      this.serializeDataStoreIdentityContent(meta);
      this.endElement("datastore-identity");
   }

   protected void addDataStoreIdentityAttributes(ClassMetaData meta) throws SAXException {
      if (meta.getIdentitySequenceName() != null) {
         this.addAttribute("sequence", this.getClassName(meta.getIdentitySequenceName()));
      }

      if (meta.getIdentityStrategy() != 0 && meta.getIdentityStrategy() != 1 && (meta.getIdentitySequenceName() == null || meta.getIdentityStrategy() != 2)) {
         this.addAttribute("strategy", ValueStrategies.getName(meta.getIdentityStrategy()));
      }

   }

   protected void serializeDataStoreIdentityContent(ClassMetaData meta) throws SAXException {
   }

   protected Boolean needsSerialization(FieldMetaData fmd, FieldMetaData orig) {
      if (!this.isMetaDataMode()) {
         return fmd.getManagement() != 3 || !fmd.isSerialized() && fmd.getMappedBy() == null && !fmd.isVersion() && !this.hasMappingInformation(fmd) ? Boolean.FALSE : Boolean.TRUE;
      } else {
         if (orig == null) {
            label187: {
               if (!fmd.isPrimaryKey() && fmd.getManagement() == 3) {
                  boolean persType = true;
                  switch (fmd.getDeclaredTypeCode()) {
                     case 8:
                     case 27:
                     case 29:
                        persType = false;
                        break;
                     case 11:
                        persType = fmd.getElement().getDeclaredTypeCode() != 8;
                  }

                  if (persType && !fmd.isTransient()) {
                     break label187;
                  }

                  return null;
               }

               return Boolean.TRUE;
            }
         }

         if (this.isVerbose()) {
            return Boolean.TRUE;
         } else if (fmd.getComments().length > 0) {
            return Boolean.TRUE;
         } else if (!fmd.isEmpty()) {
            return Boolean.TRUE;
         } else if (this.isMappingMode() && fmd.getValueStrategy() != 0) {
            return Boolean.TRUE;
         } else {
            if (orig == null) {
               if (fmd.getTypeOverride() != null) {
                  return null;
               }

               if (fmd.getCascadeDelete() == 2) {
                  return Boolean.TRUE;
               }

               if (fmd.getNullValue() != -1) {
                  return Boolean.TRUE;
               }

               if (fmd.getExternalValues() != null) {
                  return Boolean.TRUE;
               }

               if (fmd.getExternalizer() != null) {
                  return Boolean.TRUE;
               }

               if (fmd.getFactory() != null) {
                  return Boolean.TRUE;
               }

               if (fmd.isLRS()) {
                  return Boolean.TRUE;
               }

               if (this.isMappingMode() && fmd.getMappedBy() != null) {
                  return Boolean.TRUE;
               }

               if (fmd.getInverse() != null) {
                  return Boolean.TRUE;
               }

               if (!fmd.isVersion() && fmd.getUpdateStrategy() != 0) {
                  return Boolean.TRUE;
               }

               switch (fmd.getDeclaredTypeCode()) {
                  case 11:
                     if (this.needsSerialization(fmd.getElement())) {
                        return Boolean.TRUE;
                     }
                  default:
                     if (this.needsSerialization(fmd)) {
                        return Boolean.TRUE;
                     }

                     if (!this.isEmbeddedNonDefault(fmd) && !this.isDFGNonDefault(fmd)) {
                        if (fmd.getLoadFetchGroup() != null) {
                           return Boolean.TRUE;
                        }

                        if (fmd.getExtensionKeys().length > 0) {
                           return Boolean.TRUE;
                        }
                        break;
                     }

                     return Boolean.TRUE;
                  case 12:
                  case 13:
                     return Boolean.TRUE;
               }
            } else {
               if (this.isMappingMode() && fmd.isSerialized()) {
                  return Boolean.TRUE;
               }

               if (orig.isInDefaultFetchGroup() != fmd.isInDefaultFetchGroup()) {
                  return Boolean.TRUE;
               }

               if (!StringUtils.equals(orig.getLoadFetchGroup(), fmd.getLoadFetchGroup())) {
                  return Boolean.TRUE;
               }

               if (orig.isLRS() != fmd.isLRS()) {
                  return Boolean.TRUE;
               }
            }

            return this.isMappingMode((ValueMetaData)fmd) && this.hasMappingInformation(fmd) ? Boolean.TRUE : Boolean.FALSE;
         }
      }
   }

   protected boolean hasMappingInformation(FieldMetaData fmd) {
      return this.hasEmbeddedMappingInformation(fmd) || this.hasEmbeddedMappingInformation(fmd.getElement()) || this.hasEmbeddedMappingInformation(fmd.getKey());
   }

   private boolean hasEmbeddedMappingInformation(ValueMetaData vmd) {
      if (!vmd.isEmbeddedPC()) {
         return false;
      } else {
         FieldMetaData[] fmds = vmd.getEmbeddedMetaData().getDefinedFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (this.hasMappingInformation(fmds[i])) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isEmbeddedNonDefault(ValueMetaData vmd) {
      if (vmd.getFieldMetaData().getManagement() != 3) {
         return false;
      } else {
         switch (vmd.getDeclaredTypeCode()) {
            case 12:
            case 13:
            case 15:
            case 27:
               return vmd.isEmbedded();
            default:
               return !vmd.isEmbedded();
         }
      }
   }

   private boolean isDFGNonDefault(FieldMetaData fmd) {
      if (fmd.getManagement() == 3 && !fmd.isPrimaryKey()) {
         switch (fmd.getTypeCode()) {
            case 8:
            case 11:
            case 12:
            case 13:
            case 15:
            case 27:
               return fmd.isInDefaultFetchGroup();
            case 9:
            case 10:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            default:
               return !fmd.isInDefaultFetchGroup();
         }
      } else {
         return false;
      }
   }

   private void serializeField(FieldMetaData fmd, FieldMetaData orig, boolean markPersistent) throws SAXException {
      String element = fmd.getDefiningMetaData().isManagedInterface() ? "property" : "field";
      this.addComments(fmd);
      String name = fmd.getName();
      if (fmd.getDeclaringType() != fmd.getDefiningMetaData().getDescribedType()) {
         name = this.getClassName(fmd.getDeclaringType().getName()) + "." + name;
      }

      this.addAttribute("name", name);
      if (this.isMetaDataMode() && fmd.getManagement() == 0) {
         this.addAttribute("persistence-modifier", "none");
         this.startElement(element);
         this.endElement(element);
      } else {
         if (this.isMetaDataMode()) {
            this.addFieldAttributes(fmd, orig, markPersistent);
         }

         if (this.isMappingMode((ValueMetaData)fmd)) {
            this.addFieldMappingAttributes(fmd, orig);
            this.addValueMappingAttributes(fmd);
         }

         this.startElement(element);
         if (this.isMetaDataMode()) {
            switch (fmd.getDeclaredTypeCode()) {
               case 11:
                  ValueMetaData elem = fmd.getElement();
                  if (this.isEmbeddedNonDefault(elem) || elem.getCascadeDelete() == 2 && !elem.isEmbedded()) {
                     this.serializeArray(fmd);
                  }
                  break;
               case 12:
                  this.serializeCollection(fmd);
                  break;
               case 13:
                  this.serializeMap(fmd);
            }
         }

         if (this.isMappingMode((ValueMetaData)fmd)) {
            this.serializeFieldMappingContent(fmd);
         }

         if (fmd.getManagement() == 3) {
            switch (fmd.getTypeCode()) {
               case 11:
               case 12:
                  if (this.needsSerialization(fmd.getElement())) {
                     if (this.isMappingMode((ValueMetaData)fmd)) {
                        this.addValueMappingAttributes(fmd.getElement());
                     }

                     this.startElement("element");
                     this.serializeAllValueContent(fmd.getElement());
                     this.endElement("element");
                  }
                  break;
               case 13:
                  if (this.needsSerialization(fmd.getKey())) {
                     if (this.isMappingMode((ValueMetaData)fmd)) {
                        this.addValueMappingAttributes(fmd.getKey());
                     }

                     this.startElement("key");
                     this.serializeAllValueContent(fmd.getKey());
                     this.endElement("key");
                  }

                  if (this.needsSerialization(fmd.getElement())) {
                     if (this.isMappingMode((ValueMetaData)fmd)) {
                        this.addValueMappingAttributes(fmd.getElement());
                     }

                     this.startElement("value");
                     this.serializeAllValueContent(fmd.getElement());
                     this.endElement("value");
                  }
            }
         }

         this.serializeAllValueContent(fmd);
         if (this.isMetaDataMode()) {
            this.serializeKnownFieldExtensions(fmd);
         }

         if (this.isMappingMode((ValueMetaData)fmd)) {
            this.serializeKnownFieldMappingExtensions(fmd);
         }

         if (this.isMetaDataMode()) {
            this.serializeExtensions(fmd);
         }

         this.endElement(element);
      }
   }

   private void addFieldAttributes(FieldMetaData fmd, FieldMetaData orig, boolean markPersistent) {
      if (fmd.getManagement() == 1) {
         this.addAttribute("persistence-modifier", "transactional");
      } else if (orig == null) {
         if (markPersistent) {
            this.addAttribute("persistence-modifier", "persistent");
         }

         if (fmd.isPrimaryKey()) {
            this.addAttribute("primary-key", "true");
         }

         if (this.isDFGNonDefault(fmd)) {
            this.addAttribute("default-fetch-group", String.valueOf(fmd.isInDefaultFetchGroup()));
         }

         if (fmd.getLoadFetchGroup() != null) {
            this.addAttribute("load-fetch-group", fmd.getLoadFetchGroup());
         }

         if (this.isEmbeddedNonDefault(fmd)) {
            this.addAttribute("embedded", String.valueOf(fmd.isEmbedded()));
         }

         if (fmd.getCascadeDelete() == 2 && !fmd.isEmbedded()) {
            this.addAttribute("dependent", "true");
         }

         if (fmd.isSerialized()) {
            this.addAttribute("serialized", "true");
         }
      } else {
         if (fmd.isInDefaultFetchGroup() != orig.isInDefaultFetchGroup()) {
            this.addAttribute("default-fetch-group", String.valueOf(fmd.isInDefaultFetchGroup()));
         }

         if (!ObjectUtils.equals(fmd.getLoadFetchGroup(), orig.getLoadFetchGroup())) {
            this.addAttribute("load-fetch-group", fmd.getLoadFetchGroup());
         }

         if (fmd.isSerialized() != orig.isSerialized()) {
            this.addAttribute("serialized", String.valueOf(fmd.isSerialized()));
         }
      }

      if (orig == null) {
         if (fmd.getNullValue() == 0) {
            this.addAttribute("null-value", "none");
         } else if (fmd.getNullValue() == 1) {
            this.addAttribute("null-value", "default");
         } else if (fmd.getNullValue() == 2) {
            this.addAttribute("null-value", "exception");
         }
      }

   }

   protected void addFieldMappingAttributes(FieldMetaData fmd, FieldMetaData orig) throws SAXException {
      if (fmd.getMappedBy() != null) {
         this.addAttribute("mapped-by", fmd.getMappedBy());
      }

      if (fmd.isVersion()) {
         this.addAttribute("value-strategy", "version");
      } else {
         if (fmd.getValueSequenceName() != null) {
            this.addAttribute("sequence", fmd.getValueSequenceName());
         }

         if (fmd.getValueStrategy() != 0 && (fmd.getValueSequenceName() == null || fmd.getValueStrategy() != 2)) {
            this.addAttribute("value-strategy", ValueStrategies.getName(fmd.getValueStrategy()));
         }
      }

   }

   protected void serializeKnownFieldExtensions(FieldMetaData fmd) throws SAXException {
      if (fmd.getInverse() != null) {
         this.serializeExtension("openjpa", "inverse-logical", fmd.getInverse(), (Extensions)null);
      }

      if (fmd.isLRS()) {
         this.serializeExtension("openjpa", "lrs", (String)null, (Extensions)null);
      }

      if (fmd.getExternalValues() != null) {
         this.serializeExtension("openjpa", "external-values", fmd.getExternalValues(), (Extensions)null);
      }

      if (fmd.getExternalizer() != null) {
         this.serializeExtension("openjpa", "externalizer", fmd.getExternalizer(), (Extensions)null);
      }

      if (fmd.getFactory() != null) {
         this.serializeExtension("openjpa", "factory", fmd.getFactory(), (Extensions)null);
      }

      String prefix;
      if (!fmd.isVersion() && fmd.getUpdateStrategy() != 0) {
         prefix = fmd.getUpdateStrategy() == 2 ? null : UpdateStrategies.getName(fmd.getUpdateStrategy());
         this.serializeExtension("openjpa", "read-only", prefix, (Extensions)null);
      }

      if (fmd.getOrderDeclaration() != null) {
         this.serializeExtension("openjpa", "order-by", fmd.getOrderDeclaration(), (Extensions)null);
      }

      if (fmd.getTypeOverride() != null) {
         this.serializeExtension("openjpa", "type", this.getClassName(fmd.getTypeOverride().getName()), (Extensions)null);
      }

      if (fmd.getKey().getTypeOverride() != null) {
         this.serializeExtension("openjpa", "key-type", this.getClassName(fmd.getKey().getTypeOverride().getName()), (Extensions)null);
      }

      if (fmd.getElement().getTypeOverride() != null) {
         prefix = fmd.getTypeCode() == 13 ? "value-" : "element-";
         this.serializeExtension("openjpa", prefix + "type", this.getClassName(fmd.getElement().getTypeOverride().getName()), (Extensions)null);
      }

   }

   protected void serializeKnownFieldMappingExtensions(FieldMetaData fmd) throws SAXException {
   }

   protected void serializeFieldMappingContent(FieldMetaData fmd) throws SAXException {
   }

   private boolean needsSerialization(ValueMetaData vmd) {
      if (this.isEmbeddedNonDefault(vmd)) {
         return true;
      } else if (!this.isMappingMode(vmd)) {
         return false;
      } else {
         return vmd.isSerialized() || vmd != vmd.getFieldMetaData().getValue() && vmd.getValueMappedBy() != null || this.hasMappingInformation(vmd);
      }
   }

   protected boolean hasMappingInformation(ValueMetaData vmd) {
      return false;
   }

   protected void addValueMappingAttributes(ValueMetaData vmd) throws SAXException {
      if (vmd == vmd.getFieldMetaData().getKey() && vmd.getValueMappedBy() != null) {
         this.addAttribute("mapped-by", vmd.getValueMappedBy());
      }

   }

   private void serializeAllValueContent(ValueMetaData vmd) throws SAXException {
      ClassMetaData embed = vmd.getEmbeddedMetaData();
      if (embed != null) {
         if (this.isMappingMode(vmd)) {
            this.addEmbedMappingAttributes(embed);
         }

         this.startElement("embedded");
         FieldMetaData[] fmds = embed.getFieldsInListingOrder();

         for(int i = 0; i < fmds.length; ++i) {
            FieldMetaData orig = vmd.getRepository().getMetaData(embed.getDescribedType(), embed.getEnvClassLoader(), true).getField(fmds[i].getName());
            Boolean ser = this.needsSerialization(fmds[i], orig);
            if (!Boolean.FALSE.equals(ser)) {
               this.serializeField(fmds[i], orig, ser == null);
            }
         }

         this.endElement("embedded");
      }

      if (this.isMappingMode(vmd)) {
         this.serializeValueMappingContent(vmd);
      }

   }

   protected void addEmbedMappingAttributes(ClassMetaData embed) throws SAXException {
   }

   protected void serializeValueMappingContent(ValueMetaData vmd) throws SAXException {
   }

   private void serializeArray(FieldMetaData fmd) throws SAXException {
      ValueMetaData elem = fmd.getElement();
      if (this.isEmbeddedNonDefault(elem)) {
         this.addAttribute("embedded-element", String.valueOf(elem.isEmbedded()));
      }

      if (elem.getCascadeDelete() == 2 && !elem.isEmbedded()) {
         this.addAttribute("dependent-element", "true");
      }

      if (elem.isSerialized()) {
         this.addAttribute("serialized-element", "true");
      }

      this.startElement("array");
      this.endElement("array");
   }

   private void serializeCollection(FieldMetaData fmd) throws SAXException {
      ValueMetaData elem = fmd.getElement();
      this.addAttribute("element-type", this.getClassName(elem.getDeclaredType().getName()));
      if (this.isEmbeddedNonDefault(elem)) {
         this.addAttribute("embedded-element", String.valueOf(elem.isEmbedded()));
      }

      if (elem.getCascadeDelete() == 2 && !elem.isEmbedded()) {
         this.addAttribute("dependent-element", "true");
      }

      if (elem.isSerialized()) {
         this.addAttribute("serialized-element", "true");
      }

      this.startElement("collection");
      this.endElement("collection");
   }

   private void serializeMap(FieldMetaData fmd) throws SAXException {
      ValueMetaData key = fmd.getKey();
      if (!Properties.class.isAssignableFrom(fmd.getDeclaredType())) {
         this.addAttribute("key-type", this.getClassName(key.getDeclaredType().getName()));
      }

      if (this.isEmbeddedNonDefault(key)) {
         this.addAttribute("embedded-key", String.valueOf(key.isEmbedded()));
      }

      if (key.getCascadeDelete() == 2 && !key.isEmbedded()) {
         this.addAttribute("dependent-key", "true");
      }

      if (key.isSerialized()) {
         this.addAttribute("serialized-key", "true");
      }

      ValueMetaData val = fmd.getElement();
      if (!Properties.class.isAssignableFrom(fmd.getDeclaredType())) {
         this.addAttribute("value-type", this.getClassName(val.getDeclaredType().getName()));
      }

      if (this.isEmbeddedNonDefault(val)) {
         this.addAttribute("embedded-value", String.valueOf(val.isEmbedded()));
      }

      if (val.getCascadeDelete() == 2 && !val.isEmbedded()) {
         this.addAttribute("dependent-value", "true");
      }

      if (val.isSerialized()) {
         this.addAttribute("serialized-value", "true");
      }

      this.startElement("map");
      this.endElement("map");
   }

   private void serializeFetchGroup(ClassMetaData meta, FetchGroup fg) throws SAXException {
      this.addAttribute("name", fg.getName());
      if (fg.isPostLoadExplicit()) {
         this.addAttribute("post-load", fg.isPostLoad() ? "true" : "false");
      }

      this.startElement("fetch-group");
      FieldMetaData[] fmds = meta.getDeclaredFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].isInFetchGroup(fg.getName())) {
            String element = fmds[i].getDefiningMetaData().isManagedInterface() ? "property" : "field";
            this.addAttribute("name", fmds[i].getName());
            int depth = fg.getDeclaredRecursionDepth(fmds[i]);
            if (depth != 0) {
               this.addAttribute("recursion-depth", String.valueOf(depth));
            }

            this.startElement(element);
            this.endElement(element);
         }
      }

      String[] includes = fg.getDeclaredIncludes();

      for(int i = 0; i < includes.length; ++i) {
         this.addAttribute("name", includes[i]);
         this.startElement("fetch-group");
         this.endElement("fetch-group");
      }

      this.endElement("fetch-group");
   }

   private void serializePersistenceAware(NonPersistentMetaData paClass) throws SAXException {
      this.addComments(paClass);
      this.addAttribute("name", this.getClassName(paClass.getDescribedType().getName()));
      this.addAttribute("persistence-modifier", "persistence-aware");
      this.startElement("class");
      this.endElement("class");
   }

   private void serializeNonMappedInterface(NonPersistentMetaData paClass) throws SAXException {
      this.addComments(paClass);
      this.addAttribute("name", this.getClassName(paClass.getDescribedType().getName()));
      this.startElement("interface");
      this.endElement("interface");
   }

   private void serializeExtensions(Extensions exts) throws SAXException {
      if (exts != null) {
         String[] vendors = exts.getExtensionVendors();

         for(int i = 0; i < vendors.length; ++i) {
            String[] keys = exts.getExtensionKeys(vendors[i]);

            for(int j = 0; j < keys.length; ++j) {
               this.serializeExtension(vendors[i], keys[j], exts.getStringExtension(vendors[i], keys[j]), exts);
            }
         }

      }
   }

   protected void serializeExtension(String vendor, String key, String value, Extensions exts) throws SAXException {
      this.addAttribute("vendor-name", vendor);
      this.addAttribute("key", key);
      if (value != null && value.length() > 0) {
         this.addAttribute("value", value);
      }

      this.startElement("extension");
      if (exts != null) {
         this.serializeExtensions(exts.getEmbeddedExtensions(vendor, key, false));
      }

      this.endElement("extension");
   }

   private static int type(Object o) {
      if (o instanceof ClassMetaData) {
         return 1;
      } else if (o instanceof QueryMetaData) {
         return 4;
      } else if (o instanceof SequenceMetaData) {
         return 0;
      } else if (o instanceof ClassQueries) {
         return 5;
      } else if (o instanceof NonPersistentMetaData) {
         return ((NonPersistentMetaData)o).getType() == 1 ? 2 : 3;
      } else {
         return -1;
      }
   }

   private static class SerializationComparator extends MetaDataInheritanceComparator {
      private static SerializationComparator _instance = null;

      public static SerializationComparator getInstance() {
         if (_instance == null) {
            _instance = new SerializationComparator();
         }

         return _instance;
      }

      public int compare(Object o1, Object o2) {
         if (o1 == o2) {
            return 0;
         } else if (o1 == null) {
            return 1;
         } else if (o2 == null) {
            return -1;
         } else {
            int t1 = JDOMetaDataSerializer.type(o1);
            int t2 = JDOMetaDataSerializer.type(o2);
            if (t1 != t2) {
               return t1 - t2;
            } else {
               switch (t1) {
                  case 0:
                     return this.compare((SequenceMetaData)o1, (SequenceMetaData)o2);
                  case 1:
                     return this.compare((ClassMetaData)o1, (ClassMetaData)o2);
                  case 2:
                  case 3:
                     return this.compare((NonPersistentMetaData)o1, (NonPersistentMetaData)o2);
                  case 4:
                     return this.compare((QueryMetaData)o1, (QueryMetaData)o2);
                  case 5:
                     return ((Comparable)o1).compareTo(o2);
                  default:
                     return 0;
               }
            }
         }
      }

      private int compare(ClassMetaData o1, ClassMetaData o2) {
         int li1 = o1.getListingIndex();
         int li2 = o2.getListingIndex();
         if (li1 == -1 && li2 == -1) {
            int inher = super.compare(o1, o2);
            return inher != 0 ? inher : o1.getDescribedType().getName().compareTo(o2.getDescribedType().getName());
         } else if (li1 == -1) {
            return 1;
         } else {
            return li2 == -1 ? -1 : li1 - li2;
         }
      }

      private int compare(QueryMetaData o1, QueryMetaData o2) {
         return o1.getName().compareTo(o2.getName());
      }

      private int compare(SequenceMetaData o1, SequenceMetaData o2) {
         return o1.getName().compareTo(o2.getName());
      }

      private int compare(NonPersistentMetaData o1, NonPersistentMetaData o2) {
         int li1 = o1.getListingIndex();
         int li2 = o2.getListingIndex();
         if (li1 == -1 && li2 == -1) {
            return o1.compareTo(o2);
         } else if (li1 == -1) {
            return 1;
         } else {
            return li2 == -1 ? -1 : li1 - li2;
         }
      }
   }

   private static class ClassQueries implements SourceTracker, Comparable {
      public final String definingTypeName;
      public final QueryMetaData[] queries;

      public ClassQueries(String definingTypeName, List queries) {
         if (queries != null && !queries.isEmpty()) {
            this.definingTypeName = definingTypeName;
            this.queries = (QueryMetaData[])((QueryMetaData[])queries.toArray(new QueryMetaData[queries.size()]));
            Arrays.sort(this.queries, JDOMetaDataSerializer.SerializationComparator.getInstance());
         } else {
            throw new InternalException();
         }
      }

      public File getSourceFile() {
         return this.queries[0].getSourceFile();
      }

      public Object getSourceScope() {
         return this.queries[0].getSourceScope();
      }

      public int getSourceType() {
         return this.queries[0].getSourceType();
      }

      public String getResourceName() {
         return this.queries[0].getResourceName();
      }

      public int compareTo(Object o) {
         if (o == this) {
            return 0;
         } else {
            return o == null ? -1 : this.definingTypeName.compareTo(((ClassQueries)o).definingTypeName);
         }
      }
   }
}
