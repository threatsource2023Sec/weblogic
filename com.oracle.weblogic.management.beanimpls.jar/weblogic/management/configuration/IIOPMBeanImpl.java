package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.utils.collections.CombinedIterator;

public class IIOPMBeanImpl extends ConfigurationMBeanImpl implements IIOPMBean, Serializable {
   private int _CompleteMessageTimeout;
   private String _DefaultCharCodeset;
   private int _DefaultMinorVersion;
   private String _DefaultWideCharCodeset;
   private boolean _EnableIORServlet;
   private int _IdleConnectionTimeout;
   private String _LocationForwardPolicy;
   private int _MaxMessageSize;
   private String _SystemSecurity;
   private String _TxMechanism;
   private boolean _UseFullRepositoryIdList;
   private boolean _UseJavaSerialization;
   private boolean _UseLocateRequest;
   private boolean _UseSerialFormatVersion2;
   private boolean _UseStatefulAuthentication;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private IIOPMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(IIOPMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(IIOPMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public IIOPMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(IIOPMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      IIOPMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public IIOPMBeanImpl() {
      this._initializeProperty(-1);
   }

   public IIOPMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public IIOPMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getIdleConnectionTimeout() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getIdleConnectionTimeout() : this._IdleConnectionTimeout;
   }

   public boolean isIdleConnectionTimeoutInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isIdleConnectionTimeoutSet() {
      return this._isSet(10);
   }

   public void setIdleConnectionTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("IdleConnectionTimeout", param0, -1);
      boolean wasSet = this._isSet(10);
      int _oldVal = this._IdleConnectionTimeout;
      this._IdleConnectionTimeout = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteMessageTimeout() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().getCompleteMessageTimeout() : this._CompleteMessageTimeout;
   }

   public boolean isCompleteMessageTimeoutInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isCompleteMessageTimeoutSet() {
      return this._isSet(11);
   }

   public void setCompleteMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteMessageTimeout", (long)param0, -1L, 480L);
      boolean wasSet = this._isSet(11);
      int _oldVal = this._CompleteMessageTimeout;
      this._CompleteMessageTimeout = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxMessageSize() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getMaxMessageSize() : this._MaxMessageSize;
   }

   public boolean isMaxMessageSizeInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isMaxMessageSizeSet() {
      return this._isSet(12);
   }

