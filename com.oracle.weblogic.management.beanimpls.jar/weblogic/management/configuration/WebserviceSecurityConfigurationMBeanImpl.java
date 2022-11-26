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

public class WebserviceSecurityConfigurationMBeanImpl extends ConfigurationMBeanImpl implements WebserviceSecurityConfigurationMBean, Serializable {
   private String _ClassName;
   private ConfigurationPropertyMBean[] _ConfigurationProperties;
   private String _TokenType;
   private static SchemaHelper2 _schemaHelper;

   public WebserviceSecurityConfigurationMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebserviceSecurityConfigurationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebserviceSecurityConfigurationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getClassName() {
      return this._ClassName;
   }

   public boolean isClassNameInherited() {
      return false;
   }

   public boolean isClassNameSet() {
      return this._isSet(10);
   }

   public void setClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClassName;
      this._ClassName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public void setTokenType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TokenType;
      this._TokenType = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getTokenType() {
      return this._TokenType;
   }

   public boolean isTokenTypeInherited() {
      return false;
   }

   public boolean isTokenTypeSet() {
      return this._isSet(11);
   }

   public void addConfigurationProperty(ConfigurationPropertyMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         ConfigurationPropertyMBean[] _new;
         if (this._isSet(12)) {
            _new = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._extendArray(this.getConfigurationProperties(), ConfigurationPropertyMBean.class, param0));
         } else {
            _new = new ConfigurationPropertyMBean[]{param0};
         }

         try {
            this.setConfigurationProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConfigurationPropertyMBean[] getConfigurationProperties() {
      return this._ConfigurationProperties;
   }

   public boolean isConfigurationPropertiesInherited() {
      return false;
   }

   public boolean isConfigurationPropertiesSet() {
      return this._isSet(12);
   }

   public void removeConfigurationProperty(ConfigurationPropertyMBean param0) {
      this.destroyConfigurationProperty(param0);
   }

   public void setConfigurationProperties(ConfigurationPropertyMBean[] param0) throws InvalidAttributeValueException {
      ConfigurationPropertyMBean[] param0 = param0 == null ? new ConfigurationPropertyMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ConfigurationPropertyMBean[] _oldVal = this._ConfigurationProperties;
      this._ConfigurationProperties = (ConfigurationPropertyMBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public ConfigurationPropertyMBean lookupConfigurationProperty(String param0) {
      Object[] aary = (Object[])this._ConfigurationProperties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ConfigurationPropertyMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ConfigurationPropertyMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ConfigurationPropertyMBean createConfigurationProperty(String param0) {
      ConfigurationPropertyMBeanImpl lookup = (ConfigurationPropertyMBeanImpl)this.lookupConfigurationProperty(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ConfigurationPropertyMBeanImpl _val = new ConfigurationPropertyMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addConfigurationProperty(_val);
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

   public void destroyConfigurationProperty(ConfigurationPropertyMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         ConfigurationPropertyMBean[] _old = this.getConfigurationProperties();
         ConfigurationPropertyMBean[] _new = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._removeElement(_old, ConfigurationPropertyMBean.class, param0));
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
               this.setConfigurationProperties(_new);
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
      LegalChecks.checkIsSet("ClassName", this.isClassNameSet());
      LegalChecks.checkIsSet("TokenType", this.isTokenTypeSet());
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._ClassName = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._ConfigurationProperties = new ConfigurationPropertyMBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._TokenType = null;
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
      return "WebserviceSecurityConfiguration";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ClassName")) {
         oldVal = this._ClassName;
         this._ClassName = (String)v;
         this._postSet(10, oldVal, this._ClassName);
      } else if (name.equals("ConfigurationProperties")) {
         ConfigurationPropertyMBean[] oldVal = this._ConfigurationProperties;
         this._ConfigurationProperties = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])v);
         this._postSet(12, oldVal, this._ConfigurationProperties);
      } else if (name.equals("TokenType")) {
         oldVal = this._TokenType;
         this._TokenType = (String)v;
         this._postSet(11, oldVal, this._TokenType);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ClassName")) {
         return this._ClassName;
      } else if (name.equals("ConfigurationProperties")) {
         return this._ConfigurationProperties;
      } else {
         return name.equals("TokenType") ? this._TokenType : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("class-name")) {
                  return 10;
               }

               if (s.equals("token-type")) {
                  return 11;
               }
               break;
            case 22:
               if (s.equals("configuration-property")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 12:
               return new ConfigurationPropertyMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "class-name";
            case 11:
               return "token-type";
            case 12:
               return "configuration-property";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 12:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 12:
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
      private WebserviceSecurityConfigurationMBeanImpl bean;

      protected Helper(WebserviceSecurityConfigurationMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ClassName";
            case 11:
               return "TokenType";
            case 12:
               return "ConfigurationProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClassName")) {
            return 10;
         } else if (propName.equals("ConfigurationProperties")) {
            return 12;
         } else {
            return propName.equals("TokenType") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
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
            if (this.bean.isClassNameSet()) {
               buf.append("ClassName");
               buf.append(String.valueOf(this.bean.getClassName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getConfigurationProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConfigurationProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTokenTypeSet()) {
               buf.append("TokenType");
               buf.append(String.valueOf(this.bean.getTokenType()));
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
            WebserviceSecurityConfigurationMBeanImpl otherTyped = (WebserviceSecurityConfigurationMBeanImpl)other;
            this.computeDiff("ClassName", this.bean.getClassName(), otherTyped.getClassName(), true);
            this.computeChildDiff("ConfigurationProperties", this.bean.getConfigurationProperties(), otherTyped.getConfigurationProperties(), true);
            this.computeDiff("TokenType", this.bean.getTokenType(), otherTyped.getTokenType(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebserviceSecurityConfigurationMBeanImpl original = (WebserviceSecurityConfigurationMBeanImpl)event.getSourceBean();
            WebserviceSecurityConfigurationMBeanImpl proposed = (WebserviceSecurityConfigurationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClassName")) {
                  original.setClassName(proposed.getClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ConfigurationProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConfigurationProperty((ConfigurationPropertyMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConfigurationProperty((ConfigurationPropertyMBean)update.getRemovedObject());
                  }

                  if (original.getConfigurationProperties() == null || original.getConfigurationProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("TokenType")) {
                  original.setTokenType(proposed.getTokenType());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            WebserviceSecurityConfigurationMBeanImpl copy = (WebserviceSecurityConfigurationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClassName")) && this.bean.isClassNameSet()) {
               copy.setClassName(this.bean.getClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigurationProperties")) && this.bean.isConfigurationPropertiesSet() && !copy._isSet(12)) {
               ConfigurationPropertyMBean[] oldConfigurationProperties = this.bean.getConfigurationProperties();
               ConfigurationPropertyMBean[] newConfigurationProperties = new ConfigurationPropertyMBean[oldConfigurationProperties.length];

               for(int i = 0; i < newConfigurationProperties.length; ++i) {
                  newConfigurationProperties[i] = (ConfigurationPropertyMBean)((ConfigurationPropertyMBean)this.createCopy((AbstractDescriptorBean)oldConfigurationProperties[i], includeObsolete));
               }

               copy.setConfigurationProperties(newConfigurationProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("TokenType")) && this.bean.isTokenTypeSet()) {
               copy.setTokenType(this.bean.getTokenType());
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
         this.inferSubTree(this.bean.getConfigurationProperties(), clazz, annotation);
      }
   }
}
