package weblogic.coherence.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceClusterWellKnownAddressBeanImpl extends SettableBeanImpl implements CoherenceClusterWellKnownAddressBean, Serializable {
   private String _ListenAddress;
   private int _ListenPort;
   private String _Name;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceClusterWellKnownAddressBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceClusterWellKnownAddressBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceClusterWellKnownAddressBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getListenAddress() {
      return this._ListenAddress;
   }

   public boolean isListenAddressInherited() {
      return false;
   }

   public boolean isListenAddressSet() {
      return this._isSet(1);
   }

   public void setListenAddress(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ListenAddress;
      this._ListenAddress = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getListenPort() {
      return this._ListenPort;
   }

   public boolean isListenPortInherited() {
      return false;
   }

   public boolean isListenPortSet() {
      return this._isSet(2);
   }

   public void setListenPort(int param0) {
      LegalChecks.checkInRange("ListenPort", (long)param0, 1L, 65535L);
      int _oldVal = this._ListenPort;
      this._ListenPort = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      CoherenceClusterValidator.validateWKAMember(this);
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._ListenAddress = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ListenPort = 0;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("listen-port")) {
                  return 2;
               }
               break;
            case 14:
               if (s.equals("listen-address")) {
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
               return "name";
            case 1:
               return "listen-address";
            case 2:
               return "listen-port";
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
            case 2:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private CoherenceClusterWellKnownAddressBeanImpl bean;

      protected Helper(CoherenceClusterWellKnownAddressBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "ListenAddress";
            case 2:
               return "ListenPort";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ListenAddress")) {
            return 1;
         } else if (propName.equals("ListenPort")) {
            return 2;
         } else {
            return propName.equals("Name") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isListenAddressSet()) {
               buf.append("ListenAddress");
               buf.append(String.valueOf(this.bean.getListenAddress()));
            }

            if (this.bean.isListenPortSet()) {
               buf.append("ListenPort");
               buf.append(String.valueOf(this.bean.getListenPort()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
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
            CoherenceClusterWellKnownAddressBeanImpl otherTyped = (CoherenceClusterWellKnownAddressBeanImpl)other;
            this.computeDiff("ListenAddress", this.bean.getListenAddress(), otherTyped.getListenAddress(), false);
            this.computeDiff("ListenPort", this.bean.getListenPort(), otherTyped.getListenPort(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceClusterWellKnownAddressBeanImpl original = (CoherenceClusterWellKnownAddressBeanImpl)event.getSourceBean();
            CoherenceClusterWellKnownAddressBeanImpl proposed = (CoherenceClusterWellKnownAddressBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ListenAddress")) {
                  original.setListenAddress(proposed.getListenAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ListenPort")) {
                  original.setListenPort(proposed.getListenPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            CoherenceClusterWellKnownAddressBeanImpl copy = (CoherenceClusterWellKnownAddressBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ListenAddress")) && this.bean.isListenAddressSet()) {
               copy.setListenAddress(this.bean.getListenAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenPort")) && this.bean.isListenPortSet()) {
               copy.setListenPort(this.bean.getListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
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
