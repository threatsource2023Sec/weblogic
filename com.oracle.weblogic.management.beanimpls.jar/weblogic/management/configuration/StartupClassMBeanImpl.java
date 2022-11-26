package weblogic.management.configuration;

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

public class StartupClassMBeanImpl extends ClassDeploymentMBeanImpl implements StartupClassMBean, Serializable {
   private boolean _FailureIsFatal;
   private boolean _LoadAfterAppsRunning;
   private boolean _LoadBeforeAppActivation;
   private boolean _LoadBeforeAppDeployments;
   private static SchemaHelper2 _schemaHelper;

   public StartupClassMBeanImpl() {
      this._initializeProperty(-1);
   }

   public StartupClassMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public StartupClassMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getFailureIsFatal() {
      return this._FailureIsFatal;
   }

   public boolean isFailureIsFatalInherited() {
      return false;
   }

   public boolean isFailureIsFatalSet() {
      return this._isSet(14);
   }

   public void setFailureIsFatal(boolean param0) {
      boolean _oldVal = this._FailureIsFatal;
      this._FailureIsFatal = param0;
      this._postSet(14, _oldVal, param0);
   }

   public boolean getLoadBeforeAppDeployments() {
      return this._LoadBeforeAppDeployments;
   }

   public boolean isLoadBeforeAppDeploymentsInherited() {
      return false;
   }

   public boolean isLoadBeforeAppDeploymentsSet() {
      return this._isSet(15);
   }

