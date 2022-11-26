package kodo.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class StackExecutionContextNameProviderBeanImpl extends ExecutionContextNameProviderBeanImpl implements StackExecutionContextNameProviderBean, Serializable {
   private String _Style;
   private static SchemaHelper2 _schemaHelper;

   public StackExecutionContextNameProviderBeanImpl() {
      this._initializeProperty(-1);
   }

   public StackExecutionContextNameProviderBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public StackExecutionContextNameProviderBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getStyle() {
      return this._Style;
   }

   public boolean isStyleInherited() {
      return false;
   }

   public boolean isStyleSet() {
      return this._isSet(3);
   }

   public void setStyle(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"line", "partial", "full"};
      param0 = LegalChecks.checkInEnum("Style", param0, _set);
      String _oldVal = this._Style;
      this._Style = param0;
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Style = "line";
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

   public static class SchemaHelper2 extends ExecutionContextNameProviderBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("style")) {
                  return 3;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new SchemaHelper2();
            case 1:
               return new TransactionNameExecutionContextNameProviderBeanImpl.SchemaHelper2();
            case 2:
               return new UserObjectExecutionContextNameProviderBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "style";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
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

   protected static class Helper extends ExecutionContextNameProviderBeanImpl.Helper {
      private StackExecutionContextNameProviderBeanImpl bean;

      protected Helper(StackExecutionContextNameProviderBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "Style";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("Style") ? 3 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getStackExecutionContextNameProvider() != null) {
            iterators.add(new ArrayIterator(new StackExecutionContextNameProviderBean[]{this.bean.getStackExecutionContextNameProvider()}));
         }

         if (this.bean.getTransactionNameExecutionContextNameProvider() != null) {
            iterators.add(new ArrayIterator(new TransactionNameExecutionContextNameProviderBean[]{this.bean.getTransactionNameExecutionContextNameProvider()}));
         }

         if (this.bean.getUserObjectExecutionContextNameProvider() != null) {
            iterators.add(new ArrayIterator(new UserObjectExecutionContextNameProviderBean[]{this.bean.getUserObjectExecutionContextNameProvider()}));
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
            if (this.bean.isStyleSet()) {
               buf.append("Style");
               buf.append(String.valueOf(this.bean.getStyle()));
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
            StackExecutionContextNameProviderBeanImpl otherTyped = (StackExecutionContextNameProviderBeanImpl)other;
            this.computeDiff("Style", this.bean.getStyle(), otherTyped.getStyle(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            StackExecutionContextNameProviderBeanImpl original = (StackExecutionContextNameProviderBeanImpl)event.getSourceBean();
            StackExecutionContextNameProviderBeanImpl proposed = (StackExecutionContextNameProviderBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Style")) {
                  original.setStyle(proposed.getStyle());
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
            StackExecutionContextNameProviderBeanImpl copy = (StackExecutionContextNameProviderBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Style")) && this.bean.isStyleSet()) {
               copy.setStyle(this.bean.getStyle());
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
