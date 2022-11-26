package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class AuthenticationMechanismBeanImpl extends AbstractDescriptorBean implements AuthenticationMechanismBean, Serializable {
   private String _AuthenticationMechanismType;
   private String _CredentialInterface;
   private String[] _Descriptions;
   private String _Id;
   private static SchemaHelper2 _schemaHelper;

   public AuthenticationMechanismBeanImpl() {
      this._initializeProperty(-1);
   }

   public AuthenticationMechanismBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AuthenticationMechanismBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(0);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getAuthenticationMechanismType() {
      return this._AuthenticationMechanismType;
   }

   public boolean isAuthenticationMechanismTypeInherited() {
      return false;
   }

   public boolean isAuthenticationMechanismTypeSet() {
      return this._isSet(1);
   }

   public void setAuthenticationMechanismType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AuthenticationMechanismType;
      this._AuthenticationMechanismType = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getCredentialInterface() {
      return this._CredentialInterface;
   }

   public boolean isCredentialInterfaceInherited() {
      return false;
   }

   public boolean isCredentialInterfaceSet() {
      return this._isSet(2);
   }

   public void setCredentialInterface(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CredentialInterface;
      this._CredentialInterface = param0;
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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._AuthenticationMechanismType = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._CredentialInterface = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
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
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("credential-interface")) {
                  return 2;
               }
               break;
            case 29:
               if (s.equals("authentication-mechanism-type")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "authentication-mechanism-type";
            case 2:
               return "credential-interface";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AuthenticationMechanismBeanImpl bean;

      protected Helper(AuthenticationMechanismBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "AuthenticationMechanismType";
            case 2:
               return "CredentialInterface";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthenticationMechanismType")) {
            return 1;
         } else if (propName.equals("CredentialInterface")) {
            return 2;
         } else if (propName.equals("Descriptions")) {
            return 0;
         } else {
            return propName.equals("Id") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isAuthenticationMechanismTypeSet()) {
               buf.append("AuthenticationMechanismType");
               buf.append(String.valueOf(this.bean.getAuthenticationMechanismType()));
            }

            if (this.bean.isCredentialInterfaceSet()) {
               buf.append("CredentialInterface");
               buf.append(String.valueOf(this.bean.getCredentialInterface()));
            }

            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
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
            AuthenticationMechanismBeanImpl otherTyped = (AuthenticationMechanismBeanImpl)other;
            this.computeDiff("AuthenticationMechanismType", this.bean.getAuthenticationMechanismType(), otherTyped.getAuthenticationMechanismType(), false);
            this.computeDiff("CredentialInterface", this.bean.getCredentialInterface(), otherTyped.getCredentialInterface(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AuthenticationMechanismBeanImpl original = (AuthenticationMechanismBeanImpl)event.getSourceBean();
            AuthenticationMechanismBeanImpl proposed = (AuthenticationMechanismBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AuthenticationMechanismType")) {
                  original.setAuthenticationMechanismType(proposed.getAuthenticationMechanismType());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("CredentialInterface")) {
                  original.setCredentialInterface(proposed.getCredentialInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            AuthenticationMechanismBeanImpl copy = (AuthenticationMechanismBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthenticationMechanismType")) && this.bean.isAuthenticationMechanismTypeSet()) {
               copy.setAuthenticationMechanismType(this.bean.getAuthenticationMechanismType());
            }

            if ((excludeProps == null || !excludeProps.contains("CredentialInterface")) && this.bean.isCredentialInterfaceSet()) {
               copy.setCredentialInterface(this.bean.getCredentialInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               Object o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
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
      }
   }
}
