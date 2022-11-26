package weblogic.management.configuration;

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

public class DeploymentConfigOverridesMBeanImpl extends ConfigurationMBeanImpl implements DeploymentConfigOverridesMBean, Serializable {
   private String _Dir;
   private int _MaxOldAppVersions;
   private int _PollInterval;
   private static SchemaHelper2 _schemaHelper;

   public DeploymentConfigOverridesMBeanImpl() {
      this._initializeProperty(-1);
   }

   public DeploymentConfigOverridesMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DeploymentConfigOverridesMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDir() {
      return this._Dir;
   }

   public boolean isDirInherited() {
      return false;
   }

   public boolean isDirSet() {
      return this._isSet(10);
   }

   public void setDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Dir;
      this._Dir = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getPollInterval() {
      return this._PollInterval;
   }

   public boolean isPollIntervalInherited() {
      return false;
   }

   public boolean isPollIntervalSet() {
      return this._isSet(11);
   }

   public void setPollInterval(int param0) {
      LegalChecks.checkMin("PollInterval", param0, 1);
      int _oldVal = this._PollInterval;
      this._PollInterval = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getMaxOldAppVersions() {
      return this._MaxOldAppVersions;
   }

   public boolean isMaxOldAppVersionsInherited() {
      return false;
   }

   public boolean isMaxOldAppVersionsSet() {
      return this._isSet(12);
   }

   public void setMaxOldAppVersions(int param0) {
      LegalChecks.checkMin("MaxOldAppVersions", param0, 1);
      int _oldVal = this._MaxOldAppVersions;
      this._MaxOldAppVersions = param0;
      this._postSet(12, _oldVal, param0);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._Dir = "config/";
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxOldAppVersions = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 11:
               this._PollInterval = 900;
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
      return "DeploymentConfigOverrides";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Dir")) {
         String oldVal = this._Dir;
         this._Dir = (String)v;
         this._postSet(10, oldVal, this._Dir);
      } else {
         int oldVal;
         if (name.equals("MaxOldAppVersions")) {
            oldVal = this._MaxOldAppVersions;
            this._MaxOldAppVersions = (Integer)v;
            this._postSet(12, oldVal, this._MaxOldAppVersions);
         } else if (name.equals("PollInterval")) {
            oldVal = this._PollInterval;
            this._PollInterval = (Integer)v;
            this._postSet(11, oldVal, this._PollInterval);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("Dir")) {
         return this._Dir;
      } else if (name.equals("MaxOldAppVersions")) {
         return new Integer(this._MaxOldAppVersions);
      } else {
         return name.equals("PollInterval") ? new Integer(this._PollInterval) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("dir")) {
                  return 10;
               }
               break;
            case 13:
               if (s.equals("poll-interval")) {
                  return 11;
               }
               break;
            case 20:
               if (s.equals("max-old-app-versions")) {
                  return 12;
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
            case 10:
               return "dir";
            case 11:
               return "poll-interval";
            case 12:
               return "max-old-app-versions";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
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
      private DeploymentConfigOverridesMBeanImpl bean;

      protected Helper(DeploymentConfigOverridesMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Dir";
            case 11:
               return "PollInterval";
            case 12:
               return "MaxOldAppVersions";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Dir")) {
            return 10;
         } else if (propName.equals("MaxOldAppVersions")) {
            return 12;
         } else {
            return propName.equals("PollInterval") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isDirSet()) {
               buf.append("Dir");
               buf.append(String.valueOf(this.bean.getDir()));
            }

            if (this.bean.isMaxOldAppVersionsSet()) {
               buf.append("MaxOldAppVersions");
               buf.append(String.valueOf(this.bean.getMaxOldAppVersions()));
            }

            if (this.bean.isPollIntervalSet()) {
               buf.append("PollInterval");
               buf.append(String.valueOf(this.bean.getPollInterval()));
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
            DeploymentConfigOverridesMBeanImpl otherTyped = (DeploymentConfigOverridesMBeanImpl)other;
            this.computeDiff("Dir", this.bean.getDir(), otherTyped.getDir(), true);
            this.computeDiff("MaxOldAppVersions", this.bean.getMaxOldAppVersions(), otherTyped.getMaxOldAppVersions(), true);
            this.computeDiff("PollInterval", this.bean.getPollInterval(), otherTyped.getPollInterval(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DeploymentConfigOverridesMBeanImpl original = (DeploymentConfigOverridesMBeanImpl)event.getSourceBean();
            DeploymentConfigOverridesMBeanImpl proposed = (DeploymentConfigOverridesMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Dir")) {
                  original.setDir(proposed.getDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MaxOldAppVersions")) {
                  original.setMaxOldAppVersions(proposed.getMaxOldAppVersions());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("PollInterval")) {
                  original.setPollInterval(proposed.getPollInterval());
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
            DeploymentConfigOverridesMBeanImpl copy = (DeploymentConfigOverridesMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Dir")) && this.bean.isDirSet()) {
               copy.setDir(this.bean.getDir());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxOldAppVersions")) && this.bean.isMaxOldAppVersionsSet()) {
               copy.setMaxOldAppVersions(this.bean.getMaxOldAppVersions());
            }

            if ((excludeProps == null || !excludeProps.contains("PollInterval")) && this.bean.isPollIntervalSet()) {
               copy.setPollInterval(this.bean.getPollInterval());
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
