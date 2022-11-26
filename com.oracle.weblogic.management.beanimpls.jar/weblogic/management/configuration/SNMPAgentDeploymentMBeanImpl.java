package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SNMPAgentDeploymentMBeanImpl extends SNMPAgentMBeanImpl implements SNMPAgentDeploymentMBean, Serializable {
   private int _DeploymentOrder;
   private TargetMBean[] _Targets;
   private static SchemaHelper2 _schemaHelper;

   public SNMPAgentDeploymentMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SNMPAgentDeploymentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SNMPAgentDeploymentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public TargetMBean[] getTargets() {
      return this._Targets;
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isTargetsInherited() {
      return false;
   }

   public boolean isTargetsSet() {
      return this._isSet(38);
   }

   public void setTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Targets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 38, param0) {
                  public void resolveReference(Object value) {
                     try {
                        SNMPAgentDeploymentMBeanImpl.this.addTarget((TargetMBean)value);
                        SNMPAgentDeploymentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])SNMPAgentDeploymentMBeanImpl.this._Targets, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               TargetMBean[] var6 = this._Targets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTarget(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         TargetMBean[] _oldVal = this._Targets;
         this._initializeProperty(38);
         this._postSet(38, _oldVal, this._Targets);
      }
   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 38, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return SNMPAgentDeploymentMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      TargetMBean[] _oldVal = this._Targets;
      this._Targets = param0;
      this._postSet(38, _oldVal, param0);
   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 38)) {
         TargetMBean[] _new;
         if (this._isSet(38)) {
            _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));
         } else {
            _new = new TargetMBean[]{param0};
         }

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public int getDeploymentOrder() {
      return this._DeploymentOrder;
   }

   public boolean isDeploymentOrderInherited() {
      return false;
   }

   public boolean isDeploymentOrderSet() {
      return this._isSet(39);
   }

   public void setDeploymentOrder(int param0) {
      LegalChecks.checkInRange("DeploymentOrder", (long)param0, 0L, 2147483647L);
      int _oldVal = this._DeploymentOrder;
      this._DeploymentOrder = param0;
      this._postSet(39, _oldVal, param0);
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
         idx = 39;
      }

      try {
         switch (idx) {
            case 39:
               this._DeploymentOrder = 1000;
               if (initOne) {
                  break;
               }
            case 38:
               this._Targets = new TargetMBean[0];
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
      return "SNMPAgentDeployment";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DeploymentOrder")) {
         int oldVal = this._DeploymentOrder;
         this._DeploymentOrder = (Integer)v;
         this._postSet(39, oldVal, this._DeploymentOrder);
      } else if (name.equals("Targets")) {
         TargetMBean[] oldVal = this._Targets;
         this._Targets = (TargetMBean[])((TargetMBean[])v);
         this._postSet(38, oldVal, this._Targets);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("DeploymentOrder")) {
         return new Integer(this._DeploymentOrder);
      } else {
         return name.equals("Targets") ? this._Targets : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends SNMPAgentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("target")) {
                  return 38;
               }
               break;
            case 16:
               if (s.equals("deployment-order")) {
                  return 39;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 21:
               return new SNMPTrapDestinationMBeanImpl.SchemaHelper2();
            case 22:
               return new SNMPProxyMBeanImpl.SchemaHelper2();
            case 23:
               return new SNMPGaugeMonitorMBeanImpl.SchemaHelper2();
            case 24:
               return new SNMPStringMonitorMBeanImpl.SchemaHelper2();
            case 25:
               return new SNMPCounterMonitorMBeanImpl.SchemaHelper2();
            case 26:
               return new SNMPLogFilterMBeanImpl.SchemaHelper2();
            case 27:
               return new SNMPAttributeChangeMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 38:
               return "target";
            case 39:
               return "deployment-order";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            default:
               return super.isArray(propIndex);
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 38:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            default:
               return super.isBean(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends SNMPAgentMBeanImpl.Helper {
      private SNMPAgentDeploymentMBeanImpl bean;

      protected Helper(SNMPAgentDeploymentMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 38:
               return "Targets";
            case 39:
               return "DeploymentOrder";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DeploymentOrder")) {
            return 39;
         } else {
            return propName.equals("Targets") ? 38 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getSNMPAttributeChanges()));
         iterators.add(new ArrayIterator(this.bean.getSNMPCounterMonitors()));
         iterators.add(new ArrayIterator(this.bean.getSNMPGaugeMonitors()));
         iterators.add(new ArrayIterator(this.bean.getSNMPLogFilters()));
         iterators.add(new ArrayIterator(this.bean.getSNMPProxies()));
         iterators.add(new ArrayIterator(this.bean.getSNMPStringMonitors()));
         iterators.add(new ArrayIterator(this.bean.getSNMPTrapDestinations()));
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
            if (this.bean.isDeploymentOrderSet()) {
               buf.append("DeploymentOrder");
               buf.append(String.valueOf(this.bean.getDeploymentOrder()));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
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
            SNMPAgentDeploymentMBeanImpl otherTyped = (SNMPAgentDeploymentMBeanImpl)other;
            this.computeDiff("DeploymentOrder", this.bean.getDeploymentOrder(), otherTyped.getDeploymentOrder(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPAgentDeploymentMBeanImpl original = (SNMPAgentDeploymentMBeanImpl)event.getSourceBean();
            SNMPAgentDeploymentMBeanImpl proposed = (SNMPAgentDeploymentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DeploymentOrder")) {
                  original.setDeploymentOrder(proposed.getDeploymentOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("Targets")) {
                  original.setTargetsAsString(proposed.getTargetsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
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
            SNMPAgentDeploymentMBeanImpl copy = (SNMPAgentDeploymentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DeploymentOrder")) && this.bean.isDeploymentOrderSet()) {
               copy.setDeploymentOrder(this.bean.getDeploymentOrder());
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 38);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
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
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}
