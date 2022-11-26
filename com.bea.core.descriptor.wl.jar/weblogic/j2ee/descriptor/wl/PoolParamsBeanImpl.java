package weblogic.j2ee.descriptor.wl;

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
import weblogic.utils.collections.CombinedIterator;

public class PoolParamsBeanImpl extends ConnectionPoolParamsBeanImpl implements PoolParamsBean, Serializable {
   private int _ConnectionReserveTimeoutSeconds;
   private int _HighestNumWaiters;
   private boolean _MatchConnectionsSupported;
   private int _MaxCapacity;
   private boolean _UseFirstAvailable;
   private static SchemaHelper2 _schemaHelper;

   public PoolParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public PoolParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PoolParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxCapacity() {
      return this._MaxCapacity;
   }

   public boolean isMaxCapacityInherited() {
      return false;
   }

   public boolean isMaxCapacitySet() {
      return this._isSet(1);
   }

   public void setMaxCapacity(int param0) {
      int _oldVal = this._MaxCapacity;
      this._MaxCapacity = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isMatchConnectionsSupported() {
      return this._MatchConnectionsSupported;
   }

   public boolean isMatchConnectionsSupportedInherited() {
      return false;
   }

   public boolean isMatchConnectionsSupportedSet() {
      return this._isSet(15);
   }

   public void setMatchConnectionsSupported(boolean param0) {
      boolean _oldVal = this._MatchConnectionsSupported;
      this._MatchConnectionsSupported = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean isUseFirstAvailable() {
      return this._UseFirstAvailable;
   }

   public boolean isUseFirstAvailableInherited() {
      return false;
   }

   public boolean isUseFirstAvailableSet() {
      return this._isSet(16);
   }

   public void setUseFirstAvailable(boolean param0) {
      boolean _oldVal = this._UseFirstAvailable;
      this._UseFirstAvailable = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getHighestNumWaiters() {
      return this._HighestNumWaiters;
   }

   public boolean isHighestNumWaitersInherited() {
      return false;
   }

   public boolean isHighestNumWaitersSet() {
      return this._isSet(5);
   }

   public void setHighestNumWaiters(int param0) {
      int _oldVal = this._HighestNumWaiters;
      this._HighestNumWaiters = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this._ConnectionReserveTimeoutSeconds;
   }

   public boolean isConnectionReserveTimeoutSecondsInherited() {
      return false;
   }

   public boolean isConnectionReserveTimeoutSecondsSet() {
      return this._isSet(8);
   }

   public void setConnectionReserveTimeoutSeconds(int param0) {
      LegalChecks.checkInRange("ConnectionReserveTimeoutSeconds", (long)param0, -1L, 2147483647L);
      int _oldVal = this._ConnectionReserveTimeoutSeconds;
      this._ConnectionReserveTimeoutSeconds = param0;
      this._postSet(8, _oldVal, param0);
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
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._ConnectionReserveTimeoutSeconds = -1;
               if (initOne) {
                  break;
               }
            case 5:
               this._HighestNumWaiters = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._MaxCapacity = 10;
               if (initOne) {
                  break;
               }
            case 15:
               this._MatchConnectionsSupported = true;
               if (initOne) {
                  break;
               }
            case 16:
               this._UseFirstAvailable = false;
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

   public static class SchemaHelper2 extends ConnectionPoolParamsBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("max-capacity")) {
                  return 1;
               }
               break;
            case 19:
               if (s.equals("highest-num-waiters")) {
                  return 5;
               }

               if (s.equals("use-first-available")) {
                  return 16;
               }
               break;
            case 27:
               if (s.equals("match-connections-supported")) {
                  return 15;
               }
               break;
            case 34:
               if (s.equals("connection-reserve-timeout-seconds")) {
                  return 8;
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
            case 1:
               return "max-capacity";
            case 5:
               return "highest-num-waiters";
            case 8:
               return "connection-reserve-timeout-seconds";
            case 15:
               return "match-connections-supported";
            case 16:
               return "use-first-available";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 5:
               return true;
            case 8:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends ConnectionPoolParamsBeanImpl.Helper {
      private PoolParamsBeanImpl bean;

      protected Helper(PoolParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "MaxCapacity";
            case 5:
               return "HighestNumWaiters";
            case 8:
               return "ConnectionReserveTimeoutSeconds";
            case 15:
               return "MatchConnectionsSupported";
            case 16:
               return "UseFirstAvailable";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionReserveTimeoutSeconds")) {
            return 8;
         } else if (propName.equals("HighestNumWaiters")) {
            return 5;
         } else if (propName.equals("MaxCapacity")) {
            return 1;
         } else if (propName.equals("MatchConnectionsSupported")) {
            return 15;
         } else {
            return propName.equals("UseFirstAvailable") ? 16 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionReserveTimeoutSecondsSet()) {
               buf.append("ConnectionReserveTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getConnectionReserveTimeoutSeconds()));
            }

            if (this.bean.isHighestNumWaitersSet()) {
               buf.append("HighestNumWaiters");
               buf.append(String.valueOf(this.bean.getHighestNumWaiters()));
            }

            if (this.bean.isMaxCapacitySet()) {
               buf.append("MaxCapacity");
               buf.append(String.valueOf(this.bean.getMaxCapacity()));
            }

            if (this.bean.isMatchConnectionsSupportedSet()) {
               buf.append("MatchConnectionsSupported");
               buf.append(String.valueOf(this.bean.isMatchConnectionsSupported()));
            }

            if (this.bean.isUseFirstAvailableSet()) {
               buf.append("UseFirstAvailable");
               buf.append(String.valueOf(this.bean.isUseFirstAvailable()));
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
            PoolParamsBeanImpl otherTyped = (PoolParamsBeanImpl)other;
            this.computeDiff("ConnectionReserveTimeoutSeconds", this.bean.getConnectionReserveTimeoutSeconds(), otherTyped.getConnectionReserveTimeoutSeconds(), true);
            this.computeDiff("HighestNumWaiters", this.bean.getHighestNumWaiters(), otherTyped.getHighestNumWaiters(), true);
            this.computeDiff("MaxCapacity", this.bean.getMaxCapacity(), otherTyped.getMaxCapacity(), true);
            this.computeDiff("MatchConnectionsSupported", this.bean.isMatchConnectionsSupported(), otherTyped.isMatchConnectionsSupported(), true);
            this.computeDiff("UseFirstAvailable", this.bean.isUseFirstAvailable(), otherTyped.isUseFirstAvailable(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PoolParamsBeanImpl original = (PoolParamsBeanImpl)event.getSourceBean();
            PoolParamsBeanImpl proposed = (PoolParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionReserveTimeoutSeconds")) {
                  original.setConnectionReserveTimeoutSeconds(proposed.getConnectionReserveTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("HighestNumWaiters")) {
                  original.setHighestNumWaiters(proposed.getHighestNumWaiters());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("MaxCapacity")) {
                  original.setMaxCapacity(proposed.getMaxCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MatchConnectionsSupported")) {
                  original.setMatchConnectionsSupported(proposed.isMatchConnectionsSupported());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("UseFirstAvailable")) {
                  original.setUseFirstAvailable(proposed.isUseFirstAvailable());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
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
            PoolParamsBeanImpl copy = (PoolParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionReserveTimeoutSeconds")) && this.bean.isConnectionReserveTimeoutSecondsSet()) {
               copy.setConnectionReserveTimeoutSeconds(this.bean.getConnectionReserveTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("HighestNumWaiters")) && this.bean.isHighestNumWaitersSet()) {
               copy.setHighestNumWaiters(this.bean.getHighestNumWaiters());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxCapacity")) && this.bean.isMaxCapacitySet()) {
               copy.setMaxCapacity(this.bean.getMaxCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("MatchConnectionsSupported")) && this.bean.isMatchConnectionsSupportedSet()) {
               copy.setMatchConnectionsSupported(this.bean.isMatchConnectionsSupported());
            }

            if ((excludeProps == null || !excludeProps.contains("UseFirstAvailable")) && this.bean.isUseFirstAvailableSet()) {
               copy.setUseFirstAvailable(this.bean.isUseFirstAvailable());
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
