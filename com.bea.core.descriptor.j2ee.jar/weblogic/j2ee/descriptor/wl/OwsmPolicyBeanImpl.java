package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class OwsmPolicyBeanImpl extends AbstractDescriptorBean implements OwsmPolicyBean, Serializable {
   private String _Category;
   private PropertyNamevalueBean[] _SecurityConfigurationProperties;
   private String _Status;
   private String _Uri;
   private static SchemaHelper2 _schemaHelper;

   public OwsmPolicyBeanImpl() {
      this._initializeProperty(-1);
   }

   public OwsmPolicyBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public OwsmPolicyBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Uri;
      this._Uri = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getUri() {
      return this._Uri;
   }

   public boolean isUriInherited() {
      return false;
   }

   public boolean isUriSet() {
      return this._isSet(0);
   }

   public void setStatus(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Status;
      this._Status = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getStatus() {
      return this._Status;
   }

   public boolean isStatusInherited() {
      return false;
   }

   public boolean isStatusSet() {
      return this._isSet(1);
   }

   public void setCategory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Category;
      this._Category = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getCategory() {
      return this._Category;
   }

   public boolean isCategoryInherited() {
      return false;
   }

   public boolean isCategorySet() {
      return this._isSet(2);
   }

   public void addSecurityConfigurationProperty(PropertyNamevalueBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         PropertyNamevalueBean[] _new;
         if (this._isSet(3)) {
            _new = (PropertyNamevalueBean[])((PropertyNamevalueBean[])this._getHelper()._extendArray(this.getSecurityConfigurationProperties(), PropertyNamevalueBean.class, param0));
         } else {
            _new = new PropertyNamevalueBean[]{param0};
         }

         try {
            this.setSecurityConfigurationProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PropertyNamevalueBean[] getSecurityConfigurationProperties() {
      return this._SecurityConfigurationProperties;
   }

   public boolean isSecurityConfigurationPropertiesInherited() {
      return false;
   }

   public boolean isSecurityConfigurationPropertiesSet() {
      return this._isSet(3);
   }

   public void removeSecurityConfigurationProperty(PropertyNamevalueBean param0) {
      this.destroySecurityConfigurationProperty(param0);
   }

   public void setSecurityConfigurationProperties(PropertyNamevalueBean[] param0) throws InvalidAttributeValueException {
      PropertyNamevalueBean[] param0 = param0 == null ? new PropertyNamevalueBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PropertyNamevalueBean[] _oldVal = this._SecurityConfigurationProperties;
      this._SecurityConfigurationProperties = (PropertyNamevalueBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public PropertyNamevalueBean createSecurityConfigurationProperty() {
      PropertyNamevalueBeanImpl _val = new PropertyNamevalueBeanImpl(this, -1);

      try {
         this.addSecurityConfigurationProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityConfigurationProperty(PropertyNamevalueBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         PropertyNamevalueBean[] _old = this.getSecurityConfigurationProperties();
         PropertyNamevalueBean[] _new = (PropertyNamevalueBean[])((PropertyNamevalueBean[])this._getHelper()._removeElement(_old, PropertyNamevalueBean.class, param0));
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
               this.setSecurityConfigurationProperties(_new);
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
      return this.getUri();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Category", this.isCategorySet());
      LegalChecks.checkIsSet("Uri", this.isUriSet());
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 3:
            if (s.equals("uri")) {
               return info.compareXpaths(this._getPropertyXpath("uri"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._Category = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._SecurityConfigurationProperties = new PropertyNamevalueBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Status = "enabled";
               if (initOne) {
                  break;
               }
            case 0:
               this._Uri = null;
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("uri")) {
                  return 0;
               }
               break;
            case 6:
               if (s.equals("status")) {
                  return 1;
               }
               break;
            case 8:
               if (s.equals("category")) {
                  return 2;
               }
               break;
            case 31:
               if (s.equals("security-configuration-property")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new PropertyNamevalueBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "uri";
            case 1:
               return "status";
            case 2:
               return "category";
            case 3:
               return "security-configuration-property";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("uri");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private OwsmPolicyBeanImpl bean;

      protected Helper(OwsmPolicyBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Uri";
            case 1:
               return "Status";
            case 2:
               return "Category";
            case 3:
               return "SecurityConfigurationProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Category")) {
            return 2;
         } else if (propName.equals("SecurityConfigurationProperties")) {
            return 3;
         } else if (propName.equals("Status")) {
            return 1;
         } else {
            return propName.equals("Uri") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getSecurityConfigurationProperties()));
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
            if (this.bean.isCategorySet()) {
               buf.append("Category");
               buf.append(String.valueOf(this.bean.getCategory()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getSecurityConfigurationProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityConfigurationProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isStatusSet()) {
               buf.append("Status");
               buf.append(String.valueOf(this.bean.getStatus()));
            }

            if (this.bean.isUriSet()) {
               buf.append("Uri");
               buf.append(String.valueOf(this.bean.getUri()));
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
            OwsmPolicyBeanImpl otherTyped = (OwsmPolicyBeanImpl)other;
            this.computeDiff("Category", this.bean.getCategory(), otherTyped.getCategory(), false);
            this.computeChildDiff("SecurityConfigurationProperties", this.bean.getSecurityConfigurationProperties(), otherTyped.getSecurityConfigurationProperties(), false);
            this.computeDiff("Status", this.bean.getStatus(), otherTyped.getStatus(), false);
            this.computeDiff("Uri", this.bean.getUri(), otherTyped.getUri(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            OwsmPolicyBeanImpl original = (OwsmPolicyBeanImpl)event.getSourceBean();
            OwsmPolicyBeanImpl proposed = (OwsmPolicyBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Category")) {
                  original.setCategory(proposed.getCategory());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SecurityConfigurationProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityConfigurationProperty((PropertyNamevalueBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityConfigurationProperty((PropertyNamevalueBean)update.getRemovedObject());
                  }

                  if (original.getSecurityConfigurationProperties() == null || original.getSecurityConfigurationProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Status")) {
                  original.setStatus(proposed.getStatus());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Uri")) {
                  original.setUri(proposed.getUri());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            OwsmPolicyBeanImpl copy = (OwsmPolicyBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Category")) && this.bean.isCategorySet()) {
               copy.setCategory(this.bean.getCategory());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityConfigurationProperties")) && this.bean.isSecurityConfigurationPropertiesSet() && !copy._isSet(3)) {
               PropertyNamevalueBean[] oldSecurityConfigurationProperties = this.bean.getSecurityConfigurationProperties();
               PropertyNamevalueBean[] newSecurityConfigurationProperties = new PropertyNamevalueBean[oldSecurityConfigurationProperties.length];

               for(int i = 0; i < newSecurityConfigurationProperties.length; ++i) {
                  newSecurityConfigurationProperties[i] = (PropertyNamevalueBean)((PropertyNamevalueBean)this.createCopy((AbstractDescriptorBean)oldSecurityConfigurationProperties[i], includeObsolete));
               }

               copy.setSecurityConfigurationProperties(newSecurityConfigurationProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("Status")) && this.bean.isStatusSet()) {
               copy.setStatus(this.bean.getStatus());
            }

            if ((excludeProps == null || !excludeProps.contains("Uri")) && this.bean.isUriSet()) {
               copy.setUri(this.bean.getUri());
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
         this.inferSubTree(this.bean.getSecurityConfigurationProperties(), clazz, annotation);
      }
   }
}
