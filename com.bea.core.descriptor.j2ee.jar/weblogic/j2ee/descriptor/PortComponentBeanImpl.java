package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class PortComponentBeanImpl extends AbstractDescriptorBean implements PortComponentBean, Serializable {
   private String _Description;
   private String _DisplayName;
   private boolean _EnableMtom;
   private HandlerChainsBean _HandlerChains;
   private PortComponentHandlerBean[] _Handlers;
   private IconBean _Icon;
   private String _Id;
   private String _PortComponentName;
   private String _ProtocolBinding;
   private String _ServiceEndpointInterface;
   private ServiceImplBeanBean _ServiceImplBean;
   private QName _WsdlPort;
   private QName _WsdlService;
   private static SchemaHelper2 _schemaHelper;

   public PortComponentBeanImpl() {
      this._initializeProperty(-1);
   }

   public PortComponentBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PortComponentBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String getPortComponentName() {
      return this._PortComponentName;
   }

   public boolean isPortComponentNameInherited() {
      return false;
   }

   public boolean isPortComponentNameSet() {
      return this._isSet(3);
   }

   public void setPortComponentName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PortComponentName;
      this._PortComponentName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public QName getWsdlService() {
      return this._WsdlService;
   }

   public boolean isWsdlServiceInherited() {
      return false;
   }

   public boolean isWsdlServiceSet() {
      return this._isSet(4);
   }

   public void setWsdlService(QName param0) {
      QName _oldVal = this._WsdlService;
      this._WsdlService = param0;
      this._postSet(4, _oldVal, param0);
   }

   public QName getWsdlPort() {
      return this._WsdlPort;
   }

   public boolean isWsdlPortInherited() {
      return false;
   }

   public boolean isWsdlPortSet() {
      return this._isSet(5);
   }

   public void setWsdlPort(QName param0) {
      QName _oldVal = this._WsdlPort;
      this._WsdlPort = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isEnableMtom() {
      return this._EnableMtom;
   }

   public boolean isEnableMtomInherited() {
      return false;
   }

   public boolean isEnableMtomSet() {
      return this._isSet(6);
   }

   public void setEnableMtom(boolean param0) {
      boolean _oldVal = this._EnableMtom;
      this._EnableMtom = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getProtocolBinding() {
      return this._ProtocolBinding;
   }

   public boolean isProtocolBindingInherited() {
      return false;
   }

   public boolean isProtocolBindingSet() {
      return this._isSet(7);
   }

   public void setProtocolBinding(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ProtocolBinding;
      this._ProtocolBinding = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getServiceEndpointInterface() {
      return this._ServiceEndpointInterface;
   }

   public boolean isServiceEndpointInterfaceInherited() {
      return false;
   }

   public boolean isServiceEndpointInterfaceSet() {
      return this._isSet(8);
   }

   public void setServiceEndpointInterface(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceEndpointInterface;
      this._ServiceEndpointInterface = param0;
      this._postSet(8, _oldVal, param0);
   }

   public ServiceImplBeanBean getServiceImplBean() {
      return this._ServiceImplBean;
   }

   public boolean isServiceImplBeanInherited() {
      return false;
   }

   public boolean isServiceImplBeanSet() {
      return this._isSet(9);
   }

   public void setServiceImplBean(ServiceImplBeanBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getServiceImplBean() != null && param0 != this.getServiceImplBean()) {
         throw new BeanAlreadyExistsException(this.getServiceImplBean() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 9)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ServiceImplBeanBean _oldVal = this._ServiceImplBean;
         this._ServiceImplBean = param0;
         this._postSet(9, _oldVal, param0);
      }
   }

   public ServiceImplBeanBean createServiceImplBean() {
      ServiceImplBeanBeanImpl _val = new ServiceImplBeanBeanImpl(this, -1);

      try {
         this.setServiceImplBean(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServiceImplBean(ServiceImplBeanBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ServiceImplBean;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setServiceImplBean((ServiceImplBeanBean)null);
               this._unSet(9);
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

   public void addHandler(PortComponentHandlerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         PortComponentHandlerBean[] _new;
         if (this._isSet(10)) {
            _new = (PortComponentHandlerBean[])((PortComponentHandlerBean[])this._getHelper()._extendArray(this.getHandlers(), PortComponentHandlerBean.class, param0));
         } else {
            _new = new PortComponentHandlerBean[]{param0};
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

   public PortComponentHandlerBean[] getHandlers() {
      return this._Handlers;
   }

   public boolean isHandlersInherited() {
      return false;
   }

   public boolean isHandlersSet() {
      return this._isSet(10);
   }

   public void removeHandler(PortComponentHandlerBean param0) {
      this.destroyHandler(param0);
   }

   public void setHandlers(PortComponentHandlerBean[] param0) throws InvalidAttributeValueException {
      PortComponentHandlerBean[] param0 = param0 == null ? new PortComponentHandlerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PortComponentHandlerBean[] _oldVal = this._Handlers;
      this._Handlers = (PortComponentHandlerBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public PortComponentHandlerBean createHandler() {
      PortComponentHandlerBeanImpl _val = new PortComponentHandlerBeanImpl(this, -1);

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

   public void destroyHandler(PortComponentHandlerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         PortComponentHandlerBean[] _old = this.getHandlers();
         PortComponentHandlerBean[] _new = (PortComponentHandlerBean[])((PortComponentHandlerBean[])this._getHelper()._removeElement(_old, PortComponentHandlerBean.class, param0));
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

   public HandlerChainsBean getHandlerChains() {
      return this._HandlerChains;
   }

   public boolean isHandlerChainsInherited() {
      return false;
   }

   public boolean isHandlerChainsSet() {
      return this._isSet(11);
   }

   public void setHandlerChains(HandlerChainsBean param0) throws InvalidAttributeValueException {
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

         HandlerChainsBean _oldVal = this._HandlerChains;
         this._HandlerChains = param0;
         this._postSet(11, _oldVal, param0);
      }
   }

   public HandlerChainsBean createHandlerChains() {
      HandlerChainsBeanImpl _val = new HandlerChainsBeanImpl(this, -1);

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

   public void destroyHandlerChains(HandlerChainsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._HandlerChains;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setHandlerChains((HandlerChainsBean)null);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(12);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(12, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getPortComponentName();
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
         case 19:
            if (s.equals("port-component-name")) {
               return info.compareXpaths(this._getPropertyXpath("port-component-name"));
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
            case 11:
               this._HandlerChains = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Handlers = new PortComponentHandlerBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Icon = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._PortComponentName = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._ProtocolBinding = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._ServiceEndpointInterface = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._ServiceImplBean = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._WsdlPort = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._WsdlService = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._EnableMtom = false;
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
                  return 12;
               }
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            case 13:
            case 15:
            case 18:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
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
               if (s.equals("wsdl-port")) {
                  return 5;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("enable-mtom")) {
                  return 6;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 1;
               }

               if (s.equals("wsdl-service")) {
                  return 4;
               }
               break;
            case 14:
               if (s.equals("handler-chains")) {
                  return 11;
               }
               break;
            case 16:
               if (s.equals("protocol-binding")) {
                  return 7;
               }
               break;
            case 17:
               if (s.equals("service-impl-bean")) {
                  return 9;
               }
               break;
            case 19:
               if (s.equals("port-component-name")) {
                  return 3;
               }
               break;
            case 26:
               if (s.equals("service-endpoint-interface")) {
                  return 8;
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
            default:
               return super.getSchemaHelper(propIndex);
            case 9:
               return new ServiceImplBeanBeanImpl.SchemaHelper2();
            case 10:
               return new PortComponentHandlerBeanImpl.SchemaHelper2();
            case 11:
               return new HandlerChainsBeanImpl.SchemaHelper2();
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
               return "port-component-name";
            case 4:
               return "wsdl-service";
            case 5:
               return "wsdl-port";
            case 6:
               return "enable-mtom";
            case 7:
               return "protocol-binding";
            case 8:
               return "service-endpoint-interface";
            case 9:
               return "service-impl-bean";
            case 10:
               return "handler";
            case 11:
               return "handler-chains";
            case 12:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
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
            default:
               return super.isBean(propIndex);
            case 9:
               return true;
            case 10:
               return true;
            case 11:
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
         indices.add("port-component-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PortComponentBeanImpl bean;

      protected Helper(PortComponentBeanImpl bean) {
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
               return "PortComponentName";
            case 4:
               return "WsdlService";
            case 5:
               return "WsdlPort";
            case 6:
               return "EnableMtom";
            case 7:
               return "ProtocolBinding";
            case 8:
               return "ServiceEndpointInterface";
            case 9:
               return "ServiceImplBean";
            case 10:
               return "Handlers";
            case 11:
               return "HandlerChains";
            case 12:
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
         } else if (propName.equals("HandlerChains")) {
            return 11;
         } else if (propName.equals("Handlers")) {
            return 10;
         } else if (propName.equals("Icon")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 12;
         } else if (propName.equals("PortComponentName")) {
            return 3;
         } else if (propName.equals("ProtocolBinding")) {
            return 7;
         } else if (propName.equals("ServiceEndpointInterface")) {
            return 8;
         } else if (propName.equals("ServiceImplBean")) {
            return 9;
         } else if (propName.equals("WsdlPort")) {
            return 5;
         } else if (propName.equals("WsdlService")) {
            return 4;
         } else {
            return propName.equals("EnableMtom") ? 6 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getHandlerChains() != null) {
            iterators.add(new ArrayIterator(new HandlerChainsBean[]{this.bean.getHandlerChains()}));
         }

         iterators.add(new ArrayIterator(this.bean.getHandlers()));
         if (this.bean.getIcon() != null) {
            iterators.add(new ArrayIterator(new IconBean[]{this.bean.getIcon()}));
         }

         if (this.bean.getServiceImplBean() != null) {
            iterators.add(new ArrayIterator(new ServiceImplBeanBean[]{this.bean.getServiceImplBean()}));
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
            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isDisplayNameSet()) {
               buf.append("DisplayName");
               buf.append(String.valueOf(this.bean.getDisplayName()));
            }

            childValue = this.computeChildHashValue(this.bean.getHandlerChains());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getHandlers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getHandlers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getIcon());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isPortComponentNameSet()) {
               buf.append("PortComponentName");
               buf.append(String.valueOf(this.bean.getPortComponentName()));
            }

            if (this.bean.isProtocolBindingSet()) {
               buf.append("ProtocolBinding");
               buf.append(String.valueOf(this.bean.getProtocolBinding()));
            }

            if (this.bean.isServiceEndpointInterfaceSet()) {
               buf.append("ServiceEndpointInterface");
               buf.append(String.valueOf(this.bean.getServiceEndpointInterface()));
            }

            childValue = this.computeChildHashValue(this.bean.getServiceImplBean());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWsdlPortSet()) {
               buf.append("WsdlPort");
               buf.append(String.valueOf(this.bean.getWsdlPort()));
            }

            if (this.bean.isWsdlServiceSet()) {
               buf.append("WsdlService");
               buf.append(String.valueOf(this.bean.getWsdlService()));
            }

            if (this.bean.isEnableMtomSet()) {
               buf.append("EnableMtom");
               buf.append(String.valueOf(this.bean.isEnableMtom()));
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
            PortComponentBeanImpl otherTyped = (PortComponentBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("DisplayName", this.bean.getDisplayName(), otherTyped.getDisplayName(), false);
            this.computeChildDiff("HandlerChains", this.bean.getHandlerChains(), otherTyped.getHandlerChains(), false);
            this.computeChildDiff("Handlers", this.bean.getHandlers(), otherTyped.getHandlers(), false);
            this.computeChildDiff("Icon", this.bean.getIcon(), otherTyped.getIcon(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("PortComponentName", this.bean.getPortComponentName(), otherTyped.getPortComponentName(), false);
            this.computeDiff("ProtocolBinding", this.bean.getProtocolBinding(), otherTyped.getProtocolBinding(), false);
            this.computeDiff("ServiceEndpointInterface", this.bean.getServiceEndpointInterface(), otherTyped.getServiceEndpointInterface(), false);
            this.computeChildDiff("ServiceImplBean", this.bean.getServiceImplBean(), otherTyped.getServiceImplBean(), false);
            this.computeDiff("WsdlPort", this.bean.getWsdlPort(), otherTyped.getWsdlPort(), false);
            this.computeDiff("WsdlService", this.bean.getWsdlService(), otherTyped.getWsdlService(), false);
            this.computeDiff("EnableMtom", this.bean.isEnableMtom(), otherTyped.isEnableMtom(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PortComponentBeanImpl original = (PortComponentBeanImpl)event.getSourceBean();
            PortComponentBeanImpl proposed = (PortComponentBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("DisplayName")) {
                  original.setDisplayName(proposed.getDisplayName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("HandlerChains")) {
                  if (type == 2) {
                     original.setHandlerChains((HandlerChainsBean)this.createCopy((AbstractDescriptorBean)proposed.getHandlerChains()));
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
                        original.addHandler((PortComponentHandlerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeHandler((PortComponentHandlerBean)update.getRemovedObject());
                  }

                  if (original.getHandlers() == null || original.getHandlers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
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
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("PortComponentName")) {
                  original.setPortComponentName(proposed.getPortComponentName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ProtocolBinding")) {
                  original.setProtocolBinding(proposed.getProtocolBinding());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("ServiceEndpointInterface")) {
                  original.setServiceEndpointInterface(proposed.getServiceEndpointInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ServiceImplBean")) {
                  if (type == 2) {
                     original.setServiceImplBean((ServiceImplBeanBean)this.createCopy((AbstractDescriptorBean)proposed.getServiceImplBean()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ServiceImplBean", (DescriptorBean)original.getServiceImplBean());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("WsdlPort")) {
                  original.setWsdlPort(proposed.getWsdlPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("WsdlService")) {
                  original.setWsdlService(proposed.getWsdlService());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("EnableMtom")) {
                  original.setEnableMtom(proposed.isEnableMtom());
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
            PortComponentBeanImpl copy = (PortComponentBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("DisplayName")) && this.bean.isDisplayNameSet()) {
               copy.setDisplayName(this.bean.getDisplayName());
            }

            if ((excludeProps == null || !excludeProps.contains("HandlerChains")) && this.bean.isHandlerChainsSet() && !copy._isSet(11)) {
               Object o = this.bean.getHandlerChains();
               copy.setHandlerChains((HandlerChainsBean)null);
               copy.setHandlerChains(o == null ? null : (HandlerChainsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Handlers")) && this.bean.isHandlersSet() && !copy._isSet(10)) {
               PortComponentHandlerBean[] oldHandlers = this.bean.getHandlers();
               PortComponentHandlerBean[] newHandlers = new PortComponentHandlerBean[oldHandlers.length];

               for(int i = 0; i < newHandlers.length; ++i) {
                  newHandlers[i] = (PortComponentHandlerBean)((PortComponentHandlerBean)this.createCopy((AbstractDescriptorBean)oldHandlers[i], includeObsolete));
               }

               copy.setHandlers(newHandlers);
            }

            if ((excludeProps == null || !excludeProps.contains("Icon")) && this.bean.isIconSet() && !copy._isSet(2)) {
               Object o = this.bean.getIcon();
               copy.setIcon((IconBean)null);
               copy.setIcon(o == null ? null : (IconBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("PortComponentName")) && this.bean.isPortComponentNameSet()) {
               copy.setPortComponentName(this.bean.getPortComponentName());
            }

            if ((excludeProps == null || !excludeProps.contains("ProtocolBinding")) && this.bean.isProtocolBindingSet()) {
               copy.setProtocolBinding(this.bean.getProtocolBinding());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceEndpointInterface")) && this.bean.isServiceEndpointInterfaceSet()) {
               copy.setServiceEndpointInterface(this.bean.getServiceEndpointInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceImplBean")) && this.bean.isServiceImplBeanSet() && !copy._isSet(9)) {
               Object o = this.bean.getServiceImplBean();
               copy.setServiceImplBean((ServiceImplBeanBean)null);
               copy.setServiceImplBean(o == null ? null : (ServiceImplBeanBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlPort")) && this.bean.isWsdlPortSet()) {
               copy.setWsdlPort(this.bean.getWsdlPort());
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlService")) && this.bean.isWsdlServiceSet()) {
               copy.setWsdlService(this.bean.getWsdlService());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableMtom")) && this.bean.isEnableMtomSet()) {
               copy.setEnableMtom(this.bean.isEnableMtom());
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
         this.inferSubTree(this.bean.getIcon(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceImplBean(), clazz, annotation);
      }
   }
}
