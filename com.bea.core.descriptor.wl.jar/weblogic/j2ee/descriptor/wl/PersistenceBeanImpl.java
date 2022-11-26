package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class PersistenceBeanImpl extends AbstractDescriptorBean implements PersistenceBean, Serializable {
   private boolean _DelayUpdatesUntilEndOfTx;
   private boolean _FindersLoadBean;
   private String _Id;
   private String _IsModifiedMethodName;
   private PersistenceUseBean _PersistenceUse;
   private static SchemaHelper2 _schemaHelper;

   public PersistenceBeanImpl() {
      this._initializeProperty(-1);
   }

   public PersistenceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PersistenceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getIsModifiedMethodName() {
      return this._IsModifiedMethodName;
   }

   public boolean isIsModifiedMethodNameInherited() {
      return false;
   }

   public boolean isIsModifiedMethodNameSet() {
      return this._isSet(0);
   }

   public void setIsModifiedMethodName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._IsModifiedMethodName;
      this._IsModifiedMethodName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isDelayUpdatesUntilEndOfTx() {
      return this._DelayUpdatesUntilEndOfTx;
   }

   public boolean isDelayUpdatesUntilEndOfTxInherited() {
      return false;
   }

   public boolean isDelayUpdatesUntilEndOfTxSet() {
      return this._isSet(1);
   }

   public void setDelayUpdatesUntilEndOfTx(boolean param0) {
      boolean _oldVal = this._DelayUpdatesUntilEndOfTx;
      this._DelayUpdatesUntilEndOfTx = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isFindersLoadBean() {
      return this._FindersLoadBean;
   }

   public boolean isFindersLoadBeanInherited() {
      return false;
   }

   public boolean isFindersLoadBeanSet() {
      return this._isSet(2);
   }

   public void setFindersLoadBean(boolean param0) {
      boolean _oldVal = this._FindersLoadBean;
      this._FindersLoadBean = param0;
      this._postSet(2, _oldVal, param0);
   }

   public PersistenceUseBean getPersistenceUse() {
      return this._PersistenceUse;
   }

   public boolean isPersistenceUseInherited() {
      return false;
   }

   public boolean isPersistenceUseSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getPersistenceUse());
   }

   public void setPersistenceUse(PersistenceUseBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      PersistenceUseBean _oldVal = this._PersistenceUse;
      this._PersistenceUse = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
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
      return super._isAnythingSet() || this.isPersistenceUseSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._IsModifiedMethodName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._PersistenceUse = new PersistenceUseBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._PersistenceUse);
               if (initOne) {
                  break;
               }
            case 1:
               this._DelayUpdatesUntilEndOfTx = true;
               if (initOne) {
                  break;
               }
            case 2:
               this._FindersLoadBean = true;
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
            case 2:
               if (s.equals("id")) {
                  return 4;
               }
               break;
            case 15:
               if (s.equals("persistence-use")) {
                  return 3;
               }
               break;
            case 17:
               if (s.equals("finders-load-bean")) {
                  return 2;
               }
               break;
            case 23:
               if (s.equals("is-modified-method-name")) {
                  return 0;
               }
               break;
            case 29:
               if (s.equals("delay-updates-until-end-of-tx")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new PersistenceUseBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "is-modified-method-name";
            case 1:
               return "delay-updates-until-end-of-tx";
            case 2:
               return "finders-load-bean";
            case 3:
               return "persistence-use";
            case 4:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PersistenceBeanImpl bean;

      protected Helper(PersistenceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "IsModifiedMethodName";
            case 1:
               return "DelayUpdatesUntilEndOfTx";
            case 2:
               return "FindersLoadBean";
            case 3:
               return "PersistenceUse";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("IsModifiedMethodName")) {
            return 0;
         } else if (propName.equals("PersistenceUse")) {
            return 3;
         } else if (propName.equals("DelayUpdatesUntilEndOfTx")) {
            return 1;
         } else {
            return propName.equals("FindersLoadBean") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getPersistenceUse() != null) {
            iterators.add(new ArrayIterator(new PersistenceUseBean[]{this.bean.getPersistenceUse()}));
         }

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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isIsModifiedMethodNameSet()) {
               buf.append("IsModifiedMethodName");
               buf.append(String.valueOf(this.bean.getIsModifiedMethodName()));
            }

            childValue = this.computeChildHashValue(this.bean.getPersistenceUse());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDelayUpdatesUntilEndOfTxSet()) {
               buf.append("DelayUpdatesUntilEndOfTx");
               buf.append(String.valueOf(this.bean.isDelayUpdatesUntilEndOfTx()));
            }

            if (this.bean.isFindersLoadBeanSet()) {
               buf.append("FindersLoadBean");
               buf.append(String.valueOf(this.bean.isFindersLoadBean()));
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
            PersistenceBeanImpl otherTyped = (PersistenceBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("IsModifiedMethodName", this.bean.getIsModifiedMethodName(), otherTyped.getIsModifiedMethodName(), false);
            this.computeSubDiff("PersistenceUse", this.bean.getPersistenceUse(), otherTyped.getPersistenceUse());
            this.computeDiff("DelayUpdatesUntilEndOfTx", this.bean.isDelayUpdatesUntilEndOfTx(), otherTyped.isDelayUpdatesUntilEndOfTx(), false);
            this.computeDiff("FindersLoadBean", this.bean.isFindersLoadBean(), otherTyped.isFindersLoadBean(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PersistenceBeanImpl original = (PersistenceBeanImpl)event.getSourceBean();
            PersistenceBeanImpl proposed = (PersistenceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("IsModifiedMethodName")) {
                  original.setIsModifiedMethodName(proposed.getIsModifiedMethodName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("PersistenceUse")) {
                  if (type == 2) {
                     original.setPersistenceUse((PersistenceUseBean)this.createCopy((AbstractDescriptorBean)proposed.getPersistenceUse()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PersistenceUse", (DescriptorBean)original.getPersistenceUse());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("DelayUpdatesUntilEndOfTx")) {
                  original.setDelayUpdatesUntilEndOfTx(proposed.isDelayUpdatesUntilEndOfTx());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("FindersLoadBean")) {
                  original.setFindersLoadBean(proposed.isFindersLoadBean());
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
            PersistenceBeanImpl copy = (PersistenceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("IsModifiedMethodName")) && this.bean.isIsModifiedMethodNameSet()) {
               copy.setIsModifiedMethodName(this.bean.getIsModifiedMethodName());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceUse")) && this.bean.isPersistenceUseSet() && !copy._isSet(3)) {
               Object o = this.bean.getPersistenceUse();
               copy.setPersistenceUse((PersistenceUseBean)null);
               copy.setPersistenceUse(o == null ? null : (PersistenceUseBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DelayUpdatesUntilEndOfTx")) && this.bean.isDelayUpdatesUntilEndOfTxSet()) {
               copy.setDelayUpdatesUntilEndOfTx(this.bean.isDelayUpdatesUntilEndOfTx());
            }

            if ((excludeProps == null || !excludeProps.contains("FindersLoadBean")) && this.bean.isFindersLoadBeanSet()) {
               copy.setFindersLoadBean(this.bean.isFindersLoadBean());
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
         this.inferSubTree(this.bean.getPersistenceUse(), clazz, annotation);
      }
   }
}
