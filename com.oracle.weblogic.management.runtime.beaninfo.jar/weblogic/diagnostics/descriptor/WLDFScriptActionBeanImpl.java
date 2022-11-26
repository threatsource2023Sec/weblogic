package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WLDFScriptActionBeanImpl extends WLDFNotificationBeanImpl implements WLDFScriptActionBean, Serializable {
   private Properties _Environment;
   private String[] _Parameters;
   private String _PathToScript;
   private String _WorkingDirectory;
   private static SchemaHelper2 _schemaHelper;

   public WLDFScriptActionBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFScriptActionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFScriptActionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getWorkingDirectory() {
      return this._WorkingDirectory;
   }

   public boolean isWorkingDirectoryInherited() {
      return false;
   }

   public boolean isWorkingDirectorySet() {
      return this._isSet(4);
   }

   public void setWorkingDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WorkingDirectory;
      this._WorkingDirectory = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getPathToScript() {
      return this._PathToScript;
   }

   public boolean isPathToScriptInherited() {
      return false;
   }

   public boolean isPathToScriptSet() {
      return this._isSet(5);
   }

   public void setPathToScript(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("PathToScript", param0);
      LegalChecks.checkNonNull("PathToScript", param0);
      String _oldVal = this._PathToScript;
      this._PathToScript = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Properties getEnvironment() {
      return this._Environment;
   }

   public String getEnvironmentAsString() {
      return StringHelper.objectToString(this.getEnvironment());
   }

   public boolean isEnvironmentInherited() {
      return false;
   }

   public boolean isEnvironmentSet() {
      return this._isSet(6);
   }

   public void setEnvironmentAsString(String param0) {
      try {
         this.setEnvironment(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setEnvironment(Properties param0) {
      Properties _oldVal = this._Environment;
      this._Environment = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String[] getParameters() {
      return this._Parameters;
   }

   public boolean isParametersInherited() {
      return false;
   }

   public boolean isParametersSet() {
      return this._isSet(7);
   }

   public void setParameters(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Parameters;
      this._Parameters = param0;
      this._postSet(7, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("PathToScript", this.isPathToScriptSet());
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
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._Environment = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Parameters = new String[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._PathToScript = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._WorkingDirectory = null;
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
            case 9:
               if (s.equals("parameter")) {
                  return 7;
               }
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            default:
               break;
            case 11:
               if (s.equals("environment")) {
                  return 6;
               }
               break;
            case 14:
               if (s.equals("path-to-script")) {
                  return 5;
               }
               break;
            case 17:
               if (s.equals("working-directory")) {
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
            case 4:
               return "working-directory";
            case 5:
               return "path-to-script";
            case 6:
               return "environment";
            case 7:
               return "parameter";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            default:
               return super.isArray(propIndex);
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
      private WLDFScriptActionBeanImpl bean;

      protected Helper(WLDFScriptActionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "WorkingDirectory";
            case 5:
               return "PathToScript";
            case 6:
               return "Environment";
            case 7:
               return "Parameters";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Environment")) {
            return 6;
         } else if (propName.equals("Parameters")) {
            return 7;
         } else if (propName.equals("PathToScript")) {
            return 5;
         } else {
            return propName.equals("WorkingDirectory") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isEnvironmentSet()) {
               buf.append("Environment");
               buf.append(String.valueOf(this.bean.getEnvironment()));
            }

            if (this.bean.isParametersSet()) {
               buf.append("Parameters");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getParameters())));
            }

            if (this.bean.isPathToScriptSet()) {
               buf.append("PathToScript");
               buf.append(String.valueOf(this.bean.getPathToScript()));
            }

            if (this.bean.isWorkingDirectorySet()) {
               buf.append("WorkingDirectory");
               buf.append(String.valueOf(this.bean.getWorkingDirectory()));
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
            WLDFScriptActionBeanImpl otherTyped = (WLDFScriptActionBeanImpl)other;
            this.computeDiff("Environment", this.bean.getEnvironment(), otherTyped.getEnvironment(), true);
            this.computeDiff("Parameters", this.bean.getParameters(), otherTyped.getParameters(), true);
            this.computeDiff("PathToScript", this.bean.getPathToScript(), otherTyped.getPathToScript(), true);
            this.computeDiff("WorkingDirectory", this.bean.getWorkingDirectory(), otherTyped.getWorkingDirectory(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFScriptActionBeanImpl original = (WLDFScriptActionBeanImpl)event.getSourceBean();
            WLDFScriptActionBeanImpl proposed = (WLDFScriptActionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Environment")) {
                  original.setEnvironment(proposed.getEnvironment() == null ? null : (Properties)proposed.getEnvironment().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Parameters")) {
                  original.setParameters(proposed.getParameters());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("PathToScript")) {
                  original.setPathToScript(proposed.getPathToScript());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("WorkingDirectory")) {
                  original.setWorkingDirectory(proposed.getWorkingDirectory());
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
            WLDFScriptActionBeanImpl copy = (WLDFScriptActionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Environment")) && this.bean.isEnvironmentSet()) {
               copy.setEnvironment(this.bean.getEnvironment());
            }

            if ((excludeProps == null || !excludeProps.contains("Parameters")) && this.bean.isParametersSet()) {
               Object o = this.bean.getParameters();
               copy.setParameters(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("PathToScript")) && this.bean.isPathToScriptSet()) {
               copy.setPathToScript(this.bean.getPathToScript());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkingDirectory")) && this.bean.isWorkingDirectorySet()) {
               copy.setWorkingDirectory(this.bean.getWorkingDirectory());
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
