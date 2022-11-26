package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import javax.xml.namespace.QName;
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

public class ServiceEndpointInterfaceMappingBeanImpl extends AbstractDescriptorBean implements ServiceEndpointInterfaceMappingBean, Serializable {
   private String _Id;
   private String _ServiceEndpointInterface;
   private ServiceEndpointMethodMappingBean[] _ServiceEndpointMethodMappings;
   private QName _WsdlBinding;
   private QName _WsdlPortType;
   private static SchemaHelper2 _schemaHelper;

   public ServiceEndpointInterfaceMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServiceEndpointInterfaceMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServiceEndpointInterfaceMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getServiceEndpointInterface() {
      return this._ServiceEndpointInterface;
   }

   public boolean isServiceEndpointInterfaceInherited() {
      return false;
   }

   public boolean isServiceEndpointInterfaceSet() {
      return this._isSet(0);
   }

   public void setServiceEndpointInterface(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceEndpointInterface;
      this._ServiceEndpointInterface = param0;
      this._postSet(0, _oldVal, param0);
   }

   public QName getWsdlPortType() {
      return this._WsdlPortType;
   }

   public boolean isWsdlPortTypeInherited() {
      return false;
   }

   public boolean isWsdlPortTypeSet() {
      return this._isSet(1);
   }

   public void setWsdlPortType(QName param0) {
      QName _oldVal = this._WsdlPortType;
      this._WsdlPortType = param0;
      this._postSet(1, _oldVal, param0);
   }

   public QName getWsdlBinding() {
      return this._WsdlBinding;
   }

   public boolean isWsdlBindingInherited() {
      return false;
   }

   public boolean isWsdlBindingSet() {
      return this._isSet(2);
   }

   public void setWsdlBinding(QName param0) {
      QName _oldVal = this._WsdlBinding;
      this._WsdlBinding = param0;
      this._postSet(2, _oldVal, param0);
   }

