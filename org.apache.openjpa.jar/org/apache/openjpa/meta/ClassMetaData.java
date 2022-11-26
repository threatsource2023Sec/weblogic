package org.apache.openjpa.meta;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.datacache.DataCache;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.lib.conf.Value;
import org.apache.openjpa.lib.conf.ValueListener;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.xml.Commentable;
import org.apache.openjpa.util.BigDecimalId;
import org.apache.openjpa.util.BigIntegerId;
import org.apache.openjpa.util.ByteId;
import org.apache.openjpa.util.CharId;
import org.apache.openjpa.util.DateId;
import org.apache.openjpa.util.DoubleId;
import org.apache.openjpa.util.FloatId;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.IntId;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.LongId;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.ObjectId;
import org.apache.openjpa.util.OpenJPAId;
import org.apache.openjpa.util.ShortId;
import org.apache.openjpa.util.StringId;
import org.apache.openjpa.util.UnsupportedException;
import serp.util.Strings;

public class ClassMetaData extends Extensions implements Comparable, SourceTracker, MetaDataContext, MetaDataModes, Commentable, ValueListener {
   public static final int ID_UNKNOWN = 0;
   public static final int ID_DATASTORE = 1;
   public static final int ID_APPLICATION = 2;
   public static final int ACCESS_UNKNOWN = 0;
   public static final int ACCESS_FIELD = 2;
   public static final int ACCESS_PROPERTY = 4;
   public static final String SYNTHETIC = "`syn";
   protected static final String DEFAULT_STRING = "`";
   private static final Localizer _loc = Localizer.forPackage(ClassMetaData.class);
   private static final FetchGroup[] EMPTY_FETCH_GROUP_ARRAY = new FetchGroup[0];
   private static final String[] EMPTY_STRING_ARRAY = new String[0];
   private MetaDataRepository _repos;
   private transient ClassLoader _loader = null;
   private final ValueMetaData _owner;
   private final LifecycleMetaData _lifeMeta = new LifecycleMetaData(this);
   private File _srcFile = null;
   private int _srcType = 0;
   private String[] _comments = null;
   private int _listIndex = -1;
   private int _srcMode = 3;
   private int _resMode = 0;
   private Class _type = Object.class;
   private final Map _fieldMap = new TreeMap();
   private Map _supFieldMap = null;
   private boolean _defSupFields = false;
   private Collection _staticFields = null;
   private int[] _fieldDataTable = null;
   private Map _fgMap = null;
   private Class _objectId = null;
   private Boolean _objectIdShared = null;
   private Boolean _openjpaId = null;
   private Boolean _extent = null;
   private Boolean _embedded = null;
   private Boolean _interface = null;
   private Class _impl = null;
   private List _interfaces = null;
   private final Map _ifaceMap = new HashMap();
   private int _identity = 0;
   private int _idStrategy = 0;
   private int _accessType = 0;
   private String _seqName = "`";
   private SequenceMetaData _seqMeta = null;
   private String _cacheName = "`";
   private int _cacheTimeout = Integer.MIN_VALUE;
   private Boolean _detachable = null;
   private String _detachState = "`";
   private String _alias = null;
   private int _versionIdx = Integer.MIN_VALUE;
   private Class _super = null;
   private ClassMetaData _superMeta = null;
   private Class[] _subs = null;
   private ClassMetaData[] _subMetas = null;
   private ClassMetaData[] _mapSubMetas = null;
   private FieldMetaData[] _fields = null;
   private FieldMetaData[] _unmgdFields = null;
   private FieldMetaData[] _allFields = null;
   private FieldMetaData[] _allPKFields = null;
   private FieldMetaData[] _allDFGFields = null;
   private FieldMetaData[] _definedFields = null;
   private FieldMetaData[] _listingFields = null;
   private FieldMetaData[] _allListingFields = null;
   private FetchGroup[] _fgs = null;
   private FetchGroup[] _customFGs = null;
   private boolean _intercepting = false;
   private boolean _abstract = false;

   protected ClassMetaData(Class type, MetaDataRepository repos) {
      this._repos = repos;
      this._owner = null;
      this.setDescribedType(type);
      this.registerForValueUpdate("DataCacheTimeout");
   }

   protected ClassMetaData(ValueMetaData owner) {
      this._owner = owner;
      this._repos = owner.getRepository();
      this.setEnvClassLoader(owner.getFieldMetaData().getDefiningMetaData().getEnvClassLoader());
      this.registerForValueUpdate("DataCacheTimeout");
   }

   public MetaDataRepository getRepository() {
      return this._repos;
   }

   public ValueMetaData getEmbeddingMetaData() {
      return this._owner;
   }

   public Class getDescribedType() {
      return this._type;
   }

   protected void setDescribedType(Class type) {
      if (type.getSuperclass() != null && "java.lang.Enum".equals(type.getSuperclass().getName())) {
         throw new MetaDataException(_loc.get("enum", (Object)type));
      } else {
         this._type = type;
         if (PersistenceCapable.class.isAssignableFrom(type)) {
            this.setIntercepting(true);
         }

      }
   }

   public ClassLoader getEnvClassLoader() {
      return this._loader;
   }

   public void setEnvClassLoader(ClassLoader loader) {
      this._loader = loader;
   }

   public Class getPCSuperclass() {
      return this._super;
   }

   public void setPCSuperclass(Class pc) {
      this.clearAllFieldCache();
      this._super = pc;
   }

   public ClassMetaData getPCSuperclassMetaData() {
      if (this._superMeta == null && this._super != null) {
         if (this._owner != null) {
            this._superMeta = this._repos.newEmbeddedClassMetaData(this._owner);
            this._superMeta.setDescribedType(this._super);
         } else {
            this._superMeta = this._repos.getMetaData(this._super, this._loader, true);
         }
      }

      return this._superMeta;
   }

   public void setPCSuperclassMetaData(ClassMetaData meta) {
      this.clearAllFieldCache();
      this._superMeta = meta;
      if (meta != null) {
         this.setPCSuperclass(meta.getDescribedType());
      }

   }

   public boolean isMapped() {
      return this._embedded != Boolean.TRUE;
   }

   public ClassMetaData getMappedPCSuperclassMetaData() {
      ClassMetaData sup = this.getPCSuperclassMetaData();
      return sup != null && !sup.isMapped() ? sup.getMappedPCSuperclassMetaData() : sup;
   }

   public Class[] getPCSubclasses() {
      if (this._owner != null) {
         MetaDataRepository var10000 = this._repos;
         return MetaDataRepository.EMPTY_CLASSES;
      } else {
         this._repos.processRegisteredClasses(this._loader);
         if (this._subs == null) {
            Collection subs = this._repos.getPCSubclasses(this._type);
            this._subs = (Class[])((Class[])subs.toArray(new Class[subs.size()]));
         }

         return this._subs;
      }
   }

