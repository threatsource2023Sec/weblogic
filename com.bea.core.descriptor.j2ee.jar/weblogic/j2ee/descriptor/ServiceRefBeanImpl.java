package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import javax.xml.namespace.QName;
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

public class ServiceRefBeanImpl extends AbstractDescriptorBean implements ServiceRefBean, Serializable {
   private String[] _Descriptions;
   private String[] _DisplayNames;
   private ServiceRefHandlerChainsBean _HandlerChains;
   private ServiceRefHandlerBean[] _Handlers;
   private IconBean[] _Icons;
   private String _Id;
   private InjectionTargetBean[] _InjectionTargets;
   private String _JaxrpcMappingFile;
   private String _MappedName;
   private PortComponentRefBean[] _PortComponentRefs;
   private String _ServiceInterface;
   private QName _ServiceQname;
   private String _ServiceRefName;
   private String _ServiceRefType;
   private String _WsdlFile;
   private static SchemaHelper2 _schemaHelper;

   public ServiceRefBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServiceRefBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServiceRefBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String[] getDisplayNames() {
      return this._DisplayNames;
   }

   public boolean isDisplayNamesInherited() {
      return false;
   }

   public boolean isDisplayNamesSet() {
      return this._isSet(1);
   }

   public void addDisplayName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
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
      this._postSet(1, _oldVal, param0);
   }

   public void addIcon(IconBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         IconBean[] _new;
         if (this._isSet(2)) {
            _new = (IconBean[])((IconBean[])this._getHelper()._extendArray(this.getIcons(), IconBean.class, param0));
         } else {
            _new = new IconBean[]{param0};
         }

         try {
            this.setIcons(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public IconBean[] getIcons() {
      return this._Icons;
   }

   public boolean isIconsInherited() {
      return false;
   }

   public boolean isIconsSet() {
      return this._isSet(2);
   }

   public void removeIcon(IconBean param0) {
      this.destroyIcon(param0);
   }

   public void setIcons(IconBean[] param0) throws InvalidAttributeValueException {
      IconBean[] param0 = param0 == null ? new IconBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      IconBean[] _oldVal = this._Icons;
      this._Icons = (IconBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public IconBean createIcon() {
      IconBeanImpl _val = new IconBeanImpl(this, -1);

      try {
         this.addIcon(_val);
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
         this._checkIsPotentialChild(param0, 2);
         IconBean[] _old = this.getIcons();
         IconBean[] _new = (IconBean[])((IconBean[])this._getHelper()._removeElement(_old, IconBean.class, param0));
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
               this.setIcons(_new);
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

   public String getServiceRefName() {
      return this._ServiceRefName;
   }

   public boolean isServiceRefNameInherited() {
      return false;
   }

   public boolean isServiceRefNameSet() {
      return this._isSet(3);
   }

   public void setServiceRefName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceRefName;
      this._ServiceRefName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getServiceInterface() {
      return this._ServiceInterface;
   }

   public boolean isServiceInterfaceInherited() {
      return false;
   }

   public boolean isServiceInterfaceSet() {
      return this._isSet(4);
   }

   public void setServiceInterface(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceInterface;
      this._ServiceInterface = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getServiceRefType() {
      return this._ServiceRefType;
   }

   public boolean isServiceRefTypeInherited() {
      return false;
   }

   public boolean isServiceRefTypeSet() {
      return this._isSet(5);
   }

   public void setServiceRefType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceRefType;
      this._ServiceRefType = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getWsdlFile() {
      return this._WsdlFile;
   }

   public boolean isWsdlFileInherited() {
      return false;
   }

   public boolean isWsdlFileSet() {
      return this._isSet(6);
   }

   public void setWsdlFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WsdlFile;
      this._WsdlFile = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getJaxrpcMappingFile() {
      return this._JaxrpcMappingFile;
   }

   public boolean isJaxrpcMappingFileInherited() {
      return false;
   }

   public boolean isJaxrpcMappingFileSet() {
      return this._isSet(7);
   }

   public void setJaxrpcMappingFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JaxrpcMappingFile;
      this._JaxrpcMappingFile = param0;
      this._postSet(7, _oldVal, param0);
   }

   public QName getServiceQname() {
      return this._ServiceQname;
   }

   public boolean isServiceQnameInherited() {
      return false;
   }

   public boolean isServiceQnameSet() {
      return this._isSet(8);
   }

   public void setServiceQname(QName param0) {
      QName _oldVal = this._ServiceQname;
      this._ServiceQname = param0;
      this._postSet(8, _oldVal, param0);
   }

   public void addPortComponentRef(PortComponentRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         PortComponentRefBean[] _new;
         if (this._isSet(9)) {
            _new = (PortComponentRefBean[])((PortComponentRefBean[])this._getHelper()._extendArray(this.getPortComponentRefs(), PortComponentRefBean.class, param0));
         } else {
            _new = new PortComponentRefBean[]{param0};
         }

         try {
            this.setPortComponentRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PortComponentRefBean[] getPortComponentRefs() {
      return this._PortComponentRefs;
   }

   public boolean isPortComponentRefsInherited() {
      return false;
   }

   public boolean isPortComponentRefsSet() {
      return this._isSet(9);
   }

   public void removePortComponentRef(PortComponentRefBean param0) {
      this.destroyPortComponentRef(param0);
   }

   public void setPortComponentRefs(PortComponentRefBean[] param0) throws InvalidAttributeValueException {
      PortComponentRefBean[] param0 = param0 == null ? new PortComponentRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PortComponentRefBean[] _oldVal = this._PortComponentRefs;
      this._PortComponentRefs = (PortComponentRefBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public PortComponentRefBean createPortComponentRef() {
      PortComponentRefBeanImpl _val = new PortComponentRefBeanImpl(this, -1);

      try {
         this.addPortComponentRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPortComponentRef(PortComponentRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 9);
         PortComponentRefBean[] _old = this.getPortComponentRefs();
         PortComponentRefBean[] _new = (PortComponentRefBean[])((PortComponentRefBean[])this._getHelper()._removeElement(_old, PortComponentRefBean.class, param0));
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
               this.setPortComponentRefs(_new);
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

   public void addHandler(ServiceRefHandlerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         ServiceRefHandlerBean[] _new;
         if (this._isSet(10)) {
            _new = (ServiceRefHandlerBean[])((ServiceRefHandlerBean[])this._getHelper()._extendArray(this.getHandlers(), ServiceRefHandlerBean.class, param0));
         } else {
            _new = new ServiceRefHandlerBean[]{param0};
         }

         try {
            this.setHandlers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceRefHandlerBean[] getHandlers() {
      return this._Handlers;
   }

   public boolean isHandlersInherited() {
      return false;
   }

   public boolean isHandlersSet() {
      return this._isSet(10);
   }

   public void removeHandler(ServiceRefHandlerBean param0) {
      this.destroyHandler(param0);
   }

   public void setHandlers(ServiceRefHandlerBean[] param0) throws InvalidAttributeValueException {
      ServiceRefHandlerBean[] param0 = param0 == null ? new ServiceRefHandlerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceRefHandlerBean[] _oldVal = this._Handlers;
      this._Handlers = (ServiceRefHandlerBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public ServiceRefHandlerBean createHandler() {
      ServiceRefHandlerBeanImpl _val = new ServiceRefHandlerBeanImpl(this, -1);

      try {
         this.addHandler(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyHandler(ServiceRefHandlerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         ServiceRefHandlerBean[] _old = this.getHandlers();
         ServiceRefHandlerBean[] _new = (ServiceRefHandlerBean[])((ServiceRefHandlerBean[])this._getHelper()._removeElement(_old, ServiceRefHandlerBean.class, param0));
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
               this.setHandlers(_new);
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

   public ServiceRefHandlerChainsBean getHandlerChains() {
      return this._HandlerChains;
   }

   public boolean isHandlerChainsInherited() {
      return false;
   }

   public boolean isHandlerChainsSet() {
      return this._isSet(11);
   }

   public void setHandlerChains(ServiceRefHandlerChainsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getHandlerChains() != null && param0 != this.getHandlerChains()) {
         throw new BeanAlreadyExistsException(this.getHandlerChains() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 11)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ServiceRefHandlerChainsBean _oldVal = this._HandlerChains;
         this._HandlerChains = param0;
         this._postSet(11, _oldVal, param0);
      }
   }

   public ServiceRefHandlerChainsBean createHandlerChains() {
      ServiceRefHandlerChainsBeanImpl _val = new ServiceRefHandlerChainsBeanImpl(this, -1);

      try {
         this.setHandlerChains(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyHandlerChains(ServiceRefHandlerChainsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._HandlerChains;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setHandlerChains((ServiceRefHandlerChainsBean)null);
               this._unSet(11);
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

   public String getMappedName() {
      return this._MappedName;
   }

   public boolean isMappedNameInherited() {
      return false;
   }

   public boolean isMappedNameSet() {
      return this._isSet(12);
   }

   public void setMappedName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MappedName;
      this._MappedName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public void addInjectionTarget(InjectionTargetBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         InjectionTargetBean[] _new;
         if (this._isSet(13)) {
            _new = (InjectionTargetBean[])((InjectionTargetBean[])this._getHelper()._extendArray(this.getInjectionTargets(), InjectionTargetBean.class, param0));
         } else {
            _new = new InjectionTargetBean[]{param0};
         }

         try {
            this.setInjectionTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InjectionTargetBean[] getInjectionTargets() {
      return this._InjectionTargets;
   }

   public boolean isInjectionTargetsInherited() {
      return false;
   }

   public boolean isInjectionTargetsSet() {
      return this._isSet(13);
   }

   public void removeInjectionTarget(InjectionTargetBean param0) {
      this.destroyInjectionTarget(param0);
   }

   public void setInjectionTargets(InjectionTargetBean[] param0) throws InvalidAttributeValueException {
      InjectionTargetBean[] param0 = param0 == null ? new InjectionTargetBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InjectionTargetBean[] _oldVal = this._InjectionTargets;
      this._InjectionTargets = (InjectionTargetBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public InjectionTargetBean createInjectionTarget() {
      InjectionTargetBeanImpl _val = new InjectionTargetBeanImpl(this, -1);

      try {
         this.addInjectionTarget(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInjectionTarget(InjectionTargetBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         InjectionTargetBean[] _old = this.getInjectionTargets();
         InjectionTargetBean[] _new = (InjectionTargetBean[])((InjectionTargetBean[])this._getHelper()._removeElement(_old, InjectionTargetBean.class, param0));
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
               this.setInjectionTargets(_new);
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
      return this._isSet(14);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(14, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getServiceRefName();
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
         case 16:
            if (s.equals("service-ref-name")) {
               return info.compareXpaths(this._getPropertyXpath("service-ref-name"));
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
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._DisplayNames = new String[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._HandlerChains = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Handlers = new ServiceRefHandlerBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Icons = new IconBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._InjectionTargets = new InjectionTargetBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._JaxrpcMappingFile = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._MappedName = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._PortComponentRefs = new PortComponentRefBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._ServiceInterface = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._ServiceQname = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ServiceRefName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._ServiceRefType = null;
               if (initOne) {
                  break;
               }
            case 6:
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
                  return 14;
               }
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            case 15:
            default:
               break;
            case 4:
               if (s.equals("icon")) {
                  return 2;
               }
               break;
            case 7:
               if (s.equals("handler")) {
                  return 10;
               }
               break;
            case 9:
               if (s.equals("wsdl-file")) {
                  return 6;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("mapped-name")) {
                  return 12;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 1;
               }
               break;
            case 13:
               if (s.equals("service-qname")) {
                  return 8;
               }
               break;
            case 14:
               if (s.equals("handler-chains")) {
                  return 11;
               }
               break;
            case 16:
               if (s.equals("injection-target")) {
                  return 13;
               }

               if (s.equals("service-ref-name")) {
                  return 3;
               }

               if (s.equals("service-ref-type")) {
                  return 5;
               }
               break;
            case 17:
               if (s.equals("service-interface")) {
                  return 4;
               }
               break;
            case 18:
               if (s.equals("port-component-ref")) {
                  return 9;
               }
               break;
            case 19:
               if (s.equals("jaxrpc-mapping-file")) {
                  return 7;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new IconBeanImpl.SchemaHelper2();
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            default:
               return super.getSchemaHelper(propIndex);
            case 9:
               return new PortComponentRefBeanImpl.SchemaHelper2();
            case 10:
               return new ServiceRefHandlerBeanImpl.SchemaHelper2();
            case 11:
               return new ServiceRefHandlerChainsBeanImpl.SchemaHelper2();
            case 13:
               return new InjectionTargetBeanImpl.SchemaHelper2();
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
               return "service-ref-name";
            case 4:
               return "service-interface";
            case 5:
               return "service-ref-type";
            case 6:
               return "wsdl-file";
            case 7:
               return "jaxrpc-mapping-file";
            case 8:
               return "service-qname";
            case 9:
               return "port-component-ref";
            case 10:
               return "handler";
            case 11:
               return "handler-chains";
            case 12:
               return "mapped-name";
            case 13:
               return "injection-target";
            case 14:
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
            case 2:
               return true;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
            default:
               return super.isArray(propIndex);
            case 9:
               return true;
            case 10:
               return true;
            case 13:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            default:
               return super.isBean(propIndex);
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 13:
               return true;
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
         indices.add("service-ref-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServiceRefBeanImpl bean;

      protected Helper(ServiceRefBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "DisplayNames";
            case 2:
               return "Icons";
            case 3:
               return "ServiceRefName";
            case 4:
               return "ServiceInterface";
            case 5:
               return "ServiceRefType";
            case 6:
               return "WsdlFile";
            case 7:
               return "JaxrpcMappingFile";
            case 8:
               return "ServiceQname";
            case 9:
               return "PortComponentRefs";
            case 10:
               return "Handlers";
            case 11:
               return "HandlerChains";
            case 12:
               return "MappedName";
            case 13:
               return "InjectionTargets";
            case 14:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("DisplayNames")) {
            return 1;
         } else if (propName.equals("HandlerChains")) {
            return 11;
         } else if (propName.equals("Handlers")) {
            return 10;
         } else if (propName.equals("Icons")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 14;
         } else if (propName.equals("InjectionTargets")) {
            return 13;
         } else if (propName.equals("JaxrpcMappingFile")) {
            return 7;
         } else if (propName.equals("MappedName")) {
            return 12;
         } else if (propName.equals("PortComponentRefs")) {
            return 9;
         } else if (propName.equals("ServiceInterface")) {
            return 4;
         } else if (propName.equals("ServiceQname")) {
            return 8;
         } else if (propName.equals("ServiceRefName")) {
            return 3;
         } else if (propName.equals("ServiceRefType")) {
            return 5;
         } else {
            return propName.equals("WsdlFile") ? 6 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getHandlerChains() != null) {
            iterators.add(new ArrayIterator(new ServiceRefHandlerChainsBean[]{this.bean.getHandlerChains()}));
         }

         iterators.add(new ArrayIterator(this.bean.getHandlers()));
         iterators.add(new ArrayIterator(this.bean.getIcons()));
         iterators.add(new ArrayIterator(this.bean.getInjectionTargets()));
         iterators.add(new ArrayIterator(this.bean.getPortComponentRefs()));
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
            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isDisplayNamesSet()) {
               buf.append("DisplayNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDisplayNames())));
            }

            childValue = this.computeChildHashValue(this.bean.getHandlerChains());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getHandlers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getHandlers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getIcons().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getIcons()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getInjectionTargets().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInjectionTargets()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJaxrpcMappingFileSet()) {
               buf.append("JaxrpcMappingFile");
               buf.append(String.valueOf(this.bean.getJaxrpcMappingFile()));
            }

            if (this.bean.isMappedNameSet()) {
               buf.append("MappedName");
               buf.append(String.valueOf(this.bean.getMappedName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPortComponentRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPortComponentRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServiceInterfaceSet()) {
               buf.append("ServiceInterface");
               buf.append(String.valueOf(this.bean.getServiceInterface()));
            }

            if (this.bean.isServiceQnameSet()) {
               buf.append("ServiceQname");
               buf.append(String.valueOf(this.bean.getServiceQname()));
            }

            if (this.bean.isServiceRefNameSet()) {
               buf.append("ServiceRefName");
               buf.append(String.valueOf(this.bean.getServiceRefName()));
            }

            if (this.bean.isServiceRefTypeSet()) {
               buf.append("ServiceRefType");
               buf.append(String.valueOf(this.bean.getServiceRefType()));
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
            ServiceRefBeanImpl otherTyped = (ServiceRefBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeChildDiff("HandlerChains", this.bean.getHandlerChains(), otherTyped.getHandlerChains(), false);
            this.computeChildDiff("Handlers", this.bean.getHandlers(), otherTyped.getHandlers(), false);
            this.computeChildDiff("Icons", this.bean.getIcons(), otherTyped.getIcons(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("InjectionTargets", this.bean.getInjectionTargets(), otherTyped.getInjectionTargets(), false);
            this.computeDiff("JaxrpcMappingFile", this.bean.getJaxrpcMappingFile(), otherTyped.getJaxrpcMappingFile(), false);
            this.computeDiff("MappedName", this.bean.getMappedName(), otherTyped.getMappedName(), false);
            this.computeChildDiff("PortComponentRefs", this.bean.getPortComponentRefs(), otherTyped.getPortComponentRefs(), false);
            this.computeDiff("ServiceInterface", this.bean.getServiceInterface(), otherTyped.getServiceInterface(), false);
            this.computeDiff("ServiceQname", this.bean.getServiceQname(), otherTyped.getServiceQname(), false);
            this.computeDiff("ServiceRefName", this.bean.getServiceRefName(), otherTyped.getServiceRefName(), false);
            this.computeDiff("ServiceRefType", this.bean.getServiceRefType(), otherTyped.getServiceRefType(), false);
            this.computeDiff("WsdlFile", this.bean.getWsdlFile(), otherTyped.getWsdlFile(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServiceRefBeanImpl original = (ServiceRefBeanImpl)event.getSourceBean();
            ServiceRefBeanImpl proposed = (ServiceRefBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Descriptions")) {
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
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("HandlerChains")) {
                  if (type == 2) {
                     original.setHandlerChains((ServiceRefHandlerChainsBean)this.createCopy((AbstractDescriptorBean)proposed.getHandlerChains()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("HandlerChains", (DescriptorBean)original.getHandlerChains());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Handlers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addHandler((ServiceRefHandlerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeHandler((ServiceRefHandlerBean)update.getRemovedObject());
                  }

                  if (original.getHandlers() == null || original.getHandlers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("Icons")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addIcon((IconBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeIcon((IconBean)update.getRemovedObject());
                  }

                  if (original.getIcons() == null || original.getIcons().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("InjectionTargets")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addInjectionTarget((InjectionTargetBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInjectionTarget((InjectionTargetBean)update.getRemovedObject());
                  }

                  if (original.getInjectionTargets() == null || original.getInjectionTargets().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("JaxrpcMappingFile")) {
                  original.setJaxrpcMappingFile(proposed.getJaxrpcMappingFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("MappedName")) {
                  original.setMappedName(proposed.getMappedName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("PortComponentRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPortComponentRef((PortComponentRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePortComponentRef((PortComponentRefBean)update.getRemovedObject());
                  }

                  if (original.getPortComponentRefs() == null || original.getPortComponentRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("ServiceInterface")) {
                  original.setServiceInterface(proposed.getServiceInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ServiceQname")) {
                  original.setServiceQname(proposed.getServiceQname());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ServiceRefName")) {
                  original.setServiceRefName(proposed.getServiceRefName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ServiceRefType")) {
                  original.setServiceRefType(proposed.getServiceRefType());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("WsdlFile")) {
                  original.setWsdlFile(proposed.getWsdlFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            ServiceRefBeanImpl copy = (ServiceRefBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DisplayNames")) && this.bean.isDisplayNamesSet()) {
               o = this.bean.getDisplayNames();
               copy.setDisplayNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("HandlerChains")) && this.bean.isHandlerChainsSet() && !copy._isSet(11)) {
               Object o = this.bean.getHandlerChains();
               copy.setHandlerChains((ServiceRefHandlerChainsBean)null);
               copy.setHandlerChains(o == null ? null : (ServiceRefHandlerChainsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("Handlers")) && this.bean.isHandlersSet() && !copy._isSet(10)) {
               ServiceRefHandlerBean[] oldHandlers = this.bean.getHandlers();
               ServiceRefHandlerBean[] newHandlers = new ServiceRefHandlerBean[oldHandlers.length];

               for(i = 0; i < newHandlers.length; ++i) {
                  newHandlers[i] = (ServiceRefHandlerBean)((ServiceRefHandlerBean)this.createCopy((AbstractDescriptorBean)oldHandlers[i], includeObsolete));
               }

               copy.setHandlers(newHandlers);
            }

            if ((excludeProps == null || !excludeProps.contains("Icons")) && this.bean.isIconsSet() && !copy._isSet(2)) {
               IconBean[] oldIcons = this.bean.getIcons();
               IconBean[] newIcons = new IconBean[oldIcons.length];

               for(i = 0; i < newIcons.length; ++i) {
                  newIcons[i] = (IconBean)((IconBean)this.createCopy((AbstractDescriptorBean)oldIcons[i], includeObsolete));
               }

               copy.setIcons(newIcons);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InjectionTargets")) && this.bean.isInjectionTargetsSet() && !copy._isSet(13)) {
               InjectionTargetBean[] oldInjectionTargets = this.bean.getInjectionTargets();
               InjectionTargetBean[] newInjectionTargets = new InjectionTargetBean[oldInjectionTargets.length];

               for(i = 0; i < newInjectionTargets.length; ++i) {
                  newInjectionTargets[i] = (InjectionTargetBean)((InjectionTargetBean)this.createCopy((AbstractDescriptorBean)oldInjectionTargets[i], includeObsolete));
               }

               copy.setInjectionTargets(newInjectionTargets);
            }

            if ((excludeProps == null || !excludeProps.contains("JaxrpcMappingFile")) && this.bean.isJaxrpcMappingFileSet()) {
               copy.setJaxrpcMappingFile(this.bean.getJaxrpcMappingFile());
            }

            if ((excludeProps == null || !excludeProps.contains("MappedName")) && this.bean.isMappedNameSet()) {
               copy.setMappedName(this.bean.getMappedName());
            }

            if ((excludeProps == null || !excludeProps.contains("PortComponentRefs")) && this.bean.isPortComponentRefsSet() && !copy._isSet(9)) {
               PortComponentRefBean[] oldPortComponentRefs = this.bean.getPortComponentRefs();
               PortComponentRefBean[] newPortComponentRefs = new PortComponentRefBean[oldPortComponentRefs.length];

               for(i = 0; i < newPortComponentRefs.length; ++i) {
                  newPortComponentRefs[i] = (PortComponentRefBean)((PortComponentRefBean)this.createCopy((AbstractDescriptorBean)oldPortComponentRefs[i], includeObsolete));
               }

               copy.setPortComponentRefs(newPortComponentRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceInterface")) && this.bean.isServiceInterfaceSet()) {
               copy.setServiceInterface(this.bean.getServiceInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceQname")) && this.bean.isServiceQnameSet()) {
               copy.setServiceQname(this.bean.getServiceQname());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceRefName")) && this.bean.isServiceRefNameSet()) {
               copy.setServiceRefName(this.bean.getServiceRefName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceRefType")) && this.bean.isServiceRefTypeSet()) {
               copy.setServiceRefType(this.bean.getServiceRefType());
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
         this.inferSubTree(this.bean.getHandlerChains(), clazz, annotation);
         this.inferSubTree(this.bean.getHandlers(), clazz, annotation);
         this.inferSubTree(this.bean.getIcons(), clazz, annotation);
         this.inferSubTree(this.bean.getInjectionTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getPortComponentRefs(), clazz, annotation);
      }
   }
}
