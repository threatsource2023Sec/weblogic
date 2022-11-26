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

public class ServiceInterfaceMappingBeanImpl extends AbstractDescriptorBean implements ServiceInterfaceMappingBean, Serializable {
   private String _Id;
   private PortMappingBean[] _PortMappings;
   private String _ServiceInterface;
   private QName _WsdlServiceName;
   private static SchemaHelper2 _schemaHelper;

   public ServiceInterfaceMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServiceInterfaceMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServiceInterfaceMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getServiceInterface() {
      return this._ServiceInterface;
   }

   public boolean isServiceInterfaceInherited() {
      return false;
   }

   public boolean isServiceInterfaceSet() {
      return this._isSet(0);
   }

   public void setServiceInterface(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceInterface;
      this._ServiceInterface = param0;
      this._postSet(0, _oldVal, param0);
   }

   public QName getWsdlServiceName() {
      return this._WsdlServiceName;
   }

   public boolean isWsdlServiceNameInherited() {
      return false;
   }

   public boolean isWsdlServiceNameSet() {
      return this._isSet(1);
   }

   public void setWsdlServiceName(QName param0) {
      QName _oldVal = this._WsdlServiceName;
      this._WsdlServiceName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addPortMapping(PortMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         PortMappingBean[] _new;
         if (this._isSet(2)) {
            _new = (PortMappingBean[])((PortMappingBean[])this._getHelper()._extendArray(this.getPortMappings(), PortMappingBean.class, param0));
         } else {
            _new = new PortMappingBean[]{param0};
         }

         try {
            this.setPortMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PortMappingBean[] getPortMappings() {
      return this._PortMappings;
   }

   public boolean isPortMappingsInherited() {
      return false;
   }

   public boolean isPortMappingsSet() {
      return this._isSet(2);
   }

   public void removePortMapping(PortMappingBean param0) {
      this.destroyPortMapping(param0);
   }

   public void setPortMappings(PortMappingBean[] param0) throws InvalidAttributeValueException {
      PortMappingBean[] param0 = param0 == null ? new PortMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PortMappingBean[] _oldVal = this._PortMappings;
      this._PortMappings = (PortMappingBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public PortMappingBean createPortMapping() {
      PortMappingBeanImpl _val = new PortMappingBeanImpl(this, -1);

      try {
         this.addPortMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPortMapping(PortMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         PortMappingBean[] _old = this.getPortMappings();
         PortMappingBean[] _new = (PortMappingBean[])((PortMappingBean[])this._getHelper()._removeElement(_old, PortMappingBean.class, param0));
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
               this.setPortMappings(_new);
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
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._PortMappings = new PortMappingBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ServiceInterface = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WsdlServiceName = null;
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
                  return 3;
               }
               break;
            case 12:
               if (s.equals("port-mapping")) {
                  return 2;
               }
               break;
            case 17:
               if (s.equals("service-interface")) {
                  return 0;
               }

               if (s.equals("wsdl-service-name")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new PortMappingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "service-interface";
            case 1:
               return "wsdl-service-name";
            case 2:
               return "port-mapping";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServiceInterfaceMappingBeanImpl bean;

      protected Helper(ServiceInterfaceMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServiceInterface";
            case 1:
               return "WsdlServiceName";
            case 2:
               return "PortMappings";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("PortMappings")) {
            return 2;
         } else if (propName.equals("ServiceInterface")) {
            return 0;
         } else {
            return propName.equals("WsdlServiceName") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getPortMappings()));
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

            childValue = 0L;

            for(int i = 0; i < this.bean.getPortMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPortMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServiceInterfaceSet()) {
               buf.append("ServiceInterface");
               buf.append(String.valueOf(this.bean.getServiceInterface()));
            }

            if (this.bean.isWsdlServiceNameSet()) {
               buf.append("WsdlServiceName");
               buf.append(String.valueOf(this.bean.getWsdlServiceName()));
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
            ServiceInterfaceMappingBeanImpl otherTyped = (ServiceInterfaceMappingBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("PortMappings", this.bean.getPortMappings(), otherTyped.getPortMappings(), false);
            this.computeDiff("ServiceInterface", this.bean.getServiceInterface(), otherTyped.getServiceInterface(), false);
            this.computeDiff("WsdlServiceName", this.bean.getWsdlServiceName(), otherTyped.getWsdlServiceName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServiceInterfaceMappingBeanImpl original = (ServiceInterfaceMappingBeanImpl)event.getSourceBean();
            ServiceInterfaceMappingBeanImpl proposed = (ServiceInterfaceMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("PortMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPortMapping((PortMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePortMapping((PortMappingBean)update.getRemovedObject());
                  }

                  if (original.getPortMappings() == null || original.getPortMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("ServiceInterface")) {
                  original.setServiceInterface(proposed.getServiceInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("WsdlServiceName")) {
                  original.setWsdlServiceName(proposed.getWsdlServiceName());
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
            ServiceInterfaceMappingBeanImpl copy = (ServiceInterfaceMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("PortMappings")) && this.bean.isPortMappingsSet() && !copy._isSet(2)) {
               PortMappingBean[] oldPortMappings = this.bean.getPortMappings();
               PortMappingBean[] newPortMappings = new PortMappingBean[oldPortMappings.length];

               for(int i = 0; i < newPortMappings.length; ++i) {
                  newPortMappings[i] = (PortMappingBean)((PortMappingBean)this.createCopy((AbstractDescriptorBean)oldPortMappings[i], includeObsolete));
               }

               copy.setPortMappings(newPortMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceInterface")) && this.bean.isServiceInterfaceSet()) {
               copy.setServiceInterface(this.bean.getServiceInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlServiceName")) && this.bean.isWsdlServiceNameSet()) {
               copy.setWsdlServiceName(this.bean.getWsdlServiceName());
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
         this.inferSubTree(this.bean.getPortMappings(), clazz, annotation);
      }
   }
}
