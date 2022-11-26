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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class KodoCompatibilityBeanImpl extends AbstractDescriptorBean implements KodoCompatibilityBean, Serializable {
   private boolean _CloseOnManagedCommit;
   private boolean _CopyObjectIds;
   private boolean _QuotedNumbersInQueries;
   private boolean _StrictIdentityValues;
   private boolean _ValidateFalseReturnsHollow;
   private boolean _ValidateTrueChecksStore;
   private static SchemaHelper2 _schemaHelper;

   public KodoCompatibilityBeanImpl() {
      this._initializeProperty(-1);
   }

   public KodoCompatibilityBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public KodoCompatibilityBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getCopyObjectIds() {
      return this._CopyObjectIds;
   }

   public boolean isCopyObjectIdsInherited() {
      return false;
   }

   public boolean isCopyObjectIdsSet() {
      return this._isSet(0);
   }

   public void setCopyObjectIds(boolean param0) {
      boolean _oldVal = this._CopyObjectIds;
      this._CopyObjectIds = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean getCloseOnManagedCommit() {
      return this._CloseOnManagedCommit;
   }

   public boolean isCloseOnManagedCommitInherited() {
      return false;
   }

   public boolean isCloseOnManagedCommitSet() {
      return this._isSet(1);
   }

   public void setCloseOnManagedCommit(boolean param0) {
      boolean _oldVal = this._CloseOnManagedCommit;
      this._CloseOnManagedCommit = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean getValidateTrueChecksStore() {
      return this._ValidateTrueChecksStore;
   }

   public boolean isValidateTrueChecksStoreInherited() {
      return false;
   }

   public boolean isValidateTrueChecksStoreSet() {
      return this._isSet(2);
   }

   public void setValidateTrueChecksStore(boolean param0) {
      boolean _oldVal = this._ValidateTrueChecksStore;
      this._ValidateTrueChecksStore = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean getValidateFalseReturnsHollow() {
      return this._ValidateFalseReturnsHollow;
   }

   public boolean isValidateFalseReturnsHollowInherited() {
      return false;
   }

   public boolean isValidateFalseReturnsHollowSet() {
      return this._isSet(3);
   }

   public void setValidateFalseReturnsHollow(boolean param0) {
      boolean _oldVal = this._ValidateFalseReturnsHollow;
      this._ValidateFalseReturnsHollow = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean getStrictIdentityValues() {
      return this._StrictIdentityValues;
   }

   public boolean isStrictIdentityValuesInherited() {
      return false;
   }

   public boolean isStrictIdentityValuesSet() {
      return this._isSet(4);
   }

   public void setStrictIdentityValues(boolean param0) {
      boolean _oldVal = this._StrictIdentityValues;
      this._StrictIdentityValues = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean getQuotedNumbersInQueries() {
      return this._QuotedNumbersInQueries;
   }

   public boolean isQuotedNumbersInQueriesInherited() {
      return false;
   }

   public boolean isQuotedNumbersInQueriesSet() {
      return this._isSet(5);
   }

   public void setQuotedNumbersInQueries(boolean param0) {
      boolean _oldVal = this._QuotedNumbersInQueries;
      this._QuotedNumbersInQueries = param0;
      this._postSet(5, _oldVal, param0);
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
               this._CloseOnManagedCommit = true;
               if (initOne) {
                  break;
               }
            case 0:
               this._CopyObjectIds = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._QuotedNumbersInQueries = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._StrictIdentityValues = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._ValidateFalseReturnsHollow = true;
               if (initOne) {
                  break;
               }
            case 2:
               this._ValidateTrueChecksStore = false;
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
            case 15:
               if (s.equals("copy-object-ids")) {
                  return 0;
               }
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
            case 27:
            case 28:
            default:
               break;
            case 22:
               if (s.equals("strict-identity-values")) {
                  return 4;
               }
               break;
            case 23:
               if (s.equals("close-on-managed-commit")) {
                  return 1;
               }
               break;
            case 25:
               if (s.equals("quoted-numbers-in-queries")) {
                  return 5;
               }
               break;
            case 26:
               if (s.equals("validate-true-checks-store")) {
                  return 2;
               }
               break;
            case 29:
               if (s.equals("validate-false-returns-hollow")) {
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
               return "copy-object-ids";
            case 1:
               return "close-on-managed-commit";
            case 2:
               return "validate-true-checks-store";
            case 3:
               return "validate-false-returns-hollow";
            case 4:
               return "strict-identity-values";
            case 5:
               return "quoted-numbers-in-queries";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private KodoCompatibilityBeanImpl bean;

      protected Helper(KodoCompatibilityBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "CopyObjectIds";
            case 1:
               return "CloseOnManagedCommit";
            case 2:
               return "ValidateTrueChecksStore";
            case 3:
               return "ValidateFalseReturnsHollow";
            case 4:
               return "StrictIdentityValues";
            case 5:
               return "QuotedNumbersInQueries";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CloseOnManagedCommit")) {
            return 1;
         } else if (propName.equals("CopyObjectIds")) {
            return 0;
         } else if (propName.equals("QuotedNumbersInQueries")) {
            return 5;
         } else if (propName.equals("StrictIdentityValues")) {
            return 4;
         } else if (propName.equals("ValidateFalseReturnsHollow")) {
            return 3;
         } else {
            return propName.equals("ValidateTrueChecksStore") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isCloseOnManagedCommitSet()) {
               buf.append("CloseOnManagedCommit");
               buf.append(String.valueOf(this.bean.getCloseOnManagedCommit()));
            }

            if (this.bean.isCopyObjectIdsSet()) {
               buf.append("CopyObjectIds");
               buf.append(String.valueOf(this.bean.getCopyObjectIds()));
            }

            if (this.bean.isQuotedNumbersInQueriesSet()) {
               buf.append("QuotedNumbersInQueries");
               buf.append(String.valueOf(this.bean.getQuotedNumbersInQueries()));
            }

            if (this.bean.isStrictIdentityValuesSet()) {
               buf.append("StrictIdentityValues");
               buf.append(String.valueOf(this.bean.getStrictIdentityValues()));
            }

            if (this.bean.isValidateFalseReturnsHollowSet()) {
               buf.append("ValidateFalseReturnsHollow");
               buf.append(String.valueOf(this.bean.getValidateFalseReturnsHollow()));
            }

            if (this.bean.isValidateTrueChecksStoreSet()) {
               buf.append("ValidateTrueChecksStore");
               buf.append(String.valueOf(this.bean.getValidateTrueChecksStore()));
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
            KodoCompatibilityBeanImpl otherTyped = (KodoCompatibilityBeanImpl)other;
            this.computeDiff("CloseOnManagedCommit", this.bean.getCloseOnManagedCommit(), otherTyped.getCloseOnManagedCommit(), false);
            this.computeDiff("CopyObjectIds", this.bean.getCopyObjectIds(), otherTyped.getCopyObjectIds(), false);
            this.computeDiff("QuotedNumbersInQueries", this.bean.getQuotedNumbersInQueries(), otherTyped.getQuotedNumbersInQueries(), false);
            this.computeDiff("StrictIdentityValues", this.bean.getStrictIdentityValues(), otherTyped.getStrictIdentityValues(), false);
            this.computeDiff("ValidateFalseReturnsHollow", this.bean.getValidateFalseReturnsHollow(), otherTyped.getValidateFalseReturnsHollow(), false);
            this.computeDiff("ValidateTrueChecksStore", this.bean.getValidateTrueChecksStore(), otherTyped.getValidateTrueChecksStore(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            KodoCompatibilityBeanImpl original = (KodoCompatibilityBeanImpl)event.getSourceBean();
            KodoCompatibilityBeanImpl proposed = (KodoCompatibilityBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CloseOnManagedCommit")) {
                  original.setCloseOnManagedCommit(proposed.getCloseOnManagedCommit());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("CopyObjectIds")) {
                  original.setCopyObjectIds(proposed.getCopyObjectIds());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("QuotedNumbersInQueries")) {
                  original.setQuotedNumbersInQueries(proposed.getQuotedNumbersInQueries());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("StrictIdentityValues")) {
                  original.setStrictIdentityValues(proposed.getStrictIdentityValues());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ValidateFalseReturnsHollow")) {
                  original.setValidateFalseReturnsHollow(proposed.getValidateFalseReturnsHollow());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ValidateTrueChecksStore")) {
                  original.setValidateTrueChecksStore(proposed.getValidateTrueChecksStore());
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
            KodoCompatibilityBeanImpl copy = (KodoCompatibilityBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CloseOnManagedCommit")) && this.bean.isCloseOnManagedCommitSet()) {
               copy.setCloseOnManagedCommit(this.bean.getCloseOnManagedCommit());
            }

            if ((excludeProps == null || !excludeProps.contains("CopyObjectIds")) && this.bean.isCopyObjectIdsSet()) {
               copy.setCopyObjectIds(this.bean.getCopyObjectIds());
            }

            if ((excludeProps == null || !excludeProps.contains("QuotedNumbersInQueries")) && this.bean.isQuotedNumbersInQueriesSet()) {
               copy.setQuotedNumbersInQueries(this.bean.getQuotedNumbersInQueries());
            }

            if ((excludeProps == null || !excludeProps.contains("StrictIdentityValues")) && this.bean.isStrictIdentityValuesSet()) {
               copy.setStrictIdentityValues(this.bean.getStrictIdentityValues());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidateFalseReturnsHollow")) && this.bean.isValidateFalseReturnsHollowSet()) {
               copy.setValidateFalseReturnsHollow(this.bean.getValidateFalseReturnsHollow());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidateTrueChecksStore")) && this.bean.isValidateTrueChecksStoreSet()) {
               copy.setValidateTrueChecksStore(this.bean.getValidateTrueChecksStore());
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
