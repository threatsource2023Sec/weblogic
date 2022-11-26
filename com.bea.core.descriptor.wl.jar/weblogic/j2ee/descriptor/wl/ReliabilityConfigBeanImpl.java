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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.WseeConfigBeanValidator;
import weblogic.utils.collections.CombinedIterator;

public class ReliabilityConfigBeanImpl extends AbstractDescriptorBean implements ReliabilityConfigBean, Serializable {
   private String _AcknowledgementInterval;
   private String _BaseRetransmissionInterval;
   private int _BufferRetryCount;
   private String _BufferRetryDelay;
   private boolean _Customized;
   private String _InactivityTimeout;
   private boolean _NonBufferedDestination;
   private boolean _NonBufferedSource;
   private boolean _RetransmissionExponentialBackoff;
   private String _SequenceExpiration;
   private static SchemaHelper2 _schemaHelper;

   public ReliabilityConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public ReliabilityConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ReliabilityConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isCustomized() {
      return this._Customized;
   }

   public boolean isCustomizedInherited() {
      return false;
   }

   public boolean isCustomizedSet() {
      return this._isSet(0);
   }

   public void setCustomized(boolean param0) {
      boolean _oldVal = this._Customized;
      this._Customized = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getInactivityTimeout() {
      return this._InactivityTimeout;
   }

   public boolean isInactivityTimeoutInherited() {
      return false;
   }

   public boolean isInactivityTimeoutSet() {
      return this._isSet(1);
   }

   public void setInactivityTimeout(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateInactivityTimeout(param0);
      String _oldVal = this._InactivityTimeout;
      this._InactivityTimeout = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getBaseRetransmissionInterval() {
      return this._BaseRetransmissionInterval;
   }

   public boolean isBaseRetransmissionIntervalInherited() {
      return false;
   }

   public boolean isBaseRetransmissionIntervalSet() {
      return this._isSet(2);
   }

   public void setBaseRetransmissionInterval(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateBaseRetransmissionInterval(param0);
      String _oldVal = this._BaseRetransmissionInterval;
      this._BaseRetransmissionInterval = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean getRetransmissionExponentialBackoff() {
      return this._RetransmissionExponentialBackoff;
   }

   public boolean isRetransmissionExponentialBackoffInherited() {
      return false;
   }

   public boolean isRetransmissionExponentialBackoffSet() {
      return this._isSet(3);
   }

   public void setRetransmissionExponentialBackoff(boolean param0) {
      boolean _oldVal = this._RetransmissionExponentialBackoff;
      this._RetransmissionExponentialBackoff = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean getNonBufferedSource() {
      return this._NonBufferedSource;
   }

   public boolean isNonBufferedSourceInherited() {
      return false;
   }

   public boolean isNonBufferedSourceSet() {
      return this._isSet(4);
   }

   public void setNonBufferedSource(boolean param0) {
      boolean _oldVal = this._NonBufferedSource;
      this._NonBufferedSource = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getAcknowledgementInterval() {
      return this._AcknowledgementInterval;
   }

   public boolean isAcknowledgementIntervalInherited() {
      return false;
   }

   public boolean isAcknowledgementIntervalSet() {
      return this._isSet(5);
   }

   public void setAcknowledgementInterval(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateAcknowledgementInterval(param0);
      String _oldVal = this._AcknowledgementInterval;
      this._AcknowledgementInterval = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getSequenceExpiration() {
      return this._SequenceExpiration;
   }

   public boolean isSequenceExpirationInherited() {
      return false;
   }

   public boolean isSequenceExpirationSet() {
      return this._isSet(6);
   }

   public void setSequenceExpiration(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateSequenceExpiration(param0);
      String _oldVal = this._SequenceExpiration;
      this._SequenceExpiration = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getBufferRetryCount() {
      return this._BufferRetryCount;
   }

   public boolean isBufferRetryCountInherited() {
      return false;
   }

   public boolean isBufferRetryCountSet() {
      return this._isSet(7);
   }

   public void setBufferRetryCount(int param0) {
      LegalChecks.checkMin("BufferRetryCount", param0, 0);
      int _oldVal = this._BufferRetryCount;
      this._BufferRetryCount = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getBufferRetryDelay() {
      return this._BufferRetryDelay;
   }

   public boolean isBufferRetryDelayInherited() {
      return false;
   }

   public boolean isBufferRetryDelaySet() {
      return this._isSet(8);
   }

   public void setBufferRetryDelay(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateBufferRetryDelay(param0);
      String _oldVal = this._BufferRetryDelay;
      this._BufferRetryDelay = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean getNonBufferedDestination() {
      return this._NonBufferedDestination;
   }

   public boolean isNonBufferedDestinationInherited() {
      return false;
   }

   public boolean isNonBufferedDestinationSet() {
      return this._isSet(9);
   }

   public void setNonBufferedDestination(boolean param0) {
      boolean _oldVal = this._NonBufferedDestination;
      this._NonBufferedDestination = param0;
      this._postSet(9, _oldVal, param0);
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
               this._AcknowledgementInterval = "P0DT0.2S";
               if (initOne) {
                  break;
               }
            case 2:
               this._BaseRetransmissionInterval = "P0DT8S";
               if (initOne) {
                  break;
               }
            case 7:
               this._BufferRetryCount = 3;
               if (initOne) {
                  break;
               }
            case 8:
               this._BufferRetryDelay = "P0DT5S";
               if (initOne) {
                  break;
               }
            case 1:
               this._InactivityTimeout = "P0DT600S";
               if (initOne) {
                  break;
               }
            case 9:
               this._NonBufferedDestination = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._NonBufferedSource = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._RetransmissionExponentialBackoff = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._SequenceExpiration = "P1D";
               if (initOne) {
                  break;
               }
            case 0:
               this._Customized = true;
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
            case 10:
               if (s.equals("customized")) {
                  return 0;
               }
               break;
            case 18:
               if (s.equals("buffer-retry-count")) {
                  return 7;
               }

               if (s.equals("buffer-retry-delay")) {
                  return 8;
               }

               if (s.equals("inactivity-timeout")) {
                  return 1;
               }
               break;
            case 19:
               if (s.equals("non-buffered-source")) {
                  return 4;
               }

               if (s.equals("sequence-expiration")) {
                  return 6;
               }
               break;
            case 24:
               if (s.equals("acknowledgement-interval")) {
                  return 5;
               }

               if (s.equals("non-buffered-destination")) {
                  return 9;
               }
               break;
            case 28:
               if (s.equals("base-retransmission-interval")) {
                  return 2;
               }
               break;
            case 34:
               if (s.equals("retransmission-exponential-backoff")) {
                  return 3;
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
               return "customized";
            case 1:
               return "inactivity-timeout";
            case 2:
               return "base-retransmission-interval";
            case 3:
               return "retransmission-exponential-backoff";
            case 4:
               return "non-buffered-source";
            case 5:
               return "acknowledgement-interval";
            case 6:
               return "sequence-expiration";
            case 7:
               return "buffer-retry-count";
            case 8:
               return "buffer-retry-delay";
            case 9:
               return "non-buffered-destination";
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
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ReliabilityConfigBeanImpl bean;

      protected Helper(ReliabilityConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Customized";
            case 1:
               return "InactivityTimeout";
            case 2:
               return "BaseRetransmissionInterval";
            case 3:
               return "RetransmissionExponentialBackoff";
            case 4:
               return "NonBufferedSource";
            case 5:
               return "AcknowledgementInterval";
            case 6:
               return "SequenceExpiration";
            case 7:
               return "BufferRetryCount";
            case 8:
               return "BufferRetryDelay";
            case 9:
               return "NonBufferedDestination";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AcknowledgementInterval")) {
            return 5;
         } else if (propName.equals("BaseRetransmissionInterval")) {
            return 2;
         } else if (propName.equals("BufferRetryCount")) {
            return 7;
         } else if (propName.equals("BufferRetryDelay")) {
            return 8;
         } else if (propName.equals("InactivityTimeout")) {
            return 1;
         } else if (propName.equals("NonBufferedDestination")) {
            return 9;
         } else if (propName.equals("NonBufferedSource")) {
            return 4;
         } else if (propName.equals("RetransmissionExponentialBackoff")) {
            return 3;
         } else if (propName.equals("SequenceExpiration")) {
            return 6;
         } else {
            return propName.equals("Customized") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isAcknowledgementIntervalSet()) {
               buf.append("AcknowledgementInterval");
               buf.append(String.valueOf(this.bean.getAcknowledgementInterval()));
            }

            if (this.bean.isBaseRetransmissionIntervalSet()) {
               buf.append("BaseRetransmissionInterval");
               buf.append(String.valueOf(this.bean.getBaseRetransmissionInterval()));
            }

            if (this.bean.isBufferRetryCountSet()) {
               buf.append("BufferRetryCount");
               buf.append(String.valueOf(this.bean.getBufferRetryCount()));
            }

            if (this.bean.isBufferRetryDelaySet()) {
               buf.append("BufferRetryDelay");
               buf.append(String.valueOf(this.bean.getBufferRetryDelay()));
            }

            if (this.bean.isInactivityTimeoutSet()) {
               buf.append("InactivityTimeout");
               buf.append(String.valueOf(this.bean.getInactivityTimeout()));
            }

            if (this.bean.isNonBufferedDestinationSet()) {
               buf.append("NonBufferedDestination");
               buf.append(String.valueOf(this.bean.getNonBufferedDestination()));
            }

            if (this.bean.isNonBufferedSourceSet()) {
               buf.append("NonBufferedSource");
               buf.append(String.valueOf(this.bean.getNonBufferedSource()));
            }

            if (this.bean.isRetransmissionExponentialBackoffSet()) {
               buf.append("RetransmissionExponentialBackoff");
               buf.append(String.valueOf(this.bean.getRetransmissionExponentialBackoff()));
            }

            if (this.bean.isSequenceExpirationSet()) {
               buf.append("SequenceExpiration");
               buf.append(String.valueOf(this.bean.getSequenceExpiration()));
            }

            if (this.bean.isCustomizedSet()) {
               buf.append("Customized");
               buf.append(String.valueOf(this.bean.isCustomized()));
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
            ReliabilityConfigBeanImpl otherTyped = (ReliabilityConfigBeanImpl)other;
            this.computeDiff("AcknowledgementInterval", this.bean.getAcknowledgementInterval(), otherTyped.getAcknowledgementInterval(), true);
            this.computeDiff("BaseRetransmissionInterval", this.bean.getBaseRetransmissionInterval(), otherTyped.getBaseRetransmissionInterval(), true);
            this.computeDiff("BufferRetryCount", this.bean.getBufferRetryCount(), otherTyped.getBufferRetryCount(), true);
            this.computeDiff("BufferRetryDelay", this.bean.getBufferRetryDelay(), otherTyped.getBufferRetryDelay(), true);
            this.computeDiff("InactivityTimeout", this.bean.getInactivityTimeout(), otherTyped.getInactivityTimeout(), true);
            this.computeDiff("NonBufferedDestination", this.bean.getNonBufferedDestination(), otherTyped.getNonBufferedDestination(), true);
            this.computeDiff("NonBufferedSource", this.bean.getNonBufferedSource(), otherTyped.getNonBufferedSource(), true);
            this.computeDiff("RetransmissionExponentialBackoff", this.bean.getRetransmissionExponentialBackoff(), otherTyped.getRetransmissionExponentialBackoff(), true);
            this.computeDiff("SequenceExpiration", this.bean.getSequenceExpiration(), otherTyped.getSequenceExpiration(), true);
            this.computeDiff("Customized", this.bean.isCustomized(), otherTyped.isCustomized(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ReliabilityConfigBeanImpl original = (ReliabilityConfigBeanImpl)event.getSourceBean();
            ReliabilityConfigBeanImpl proposed = (ReliabilityConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AcknowledgementInterval")) {
                  original.setAcknowledgementInterval(proposed.getAcknowledgementInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("BaseRetransmissionInterval")) {
                  original.setBaseRetransmissionInterval(proposed.getBaseRetransmissionInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("BufferRetryCount")) {
                  original.setBufferRetryCount(proposed.getBufferRetryCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("BufferRetryDelay")) {
                  original.setBufferRetryDelay(proposed.getBufferRetryDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("InactivityTimeout")) {
                  original.setInactivityTimeout(proposed.getInactivityTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("NonBufferedDestination")) {
                  original.setNonBufferedDestination(proposed.getNonBufferedDestination());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("NonBufferedSource")) {
                  original.setNonBufferedSource(proposed.getNonBufferedSource());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("RetransmissionExponentialBackoff")) {
                  original.setRetransmissionExponentialBackoff(proposed.getRetransmissionExponentialBackoff());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("SequenceExpiration")) {
                  original.setSequenceExpiration(proposed.getSequenceExpiration());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Customized")) {
                  original.setCustomized(proposed.isCustomized());
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
            ReliabilityConfigBeanImpl copy = (ReliabilityConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AcknowledgementInterval")) && this.bean.isAcknowledgementIntervalSet()) {
               copy.setAcknowledgementInterval(this.bean.getAcknowledgementInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("BaseRetransmissionInterval")) && this.bean.isBaseRetransmissionIntervalSet()) {
               copy.setBaseRetransmissionInterval(this.bean.getBaseRetransmissionInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("BufferRetryCount")) && this.bean.isBufferRetryCountSet()) {
               copy.setBufferRetryCount(this.bean.getBufferRetryCount());
            }

            if ((excludeProps == null || !excludeProps.contains("BufferRetryDelay")) && this.bean.isBufferRetryDelaySet()) {
               copy.setBufferRetryDelay(this.bean.getBufferRetryDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("InactivityTimeout")) && this.bean.isInactivityTimeoutSet()) {
               copy.setInactivityTimeout(this.bean.getInactivityTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("NonBufferedDestination")) && this.bean.isNonBufferedDestinationSet()) {
               copy.setNonBufferedDestination(this.bean.getNonBufferedDestination());
            }

            if ((excludeProps == null || !excludeProps.contains("NonBufferedSource")) && this.bean.isNonBufferedSourceSet()) {
               copy.setNonBufferedSource(this.bean.getNonBufferedSource());
            }

            if ((excludeProps == null || !excludeProps.contains("RetransmissionExponentialBackoff")) && this.bean.isRetransmissionExponentialBackoffSet()) {
               copy.setRetransmissionExponentialBackoff(this.bean.getRetransmissionExponentialBackoff());
            }

            if ((excludeProps == null || !excludeProps.contains("SequenceExpiration")) && this.bean.isSequenceExpirationSet()) {
               copy.setSequenceExpiration(this.bean.getSequenceExpiration());
            }

            if ((excludeProps == null || !excludeProps.contains("Customized")) && this.bean.isCustomizedSet()) {
               copy.setCustomized(this.bean.isCustomized());
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
