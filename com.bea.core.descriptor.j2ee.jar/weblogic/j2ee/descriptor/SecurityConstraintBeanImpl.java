package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SecurityConstraintBeanImpl extends AbstractDescriptorBean implements SecurityConstraintBean, Serializable {
   private AuthConstraintBean _AuthConstraint;
   private String[] _DisplayNames;
   private String _Id;
   private UserDataConstraintBean _UserDataConstraint;
   private WebResourceCollectionBean[] _WebResourceCollections;
   private static SchemaHelper2 _schemaHelper;

   public SecurityConstraintBeanImpl() {
      this._initializeProperty(-1);
   }

   public SecurityConstraintBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SecurityConstraintBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getDisplayNames() {
      return this._DisplayNames;
   }

   public boolean isDisplayNamesInherited() {
      return false;
   }

   public boolean isDisplayNamesSet() {
      return this._isSet(0);
   }

   public void addDisplayName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDisplayNames(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDisplayNames(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDisplayName(String param0) {
      String[] _old = this.getDisplayNames();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDisplayNames(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDisplayNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DisplayNames;
      this._DisplayNames = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addWebResourceCollection(WebResourceCollectionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         WebResourceCollectionBean[] _new;
         if (this._isSet(1)) {
            _new = (WebResourceCollectionBean[])((WebResourceCollectionBean[])this._getHelper()._extendArray(this.getWebResourceCollections(), WebResourceCollectionBean.class, param0));
         } else {
            _new = new WebResourceCollectionBean[]{param0};
         }

         try {
            this.setWebResourceCollections(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebResourceCollectionBean[] getWebResourceCollections() {
      return this._WebResourceCollections;
   }

   public boolean isWebResourceCollectionsInherited() {
      return false;
   }

   public boolean isWebResourceCollectionsSet() {
      return this._isSet(1);
   }

   public void removeWebResourceCollection(WebResourceCollectionBean param0) {
      this.destroyWebResourceCollection(param0);
   }

   public void setWebResourceCollections(WebResourceCollectionBean[] param0) throws InvalidAttributeValueException {
      WebResourceCollectionBean[] param0 = param0 == null ? new WebResourceCollectionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WebResourceCollectionBean[] _oldVal = this._WebResourceCollections;
      this._WebResourceCollections = (WebResourceCollectionBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public WebResourceCollectionBean createWebResourceCollection() {
      WebResourceCollectionBeanImpl _val = new WebResourceCollectionBeanImpl(this, -1);

      try {
         this.addWebResourceCollection(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWebResourceCollection(WebResourceCollectionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         WebResourceCollectionBean[] _old = this.getWebResourceCollections();
         WebResourceCollectionBean[] _new = (WebResourceCollectionBean[])((WebResourceCollectionBean[])this._getHelper()._removeElement(_old, WebResourceCollectionBean.class, param0));
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
               this.setWebResourceCollections(_new);
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

   public AuthConstraintBean getAuthConstraint() {
      return this._AuthConstraint;
   }

   public boolean isAuthConstraintInherited() {
      return false;
   }

   public boolean isAuthConstraintSet() {
      return this._isSet(2);
   }

   public void setAuthConstraint(AuthConstraintBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAuthConstraint() != null && param0 != this.getAuthConstraint()) {
         throw new BeanAlreadyExistsException(this.getAuthConstraint() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AuthConstraintBean _oldVal = this._AuthConstraint;
         this._AuthConstraint = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public AuthConstraintBean createAuthConstraint() {
      AuthConstraintBeanImpl _val = new AuthConstraintBeanImpl(this, -1);

      try {
         this.setAuthConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAuthConstraint(AuthConstraintBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AuthConstraint;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAuthConstraint((AuthConstraintBean)null);
               this._unSet(2);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public UserDataConstraintBean getUserDataConstraint() {
      return this._UserDataConstraint;
   }

   public boolean isUserDataConstraintInherited() {
      return false;
   }

   public boolean isUserDataConstraintSet() {
      return this._isSet(3);
   }

   public void setUserDataConstraint(UserDataConstraintBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getUserDataConstraint() != null && param0 != this.getUserDataConstraint()) {
         throw new BeanAlreadyExistsException(this.getUserDataConstraint() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         UserDataConstraintBean _oldVal = this._UserDataConstraint;
         this._UserDataConstraint = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public UserDataConstraintBean createUserDataConstraint() {
      UserDataConstraintBeanImpl _val = new UserDataConstraintBeanImpl(this, -1);

      try {
         this.setUserDataConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyUserDataConstraint(UserDataConstraintBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._UserDataConstraint;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setUserDataConstraint((UserDataConstraintBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(4, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._AuthConstraint = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._DisplayNames = new String[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._UserDataConstraint = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WebResourceCollections = new WebResourceCollectionBean[0];
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
                  return 4;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("auth-constraint")) {
                  return 2;
               }
               break;
            case 20:
               if (s.equals("user-data-constraint")) {
                  return 3;
               }
               break;
            case 23:
               if (s.equals("web-resource-collection")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new WebResourceCollectionBeanImpl.SchemaHelper2();
            case 2:
               return new AuthConstraintBeanImpl.SchemaHelper2();
            case 3:
               return new UserDataConstraintBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "display-name";
            case 1:
               return "web-resource-collection";
            case 2:
               return "auth-constraint";
            case 3:
               return "user-data-constraint";
            case 4:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SecurityConstraintBeanImpl bean;

      protected Helper(SecurityConstraintBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DisplayNames";
            case 1:
               return "WebResourceCollections";
            case 2:
               return "AuthConstraint";
            case 3:
               return "UserDataConstraint";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthConstraint")) {
            return 2;
         } else if (propName.equals("DisplayNames")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("UserDataConstraint")) {
            return 3;
         } else {
            return propName.equals("WebResourceCollections") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAuthConstraint() != null) {
            iterators.add(new ArrayIterator(new AuthConstraintBean[]{this.bean.getAuthConstraint()}));
         }

         if (this.bean.getUserDataConstraint() != null) {
            iterators.add(new ArrayIterator(new UserDataConstraintBean[]{this.bean.getUserDataConstraint()}));
         }

         iterators.add(new ArrayIterator(this.bean.getWebResourceCollections()));
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
            childValue = this.computeChildHashValue(this.bean.getAuthConstraint());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDisplayNamesSet()) {
               buf.append("DisplayNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDisplayNames())));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getUserDataConstraint());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getWebResourceCollections().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebResourceCollections()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            SecurityConstraintBeanImpl otherTyped = (SecurityConstraintBeanImpl)other;
            this.computeChildDiff("AuthConstraint", this.bean.getAuthConstraint(), otherTyped.getAuthConstraint(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("UserDataConstraint", this.bean.getUserDataConstraint(), otherTyped.getUserDataConstraint(), false);
            this.computeChildDiff("WebResourceCollections", this.bean.getWebResourceCollections(), otherTyped.getWebResourceCollections(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SecurityConstraintBeanImpl original = (SecurityConstraintBeanImpl)event.getSourceBean();
            SecurityConstraintBeanImpl proposed = (SecurityConstraintBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AuthConstraint")) {
                  if (type == 2) {
                     original.setAuthConstraint((AuthConstraintBean)this.createCopy((AbstractDescriptorBean)proposed.getAuthConstraint()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AuthConstraint", (DescriptorBean)original.getAuthConstraint());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("DisplayNames")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDisplayName((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDisplayName((String)update.getRemovedObject());
                  }

                  if (original.getDisplayNames() == null || original.getDisplayNames().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("UserDataConstraint")) {
                  if (type == 2) {
                     original.setUserDataConstraint((UserDataConstraintBean)this.createCopy((AbstractDescriptorBean)proposed.getUserDataConstraint()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("UserDataConstraint", (DescriptorBean)original.getUserDataConstraint());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("WebResourceCollections")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWebResourceCollection((WebResourceCollectionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWebResourceCollection((WebResourceCollectionBean)update.getRemovedObject());
                  }

                  if (original.getWebResourceCollections() == null || original.getWebResourceCollections().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
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
            SecurityConstraintBeanImpl copy = (SecurityConstraintBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthConstraint")) && this.bean.isAuthConstraintSet() && !copy._isSet(2)) {
               Object o = this.bean.getAuthConstraint();
               copy.setAuthConstraint((AuthConstraintBean)null);
               copy.setAuthConstraint(o == null ? null : (AuthConstraintBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DisplayNames")) && this.bean.isDisplayNamesSet()) {
               Object o = this.bean.getDisplayNames();
               copy.setDisplayNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("UserDataConstraint")) && this.bean.isUserDataConstraintSet() && !copy._isSet(3)) {
               Object o = this.bean.getUserDataConstraint();
               copy.setUserDataConstraint((UserDataConstraintBean)null);
               copy.setUserDataConstraint(o == null ? null : (UserDataConstraintBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WebResourceCollections")) && this.bean.isWebResourceCollectionsSet() && !copy._isSet(1)) {
               WebResourceCollectionBean[] oldWebResourceCollections = this.bean.getWebResourceCollections();
               WebResourceCollectionBean[] newWebResourceCollections = new WebResourceCollectionBean[oldWebResourceCollections.length];

               for(int i = 0; i < newWebResourceCollections.length; ++i) {
                  newWebResourceCollections[i] = (WebResourceCollectionBean)((WebResourceCollectionBean)this.createCopy((AbstractDescriptorBean)oldWebResourceCollections[i], includeObsolete));
               }

               copy.setWebResourceCollections(newWebResourceCollections);
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
         this.inferSubTree(this.bean.getAuthConstraint(), clazz, annotation);
         this.inferSubTree(this.bean.getUserDataConstraint(), clazz, annotation);
         this.inferSubTree(this.bean.getWebResourceCollections(), clazz, annotation);
      }
   }
}