   public ClassMetaData[] getPCSubclassMetaDatas() {
      if (this._owner != null) {
         return this._repos.EMPTY_METAS;
      } else {
         Class[] subs = this.getPCSubclasses();
         if (this._subMetas == null) {
            if (subs.length == 0) {
               this._subMetas = this._repos.EMPTY_METAS;
            } else {
               ClassMetaData[] metas = this._repos.newClassMetaDataArray(subs.length);

               for(int i = 0; i < subs.length; ++i) {
                  metas[i] = this._repos.getMetaData(subs[i], this._loader, true);
               }

               this._subMetas = metas;
            }
         }

         return this._subMetas;
      }
   }

   public ClassMetaData[] getMappedPCSubclassMetaDatas() {
      if (this._owner != null) {
         return this._repos.EMPTY_METAS;
      } else {
         ClassMetaData[] subs = this.getPCSubclassMetaDatas();
         if (this._mapSubMetas == null) {
            if (subs.length == 0) {
               this._mapSubMetas = subs;
            } else {
               List mapped = new ArrayList(subs.length);

               for(int i = 0; i < subs.length; ++i) {
                  if (subs[i].isMapped()) {
                     mapped.add(subs[i]);
                  }
               }

               this._mapSubMetas = (ClassMetaData[])((ClassMetaData[])mapped.toArray(this._repos.newClassMetaDataArray(mapped.size())));
            }
         }

         return this._mapSubMetas;
      }
   }

   public int getIdentityType() {
      if (this._identity == 0) {
         ClassMetaData sup = this.getPCSuperclassMetaData();
         if (sup != null && sup.getIdentityType() != 0) {
            this._identity = sup.getIdentityType();
         } else if (this.getPrimaryKeyFields().length > 0) {
            this._identity = 2;
         } else if (this.isMapped()) {
            this._identity = 1;
         } else {
            this._identity = this._repos.getMetaDataFactory().getDefaults().getDefaultIdentityType();
         }
      }

      return this._identity;
   }

   public void setIdentityType(int type) {
      this._identity = type;
      if (type != 2) {
         this._objectId = null;
         this._openjpaId = null;
      }

   }

   public Class getObjectIdType() {
      if (this._objectId != null) {
         return this._objectId;
      } else if (this.getIdentityType() != 2) {
         return null;
      } else {
         ClassMetaData sup = this.getPCSuperclassMetaData();
         if (sup != null && sup.getIdentityType() != 0) {
            this._objectId = sup.getObjectIdType();
            return this._objectId;
         } else {
            FieldMetaData[] pks = this.getPrimaryKeyFields();
            if (pks.length != 1) {
               return null;
            } else {
               switch (pks[0].getObjectIdFieldTypeCode()) {
                  case 1:
                  case 17:
                     this._objectId = ByteId.class;
                     break;
                  case 2:
                  case 18:
                     this._objectId = CharId.class;
                     break;
                  case 3:
                  case 19:
                     this._objectId = DoubleId.class;
                     break;
                  case 4:
                  case 20:
                     this._objectId = FloatId.class;
                     break;
                  case 5:
                  case 21:
                     this._objectId = IntId.class;
                     break;
                  case 6:
                  case 22:
                     this._objectId = LongId.class;
                     break;
                  case 7:
                  case 23:
                     this._objectId = ShortId.class;
                     break;
                  case 8:
                  case 29:
                     this._objectId = ObjectId.class;
                     break;
                  case 9:
                     this._objectId = StringId.class;
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 15:
                  case 16:
                  case 26:
                  case 27:
                  case 28:
                  default:
                     break;
                  case 14:
                     this._objectId = DateId.class;
                     break;
                  case 24:
                     this._objectId = BigDecimalId.class;
                     break;
                  case 25:
                     this._objectId = BigIntegerId.class;
               }

               return this._objectId;
            }
         }
      }
   }

   public void setObjectIdType(Class cls, boolean shared) {
      this._objectId = null;
      this._openjpaId = null;
      this._objectIdShared = null;
      if (cls != null) {
         this.setIdentityType(2);
         if (!OpenJPAId.class.isAssignableFrom(cls)) {
            this._objectId = cls;
            this._objectIdShared = shared ? Boolean.TRUE : Boolean.FALSE;
         }
      }

   }

   public boolean isObjectIdTypeShared() {
      if (this._objectIdShared != null) {
         return this._objectIdShared;
      } else {
         return this._super != null ? this.getPCSuperclassMetaData().isObjectIdTypeShared() : this.isOpenJPAIdentity();
      }
   }

   public boolean isOpenJPAIdentity() {
      if (this._openjpaId == null) {
         Class cls = this.getObjectIdType();
         if (cls == null) {
            return false;
         }

         this._openjpaId = OpenJPAId.class.isAssignableFrom(cls) ? Boolean.TRUE : Boolean.FALSE;
      }

      return this._openjpaId;
   }

   public int getIdentityStrategy() {
      if (this.getIdentityType() == 1 && this._idStrategy == 0) {
         ClassMetaData sup = this.getPCSuperclassMetaData();
         if (sup != null && sup.getIdentityType() != 0) {
            this._idStrategy = sup.getIdentityStrategy();
         } else {
            this._idStrategy = 1;
         }
      }

      return this._idStrategy;
   }

   public void setIdentityStrategy(int strategy) {
      this._idStrategy = strategy;
      if (strategy != 2) {
         this.setIdentitySequenceName((String)null);
      }

   }

   public String getIdentitySequenceName() {
      if ("`".equals(this._seqName)) {
         if (this._super != null) {
            this._seqName = this.getPCSuperclassMetaData().getIdentitySequenceName();
         } else {
            this._seqName = null;
         }
      }

      return this._seqName;
   }

   public void setIdentitySequenceName(String seqName) {
      this._seqName = seqName;
      this._seqMeta = null;
      if (seqName != null) {
         this.setIdentityStrategy(2);
      }

   }

   public SequenceMetaData getIdentitySequenceMetaData() {
      if (this._seqMeta == null && this.getIdentitySequenceName() != null) {
         this._seqMeta = this._repos.getSequenceMetaData(this, this.getIdentitySequenceName(), true);
      }

      return this._seqMeta;
   }

   public LifecycleMetaData getLifecycleMetaData() {
      return this._lifeMeta;
   }

   public String getTypeAlias() {
      if (this._alias == null) {
         this._alias = Strings.getClassName(this._type);
      }

      return this._alias;
   }

   public void setTypeAlias(String alias) {
      this._alias = alias;
   }

   public int getAccessType() {
      if (this._accessType == 0) {
         ClassMetaData sup = this.getPCSuperclassMetaData();
         return sup != null ? sup.getAccessType() : this.getRepository().getMetaDataFactory().getDefaults().getDefaultAccessType();
      } else {
         return this._accessType;
      }
   }

   public void setAccessType(int type) {
      this._accessType = type;
   }

   public boolean getRequiresExtent() {
      if (this._owner == null && !this.isEmbeddedOnly()) {
         if (this._extent == null) {
            ClassMetaData sup = this.getPCSuperclassMetaData();
            if (sup != null) {
               this._extent = sup.getRequiresExtent() ? Boolean.TRUE : Boolean.FALSE;
            } else {
               this._extent = Boolean.TRUE;
            }
         }

         return this._extent;
      } else {
         return false;
      }
   }

