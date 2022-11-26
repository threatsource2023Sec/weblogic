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

public class WebserviceAddressBeanImpl extends AbstractDescriptorBean implements WebserviceAddressBean, Serializable {
   private String _WebserviceContextpath;
   private String _WebserviceServiceuri;
   private static SchemaHelper2 _schemaHelper;

   public WebserviceAddressBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebserviceAddressBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebserviceAddressBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getWebserviceContextpath() {
      return this._WebserviceContextpath;
   }

   public boolean isWebserviceContextpathInherited() {
      return false;
   }

   public boolean isWebserviceContextpathSet() {
      return this._isSet(0);
   }

   public void setWebserviceContextpath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WebserviceContextpath;
      this._WebserviceContextpath = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getWebserviceServiceuri() {
      return this._WebserviceServiceuri;
   }

   public boolean isWebserviceServiceuriInherited() {
      return false;
   }

   public boolean isWebserviceServiceuriSet() {
      return this._isSet(1);
   }

   public void setWebserviceServiceuri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WebserviceServiceuri;
      this._WebserviceServiceuri = param0;
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
               this._WebserviceContextpath = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WebserviceServiceuri = null;
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
               if (s.equals("webservice-serviceuri")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("webservice-contextpath")) {
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
               return "webservice-contextpath";
            case 1:
               return "webservice-serviceuri";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WebserviceAddressBeanImpl bean;

      protected Helper(WebserviceAddressBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "WebserviceContextpath";
            case 1:
               return "WebserviceServiceuri";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("WebserviceContextpath")) {
            return 0;
         } else {
            return propName.equals("WebserviceServiceuri") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isWebserviceContextpathSet()) {
               buf.append("WebserviceContextpath");
               buf.append(String.valueOf(this.bean.getWebserviceContextpath()));
            }

            if (this.bean.isWebserviceServiceuriSet()) {
               buf.append("WebserviceServiceuri");
               buf.append(String.valueOf(this.bean.getWebserviceServiceuri()));
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
            WebserviceAddressBeanImpl otherTyped = (WebserviceAddressBeanImpl)other;
            this.computeDiff("WebserviceContextpath", this.bean.getWebserviceContextpath(), otherTyped.getWebserviceContextpath(), false);
            this.computeDiff("WebserviceServiceuri", this.bean.getWebserviceServiceuri(), otherTyped.getWebserviceServiceuri(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebserviceAddressBeanImpl original = (WebserviceAddressBeanImpl)event.getSourceBean();
            WebserviceAddressBeanImpl proposed = (WebserviceAddressBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("WebserviceContextpath")) {
                  original.setWebserviceContextpath(proposed.getWebserviceContextpath());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("WebserviceServiceuri")) {
                  original.setWebserviceServiceuri(proposed.getWebserviceServiceuri());
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
            WebserviceAddressBeanImpl copy = (WebserviceAddressBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("WebserviceContextpath")) && this.bean.isWebserviceContextpathSet()) {
               copy.setWebserviceContextpath(this.bean.getWebserviceContextpath());
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceServiceuri")) && this.bean.isWebserviceServiceuriSet()) {
               copy.setWebserviceServiceuri(this.bean.getWebserviceServiceuri());
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