   public void setLoadBeforeAppDeployments(boolean param0) {
      boolean _oldVal = this._LoadBeforeAppDeployments;
      this._LoadBeforeAppDeployments = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean getLoadBeforeAppActivation() {
      return this._LoadBeforeAppActivation;
   }

   public boolean isLoadBeforeAppActivationInherited() {
      return false;
   }

   public boolean isLoadBeforeAppActivationSet() {
      return this._isSet(16);
   }

   public void setLoadBeforeAppActivation(boolean param0) {
      boolean _oldVal = this._LoadBeforeAppActivation;
      this._LoadBeforeAppActivation = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean getLoadAfterAppsRunning() {
      return this._LoadAfterAppsRunning;
   }

   public boolean isLoadAfterAppsRunningInherited() {
      return false;
   }

   public boolean isLoadAfterAppsRunningSet() {
      return this._isSet(17);
   }

   public void setLoadAfterAppsRunning(boolean param0) {
      boolean _oldVal = this._LoadAfterAppsRunning;
      this._LoadAfterAppsRunning = param0;
      this._postSet(17, _oldVal, param0);
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._FailureIsFatal = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._LoadAfterAppsRunning = false;
               if (initOne) {
                  break;
               }
            case 16:
               this._LoadBeforeAppActivation = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._LoadBeforeAppDeployments = false;
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
      return "StartupClass";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("FailureIsFatal")) {
         oldVal = this._FailureIsFatal;
         this._FailureIsFatal = (Boolean)v;
         this._postSet(14, oldVal, this._FailureIsFatal);
      } else if (name.equals("LoadAfterAppsRunning")) {
         oldVal = this._LoadAfterAppsRunning;
         this._LoadAfterAppsRunning = (Boolean)v;
         this._postSet(17, oldVal, this._LoadAfterAppsRunning);
      } else if (name.equals("LoadBeforeAppActivation")) {
         oldVal = this._LoadBeforeAppActivation;
         this._LoadBeforeAppActivation = (Boolean)v;
         this._postSet(16, oldVal, this._LoadBeforeAppActivation);
      } else if (name.equals("LoadBeforeAppDeployments")) {
         oldVal = this._LoadBeforeAppDeployments;
         this._LoadBeforeAppDeployments = (Boolean)v;
         this._postSet(15, oldVal, this._LoadBeforeAppDeployments);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("FailureIsFatal")) {
         return new Boolean(this._FailureIsFatal);
      } else if (name.equals("LoadAfterAppsRunning")) {
         return new Boolean(this._LoadAfterAppsRunning);
      } else if (name.equals("LoadBeforeAppActivation")) {
         return new Boolean(this._LoadBeforeAppActivation);
      } else {
         return name.equals("LoadBeforeAppDeployments") ? new Boolean(this._LoadBeforeAppDeployments) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ClassDeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 16:
               if (s.equals("failure-is-fatal")) {
                  return 14;
               }
               break;
            case 23:
               if (s.equals("load-after-apps-running")) {
                  return 17;
               }
               break;
            case 26:
               if (s.equals("load-before-app-activation")) {
                  return 16;
               }
               break;
            case 27:
               if (s.equals("load-before-app-deployments")) {
                  return 15;
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
            case 14:
               return "failure-is-fatal";
            case 15:
               return "load-before-app-deployments";
            case 16:
               return "load-before-app-activation";
            case 17:
               return "load-after-apps-running";
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

   protected static class Helper extends ClassDeploymentMBeanImpl.Helper {
      private StartupClassMBeanImpl bean;

      protected Helper(StartupClassMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 14:
               return "FailureIsFatal";
            case 15:
               return "LoadBeforeAppDeployments";
            case 16:
               return "LoadBeforeAppActivation";
            case 17:
               return "LoadAfterAppsRunning";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FailureIsFatal")) {
            return 14;
         } else if (propName.equals("LoadAfterAppsRunning")) {
            return 17;
         } else if (propName.equals("LoadBeforeAppActivation")) {
            return 16;
         } else {
            return propName.equals("LoadBeforeAppDeployments") ? 15 : super.getPropertyIndex(propName);
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
            if (this.bean.isFailureIsFatalSet()) {
               buf.append("FailureIsFatal");
               buf.append(String.valueOf(this.bean.getFailureIsFatal()));
            }

            if (this.bean.isLoadAfterAppsRunningSet()) {
               buf.append("LoadAfterAppsRunning");
               buf.append(String.valueOf(this.bean.getLoadAfterAppsRunning()));
            }

            if (this.bean.isLoadBeforeAppActivationSet()) {
               buf.append("LoadBeforeAppActivation");
               buf.append(String.valueOf(this.bean.getLoadBeforeAppActivation()));
            }

            if (this.bean.isLoadBeforeAppDeploymentsSet()) {
               buf.append("LoadBeforeAppDeployments");
               buf.append(String.valueOf(this.bean.getLoadBeforeAppDeployments()));
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
            StartupClassMBeanImpl otherTyped = (StartupClassMBeanImpl)other;
            this.computeDiff("FailureIsFatal", this.bean.getFailureIsFatal(), otherTyped.getFailureIsFatal(), false);
            this.computeDiff("LoadAfterAppsRunning", this.bean.getLoadAfterAppsRunning(), otherTyped.getLoadAfterAppsRunning(), false);
            this.computeDiff("LoadBeforeAppActivation", this.bean.getLoadBeforeAppActivation(), otherTyped.getLoadBeforeAppActivation(), false);
            this.computeDiff("LoadBeforeAppDeployments", this.bean.getLoadBeforeAppDeployments(), otherTyped.getLoadBeforeAppDeployments(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            StartupClassMBeanImpl original = (StartupClassMBeanImpl)event.getSourceBean();
            StartupClassMBeanImpl proposed = (StartupClassMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FailureIsFatal")) {
                  original.setFailureIsFatal(proposed.getFailureIsFatal());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("LoadAfterAppsRunning")) {
                  original.setLoadAfterAppsRunning(proposed.getLoadAfterAppsRunning());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("LoadBeforeAppActivation")) {
                  original.setLoadBeforeAppActivation(proposed.getLoadBeforeAppActivation());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("LoadBeforeAppDeployments")) {
                  original.setLoadBeforeAppDeployments(proposed.getLoadBeforeAppDeployments());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
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
            StartupClassMBeanImpl copy = (StartupClassMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FailureIsFatal")) && this.bean.isFailureIsFatalSet()) {
               copy.setFailureIsFatal(this.bean.getFailureIsFatal());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadAfterAppsRunning")) && this.bean.isLoadAfterAppsRunningSet()) {
               copy.setLoadAfterAppsRunning(this.bean.getLoadAfterAppsRunning());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadBeforeAppActivation")) && this.bean.isLoadBeforeAppActivationSet()) {
               copy.setLoadBeforeAppActivation(this.bean.getLoadBeforeAppActivation());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadBeforeAppDeployments")) && this.bean.isLoadBeforeAppDeploymentsSet()) {
               copy.setLoadBeforeAppDeployments(this.bean.getLoadBeforeAppDeployments());
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
