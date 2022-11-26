package kodo.jdbc.conf.descriptor;

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

public class FileSchemaFactoryBeanImpl extends SchemaFactoryBeanImpl implements FileSchemaFactoryBean, Serializable {
   private String _File;
   private String _FileName;
   private static SchemaHelper2 _schemaHelper;

   public FileSchemaFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public FileSchemaFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public FileSchemaFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getFileName() {
      return this._FileName;
   }

   public boolean isFileNameInherited() {
      return false;
   }

   public boolean isFileNameSet() {
      return this._isSet(0);
   }

   public void setFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FileName;
      this._FileName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getFile() {
      return this._File;
   }

   public boolean isFileInherited() {
      return false;
   }

   public boolean isFileSet() {
      return this._isSet(1);
   }

   public void setFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._File;
      this._File = param0;
      this._postSet(1, _oldVal, param0);
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
               this._File = "package.schema";
               if (initOne) {
                  break;
               }
            case 0:
               this._FileName = "package.schema";
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

   public static class SchemaHelper2 extends SchemaFactoryBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("file")) {
                  return 1;
               }
               break;
            case 9:
               if (s.equals("file-name")) {
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
               return "file-name";
            case 1:
               return "file";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SchemaFactoryBeanImpl.Helper {
      private FileSchemaFactoryBeanImpl bean;

      protected Helper(FileSchemaFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "FileName";
            case 1:
               return "File";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("File")) {
            return 1;
         } else {
            return propName.equals("FileName") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isFileSet()) {
               buf.append("File");
               buf.append(String.valueOf(this.bean.getFile()));
            }

            if (this.bean.isFileNameSet()) {
               buf.append("FileName");
               buf.append(String.valueOf(this.bean.getFileName()));
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
            FileSchemaFactoryBeanImpl otherTyped = (FileSchemaFactoryBeanImpl)other;
            this.computeDiff("File", this.bean.getFile(), otherTyped.getFile(), false);
            this.computeDiff("FileName", this.bean.getFileName(), otherTyped.getFileName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FileSchemaFactoryBeanImpl original = (FileSchemaFactoryBeanImpl)event.getSourceBean();
            FileSchemaFactoryBeanImpl proposed = (FileSchemaFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("File")) {
                  original.setFile(proposed.getFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("FileName")) {
                  original.setFileName(proposed.getFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            FileSchemaFactoryBeanImpl copy = (FileSchemaFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("File")) && this.bean.isFileSet()) {
               copy.setFile(this.bean.getFile());
            }

            if ((excludeProps == null || !excludeProps.contains("FileName")) && this.bean.isFileNameSet()) {
               copy.setFileName(this.bean.getFileName());
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
