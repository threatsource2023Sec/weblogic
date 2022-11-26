package weblogic.j2ee.descriptor;

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
import weblogic.descriptor.internal.CompoundKey;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class InjectionTargetBeanImpl extends AbstractDescriptorBean implements InjectionTargetBean, Serializable {
   private String _InjectionTargetClass;
   private String _InjectionTargetName;
   private static SchemaHelper2 _schemaHelper;

   public InjectionTargetBeanImpl() {
      this._initializeProperty(-1);
   }

   public InjectionTargetBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InjectionTargetBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getInjectionTargetClass() {
      return this._InjectionTargetClass;
   }

   public boolean isInjectionTargetClassInherited() {
      return false;
   }

   public boolean isInjectionTargetClassSet() {
      return this._isSet(0);
   }

   public void setInjectionTargetClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InjectionTargetClass;
      this._InjectionTargetClass = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getInjectionTargetName() {
      return this._InjectionTargetName;
   }

   public boolean isInjectionTargetNameInherited() {
      return false;
   }

   public boolean isInjectionTargetNameSet() {
      return this._isSet(1);
   }

   public void setInjectionTargetName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InjectionTargetName;
      this._InjectionTargetName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public Object _getKey() {
      return new CompoundKey(new Object[]{this.getInjectionTargetClass(), this.getInjectionTargetName()});
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
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
               this._InjectionTargetClass = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._InjectionTargetName = null;
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
            case 21:
               if (s.equals("injection-target-name")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("injection-target-class")) {
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
               return "injection-target-class";
            case 1:
               return "injection-target-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isKeyComponent(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isKeyComponent(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("injection-target-class");
         indices.add("injection-target-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private InjectionTargetBeanImpl bean;

      protected Helper(InjectionTargetBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InjectionTargetClass";
            case 1:
               return "InjectionTargetName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("InjectionTargetClass")) {
            return 0;
         } else {
            return propName.equals("InjectionTargetName") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isInjectionTargetClassSet()) {
               buf.append("InjectionTargetClass");
               buf.append(String.valueOf(this.bean.getInjectionTargetClass()));
            }

            if (this.bean.isInjectionTargetNameSet()) {
               buf.append("InjectionTargetName");
               buf.append(String.valueOf(this.bean.getInjectionTargetName()));
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
            InjectionTargetBeanImpl otherTyped = (InjectionTargetBeanImpl)other;
            this.computeDiff("InjectionTargetClass", this.bean.getInjectionTargetClass(), otherTyped.getInjectionTargetClass(), false);
            this.computeDiff("InjectionTargetName", this.bean.getInjectionTargetName(), otherTyped.getInjectionTargetName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InjectionTargetBeanImpl original = (InjectionTargetBeanImpl)event.getSourceBean();
            InjectionTargetBeanImpl proposed = (InjectionTargetBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("InjectionTargetClass")) {
                  original.setInjectionTargetClass(proposed.getInjectionTargetClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("InjectionTargetName")) {
                  original.setInjectionTargetName(proposed.getInjectionTargetName());
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
            InjectionTargetBeanImpl copy = (InjectionTargetBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("InjectionTargetClass")) && this.bean.isInjectionTargetClassSet()) {
               copy.setInjectionTargetClass(this.bean.getInjectionTargetClass());
            }

            if ((excludeProps == null || !excludeProps.contains("InjectionTargetName")) && this.bean.isInjectionTargetNameSet()) {
               copy.setInjectionTargetName(this.bean.getInjectionTargetName());
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
