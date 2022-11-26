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

public class ShutdownBeanImpl extends AbstractDescriptorBean implements ShutdownBean, Serializable {
   private String _ShutdownClass;
   private String _ShutdownUri;
   private static SchemaHelper2 _schemaHelper;

   public ShutdownBeanImpl() {
      this._initializeProperty(-1);
   }

   public ShutdownBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ShutdownBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getShutdownClass() {
      return this._ShutdownClass;
   }

   public boolean isShutdownClassInherited() {
      return false;
   }

   public boolean isShutdownClassSet() {
      return this._isSet(0);
   }

   public void setShutdownClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ShutdownClass;
      this._ShutdownClass = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getShutdownUri() {
      return this._ShutdownUri;
   }

   public boolean isShutdownUriInherited() {
      return false;
   }

   public boolean isShutdownUriSet() {
      return this._isSet(1);
   }

   public void setShutdownUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ShutdownUri;
      this._ShutdownUri = param0;
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
               this._ShutdownClass = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ShutdownUri = null;
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
               if (s.equals("shutdown-uri")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("shutdown-class")) {
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
               return "shutdown-class";
            case 1:
               return "shutdown-uri";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ShutdownBeanImpl bean;

      protected Helper(ShutdownBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ShutdownClass";
            case 1:
               return "ShutdownUri";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ShutdownClass")) {
            return 0;
         } else {
            return propName.equals("ShutdownUri") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isShutdownClassSet()) {
               buf.append("ShutdownClass");
               buf.append(String.valueOf(this.bean.getShutdownClass()));
            }

            if (this.bean.isShutdownUriSet()) {
               buf.append("ShutdownUri");
               buf.append(String.valueOf(this.bean.getShutdownUri()));
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
            ShutdownBeanImpl otherTyped = (ShutdownBeanImpl)other;
            this.computeDiff("ShutdownClass", this.bean.getShutdownClass(), otherTyped.getShutdownClass(), false);
            this.computeDiff("ShutdownUri", this.bean.getShutdownUri(), otherTyped.getShutdownUri(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ShutdownBeanImpl original = (ShutdownBeanImpl)event.getSourceBean();
            ShutdownBeanImpl proposed = (ShutdownBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ShutdownClass")) {
                  original.setShutdownClass(proposed.getShutdownClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ShutdownUri")) {
                  original.setShutdownUri(proposed.getShutdownUri());
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
            ShutdownBeanImpl copy = (ShutdownBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ShutdownClass")) && this.bean.isShutdownClassSet()) {
               copy.setShutdownClass(this.bean.getShutdownClass());
            }

            if ((excludeProps == null || !excludeProps.contains("ShutdownUri")) && this.bean.isShutdownUriSet()) {
               copy.setShutdownUri(this.bean.getShutdownUri());
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
