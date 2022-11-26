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

public class DistributedDestinationMemberBeanImpl extends NamedEntityBeanImpl implements DistributedDestinationMemberBean, Serializable {
   private String _PhysicalDestinationName;
   private int _Weight;
   private static SchemaHelper2 _schemaHelper;

   public DistributedDestinationMemberBeanImpl() {
      this._initializeProperty(-1);
   }

   public DistributedDestinationMemberBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DistributedDestinationMemberBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getWeight() {
      return this._Weight;
   }

   public boolean isWeightInherited() {
      return false;
   }

   public boolean isWeightSet() {
      return this._isSet(3);
   }

   public void setWeight(int param0) throws IllegalArgumentException {
      LegalChecks.checkMin("Weight", param0, 1);
      int _oldVal = this._Weight;
      this._Weight = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getPhysicalDestinationName() {
      if (!this._isSet(4)) {
         try {
            return this.getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._PhysicalDestinationName;
   }

   public boolean isPhysicalDestinationNameInherited() {
      return false;
   }

   public boolean isPhysicalDestinationNameSet() {
      return this._isSet(4);
   }

   public void setPhysicalDestinationName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("PhysicalDestinationName", param0);
      String _oldVal = this._PhysicalDestinationName;
      this._PhysicalDestinationName = param0;
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
               this._PhysicalDestinationName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Weight = 1;
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
            case 6:
               if (s.equals("weight")) {
                  return 3;
               }
               break;
            case 25:
               if (s.equals("physical-destination-name")) {
                  return 4;
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
               return "weight";
            case 4:
               return "physical-destination-name";
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
            case 3:
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
      private DistributedDestinationMemberBeanImpl bean;

      protected Helper(DistributedDestinationMemberBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "Weight";
            case 4:
               return "PhysicalDestinationName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("PhysicalDestinationName")) {
            return 4;
         } else {
            return propName.equals("Weight") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isPhysicalDestinationNameSet()) {
               buf.append("PhysicalDestinationName");
               buf.append(String.valueOf(this.bean.getPhysicalDestinationName()));
            }

            if (this.bean.isWeightSet()) {
               buf.append("Weight");
               buf.append(String.valueOf(this.bean.getWeight()));
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
            DistributedDestinationMemberBeanImpl otherTyped = (DistributedDestinationMemberBeanImpl)other;
            this.computeDiff("PhysicalDestinationName", this.bean.getPhysicalDestinationName(), otherTyped.getPhysicalDestinationName(), true);
            this.computeDiff("Weight", this.bean.getWeight(), otherTyped.getWeight(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DistributedDestinationMemberBeanImpl original = (DistributedDestinationMemberBeanImpl)event.getSourceBean();
            DistributedDestinationMemberBeanImpl proposed = (DistributedDestinationMemberBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("PhysicalDestinationName")) {
                  original.setPhysicalDestinationName(proposed.getPhysicalDestinationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Weight")) {
                  original.setWeight(proposed.getWeight());
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
            DistributedDestinationMemberBeanImpl copy = (DistributedDestinationMemberBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PhysicalDestinationName")) && this.bean.isPhysicalDestinationNameSet()) {
               copy.setPhysicalDestinationName(this.bean.getPhysicalDestinationName());
            }

            if ((excludeProps == null || !excludeProps.contains("Weight")) && this.bean.isWeightSet()) {
               copy.setWeight(this.bean.getWeight());
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
