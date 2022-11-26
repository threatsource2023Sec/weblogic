package weblogic.diagnostics.descriptor;

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

public class WLDFScalingActionBeanImpl extends WLDFNotificationBeanImpl implements WLDFScalingActionBean, Serializable {
   private String _ClusterName;
   private int _ScalingSize;
   private static SchemaHelper2 _schemaHelper;

   public WLDFScalingActionBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFScalingActionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFScalingActionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getClusterName() {
      return this._ClusterName;
   }

   public boolean isClusterNameInherited() {
      return false;
   }

   public boolean isClusterNameSet() {
      return this._isSet(4);
   }

   public void setClusterName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("ClusterName", param0);
      LegalChecks.checkNonNull("ClusterName", param0);
      String _oldVal = this._ClusterName;
      this._ClusterName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getScalingSize() {
      return this._ScalingSize;
   }

   public boolean isScalingSizeInherited() {
      return false;
   }

   public boolean isScalingSizeSet() {
      return this._isSet(5);
   }

   public void setScalingSize(int param0) {
      LegalChecks.checkMin("ScalingSize", param0, 1);
      int _oldVal = this._ScalingSize;
      this._ScalingSize = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("ClusterName", this.isClusterNameSet());
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
               this._ClusterName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._ScalingSize = 1;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFNotificationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("cluster-name")) {
                  return 4;
               } else if (s.equals("scaling-size")) {
                  return 5;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "cluster-name";
            case 5:
               return "scaling-size";
            default:
               return super.getElementName(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends WLDFNotificationBeanImpl.Helper {
      private WLDFScalingActionBeanImpl bean;

      protected Helper(WLDFScalingActionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "ClusterName";
            case 5:
               return "ScalingSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClusterName")) {
            return 4;
         } else {
            return propName.equals("ScalingSize") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isClusterNameSet()) {
               buf.append("ClusterName");
               buf.append(String.valueOf(this.bean.getClusterName()));
            }

            if (this.bean.isScalingSizeSet()) {
               buf.append("ScalingSize");
               buf.append(String.valueOf(this.bean.getScalingSize()));
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
            WLDFScalingActionBeanImpl otherTyped = (WLDFScalingActionBeanImpl)other;
            this.computeDiff("ClusterName", this.bean.getClusterName(), otherTyped.getClusterName(), true);
            this.computeDiff("ScalingSize", this.bean.getScalingSize(), otherTyped.getScalingSize(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFScalingActionBeanImpl original = (WLDFScalingActionBeanImpl)event.getSourceBean();
            WLDFScalingActionBeanImpl proposed = (WLDFScalingActionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClusterName")) {
                  original.setClusterName(proposed.getClusterName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ScalingSize")) {
                  original.setScalingSize(proposed.getScalingSize());
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
            WLDFScalingActionBeanImpl copy = (WLDFScalingActionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClusterName")) && this.bean.isClusterNameSet()) {
               copy.setClusterName(this.bean.getClusterName());
            }

            if ((excludeProps == null || !excludeProps.contains("ScalingSize")) && this.bean.isScalingSizeSet()) {
               copy.setScalingSize(this.bean.getScalingSize());
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
