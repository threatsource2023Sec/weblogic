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

public class PortInfoBeanImpl extends AbstractDescriptorBean implements PortInfoBean, Serializable {
   private PropertyNamevalueBean[] _CallProperties;
   private OperationInfoBean[] _Operations;
   private OwsmPolicyBean[] _OwsmPolicy;
   private String _PortName;
   private PropertyNamevalueBean[] _StubProperties;
   private WSATConfigBean _WSATConfig;
   private static SchemaHelper2 _schemaHelper;

   public PortInfoBeanImpl() {
      this._initializeProperty(-1);
   }

   public PortInfoBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PortInfoBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getPortName() {
      return this._PortName;
   }

   public boolean isPortNameInherited() {
      return false;
   }

   public boolean isPortNameSet() {
      return this._isSet(0);
   }

   public void setPortName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PortName;
      this._PortName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addStubProperty(PropertyNamevalueBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         PropertyNamevalueBean[] _new;
         if (this._isSet(1)) {
            _new = (PropertyNamevalueBean[])((PropertyNamevalueBean[])this._getHelper()._extendArray(this.getStubProperties(), PropertyNamevalueBean.class, param0));
         } else {
            _new = new PropertyNamevalueBean[]{param0};
         }

         try {
            this.setStubProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PropertyNamevalueBean[] getStubProperties() {
      return this._StubProperties;
   }

   public boolean isStubPropertiesInherited() {
      return false;
   }

   public boolean isStubPropertiesSet() {
      return this._isSet(1);
   }

   public void removeStubProperty(PropertyNamevalueBean param0) {
      this.destroyStubProperty(param0);
   }

   public void setStubProperties(PropertyNamevalueBean[] param0) throws InvalidAttributeValueException {
      PropertyNamevalueBean[] param0 = param0 == null ? new PropertyNamevalueBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PropertyNamevalueBean[] _oldVal = this._StubProperties;
      this._StubProperties = (PropertyNamevalueBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public PropertyNamevalueBean createStubProperty() {
      PropertyNamevalueBeanImpl _val = new PropertyNamevalueBeanImpl(this, -1);

      try {
         this.addStubProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyStubProperty(PropertyNamevalueBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         PropertyNamevalueBean[] _old = this.getStubProperties();
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
               this.setStubProperties(_new);
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

   public WSATConfigBean getWSATConfig() {
      return this._WSATConfig;
   }

   public boolean isWSATConfigInherited() {
      return false;
   }

   public boolean isWSATConfigSet() {
      return this._isSet(3);
   }

   public void setWSATConfig(WSATConfigBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWSATConfig() != null && param0 != this.getWSATConfig()) {
         throw new BeanAlreadyExistsException(this.getWSATConfig() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WSATConfigBean _oldVal = this._WSATConfig;
         this._WSATConfig = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public WSATConfigBean createWSATConfig() {
      WSATConfigBeanImpl _val = new WSATConfigBeanImpl(this, -1);

      try {
         this.setWSATConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWSATConfig() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WSATConfig;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWSATConfig((WSATConfigBean)null);
               this._unSet(3);
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

   public OperationInfoBean createOperation() {
      OperationInfoBeanImpl _val = new OperationInfoBeanImpl(this, -1);

      try {
         this.addOperation(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOperation(OperationInfoBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         OperationInfoBean[] _old = this.getOperations();
         OperationInfoBean[] _new = (OperationInfoBean[])((OperationInfoBean[])this._getHelper()._removeElement(_old, OperationInfoBean.class, param0));
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
               this.setOperations(_new);
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

   public void addOperation(OperationInfoBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         OperationInfoBean[] _new;
         if (this._isSet(4)) {
            _new = (OperationInfoBean[])((OperationInfoBean[])this._getHelper()._extendArray(this.getOperations(), OperationInfoBean.class, param0));
         } else {
            _new = new OperationInfoBean[]{param0};
         }

         try {
            this.setOperations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public OperationInfoBean[] getOperations() {
      return this._Operations;
   }

   public boolean isOperationsInherited() {
      return false;
   }

   public boolean isOperationsSet() {
      return this._isSet(4);
   }

   public void removeOperation(OperationInfoBean param0) {
      this.destroyOperation(param0);
   }

   public void setOperations(OperationInfoBean[] param0) throws InvalidAttributeValueException {
      OperationInfoBean[] param0 = param0 == null ? new OperationInfoBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      OperationInfoBean[] _oldVal = this._Operations;
      this._Operations = (OperationInfoBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public OperationInfoBean lookupOperation(String param0) {
      Object[] aary = (Object[])this._Operations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      OperationInfoBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (OperationInfoBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addOwsmPolicy(OwsmPolicyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         OwsmPolicyBean[] _new;
         if (this._isSet(5)) {
            _new = (OwsmPolicyBean[])((OwsmPolicyBean[])this._getHelper()._extendArray(this.getOwsmPolicy(), OwsmPolicyBean.class, param0));
         } else {
            _new = new OwsmPolicyBean[]{param0};
         }

         try {
            this.setOwsmPolicy(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public OwsmPolicyBean[] getOwsmPolicy() {
      return this._OwsmPolicy;
   }

   public boolean isOwsmPolicyInherited() {
      return false;
   }

   public boolean isOwsmPolicySet() {
      return this._isSet(5);
   }

   public void removeOwsmPolicy(OwsmPolicyBean param0) {
      this.destroyOwsmPolicy(param0);
   }

   public void setOwsmPolicy(OwsmPolicyBean[] param0) throws InvalidAttributeValueException {
      OwsmPolicyBean[] param0 = param0 == null ? new OwsmPolicyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      OwsmPolicyBean[] _oldVal = this._OwsmPolicy;
      this._OwsmPolicy = (OwsmPolicyBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public OwsmPolicyBean createOwsmPolicy() {
      OwsmPolicyBeanImpl _val = new OwsmPolicyBeanImpl(this, -1);

      try {
         this.addOwsmPolicy(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOwsmPolicy(OwsmPolicyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         OwsmPolicyBean[] _old = this.getOwsmPolicy();
         OwsmPolicyBean[] _new = (OwsmPolicyBean[])((OwsmPolicyBean[])this._getHelper()._removeElement(_old, OwsmPolicyBean.class, param0));
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
               this.setOwsmPolicy(_new);
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

   public Object _getKey() {
      return this.getPortName();
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
         case 9:
            if (s.equals("port-name")) {
               return info.compareXpaths(this._getPropertyXpath("port-name"));
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
               this._Operations = new OperationInfoBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._OwsmPolicy = new OwsmPolicyBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._PortName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._StubProperties = new PropertyNamevalueBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._WSATConfig = null;
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
            case 9:
               if (s.equals("operation")) {
                  return 4;
               }

               if (s.equals("port-name")) {
                  return 0;
               }
            case 10:
            case 12:
            default:
               break;
            case 11:
               if (s.equals("owsm-policy")) {
                  return 5;
               }

               if (s.equals("wsat-config")) {
                  return 3;
               }
               break;
            case 13:
               if (s.equals("call-property")) {
                  return 2;
               }

               if (s.equals("stub-property")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new PropertyNamevalueBeanImpl.SchemaHelper2();
            case 2:
               return new PropertyNamevalueBeanImpl.SchemaHelper2();
            case 3:
               return new WSATConfigBeanImpl.SchemaHelper2();
            case 4:
               return new OperationInfoBeanImpl.SchemaHelper2();
            case 5:
               return new OwsmPolicyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "port-name";
            case 1:
               return "stub-property";
            case 2:
               return "call-property";
            case 3:
               return "wsat-config";
            case 4:
               return "operation";
            case 5:
               return "owsm-policy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
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

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 5:
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
         indices.add("port-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PortInfoBeanImpl bean;

      protected Helper(PortInfoBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "PortName";
            case 1:
               return "StubProperties";
            case 2:
               return "CallProperties";
            case 3:
               return "WSATConfig";
            case 4:
               return "Operations";
            case 5:
               return "OwsmPolicy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CallProperties")) {
            return 2;
         } else if (propName.equals("Operations")) {
            return 4;
         } else if (propName.equals("OwsmPolicy")) {
            return 5;
         } else if (propName.equals("PortName")) {
            return 0;
         } else if (propName.equals("StubProperties")) {
            return 1;
         } else {
            return propName.equals("WSATConfig") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCallProperties()));
         iterators.add(new ArrayIterator(this.bean.getOperations()));
         iterators.add(new ArrayIterator(this.bean.getOwsmPolicy()));
         iterators.add(new ArrayIterator(this.bean.getStubProperties()));
         if (this.bean.getWSATConfig() != null) {
            iterators.add(new ArrayIterator(new WSATConfigBean[]{this.bean.getWSATConfig()}));
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
            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getCallProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCallProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getOperations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getOperations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getOwsmPolicy().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getOwsmPolicy()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isPortNameSet()) {
               buf.append("PortName");
               buf.append(String.valueOf(this.bean.getPortName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getStubProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getStubProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWSATConfig());
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
            PortInfoBeanImpl otherTyped = (PortInfoBeanImpl)other;
            this.computeChildDiff("CallProperties", this.bean.getCallProperties(), otherTyped.getCallProperties(), false);
            this.computeChildDiff("Operations", this.bean.getOperations(), otherTyped.getOperations(), false);
            this.computeChildDiff("OwsmPolicy", this.bean.getOwsmPolicy(), otherTyped.getOwsmPolicy(), false);
            this.computeDiff("PortName", this.bean.getPortName(), otherTyped.getPortName(), false);
            this.computeChildDiff("StubProperties", this.bean.getStubProperties(), otherTyped.getStubProperties(), false);
            this.computeChildDiff("WSATConfig", this.bean.getWSATConfig(), otherTyped.getWSATConfig(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PortInfoBeanImpl original = (PortInfoBeanImpl)event.getSourceBean();
            PortInfoBeanImpl proposed = (PortInfoBeanImpl)event.getProposedBean();
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
               } else if (prop.equals("Operations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addOperation((OperationInfoBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeOperation((OperationInfoBean)update.getRemovedObject());
                  }

                  if (original.getOperations() == null || original.getOperations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("OwsmPolicy")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addOwsmPolicy((OwsmPolicyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeOwsmPolicy((OwsmPolicyBean)update.getRemovedObject());
                  }

                  if (original.getOwsmPolicy() == null || original.getOwsmPolicy().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("PortName")) {
                  original.setPortName(proposed.getPortName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("StubProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addStubProperty((PropertyNamevalueBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeStubProperty((PropertyNamevalueBean)update.getRemovedObject());
                  }

                  if (original.getStubProperties() == null || original.getStubProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("WSATConfig")) {
                  if (type == 2) {
                     original.setWSATConfig((WSATConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getWSATConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WSATConfig", (DescriptorBean)original.getWSATConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            PortInfoBeanImpl copy = (PortInfoBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            PropertyNamevalueBean[] oldStubProperties;
            PropertyNamevalueBean[] newStubProperties;
            int i;
            if ((excludeProps == null || !excludeProps.contains("CallProperties")) && this.bean.isCallPropertiesSet() && !copy._isSet(2)) {
               oldStubProperties = this.bean.getCallProperties();
               newStubProperties = new PropertyNamevalueBean[oldStubProperties.length];

               for(i = 0; i < newStubProperties.length; ++i) {
                  newStubProperties[i] = (PropertyNamevalueBean)((PropertyNamevalueBean)this.createCopy((AbstractDescriptorBean)oldStubProperties[i], includeObsolete));
               }

               copy.setCallProperties(newStubProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("Operations")) && this.bean.isOperationsSet() && !copy._isSet(4)) {
               OperationInfoBean[] oldOperations = this.bean.getOperations();
               OperationInfoBean[] newOperations = new OperationInfoBean[oldOperations.length];

               for(i = 0; i < newOperations.length; ++i) {
                  newOperations[i] = (OperationInfoBean)((OperationInfoBean)this.createCopy((AbstractDescriptorBean)oldOperations[i], includeObsolete));
               }

               copy.setOperations(newOperations);
            }

            if ((excludeProps == null || !excludeProps.contains("OwsmPolicy")) && this.bean.isOwsmPolicySet() && !copy._isSet(5)) {
               OwsmPolicyBean[] oldOwsmPolicy = this.bean.getOwsmPolicy();
               OwsmPolicyBean[] newOwsmPolicy = new OwsmPolicyBean[oldOwsmPolicy.length];

               for(i = 0; i < newOwsmPolicy.length; ++i) {
                  newOwsmPolicy[i] = (OwsmPolicyBean)((OwsmPolicyBean)this.createCopy((AbstractDescriptorBean)oldOwsmPolicy[i], includeObsolete));
               }

               copy.setOwsmPolicy(newOwsmPolicy);
            }

            if ((excludeProps == null || !excludeProps.contains("PortName")) && this.bean.isPortNameSet()) {
               copy.setPortName(this.bean.getPortName());
            }

            if ((excludeProps == null || !excludeProps.contains("StubProperties")) && this.bean.isStubPropertiesSet() && !copy._isSet(1)) {
               oldStubProperties = this.bean.getStubProperties();
               newStubProperties = new PropertyNamevalueBean[oldStubProperties.length];

               for(i = 0; i < newStubProperties.length; ++i) {
                  newStubProperties[i] = (PropertyNamevalueBean)((PropertyNamevalueBean)this.createCopy((AbstractDescriptorBean)oldStubProperties[i], includeObsolete));
               }

               copy.setStubProperties(newStubProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("WSATConfig")) && this.bean.isWSATConfigSet() && !copy._isSet(3)) {
               Object o = this.bean.getWSATConfig();
               copy.setWSATConfig((WSATConfigBean)null);
               copy.setWSATConfig(o == null ? null : (WSATConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getOperations(), clazz, annotation);
         this.inferSubTree(this.bean.getOwsmPolicy(), clazz, annotation);
         this.inferSubTree(this.bean.getStubProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getWSATConfig(), clazz, annotation);
      }
   }
}
