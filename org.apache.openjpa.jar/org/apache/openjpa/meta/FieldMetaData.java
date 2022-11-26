package org.apache.openjpa.meta;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.lib.xml.Commentable;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;
import serp.util.Strings;

public class FieldMetaData extends Extensions implements ValueMetaData, MetaDataContext, MetaDataModes, Commentable {
   public static final int NULL_UNSET = -1;
   public static final int NULL_NONE = 0;
   public static final int NULL_DEFAULT = 1;
   public static final int NULL_EXCEPTION = 2;
   public static final int MANAGE_PERSISTENT = 3;
   public static final int MANAGE_TRANSACTIONAL = 1;
   public static final int MANAGE_NONE = 0;
   private static final Localizer _loc = Localizer.forPackage(FieldMetaData.class);
   private static final int DFG_FALSE = 1;
   private static final int DFG_TRUE = 2;
   private static final int DFG_EXPLICIT = 4;
   private static final Method DEFAULT_METHOD;
   private final ValueMetaData _val;
   private final ValueMetaData _key;
   private final ValueMetaData _elem;
   private final ClassMetaData _owner;
   private final String _name;
   private Class _dec = null;
   private ClassMetaData _decMeta = null;
   private String _fullName = null;
   private String _embedFullName = null;
   private int _resMode = 0;
   private String[] _comments = null;
   private int _listIndex = -1;
   private Class _proxyClass = null;
   private Object _initializer = null;
   private boolean _transient = false;
   private boolean _primKey = false;
   private Boolean _version = null;
   private int _nullValue = -1;
   private int _manage = 3;
   private int _index = -1;
   private int _decIndex = -1;
   private int _pkIndex = -1;
   private boolean _explicit = false;
   private int _dfg = 0;
   private Set _fgSet = null;
   private String[] _fgs = null;
   private String _lfg = null;
   private Boolean _lrs = null;
   private Boolean _stream = null;
   private String _extName = null;
   private String _factName = null;
   private String _extString = null;
   private Map _extValues;
   private Map _fieldValues;
   private Boolean _enumField;
   private Boolean _lobField;
   private Boolean _serializableField;
   private boolean _generated;
   private MemberProvider _backingMember;
   private transient Method _extMethod;
   private transient Member _factMethod;
   private boolean _intermediate;
   private Boolean _implData;
   private int _valStrategy;
   private int _upStrategy;
   private String _seqName;
   private SequenceMetaData _seqMeta;
   private String _mappedBy;
   private FieldMetaData _mappedByMeta;
   private FieldMetaData[] _inverses;
   private String _inverse;
   private Order[] _orders;
   private String _orderDec;
   private boolean _usedInOrderBy;

   protected FieldMetaData(String name, Class type, ClassMetaData owner) {
      this._extValues = Collections.EMPTY_MAP;
      this._fieldValues = Collections.EMPTY_MAP;
      this._enumField = null;
      this._lobField = null;
      this._serializableField = null;
      this._generated = false;
      this._backingMember = null;
      this._extMethod = DEFAULT_METHOD;
      this._factMethod = DEFAULT_METHOD;
      this._intermediate = true;
      this._implData = Boolean.TRUE;
      this._valStrategy = -1;
      this._upStrategy = -1;
      this._seqName = "`";
      this._seqMeta = null;
      this._mappedBy = null;
      this._mappedByMeta = null;
      this._inverses = null;
      this._inverse = "`";
      this._orders = null;
      this._orderDec = null;
      this._usedInOrderBy = false;
      this._name = name;
      this._owner = owner;
      this._dec = null;
      this._decMeta = null;
      this._val = owner.getRepository().newValueMetaData(this);
      this._key = owner.getRepository().newValueMetaData(this);
      this._elem = owner.getRepository().newValueMetaData(this);
      this.setDeclaredType(type);
   }

   public void backingMember(Member member) {
      if (member != null) {
         if (Modifier.isTransient(member.getModifiers())) {
            this._transient = true;
         }

         this._backingMember = new MemberProvider(member);
         Class type;
         Class[] types;
         if (member instanceof Field) {
            Field f = (Field)member;
            type = f.getType();
            types = JavaVersions.getParameterizedTypes(f);
         } else {
            Method meth = (Method)member;
            type = meth.getReturnType();
            types = JavaVersions.getParameterizedTypes(meth);
         }

         this.setDeclaredType(type);
         if (Collection.class.isAssignableFrom(type) && this._elem.getDeclaredType() == Object.class && types.length == 1) {
            this._elem.setDeclaredType(types[0]);
         } else if (Map.class.isAssignableFrom(type) && types.length == 2) {
            if (this._key.getDeclaredType() == Object.class) {
               this._key.setDeclaredType(types[0]);
            }

            if (this._elem.getDeclaredType() == Object.class) {
               this._elem.setDeclaredType(types[1]);
            }
         }

      }
   }

   public Member getBackingMember() {
      return this._backingMember == null ? null : this._backingMember.getMember();
   }

   public MetaDataRepository getRepository() {
      return this._owner.getRepository();
   }

   public ClassMetaData getDefiningMetaData() {
      return this._owner;
   }

   public Class getDeclaringType() {
      return this._dec == null ? this._owner.getDescribedType() : this._dec;
   }

   public void setDeclaringType(Class cls) {
      this._dec = cls;
      this._decMeta = null;
      this._fullName = null;
      this._embedFullName = null;
   }

