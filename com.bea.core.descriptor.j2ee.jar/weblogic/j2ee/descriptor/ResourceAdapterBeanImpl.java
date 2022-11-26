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

public class ResourceAdapterBeanImpl extends AbstractDescriptorBean implements ResourceAdapterBean, Serializable {
   private AdminObjectBean[] _AdminObjects;
   private ConfigPropertyBean[] _ConfigProperties;
   private String _Id;
   private InboundResourceAdapterBean _InboundResourceAdapter;
   private OutboundResourceAdapterBean _OutboundResourceAdapter;
   private String _ResourceAdapterClass;
   private SecurityPermissionBean[] _SecurityPermissions;
   private static SchemaHelper2 _schemaHelper;

   public ResourceAdapterBeanImpl() {
      this._initializeProperty(-1);
   }

   public ResourceAdapterBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ResourceAdapterBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getResourceAdapterClass() {
      return this._ResourceAdapterClass;
   }

   public boolean isResourceAdapterClassInherited() {
      return false;
   }

   public boolean isResourceAdapterClassSet() {
      return this._isSet(0);
   }

   public void setResourceAdapterClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceAdapterClass;
      this._ResourceAdapterClass = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addConfigProperty(ConfigPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         ConfigPropertyBean[] _new;
         if (this._isSet(1)) {
            _new = (ConfigPropertyBean[])((ConfigPropertyBean[])this._getHelper()._extendArray(this.getConfigProperties(), ConfigPropertyBean.class, param0));
         } else {
            _new = new ConfigPropertyBean[]{param0};
         }

         try {
            this.setConfigProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConfigPropertyBean[] getConfigProperties() {
      return this._ConfigProperties;
   }

   public boolean isConfigPropertiesInherited() {
      return false;
   }

   public boolean isConfigPropertiesSet() {
      return this._isSet(1);
   }

   public void removeConfigProperty(ConfigPropertyBean param0) {
      this.destroyConfigProperty(param0);
   }

   public void setConfigProperties(ConfigPropertyBean[] param0) throws InvalidAttributeValueException {
      ConfigPropertyBean[] param0 = param0 == null ? new ConfigPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConfigPropertyBean[] _oldVal = this._ConfigProperties;
      this._ConfigProperties = (ConfigPropertyBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public ConfigPropertyBean createConfigProperty() {
      ConfigPropertyBeanImpl _val = new ConfigPropertyBeanImpl(this, -1);

      try {
         this.addConfigProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConfigProperty(ConfigPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         ConfigPropertyBean[] _old = this.getConfigProperties();
         ConfigPropertyBean[] _new = (ConfigPropertyBean[])((ConfigPropertyBean[])this._getHelper()._removeElement(_old, ConfigPropertyBean.class, param0));
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
               this.setConfigProperties(_new);
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

   public OutboundResourceAdapterBean getOutboundResourceAdapter() {
      return this._OutboundResourceAdapter;
   }

   public boolean isOutboundResourceAdapterInherited() {
      return false;
   }

   public boolean isOutboundResourceAdapterSet() {
      return this._isSet(2);
   }

   public void setOutboundResourceAdapter(OutboundResourceAdapterBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getOutboundResourceAdapter() != null && param0 != this.getOutboundResourceAdapter()) {
         throw new BeanAlreadyExistsException(this.getOutboundResourceAdapter() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         OutboundResourceAdapterBean _oldVal = this._OutboundResourceAdapter;
         this._OutboundResourceAdapter = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public OutboundResourceAdapterBean createOutboundResourceAdapter() {
      OutboundResourceAdapterBeanImpl _val = new OutboundResourceAdapterBeanImpl(this, -1);

      try {
         this.setOutboundResourceAdapter(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOutboundResourceAdapter(OutboundResourceAdapterBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._OutboundResourceAdapter;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setOutboundResourceAdapter((OutboundResourceAdapterBean)null);
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

   public InboundResourceAdapterBean getInboundResourceAdapter() {
      return this._InboundResourceAdapter;
   }

   public boolean isInboundResourceAdapterInherited() {
      return false;
   }

   public boolean isInboundResourceAdapterSet() {
      return this._isSet(3);
   }

   public void setInboundResourceAdapter(InboundResourceAdapterBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getInboundResourceAdapter() != null && param0 != this.getInboundResourceAdapter()) {
         throw new BeanAlreadyExistsException(this.getInboundResourceAdapter() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         InboundResourceAdapterBean _oldVal = this._InboundResourceAdapter;
         this._InboundResourceAdapter = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public InboundResourceAdapterBean createInboundResourceAdapter() {
      InboundResourceAdapterBeanImpl _val = new InboundResourceAdapterBeanImpl(this, -1);

      try {
         this.setInboundResourceAdapter(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInboundResourceAdapter(InboundResourceAdapterBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._InboundResourceAdapter;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setInboundResourceAdapter((InboundResourceAdapterBean)null);
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

   public void addAdminObject(AdminObjectBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         AdminObjectBean[] _new;
         if (this._isSet(4)) {
            _new = (AdminObjectBean[])((AdminObjectBean[])this._getHelper()._extendArray(this.getAdminObjects(), AdminObjectBean.class, param0));
         } else {
            _new = new AdminObjectBean[]{param0};
         }

         try {
            this.setAdminObjects(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AdminObjectBean[] getAdminObjects() {
      return this._AdminObjects;
   }

   public boolean isAdminObjectsInherited() {
      return false;
   }

   public boolean isAdminObjectsSet() {
      return this._isSet(4);
   }

   public void removeAdminObject(AdminObjectBean param0) {
      this.destroyAdminObject(param0);
   }

   public void setAdminObjects(AdminObjectBean[] param0) throws InvalidAttributeValueException {
      AdminObjectBean[] param0 = param0 == null ? new AdminObjectBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AdminObjectBean[] _oldVal = this._AdminObjects;
      this._AdminObjects = (AdminObjectBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public AdminObjectBean createAdminObject() {
      AdminObjectBeanImpl _val = new AdminObjectBeanImpl(this, -1);

      try {
         this.addAdminObject(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAdminObject(AdminObjectBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         AdminObjectBean[] _old = this.getAdminObjects();
         AdminObjectBean[] _new = (AdminObjectBean[])((AdminObjectBean[])this._getHelper()._removeElement(_old, AdminObjectBean.class, param0));
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
               this.setAdminObjects(_new);
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

   public void addSecurityPermission(SecurityPermissionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         SecurityPermissionBean[] _new;
         if (this._isSet(5)) {
            _new = (SecurityPermissionBean[])((SecurityPermissionBean[])this._getHelper()._extendArray(this.getSecurityPermissions(), SecurityPermissionBean.class, param0));
         } else {
            _new = new SecurityPermissionBean[]{param0};
         }

         try {
            this.setSecurityPermissions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SecurityPermissionBean[] getSecurityPermissions() {
      return this._SecurityPermissions;
   }

   public boolean isSecurityPermissionsInherited() {
      return false;
   }

   public boolean isSecurityPermissionsSet() {
      return this._isSet(5);
   }

   public void removeSecurityPermission(SecurityPermissionBean param0) {
      this.destroySecurityPermission(param0);
   }

   public void setSecurityPermissions(SecurityPermissionBean[] param0) throws InvalidAttributeValueException {
      SecurityPermissionBean[] param0 = param0 == null ? new SecurityPermissionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityPermissionBean[] _oldVal = this._SecurityPermissions;
      this._SecurityPermissions = (SecurityPermissionBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public SecurityPermissionBean createSecurityPermission() {
      SecurityPermissionBeanImpl _val = new SecurityPermissionBeanImpl(this, -1);

      try {
         this.addSecurityPermission(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityPermission(SecurityPermissionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         SecurityPermissionBean[] _old = this.getSecurityPermissions();
         SecurityPermissionBean[] _new = (SecurityPermissionBean[])((SecurityPermissionBean[])this._getHelper()._removeElement(_old, SecurityPermissionBean.class, param0));
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
               this.setSecurityPermissions(_new);
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
      return this._isSet(6);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(6, _oldVal, param0);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._AdminObjects = new AdminObjectBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._ConfigProperties = new ConfigPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._InboundResourceAdapter = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._OutboundResourceAdapter = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ResourceAdapterClass = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._SecurityPermissions = new SecurityPermissionBean[0];
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
                  return 6;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 20:
            case 22:
            default:
               break;
            case 11:
               if (s.equals("adminobject")) {
                  return 4;
               }
               break;
            case 15:
               if (s.equals("config-property")) {
                  return 1;
               }
               break;
            case 19:
               if (s.equals("security-permission")) {
                  return 5;
               }
               break;
            case 21:
               if (s.equals("resourceadapter-class")) {
                  return 0;
               }
               break;
            case 23:
               if (s.equals("inbound-resourceadapter")) {
                  return 3;
               }
               break;
            case 24:
               if (s.equals("outbound-resourceadapter")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ConfigPropertyBeanImpl.SchemaHelper2();
            case 2:
               return new OutboundResourceAdapterBeanImpl.SchemaHelper2();
            case 3:
               return new InboundResourceAdapterBeanImpl.SchemaHelper2();
            case 4:
               return new AdminObjectBeanImpl.SchemaHelper2();
            case 5:
               return new SecurityPermissionBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "resourceadapter-class";
            case 1:
               return "config-property";
            case 2:
               return "outbound-resourceadapter";
            case 3:
               return "inbound-resourceadapter";
            case 4:
               return "adminobject";
            case 5:
               return "security-permission";
            case 6:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
            case 3:
            default:
               return super.isArray(propIndex);
            case 4:
               return true;
            case 5:
               return true;
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
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ResourceAdapterBeanImpl bean;

      protected Helper(ResourceAdapterBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ResourceAdapterClass";
            case 1:
               return "ConfigProperties";
            case 2:
               return "OutboundResourceAdapter";
            case 3:
               return "InboundResourceAdapter";
            case 4:
               return "AdminObjects";
            case 5:
               return "SecurityPermissions";
            case 6:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdminObjects")) {
            return 4;
         } else if (propName.equals("ConfigProperties")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 6;
         } else if (propName.equals("InboundResourceAdapter")) {
            return 3;
         } else if (propName.equals("OutboundResourceAdapter")) {
            return 2;
         } else if (propName.equals("ResourceAdapterClass")) {
            return 0;
         } else {
            return propName.equals("SecurityPermissions") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAdminObjects()));
         iterators.add(new ArrayIterator(this.bean.getConfigProperties()));
         if (this.bean.getInboundResourceAdapter() != null) {
            iterators.add(new ArrayIterator(new InboundResourceAdapterBean[]{this.bean.getInboundResourceAdapter()}));
         }

         if (this.bean.getOutboundResourceAdapter() != null) {
            iterators.add(new ArrayIterator(new OutboundResourceAdapterBean[]{this.bean.getOutboundResourceAdapter()}));
         }

         iterators.add(new ArrayIterator(this.bean.getSecurityPermissions()));
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
            for(i = 0; i < this.bean.getAdminObjects().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAdminObjects()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getConfigProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConfigProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getInboundResourceAdapter());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getOutboundResourceAdapter());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isResourceAdapterClassSet()) {
               buf.append("ResourceAdapterClass");
               buf.append(String.valueOf(this.bean.getResourceAdapterClass()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSecurityPermissions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityPermissions()[i]);
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
            ResourceAdapterBeanImpl otherTyped = (ResourceAdapterBeanImpl)other;
            this.computeChildDiff("AdminObjects", this.bean.getAdminObjects(), otherTyped.getAdminObjects(), false);
            this.computeChildDiff("ConfigProperties", this.bean.getConfigProperties(), otherTyped.getConfigProperties(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("InboundResourceAdapter", this.bean.getInboundResourceAdapter(), otherTyped.getInboundResourceAdapter(), false);
            this.computeChildDiff("OutboundResourceAdapter", this.bean.getOutboundResourceAdapter(), otherTyped.getOutboundResourceAdapter(), false);
            this.computeDiff("ResourceAdapterClass", this.bean.getResourceAdapterClass(), otherTyped.getResourceAdapterClass(), false);
            this.computeChildDiff("SecurityPermissions", this.bean.getSecurityPermissions(), otherTyped.getSecurityPermissions(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ResourceAdapterBeanImpl original = (ResourceAdapterBeanImpl)event.getSourceBean();
            ResourceAdapterBeanImpl proposed = (ResourceAdapterBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdminObjects")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAdminObject((AdminObjectBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAdminObject((AdminObjectBean)update.getRemovedObject());
                  }

                  if (original.getAdminObjects() == null || original.getAdminObjects().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("ConfigProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConfigProperty((ConfigPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConfigProperty((ConfigPropertyBean)update.getRemovedObject());
                  }

                  if (original.getConfigProperties() == null || original.getConfigProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("InboundResourceAdapter")) {
                  if (type == 2) {
                     original.setInboundResourceAdapter((InboundResourceAdapterBean)this.createCopy((AbstractDescriptorBean)proposed.getInboundResourceAdapter()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("InboundResourceAdapter", (DescriptorBean)original.getInboundResourceAdapter());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("OutboundResourceAdapter")) {
                  if (type == 2) {
                     original.setOutboundResourceAdapter((OutboundResourceAdapterBean)this.createCopy((AbstractDescriptorBean)proposed.getOutboundResourceAdapter()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("OutboundResourceAdapter", (DescriptorBean)original.getOutboundResourceAdapter());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ResourceAdapterClass")) {
                  original.setResourceAdapterClass(proposed.getResourceAdapterClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SecurityPermissions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityPermission((SecurityPermissionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityPermission((SecurityPermissionBean)update.getRemovedObject());
                  }

                  if (original.getSecurityPermissions() == null || original.getSecurityPermissions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            ResourceAdapterBeanImpl copy = (ResourceAdapterBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AdminObjects")) && this.bean.isAdminObjectsSet() && !copy._isSet(4)) {
               AdminObjectBean[] oldAdminObjects = this.bean.getAdminObjects();
               AdminObjectBean[] newAdminObjects = new AdminObjectBean[oldAdminObjects.length];

               for(i = 0; i < newAdminObjects.length; ++i) {
                  newAdminObjects[i] = (AdminObjectBean)((AdminObjectBean)this.createCopy((AbstractDescriptorBean)oldAdminObjects[i], includeObsolete));
               }

               copy.setAdminObjects(newAdminObjects);
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigProperties")) && this.bean.isConfigPropertiesSet() && !copy._isSet(1)) {
               ConfigPropertyBean[] oldConfigProperties = this.bean.getConfigProperties();
               ConfigPropertyBean[] newConfigProperties = new ConfigPropertyBean[oldConfigProperties.length];

               for(i = 0; i < newConfigProperties.length; ++i) {
                  newConfigProperties[i] = (ConfigPropertyBean)((ConfigPropertyBean)this.createCopy((AbstractDescriptorBean)oldConfigProperties[i], includeObsolete));
               }

               copy.setConfigProperties(newConfigProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InboundResourceAdapter")) && this.bean.isInboundResourceAdapterSet() && !copy._isSet(3)) {
               Object o = this.bean.getInboundResourceAdapter();
               copy.setInboundResourceAdapter((InboundResourceAdapterBean)null);
               copy.setInboundResourceAdapter(o == null ? null : (InboundResourceAdapterBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundResourceAdapter")) && this.bean.isOutboundResourceAdapterSet() && !copy._isSet(2)) {
               Object o = this.bean.getOutboundResourceAdapter();
               copy.setOutboundResourceAdapter((OutboundResourceAdapterBean)null);
               copy.setOutboundResourceAdapter(o == null ? null : (OutboundResourceAdapterBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceAdapterClass")) && this.bean.isResourceAdapterClassSet()) {
               copy.setResourceAdapterClass(this.bean.getResourceAdapterClass());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityPermissions")) && this.bean.isSecurityPermissionsSet() && !copy._isSet(5)) {
               SecurityPermissionBean[] oldSecurityPermissions = this.bean.getSecurityPermissions();
               SecurityPermissionBean[] newSecurityPermissions = new SecurityPermissionBean[oldSecurityPermissions.length];

               for(i = 0; i < newSecurityPermissions.length; ++i) {
                  newSecurityPermissions[i] = (SecurityPermissionBean)((SecurityPermissionBean)this.createCopy((AbstractDescriptorBean)oldSecurityPermissions[i], includeObsolete));
               }

               copy.setSecurityPermissions(newSecurityPermissions);
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
         this.inferSubTree(this.bean.getAdminObjects(), clazz, annotation);
         this.inferSubTree(this.bean.getConfigProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getInboundResourceAdapter(), clazz, annotation);
         this.inferSubTree(this.bean.getOutboundResourceAdapter(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityPermissions(), clazz, annotation);
      }
   }
}
