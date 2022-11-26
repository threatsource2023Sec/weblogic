package weblogic.j2ee.descriptor;

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

public class WebserviceDescriptionBeanImpl extends AbstractDescriptorBean implements WebserviceDescriptionBean, Serializable {
   private String _Description;
   private String _DisplayName;
   private IconBean _Icon;
   private String _Id;
   private String _JaxrpcMappingFile;
   private PortComponentBean[] _PortComponents;
   private String _WebserviceDescriptionName;
   private String _WsdlFile;
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

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDisplayName() {
      return this._DisplayName;
   }

   public boolean isDisplayNameInherited() {
      return false;
   }

   public boolean isDisplayNameSet() {
      return this._isSet(1);
   }

   public void setDisplayName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DisplayName;
      this._DisplayName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public IconBean getIcon() {
      return this._Icon;
   }

   public boolean isIconInherited() {
      return false;
   }

   public boolean isIconSet() {
      return this._isSet(2);
   }

   public void setIcon(IconBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getIcon() != null && param0 != this.getIcon()) {
         throw new BeanAlreadyExistsException(this.getIcon() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         IconBean _oldVal = this._Icon;
         this._Icon = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public IconBean createIcon() {
      IconBeanImpl _val = new IconBeanImpl(this, -1);

      try {
         this.setIcon(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyIcon(IconBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Icon;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setIcon((IconBean)null);
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

   public String getWebserviceDescriptionName() {
      return this._WebserviceDescriptionName;
   }

   public boolean isWebserviceDescriptionNameInherited() {
      return false;
   }

   public boolean isWebserviceDescriptionNameSet() {
      return this._isSet(3);
   }

   public void setWebserviceDescriptionName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WebserviceDescriptionName;
      this._WebserviceDescriptionName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getWsdlFile() {
      return this._WsdlFile;
   }

   public boolean isWsdlFileInherited() {
      return false;
   }

   public boolean isWsdlFileSet() {
      return this._isSet(4);
   }

   public void setWsdlFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WsdlFile;
      this._WsdlFile = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getJaxrpcMappingFile() {
      return this._JaxrpcMappingFile;
   }

   public boolean isJaxrpcMappingFileInherited() {
      return false;
   }

   public boolean isJaxrpcMappingFileSet() {
      return this._isSet(5);
   }

   public void setJaxrpcMappingFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JaxrpcMappingFile;
      this._JaxrpcMappingFile = param0;
      this._postSet(5, _oldVal, param0);
   }

   public void addPortComponent(PortComponentBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         PortComponentBean[] _new;
         if (this._isSet(6)) {
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
      return this._isSet(6);
   }

   public void removePortComponent(PortComponentBean param0) {
      this.destroyPortComponent(param0);
   }

   public void setPortComponents(PortComponentBean[] param0) throws InvalidAttributeValueException {
      PortComponentBean[] param0 = param0 == null ? new PortComponentBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PortComponentBean[] _oldVal = this._PortComponents;
      this._PortComponents = (PortComponentBean[])param0;
      this._postSet(6, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 6);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._DisplayName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Icon = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._JaxrpcMappingFile = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._PortComponents = new PortComponentBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._WebserviceDescriptionName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._WsdlFile = null;
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
                  return 7;
               }
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            default:
               break;
            case 4:
               if (s.equals("icon")) {
                  return 2;
               }
               break;
            case 9:
               if (s.equals("wsdl-file")) {
                  return 4;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("port-component")) {
                  return 6;
               }
               break;
            case 19:
               if (s.equals("jaxrpc-mapping-file")) {
                  return 5;
               }
               break;
            case 27:
               if (s.equals("webservice-description-name")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new IconBeanImpl.SchemaHelper2();
            case 6:
               return new PortComponentBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "display-name";
            case 2:
               return "icon";
            case 3:
               return "webservice-description-name";
            case 4:
               return "wsdl-file";
            case 5:
               return "jaxrpc-mapping-file";
            case 6:
               return "port-component";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 6:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 6:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 3:
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
               return "Description";
            case 1:
               return "DisplayName";
            case 2:
               return "Icon";
            case 3:
               return "WebserviceDescriptionName";
            case 4:
               return "WsdlFile";
            case 5:
               return "JaxrpcMappingFile";
            case 6:
               return "PortComponents";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("DisplayName")) {
            return 1;
         } else if (propName.equals("Icon")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("JaxrpcMappingFile")) {
            return 5;
         } else if (propName.equals("PortComponents")) {
            return 6;
         } else if (propName.equals("WebserviceDescriptionName")) {
            return 3;
         } else {
            return propName.equals("WsdlFile") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getIcon() != null) {
            iterators.add(new ArrayIterator(new IconBean[]{this.bean.getIcon()}));
         }

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
            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isDisplayNameSet()) {
               buf.append("DisplayName");
               buf.append(String.valueOf(this.bean.getDisplayName()));
            }

            childValue = this.computeChildHashValue(this.bean.getIcon());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isJaxrpcMappingFileSet()) {
               buf.append("JaxrpcMappingFile");
               buf.append(String.valueOf(this.bean.getJaxrpcMappingFile()));
            }

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

            if (this.bean.isWsdlFileSet()) {
               buf.append("WsdlFile");
               buf.append(String.valueOf(this.bean.getWsdlFile()));
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
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("DisplayName", this.bean.getDisplayName(), otherTyped.getDisplayName(), false);
            this.computeChildDiff("Icon", this.bean.getIcon(), otherTyped.getIcon(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("JaxrpcMappingFile", this.bean.getJaxrpcMappingFile(), otherTyped.getJaxrpcMappingFile(), false);
            this.computeChildDiff("PortComponents", this.bean.getPortComponents(), otherTyped.getPortComponents(), false);
            this.computeDiff("WebserviceDescriptionName", this.bean.getWebserviceDescriptionName(), otherTyped.getWebserviceDescriptionName(), false);
            this.computeDiff("WsdlFile", this.bean.getWsdlFile(), otherTyped.getWsdlFile(), false);
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
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("DisplayName")) {
                  original.setDisplayName(proposed.getDisplayName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Icon")) {
                  if (type == 2) {
                     original.setIcon((IconBean)this.createCopy((AbstractDescriptorBean)proposed.getIcon()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Icon", (DescriptorBean)original.getIcon());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("JaxrpcMappingFile")) {
                  original.setJaxrpcMappingFile(proposed.getJaxrpcMappingFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("PortComponents")) {
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
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("WebserviceDescriptionName")) {
                  original.setWebserviceDescriptionName(proposed.getWebserviceDescriptionName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("WsdlFile")) {
                  original.setWsdlFile(proposed.getWsdlFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("DisplayName")) && this.bean.isDisplayNameSet()) {
               copy.setDisplayName(this.bean.getDisplayName());
            }

            if ((excludeProps == null || !excludeProps.contains("Icon")) && this.bean.isIconSet() && !copy._isSet(2)) {
               Object o = this.bean.getIcon();
               copy.setIcon((IconBean)null);
               copy.setIcon(o == null ? null : (IconBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JaxrpcMappingFile")) && this.bean.isJaxrpcMappingFileSet()) {
               copy.setJaxrpcMappingFile(this.bean.getJaxrpcMappingFile());
            }

            if ((excludeProps == null || !excludeProps.contains("PortComponents")) && this.bean.isPortComponentsSet() && !copy._isSet(6)) {
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

            if ((excludeProps == null || !excludeProps.contains("WsdlFile")) && this.bean.isWsdlFileSet()) {
               copy.setWsdlFile(this.bean.getWsdlFile());
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
         this.inferSubTree(this.bean.getIcon(), clazz, annotation);
         this.inferSubTree(this.bean.getPortComponents(), clazz, annotation);
      }
   }
}
