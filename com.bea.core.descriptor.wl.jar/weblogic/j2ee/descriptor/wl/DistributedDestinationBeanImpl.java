package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class DistributedDestinationBeanImpl extends NamedEntityBeanImpl implements DistributedDestinationBean, Serializable {
   private String _JNDIName;
   private String _LoadBalancingPolicy;
   private String _SAFExportPolicy;
   private String _UnitOfOrderRouting;
   private static SchemaHelper2 _schemaHelper;

   public DistributedDestinationBeanImpl() {
      this._initializeProperty(-1);
   }

   public DistributedDestinationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DistributedDestinationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getJNDIName() {
      return this._JNDIName;
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(3);
   }

   public void setJNDIName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getLoadBalancingPolicy() {
      return this._LoadBalancingPolicy;
   }

   public boolean isLoadBalancingPolicyInherited() {
      return false;
   }

   public boolean isLoadBalancingPolicySet() {
      return this._isSet(4);
   }

   public void setLoadBalancingPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Round-Robin", "Random"};
      param0 = LegalChecks.checkInEnum("LoadBalancingPolicy", param0, _set);
      String _oldVal = this._LoadBalancingPolicy;
      this._LoadBalancingPolicy = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getUnitOfOrderRouting() {
      return this._UnitOfOrderRouting;
   }

   public boolean isUnitOfOrderRoutingInherited() {
      return false;
   }

   public boolean isUnitOfOrderRoutingSet() {
      return this._isSet(5);
   }

   public void setUnitOfOrderRouting(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Hash", "PathService"};
      param0 = LegalChecks.checkInEnum("UnitOfOrderRouting", param0, _set);
      String _oldVal = this._UnitOfOrderRouting;
      this._UnitOfOrderRouting = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getSAFExportPolicy() {
      return this._SAFExportPolicy;
   }

   public boolean isSAFExportPolicyInherited() {
      return false;
   }

   public boolean isSAFExportPolicySet() {
      return this._isSet(6);
   }

   public void setSAFExportPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"All", "None"};
      param0 = LegalChecks.checkInEnum("SAFExportPolicy", param0, _set);
      String _oldVal = this._SAFExportPolicy;
      this._SAFExportPolicy = param0;
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
               this._JNDIName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._LoadBalancingPolicy = "Round-Robin";
               if (initOne) {
                  break;
               }
            case 6:
               this._SAFExportPolicy = "All";
               if (initOne) {
                  break;
               }
            case 5:
               this._UnitOfOrderRouting = "Hash";
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

   public static class SchemaHelper2 extends NamedEntityBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("jndi-name")) {
                  return 3;
               }
               break;
            case 17:
               if (s.equals("saf-export-policy")) {
                  return 6;
               }
               break;
            case 21:
               if (s.equals("load-balancing-policy")) {
                  return 4;
               }

               if (s.equals("unit-of-order-routing")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "jndi-name";
            case 4:
               return "load-balancing-policy";
            case 5:
               return "unit-of-order-routing";
            case 6:
               return "saf-export-policy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends NamedEntityBeanImpl.Helper {
      private DistributedDestinationBeanImpl bean;

      protected Helper(DistributedDestinationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "JNDIName";
            case 4:
               return "LoadBalancingPolicy";
            case 5:
               return "UnitOfOrderRouting";
            case 6:
               return "SAFExportPolicy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("JNDIName")) {
            return 3;
         } else if (propName.equals("LoadBalancingPolicy")) {
            return 4;
         } else if (propName.equals("SAFExportPolicy")) {
            return 6;
         } else {
            return propName.equals("UnitOfOrderRouting") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            if (this.bean.isLoadBalancingPolicySet()) {
               buf.append("LoadBalancingPolicy");
               buf.append(String.valueOf(this.bean.getLoadBalancingPolicy()));
            }

            if (this.bean.isSAFExportPolicySet()) {
               buf.append("SAFExportPolicy");
               buf.append(String.valueOf(this.bean.getSAFExportPolicy()));
            }

            if (this.bean.isUnitOfOrderRoutingSet()) {
               buf.append("UnitOfOrderRouting");
               buf.append(String.valueOf(this.bean.getUnitOfOrderRouting()));
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
            DistributedDestinationBeanImpl otherTyped = (DistributedDestinationBeanImpl)other;
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), true);
            this.computeDiff("LoadBalancingPolicy", this.bean.getLoadBalancingPolicy(), otherTyped.getLoadBalancingPolicy(), true);
            this.computeDiff("SAFExportPolicy", this.bean.getSAFExportPolicy(), otherTyped.getSAFExportPolicy(), false);
            this.computeDiff("UnitOfOrderRouting", this.bean.getUnitOfOrderRouting(), otherTyped.getUnitOfOrderRouting(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DistributedDestinationBeanImpl original = (DistributedDestinationBeanImpl)event.getSourceBean();
            DistributedDestinationBeanImpl proposed = (DistributedDestinationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("LoadBalancingPolicy")) {
                  original.setLoadBalancingPolicy(proposed.getLoadBalancingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("SAFExportPolicy")) {
                  original.setSAFExportPolicy(proposed.getSAFExportPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("UnitOfOrderRouting")) {
                  original.setUnitOfOrderRouting(proposed.getUnitOfOrderRouting());
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
            DistributedDestinationBeanImpl copy = (DistributedDestinationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadBalancingPolicy")) && this.bean.isLoadBalancingPolicySet()) {
               copy.setLoadBalancingPolicy(this.bean.getLoadBalancingPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("SAFExportPolicy")) && this.bean.isSAFExportPolicySet()) {
               copy.setSAFExportPolicy(this.bean.getSAFExportPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("UnitOfOrderRouting")) && this.bean.isUnitOfOrderRoutingSet()) {
               copy.setUnitOfOrderRouting(this.bean.getUnitOfOrderRouting());
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
      }
   }
}
