package weblogic.j2ee.descriptor;

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

public class JavaWsdlMappingBeanImpl extends AbstractDescriptorBean implements JavaWsdlMappingBean, Serializable {
   private ExceptionMappingBean[] _ExceptionMappings;
   private String _Id;
   private JavaXmlTypeMappingBean[] _JavaXmlTypeMappings;
   private PackageMappingBean[] _PackageMappings;
   private ServiceEndpointInterfaceMappingBean[] _ServiceEndpointInterfaceMappings;
   private ServiceInterfaceMappingBean[] _ServiceInterfaceMappings;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public JavaWsdlMappingBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public JavaWsdlMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public JavaWsdlMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addPackageMapping(PackageMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         PackageMappingBean[] _new;
         if (this._isSet(0)) {
            _new = (PackageMappingBean[])((PackageMappingBean[])this._getHelper()._extendArray(this.getPackageMappings(), PackageMappingBean.class, param0));
         } else {
            _new = new PackageMappingBean[]{param0};
         }

         try {
            this.setPackageMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PackageMappingBean[] getPackageMappings() {
      return this._PackageMappings;
   }

   public boolean isPackageMappingsInherited() {
      return false;
   }

   public boolean isPackageMappingsSet() {
      return this._isSet(0);
   }

   public void removePackageMapping(PackageMappingBean param0) {
      this.destroyPackageMapping(param0);
   }

   public void setPackageMappings(PackageMappingBean[] param0) throws InvalidAttributeValueException {
      PackageMappingBean[] param0 = param0 == null ? new PackageMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PackageMappingBean[] _oldVal = this._PackageMappings;
      this._PackageMappings = (PackageMappingBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public PackageMappingBean createPackageMapping() {
      PackageMappingBeanImpl _val = new PackageMappingBeanImpl(this, -1);

      try {
         this.addPackageMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPackageMapping(PackageMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         PackageMappingBean[] _old = this.getPackageMappings();
         PackageMappingBean[] _new = (PackageMappingBean[])((PackageMappingBean[])this._getHelper()._removeElement(_old, PackageMappingBean.class, param0));
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
               this.setPackageMappings(_new);
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

   public void addJavaXmlTypeMapping(JavaXmlTypeMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         JavaXmlTypeMappingBean[] _new;
         if (this._isSet(1)) {
            _new = (JavaXmlTypeMappingBean[])((JavaXmlTypeMappingBean[])this._getHelper()._extendArray(this.getJavaXmlTypeMappings(), JavaXmlTypeMappingBean.class, param0));
         } else {
            _new = new JavaXmlTypeMappingBean[]{param0};
         }

         try {
            this.setJavaXmlTypeMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JavaXmlTypeMappingBean[] getJavaXmlTypeMappings() {
      return this._JavaXmlTypeMappings;
   }

   public boolean isJavaXmlTypeMappingsInherited() {
      return false;
   }

   public boolean isJavaXmlTypeMappingsSet() {
      return this._isSet(1);
   }

   public void removeJavaXmlTypeMapping(JavaXmlTypeMappingBean param0) {
      this.destroyJavaXmlTypeMapping(param0);
   }

   public void setJavaXmlTypeMappings(JavaXmlTypeMappingBean[] param0) throws InvalidAttributeValueException {
      JavaXmlTypeMappingBean[] param0 = param0 == null ? new JavaXmlTypeMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JavaXmlTypeMappingBean[] _oldVal = this._JavaXmlTypeMappings;
      this._JavaXmlTypeMappings = (JavaXmlTypeMappingBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public JavaXmlTypeMappingBean createJavaXmlTypeMapping() {
      JavaXmlTypeMappingBeanImpl _val = new JavaXmlTypeMappingBeanImpl(this, -1);

      try {
         this.addJavaXmlTypeMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJavaXmlTypeMapping(JavaXmlTypeMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         JavaXmlTypeMappingBean[] _old = this.getJavaXmlTypeMappings();
         JavaXmlTypeMappingBean[] _new = (JavaXmlTypeMappingBean[])((JavaXmlTypeMappingBean[])this._getHelper()._removeElement(_old, JavaXmlTypeMappingBean.class, param0));
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
               this.setJavaXmlTypeMappings(_new);
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

   public void addExceptionMapping(ExceptionMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         ExceptionMappingBean[] _new;
         if (this._isSet(2)) {
            _new = (ExceptionMappingBean[])((ExceptionMappingBean[])this._getHelper()._extendArray(this.getExceptionMappings(), ExceptionMappingBean.class, param0));
         } else {
            _new = new ExceptionMappingBean[]{param0};
         }

         try {
            this.setExceptionMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ExceptionMappingBean[] getExceptionMappings() {
      return this._ExceptionMappings;
   }

   public boolean isExceptionMappingsInherited() {
      return false;
   }

   public boolean isExceptionMappingsSet() {
      return this._isSet(2);
   }

   public void removeExceptionMapping(ExceptionMappingBean param0) {
      this.destroyExceptionMapping(param0);
   }

   public void setExceptionMappings(ExceptionMappingBean[] param0) throws InvalidAttributeValueException {
      ExceptionMappingBean[] param0 = param0 == null ? new ExceptionMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ExceptionMappingBean[] _oldVal = this._ExceptionMappings;
      this._ExceptionMappings = (ExceptionMappingBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public ExceptionMappingBean createExceptionMapping() {
      ExceptionMappingBeanImpl _val = new ExceptionMappingBeanImpl(this, -1);

      try {
         this.addExceptionMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyExceptionMapping(ExceptionMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         ExceptionMappingBean[] _old = this.getExceptionMappings();
         ExceptionMappingBean[] _new = (ExceptionMappingBean[])((ExceptionMappingBean[])this._getHelper()._removeElement(_old, ExceptionMappingBean.class, param0));
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
               this.setExceptionMappings(_new);
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

   public void addServiceInterfaceMapping(ServiceInterfaceMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         ServiceInterfaceMappingBean[] _new;
         if (this._isSet(3)) {
            _new = (ServiceInterfaceMappingBean[])((ServiceInterfaceMappingBean[])this._getHelper()._extendArray(this.getServiceInterfaceMappings(), ServiceInterfaceMappingBean.class, param0));
         } else {
            _new = new ServiceInterfaceMappingBean[]{param0};
         }

         try {
            this.setServiceInterfaceMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceInterfaceMappingBean[] getServiceInterfaceMappings() {
      return this._ServiceInterfaceMappings;
   }

   public boolean isServiceInterfaceMappingsInherited() {
      return false;
   }

   public boolean isServiceInterfaceMappingsSet() {
      return this._isSet(3);
   }

   public void removeServiceInterfaceMapping(ServiceInterfaceMappingBean param0) {
      this.destroyServiceInterfaceMapping(param0);
   }

   public void setServiceInterfaceMappings(ServiceInterfaceMappingBean[] param0) throws InvalidAttributeValueException {
      ServiceInterfaceMappingBean[] param0 = param0 == null ? new ServiceInterfaceMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceInterfaceMappingBean[] _oldVal = this._ServiceInterfaceMappings;
      this._ServiceInterfaceMappings = (ServiceInterfaceMappingBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public ServiceInterfaceMappingBean createServiceInterfaceMapping() {
      ServiceInterfaceMappingBeanImpl _val = new ServiceInterfaceMappingBeanImpl(this, -1);

      try {
         this.addServiceInterfaceMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServiceInterfaceMapping(ServiceInterfaceMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         ServiceInterfaceMappingBean[] _old = this.getServiceInterfaceMappings();
         ServiceInterfaceMappingBean[] _new = (ServiceInterfaceMappingBean[])((ServiceInterfaceMappingBean[])this._getHelper()._removeElement(_old, ServiceInterfaceMappingBean.class, param0));
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
               this.setServiceInterfaceMappings(_new);
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

   public void addServiceEndpointInterfaceMapping(ServiceEndpointInterfaceMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         ServiceEndpointInterfaceMappingBean[] _new;
         if (this._isSet(4)) {
            _new = (ServiceEndpointInterfaceMappingBean[])((ServiceEndpointInterfaceMappingBean[])this._getHelper()._extendArray(this.getServiceEndpointInterfaceMappings(), ServiceEndpointInterfaceMappingBean.class, param0));
         } else {
            _new = new ServiceEndpointInterfaceMappingBean[]{param0};
         }

         try {
            this.setServiceEndpointInterfaceMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceEndpointInterfaceMappingBean[] getServiceEndpointInterfaceMappings() {
      return this._ServiceEndpointInterfaceMappings;
   }

   public boolean isServiceEndpointInterfaceMappingsInherited() {
      return false;
   }

   public boolean isServiceEndpointInterfaceMappingsSet() {
      return this._isSet(4);
   }

   public void removeServiceEndpointInterfaceMapping(ServiceEndpointInterfaceMappingBean param0) {
      this.destroyServiceEndpointInterfaceMapping(param0);
   }

   public void setServiceEndpointInterfaceMappings(ServiceEndpointInterfaceMappingBean[] param0) throws InvalidAttributeValueException {
      ServiceEndpointInterfaceMappingBean[] param0 = param0 == null ? new ServiceEndpointInterfaceMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceEndpointInterfaceMappingBean[] _oldVal = this._ServiceEndpointInterfaceMappings;
      this._ServiceEndpointInterfaceMappings = (ServiceEndpointInterfaceMappingBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public ServiceEndpointInterfaceMappingBean createServiceEndpointInterfaceMapping() {
      ServiceEndpointInterfaceMappingBeanImpl _val = new ServiceEndpointInterfaceMappingBeanImpl(this, -1);

      try {
         this.addServiceEndpointInterfaceMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServiceEndpointInterfaceMapping(ServiceEndpointInterfaceMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         ServiceEndpointInterfaceMappingBean[] _old = this.getServiceEndpointInterfaceMappings();
         ServiceEndpointInterfaceMappingBean[] _new = (ServiceEndpointInterfaceMappingBean[])((ServiceEndpointInterfaceMappingBean[])this._getHelper()._removeElement(_old, ServiceEndpointInterfaceMappingBean.class, param0));
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
               this.setServiceEndpointInterfaceMappings(_new);
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

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(5);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(5, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ExceptionMappings = new ExceptionMappingBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._JavaXmlTypeMappings = new JavaXmlTypeMappingBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._PackageMappings = new PackageMappingBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._ServiceEndpointInterfaceMappings = new ServiceEndpointInterfaceMappingBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._ServiceInterfaceMappings = new ServiceInterfaceMappingBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Version = "1.1";
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
               break;
            case 7:
               if (s.equals("version")) {
                  return 5;
               }
               break;
            case 15:
               if (s.equals("package-mapping")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("exception-mapping")) {
                  return 2;
               }
               break;
            case 21:
               if (s.equals("java-xml-type-mapping")) {
                  return 1;
               }
               break;
            case 25:
               if (s.equals("service-interface-mapping")) {
                  return 3;
               }
               break;
            case 34:
               if (s.equals("service-endpoint-interface-mapping")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new PackageMappingBeanImpl.SchemaHelper2();
            case 1:
               return new JavaXmlTypeMappingBeanImpl.SchemaHelper2();
            case 2:
               return new ExceptionMappingBeanImpl.SchemaHelper2();
            case 3:
               return new ServiceInterfaceMappingBeanImpl.SchemaHelper2();
            case 4:
               return new ServiceEndpointInterfaceMappingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "java-wsdl-mapping";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "package-mapping";
            case 1:
               return "java-xml-type-mapping";
            case 2:
               return "exception-mapping";
            case 3:
               return "service-interface-mapping";
            case 4:
               return "service-endpoint-interface-mapping";
            case 5:
               return "version";
            case 6:
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
               return true;
            case 4:
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
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JavaWsdlMappingBeanImpl bean;

      protected Helper(JavaWsdlMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "PackageMappings";
            case 1:
               return "JavaXmlTypeMappings";
            case 2:
               return "ExceptionMappings";
            case 3:
               return "ServiceInterfaceMappings";
            case 4:
               return "ServiceEndpointInterfaceMappings";
            case 5:
               return "Version";
            case 6:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ExceptionMappings")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 6;
         } else if (propName.equals("JavaXmlTypeMappings")) {
            return 1;
         } else if (propName.equals("PackageMappings")) {
            return 0;
         } else if (propName.equals("ServiceEndpointInterfaceMappings")) {
            return 4;
         } else if (propName.equals("ServiceInterfaceMappings")) {
            return 3;
         } else {
            return propName.equals("Version") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getExceptionMappings()));
         iterators.add(new ArrayIterator(this.bean.getJavaXmlTypeMappings()));
         iterators.add(new ArrayIterator(this.bean.getPackageMappings()));
         iterators.add(new ArrayIterator(this.bean.getServiceEndpointInterfaceMappings()));
         iterators.add(new ArrayIterator(this.bean.getServiceInterfaceMappings()));
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
            for(i = 0; i < this.bean.getExceptionMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getExceptionMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJavaXmlTypeMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJavaXmlTypeMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPackageMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPackageMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServiceEndpointInterfaceMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceEndpointInterfaceMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServiceInterfaceMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceInterfaceMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
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
            JavaWsdlMappingBeanImpl otherTyped = (JavaWsdlMappingBeanImpl)other;
            this.computeChildDiff("ExceptionMappings", this.bean.getExceptionMappings(), otherTyped.getExceptionMappings(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("JavaXmlTypeMappings", this.bean.getJavaXmlTypeMappings(), otherTyped.getJavaXmlTypeMappings(), false);
            this.computeChildDiff("PackageMappings", this.bean.getPackageMappings(), otherTyped.getPackageMappings(), false);
            this.computeChildDiff("ServiceEndpointInterfaceMappings", this.bean.getServiceEndpointInterfaceMappings(), otherTyped.getServiceEndpointInterfaceMappings(), false);
            this.computeChildDiff("ServiceInterfaceMappings", this.bean.getServiceInterfaceMappings(), otherTyped.getServiceInterfaceMappings(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JavaWsdlMappingBeanImpl original = (JavaWsdlMappingBeanImpl)event.getSourceBean();
            JavaWsdlMappingBeanImpl proposed = (JavaWsdlMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ExceptionMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addExceptionMapping((ExceptionMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeExceptionMapping((ExceptionMappingBean)update.getRemovedObject());
                  }

                  if (original.getExceptionMappings() == null || original.getExceptionMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("JavaXmlTypeMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJavaXmlTypeMapping((JavaXmlTypeMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJavaXmlTypeMapping((JavaXmlTypeMappingBean)update.getRemovedObject());
                  }

                  if (original.getJavaXmlTypeMappings() == null || original.getJavaXmlTypeMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("PackageMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPackageMapping((PackageMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePackageMapping((PackageMappingBean)update.getRemovedObject());
                  }

                  if (original.getPackageMappings() == null || original.getPackageMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("ServiceEndpointInterfaceMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServiceEndpointInterfaceMapping((ServiceEndpointInterfaceMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServiceEndpointInterfaceMapping((ServiceEndpointInterfaceMappingBean)update.getRemovedObject());
                  }

                  if (original.getServiceEndpointInterfaceMappings() == null || original.getServiceEndpointInterfaceMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("ServiceInterfaceMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServiceInterfaceMapping((ServiceInterfaceMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServiceInterfaceMapping((ServiceInterfaceMappingBean)update.getRemovedObject());
                  }

                  if (original.getServiceInterfaceMappings() == null || original.getServiceInterfaceMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            JavaWsdlMappingBeanImpl copy = (JavaWsdlMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("ExceptionMappings")) && this.bean.isExceptionMappingsSet() && !copy._isSet(2)) {
               ExceptionMappingBean[] oldExceptionMappings = this.bean.getExceptionMappings();
               ExceptionMappingBean[] newExceptionMappings = new ExceptionMappingBean[oldExceptionMappings.length];

               for(i = 0; i < newExceptionMappings.length; ++i) {
                  newExceptionMappings[i] = (ExceptionMappingBean)((ExceptionMappingBean)this.createCopy((AbstractDescriptorBean)oldExceptionMappings[i], includeObsolete));
               }

               copy.setExceptionMappings(newExceptionMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaXmlTypeMappings")) && this.bean.isJavaXmlTypeMappingsSet() && !copy._isSet(1)) {
               JavaXmlTypeMappingBean[] oldJavaXmlTypeMappings = this.bean.getJavaXmlTypeMappings();
               JavaXmlTypeMappingBean[] newJavaXmlTypeMappings = new JavaXmlTypeMappingBean[oldJavaXmlTypeMappings.length];

               for(i = 0; i < newJavaXmlTypeMappings.length; ++i) {
                  newJavaXmlTypeMappings[i] = (JavaXmlTypeMappingBean)((JavaXmlTypeMappingBean)this.createCopy((AbstractDescriptorBean)oldJavaXmlTypeMappings[i], includeObsolete));
               }

               copy.setJavaXmlTypeMappings(newJavaXmlTypeMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("PackageMappings")) && this.bean.isPackageMappingsSet() && !copy._isSet(0)) {
               PackageMappingBean[] oldPackageMappings = this.bean.getPackageMappings();
               PackageMappingBean[] newPackageMappings = new PackageMappingBean[oldPackageMappings.length];

               for(i = 0; i < newPackageMappings.length; ++i) {
                  newPackageMappings[i] = (PackageMappingBean)((PackageMappingBean)this.createCopy((AbstractDescriptorBean)oldPackageMappings[i], includeObsolete));
               }

               copy.setPackageMappings(newPackageMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceEndpointInterfaceMappings")) && this.bean.isServiceEndpointInterfaceMappingsSet() && !copy._isSet(4)) {
               ServiceEndpointInterfaceMappingBean[] oldServiceEndpointInterfaceMappings = this.bean.getServiceEndpointInterfaceMappings();
               ServiceEndpointInterfaceMappingBean[] newServiceEndpointInterfaceMappings = new ServiceEndpointInterfaceMappingBean[oldServiceEndpointInterfaceMappings.length];

               for(i = 0; i < newServiceEndpointInterfaceMappings.length; ++i) {
                  newServiceEndpointInterfaceMappings[i] = (ServiceEndpointInterfaceMappingBean)((ServiceEndpointInterfaceMappingBean)this.createCopy((AbstractDescriptorBean)oldServiceEndpointInterfaceMappings[i], includeObsolete));
               }

               copy.setServiceEndpointInterfaceMappings(newServiceEndpointInterfaceMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceInterfaceMappings")) && this.bean.isServiceInterfaceMappingsSet() && !copy._isSet(3)) {
               ServiceInterfaceMappingBean[] oldServiceInterfaceMappings = this.bean.getServiceInterfaceMappings();
               ServiceInterfaceMappingBean[] newServiceInterfaceMappings = new ServiceInterfaceMappingBean[oldServiceInterfaceMappings.length];

               for(i = 0; i < newServiceInterfaceMappings.length; ++i) {
                  newServiceInterfaceMappings[i] = (ServiceInterfaceMappingBean)((ServiceInterfaceMappingBean)this.createCopy((AbstractDescriptorBean)oldServiceInterfaceMappings[i], includeObsolete));
               }

               copy.setServiceInterfaceMappings(newServiceInterfaceMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
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
         this.inferSubTree(this.bean.getExceptionMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getJavaXmlTypeMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getPackageMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceEndpointInterfaceMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceInterfaceMappings(), clazz, annotation);
      }
   }
}
