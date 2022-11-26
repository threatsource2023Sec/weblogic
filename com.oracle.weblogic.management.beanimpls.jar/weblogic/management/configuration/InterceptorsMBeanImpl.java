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

public class InterceptorsMBeanImpl extends ConfigurationMBeanImpl implements InterceptorsMBean, Serializable {
   private InterceptorMBean[] _Interceptors;
   private boolean _WhiteListingEnabled;
   private static SchemaHelper2 _schemaHelper;

   public InterceptorsMBeanImpl() {
      this._initializeProperty(-1);
   }

   public InterceptorsMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InterceptorsMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isWhiteListingEnabled() {
      return this._WhiteListingEnabled;
   }

   public boolean isWhiteListingEnabledInherited() {
      return false;
   }

   public boolean isWhiteListingEnabledSet() {
      return this._isSet(10);
   }

   public void setWhiteListingEnabled(boolean param0) {
      boolean _oldVal = this._WhiteListingEnabled;
      this._WhiteListingEnabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public void addInterceptor(InterceptorMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         InterceptorMBean[] _new;
         if (this._isSet(11)) {
            _new = (InterceptorMBean[])((InterceptorMBean[])this._getHelper()._extendArray(this.getInterceptors(), InterceptorMBean.class, param0));
         } else {
            _new = new InterceptorMBean[]{param0};
         }

         try {
            this.setInterceptors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InterceptorMBean[] getInterceptors() {
      return this._Interceptors;
   }

   public boolean isInterceptorsInherited() {
      return false;
   }

   public boolean isInterceptorsSet() {
      return this._isSet(11);
   }

   public void removeInterceptor(InterceptorMBean param0) {
      this.destroyInterceptor(param0);
   }

   public void setInterceptors(InterceptorMBean[] param0) throws InvalidAttributeValueException {
      InterceptorMBean[] param0 = param0 == null ? new InterceptorMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InterceptorMBean[] _oldVal = this._Interceptors;
      this._Interceptors = (InterceptorMBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public InterceptorMBean lookupInterceptor(String param0) {
      Object[] aary = (Object[])this._Interceptors;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      InterceptorMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (InterceptorMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public InterceptorMBean createInterceptor(String param0) {
      InterceptorMBeanImpl lookup = (InterceptorMBeanImpl)this.lookupInterceptor(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         InterceptorMBeanImpl _val = new InterceptorMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addInterceptor(_val);
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

   public ScriptInterceptorMBean createScriptInterceptor(String param0) {
      ScriptInterceptorMBeanImpl lookup = (ScriptInterceptorMBeanImpl)this.lookupInterceptor(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ScriptInterceptorMBeanImpl _val = new ScriptInterceptorMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addInterceptor(_val);
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

   public DatasourceInterceptorMBean createDatasourceInterceptor(String param0) {
      DatasourceInterceptorMBeanImpl lookup = (DatasourceInterceptorMBeanImpl)this.lookupInterceptor(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         DatasourceInterceptorMBeanImpl _val = new DatasourceInterceptorMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addInterceptor(_val);
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

   public void destroyInterceptor(InterceptorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         InterceptorMBean[] _old = this.getInterceptors();
         InterceptorMBean[] _new = (InterceptorMBean[])((InterceptorMBean[])this._getHelper()._removeElement(_old, InterceptorMBean.class, param0));
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
               this.setInterceptors(_new);
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
               this._Interceptors = new InterceptorMBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._WhiteListingEnabled = false;
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
      return "Interceptors";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Interceptors")) {
         InterceptorMBean[] oldVal = this._Interceptors;
         this._Interceptors = (InterceptorMBean[])((InterceptorMBean[])v);
         this._postSet(11, oldVal, this._Interceptors);
      } else if (name.equals("WhiteListingEnabled")) {
         boolean oldVal = this._WhiteListingEnabled;
         this._WhiteListingEnabled = (Boolean)v;
         this._postSet(10, oldVal, this._WhiteListingEnabled);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Interceptors")) {
         return this._Interceptors;
      } else {
         return name.equals("WhiteListingEnabled") ? new Boolean(this._WhiteListingEnabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("interceptor")) {
                  return 11;
               }
               break;
            case 21:
               if (s.equals("white-listing-enabled")) {
                  return 10;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 11:
               return new InterceptorMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "white-listing-enabled";
            case 11:
               return "interceptor";
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
      private InterceptorsMBeanImpl bean;

      protected Helper(InterceptorsMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "WhiteListingEnabled";
            case 11:
               return "Interceptors";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Interceptors")) {
            return 11;
         } else {
            return propName.equals("WhiteListingEnabled") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getInterceptors()));
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

            for(int i = 0; i < this.bean.getInterceptors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInterceptors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWhiteListingEnabledSet()) {
               buf.append("WhiteListingEnabled");
               buf.append(String.valueOf(this.bean.isWhiteListingEnabled()));
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
            InterceptorsMBeanImpl otherTyped = (InterceptorsMBeanImpl)other;
            this.computeChildDiff("Interceptors", this.bean.getInterceptors(), otherTyped.getInterceptors(), true);
            this.computeDiff("WhiteListingEnabled", this.bean.isWhiteListingEnabled(), otherTyped.isWhiteListingEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InterceptorsMBeanImpl original = (InterceptorsMBeanImpl)event.getSourceBean();
            InterceptorsMBeanImpl proposed = (InterceptorsMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Interceptors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addInterceptor((InterceptorMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInterceptor((InterceptorMBean)update.getRemovedObject());
                  }

                  if (original.getInterceptors() == null || original.getInterceptors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("WhiteListingEnabled")) {
                  original.setWhiteListingEnabled(proposed.isWhiteListingEnabled());
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
            InterceptorsMBeanImpl copy = (InterceptorsMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Interceptors")) && this.bean.isInterceptorsSet() && !copy._isSet(11)) {
               InterceptorMBean[] oldInterceptors = this.bean.getInterceptors();
               InterceptorMBean[] newInterceptors = new InterceptorMBean[oldInterceptors.length];

               for(int i = 0; i < newInterceptors.length; ++i) {
                  newInterceptors[i] = (InterceptorMBean)((InterceptorMBean)this.createCopy((AbstractDescriptorBean)oldInterceptors[i], includeObsolete));
               }

               copy.setInterceptors(newInterceptors);
            }

            if ((excludeProps == null || !excludeProps.contains("WhiteListingEnabled")) && this.bean.isWhiteListingEnabledSet()) {
               copy.setWhiteListingEnabled(this.bean.isWhiteListingEnabled());
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
         this.inferSubTree(this.bean.getInterceptors(), clazz, annotation);
      }
   }
}
