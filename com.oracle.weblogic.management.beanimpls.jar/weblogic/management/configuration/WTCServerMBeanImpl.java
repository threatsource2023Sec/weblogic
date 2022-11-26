package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.WTCServer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WTCServerMBeanImpl extends DeploymentMBeanImpl implements WTCServerMBean, Serializable {
   private boolean _DynamicallyCreated;
   private WTCExportMBean[] _Exports;
   private WTCImportMBean[] _Imports;
   private WTCLocalTuxDomMBean[] _LocalTuxDoms;
   private String _Name;
   private WTCPasswordMBean[] _Passwords;
   private WTCRemoteTuxDomMBean[] _RemoteTuxDoms;
   private WTCResourcesMBean _Resource;
   private WTCResourcesMBean _Resources;
   private String[] _Tags;
   private WTCExportMBean[] _WTCExports;
   private WTCImportMBean[] _WTCImports;
   private WTCLocalTuxDomMBean[] _WTCLocalTuxDoms;
   private WTCPasswordMBean[] _WTCPasswords;
   private WTCRemoteTuxDomMBean[] _WTCRemoteTuxDoms;
   private WTCResourcesMBean _WTCResources;
   private WTCtBridgeGlobalMBean _WTCtBridgeGlobal;
   private WTCtBridgeRedirectMBean[] _WTCtBridgeRedirects;
   private transient WTCServer _customizer;
   private WTCtBridgeGlobalMBean _tBridgeGlobal;
   private WTCtBridgeRedirectMBean[] _tBridgeRedirects;
   private static SchemaHelper2 _schemaHelper;

   public WTCServerMBeanImpl() {
      try {
         this._customizer = new WTCServer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WTCServerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new WTCServer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WTCServerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new WTCServer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public void addWTCLocalTuxDom(WTCLocalTuxDomMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         WTCLocalTuxDomMBean[] _new;
         if (this._isSet(12)) {
            _new = (WTCLocalTuxDomMBean[])((WTCLocalTuxDomMBean[])this._getHelper()._extendArray(this.getWTCLocalTuxDoms(), WTCLocalTuxDomMBean.class, param0));
         } else {
            _new = new WTCLocalTuxDomMBean[]{param0};
         }

         try {
            this.setWTCLocalTuxDoms(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public WTCLocalTuxDomMBean[] getWTCLocalTuxDoms() {
      return this._WTCLocalTuxDoms;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isWTCLocalTuxDomsInherited() {
      return false;
   }

   public boolean isWTCLocalTuxDomsSet() {
      return this._isSet(12);
   }

   public void removeWTCLocalTuxDom(WTCLocalTuxDomMBean param0) {
      this.destroyWTCLocalTuxDom(param0);
   }

   public void setWTCLocalTuxDoms(WTCLocalTuxDomMBean[] param0) throws InvalidAttributeValueException {
      WTCLocalTuxDomMBean[] param0 = param0 == null ? new WTCLocalTuxDomMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WTCLocalTuxDomMBean[] _oldVal = this._WTCLocalTuxDoms;
      this._WTCLocalTuxDoms = (WTCLocalTuxDomMBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public WTCLocalTuxDomMBean createWTCLocalTuxDom(String param0) {
      WTCLocalTuxDomMBeanImpl lookup = (WTCLocalTuxDomMBeanImpl)this.lookupWTCLocalTuxDom(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WTCLocalTuxDomMBeanImpl _val = new WTCLocalTuxDomMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWTCLocalTuxDom(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void destroyWTCLocalTuxDom(WTCLocalTuxDomMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         WTCLocalTuxDomMBean[] _old = this.getWTCLocalTuxDoms();
         WTCLocalTuxDomMBean[] _new = (WTCLocalTuxDomMBean[])((WTCLocalTuxDomMBean[])this._getHelper()._removeElement(_old, WTCLocalTuxDomMBean.class, param0));
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
               this.setWTCLocalTuxDoms(_new);
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

   public WTCLocalTuxDomMBean lookupWTCLocalTuxDom(String param0) {
      Object[] aary = (Object[])this._WTCLocalTuxDoms;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WTCLocalTuxDomMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WTCLocalTuxDomMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addLocalTuxDom(WTCLocalTuxDomMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         WTCLocalTuxDomMBean[] _new = (WTCLocalTuxDomMBean[])((WTCLocalTuxDomMBean[])this._getHelper()._extendArray(this.getLocalTuxDoms(), WTCLocalTuxDomMBean.class, param0));

         try {
            this.setLocalTuxDoms(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCLocalTuxDomMBean[] getLocalTuxDoms() {
      return this._customizer.getLocalTuxDoms();
   }

   public boolean isLocalTuxDomsInherited() {
      return false;
   }

   public boolean isLocalTuxDomsSet() {
      return this._isSet(13);
   }

   public void removeLocalTuxDom(WTCLocalTuxDomMBean param0) {
      WTCLocalTuxDomMBean[] _old = this.getLocalTuxDoms();
      WTCLocalTuxDomMBean[] _new = (WTCLocalTuxDomMBean[])((WTCLocalTuxDomMBean[])this._getHelper()._removeElement(_old, WTCLocalTuxDomMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setLocalTuxDoms(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setLocalTuxDoms(WTCLocalTuxDomMBean[] param0) throws InvalidAttributeValueException {
      WTCLocalTuxDomMBean[] param0 = param0 == null ? new WTCLocalTuxDomMBeanImpl[0] : param0;
      this._LocalTuxDoms = (WTCLocalTuxDomMBean[])param0;
   }

   public void addWTCRemoteTuxDom(WTCRemoteTuxDomMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         WTCRemoteTuxDomMBean[] _new;
         if (this._isSet(14)) {
            _new = (WTCRemoteTuxDomMBean[])((WTCRemoteTuxDomMBean[])this._getHelper()._extendArray(this.getWTCRemoteTuxDoms(), WTCRemoteTuxDomMBean.class, param0));
         } else {
            _new = new WTCRemoteTuxDomMBean[]{param0};
         }

         try {
            this.setWTCRemoteTuxDoms(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCRemoteTuxDomMBean[] getWTCRemoteTuxDoms() {
      return this._WTCRemoteTuxDoms;
   }

   public boolean isWTCRemoteTuxDomsInherited() {
      return false;
   }

   public boolean isWTCRemoteTuxDomsSet() {
      return this._isSet(14);
   }

   public void removeWTCRemoteTuxDom(WTCRemoteTuxDomMBean param0) {
      this.destroyWTCRemoteTuxDom(param0);
   }

   public void setWTCRemoteTuxDoms(WTCRemoteTuxDomMBean[] param0) throws InvalidAttributeValueException {
      WTCRemoteTuxDomMBean[] param0 = param0 == null ? new WTCRemoteTuxDomMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WTCRemoteTuxDomMBean[] _oldVal = this._WTCRemoteTuxDoms;
      this._WTCRemoteTuxDoms = (WTCRemoteTuxDomMBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public WTCRemoteTuxDomMBean createWTCRemoteTuxDom(String param0) {
      WTCRemoteTuxDomMBeanImpl lookup = (WTCRemoteTuxDomMBeanImpl)this.lookupWTCRemoteTuxDom(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WTCRemoteTuxDomMBeanImpl _val = new WTCRemoteTuxDomMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWTCRemoteTuxDom(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWTCRemoteTuxDom(WTCRemoteTuxDomMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         WTCRemoteTuxDomMBean[] _old = this.getWTCRemoteTuxDoms();
         WTCRemoteTuxDomMBean[] _new = (WTCRemoteTuxDomMBean[])((WTCRemoteTuxDomMBean[])this._getHelper()._removeElement(_old, WTCRemoteTuxDomMBean.class, param0));
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
               this.setWTCRemoteTuxDoms(_new);
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

   public WTCRemoteTuxDomMBean lookupWTCRemoteTuxDom(String param0) {
      Object[] aary = (Object[])this._WTCRemoteTuxDoms;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WTCRemoteTuxDomMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WTCRemoteTuxDomMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addRemoteTuxDom(WTCRemoteTuxDomMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         WTCRemoteTuxDomMBean[] _new = (WTCRemoteTuxDomMBean[])((WTCRemoteTuxDomMBean[])this._getHelper()._extendArray(this.getRemoteTuxDoms(), WTCRemoteTuxDomMBean.class, param0));

         try {
            this.setRemoteTuxDoms(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCRemoteTuxDomMBean[] getRemoteTuxDoms() {
      return this._customizer.getRemoteTuxDoms();
   }

   public boolean isRemoteTuxDomsInherited() {
      return false;
   }

   public boolean isRemoteTuxDomsSet() {
      return this._isSet(15);
   }

   public void removeRemoteTuxDom(WTCRemoteTuxDomMBean param0) {
      WTCRemoteTuxDomMBean[] _old = this.getRemoteTuxDoms();
      WTCRemoteTuxDomMBean[] _new = (WTCRemoteTuxDomMBean[])((WTCRemoteTuxDomMBean[])this._getHelper()._removeElement(_old, WTCRemoteTuxDomMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setRemoteTuxDoms(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setRemoteTuxDoms(WTCRemoteTuxDomMBean[] param0) throws InvalidAttributeValueException {
      WTCRemoteTuxDomMBean[] param0 = param0 == null ? new WTCRemoteTuxDomMBeanImpl[0] : param0;
      this._RemoteTuxDoms = (WTCRemoteTuxDomMBean[])param0;
   }

   public void addWTCExport(WTCExportMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         WTCExportMBean[] _new;
         if (this._isSet(16)) {
            _new = (WTCExportMBean[])((WTCExportMBean[])this._getHelper()._extendArray(this.getWTCExports(), WTCExportMBean.class, param0));
         } else {
            _new = new WTCExportMBean[]{param0};
         }

         try {
            this.setWTCExports(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCExportMBean[] getWTCExports() {
      return this._WTCExports;
   }

   public boolean isWTCExportsInherited() {
      return false;
   }

   public boolean isWTCExportsSet() {
      return this._isSet(16);
   }

   public void removeWTCExport(WTCExportMBean param0) {
      this.destroyWTCExport(param0);
   }

   public void setWTCExports(WTCExportMBean[] param0) throws InvalidAttributeValueException {
      WTCExportMBean[] param0 = param0 == null ? new WTCExportMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WTCExportMBean[] _oldVal = this._WTCExports;
      this._WTCExports = (WTCExportMBean[])param0;
      this._postSet(16, _oldVal, param0);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public WTCExportMBean createWTCExport(String param0) {
      WTCExportMBeanImpl lookup = (WTCExportMBeanImpl)this.lookupWTCExport(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WTCExportMBeanImpl _val = new WTCExportMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWTCExport(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void destroyWTCExport(WTCExportMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 16);
         WTCExportMBean[] _old = this.getWTCExports();
         WTCExportMBean[] _new = (WTCExportMBean[])((WTCExportMBean[])this._getHelper()._removeElement(_old, WTCExportMBean.class, param0));
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
               this.setWTCExports(_new);
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

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public WTCExportMBean lookupWTCExport(String param0) {
      Object[] aary = (Object[])this._WTCExports;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WTCExportMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WTCExportMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addExport(WTCExportMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         WTCExportMBean[] _new = (WTCExportMBean[])((WTCExportMBean[])this._getHelper()._extendArray(this.getExports(), WTCExportMBean.class, param0));

         try {
            this.setExports(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCExportMBean[] getExports() {
      return this._customizer.getExports();
   }

   public boolean isExportsInherited() {
      return false;
   }

   public boolean isExportsSet() {
      return this._isSet(17);
   }

   public void removeExport(WTCExportMBean param0) {
      WTCExportMBean[] _old = this.getExports();
      WTCExportMBean[] _new = (WTCExportMBean[])((WTCExportMBean[])this._getHelper()._removeElement(_old, WTCExportMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setExports(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setExports(WTCExportMBean[] param0) throws InvalidAttributeValueException {
      WTCExportMBean[] param0 = param0 == null ? new WTCExportMBeanImpl[0] : param0;
      this._Exports = (WTCExportMBean[])param0;
   }

   public void addWTCImport(WTCImportMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         WTCImportMBean[] _new;
         if (this._isSet(18)) {
            _new = (WTCImportMBean[])((WTCImportMBean[])this._getHelper()._extendArray(this.getWTCImports(), WTCImportMBean.class, param0));
         } else {
            _new = new WTCImportMBean[]{param0};
         }

         try {
            this.setWTCImports(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCImportMBean[] getWTCImports() {
      return this._WTCImports;
   }

   public boolean isWTCImportsInherited() {
      return false;
   }

   public boolean isWTCImportsSet() {
      return this._isSet(18);
   }

   public void removeWTCImport(WTCImportMBean param0) {
      this.destroyWTCImport(param0);
   }

   public void setWTCImports(WTCImportMBean[] param0) throws InvalidAttributeValueException {
      WTCImportMBean[] param0 = param0 == null ? new WTCImportMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WTCImportMBean[] _oldVal = this._WTCImports;
      this._WTCImports = (WTCImportMBean[])param0;
      this._postSet(18, _oldVal, param0);
   }

   public WTCImportMBean createWTCImport(String param0) {
      WTCImportMBeanImpl lookup = (WTCImportMBeanImpl)this.lookupWTCImport(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WTCImportMBeanImpl _val = new WTCImportMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWTCImport(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWTCImport(WTCImportMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 18);
         WTCImportMBean[] _old = this.getWTCImports();
         WTCImportMBean[] _new = (WTCImportMBean[])((WTCImportMBean[])this._getHelper()._removeElement(_old, WTCImportMBean.class, param0));
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
               this.setWTCImports(_new);
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

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public WTCImportMBean lookupWTCImport(String param0) {
      Object[] aary = (Object[])this._WTCImports;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WTCImportMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WTCImportMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addImport(WTCImportMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         WTCImportMBean[] _new = (WTCImportMBean[])((WTCImportMBean[])this._getHelper()._extendArray(this.getImports(), WTCImportMBean.class, param0));

         try {
            this.setImports(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCImportMBean[] getImports() {
      return this._customizer.getImports();
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isImportsInherited() {
      return false;
   }

   public boolean isImportsSet() {
      return this._isSet(19);
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void removeImport(WTCImportMBean param0) {
      WTCImportMBean[] _old = this.getImports();
      WTCImportMBean[] _new = (WTCImportMBean[])((WTCImportMBean[])this._getHelper()._removeElement(_old, WTCImportMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setImports(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setImports(WTCImportMBean[] param0) throws InvalidAttributeValueException {
      WTCImportMBean[] param0 = param0 == null ? new WTCImportMBeanImpl[0] : param0;
      this._Imports = (WTCImportMBean[])param0;
   }

   public void addWTCPassword(WTCPasswordMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         WTCPasswordMBean[] _new;
         if (this._isSet(20)) {
            _new = (WTCPasswordMBean[])((WTCPasswordMBean[])this._getHelper()._extendArray(this.getWTCPasswords(), WTCPasswordMBean.class, param0));
         } else {
            _new = new WTCPasswordMBean[]{param0};
         }

         try {
            this.setWTCPasswords(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCPasswordMBean[] getWTCPasswords() {
      return this._WTCPasswords;
   }

   public boolean isWTCPasswordsInherited() {
      return false;
   }

   public boolean isWTCPasswordsSet() {
      return this._isSet(20);
   }

   public void removeWTCPassword(WTCPasswordMBean param0) {
      this.destroyWTCPassword(param0);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
   }

   public void setWTCPasswords(WTCPasswordMBean[] param0) throws InvalidAttributeValueException {
      WTCPasswordMBean[] param0 = param0 == null ? new WTCPasswordMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 20)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WTCPasswordMBean[] _oldVal = this._WTCPasswords;
      this._WTCPasswords = (WTCPasswordMBean[])param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public WTCPasswordMBean createWTCPassword(String param0) {
      WTCPasswordMBeanImpl lookup = (WTCPasswordMBeanImpl)this.lookupWTCPassword(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WTCPasswordMBeanImpl _val = new WTCPasswordMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWTCPassword(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWTCPassword(WTCPasswordMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 20);
         WTCPasswordMBean[] _old = this.getWTCPasswords();
         WTCPasswordMBean[] _new = (WTCPasswordMBean[])((WTCPasswordMBean[])this._getHelper()._removeElement(_old, WTCPasswordMBean.class, param0));
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
               this.setWTCPasswords(_new);
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

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public WTCPasswordMBean lookupWTCPassword(String param0) {
      Object[] aary = (Object[])this._WTCPasswords;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WTCPasswordMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WTCPasswordMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addPassword(WTCPasswordMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         WTCPasswordMBean[] _new = (WTCPasswordMBean[])((WTCPasswordMBean[])this._getHelper()._extendArray(this.getPasswords(), WTCPasswordMBean.class, param0));

         try {
            this.setPasswords(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCPasswordMBean[] getPasswords() {
      return this._customizer.getPasswords();
   }

   public boolean isPasswordsInherited() {
      return false;
   }

   public boolean isPasswordsSet() {
      return this._isSet(21);
   }

   public void removePassword(WTCPasswordMBean param0) {
      WTCPasswordMBean[] _old = this.getPasswords();
      WTCPasswordMBean[] _new = (WTCPasswordMBean[])((WTCPasswordMBean[])this._getHelper()._removeElement(_old, WTCPasswordMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setPasswords(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setPasswords(WTCPasswordMBean[] param0) throws InvalidAttributeValueException {
      WTCPasswordMBean[] param0 = param0 == null ? new WTCPasswordMBeanImpl[0] : param0;
      this._Passwords = (WTCPasswordMBean[])param0;
   }

   public WTCResourcesMBean getWTCResources() {
      return this._WTCResources;
   }

   public boolean isWTCResourcesInherited() {
      return false;
   }

   public boolean isWTCResourcesSet() {
      return this._isSet(22);
   }

   public void setWTCResources(WTCResourcesMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWTCResources() != null && param0 != this.getWTCResources()) {
         throw new BeanAlreadyExistsException(this.getWTCResources() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 22)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WTCResourcesMBean _oldVal = this._WTCResources;
         this._WTCResources = param0;
         this._postSet(22, _oldVal, param0);
      }
   }

   public WTCResourcesMBean getResources() {
      return this._customizer.getResources();
   }

   public boolean isResourcesInherited() {
      return false;
   }

   public boolean isResourcesSet() {
      return this._isSet(23);
   }

   public void setResources(WTCResourcesMBean param0) throws InvalidAttributeValueException {
      this._Resources = param0;
   }

   public WTCResourcesMBean createWTCResources(String param0) throws InstanceAlreadyExistsException {
      WTCResourcesMBeanImpl _val = new WTCResourcesMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setWTCResources(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyWTCResources(WTCResourcesMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WTCResources;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWTCResources((WTCResourcesMBean)null);
               this._unSet(22);
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

   public WTCtBridgeGlobalMBean getWTCtBridgeGlobal() {
      return this._WTCtBridgeGlobal;
   }

   public boolean isWTCtBridgeGlobalInherited() {
      return false;
   }

   public boolean isWTCtBridgeGlobalSet() {
      return this._isSet(24);
   }

   public void setWTCtBridgeGlobal(WTCtBridgeGlobalMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWTCtBridgeGlobal() != null && param0 != this.getWTCtBridgeGlobal()) {
         throw new BeanAlreadyExistsException(this.getWTCtBridgeGlobal() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 24)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WTCtBridgeGlobalMBean _oldVal = this._WTCtBridgeGlobal;
         this._WTCtBridgeGlobal = param0;
         this._postSet(24, _oldVal, param0);
      }
   }

   public WTCtBridgeGlobalMBean createWTCtBridgeGlobal() throws InstanceAlreadyExistsException {
      WTCtBridgeGlobalMBeanImpl _val = new WTCtBridgeGlobalMBeanImpl(this, -1);

      try {
         this.setWTCtBridgeGlobal(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else if (var3 instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWTCtBridgeGlobal() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WTCtBridgeGlobal;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWTCtBridgeGlobal((WTCtBridgeGlobalMBean)null);
               this._unSet(24);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public WTCResourcesMBean getResource() {
      return this._Resource;
   }

   public boolean isResourceInherited() {
      return false;
   }

   public boolean isResourceSet() {
      return this._isSet(25);
   }

   public void setResource(WTCResourcesMBean param0) throws InvalidAttributeValueException {
      this._Resource = param0;
   }

   public WTCtBridgeGlobalMBean gettBridgeGlobal() {
      return this._tBridgeGlobal;
   }

   public boolean istBridgeGlobalInherited() {
      return false;
   }

   public boolean istBridgeGlobalSet() {
      return this._isSet(26);
   }

   public void settBridgeGlobal(WTCtBridgeGlobalMBean param0) throws InvalidAttributeValueException {
      this._tBridgeGlobal = param0;
   }

   public void addtBridgeRedirect(WTCtBridgeRedirectMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 27)) {
         WTCtBridgeRedirectMBean[] _new = (WTCtBridgeRedirectMBean[])((WTCtBridgeRedirectMBean[])this._getHelper()._extendArray(this.gettBridgeRedirects(), WTCtBridgeRedirectMBean.class, param0));

         try {
            this.settBridgeRedirects(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCtBridgeRedirectMBean[] gettBridgeRedirects() {
      return this._customizer.gettBridgeRedirects();
   }

   public boolean istBridgeRedirectsInherited() {
      return false;
   }

   public boolean istBridgeRedirectsSet() {
      return this._isSet(27);
   }

   public void removetBridgeRedirect(WTCtBridgeRedirectMBean param0) {
      WTCtBridgeRedirectMBean[] _old = this.gettBridgeRedirects();
      WTCtBridgeRedirectMBean[] _new = (WTCtBridgeRedirectMBean[])((WTCtBridgeRedirectMBean[])this._getHelper()._removeElement(_old, WTCtBridgeRedirectMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.settBridgeRedirects(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void settBridgeRedirects(WTCtBridgeRedirectMBean[] param0) throws InvalidAttributeValueException {
      WTCtBridgeRedirectMBean[] param0 = param0 == null ? new WTCtBridgeRedirectMBeanImpl[0] : param0;
      this._tBridgeRedirects = (WTCtBridgeRedirectMBean[])param0;
   }

   public void addWTCtBridgeRedirect(WTCtBridgeRedirectMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 28)) {
         WTCtBridgeRedirectMBean[] _new;
         if (this._isSet(28)) {
            _new = (WTCtBridgeRedirectMBean[])((WTCtBridgeRedirectMBean[])this._getHelper()._extendArray(this.getWTCtBridgeRedirects(), WTCtBridgeRedirectMBean.class, param0));
         } else {
            _new = new WTCtBridgeRedirectMBean[]{param0};
         }

         try {
            this.setWTCtBridgeRedirects(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WTCtBridgeRedirectMBean[] getWTCtBridgeRedirects() {
      return this._WTCtBridgeRedirects;
   }

   public boolean isWTCtBridgeRedirectsInherited() {
      return false;
   }

   public boolean isWTCtBridgeRedirectsSet() {
      return this._isSet(28);
   }

   public void removeWTCtBridgeRedirect(WTCtBridgeRedirectMBean param0) {
      this.destroyWTCtBridgeRedirect(param0);
   }

   public void setWTCtBridgeRedirects(WTCtBridgeRedirectMBean[] param0) throws InvalidAttributeValueException {
      WTCtBridgeRedirectMBean[] param0 = param0 == null ? new WTCtBridgeRedirectMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 28)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WTCtBridgeRedirectMBean[] _oldVal = this._WTCtBridgeRedirects;
      this._WTCtBridgeRedirects = (WTCtBridgeRedirectMBean[])param0;
      this._postSet(28, _oldVal, param0);
   }

   public WTCtBridgeRedirectMBean createWTCtBridgeRedirect(String param0) {
      WTCtBridgeRedirectMBeanImpl lookup = (WTCtBridgeRedirectMBeanImpl)this.lookupWTCtBridgeRedirect(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WTCtBridgeRedirectMBeanImpl _val = new WTCtBridgeRedirectMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWTCtBridgeRedirect(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWTCtBridgeRedirect(WTCtBridgeRedirectMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 28);
         WTCtBridgeRedirectMBean[] _old = this.getWTCtBridgeRedirects();
         WTCtBridgeRedirectMBean[] _new = (WTCtBridgeRedirectMBean[])((WTCtBridgeRedirectMBean[])this._getHelper()._removeElement(_old, WTCtBridgeRedirectMBean.class, param0));
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
               this.setWTCtBridgeRedirects(_new);
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

   public WTCtBridgeRedirectMBean lookupWTCtBridgeRedirect(String param0) {
      Object[] aary = (Object[])this._WTCtBridgeRedirects;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WTCtBridgeRedirectMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WTCtBridgeRedirectMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public Object _getKey() {
      return this.getName();
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
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._Exports = new WTCExportMBean[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._Imports = new WTCImportMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._LocalTuxDoms = new WTCLocalTuxDomMBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 21:
               this._Passwords = new WTCPasswordMBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._RemoteTuxDoms = new WTCRemoteTuxDomMBean[0];
               if (initOne) {
                  break;
               }
            case 25:
               this._Resource = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._Resources = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 16:
               this._WTCExports = new WTCExportMBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._WTCImports = new WTCImportMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._WTCLocalTuxDoms = new WTCLocalTuxDomMBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._WTCPasswords = new WTCPasswordMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._WTCRemoteTuxDoms = new WTCRemoteTuxDomMBean[0];
               if (initOne) {
                  break;
               }
            case 22:
               this._WTCResources = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._WTCtBridgeGlobal = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._WTCtBridgeRedirects = new WTCtBridgeRedirectMBean[0];
               if (initOne) {
                  break;
               }
            case 26:
               this._tBridgeGlobal = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._tBridgeRedirects = new WTCtBridgeRedirectMBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "WTCServer";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else {
         WTCExportMBean[] oldVal;
         if (name.equals("Exports")) {
            oldVal = this._Exports;
            this._Exports = (WTCExportMBean[])((WTCExportMBean[])v);
            this._postSet(17, oldVal, this._Exports);
         } else {
            WTCImportMBean[] oldVal;
            if (name.equals("Imports")) {
               oldVal = this._Imports;
               this._Imports = (WTCImportMBean[])((WTCImportMBean[])v);
               this._postSet(19, oldVal, this._Imports);
            } else {
               WTCLocalTuxDomMBean[] oldVal;
               if (name.equals("LocalTuxDoms")) {
                  oldVal = this._LocalTuxDoms;
                  this._LocalTuxDoms = (WTCLocalTuxDomMBean[])((WTCLocalTuxDomMBean[])v);
                  this._postSet(13, oldVal, this._LocalTuxDoms);
               } else if (name.equals("Name")) {
                  String oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else {
                  WTCPasswordMBean[] oldVal;
                  if (name.equals("Passwords")) {
                     oldVal = this._Passwords;
                     this._Passwords = (WTCPasswordMBean[])((WTCPasswordMBean[])v);
                     this._postSet(21, oldVal, this._Passwords);
                  } else {
                     WTCRemoteTuxDomMBean[] oldVal;
                     if (name.equals("RemoteTuxDoms")) {
                        oldVal = this._RemoteTuxDoms;
                        this._RemoteTuxDoms = (WTCRemoteTuxDomMBean[])((WTCRemoteTuxDomMBean[])v);
                        this._postSet(15, oldVal, this._RemoteTuxDoms);
                     } else {
                        WTCResourcesMBean oldVal;
                        if (name.equals("Resource")) {
                           oldVal = this._Resource;
                           this._Resource = (WTCResourcesMBean)v;
                           this._postSet(25, oldVal, this._Resource);
                        } else if (name.equals("Resources")) {
                           oldVal = this._Resources;
                           this._Resources = (WTCResourcesMBean)v;
                           this._postSet(23, oldVal, this._Resources);
                        } else if (name.equals("Tags")) {
                           String[] oldVal = this._Tags;
                           this._Tags = (String[])((String[])v);
                           this._postSet(9, oldVal, this._Tags);
                        } else if (name.equals("WTCExports")) {
                           oldVal = this._WTCExports;
                           this._WTCExports = (WTCExportMBean[])((WTCExportMBean[])v);
                           this._postSet(16, oldVal, this._WTCExports);
                        } else if (name.equals("WTCImports")) {
                           oldVal = this._WTCImports;
                           this._WTCImports = (WTCImportMBean[])((WTCImportMBean[])v);
                           this._postSet(18, oldVal, this._WTCImports);
                        } else if (name.equals("WTCLocalTuxDoms")) {
                           oldVal = this._WTCLocalTuxDoms;
                           this._WTCLocalTuxDoms = (WTCLocalTuxDomMBean[])((WTCLocalTuxDomMBean[])v);
                           this._postSet(12, oldVal, this._WTCLocalTuxDoms);
                        } else if (name.equals("WTCPasswords")) {
                           oldVal = this._WTCPasswords;
                           this._WTCPasswords = (WTCPasswordMBean[])((WTCPasswordMBean[])v);
                           this._postSet(20, oldVal, this._WTCPasswords);
                        } else if (name.equals("WTCRemoteTuxDoms")) {
                           oldVal = this._WTCRemoteTuxDoms;
                           this._WTCRemoteTuxDoms = (WTCRemoteTuxDomMBean[])((WTCRemoteTuxDomMBean[])v);
                           this._postSet(14, oldVal, this._WTCRemoteTuxDoms);
                        } else if (name.equals("WTCResources")) {
                           oldVal = this._WTCResources;
                           this._WTCResources = (WTCResourcesMBean)v;
                           this._postSet(22, oldVal, this._WTCResources);
                        } else {
                           WTCtBridgeGlobalMBean oldVal;
                           if (name.equals("WTCtBridgeGlobal")) {
                              oldVal = this._WTCtBridgeGlobal;
                              this._WTCtBridgeGlobal = (WTCtBridgeGlobalMBean)v;
                              this._postSet(24, oldVal, this._WTCtBridgeGlobal);
                           } else {
                              WTCtBridgeRedirectMBean[] oldVal;
                              if (name.equals("WTCtBridgeRedirects")) {
                                 oldVal = this._WTCtBridgeRedirects;
                                 this._WTCtBridgeRedirects = (WTCtBridgeRedirectMBean[])((WTCtBridgeRedirectMBean[])v);
                                 this._postSet(28, oldVal, this._WTCtBridgeRedirects);
                              } else if (name.equals("customizer")) {
                                 WTCServer oldVal = this._customizer;
                                 this._customizer = (WTCServer)v;
                              } else if (name.equals("tBridgeGlobal")) {
                                 oldVal = this._tBridgeGlobal;
                                 this._tBridgeGlobal = (WTCtBridgeGlobalMBean)v;
                                 this._postSet(26, oldVal, this._tBridgeGlobal);
                              } else if (name.equals("tBridgeRedirects")) {
                                 oldVal = this._tBridgeRedirects;
                                 this._tBridgeRedirects = (WTCtBridgeRedirectMBean[])((WTCtBridgeRedirectMBean[])v);
                                 this._postSet(27, oldVal, this._tBridgeRedirects);
                              } else {
                                 super.putValue(name, v);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Exports")) {
         return this._Exports;
      } else if (name.equals("Imports")) {
         return this._Imports;
      } else if (name.equals("LocalTuxDoms")) {
         return this._LocalTuxDoms;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Passwords")) {
         return this._Passwords;
      } else if (name.equals("RemoteTuxDoms")) {
         return this._RemoteTuxDoms;
      } else if (name.equals("Resource")) {
         return this._Resource;
      } else if (name.equals("Resources")) {
         return this._Resources;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("WTCExports")) {
         return this._WTCExports;
      } else if (name.equals("WTCImports")) {
         return this._WTCImports;
      } else if (name.equals("WTCLocalTuxDoms")) {
         return this._WTCLocalTuxDoms;
      } else if (name.equals("WTCPasswords")) {
         return this._WTCPasswords;
      } else if (name.equals("WTCRemoteTuxDoms")) {
         return this._WTCRemoteTuxDoms;
      } else if (name.equals("WTCResources")) {
         return this._WTCResources;
      } else if (name.equals("WTCtBridgeGlobal")) {
         return this._WTCtBridgeGlobal;
      } else if (name.equals("WTCtBridgeRedirects")) {
         return this._WTCtBridgeRedirects;
      } else if (name.equals("customizer")) {
         return this._customizer;
      } else if (name.equals("tBridgeGlobal")) {
         return this._tBridgeGlobal;
      } else {
         return name.equals("tBridgeRedirects") ? this._tBridgeRedirects : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 7:
            case 11:
            case 16:
            default:
               break;
            case 6:
               if (s.equals("export")) {
                  return 17;
               }

               if (s.equals("import")) {
                  return 19;
               }
               break;
            case 8:
               if (s.equals("password")) {
                  return 21;
               }

               if (s.equals("resource")) {
                  return 25;
               }
               break;
            case 9:
               if (s.equals("resources")) {
                  return 23;
               }
               break;
            case 10:
               if (s.equals("wtc-export")) {
                  return 16;
               }

               if (s.equals("wtc-import")) {
                  return 18;
               }
               break;
            case 12:
               if (s.equals("wtc-password")) {
                  return 20;
               }
               break;
            case 13:
               if (s.equals("local-tux-dom")) {
                  return 13;
               }

               if (s.equals("wtc-resources")) {
                  return 22;
               }
               break;
            case 14:
               if (s.equals("remote-tux-dom")) {
                  return 15;
               }
               break;
            case 15:
               if (s.equals("t-bridge-global")) {
                  return 26;
               }
               break;
            case 17:
               if (s.equals("wtc-local-tux-dom")) {
                  return 12;
               }

               if (s.equals("t-bridge-redirect")) {
                  return 27;
               }
               break;
            case 18:
               if (s.equals("wtc-remote-tux-dom")) {
                  return 14;
               }

               if (s.equals("wtc-tbridge-global")) {
                  return 24;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("wtc-tbridge-redirect")) {
                  return 28;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 12:
               return new WTCLocalTuxDomMBeanImpl.SchemaHelper2();
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 26:
            case 27:
            default:
               return super.getSchemaHelper(propIndex);
            case 14:
               return new WTCRemoteTuxDomMBeanImpl.SchemaHelper2();
            case 16:
               return new WTCExportMBeanImpl.SchemaHelper2();
            case 18:
               return new WTCImportMBeanImpl.SchemaHelper2();
            case 20:
               return new WTCPasswordMBeanImpl.SchemaHelper2();
            case 22:
               return new WTCResourcesMBeanImpl.SchemaHelper2();
            case 24:
               return new WTCtBridgeGlobalMBeanImpl.SchemaHelper2();
            case 28:
               return new WTCtBridgeRedirectMBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 12:
               return "wtc-local-tux-dom";
            case 13:
               return "local-tux-dom";
            case 14:
               return "wtc-remote-tux-dom";
            case 15:
               return "remote-tux-dom";
            case 16:
               return "wtc-export";
            case 17:
               return "export";
            case 18:
               return "wtc-import";
            case 19:
               return "import";
            case 20:
               return "wtc-password";
            case 21:
               return "password";
            case 22:
               return "wtc-resources";
            case 23:
               return "resources";
            case 24:
               return "wtc-tbridge-global";
            case 25:
               return "resource";
            case 26:
               return "t-bridge-global";
            case 27:
               return "t-bridge-redirect";
            case 28:
               return "wtc-tbridge-redirect";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            default:
               return super.isArray(propIndex);
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 27:
               return true;
            case 28:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 26:
            case 27:
            default:
               return super.isBean(propIndex);
            case 14:
               return true;
            case 16:
               return true;
            case 18:
               return true;
            case 20:
               return true;
            case 22:
               return true;
            case 24:
               return true;
            case 28:
               return true;
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
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
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private WTCServerMBeanImpl bean;

      protected Helper(WTCServerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 12:
               return "WTCLocalTuxDoms";
            case 13:
               return "LocalTuxDoms";
            case 14:
               return "WTCRemoteTuxDoms";
            case 15:
               return "RemoteTuxDoms";
            case 16:
               return "WTCExports";
            case 17:
               return "Exports";
            case 18:
               return "WTCImports";
            case 19:
               return "Imports";
            case 20:
               return "WTCPasswords";
            case 21:
               return "Passwords";
            case 22:
               return "WTCResources";
            case 23:
               return "Resources";
            case 24:
               return "WTCtBridgeGlobal";
            case 25:
               return "Resource";
            case 26:
               return "tBridgeGlobal";
            case 27:
               return "tBridgeRedirects";
            case 28:
               return "WTCtBridgeRedirects";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Exports")) {
            return 17;
         } else if (propName.equals("Imports")) {
            return 19;
         } else if (propName.equals("LocalTuxDoms")) {
            return 13;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Passwords")) {
            return 21;
         } else if (propName.equals("RemoteTuxDoms")) {
            return 15;
         } else if (propName.equals("Resource")) {
            return 25;
         } else if (propName.equals("Resources")) {
            return 23;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("WTCExports")) {
            return 16;
         } else if (propName.equals("WTCImports")) {
            return 18;
         } else if (propName.equals("WTCLocalTuxDoms")) {
            return 12;
         } else if (propName.equals("WTCPasswords")) {
            return 20;
         } else if (propName.equals("WTCRemoteTuxDoms")) {
            return 14;
         } else if (propName.equals("WTCResources")) {
            return 22;
         } else if (propName.equals("WTCtBridgeGlobal")) {
            return 24;
         } else if (propName.equals("WTCtBridgeRedirects")) {
            return 28;
         } else if (propName.equals("tBridgeGlobal")) {
            return 26;
         } else if (propName.equals("tBridgeRedirects")) {
            return 27;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWTCExports()));
         iterators.add(new ArrayIterator(this.bean.getWTCImports()));
         iterators.add(new ArrayIterator(this.bean.getWTCLocalTuxDoms()));
         iterators.add(new ArrayIterator(this.bean.getWTCPasswords()));
         iterators.add(new ArrayIterator(this.bean.getWTCRemoteTuxDoms()));
         if (this.bean.getWTCResources() != null) {
            iterators.add(new ArrayIterator(new WTCResourcesMBean[]{this.bean.getWTCResources()}));
         }

         if (this.bean.getWTCtBridgeGlobal() != null) {
            iterators.add(new ArrayIterator(new WTCtBridgeGlobalMBean[]{this.bean.getWTCtBridgeGlobal()}));
         }

         iterators.add(new ArrayIterator(this.bean.getWTCtBridgeRedirects()));
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
            if (this.bean.isExportsSet()) {
               buf.append("Exports");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getExports())));
            }

            if (this.bean.isImportsSet()) {
               buf.append("Imports");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getImports())));
            }

            if (this.bean.isLocalTuxDomsSet()) {
               buf.append("LocalTuxDoms");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getLocalTuxDoms())));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPasswordsSet()) {
               buf.append("Passwords");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswords())));
            }

            if (this.bean.isRemoteTuxDomsSet()) {
               buf.append("RemoteTuxDoms");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getRemoteTuxDoms())));
            }

            if (this.bean.isResourceSet()) {
               buf.append("Resource");
               buf.append(String.valueOf(this.bean.getResource()));
            }

            if (this.bean.isResourcesSet()) {
               buf.append("Resources");
               buf.append(String.valueOf(this.bean.getResources()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getWTCExports().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWTCExports()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWTCImports().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWTCImports()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWTCLocalTuxDoms().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWTCLocalTuxDoms()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWTCPasswords().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWTCPasswords()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWTCRemoteTuxDoms().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWTCRemoteTuxDoms()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWTCResources());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWTCtBridgeGlobal());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWTCtBridgeRedirects().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWTCtBridgeRedirects()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.istBridgeGlobalSet()) {
               buf.append("tBridgeGlobal");
               buf.append(String.valueOf(this.bean.gettBridgeGlobal()));
            }

            if (this.bean.istBridgeRedirectsSet()) {
               buf.append("tBridgeRedirects");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.gettBridgeRedirects())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
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
            WTCServerMBeanImpl otherTyped = (WTCServerMBeanImpl)other;
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeChildDiff("WTCExports", this.bean.getWTCExports(), otherTyped.getWTCExports(), true);
            this.computeChildDiff("WTCImports", this.bean.getWTCImports(), otherTyped.getWTCImports(), true);
            this.computeChildDiff("WTCLocalTuxDoms", this.bean.getWTCLocalTuxDoms(), otherTyped.getWTCLocalTuxDoms(), false);
            this.computeChildDiff("WTCPasswords", this.bean.getWTCPasswords(), otherTyped.getWTCPasswords(), false);
            this.computeChildDiff("WTCRemoteTuxDoms", this.bean.getWTCRemoteTuxDoms(), otherTyped.getWTCRemoteTuxDoms(), false);
            this.computeChildDiff("WTCResources", this.bean.getWTCResources(), otherTyped.getWTCResources(), false);
            this.computeChildDiff("WTCtBridgeGlobal", this.bean.getWTCtBridgeGlobal(), otherTyped.getWTCtBridgeGlobal(), false);
            this.computeChildDiff("WTCtBridgeRedirects", this.bean.getWTCtBridgeRedirects(), otherTyped.getWTCtBridgeRedirects(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WTCServerMBeanImpl original = (WTCServerMBeanImpl)event.getSourceBean();
            WTCServerMBeanImpl proposed = (WTCServerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("Exports") && !prop.equals("Imports") && !prop.equals("LocalTuxDoms")) {
                  if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (!prop.equals("Passwords") && !prop.equals("RemoteTuxDoms") && !prop.equals("Resource") && !prop.equals("Resources")) {
                     if (prop.equals("Tags")) {
                        if (type == 2) {
                           update.resetAddedObject(update.getAddedObject());
                           original.addTag((String)update.getAddedObject());
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeTag((String)update.getRemovedObject());
                        }

                        if (original.getTags() == null || original.getTags().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 9);
                        }
                     } else if (prop.equals("WTCExports")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addWTCExport((WTCExportMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeWTCExport((WTCExportMBean)update.getRemovedObject());
                        }

                        if (original.getWTCExports() == null || original.getWTCExports().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 16);
                        }
                     } else if (prop.equals("WTCImports")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addWTCImport((WTCImportMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeWTCImport((WTCImportMBean)update.getRemovedObject());
                        }

                        if (original.getWTCImports() == null || original.getWTCImports().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 18);
                        }
                     } else if (prop.equals("WTCLocalTuxDoms")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addWTCLocalTuxDom((WTCLocalTuxDomMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeWTCLocalTuxDom((WTCLocalTuxDomMBean)update.getRemovedObject());
                        }

                        if (original.getWTCLocalTuxDoms() == null || original.getWTCLocalTuxDoms().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 12);
                        }
                     } else if (prop.equals("WTCPasswords")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addWTCPassword((WTCPasswordMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeWTCPassword((WTCPasswordMBean)update.getRemovedObject());
                        }

                        if (original.getWTCPasswords() == null || original.getWTCPasswords().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 20);
                        }
                     } else if (prop.equals("WTCRemoteTuxDoms")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addWTCRemoteTuxDom((WTCRemoteTuxDomMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeWTCRemoteTuxDom((WTCRemoteTuxDomMBean)update.getRemovedObject());
                        }

                        if (original.getWTCRemoteTuxDoms() == null || original.getWTCRemoteTuxDoms().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 14);
                        }
                     } else if (prop.equals("WTCResources")) {
                        if (type == 2) {
                           original.setWTCResources((WTCResourcesMBean)this.createCopy((AbstractDescriptorBean)proposed.getWTCResources()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("WTCResources", original.getWTCResources());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 22);
                     } else if (prop.equals("WTCtBridgeGlobal")) {
                        if (type == 2) {
                           original.setWTCtBridgeGlobal((WTCtBridgeGlobalMBean)this.createCopy((AbstractDescriptorBean)proposed.getWTCtBridgeGlobal()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("WTCtBridgeGlobal", original.getWTCtBridgeGlobal());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 24);
                     } else if (prop.equals("WTCtBridgeRedirects")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addWTCtBridgeRedirect((WTCtBridgeRedirectMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeWTCtBridgeRedirect((WTCtBridgeRedirectMBean)update.getRemovedObject());
                        }

                        if (original.getWTCtBridgeRedirects() == null || original.getWTCtBridgeRedirects().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 28);
                        }
                     } else if (!prop.equals("tBridgeGlobal") && !prop.equals("tBridgeRedirects") && !prop.equals("DynamicallyCreated")) {
                        super.applyPropertyUpdate(event, update);
                     }
                  }
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
            WTCServerMBeanImpl copy = (WTCServerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("WTCExports")) && this.bean.isWTCExportsSet() && !copy._isSet(16)) {
               WTCExportMBean[] oldWTCExports = this.bean.getWTCExports();
               WTCExportMBean[] newWTCExports = new WTCExportMBean[oldWTCExports.length];

               for(i = 0; i < newWTCExports.length; ++i) {
                  newWTCExports[i] = (WTCExportMBean)((WTCExportMBean)this.createCopy((AbstractDescriptorBean)oldWTCExports[i], includeObsolete));
               }

               copy.setWTCExports(newWTCExports);
            }

            if ((excludeProps == null || !excludeProps.contains("WTCImports")) && this.bean.isWTCImportsSet() && !copy._isSet(18)) {
               WTCImportMBean[] oldWTCImports = this.bean.getWTCImports();
               WTCImportMBean[] newWTCImports = new WTCImportMBean[oldWTCImports.length];

               for(i = 0; i < newWTCImports.length; ++i) {
                  newWTCImports[i] = (WTCImportMBean)((WTCImportMBean)this.createCopy((AbstractDescriptorBean)oldWTCImports[i], includeObsolete));
               }

               copy.setWTCImports(newWTCImports);
            }

            if ((excludeProps == null || !excludeProps.contains("WTCLocalTuxDoms")) && this.bean.isWTCLocalTuxDomsSet() && !copy._isSet(12)) {
               WTCLocalTuxDomMBean[] oldWTCLocalTuxDoms = this.bean.getWTCLocalTuxDoms();
               WTCLocalTuxDomMBean[] newWTCLocalTuxDoms = new WTCLocalTuxDomMBean[oldWTCLocalTuxDoms.length];

               for(i = 0; i < newWTCLocalTuxDoms.length; ++i) {
                  newWTCLocalTuxDoms[i] = (WTCLocalTuxDomMBean)((WTCLocalTuxDomMBean)this.createCopy((AbstractDescriptorBean)oldWTCLocalTuxDoms[i], includeObsolete));
               }

               copy.setWTCLocalTuxDoms(newWTCLocalTuxDoms);
            }

            if ((excludeProps == null || !excludeProps.contains("WTCPasswords")) && this.bean.isWTCPasswordsSet() && !copy._isSet(20)) {
               WTCPasswordMBean[] oldWTCPasswords = this.bean.getWTCPasswords();
               WTCPasswordMBean[] newWTCPasswords = new WTCPasswordMBean[oldWTCPasswords.length];

               for(i = 0; i < newWTCPasswords.length; ++i) {
                  newWTCPasswords[i] = (WTCPasswordMBean)((WTCPasswordMBean)this.createCopy((AbstractDescriptorBean)oldWTCPasswords[i], includeObsolete));
               }

               copy.setWTCPasswords(newWTCPasswords);
            }

            if ((excludeProps == null || !excludeProps.contains("WTCRemoteTuxDoms")) && this.bean.isWTCRemoteTuxDomsSet() && !copy._isSet(14)) {
               WTCRemoteTuxDomMBean[] oldWTCRemoteTuxDoms = this.bean.getWTCRemoteTuxDoms();
               WTCRemoteTuxDomMBean[] newWTCRemoteTuxDoms = new WTCRemoteTuxDomMBean[oldWTCRemoteTuxDoms.length];

               for(i = 0; i < newWTCRemoteTuxDoms.length; ++i) {
                  newWTCRemoteTuxDoms[i] = (WTCRemoteTuxDomMBean)((WTCRemoteTuxDomMBean)this.createCopy((AbstractDescriptorBean)oldWTCRemoteTuxDoms[i], includeObsolete));
               }

               copy.setWTCRemoteTuxDoms(newWTCRemoteTuxDoms);
            }

            if ((excludeProps == null || !excludeProps.contains("WTCResources")) && this.bean.isWTCResourcesSet() && !copy._isSet(22)) {
               Object o = this.bean.getWTCResources();
               copy.setWTCResources((WTCResourcesMBean)null);
               copy.setWTCResources(o == null ? null : (WTCResourcesMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WTCtBridgeGlobal")) && this.bean.isWTCtBridgeGlobalSet() && !copy._isSet(24)) {
               Object o = this.bean.getWTCtBridgeGlobal();
               copy.setWTCtBridgeGlobal((WTCtBridgeGlobalMBean)null);
               copy.setWTCtBridgeGlobal(o == null ? null : (WTCtBridgeGlobalMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WTCtBridgeRedirects")) && this.bean.isWTCtBridgeRedirectsSet() && !copy._isSet(28)) {
               WTCtBridgeRedirectMBean[] oldWTCtBridgeRedirects = this.bean.getWTCtBridgeRedirects();
               WTCtBridgeRedirectMBean[] newWTCtBridgeRedirects = new WTCtBridgeRedirectMBean[oldWTCtBridgeRedirects.length];

               for(i = 0; i < newWTCtBridgeRedirects.length; ++i) {
                  newWTCtBridgeRedirects[i] = (WTCtBridgeRedirectMBean)((WTCtBridgeRedirectMBean)this.createCopy((AbstractDescriptorBean)oldWTCtBridgeRedirects[i], includeObsolete));
               }

               copy.setWTCtBridgeRedirects(newWTCtBridgeRedirects);
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
         this.inferSubTree(this.bean.getExports(), clazz, annotation);
         this.inferSubTree(this.bean.getImports(), clazz, annotation);
         this.inferSubTree(this.bean.getLocalTuxDoms(), clazz, annotation);
         this.inferSubTree(this.bean.getPasswords(), clazz, annotation);
         this.inferSubTree(this.bean.getRemoteTuxDoms(), clazz, annotation);
         this.inferSubTree(this.bean.getResource(), clazz, annotation);
         this.inferSubTree(this.bean.getResources(), clazz, annotation);
         this.inferSubTree(this.bean.getWTCExports(), clazz, annotation);
         this.inferSubTree(this.bean.getWTCImports(), clazz, annotation);
         this.inferSubTree(this.bean.getWTCLocalTuxDoms(), clazz, annotation);
         this.inferSubTree(this.bean.getWTCPasswords(), clazz, annotation);
         this.inferSubTree(this.bean.getWTCRemoteTuxDoms(), clazz, annotation);
         this.inferSubTree(this.bean.getWTCResources(), clazz, annotation);
         this.inferSubTree(this.bean.getWTCtBridgeGlobal(), clazz, annotation);
         this.inferSubTree(this.bean.getWTCtBridgeRedirects(), clazz, annotation);
         this.inferSubTree(this.bean.gettBridgeGlobal(), clazz, annotation);
         this.inferSubTree(this.bean.gettBridgeRedirects(), clazz, annotation);
      }
   }
}
