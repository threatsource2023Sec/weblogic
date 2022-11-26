package kodo.jdo;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.jdo.datastore.Sequence;
import javax.jdo.identity.ByteIdentity;
import javax.jdo.identity.CharIdentity;
import javax.jdo.identity.IntIdentity;
import javax.jdo.identity.LongIdentity;
import javax.jdo.identity.ObjectIdentity;
import javax.jdo.identity.ShortIdentity;
import javax.jdo.identity.StringIdentity;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.CFMetaDataParser;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.AbstractCFMetaDataFactory;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.DelegatingMetaDataFactory;
import org.apache.openjpa.meta.Extensions;
import org.apache.openjpa.meta.FetchGroup;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataModes;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.NonPersistentMetaData;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.meta.UpdateStrategies;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.meta.ValueStrategies;
import org.apache.openjpa.util.ByteId;
import org.apache.openjpa.util.CharId;
import org.apache.openjpa.util.IntId;
import org.apache.openjpa.util.LongId;
import org.apache.openjpa.util.ObjectId;
import org.apache.openjpa.util.ShortId;
import org.apache.openjpa.util.StringId;
import org.apache.openjpa.util.UnsupportedException;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import serp.util.Numbers;
import serp.util.Strings;

public class JDOMetaDataParser extends CFMetaDataParser implements AbstractCFMetaDataFactory.Parser, MetaDataModes {
   public static final String KODO = "kodo";
   public static final String ELEMENT = "element-";
   public static final String KEY = "key-";
   public static final String VALUE = "value-";
   public static final String EXT_DATA_CACHE = "data-cache";
   public static final String EXT_DATA_CACHE_TIMEOUT = "data-cache-timeout";
   public static final String EXT_DETACH_STATE_FIELD = "detached-state-field";
   public static final String[] CLASS_EXTENSIONS = new String[]{"data-cache", "data-cache-timeout", "detached-state-field"};
   public static final String EXT_INVERSE = "inverse-logical";
   public static final String EXT_EXTERNAL_VALUES = "external-values";
   public static final String EXT_EXTERNALIZER = "externalizer";
   public static final String EXT_FACTORY = "factory";
   public static final String EXT_LRS = "lrs";
   public static final String EXT_READ_ONLY = "read-only";
   public static final String EXT_TYPE = "type";
   public static final String EXT_ORDER_BY = "order-by";
   public static final String[] FIELD_EXTENSIONS = new String[]{"inverse-logical", "external-values", "externalizer", "factory", "lrs", "read-only", "type", "element-type", "key-type", "value-type", "order-by"};
   public static final String SUFFIX_JDO = ".jdo";
   public static final String SUFFIX_QUERY = ".jdoquery";
   public static final String SUFFIX_ORM = ".orm";
   public static final String DOCTYPE_DEC_JDO = "<!DOCTYPE jdo SYSTEM 'file:/javax/jdo/jdo.dtd'>";
   public static final String DOCTYPE_DEC_QUERY = "<!DOCTYPE jdoquery SYSTEM 'file:/javax/jdo/jdoquery.dtd'>";
   protected static final int ELEM_CLASS = 0;
   protected static final int ELEM_FIELD = 1;
   protected static final int ELEM_SUPER_FIELD = 2;
   protected static final int ELEM_ARRAY = 3;
   protected static final int ELEM_COLLECTION = 4;
   protected static final int ELEM_MAP = 5;
   protected static final int ELEM_EXTENSION = 6;
   protected static final int ELEM_COLUMN = 7;
   protected static final int ELEM_IDENTITY = 8;
   protected static final int ELEM_INHERITANCE = 9;
   protected static final int ELEM_DISCRIMINATOR = 10;
   protected static final int ELEM_VERSION = 11;
   protected static final int ELEM_JOIN = 12;
   protected static final int ELEM_ELEMENT = 13;
   protected static final int ELEM_KEY = 14;
   protected static final int ELEM_VALUE = 15;
   protected static final int ELEM_ORDER = 16;
   protected static final int ELEM_EMBEDDED = 17;
   protected static final int ELEM_INDEX = 18;
   protected static final int ELEM_FOREIGN_KEY = 19;
   protected static final int ELEM_UNIQUE = 20;
   protected static final int ELEM_QUERY = 21;
   protected static final int ELEM_SEQUENCE = 22;
   protected static final int ELEM_FG = 23;
   protected static final int ELEM_INCLUDED_FG = 24;
   protected static final int ELEM_FG_FIELD = 25;
   protected static final int ELEM_IMPLEMENTS = 26;
   protected static final int ELEM_INTERFACE = 27;
   protected static final int ELEM_PROPERTY = 28;
   protected static final int ELEM_UNKNOWN = 99;
   private static final Integer ELEM_UNKNOWN_OBJ;
   private static final Integer ELEM_CLASS_OBJ;
   private static final Integer ELEM_INTERFACE_OBJ;
   private static final Integer ELEM_SUPER_FIELD_OBJ;
   private static final Integer ELEM_INCLUDED_FG_OBJ;
   private static final Integer ELEM_FG_FIELD_OBJ;
   private static final SequenceMetaData.SequenceFactory FACTORY;
   private static final Map _elems = new HashMap();
   private static final Localizer _loc;
   private final OpenJPAConfiguration _conf;
   private final EntityResolver _resolver;
   private MetaDataRepository _repos = null;
   private ClassLoader _envLoader = null;
   private int _mode = 0;
   private boolean _trackResults = false;
   private boolean _override = false;
   private final Stack _elements = new Stack();
   private final Stack _parents = new Stack();
   private final Stack _exts = new Stack();
   private Class _cls = null;
   private Class _iface = null;
   private int _fieldPos = 0;
   private int _clsPos = 0;
   private FetchGroup _fg = null;
   private boolean _useSchema = false;

   public JDOMetaDataParser(OpenJPAConfiguration conf) {
      this._conf = conf;
      this._resolver = this.newResolver();
      this.setLog(conf.getLog("openjpa.MetaData"));
      this.setParseComments(true);
      this.setMode(7);
   }

