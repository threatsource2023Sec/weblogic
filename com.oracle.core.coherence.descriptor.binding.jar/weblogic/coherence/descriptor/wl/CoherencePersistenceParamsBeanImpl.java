package weblogic.coherence.descriptor.wl;

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

public class CoherencePersistenceParamsBeanImpl extends SettableBeanImpl implements CoherencePersistenceParamsBean, Serializable {
   private String _ActiveDirectory;
   private String _DefaultPersistenceMode;
   private String _SnapshotDirectory;
   private String _TrashDirectory;
   private static SchemaHelper2 _schemaHelper;

   public CoherencePersistenceParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherencePersistenceParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherencePersistenceParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDefaultPersistenceMode() {
      return this._DefaultPersistenceMode;
   }

   public boolean isDefaultPersistenceModeInherited() {
      return false;
   }

   public boolean isDefaultPersistenceModeSet() {
      return this._isSet(0);
   }

   public void setDefaultPersistenceMode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"on-demand", "active"};
      param0 = LegalChecks.checkInEnum("DefaultPersistenceMode", param0, _set);
      String _oldVal = this._DefaultPersistenceMode;
      this._DefaultPersistenceMode = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getActiveDirectory() {
      return this._ActiveDirectory;
   }

   public boolean isActiveDirectoryInherited() {
      return false;
   }

   public boolean isActiveDirectorySet() {
      return this._isSet(1);
   }

   public void setActiveDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ActiveDirectory;
      this._ActiveDirectory = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getSnapshotDirectory() {
      return this._SnapshotDirectory;
   }

   public boolean isSnapshotDirectoryInherited() {
      return false;
   }

   public boolean isSnapshotDirectorySet() {
      return this._isSet(2);
   }

   public void setSnapshotDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SnapshotDirectory;
      this._SnapshotDirectory = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getTrashDirectory() {
      return this._TrashDirectory;
   }

   public boolean isTrashDirectoryInherited() {
      return false;
   }

   public boolean isTrashDirectorySet() {
      return this._isSet(3);
   }

   public void setTrashDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TrashDirectory;
      this._TrashDirectory = param0;
      this._postSet(3, _oldVal, param0);
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
               this._ActiveDirectory = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._DefaultPersistenceMode = "on-demand";
               if (initOne) {
                  break;
               }
            case 2:
               this._SnapshotDirectory = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._TrashDirectory = null;
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
            case 15:
               if (s.equals("trash-directory")) {
                  return 3;
               }
               break;
            case 16:
               if (s.equals("active-directory")) {
                  return 1;
               }
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
               break;
            case 18:
               if (s.equals("snapshot-directory")) {
                  return 2;
               }
               break;
            case 24:
               if (s.equals("default-persistence-mode")) {
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
               return "default-persistence-mode";
            case 1:
               return "active-directory";
            case 2:
               return "snapshot-directory";
            case 3:
               return "trash-directory";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private CoherencePersistenceParamsBeanImpl bean;

      protected Helper(CoherencePersistenceParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DefaultPersistenceMode";
            case 1:
               return "ActiveDirectory";
            case 2:
               return "SnapshotDirectory";
            case 3:
               return "TrashDirectory";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActiveDirectory")) {
            return 1;
         } else if (propName.equals("DefaultPersistenceMode")) {
            return 0;
         } else if (propName.equals("SnapshotDirectory")) {
            return 2;
         } else {
            return propName.equals("TrashDirectory") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isActiveDirectorySet()) {
               buf.append("ActiveDirectory");
               buf.append(String.valueOf(this.bean.getActiveDirectory()));
            }

            if (this.bean.isDefaultPersistenceModeSet()) {
               buf.append("DefaultPersistenceMode");
               buf.append(String.valueOf(this.bean.getDefaultPersistenceMode()));
            }

            if (this.bean.isSnapshotDirectorySet()) {
               buf.append("SnapshotDirectory");
               buf.append(String.valueOf(this.bean.getSnapshotDirectory()));
            }

            if (this.bean.isTrashDirectorySet()) {
               buf.append("TrashDirectory");
               buf.append(String.valueOf(this.bean.getTrashDirectory()));
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
            CoherencePersistenceParamsBeanImpl otherTyped = (CoherencePersistenceParamsBeanImpl)other;
            this.computeDiff("ActiveDirectory", this.bean.getActiveDirectory(), otherTyped.getActiveDirectory(), false);
            this.computeDiff("DefaultPersistenceMode", this.bean.getDefaultPersistenceMode(), otherTyped.getDefaultPersistenceMode(), false);
            this.computeDiff("SnapshotDirectory", this.bean.getSnapshotDirectory(), otherTyped.getSnapshotDirectory(), false);
            this.computeDiff("TrashDirectory", this.bean.getTrashDirectory(), otherTyped.getTrashDirectory(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherencePersistenceParamsBeanImpl original = (CoherencePersistenceParamsBeanImpl)event.getSourceBean();
            CoherencePersistenceParamsBeanImpl proposed = (CoherencePersistenceParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ActiveDirectory")) {
                  original.setActiveDirectory(proposed.getActiveDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DefaultPersistenceMode")) {
                  original.setDefaultPersistenceMode(proposed.getDefaultPersistenceMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SnapshotDirectory")) {
                  original.setSnapshotDirectory(proposed.getSnapshotDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TrashDirectory")) {
                  original.setTrashDirectory(proposed.getTrashDirectory());
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
            CoherencePersistenceParamsBeanImpl copy = (CoherencePersistenceParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ActiveDirectory")) && this.bean.isActiveDirectorySet()) {
               copy.setActiveDirectory(this.bean.getActiveDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultPersistenceMode")) && this.bean.isDefaultPersistenceModeSet()) {
               copy.setDefaultPersistenceMode(this.bean.getDefaultPersistenceMode());
            }

            if ((excludeProps == null || !excludeProps.contains("SnapshotDirectory")) && this.bean.isSnapshotDirectorySet()) {
               copy.setSnapshotDirectory(this.bean.getSnapshotDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("TrashDirectory")) && this.bean.isTrashDirectorySet()) {
               copy.setTrashDirectory(this.bean.getTrashDirectory());
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