   public void addServiceEndpointMethodMapping(ServiceEndpointMethodMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         ServiceEndpointMethodMappingBean[] _new;
         if (this._isSet(3)) {
            _new = (ServiceEndpointMethodMappingBean[])((ServiceEndpointMethodMappingBean[])this._getHelper()._extendArray(this.getServiceEndpointMethodMappings(), ServiceEndpointMethodMappingBean.class, param0));
         } else {
            _new = new ServiceEndpointMethodMappingBean[]{param0};
         }

         try {
            this.setServiceEndpointMethodMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceEndpointMethodMappingBean[] getServiceEndpointMethodMappings() {
      return this._ServiceEndpointMethodMappings;
   }

   public boolean isServiceEndpointMethodMappingsInherited() {
      return false;
   }

   public boolean isServiceEndpointMethodMappingsSet() {
      return this._isSet(3);
   }

   public void removeServiceEndpointMethodMapping(ServiceEndpointMethodMappingBean param0) {
      this.destroyServiceEndpointMethodMapping(param0);
   }

   public void setServiceEndpointMethodMappings(ServiceEndpointMethodMappingBean[] param0) throws InvalidAttributeValueException {
      ServiceEndpointMethodMappingBean[] param0 = param0 == null ? new ServiceEndpointMethodMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceEndpointMethodMappingBean[] _oldVal = this._ServiceEndpointMethodMappings;
      this._ServiceEndpointMethodMappings = (ServiceEndpointMethodMappingBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public ServiceEndpointMethodMappingBean createServiceEndpointMethodMapping() {
      ServiceEndpointMethodMappingBeanImpl _val = new ServiceEndpointMethodMappingBeanImpl(this, -1);

      try {
         this.addServiceEndpointMethodMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServiceEndpointMethodMapping(ServiceEndpointMethodMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         ServiceEndpointMethodMappingBean[] _old = this.getServiceEndpointMethodMappings();
         ServiceEndpointMethodMappingBean[] _new = (ServiceEndpointMethodMappingBean[])((ServiceEndpointMethodMappingBean[])this._getHelper()._removeElement(_old, ServiceEndpointMethodMappingBean.class, param0));
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
               this.setServiceEndpointMethodMappings(_new);
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
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(4, _oldVal, param0);
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
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ServiceEndpointInterface = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ServiceEndpointMethodMappings = new ServiceEndpointMethodMappingBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._WsdlBinding = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WsdlPortType = null;
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
                  return 4;
               }
               break;
            case 12:
               if (s.equals("wsdl-binding")) {
                  return 2;
               }
               break;
            case 14:
               if (s.equals("wsdl-port-type")) {
                  return 1;
               }
               break;
            case 26:
               if (s.equals("service-endpoint-interface")) {
                  return 0;
               }
               break;
            case 31:
               if (s.equals("service-endpoint-method-mapping")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new ServiceEndpointMethodMappingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "service-endpoint-interface";
            case 1:
               return "wsdl-port-type";
            case 2:
               return "wsdl-binding";
            case 3:
               return "service-endpoint-method-mapping";
            case 4:
               return "id";
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
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServiceEndpointInterfaceMappingBeanImpl bean;

      protected Helper(ServiceEndpointInterfaceMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServiceEndpointInterface";
            case 1:
               return "WsdlPortType";
            case 2:
               return "WsdlBinding";
            case 3:
               return "ServiceEndpointMethodMappings";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("ServiceEndpointInterface")) {
            return 0;
         } else if (propName.equals("ServiceEndpointMethodMappings")) {
            return 3;
         } else if (propName.equals("WsdlBinding")) {
            return 2;
         } else {
            return propName.equals("WsdlPortType") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getServiceEndpointMethodMappings()));
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isServiceEndpointInterfaceSet()) {
               buf.append("ServiceEndpointInterface");
               buf.append(String.valueOf(this.bean.getServiceEndpointInterface()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getServiceEndpointMethodMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceEndpointMethodMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWsdlBindingSet()) {
               buf.append("WsdlBinding");
               buf.append(String.valueOf(this.bean.getWsdlBinding()));
            }

            if (this.bean.isWsdlPortTypeSet()) {
               buf.append("WsdlPortType");
               buf.append(String.valueOf(this.bean.getWsdlPortType()));
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
            ServiceEndpointInterfaceMappingBeanImpl otherTyped = (ServiceEndpointInterfaceMappingBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("ServiceEndpointInterface", this.bean.getServiceEndpointInterface(), otherTyped.getServiceEndpointInterface(), false);
            this.computeChildDiff("ServiceEndpointMethodMappings", this.bean.getServiceEndpointMethodMappings(), otherTyped.getServiceEndpointMethodMappings(), false);
            this.computeDiff("WsdlBinding", this.bean.getWsdlBinding(), otherTyped.getWsdlBinding(), false);
            this.computeDiff("WsdlPortType", this.bean.getWsdlPortType(), otherTyped.getWsdlPortType(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServiceEndpointInterfaceMappingBeanImpl original = (ServiceEndpointInterfaceMappingBeanImpl)event.getSourceBean();
            ServiceEndpointInterfaceMappingBeanImpl proposed = (ServiceEndpointInterfaceMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ServiceEndpointInterface")) {
                  original.setServiceEndpointInterface(proposed.getServiceEndpointInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ServiceEndpointMethodMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServiceEndpointMethodMapping((ServiceEndpointMethodMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServiceEndpointMethodMapping((ServiceEndpointMethodMappingBean)update.getRemovedObject());
                  }

                  if (original.getServiceEndpointMethodMappings() == null || original.getServiceEndpointMethodMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("WsdlBinding")) {
                  original.setWsdlBinding(proposed.getWsdlBinding());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("WsdlPortType")) {
                  original.setWsdlPortType(proposed.getWsdlPortType());
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
            ServiceEndpointInterfaceMappingBeanImpl copy = (ServiceEndpointInterfaceMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceEndpointInterface")) && this.bean.isServiceEndpointInterfaceSet()) {
               copy.setServiceEndpointInterface(this.bean.getServiceEndpointInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceEndpointMethodMappings")) && this.bean.isServiceEndpointMethodMappingsSet() && !copy._isSet(3)) {
               ServiceEndpointMethodMappingBean[] oldServiceEndpointMethodMappings = this.bean.getServiceEndpointMethodMappings();
               ServiceEndpointMethodMappingBean[] newServiceEndpointMethodMappings = new ServiceEndpointMethodMappingBean[oldServiceEndpointMethodMappings.length];

               for(int i = 0; i < newServiceEndpointMethodMappings.length; ++i) {
                  newServiceEndpointMethodMappings[i] = (ServiceEndpointMethodMappingBean)((ServiceEndpointMethodMappingBean)this.createCopy((AbstractDescriptorBean)oldServiceEndpointMethodMappings[i], includeObsolete));
               }

               copy.setServiceEndpointMethodMappings(newServiceEndpointMethodMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlBinding")) && this.bean.isWsdlBindingSet()) {
               copy.setWsdlBinding(this.bean.getWsdlBinding());
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlPortType")) && this.bean.isWsdlPortTypeSet()) {
               copy.setWsdlPortType(this.bean.getWsdlPortType());
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
         this.inferSubTree(this.bean.getServiceEndpointMethodMappings(), clazz, annotation);
      }
   }
}