   public void setMaxMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      IIOPValidator.validateMaxMessageSize(param0);
      boolean wasSet = this._isSet(12);
      int _oldVal = this._MaxMessageSize;
      this._MaxMessageSize = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public int getDefaultMinorVersion() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getDefaultMinorVersion() : this._DefaultMinorVersion;
   }

   public boolean isDefaultMinorVersionInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isDefaultMinorVersionSet() {
      return this._isSet(13);
   }

   public void setDefaultMinorVersion(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DefaultMinorVersion", (long)param0, 0L, 2L);
      boolean wasSet = this._isSet(13);
      int _oldVal = this._DefaultMinorVersion;
      this._DefaultMinorVersion = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getUseLocateRequest() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getUseLocateRequest() : this._UseLocateRequest;
   }

   public boolean isUseLocateRequestInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isUseLocateRequestSet() {
      return this._isSet(14);
   }

   public void setUseLocateRequest(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      boolean _oldVal = this._UseLocateRequest;
      this._UseLocateRequest = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTxMechanism() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getTxMechanism(), this) : this._TxMechanism;
   }

   public boolean isTxMechanismInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isTxMechanismSet() {
      return this._isSet(15);
   }

   public void setTxMechanism(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"OTS", "JTA", "OTSv11", "none"};
      param0 = LegalChecks.checkInEnum("TxMechanism", param0, _set);
      boolean wasSet = this._isSet(15);
      String _oldVal = this._TxMechanism;
      this._TxMechanism = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var5.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLocationForwardPolicy() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getLocationForwardPolicy(), this) : this._LocationForwardPolicy;
   }

   public boolean isLocationForwardPolicyInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isLocationForwardPolicySet() {
      return this._isSet(16);
   }

   public void setLocationForwardPolicy(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"off", "failover", "round-robin", "random"};
      param0 = LegalChecks.checkInEnum("LocationForwardPolicy", param0, _set);
      boolean wasSet = this._isSet(16);
      String _oldVal = this._LocationForwardPolicy;
      this._LocationForwardPolicy = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var5.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultWideCharCodeset() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultWideCharCodeset(), this) : this._DefaultWideCharCodeset;
   }

   public boolean isDefaultWideCharCodesetInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isDefaultWideCharCodesetSet() {
      return this._isSet(17);
   }

   public void setDefaultWideCharCodeset(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"UCS-2", "UTF-16", "UTF-8", "UTF-16BE", "UTF-16LE"};
      param0 = LegalChecks.checkInEnum("DefaultWideCharCodeset", param0, _set);
      boolean wasSet = this._isSet(17);
      String _oldVal = this._DefaultWideCharCodeset;
      this._DefaultWideCharCodeset = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var5.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultCharCodeset() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultCharCodeset(), this) : this._DefaultCharCodeset;
   }

   public boolean isDefaultCharCodesetInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isDefaultCharCodesetSet() {
      return this._isSet(18);
   }

   public void setDefaultCharCodeset(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"US-ASCII", "UTF-8", "ISO-8859-1"};
      param0 = LegalChecks.checkInEnum("DefaultCharCodeset", param0, _set);
      boolean wasSet = this._isSet(18);
      String _oldVal = this._DefaultCharCodeset;
      this._DefaultCharCodeset = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var5.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getUseFullRepositoryIdList() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getUseFullRepositoryIdList() : this._UseFullRepositoryIdList;
   }

   public boolean isUseFullRepositoryIdListInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isUseFullRepositoryIdListSet() {
      return this._isSet(19);
   }

   public void setUseFullRepositoryIdList(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      boolean _oldVal = this._UseFullRepositoryIdList;
      this._UseFullRepositoryIdList = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getUseStatefulAuthentication() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getUseStatefulAuthentication() : this._UseStatefulAuthentication;
   }

   public boolean isUseStatefulAuthenticationInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isUseStatefulAuthenticationSet() {
      return this._isSet(20);
   }

   public void setUseStatefulAuthentication(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      boolean _oldVal = this._UseStatefulAuthentication;
      this._UseStatefulAuthentication = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getUseSerialFormatVersion2() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getUseSerialFormatVersion2() : this._UseSerialFormatVersion2;
   }

   public boolean isUseSerialFormatVersion2Inherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isUseSerialFormatVersion2Set() {
      return this._isSet(21);
   }

   public void setUseSerialFormatVersion2(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      boolean _oldVal = this._UseSerialFormatVersion2;
      this._UseSerialFormatVersion2 = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getEnableIORServlet() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().getEnableIORServlet() : this._EnableIORServlet;
   }

   public boolean isEnableIORServletInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isEnableIORServletSet() {
      return this._isSet(22);
   }

   public void setEnableIORServlet(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      boolean _oldVal = this._EnableIORServlet;
      this._EnableIORServlet = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getUseJavaSerialization() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getUseJavaSerialization() : this._UseJavaSerialization;
   }

   public boolean isUseJavaSerializationInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isUseJavaSerializationSet() {
      return this._isSet(23);
   }

   public void setUseJavaSerialization(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      boolean _oldVal = this._UseJavaSerialization;
      this._UseJavaSerialization = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSystemSecurity() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getSystemSecurity(), this) : this._SystemSecurity;
   }

   public boolean isSystemSecurityInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isSystemSecuritySet() {
      return this._isSet(24);
   }

   public void setSystemSecurity(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"none", "supported", "required"};
      param0 = LegalChecks.checkInEnum("SystemSecurity", param0, _set);
      boolean wasSet = this._isSet(24);
      String _oldVal = this._SystemSecurity;
      this._SystemSecurity = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         IIOPMBeanImpl source = (IIOPMBeanImpl)var5.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
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
               this._CompleteMessageTimeout = -1;
               if (initOne) {
                  break;
               }
            case 18:
               this._DefaultCharCodeset = "US-ASCII";
               if (initOne) {
                  break;
               }
            case 13:
               this._DefaultMinorVersion = 2;
               if (initOne) {
                  break;
               }
            case 17:
               this._DefaultWideCharCodeset = "UCS-2";
               if (initOne) {
                  break;
               }
            case 22:
               this._EnableIORServlet = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._IdleConnectionTimeout = -1;
               if (initOne) {
                  break;
               }
            case 16:
               this._LocationForwardPolicy = "off";
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxMessageSize = -1;
               if (initOne) {
                  break;
               }
            case 24:
               this._SystemSecurity = "supported";
               if (initOne) {
                  break;
               }
            case 15:
               this._TxMechanism = "OTS";
               if (initOne) {
                  break;
               }
            case 19:
               this._UseFullRepositoryIdList = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._UseJavaSerialization = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._UseLocateRequest = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._UseSerialFormatVersion2 = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._UseStatefulAuthentication = true;
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
      return "IIOP";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("CompleteMessageTimeout")) {
         oldVal = this._CompleteMessageTimeout;
         this._CompleteMessageTimeout = (Integer)v;
         this._postSet(11, oldVal, this._CompleteMessageTimeout);
      } else {
         String oldVal;
         if (name.equals("DefaultCharCodeset")) {
            oldVal = this._DefaultCharCodeset;
            this._DefaultCharCodeset = (String)v;
            this._postSet(18, oldVal, this._DefaultCharCodeset);
         } else if (name.equals("DefaultMinorVersion")) {
            oldVal = this._DefaultMinorVersion;
            this._DefaultMinorVersion = (Integer)v;
            this._postSet(13, oldVal, this._DefaultMinorVersion);
         } else if (name.equals("DefaultWideCharCodeset")) {
            oldVal = this._DefaultWideCharCodeset;
            this._DefaultWideCharCodeset = (String)v;
            this._postSet(17, oldVal, this._DefaultWideCharCodeset);
         } else {
            boolean oldVal;
            if (name.equals("EnableIORServlet")) {
               oldVal = this._EnableIORServlet;
               this._EnableIORServlet = (Boolean)v;
               this._postSet(22, oldVal, this._EnableIORServlet);
            } else if (name.equals("IdleConnectionTimeout")) {
               oldVal = this._IdleConnectionTimeout;
               this._IdleConnectionTimeout = (Integer)v;
               this._postSet(10, oldVal, this._IdleConnectionTimeout);
            } else if (name.equals("LocationForwardPolicy")) {
               oldVal = this._LocationForwardPolicy;
               this._LocationForwardPolicy = (String)v;
               this._postSet(16, oldVal, this._LocationForwardPolicy);
            } else if (name.equals("MaxMessageSize")) {
               oldVal = this._MaxMessageSize;
               this._MaxMessageSize = (Integer)v;
               this._postSet(12, oldVal, this._MaxMessageSize);
            } else if (name.equals("SystemSecurity")) {
               oldVal = this._SystemSecurity;
               this._SystemSecurity = (String)v;
               this._postSet(24, oldVal, this._SystemSecurity);
            } else if (name.equals("TxMechanism")) {
               oldVal = this._TxMechanism;
               this._TxMechanism = (String)v;
               this._postSet(15, oldVal, this._TxMechanism);
            } else if (name.equals("UseFullRepositoryIdList")) {
               oldVal = this._UseFullRepositoryIdList;
               this._UseFullRepositoryIdList = (Boolean)v;
               this._postSet(19, oldVal, this._UseFullRepositoryIdList);
            } else if (name.equals("UseJavaSerialization")) {
               oldVal = this._UseJavaSerialization;
               this._UseJavaSerialization = (Boolean)v;
               this._postSet(23, oldVal, this._UseJavaSerialization);
            } else if (name.equals("UseLocateRequest")) {
               oldVal = this._UseLocateRequest;
               this._UseLocateRequest = (Boolean)v;
               this._postSet(14, oldVal, this._UseLocateRequest);
            } else if (name.equals("UseSerialFormatVersion2")) {
               oldVal = this._UseSerialFormatVersion2;
               this._UseSerialFormatVersion2 = (Boolean)v;
               this._postSet(21, oldVal, this._UseSerialFormatVersion2);
            } else if (name.equals("UseStatefulAuthentication")) {
               oldVal = this._UseStatefulAuthentication;
               this._UseStatefulAuthentication = (Boolean)v;
               this._postSet(20, oldVal, this._UseStatefulAuthentication);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CompleteMessageTimeout")) {
         return new Integer(this._CompleteMessageTimeout);
      } else if (name.equals("DefaultCharCodeset")) {
         return this._DefaultCharCodeset;
      } else if (name.equals("DefaultMinorVersion")) {
         return new Integer(this._DefaultMinorVersion);
      } else if (name.equals("DefaultWideCharCodeset")) {
         return this._DefaultWideCharCodeset;
      } else if (name.equals("EnableIORServlet")) {
         return new Boolean(this._EnableIORServlet);
      } else if (name.equals("IdleConnectionTimeout")) {
         return new Integer(this._IdleConnectionTimeout);
      } else if (name.equals("LocationForwardPolicy")) {
         return this._LocationForwardPolicy;
      } else if (name.equals("MaxMessageSize")) {
         return new Integer(this._MaxMessageSize);
      } else if (name.equals("SystemSecurity")) {
         return this._SystemSecurity;
      } else if (name.equals("TxMechanism")) {
         return this._TxMechanism;
      } else if (name.equals("UseFullRepositoryIdList")) {
         return new Boolean(this._UseFullRepositoryIdList);
      } else if (name.equals("UseJavaSerialization")) {
         return new Boolean(this._UseJavaSerialization);
      } else if (name.equals("UseLocateRequest")) {
         return new Boolean(this._UseLocateRequest);
      } else if (name.equals("UseSerialFormatVersion2")) {
         return new Boolean(this._UseSerialFormatVersion2);
      } else {
         return name.equals("UseStatefulAuthentication") ? new Boolean(this._UseStatefulAuthentication) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("tx-mechanism")) {
                  return 15;
               }
            case 13:
            case 14:
            case 19:
            default:
               break;
            case 15:
               if (s.equals("system-security")) {
                  return 24;
               }
               break;
            case 16:
               if (s.equals("max-message-size")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("enableior-servlet")) {
                  return 22;
               }
               break;
            case 18:
               if (s.equals("use-locate-request")) {
                  return 14;
               }
               break;
            case 20:
               if (s.equals("default-char-codeset")) {
                  return 18;
               }
               break;
            case 21:
               if (s.equals("default-minor-version")) {
                  return 13;
               }
               break;
            case 22:
               if (s.equals("use-java-serialization")) {
                  return 23;
               }
               break;
            case 23:
               if (s.equals("idle-connection-timeout")) {
                  return 10;
               }

               if (s.equals("location-forward-policy")) {
                  return 16;
               }
               break;
            case 24:
               if (s.equals("complete-message-timeout")) {
                  return 11;
               }
               break;
            case 25:
               if (s.equals("default-wide-char-codeset")) {
                  return 17;
               }
               break;
            case 26:
               if (s.equals("use-serial-format-version2")) {
                  return 21;
               }
               break;
            case 27:
               if (s.equals("use-full-repository-id-list")) {
                  return 19;
               }

               if (s.equals("use-stateful-authentication")) {
                  return 20;
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
               return "idle-connection-timeout";
            case 11:
               return "complete-message-timeout";
            case 12:
               return "max-message-size";
            case 13:
               return "default-minor-version";
            case 14:
               return "use-locate-request";
            case 15:
               return "tx-mechanism";
            case 16:
               return "location-forward-policy";
            case 17:
               return "default-wide-char-codeset";
            case 18:
               return "default-char-codeset";
            case 19:
               return "use-full-repository-id-list";
            case 20:
               return "use-stateful-authentication";
            case 21:
               return "use-serial-format-version2";
            case 22:
               return "enableior-servlet";
            case 23:
               return "use-java-serialization";
            case 24:
               return "system-security";
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

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
            default:
               return super.isConfigurable(propIndex);
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
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
      private IIOPMBeanImpl bean;

      protected Helper(IIOPMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "IdleConnectionTimeout";
            case 11:
               return "CompleteMessageTimeout";
            case 12:
               return "MaxMessageSize";
            case 13:
               return "DefaultMinorVersion";
            case 14:
               return "UseLocateRequest";
            case 15:
               return "TxMechanism";
            case 16:
               return "LocationForwardPolicy";
            case 17:
               return "DefaultWideCharCodeset";
            case 18:
               return "DefaultCharCodeset";
            case 19:
               return "UseFullRepositoryIdList";
            case 20:
               return "UseStatefulAuthentication";
            case 21:
               return "UseSerialFormatVersion2";
            case 22:
               return "EnableIORServlet";
            case 23:
               return "UseJavaSerialization";
            case 24:
               return "SystemSecurity";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CompleteMessageTimeout")) {
            return 11;
         } else if (propName.equals("DefaultCharCodeset")) {
            return 18;
         } else if (propName.equals("DefaultMinorVersion")) {
            return 13;
         } else if (propName.equals("DefaultWideCharCodeset")) {
            return 17;
         } else if (propName.equals("EnableIORServlet")) {
            return 22;
         } else if (propName.equals("IdleConnectionTimeout")) {
            return 10;
         } else if (propName.equals("LocationForwardPolicy")) {
            return 16;
         } else if (propName.equals("MaxMessageSize")) {
            return 12;
         } else if (propName.equals("SystemSecurity")) {
            return 24;
         } else if (propName.equals("TxMechanism")) {
            return 15;
         } else if (propName.equals("UseFullRepositoryIdList")) {
            return 19;
         } else if (propName.equals("UseJavaSerialization")) {
            return 23;
         } else if (propName.equals("UseLocateRequest")) {
            return 14;
         } else if (propName.equals("UseSerialFormatVersion2")) {
            return 21;
         } else {
            return propName.equals("UseStatefulAuthentication") ? 20 : super.getPropertyIndex(propName);
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
            if (this.bean.isCompleteMessageTimeoutSet()) {
               buf.append("CompleteMessageTimeout");
               buf.append(String.valueOf(this.bean.getCompleteMessageTimeout()));
            }

            if (this.bean.isDefaultCharCodesetSet()) {
               buf.append("DefaultCharCodeset");
               buf.append(String.valueOf(this.bean.getDefaultCharCodeset()));
            }

            if (this.bean.isDefaultMinorVersionSet()) {
               buf.append("DefaultMinorVersion");
               buf.append(String.valueOf(this.bean.getDefaultMinorVersion()));
            }

            if (this.bean.isDefaultWideCharCodesetSet()) {
               buf.append("DefaultWideCharCodeset");
               buf.append(String.valueOf(this.bean.getDefaultWideCharCodeset()));
            }

            if (this.bean.isEnableIORServletSet()) {
               buf.append("EnableIORServlet");
               buf.append(String.valueOf(this.bean.getEnableIORServlet()));
            }

            if (this.bean.isIdleConnectionTimeoutSet()) {
               buf.append("IdleConnectionTimeout");
               buf.append(String.valueOf(this.bean.getIdleConnectionTimeout()));
            }

            if (this.bean.isLocationForwardPolicySet()) {
               buf.append("LocationForwardPolicy");
               buf.append(String.valueOf(this.bean.getLocationForwardPolicy()));
            }

            if (this.bean.isMaxMessageSizeSet()) {
               buf.append("MaxMessageSize");
               buf.append(String.valueOf(this.bean.getMaxMessageSize()));
            }

            if (this.bean.isSystemSecuritySet()) {
               buf.append("SystemSecurity");
               buf.append(String.valueOf(this.bean.getSystemSecurity()));
            }

            if (this.bean.isTxMechanismSet()) {
               buf.append("TxMechanism");
               buf.append(String.valueOf(this.bean.getTxMechanism()));
            }

            if (this.bean.isUseFullRepositoryIdListSet()) {
               buf.append("UseFullRepositoryIdList");
               buf.append(String.valueOf(this.bean.getUseFullRepositoryIdList()));
            }

            if (this.bean.isUseJavaSerializationSet()) {
               buf.append("UseJavaSerialization");
               buf.append(String.valueOf(this.bean.getUseJavaSerialization()));
            }

            if (this.bean.isUseLocateRequestSet()) {
               buf.append("UseLocateRequest");
               buf.append(String.valueOf(this.bean.getUseLocateRequest()));
            }

            if (this.bean.isUseSerialFormatVersion2Set()) {
               buf.append("UseSerialFormatVersion2");
               buf.append(String.valueOf(this.bean.getUseSerialFormatVersion2()));
            }

            if (this.bean.isUseStatefulAuthenticationSet()) {
               buf.append("UseStatefulAuthentication");
               buf.append(String.valueOf(this.bean.getUseStatefulAuthentication()));
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
            IIOPMBeanImpl otherTyped = (IIOPMBeanImpl)other;
            this.computeDiff("CompleteMessageTimeout", this.bean.getCompleteMessageTimeout(), otherTyped.getCompleteMessageTimeout(), true);
            this.computeDiff("DefaultCharCodeset", this.bean.getDefaultCharCodeset(), otherTyped.getDefaultCharCodeset(), false);
            this.computeDiff("DefaultMinorVersion", this.bean.getDefaultMinorVersion(), otherTyped.getDefaultMinorVersion(), false);
            this.computeDiff("DefaultWideCharCodeset", this.bean.getDefaultWideCharCodeset(), otherTyped.getDefaultWideCharCodeset(), false);
            this.computeDiff("EnableIORServlet", this.bean.getEnableIORServlet(), otherTyped.getEnableIORServlet(), false);
            this.computeDiff("IdleConnectionTimeout", this.bean.getIdleConnectionTimeout(), otherTyped.getIdleConnectionTimeout(), true);
            this.computeDiff("LocationForwardPolicy", this.bean.getLocationForwardPolicy(), otherTyped.getLocationForwardPolicy(), false);
            this.computeDiff("MaxMessageSize", this.bean.getMaxMessageSize(), otherTyped.getMaxMessageSize(), false);
            this.computeDiff("SystemSecurity", this.bean.getSystemSecurity(), otherTyped.getSystemSecurity(), false);
            this.computeDiff("TxMechanism", this.bean.getTxMechanism(), otherTyped.getTxMechanism(), false);
            this.computeDiff("UseFullRepositoryIdList", this.bean.getUseFullRepositoryIdList(), otherTyped.getUseFullRepositoryIdList(), false);
            this.computeDiff("UseJavaSerialization", this.bean.getUseJavaSerialization(), otherTyped.getUseJavaSerialization(), true);
            this.computeDiff("UseLocateRequest", this.bean.getUseLocateRequest(), otherTyped.getUseLocateRequest(), false);
            this.computeDiff("UseSerialFormatVersion2", this.bean.getUseSerialFormatVersion2(), otherTyped.getUseSerialFormatVersion2(), false);
            this.computeDiff("UseStatefulAuthentication", this.bean.getUseStatefulAuthentication(), otherTyped.getUseStatefulAuthentication(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            IIOPMBeanImpl original = (IIOPMBeanImpl)event.getSourceBean();
            IIOPMBeanImpl proposed = (IIOPMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CompleteMessageTimeout")) {
                  original.setCompleteMessageTimeout(proposed.getCompleteMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("DefaultCharCodeset")) {
                  original.setDefaultCharCodeset(proposed.getDefaultCharCodeset());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("DefaultMinorVersion")) {
                  original.setDefaultMinorVersion(proposed.getDefaultMinorVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DefaultWideCharCodeset")) {
                  original.setDefaultWideCharCodeset(proposed.getDefaultWideCharCodeset());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("EnableIORServlet")) {
                  original.setEnableIORServlet(proposed.getEnableIORServlet());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("IdleConnectionTimeout")) {
                  original.setIdleConnectionTimeout(proposed.getIdleConnectionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("LocationForwardPolicy")) {
                  original.setLocationForwardPolicy(proposed.getLocationForwardPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("MaxMessageSize")) {
                  original.setMaxMessageSize(proposed.getMaxMessageSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("SystemSecurity")) {
                  original.setSystemSecurity(proposed.getSystemSecurity());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("TxMechanism")) {
                  original.setTxMechanism(proposed.getTxMechanism());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("UseFullRepositoryIdList")) {
                  original.setUseFullRepositoryIdList(proposed.getUseFullRepositoryIdList());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("UseJavaSerialization")) {
                  original.setUseJavaSerialization(proposed.getUseJavaSerialization());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("UseLocateRequest")) {
                  original.setUseLocateRequest(proposed.getUseLocateRequest());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("UseSerialFormatVersion2")) {
                  original.setUseSerialFormatVersion2(proposed.getUseSerialFormatVersion2());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("UseStatefulAuthentication")) {
                  original.setUseStatefulAuthentication(proposed.getUseStatefulAuthentication());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
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
            IIOPMBeanImpl copy = (IIOPMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CompleteMessageTimeout")) && this.bean.isCompleteMessageTimeoutSet()) {
               copy.setCompleteMessageTimeout(this.bean.getCompleteMessageTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultCharCodeset")) && this.bean.isDefaultCharCodesetSet()) {
               copy.setDefaultCharCodeset(this.bean.getDefaultCharCodeset());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultMinorVersion")) && this.bean.isDefaultMinorVersionSet()) {
               copy.setDefaultMinorVersion(this.bean.getDefaultMinorVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultWideCharCodeset")) && this.bean.isDefaultWideCharCodesetSet()) {
               copy.setDefaultWideCharCodeset(this.bean.getDefaultWideCharCodeset());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableIORServlet")) && this.bean.isEnableIORServletSet()) {
               copy.setEnableIORServlet(this.bean.getEnableIORServlet());
            }

            if ((excludeProps == null || !excludeProps.contains("IdleConnectionTimeout")) && this.bean.isIdleConnectionTimeoutSet()) {
               copy.setIdleConnectionTimeout(this.bean.getIdleConnectionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("LocationForwardPolicy")) && this.bean.isLocationForwardPolicySet()) {
               copy.setLocationForwardPolicy(this.bean.getLocationForwardPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxMessageSize")) && this.bean.isMaxMessageSizeSet()) {
               copy.setMaxMessageSize(this.bean.getMaxMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("SystemSecurity")) && this.bean.isSystemSecuritySet()) {
               copy.setSystemSecurity(this.bean.getSystemSecurity());
            }

            if ((excludeProps == null || !excludeProps.contains("TxMechanism")) && this.bean.isTxMechanismSet()) {
               copy.setTxMechanism(this.bean.getTxMechanism());
            }

            if ((excludeProps == null || !excludeProps.contains("UseFullRepositoryIdList")) && this.bean.isUseFullRepositoryIdListSet()) {
               copy.setUseFullRepositoryIdList(this.bean.getUseFullRepositoryIdList());
            }

            if ((excludeProps == null || !excludeProps.contains("UseJavaSerialization")) && this.bean.isUseJavaSerializationSet()) {
               copy.setUseJavaSerialization(this.bean.getUseJavaSerialization());
            }

            if ((excludeProps == null || !excludeProps.contains("UseLocateRequest")) && this.bean.isUseLocateRequestSet()) {
               copy.setUseLocateRequest(this.bean.getUseLocateRequest());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSerialFormatVersion2")) && this.bean.isUseSerialFormatVersion2Set()) {
               copy.setUseSerialFormatVersion2(this.bean.getUseSerialFormatVersion2());
            }

            if ((excludeProps == null || !excludeProps.contains("UseStatefulAuthentication")) && this.bean.isUseStatefulAuthenticationSet()) {
               copy.setUseStatefulAuthentication(this.bean.getUseStatefulAuthentication());
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