   public ClassMetaData getDeclaringMetaData() {
      if (this._dec == null) {
         return this._owner;
      } else {
         if (this._decMeta == null) {
            this._decMeta = this.getRepository().getMetaData(this._dec, this._owner.getEnvClassLoader(), true);
         }

         return this._decMeta;
      }
   }

   public String getName() {
      return this._name;
   }

   /** @deprecated */
   public String getFullName() {
      return this.getFullName(false);
   }

   public String getFullName(boolean embedOwner) {
      if (this._fullName == null) {
         this._fullName = this.getDeclaringType().getName() + "." + this._name;
      }

      if (embedOwner && this._embedFullName == null) {
         if (this._owner.getEmbeddingMetaData() == null) {
            this._embedFullName = this._fullName;
         } else {
            this._embedFullName = this._owner.getEmbeddingMetaData().getFieldMetaData().getFullName(true) + "." + this._fullName;
         }
      }

      return embedOwner ? this._embedFullName : this._fullName;
   }

   public ValueMetaData getValue() {
      return this._val;
   }

   public ValueMetaData getKey() {
      return this._key;
   }

   public ValueMetaData getElement() {
      return this._elem;
   }

   public boolean isMapped() {
      return this._manage == 3 && this._owner.isMapped();
   }

   public Class getProxyType() {
      return this._proxyClass == null ? this.getDeclaredType() : this._proxyClass;
   }

   public void setProxyType(Class type) {
      this._proxyClass = type;
   }

   public Object getInitializer() {
      return this._initializer;
   }

   public void setInitializer(Object initializer) {
      this._initializer = initializer;
   }

   public boolean isTransient() {
      return this._transient;
   }

   public void setTransient(boolean trans) {
      this._transient = trans;
   }

   public int getIndex() {
      return this._index;
   }

   public void setIndex(int index) {
      this._index = index;
   }

   public int getDeclaredIndex() {
      return this._decIndex;
   }

   public void setDeclaredIndex(int index) {
      this._decIndex = index;
   }

   public int getListingIndex() {
      return this._listIndex;
   }

   public void setListingIndex(int index) {
      this._listIndex = index;
   }

   public int getPrimaryKeyIndex() {
      return this._pkIndex;
   }

   public void setPrimaryKeyIndex(int index) {
      this._pkIndex = index;
   }

   public int getManagement() {
      return this._manage;
   }

   public void setManagement(int manage) {
      if (this._manage == 0 != (manage == 0)) {
         this._owner.clearFieldCache();
      }

      this._manage = manage;
   }

   public boolean isPrimaryKey() {
      return this._primKey;
   }

   public void setPrimaryKey(boolean primKey) {
      this._primKey = primKey;
   }

   public int getObjectIdFieldTypeCode() {
      ClassMetaData relmeta = this.getDeclaredTypeMetaData();
      if (relmeta == null) {
         return this.getDeclaredTypeCode();
      } else if (relmeta.getIdentityType() == 1) {
         boolean unwrap = this.getRepository().getMetaDataFactory().getDefaults().isDataStoreObjectIdFieldUnwrapped();
         return unwrap ? 6 : 8;
      } else {
         return relmeta.isOpenJPAIdentity() ? relmeta.getPrimaryKeyFields()[0].getObjectIdFieldTypeCode() : 8;
      }
   }

   public Class getObjectIdFieldType() {
      ClassMetaData relmeta = this.getDeclaredTypeMetaData();
      if (relmeta == null) {
         return this.getDeclaredType();
      } else {
         switch (relmeta.getIdentityType()) {
            case 1:
               boolean unwrap = this.getRepository().getMetaDataFactory().getDefaults().isDataStoreObjectIdFieldUnwrapped();
               return unwrap ? Long.TYPE : Object.class;
            case 2:
               if (relmeta.isOpenJPAIdentity()) {
                  return relmeta.getPrimaryKeyFields()[0].getObjectIdFieldType();
               }

               return relmeta.getObjectIdType() == null ? Object.class : relmeta.getObjectIdType();
            default:
               return Object.class;
         }
      }
   }

   public boolean isVersion() {
      return this._version == Boolean.TRUE;
   }

   public void setVersion(boolean version) {
      this._version = version ? Boolean.TRUE : Boolean.FALSE;
   }

   public boolean isInDefaultFetchGroup() {
      if (this._dfg == 0) {
         if (this._manage == 3 && !this.isPrimaryKey() && !this.isVersion()) {
            switch (this.getTypeCode()) {
               case 8:
                  if (!this.isSerializable() && !this.isEnum()) {
                     this._dfg = 1;
                  } else {
                     this._dfg = 2;
                  }
                  break;
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
                  this._dfg = 2;
                  break;
               case 11:
                  if (this.isLobArray()) {
                     this._dfg = 2;
                  } else {
                     this._dfg = 1;
                  }
                  break;
               case 12:
               case 13:
               case 15:
               case 27:
                  this._dfg = 1;
            }
         } else {
            this._dfg = 1;
         }
      }

      return (this._dfg & 2) > 0;
   }

   private boolean isEnum() {
      if (this._enumField == null) {
         Class dt = this.getDeclaredType();
         this._enumField = JavaVersions.isEnumeration(dt) ? Boolean.TRUE : Boolean.FALSE;
      }

      return this._enumField;
   }

   private boolean isSerializable() {
      if (this._serializableField == null) {
         Class dt = this.getDeclaredType();
         if (Serializable.class.isAssignableFrom(dt)) {
            this._serializableField = Boolean.TRUE;
         } else {
            this._serializableField = Boolean.FALSE;
         }
      }

      return this._serializableField;
   }

