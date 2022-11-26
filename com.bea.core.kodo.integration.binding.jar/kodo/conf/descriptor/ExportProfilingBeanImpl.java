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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ExportProfilingBeanImpl extends ProfilingBeanImpl implements ExportProfilingBean, Serializable {
   private String _BaseName;
   private int _IntervalMillis;
   private static SchemaHelper2 _schemaHelper;

   public ExportProfilingBeanImpl() {
      this._initializeProperty(-1);
   }

   public ExportProfilingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ExportProfilingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getIntervalMillis() {
      return this._IntervalMillis;
   }

   public boolean isIntervalMillisInherited() {
      return false;
   }

   public boolean isIntervalMillisSet() {
      return this._isSet(4);
   }

   public void setIntervalMillis(int param0) {
      int _oldVal = this._IntervalMillis;
      this._IntervalMillis = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getBaseName() {
      return this._BaseName;
   }

   public boolean isBaseNameInherited() {
      return false;
   }

   public boolean isBaseNameSet() {
      return this._isSet(5);
   }

   public void setBaseName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BaseName;
      this._BaseName = param0;
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._BaseName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._IntervalMillis = 0;
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

   public static class SchemaHelper2 extends ProfilingBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("base-name")) {
                  return 5;
               }
               break;
            case 15:
               if (s.equals("interval-millis")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new NoneProfilingBeanImpl.SchemaHelper2();
            case 1:
               return new LocalProfilingBeanImpl.SchemaHelper2();
            case 2:
               return new SchemaHelper2();
            case 3:
               return new GUIProfilingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "interval-millis";
            case 5:
               return "base-name";
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
            case 3:
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
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends ProfilingBeanImpl.Helper {
      private ExportProfilingBeanImpl bean;

      protected Helper(ExportProfilingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "IntervalMillis";
            case 5:
               return "BaseName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BaseName")) {
            return 5;
         } else {
            return propName.equals("IntervalMillis") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getExportProfiling() != null) {
            iterators.add(new ArrayIterator(new ExportProfilingBean[]{this.bean.getExportProfiling()}));
         }

         if (this.bean.getGUIProfiling() != null) {
            iterators.add(new ArrayIterator(new GUIProfilingBean[]{this.bean.getGUIProfiling()}));
         }

         if (this.bean.getLocalProfiling() != null) {
            iterators.add(new ArrayIterator(new LocalProfilingBean[]{this.bean.getLocalProfiling()}));
         }

         if (this.bean.getNoneProfiling() != null) {
            iterators.add(new ArrayIterator(new NoneProfilingBean[]{this.bean.getNoneProfiling()}));
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
            if (this.bean.isBaseNameSet()) {
               buf.append("BaseName");
               buf.append(String.valueOf(this.bean.getBaseName()));
            }

            if (this.bean.isIntervalMillisSet()) {
               buf.append("IntervalMillis");
               buf.append(String.valueOf(this.bean.getIntervalMillis()));
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
            ExportProfilingBeanImpl otherTyped = (ExportProfilingBeanImpl)other;
            this.computeDiff("BaseName", this.bean.getBaseName(), otherTyped.getBaseName(), false);
            this.computeDiff("IntervalMillis", this.bean.getIntervalMillis(), otherTyped.getIntervalMillis(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ExportProfilingBeanImpl original = (ExportProfilingBeanImpl)event.getSourceBean();
            ExportProfilingBeanImpl proposed = (ExportProfilingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BaseName")) {
                  original.setBaseName(proposed.getBaseName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("IntervalMillis")) {
                  original.setIntervalMillis(proposed.getIntervalMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            ExportProfilingBeanImpl copy = (ExportProfilingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BaseName")) && this.bean.isBaseNameSet()) {
               copy.setBaseName(this.bean.getBaseName());
            }

            if ((excludeProps == null || !excludeProps.contains("IntervalMillis")) && this.bean.isIntervalMillisSet()) {
               copy.setIntervalMillis(this.bean.getIntervalMillis());
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
