package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class DefaultPersistentStoreBeanImpl extends SettableBeanImpl implements DefaultPersistentStoreBean, Serializable {
   private String _DirectoryPath;
   private String _Notes;
   private String _SynchronousWritePolicy;
   private static SchemaHelper2 _schemaHelper;

   public DefaultPersistentStoreBeanImpl() {
      this._initializeProperty(-1);
   }

   public DefaultPersistentStoreBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DefaultPersistentStoreBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getNotes() {
      return this._Notes;
   }

   public boolean isNotesInherited() {
      return false;
   }

   public boolean isNotesSet() {
      return this._isSet(0);
   }

   public void setNotes(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Notes;
      this._Notes = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDirectoryPath() {
      return this._DirectoryPath;
   }

   public boolean isDirectoryPathInherited() {
      return false;
   }

   public boolean isDirectoryPathSet() {
      return this._isSet(1);
   }

   public void setDirectoryPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DirectoryPath;
      this._DirectoryPath = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getSynchronousWritePolicy() {
      return this._SynchronousWritePolicy;
   }

   public boolean isSynchronousWritePolicyInherited() {
      return false;
   }

   public boolean isSynchronousWritePolicySet() {
      return this._isSet(2);
   }

   public void setSynchronousWritePolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Disabled", "Cache-Flush", "Direct-Write"};
      param0 = LegalChecks.checkInEnum("SynchronousWritePolicy", param0, _set);
      String _oldVal = this._SynchronousWritePolicy;
      this._SynchronousWritePolicy = param0;
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
               this._DirectoryPath = "store/default";
               if (initOne) {
                  break;
               }
            case 0:
               this._Notes = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._SynchronousWritePolicy = "Direct-Write";
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("notes")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("directory-path")) {
                  return 1;
               }
               break;
            case 24:
               if (s.equals("synchronous-write-policy")) {
                  return 2;
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
               return "notes";
            case 1:
               return "directory-path";
            case 2:
               return "synchronous-write-policy";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private DefaultPersistentStoreBeanImpl bean;

      protected Helper(DefaultPersistentStoreBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Notes";
            case 1:
               return "DirectoryPath";
            case 2:
               return "SynchronousWritePolicy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DirectoryPath")) {
            return 1;
         } else if (propName.equals("Notes")) {
            return 0;
         } else {
            return propName.equals("SynchronousWritePolicy") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isDirectoryPathSet()) {
               buf.append("DirectoryPath");
               buf.append(String.valueOf(this.bean.getDirectoryPath()));
            }

            if (this.bean.isNotesSet()) {
               buf.append("Notes");
               buf.append(String.valueOf(this.bean.getNotes()));
            }

            if (this.bean.isSynchronousWritePolicySet()) {
               buf.append("SynchronousWritePolicy");
               buf.append(String.valueOf(this.bean.getSynchronousWritePolicy()));
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
            DefaultPersistentStoreBeanImpl otherTyped = (DefaultPersistentStoreBeanImpl)other;
            this.computeDiff("DirectoryPath", this.bean.getDirectoryPath(), otherTyped.getDirectoryPath(), false);
            this.computeDiff("Notes", this.bean.getNotes(), otherTyped.getNotes(), true);
            this.computeDiff("SynchronousWritePolicy", this.bean.getSynchronousWritePolicy(), otherTyped.getSynchronousWritePolicy(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DefaultPersistentStoreBeanImpl original = (DefaultPersistentStoreBeanImpl)event.getSourceBean();
            DefaultPersistentStoreBeanImpl proposed = (DefaultPersistentStoreBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DirectoryPath")) {
                  original.setDirectoryPath(proposed.getDirectoryPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Notes")) {
                  original.setNotes(proposed.getNotes());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SynchronousWritePolicy")) {
                  original.setSynchronousWritePolicy(proposed.getSynchronousWritePolicy());
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
            DefaultPersistentStoreBeanImpl copy = (DefaultPersistentStoreBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DirectoryPath")) && this.bean.isDirectoryPathSet()) {
               copy.setDirectoryPath(this.bean.getDirectoryPath());
            }

            if ((excludeProps == null || !excludeProps.contains("Notes")) && this.bean.isNotesSet()) {
               copy.setNotes(this.bean.getNotes());
            }

            if ((excludeProps == null || !excludeProps.contains("SynchronousWritePolicy")) && this.bean.isSynchronousWritePolicySet()) {
               copy.setSynchronousWritePolicy(this.bean.getSynchronousWritePolicy());
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
