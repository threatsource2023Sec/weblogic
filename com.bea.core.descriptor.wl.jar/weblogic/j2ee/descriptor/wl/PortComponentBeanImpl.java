package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.AuthConstraintBean;
import weblogic.j2ee.descriptor.AuthConstraintBeanImpl;
import weblogic.j2ee.descriptor.LoginConfigBean;
import weblogic.j2ee.descriptor.LoginConfigBeanImpl;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class PortComponentBeanImpl extends AbstractDescriptorBean implements PortComponentBean, Serializable {
   private AuthConstraintBean _AuthConstraint;
   private BufferingConfigBean _BufferingConfig;
   private String _CallbackProtocol;
   private DeploymentListenerListBean _DeploymentListenerList;
   private boolean _FastInfoset;
   private boolean _HttpFlushResponse;
   private int _HttpResponseBuffersize;
   private String _LoggingLevel;
   private LoginConfigBean _LoginConfig;
   private OperationComponentBean[] _Operations;
   private PersistenceConfigBean _PersistenceConfig;
   private String _PortComponentName;
   private ReliabilityConfigBean _ReliabilityConfig;
   private WebserviceAddressBean _ServiceEndpointAddress;
   private SoapjmsServiceEndpointAddressBean _SoapjmsServiceEndpointAddress;
   private boolean _StreamAttachments;
   private int _TransactionTimeout;
   private String _TransportGuarantee;
   private boolean _ValidateRequest;
   private WSATConfigBean _WSATConfig;
   private WsdlBean _Wsdl;
   private static SchemaHelper2 _schemaHelper;

   public PortComponentBeanImpl() {
      this._initializeProperty(-1);
   }

   public PortComponentBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PortComponentBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getPortComponentName() {
      return this._PortComponentName;
   }

   public boolean isPortComponentNameInherited() {
      return false;
   }

   public boolean isPortComponentNameSet() {
      return this._isSet(0);
   }

   public void setPortComponentName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PortComponentName;
      this._PortComponentName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public WebserviceAddressBean getServiceEndpointAddress() {
      return this._ServiceEndpointAddress;
   }

   public boolean isServiceEndpointAddressInherited() {
      return false;
   }

   public boolean isServiceEndpointAddressSet() {
      return this._isSet(1);
   }

   public void setServiceEndpointAddress(WebserviceAddressBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getServiceEndpointAddress() != null && param0 != this.getServiceEndpointAddress()) {
         throw new BeanAlreadyExistsException(this.getServiceEndpointAddress() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WebserviceAddressBean _oldVal = this._ServiceEndpointAddress;
         this._ServiceEndpointAddress = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public WebserviceAddressBean createServiceEndpointAddress() {
      WebserviceAddressBeanImpl _val = new WebserviceAddressBeanImpl(this, -1);

      try {
         this.setServiceEndpointAddress(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServiceEndpointAddress(WebserviceAddressBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ServiceEndpointAddress;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setServiceEndpointAddress((WebserviceAddressBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public AuthConstraintBean getAuthConstraint() {
      return this._AuthConstraint;
   }

   public boolean isAuthConstraintInherited() {
      return false;
   }

   public boolean isAuthConstraintSet() {
      return this._isSet(2);
   }

   public void setAuthConstraint(AuthConstraintBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAuthConstraint() != null && param0 != this.getAuthConstraint()) {
         throw new BeanAlreadyExistsException(this.getAuthConstraint() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AuthConstraintBean _oldVal = this._AuthConstraint;
         this._AuthConstraint = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public AuthConstraintBean createAuthConstraint() {
      AuthConstraintBeanImpl _val = new AuthConstraintBeanImpl(this, -1);

      try {
         this.setAuthConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAuthConstraint(AuthConstraintBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AuthConstraint;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAuthConstraint((AuthConstraintBean)null);
               this._unSet(2);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public LoginConfigBean getLoginConfig() {
      return this._LoginConfig;
   }

   public boolean isLoginConfigInherited() {
      return false;
   }

   public boolean isLoginConfigSet() {
      return this._isSet(3);
   }

   public void setLoginConfig(LoginConfigBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLoginConfig() != null && param0 != this.getLoginConfig()) {
         throw new BeanAlreadyExistsException(this.getLoginConfig() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LoginConfigBean _oldVal = this._LoginConfig;
         this._LoginConfig = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public LoginConfigBean createLoginConfig() {
      LoginConfigBeanImpl _val = new LoginConfigBeanImpl(this, -1);

      try {
         this.setLoginConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLoginConfig(LoginConfigBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LoginConfig;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLoginConfig((LoginConfigBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public String getTransportGuarantee() {
      return this._TransportGuarantee;
   }

   public boolean isTransportGuaranteeInherited() {
      return false;
   }

   public boolean isTransportGuaranteeSet() {
      return this._isSet(4);
   }

   public void setTransportGuarantee(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NONE", "INTEGRAL", "CONFIDENTIAL"};
      param0 = LegalChecks.checkInEnum("TransportGuarantee", param0, _set);
      String _oldVal = this._TransportGuarantee;
      this._TransportGuarantee = param0;
      this._postSet(4, _oldVal, param0);
   }

   public DeploymentListenerListBean getDeploymentListenerList() {
      return this._DeploymentListenerList;
   }

   public boolean isDeploymentListenerListInherited() {
      return false;
   }

   public boolean isDeploymentListenerListSet() {
      return this._isSet(5);
   }

   public void setDeploymentListenerList(DeploymentListenerListBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDeploymentListenerList() != null && param0 != this.getDeploymentListenerList()) {
         throw new BeanAlreadyExistsException(this.getDeploymentListenerList() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DeploymentListenerListBean _oldVal = this._DeploymentListenerList;
         this._DeploymentListenerList = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public DeploymentListenerListBean createDeploymentListenerList() {
      DeploymentListenerListBeanImpl _val = new DeploymentListenerListBeanImpl(this, -1);

      try {
         this.setDeploymentListenerList(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDeploymentListenerList(DeploymentListenerListBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DeploymentListenerList;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDeploymentListenerList((DeploymentListenerListBean)null);
               this._unSet(5);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public WsdlBean getWsdl() {
      return this._Wsdl;
   }

   public boolean isWsdlInherited() {
      return false;
   }

   public boolean isWsdlSet() {
      return this._isSet(6);
   }

   public void setWsdl(WsdlBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWsdl() != null && param0 != this.getWsdl()) {
         throw new BeanAlreadyExistsException(this.getWsdl() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 6)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WsdlBean _oldVal = this._Wsdl;
         this._Wsdl = param0;
         this._postSet(6, _oldVal, param0);
      }
   }

   public WsdlBean createWsdl() {
      WsdlBeanImpl _val = new WsdlBeanImpl(this, -1);

      try {
         this.setWsdl(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWsdl(WsdlBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Wsdl;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWsdl((WsdlBean)null);
               this._unSet(6);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setTransactionTimeout(int param0) {
      LegalChecks.checkMin("TransactionTimeout", param0, 0);
      int _oldVal = this._TransactionTimeout;
      this._TransactionTimeout = param0;
      this._postSet(7, _oldVal, param0);
   }

   public int getTransactionTimeout() {
      return this._TransactionTimeout;
   }

   public boolean isTransactionTimeoutInherited() {
      return false;
   }

   public boolean isTransactionTimeoutSet() {
      return this._isSet(7);
   }

   public void setCallbackProtocol(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"http", "https", "jms"};
      param0 = LegalChecks.checkInEnum("CallbackProtocol", param0, _set);
      String _oldVal = this._CallbackProtocol;
      this._CallbackProtocol = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getCallbackProtocol() {
      return this._CallbackProtocol;
   }

   public boolean isCallbackProtocolInherited() {
      return false;
   }

   public boolean isCallbackProtocolSet() {
      return this._isSet(8);
   }

   public void setStreamAttachments(boolean param0) {
      boolean _oldVal = this._StreamAttachments;
      this._StreamAttachments = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean getStreamAttachments() {
      return this._StreamAttachments;
   }

   public boolean isStreamAttachmentsInherited() {
      return false;
   }

   public boolean isStreamAttachmentsSet() {
      return this._isSet(9);
   }

   public boolean isValidateRequest() {
      return this._ValidateRequest;
   }

   public boolean isValidateRequestInherited() {
      return false;
   }

   public boolean isValidateRequestSet() {
      return this._isSet(10);
   }

   public void setValidateRequest(boolean param0) {
      boolean _oldVal = this._ValidateRequest;
      this._ValidateRequest = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isHttpFlushResponse() {
      return this._HttpFlushResponse;
   }

   public boolean isHttpFlushResponseInherited() {
      return false;
   }

   public boolean isHttpFlushResponseSet() {
      return this._isSet(11);
   }

   public void setHttpFlushResponse(boolean param0) {
      boolean _oldVal = this._HttpFlushResponse;
      this._HttpFlushResponse = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getHttpResponseBuffersize() {
      return this._HttpResponseBuffersize;
   }

   public boolean isHttpResponseBuffersizeInherited() {
      return false;
   }

   public boolean isHttpResponseBuffersizeSet() {
      return this._isSet(12);
   }

   public void setHttpResponseBuffersize(int param0) {
      int _oldVal = this._HttpResponseBuffersize;
      this._HttpResponseBuffersize = param0;
      this._postSet(12, _oldVal, param0);
   }

   public ReliabilityConfigBean getReliabilityConfig() {
      return this._ReliabilityConfig;
   }

   public boolean isReliabilityConfigInherited() {
      return false;
   }

   public boolean isReliabilityConfigSet() {
      return this._isSet(13);
   }

   public void setReliabilityConfig(ReliabilityConfigBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getReliabilityConfig() != null && param0 != this.getReliabilityConfig()) {
         throw new BeanAlreadyExistsException(this.getReliabilityConfig() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 13)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ReliabilityConfigBean _oldVal = this._ReliabilityConfig;
         this._ReliabilityConfig = param0;
         this._postSet(13, _oldVal, param0);
      }
   }

   public ReliabilityConfigBean createReliabilityConfig() {
      ReliabilityConfigBeanImpl _val = new ReliabilityConfigBeanImpl(this, -1);

      try {
         this.setReliabilityConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyReliabilityConfig() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ReliabilityConfig;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setReliabilityConfig((ReliabilityConfigBean)null);
               this._unSet(13);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PersistenceConfigBean getPersistenceConfig() {
      return this._PersistenceConfig;
   }

   public boolean isPersistenceConfigInherited() {
      return false;
   }

   public boolean isPersistenceConfigSet() {
      return this._isSet(14);
   }

   public void setPersistenceConfig(PersistenceConfigBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPersistenceConfig() != null && param0 != this.getPersistenceConfig()) {
         throw new BeanAlreadyExistsException(this.getPersistenceConfig() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 14)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         PersistenceConfigBean _oldVal = this._PersistenceConfig;
         this._PersistenceConfig = param0;
         this._postSet(14, _oldVal, param0);
      }
   }

   public PersistenceConfigBean createPersistenceConfig() {
      PersistenceConfigBeanImpl _val = new PersistenceConfigBeanImpl(this, -1);

      try {
         this.setPersistenceConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPersistenceConfig() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PersistenceConfig;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPersistenceConfig((PersistenceConfigBean)null);
               this._unSet(14);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public BufferingConfigBean getBufferingConfig() {
      return this._BufferingConfig;
   }

   public boolean isBufferingConfigInherited() {
      return false;
   }

   public boolean isBufferingConfigSet() {
      return this._isSet(15);
   }

   public void setBufferingConfig(BufferingConfigBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getBufferingConfig() != null && param0 != this.getBufferingConfig()) {
         throw new BeanAlreadyExistsException(this.getBufferingConfig() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 15)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         BufferingConfigBean _oldVal = this._BufferingConfig;
         this._BufferingConfig = param0;
         this._postSet(15, _oldVal, param0);
      }
   }

   public BufferingConfigBean createBufferingConfig() {
      BufferingConfigBeanImpl _val = new BufferingConfigBeanImpl(this, -1);

      try {
         this.setBufferingConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyBufferingConfig() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._BufferingConfig;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setBufferingConfig((BufferingConfigBean)null);
               this._unSet(15);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public WSATConfigBean getWSATConfig() {
      return this._WSATConfig;
   }

   public boolean isWSATConfigInherited() {
      return false;
   }

   public boolean isWSATConfigSet() {
      return this._isSet(16);
   }

   public void setWSATConfig(WSATConfigBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWSATConfig() != null && param0 != this.getWSATConfig()) {
         throw new BeanAlreadyExistsException(this.getWSATConfig() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 16)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WSATConfigBean _oldVal = this._WSATConfig;
         this._WSATConfig = param0;
         this._postSet(16, _oldVal, param0);
      }
   }

   public WSATConfigBean createWSATConfig() {
      WSATConfigBeanImpl _val = new WSATConfigBeanImpl(this, -1);

      try {
         this.setWSATConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWSATConfig() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WSATConfig;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWSATConfig((WSATConfigBean)null);
               this._unSet(16);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public OperationComponentBean createOperation() {
      OperationComponentBeanImpl _val = new OperationComponentBeanImpl(this, -1);

      try {
         this.addOperation(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public OperationComponentBean lookupOperation(String param0) {
      Object[] aary = (Object[])this._Operations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      OperationComponentBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (OperationComponentBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyOperation(OperationComponentBean param0) {
      try {
         this._checkIsPotentialChild(param0, 17);
         OperationComponentBean[] _old = this.getOperations();
         OperationComponentBean[] _new = (OperationComponentBean[])((OperationComponentBean[])this._getHelper()._removeElement(_old, OperationComponentBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setOperations(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addOperation(OperationComponentBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         OperationComponentBean[] _new;
         if (this._isSet(17)) {
            _new = (OperationComponentBean[])((OperationComponentBean[])this._getHelper()._extendArray(this.getOperations(), OperationComponentBean.class, param0));
         } else {
            _new = new OperationComponentBean[]{param0};
         }

         try {
            this.setOperations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public OperationComponentBean[] getOperations() {
      return this._Operations;
   }

   public boolean isOperationsInherited() {
      return false;
   }

   public boolean isOperationsSet() {
      return this._isSet(17);
   }

   public void removeOperation(OperationComponentBean param0) {
      this.destroyOperation(param0);
   }

   public void setOperations(OperationComponentBean[] param0) throws InvalidAttributeValueException {
      OperationComponentBean[] param0 = param0 == null ? new OperationComponentBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      OperationComponentBean[] _oldVal = this._Operations;
      this._Operations = (OperationComponentBean[])param0;
      this._postSet(17, _oldVal, param0);
   }

   public SoapjmsServiceEndpointAddressBean getSoapjmsServiceEndpointAddress() {
      return this._SoapjmsServiceEndpointAddress;
   }

   public boolean isSoapjmsServiceEndpointAddressInherited() {
      return false;
   }

   public boolean isSoapjmsServiceEndpointAddressSet() {
      return this._isSet(18);
   }

   public void setSoapjmsServiceEndpointAddress(SoapjmsServiceEndpointAddressBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSoapjmsServiceEndpointAddress() != null && param0 != this.getSoapjmsServiceEndpointAddress()) {
         throw new BeanAlreadyExistsException(this.getSoapjmsServiceEndpointAddress() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 18)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SoapjmsServiceEndpointAddressBean _oldVal = this._SoapjmsServiceEndpointAddress;
         this._SoapjmsServiceEndpointAddress = param0;
         this._postSet(18, _oldVal, param0);
      }
   }

   public SoapjmsServiceEndpointAddressBean createSoapjmsServiceEndpointAddress() {
      SoapjmsServiceEndpointAddressBeanImpl _val = new SoapjmsServiceEndpointAddressBeanImpl(this, -1);

      try {
         this.setSoapjmsServiceEndpointAddress(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySoapjmsServiceEndpointAddress() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SoapjmsServiceEndpointAddress;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSoapjmsServiceEndpointAddress((SoapjmsServiceEndpointAddressBean)null);
               this._unSet(18);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isFastInfoset() {
      return this._FastInfoset;
   }

   public boolean isFastInfosetInherited() {
      return false;
   }

   public boolean isFastInfosetSet() {
      return this._isSet(19);
   }

   public void setFastInfoset(boolean param0) {
      boolean _oldVal = this._FastInfoset;
      this._FastInfoset = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String getLoggingLevel() {
      return this._LoggingLevel;
   }

   public boolean isLoggingLevelInherited() {
      return false;
   }

   public boolean isLoggingLevelSet() {
      return this._isSet(20);
   }

   public void setLoggingLevel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LoggingLevel;
      this._LoggingLevel = param0;
      this._postSet(20, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getPortComponentName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 19:
            if (s.equals("port-component-name")) {
               return info.compareXpaths(this._getPropertyXpath("port-component-name"));
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._AuthConstraint = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._BufferingConfig = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._CallbackProtocol = "http";
               if (initOne) {
                  break;
               }
            case 5:
               this._DeploymentListenerList = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._HttpResponseBuffersize = 0;
               if (initOne) {
                  break;
               }
            case 20:
               this._LoggingLevel = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._LoginConfig = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._Operations = new OperationComponentBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._PersistenceConfig = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._PortComponentName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ReliabilityConfig = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ServiceEndpointAddress = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._SoapjmsServiceEndpointAddress = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._StreamAttachments = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._TransactionTimeout = 30;
               if (initOne) {
                  break;
               }
            case 4:
               this._TransportGuarantee = "NONE";
               if (initOne) {
                  break;
               }
            case 16:
               this._WSATConfig = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Wsdl = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._FastInfoset = true;
               if (initOne) {
                  break;
               }
            case 11:
               this._HttpFlushResponse = true;
               if (initOne) {
                  break;
               }
            case 10:
               this._ValidateRequest = false;
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
            case 4:
               if (s.equals("wsdl")) {
                  return 6;
               }
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 14:
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            default:
               break;
            case 9:
               if (s.equals("operation")) {
                  return 17;
               }
               break;
            case 11:
               if (s.equals("wsat-config")) {
                  return 16;
               }
               break;
            case 12:
               if (s.equals("login-config")) {
                  return 3;
               }

               if (s.equals("fast-infoset")) {
                  return 19;
               }
               break;
            case 13:
               if (s.equals("logging-level")) {
                  return 20;
               }
               break;
            case 15:
               if (s.equals("auth-constraint")) {
                  return 2;
               }
               break;
            case 16:
               if (s.equals("buffering-config")) {
                  return 15;
               }

               if (s.equals("validate-request")) {
                  return 10;
               }
               break;
            case 17:
               if (s.equals("callback-protocol")) {
                  return 8;
               }
               break;
            case 18:
               if (s.equals("persistence-config")) {
                  return 14;
               }

               if (s.equals("reliability-config")) {
                  return 13;
               }

               if (s.equals("stream-attachments")) {
                  return 9;
               }
               break;
            case 19:
               if (s.equals("port-component-name")) {
                  return 0;
               }

               if (s.equals("transaction-timeout")) {
                  return 7;
               }

               if (s.equals("transport-guarantee")) {
                  return 4;
               }

               if (s.equals("http-flush-response")) {
                  return 11;
               }
               break;
            case 24:
               if (s.equals("deployment-listener-list")) {
                  return 5;
               }

               if (s.equals("http-response-buffersize")) {
                  return 12;
               }

               if (s.equals("service-endpoint-address")) {
                  return 1;
               }
               break;
            case 32:
               if (s.equals("soapjms-service-endpoint-address")) {
                  return 18;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new WebserviceAddressBeanImpl.SchemaHelper2();
            case 2:
               return new AuthConstraintBeanImpl.SchemaHelper2();
            case 3:
               return new LoginConfigBeanImpl.SchemaHelper2();
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            default:
               return super.getSchemaHelper(propIndex);
            case 5:
               return new DeploymentListenerListBeanImpl.SchemaHelper2();
            case 6:
               return new WsdlBeanImpl.SchemaHelper2();
            case 13:
               return new ReliabilityConfigBeanImpl.SchemaHelper2();
            case 14:
               return new PersistenceConfigBeanImpl.SchemaHelper2();
            case 15:
               return new BufferingConfigBeanImpl.SchemaHelper2();
            case 16:
               return new WSATConfigBeanImpl.SchemaHelper2();
            case 17:
               return new OperationComponentBeanImpl.SchemaHelper2();
            case 18:
               return new SoapjmsServiceEndpointAddressBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "port-component-name";
            case 1:
               return "service-endpoint-address";
            case 2:
               return "auth-constraint";
            case 3:
               return "login-config";
            case 4:
               return "transport-guarantee";
            case 5:
               return "deployment-listener-list";
            case 6:
               return "wsdl";
            case 7:
               return "transaction-timeout";
            case 8:
               return "callback-protocol";
            case 9:
               return "stream-attachments";
            case 10:
               return "validate-request";
            case 11:
               return "http-flush-response";
            case 12:
               return "http-response-buffersize";
            case 13:
               return "reliability-config";
            case 14:
               return "persistence-config";
            case 15:
               return "buffering-config";
            case 16:
               return "wsat-config";
            case 17:
               return "operation";
            case 18:
               return "soapjms-service-endpoint-address";
            case 19:
               return "fast-infoset";
            case 20:
               return "logging-level";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 17:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            default:
               return super.isBean(propIndex);
            case 5:
               return true;
            case 6:
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
               return true;
            case 18:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
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
         indices.add("port-component-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PortComponentBeanImpl bean;

      protected Helper(PortComponentBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "PortComponentName";
            case 1:
               return "ServiceEndpointAddress";
            case 2:
               return "AuthConstraint";
            case 3:
               return "LoginConfig";
            case 4:
               return "TransportGuarantee";
            case 5:
               return "DeploymentListenerList";
            case 6:
               return "Wsdl";
            case 7:
               return "TransactionTimeout";
            case 8:
               return "CallbackProtocol";
            case 9:
               return "StreamAttachments";
            case 10:
               return "ValidateRequest";
            case 11:
               return "HttpFlushResponse";
            case 12:
               return "HttpResponseBuffersize";
            case 13:
               return "ReliabilityConfig";
            case 14:
               return "PersistenceConfig";
            case 15:
               return "BufferingConfig";
            case 16:
               return "WSATConfig";
            case 17:
               return "Operations";
            case 18:
               return "SoapjmsServiceEndpointAddress";
            case 19:
               return "FastInfoset";
            case 20:
               return "LoggingLevel";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthConstraint")) {
            return 2;
         } else if (propName.equals("BufferingConfig")) {
            return 15;
         } else if (propName.equals("CallbackProtocol")) {
            return 8;
         } else if (propName.equals("DeploymentListenerList")) {
            return 5;
         } else if (propName.equals("HttpResponseBuffersize")) {
            return 12;
         } else if (propName.equals("LoggingLevel")) {
            return 20;
         } else if (propName.equals("LoginConfig")) {
            return 3;
         } else if (propName.equals("Operations")) {
            return 17;
         } else if (propName.equals("PersistenceConfig")) {
            return 14;
         } else if (propName.equals("PortComponentName")) {
            return 0;
         } else if (propName.equals("ReliabilityConfig")) {
            return 13;
         } else if (propName.equals("ServiceEndpointAddress")) {
            return 1;
         } else if (propName.equals("SoapjmsServiceEndpointAddress")) {
            return 18;
         } else if (propName.equals("StreamAttachments")) {
            return 9;
         } else if (propName.equals("TransactionTimeout")) {
            return 7;
         } else if (propName.equals("TransportGuarantee")) {
            return 4;
         } else if (propName.equals("WSATConfig")) {
            return 16;
         } else if (propName.equals("Wsdl")) {
            return 6;
         } else if (propName.equals("FastInfoset")) {
            return 19;
         } else if (propName.equals("HttpFlushResponse")) {
            return 11;
         } else {
            return propName.equals("ValidateRequest") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAuthConstraint() != null) {
            iterators.add(new ArrayIterator(new AuthConstraintBean[]{this.bean.getAuthConstraint()}));
         }

         if (this.bean.getBufferingConfig() != null) {
            iterators.add(new ArrayIterator(new BufferingConfigBean[]{this.bean.getBufferingConfig()}));
         }

         if (this.bean.getDeploymentListenerList() != null) {
            iterators.add(new ArrayIterator(new DeploymentListenerListBean[]{this.bean.getDeploymentListenerList()}));
         }

         if (this.bean.getLoginConfig() != null) {
            iterators.add(new ArrayIterator(new LoginConfigBean[]{this.bean.getLoginConfig()}));
         }

         iterators.add(new ArrayIterator(this.bean.getOperations()));
         if (this.bean.getPersistenceConfig() != null) {
            iterators.add(new ArrayIterator(new PersistenceConfigBean[]{this.bean.getPersistenceConfig()}));
         }

         if (this.bean.getReliabilityConfig() != null) {
            iterators.add(new ArrayIterator(new ReliabilityConfigBean[]{this.bean.getReliabilityConfig()}));
         }

         if (this.bean.getServiceEndpointAddress() != null) {
            iterators.add(new ArrayIterator(new WebserviceAddressBean[]{this.bean.getServiceEndpointAddress()}));
         }

         if (this.bean.getSoapjmsServiceEndpointAddress() != null) {
            iterators.add(new ArrayIterator(new SoapjmsServiceEndpointAddressBean[]{this.bean.getSoapjmsServiceEndpointAddress()}));
         }

         if (this.bean.getWSATConfig() != null) {
            iterators.add(new ArrayIterator(new WSATConfigBean[]{this.bean.getWSATConfig()}));
         }

         if (this.bean.getWsdl() != null) {
            iterators.add(new ArrayIterator(new WsdlBean[]{this.bean.getWsdl()}));
         }

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
            childValue = this.computeChildHashValue(this.bean.getAuthConstraint());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getBufferingConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCallbackProtocolSet()) {
               buf.append("CallbackProtocol");
               buf.append(String.valueOf(this.bean.getCallbackProtocol()));
            }

            childValue = this.computeChildHashValue(this.bean.getDeploymentListenerList());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isHttpResponseBuffersizeSet()) {
               buf.append("HttpResponseBuffersize");
               buf.append(String.valueOf(this.bean.getHttpResponseBuffersize()));
            }

            if (this.bean.isLoggingLevelSet()) {
               buf.append("LoggingLevel");
               buf.append(String.valueOf(this.bean.getLoggingLevel()));
            }

            childValue = this.computeChildHashValue(this.bean.getLoginConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getOperations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getOperations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPersistenceConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isPortComponentNameSet()) {
               buf.append("PortComponentName");
               buf.append(String.valueOf(this.bean.getPortComponentName()));
            }

            childValue = this.computeChildHashValue(this.bean.getReliabilityConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getServiceEndpointAddress());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSoapjmsServiceEndpointAddress());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isStreamAttachmentsSet()) {
               buf.append("StreamAttachments");
               buf.append(String.valueOf(this.bean.getStreamAttachments()));
            }

            if (this.bean.isTransactionTimeoutSet()) {
               buf.append("TransactionTimeout");
               buf.append(String.valueOf(this.bean.getTransactionTimeout()));
            }

            if (this.bean.isTransportGuaranteeSet()) {
               buf.append("TransportGuarantee");
               buf.append(String.valueOf(this.bean.getTransportGuarantee()));
            }

            childValue = this.computeChildHashValue(this.bean.getWSATConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWsdl());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isFastInfosetSet()) {
               buf.append("FastInfoset");
               buf.append(String.valueOf(this.bean.isFastInfoset()));
            }

            if (this.bean.isHttpFlushResponseSet()) {
               buf.append("HttpFlushResponse");
               buf.append(String.valueOf(this.bean.isHttpFlushResponse()));
            }

            if (this.bean.isValidateRequestSet()) {
               buf.append("ValidateRequest");
               buf.append(String.valueOf(this.bean.isValidateRequest()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            PortComponentBeanImpl otherTyped = (PortComponentBeanImpl)other;
            this.computeChildDiff("AuthConstraint", this.bean.getAuthConstraint(), otherTyped.getAuthConstraint(), false);
            this.computeChildDiff("BufferingConfig", this.bean.getBufferingConfig(), otherTyped.getBufferingConfig(), true);
            this.computeDiff("CallbackProtocol", this.bean.getCallbackProtocol(), otherTyped.getCallbackProtocol(), false);
            this.computeChildDiff("DeploymentListenerList", this.bean.getDeploymentListenerList(), otherTyped.getDeploymentListenerList(), false);
            this.computeDiff("HttpResponseBuffersize", this.bean.getHttpResponseBuffersize(), otherTyped.getHttpResponseBuffersize(), false);
            this.computeDiff("LoggingLevel", this.bean.getLoggingLevel(), otherTyped.getLoggingLevel(), false);
            this.computeChildDiff("LoginConfig", this.bean.getLoginConfig(), otherTyped.getLoginConfig(), false);
            this.computeChildDiff("Operations", this.bean.getOperations(), otherTyped.getOperations(), false);
            this.computeChildDiff("PersistenceConfig", this.bean.getPersistenceConfig(), otherTyped.getPersistenceConfig(), true);
            this.computeDiff("PortComponentName", this.bean.getPortComponentName(), otherTyped.getPortComponentName(), false);
            this.computeChildDiff("ReliabilityConfig", this.bean.getReliabilityConfig(), otherTyped.getReliabilityConfig(), true);
            this.computeChildDiff("ServiceEndpointAddress", this.bean.getServiceEndpointAddress(), otherTyped.getServiceEndpointAddress(), false);
            this.computeChildDiff("SoapjmsServiceEndpointAddress", this.bean.getSoapjmsServiceEndpointAddress(), otherTyped.getSoapjmsServiceEndpointAddress(), true);
            this.computeDiff("StreamAttachments", this.bean.getStreamAttachments(), otherTyped.getStreamAttachments(), false);
            this.computeDiff("TransactionTimeout", this.bean.getTransactionTimeout(), otherTyped.getTransactionTimeout(), false);
            this.computeDiff("TransportGuarantee", this.bean.getTransportGuarantee(), otherTyped.getTransportGuarantee(), false);
            this.computeChildDiff("WSATConfig", this.bean.getWSATConfig(), otherTyped.getWSATConfig(), false);
            this.computeChildDiff("Wsdl", this.bean.getWsdl(), otherTyped.getWsdl(), false);
            this.computeDiff("FastInfoset", this.bean.isFastInfoset(), otherTyped.isFastInfoset(), false);
            this.computeDiff("HttpFlushResponse", this.bean.isHttpFlushResponse(), otherTyped.isHttpFlushResponse(), false);
            this.computeDiff("ValidateRequest", this.bean.isValidateRequest(), otherTyped.isValidateRequest(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PortComponentBeanImpl original = (PortComponentBeanImpl)event.getSourceBean();
            PortComponentBeanImpl proposed = (PortComponentBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AuthConstraint")) {
                  if (type == 2) {
                     original.setAuthConstraint((AuthConstraintBean)this.createCopy((AbstractDescriptorBean)proposed.getAuthConstraint()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AuthConstraint", (DescriptorBean)original.getAuthConstraint());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("BufferingConfig")) {
                  if (type == 2) {
                     original.setBufferingConfig((BufferingConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getBufferingConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("BufferingConfig", (DescriptorBean)original.getBufferingConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("CallbackProtocol")) {
                  original.setCallbackProtocol(proposed.getCallbackProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("DeploymentListenerList")) {
                  if (type == 2) {
                     original.setDeploymentListenerList((DeploymentListenerListBean)this.createCopy((AbstractDescriptorBean)proposed.getDeploymentListenerList()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DeploymentListenerList", (DescriptorBean)original.getDeploymentListenerList());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("HttpResponseBuffersize")) {
                  original.setHttpResponseBuffersize(proposed.getHttpResponseBuffersize());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("LoggingLevel")) {
                  original.setLoggingLevel(proposed.getLoggingLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("LoginConfig")) {
                  if (type == 2) {
                     original.setLoginConfig((LoginConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getLoginConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("LoginConfig", (DescriptorBean)original.getLoginConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Operations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addOperation((OperationComponentBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeOperation((OperationComponentBean)update.getRemovedObject());
                  }

                  if (original.getOperations() == null || original.getOperations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
               } else if (prop.equals("PersistenceConfig")) {
                  if (type == 2) {
                     original.setPersistenceConfig((PersistenceConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getPersistenceConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PersistenceConfig", (DescriptorBean)original.getPersistenceConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("PortComponentName")) {
                  original.setPortComponentName(proposed.getPortComponentName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ReliabilityConfig")) {
                  if (type == 2) {
                     original.setReliabilityConfig((ReliabilityConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getReliabilityConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ReliabilityConfig", (DescriptorBean)original.getReliabilityConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ServiceEndpointAddress")) {
                  if (type == 2) {
                     original.setServiceEndpointAddress((WebserviceAddressBean)this.createCopy((AbstractDescriptorBean)proposed.getServiceEndpointAddress()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ServiceEndpointAddress", (DescriptorBean)original.getServiceEndpointAddress());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SoapjmsServiceEndpointAddress")) {
                  if (type == 2) {
                     original.setSoapjmsServiceEndpointAddress((SoapjmsServiceEndpointAddressBean)this.createCopy((AbstractDescriptorBean)proposed.getSoapjmsServiceEndpointAddress()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SoapjmsServiceEndpointAddress", (DescriptorBean)original.getSoapjmsServiceEndpointAddress());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("StreamAttachments")) {
                  original.setStreamAttachments(proposed.getStreamAttachments());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("TransactionTimeout")) {
                  original.setTransactionTimeout(proposed.getTransactionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("TransportGuarantee")) {
                  original.setTransportGuarantee(proposed.getTransportGuarantee());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("WSATConfig")) {
                  if (type == 2) {
                     original.setWSATConfig((WSATConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getWSATConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WSATConfig", (DescriptorBean)original.getWSATConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Wsdl")) {
                  if (type == 2) {
                     original.setWsdl((WsdlBean)this.createCopy((AbstractDescriptorBean)proposed.getWsdl()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Wsdl", (DescriptorBean)original.getWsdl());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("FastInfoset")) {
                  original.setFastInfoset(proposed.isFastInfoset());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("HttpFlushResponse")) {
                  original.setHttpFlushResponse(proposed.isHttpFlushResponse());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("ValidateRequest")) {
                  original.setValidateRequest(proposed.isValidateRequest());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            PortComponentBeanImpl copy = (PortComponentBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthConstraint")) && this.bean.isAuthConstraintSet() && !copy._isSet(2)) {
               Object o = this.bean.getAuthConstraint();
               copy.setAuthConstraint((AuthConstraintBean)null);
               copy.setAuthConstraint(o == null ? null : (AuthConstraintBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("BufferingConfig")) && this.bean.isBufferingConfigSet() && !copy._isSet(15)) {
               Object o = this.bean.getBufferingConfig();
               copy.setBufferingConfig((BufferingConfigBean)null);
               copy.setBufferingConfig(o == null ? null : (BufferingConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CallbackProtocol")) && this.bean.isCallbackProtocolSet()) {
               copy.setCallbackProtocol(this.bean.getCallbackProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("DeploymentListenerList")) && this.bean.isDeploymentListenerListSet() && !copy._isSet(5)) {
               Object o = this.bean.getDeploymentListenerList();
               copy.setDeploymentListenerList((DeploymentListenerListBean)null);
               copy.setDeploymentListenerList(o == null ? null : (DeploymentListenerListBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("HttpResponseBuffersize")) && this.bean.isHttpResponseBuffersizeSet()) {
               copy.setHttpResponseBuffersize(this.bean.getHttpResponseBuffersize());
            }

            if ((excludeProps == null || !excludeProps.contains("LoggingLevel")) && this.bean.isLoggingLevelSet()) {
               copy.setLoggingLevel(this.bean.getLoggingLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginConfig")) && this.bean.isLoginConfigSet() && !copy._isSet(3)) {
               Object o = this.bean.getLoginConfig();
               copy.setLoginConfig((LoginConfigBean)null);
               copy.setLoginConfig(o == null ? null : (LoginConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Operations")) && this.bean.isOperationsSet() && !copy._isSet(17)) {
               OperationComponentBean[] oldOperations = this.bean.getOperations();
               OperationComponentBean[] newOperations = new OperationComponentBean[oldOperations.length];

               for(int i = 0; i < newOperations.length; ++i) {
                  newOperations[i] = (OperationComponentBean)((OperationComponentBean)this.createCopy((AbstractDescriptorBean)oldOperations[i], includeObsolete));
               }

               copy.setOperations(newOperations);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceConfig")) && this.bean.isPersistenceConfigSet() && !copy._isSet(14)) {
               Object o = this.bean.getPersistenceConfig();
               copy.setPersistenceConfig((PersistenceConfigBean)null);
               copy.setPersistenceConfig(o == null ? null : (PersistenceConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PortComponentName")) && this.bean.isPortComponentNameSet()) {
               copy.setPortComponentName(this.bean.getPortComponentName());
            }

            if ((excludeProps == null || !excludeProps.contains("ReliabilityConfig")) && this.bean.isReliabilityConfigSet() && !copy._isSet(13)) {
               Object o = this.bean.getReliabilityConfig();
               copy.setReliabilityConfig((ReliabilityConfigBean)null);
               copy.setReliabilityConfig(o == null ? null : (ReliabilityConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceEndpointAddress")) && this.bean.isServiceEndpointAddressSet() && !copy._isSet(1)) {
               Object o = this.bean.getServiceEndpointAddress();
               copy.setServiceEndpointAddress((WebserviceAddressBean)null);
               copy.setServiceEndpointAddress(o == null ? null : (WebserviceAddressBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SoapjmsServiceEndpointAddress")) && this.bean.isSoapjmsServiceEndpointAddressSet() && !copy._isSet(18)) {
               Object o = this.bean.getSoapjmsServiceEndpointAddress();
               copy.setSoapjmsServiceEndpointAddress((SoapjmsServiceEndpointAddressBean)null);
               copy.setSoapjmsServiceEndpointAddress(o == null ? null : (SoapjmsServiceEndpointAddressBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("StreamAttachments")) && this.bean.isStreamAttachmentsSet()) {
               copy.setStreamAttachments(this.bean.getStreamAttachments());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionTimeout")) && this.bean.isTransactionTimeoutSet()) {
               copy.setTransactionTimeout(this.bean.getTransactionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("TransportGuarantee")) && this.bean.isTransportGuaranteeSet()) {
               copy.setTransportGuarantee(this.bean.getTransportGuarantee());
            }

            if ((excludeProps == null || !excludeProps.contains("WSATConfig")) && this.bean.isWSATConfigSet() && !copy._isSet(16)) {
               Object o = this.bean.getWSATConfig();
               copy.setWSATConfig((WSATConfigBean)null);
               copy.setWSATConfig(o == null ? null : (WSATConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Wsdl")) && this.bean.isWsdlSet() && !copy._isSet(6)) {
               Object o = this.bean.getWsdl();
               copy.setWsdl((WsdlBean)null);
               copy.setWsdl(o == null ? null : (WsdlBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FastInfoset")) && this.bean.isFastInfosetSet()) {
               copy.setFastInfoset(this.bean.isFastInfoset());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpFlushResponse")) && this.bean.isHttpFlushResponseSet()) {
               copy.setHttpFlushResponse(this.bean.isHttpFlushResponse());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidateRequest")) && this.bean.isValidateRequestSet()) {
               copy.setValidateRequest(this.bean.isValidateRequest());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getAuthConstraint(), clazz, annotation);
         this.inferSubTree(this.bean.getBufferingConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getDeploymentListenerList(), clazz, annotation);
         this.inferSubTree(this.bean.getLoginConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getOperations(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getReliabilityConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceEndpointAddress(), clazz, annotation);
         this.inferSubTree(this.bean.getSoapjmsServiceEndpointAddress(), clazz, annotation);
         this.inferSubTree(this.bean.getWSATConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getWsdl(), clazz, annotation);
      }
   }
}