   private boolean isLobArray() {
      if (this._lobField == null) {
         Class dt = this.getDeclaredType();
         if (dt != byte[].class && dt != Byte[].class && dt != char[].class && dt != Character[].class) {
            this._lobField = Boolean.FALSE;
         } else {
            this._lobField = Boolean.TRUE;
         }
      }

      return this._lobField;
   }

   public void setInDefaultFetchGroup(boolean dfg) {
      if (dfg) {
         this._dfg = 2;
      } else {
         this._dfg = 1;
      }

      this._dfg |= 4;
   }

   public boolean isDefaultFetchGroupExplicit() {
      return (this._dfg & 4) > 0;
   }

   public void setDefaultFetchGroupExplicit(boolean explicit) {
      if (explicit) {
         this._dfg |= 4;
      } else {
         this._dfg &= -5;
      }

   }

   public String[] getCustomFetchGroups() {
      if (this._fgs == null) {
         if (this._fgSet != null && this._manage == 3 && !this.isPrimaryKey() && !this.isVersion()) {
            this._fgs = (String[])((String[])this._fgSet.toArray(new String[this._fgSet.size()]));
         } else {
            this._fgs = new String[0];
         }
      }

      return this._fgs;
   }

   public String getLoadFetchGroup() {
      return this._lfg;
   }

   public void setLoadFetchGroup(String lfg) {
      if ("".equals(lfg)) {
         lfg = null;
      }

      this._lfg = lfg;
   }

   public boolean isInFetchGroup(String fg) {
      if (this._manage == 3 && !this.isPrimaryKey() && !this.isVersion()) {
         if ("all".equals(fg)) {
            return true;
         } else if ("default".equals(fg)) {
            return this.isInDefaultFetchGroup();
         } else {
            return this._fgSet != null && this._fgSet.contains(fg);
         }
      } else {
         return false;
      }
   }

   public void setInFetchGroup(String fg, boolean in) {
      if (StringUtils.isEmpty(fg)) {
         throw new MetaDataException(_loc.get("empty-fg-name", (Object)this));
      } else if (!fg.equals("all")) {
         if (fg.equals("default")) {
            this.setInDefaultFetchGroup(in);
         } else if (this._owner.getFetchGroup(fg) == null) {
            throw new MetaDataException(_loc.get("unknown-fg", fg, this));
         } else {
            if (in && this._fgSet == null) {
               this._fgSet = new HashSet();
            }

            if (in && this._fgSet.add(fg) || !in && this._fgSet != null && this._fgSet.remove(fg)) {
               this._fgs = null;
            }

         }
      }
   }

   public int getNullValue() {
      return this._nullValue;
   }

   public void setNullValue(int nullValue) {
      this._nullValue = nullValue;
   }

   public boolean isExplicit() {
      return this._explicit;
   }

   public void setExplicit(boolean explicit) {
      this._explicit = explicit;
   }

   public String getMappedBy() {
      return this._mappedBy;
   }

   public void setMappedBy(String mapped) {
      this._mappedBy = mapped;
      this._mappedByMeta = null;
   }

   public FieldMetaData getMappedByMetaData() {
      if (this._mappedBy != null && this._mappedByMeta == null) {
         ClassMetaData meta = null;
         switch (this.getTypeCode()) {
            case 11:
            case 12:
            case 13:
               meta = this._elem.getTypeMetaData();
            case 14:
            default:
               break;
            case 15:
               meta = this.getTypeMetaData();
         }

         FieldMetaData field = meta == null ? null : meta.getField(this._mappedBy);
         if (field == null) {
            throw new MetaDataException(_loc.get("no-mapped-by", this, this._mappedBy));
         }

         if (field.getMappedBy() != null) {
            throw new MetaDataException(_loc.get("circ-mapped-by", this, this._mappedBy));
         }

         this._mappedByMeta = field;
      }

      return this._mappedByMeta;
   }

   public String getInverse() {
      if ("`".equals(this._inverse)) {
         this._inverse = null;
      }

      return this._inverse;
   }

   public void setInverse(String inverse) {
      this._inverses = null;
      this._inverse = inverse;
   }

   public FieldMetaData[] getInverseMetaDatas() {
      if (this._inverses == null) {
         String inv = this.getInverse();
         if (this._mappedBy != null && inv != null && !this._mappedBy.equals(inv)) {
            throw new MetaDataException(_loc.get("mapped-not-inverse", (Object)this));
         }

         ClassMetaData meta = null;
         switch (this.getTypeCode()) {
            case 11:
            case 12:
               meta = this._elem.getTypeMetaData();
            case 13:
            case 14:
            default:
               break;
            case 15:
               meta = this.getTypeMetaData();
         }

         Collection inverses = null;
         if (meta != null) {
            FieldMetaData field = this.getMappedByMetaData();
            if (field != null) {
               if (field.getTypeCode() == 15 || field.getElement().getTypeCode() == 15) {
                  inverses = new ArrayList(3);
                  inverses.add(field);
               }
            } else if (inv != null) {
               field = meta.getField(inv);
               if (field == null) {
                  throw new MetaDataException(_loc.get("no-inverse", this, inv));
               }

               inverses = new ArrayList(3);
               inverses.add(field);
            }

            FieldMetaData[] fields = meta.getFields();
            Class type = this.getDeclaringMetaData().getDescribedType();

            for(int i = 0; i < fields.length; ++i) {
               switch (fields[i].getTypeCode()) {
                  case 11:
                  case 12:
                     if (!type.isAssignableFrom(fields[i].getElement().getType())) {
                        continue;
                     }
                     break;
                  case 13:
                  case 14:
                  default:
                     continue;
                  case 15:
                     if (!type.isAssignableFrom(fields[i].getType())) {
                        continue;
                     }
               }

               if (this._name.equals(fields[i].getMappedBy()) || this._name.equals(fields[i].getInverse())) {
                  if (inverses == null) {
                     inverses = new ArrayList(3);
                  }

                  if (!inverses.contains(fields[i])) {
                     inverses.add(fields[i]);
                  }
               }
            }
         }

         MetaDataRepository repos = this.getRepository();
         if (inverses == null) {
            this._inverses = repos.EMPTY_FIELDS;
         } else {
            this._inverses = (FieldMetaData[])((FieldMetaData[])inverses.toArray(repos.newFieldMetaDataArray(inverses.size())));
         }
      }

      return this._inverses;
   }

