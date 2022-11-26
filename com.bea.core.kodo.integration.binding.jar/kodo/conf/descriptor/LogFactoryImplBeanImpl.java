package kodo.conf.descriptor;

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

public class LogFactoryImplBeanImpl extends LogBeanImpl implements LogFactoryImplBean, Serializable {
   private String _DefaultLevel;
   private String _DiagnosticContext;
   private String _File;
   private static SchemaHelper2 _schemaHelper;

   public LogFactoryImplBeanImpl() {
      this._initializeProperty(-1);
   }

   public LogFactoryImplBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LogFactoryImplBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDiagnosticContext() {
      return this._DiagnosticContext;
   }

   public boolean isDiagnosticContextInherited() {
      return false;
   }

   public boolean isDiagnosticContextSet() {
      return this._isSet(0);
   }

   public void setDiagnosticContext(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DiagnosticContext;
      this._DiagnosticContext = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDefaultLevel() {
      return this._DefaultLevel;
   }

   public boolean isDefaultLevelInherited() {
      return false;
   }

   public boolean isDefaultLevelSet() {
      return this._isSet(1);
   }

   public void setDefaultLevel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DefaultLevel;
      this._DefaultLevel = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getFile() {
      return this._File;
   }

   public boolean isFileInherited() {
      return false;
   }

   public boolean isFileSet() {
      return this._isSet(2);
   }

   public void setFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._File;
      this._File = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._DefaultLevel = "3";
               if (initOne) {
                  break;
               }
            case 0:
               this._DiagnosticContext = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._File = null;
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

   public static class SchemaHelper2 extends LogBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("file")) {
                  return 2;
               }
               break;
            case 13:
               if (s.equals("default-level")) {
                  return 1;
               }
               break;
            case 18:
               if (s.equals("diagnostic-context")) {
                  return 0;
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
               return "diagnostic-context";
            case 1:
               return "default-level";
            case 2:
               return "file";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends LogBeanImpl.Helper {
      private LogFactoryImplBeanImpl bean;

      protected Helper(LogFactoryImplBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DiagnosticContext";
            case 1:
               return "DefaultLevel";
            case 2:
               return "File";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DefaultLevel")) {
            return 1;
         } else if (propName.equals("DiagnosticContext")) {
            return 0;
         } else {
            return propName.equals("File") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isDefaultLevelSet()) {
               buf.append("DefaultLevel");
               buf.append(String.valueOf(this.bean.getDefaultLevel()));
            }

            if (this.bean.isDiagnosticContextSet()) {
               buf.append("DiagnosticContext");
               buf.append(String.valueOf(this.bean.getDiagnosticContext()));
            }

            if (this.bean.isFileSet()) {
               buf.append("File");
               buf.append(String.valueOf(this.bean.getFile()));
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
            LogFactoryImplBeanImpl otherTyped = (LogFactoryImplBeanImpl)other;
            this.computeDiff("DefaultLevel", this.bean.getDefaultLevel(), otherTyped.getDefaultLevel(), false);
            this.computeDiff("DiagnosticContext", this.bean.getDiagnosticContext(), otherTyped.getDiagnosticContext(), false);
            this.computeDiff("File", this.bean.getFile(), otherTyped.getFile(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LogFactoryImplBeanImpl original = (LogFactoryImplBeanImpl)event.getSourceBean();
            LogFactoryImplBeanImpl proposed = (LogFactoryImplBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DefaultLevel")) {
                  original.setDefaultLevel(proposed.getDefaultLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DiagnosticContext")) {
                  original.setDiagnosticContext(proposed.getDiagnosticContext());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("File")) {
                  original.setFile(proposed.getFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            LogFactoryImplBeanImpl copy = (LogFactoryImplBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DefaultLevel")) && this.bean.isDefaultLevelSet()) {
               copy.setDefaultLevel(this.bean.getDefaultLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticContext")) && this.bean.isDiagnosticContextSet()) {
               copy.setDiagnosticContext(this.bean.getDiagnosticContext());
            }

            if ((excludeProps == null || !excludeProps.contains("File")) && this.bean.isFileSet()) {
               copy.setFile(this.bean.getFile());
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
