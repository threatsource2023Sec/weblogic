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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JASPICMBeanImpl extends ConfigurationMBeanImpl implements JASPICMBean, Serializable {
   private AuthConfigProviderMBean[] _AuthConfigProviders;
   private boolean _Enabled;
   private static SchemaHelper2 _schemaHelper;

   public JASPICMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JASPICMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JASPICMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(10);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public void addAuthConfigProvider(AuthConfigProviderMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         AuthConfigProviderMBean[] _new;
         if (this._isSet(11)) {
            _new = (AuthConfigProviderMBean[])((AuthConfigProviderMBean[])this._getHelper()._extendArray(this.getAuthConfigProviders(), AuthConfigProviderMBean.class, param0));
         } else {
            _new = new AuthConfigProviderMBean[]{param0};
         }

         try {
            this.setAuthConfigProviders(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AuthConfigProviderMBean[] getAuthConfigProviders() {
      return this._AuthConfigProviders;
   }

   public boolean isAuthConfigProvidersInherited() {
      return false;
   }

   public boolean isAuthConfigProvidersSet() {
      return this._isSet(11);
   }

   public void removeAuthConfigProvider(AuthConfigProviderMBean param0) {
      this.destroyAuthConfigProvider(param0);
   }

   public void setAuthConfigProviders(AuthConfigProviderMBean[] param0) throws InvalidAttributeValueException {
      AuthConfigProviderMBean[] param0 = param0 == null ? new AuthConfigProviderMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      AuthConfigProviderMBean[] _oldVal = this._AuthConfigProviders;
      this._AuthConfigProviders = (AuthConfigProviderMBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public AuthConfigProviderMBean lookupAuthConfigProvider(String param0) {
      Object[] aary = (Object[])this._AuthConfigProviders;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      AuthConfigProviderMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (AuthConfigProviderMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public AuthConfigProviderMBean createAuthConfigProvider(String param0) {
      AuthConfigProviderMBeanImpl lookup = (AuthConfigProviderMBeanImpl)this.lookupAuthConfigProvider(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         AuthConfigProviderMBeanImpl _val = new AuthConfigProviderMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addAuthConfigProvider(_val);
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

   public void destroyAuthConfigProvider(AuthConfigProviderMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         AuthConfigProviderMBean[] _old = this.getAuthConfigProviders();
         AuthConfigProviderMBean[] _new = (AuthConfigProviderMBean[])((AuthConfigProviderMBean[])this._getHelper()._removeElement(_old, AuthConfigProviderMBean.class, param0));
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
               this.setAuthConfigProviders(_new);
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

   public CustomAuthConfigProviderMBean createCustomAuthConfigProvider(String param0) {
      CustomAuthConfigProviderMBeanImpl lookup = (CustomAuthConfigProviderMBeanImpl)this.lookupAuthConfigProvider(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CustomAuthConfigProviderMBeanImpl _val = new CustomAuthConfigProviderMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addAuthConfigProvider(_val);
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

   public WLSAuthConfigProviderMBean createWLSAuthConfigProvider(String param0) {
      WLSAuthConfigProviderMBeanImpl lookup = (WLSAuthConfigProviderMBeanImpl)this.lookupAuthConfigProvider(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLSAuthConfigProviderMBeanImpl _val = new WLSAuthConfigProviderMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addAuthConfigProvider(_val);
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

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._AuthConfigProviders = new AuthConfigProviderMBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._Enabled = true;
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
      return "JASPIC";
   }

   public void putValue(String name, Object v) {
      if (name.equals("AuthConfigProviders")) {
         AuthConfigProviderMBean[] oldVal = this._AuthConfigProviders;
         this._AuthConfigProviders = (AuthConfigProviderMBean[])((AuthConfigProviderMBean[])v);
         this._postSet(11, oldVal, this._AuthConfigProviders);
      } else if (name.equals("Enabled")) {
         boolean oldVal = this._Enabled;
         this._Enabled = (Boolean)v;
         this._postSet(10, oldVal, this._Enabled);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("AuthConfigProviders")) {
         return this._AuthConfigProviders;
      } else {
         return name.equals("Enabled") ? new Boolean(this._Enabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("enabled")) {
                  return 10;
               }
               break;
            case 20:
               if (s.equals("auth-config-provider")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 11:
               return new AuthConfigProviderMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "enabled";
            case 11:
               return "auth-config-provider";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 11:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 11:
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
      private JASPICMBeanImpl bean;

      protected Helper(JASPICMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Enabled";
            case 11:
               return "AuthConfigProviders";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthConfigProviders")) {
            return 11;
         } else {
            return propName.equals("Enabled") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAuthConfigProviders()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getAuthConfigProviders().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAuthConfigProviders()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            JASPICMBeanImpl otherTyped = (JASPICMBeanImpl)other;
            this.computeChildDiff("AuthConfigProviders", this.bean.getAuthConfigProviders(), otherTyped.getAuthConfigProviders(), false);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JASPICMBeanImpl original = (JASPICMBeanImpl)event.getSourceBean();
            JASPICMBeanImpl proposed = (JASPICMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AuthConfigProviders")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAuthConfigProvider((AuthConfigProviderMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAuthConfigProvider((AuthConfigProviderMBean)update.getRemovedObject());
                  }

                  if (original.getAuthConfigProviders() == null || original.getAuthConfigProviders().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
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
            JASPICMBeanImpl copy = (JASPICMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthConfigProviders")) && this.bean.isAuthConfigProvidersSet() && !copy._isSet(11)) {
               AuthConfigProviderMBean[] oldAuthConfigProviders = this.bean.getAuthConfigProviders();
               AuthConfigProviderMBean[] newAuthConfigProviders = new AuthConfigProviderMBean[oldAuthConfigProviders.length];

               for(int i = 0; i < newAuthConfigProviders.length; ++i) {
                  newAuthConfigProviders[i] = (AuthConfigProviderMBean)((AuthConfigProviderMBean)this.createCopy((AbstractDescriptorBean)oldAuthConfigProviders[i], includeObsolete));
               }

               copy.setAuthConfigProviders(newAuthConfigProviders);
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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
         this.inferSubTree(this.bean.getAuthConfigProviders(), clazz, annotation);
      }
   }
}
