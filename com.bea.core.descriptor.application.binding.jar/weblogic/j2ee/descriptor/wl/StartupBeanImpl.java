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

public class StartupBeanImpl extends AbstractDescriptorBean implements StartupBean, Serializable {
   private String _StartupClass;
   private String _StartupUri;
   private static SchemaHelper2 _schemaHelper;

   public StartupBeanImpl() {
      this._initializeProperty(-1);
   }

   public StartupBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public StartupBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getStartupClass() {
      return this._StartupClass;
   }

   public boolean isStartupClassInherited() {
      return false;
   }

   public boolean isStartupClassSet() {
      return this._isSet(0);
   }

   public void setStartupClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._StartupClass;
      this._StartupClass = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getStartupUri() {
      return this._StartupUri;
   }

   public boolean isStartupUriInherited() {
      return false;
   }

   public boolean isStartupUriSet() {
      return this._isSet(1);
   }

   public void setStartupUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._StartupUri;
      this._StartupUri = param0;
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
               this._StartupClass = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._StartupUri = null;
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
            case 11:
               if (s.equals("startup-uri")) {
                  return 1;
               }
               break;
            case 13:
               if (s.equals("startup-class")) {
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
               return "startup-class";
            case 1:
               return "startup-uri";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private StartupBeanImpl bean;

      protected Helper(StartupBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "StartupClass";
            case 1:
               return "StartupUri";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("StartupClass")) {
            return 0;
         } else {
            return propName.equals("StartupUri") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isStartupClassSet()) {
               buf.append("StartupClass");
               buf.append(String.valueOf(this.bean.getStartupClass()));
            }

            if (this.bean.isStartupUriSet()) {
               buf.append("StartupUri");
               buf.append(String.valueOf(this.bean.getStartupUri()));
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
            StartupBeanImpl otherTyped = (StartupBeanImpl)other;
            this.computeDiff("StartupClass", this.bean.getStartupClass(), otherTyped.getStartupClass(), false);
            this.computeDiff("StartupUri", this.bean.getStartupUri(), otherTyped.getStartupUri(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            StartupBeanImpl original = (StartupBeanImpl)event.getSourceBean();
            StartupBeanImpl proposed = (StartupBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("StartupClass")) {
                  original.setStartupClass(proposed.getStartupClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("StartupUri")) {
                  original.setStartupUri(proposed.getStartupUri());
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
            StartupBeanImpl copy = (StartupBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("StartupClass")) && this.bean.isStartupClassSet()) {
               copy.setStartupClass(this.bean.getStartupClass());
            }

            if ((excludeProps == null || !excludeProps.contains("StartupUri")) && this.bean.isStartupUriSet()) {
               copy.setStartupUri(this.bean.getStartupUri());
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
