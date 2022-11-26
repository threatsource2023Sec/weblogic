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

public class ProxyBeanImpl extends AbstractDescriptorBean implements ProxyBean, Serializable {
   private boolean _ConnectionProfilingEnabled;
   private int _InactiveConnectionTimeoutSeconds;
   private String _UseConnectionProxies;
   private static SchemaHelper2 _schemaHelper;

   public ProxyBeanImpl() {
      this._initializeProperty(-1);
   }

   public ProxyBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ProxyBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getInactiveConnectionTimeoutSeconds() {
      return this._InactiveConnectionTimeoutSeconds;
   }

   public boolean isInactiveConnectionTimeoutSecondsInherited() {
      return false;
   }

   public boolean isInactiveConnectionTimeoutSecondsSet() {
      return this._isSet(0);
   }

   public void setInactiveConnectionTimeoutSeconds(int param0) {
      int _oldVal = this._InactiveConnectionTimeoutSeconds;
      this._InactiveConnectionTimeoutSeconds = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isConnectionProfilingEnabled() {
      return this._ConnectionProfilingEnabled;
   }

   public boolean isConnectionProfilingEnabledInherited() {
      return false;
   }

   public boolean isConnectionProfilingEnabledSet() {
      return this._isSet(1);
   }

   public void setConnectionProfilingEnabled(boolean param0) {
      boolean _oldVal = this._ConnectionProfilingEnabled;
      this._ConnectionProfilingEnabled = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getUseConnectionProxies() {
      return this._UseConnectionProxies;
   }

   public boolean isUseConnectionProxiesInherited() {
      return false;
   }

   public boolean isUseConnectionProxiesSet() {
      return this._isSet(2);
   }

   public void setUseConnectionProxies(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UseConnectionProxies;
      this._UseConnectionProxies = param0;
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
               this._InactiveConnectionTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 2:
               this._UseConnectionProxies = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ConnectionProfilingEnabled = false;
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
            case 22:
               if (s.equals("use-connection-proxies")) {
                  return 2;
               }
               break;
            case 28:
               if (s.equals("connection-profiling-enabled")) {
                  return 1;
               }
               break;
            case 35:
               if (s.equals("inactive-connection-timeout-seconds")) {
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
               return "inactive-connection-timeout-seconds";
            case 1:
               return "connection-profiling-enabled";
            case 2:
               return "use-connection-proxies";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ProxyBeanImpl bean;

      protected Helper(ProxyBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InactiveConnectionTimeoutSeconds";
            case 1:
               return "ConnectionProfilingEnabled";
            case 2:
               return "UseConnectionProxies";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("InactiveConnectionTimeoutSeconds")) {
            return 0;
         } else if (propName.equals("UseConnectionProxies")) {
            return 2;
         } else {
            return propName.equals("ConnectionProfilingEnabled") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isInactiveConnectionTimeoutSecondsSet()) {
               buf.append("InactiveConnectionTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getInactiveConnectionTimeoutSeconds()));
            }

            if (this.bean.isUseConnectionProxiesSet()) {
               buf.append("UseConnectionProxies");
               buf.append(String.valueOf(this.bean.getUseConnectionProxies()));
            }

            if (this.bean.isConnectionProfilingEnabledSet()) {
               buf.append("ConnectionProfilingEnabled");
               buf.append(String.valueOf(this.bean.isConnectionProfilingEnabled()));
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
            ProxyBeanImpl otherTyped = (ProxyBeanImpl)other;
            this.computeDiff("InactiveConnectionTimeoutSeconds", this.bean.getInactiveConnectionTimeoutSeconds(), otherTyped.getInactiveConnectionTimeoutSeconds(), false);
            this.computeDiff("UseConnectionProxies", this.bean.getUseConnectionProxies(), otherTyped.getUseConnectionProxies(), false);
            this.computeDiff("ConnectionProfilingEnabled", this.bean.isConnectionProfilingEnabled(), otherTyped.isConnectionProfilingEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ProxyBeanImpl original = (ProxyBeanImpl)event.getSourceBean();
            ProxyBeanImpl proposed = (ProxyBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("InactiveConnectionTimeoutSeconds")) {
                  original.setInactiveConnectionTimeoutSeconds(proposed.getInactiveConnectionTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("UseConnectionProxies")) {
                  original.setUseConnectionProxies(proposed.getUseConnectionProxies());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ConnectionProfilingEnabled")) {
                  original.setConnectionProfilingEnabled(proposed.isConnectionProfilingEnabled());
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
            ProxyBeanImpl copy = (ProxyBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("InactiveConnectionTimeoutSeconds")) && this.bean.isInactiveConnectionTimeoutSecondsSet()) {
               copy.setInactiveConnectionTimeoutSeconds(this.bean.getInactiveConnectionTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("UseConnectionProxies")) && this.bean.isUseConnectionProxiesSet()) {
               copy.setUseConnectionProxies(this.bean.getUseConnectionProxies());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionProfilingEnabled")) && this.bean.isConnectionProfilingEnabledSet()) {
               copy.setConnectionProfilingEnabled(this.bean.isConnectionProfilingEnabled());
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
