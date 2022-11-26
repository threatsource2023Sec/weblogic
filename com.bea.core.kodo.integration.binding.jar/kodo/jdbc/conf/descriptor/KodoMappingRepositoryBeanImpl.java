package kodo.jdbc.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import kodo.conf.descriptor.MetaDataRepositoryBeanImpl;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class KodoMappingRepositoryBeanImpl extends MetaDataRepositoryBeanImpl implements KodoMappingRepositoryBean, Serializable {
   private int _Resolve;
   private int _SourceMode;
   private int _Validate;
   private static SchemaHelper2 _schemaHelper;

   public KodoMappingRepositoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public KodoMappingRepositoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public KodoMappingRepositoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getResolve() {
      return this._Resolve;
   }

   public boolean isResolveInherited() {
      return false;
   }

   public boolean isResolveSet() {
      return this._isSet(0);
   }

   public void setResolve(int param0) {
      int _oldVal = this._Resolve;
      this._Resolve = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getValidate() {
      return this._Validate;
   }

   public boolean isValidateInherited() {
      return false;
   }

   public boolean isValidateSet() {
      return this._isSet(1);
   }

   public void setValidate(int param0) {
      int _oldVal = this._Validate;
      this._Validate = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getSourceMode() {
      return this._SourceMode;
   }

   public boolean isSourceModeInherited() {
      return false;
   }

   public boolean isSourceModeSet() {
      return this._isSet(2);
   }

   public void setSourceMode(int param0) {
      int _oldVal = this._SourceMode;
      this._SourceMode = param0;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._Resolve = 3;
               if (initOne) {
                  break;
               }
            case 2:
               this._SourceMode = 7;
               if (initOne) {
                  break;
               }
            case 1:
               this._Validate = 7;
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

   public static class SchemaHelper2 extends MetaDataRepositoryBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("resolve")) {
                  return 0;
               }
               break;
            case 8:
               if (s.equals("validate")) {
                  return 1;
               }
            case 9:
            case 10:
            default:
               break;
            case 11:
               if (s.equals("source-mode")) {
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
               return "resolve";
            case 1:
               return "validate";
            case 2:
               return "source-mode";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends MetaDataRepositoryBeanImpl.Helper {
      private KodoMappingRepositoryBeanImpl bean;

      protected Helper(KodoMappingRepositoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Resolve";
            case 1:
               return "Validate";
            case 2:
               return "SourceMode";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Resolve")) {
            return 0;
         } else if (propName.equals("SourceMode")) {
            return 2;
         } else {
            return propName.equals("Validate") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isResolveSet()) {
               buf.append("Resolve");
               buf.append(String.valueOf(this.bean.getResolve()));
            }

            if (this.bean.isSourceModeSet()) {
               buf.append("SourceMode");
               buf.append(String.valueOf(this.bean.getSourceMode()));
            }

            if (this.bean.isValidateSet()) {
               buf.append("Validate");
               buf.append(String.valueOf(this.bean.getValidate()));
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
            KodoMappingRepositoryBeanImpl otherTyped = (KodoMappingRepositoryBeanImpl)other;
            this.computeDiff("Resolve", this.bean.getResolve(), otherTyped.getResolve(), false);
            this.computeDiff("SourceMode", this.bean.getSourceMode(), otherTyped.getSourceMode(), false);
            this.computeDiff("Validate", this.bean.getValidate(), otherTyped.getValidate(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            KodoMappingRepositoryBeanImpl original = (KodoMappingRepositoryBeanImpl)event.getSourceBean();
            KodoMappingRepositoryBeanImpl proposed = (KodoMappingRepositoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Resolve")) {
                  original.setResolve(proposed.getResolve());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SourceMode")) {
                  original.setSourceMode(proposed.getSourceMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Validate")) {
                  original.setValidate(proposed.getValidate());
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
            KodoMappingRepositoryBeanImpl copy = (KodoMappingRepositoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Resolve")) && this.bean.isResolveSet()) {
               copy.setResolve(this.bean.getResolve());
            }

            if ((excludeProps == null || !excludeProps.contains("SourceMode")) && this.bean.isSourceModeSet()) {
               copy.setSourceMode(this.bean.getSourceMode());
            }

            if ((excludeProps == null || !excludeProps.contains("Validate")) && this.bean.isValidateSet()) {
               copy.setValidate(this.bean.getValidate());
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
