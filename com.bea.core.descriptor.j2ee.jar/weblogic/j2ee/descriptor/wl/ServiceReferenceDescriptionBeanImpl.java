package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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

public class ServiceReferenceDescriptionBeanImpl extends AbstractDescriptorBean implements ServiceReferenceDescriptionBean, Serializable {
   private PropertyNamevalueBean[] _CallProperties;
   private String _Id;
   private PortInfoBean[] _PortInfos;
   private String _ServiceRefName;
   private String _WsdlUrl;
   private static SchemaHelper2 _schemaHelper;

   public ServiceReferenceDescriptionBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServiceReferenceDescriptionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServiceReferenceDescriptionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getServiceRefName() {
      return this._ServiceRefName;
   }

   public boolean isServiceRefNameInherited() {
      return false;
   }

   public boolean isServiceRefNameSet() {
      return this._isSet(0);
   }

   public void setServiceRefName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceRefName;
      this._ServiceRefName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getWsdlUrl() {
      return this._WsdlUrl;
   }

   public boolean isWsdlUrlInherited() {
      return false;
   }

   public boolean isWsdlUrlSet() {
      return this._isSet(1);
   }

   public void setWsdlUrl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WsdlUrl;
      this._WsdlUrl = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addCallProperty(PropertyNamevalueBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         PropertyNamevalueBean[] _new;
         if (this._isSet(2)) {
            _new = (PropertyNamevalueBean[])((PropertyNamevalueBean[])this._getHelper()._extendArray(this.getCallProperties(), PropertyNamevalueBean.class, param0));
         } else {
            _new = new PropertyNamevalueBean[]{param0};
         }

         try {
            this.setCallProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PropertyNamevalueBean[] getCallProperties() {
      return this._CallProperties;
   }

   public boolean isCallPropertiesInherited() {
      return false;
   }

   public boolean isCallPropertiesSet() {
      return this._isSet(2);
   }

   public void removeCallProperty(PropertyNamevalueBean param0) {
      this.destroyCallProperty(param0);
   }

   public void setCallProperties(PropertyNamevalueBean[] param0) throws InvalidAttributeValueException {
      PropertyNamevalueBean[] param0 = param0 == null ? new PropertyNamevalueBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PropertyNamevalueBean[] _oldVal = this._CallProperties;
      this._CallProperties = (PropertyNamevalueBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public PropertyNamevalueBean createCallProperty() {
      PropertyNamevalueBeanImpl _val = new PropertyNamevalueBeanImpl(this, -1);

      try {
         this.addCallProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCallProperty(PropertyNamevalueBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         PropertyNamevalueBean[] _old = this.getCallProperties();
         PropertyNamevalueBean[] _new = (PropertyNamevalueBean[])((PropertyNamevalueBean[])this._getHelper()._removeElement(_old, PropertyNamevalueBean.class, param0));
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
               this.setCallProperties(_new);
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

   public void addPortInfo(PortInfoBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         PortInfoBean[] _new;
         if (this._isSet(3)) {
            _new = (PortInfoBean[])((PortInfoBean[])this._getHelper()._extendArray(this.getPortInfos(), PortInfoBean.class, param0));
         } else {
            _new = new PortInfoBean[]{param0};
         }

         try {
            this.setPortInfos(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PortInfoBean[] getPortInfos() {
      return this._PortInfos;
   }

   public boolean isPortInfosInherited() {
      return false;
   }

   public boolean isPortInfosSet() {
      return this._isSet(3);
   }

   public void removePortInfo(PortInfoBean param0) {
      this.destroyPortInfo(param0);
   }

   public void setPortInfos(PortInfoBean[] param0) throws InvalidAttributeValueException {
      PortInfoBean[] param0 = param0 == null ? new PortInfoBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PortInfoBean[] _oldVal = this._PortInfos;
      this._PortInfos = (PortInfoBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public PortInfoBean createPortInfo() {
      PortInfoBeanImpl _val = new PortInfoBeanImpl(this, -1);

      try {
         this.addPortInfo(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PortInfoBean lookupPortInfo(String param0) {
      Object[] aary = (Object[])this._PortInfos;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PortInfoBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PortInfoBeanImpl)it.previous();
      } while(!bean.getPortName().equals(param0));

      return bean;
   }

   public void destroyPortInfo(PortInfoBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         PortInfoBean[] _old = this.getPortInfos();
         PortInfoBean[] _new = (PortInfoBean[])((PortInfoBean[])this._getHelper()._removeElement(_old, PortInfoBean.class, param0));
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
               this.setPortInfos(_new);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._CallProperties = new PropertyNamevalueBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._PortInfos = new PortInfoBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ServiceRefName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WsdlUrl = null;
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
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            default:
               break;
            case 8:
               if (s.equals("wsdl-url")) {
                  return 1;
               }
               break;
            case 9:
               if (s.equals("port-info")) {
                  return 3;
               }
               break;
            case 13:
               if (s.equals("call-property")) {
                  return 2;
               }
               break;
            case 16:
               if (s.equals("service-ref-name")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new PropertyNamevalueBeanImpl.SchemaHelper2();
            case 3:
               return new PortInfoBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "service-ref-name";
            case 1:
               return "wsdl-url";
            case 2:
               return "call-property";
            case 3:
               return "port-info";
            case 4:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
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
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 1:
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
         indices.add("service-ref-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServiceReferenceDescriptionBeanImpl bean;

      protected Helper(ServiceReferenceDescriptionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServiceRefName";
            case 1:
               return "WsdlUrl";
            case 2:
               return "CallProperties";
            case 3:
               return "PortInfos";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CallProperties")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("PortInfos")) {
            return 3;
         } else if (propName.equals("ServiceRefName")) {
            return 0;
         } else {
            return propName.equals("WsdlUrl") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCallProperties()));
         iterators.add(new ArrayIterator(this.bean.getPortInfos()));
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
            for(i = 0; i < this.bean.getCallProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCallProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPortInfos().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPortInfos()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServiceRefNameSet()) {
               buf.append("ServiceRefName");
               buf.append(String.valueOf(this.bean.getServiceRefName()));
            }

            if (this.bean.isWsdlUrlSet()) {
               buf.append("WsdlUrl");
               buf.append(String.valueOf(this.bean.getWsdlUrl()));
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
            ServiceReferenceDescriptionBeanImpl otherTyped = (ServiceReferenceDescriptionBeanImpl)other;
            this.computeChildDiff("CallProperties", this.bean.getCallProperties(), otherTyped.getCallProperties(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("PortInfos", this.bean.getPortInfos(), otherTyped.getPortInfos(), false);
            this.computeDiff("ServiceRefName", this.bean.getServiceRefName(), otherTyped.getServiceRefName(), false);
            this.computeDiff("WsdlUrl", this.bean.getWsdlUrl(), otherTyped.getWsdlUrl(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServiceReferenceDescriptionBeanImpl original = (ServiceReferenceDescriptionBeanImpl)event.getSourceBean();
            ServiceReferenceDescriptionBeanImpl proposed = (ServiceReferenceDescriptionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CallProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCallProperty((PropertyNamevalueBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCallProperty((PropertyNamevalueBean)update.getRemovedObject());
                  }

                  if (original.getCallProperties() == null || original.getCallProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("PortInfos")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPortInfo((PortInfoBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePortInfo((PortInfoBean)update.getRemovedObject());
                  }

                  if (original.getPortInfos() == null || original.getPortInfos().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("ServiceRefName")) {
                  original.setServiceRefName(proposed.getServiceRefName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("WsdlUrl")) {
                  original.setWsdlUrl(proposed.getWsdlUrl());
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
            ServiceReferenceDescriptionBeanImpl copy = (ServiceReferenceDescriptionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("CallProperties")) && this.bean.isCallPropertiesSet() && !copy._isSet(2)) {
               PropertyNamevalueBean[] oldCallProperties = this.bean.getCallProperties();
               PropertyNamevalueBean[] newCallProperties = new PropertyNamevalueBean[oldCallProperties.length];

               for(i = 0; i < newCallProperties.length; ++i) {
                  newCallProperties[i] = (PropertyNamevalueBean)((PropertyNamevalueBean)this.createCopy((AbstractDescriptorBean)oldCallProperties[i], includeObsolete));
               }

               copy.setCallProperties(newCallProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("PortInfos")) && this.bean.isPortInfosSet() && !copy._isSet(3)) {
               PortInfoBean[] oldPortInfos = this.bean.getPortInfos();
               PortInfoBean[] newPortInfos = new PortInfoBean[oldPortInfos.length];

               for(i = 0; i < newPortInfos.length; ++i) {
                  newPortInfos[i] = (PortInfoBean)((PortInfoBean)this.createCopy((AbstractDescriptorBean)oldPortInfos[i], includeObsolete));
               }

               copy.setPortInfos(newPortInfos);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceRefName")) && this.bean.isServiceRefNameSet()) {
               copy.setServiceRefName(this.bean.getServiceRefName());
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlUrl")) && this.bean.isWsdlUrlSet()) {
               copy.setWsdlUrl(this.bean.getWsdlUrl());
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
         this.inferSubTree(this.bean.getCallProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getPortInfos(), clazz, annotation);
      }
   }
}