   public int getValueStrategy() {
      if (this._valStrategy == -1) {
         this._valStrategy = 0;
      }

      return this._valStrategy;
   }

   public void setValueStrategy(int strategy) {
      this._valStrategy = strategy;
      if (strategy != 2) {
         this.setValueSequenceName((String)null);
      }

   }

   public String getValueSequenceName() {
      if ("`".equals(this._seqName)) {
         this._seqName = null;
      }

      return this._seqName;
   }

   public void setValueSequenceName(String seqName) {
      this._seqName = seqName;
      this._seqMeta = null;
      if (seqName != null) {
         this.setValueStrategy(2);
      }

   }

   public SequenceMetaData getValueSequenceMetaData() {
      if (this._seqMeta == null && this.getValueSequenceName() != null) {
         this._seqMeta = this.getRepository().getSequenceMetaData(this._owner, this.getValueSequenceName(), true);
      }

      return this._seqMeta;
   }

   public int getUpdateStrategy() {
      if (this.isVersion()) {
         return 2;
      } else {
         if (this._upStrategy == -1) {
            this._upStrategy = 0;
         }

         return this._upStrategy;
      }
   }

   public void setUpdateStrategy(int strategy) {
      this._upStrategy = strategy;
   }

   public boolean isLRS() {
      return this._lrs == Boolean.TRUE && this._manage == 3;
   }

   public void setLRS(boolean lrs) {
      this._lrs = lrs ? Boolean.TRUE : Boolean.FALSE;
   }

   public boolean isStream() {
      return this._stream == Boolean.TRUE && this._manage == 3;
   }

   public void setStream(boolean stream) {
      this._stream = stream ? Boolean.TRUE : Boolean.FALSE;
   }

   public boolean usesIntermediate() {
      return this._intermediate;
   }

   public void setUsesIntermediate(boolean intermediate) {
      this._intermediate = intermediate;
      this._owner.clearExtraFieldDataTable();
   }

   public Boolean usesImplData() {
      return this._implData;
   }

   public void setUsesImplData(Boolean implData) {
      this._implData = implData;
      this._owner.clearExtraFieldDataTable();
   }

   public Order[] getOrders() {
      if (this._orders == null) {
         if (this._orderDec == null) {
            this._orders = this.getRepository().EMPTY_ORDERS;
         } else {
            String[] decs = Strings.split(this._orderDec, ",", 0);
            Order[] orders = this.getRepository().newOrderArray(decs.length);

            for(int i = 0; i < decs.length; ++i) {
               decs[i] = decs[i].trim();
               int spc = decs[i].indexOf(32);
               boolean asc;
               if (spc == -1) {
                  asc = true;
               } else {
                  asc = decs[i].substring(spc + 1).trim().toLowerCase().startsWith("asc");
                  decs[i] = decs[i].substring(0, spc);
               }

               orders[i] = this.getRepository().newOrder(this, decs[i], asc);
               ClassMetaData elemCls = this.getElement().getDeclaredTypeMetaData();
               if (elemCls != null) {
                  FieldMetaData fmd = elemCls.getDeclaredField(decs[i]);
                  if (fmd != null) {
                     fmd.setUsedInOrderBy(true);
                  }
               }
            }

            this._orders = orders;
         }
      }

      return this._orders;
   }

   public void setOrders(Order[] orders) {
      this._orderDec = null;
      this._orders = orders;
   }

   public String getOrderDeclaration() {
      if (this._orderDec == null && this._orders != null) {
         StringBuffer buf = new StringBuffer();

         for(int i = 0; i < this._orders.length; ++i) {
            if (i > 0) {
               buf.append(", ");
            }

            buf.append(this._orders[i].getName()).append(" ");
            buf.append(this._orders[i].isAscending() ? "asc" : "desc");
         }

         this._orderDec = buf.toString();
      }

      return this._orderDec;
   }

   public void setOrderDeclaration(String dec) {
      this._orderDec = StringUtils.trimToNull(dec);
      this._orders = null;
   }