   public void setRequiresExtent(boolean req) {
      this._extent = req ? Boolean.TRUE : Boolean.FALSE;
   }

   public boolean isEmbeddedOnly() {
      if (this._embedded == null) {
         ClassMetaData sup = this.getPCSuperclassMetaData();
         if (sup != null) {
            this._embedded = sup.isEmbeddedOnly() ? Boolean.TRUE : Boolean.FALSE;
         } else {
            this._embedded = Boolean.FALSE;
         }
      }

      return this._embedded;
   }

   public void setEmbeddedOnly(boolean embed) {
      this._embedded = embed ? Boolean.TRUE : Boolean.FALSE;
   }

   public boolean isIntercepting() {
      return this._intercepting;
   }

   public void setIntercepting(boolean intercepting) {
      this._intercepting = intercepting;
   }

   public boolean isManagedInterface() {
      if (!this._type.isInterface()) {
         return false;
      } else {
         return this._interface == null ? false : this._interface;
      }
   }

   public void setManagedInterface(boolean managedInterface) {
      if (!this._type.isInterface()) {
         throw new MetaDataException(_loc.get("not-interface", (Object)this._type));
      } else {
         this._interface = managedInterface ? Boolean.TRUE : Boolean.FALSE;
         if (this.isManagedInterface()) {
            this.setIntercepting(true);
         }

         this.setAccessType(4);
      }
   }

   public Class getInterfaceImpl() {
      return this._impl;
   }

   public void setInterfaceImpl(Class impl) {
      this._impl = impl;
   }

   public Class[] getDeclaredInterfaces() {
      return this._interfaces == null ? MetaDataRepository.EMPTY_CLASSES : (Class[])((Class[])this._interfaces.toArray(new Class[this._interfaces.size()]));
   }

   public void addDeclaredInterface(Class iface) {
      if (iface != null && iface.isInterface()) {
         if (this._interfaces == null) {
            this._interfaces = new ArrayList();
         }

         this._interfaces.add(iface);
      } else {
         throw new MetaDataException(_loc.get("declare-non-interface", this, iface));
      }
   }

   public boolean removeDeclaredInterface(Class iface) {
      return this._interfaces == null ? false : this._interfaces.remove(iface);
   }

   public void setInterfacePropertyAlias(Class iface, String orig, String local) {
      synchronized(this._ifaceMap) {
         Map fields = (Map)this._ifaceMap.get(iface);
         if (fields == null) {
            fields = new HashMap();
            this._ifaceMap.put(iface, fields);
         }

         if (((Map)fields).containsKey(orig)) {
            throw new MetaDataException(_loc.get("duplicate-iface-alias", this, orig, local));
         } else {
            ((Map)fields).put(orig, local);
         }
      }
   }

   public String getInterfacePropertyAlias(Class iface, String orig) {
      synchronized(this._ifaceMap) {
         Map fields = (Map)this._ifaceMap.get(iface);
         return fields == null ? null : (String)fields.get(orig);
      }
   }

   public String[] getInterfaceAliasedProperties(Class iface) {
      synchronized(this._ifaceMap) {
         Map fields = (Map)this._ifaceMap.get(iface);
         return fields == null ? EMPTY_STRING_ARRAY : (String[])((String[])fields.keySet().toArray(new String[fields.size()]));
      }
   }

   public int getExtraFieldDataLength() {
      int[] table = this.getExtraFieldDataTable();

      for(int i = table.length - 1; i >= 0; --i) {
         if (table[i] != -1) {
            return table[i] + 1;
         }
      }

      return 0;
   }

   public int getExtraFieldDataIndex(int field) {
      return this.getExtraFieldDataTable()[field];
   }

   private int[] getExtraFieldDataTable() {
      if (this._fieldDataTable == null) {
         FieldMetaData[] fmds = this.getFields();
         int[] table = new int[fmds.length];
         int idx = 0;

         for(int i = 0; i < fmds.length; ++i) {
            if (!fmds[i].usesIntermediate() && fmds[i].usesImplData() == Boolean.FALSE) {
               table[i] = -1;
            } else {
               table[i] = idx++;
            }
         }

         this._fieldDataTable = table;
      }

      return this._fieldDataTable;
   }

   public boolean isAccessibleField(String field) {
      if (this.getDeclaredField(field) != null) {
         return true;
      } else {
         if (this._staticFields == null) {
            Field[] fields = (Field[])((Field[])AccessController.doPrivileged(J2DoPrivHelper.getDeclaredFieldsAction(this._type)));
            Set names = new HashSet((int)((double)fields.length * 1.33 + 1.0));

            for(int i = 0; i < fields.length; ++i) {
               if (Modifier.isStatic(fields[i].getModifiers())) {
                  names.add(fields[i].getName());
               }
            }

            this._staticFields = names;
         }

         if (this._staticFields.contains(field)) {
            return true;
         } else {
            return this._super != null ? this.getPCSuperclassMetaData().isAccessibleField(field) : false;
         }
      }
   }

   public FieldMetaData[] getFields() {
      if (this._allFields == null) {
         if (this._super == null) {
            this._allFields = this.getDeclaredFields();
         } else {
            FieldMetaData[] fields = this.getDeclaredFields();
            FieldMetaData[] supFields = this.getPCSuperclassMetaData().getFields();
            FieldMetaData[] allFields = this._repos.newFieldMetaDataArray(fields.length + supFields.length);
            System.arraycopy(supFields, 0, allFields, 0, supFields.length);
            this.replaceDefinedSuperclassFields(allFields, supFields.length);

            for(int i = 0; i < fields.length; ++i) {
               fields[i].setIndex(supFields.length + i);
               allFields[supFields.length + i] = fields[i];
            }

            this._allFields = allFields;
         }
      }

      return this._allFields;
   }

   private void replaceDefinedSuperclassFields(FieldMetaData[] fields, int len) {
      if (this._supFieldMap != null && this._defSupFields) {
         for(int i = 0; i < len; ++i) {
            FieldMetaData supField = (FieldMetaData)this._supFieldMap.get(fields[i].getName());
            if (supField != null) {
               fields[i] = supField;
               supField.setIndex(i);
            }
         }

      }
   }

   protected FieldMetaData getSuperclassField(FieldMetaData supField) {
      ClassMetaData sm = this.getPCSuperclassMetaData();
      FieldMetaData fmd = sm == null ? null : sm.getField(supField.getName());
      if (fmd != null && fmd.getManagement() == 3) {
         return fmd;
      } else {
         throw new MetaDataException(_loc.get("unmanaged-sup-field", supField, this));
      }
   }

   public FieldMetaData[] getDeclaredFields() {
      if (this._fields == null) {
         List fields = new ArrayList(this._fieldMap.size());
         Iterator itr = this._fieldMap.values().iterator();

         while(itr.hasNext()) {
            FieldMetaData fmd = (FieldMetaData)itr.next();
            if (fmd.getManagement() != 0) {
               fmd.setDeclaredIndex(fields.size());
               fmd.setIndex(fmd.getDeclaredIndex());
               fields.add(fmd);
            }
         }

         this._fields = (FieldMetaData[])((FieldMetaData[])fields.toArray(this._repos.newFieldMetaDataArray(fields.size())));
      }

      return this._fields;
   }