   protected boolean isClassElementName(String name) {
      return super.isClassElementName(name) || "interface".equals(name);
   }

   protected EntityResolver newResolver() {
      return new JDOEntityResolver();
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public MetaDataRepository getRepository() {
      if (this._repos == null) {
         MetaDataRepository repos = this._conf.newMetaDataRepositoryInstance();
         MetaDataFactory mdf = repos.getMetaDataFactory();
         if (mdf instanceof DelegatingMetaDataFactory) {
            mdf = ((DelegatingMetaDataFactory)mdf).getInnermostDelegate();
         }

         if (mdf instanceof JDOMetaDataFactory) {
            ((JDOMetaDataFactory)mdf).setParser(this);
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

   public boolean getTrackResults() {
      return this._trackResults;
   }

   public void setTrackResults(boolean track) {
      this._trackResults = track;
   }

   public boolean getMappingOverride() {
      return this._override;
   }

   public void setMappingOverride(boolean override) {
      this._override = override;
   }

   public boolean useSchemaValidation() {
      return this._useSchema;
   }

   public void setUseSchemaValidation(boolean useSchema) {
      this._useSchema = useSchema;
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
      if ((this._mode & 1) != 0) {
         this.setSuffix(".jdo");
      } else if ((this._mode & 2) != 0) {
         this.setSuffix(".orm");
      } else if ((this._mode & 4) != 0) {
         this.setSuffix(".jdoquery");
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

   protected Integer currentParentCode() {
      return this._parents.isEmpty() ? ELEM_UNKNOWN_OBJ : (Integer)this._parents.peek();
   }

   protected Integer getElementCode(String name) {
      Integer code = (Integer)_elems.get(name);
      return code == null ? ELEM_UNKNOWN_OBJ : code;
   }

   protected boolean isRuntime() {
      return (this.getRepository().getValidate() & 8) != 0;
   }

   protected void reset() {
      super.reset();
      this._elements.clear();
      this._parents.clear();
      this._cls = null;
      this._fieldPos = 0;
      this._clsPos = 0;
      this._fg = null;
   }

   protected Object getSchemaSource() {
      if (!this._useSchema) {
         return null;
      } else if (this.isMetaDataMode() && JDOEntityResolver.JdoXSD.value != null) {
         return new InputSource(new StringReader(JDOEntityResolver.JdoXSD.value));
      } else if (this.isMappingMode() && JDOEntityResolver.OrmXSD.value != null) {
         return new InputSource(new StringReader(JDOEntityResolver.OrmXSD.value));
      } else {
         return this.isQueryMode() && JDOEntityResolver.JdoqueryXSD.value != null ? new InputSource(new StringReader(JDOEntityResolver.JdoqueryXSD.value)) : null;
      }
   }

   protected Reader getDocType() throws IOException {
      if (this.isMetaDataMode()) {
         return new StringReader("<!DOCTYPE jdo SYSTEM 'file:/javax/jdo/jdo.dtd'>");
      } else {
         return !this.isMappingMode() && this.isQueryMode() ? new StringReader("<!DOCTYPE jdoquery SYSTEM 'file:/javax/jdo/jdoquery.dtd'>") : null;
      }
   }

   public InputSource resolveEntity(String pub, String sys) throws SAXException {
      try {
         InputSource source = this._resolver.resolveEntity(pub, sys);
         return source == null ? super.resolveEntity(pub, sys) : source;
      } catch (SAXException var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new SAXException(var6);
      }
   }

   protected boolean startSystemElement(String name, Attributes attrs) throws SAXException {
      return this.startClassElement(name, attrs);
   }

   protected void endSystemElement(String name) throws SAXException {
      this.endClassElement(name);
   }

   protected boolean startPackageElement(String name, Attributes attrs) throws SAXException {
      return this.startClassElement(name, attrs);
   }

   protected void endPackageElement(String name) throws SAXException {
      this.endClassElement(name);
   }

   protected boolean startClass(String elem, Attributes attrs) throws SAXException {
      super.startClass(elem, attrs);
      boolean intf = "interface".equals(elem);
      this._cls = this.classForName(this.currentClassName(), this.isRuntime());
      MetaDataRepository repos = this.getRepository();
      NonPersistentMetaData paClass;
      if (intf && this.isMetaDataMode() && attrs.getLength() <= 1) {
         paClass = repos.addNonMappedInterface(this._cls);
         if (this.isMetaDataMode()) {
            paClass.setSource(this.getSourceFile(), 2);
            paClass.setListingIndex(this._clsPos);
         }

         return false;
      } else if ("persistence-aware".equals(attrs.getValue("persistence-modifier"))) {
         paClass = repos.addPersistenceAware(this._cls);
         if (this.isMetaDataMode()) {
            paClass.setSource(this.getSourceFile(), 2);
            paClass.setListingIndex(this._clsPos);
         }

         return false;
      } else {
         if (intf) {
            this._parents.push(ELEM_INTERFACE_OBJ);
         } else {
            this._parents.push(ELEM_CLASS_OBJ);
         }

         if (this._mode == 4) {
            return true;
         } else {
            Log log = this.getLog();
            if (log.isTraceEnabled()) {
               log.trace(_loc.get("parse-class", this._cls.getName()));
            }

            ClassMetaData meta = repos.getCachedMetaData(this._cls);
            if (meta != null && (this.isMetaDataMode() && (meta.getSourceMode() & 1) != 0 || this.isMappingMode() && (meta.getSourceMode() & 2) != 0)) {
               if (log.isWarnEnabled()) {
                  log.warn(_loc.get("dup-metadata", this._cls, this.getSourceName()));
               }

               this._cls = null;
               this._parents.pop();
               return false;
            } else {
               if (meta == null) {
                  meta = repos.addMetaData(this._cls, intf ? 4 : 0);
                  meta.setEnvClassLoader(this._envLoader);
                  meta.setSourceMode(0);
               }

               if (this.isMetaDataMode()) {
                  meta.setSource(this.getSourceFile(), 2);
                  meta.setSourceMode(1, true);
                  meta.setListingIndex(this._clsPos);
                  InstanceCallbacksAdapter.install(meta);
                  if (attrs.getValue("persistence-capable-superclass") != null && log.isInfoEnabled()) {
                     log.info(_loc.get("pc-super-deprecated", meta));
                  }

                  String val = attrs.getValue("objectid-class");
                  if (val != null) {
                     meta.setObjectIdType(this.classForName(val, this.isRuntime()), false);
                  }

                  val = attrs.getValue("requires-extent");
                  if (val != null) {
                     meta.setRequiresExtent(!"false".equals(val));
                  }

                  val = attrs.getValue("embedded-only");
                  if (val != null) {
                     meta.setEmbeddedOnly("true".equals(val));
                  }

                  val = attrs.getValue("identity-type");
                  if ("application".equals(val)) {
                     meta.setIdentityType(2);
                  } else if ("datastore".equals(val)) {
                     meta.setIdentityType(1);
                  } else if (val != null && val.length() != 0) {
                     throw new UnsupportedException(_loc.get("nondurableid-not-supported", meta));
                  }

                  val = attrs.getValue("detachable");
                  if (val != null) {
                     meta.setDetachable("true".equals(val));
                  }

                  if (intf) {
                     if (!this._cls.isInterface()) {
                        throw this.getException(_loc.get("notinterface-meta", this._cls));
                     }

                     meta.setManagedInterface(true);
                  } else if (this._cls.isInterface()) {
                     throw this.getException(_loc.get("interface-meta", this._cls));
                  }
               }

               if (this.isMappingMode()) {
                  meta.setSourceMode(2, true);
               }

               if (this.isMappingOverrideMode()) {
                  this.startClassMapping(meta, attrs);
               }

               ++this._clsPos;
               this._fieldPos = 0;
               this.addComments(meta);
               this.pushElement(meta);
               return true;
            }
         }
      }
   }

   protected void endClass(String elem) throws SAXException {
      ClassMetaData meta = null;
      if (this.isMetaDataMode() || this.isMappingMode()) {
         meta = (ClassMetaData)this.popElement();
      }

      this._parents.pop();
      this._cls = null;
      if (meta != null && this.isMappingOverrideMode()) {
         this.endClassMapping(meta);
      }

      if (this._trackResults && meta != null) {
         this.addResult(meta);
      }

      super.endClass(elem);
   }

   protected void startClassMapping(ClassMetaData mapping, Attributes attrs) throws SAXException {
   }

   protected void endClassMapping(ClassMetaData mapping) throws SAXException {
   }

   protected boolean startClassElement(String name, Attributes attrs) throws SAXException {
      Integer code = this.getElementCode(name);
      int parent = this.currentParentCode();
      if (parent == 6 && code != 6) {
         return false;
      } else {
         boolean ret = false;
         String field;
         switch (code) {
            case 1:
               field = attrs.getValue("name");
               if (parent == 23) {
                  code = ELEM_FG_FIELD_OBJ;
                  ret = this.isMetaDataMode() && this.startFetchGroupField(field, attrs);
               } else if (field.indexOf(46) != -1) {
                  code = ELEM_SUPER_FIELD_OBJ;
                  ret = this.startSuperclassField(field, attrs);
               } else {
                  ret = this.startField(field, attrs);
               }
               break;
            case 2:
            case 24:
            case 25:
            case 27:
            default:
               throw this.getException(_loc.get("bad-element", name));
            case 3:
               ret = this.isMetaDataMode() && this.startArray(attrs);
               break;
            case 4:
               ret = this.isMetaDataMode() && this.startCollection(attrs);
               break;
            case 5:
               ret = this.isMetaDataMode() && this.startMap(attrs);
               break;
            case 6:
               ret = this.startExtension(attrs);
               break;
            case 7:
               ret = this.isMappingOverrideMode() && this.startColumn(attrs);
               break;
            case 8:
               ret = this.isMappingOverrideMode() && this.startDatastoreIdentity(attrs);
               break;
            case 9:
               ret = this.isMappingOverrideMode() && this.startInheritance(attrs);
               break;
            case 10:
               ret = this.isMappingOverrideMode() && this.startDiscriminator(attrs);
               break;
            case 11:
               ret = this.isMappingOverrideMode() && this.startVersion(attrs);
               break;
            case 12:
               ret = this.isMappingOverrideMode() && this.startJoin(attrs);
               break;
            case 13:
               ret = this.isMappingOverrideMode() && this.startCollectionElement(attrs);
               break;
            case 14:
               ret = this.isMappingOverrideMode() && this.startMapKey(attrs);
               break;
            case 15:
               ret = this.isMappingOverrideMode() && this.startMapValue(attrs);
               break;
            case 16:
               ret = this.isMappingOverrideMode() && this.startOrder(attrs);
               break;
            case 17:
               ret = this.startEmbedded(attrs);
               break;
            case 18:
               ret = this.isMappingOverrideMode() && this.startIndex(attrs);
               break;
            case 19:
               ret = this.isMappingOverrideMode() && this.startForeignKey(attrs);
               break;
            case 20:
               ret = this.isMappingOverrideMode() && this.startUnique(attrs);
               break;
            case 21:
               ret = this.isQueryMode() && this.startQuery(attrs);
               break;
            case 22:
               ret = this.isMappingOverrideMode() && this.startSequence(attrs);
               break;
            case 23:
               if (parent == 23) {
                  code = ELEM_INCLUDED_FG_OBJ;
                  ret = this.isMetaDataMode() && this.startIncludedFetchGroup(attrs);
               } else {
                  ret = this.isMetaDataMode() && this.startFetchGroup(attrs);
               }
               break;
            case 26:
               ret = this.isMetaDataMode() && this.startImplements(attrs);
               break;
            case 28:
               field = attrs.getValue("name");
               if (parent == 26) {
                  ret = this.isMetaDataMode() && this.startImplProperty(field, attrs);
               } else {
                  ret = this.startField(field, attrs);
               }
         }

         if (ret) {
            this._parents.push(code);
         }

         return ret;
      }
   }

   protected void endClassElement(String name) throws SAXException {
      int code = (Integer)this._parents.pop();
      int parent = this.currentParentCode();
      switch (code) {
         case 1:
         case 28:
            if (parent == 26) {
               this.endImplProperty();
            } else {
               this.endField();
            }
            break;
         case 2:
            this.endSuperclassField();
         case 3:
         case 4:
         case 5:
         case 24:
         case 25:
            break;
         case 6:
            this.endExtension();
            break;
         case 7:
            this.endColumn();
            break;
         case 8:
            this.endDatastoreIdentity();
            break;
         case 9:
            this.endInheritance();
            break;
         case 10:
            this.endDiscriminator();
            break;
         case 11:
            this.endVersion();
            break;
         case 12:
            this.endJoin();
            break;
         case 13:
            this.endCollectionElement();
            break;
         case 14:
            this.endMapKey();
            break;
         case 15:
            this.endMapValue();
            break;
         case 16:
            this.endOrder();
            break;
         case 17:
            this.endEmbedded();
            break;
         case 18:
            this.endIndex();
            break;
         case 19:
            this.endForeignKey();
            break;
         case 20:
            this.endUnique();
            break;
         case 21:
            this.endQuery();
            break;
         case 22:
            this.endSequence();
            break;
         case 23:
            this.endFetchGroup();
            break;
         case 26:
            this.endImplements();
            break;
         case 27:
         default:
            throw this.getException(_loc.get("bad-element", name));
      }

   }

   private boolean startSuperclassField(String name, Attributes attrs) throws SAXException {
      ClassMetaData meta = (ClassMetaData)this.currentElement();
      String supName = Strings.getPackageName(name);
      Class sup = this.classForName(supName, this.isRuntime());
      if (meta.getDescribedType() != Object.class && !sup.isAssignableFrom(meta.getDescribedType())) {
         throw this.getException(_loc.get("not-supclass-field", name, meta.getDescribedType(), supName));
      } else {
         name = Strings.getClassName(name);
         FieldMetaData field = meta.getDefinedSuperclassField(name);
         if (field == null || field.getDeclaredType() == Object.class) {
            Field f = null;

            try {
               f = sup.getDeclaredField(name);
            } catch (Exception var9) {
               throw this.getException(_loc.get("invalid-field", name, sup), var9);
            }

            if (field == null) {
               field = meta.addDefinedSuperclassField(name, f.getType(), sup);
            }

            field.backingMember(f);
         }

         String val = attrs.getValue("serialized");
         if (val != null) {
            field.setSerialized("true".equals(val));
         }

         if (this.isMetaDataMode()) {
            val = attrs.getValue("default-fetch-group");
            if (val != null) {
               field.setInDefaultFetchGroup("true".equals(val));
            }

            val = attrs.getValue("load-fetch-group");
            if (val != null) {
               field.setLoadFetchGroup(val);
            }

            val = attrs.getValue("field-type");
            if (val != null) {
               field.setTypeOverride(this.classForName(val, this.isRuntime()));
            }

            field.setListingIndex(this._fieldPos);
         }

         if (this.isMappingOverrideMode()) {
            val = attrs.getValue("mapped-by");
            if (val != null) {
               field.setMappedBy(val);
            }

            val = attrs.getValue("value-strategy");
            if (val != null) {
               if ("version".equals(val)) {
                  field.setVersion(true);
               } else {
                  field.setVersion(false);
                  field.setValueStrategy(ValueStrategies.getCode(val, field));
               }
            }

            val = attrs.getValue("sequence");
            if (val != null) {
               field.setValueSequenceName(val);
            }

            this.startSuperclassFieldMapping(field, attrs);
         }

         ++this._fieldPos;
         this.addComments(field);
         this.pushElement(field);
         return true;
      }
   }

   private void endSuperclassField() throws SAXException {
      FieldMetaData field = (FieldMetaData)this.popElement();
      if (this.isMappingOverrideMode()) {
         this.endSuperclassFieldMapping(field);
      }

   }

   protected void startSuperclassFieldMapping(FieldMetaData field, Attributes attrs) throws SAXException {
   }

   protected void endSuperclassFieldMapping(FieldMetaData field) throws SAXException {
   }

   private boolean startField(String name, Attributes attrs) throws SAXException {
      boolean intf = ELEM_INTERFACE_OBJ == this.currentParentCode();
      ClassMetaData meta = (ClassMetaData)this.currentElement();
      FieldMetaData field = meta.getDeclaredField(name);
      String pers = this.isMetaDataMode() ? attrs.getValue("persistence-modifier") : null;
      if ((field == null || field.getDeclaredType() == Object.class && field.getDeclaredTypeCode() == 8 && field.getBackingMember() == null) && !intf && meta.getDescribedType() != Object.class) {
         Field f = null;

         try {
            f = meta.getDescribedType().getDeclaredField(name);
         } catch (Exception var9) {
            throw this.getException(_loc.get("invalid-field", name, meta), var9);
         }

         if (pers != null && !"none".equals(pers)) {
            int mods = f.getModifiers();
            if (Modifier.isFinal(mods)) {
               throw this.getException(_loc.get("final-field", name, meta));
            }

            if (Modifier.isStatic(mods)) {
               throw this.getException(_loc.get("static-field", name, meta));
            }
         }

         if (field == null) {
            field = meta.addDeclaredField(name, f.getType());
         }

         field.backingMember(f);
      } else if (field == null) {
         if (intf) {
            throw this.getException(_loc.get("invalid-property", name, meta));
         }

         field = meta.addDeclaredField(name, Object.class);
      }

      if (pers != null) {
         field.setExplicit(true);
         if ("none".equals(pers)) {
            field.setManagement(0);
         } else if ("transactional".equals(pers)) {
            field.setManagement(1);
         } else if ("persistent".equals(pers)) {
            field.setManagement(3);
         }
      }

      String val = attrs.getValue("serialized");
      if (val != null) {
         field.setSerialized("true".equals(val));
      }

      if (this.isMetaDataMode()) {
         val = attrs.getValue("primary-key");
         if (val != null) {
            field.setPrimaryKey("true".equals(val));
         }

         val = attrs.getValue("default-fetch-group");
         if (val != null) {
            field.setInDefaultFetchGroup("true".equals(val));
         }

         val = attrs.getValue("load-fetch-group");
         if (val != null) {
            field.setLoadFetchGroup(val);
         }

         val = attrs.getValue("embedded");
         if (val != null) {
            field.setEmbedded("true".equals(val));
         }

         val = attrs.getValue("field-type");
         if (val != null) {
            field.setTypeOverride(this.classForName(val, this.isRuntime()));
         }

         val = attrs.getValue("dependent");
         if (val != null) {
            if ("true".equals(val)) {
               field.setCascadeDelete(2);
            } else {
               field.setCascadeDelete(0);
            }
         }

         val = attrs.getValue("null-value");
         if (val != null) {
            if ("default".equals(val)) {
               field.setNullValue(1);
            } else if ("exception".equals(val)) {
               field.setNullValue(2);
            } else if ("none".equals(val)) {
               field.setNullValue(0);
            }
         }

         field.setListingIndex(this._fieldPos);
      }

      if (this.isMappingOverrideMode()) {
         val = attrs.getValue("mapped-by");
         if (val != null) {
            field.setMappedBy(val);
         }

         val = attrs.getValue("value-strategy");
         if (val != null) {
            if ("version".equals(val)) {
               field.setVersion(true);
            } else {
               field.setVersion(false);
               field.setValueStrategy(ValueStrategies.getCode(val, field));
            }
         }

         val = attrs.getValue("sequence");
         if (val != null) {
            field.setValueSequenceName(val);
         }

         this.startFieldMapping(field, attrs);
      }

      ++this._fieldPos;
      this.addComments(field);
      this.pushElement(field);
      return true;
   }

   private void endField() throws SAXException {
      FieldMetaData field = (FieldMetaData)this.popElement();
      if (this.isMappingOverrideMode()) {
         this.endFieldMapping(field);
      }

   }

   protected void startFieldMapping(FieldMetaData field, Attributes attrs) throws SAXException {
   }

   protected void endFieldMapping(FieldMetaData field) throws SAXException {
   }

   private boolean startArray(Attributes attrs) throws SAXException {
      FieldMetaData field = (FieldMetaData)this.currentElement();
      if (field.getDeclaredTypeCode() != 11) {
         throw this.getException(_loc.get("bad-array", field));
      } else {
         this.setElementMetaDataAttributes(field.getElement(), attrs);
         return true;
      }
   }

   private void setElementMetaDataAttributes(ValueMetaData elem, Attributes attrs) throws SAXException {
      String val = attrs.getValue("element-type");
      if (val != null) {
         elem.setDeclaredType(this.classForName(val, this.isRuntime()));
      }

      val = attrs.getValue("embedded-element");
      if (val != null) {
         elem.setEmbedded("true".equals(val));
      }

      val = attrs.getValue("serialized-element");
      if (val != null) {
         elem.setSerialized("true".equals(val));
      }

      val = attrs.getValue("dependent-element");
      if (val != null) {
         if ("true".equals(val)) {
            elem.setCascadeDelete(2);
         } else {
            elem.setCascadeDelete(0);
         }
      }

   }

   private boolean startCollection(Attributes attrs) throws SAXException {
      FieldMetaData field = (FieldMetaData)this.currentElement();
      if (field.getDeclaredTypeCode() != 12) {
         throw this.getException(_loc.get("bad-coll", field));
      } else {
         this.setElementMetaDataAttributes(field.getElement(), attrs);
         return true;
      }
   }

   private boolean startMap(Attributes attrs) throws SAXException {
      FieldMetaData field = (FieldMetaData)this.currentElement();
      if (field.getDeclaredTypeCode() != 13) {
         throw this.getException(_loc.get("bad-map", field));
      } else {
         ValueMetaData elem = field.getElement();
         String val = attrs.getValue("value-type");
         if (val != null) {
            elem.setDeclaredType(this.classForName(val, this.isRuntime()));
         }

         val = attrs.getValue("embedded-value");
         if (val != null) {
            elem.setEmbedded("true".equals(val));
         }

         val = attrs.getValue("serialized-value");
         if (val != null) {
            elem.setSerialized("true".equals(val));
         }

         val = attrs.getValue("dependent-value");
         if (val != null) {
            if ("true".equals(val)) {
               elem.setCascadeDelete(2);
            } else {
               elem.setCascadeDelete(0);
            }
         }

         ValueMetaData key = field.getKey();
         val = attrs.getValue("key-type");
         if (val != null) {
            key.setDeclaredType(this.classForName(val, this.isRuntime()));
         }

         val = attrs.getValue("embedded-key");
         if (val != null) {
            key.setEmbedded("true".equals(val));
         }

         val = attrs.getValue("serialized-key");
         if (val != null) {
            key.setSerialized("true".equals(val));
         }

         val = attrs.getValue("dependent-key");
         if (val != null) {
            if ("true".equals(val)) {
               key.setCascadeDelete(2);
            } else {
               key.setCascadeDelete(0);
            }
         }

         return true;
      }
   }

   private boolean startExtension(Attributes attrs) throws SAXException {
      String vendor = attrs.getValue("vendor-name");
      String key = attrs.getValue("key");
      String value = attrs.getValue("value");
      int pcode = this.currentParentCode();
      switch (pcode) {
         case 0:
         case 17:
            ClassMetaData cls = (ClassMetaData)this.currentElement();
            if (!"openjpa".equals(vendor) && !"kodo".equals(vendor) || (!this.isMetaDataMode() || !this.setKnownClassExtension(cls, key, value)) && (!this.isMappingOverrideMode() || !this.setKnownClassMappingExtension(cls, key, value))) {
               cls.addExtension(vendor, key, value);
            }
            break;
         case 1:
            FieldMetaData field = (FieldMetaData)this.currentElement();
            if (!"openjpa".equals(vendor) && !"kodo".equals(vendor) || (!this.isMetaDataMode() || !this.setKnownFieldExtension(field, key, value)) && (!this.isMappingOverrideMode() || !this.setKnownFieldMappingExtension(field, key, value))) {
               field.addExtension(vendor, key, value);
            }
            break;
         case 6:
            String pkey = (String)this._exts.peek();
            String pvendor = (String)this._exts.get(this._exts.size() - 2);
            Extensions embed = ((Extensions)this.currentElement()).getEmbeddedExtensions(pvendor, pkey, true);
            embed.addExtension(vendor, key, value);
            this.pushElement(embed);
            break;
         case 21:
            if ("openjpa".equals(vendor) || "kodo".equals(vendor)) {
               ((QueryMetaData)this.currentElement()).addHint(key, value);
            }

            return false;
         default:
            if (!"openjpa".equals(vendor) && !"kodo".equals(vendor) || !this.isMappingOverrideMode() || !this.setKnownMappingExtension(pcode, key, value)) {
               return false;
            }
      }

      this._exts.push(vendor);
      this._exts.push(key);
      return true;
   }

   private void endExtension() {
      this._exts.pop();
      this._exts.pop();
      if (this.currentParentCode() == 6) {
         this.popElement();
      }

   }

   protected boolean setKnownMappingExtension(int elemCode, String key, String value) {
      return false;
   }

   protected boolean setKnownClassExtension(ClassMetaData cls, String key, String value) throws SAXException {
      if ("data-cache".equals(key)) {
         if ("false".equals(value)) {
            cls.setDataCacheName((String)null);
         } else if ("true".equals(value)) {
            cls.setDataCacheName("default");
         } else {
            cls.setDataCacheName(value);
         }
      } else if ("data-cache-timeout".equals(key)) {
         cls.setDataCacheTimeout(value == null ? -1 : Integer.parseInt(value));
      } else {
         if (!"detached-state-field".equals(key)) {
            return false;
         }

         if ("false".equals(value)) {
            cls.setDetachedState((String)null);
         } else {
            cls.setDetachedState(value);
         }
      }

      return true;
   }

   protected boolean setKnownClassMappingExtension(ClassMetaData meta, String key, String value) throws SAXException {
      return false;
   }

   protected boolean setKnownFieldExtension(FieldMetaData field, String key, String value) throws SAXException {
      if ("inverse-logical".equals(key)) {
         field.setInverse(value);
      } else if ("lrs".equals(key)) {
         field.setLRS(!"false".equals(value));
      } else if ("external-values".equals(key)) {
         field.setExternalValues(value);
      } else if ("externalizer".equals(key)) {
         field.setExternalizer(value);
      } else if ("factory".equals(key)) {
         field.setFactory(value);
      } else if ("read-only".equals(key)) {
         field.setUpdateStrategy(value != null && value.length() != 0 ? UpdateStrategies.getCode(value, field) : 2);
      } else if ("type".equals(key)) {
         field.setTypeOverride(this.classForName(value, this.isRuntime()));
      } else if ("key-type".equals(key)) {
         field.getKey().setTypeOverride(this.classForName(value, this.isRuntime()));
      } else if (!"element-type".equals(key) && !"value-type".equals(key)) {
         if (!"order-by".equals(key)) {
            return false;
         }

         field.setOrderDeclaration(value);
      } else {
         field.getElement().setTypeOverride(this.classForName(value, this.isRuntime()));
      }

      return true;
   }

   protected boolean setKnownFieldMappingExtension(FieldMetaData field, String key, String value) throws SAXException {
      return false;
   }

   protected boolean startColumn(Attributes attrs) throws SAXException {
      return false;
   }

   protected void endColumn() throws SAXException {
   }

   protected boolean startDatastoreIdentity(Attributes attrs) throws SAXException {
      ClassMetaData meta = (ClassMetaData)this.currentElement();
      String val = attrs.getValue("strategy");
      if (val != null) {
         meta.setIdentityStrategy(ValueStrategies.getCode(val, meta));
      }

      val = attrs.getValue("sequence");
      if (val != null) {
         meta.setIdentitySequenceName(val);
      }

      return true;
   }

   protected void endDatastoreIdentity() throws SAXException {
   }

   protected boolean startInheritance(Attributes attrs) throws SAXException {
      return false;
   }

   protected void endInheritance() throws SAXException {
   }

   protected boolean startDiscriminator(Attributes attrs) throws SAXException {
      return false;
   }

   protected void endDiscriminator() throws SAXException {
   }

   protected boolean startVersion(Attributes attrs) throws SAXException {
      return false;
   }

   protected void endVersion() throws SAXException {
   }

   protected boolean startJoin(Attributes attrs) throws SAXException {
      return false;
   }

   protected void endJoin() throws SAXException {
   }

   protected boolean startCollectionElement(Attributes attrs) throws SAXException {
      ValueMetaData vmd = ((FieldMetaData)this.currentElement()).getElement();
      this.pushElement(vmd);
      return true;
   }

   protected void endCollectionElement() throws SAXException {
      this.popElement();
   }

   protected boolean startMapKey(Attributes attrs) throws SAXException {
      ValueMetaData vmd = ((FieldMetaData)this.currentElement()).getKey();
      String val = attrs.getValue("mapped-by");
      if (val != null) {
         vmd.setValueMappedBy(val);
      }

      this.pushElement(vmd);
      return true;
   }

   protected void endMapKey() throws SAXException {
      this.popElement();
   }

   protected boolean startMapValue(Attributes attrs) throws SAXException {
      ValueMetaData vmd = ((FieldMetaData)this.currentElement()).getElement();
      this.pushElement(vmd);
      return true;
   }

   protected void endMapValue() throws SAXException {
      this.popElement();
   }

   protected boolean startOrder(Attributes attrs) throws SAXException {
      return false;
   }

   protected void endOrder() throws SAXException {
   }

   private boolean startEmbedded(Attributes attrs) throws SAXException {
      ValueMetaData vmd = (ValueMetaData)this.currentElement();
      ClassMetaData embed = vmd.getEmbeddedMetaData();
      if (embed == null) {
         embed = vmd.addEmbeddedMetaData();
      }

      this.pushElement(embed);
      if (this.isMappingOverrideMode()) {
         this.startEmbeddedMapping(embed, attrs);
      }

      return true;
   }

   private void endEmbedded() throws SAXException {
      ClassMetaData embed = (ClassMetaData)this.popElement();
      if (this.isMappingOverrideMode()) {
         this.endEmbeddedMapping(embed);
      }

   }

   protected void startEmbeddedMapping(ClassMetaData embed, Attributes attrs) throws SAXException {
   }

   protected void endEmbeddedMapping(ClassMetaData embed) throws SAXException {
   }

   protected boolean startIndex(Attributes attrs) throws SAXException {
      return false;
   }

   protected void endIndex() throws SAXException {
   }

   protected boolean startForeignKey(Attributes attrs) throws SAXException {
      return false;
   }

   protected void endForeignKey() throws SAXException {
   }

   protected boolean startUnique(Attributes attrs) throws SAXException {
      return false;
   }

   protected void endUnique() throws SAXException {
   }

   private boolean startQuery(Attributes attrs) throws SAXException {
      this.setParseText(true);
      String name = attrs.getValue("name");
      Log log = this.getLog();
      if (log.isInfoEnabled()) {
         if (this._cls != null) {
            log.info(_loc.get("parse-cls-query", this._cls, name));
         } else {
            log.info(_loc.get("parse-query", name));
         }
      }

      QueryMetaData meta = this.getRepository().getCachedQueryMetaData(this._cls, name);
      if (meta != null) {
         if (log.isWarnEnabled()) {
            log.warn(_loc.get("dup-query", name, this._cls, this.getSourceName()));
         }

         return false;
      } else {
         meta = this.getRepository().addQueryMetaData(this._cls, name);
         meta.setSource(this.getSourceFile(), this._cls, 2);
         meta.setCandidateType(this._cls);
         if (this.isMetaDataMode()) {
            meta.setSourceMode(1);
         } else if (this.isMappingMode()) {
            meta.setSourceMode(2);
         } else {
            meta.setSourceMode(4);
         }

         String val = attrs.getValue("language");
         if ("javax.jdo.query.SQL".equals(val)) {
            val = "openjpa.SQL";
         } else if (val == null) {
            val = "javax.jdo.query.JDOQL";
         }

         meta.setLanguage(val);
         val = attrs.getValue("unmodifiable");
         if ("true".equals(val)) {
            meta.setReadOnly(true);
         }

         this.addComments(meta);
         this.pushElement(meta);
         return true;
      }
   }

   private void endQuery() throws SAXException {
      QueryMetaData meta = (QueryMetaData)this.popElement();
      meta.setQueryString(this.currentText());
      if (this._trackResults) {
         this.addResult(meta);
      }

      this.setParseText(false);
   }

   private boolean startSequence(Attributes attrs) throws SAXException {
      String prefix = this.currentPackage();
      String name = attrs.getValue("name");
      if (prefix != null && prefix.length() > 0) {
         name = prefix + "." + name;
      }

      Log log = this.getLog();
      if (log.isInfoEnabled()) {
         log.info(_loc.get("parse-sequence", name));
      }

      SequenceMetaData meta = this.getRepository().getCachedSequenceMetaData(name);
      if (meta != null) {
         if (log.isWarnEnabled()) {
            log.warn(_loc.get("dup-sequence", name, this.getSourceName()));
         }

         return false;
      } else {
         meta = this.getRepository().addSequenceMetaData(name);
         if (this.isMetaDataMode()) {
            meta.setSource(this.getSourceFile(), (Object)null, 2);
         }

         meta.setSequenceFactory(FACTORY);
         String val = attrs.getValue("strategy");
         if ("nontransactional".equals(val)) {
            meta.setType(1);
         } else if ("transactional".equals(val)) {
            meta.setType(2);
         } else if ("contiguous".equals(val)) {
            meta.setType(3);
         }

         String ds = attrs.getValue("datastore-sequence");
         if (ds != null) {
            meta.setSequence(ds);
         }

         val = attrs.getValue("factory-class");
         String props = null;
         String clsName;
         if (val == null) {
            clsName = "native";
         } else {
            clsName = Configurations.getClassName(val);
            props = Configurations.getProperties(val);
            if (clsName == null) {
               clsName = "native";
            } else {
               Class cls = classForName(clsName, this.currentPackage(), this.isRuntime(), this.currentClassLoader());
               if (cls != null) {
                  clsName = cls.getName();
               }
            }
         }

         meta.setSequencePlugin(Configurations.getPlugin(clsName, props));
         this.startSequenceMapping(meta, attrs);
         this.addComments(meta);
         this.pushElement(meta);
         return true;
      }
   }

   private void endSequence() throws SAXException {
      SequenceMetaData seq = (SequenceMetaData)this.popElement();
      this.endSequenceMapping(seq);
      if (this._trackResults) {
         this.addResult(seq);
      }

   }

   protected void startSequenceMapping(SequenceMetaData seq, Attributes attrs) throws SAXException {
   }

   protected void endSequenceMapping(SequenceMetaData seq) throws SAXException {
   }

   private boolean startFetchGroup(Attributes attrs) throws SAXException {
      ClassMetaData meta = (ClassMetaData)this.currentElement();
      this._fg = meta.addDeclaredFetchGroup(attrs.getValue("name"));
      String val = attrs.getValue("post-load");
      if (val != null) {
         this._fg.setPostLoad("true".equals(val));
      }

      return true;
   }

   private void endFetchGroup() throws SAXException {
      this._fg = null;
   }

   private boolean startIncludedFetchGroup(Attributes attrs) throws SAXException {
      this._fg.addDeclaredInclude(attrs.getValue("name"));
      return true;
   }

   private boolean startFetchGroupField(String name, Attributes attrs) throws SAXException {
      ClassMetaData meta = (ClassMetaData)this.currentElement();
      FieldMetaData field = meta.getDeclaredField(name);
      if (field == null) {
         throw this.getException(_loc.get("bad-fg-field", this._fg, name));
      } else {
         String depth = attrs.getValue("recursion-depth");
         if (depth != null) {
            try {
               this._fg.setRecursionDepth(field, Integer.parseInt(depth));
            } catch (NumberFormatException var7) {
               throw this.getException(_loc.get("invalid-fetch-depth", this._fg, depth, field));
            }
         }

         field.setInFetchGroup(this._fg.getName(), true);
         return true;
      }
   }

   protected boolean startImplements(Attributes attrs) throws SAXException {
      ClassMetaData meta = (ClassMetaData)this.currentElement();
      String val = attrs.getValue("name");
      if (val == null) {
         return false;
      } else {
         this._iface = this.classForName(val, this.isRuntime());
         meta.addDeclaredInterface(this._iface);
         this.startImplementsMapping(attrs);
         return true;
      }
   }

   protected void endImplements() throws SAXException {
      this.endImplementsMapping();
      this._iface = null;
   }

   protected void startImplementsMapping(Attributes attrs) throws SAXException {
   }

   protected void endImplementsMapping() throws SAXException {
   }

   protected boolean startImplProperty(String name, Attributes attrs) throws SAXException {
      String local = attrs.getValue("field-name");
      if (local == null) {
         throw this.getException(_loc.get("bad-implements-prop", this._cls, name));
      } else {
         ClassMetaData meta = (ClassMetaData)this.currentElement();
         meta.setInterfacePropertyAlias(this._iface, name, local);
         return true;
      }
   }

   protected void endImplProperty() throws SAXException {
   }

   protected Class getPropertyType(String name) throws SAXException {
      name = StringUtils.capitalize(name);

      try {
         return this._cls.getDeclaredMethod("get" + name, (Class[])null).getReturnType();
      } catch (NoSuchMethodException var4) {
         try {
            Method m = this._cls.getDeclaredMethod("is" + name, (Class[])null);
            if (m.getReturnType() == Boolean.TYPE || m.getReturnType() == Boolean.class) {
               return m.getReturnType();
            }
         } catch (NoSuchMethodException var3) {
         }

         throw this.getException(_loc.get("bad-property", this._cls, name));
      }
   }

   protected Class classForName(String name, boolean resolve) throws SAXException {
      if ("PersistenceCapable".equals(name)) {
         return PersistenceCapable.class;
      } else if ("Object".equals(name)) {
         return Object.class;
      } else if (ByteIdentity.class.getName().equals(name)) {
         return ByteId.class;
      } else if (CharIdentity.class.getName().equals(name)) {
         return CharId.class;
      } else if (IntIdentity.class.getName().equals(name)) {
         return IntId.class;
      } else if (LongIdentity.class.getName().equals(name)) {
         return LongId.class;
      } else if (ShortIdentity.class.getName().equals(name)) {
         return ShortId.class;
      } else if (StringIdentity.class.getName().equals(name)) {
         return StringId.class;
      } else {
         return ObjectIdentity.class.getName().equals(name) ? ObjectId.class : super.classForName(name, resolve);
      }
   }

   static {
      _elems.put("field", Numbers.valueOf(1));
      _elems.put("array", Numbers.valueOf(3));
      _elems.put("collection", Numbers.valueOf(4));
      _elems.put("map", Numbers.valueOf(5));
      _elems.put("extension", Numbers.valueOf(6));
      _elems.put("column", Numbers.valueOf(7));
      _elems.put("datastore-identity", Numbers.valueOf(8));
      _elems.put("inheritance", Numbers.valueOf(9));
      _elems.put("discriminator", Numbers.valueOf(10));
      _elems.put("version", Numbers.valueOf(11));
      _elems.put("join", Numbers.valueOf(12));
      _elems.put("element", Numbers.valueOf(13));
      _elems.put("key", Numbers.valueOf(14));
      _elems.put("value", Numbers.valueOf(15));
      _elems.put("order", Numbers.valueOf(16));
      _elems.put("embedded", Numbers.valueOf(17));
      _elems.put("index", Numbers.valueOf(18));
      _elems.put("foreign-key", Numbers.valueOf(19));
      _elems.put("unique", Numbers.valueOf(20));
      _elems.put("query", Numbers.valueOf(21));
      _elems.put("sequence", Numbers.valueOf(22));
      _elems.put("fetch-group", Numbers.valueOf(23));
      _elems.put("implements", Numbers.valueOf(26));
      _elems.put("property", Numbers.valueOf(28));
      ELEM_UNKNOWN_OBJ = Numbers.valueOf(99);
      ELEM_CLASS_OBJ = Numbers.valueOf(0);
      ELEM_INTERFACE_OBJ = Numbers.valueOf(27);
      ELEM_SUPER_FIELD_OBJ = Numbers.valueOf(2);
      ELEM_FG_FIELD_OBJ = Numbers.valueOf(25);
      ELEM_INCLUDED_FG_OBJ = Numbers.valueOf(24);
      FACTORY = new SequenceMetaData.SequenceFactory() {
         public Seq toSequence(Class cls, String props) throws Exception {
            Method newInst = cls.getMethod("newInstance", (Class[])null);
            Object obj = newInst.invoke((Object)null, (Object[])null);
            return new SeqAdapter((Sequence)obj);
         }
      };
      _loc = Localizer.forPackage(JDOMetaDataParser.class);
   }
}
