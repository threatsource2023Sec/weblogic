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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicConnectorExtensionBeanImpl extends WeblogicConnectorBeanImpl implements WeblogicConnectorExtensionBean, Serializable {
   private LinkRefBean _LinkRef;
   private ProxyBean _Proxy;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicConnectorExtensionBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicConnectorExtensionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicConnectorExtensionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public LinkRefBean getLinkRef() {
      return this._LinkRef;
   }

   public boolean isLinkRefInherited() {
      return false;
   }

   public boolean isLinkRefSet() {
      return this._isSet(12);
   }

   public void setLinkRef(LinkRefBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLinkRef() != null && param0 != this.getLinkRef()) {
         throw new BeanAlreadyExistsException(this.getLinkRef() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 12)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LinkRefBean _oldVal = this._LinkRef;
         this._LinkRef = param0;
         this._postSet(12, _oldVal, param0);
      }
   }

   public LinkRefBean createLinkRef() {
      LinkRefBeanImpl _val = new LinkRefBeanImpl(this, -1);

      try {
         this.setLinkRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLinkRef(LinkRefBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LinkRef;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLinkRef((LinkRefBean)null);
               this._unSet(12);
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

   public ProxyBean getProxy() {
      return this._Proxy;
   }

   public boolean isProxyInherited() {
      return false;
   }

   public boolean isProxySet() {
      return this._isSet(13);
   }

   public void setProxy(ProxyBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getProxy() != null && param0 != this.getProxy()) {
         throw new BeanAlreadyExistsException(this.getProxy() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 13)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ProxyBean _oldVal = this._Proxy;
         this._Proxy = param0;
         this._postSet(13, _oldVal, param0);
      }
   }

   public ProxyBean createProxy() {
      ProxyBeanImpl _val = new ProxyBeanImpl(this, -1);

      try {
         this.setProxy(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyProxy(ProxyBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Proxy;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setProxy((ProxyBean)null);
               this._unSet(13);
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._LinkRef = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._Proxy = null;
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

   public static class SchemaHelper2 extends WeblogicConnectorBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("proxy")) {
                  return 13;
               }
               break;
            case 8:
               if (s.equals("link-ref")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new WorkManagerBeanImpl.SchemaHelper2();
            case 6:
               return new ConnectorWorkManagerBeanImpl.SchemaHelper2();
            case 7:
               return new ResourceAdapterSecurityBeanImpl.SchemaHelper2();
            case 8:
               return new ConfigPropertiesBeanImpl.SchemaHelper2();
            case 9:
               return new AdminObjectsBeanImpl.SchemaHelper2();
            case 10:
               return new OutboundResourceAdapterBeanImpl.SchemaHelper2();
            case 11:
            default:
               return super.getSchemaHelper(propIndex);
            case 12:
               return new LinkRefBeanImpl.SchemaHelper2();
            case 13:
               return new ProxyBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "weblogic-connector-extension";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "link-ref";
            case 13:
               return "proxy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
            default:
               return super.isBean(propIndex);
            case 12:
               return true;
            case 13:
               return true;
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
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends WeblogicConnectorBeanImpl.Helper {
      private WeblogicConnectorExtensionBeanImpl bean;

      protected Helper(WeblogicConnectorExtensionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "LinkRef";
            case 13:
               return "Proxy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LinkRef")) {
            return 12;
         } else {
            return propName.equals("Proxy") ? 13 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAdminObjects() != null) {
            iterators.add(new ArrayIterator(new AdminObjectsBean[]{this.bean.getAdminObjects()}));
         }

         if (this.bean.getConnectorWorkManager() != null) {
            iterators.add(new ArrayIterator(new ConnectorWorkManagerBean[]{this.bean.getConnectorWorkManager()}));
         }

         if (this.bean.getLinkRef() != null) {
            iterators.add(new ArrayIterator(new LinkRefBean[]{this.bean.getLinkRef()}));
         }

         if (this.bean.getOutboundResourceAdapter() != null) {
            iterators.add(new ArrayIterator(new OutboundResourceAdapterBean[]{this.bean.getOutboundResourceAdapter()}));
         }

         if (this.bean.getProperties() != null) {
            iterators.add(new ArrayIterator(new ConfigPropertiesBean[]{this.bean.getProperties()}));
         }

         if (this.bean.getProxy() != null) {
            iterators.add(new ArrayIterator(new ProxyBean[]{this.bean.getProxy()}));
         }

         if (this.bean.getSecurity() != null) {
            iterators.add(new ArrayIterator(new ResourceAdapterSecurityBean[]{this.bean.getSecurity()}));
         }

         if (this.bean.getWorkManager() != null) {
            iterators.add(new ArrayIterator(new WorkManagerBean[]{this.bean.getWorkManager()}));
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
            childValue = this.computeChildHashValue(this.bean.getLinkRef());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getProxy());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            WeblogicConnectorExtensionBeanImpl otherTyped = (WeblogicConnectorExtensionBeanImpl)other;
            this.computeChildDiff("LinkRef", this.bean.getLinkRef(), otherTyped.getLinkRef(), false);
            this.computeChildDiff("Proxy", this.bean.getProxy(), otherTyped.getProxy(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicConnectorExtensionBeanImpl original = (WeblogicConnectorExtensionBeanImpl)event.getSourceBean();
            WeblogicConnectorExtensionBeanImpl proposed = (WeblogicConnectorExtensionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LinkRef")) {
                  if (type == 2) {
                     original.setLinkRef((LinkRefBean)this.createCopy((AbstractDescriptorBean)proposed.getLinkRef()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("LinkRef", (DescriptorBean)original.getLinkRef());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Proxy")) {
                  if (type == 2) {
                     original.setProxy((ProxyBean)this.createCopy((AbstractDescriptorBean)proposed.getProxy()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Proxy", (DescriptorBean)original.getProxy());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            WeblogicConnectorExtensionBeanImpl copy = (WeblogicConnectorExtensionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LinkRef")) && this.bean.isLinkRefSet() && !copy._isSet(12)) {
               Object o = this.bean.getLinkRef();
               copy.setLinkRef((LinkRefBean)null);
               copy.setLinkRef(o == null ? null : (LinkRefBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Proxy")) && this.bean.isProxySet() && !copy._isSet(13)) {
               Object o = this.bean.getProxy();
               copy.setProxy((ProxyBean)null);
               copy.setProxy(o == null ? null : (ProxyBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getLinkRef(), clazz, annotation);
         this.inferSubTree(this.bean.getProxy(), clazz, annotation);
      }
   }
}