   public FieldMetaData[] getPrimaryKeyFields() {
      if (this._allPKFields == null) {
         FieldMetaData[] fields = this.getFields();
         int num = 0;

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i].isPrimaryKey()) {
               ++num;
            }
         }

         if (num == 0) {
            this._allPKFields = this._repos.EMPTY_FIELDS;
         } else {
            FieldMetaData[] pks = this._repos.newFieldMetaDataArray(num);
            num = 0;

            for(int i = 0; i < fields.length; ++i) {
               if (fields[i].isPrimaryKey()) {
                  fields[i].setPrimaryKeyIndex(num);
                  pks[num] = fields[i];
                  ++num;
               }
            }

            this._allPKFields = pks;
         }
      }

      return this._allPKFields;
   }

   public FieldMetaData[] getDefaultFetchGroupFields() {
      if (this._allDFGFields == null) {
         FieldMetaData[] fields = this.getFields();
         int num = 0;

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i].isInDefaultFetchGroup()) {
               ++num;
            }
         }

         FieldMetaData[] dfgs = this._repos.newFieldMetaDataArray(num);
         num = 0;

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i].isInDefaultFetchGroup()) {
               dfgs[num++] = fields[i];
            }
         }

         this._allDFGFields = dfgs;
      }

      return this._allDFGFields;
   }

   public FieldMetaData getVersionField() {
      if (this._versionIdx == Integer.MIN_VALUE) {
         FieldMetaData[] fields = this.getFields();
         int idx = -1;

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i].isVersion()) {
               if (idx != -1) {
                  throw new MetaDataException(_loc.get("mult-vers-fields", this, fields[idx], fields[i]));
               }

               idx = i;
            }
         }

         this._versionIdx = idx;
      }

      return this._versionIdx == -1 ? null : this.getFields()[this._versionIdx];
   }

   public FieldMetaData getField(int index) {
      FieldMetaData[] fields = this.getFields();
      return index >= 0 && index < fields.length ? fields[index] : null;
   }

   public FieldMetaData getDeclaredField(int index) {
      FieldMetaData[] fields = this.getDeclaredFields();
      return index >= 0 && index < fields.length ? fields[index] : null;
   }

   public FieldMetaData getField(String name) {
      FieldMetaData fmd = this.getDeclaredField(name);
      if (fmd != null) {
         return fmd;
      } else {
         if (this._supFieldMap != null && this._defSupFields) {
            fmd = (FieldMetaData)this._supFieldMap.get(name);
            if (fmd != null) {
               return fmd;
            }
         }

         return this._super != null ? this.getPCSuperclassMetaData().getField(name) : null;
      }
   }

   public FieldMetaData getDeclaredField(String name) {
      FieldMetaData field = (FieldMetaData)this._fieldMap.get(name);
      return field != null && field.getManagement() != 0 ? field : null;
   }

   public FieldMetaData[] getDeclaredUnmanagedFields() {
      if (this._unmgdFields == null) {
         Collection unmanaged = new ArrayList(3);
         Iterator itr = this._fieldMap.values().iterator();

         while(itr.hasNext()) {
            FieldMetaData field = (FieldMetaData)itr.next();
            if (field.getManagement() == 0) {
               unmanaged.add(field);
            }
         }

         this._unmgdFields = (FieldMetaData[])((FieldMetaData[])unmanaged.toArray(this._repos.newFieldMetaDataArray(unmanaged.size())));
      }

      return this._unmgdFields;
   }

   public FieldMetaData addDeclaredField(String name, Class type) {
      FieldMetaData fmd = this._repos.newFieldMetaData(name, type, this);
      this.clearFieldCache();
      this._fieldMap.put(name, fmd);
      return fmd;
   }

   public boolean removeDeclaredField(FieldMetaData field) {
      if (field != null && this._fieldMap.remove(field.getName()) != null) {
         this.clearFieldCache();
         return true;
      } else {
         return false;
      }
   }

   public FieldMetaData getDefinedSuperclassField(String name) {
      return this._supFieldMap == null ? null : (FieldMetaData)this._supFieldMap.get(name);
   }

   public FieldMetaData addDefinedSuperclassField(String name, Class type, Class sup) {
      FieldMetaData fmd = this._repos.newFieldMetaData(name, type, this);
      fmd.setDeclaringType(sup);
      this.clearAllFieldCache();
      this._defSupFields = false;
      if (this._supFieldMap == null) {
         this._supFieldMap = new HashMap();
      }

      this._supFieldMap.put(name, fmd);
      return fmd;
   }

   public boolean removeDefinedSuperclassField(FieldMetaData field) {
      if (field != null && this._supFieldMap != null && this._supFieldMap.remove(field.getName()) != null) {
         this.clearAllFieldCache();
         this._defSupFields = false;
         return true;
      } else {
         return false;
      }
   }

   public void defineSuperclassFields(boolean force) {
      if (!this._defSupFields) {
         ClassMetaData sup = this.getPCSuperclassMetaData();
         if (this.isMapped() && sup != null) {
            FieldMetaData[] sups = sup.getFields();

            for(int i = 0; i < sups.length; ++i) {
               if ((force || !sups[i].getDefiningMetaData().isMapped()) && this.getDefinedSuperclassField(sups[i].getName()) == null) {
                  this.addDefinedSuperclassField(sups[i].getName(), sups[i].getDeclaredType(), sups[i].getDeclaringType());
               }
            }
         }

         this.resolveDefinedSuperclassFields();
         this.clearAllFieldCache();
         this.cacheFields();
      }
   }

   private void resolveDefinedSuperclassFields() {
      this._defSupFields = true;
      if (this._supFieldMap != null) {
         Iterator itr = this._supFieldMap.values().iterator();

         while(itr.hasNext()) {
            FieldMetaData fmd = (FieldMetaData)itr.next();
            FieldMetaData sup = this.getSuperclassField(fmd);
            if (fmd.getDeclaringType() == Object.class) {
               fmd.setDeclaringType(sup.getDeclaringType());
               fmd.backingMember(this.getRepository().getMetaDataFactory().getDefaults().getBackingMember(fmd));
            }

            fmd.copy(sup);
            fmd.resolve(1);
         }

      }
   }

   public FieldMetaData[] getDefinedFields() {
      if (this._definedFields == null) {
         FieldMetaData[] fields = this.getFields();
         List defined = new ArrayList(fields.length);

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i].isMapped() && fields[i].getDefiningMetaData() == this) {
               defined.add(fields[i]);
            }
         }

         this._definedFields = (FieldMetaData[])((FieldMetaData[])defined.toArray(this._repos.newFieldMetaDataArray(defined.size())));
      }

      return this._definedFields;
   }

   public FieldMetaData[] getFieldsInListingOrder() {
      if (this._allListingFields == null) {
         FieldMetaData[] dec = this.getDeclaredFields();
         FieldMetaData[] unmgd = this.getDeclaredUnmanagedFields();
         FieldMetaData[] decListing = this._repos.newFieldMetaDataArray(dec.length + unmgd.length);
         System.arraycopy(dec, 0, decListing, 0, dec.length);
         System.arraycopy(unmgd, 0, decListing, dec.length, unmgd.length);
         Arrays.sort(decListing, ClassMetaData.ListingOrderComparator.getInstance());
         if (this._super == null) {
            this._allListingFields = decListing;
         } else {
            FieldMetaData[] sup = this.getPCSuperclassMetaData().getFieldsInListingOrder();
            FieldMetaData[] listing = this._repos.newFieldMetaDataArray(sup.length + decListing.length);
            System.arraycopy(sup, 0, listing, 0, sup.length);
            this.replaceDefinedSuperclassFields(listing, sup.length);
            System.arraycopy(decListing, 0, listing, sup.length, decListing.length);
            this._allListingFields = listing;
         }
      }

      return this._allListingFields;
   }

   public FieldMetaData[] getDefinedFieldsInListingOrder() {
      if (this._listingFields == null) {
         FieldMetaData[] fields = this.getFields();
         List defined = new ArrayList(fields.length);

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i].getDefiningMetaData() == this) {
               defined.add(fields[i]);
            }
         }

         FieldMetaData[] unmgd = this.getDeclaredUnmanagedFields();
         FieldMetaData[] listing = this._repos.newFieldMetaDataArray(defined.size() + unmgd.length);

         for(int i = 0; i < defined.size(); ++i) {
            listing[i] = (FieldMetaData)defined.get(i);
         }

         System.arraycopy(unmgd, 0, listing, defined.size(), unmgd.length);
         Arrays.sort(listing, ClassMetaData.ListingOrderComparator.getInstance());
         this._listingFields = listing;
      }

      return this._listingFields;
   }

   public String getDataCacheName() {
      if ("`".equals(this._cacheName)) {
         if (this._super != null) {
            this._cacheName = this.getPCSuperclassMetaData().getDataCacheName();
         } else {
            this._cacheName = "default";
         }
      }

      return this._cacheName;
   }

   public void setDataCacheName(String name) {
      this._cacheName = name;
   }

   public int getDataCacheTimeout() {
      if (this._cacheTimeout == Integer.MIN_VALUE) {
         if (this._super != null) {
            this._cacheTimeout = this.getPCSuperclassMetaData().getDataCacheTimeout();
         } else {
            this._cacheTimeout = this._repos.getConfiguration().getDataCacheTimeout();
         }
      }

      return this._cacheTimeout;
   }

   public void setDataCacheTimeout(int timeout) {
      this._cacheTimeout = timeout;
   }

   public DataCache getDataCache() {
      String name = this.getDataCacheName();
      return name == null ? null : this._repos.getConfiguration().getDataCacheManagerInstance().getDataCache(name, true);
   }

   public boolean isDetachable() {
      if (this._detachable == null) {
         if (this._super != null) {
            this._detachable = this.getPCSuperclassMetaData().isDetachable() ? Boolean.TRUE : Boolean.FALSE;
         } else {
            this._detachable = Boolean.FALSE;
         }
      }

      return this._detachable;
   }

   public void setDetachable(boolean detachable) {
      this._detachable = detachable ? Boolean.TRUE : Boolean.FALSE;
   }

   public String getDetachedState() {
      if ("`".equals(this._detachState)) {
         ClassMetaData sup = this.getPCSuperclassMetaData();
         if (sup != null && sup.isDetachable() == this.isDetachable()) {
            this._detachState = sup.getDetachedState();
         } else {
            Boolean use = this.usesDetachedState("`syn", true);
            this._detachState = Boolean.FALSE.equals(use) ? null : "`syn";
         }
      }

      return this._detachState;
   }

   public void setDetachedState(String field) {
      this._detachState = field;
   }

   public Field getDetachedStateField() {
      String fieldName = this.getDetachedState();
      if (fieldName != null && !"`syn".equals(fieldName)) {
         Field f = Reflection.findField(this._type, fieldName, false);
         if (f != null) {
            return f;
         } else {
            throw new MetaDataException(_loc.get("no-detach-state", fieldName, this._type));
         }
      } else {
         return null;
      }
   }

   public Boolean usesDetachedState() {
      return this.usesDetachedState(this.getDetachedState(), false);
   }

   private Boolean usesDetachedState(String detachedField, boolean confDisallows) {
      if (!this.isDetachable()) {
         return Boolean.FALSE;
      } else if (detachedField == null) {
         return Boolean.FALSE;
      } else if (!"`syn".equals(detachedField)) {
         return Boolean.TRUE;
      } else if (confDisallows && !this._repos.getConfiguration().getDetachStateInstance().getDetachedStateField()) {
         return Boolean.FALSE;
      } else {
         return this.getIdentityType() == 1 ? Boolean.TRUE : null;
      }
   }

   protected void clearAllFieldCache() {
      this._allFields = null;
      this._allDFGFields = null;
      this._allPKFields = null;
      this._definedFields = null;
      this._listingFields = null;
      this._allListingFields = null;
      this._fieldDataTable = null;
   }

   protected void clearDefinedFieldCache() {
      this._definedFields = null;
      this._listingFields = null;
   }

   protected void clearFieldCache() {
      this.clearAllFieldCache();
      this._fields = null;
      this._unmgdFields = null;
      this._versionIdx = Integer.MIN_VALUE;
   }

   protected void clearSubclassCache() {
      this._subs = null;
      this._subMetas = null;
      this._mapSubMetas = null;
   }

   void clearExtraFieldDataTable() {
      this._fieldDataTable = null;
   }

   private void cacheFields() {
      this.getFields();
      this.getPrimaryKeyFields();
   }

   public int hashCode() {
      return this._type.getName().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ClassMetaData)) {
         return false;
      } else {
         return this._type == ((ClassMetaData)other).getDescribedType();
      }
   }

   public int compareTo(Object other) {
      return other == this ? 0 : this._type.getName().compareTo(((ClassMetaData)other).getDescribedType().getName());
   }

   public String toString() {
      return this.getDescribedType().getName();
   }

   public int getResolve() {
      return this._resMode;
   }

   public void setResolve(int mode) {
      this._resMode = mode;
   }

   public void setResolve(int mode, boolean on) {
      if (mode == 0) {
         this._resMode = mode;
      } else if (on) {
         this._resMode |= mode;
      } else {
         this._resMode &= ~mode;
      }

   }

   public boolean resolve(int mode) {
      if ((this._resMode & mode) == mode) {
         return true;
      } else {
         int cur = this._resMode;
         this._resMode |= mode;
         int val = this._repos.getValidate();
         MetaDataRepository var10001 = this._repos;
         boolean runtime = (val & 8) != 0;
         boolean validate = !ImplHelper.isManagedType(this.getRepository().getConfiguration(), this._type) || (val & 4) == 0;
         if ((mode & 1) != 0 && (cur & 1) == 0) {
            this.resolveMeta(runtime);
            if (validate) {
               var10001 = this._repos;
               if ((val & 1) != 0) {
                  this.validateMeta(runtime);
               }
            }
         }

         if ((mode & 2) != 0 && (cur & 2) == 0) {
            this.resolveMapping(runtime);
            if (validate) {
               var10001 = this._repos;
               if ((val & 2) != 0) {
                  this.validateMapping(runtime);
               }
            }
         }

         if ((mode & 8) != 0 && (cur & 8) == 0) {
            this.initializeMapping();
         }

         return false;
      }
   }

   protected void resolveMeta(boolean runtime) {
      boolean embed = this._owner != null && this._owner.getDeclaredType() == this._type;
      Log log = this._repos.getLog();
      if (log.isTraceEnabled()) {
         log.trace(_loc.get(embed ? "resolve-embed-meta" : "resolve-meta", (Object)(this + "@" + System.identityHashCode(this))));
      }

      if (runtime && !this._type.isInterface() && !ImplHelper.isManagedType(this.getRepository().getConfiguration(), this._type)) {
         throw new MetaDataException(_loc.get("not-enhanced", (Object)this._type));
      } else {
         ClassMetaData sup;
         if (embed) {
            if (this._owner.getFieldMetaData().getDefiningMetaData().getDescribedType().isAssignableFrom(this._type)) {
               throw new MetaDataException(_loc.get("recurse-embed", (Object)this._owner));
            }

            sup = this._repos.getMetaData(this._type, this._loader, true);
            sup.resolve(1);
            copy(this, sup);
            this._embedded = Boolean.FALSE;
         }

         sup = this.getPCSuperclassMetaData();
         if (sup != null) {
            sup.resolve(1);
            if (embed) {
               FieldMetaData[] sups = sup.getFields();

               for(int i = 0; i < sups.length; ++i) {
                  if (this._supFieldMap == null || !this._supFieldMap.containsKey(sups[i].getName())) {
                     this.addDefinedSuperclassField(sups[i].getName(), sups[i].getDeclaredType(), sups[i].getDeclaringType());
                  }
               }
            }
         }

         Iterator itr = this._fieldMap.values().iterator();

         while(true) {
            FieldMetaData fmd;
            do {
               do {
                  if (!itr.hasNext()) {
                     if (embed) {
                        this.clearAllFieldCache();
                        this.resolveDefinedSuperclassFields();
                     }

                     this.cacheFields();
                     this._lifeMeta.resolve();
                     if (this._interfaces != null) {
                        itr = this._interfaces.iterator();

                        while(itr.hasNext()) {
                           this._repos.addDeclaredInterfaceImpl(this, (Class)itr.next());
                        }
                     }

                     if (this._fgMap != null) {
                        itr = this._fgMap.values().iterator();

                        while(itr.hasNext()) {
                           ((FetchGroup)itr.next()).resolve();
                        }
                     }

                     if (!embed && this._type.isInterface()) {
                        if (this._interface != Boolean.TRUE) {
                           throw new MetaDataException(_loc.get("interface", (Object)this._type));
                        }

                        if (runtime) {
                           this._impl = this._repos.getImplGenerator().createImpl(this);
                           this._repos.setInterfaceImpl(this, this._impl);
                        }
                     }

                     if (runtime && !Modifier.isAbstract(this._type.getModifiers())) {
                        ProxySetupStateManager sm = new ProxySetupStateManager();
                        sm.setProxyData(PCRegistry.newInstance(this._type, sm, false), this);
                     }

                     return;
                  }

                  fmd = (FieldMetaData)itr.next();
                  fmd.resolve(1);
               } while(fmd.isExplicit());
            } while(fmd.getDeclaredTypeCode() != 8 && fmd.getDeclaredTypeCode() != 27 && (fmd.getDeclaredTypeCode() != 11 || fmd.getElement().getDeclaredTypeCode() != 8));

            this._repos.getLog().warn(_loc.get("rm-field", (Object)fmd));
            if (fmd.getListingIndex() != -1) {
               fmd.setManagement(0);
            } else {
               itr.remove();
            }

            this.clearFieldCache();
         }
      }
   }

   protected void validateMeta(boolean runtime) {
      this.validateDataCache();
      this.validateDetachable();
      this.validateExtensionKeys();
      this.validateIdentity();
      this.validateAccessType();
   }

   protected void resolveMapping(boolean runtime) {
      Log log = this._repos.getLog();
      if (log.isTraceEnabled()) {
         log.trace(_loc.get("resolve-mapping", (Object)(this + "@" + System.identityHashCode(this))));
      }

      ClassMetaData sup = this.getPCSuperclassMetaData();
      if (sup != null) {
         sup.resolve(2);
      }

   }

   protected void validateMapping(boolean runtime) {
   }

   protected void initializeMapping() {
      Log log = this._repos.getLog();
      if (log.isTraceEnabled()) {
         log.trace(_loc.get("init-mapping", (Object)(this + "@" + System.identityHashCode(this))));
      }

   }

   private void validateDataCache() {
      int timeout = this.getDataCacheTimeout();
      if (timeout >= -1 && timeout != 0) {
         if (this._super != null) {
            String cache = this.getDataCacheName();
            if (cache != null) {
               String superCache = this.getPCSuperclassMetaData().getDataCacheName();
               if (!StringUtils.equals(cache, superCache)) {
                  throw new MetaDataException(_loc.get("cache-names", new Object[]{this._type, cache, this._super, superCache}));
               }
            }
         }
      } else {
         throw new MetaDataException(_loc.get("cache-timeout-invalid", this._type, String.valueOf(timeout)));
      }
   }

   private void validateIdentity() {
      ClassMetaData sup = this.getPCSuperclassMetaData();
      int id = this.getIdentityType();
      if (sup != null && sup.getIdentityType() != 0 && sup.getIdentityType() != id) {
         throw new MetaDataException(_loc.get("id-types", (Object)this._type));
      } else {
         Collection opts = this._repos.getConfiguration().supportedOptions();
         if (id == 2 && !opts.contains("openjpa.option.ApplicationIdentity")) {
            throw new UnsupportedException(_loc.get("appid-not-supported", (Object)this._type));
         } else if (id == 1 && !opts.contains("openjpa.option.DatastoreIdentity")) {
            throw new UnsupportedException(_loc.get("datastoreid-not-supported", (Object)this._type));
         } else {
            if (id == 2) {
               if (this._idStrategy != 0) {
                  throw new MetaDataException(_loc.get("appid-strategy", (Object)this._type));
               }

               this.validateAppIdClass();
            } else if (id != 0) {
               this.validateNoPKFields();
            }

            int strategy = this.getIdentityStrategy();
            if (strategy == 2 && this.getIdentitySequenceName() == null) {
               throw new MetaDataException(_loc.get("no-seq-name", (Object)this._type));
            } else {
               ValueStrategies.assertSupported(strategy, this, "datastore identity strategy");
            }
         }
      }
   }

   private void validateAppIdClass() {
      FieldMetaData[] pks = this.getPrimaryKeyFields();
      if (this.getObjectIdType() == null) {
         if (pks.length == 1) {
            throw new MetaDataException(_loc.get("unsupported-id-type", this._type, pks[0].getName(), pks[0].getDeclaredType().getName()));
         } else {
            throw new MetaDataException(_loc.get("no-id-class", (Object)this._type));
         }
      } else if (this._objectId != null) {
         ClassMetaData sup;
         if (this.isOpenJPAIdentity()) {
            if (pks[0].getDeclaredTypeCode() == 29) {
               sup = pks[0].getEmbeddedMetaData();
               this.validateAppIdClassMethods(sup.getDescribedType());
               this.validateAppIdClassPKs(sup, sup.getFields(), sup.getDescribedType());
            }

         } else {
            if (this._super != null) {
               sup = this.getPCSuperclassMetaData();
               Class objectIdType = sup.getObjectIdType();
               if (objectIdType != null && !objectIdType.isAssignableFrom(this._objectId)) {
                  throw new MetaDataException(_loc.get("id-classes", new Object[]{this._type, this._objectId, this._super, sup.getObjectIdType()}));
               }

               if (this.hasConcretePCSuperclass()) {
                  this.validateNoPKFields();
               }
            }

            if (this._super == null || this._objectId != this.getPCSuperclassMetaData().getObjectIdType()) {
               if (!Modifier.isAbstract(this._objectId.getModifiers())) {
                  this.validateAppIdClassMethods(this._objectId);
               }

               this.validateAppIdClassPKs(this, pks, this._objectId);
            }

         }
      }
   }

   private boolean hasConcretePCSuperclass() {
      if (this._super == null) {
         return false;
      } else {
         return !Modifier.isAbstract(this._super.getModifiers()) && !this.getPCSuperclassMetaData().isAbstract() ? true : this.getPCSuperclassMetaData().hasConcretePCSuperclass();
      }
   }

   private void validateAppIdClassMethods(Class oid) {
      try {
         oid.getConstructor((Class[])null);
      } catch (Exception var7) {
         throw (new MetaDataException(_loc.get("null-cons", (Object)this._type))).setCause(var7);
      }

      Method method;
      try {
         method = oid.getMethod("equals", Object.class);
      } catch (Exception var6) {
         throw (new GeneralException(var6)).setFatal(true);
      }

      boolean abs = Modifier.isAbstract(this._type.getModifiers());
      if (!abs && method.getDeclaringClass() == Object.class) {
         throw new MetaDataException(_loc.get("eq-method", (Object)this._type));
      } else {
         try {
            method = oid.getMethod("hashCode", (Class[])null);
         } catch (Exception var5) {
            throw (new GeneralException(var5)).setFatal(true);
         }

         if (!abs && method.getDeclaringClass() == Object.class) {
            throw new MetaDataException(_loc.get("hc-method", (Object)this._type));
         }
      }
   }

   private void validateAppIdClassPKs(ClassMetaData meta, FieldMetaData[] fmds, Class oid) {
      if (fmds.length == 0 && !Modifier.isAbstract(meta.getDescribedType().getModifiers())) {
         throw new MetaDataException(_loc.get("no-pk", (Object)this._type));
      } else {
         for(int i = 0; i < fmds.length; ++i) {
            Class c;
            switch (fmds[i].getDeclaredTypeCode()) {
               case 11:
                  c = fmds[i].getDeclaredType().getComponentType();
                  if (c == Byte.TYPE || c == Byte.class || c == Character.TYPE || c == Character.class) {
                     c = fmds[i].getDeclaredType();
                     break;
                  }
               case 12:
               case 13:
               case 27:
               case 29:
                  throw new MetaDataException(_loc.get("bad-pk-type", (Object)fmds[i]));
               default:
                  c = fmds[i].getObjectIdFieldType();
            }

            if (meta.getAccessType() == 2) {
               Field f = Reflection.findField(oid, fmds[i].getName(), false);
               if (f == null || !f.getType().isAssignableFrom(c)) {
                  throw new MetaDataException(_loc.get("invalid-id", this._type, fmds[i].getName()));
               }
            } else if (meta.getAccessType() == 4) {
               Method m = Reflection.findGetter(oid, fmds[i].getName(), false);
               if (m == null || !m.getReturnType().isAssignableFrom(c)) {
                  throw new MetaDataException(_loc.get("invalid-id", this._type, fmds[i].getName()));
               }

               m = Reflection.findSetter(oid, fmds[i].getName(), fmds[i].getObjectIdFieldType(), false);
               if (m == null || m.getReturnType() != Void.TYPE) {
                  throw new MetaDataException(_loc.get("invalid-id", this._type, fmds[i].getName()));
               }
            }
         }

      }
   }

   private void validateNoPKFields() {
      FieldMetaData[] fields = this.getDeclaredFields();

      for(int i = 0; i < fields.length; ++i) {
         if (fields[i].isPrimaryKey()) {
            throw new MetaDataException(_loc.get("bad-pk", (Object)fields[i]));
         }
      }

   }

   private void validateAccessType() {
      if (this._accessType != 0) {
         ClassMetaData sup = this.getPCSuperclassMetaData();
         if (sup != null && sup.getAccessType() != 0 && sup.getAccessType() != this._accessType && this.getPCSuperclassMetaData().getFields().length > 0) {
            throw new MetaDataException(_loc.get("inconsistent-access", this, sup));
         }
      }
   }

   private void validateDetachable() {
      boolean first = true;

      for(ClassMetaData parent = this.getPCSuperclassMetaData(); first && parent != null; parent = parent.getPCSuperclassMetaData()) {
         if (parent.isDetachable()) {
            first = false;
         }
      }

      Field field = this.getDetachedStateField();
      if (field != null) {
         if (!first) {
            throw new MetaDataException(_loc.get("parent-detach-state", (Object)this._type));
         }

         if (this.getField(field.getName()) != null) {
            throw new MetaDataException(_loc.get("managed-detach-state", field.getName(), this._type));
         }

         if (field.getType() != Object.class) {
            throw new MetaDataException(_loc.get("bad-detach-state", field.getName(), this._type));
         }
      }

   }

   public FetchGroup[] getDeclaredFetchGroups() {
      if (this._fgs == null) {
         this._fgs = this._fgMap == null ? EMPTY_FETCH_GROUP_ARRAY : (FetchGroup[])((FetchGroup[])this._fgMap.values().toArray(new FetchGroup[this._fgMap.size()]));
      }

      return this._fgs;
   }

   public FetchGroup[] getCustomFetchGroups() {
      if (this._customFGs == null) {
         Map fgs = new HashMap();
         ClassMetaData sup = this.getPCSuperclassMetaData();
         FetchGroup[] decs;
         int i;
         if (sup != null) {
            decs = sup.getCustomFetchGroups();

            for(i = 0; i < decs.length; ++i) {
               fgs.put(decs[i].getName(), decs[i]);
            }
         }

         decs = this.getDeclaredFetchGroups();

         for(i = 0; i < decs.length; ++i) {
            fgs.put(decs[i].getName(), decs[i]);
         }

         fgs.remove("default");
         fgs.remove("all");
         this._customFGs = (FetchGroup[])((FetchGroup[])fgs.values().toArray(new FetchGroup[fgs.size()]));
      }

      return this._customFGs;
   }

   public FetchGroup getFetchGroup(String name) {
      FetchGroup fg = this._fgMap == null ? null : (FetchGroup)this._fgMap.get(name);
      if (fg != null) {
         return fg;
      } else {
         ClassMetaData sup = this.getPCSuperclassMetaData();
         if (sup != null) {
            return sup.getFetchGroup(name);
         } else if ("default".equals(name)) {
            return FetchGroup.DEFAULT;
         } else {
            return "all".equals(name) ? FetchGroup.ALL : null;
         }
      }
   }

   public FetchGroup addDeclaredFetchGroup(String name) {
      if (StringUtils.isEmpty(name)) {
         throw new MetaDataException(_loc.get("empty-fg-name", (Object)this));
      } else {
         if (this._fgMap == null) {
            this._fgMap = new HashMap();
         }

         FetchGroup fg = (FetchGroup)this._fgMap.get(name);
         if (fg == null) {
            fg = new FetchGroup(this, name);
            this._fgMap.put(name, fg);
            this._fgs = null;
            this._customFGs = null;
         }

         return fg;
      }
   }

   public boolean removeDeclaredFetchGroup(FetchGroup fg) {
      if (fg == null) {
         return false;
      } else if (this._fgMap.remove(fg.getName()) != null) {
         this._fgs = null;
         this._customFGs = null;
         return true;
      } else {
         return false;
      }
   }

   public File getSourceFile() {
      return this._srcFile;
   }

   public Object getSourceScope() {
      return null;
   }

   public int getSourceType() {
      return this._srcType;
   }

   public void setSource(File file, int srcType) {
      this._srcFile = file;
      this._srcType = srcType;
   }

   public String getResourceName() {
      return this._type.getName();
   }

   public int getSourceMode() {
      return this._srcMode;
   }

   public void setSourceMode(int mode) {
      this._srcMode = mode;
   }

   public void setSourceMode(int mode, boolean on) {
      if (mode == 0) {
         this._srcMode = mode;
      } else if (on) {
         this._srcMode |= mode;
      } else {
         this._srcMode &= ~mode;
      }

   }

   public int getListingIndex() {
      return this._listIndex;
   }

   public void setListingIndex(int index) {
      this._listIndex = index;
   }

   public String[] getComments() {
      return this._comments == null ? EMPTY_COMMENTS : this._comments;
   }

   public void setComments(String[] comments) {
      this._comments = comments;
   }

   public void copy(ClassMetaData meta) {
      if (meta.getDescribedType() != this._type) {
         throw new InternalException();
      } else {
         super.copy(meta);
         this._super = meta.getPCSuperclass();
         this._objectId = meta.getObjectIdType();
         this._extent = meta.getRequiresExtent() ? Boolean.TRUE : Boolean.FALSE;
         this._embedded = meta.isEmbeddedOnly() ? Boolean.TRUE : Boolean.FALSE;
         this._interface = meta.isManagedInterface() ? Boolean.TRUE : Boolean.FALSE;
         this.setIntercepting(meta.isIntercepting());
         this._impl = meta.getInterfaceImpl();
         this._identity = meta.getIdentityType();
         this._idStrategy = meta.getIdentityStrategy();
         this._seqName = meta.getIdentitySequenceName();
         this._seqMeta = null;
         this._alias = meta.getTypeAlias();
         this._accessType = meta.getAccessType();
         if ("`".equals(this._cacheName)) {
            this._cacheName = meta.getDataCacheName();
         }

         if (this._cacheTimeout == Integer.MIN_VALUE) {
            this._cacheTimeout = meta.getDataCacheTimeout();
         }

         if (this._detachable == null) {
            this._detachable = meta._detachable;
         }

         if ("`".equals(this._detachState)) {
            this._detachState = meta.getDetachedState();
         }

         this.clearFieldCache();
         this._fieldMap.keySet().retainAll(meta._fieldMap.keySet());
         FieldMetaData[] fields = meta.getDeclaredFields();

         for(int i = 0; i < fields.length; ++i) {
            FieldMetaData field = this.getDeclaredField(fields[i].getName());
            if (field == null) {
               field = this.addDeclaredField(fields[i].getName(), fields[i].getDeclaredType());
            }

            field.setDeclaredIndex(-1);
            field.setIndex(-1);
            field.copy(fields[i]);
         }

         FetchGroup[] fgs = meta.getDeclaredFetchGroups();

         for(int i = 0; i < fgs.length; ++i) {
            FetchGroup fg = this.addDeclaredFetchGroup(fgs[i].getName());
            fg.copy(fgs[i]);
         }

         this._ifaceMap.clear();
         this._ifaceMap.putAll(meta._ifaceMap);
      }
   }

   private static void copy(ClassMetaData embed, ClassMetaData dec) {
      ClassMetaData sup = dec.getPCSuperclassMetaData();
      if (sup != null) {
         embed.setPCSuperclass(sup.getDescribedType());
         copy(embed.getPCSuperclassMetaData(), sup);
      }

      embed.copy(dec);
   }

   protected void addExtensionKeys(Collection exts) {
      this._repos.getMetaDataFactory().addClassExtensionKeys(exts);
   }

   public void registerForValueUpdate(String... values) {
      if (values != null) {
         String[] arr$ = values;
         int len$ = values.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String key = arr$[i$];
            Value value = this.getRepository().getConfiguration().getValue(key);
            if (value != null) {
               value.addListener(this);
            }
         }

      }
   }

   public void valueChanged(Value val) {
      if (val != null) {
         String key = val.getProperty();
         if ("DataCacheTimeout".equals(key)) {
            this._cacheTimeout = Integer.MIN_VALUE;
         }

      }
   }

   public boolean isAbstract() {
      return this._abstract;
   }

   public void setAbstract(boolean flag) {
      this._abstract = flag;
   }

   private static class ListingOrderComparator implements Comparator {
      private static final ListingOrderComparator _instance = new ListingOrderComparator();

      public static ListingOrderComparator getInstance() {
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
            FieldMetaData f1 = (FieldMetaData)o1;
            FieldMetaData f2 = (FieldMetaData)o2;
            if (f1.getListingIndex() == f2.getListingIndex()) {
               if (f1.getIndex() == f2.getIndex()) {
                  return f1.getFullName(false).compareTo(f2.getFullName(false));
               } else if (f1.getIndex() == -1) {
                  return 1;
               } else {
                  return f2.getIndex() == -1 ? -1 : f1.getIndex() - f2.getIndex();
               }
            } else if (f1.getListingIndex() == -1) {
               return 1;
            } else {
               return f2.getListingIndex() == -1 ? -1 : f1.getListingIndex() - f2.getListingIndex();
            }
         }
      }
   }
}