   public Object order(Object val) {
      if (val == null) {
         return null;
      } else {
         Order[] orders = this.getOrders();
         if (orders.length == 0) {
            return val;
         } else {
            Object comp;
            if (orders.length == 1) {
               comp = orders[0].getComparator();
            } else {
               List comps = null;

               for(int i = 0; i < orders.length; ++i) {
                  Comparator curComp = orders[i].getComparator();
                  if (curComp != null) {
                     if (comps == null) {
                        comps = new ArrayList(orders.length);
                     }

                     if (i != comps.size()) {
                        throw new MetaDataException(_loc.get("mixed-inmem-ordering", (Object)this));
                     }

                     comps.add(curComp);
                  }
               }

               if (comps == null) {
                  comp = null;
               } else {
                  comp = new ComparatorChain(comps);
               }
            }

            if (comp == null) {
               return val;
            } else {
               switch (this.getTypeCode()) {
                  case 11:
                     List l = JavaTypes.toList(val, this._elem.getType(), true);
                     Collections.sort(l, (Comparator)comp);
                     return JavaTypes.toArray(l, this._elem.getType());
                  case 12:
                     if (val instanceof List) {
                        Collections.sort((List)val, (Comparator)comp);
                     }

                     return val;
                  default:
                     throw new MetaDataException(_loc.get("cant-order", (Object)this));
               }
            }
         }
      }
   }

   public boolean isExternalized() {
      return this.getExternalizerMethod() != null || this.getExternalValueMap() != null;
   }

   public Object getExternalValue(Object val, StoreContext ctx) {
      Map extValues = this.getExternalValueMap();
      if (extValues != null) {
         Object foundVal = extValues.get(val);
         if (foundVal == null) {
            throw (new UserException(_loc.get("bad-externalized-value", new Object[]{val, extValues.keySet(), this}))).setFatal(true).setFailedObject(val);
         } else {
            return foundVal;
         }
      } else {
         Method externalizer = this.getExternalizerMethod();
         if (externalizer == null) {
            return val;
         } else if (val != null && this.getType().isInstance(val) && (!this.getDeclaredType().isInstance(val) || this.getDeclaredType() == Object.class)) {
            return val;
         } else {
            try {
               if (Modifier.isStatic(externalizer.getModifiers())) {
                  return externalizer.getParameterTypes().length == 1 ? externalizer.invoke((Object)null, val) : externalizer.invoke((Object)null, val, ctx);
               } else if (val == null) {
                  return null;
               } else {
                  return externalizer.getParameterTypes().length == 0 ? externalizer.invoke(val, (Object[])null) : externalizer.invoke(val, ctx);
               }
            } catch (OpenJPAException var6) {
               throw var6;
            } catch (Exception var7) {
               throw (new MetaDataException(_loc.get("externalizer-err", this, Exceptions.toString(val), var7.toString()))).setCause(var7);
            }
         }
      }
   }

