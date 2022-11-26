package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicWebservicesBeanImpl extends AbstractDescriptorBean implements WeblogicWebservicesBean, Serializable {
   private String _Version;
   private WebserviceDescriptionBean[] _WebserviceDescriptions;
   private WebserviceSecurityBean _WebserviceSecurity;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicWebservicesBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicWebservicesBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicWebservicesBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addWebserviceDescription(WebserviceDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         WebserviceDescriptionBean[] _new;
         if (this._isSet(0)) {
            _new = (WebserviceDescriptionBean[])((WebserviceDescriptionBean[])this._getHelper()._extendArray(this.getWebserviceDescriptions(), WebserviceDescriptionBean.class, param0));
         } else {
            _new = new WebserviceDescriptionBean[]{param0};
         }

         try {
            this.setWebserviceDescriptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebserviceDescriptionBean[] getWebserviceDescriptions() {
      return this._WebserviceDescriptions;
   }

   public boolean isWebserviceDescriptionsInherited() {
      return false;
   }

   public boolean isWebserviceDescriptionsSet() {
      return this._isSet(0);
   }

   public void removeWebserviceDescription(WebserviceDescriptionBean param0) {
      this.destroyWebserviceDescription(param0);
   }

   public void setWebserviceDescriptions(WebserviceDescriptionBean[] param0) throws InvalidAttributeValueException {
      WebserviceDescriptionBean[] param0 = param0 == null ? new WebserviceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WebserviceDescriptionBean[] _oldVal = this._WebserviceDescriptions;
      this._WebserviceDescriptions = (WebserviceDescriptionBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public WebserviceDescriptionBean createWebserviceDescription() {
      WebserviceDescriptionBeanImpl _val = new WebserviceDescriptionBeanImpl(this, -1);

      try {
         this.addWebserviceDescription(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWebserviceDescription(WebserviceDescriptionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         WebserviceDescriptionBean[] _old = this.getWebserviceDescriptions();
         WebserviceDescriptionBean[] _new = (WebserviceDescriptionBean[])((WebserviceDescriptionBean[])this._getHelper()._removeElement(_old, WebserviceDescriptionBean.class, param0));
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
               this.setWebserviceDescriptions(_new);
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

   public WebserviceSecurityBean getWebserviceSecurity() {
      return this._WebserviceSecurity;
   }

   public boolean isWebserviceSecurityInherited() {
      return false;
   }

   public boolean isWebserviceSecuritySet() {
      return this._isSet(1);
   }

   public void setWebserviceSecurity(WebserviceSecurityBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWebserviceSecurity() != null && param0 != this.getWebserviceSecurity()) {
         throw new BeanAlreadyExistsException(this.getWebserviceSecurity() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WebserviceSecurityBean _oldVal = this._WebserviceSecurity;
         this._WebserviceSecurity = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public WebserviceSecurityBean createWebserviceSecurity() {
      WebserviceSecurityBeanImpl _val = new WebserviceSecurityBeanImpl(this, -1);

      try {
         this.setWebserviceSecurity(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWebserviceSecurity(WebserviceSecurityBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WebserviceSecurity;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWebserviceSecurity((WebserviceSecurityBean)null);
               this._unSet(1);
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

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(2);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(2, _oldVal, param0);
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
               this._Version = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._WebserviceDescriptions = new WebserviceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._WebserviceSecurity = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-webservices/1.1/weblogic-webservices.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-webservices";
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
               if (s.equals("version")) {
                  return 2;
               }
               break;
            case 19:
               if (s.equals("webservice-security")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("webservice-description")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new WebserviceDescriptionBeanImpl.SchemaHelper2();
            case 1:
               return new WebserviceSecurityBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "weblogic-webservices";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "webservice-description";
            case 1:
               return "webservice-security";
            case 2:
               return "version";
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
      private WeblogicWebservicesBeanImpl bean;

      protected Helper(WeblogicWebservicesBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "WebserviceDescriptions";
            case 1:
               return "WebserviceSecurity";
            case 2:
               return "Version";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Version")) {
            return 2;
         } else if (propName.equals("WebserviceDescriptions")) {
            return 0;
         } else {
            return propName.equals("WebserviceSecurity") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWebserviceDescriptions()));
         if (this.bean.getWebserviceSecurity() != null) {
            iterators.add(new ArrayIterator(new WebserviceSecurityBean[]{this.bean.getWebserviceSecurity()}));
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
            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getWebserviceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebserviceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebserviceSecurity());
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
            WeblogicWebservicesBeanImpl otherTyped = (WeblogicWebservicesBeanImpl)other;
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeChildDiff("WebserviceDescriptions", this.bean.getWebserviceDescriptions(), otherTyped.getWebserviceDescriptions(), false);
            this.computeChildDiff("WebserviceSecurity", this.bean.getWebserviceSecurity(), otherTyped.getWebserviceSecurity(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicWebservicesBeanImpl original = (WeblogicWebservicesBeanImpl)event.getSourceBean();
            WeblogicWebservicesBeanImpl proposed = (WeblogicWebservicesBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("WebserviceDescriptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWebserviceDescription((WebserviceDescriptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWebserviceDescription((WebserviceDescriptionBean)update.getRemovedObject());
                  }

                  if (original.getWebserviceDescriptions() == null || original.getWebserviceDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("WebserviceSecurity")) {
                  if (type == 2) {
                     original.setWebserviceSecurity((WebserviceSecurityBean)this.createCopy((AbstractDescriptorBean)proposed.getWebserviceSecurity()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WebserviceSecurity", (DescriptorBean)original.getWebserviceSecurity());
                  }

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
            WeblogicWebservicesBeanImpl copy = (WeblogicWebservicesBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceDescriptions")) && this.bean.isWebserviceDescriptionsSet() && !copy._isSet(0)) {
               WebserviceDescriptionBean[] oldWebserviceDescriptions = this.bean.getWebserviceDescriptions();
               WebserviceDescriptionBean[] newWebserviceDescriptions = new WebserviceDescriptionBean[oldWebserviceDescriptions.length];

               for(int i = 0; i < newWebserviceDescriptions.length; ++i) {
                  newWebserviceDescriptions[i] = (WebserviceDescriptionBean)((WebserviceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldWebserviceDescriptions[i], includeObsolete));
               }

               copy.setWebserviceDescriptions(newWebserviceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceSecurity")) && this.bean.isWebserviceSecuritySet() && !copy._isSet(1)) {
               Object o = this.bean.getWebserviceSecurity();
               copy.setWebserviceSecurity((WebserviceSecurityBean)null);
               copy.setWebserviceSecurity(o == null ? null : (WebserviceSecurityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getWebserviceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getWebserviceSecurity(), clazz, annotation);
      }
   }
}
