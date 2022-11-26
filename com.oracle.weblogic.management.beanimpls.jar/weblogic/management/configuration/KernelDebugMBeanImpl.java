package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class KernelDebugMBeanImpl extends DebugMBeanImpl implements KernelDebugMBean, Serializable {
   private boolean _DebugAbbreviation;
   private boolean _DebugConnection;
   private boolean _DebugDGCEnrollment;
   private boolean _DebugFailOver;
   private boolean _DebugIIOP;
   private boolean _DebugIIOPConnection;
   private boolean _DebugIIOPMarshal;
   private boolean _DebugIIOPOTS;
   private boolean _DebugIIOPReplacer;
   private boolean _DebugIIOPSecurity;
   private boolean _DebugIIOPStartup;
   private boolean _DebugIIOPTransport;
   private boolean _DebugLoadBalancing;
   private boolean _DebugLocalRemoteJVM;
   private boolean _DebugMessaging;
   private boolean _DebugMuxer;
   private boolean _DebugMuxerConnection;
   private boolean _DebugMuxerDetail;
   private boolean _DebugMuxerException;
   private boolean _DebugMuxerTimeout;
   private Properties _DebugParameters;
   private boolean _DebugRC4;
   private boolean _DebugRSA;
   private boolean _DebugRouting;
   private boolean _DebugSSL;
   private boolean _DebugSelfTuning;
   private boolean _DebugWorkContext;
   private boolean _ForceGCEachDGCPeriod;
   private boolean _LogDGCStatistics;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private KernelDebugMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(KernelDebugMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(KernelDebugMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public KernelDebugMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(KernelDebugMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      KernelDebugMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public KernelDebugMBeanImpl() {
      this._initializeProperty(-1);
   }

   public KernelDebugMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public KernelDebugMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getDebugAbbreviation() {
      if (!this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         return this._getDelegateBean().getDebugAbbreviation();
      } else if (!this._isSet(11)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._DebugAbbreviation;
      }
   }

   public boolean isDebugAbbreviationInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isDebugAbbreviationSet() {
      return this._isSet(11);
   }

   public void setDebugAbbreviation(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      boolean _oldVal = this._DebugAbbreviation;
      this._DebugAbbreviation = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugConnection() {
      if (!this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         return this._getDelegateBean().getDebugConnection();
      } else if (!this._isSet(12)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._DebugConnection;
      }
   }

   public boolean isDebugConnectionInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isDebugConnectionSet() {
      return this._isSet(12);
   }

   public void setDebugConnection(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      boolean _oldVal = this._DebugConnection;
      this._DebugConnection = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugMessaging() {
      if (!this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         return this._getDelegateBean().getDebugMessaging();
      } else if (!this._isSet(13)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._DebugMessaging;
      }
   }

   public boolean isDebugMessagingInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isDebugMessagingSet() {
      return this._isSet(13);
   }

   public void setDebugMessaging(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      boolean _oldVal = this._DebugMessaging;
      this._DebugMessaging = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugRouting() {
      if (!this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         return this._getDelegateBean().getDebugRouting();
      } else if (!this._isSet(14)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._DebugRouting;
      }
   }

   public boolean isDebugRoutingInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isDebugRoutingSet() {
      return this._isSet(14);
   }

   public void setDebugRouting(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      boolean _oldVal = this._DebugRouting;
      this._DebugRouting = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugLocalRemoteJVM() {
      if (!this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15)) {
         return this._getDelegateBean().getDebugLocalRemoteJVM();
      } else if (!this._isSet(15)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._DebugLocalRemoteJVM;
      }
   }

   public boolean isDebugLocalRemoteJVMInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isDebugLocalRemoteJVMSet() {
      return this._isSet(15);
   }

   public void setDebugLocalRemoteJVM(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      boolean _oldVal = this._DebugLocalRemoteJVM;
      this._DebugLocalRemoteJVM = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugLoadBalancing() {
      if (!this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16)) {
         return this._getDelegateBean().getDebugLoadBalancing();
      } else if (!this._isSet(16)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._DebugLoadBalancing;
      }
   }

   public boolean isDebugLoadBalancingInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isDebugLoadBalancingSet() {
      return this._isSet(16);
   }

   public void setDebugLoadBalancing(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      boolean _oldVal = this._DebugLoadBalancing;
      this._DebugLoadBalancing = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugWorkContext() {
      if (!this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17)) {
         return this._getDelegateBean().getDebugWorkContext();
      } else if (!this._isSet(17)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._DebugWorkContext;
      }
   }

   public boolean isDebugWorkContextInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isDebugWorkContextSet() {
      return this._isSet(17);
   }

   public void setDebugWorkContext(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      boolean _oldVal = this._DebugWorkContext;
      this._DebugWorkContext = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugFailOver() {
      if (!this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18)) {
         return this._getDelegateBean().getDebugFailOver();
      } else if (!this._isSet(18)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._DebugFailOver;
      }
   }

   public boolean isDebugFailOverInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isDebugFailOverSet() {
      return this._isSet(18);
   }

   public void setDebugFailOver(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      boolean _oldVal = this._DebugFailOver;
      this._DebugFailOver = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getForceGCEachDGCPeriod() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getForceGCEachDGCPeriod() : this._ForceGCEachDGCPeriod;
   }

   public boolean isForceGCEachDGCPeriodInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isForceGCEachDGCPeriodSet() {
      return this._isSet(19);
   }

   public void setForceGCEachDGCPeriod(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      boolean _oldVal = this._ForceGCEachDGCPeriod;
      this._ForceGCEachDGCPeriod = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugDGCEnrollment() {
      if (!this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20)) {
         return this._getDelegateBean().getDebugDGCEnrollment();
      } else if (!this._isSet(20)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._DebugDGCEnrollment;
      }
   }

   public boolean isDebugDGCEnrollmentInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isDebugDGCEnrollmentSet() {
      return this._isSet(20);
   }

   public void setDebugDGCEnrollment(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      boolean _oldVal = this._DebugDGCEnrollment;
      this._DebugDGCEnrollment = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getLogDGCStatistics() {
      if (!this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21)) {
         return this._getDelegateBean().getLogDGCStatistics();
      } else if (!this._isSet(21)) {
         return this._isSecureModeEnabled() ? false : false;
      } else {
         return this._LogDGCStatistics;
      }
   }

   public boolean isLogDGCStatisticsInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isLogDGCStatisticsSet() {
      return this._isSet(21);
   }

   public void setLogDGCStatistics(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      boolean _oldVal = this._LogDGCStatistics;
      this._LogDGCStatistics = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugSSL() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().getDebugSSL() : this._DebugSSL;
   }

   public boolean isDebugSSLInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isDebugSSLSet() {
      return this._isSet(22);
   }

   public void setDebugSSL(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      boolean _oldVal = this._DebugSSL;
      this._DebugSSL = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugRC4() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getDebugRC4() : this._DebugRC4;
   }

   public boolean isDebugRC4Inherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isDebugRC4Set() {
      return this._isSet(23);
   }

   public void setDebugRC4(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      boolean _oldVal = this._DebugRC4;
      this._DebugRC4 = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugRSA() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().getDebugRSA() : this._DebugRSA;
   }

   public boolean isDebugRSAInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isDebugRSASet() {
      return this._isSet(24);
   }

   public void setDebugRSA(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      boolean _oldVal = this._DebugRSA;
      this._DebugRSA = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugMuxer() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getDebugMuxer() : this._DebugMuxer;
   }

   public boolean isDebugMuxerInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isDebugMuxerSet() {
      return this._isSet(25);
   }

   public void setDebugMuxer(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(25);
      boolean _oldVal = this._DebugMuxer;
      this._DebugMuxer = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugMuxerDetail() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getDebugMuxerDetail() : this._DebugMuxerDetail;
   }

   public boolean isDebugMuxerDetailInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isDebugMuxerDetailSet() {
      return this._isSet(26);
   }

   public void setDebugMuxerDetail(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      boolean _oldVal = this._DebugMuxerDetail;
      this._DebugMuxerDetail = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugMuxerTimeout() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().getDebugMuxerTimeout() : this._DebugMuxerTimeout;
   }

   public boolean isDebugMuxerTimeoutInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isDebugMuxerTimeoutSet() {
      return this._isSet(27);
   }

   public void setDebugMuxerTimeout(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      boolean _oldVal = this._DebugMuxerTimeout;
      this._DebugMuxerTimeout = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugMuxerConnection() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getDebugMuxerConnection() : this._DebugMuxerConnection;
   }

   public boolean isDebugMuxerConnectionInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isDebugMuxerConnectionSet() {
      return this._isSet(28);
   }

   public void setDebugMuxerConnection(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      boolean _oldVal = this._DebugMuxerConnection;
      this._DebugMuxerConnection = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugMuxerException() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().getDebugMuxerException() : this._DebugMuxerException;
   }

   public boolean isDebugMuxerExceptionInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isDebugMuxerExceptionSet() {
      return this._isSet(29);
   }

   public void setDebugMuxerException(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      boolean _oldVal = this._DebugMuxerException;
      this._DebugMuxerException = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugIIOP() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getDebugIIOP() : this._DebugIIOP;
   }

   public boolean isDebugIIOPInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isDebugIIOPSet() {
      return this._isSet(30);
   }

   public void setDebugIIOP(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      boolean _oldVal = this._DebugIIOP;
      this._DebugIIOP = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugIIOPTransport() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getDebugIIOPTransport() : this._DebugIIOPTransport;
   }

   public boolean isDebugIIOPTransportInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isDebugIIOPTransportSet() {
      return this._isSet(31);
   }

   public void setDebugIIOPTransport(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      boolean _oldVal = this._DebugIIOPTransport;
      this._DebugIIOPTransport = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugIIOPMarshal() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().getDebugIIOPMarshal() : this._DebugIIOPMarshal;
   }

   public boolean isDebugIIOPMarshalInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isDebugIIOPMarshalSet() {
      return this._isSet(32);
   }

   public void setDebugIIOPMarshal(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(32);
      boolean _oldVal = this._DebugIIOPMarshal;
      this._DebugIIOPMarshal = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugIIOPSecurity() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getDebugIIOPSecurity() : this._DebugIIOPSecurity;
   }

   public boolean isDebugIIOPSecurityInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isDebugIIOPSecuritySet() {
      return this._isSet(33);
   }

   public void setDebugIIOPSecurity(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(33);
      boolean _oldVal = this._DebugIIOPSecurity;
      this._DebugIIOPSecurity = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugIIOPOTS() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().getDebugIIOPOTS() : this._DebugIIOPOTS;
   }

   public boolean isDebugIIOPOTSInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isDebugIIOPOTSSet() {
      return this._isSet(34);
   }

   public void setDebugIIOPOTS(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      boolean _oldVal = this._DebugIIOPOTS;
      this._DebugIIOPOTS = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugIIOPReplacer() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().getDebugIIOPReplacer() : this._DebugIIOPReplacer;
   }

   public boolean isDebugIIOPReplacerInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isDebugIIOPReplacerSet() {
      return this._isSet(35);
   }

   public void setDebugIIOPReplacer(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(35);
      boolean _oldVal = this._DebugIIOPReplacer;
      this._DebugIIOPReplacer = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugIIOPConnection() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().getDebugIIOPConnection() : this._DebugIIOPConnection;
   }

   public boolean isDebugIIOPConnectionInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isDebugIIOPConnectionSet() {
      return this._isSet(36);
   }

   public void setDebugIIOPConnection(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(36);
      boolean _oldVal = this._DebugIIOPConnection;
      this._DebugIIOPConnection = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugIIOPStartup() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().getDebugIIOPStartup() : this._DebugIIOPStartup;
   }

   public boolean isDebugIIOPStartupInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isDebugIIOPStartupSet() {
      return this._isSet(37);
   }

   public void setDebugIIOPStartup(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(37);
      boolean _oldVal = this._DebugIIOPStartup;
      this._DebugIIOPStartup = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getDebugSelfTuning() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._getDelegateBean().getDebugSelfTuning() : this._DebugSelfTuning;
   }

   public boolean isDebugSelfTuningInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isDebugSelfTuningSet() {
      return this._isSet(38);
   }

   public void setDebugSelfTuning(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(38);
      boolean _oldVal = this._DebugSelfTuning;
      this._DebugSelfTuning = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public Properties getDebugParameters() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().getDebugParameters() : this._DebugParameters;
   }

   public String getDebugParametersAsString() {
      return StringHelper.objectToString(this.getDebugParameters());
   }

   public boolean isDebugParametersInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isDebugParametersSet() {
      return this._isSet(39);
   }

   public void setDebugParametersAsString(String param0) {
      try {
         this.setDebugParameters(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setDebugParameters(Properties param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(39);
      Properties _oldVal = this._DebugParameters;
      this._DebugParameters = param0;
      this._postSet(39, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelDebugMBeanImpl source = (KernelDebugMBeanImpl)var4.next();
         if (source != null && !source._isSet(39)) {
            source._postSetFirePropertyChange(39, wasSet, _oldVal, param0);
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._DebugAbbreviation = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._DebugConnection = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._DebugDGCEnrollment = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._DebugFailOver = false;
               if (initOne) {
                  break;
               }
            case 30:
               this._DebugIIOP = false;
               if (initOne) {
                  break;
               }
            case 36:
               this._DebugIIOPConnection = false;
               if (initOne) {
                  break;
               }
            case 32:
               this._DebugIIOPMarshal = false;
               if (initOne) {
                  break;
               }
            case 34:
               this._DebugIIOPOTS = false;
               if (initOne) {
                  break;
               }
            case 35:
               this._DebugIIOPReplacer = false;
               if (initOne) {
                  break;
               }
            case 33:
               this._DebugIIOPSecurity = false;
               if (initOne) {
                  break;
               }
            case 37:
               this._DebugIIOPStartup = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._DebugIIOPTransport = false;
               if (initOne) {
                  break;
               }
            case 16:
               this._DebugLoadBalancing = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._DebugLocalRemoteJVM = false;
               if (initOne) {
                  break;
               }
            case 13:
               this._DebugMessaging = false;
               if (initOne) {
                  break;
               }
            case 25:
               this._DebugMuxer = false;
               if (initOne) {
                  break;
               }
            case 28:
               this._DebugMuxerConnection = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._DebugMuxerDetail = false;
               if (initOne) {
                  break;
               }
            case 29:
               this._DebugMuxerException = false;
               if (initOne) {
                  break;
               }
            case 27:
               this._DebugMuxerTimeout = false;
               if (initOne) {
                  break;
               }
            case 39:
               this._DebugParameters = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._DebugRC4 = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._DebugRSA = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._DebugRouting = false;
               if (initOne) {
                  break;
               }
            case 22:
               this._DebugSSL = false;
               if (initOne) {
                  break;
               }
            case 38:
               this._DebugSelfTuning = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._DebugWorkContext = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._ForceGCEachDGCPeriod = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._LogDGCStatistics = false;
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
      return "KernelDebug";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("DebugAbbreviation")) {
         oldVal = this._DebugAbbreviation;
         this._DebugAbbreviation = (Boolean)v;
         this._postSet(11, oldVal, this._DebugAbbreviation);
      } else if (name.equals("DebugConnection")) {
         oldVal = this._DebugConnection;
         this._DebugConnection = (Boolean)v;
         this._postSet(12, oldVal, this._DebugConnection);
      } else if (name.equals("DebugDGCEnrollment")) {
         oldVal = this._DebugDGCEnrollment;
         this._DebugDGCEnrollment = (Boolean)v;
         this._postSet(20, oldVal, this._DebugDGCEnrollment);
      } else if (name.equals("DebugFailOver")) {
         oldVal = this._DebugFailOver;
         this._DebugFailOver = (Boolean)v;
         this._postSet(18, oldVal, this._DebugFailOver);
      } else if (name.equals("DebugIIOP")) {
         oldVal = this._DebugIIOP;
         this._DebugIIOP = (Boolean)v;
         this._postSet(30, oldVal, this._DebugIIOP);
      } else if (name.equals("DebugIIOPConnection")) {
         oldVal = this._DebugIIOPConnection;
         this._DebugIIOPConnection = (Boolean)v;
         this._postSet(36, oldVal, this._DebugIIOPConnection);
      } else if (name.equals("DebugIIOPMarshal")) {
         oldVal = this._DebugIIOPMarshal;
         this._DebugIIOPMarshal = (Boolean)v;
         this._postSet(32, oldVal, this._DebugIIOPMarshal);
      } else if (name.equals("DebugIIOPOTS")) {
         oldVal = this._DebugIIOPOTS;
         this._DebugIIOPOTS = (Boolean)v;
         this._postSet(34, oldVal, this._DebugIIOPOTS);
      } else if (name.equals("DebugIIOPReplacer")) {
         oldVal = this._DebugIIOPReplacer;
         this._DebugIIOPReplacer = (Boolean)v;
         this._postSet(35, oldVal, this._DebugIIOPReplacer);
      } else if (name.equals("DebugIIOPSecurity")) {
         oldVal = this._DebugIIOPSecurity;
         this._DebugIIOPSecurity = (Boolean)v;
         this._postSet(33, oldVal, this._DebugIIOPSecurity);
      } else if (name.equals("DebugIIOPStartup")) {
         oldVal = this._DebugIIOPStartup;
         this._DebugIIOPStartup = (Boolean)v;
         this._postSet(37, oldVal, this._DebugIIOPStartup);
      } else if (name.equals("DebugIIOPTransport")) {
         oldVal = this._DebugIIOPTransport;
         this._DebugIIOPTransport = (Boolean)v;
         this._postSet(31, oldVal, this._DebugIIOPTransport);
      } else if (name.equals("DebugLoadBalancing")) {
         oldVal = this._DebugLoadBalancing;
         this._DebugLoadBalancing = (Boolean)v;
         this._postSet(16, oldVal, this._DebugLoadBalancing);
      } else if (name.equals("DebugLocalRemoteJVM")) {
         oldVal = this._DebugLocalRemoteJVM;
         this._DebugLocalRemoteJVM = (Boolean)v;
         this._postSet(15, oldVal, this._DebugLocalRemoteJVM);
      } else if (name.equals("DebugMessaging")) {
         oldVal = this._DebugMessaging;
         this._DebugMessaging = (Boolean)v;
         this._postSet(13, oldVal, this._DebugMessaging);
      } else if (name.equals("DebugMuxer")) {
         oldVal = this._DebugMuxer;
         this._DebugMuxer = (Boolean)v;
         this._postSet(25, oldVal, this._DebugMuxer);
      } else if (name.equals("DebugMuxerConnection")) {
         oldVal = this._DebugMuxerConnection;
         this._DebugMuxerConnection = (Boolean)v;
         this._postSet(28, oldVal, this._DebugMuxerConnection);
      } else if (name.equals("DebugMuxerDetail")) {
         oldVal = this._DebugMuxerDetail;
         this._DebugMuxerDetail = (Boolean)v;
         this._postSet(26, oldVal, this._DebugMuxerDetail);
      } else if (name.equals("DebugMuxerException")) {
         oldVal = this._DebugMuxerException;
         this._DebugMuxerException = (Boolean)v;
         this._postSet(29, oldVal, this._DebugMuxerException);
      } else if (name.equals("DebugMuxerTimeout")) {
         oldVal = this._DebugMuxerTimeout;
         this._DebugMuxerTimeout = (Boolean)v;
         this._postSet(27, oldVal, this._DebugMuxerTimeout);
      } else if (name.equals("DebugParameters")) {
         Properties oldVal = this._DebugParameters;
         this._DebugParameters = (Properties)v;
         this._postSet(39, oldVal, this._DebugParameters);
      } else if (name.equals("DebugRC4")) {
         oldVal = this._DebugRC4;
         this._DebugRC4 = (Boolean)v;
         this._postSet(23, oldVal, this._DebugRC4);
      } else if (name.equals("DebugRSA")) {
         oldVal = this._DebugRSA;
         this._DebugRSA = (Boolean)v;
         this._postSet(24, oldVal, this._DebugRSA);
      } else if (name.equals("DebugRouting")) {
         oldVal = this._DebugRouting;
         this._DebugRouting = (Boolean)v;
         this._postSet(14, oldVal, this._DebugRouting);
      } else if (name.equals("DebugSSL")) {
         oldVal = this._DebugSSL;
         this._DebugSSL = (Boolean)v;
         this._postSet(22, oldVal, this._DebugSSL);
      } else if (name.equals("DebugSelfTuning")) {
         oldVal = this._DebugSelfTuning;
         this._DebugSelfTuning = (Boolean)v;
         this._postSet(38, oldVal, this._DebugSelfTuning);
      } else if (name.equals("DebugWorkContext")) {
         oldVal = this._DebugWorkContext;
         this._DebugWorkContext = (Boolean)v;
         this._postSet(17, oldVal, this._DebugWorkContext);
      } else if (name.equals("ForceGCEachDGCPeriod")) {
         oldVal = this._ForceGCEachDGCPeriod;
         this._ForceGCEachDGCPeriod = (Boolean)v;
         this._postSet(19, oldVal, this._ForceGCEachDGCPeriod);
      } else if (name.equals("LogDGCStatistics")) {
         oldVal = this._LogDGCStatistics;
         this._LogDGCStatistics = (Boolean)v;
         this._postSet(21, oldVal, this._LogDGCStatistics);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("DebugAbbreviation")) {
         return new Boolean(this._DebugAbbreviation);
      } else if (name.equals("DebugConnection")) {
         return new Boolean(this._DebugConnection);
      } else if (name.equals("DebugDGCEnrollment")) {
         return new Boolean(this._DebugDGCEnrollment);
      } else if (name.equals("DebugFailOver")) {
         return new Boolean(this._DebugFailOver);
      } else if (name.equals("DebugIIOP")) {
         return new Boolean(this._DebugIIOP);
      } else if (name.equals("DebugIIOPConnection")) {
         return new Boolean(this._DebugIIOPConnection);
      } else if (name.equals("DebugIIOPMarshal")) {
         return new Boolean(this._DebugIIOPMarshal);
      } else if (name.equals("DebugIIOPOTS")) {
         return new Boolean(this._DebugIIOPOTS);
      } else if (name.equals("DebugIIOPReplacer")) {
         return new Boolean(this._DebugIIOPReplacer);
      } else if (name.equals("DebugIIOPSecurity")) {
         return new Boolean(this._DebugIIOPSecurity);
      } else if (name.equals("DebugIIOPStartup")) {
         return new Boolean(this._DebugIIOPStartup);
      } else if (name.equals("DebugIIOPTransport")) {
         return new Boolean(this._DebugIIOPTransport);
      } else if (name.equals("DebugLoadBalancing")) {
         return new Boolean(this._DebugLoadBalancing);
      } else if (name.equals("DebugLocalRemoteJVM")) {
         return new Boolean(this._DebugLocalRemoteJVM);
      } else if (name.equals("DebugMessaging")) {
         return new Boolean(this._DebugMessaging);
      } else if (name.equals("DebugMuxer")) {
         return new Boolean(this._DebugMuxer);
      } else if (name.equals("DebugMuxerConnection")) {
         return new Boolean(this._DebugMuxerConnection);
      } else if (name.equals("DebugMuxerDetail")) {
         return new Boolean(this._DebugMuxerDetail);
      } else if (name.equals("DebugMuxerException")) {
         return new Boolean(this._DebugMuxerException);
      } else if (name.equals("DebugMuxerTimeout")) {
         return new Boolean(this._DebugMuxerTimeout);
      } else if (name.equals("DebugParameters")) {
         return this._DebugParameters;
      } else if (name.equals("DebugRC4")) {
         return new Boolean(this._DebugRC4);
      } else if (name.equals("DebugRSA")) {
         return new Boolean(this._DebugRSA);
      } else if (name.equals("DebugRouting")) {
         return new Boolean(this._DebugRouting);
      } else if (name.equals("DebugSSL")) {
         return new Boolean(this._DebugSSL);
      } else if (name.equals("DebugSelfTuning")) {
         return new Boolean(this._DebugSelfTuning);
      } else if (name.equals("DebugWorkContext")) {
         return new Boolean(this._DebugWorkContext);
      } else if (name.equals("ForceGCEachDGCPeriod")) {
         return new Boolean(this._ForceGCEachDGCPeriod);
      } else {
         return name.equals("LogDGCStatistics") ? new Boolean(this._LogDGCStatistics) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DebugMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("debugrc4")) {
                  return 23;
               }

               if (s.equals("debugrsa")) {
                  return 24;
               }

               if (s.equals("debugssl")) {
                  return 22;
               }
               break;
            case 9:
               if (s.equals("debugiiop")) {
                  return 30;
               }
            case 10:
            case 14:
            default:
               break;
            case 11:
               if (s.equals("debug-muxer")) {
                  return 25;
               }
               break;
            case 12:
               if (s.equals("debugiiopots")) {
                  return 34;
               }
               break;
            case 13:
               if (s.equals("debug-routing")) {
                  return 14;
               }
               break;
            case 15:
               if (s.equals("debug-fail-over")) {
                  return 18;
               }

               if (s.equals("debug-messaging")) {
                  return 13;
               }
               break;
            case 16:
               if (s.equals("debug-connection")) {
                  return 12;
               }

               if (s.equals("debug-parameters")) {
                  return 39;
               }
               break;
            case 17:
               if (s.equals("debugiiop-marshal")) {
                  return 32;
               }

               if (s.equals("debugiiop-startup")) {
                  return 37;
               }

               if (s.equals("debug-self-tuning")) {
                  return 38;
               }

               if (s.equals("logdgc-statistics")) {
                  return 21;
               }
               break;
            case 18:
               if (s.equals("debug-abbreviation")) {
                  return 11;
               }

               if (s.equals("debugiiop-replacer")) {
                  return 35;
               }

               if (s.equals("debugiiop-security")) {
                  return 33;
               }

               if (s.equals("debug-muxer-detail")) {
                  return 26;
               }

               if (s.equals("debug-work-context")) {
                  return 17;
               }
               break;
            case 19:
               if (s.equals("debugdgc-enrollment")) {
                  return 20;
               }

               if (s.equals("debugiiop-transport")) {
                  return 31;
               }

               if (s.equals("debug-muxer-timeout")) {
                  return 27;
               }
               break;
            case 20:
               if (s.equals("debugiiop-connection")) {
                  return 36;
               }

               if (s.equals("debug-load-balancing")) {
                  return 16;
               }
               break;
            case 21:
               if (s.equals("debug-muxer-exception")) {
                  return 29;
               }
               break;
            case 22:
               if (s.equals("debug-local-remote-jvm")) {
                  return 15;
               }

               if (s.equals("debug-muxer-connection")) {
                  return 28;
               }

               if (s.equals("forcegc-eachdgc-period")) {
                  return 19;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new DebugScopeMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 11:
               return "debug-abbreviation";
            case 12:
               return "debug-connection";
            case 13:
               return "debug-messaging";
            case 14:
               return "debug-routing";
            case 15:
               return "debug-local-remote-jvm";
            case 16:
               return "debug-load-balancing";
            case 17:
               return "debug-work-context";
            case 18:
               return "debug-fail-over";
            case 19:
               return "forcegc-eachdgc-period";
            case 20:
               return "debugdgc-enrollment";
            case 21:
               return "logdgc-statistics";
            case 22:
               return "debugssl";
            case 23:
               return "debugrc4";
            case 24:
               return "debugrsa";
            case 25:
               return "debug-muxer";
            case 26:
               return "debug-muxer-detail";
            case 27:
               return "debug-muxer-timeout";
            case 28:
               return "debug-muxer-connection";
            case 29:
               return "debug-muxer-exception";
            case 30:
               return "debugiiop";
            case 31:
               return "debugiiop-transport";
            case 32:
               return "debugiiop-marshal";
            case 33:
               return "debugiiop-security";
            case 34:
               return "debugiiopots";
            case 35:
               return "debugiiop-replacer";
            case 36:
               return "debugiiop-connection";
            case 37:
               return "debugiiop-startup";
            case 38:
               return "debug-self-tuning";
            case 39:
               return "debug-parameters";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            default:
               return super.isBean(propIndex);
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

   protected static class Helper extends DebugMBeanImpl.Helper {
      private KernelDebugMBeanImpl bean;

      protected Helper(KernelDebugMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 11:
               return "DebugAbbreviation";
            case 12:
               return "DebugConnection";
            case 13:
               return "DebugMessaging";
            case 14:
               return "DebugRouting";
            case 15:
               return "DebugLocalRemoteJVM";
            case 16:
               return "DebugLoadBalancing";
            case 17:
               return "DebugWorkContext";
            case 18:
               return "DebugFailOver";
            case 19:
               return "ForceGCEachDGCPeriod";
            case 20:
               return "DebugDGCEnrollment";
            case 21:
               return "LogDGCStatistics";
            case 22:
               return "DebugSSL";
            case 23:
               return "DebugRC4";
            case 24:
               return "DebugRSA";
            case 25:
               return "DebugMuxer";
            case 26:
               return "DebugMuxerDetail";
            case 27:
               return "DebugMuxerTimeout";
            case 28:
               return "DebugMuxerConnection";
            case 29:
               return "DebugMuxerException";
            case 30:
               return "DebugIIOP";
            case 31:
               return "DebugIIOPTransport";
            case 32:
               return "DebugIIOPMarshal";
            case 33:
               return "DebugIIOPSecurity";
            case 34:
               return "DebugIIOPOTS";
            case 35:
               return "DebugIIOPReplacer";
            case 36:
               return "DebugIIOPConnection";
            case 37:
               return "DebugIIOPStartup";
            case 38:
               return "DebugSelfTuning";
            case 39:
               return "DebugParameters";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DebugAbbreviation")) {
            return 11;
         } else if (propName.equals("DebugConnection")) {
            return 12;
         } else if (propName.equals("DebugDGCEnrollment")) {
            return 20;
         } else if (propName.equals("DebugFailOver")) {
            return 18;
         } else if (propName.equals("DebugIIOP")) {
            return 30;
         } else if (propName.equals("DebugIIOPConnection")) {
            return 36;
         } else if (propName.equals("DebugIIOPMarshal")) {
            return 32;
         } else if (propName.equals("DebugIIOPOTS")) {
            return 34;
         } else if (propName.equals("DebugIIOPReplacer")) {
            return 35;
         } else if (propName.equals("DebugIIOPSecurity")) {
            return 33;
         } else if (propName.equals("DebugIIOPStartup")) {
            return 37;
         } else if (propName.equals("DebugIIOPTransport")) {
            return 31;
         } else if (propName.equals("DebugLoadBalancing")) {
            return 16;
         } else if (propName.equals("DebugLocalRemoteJVM")) {
            return 15;
         } else if (propName.equals("DebugMessaging")) {
            return 13;
         } else if (propName.equals("DebugMuxer")) {
            return 25;
         } else if (propName.equals("DebugMuxerConnection")) {
            return 28;
         } else if (propName.equals("DebugMuxerDetail")) {
            return 26;
         } else if (propName.equals("DebugMuxerException")) {
            return 29;
         } else if (propName.equals("DebugMuxerTimeout")) {
            return 27;
         } else if (propName.equals("DebugParameters")) {
            return 39;
         } else if (propName.equals("DebugRC4")) {
            return 23;
         } else if (propName.equals("DebugRSA")) {
            return 24;
         } else if (propName.equals("DebugRouting")) {
            return 14;
         } else if (propName.equals("DebugSSL")) {
            return 22;
         } else if (propName.equals("DebugSelfTuning")) {
            return 38;
         } else if (propName.equals("DebugWorkContext")) {
            return 17;
         } else if (propName.equals("ForceGCEachDGCPeriod")) {
            return 19;
         } else {
            return propName.equals("LogDGCStatistics") ? 21 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getDebugScopes()));
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
            if (this.bean.isDebugAbbreviationSet()) {
               buf.append("DebugAbbreviation");
               buf.append(String.valueOf(this.bean.getDebugAbbreviation()));
            }

            if (this.bean.isDebugConnectionSet()) {
               buf.append("DebugConnection");
               buf.append(String.valueOf(this.bean.getDebugConnection()));
            }

            if (this.bean.isDebugDGCEnrollmentSet()) {
               buf.append("DebugDGCEnrollment");
               buf.append(String.valueOf(this.bean.getDebugDGCEnrollment()));
            }

            if (this.bean.isDebugFailOverSet()) {
               buf.append("DebugFailOver");
               buf.append(String.valueOf(this.bean.getDebugFailOver()));
            }

            if (this.bean.isDebugIIOPSet()) {
               buf.append("DebugIIOP");
               buf.append(String.valueOf(this.bean.getDebugIIOP()));
            }

            if (this.bean.isDebugIIOPConnectionSet()) {
               buf.append("DebugIIOPConnection");
               buf.append(String.valueOf(this.bean.getDebugIIOPConnection()));
            }

            if (this.bean.isDebugIIOPMarshalSet()) {
               buf.append("DebugIIOPMarshal");
               buf.append(String.valueOf(this.bean.getDebugIIOPMarshal()));
            }

            if (this.bean.isDebugIIOPOTSSet()) {
               buf.append("DebugIIOPOTS");
               buf.append(String.valueOf(this.bean.getDebugIIOPOTS()));
            }

            if (this.bean.isDebugIIOPReplacerSet()) {
               buf.append("DebugIIOPReplacer");
               buf.append(String.valueOf(this.bean.getDebugIIOPReplacer()));
            }

            if (this.bean.isDebugIIOPSecuritySet()) {
               buf.append("DebugIIOPSecurity");
               buf.append(String.valueOf(this.bean.getDebugIIOPSecurity()));
            }

            if (this.bean.isDebugIIOPStartupSet()) {
               buf.append("DebugIIOPStartup");
               buf.append(String.valueOf(this.bean.getDebugIIOPStartup()));
            }

            if (this.bean.isDebugIIOPTransportSet()) {
               buf.append("DebugIIOPTransport");
               buf.append(String.valueOf(this.bean.getDebugIIOPTransport()));
            }

            if (this.bean.isDebugLoadBalancingSet()) {
               buf.append("DebugLoadBalancing");
               buf.append(String.valueOf(this.bean.getDebugLoadBalancing()));
            }

            if (this.bean.isDebugLocalRemoteJVMSet()) {
               buf.append("DebugLocalRemoteJVM");
               buf.append(String.valueOf(this.bean.getDebugLocalRemoteJVM()));
            }

            if (this.bean.isDebugMessagingSet()) {
               buf.append("DebugMessaging");
               buf.append(String.valueOf(this.bean.getDebugMessaging()));
            }

            if (this.bean.isDebugMuxerSet()) {
               buf.append("DebugMuxer");
               buf.append(String.valueOf(this.bean.getDebugMuxer()));
            }

            if (this.bean.isDebugMuxerConnectionSet()) {
               buf.append("DebugMuxerConnection");
               buf.append(String.valueOf(this.bean.getDebugMuxerConnection()));
            }

            if (this.bean.isDebugMuxerDetailSet()) {
               buf.append("DebugMuxerDetail");
               buf.append(String.valueOf(this.bean.getDebugMuxerDetail()));
            }

            if (this.bean.isDebugMuxerExceptionSet()) {
               buf.append("DebugMuxerException");
               buf.append(String.valueOf(this.bean.getDebugMuxerException()));
            }

            if (this.bean.isDebugMuxerTimeoutSet()) {
               buf.append("DebugMuxerTimeout");
               buf.append(String.valueOf(this.bean.getDebugMuxerTimeout()));
            }

            if (this.bean.isDebugParametersSet()) {
               buf.append("DebugParameters");
               buf.append(String.valueOf(this.bean.getDebugParameters()));
            }

            if (this.bean.isDebugRC4Set()) {
               buf.append("DebugRC4");
               buf.append(String.valueOf(this.bean.getDebugRC4()));
            }

            if (this.bean.isDebugRSASet()) {
               buf.append("DebugRSA");
               buf.append(String.valueOf(this.bean.getDebugRSA()));
            }

            if (this.bean.isDebugRoutingSet()) {
               buf.append("DebugRouting");
               buf.append(String.valueOf(this.bean.getDebugRouting()));
            }

            if (this.bean.isDebugSSLSet()) {
               buf.append("DebugSSL");
               buf.append(String.valueOf(this.bean.getDebugSSL()));
            }

            if (this.bean.isDebugSelfTuningSet()) {
               buf.append("DebugSelfTuning");
               buf.append(String.valueOf(this.bean.getDebugSelfTuning()));
            }

            if (this.bean.isDebugWorkContextSet()) {
               buf.append("DebugWorkContext");
               buf.append(String.valueOf(this.bean.getDebugWorkContext()));
            }

            if (this.bean.isForceGCEachDGCPeriodSet()) {
               buf.append("ForceGCEachDGCPeriod");
               buf.append(String.valueOf(this.bean.getForceGCEachDGCPeriod()));
            }

            if (this.bean.isLogDGCStatisticsSet()) {
               buf.append("LogDGCStatistics");
               buf.append(String.valueOf(this.bean.getLogDGCStatistics()));
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
            KernelDebugMBeanImpl otherTyped = (KernelDebugMBeanImpl)other;
            this.computeDiff("DebugAbbreviation", this.bean.getDebugAbbreviation(), otherTyped.getDebugAbbreviation(), false);
            this.computeDiff("DebugConnection", this.bean.getDebugConnection(), otherTyped.getDebugConnection(), false);
            this.computeDiff("DebugDGCEnrollment", this.bean.getDebugDGCEnrollment(), otherTyped.getDebugDGCEnrollment(), false);
            this.computeDiff("DebugFailOver", this.bean.getDebugFailOver(), otherTyped.getDebugFailOver(), false);
            this.computeDiff("DebugIIOP", this.bean.getDebugIIOP(), otherTyped.getDebugIIOP(), true);
            this.computeDiff("DebugIIOPConnection", this.bean.getDebugIIOPConnection(), otherTyped.getDebugIIOPConnection(), true);
            this.computeDiff("DebugIIOPMarshal", this.bean.getDebugIIOPMarshal(), otherTyped.getDebugIIOPMarshal(), true);
            this.computeDiff("DebugIIOPOTS", this.bean.getDebugIIOPOTS(), otherTyped.getDebugIIOPOTS(), true);
            this.computeDiff("DebugIIOPReplacer", this.bean.getDebugIIOPReplacer(), otherTyped.getDebugIIOPReplacer(), true);
            this.computeDiff("DebugIIOPSecurity", this.bean.getDebugIIOPSecurity(), otherTyped.getDebugIIOPSecurity(), true);
            this.computeDiff("DebugIIOPStartup", this.bean.getDebugIIOPStartup(), otherTyped.getDebugIIOPStartup(), true);
            this.computeDiff("DebugIIOPTransport", this.bean.getDebugIIOPTransport(), otherTyped.getDebugIIOPTransport(), true);
            this.computeDiff("DebugLoadBalancing", this.bean.getDebugLoadBalancing(), otherTyped.getDebugLoadBalancing(), false);
            this.computeDiff("DebugLocalRemoteJVM", this.bean.getDebugLocalRemoteJVM(), otherTyped.getDebugLocalRemoteJVM(), false);
            this.computeDiff("DebugMessaging", this.bean.getDebugMessaging(), otherTyped.getDebugMessaging(), false);
            this.computeDiff("DebugMuxer", this.bean.getDebugMuxer(), otherTyped.getDebugMuxer(), false);
            this.computeDiff("DebugMuxerConnection", this.bean.getDebugMuxerConnection(), otherTyped.getDebugMuxerConnection(), false);
            this.computeDiff("DebugMuxerDetail", this.bean.getDebugMuxerDetail(), otherTyped.getDebugMuxerDetail(), false);
            this.computeDiff("DebugMuxerException", this.bean.getDebugMuxerException(), otherTyped.getDebugMuxerException(), false);
            this.computeDiff("DebugMuxerTimeout", this.bean.getDebugMuxerTimeout(), otherTyped.getDebugMuxerTimeout(), false);
            this.computeDiff("DebugParameters", this.bean.getDebugParameters(), otherTyped.getDebugParameters(), true);
            this.computeDiff("DebugRC4", this.bean.getDebugRC4(), otherTyped.getDebugRC4(), false);
            this.computeDiff("DebugRSA", this.bean.getDebugRSA(), otherTyped.getDebugRSA(), false);
            this.computeDiff("DebugRouting", this.bean.getDebugRouting(), otherTyped.getDebugRouting(), false);
            this.computeDiff("DebugSSL", this.bean.getDebugSSL(), otherTyped.getDebugSSL(), false);
            this.computeDiff("DebugSelfTuning", this.bean.getDebugSelfTuning(), otherTyped.getDebugSelfTuning(), true);
            this.computeDiff("DebugWorkContext", this.bean.getDebugWorkContext(), otherTyped.getDebugWorkContext(), false);
            this.computeDiff("ForceGCEachDGCPeriod", this.bean.getForceGCEachDGCPeriod(), otherTyped.getForceGCEachDGCPeriod(), false);
            this.computeDiff("LogDGCStatistics", this.bean.getLogDGCStatistics(), otherTyped.getLogDGCStatistics(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            KernelDebugMBeanImpl original = (KernelDebugMBeanImpl)event.getSourceBean();
            KernelDebugMBeanImpl proposed = (KernelDebugMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DebugAbbreviation")) {
                  original.setDebugAbbreviation(proposed.getDebugAbbreviation());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("DebugConnection")) {
                  original.setDebugConnection(proposed.getDebugConnection());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("DebugDGCEnrollment")) {
                  original.setDebugDGCEnrollment(proposed.getDebugDGCEnrollment());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("DebugFailOver")) {
                  original.setDebugFailOver(proposed.getDebugFailOver());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("DebugIIOP")) {
                  original.setDebugIIOP(proposed.getDebugIIOP());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("DebugIIOPConnection")) {
                  original.setDebugIIOPConnection(proposed.getDebugIIOPConnection());
                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("DebugIIOPMarshal")) {
                  original.setDebugIIOPMarshal(proposed.getDebugIIOPMarshal());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("DebugIIOPOTS")) {
                  original.setDebugIIOPOTS(proposed.getDebugIIOPOTS());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("DebugIIOPReplacer")) {
                  original.setDebugIIOPReplacer(proposed.getDebugIIOPReplacer());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("DebugIIOPSecurity")) {
                  original.setDebugIIOPSecurity(proposed.getDebugIIOPSecurity());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("DebugIIOPStartup")) {
                  original.setDebugIIOPStartup(proposed.getDebugIIOPStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("DebugIIOPTransport")) {
                  original.setDebugIIOPTransport(proposed.getDebugIIOPTransport());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("DebugLoadBalancing")) {
                  original.setDebugLoadBalancing(proposed.getDebugLoadBalancing());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("DebugLocalRemoteJVM")) {
                  original.setDebugLocalRemoteJVM(proposed.getDebugLocalRemoteJVM());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("DebugMessaging")) {
                  original.setDebugMessaging(proposed.getDebugMessaging());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DebugMuxer")) {
                  original.setDebugMuxer(proposed.getDebugMuxer());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("DebugMuxerConnection")) {
                  original.setDebugMuxerConnection(proposed.getDebugMuxerConnection());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("DebugMuxerDetail")) {
                  original.setDebugMuxerDetail(proposed.getDebugMuxerDetail());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("DebugMuxerException")) {
                  original.setDebugMuxerException(proposed.getDebugMuxerException());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("DebugMuxerTimeout")) {
                  original.setDebugMuxerTimeout(proposed.getDebugMuxerTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("DebugParameters")) {
                  original.setDebugParameters(proposed.getDebugParameters() == null ? null : (Properties)proposed.getDebugParameters().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("DebugRC4")) {
                  original.setDebugRC4(proposed.getDebugRC4());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("DebugRSA")) {
                  original.setDebugRSA(proposed.getDebugRSA());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("DebugRouting")) {
                  original.setDebugRouting(proposed.getDebugRouting());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("DebugSSL")) {
                  original.setDebugSSL(proposed.getDebugSSL());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("DebugSelfTuning")) {
                  original.setDebugSelfTuning(proposed.getDebugSelfTuning());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("DebugWorkContext")) {
                  original.setDebugWorkContext(proposed.getDebugWorkContext());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("ForceGCEachDGCPeriod")) {
                  original.setForceGCEachDGCPeriod(proposed.getForceGCEachDGCPeriod());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("LogDGCStatistics")) {
                  original.setLogDGCStatistics(proposed.getLogDGCStatistics());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
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
            KernelDebugMBeanImpl copy = (KernelDebugMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DebugAbbreviation")) && this.bean.isDebugAbbreviationSet()) {
               copy.setDebugAbbreviation(this.bean.getDebugAbbreviation());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugConnection")) && this.bean.isDebugConnectionSet()) {
               copy.setDebugConnection(this.bean.getDebugConnection());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugDGCEnrollment")) && this.bean.isDebugDGCEnrollmentSet()) {
               copy.setDebugDGCEnrollment(this.bean.getDebugDGCEnrollment());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugFailOver")) && this.bean.isDebugFailOverSet()) {
               copy.setDebugFailOver(this.bean.getDebugFailOver());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugIIOP")) && this.bean.isDebugIIOPSet()) {
               copy.setDebugIIOP(this.bean.getDebugIIOP());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugIIOPConnection")) && this.bean.isDebugIIOPConnectionSet()) {
               copy.setDebugIIOPConnection(this.bean.getDebugIIOPConnection());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugIIOPMarshal")) && this.bean.isDebugIIOPMarshalSet()) {
               copy.setDebugIIOPMarshal(this.bean.getDebugIIOPMarshal());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugIIOPOTS")) && this.bean.isDebugIIOPOTSSet()) {
               copy.setDebugIIOPOTS(this.bean.getDebugIIOPOTS());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugIIOPReplacer")) && this.bean.isDebugIIOPReplacerSet()) {
               copy.setDebugIIOPReplacer(this.bean.getDebugIIOPReplacer());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugIIOPSecurity")) && this.bean.isDebugIIOPSecuritySet()) {
               copy.setDebugIIOPSecurity(this.bean.getDebugIIOPSecurity());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugIIOPStartup")) && this.bean.isDebugIIOPStartupSet()) {
               copy.setDebugIIOPStartup(this.bean.getDebugIIOPStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugIIOPTransport")) && this.bean.isDebugIIOPTransportSet()) {
               copy.setDebugIIOPTransport(this.bean.getDebugIIOPTransport());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugLoadBalancing")) && this.bean.isDebugLoadBalancingSet()) {
               copy.setDebugLoadBalancing(this.bean.getDebugLoadBalancing());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugLocalRemoteJVM")) && this.bean.isDebugLocalRemoteJVMSet()) {
               copy.setDebugLocalRemoteJVM(this.bean.getDebugLocalRemoteJVM());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugMessaging")) && this.bean.isDebugMessagingSet()) {
               copy.setDebugMessaging(this.bean.getDebugMessaging());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugMuxer")) && this.bean.isDebugMuxerSet()) {
               copy.setDebugMuxer(this.bean.getDebugMuxer());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugMuxerConnection")) && this.bean.isDebugMuxerConnectionSet()) {
               copy.setDebugMuxerConnection(this.bean.getDebugMuxerConnection());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugMuxerDetail")) && this.bean.isDebugMuxerDetailSet()) {
               copy.setDebugMuxerDetail(this.bean.getDebugMuxerDetail());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugMuxerException")) && this.bean.isDebugMuxerExceptionSet()) {
               copy.setDebugMuxerException(this.bean.getDebugMuxerException());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugMuxerTimeout")) && this.bean.isDebugMuxerTimeoutSet()) {
               copy.setDebugMuxerTimeout(this.bean.getDebugMuxerTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugParameters")) && this.bean.isDebugParametersSet()) {
               copy.setDebugParameters(this.bean.getDebugParameters());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugRC4")) && this.bean.isDebugRC4Set()) {
               copy.setDebugRC4(this.bean.getDebugRC4());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugRSA")) && this.bean.isDebugRSASet()) {
               copy.setDebugRSA(this.bean.getDebugRSA());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugRouting")) && this.bean.isDebugRoutingSet()) {
               copy.setDebugRouting(this.bean.getDebugRouting());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugSSL")) && this.bean.isDebugSSLSet()) {
               copy.setDebugSSL(this.bean.getDebugSSL());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugSelfTuning")) && this.bean.isDebugSelfTuningSet()) {
               copy.setDebugSelfTuning(this.bean.getDebugSelfTuning());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugWorkContext")) && this.bean.isDebugWorkContextSet()) {
               copy.setDebugWorkContext(this.bean.getDebugWorkContext());
            }

            if ((excludeProps == null || !excludeProps.contains("ForceGCEachDGCPeriod")) && this.bean.isForceGCEachDGCPeriodSet()) {
               copy.setForceGCEachDGCPeriod(this.bean.getForceGCEachDGCPeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("LogDGCStatistics")) && this.bean.isLogDGCStatisticsSet()) {
               copy.setLogDGCStatistics(this.bean.getLogDGCStatistics());
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
