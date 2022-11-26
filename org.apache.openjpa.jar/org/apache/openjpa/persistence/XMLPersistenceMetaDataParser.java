package org.apache.openjpa.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.event.BeanLifecycleCallbacks;
import org.apache.openjpa.event.LifecycleCallbacks;
import org.apache.openjpa.event.LifecycleEvent;
import org.apache.openjpa.event.MethodLifecycleCallbacks;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.CFMetaDataParser;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.AbstractCFMetaDataFactory;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.DelegatingMetaDataFactory;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.meta.LifecycleMetaData;
import org.apache.openjpa.meta.MetaDataDefaults;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ImplHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLPersistenceMetaDataParser extends CFMetaDataParser implements AbstractCFMetaDataFactory.Parser {
   protected static final String ELEM_PKG = "package";
   protected static final String ELEM_ACCESS = "access";
   protected static final String ELEM_ATTRS = "attributes";
   protected static final String ELEM_LISTENER = "entity-listener";
   protected static final String ELEM_CASCADE = "cascade";
   protected static final String ELEM_CASCADE_ALL = "cascade-all";
   protected static final String ELEM_CASCADE_PER = "cascade-persist";
   protected static final String ELEM_CASCADE_MER = "cascade-merge";
   protected static final String ELEM_CASCADE_REM = "cascade-remove";
   protected static final String ELEM_CASCADE_REF = "cascade-refresh";
   protected static final String ELEM_PU_META = "persistence-unit-metadata";
   protected static final String ELEM_PU_DEF = "persistence-unit-defaults";
   protected static final String ELEM_XML_MAP_META_COMPLETE = "xml-mapping-metadata-complete";
   private static final Map _elems = new HashMap();
   private static final Localizer _loc;
   private final OpenJPAConfiguration _conf;
   private MetaDataRepository _repos = null;
   private AnnotationPersistenceMetaDataParser _parser = null;
   private ClassLoader _envLoader = null;
   private int _mode = 0;
   private boolean _override = false;
   private final Stack _elements = new Stack();
   private final Stack _parents = new Stack();
   private Class _cls = null;
   private int _fieldPos = 0;
   private int _clsPos = 0;
   private int _access = 0;
   private PersistenceStrategy _strategy = null;
   private Set _cascades = null;
   private Set _pkgCascades = null;
   private Class _listener = null;
   private Collection[] _callbacks = null;
   private int[] _highs = null;

   public XMLPersistenceMetaDataParser(OpenJPAConfiguration conf) {
      this._conf = conf;
      this.setValidating(true);
      this.setLog(conf.getLog("openjpa.MetaData"));
      this.setParseComments(true);
      this.setMode(7);
      this.setParseText(true);
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public AnnotationPersistenceMetaDataParser getAnnotationParser() {
      return this._parser;
   }

   public void setAnnotationParser(AnnotationPersistenceMetaDataParser parser) {
      this._parser = parser;
   }

   public MetaDataRepository getRepository() {
      if (this._repos == null) {
         MetaDataRepository repos = this._conf.newMetaDataRepositoryInstance();
         MetaDataFactory mdf = repos.getMetaDataFactory();
         if (mdf instanceof DelegatingMetaDataFactory) {
            mdf = ((DelegatingMetaDataFactory)mdf).getInnermostDelegate();
         }

         if (mdf instanceof PersistenceMetaDataFactory) {
            ((PersistenceMetaDataFactory)mdf).setXMLParser(this);
         }

         this._repos = repos;
      }

      return this._repos;
   }

   public void setRepository(MetaDataRepository repos) {
      this._repos = repos;
      if (repos != null && (repos.getValidate() & 8) != 0) {
         this.setParseComments(false);
      }

   }

   public ClassLoader getEnvClassLoader() {
      return this._envLoader;
   }

   public void setEnvClassLoader(ClassLoader loader) {
      this._envLoader = loader;
   }

   public boolean getMappingOverride() {
      return this._override;
   }

   public void setMappingOverride(boolean override) {
      this._override = override;
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
      if (this._parser != null) {
         this._parser.setMode(mode);
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

   protected boolean isMappingOverrideMode() {
      return this.isMappingMode() || this._override && this.isMetaDataMode();
   }

   protected void pushElement(Object elem) {
      this._elements.push(elem);
   }

   protected Object popElement() {
      return this._elements.pop();
   }

   protected Object currentElement() {
      return this._elements.isEmpty() ? null : this._elements.peek();
   }

   protected PersistenceStrategy currentStrategy() {
      return this._strategy;
   }

   protected Object currentParent() {
      return this._parents.isEmpty() ? null : this._parents.peek();
   }

   protected boolean isRuntime() {
      return (this.getRepository().getValidate() & 8) != 0;
   }

   protected Object getSchemaSource() {
      return XMLPersistenceMetaDataParser.class.getResourceAsStream("orm-xsd.rsrc");
   }

   protected String getPackageAttributeName() {
      return null;
   }

   protected String getClassAttributeName() {
      return "class";
   }

   protected int getClassElementDepth() {
      return 1;
   }

   protected boolean isClassElementName(String name) {
      return "entity".equals(name) || "embeddable".equals(name) || "mapped-superclass".equals(name);
   }

   protected void reset() {
      super.reset();
      this._elements.clear();
      this._parents.clear();
      this._cls = null;
      this._fieldPos = 0;
      this._clsPos = 0;
      this._access = 0;
      this._strategy = null;
      this._listener = null;
      this._callbacks = null;
      this._highs = null;
      this._cascades = null;
      this._pkgCascades = null;
   }

   protected boolean startSystemElement(String name, Attributes attrs) throws SAXException {
      Object tag = _elems.get(name);
      boolean ret = false;
      if (tag == null) {
         if (this.isMappingOverrideMode()) {
            tag = this.startSystemMappingElement(name, attrs);
         }

         ret = tag != null;
      } else if (tag instanceof MetaDataTag) {
         switch ((MetaDataTag)tag) {
            case QUERY:
               ret = this.startNamedQuery(attrs);
               break;
            case QUERY_HINT:
               ret = this.startQueryHint(attrs);
               break;
            case NATIVE_QUERY:
               ret = this.startNamedNativeQuery(attrs);
               break;
            case QUERY_STRING:
               ret = this.startQueryString(attrs);
               break;
            case SEQ_GENERATOR:
               ret = this.startSequenceGenerator(attrs);
               break;
            case FLUSH_MODE:
               ret = this.startFlushMode(attrs);
               break;
            case ENTITY_LISTENERS:
               ret = this.startEntityListeners(attrs);
               break;
            case PRE_PERSIST:
            case POST_PERSIST:
            case PRE_REMOVE:
            case POST_REMOVE:
            case PRE_UPDATE:
            case POST_UPDATE:
            case POST_LOAD:
               ret = this.startCallback((MetaDataTag)tag, attrs);
               break;
            default:
               this.warnUnsupportedTag(name);
         }
      } else if (tag != "persistence-unit-metadata" && tag != "persistence-unit-defaults") {
         if (tag == "xml-mapping-metadata-complete") {
            this.setAnnotationParser((AnnotationPersistenceMetaDataParser)null);
         } else if (tag == "access") {
            ret = this._mode != 4;
         } else if (tag == "entity-listener") {
            ret = this.startEntityListener(attrs);
         } else if (tag == "cascade") {
            ret = this.isMetaDataMode();
         } else if (tag == "cascade-all" || tag == "cascade-persist" || tag == "cascade-merge" || tag == "cascade-remove" || tag == "cascade-refresh") {
            ret = this.startCascade(tag, attrs);
         }
      } else {
         ret = this.isMetaDataMode();
      }

      if (ret) {
         this._parents.push(tag);
      }

      return ret;
   }

   protected void endSystemElement(String name) throws SAXException {
      Object tag = _elems.get(name);
      if (tag == null && this.isMappingOverrideMode()) {
         this.endSystemMappingElement(name);
      } else if (tag instanceof MetaDataTag) {
         switch ((MetaDataTag)tag) {
            case QUERY:
               this.endNamedQuery();
               break;
            case QUERY_HINT:
               this.endQueryHint();
               break;
            case NATIVE_QUERY:
               this.endNamedNativeQuery();
               break;
            case QUERY_STRING:
               this.endQueryString();
               break;
            case SEQ_GENERATOR:
               this.endSequenceGenerator();
         }
      } else if (tag == "access") {
         this.endAccess();
      } else if (tag == "entity-listener") {
         this.endEntityListener();
      }

      this._parents.pop();
   }

   protected Object startSystemMappingElement(String name, Attributes attrs) throws SAXException {
      return null;
   }

   protected void endSystemMappingElement(String name) throws SAXException {
   }

   protected boolean startClassElement(String name, Attributes attrs) throws SAXException {
      Object tag = _elems.get(name);
      boolean ret = false;
      if (tag == null) {
         if (this.isMappingOverrideMode()) {
            tag = this.startClassMappingElement(name, attrs);
         }

         ret = tag != null;
      } else if (tag instanceof MetaDataTag) {
         switch ((MetaDataTag)tag) {
            case QUERY:
               ret = this.startNamedQuery(attrs);
               break;
            case QUERY_HINT:
               ret = this.startQueryHint(attrs);
               break;
            case NATIVE_QUERY:
               ret = this.startNamedNativeQuery(attrs);
               break;
            case QUERY_STRING:
               ret = this.startQueryString(attrs);
               break;
            case SEQ_GENERATOR:
               ret = this.startSequenceGenerator(attrs);
               break;
            case FLUSH_MODE:
               ret = this.startFlushMode(attrs);
               break;
            case ENTITY_LISTENERS:
            case ORDER_BY:
               ret = this.isMetaDataMode();
               break;
            case PRE_PERSIST:
            case POST_PERSIST:
            case PRE_REMOVE:
            case POST_REMOVE:
            case PRE_UPDATE:
            case POST_UPDATE:
            case POST_LOAD:
               ret = this.startCallback((MetaDataTag)tag, attrs);
               break;
            case GENERATED_VALUE:
               ret = this.startGeneratedValue(attrs);
               break;
            case ID:
               ret = this.startId(attrs);
               break;
            case EMBEDDED_ID:
               ret = this.startEmbeddedId(attrs);
               break;
            case ID_CLASS:
               ret = this.startIdClass(attrs);
               break;
            case LOB:
               ret = this.startLob(attrs);
               break;
            case VERSION:
               ret = this.startVersion(attrs);
               break;
            case MAP_KEY:
               ret = this.startMapKey(attrs);
               break;
            case EXCLUDE_DEFAULT_LISTENERS:
               ret = this.startExcludeDefaultListeners(attrs);
               break;
            case EXCLUDE_SUPERCLASS_LISTENERS:
               ret = this.startExcludeSuperclassListeners(attrs);
               break;
            default:
               this.warnUnsupportedTag(name);
         }
      } else if (tag instanceof PersistenceStrategy) {
         PersistenceStrategy ps = (PersistenceStrategy)tag;
         ret = this.startStrategy(ps, attrs);
         if (ret) {
            this._strategy = ps;
         }
      } else if (tag == "entity-listener") {
         ret = this.startEntityListener(attrs);
      } else if (tag == "attributes") {
         ret = this._mode != 4;
      } else if (tag == "cascade") {
         ret = this.isMetaDataMode();
      } else if (tag == "cascade-all" || tag == "cascade-persist" || tag == "cascade-merge" || tag == "cascade-remove" || tag == "cascade-refresh") {
         ret = this.startCascade(tag, attrs);
      }

      if (ret) {
         this._parents.push(tag);
      }

      return ret;
   }

   protected void endClassElement(String name) throws SAXException {
      Object tag = _elems.get(name);
      if (tag == null && this.isMappingOverrideMode()) {
         this.endClassMappingElement(name);
      } else if (tag instanceof MetaDataTag) {
         switch ((MetaDataTag)tag) {
            case QUERY:
               this.endNamedQuery();
               break;
            case QUERY_HINT:
               this.endQueryHint();
               break;
            case NATIVE_QUERY:
               this.endNamedNativeQuery();
               break;
            case QUERY_STRING:
               this.endQueryString();
               break;
            case SEQ_GENERATOR:
               this.endSequenceGenerator();
            case FLUSH_MODE:
            case ENTITY_LISTENERS:
            case PRE_PERSIST:
            case POST_PERSIST:
            case PRE_REMOVE:
            case POST_REMOVE:
            case PRE_UPDATE:
            case POST_UPDATE:
            case POST_LOAD:
            case MAP_KEY:
            default:
               break;
            case GENERATED_VALUE:
               this.endGeneratedValue();
               break;
            case ID:
               this.endId();
               break;
            case EMBEDDED_ID:
               this.endEmbeddedId();
               break;
            case ID_CLASS:
               this.endIdClass();
               break;
            case LOB:
               this.endLob();
               break;
            case VERSION:
               this.endVersion();
               break;
            case ORDER_BY:
               this.endOrderBy();
         }
      } else if (tag instanceof PersistenceStrategy) {
         this.endStrategy((PersistenceStrategy)tag);
      } else if (tag == "access") {
         this.endAccess();
      } else if (tag == "entity-listener") {
         this.endEntityListener();
      }

      this._parents.pop();
   }

   private void warnUnsupportedTag(String name) {
      Log log = this.getLog();
      if (log.isInfoEnabled()) {
         log.trace(_loc.get("unsupported-tag", (Object)name));
      }

   }

   protected Object startClassMappingElement(String name, Attributes attrs) throws SAXException {
      return null;
   }

   protected void endClassMappingElement(String name) throws SAXException {
   }

   protected boolean startClass(String elem, Attributes attrs) throws SAXException {
      super.startClass(elem, attrs);
      this._cls = this.classForName(this.currentClassName());
      if (this._mode == 4) {
         if (this._parser != null && !"true".equals(attrs.getValue("metadata-complete"))) {
            this._parser.parse(this._cls);
         }

         return true;
      } else {
         Log log = this.getLog();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("parse-class", (Object)this._cls.getName()));
         }

         MetaDataRepository repos = this.getRepository();
         ClassMetaData meta = repos.getCachedMetaData(this._cls);
         if (meta != null && (this.isMetaDataMode() && (meta.getSourceMode() & 1) != 0 || this.isMappingMode() && (meta.getSourceMode() & 2) != 0)) {
            if (log.isWarnEnabled()) {
               log.warn(_loc.get("dup-metadata", this._cls, this.getSourceName()));
            }

            this._cls = null;
            return false;
         } else {
            int defaultAccess = this._access;
            if (defaultAccess == 0) {
               ClassMetaData sup = repos.getCachedMetaData(this._cls.getSuperclass());
               if (sup != null) {
                  defaultAccess = sup.getAccessType();
               }
            }

            if (meta == null) {
               int access = this.toAccessType(attrs.getValue("access"), defaultAccess);
               meta = repos.addMetaData(this._cls, access);
               meta.setEnvClassLoader(this._envLoader);
               meta.setSourceMode(0);
               if (this._parser != null && !"true".equals(attrs.getValue("metadata-complete"))) {
                  this._parser.parse(this._cls);
               }
            }

            boolean mappedSuper = "mapped-superclass".equals(elem);
            if (this.isMetaDataMode()) {
               meta.setSource(this.getSourceFile(), 2);
               meta.setSourceMode(1, true);
               meta.setListingIndex(this._clsPos);
               String name = attrs.getValue("name");
               if (!StringUtils.isEmpty(name)) {
                  meta.setTypeAlias(name);
               }

               meta.setAbstract(mappedSuper);
               meta.setEmbeddedOnly(mappedSuper || "embeddable".equals(elem));
               if (mappedSuper) {
                  meta.setIdentityType(0);
               }
            }

            if (this.isMappingMode()) {
               meta.setSourceMode(2, true);
            }

            if (this.isMappingOverrideMode()) {
               this.startClassMapping(meta, mappedSuper, attrs);
            }

            if (this.isQueryMode()) {
               meta.setSourceMode(4, true);
            }

            ++this._clsPos;
            this._fieldPos = 0;
            this.addComments(meta);
            this.pushElement(meta);
            return true;
         }
      }
   }

   protected void endClass(String elem) throws SAXException {
      if (this._mode != 4) {
         ClassMetaData meta = (ClassMetaData)this.popElement();
         this.storeCallbacks(meta);
         if (this.isMappingOverrideMode()) {
            this.endClassMapping(meta);
         }
      }

      this._cls = null;
      super.endClass(elem);
   }

   protected void startClassMapping(ClassMetaData mapping, boolean mappedSuper, Attributes attrs) throws SAXException {
   }

   protected void endClassMapping(ClassMetaData mapping) throws SAXException {
   }

   private void endAccess() {
      this._access = this.toAccessType(this.currentText(), 0);
   }

   private int toAccessType(String str, int def) {
      if (StringUtils.isEmpty(str)) {
         return def;
      } else {
         return "PROPERTY".equals(str) ? 4 : 2;
      }
   }

   private boolean startFlushMode(Attributes attrs) throws SAXException {
      Log log = this.getLog();
      if (log.isWarnEnabled()) {
         log.warn(_loc.get("unsupported", "flush-mode", this.getSourceName()));
      }

      return false;
   }

   protected boolean startSequenceGenerator(Attributes attrs) {
      if (!this.isMappingOverrideMode()) {
         return false;
      } else {
         String name = attrs.getValue("name");
         Log log = this.getLog();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("parse-sequence", (Object)name));
         }

         SequenceMetaData meta = this.getRepository().getCachedSequenceMetaData(name);
         if (meta != null && log.isWarnEnabled()) {
            log.warn(_loc.get("override-sequence", (Object)name));
         }

         meta = this.getRepository().addSequenceMetaData(name);
         String seq = attrs.getValue("sequence-name");
         String val = attrs.getValue("initial-value");
         int initial = val == null ? 1 : Integer.parseInt(val);
         val = attrs.getValue("allocation-size");
         int allocate = val == null ? 50 : Integer.parseInt(val);
         String clsName;
         String props;
         if (seq != null && seq.indexOf(40) != -1) {
            clsName = Configurations.getClassName(seq);
            props = Configurations.getProperties(seq);
            seq = null;
         } else {
            clsName = "native";
            props = null;
         }

         meta.setSequencePlugin(Configurations.getPlugin(clsName, props));
         meta.setSequence(seq);
         meta.setInitialValue(initial);
         meta.setAllocate(allocate);
         Object cur = this.currentElement();
         Object scope = cur instanceof ClassMetaData ? ((ClassMetaData)cur).getDescribedType() : null;
         meta.setSource(this.getSourceFile(), scope, 2);
         return true;
      }
   }

   protected void endSequenceGenerator() {
   }

   protected boolean startId(Attributes attrs) throws SAXException {
      FieldMetaData fmd = this.parseField(attrs);
      fmd.setExplicit(true);
      fmd.setPrimaryKey(true);
      return true;
   }

   protected void endId() throws SAXException {
      this.finishField();
   }

   protected boolean startEmbeddedId(Attributes attrs) throws SAXException {
      FieldMetaData fmd = this.parseField(attrs);
      fmd.setExplicit(true);
      fmd.setPrimaryKey(true);
      fmd.setEmbedded(true);
      if (fmd.getEmbeddedMetaData() == null) {
         fmd.addEmbeddedMetaData();
      }

      return true;
   }

   protected void endEmbeddedId() throws SAXException {
      this.finishField();
   }

   protected boolean startIdClass(Attributes attrs) throws SAXException {
      if (!this.isMetaDataMode()) {
         return false;
      } else {
         ClassMetaData meta = (ClassMetaData)this.currentElement();
         String cls = attrs.getValue("class");
         Class idCls = null;

         try {
            idCls = this.classForName(cls);
         } catch (Throwable var6) {
            throw this.getException(_loc.get("invalid-id-class", meta, cls), var6);
         }

         meta.setObjectIdType(idCls, true);
         return true;
      }
   }

   protected void endIdClass() throws SAXException {
   }

   protected boolean startLob(Attributes attrs) throws SAXException {
      FieldMetaData fmd = (FieldMetaData)this.currentElement();
      if (fmd.getDeclaredTypeCode() != 9 && fmd.getDeclaredType() != char[].class && fmd.getDeclaredType() != Character[].class && fmd.getDeclaredType() != byte[].class && fmd.getDeclaredType() != Byte[].class) {
         fmd.setSerialized(true);
      }

      return true;
   }

   protected void endLob() throws SAXException {
   }

   protected boolean startGeneratedValue(Attributes attrs) throws SAXException {
      if (!this.isMappingOverrideMode()) {
         return false;
      } else {
         String strategy = attrs.getValue("strategy");
         String generator = attrs.getValue("generator");
         GenerationType type = StringUtils.isEmpty(strategy) ? GenerationType.AUTO : GenerationType.valueOf(strategy);
         FieldMetaData fmd = (FieldMetaData)this.currentElement();
         AnnotationPersistenceMetaDataParser.parseGeneratedValue(fmd, type, generator);
         return true;
      }
   }

   protected void endGeneratedValue() throws SAXException {
   }

   protected boolean startCascade(Object tag, Attributes attrs) throws SAXException {
      if (!this.isMetaDataMode()) {
         return false;
      } else {
         Set cascades = null;
         if (this.currentElement() instanceof FieldMetaData) {
            if (this._cascades == null) {
               this._cascades = EnumSet.noneOf(CascadeType.class);
            }

            cascades = this._cascades;
         } else {
            if (this._pkgCascades == null) {
               this._pkgCascades = EnumSet.noneOf(CascadeType.class);
            }

            cascades = this._pkgCascades;
         }

         boolean all = "cascade-all" == tag;
         if (all || "cascade-persist" == tag) {
            cascades.add(CascadeType.PERSIST);
         }

         if (all || "cascade-remove" == tag) {
            cascades.add(CascadeType.REMOVE);
         }

         if (all || "cascade-merge" == tag) {
            cascades.add(CascadeType.MERGE);
         }

         if (all || "cascade-refresh" == tag) {
            cascades.add(CascadeType.REFRESH);
         }

         return true;
      }
   }

   protected void setCascades(FieldMetaData fmd) {
      Set cascades = this._cascades;
      if (this._cascades == null) {
         cascades = this._pkgCascades;
      }

      if (cascades != null) {
         ValueMetaData vmd = fmd;
         switch (this._strategy) {
            case ONE_MANY:
            case MANY_MANY:
               vmd = fmd.getElement();
            default:
               Iterator i$ = cascades.iterator();

               while(i$.hasNext()) {
                  CascadeType cascade = (CascadeType)i$.next();
                  switch (cascade) {
                     case PERSIST:
                        ((ValueMetaData)vmd).setCascadePersist(1);
                        break;
                     case MERGE:
                        ((ValueMetaData)vmd).setCascadeAttach(1);
                        break;
                     case REMOVE:
                        ((ValueMetaData)vmd).setCascadeDelete(1);
                        break;
                     case REFRESH:
                        ((ValueMetaData)vmd).setCascadeRefresh(1);
                  }
               }

               this._cascades = null;
         }
      }
   }

   private FieldMetaData parseField(Attributes attrs) throws SAXException {
      ClassMetaData meta = (ClassMetaData)this.currentElement();
      String name = attrs.getValue("name");
      FieldMetaData field = meta.getDeclaredField(name);
      if ((field == null || field.getDeclaredType() == Object.class) && meta.getDescribedType() != Object.class) {
         Member member = null;
         Class type = null;
         int def = this._repos.getMetaDataFactory().getDefaults().getDefaultAccessType();

         try {
            if (meta.getAccessType() == 4 || meta.getAccessType() == 0 && def == 4) {
               String cap = StringUtils.capitalize(name);
               type = meta.getDescribedType();

               try {
                  member = (Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(type, "get" + cap, (Class[])null));
               } catch (Exception var12) {
                  try {
                     member = (Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(type, "is" + cap, (Class[])null));
                  } catch (Exception var11) {
                     throw var12;
                  }
               }

               type = ((Method)member).getReturnType();
            } else {
               member = (Field)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredFieldAction(meta.getDescribedType(), name));
               type = ((Field)member).getType();
            }
         } catch (Exception var13) {
            Exception e = var13;
            if (var13 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var13).getException();
            }

            throw this.getException(_loc.get("invalid-attr", name, meta), e);
         }

         if (field == null) {
            field = meta.addDeclaredField(name, type);
            PersistenceMetaDataDefaults.setCascadeNone(field);
            PersistenceMetaDataDefaults.setCascadeNone(field.getKey());
            PersistenceMetaDataDefaults.setCascadeNone(field.getElement());
         }

         field.backingMember((Member)member);
      } else if (field == null) {
         field = meta.addDeclaredField(name, Object.class);
         PersistenceMetaDataDefaults.setCascadeNone(field);
         PersistenceMetaDataDefaults.setCascadeNone(field.getKey());
         PersistenceMetaDataDefaults.setCascadeNone(field.getElement());
      }

      if (this.isMetaDataMode()) {
         field.setListingIndex(this._fieldPos);
      }

      ++this._fieldPos;
      this.pushElement(field);
      this.addComments(field);
      if (this.isMappingOverrideMode()) {
         this.startFieldMapping(field, attrs);
      }

      return field;
   }

   private void finishField() throws SAXException {
      FieldMetaData field = (FieldMetaData)this.popElement();
      this.setCascades(field);
      if (this.isMappingOverrideMode()) {
         this.endFieldMapping(field);
      }

      this._strategy = null;
   }

   protected void startFieldMapping(FieldMetaData field, Attributes attrs) throws SAXException {
   }

   protected void endFieldMapping(FieldMetaData field) throws SAXException {
   }

   protected boolean startVersion(Attributes attrs) throws SAXException {
      FieldMetaData fmd = this.parseField(attrs);
      fmd.setExplicit(true);
      fmd.setVersion(true);
      return true;
   }

   protected void endVersion() throws SAXException {
      this.finishField();
   }

   private boolean startStrategy(PersistenceStrategy strategy, Attributes attrs) throws SAXException {
      FieldMetaData fmd = this.parseField(attrs);
      fmd.setExplicit(true);
      fmd.setManagement(3);
      String val = attrs.getValue("optional");
      if ("false".equals(val)) {
         fmd.setNullValue(2);
      } else if ("true".equals(val) && fmd.getNullValue() == 2) {
         fmd.setNullValue(-1);
      }

      if (this.isMappingOverrideMode()) {
         val = attrs.getValue("mapped-by");
         if (val != null) {
            fmd.setMappedBy(val);
         }
      }

      this.parseStrategy(fmd, strategy, attrs);
      return true;
   }

   private void endStrategy(PersistenceStrategy strategy) throws SAXException {
      this.finishField();
   }

   private void parseStrategy(FieldMetaData fmd, PersistenceStrategy strategy, Attributes attrs) throws SAXException {
      switch (strategy) {
         case ONE_MANY:
            this.parseOneToMany(fmd, attrs);
            break;
         case MANY_MANY:
            this.parseManyToMany(fmd, attrs);
            break;
         case BASIC:
            this.parseBasic(fmd, attrs);
            break;
         case EMBEDDED:
            this.parseEmbedded(fmd, attrs);
            break;
         case ONE_ONE:
            this.parseOneToOne(fmd, attrs);
            break;
         case MANY_ONE:
            this.parseManyToOne(fmd, attrs);
            break;
         case TRANSIENT:
            String val = attrs.getValue("fetch");
            if (val != null) {
               fmd.setInDefaultFetchGroup("EAGER".equals(val));
            }

            fmd.setManagement(0);
      }

   }

   protected void parseBasic(FieldMetaData fmd, Attributes attrs) throws SAXException {
      String val = attrs.getValue("fetch");
      if (val != null) {
         fmd.setInDefaultFetchGroup("EAGER".equals(val));
      }

   }

   protected void parseEmbedded(FieldMetaData fmd, Attributes attrs) throws SAXException {
      this.assertPC(fmd, "Embedded");
      fmd.setInDefaultFetchGroup(true);
      fmd.setEmbedded(true);
      fmd.setSerialized(false);
      if (fmd.getEmbeddedMetaData() == null) {
         fmd.addEmbeddedMetaData();
      }

   }

   private void assertPC(FieldMetaData fmd, String attr) throws SAXException {
      if (!JavaTypes.maybePC(fmd)) {
         throw this.getException(_loc.get("bad-meta-anno", fmd, attr));
      }
   }

   protected void parseOneToOne(FieldMetaData fmd, Attributes attrs) throws SAXException {
      String val = attrs.getValue("fetch");
      if (val != null && "EAGER".equals(val)) {
         fmd.setInDefaultFetchGroup(true);
      }

      val = attrs.getValue("target-entity");
      if (val != null) {
         fmd.setTypeOverride(this.classForName(val));
      }

      this.assertPC(fmd, "OneToOne");
      fmd.setSerialized(false);
   }

   protected void parseManyToOne(FieldMetaData fmd, Attributes attrs) throws SAXException {
      String val = attrs.getValue("fetch");
      if (val != null && "EAGER".equals(val)) {
         fmd.setInDefaultFetchGroup(true);
      }

      val = attrs.getValue("target-entity");
      if (val != null) {
         fmd.setTypeOverride(this.classForName(val));
      }

      this.assertPC(fmd, "ManyToOne");
      fmd.setSerialized(false);
   }

   protected void parseManyToMany(FieldMetaData fmd, Attributes attrs) throws SAXException {
      String val = attrs.getValue("fetch");
      if (val != null) {
         fmd.setInDefaultFetchGroup("EAGER".equals(val));
      }

      val = attrs.getValue("target-entity");
      if (val != null) {
         fmd.getElement().setDeclaredType(this.classForName(val));
      }

      this.assertPCCollection(fmd, "ManyToMany");
      fmd.setSerialized(false);
   }

   private void assertPCCollection(FieldMetaData fmd, String attr) throws SAXException {
      switch (fmd.getDeclaredTypeCode()) {
         case 11:
         case 12:
         case 13:
            if (JavaTypes.maybePC(fmd.getElement())) {
               return;
            }
         default:
            throw this.getException(_loc.get("bad-meta-anno", fmd, attr));
      }
   }

   protected void parseOneToMany(FieldMetaData fmd, Attributes attrs) throws SAXException {
      String val = attrs.getValue("fetch");
      if (val != null) {
         fmd.setInDefaultFetchGroup("EAGER".equals(val));
      }

      val = attrs.getValue("target-entity");
      if (val != null) {
         fmd.getElement().setDeclaredType(this.classForName(val));
      }

      this.assertPCCollection(fmd, "OneToMany");
      fmd.setSerialized(false);
   }

   private boolean startMapKey(Attributes attrs) throws SAXException {
      if (!this.isMappingOverrideMode()) {
         return false;
      } else {
         FieldMetaData fmd = (FieldMetaData)this.currentElement();
         String mapKey = attrs.getValue("name");
         if (mapKey == null) {
            fmd.getKey().setValueMappedBy("`pk`");
         } else {
            fmd.getKey().setValueMappedBy(mapKey);
         }

         return true;
      }
   }

   private void endOrderBy() throws SAXException {
      FieldMetaData fmd = (FieldMetaData)this.currentElement();
      String dec = this.currentText();
      if (StringUtils.isEmpty(dec)) {
         dec = "#element asc";
      }

      fmd.setOrderDeclaration(dec);
   }

   protected boolean startNamedQuery(Attributes attrs) throws SAXException {
      if (!this.isQueryMode()) {
         return false;
      } else {
         String name = attrs.getValue("name");
         Log log = this.getLog();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("parse-query", (Object)name));
         }

         QueryMetaData meta = this.getRepository().getCachedQueryMetaData((Class)null, name);
         if (meta != null && log.isWarnEnabled()) {
            log.warn(_loc.get("override-query", name, this.currentLocation()));
         }

         meta = this.getRepository().addQueryMetaData((Class)null, name);
         meta.setDefiningType(this._cls);
         meta.setQueryString(attrs.getValue("query"));
         meta.setLanguage("javax.persistence.JPQL");
         Object cur = this.currentElement();
         Object scope = cur instanceof ClassMetaData ? ((ClassMetaData)cur).getDescribedType() : null;
         meta.setSource(this.getSourceFile(), scope, 2);
         if (this.isMetaDataMode()) {
            meta.setSourceMode(1);
         } else if (this.isMappingMode()) {
            meta.setSourceMode(2);
         } else {
            meta.setSourceMode(4);
         }

         this.pushElement(meta);
         return true;
      }
   }

   protected void endNamedQuery() throws SAXException {
      this.popElement();
   }

   protected boolean startQueryString(Attributes attrs) throws SAXException {
      return true;
   }

   protected void endQueryString() throws SAXException {
      QueryMetaData meta = (QueryMetaData)this.currentElement();
      meta.setQueryString(this.currentText());
   }

   protected boolean startQueryHint(Attributes attrs) throws SAXException {
      QueryMetaData meta = (QueryMetaData)this.currentElement();
      meta.addHint(attrs.getValue("name"), attrs.getValue("value"));
      return true;
   }

   protected void endQueryHint() throws SAXException {
   }

   protected boolean startNamedNativeQuery(Attributes attrs) throws SAXException {
      if (!this.isQueryMode()) {
         return false;
      } else {
         String name = attrs.getValue("name");
         Log log = this.getLog();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("parse-native-query", (Object)name));
         }

         QueryMetaData meta = this.getRepository().getCachedQueryMetaData((Class)null, name);
         if (meta != null && log.isWarnEnabled()) {
            log.warn(_loc.get("override-query", name, this.currentLocation()));
         }

         meta = this.getRepository().addQueryMetaData((Class)null, name);
         meta.setDefiningType(this._cls);
         meta.setQueryString(attrs.getValue("query"));
         meta.setLanguage("openjpa.SQL");
         String val = attrs.getValue("result-class");
         if (val != null) {
            Class type = this.classForName(val);
            if (ImplHelper.isManagedType(this.getConfiguration(), type)) {
               meta.setCandidateType(type);
            } else {
               meta.setResultType(type);
            }
         }

         val = attrs.getValue("result-set-mapping");
         if (val != null) {
            meta.setResultSetMappingName(val);
         }

         Object cur = this.currentElement();
         Object scope = cur instanceof ClassMetaData ? ((ClassMetaData)cur).getDescribedType() : null;
         meta.setSource(this.getSourceFile(), scope, 2);
         if (this.isMetaDataMode()) {
            meta.setSourceMode(1);
         } else if (this.isMappingMode()) {
            meta.setSourceMode(2);
         } else {
            meta.setSourceMode(4);
         }

         this.pushElement(meta);
         return true;
      }
   }

   protected void endNamedNativeQuery() throws SAXException {
      this.popElement();
   }

   private boolean startEntityListeners(Attributes attrs) throws SAXException {
      if (!this.isMetaDataMode()) {
         return false;
      } else if (this.currentElement() == null) {
         return true;
      } else {
         LifecycleMetaData meta = ((ClassMetaData)this.currentElement()).getLifecycleMetaData();

         for(int i = 0; i < LifecycleEvent.ALL_EVENTS.length; ++i) {
            meta.setDeclaredCallbacks(i, (LifecycleCallbacks[])null, 0);
         }

         return true;
      }
   }

   private boolean startExcludeDefaultListeners(Attributes attrs) throws SAXException {
      if (!this.isMetaDataMode()) {
         return false;
      } else {
         ClassMetaData meta = (ClassMetaData)this.currentElement();
         meta.getLifecycleMetaData().setIgnoreSystemListeners(true);
         return true;
      }
   }

   private boolean startExcludeSuperclassListeners(Attributes attrs) throws SAXException {
      if (!this.isMetaDataMode()) {
         return false;
      } else {
         ClassMetaData meta = (ClassMetaData)this.currentElement();
         meta.getLifecycleMetaData().setIgnoreSuperclassCallbacks(2);
         return true;
      }
   }

   private boolean startEntityListener(Attributes attrs) throws SAXException {
      this._listener = this.classForName(attrs.getValue("class"));
      boolean system = this.currentElement() == null;
      Collection[] parsed = AnnotationPersistenceMetaDataParser.parseCallbackMethods(this._listener, (Collection[])null, true, true, this._repos);
      if (parsed == null) {
         return true;
      } else {
         if (this._callbacks == null) {
            this._callbacks = (Collection[])(new Collection[LifecycleEvent.ALL_EVENTS.length]);
            if (!system) {
               this._highs = new int[LifecycleEvent.ALL_EVENTS.length];
            }
         }

         for(int i = 0; i < parsed.length; ++i) {
            if (parsed[i] != null) {
               if (this._callbacks[i] == null) {
                  this._callbacks[i] = parsed[i];
               } else {
                  this._callbacks[i].addAll(parsed[i]);
               }

               if (!system) {
                  int[] var10000 = this._highs;
                  var10000[i] += parsed[i].size();
               }
            }
         }

         return true;
      }
   }

   private void endEntityListener() throws SAXException {
      if (this.currentElement() == null && this._callbacks != null) {
         this._repos.addSystemListener(new PersistenceListenerAdapter(this._callbacks));
         this._callbacks = null;
      }

      this._listener = null;
   }

   private boolean startCallback(MetaDataTag callback, Attributes attrs) throws SAXException {
      if (!this.isMetaDataMode()) {
         return false;
      } else {
         int[] events = MetaDataParsers.getEventTypes(callback);
         if (events == null) {
            return false;
         } else {
            boolean system = this.currentElement() == null;
            Class type = this.currentElement() == null ? null : ((ClassMetaData)this.currentElement()).getDescribedType();
            if (type == null) {
               type = Object.class;
            }

            if (this._callbacks == null) {
               this._callbacks = (Collection[])(new Collection[LifecycleEvent.ALL_EVENTS.length]);
               if (!system) {
                  this._highs = new int[LifecycleEvent.ALL_EVENTS.length];
               }
            }

            MetaDataDefaults def = this._repos.getMetaDataFactory().getDefaults();
            Object adapter;
            if (this._listener != null) {
               adapter = new BeanLifecycleCallbacks(this._listener, attrs.getValue("method-name"), false, type);
            } else {
               adapter = new MethodLifecycleCallbacks(this._cls, attrs.getValue("method-name"), false);
            }

            for(int i = 0; i < events.length; ++i) {
               int event = events[i];
               if (this._listener != null) {
                  MetaDataParsers.validateMethodsForSameCallback(this._listener, this._callbacks[event], ((BeanLifecycleCallbacks)adapter).getCallbackMethod(), callback, def, this.getLog());
               } else {
                  MetaDataParsers.validateMethodsForSameCallback(this._cls, this._callbacks[event], ((MethodLifecycleCallbacks)adapter).getCallbackMethod(), callback, def, this.getLog());
               }

               if (this._callbacks[event] == null) {
                  this._callbacks[event] = new ArrayList(3);
               }

               this._callbacks[event].add(adapter);
               if (!system && this._listener != null) {
                  int var10002 = this._highs[event]++;
               }
            }

            return true;
         }
      }
   }

   private void storeCallbacks(ClassMetaData cls) {
      LifecycleMetaData meta = cls.getLifecycleMetaData();
      Class supCls = cls.getDescribedType().getSuperclass();
      Collection[] supCalls = null;
      if (!Object.class.equals(supCls)) {
         supCalls = AnnotationPersistenceMetaDataParser.parseCallbackMethods(supCls, (Collection[])null, true, false, this._repos);
      }

      int[] arr$;
      int len$;
      int i$;
      int event;
      if (supCalls != null) {
         arr$ = LifecycleEvent.ALL_EVENTS;
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            event = arr$[i$];
            if (supCalls[event] != null) {
               meta.setNonPCSuperclassCallbacks(event, (LifecycleCallbacks[])supCalls[event].toArray(new LifecycleCallbacks[supCalls[event].size()]), 0);
            }
         }
      }

      if (this._callbacks != null) {
         arr$ = LifecycleEvent.ALL_EVENTS;
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            event = arr$[i$];
            if (this._callbacks[event] != null) {
               meta.setDeclaredCallbacks(event, (LifecycleCallbacks[])((LifecycleCallbacks[])this._callbacks[event].toArray(new LifecycleCallbacks[this._callbacks[event].size()])), this._highs[event]);
            }
         }

         this._callbacks = null;
         this._highs = null;
      }
   }

   protected Class classForName(String name) throws SAXException {
      return "Entity".equals(name) ? PersistenceCapable.class : super.classForName(name, this.isRuntime());
   }

   static {
      _elems.put("package", "package");
      _elems.put("access", "access");
      _elems.put("attributes", "attributes");
      _elems.put("entity-listener", "entity-listener");
      _elems.put("cascade", "cascade");
      _elems.put("cascade-all", "cascade-all");
      _elems.put("cascade-persist", "cascade-persist");
      _elems.put("cascade-remove", "cascade-remove");
      _elems.put("cascade-merge", "cascade-merge");
      _elems.put("cascade-refresh", "cascade-refresh");
      _elems.put("persistence-unit-metadata", "persistence-unit-metadata");
      _elems.put("persistence-unit-defaults", "persistence-unit-defaults");
      _elems.put("xml-mapping-metadata-complete", "xml-mapping-metadata-complete");
      _elems.put("entity-listeners", MetaDataTag.ENTITY_LISTENERS);
      _elems.put("pre-persist", MetaDataTag.PRE_PERSIST);
      _elems.put("post-persist", MetaDataTag.POST_PERSIST);
      _elems.put("pre-remove", MetaDataTag.PRE_REMOVE);
      _elems.put("post-remove", MetaDataTag.POST_REMOVE);
      _elems.put("pre-update", MetaDataTag.PRE_UPDATE);
      _elems.put("post-update", MetaDataTag.POST_UPDATE);
      _elems.put("post-load", MetaDataTag.POST_LOAD);
      _elems.put("exclude-default-listeners", MetaDataTag.EXCLUDE_DEFAULT_LISTENERS);
      _elems.put("exclude-superclass-listeners", MetaDataTag.EXCLUDE_SUPERCLASS_LISTENERS);
      _elems.put("named-query", MetaDataTag.QUERY);
      _elems.put("named-native-query", MetaDataTag.NATIVE_QUERY);
      _elems.put("query-hint", MetaDataTag.QUERY_HINT);
      _elems.put("query", MetaDataTag.QUERY_STRING);
      _elems.put("flush-mode", MetaDataTag.FLUSH_MODE);
      _elems.put("sequence-generator", MetaDataTag.SEQ_GENERATOR);
      _elems.put("id", MetaDataTag.ID);
      _elems.put("id-class", MetaDataTag.ID_CLASS);
      _elems.put("embedded-id", MetaDataTag.EMBEDDED_ID);
      _elems.put("version", MetaDataTag.VERSION);
      _elems.put("generated-value", MetaDataTag.GENERATED_VALUE);
      _elems.put("map-key", MetaDataTag.MAP_KEY);
      _elems.put("order-by", MetaDataTag.ORDER_BY);
      _elems.put("lob", MetaDataTag.LOB);
      _elems.put("basic", PersistenceStrategy.BASIC);
      _elems.put("many-to-one", PersistenceStrategy.MANY_ONE);
      _elems.put("one-to-one", PersistenceStrategy.ONE_ONE);
      _elems.put("embedded", PersistenceStrategy.EMBEDDED);
      _elems.put("one-to-many", PersistenceStrategy.ONE_MANY);
      _elems.put("many-to-many", PersistenceStrategy.MANY_MANY);
      _elems.put("transient", PersistenceStrategy.TRANSIENT);
      _loc = Localizer.forPackage(XMLPersistenceMetaDataParser.class);
   }
}
