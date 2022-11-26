package weblogic.j2ee.descriptor.wl;

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

public class LibraryContextRootOverrideBeanImpl extends AbstractDescriptorBean implements LibraryContextRootOverrideBean, Serializable {
   private String _ContextRoot;
   private String _OverrideValue;
   private static SchemaHelper2 _schemaHelper;

   public LibraryContextRootOverrideBeanImpl() {
      this._initializeProperty(-1);
   }

   public LibraryContextRootOverrideBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LibraryContextRootOverrideBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getContextRoot() {
      return this._ContextRoot;
   }

   public boolean isContextRootInherited() {
      return false;
   }

   public boolean isContextRootSet() {
      return this._isSet(0);
   }

   public void setContextRoot(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ContextRoot;
      this._ContextRoot = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getOverrideValue() {
      return this._OverrideValue;
   }

   public boolean isOverrideValueInherited() {
      return false;
   }

   public boolean isOverrideValueSet() {
      return this._isSet(1);
   }

   public void setOverrideValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OverrideValue;
      this._OverrideValue = param0;
      this._postSet(1, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getContextRoot();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 12:
            if (s.equals("context-root")) {
               return info.compareXpaths(this._getPropertyXpath("context-root"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
               this._ContextRoot = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._OverrideValue = null;
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
            case 12:
               if (s.equals("context-root")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("override-value")) {
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
               return "context-root";
            case 1:
               return "override-value";
            default:
               return super.getElementName(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("context-root");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private LibraryContextRootOverrideBeanImpl bean;

      protected Helper(LibraryContextRootOverrideBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ContextRoot";
            case 1:
               return "OverrideValue";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ContextRoot")) {
            return 0;
         } else {
            return propName.equals("OverrideValue") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isContextRootSet()) {
               buf.append("ContextRoot");
               buf.append(String.valueOf(this.bean.getContextRoot()));
            }

            if (this.bean.isOverrideValueSet()) {
               buf.append("OverrideValue");
               buf.append(String.valueOf(this.bean.getOverrideValue()));
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
            LibraryContextRootOverrideBeanImpl otherTyped = (LibraryContextRootOverrideBeanImpl)other;
            this.computeDiff("ContextRoot", this.bean.getContextRoot(), otherTyped.getContextRoot(), false);
            this.computeDiff("OverrideValue", this.bean.getOverrideValue(), otherTyped.getOverrideValue(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LibraryContextRootOverrideBeanImpl original = (LibraryContextRootOverrideBeanImpl)event.getSourceBean();
            LibraryContextRootOverrideBeanImpl proposed = (LibraryContextRootOverrideBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ContextRoot")) {
                  original.setContextRoot(proposed.getContextRoot());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("OverrideValue")) {
                  original.setOverrideValue(proposed.getOverrideValue());
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
            LibraryContextRootOverrideBeanImpl copy = (LibraryContextRootOverrideBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ContextRoot")) && this.bean.isContextRootSet()) {
               copy.setContextRoot(this.bean.getContextRoot());
            }

            if ((excludeProps == null || !excludeProps.contains("OverrideValue")) && this.bean.isOverrideValueSet()) {
               copy.setOverrideValue(this.bean.getOverrideValue());
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
