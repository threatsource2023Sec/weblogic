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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class AppDeploymentBeanImpl extends AbstractDescriptorBean implements AppDeploymentBean, Serializable {
   private String _GeneratedVersion;
   private String _Name;
   private String _RetireTimeout;
   private String _SourcePath;
   private static SchemaHelper2 _schemaHelper;

   public AppDeploymentBeanImpl() {
      this._initializeProperty(-1);
   }

   public AppDeploymentBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AppDeploymentBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getSourcePath() {
      return this._SourcePath;
   }

   public boolean isSourcePathInherited() {
      return false;
   }

   public boolean isSourcePathSet() {
      return this._isSet(1);
   }

   public void setSourcePath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SourcePath;
      this._SourcePath = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getRetireTimeout() {
      return this._RetireTimeout;
   }

   public boolean isRetireTimeoutInherited() {
      return false;
   }

   public boolean isRetireTimeoutSet() {
      return this._isSet(2);
   }

   public void setRetireTimeout(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RetireTimeout;
      this._RetireTimeout = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getGeneratedVersion() {
      return this._GeneratedVersion;
   }

   public boolean isGeneratedVersionInherited() {
      return false;
   }

   public boolean isGeneratedVersionSet() {
      return this._isSet(3);
   }

   public void setGeneratedVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._GeneratedVersion;
      this._GeneratedVersion = param0;
      this._postSet(3, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
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
               this._GeneratedVersion = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._RetireTimeout = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._SourcePath = null;
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
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("source-path")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("retire-timeout")) {
                  return 2;
               }
               break;
            case 17:
               if (s.equals("generated-version")) {
                  return 3;
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
            case 0:
               return "name";
            case 1:
               return "source-path";
            case 2:
               return "retire-timeout";
            case 3:
               return "generated-version";
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AppDeploymentBeanImpl bean;

      protected Helper(AppDeploymentBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "SourcePath";
            case 2:
               return "RetireTimeout";
            case 3:
               return "GeneratedVersion";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("GeneratedVersion")) {
            return 3;
         } else if (propName.equals("Name")) {
            return 0;
         } else if (propName.equals("RetireTimeout")) {
            return 2;
         } else {
            return propName.equals("SourcePath") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isGeneratedVersionSet()) {
               buf.append("GeneratedVersion");
               buf.append(String.valueOf(this.bean.getGeneratedVersion()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isRetireTimeoutSet()) {
               buf.append("RetireTimeout");
               buf.append(String.valueOf(this.bean.getRetireTimeout()));
            }

            if (this.bean.isSourcePathSet()) {
               buf.append("SourcePath");
               buf.append(String.valueOf(this.bean.getSourcePath()));
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
            AppDeploymentBeanImpl otherTyped = (AppDeploymentBeanImpl)other;
            this.computeDiff("GeneratedVersion", this.bean.getGeneratedVersion(), otherTyped.getGeneratedVersion(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("RetireTimeout", this.bean.getRetireTimeout(), otherTyped.getRetireTimeout(), false);
            this.computeDiff("SourcePath", this.bean.getSourcePath(), otherTyped.getSourcePath(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AppDeploymentBeanImpl original = (AppDeploymentBeanImpl)event.getSourceBean();
            AppDeploymentBeanImpl proposed = (AppDeploymentBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("GeneratedVersion")) {
                  original.setGeneratedVersion(proposed.getGeneratedVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("RetireTimeout")) {
                  original.setRetireTimeout(proposed.getRetireTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SourcePath")) {
                  original.setSourcePath(proposed.getSourcePath());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            AppDeploymentBeanImpl copy = (AppDeploymentBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("GeneratedVersion")) && this.bean.isGeneratedVersionSet()) {
               copy.setGeneratedVersion(this.bean.getGeneratedVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("RetireTimeout")) && this.bean.isRetireTimeoutSet()) {
               copy.setRetireTimeout(this.bean.getRetireTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("SourcePath")) && this.bean.isSourcePathSet()) {
               copy.setSourcePath(this.bean.getSourcePath());
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
