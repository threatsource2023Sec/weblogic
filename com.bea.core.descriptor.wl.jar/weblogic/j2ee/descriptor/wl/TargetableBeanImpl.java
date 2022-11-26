package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class TargetableBeanImpl extends NamedEntityBeanImpl implements TargetableBean, Serializable {
   private boolean _DefaultTargetingEnabled;
   private String _SubDeploymentName;
   private static SchemaHelper2 _schemaHelper;

   public TargetableBeanImpl() {
      this._initializeProperty(-1);
   }

   public TargetableBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TargetableBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSubDeploymentName() {
      if (!this._isSet(3)) {
         try {
            return this.getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._SubDeploymentName;
   }

   public boolean isSubDeploymentNameInherited() {
      return false;
   }

   public boolean isSubDeploymentNameSet() {
      return this._isSet(3);
   }

   public void setSubDeploymentName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SubDeploymentName;
      this._SubDeploymentName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isDefaultTargetingEnabled() {
      return this._DefaultTargetingEnabled;
   }

   public boolean isDefaultTargetingEnabledInherited() {
      return false;
   }

   public boolean isDefaultTargetingEnabledSet() {
      return this._isSet(4);
   }

   public void setDefaultTargetingEnabled(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._DefaultTargetingEnabled;
      this._DefaultTargetingEnabled = param0;
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._SubDeploymentName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._DefaultTargetingEnabled = false;
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
            case 19:
               if (s.equals("sub-deployment-name")) {
                  return 3;
               }
               break;
            case 25:
               if (s.equals("default-targeting-enabled")) {
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
               return "sub-deployment-name";
            case 4:
               return "default-targeting-enabled";
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
      private TargetableBeanImpl bean;

      protected Helper(TargetableBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "SubDeploymentName";
            case 4:
               return "DefaultTargetingEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("SubDeploymentName")) {
            return 3;
         } else {
            return propName.equals("DefaultTargetingEnabled") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isSubDeploymentNameSet()) {
               buf.append("SubDeploymentName");
               buf.append(String.valueOf(this.bean.getSubDeploymentName()));
            }

            if (this.bean.isDefaultTargetingEnabledSet()) {
               buf.append("DefaultTargetingEnabled");
               buf.append(String.valueOf(this.bean.isDefaultTargetingEnabled()));
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
            TargetableBeanImpl otherTyped = (TargetableBeanImpl)other;
            this.computeDiff("SubDeploymentName", this.bean.getSubDeploymentName(), otherTyped.getSubDeploymentName(), false);
            this.computeDiff("DefaultTargetingEnabled", this.bean.isDefaultTargetingEnabled(), otherTyped.isDefaultTargetingEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TargetableBeanImpl original = (TargetableBeanImpl)event.getSourceBean();
            TargetableBeanImpl proposed = (TargetableBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("SubDeploymentName")) {
                  original.setSubDeploymentName(proposed.getSubDeploymentName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("DefaultTargetingEnabled")) {
                  original.setDefaultTargetingEnabled(proposed.isDefaultTargetingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            TargetableBeanImpl copy = (TargetableBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("SubDeploymentName")) && this.bean.isSubDeploymentNameSet()) {
               copy.setSubDeploymentName(this.bean.getSubDeploymentName());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultTargetingEnabled")) && this.bean.isDefaultTargetingEnabledSet()) {
               copy.setDefaultTargetingEnabled(this.bean.isDefaultTargetingEnabled());
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
