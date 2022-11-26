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

public class PortComponentRefBeanImpl extends AbstractDescriptorBean implements PortComponentRefBean, Serializable {
   private AddressingBean _Addressing;
   private boolean _EnableMtom;
   private String _Id;
   private int _MtomThreshold;
   private String _PortComponentLink;
   private RespectBindingBean _RespectBinding;
   private String _ServiceEndpointInterface;
   private static SchemaHelper2 _schemaHelper;

   public PortComponentRefBeanImpl() {
      this._initializeProperty(-1);
   }

   public PortComponentRefBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PortComponentRefBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public boolean isEnableMtom() {
      return this._EnableMtom;
   }

   public boolean isEnableMtomInherited() {
      return false;
   }

   public boolean isEnableMtomSet() {
      return this._isSet(1);
   }

   public void setEnableMtom(boolean param0) {
      boolean _oldVal = this._EnableMtom;
      this._EnableMtom = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getMtomThreshold() {
      return this._MtomThreshold;
   }

   public boolean isMtomThresholdInherited() {
      return false;
   }

   public boolean isMtomThresholdSet() {
      return this._isSet(2);
   }

   public void setMtomThreshold(int param0) {
      int _oldVal = this._MtomThreshold;
      this._MtomThreshold = param0;
      this._postSet(2, _oldVal, param0);
   }

   public AddressingBean getAddressing() {
      return this._Addressing;
   }

   public boolean isAddressingInherited() {
      return false;
   }

   public boolean isAddressingSet() {
      return this._isSet(3);
   }

   public void setAddressing(AddressingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAddressing() != null && param0 != this.getAddressing()) {
         throw new BeanAlreadyExistsException(this.getAddressing() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AddressingBean _oldVal = this._Addressing;
         this._Addressing = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public AddressingBean createAddressing() {
      AddressingBeanImpl _val = new AddressingBeanImpl(this, -1);

      try {
         this.setAddressing(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAddressing(AddressingBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Addressing;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAddressing((AddressingBean)null);
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

   public RespectBindingBean getRespectBinding() {
      return this._RespectBinding;
   }

   public boolean isRespectBindingInherited() {
      return false;
   }

   public boolean isRespectBindingSet() {
      return this._isSet(4);
   }

   public void setRespectBinding(RespectBindingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getRespectBinding() != null && param0 != this.getRespectBinding()) {
         throw new BeanAlreadyExistsException(this.getRespectBinding() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         RespectBindingBean _oldVal = this._RespectBinding;
         this._RespectBinding = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public RespectBindingBean createRespectBinding() {
      RespectBindingBeanImpl _val = new RespectBindingBeanImpl(this, -1);

      try {
         this.setRespectBinding(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRespectBinding(RespectBindingBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._RespectBinding;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setRespectBinding((RespectBindingBean)null);
               this._unSet(4);
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

   public String getPortComponentLink() {
      return this._PortComponentLink;
   }

   public boolean isPortComponentLinkInherited() {
      return false;
   }

   public boolean isPortComponentLinkSet() {
      return this._isSet(5);
   }

   public void setPortComponentLink(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PortComponentLink;
      this._PortComponentLink = param0;
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Addressing = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._MtomThreshold = 0;
               if (initOne) {
                  break;
               }
            case 5:
               this._PortComponentLink = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._RespectBinding = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ServiceEndpointInterface = null;
               if (initOne) {
                  break;
               }
            case 1:
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
                  return 6;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            default:
               break;
            case 10:
               if (s.equals("addressing")) {
                  return 3;
               }
               break;
            case 11:
               if (s.equals("enable-mtom")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("mtom-threshold")) {
                  return 2;
               }
               break;
            case 15:
               if (s.equals("respect-binding")) {
                  return 4;
               }
               break;
            case 19:
               if (s.equals("port-component-link")) {
                  return 5;
               }
               break;
            case 26:
               if (s.equals("service-endpoint-interface")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new AddressingBeanImpl.SchemaHelper2();
            case 4:
               return new RespectBindingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "service-endpoint-interface";
            case 1:
               return "enable-mtom";
            case 2:
               return "mtom-threshold";
            case 3:
               return "addressing";
            case 4:
               return "respect-binding";
            case 5:
               return "port-component-link";
            case 6:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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
      private PortComponentRefBeanImpl bean;

      protected Helper(PortComponentRefBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServiceEndpointInterface";
            case 1:
               return "EnableMtom";
            case 2:
               return "MtomThreshold";
            case 3:
               return "Addressing";
            case 4:
               return "RespectBinding";
            case 5:
               return "PortComponentLink";
            case 6:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Addressing")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 6;
         } else if (propName.equals("MtomThreshold")) {
            return 2;
         } else if (propName.equals("PortComponentLink")) {
            return 5;
         } else if (propName.equals("RespectBinding")) {
            return 4;
         } else if (propName.equals("ServiceEndpointInterface")) {
            return 0;
         } else {
            return propName.equals("EnableMtom") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAddressing() != null) {
            iterators.add(new ArrayIterator(new AddressingBean[]{this.bean.getAddressing()}));
         }

         if (this.bean.getRespectBinding() != null) {
            iterators.add(new ArrayIterator(new RespectBindingBean[]{this.bean.getRespectBinding()}));
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
            childValue = this.computeChildHashValue(this.bean.getAddressing());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isMtomThresholdSet()) {
               buf.append("MtomThreshold");
               buf.append(String.valueOf(this.bean.getMtomThreshold()));
            }

            if (this.bean.isPortComponentLinkSet()) {
               buf.append("PortComponentLink");
               buf.append(String.valueOf(this.bean.getPortComponentLink()));
            }

            childValue = this.computeChildHashValue(this.bean.getRespectBinding());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServiceEndpointInterfaceSet()) {
               buf.append("ServiceEndpointInterface");
               buf.append(String.valueOf(this.bean.getServiceEndpointInterface()));
            }

            if (this.bean.isEnableMtomSet()) {
               buf.append("EnableMtom");
               buf.append(String.valueOf(this.bean.isEnableMtom()));
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
            PortComponentRefBeanImpl otherTyped = (PortComponentRefBeanImpl)other;
            this.computeChildDiff("Addressing", this.bean.getAddressing(), otherTyped.getAddressing(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("MtomThreshold", this.bean.getMtomThreshold(), otherTyped.getMtomThreshold(), false);
            this.computeDiff("PortComponentLink", this.bean.getPortComponentLink(), otherTyped.getPortComponentLink(), false);
            this.computeChildDiff("RespectBinding", this.bean.getRespectBinding(), otherTyped.getRespectBinding(), false);
            this.computeDiff("ServiceEndpointInterface", this.bean.getServiceEndpointInterface(), otherTyped.getServiceEndpointInterface(), false);
            this.computeDiff("EnableMtom", this.bean.isEnableMtom(), otherTyped.isEnableMtom(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PortComponentRefBeanImpl original = (PortComponentRefBeanImpl)event.getSourceBean();
            PortComponentRefBeanImpl proposed = (PortComponentRefBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Addressing")) {
                  if (type == 2) {
                     original.setAddressing((AddressingBean)this.createCopy((AbstractDescriptorBean)proposed.getAddressing()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Addressing", (DescriptorBean)original.getAddressing());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("MtomThreshold")) {
                  original.setMtomThreshold(proposed.getMtomThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("PortComponentLink")) {
                  original.setPortComponentLink(proposed.getPortComponentLink());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("RespectBinding")) {
                  if (type == 2) {
                     original.setRespectBinding((RespectBindingBean)this.createCopy((AbstractDescriptorBean)proposed.getRespectBinding()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("RespectBinding", (DescriptorBean)original.getRespectBinding());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ServiceEndpointInterface")) {
                  original.setServiceEndpointInterface(proposed.getServiceEndpointInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("EnableMtom")) {
                  original.setEnableMtom(proposed.isEnableMtom());
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
            PortComponentRefBeanImpl copy = (PortComponentRefBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Addressing")) && this.bean.isAddressingSet() && !copy._isSet(3)) {
               Object o = this.bean.getAddressing();
               copy.setAddressing((AddressingBean)null);
               copy.setAddressing(o == null ? null : (AddressingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MtomThreshold")) && this.bean.isMtomThresholdSet()) {
               copy.setMtomThreshold(this.bean.getMtomThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("PortComponentLink")) && this.bean.isPortComponentLinkSet()) {
               copy.setPortComponentLink(this.bean.getPortComponentLink());
            }

            if ((excludeProps == null || !excludeProps.contains("RespectBinding")) && this.bean.isRespectBindingSet() && !copy._isSet(4)) {
               Object o = this.bean.getRespectBinding();
               copy.setRespectBinding((RespectBindingBean)null);
               copy.setRespectBinding(o == null ? null : (RespectBindingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceEndpointInterface")) && this.bean.isServiceEndpointInterfaceSet()) {
               copy.setServiceEndpointInterface(this.bean.getServiceEndpointInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableMtom")) && this.bean.isEnableMtomSet()) {
               copy.setEnableMtom(this.bean.isEnableMtom());
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
         this.inferSubTree(this.bean.getAddressing(), clazz, annotation);
         this.inferSubTree(this.bean.getRespectBinding(), clazz, annotation);
      }
   }
}
