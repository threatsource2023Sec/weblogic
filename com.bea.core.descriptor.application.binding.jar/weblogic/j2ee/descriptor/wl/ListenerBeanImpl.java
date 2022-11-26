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

public class ListenerBeanImpl extends AbstractDescriptorBean implements ListenerBean, Serializable {
   private String _ListenerClass;
   private String _ListenerUri;
   private String _RunAsPrincipalName;
   private static SchemaHelper2 _schemaHelper;

   public ListenerBeanImpl() {
      this._initializeProperty(-1);
   }

   public ListenerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ListenerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getListenerClass() {
      return this._ListenerClass;
   }

   public boolean isListenerClassInherited() {
      return false;
   }

   public boolean isListenerClassSet() {
      return this._isSet(0);
   }

   public void setListenerClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ListenerClass;
      this._ListenerClass = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getListenerUri() {
      return this._ListenerUri;
   }

   public boolean isListenerUriInherited() {
      return false;
   }

   public boolean isListenerUriSet() {
      return this._isSet(1);
   }

   public void setListenerUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ListenerUri;
      this._ListenerUri = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getRunAsPrincipalName() {
      return this._RunAsPrincipalName;
   }

   public boolean isRunAsPrincipalNameInherited() {
      return false;
   }

   public boolean isRunAsPrincipalNameSet() {
      return this._isSet(2);
   }

   public void setRunAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RunAsPrincipalName;
      this._RunAsPrincipalName = param0;
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
               this._ListenerClass = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ListenerUri = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._RunAsPrincipalName = null;
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
               if (s.equals("listener-uri")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("listener-class")) {
                  return 0;
               }
               break;
            case 21:
               if (s.equals("run-as-principal-name")) {
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
               return "listener-class";
            case 1:
               return "listener-uri";
            case 2:
               return "run-as-principal-name";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ListenerBeanImpl bean;

      protected Helper(ListenerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ListenerClass";
            case 1:
               return "ListenerUri";
            case 2:
               return "RunAsPrincipalName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ListenerClass")) {
            return 0;
         } else if (propName.equals("ListenerUri")) {
            return 1;
         } else {
            return propName.equals("RunAsPrincipalName") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isListenerClassSet()) {
               buf.append("ListenerClass");
               buf.append(String.valueOf(this.bean.getListenerClass()));
            }

            if (this.bean.isListenerUriSet()) {
               buf.append("ListenerUri");
               buf.append(String.valueOf(this.bean.getListenerUri()));
            }

            if (this.bean.isRunAsPrincipalNameSet()) {
               buf.append("RunAsPrincipalName");
               buf.append(String.valueOf(this.bean.getRunAsPrincipalName()));
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
            ListenerBeanImpl otherTyped = (ListenerBeanImpl)other;
            this.computeDiff("ListenerClass", this.bean.getListenerClass(), otherTyped.getListenerClass(), false);
            this.computeDiff("ListenerUri", this.bean.getListenerUri(), otherTyped.getListenerUri(), false);
            this.computeDiff("RunAsPrincipalName", this.bean.getRunAsPrincipalName(), otherTyped.getRunAsPrincipalName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ListenerBeanImpl original = (ListenerBeanImpl)event.getSourceBean();
            ListenerBeanImpl proposed = (ListenerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ListenerClass")) {
                  original.setListenerClass(proposed.getListenerClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ListenerUri")) {
                  original.setListenerUri(proposed.getListenerUri());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("RunAsPrincipalName")) {
                  original.setRunAsPrincipalName(proposed.getRunAsPrincipalName());
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
            ListenerBeanImpl copy = (ListenerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ListenerClass")) && this.bean.isListenerClassSet()) {
               copy.setListenerClass(this.bean.getListenerClass());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenerUri")) && this.bean.isListenerUriSet()) {
               copy.setListenerUri(this.bean.getListenerUri());
            }

            if ((excludeProps == null || !excludeProps.contains("RunAsPrincipalName")) && this.bean.isRunAsPrincipalNameSet()) {
               copy.setRunAsPrincipalName(this.bean.getRunAsPrincipalName());
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
