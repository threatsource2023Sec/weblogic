package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class LoginConfigBeanImpl extends AbstractDescriptorBean implements LoginConfigBean, Serializable {
   private String _AuthMethod;
   private FormLoginConfigBean _FormLoginConfig;
   private String _Id;
   private String _RealmName;
   private static SchemaHelper2 _schemaHelper;

   public LoginConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public LoginConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LoginConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getAuthMethod() {
      return this._AuthMethod;
   }

   public boolean isAuthMethodInherited() {
      return false;
   }

   public boolean isAuthMethodSet() {
      return this._isSet(0);
   }

   public void setAuthMethod(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AuthMethod;
      this._AuthMethod = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getRealmName() {
      return this._RealmName;
   }

   public boolean isRealmNameInherited() {
      return false;
   }

   public boolean isRealmNameSet() {
      return this._isSet(1);
   }

   public void setRealmName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RealmName;
      this._RealmName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public FormLoginConfigBean getFormLoginConfig() {
      return this._FormLoginConfig;
   }

   public boolean isFormLoginConfigInherited() {
      return false;
   }

   public boolean isFormLoginConfigSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getFormLoginConfig());
   }

   public void setFormLoginConfig(FormLoginConfigBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      FormLoginConfigBean _oldVal = this._FormLoginConfig;
      this._FormLoginConfig = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
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
      return super._isAnythingSet() || this.isFormLoginConfigSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._AuthMethod = "BASIC";
               if (initOne) {
                  break;
               }
            case 2:
               this._FormLoginConfig = new FormLoginConfigBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._FormLoginConfig);
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._RealmName = null;
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
            case 2:
               if (s.equals("id")) {
                  return 3;
               }
               break;
            case 10:
               if (s.equals("realm-name")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("auth-method")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("form-login-config")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new FormLoginConfigBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "auth-method";
            case 1:
               return "realm-name";
            case 2:
               return "form-login-config";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private LoginConfigBeanImpl bean;

      protected Helper(LoginConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AuthMethod";
            case 1:
               return "RealmName";
            case 2:
               return "FormLoginConfig";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthMethod")) {
            return 0;
         } else if (propName.equals("FormLoginConfig")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 3;
         } else {
            return propName.equals("RealmName") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getFormLoginConfig() != null) {
            iterators.add(new ArrayIterator(new FormLoginConfigBean[]{this.bean.getFormLoginConfig()}));
         }

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
            if (this.bean.isAuthMethodSet()) {
               buf.append("AuthMethod");
               buf.append(String.valueOf(this.bean.getAuthMethod()));
            }

            childValue = this.computeChildHashValue(this.bean.getFormLoginConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isRealmNameSet()) {
               buf.append("RealmName");
               buf.append(String.valueOf(this.bean.getRealmName()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            LoginConfigBeanImpl otherTyped = (LoginConfigBeanImpl)other;
            this.computeDiff("AuthMethod", this.bean.getAuthMethod(), otherTyped.getAuthMethod(), false);
            this.computeSubDiff("FormLoginConfig", this.bean.getFormLoginConfig(), otherTyped.getFormLoginConfig());
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("RealmName", this.bean.getRealmName(), otherTyped.getRealmName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LoginConfigBeanImpl original = (LoginConfigBeanImpl)event.getSourceBean();
            LoginConfigBeanImpl proposed = (LoginConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AuthMethod")) {
                  original.setAuthMethod(proposed.getAuthMethod());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("FormLoginConfig")) {
                  if (type == 2) {
                     original.setFormLoginConfig((FormLoginConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getFormLoginConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("FormLoginConfig", (DescriptorBean)original.getFormLoginConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RealmName")) {
                  original.setRealmName(proposed.getRealmName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            LoginConfigBeanImpl copy = (LoginConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthMethod")) && this.bean.isAuthMethodSet()) {
               copy.setAuthMethod(this.bean.getAuthMethod());
            }

            if ((excludeProps == null || !excludeProps.contains("FormLoginConfig")) && this.bean.isFormLoginConfigSet() && !copy._isSet(2)) {
               Object o = this.bean.getFormLoginConfig();
               copy.setFormLoginConfig((FormLoginConfigBean)null);
               copy.setFormLoginConfig(o == null ? null : (FormLoginConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("RealmName")) && this.bean.isRealmNameSet()) {
               copy.setRealmName(this.bean.getRealmName());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getFormLoginConfig(), clazz, annotation);
      }
   }
}
