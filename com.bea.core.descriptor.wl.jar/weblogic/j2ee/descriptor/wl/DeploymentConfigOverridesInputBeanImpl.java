package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
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

public class DeploymentConfigOverridesInputBeanImpl extends AbstractDescriptorBean implements DeploymentConfigOverridesInputBean, Serializable {
   private AppDeploymentBean[] _AppDeployments;
   private LibraryBean[] _Libraries;
   private static SchemaHelper2 _schemaHelper;

   public DeploymentConfigOverridesInputBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public DeploymentConfigOverridesInputBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public DeploymentConfigOverridesInputBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addAppDeployment(AppDeploymentBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         AppDeploymentBean[] _new;
         if (this._isSet(0)) {
            _new = (AppDeploymentBean[])((AppDeploymentBean[])this._getHelper()._extendArray(this.getAppDeployments(), AppDeploymentBean.class, param0));
         } else {
            _new = new AppDeploymentBean[]{param0};
         }

         try {
            this.setAppDeployments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AppDeploymentBean[] getAppDeployments() {
      return this._AppDeployments;
   }

   public boolean isAppDeploymentsInherited() {
      return false;
   }

   public boolean isAppDeploymentsSet() {
      return this._isSet(0);
   }

   public void removeAppDeployment(AppDeploymentBean param0) {
      AppDeploymentBean[] _old = this.getAppDeployments();
      AppDeploymentBean[] _new = (AppDeploymentBean[])((AppDeploymentBean[])this._getHelper()._removeElement(_old, AppDeploymentBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setAppDeployments(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setAppDeployments(AppDeploymentBean[] param0) throws InvalidAttributeValueException {
      AppDeploymentBean[] param0 = param0 == null ? new AppDeploymentBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AppDeploymentBean[] _oldVal = this._AppDeployments;
      this._AppDeployments = (AppDeploymentBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public AppDeploymentBean createAppDeployment(String param0) {
      AppDeploymentBeanImpl _val = new AppDeploymentBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addAppDeployment(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addLibrary(LibraryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         LibraryBean[] _new;
         if (this._isSet(1)) {
            _new = (LibraryBean[])((LibraryBean[])this._getHelper()._extendArray(this.getLibraries(), LibraryBean.class, param0));
         } else {
            _new = new LibraryBean[]{param0};
         }

         try {
            this.setLibraries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LibraryBean[] getLibraries() {
      return this._Libraries;
   }

   public boolean isLibrariesInherited() {
      return false;
   }

   public boolean isLibrariesSet() {
      return this._isSet(1);
   }

   public void removeLibrary(LibraryBean param0) {
      LibraryBean[] _old = this.getLibraries();
      LibraryBean[] _new = (LibraryBean[])((LibraryBean[])this._getHelper()._removeElement(_old, LibraryBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setLibraries(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setLibraries(LibraryBean[] param0) throws InvalidAttributeValueException {
      LibraryBean[] param0 = param0 == null ? new LibraryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LibraryBean[] _oldVal = this._Libraries;
      this._Libraries = (LibraryBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public LibraryBean createLibrary(String param0) {
      LibraryBeanImpl _val = new LibraryBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addLibrary(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._AppDeployments = new AppDeploymentBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Libraries = new LibraryBean[0];
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
      return "http://xmlns.oracle.com/weblogic/deployment-config-overrides-input/1.0/deployment-config-overrides-input.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/deployment-config-overrides-input";
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
            case 7:
               if (s.equals("library")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("app-deployment")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new AppDeploymentBeanImpl.SchemaHelper2();
            case 1:
               return new LibraryBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "deployment-config-overrides-input";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "app-deployment";
            case 1:
               return "library";
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
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private DeploymentConfigOverridesInputBeanImpl bean;

      protected Helper(DeploymentConfigOverridesInputBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AppDeployments";
            case 1:
               return "Libraries";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AppDeployments")) {
            return 0;
         } else {
            return propName.equals("Libraries") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAppDeployments()));
         iterators.add(new ArrayIterator(this.bean.getLibraries()));
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

            int i;
            for(i = 0; i < this.bean.getAppDeployments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAppDeployments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLibraries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLibraries()[i]);
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
            DeploymentConfigOverridesInputBeanImpl otherTyped = (DeploymentConfigOverridesInputBeanImpl)other;
            this.computeChildDiff("AppDeployments", this.bean.getAppDeployments(), otherTyped.getAppDeployments(), false);
            this.computeChildDiff("Libraries", this.bean.getLibraries(), otherTyped.getLibraries(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DeploymentConfigOverridesInputBeanImpl original = (DeploymentConfigOverridesInputBeanImpl)event.getSourceBean();
            DeploymentConfigOverridesInputBeanImpl proposed = (DeploymentConfigOverridesInputBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AppDeployments")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAppDeployment((AppDeploymentBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAppDeployment((AppDeploymentBean)update.getRemovedObject());
                  }

                  if (original.getAppDeployments() == null || original.getAppDeployments().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("Libraries")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLibrary((LibraryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLibrary((LibraryBean)update.getRemovedObject());
                  }

                  if (original.getLibraries() == null || original.getLibraries().length == 0) {
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
            DeploymentConfigOverridesInputBeanImpl copy = (DeploymentConfigOverridesInputBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AppDeployments")) && this.bean.isAppDeploymentsSet() && !copy._isSet(0)) {
               AppDeploymentBean[] oldAppDeployments = this.bean.getAppDeployments();
               AppDeploymentBean[] newAppDeployments = new AppDeploymentBean[oldAppDeployments.length];

               for(i = 0; i < newAppDeployments.length; ++i) {
                  newAppDeployments[i] = (AppDeploymentBean)((AppDeploymentBean)this.createCopy((AbstractDescriptorBean)oldAppDeployments[i], includeObsolete));
               }

               copy.setAppDeployments(newAppDeployments);
            }

            if ((excludeProps == null || !excludeProps.contains("Libraries")) && this.bean.isLibrariesSet() && !copy._isSet(1)) {
               LibraryBean[] oldLibraries = this.bean.getLibraries();
               LibraryBean[] newLibraries = new LibraryBean[oldLibraries.length];

               for(i = 0; i < newLibraries.length; ++i) {
                  newLibraries[i] = (LibraryBean)((LibraryBean)this.createCopy((AbstractDescriptorBean)oldLibraries[i], includeObsolete));
               }

               copy.setLibraries(newLibraries);
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
         this.inferSubTree(this.bean.getAppDeployments(), clazz, annotation);
         this.inferSubTree(this.bean.getLibraries(), clazz, annotation);
      }
   }
}
