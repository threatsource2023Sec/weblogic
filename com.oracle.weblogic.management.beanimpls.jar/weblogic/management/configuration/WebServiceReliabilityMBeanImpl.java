package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.WseeConfigBeanValidator;
import weblogic.utils.collections.CombinedIterator;

public class WebServiceReliabilityMBeanImpl extends ConfigurationMBeanImpl implements WebServiceReliabilityMBean, Serializable {
   private String _AcknowledgementInterval;
   private String _BaseRetransmissionInterval;
   private String _InactivityTimeout;
   private Boolean _NonBufferedDestination;
   private Boolean _NonBufferedSource;
   private Boolean _RetransmissionExponentialBackoff;
   private String _SequenceExpiration;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WebServiceReliabilityMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WebServiceReliabilityMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WebServiceReliabilityMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WebServiceReliabilityMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WebServiceReliabilityMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WebServiceReliabilityMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public WebServiceReliabilityMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebServiceReliabilityMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebServiceReliabilityMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getBaseRetransmissionInterval() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getBaseRetransmissionInterval(), this) : this._BaseRetransmissionInterval;
   }

   public boolean isBaseRetransmissionIntervalInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isBaseRetransmissionIntervalSet() {
      return this._isSet(10);
   }

   public void setBaseRetransmissionInterval(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WseeConfigBeanValidator.validateBaseRetransmissionInterval(param0);
      boolean wasSet = this._isSet(10);
      String _oldVal = this._BaseRetransmissionInterval;
      this._BaseRetransmissionInterval = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceReliabilityMBeanImpl source = (WebServiceReliabilityMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public Boolean isRetransmissionExponentialBackoff() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().isRetransmissionExponentialBackoff() : this._RetransmissionExponentialBackoff;
   }

   public boolean isRetransmissionExponentialBackoffInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isRetransmissionExponentialBackoffSet() {
      return this._isSet(11);
   }

   public void setRetransmissionExponentialBackoff(Boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      Boolean _oldVal = this._RetransmissionExponentialBackoff;
      this._RetransmissionExponentialBackoff = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceReliabilityMBeanImpl source = (WebServiceReliabilityMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public Boolean isNonBufferedSource() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().isNonBufferedSource() : this._NonBufferedSource;
   }

   public boolean isNonBufferedSourceInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isNonBufferedSourceSet() {
      return this._isSet(12);
   }

   public void setNonBufferedSource(Boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      Boolean _oldVal = this._NonBufferedSource;
      this._NonBufferedSource = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceReliabilityMBeanImpl source = (WebServiceReliabilityMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAcknowledgementInterval() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getAcknowledgementInterval(), this) : this._AcknowledgementInterval;
   }

   public boolean isAcknowledgementIntervalInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isAcknowledgementIntervalSet() {
      return this._isSet(13);
   }

   public void setAcknowledgementInterval(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WseeConfigBeanValidator.validateAcknowledgementInterval(param0);
      boolean wasSet = this._isSet(13);
      String _oldVal = this._AcknowledgementInterval;
      this._AcknowledgementInterval = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceReliabilityMBeanImpl source = (WebServiceReliabilityMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getInactivityTimeout() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getInactivityTimeout(), this) : this._InactivityTimeout;
   }

   public boolean isInactivityTimeoutInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isInactivityTimeoutSet() {
      return this._isSet(14);
   }

   public void setInactivityTimeout(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WseeConfigBeanValidator.validateInactivityTimeout(param0);
      boolean wasSet = this._isSet(14);
      String _oldVal = this._InactivityTimeout;
      this._InactivityTimeout = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceReliabilityMBeanImpl source = (WebServiceReliabilityMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSequenceExpiration() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getSequenceExpiration(), this) : this._SequenceExpiration;
   }

   public boolean isSequenceExpirationInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isSequenceExpirationSet() {
      return this._isSet(15);
   }

   public void setSequenceExpiration(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WseeConfigBeanValidator.validateSequenceExpiration(param0);
      boolean wasSet = this._isSet(15);
      String _oldVal = this._SequenceExpiration;
      this._SequenceExpiration = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceReliabilityMBeanImpl source = (WebServiceReliabilityMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public Boolean isNonBufferedDestination() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().isNonBufferedDestination() : this._NonBufferedDestination;
   }

   public boolean isNonBufferedDestinationInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isNonBufferedDestinationSet() {
      return this._isSet(16);
   }

   public void setNonBufferedDestination(Boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      Boolean _oldVal = this._NonBufferedDestination;
      this._NonBufferedDestination = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceReliabilityMBeanImpl source = (WebServiceReliabilityMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._AcknowledgementInterval = "P0DT0.2S";
               if (initOne) {
                  break;
               }
            case 10:
               this._BaseRetransmissionInterval = "P0DT3S";
               if (initOne) {
                  break;
               }
            case 14:
               this._InactivityTimeout = "P0DT600S";
               if (initOne) {
                  break;
               }
            case 15:
               this._SequenceExpiration = "P1D";
               if (initOne) {
                  break;
               }
            case 16:
               this._NonBufferedDestination = true;
               if (initOne) {
                  break;
               }
            case 12:
               this._NonBufferedSource = true;
               if (initOne) {
                  break;
               }
            case 11:
               this._RetransmissionExponentialBackoff = false;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "WebServiceReliability";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AcknowledgementInterval")) {
         oldVal = this._AcknowledgementInterval;
         this._AcknowledgementInterval = (String)v;
         this._postSet(13, oldVal, this._AcknowledgementInterval);
      } else if (name.equals("BaseRetransmissionInterval")) {
         oldVal = this._BaseRetransmissionInterval;
         this._BaseRetransmissionInterval = (String)v;
         this._postSet(10, oldVal, this._BaseRetransmissionInterval);
      } else if (name.equals("InactivityTimeout")) {
         oldVal = this._InactivityTimeout;
         this._InactivityTimeout = (String)v;
         this._postSet(14, oldVal, this._InactivityTimeout);
      } else {
         Boolean oldVal;
         if (name.equals("NonBufferedDestination")) {
            oldVal = this._NonBufferedDestination;
            this._NonBufferedDestination = (Boolean)v;
            this._postSet(16, oldVal, this._NonBufferedDestination);
         } else if (name.equals("NonBufferedSource")) {
            oldVal = this._NonBufferedSource;
            this._NonBufferedSource = (Boolean)v;
            this._postSet(12, oldVal, this._NonBufferedSource);
         } else if (name.equals("RetransmissionExponentialBackoff")) {
            oldVal = this._RetransmissionExponentialBackoff;
            this._RetransmissionExponentialBackoff = (Boolean)v;
            this._postSet(11, oldVal, this._RetransmissionExponentialBackoff);
         } else if (name.equals("SequenceExpiration")) {
            oldVal = this._SequenceExpiration;
            this._SequenceExpiration = (String)v;
            this._postSet(15, oldVal, this._SequenceExpiration);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AcknowledgementInterval")) {
         return this._AcknowledgementInterval;
      } else if (name.equals("BaseRetransmissionInterval")) {
         return this._BaseRetransmissionInterval;
      } else if (name.equals("InactivityTimeout")) {
         return this._InactivityTimeout;
      } else if (name.equals("NonBufferedDestination")) {
         return this._NonBufferedDestination;
      } else if (name.equals("NonBufferedSource")) {
         return this._NonBufferedSource;
      } else if (name.equals("RetransmissionExponentialBackoff")) {
         return this._RetransmissionExponentialBackoff;
      } else {
         return name.equals("SequenceExpiration") ? this._SequenceExpiration : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 18:
               if (s.equals("inactivity-timeout")) {
                  return 14;
               }
               break;
            case 19:
               if (s.equals("sequence-expiration")) {
                  return 15;
               }

               if (s.equals("non-buffered-source")) {
                  return 12;
               }
               break;
            case 24:
               if (s.equals("acknowledgement-interval")) {
                  return 13;
               }

               if (s.equals("non-buffered-destination")) {
                  return 16;
               }
               break;
            case 28:
               if (s.equals("base-retransmission-interval")) {
                  return 10;
               }
               break;
            case 34:
               if (s.equals("retransmission-exponential-backoff")) {
                  return 11;
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
            case 10:
               return "base-retransmission-interval";
            case 11:
               return "retransmission-exponential-backoff";
            case 12:
               return "non-buffered-source";
            case 13:
               return "acknowledgement-interval";
            case 14:
               return "inactivity-timeout";
            case 15:
               return "sequence-expiration";
            case 16:
               return "non-buffered-destination";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WebServiceReliabilityMBeanImpl bean;

      protected Helper(WebServiceReliabilityMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "BaseRetransmissionInterval";
            case 11:
               return "RetransmissionExponentialBackoff";
            case 12:
               return "NonBufferedSource";
            case 13:
               return "AcknowledgementInterval";
            case 14:
               return "InactivityTimeout";
            case 15:
               return "SequenceExpiration";
            case 16:
               return "NonBufferedDestination";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AcknowledgementInterval")) {
            return 13;
         } else if (propName.equals("BaseRetransmissionInterval")) {
            return 10;
         } else if (propName.equals("InactivityTimeout")) {
            return 14;
         } else if (propName.equals("SequenceExpiration")) {
            return 15;
         } else if (propName.equals("NonBufferedDestination")) {
            return 16;
         } else if (propName.equals("NonBufferedSource")) {
            return 12;
         } else {
            return propName.equals("RetransmissionExponentialBackoff") ? 11 : super.getPropertyIndex(propName);
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

            if (this.bean.isInactivityTimeoutSet()) {
               buf.append("InactivityTimeout");
               buf.append(String.valueOf(this.bean.getInactivityTimeout()));
            }

            if (this.bean.isSequenceExpirationSet()) {
               buf.append("SequenceExpiration");
               buf.append(String.valueOf(this.bean.getSequenceExpiration()));
            }

            if (this.bean.isNonBufferedDestinationSet()) {
               buf.append("NonBufferedDestination");
               buf.append(String.valueOf(this.bean.isNonBufferedDestination()));
            }

            if (this.bean.isNonBufferedSourceSet()) {
               buf.append("NonBufferedSource");
               buf.append(String.valueOf(this.bean.isNonBufferedSource()));
            }

            if (this.bean.isRetransmissionExponentialBackoffSet()) {
               buf.append("RetransmissionExponentialBackoff");
               buf.append(String.valueOf(this.bean.isRetransmissionExponentialBackoff()));
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
            WebServiceReliabilityMBeanImpl otherTyped = (WebServiceReliabilityMBeanImpl)other;
            this.computeDiff("AcknowledgementInterval", this.bean.getAcknowledgementInterval(), otherTyped.getAcknowledgementInterval(), true);
            this.computeDiff("BaseRetransmissionInterval", this.bean.getBaseRetransmissionInterval(), otherTyped.getBaseRetransmissionInterval(), true);
            this.computeDiff("InactivityTimeout", this.bean.getInactivityTimeout(), otherTyped.getInactivityTimeout(), true);
            this.computeDiff("SequenceExpiration", this.bean.getSequenceExpiration(), otherTyped.getSequenceExpiration(), true);
            this.computeDiff("NonBufferedDestination", this.bean.isNonBufferedDestination(), otherTyped.isNonBufferedDestination(), true);
            this.computeDiff("NonBufferedSource", this.bean.isNonBufferedSource(), otherTyped.isNonBufferedSource(), true);
            this.computeDiff("RetransmissionExponentialBackoff", this.bean.isRetransmissionExponentialBackoff(), otherTyped.isRetransmissionExponentialBackoff(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebServiceReliabilityMBeanImpl original = (WebServiceReliabilityMBeanImpl)event.getSourceBean();
            WebServiceReliabilityMBeanImpl proposed = (WebServiceReliabilityMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AcknowledgementInterval")) {
                  original.setAcknowledgementInterval(proposed.getAcknowledgementInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("BaseRetransmissionInterval")) {
                  original.setBaseRetransmissionInterval(proposed.getBaseRetransmissionInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("InactivityTimeout")) {
                  original.setInactivityTimeout(proposed.getInactivityTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("SequenceExpiration")) {
                  original.setSequenceExpiration(proposed.getSequenceExpiration());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("NonBufferedDestination")) {
                  original.setNonBufferedDestination(proposed.isNonBufferedDestination());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("NonBufferedSource")) {
                  original.setNonBufferedSource(proposed.isNonBufferedSource());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("RetransmissionExponentialBackoff")) {
                  original.setRetransmissionExponentialBackoff(proposed.isRetransmissionExponentialBackoff());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            WebServiceReliabilityMBeanImpl copy = (WebServiceReliabilityMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AcknowledgementInterval")) && this.bean.isAcknowledgementIntervalSet()) {
               copy.setAcknowledgementInterval(this.bean.getAcknowledgementInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("BaseRetransmissionInterval")) && this.bean.isBaseRetransmissionIntervalSet()) {
               copy.setBaseRetransmissionInterval(this.bean.getBaseRetransmissionInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("InactivityTimeout")) && this.bean.isInactivityTimeoutSet()) {
               copy.setInactivityTimeout(this.bean.getInactivityTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("SequenceExpiration")) && this.bean.isSequenceExpirationSet()) {
               copy.setSequenceExpiration(this.bean.getSequenceExpiration());
            }

            if ((excludeProps == null || !excludeProps.contains("NonBufferedDestination")) && this.bean.isNonBufferedDestinationSet()) {
               copy.setNonBufferedDestination(this.bean.isNonBufferedDestination());
            }

            if ((excludeProps == null || !excludeProps.contains("NonBufferedSource")) && this.bean.isNonBufferedSourceSet()) {
               copy.setNonBufferedSource(this.bean.isNonBufferedSource());
            }

            if ((excludeProps == null || !excludeProps.contains("RetransmissionExponentialBackoff")) && this.bean.isRetransmissionExponentialBackoffSet()) {
               copy.setRetransmissionExponentialBackoff(this.bean.isRetransmissionExponentialBackoff());
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