   public Object getFieldValue(Object val, StoreContext ctx) {
      Map fieldValues = this.getFieldValueMap();
      if (fieldValues != null) {
         return fieldValues.get(val);
      } else {
         Member factory = this.getFactoryMethod();
         if (factory == null) {
            return val;
         } else {
            try {
               if (val == null && this.getNullValue() == 1) {
                  return AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(this.getDeclaredType()));
               } else if (factory instanceof Constructor) {
                  return val == null ? null : ((Constructor)factory).newInstance(val);
               } else {
                  Method meth = (Method)factory;
                  return meth.getParameterTypes().length == 1 ? meth.invoke((Object)null, val) : meth.invoke((Object)null, val, ctx);
               }
            } catch (Exception var7) {
               Exception e = var7;
               if (var7 instanceof InvocationTargetException) {
                  Throwable t = ((InvocationTargetException)var7).getTargetException();
                  if (t instanceof Error) {
                     throw (Error)t;
                  }

                  e = (Exception)t;
                  if (val == null && (e instanceof NullPointerException || e instanceof IllegalArgumentException)) {
                     return null;
                  }
               }

               if (e instanceof OpenJPAException) {
                  throw (OpenJPAException)e;
               } else {
                  if (e instanceof PrivilegedActionException) {
                     e = ((PrivilegedActionException)e).getException();
                  }

                  throw (new MetaDataException(_loc.get("factory-err", this, Exceptions.toString(val), e.toString()))).setCause(e);
               }
            }
         }
      }
   }

   public String getExternalizer() {
      return this._extName;
   }

   public void setExternalizer(String externalizer) {
      this._extName = externalizer;
      this._extMethod = DEFAULT_METHOD;
   }

   public String getFactory() {
      return this._factName;
   }

   public void setFactory(String factory) {
      this._factName = factory;
      this._factMethod = DEFAULT_METHOD;
   }

   public String getExternalValues() {
      return this._extString;
   }

   public void setExternalValues(String values) {
      this._extString = values;
      this._extValues = null;
   }

   public Map getExternalValueMap() {
      this.parseExternalValues();
      return this._extValues;
   }

   public Map getFieldValueMap() {
      this.parseExternalValues();
      return this._fieldValues;
   }

   private void parseExternalValues() {
      if (this._extValues == Collections.EMPTY_MAP || this._fieldValues == Collections.EMPTY_MAP) {
         if (this._extString == null) {
            this._extValues = null;
            this._fieldValues = null;
         } else {
            Options values = Configurations.parseProperties(this._extString);
            if (values.isEmpty()) {
               throw new MetaDataException(_loc.get("no-external-values", this, this._extString));
            } else {
               Map extValues = new HashMap((int)((double)values.size() * 1.33 + 1.0));
               Map fieldValues = new HashMap((int)((double)values.size() * 1.33 + 1.0));
               Iterator itr = values.entrySet().iterator();

               while(itr.hasNext()) {
                  Map.Entry entry = (Map.Entry)itr.next();
                  Object fieldValue = this.transform((String)entry.getKey(), this.getDeclaredTypeCode());
                  Object extValue = this.transform((String)entry.getValue(), this.getTypeCode());
                  extValues.put(fieldValue, extValue);
                  fieldValues.put(extValue, fieldValue);
               }

               this._extValues = extValues;
               this._fieldValues = fieldValues;
            }
         }
      }
   }

   private Object transform(String val, int typeCode) {
      if ("null".equals(val)) {
         return null;
      } else {
         switch (typeCode) {
            case 0:
            case 16:
               return Boolean.valueOf(val);
            case 1:
            case 17:
               return Byte.valueOf(val);
            case 2:
            case 18:
               return new Character(val.charAt(0));
            case 3:
            case 19:
               return Double.valueOf(val);
            case 4:
            case 20:
               return Float.valueOf(val);
            case 5:
            case 21:
               return Integer.valueOf(val);
            case 6:
            case 22:
               return Long.valueOf(val);
            case 7:
            case 23:
               return Short.valueOf(val);
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               throw new MetaDataException(_loc.get("bad-external-type", (Object)this));
            case 9:
               return val;
         }
      }
   }

   public Method getExternalizerMethod() {
      if (this._manage != 3) {
         return null;
      } else {
         if (this._extMethod == DEFAULT_METHOD) {
            if (this._extName != null) {
               this._extMethod = this.findMethod(this._extName);
               if (this._extMethod == null) {
                  throw new MetaDataException(_loc.get("bad-externalizer", this, this._extName));
               }
            } else {
               this._extMethod = null;
            }
         }

         return this._extMethod;
      }
   }

   public Member getFactoryMethod() {
      if (this._manage != 3) {
         return null;
      } else {
         if (this._factMethod == DEFAULT_METHOD) {
            if (this.getExternalizerMethod() == null) {
               this._factMethod = null;
            } else {
               try {
                  if (this._factName == null) {
                     this._factMethod = this.getDeclaredType().getConstructor(this.getType());
                  } else {
                     this._factMethod = this.findMethod(this._factName);
                  }
               } catch (OpenJPAException var2) {
                  throw var2;
               } catch (Exception var3) {
               }

               if (!(this._factMethod instanceof Constructor) && !(this._factMethod instanceof Method)) {
                  throw new MetaDataException(_loc.get("bad-factory", (Object)this));
               }
            }
         }

         return this._factMethod;
      }
   }

   private Method findMethod(String method) {
      if (StringUtils.isEmpty(method)) {
         return null;
      } else {
         String methodName = Strings.getClassName(method);
         String clsName = Strings.getPackageName(method);
         Class cls = null;
         Class owner = this._owner.getDescribedType();
         if (clsName.length() == 0) {
            cls = this.getDeclaredType();
         } else if (!clsName.equals(owner.getName()) && !clsName.equals(Strings.getClassName(owner))) {
            cls = JavaTypes.classForName(clsName, (ValueMetaData)this);
         } else {
            cls = owner;
         }

         Method[] methods = cls.getMethods();

         for(int i = 0; i < methods.length; ++i) {
            if (methods[i].getName().equals(methodName)) {
               Class[] params = methods[i].getParameterTypes();
               if (Modifier.isStatic(methods[i].getModifiers()) && (params.length == 1 || params.length == 2 && isStoreContextParameter(params[1]))) {
                  return methods[i];
               }

               if (!Modifier.isStatic(methods[i].getModifiers()) && (params.length == 0 || params.length == 1 && isStoreContextParameter(params[0]))) {
                  return methods[i];
               }
            }
         }

         return null;
      }
   }

   private static boolean isStoreContextParameter(Class type) {
      return StoreContext.class.getName().equals(type.getName());
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof FieldMetaData) ? false : this.getFullName(true).equals(((FieldMetaData)other).getFullName(true));
      }
   }

   public int hashCode() {
      return this.getFullName(true).hashCode();
   }

   public int compareTo(Object other) {
      return other == null ? 1 : this.getFullName(true).compareTo(((FieldMetaData)other).getFullName(true));
   }

   public String toString() {
      return this.getFullName(true);
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
         Log log = this.getRepository().getLog();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("resolve-field", (Object)(this._owner + "@" + System.identityHashCode(this._owner) + "." + this._name)));
         }

         if ((mode & 1) != 0 && (cur & 1) == 0) {
            Method externalizer = this.getExternalizerMethod();
            if (externalizer != null) {
               this.setType(externalizer.getReturnType());
            }

            this._val.resolve(1);
            this._key.resolve(1);
            this._elem.resolve(1);
            MetaDataRepository repos = this.getRepository();
            int validate = repos.getValidate();
            if ((validate & 1) != 0 && (!ImplHelper.isManagedType(repos.getConfiguration(), this._owner.getDescribedType()) || (validate & 4) == 0)) {
               this.validateLRS();
               if ((validate & 8) == 0) {
                  this.validateSupportedType();
               }

               this.validateValue();
               this.validateExtensionKeys();
            }

            return false;
         } else {
            return false;
         }
      }
   }

   private void validateLRS() {
      if (this.isLRS()) {
         if (this.getTypeCode() == 11) {
            throw new MetaDataException(_loc.get("bad-lrs-array", (Object)this));
         } else if (this.getExternalizerMethod() != null) {
            throw new MetaDataException(_loc.get("bad-lrs-extern", (Object)this));
         } else if (this.getType() != Collection.class && this.getType() != Map.class && this.getType() != Set.class) {
            throw new MetaDataException(_loc.get("bad-lrs-concrete", (Object)this));
         }
      }
   }

   private void validateSupportedType() {
      OpenJPAConfiguration conf = this.getRepository().getConfiguration();
      Collection opts = conf.supportedOptions();
      Log log = conf.getLog("openjpa.MetaData");
      switch (this.getTypeCode()) {
         case 11:
            if (!opts.contains("openjpa.option.Array")) {
               throw new UnsupportedException(_loc.get("type-not-supported", "Array", this));
            }

            if (this._elem.isEmbeddedPC() && !opts.contains("openjpa.option.EmbeddedCollectionRelation")) {
               this._elem.setEmbedded(false);
               if (log.isWarnEnabled()) {
                  log.warn(_loc.get("cant-embed-element", (Object)this));
               }
            }
            break;
         case 12:
            if (!opts.contains("openjpa.option.Collection")) {
               throw new UnsupportedException(_loc.get("type-not-supported", "Collection", this));
            }

            if (this._elem.isEmbeddedPC() && !opts.contains("openjpa.option.EmbeddedCollectionRelation")) {
               this._elem.setEmbedded(false);
               if (log.isWarnEnabled()) {
                  log.warn(_loc.get("cant-embed-element", (Object)this));
               }
            }
            break;
         case 13:
            if (!opts.contains("openjpa.option.Map")) {
               throw new UnsupportedException(_loc.get("type-not-supported", "Map", this));
            }

            if (this._elem.isEmbeddedPC() && !opts.contains("openjpa.option.EmbeddedMapRelation")) {
               this._elem.setEmbedded(false);
               if (log.isWarnEnabled()) {
                  log.warn(_loc.get("cant-embed-element", (Object)this));
               }
            }

            if (this._key.isEmbeddedPC() && !opts.contains("openjpa.option.EmbeddedMapRelation")) {
               this._key.setEmbedded(false);
               if (log.isWarnEnabled()) {
                  log.warn(_loc.get("cant-embed-key", (Object)this));
               }
            }
         case 14:
         default:
            break;
         case 15:
            if (this.isEmbedded() && !opts.contains("openjpa.option.EmbeddedRelation")) {
               this.setEmbedded(false);
               if (log.isWarnEnabled()) {
                  log.warn(_loc.get("cant-embed", (Object)this));
               }
            } else if (this.isEmbedded() && this.getDeclaredTypeCode() != 15) {
               this.setEmbedded(false);
               if (log.isWarnEnabled()) {
                  log.warn(_loc.get("cant-embed-extern", (Object)this));
               }
            }
      }

   }

   private void validateValue() {
      if (this.getExternalizerMethod() != null && this.getExternalValueMap() != null) {
         throw new MetaDataException(_loc.get("extern-externvalues", (Object)this));
      } else if (this.getValueStrategy() == 2 && this.getValueSequenceName() == null) {
         throw new MetaDataException(_loc.get("no-seq-name", (Object)this));
      } else {
         ValueStrategies.assertSupported(this.getValueStrategy(), this, "value strategy");
      }
   }

   public void copy(FieldMetaData field) {
      super.copy(field);
      this._intermediate = field.usesIntermediate();
      this._implData = field.usesImplData();
      this._proxyClass = field.getProxyType();
      this._initializer = field.getInitializer();
      this._transient = field.isTransient();
      this._nullValue = field.getNullValue();
      this._manage = field.getManagement();
      this._explicit = field.isExplicit();
      this._extName = field.getExternalizer();
      this._extMethod = DEFAULT_METHOD;
      this._factName = field.getFactory();
      this._factMethod = DEFAULT_METHOD;
      this._extString = field.getExternalValues();
      this._extValues = Collections.EMPTY_MAP;
      this._fieldValues = Collections.EMPTY_MAP;
      this._primKey = field.isPrimaryKey();
      this._backingMember = field._backingMember;
      this._enumField = field._enumField;
      this._lobField = field._lobField;
      this._serializableField = field._serializableField;
      this._generated = field._generated;
      if (this._owner.getEmbeddingMetaData() == null && this._version == null) {
         this._version = field.isVersion() ? Boolean.TRUE : Boolean.FALSE;
      }

      if (this._dfg == 0) {
         this._dfg = field.isInDefaultFetchGroup() ? 2 : 1;
         if (field.isDefaultFetchGroupExplicit()) {
            this._dfg |= 4;
         }
      }

      if (this._fgSet == null && field._fgSet != null) {
         this._fgSet = new HashSet(field._fgSet);
      }

      if (this._lfg == null) {
         this._lfg = field.getLoadFetchGroup();
      }

      if (this._lrs == null) {
         this._lrs = field.isLRS() ? Boolean.TRUE : Boolean.FALSE;
      }

      if (this._valStrategy == -1) {
         this._valStrategy = field.getValueStrategy();
      }

      if (this._upStrategy == -1) {
         this._upStrategy = field.getUpdateStrategy();
      }

      if ("`".equals(this._seqName)) {
         this._seqName = field.getValueSequenceName();
         this._seqMeta = null;
      }

      if ("`".equals(this._inverse)) {
         this._inverse = field.getInverse();
      }

      this._val.copy(field);
      this._key.copy(field.getKey());
      this._elem.copy(field.getElement());
   }

   protected void addExtensionKeys(Collection exts) {
      this.getRepository().getMetaDataFactory().addFieldExtensionKeys(exts);
   }

   public String[] getComments() {
      return this._comments == null ? EMPTY_COMMENTS : this._comments;
   }

   public void setComments(String[] comments) {
      this._comments = comments;
   }

   public FieldMetaData getFieldMetaData() {
      return this;
   }

   public Class getType() {
      return this._val.getType();
   }

   public void setType(Class type) {
      this._val.setType(type);
      if (type.isArray()) {
         this._elem.setType(type.getComponentType());
      } else if (type == Properties.class) {
         this._key.setType(String.class);
         this._elem.setType(String.class);
      }

   }

   public int getTypeCode() {
      return this._val.getTypeCode();
   }

   public void setTypeCode(int code) {
      this._val.setTypeCode(code);
   }

   public boolean isTypePC() {
      return this._val.isTypePC();
   }

   public ClassMetaData getTypeMetaData() {
      return this._val.getTypeMetaData();
   }

   public Class getDeclaredType() {
      return this._val.getDeclaredType();
   }

   public void setDeclaredType(Class type) {
      this._val.setDeclaredType(type);
      if (type.isArray()) {
         this._elem.setDeclaredType(type.getComponentType());
      } else if (type == Properties.class) {
         this._key.setDeclaredType(String.class);
         this._elem.setDeclaredType(String.class);
      }

   }

   public int getDeclaredTypeCode() {
      return this._val.getDeclaredTypeCode();
   }

   public void setDeclaredTypeCode(int type) {
      this._val.setDeclaredTypeCode(type);
   }

   public boolean isDeclaredTypePC() {
      return this._val.isDeclaredTypePC();
   }

   public ClassMetaData getDeclaredTypeMetaData() {
      return this._val.getDeclaredTypeMetaData();
   }

   public boolean isEmbedded() {
      return this._val.isEmbedded();
   }

   public void setEmbedded(boolean embedded) {
      this._val.setEmbedded(embedded);
   }

   public boolean isEmbeddedPC() {
      return this._val.isEmbeddedPC();
   }

   public ClassMetaData getEmbeddedMetaData() {
      return this._val.getEmbeddedMetaData();
   }

   public ClassMetaData addEmbeddedMetaData() {
      return this._val.addEmbeddedMetaData();
   }

   public int getCascadeDelete() {
      return this._val.getCascadeDelete();
   }

   public void setCascadeDelete(int delete) {
      this._val.setCascadeDelete(delete);
   }

   public int getCascadePersist() {
      return this._val.getCascadePersist();
   }

   public void setCascadePersist(int persist) {
      this._val.setCascadePersist(persist);
   }

   public int getCascadeAttach() {
      return this._val.getCascadeAttach();
   }

   public void setCascadeAttach(int attach) {
      this._val.setCascadeAttach(attach);
   }

   public int getCascadeRefresh() {
      return this._val.getCascadeRefresh();
   }

   public void setCascadeRefresh(int refresh) {
      this._val.setCascadeRefresh(refresh);
   }

   public boolean isSerialized() {
      return this._val.isSerialized();
   }

   public void setSerialized(boolean serialized) {
      this._val.setSerialized(serialized);
   }

   public String getValueMappedBy() {
      return this._val.getValueMappedBy();
   }

   public void setValueMappedBy(String mapped) {
      this._val.setValueMappedBy(mapped);
   }

   public FieldMetaData getValueMappedByMetaData() {
      return this._val.getValueMappedByMetaData();
   }

   public Class getTypeOverride() {
      return this._val.getTypeOverride();
   }

   public void setTypeOverride(Class type) {
      this._val.setTypeOverride(type);
   }

   public void copy(ValueMetaData vmd) {
      this._val.copy(vmd);
   }

   public boolean isUsedInOrderBy() {
      return this._usedInOrderBy;
   }

   public void setUsedInOrderBy(boolean isUsed) {
      this._usedInOrderBy = isUsed;
   }

   public boolean isValueGenerated() {
      return this._generated;
   }

   public void setValueGenerated(boolean generated) {
      this._generated = generated;
   }

   static {
      try {
         DEFAULT_METHOD = Object.class.getMethod("wait", (Class[])null);
      } catch (Exception var1) {
         throw new InternalException(var1);
      }
   }

   public static class MemberProvider implements Externalizable {
      private transient Member _member;

      public MemberProvider() {
      }

      MemberProvider(Member member) {
         if (member instanceof Constructor) {
            throw new IllegalArgumentException();
         } else {
            this._member = member;
         }
      }

      public Member getMember() {
         return this._member;
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         boolean isField = in.readBoolean();
         Class cls = (Class)in.readObject();
         String memberName = (String)in.readObject();

         IOException ioe;
         try {
            if (isField) {
               this._member = (Field)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredFieldAction(cls, memberName));
            } else {
               Class[] parameterTypes = (Class[])((Class[])in.readObject());
               this._member = (Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(cls, memberName, parameterTypes));
            }

         } catch (SecurityException var7) {
            ioe = new IOException(var7.getMessage());
            ioe.initCause(var7);
            throw ioe;
         } catch (PrivilegedActionException var8) {
            ioe = new IOException(var8.getException().getMessage());
            ioe.initCause(var8);
            throw ioe;
         }
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         boolean isField = this._member instanceof Field;
         out.writeBoolean(isField);
         out.writeObject(this._member.getDeclaringClass());
         out.writeObject(this._member.getName());
         if (!isField) {
            out.writeObject(((Method)this._member).getParameterTypes());
         }

      }
   }
}
