package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class MachineMBeanImpl extends ConfigurationMBeanImpl implements MachineMBean, Serializable {
   private String[] _Addresses;
   private NodeManagerMBean _NodeManager;
   private static SchemaHelper2 _schemaHelper;

   public MachineMBeanImpl() {
      this._initializeProperty(-1);
   }

   public MachineMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MachineMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getAddresses() {
      return this._Addresses;
   }

   public boolean isAddressesInherited() {
      return false;
   }

   public boolean isAddressesSet() {
      return this._isSet(10);
   }

   public void setAddresses(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Addresses;
      this._Addresses = param0;
      this._postSet(10, _oldVal, param0);
   }

   public NodeManagerMBean getNodeManager() {
      return this._NodeManager;
   }

   public boolean isNodeManagerInherited() {
      return false;
   }

   public boolean isNodeManagerSet() {
      return this._isSet(11) || this._isAnythingSet((AbstractDescriptorBean)this.getNodeManager());
   }

   public void setNodeManager(NodeManagerMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 11)) {
         this._postCreate(_child);
      }

      NodeManagerMBean _oldVal = this._NodeManager;
      this._NodeManager = param0;
      this._postSet(11, _oldVal, param0);
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
      return super._isAnythingSet() || this.isNodeManagerSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._Addresses = new String[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._NodeManager = new NodeManagerMBeanImpl(this, 11);
               this._postCreate((AbstractDescriptorBean)this._NodeManager);
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
      return "Machine";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Addresses")) {
         String[] oldVal = this._Addresses;
         this._Addresses = (String[])((String[])v);
         this._postSet(10, oldVal, this._Addresses);
      } else if (name.equals("NodeManager")) {
         NodeManagerMBean oldVal = this._NodeManager;
         this._NodeManager = (NodeManagerMBean)v;
         this._postSet(11, oldVal, this._NodeManager);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Addresses")) {
         return this._Addresses;
      } else {
         return name.equals("NodeManager") ? this._NodeManager : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("address")) {
                  return 10;
               }
               break;
            case 12:
               if (s.equals("node-manager")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 11:
               return new NodeManagerMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "address";
            case 11:
               return "node-manager";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 11:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private MachineMBeanImpl bean;

      protected Helper(MachineMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Addresses";
            case 11:
               return "NodeManager";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Addresses")) {
            return 10;
         } else {
            return propName.equals("NodeManager") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getNodeManager() != null) {
            iterators.add(new ArrayIterator(new NodeManagerMBean[]{this.bean.getNodeManager()}));
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
            if (this.bean.isAddressesSet()) {
               buf.append("Addresses");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAddresses())));
            }

            childValue = this.computeChildHashValue(this.bean.getNodeManager());
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
            MachineMBeanImpl otherTyped = (MachineMBeanImpl)other;
            this.computeDiff("Addresses", this.bean.getAddresses(), otherTyped.getAddresses(), false);
            this.computeSubDiff("NodeManager", this.bean.getNodeManager(), otherTyped.getNodeManager());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MachineMBeanImpl original = (MachineMBeanImpl)event.getSourceBean();
            MachineMBeanImpl proposed = (MachineMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Addresses")) {
                  original.setAddresses(proposed.getAddresses());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("NodeManager")) {
                  if (type == 2) {
                     original.setNodeManager((NodeManagerMBean)this.createCopy((AbstractDescriptorBean)proposed.getNodeManager()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("NodeManager", original.getNodeManager());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            MachineMBeanImpl copy = (MachineMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Addresses")) && this.bean.isAddressesSet()) {
               Object o = this.bean.getAddresses();
               copy.setAddresses(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("NodeManager")) && this.bean.isNodeManagerSet() && !copy._isSet(11)) {
               Object o = this.bean.getNodeManager();
               copy.setNodeManager((NodeManagerMBean)null);
               copy.setNodeManager(o == null ? null : (NodeManagerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getNodeManager(), clazz, annotation);
      }
   }
}
