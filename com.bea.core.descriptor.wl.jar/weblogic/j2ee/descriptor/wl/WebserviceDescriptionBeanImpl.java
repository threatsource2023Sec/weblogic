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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebserviceDescriptionBeanImpl extends AbstractDescriptorBean implements WebserviceDescriptionBean, Serializable {
   private PortComponentBean[] _PortComponents;
   private String _WebserviceDescriptionName;
   private String _WebserviceType;
   private String _WsdlPublishFile;
   private static SchemaHelper2 _schemaHelper;

   public WebserviceDescriptionBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebserviceDescriptionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebserviceDescriptionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getWebserviceDescriptionName() {
      return this._WebserviceDescriptionName;
   }

   public boolean isWebserviceDescriptionNameInherited() {
      return false;
   }

   public boolean isWebserviceDescriptionNameSet() {
      return this._isSet(0);
   }

   public void setWebserviceDescriptionName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WebserviceDescriptionName;
      this._WebserviceDescriptionName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getWebserviceType() {
      return this._WebserviceType;
   }

   public boolean isWebserviceTypeInherited() {
      return false;
   }

   public boolean isWebserviceTypeSet() {
      return this._isSet(1);
   }

   public void setWebserviceType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WebserviceType;
      this._WebserviceType = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getWsdlPublishFile() {
      return this._WsdlPublishFile;
   }

   public boolean isWsdlPublishFileInherited() {
      return false;
   }

   public boolean isWsdlPublishFileSet() {
      return this._isSet(2);
   }

   public void setWsdlPublishFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WsdlPublishFile;
      this._WsdlPublishFile = param0;
      this._postSet(2, _oldVal, param0);
   }

   public void addPortComponent(PortComponentBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         PortComponentBean[] _new;
         if (this._isSet(3)) {
            _new = (PortComponentBean[])((PortComponentBean[])this._getHelper()._extendArray(this.getPortComponents(), PortComponentBean.class, param0));
         } else {
            _new = new PortComponentBean[]{param0};
         }

         try {
            this.setPortComponents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PortComponentBean[] getPortComponents() {
      return this._PortComponents;
   }

   public boolean isPortComponentsInherited() {
      return false;
   }

   public boolean isPortComponentsSet() {
      return this._isSet(3);
   }

   public void removePortComponent(PortComponentBean param0) {
      this.destroyPortComponent(param0);
   }

   public void setPortComponents(PortComponentBean[] param0) throws InvalidAttributeValueException {
      PortComponentBean[] param0 = param0 == null ? new PortComponentBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PortComponentBean[] _oldVal = this._PortComponents;
      this._PortComponents = (PortComponentBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public PortComponentBean createPortComponent() {
      PortComponentBeanImpl _val = new PortComponentBeanImpl(this, -1);

      try {
         this.addPortComponent(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPortComponent(PortComponentBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         PortComponentBean[] _old = this.getPortComponents();
         PortComponentBean[] _new = (PortComponentBean[])((PortComponentBean[])this._getHelper()._removeElement(_old, PortComponentBean.class, param0));
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
               this.setPortComponents(_new);
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
      return this.getWebserviceDescriptionName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 27:
            if (s.equals("webservice-description-name")) {
               return info.compareXpaths(this._getPropertyXpath("webservice-description-name"));
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._PortComponents = new PortComponentBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._WebserviceDescriptionName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WebserviceType = "JAXRPC";
               if (initOne) {
                  break;
               }
            case 2:
               this._WsdlPublishFile = null;
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
            case 14:
               if (s.equals("port-component")) {
                  return 3;
               }
               break;
            case 15:
               if (s.equals("webservice-type")) {
                  return 1;
               }
               break;
            case 17:
               if (s.equals("wsdl-publish-file")) {
                  return 2;
               }
               break;
            case 27:
               if (s.equals("webservice-description-name")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new PortComponentBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "webservice-description-name";
            case 1:
               return "webservice-type";
            case 2:
               return "wsdl-publish-file";
            case 3:
               return "port-component";
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
            case 2:
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
         indices.add("webservice-description-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WebserviceDescriptionBeanImpl bean;

      protected Helper(WebserviceDescriptionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "WebserviceDescriptionName";
            case 1:
               return "WebserviceType";
            case 2:
               return "WsdlPublishFile";
            case 3:
               return "PortComponents";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("PortComponents")) {
            return 3;
         } else if (propName.equals("WebserviceDescriptionName")) {
            return 0;
         } else if (propName.equals("WebserviceType")) {
            return 1;
         } else {
            return propName.equals("WsdlPublishFile") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getPortComponents()));
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

            for(int i = 0; i < this.bean.getPortComponents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPortComponents()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWebserviceDescriptionNameSet()) {
               buf.append("WebserviceDescriptionName");
               buf.append(String.valueOf(this.bean.getWebserviceDescriptionName()));
            }

            if (this.bean.isWebserviceTypeSet()) {
               buf.append("WebserviceType");
               buf.append(String.valueOf(this.bean.getWebserviceType()));
            }

            if (this.bean.isWsdlPublishFileSet()) {
               buf.append("WsdlPublishFile");
               buf.append(String.valueOf(this.bean.getWsdlPublishFile()));
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
            WebserviceDescriptionBeanImpl otherTyped = (WebserviceDescriptionBeanImpl)other;
            this.computeChildDiff("PortComponents", this.bean.getPortComponents(), otherTyped.getPortComponents(), false);
            this.computeDiff("WebserviceDescriptionName", this.bean.getWebserviceDescriptionName(), otherTyped.getWebserviceDescriptionName(), false);
            this.computeDiff("WebserviceType", this.bean.getWebserviceType(), otherTyped.getWebserviceType(), false);
            this.computeDiff("WsdlPublishFile", this.bean.getWsdlPublishFile(), otherTyped.getWsdlPublishFile(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebserviceDescriptionBeanImpl original = (WebserviceDescriptionBeanImpl)event.getSourceBean();
            WebserviceDescriptionBeanImpl proposed = (WebserviceDescriptionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("PortComponents")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPortComponent((PortComponentBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePortComponent((PortComponentBean)update.getRemovedObject());
                  }

                  if (original.getPortComponents() == null || original.getPortComponents().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("WebserviceDescriptionName")) {
                  original.setWebserviceDescriptionName(proposed.getWebserviceDescriptionName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("WebserviceType")) {
                  original.setWebserviceType(proposed.getWebserviceType());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("WsdlPublishFile")) {
                  original.setWsdlPublishFile(proposed.getWsdlPublishFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            WebserviceDescriptionBeanImpl copy = (WebserviceDescriptionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PortComponents")) && this.bean.isPortComponentsSet() && !copy._isSet(3)) {
               PortComponentBean[] oldPortComponents = this.bean.getPortComponents();
               PortComponentBean[] newPortComponents = new PortComponentBean[oldPortComponents.length];

               for(int i = 0; i < newPortComponents.length; ++i) {
                  newPortComponents[i] = (PortComponentBean)((PortComponentBean)this.createCopy((AbstractDescriptorBean)oldPortComponents[i], includeObsolete));
               }

               copy.setPortComponents(newPortComponents);
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceDescriptionName")) && this.bean.isWebserviceDescriptionNameSet()) {
               copy.setWebserviceDescriptionName(this.bean.getWebserviceDescriptionName());
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceType")) && this.bean.isWebserviceTypeSet()) {
               copy.setWebserviceType(this.bean.getWebserviceType());
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlPublishFile")) && this.bean.isWsdlPublishFileSet()) {
               copy.setWsdlPublishFile(this.bean.getWsdlPublishFile());
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
         this.inferSubTree(this.bean.getPortComponents(), clazz, annotation);
      }
   }
}
