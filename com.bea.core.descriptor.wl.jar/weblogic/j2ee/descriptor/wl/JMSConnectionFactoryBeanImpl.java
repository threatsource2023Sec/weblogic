package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.JMSModuleValidator;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JMSConnectionFactoryBeanImpl extends TargetableBeanImpl implements JMSConnectionFactoryBean, Serializable {
   private ClientParamsBean _ClientParams;
   private DefaultDeliveryParamsBean _DefaultDeliveryParams;
   private FlowControlParamsBean _FlowControlParams;
   private String _JNDIName;
   private LoadBalancingParamsBean _LoadBalancingParams;
   private String _LocalJNDIName;
   private String _Name;
   private SecurityParamsBean _SecurityParams;
   private TransactionParamsBean _TransactionParams;
   private static SchemaHelper2 _schemaHelper;

   public JMSConnectionFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public JMSConnectionFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JMSConnectionFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getJNDIName() {
      return this._JNDIName;
   }

   public String getName() {
      return this._Name;
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(5);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(1);
   }

   public void setJNDIName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      JMSModuleValidator.validateCFJNDIName(param0);
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(5, _oldVal, param0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("Name", param0);
      JMSModuleValidator.validateCFName(param0);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getLocalJNDIName() {
      return this._LocalJNDIName;
   }

   public boolean isLocalJNDINameInherited() {
      return false;
   }

   public boolean isLocalJNDINameSet() {
      return this._isSet(6);
   }

   public void setLocalJNDIName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LocalJNDIName;
      this._LocalJNDIName = param0;
      this._postSet(6, _oldVal, param0);
   }

   public DefaultDeliveryParamsBean getDefaultDeliveryParams() {
      return this._DefaultDeliveryParams;
   }

   public boolean isDefaultDeliveryParamsInherited() {
      return false;
   }

   public boolean isDefaultDeliveryParamsSet() {
      return this._isSet(7) || this._isAnythingSet((AbstractDescriptorBean)this.getDefaultDeliveryParams());
   }

   public void setDefaultDeliveryParams(DefaultDeliveryParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 7)) {
         this._postCreate(_child);
      }

      DefaultDeliveryParamsBean _oldVal = this._DefaultDeliveryParams;
      this._DefaultDeliveryParams = param0;
      this._postSet(7, _oldVal, param0);
   }

   public ClientParamsBean getClientParams() {
      return this._ClientParams;
   }

   public boolean isClientParamsInherited() {
      return false;
   }

   public boolean isClientParamsSet() {
      return this._isSet(8) || this._isAnythingSet((AbstractDescriptorBean)this.getClientParams());
   }

   public void setClientParams(ClientParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 8)) {
         this._postCreate(_child);
      }

      ClientParamsBean _oldVal = this._ClientParams;
      this._ClientParams = param0;
      this._postSet(8, _oldVal, param0);
   }

   public TransactionParamsBean getTransactionParams() {
      return this._TransactionParams;
   }

   public boolean isTransactionParamsInherited() {
      return false;
   }

   public boolean isTransactionParamsSet() {
      return this._isSet(9) || this._isAnythingSet((AbstractDescriptorBean)this.getTransactionParams());
   }

   public void setTransactionParams(TransactionParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 9)) {
         this._postCreate(_child);
      }

      TransactionParamsBean _oldVal = this._TransactionParams;
      this._TransactionParams = param0;
      this._postSet(9, _oldVal, param0);
   }

   public FlowControlParamsBean getFlowControlParams() {
      return this._FlowControlParams;
   }

   public boolean isFlowControlParamsInherited() {
      return false;
   }

   public boolean isFlowControlParamsSet() {
      return this._isSet(10) || this._isAnythingSet((AbstractDescriptorBean)this.getFlowControlParams());
   }

   public void setFlowControlParams(FlowControlParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 10)) {
         this._postCreate(_child);
      }

      FlowControlParamsBean _oldVal = this._FlowControlParams;
      this._FlowControlParams = param0;
      this._postSet(10, _oldVal, param0);
   }

   public LoadBalancingParamsBean getLoadBalancingParams() {
      return this._LoadBalancingParams;
   }

   public boolean isLoadBalancingParamsInherited() {
      return false;
   }

   public boolean isLoadBalancingParamsSet() {
      return this._isSet(11) || this._isAnythingSet((AbstractDescriptorBean)this.getLoadBalancingParams());
   }

   public void setLoadBalancingParams(LoadBalancingParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 11)) {
         this._postCreate(_child);
      }

      LoadBalancingParamsBean _oldVal = this._LoadBalancingParams;
      this._LoadBalancingParams = param0;
      this._postSet(11, _oldVal, param0);
   }

   public SecurityParamsBean getSecurityParams() {
      return this._SecurityParams;
   }

   public boolean isSecurityParamsInherited() {
      return false;
   }

   public boolean isSecurityParamsSet() {
      return this._isSet(12) || this._isAnythingSet((AbstractDescriptorBean)this.getSecurityParams());
   }

   public void setSecurityParams(SecurityParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 12)) {
         this._postCreate(_child);
      }

      SecurityParamsBean _oldVal = this._SecurityParams;
      this._SecurityParams = param0;
      this._postSet(12, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Name", this.isNameSet());
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
      return super._isAnythingSet() || this.isClientParamsSet() || this.isDefaultDeliveryParamsSet() || this.isFlowControlParamsSet() || this.isLoadBalancingParamsSet() || this.isSecurityParamsSet() || this.isTransactionParamsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._ClientParams = new ClientParamsBeanImpl(this, 8);
               this._postCreate((AbstractDescriptorBean)this._ClientParams);
               if (initOne) {
                  break;
               }
            case 7:
               this._DefaultDeliveryParams = new DefaultDeliveryParamsBeanImpl(this, 7);
               this._postCreate((AbstractDescriptorBean)this._DefaultDeliveryParams);
               if (initOne) {
                  break;
               }
            case 10:
               this._FlowControlParams = new FlowControlParamsBeanImpl(this, 10);
               this._postCreate((AbstractDescriptorBean)this._FlowControlParams);
               if (initOne) {
                  break;
               }
            case 5:
               this._JNDIName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._LoadBalancingParams = new LoadBalancingParamsBeanImpl(this, 11);
               this._postCreate((AbstractDescriptorBean)this._LoadBalancingParams);
               if (initOne) {
                  break;
               }
            case 6:
               this._LocalJNDIName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._SecurityParams = new SecurityParamsBeanImpl(this, 12);
               this._postCreate((AbstractDescriptorBean)this._SecurityParams);
               if (initOne) {
                  break;
               }
            case 9:
               this._TransactionParams = new TransactionParamsBeanImpl(this, 9);
               this._postCreate((AbstractDescriptorBean)this._TransactionParams);
               if (initOne) {
                  break;
               }
            case 2:
            case 3:
            case 4:
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

   public static class SchemaHelper2 extends TargetableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 1;
               }
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
            case 16:
            case 17:
            case 20:
            case 22:
            default:
               break;
            case 9:
               if (s.equals("jndi-name")) {
                  return 5;
               }
               break;
            case 13:
               if (s.equals("client-params")) {
                  return 8;
               }
               break;
            case 15:
               if (s.equals("local-jndi-name")) {
                  return 6;
               }

               if (s.equals("security-params")) {
                  return 12;
               }
               break;
            case 18:
               if (s.equals("transaction-params")) {
                  return 9;
               }
               break;
            case 19:
               if (s.equals("flow-control-params")) {
                  return 10;
               }
               break;
            case 21:
               if (s.equals("load-balancing-params")) {
                  return 11;
               }
               break;
            case 23:
               if (s.equals("default-delivery-params")) {
                  return 7;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 7:
               return new DefaultDeliveryParamsBeanImpl.SchemaHelper2();
            case 8:
               return new ClientParamsBeanImpl.SchemaHelper2();
            case 9:
               return new TransactionParamsBeanImpl.SchemaHelper2();
            case 10:
               return new FlowControlParamsBeanImpl.SchemaHelper2();
            case 11:
               return new LoadBalancingParamsBeanImpl.SchemaHelper2();
            case 12:
               return new SecurityParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "name";
            case 2:
            case 3:
            case 4:
            default:
               return super.getElementName(propIndex);
            case 5:
               return "jndi-name";
            case 6:
               return "local-jndi-name";
            case 7:
               return "default-delivery-params";
            case 8:
               return "client-params";
            case 9:
               return "transaction-params";
            case 10:
               return "flow-control-params";
            case 11:
               return "load-balancing-params";
            case 12:
               return "security-params";
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends TargetableBeanImpl.Helper {
      private JMSConnectionFactoryBeanImpl bean;

      protected Helper(JMSConnectionFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "Name";
            case 2:
            case 3:
            case 4:
            default:
               return super.getPropertyName(propIndex);
            case 5:
               return "JNDIName";
            case 6:
               return "LocalJNDIName";
            case 7:
               return "DefaultDeliveryParams";
            case 8:
               return "ClientParams";
            case 9:
               return "TransactionParams";
            case 10:
               return "FlowControlParams";
            case 11:
               return "LoadBalancingParams";
            case 12:
               return "SecurityParams";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClientParams")) {
            return 8;
         } else if (propName.equals("DefaultDeliveryParams")) {
            return 7;
         } else if (propName.equals("FlowControlParams")) {
            return 10;
         } else if (propName.equals("JNDIName")) {
            return 5;
         } else if (propName.equals("LoadBalancingParams")) {
            return 11;
         } else if (propName.equals("LocalJNDIName")) {
            return 6;
         } else if (propName.equals("Name")) {
            return 1;
         } else if (propName.equals("SecurityParams")) {
            return 12;
         } else {
            return propName.equals("TransactionParams") ? 9 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getClientParams() != null) {
            iterators.add(new ArrayIterator(new ClientParamsBean[]{this.bean.getClientParams()}));
         }

         if (this.bean.getDefaultDeliveryParams() != null) {
            iterators.add(new ArrayIterator(new DefaultDeliveryParamsBean[]{this.bean.getDefaultDeliveryParams()}));
         }

         if (this.bean.getFlowControlParams() != null) {
            iterators.add(new ArrayIterator(new FlowControlParamsBean[]{this.bean.getFlowControlParams()}));
         }

         if (this.bean.getLoadBalancingParams() != null) {
            iterators.add(new ArrayIterator(new LoadBalancingParamsBean[]{this.bean.getLoadBalancingParams()}));
         }

         if (this.bean.getSecurityParams() != null) {
            iterators.add(new ArrayIterator(new SecurityParamsBean[]{this.bean.getSecurityParams()}));
         }

         if (this.bean.getTransactionParams() != null) {
            iterators.add(new ArrayIterator(new TransactionParamsBean[]{this.bean.getTransactionParams()}));
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
            childValue = this.computeChildHashValue(this.bean.getClientParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultDeliveryParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getFlowControlParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            childValue = this.computeChildHashValue(this.bean.getLoadBalancingParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLocalJNDINameSet()) {
               buf.append("LocalJNDIName");
               buf.append(String.valueOf(this.bean.getLocalJNDIName()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = this.computeChildHashValue(this.bean.getSecurityParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTransactionParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            JMSConnectionFactoryBeanImpl otherTyped = (JMSConnectionFactoryBeanImpl)other;
            this.computeSubDiff("ClientParams", this.bean.getClientParams(), otherTyped.getClientParams());
            this.computeSubDiff("DefaultDeliveryParams", this.bean.getDefaultDeliveryParams(), otherTyped.getDefaultDeliveryParams());
            this.computeSubDiff("FlowControlParams", this.bean.getFlowControlParams(), otherTyped.getFlowControlParams());
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), true);
            this.computeSubDiff("LoadBalancingParams", this.bean.getLoadBalancingParams(), otherTyped.getLoadBalancingParams());
            this.computeDiff("LocalJNDIName", this.bean.getLocalJNDIName(), otherTyped.getLocalJNDIName(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeSubDiff("SecurityParams", this.bean.getSecurityParams(), otherTyped.getSecurityParams());
            this.computeSubDiff("TransactionParams", this.bean.getTransactionParams(), otherTyped.getTransactionParams());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSConnectionFactoryBeanImpl original = (JMSConnectionFactoryBeanImpl)event.getSourceBean();
            JMSConnectionFactoryBeanImpl proposed = (JMSConnectionFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClientParams")) {
                  if (type == 2) {
                     original.setClientParams((ClientParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getClientParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ClientParams", (DescriptorBean)original.getClientParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("DefaultDeliveryParams")) {
                  if (type == 2) {
                     original.setDefaultDeliveryParams((DefaultDeliveryParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultDeliveryParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DefaultDeliveryParams", (DescriptorBean)original.getDefaultDeliveryParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("FlowControlParams")) {
                  if (type == 2) {
                     original.setFlowControlParams((FlowControlParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getFlowControlParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("FlowControlParams", (DescriptorBean)original.getFlowControlParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("LoadBalancingParams")) {
                  if (type == 2) {
                     original.setLoadBalancingParams((LoadBalancingParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getLoadBalancingParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("LoadBalancingParams", (DescriptorBean)original.getLoadBalancingParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("LocalJNDIName")) {
                  original.setLocalJNDIName(proposed.getLocalJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SecurityParams")) {
                  if (type == 2) {
                     original.setSecurityParams((SecurityParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getSecurityParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SecurityParams", (DescriptorBean)original.getSecurityParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("TransactionParams")) {
                  if (type == 2) {
                     original.setTransactionParams((TransactionParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getTransactionParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TransactionParams", (DescriptorBean)original.getTransactionParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 9);
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
            JMSConnectionFactoryBeanImpl copy = (JMSConnectionFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClientParams")) && this.bean.isClientParamsSet() && !copy._isSet(8)) {
               Object o = this.bean.getClientParams();
               copy.setClientParams((ClientParamsBean)null);
               copy.setClientParams(o == null ? null : (ClientParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultDeliveryParams")) && this.bean.isDefaultDeliveryParamsSet() && !copy._isSet(7)) {
               Object o = this.bean.getDefaultDeliveryParams();
               copy.setDefaultDeliveryParams((DefaultDeliveryParamsBean)null);
               copy.setDefaultDeliveryParams(o == null ? null : (DefaultDeliveryParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FlowControlParams")) && this.bean.isFlowControlParamsSet() && !copy._isSet(10)) {
               Object o = this.bean.getFlowControlParams();
               copy.setFlowControlParams((FlowControlParamsBean)null);
               copy.setFlowControlParams(o == null ? null : (FlowControlParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadBalancingParams")) && this.bean.isLoadBalancingParamsSet() && !copy._isSet(11)) {
               Object o = this.bean.getLoadBalancingParams();
               copy.setLoadBalancingParams((LoadBalancingParamsBean)null);
               copy.setLoadBalancingParams(o == null ? null : (LoadBalancingParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LocalJNDIName")) && this.bean.isLocalJNDINameSet()) {
               copy.setLocalJNDIName(this.bean.getLocalJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityParams")) && this.bean.isSecurityParamsSet() && !copy._isSet(12)) {
               Object o = this.bean.getSecurityParams();
               copy.setSecurityParams((SecurityParamsBean)null);
               copy.setSecurityParams(o == null ? null : (SecurityParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionParams")) && this.bean.isTransactionParamsSet() && !copy._isSet(9)) {
               Object o = this.bean.getTransactionParams();
               copy.setTransactionParams((TransactionParamsBean)null);
               copy.setTransactionParams(o == null ? null : (TransactionParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getClientParams(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultDeliveryParams(), clazz, annotation);
         this.inferSubTree(this.bean.getFlowControlParams(), clazz, annotation);
         this.inferSubTree(this.bean.getLoadBalancingParams(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityParams(), clazz, annotation);
         this.inferSubTree(this.bean.getTransactionParams(), clazz, annotation);
      }
   }
}
