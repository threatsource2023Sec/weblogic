package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CoherencePartitionCacheConfigMBeanImpl extends ConfigurationMBeanImpl implements CoherencePartitionCacheConfigMBean, Serializable {
   private String _ApplicationName;
   private String _CacheName;
   private CoherencePartitionCachePropertyMBean[] _CoherencePartitionCacheProperties;
   private boolean _Shared;
   private static SchemaHelper2 _schemaHelper;

   public CoherencePartitionCacheConfigMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherencePartitionCacheConfigMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherencePartitionCacheConfigMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isShared() {
      return this._Shared;
   }

   public boolean isSharedInherited() {
      return false;
   }

   public boolean isSharedSet() {
      return this._isSet(10);
   }

   public void setShared(boolean param0) {
      boolean _oldVal = this._Shared;
      this._Shared = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getCacheName() {
      return this._CacheName;
   }

   public boolean isCacheNameInherited() {
      return false;
   }

   public boolean isCacheNameSet() {
      return this._isSet(11);
   }

   public void setCacheName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CacheName;
      this._CacheName = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getApplicationName() {
      return this._ApplicationName;
   }

   public boolean isApplicationNameInherited() {
      return false;
   }

   public boolean isApplicationNameSet() {
      return this._isSet(12);
   }

   public void setApplicationName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ApplicationName;
      this._ApplicationName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public void addCoherencePartitionCacheProperty(CoherencePartitionCachePropertyMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         CoherencePartitionCachePropertyMBean[] _new;
         if (this._isSet(13)) {
            _new = (CoherencePartitionCachePropertyMBean[])((CoherencePartitionCachePropertyMBean[])this._getHelper()._extendArray(this.getCoherencePartitionCacheProperties(), CoherencePartitionCachePropertyMBean.class, param0));
         } else {
            _new = new CoherencePartitionCachePropertyMBean[]{param0};
         }

         try {
            this.setCoherencePartitionCacheProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherencePartitionCachePropertyMBean[] getCoherencePartitionCacheProperties() {
      return this._CoherencePartitionCacheProperties;
   }

   public boolean isCoherencePartitionCachePropertiesInherited() {
      return false;
   }

   public boolean isCoherencePartitionCachePropertiesSet() {
      return this._isSet(13);
   }

   public void removeCoherencePartitionCacheProperty(CoherencePartitionCachePropertyMBean param0) {
      this.destroyCoherencePartitionCacheProperty(param0);
   }

   public void setCoherencePartitionCacheProperties(CoherencePartitionCachePropertyMBean[] param0) throws InvalidAttributeValueException {
      CoherencePartitionCachePropertyMBean[] param0 = param0 == null ? new CoherencePartitionCachePropertyMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CoherencePartitionCachePropertyMBean[] _oldVal = this._CoherencePartitionCacheProperties;
      this._CoherencePartitionCacheProperties = (CoherencePartitionCachePropertyMBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public CoherencePartitionCachePropertyMBean lookupCoherencePartitionCacheProperty(String param0) {
      Object[] aary = (Object[])this._CoherencePartitionCacheProperties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherencePartitionCachePropertyMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherencePartitionCachePropertyMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public CoherencePartitionCachePropertyMBean createCoherencePartitionCacheProperty(String param0) {
      CoherencePartitionCachePropertyMBeanImpl lookup = (CoherencePartitionCachePropertyMBeanImpl)this.lookupCoherencePartitionCacheProperty(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherencePartitionCachePropertyMBeanImpl _val = new CoherencePartitionCachePropertyMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherencePartitionCacheProperty(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyCoherencePartitionCacheProperty(CoherencePartitionCachePropertyMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         CoherencePartitionCachePropertyMBean[] _old = this.getCoherencePartitionCacheProperties();
         CoherencePartitionCachePropertyMBean[] _new = (CoherencePartitionCachePropertyMBean[])((CoherencePartitionCachePropertyMBean[])this._getHelper()._removeElement(_old, CoherencePartitionCachePropertyMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCoherencePartitionCacheProperties(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      CoherenceValidator.validateCoherencePartitionCacheConfiguration(this);
      LegalChecks.checkIsSet("ApplicationName", this.isApplicationNameSet());
      LegalChecks.checkIsSet("CacheName", this.isCacheNameSet());
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._ApplicationName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._CacheName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._CoherencePartitionCacheProperties = new CoherencePartitionCachePropertyMBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._Shared = false;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "CoherencePartitionCacheConfig";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ApplicationName")) {
         oldVal = this._ApplicationName;
         this._ApplicationName = (String)v;
         this._postSet(12, oldVal, this._ApplicationName);
      } else if (name.equals("CacheName")) {
         oldVal = this._CacheName;
         this._CacheName = (String)v;
         this._postSet(11, oldVal, this._CacheName);
      } else if (name.equals("CoherencePartitionCacheProperties")) {
         CoherencePartitionCachePropertyMBean[] oldVal = this._CoherencePartitionCacheProperties;
         this._CoherencePartitionCacheProperties = (CoherencePartitionCachePropertyMBean[])((CoherencePartitionCachePropertyMBean[])v);
         this._postSet(13, oldVal, this._CoherencePartitionCacheProperties);
      } else if (name.equals("Shared")) {
         boolean oldVal = this._Shared;
         this._Shared = (Boolean)v;
         this._postSet(10, oldVal, this._Shared);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ApplicationName")) {
         return this._ApplicationName;
      } else if (name.equals("CacheName")) {
         return this._CacheName;
      } else if (name.equals("CoherencePartitionCacheProperties")) {
         return this._CoherencePartitionCacheProperties;
      } else {
         return name.equals("Shared") ? new Boolean(this._Shared) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("shared")) {
                  return 10;
               }
               break;
            case 10:
               if (s.equals("cache-name")) {
                  return 11;
               }
               break;
            case 16:
               if (s.equals("application-name")) {
                  return 12;
               }
               break;
            case 34:
               if (s.equals("coherence-partition-cache-property")) {
                  return 13;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 13:
               return new CoherencePartitionCachePropertyMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "shared";
            case 11:
               return "cache-name";
            case 12:
               return "application-name";
            case 13:
               return "coherence-partition-cache-property";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 13:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 13:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private CoherencePartitionCacheConfigMBeanImpl bean;

      protected Helper(CoherencePartitionCacheConfigMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Shared";
            case 11:
               return "CacheName";
            case 12:
               return "ApplicationName";
            case 13:
               return "CoherencePartitionCacheProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ApplicationName")) {
            return 12;
         } else if (propName.equals("CacheName")) {
            return 11;
         } else if (propName.equals("CoherencePartitionCacheProperties")) {
            return 13;
         } else {
            return propName.equals("Shared") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCoherencePartitionCacheProperties()));
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isApplicationNameSet()) {
               buf.append("ApplicationName");
               buf.append(String.valueOf(this.bean.getApplicationName()));
            }

            if (this.bean.isCacheNameSet()) {
               buf.append("CacheName");
               buf.append(String.valueOf(this.bean.getCacheName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getCoherencePartitionCacheProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherencePartitionCacheProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSharedSet()) {
               buf.append("Shared");
               buf.append(String.valueOf(this.bean.isShared()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            CoherencePartitionCacheConfigMBeanImpl otherTyped = (CoherencePartitionCacheConfigMBeanImpl)other;
            this.computeDiff("ApplicationName", this.bean.getApplicationName(), otherTyped.getApplicationName(), false);
            this.computeDiff("CacheName", this.bean.getCacheName(), otherTyped.getCacheName(), false);
            this.computeChildDiff("CoherencePartitionCacheProperties", this.bean.getCoherencePartitionCacheProperties(), otherTyped.getCoherencePartitionCacheProperties(), true);
            this.computeDiff("Shared", this.bean.isShared(), otherTyped.isShared(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherencePartitionCacheConfigMBeanImpl original = (CoherencePartitionCacheConfigMBeanImpl)event.getSourceBean();
            CoherencePartitionCacheConfigMBeanImpl proposed = (CoherencePartitionCacheConfigMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ApplicationName")) {
                  original.setApplicationName(proposed.getApplicationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("CacheName")) {
                  original.setCacheName(proposed.getCacheName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("CoherencePartitionCacheProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherencePartitionCacheProperty((CoherencePartitionCachePropertyMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherencePartitionCacheProperty((CoherencePartitionCachePropertyMBean)update.getRemovedObject());
                  }

                  if (original.getCoherencePartitionCacheProperties() == null || original.getCoherencePartitionCacheProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("Shared")) {
                  original.setShared(proposed.isShared());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            CoherencePartitionCacheConfigMBeanImpl copy = (CoherencePartitionCacheConfigMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ApplicationName")) && this.bean.isApplicationNameSet()) {
               copy.setApplicationName(this.bean.getApplicationName());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheName")) && this.bean.isCacheNameSet()) {
               copy.setCacheName(this.bean.getCacheName());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherencePartitionCacheProperties")) && this.bean.isCoherencePartitionCachePropertiesSet() && !copy._isSet(13)) {
               CoherencePartitionCachePropertyMBean[] oldCoherencePartitionCacheProperties = this.bean.getCoherencePartitionCacheProperties();
               CoherencePartitionCachePropertyMBean[] newCoherencePartitionCacheProperties = new CoherencePartitionCachePropertyMBean[oldCoherencePartitionCacheProperties.length];

               for(int i = 0; i < newCoherencePartitionCacheProperties.length; ++i) {
                  newCoherencePartitionCacheProperties[i] = (CoherencePartitionCachePropertyMBean)((CoherencePartitionCachePropertyMBean)this.createCopy((AbstractDescriptorBean)oldCoherencePartitionCacheProperties[i], includeObsolete));
               }

               copy.setCoherencePartitionCacheProperties(newCoherencePartitionCacheProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("Shared")) && this.bean.isSharedSet()) {
               copy.setShared(this.bean.isShared());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getCoherencePartitionCacheProperties(), clazz, annotation);
      }
   }
}
