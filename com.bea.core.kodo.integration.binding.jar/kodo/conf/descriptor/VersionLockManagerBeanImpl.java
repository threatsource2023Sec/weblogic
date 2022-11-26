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

public class VersionLockManagerBeanImpl extends LockManagerBeanImpl implements VersionLockManagerBean, Serializable {
   private boolean _VersionCheckOnReadLock;
   private boolean _VersionUpdateOnWriteLock;
   private static SchemaHelper2 _schemaHelper;

   public VersionLockManagerBeanImpl() {
      this._initializeProperty(-1);
   }

   public VersionLockManagerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public VersionLockManagerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getVersionCheckOnReadLock() {
      return this._VersionCheckOnReadLock;
   }

   public boolean isVersionCheckOnReadLockInherited() {
      return false;
   }

   public boolean isVersionCheckOnReadLockSet() {
      return this._isSet(0);
   }

   public void setVersionCheckOnReadLock(boolean param0) {
      boolean _oldVal = this._VersionCheckOnReadLock;
      this._VersionCheckOnReadLock = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean getVersionUpdateOnWriteLock() {
      return this._VersionUpdateOnWriteLock;
   }

   public boolean isVersionUpdateOnWriteLockInherited() {
      return false;
   }

   public boolean isVersionUpdateOnWriteLockSet() {
      return this._isSet(1);
   }

   public void setVersionUpdateOnWriteLock(boolean param0) {
      boolean _oldVal = this._VersionUpdateOnWriteLock;
      this._VersionUpdateOnWriteLock = param0;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._VersionCheckOnReadLock = true;
               if (initOne) {
                  break;
               }
            case 1:
               this._VersionUpdateOnWriteLock = true;
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

   public static class SchemaHelper2 extends LockManagerBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 26:
               if (s.equals("version-check-on-read-lock")) {
                  return 0;
               }
               break;
            case 28:
               if (s.equals("version-update-on-write-lock")) {
                  return 1;
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
               return "version-check-on-read-lock";
            case 1:
               return "version-update-on-write-lock";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends LockManagerBeanImpl.Helper {
      private VersionLockManagerBeanImpl bean;

      protected Helper(VersionLockManagerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "VersionCheckOnReadLock";
            case 1:
               return "VersionUpdateOnWriteLock";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("VersionCheckOnReadLock")) {
            return 0;
         } else {
            return propName.equals("VersionUpdateOnWriteLock") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isVersionCheckOnReadLockSet()) {
               buf.append("VersionCheckOnReadLock");
               buf.append(String.valueOf(this.bean.getVersionCheckOnReadLock()));
            }

            if (this.bean.isVersionUpdateOnWriteLockSet()) {
               buf.append("VersionUpdateOnWriteLock");
               buf.append(String.valueOf(this.bean.getVersionUpdateOnWriteLock()));
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
            VersionLockManagerBeanImpl otherTyped = (VersionLockManagerBeanImpl)other;
            this.computeDiff("VersionCheckOnReadLock", this.bean.getVersionCheckOnReadLock(), otherTyped.getVersionCheckOnReadLock(), false);
            this.computeDiff("VersionUpdateOnWriteLock", this.bean.getVersionUpdateOnWriteLock(), otherTyped.getVersionUpdateOnWriteLock(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            VersionLockManagerBeanImpl original = (VersionLockManagerBeanImpl)event.getSourceBean();
            VersionLockManagerBeanImpl proposed = (VersionLockManagerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("VersionCheckOnReadLock")) {
                  original.setVersionCheckOnReadLock(proposed.getVersionCheckOnReadLock());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("VersionUpdateOnWriteLock")) {
                  original.setVersionUpdateOnWriteLock(proposed.getVersionUpdateOnWriteLock());
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
            VersionLockManagerBeanImpl copy = (VersionLockManagerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("VersionCheckOnReadLock")) && this.bean.isVersionCheckOnReadLockSet()) {
               copy.setVersionCheckOnReadLock(this.bean.getVersionCheckOnReadLock());
            }

            if ((excludeProps == null || !excludeProps.contains("VersionUpdateOnWriteLock")) && this.bean.isVersionUpdateOnWriteLockSet()) {
               copy.setVersionUpdateOnWriteLock(this.bean.getVersionUpdateOnWriteLock());
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
