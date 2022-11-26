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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebServiceMBeanImpl extends ConfigurationMBeanImpl implements WebServiceMBean, Serializable {
   private String _CallbackQueue;
   private String _CallbackQueueMDBRunAsPrincipalName;
   private String _JmsConnectionFactory;
   private String _MessagingQueue;
   private String _MessagingQueueMDBRunAsPrincipalName;
   private WebServiceBufferingMBean _WebServiceBuffering;
   private WebServicePersistenceMBean _WebServicePersistence;
   private WebServiceReliabilityMBean _WebServiceReliability;
   private WebServiceResiliencyMBean _WebServiceResiliency;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WebServiceMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WebServiceMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WebServiceMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WebServiceMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WebServiceMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WebServiceMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._WebServiceBuffering instanceof WebServiceBufferingMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getWebServiceBuffering() != null) {
            this._getReferenceManager().unregisterBean((WebServiceBufferingMBeanImpl)oldDelegate.getWebServiceBuffering());
         }

         if (delegate != null && delegate.getWebServiceBuffering() != null) {
            this._getReferenceManager().registerBean((WebServiceBufferingMBeanImpl)delegate.getWebServiceBuffering(), false);
         }

         ((WebServiceBufferingMBeanImpl)this._WebServiceBuffering)._setDelegateBean((WebServiceBufferingMBeanImpl)((WebServiceBufferingMBeanImpl)(delegate == null ? null : delegate.getWebServiceBuffering())));
      }

      if (this._WebServicePersistence instanceof WebServicePersistenceMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getWebServicePersistence() != null) {
            this._getReferenceManager().unregisterBean((WebServicePersistenceMBeanImpl)oldDelegate.getWebServicePersistence());
         }

         if (delegate != null && delegate.getWebServicePersistence() != null) {
            this._getReferenceManager().registerBean((WebServicePersistenceMBeanImpl)delegate.getWebServicePersistence(), false);
         }

         ((WebServicePersistenceMBeanImpl)this._WebServicePersistence)._setDelegateBean((WebServicePersistenceMBeanImpl)((WebServicePersistenceMBeanImpl)(delegate == null ? null : delegate.getWebServicePersistence())));
      }

      if (this._WebServiceReliability instanceof WebServiceReliabilityMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getWebServiceReliability() != null) {
            this._getReferenceManager().unregisterBean((WebServiceReliabilityMBeanImpl)oldDelegate.getWebServiceReliability());
         }

         if (delegate != null && delegate.getWebServiceReliability() != null) {
            this._getReferenceManager().registerBean((WebServiceReliabilityMBeanImpl)delegate.getWebServiceReliability(), false);
         }

         ((WebServiceReliabilityMBeanImpl)this._WebServiceReliability)._setDelegateBean((WebServiceReliabilityMBeanImpl)((WebServiceReliabilityMBeanImpl)(delegate == null ? null : delegate.getWebServiceReliability())));
      }

      if (this._WebServiceResiliency instanceof WebServiceResiliencyMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getWebServiceResiliency() != null) {
            this._getReferenceManager().unregisterBean((WebServiceResiliencyMBeanImpl)oldDelegate.getWebServiceResiliency());
         }

         if (delegate != null && delegate.getWebServiceResiliency() != null) {
            this._getReferenceManager().registerBean((WebServiceResiliencyMBeanImpl)delegate.getWebServiceResiliency(), false);
         }

         ((WebServiceResiliencyMBeanImpl)this._WebServiceResiliency)._setDelegateBean((WebServiceResiliencyMBeanImpl)((WebServiceResiliencyMBeanImpl)(delegate == null ? null : delegate.getWebServiceResiliency())));
      }

   }

   public WebServiceMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebServiceMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebServiceMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setJmsConnectionFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._JmsConnectionFactory;
      this._JmsConnectionFactory = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceMBeanImpl source = (WebServiceMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getJmsConnectionFactory() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getJmsConnectionFactory(), this) : this._JmsConnectionFactory;
   }

   public boolean isJmsConnectionFactoryInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isJmsConnectionFactorySet() {
      return this._isSet(10);
   }

   public void setMessagingQueue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._MessagingQueue;
      this._MessagingQueue = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceMBeanImpl source = (WebServiceMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMessagingQueue() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getMessagingQueue(), this) : this._MessagingQueue;
   }

   public boolean isMessagingQueueInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isMessagingQueueSet() {
      return this._isSet(11);
   }

   public void setMessagingQueueMDBRunAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._MessagingQueueMDBRunAsPrincipalName;
      this._MessagingQueueMDBRunAsPrincipalName = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceMBeanImpl source = (WebServiceMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMessagingQueueMDBRunAsPrincipalName() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getMessagingQueueMDBRunAsPrincipalName(), this) : this._MessagingQueueMDBRunAsPrincipalName;
   }

   public boolean isMessagingQueueMDBRunAsPrincipalNameInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isMessagingQueueMDBRunAsPrincipalNameSet() {
      return this._isSet(12);
   }

   public void setCallbackQueue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this._CallbackQueue;
      this._CallbackQueue = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceMBeanImpl source = (WebServiceMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCallbackQueue() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getCallbackQueue(), this) : this._CallbackQueue;
   }

   public boolean isCallbackQueueInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isCallbackQueueSet() {
      return this._isSet(13);
   }

   public void setCallbackQueueMDBRunAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      String _oldVal = this._CallbackQueueMDBRunAsPrincipalName;
      this._CallbackQueueMDBRunAsPrincipalName = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceMBeanImpl source = (WebServiceMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCallbackQueueMDBRunAsPrincipalName() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getCallbackQueueMDBRunAsPrincipalName(), this) : this._CallbackQueueMDBRunAsPrincipalName;
   }

   public boolean isCallbackQueueMDBRunAsPrincipalNameInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isCallbackQueueMDBRunAsPrincipalNameSet() {
      return this._isSet(14);
   }

   public WebServicePersistenceMBean getWebServicePersistence() {
      return this._WebServicePersistence;
   }

   public boolean isWebServicePersistenceInherited() {
      return false;
   }

   public boolean isWebServicePersistenceSet() {
      return this._isSet(15) || this._isAnythingSet((AbstractDescriptorBean)this.getWebServicePersistence());
   }

   public void setWebServicePersistence(WebServicePersistenceMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 15)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(15);
      WebServicePersistenceMBean _oldVal = this._WebServicePersistence;
      this._WebServicePersistence = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServiceMBeanImpl source = (WebServiceMBeanImpl)var5.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServiceBufferingMBean getWebServiceBuffering() {
      return this._WebServiceBuffering;
   }

   public boolean isWebServiceBufferingInherited() {
      return false;
   }

   public boolean isWebServiceBufferingSet() {
      return this._isSet(16) || this._isAnythingSet((AbstractDescriptorBean)this.getWebServiceBuffering());
   }

   public void setWebServiceBuffering(WebServiceBufferingMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 16)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(16);
      WebServiceBufferingMBean _oldVal = this._WebServiceBuffering;
      this._WebServiceBuffering = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServiceMBeanImpl source = (WebServiceMBeanImpl)var5.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServiceReliabilityMBean getWebServiceReliability() {
      return this._WebServiceReliability;
   }

   public boolean isWebServiceReliabilityInherited() {
      return false;
   }

   public boolean isWebServiceReliabilitySet() {
      return this._isSet(17) || this._isAnythingSet((AbstractDescriptorBean)this.getWebServiceReliability());
   }

   public void setWebServiceReliability(WebServiceReliabilityMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 17)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(17);
      WebServiceReliabilityMBean _oldVal = this._WebServiceReliability;
      this._WebServiceReliability = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServiceMBeanImpl source = (WebServiceMBeanImpl)var5.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServiceResiliencyMBean getWebServiceResiliency() {
      return this._WebServiceResiliency;
   }

   public boolean isWebServiceResiliencyInherited() {
      return false;
   }

   public boolean isWebServiceResiliencySet() {
      return this._isSet(18) || this._isAnythingSet((AbstractDescriptorBean)this.getWebServiceResiliency());
   }

   public void setWebServiceResiliency(WebServiceResiliencyMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 18)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(18);
      WebServiceResiliencyMBean _oldVal = this._WebServiceResiliency;
      this._WebServiceResiliency = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServiceMBeanImpl source = (WebServiceMBeanImpl)var5.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
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
      return super._isAnythingSet() || this.isWebServiceBufferingSet() || this.isWebServicePersistenceSet() || this.isWebServiceReliabilitySet() || this.isWebServiceResiliencySet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._CallbackQueue = "weblogic.wsee.DefaultCallbackQueue";
               if (initOne) {
                  break;
               }
            case 14:
               this._CallbackQueueMDBRunAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._JmsConnectionFactory = "weblogic.jms.XAConnectionFactory";
               if (initOne) {
                  break;
               }
            case 11:
               this._MessagingQueue = "weblogic.wsee.DefaultQueue";
               if (initOne) {
                  break;
               }
            case 12:
               this._MessagingQueueMDBRunAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._WebServiceBuffering = new WebServiceBufferingMBeanImpl(this, 16);
               this._postCreate((AbstractDescriptorBean)this._WebServiceBuffering);
               if (initOne) {
                  break;
               }
            case 15:
               this._WebServicePersistence = new WebServicePersistenceMBeanImpl(this, 15);
               this._postCreate((AbstractDescriptorBean)this._WebServicePersistence);
               if (initOne) {
                  break;
               }
            case 17:
               this._WebServiceReliability = new WebServiceReliabilityMBeanImpl(this, 17);
               this._postCreate((AbstractDescriptorBean)this._WebServiceReliability);
               if (initOne) {
                  break;
               }
            case 18:
               this._WebServiceResiliency = new WebServiceResiliencyMBeanImpl(this, 18);
               this._postCreate((AbstractDescriptorBean)this._WebServiceResiliency);
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
      return "WebService";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("CallbackQueue")) {
         oldVal = this._CallbackQueue;
         this._CallbackQueue = (String)v;
         this._postSet(13, oldVal, this._CallbackQueue);
      } else if (name.equals("CallbackQueueMDBRunAsPrincipalName")) {
         oldVal = this._CallbackQueueMDBRunAsPrincipalName;
         this._CallbackQueueMDBRunAsPrincipalName = (String)v;
         this._postSet(14, oldVal, this._CallbackQueueMDBRunAsPrincipalName);
      } else if (name.equals("JmsConnectionFactory")) {
         oldVal = this._JmsConnectionFactory;
         this._JmsConnectionFactory = (String)v;
         this._postSet(10, oldVal, this._JmsConnectionFactory);
      } else if (name.equals("MessagingQueue")) {
         oldVal = this._MessagingQueue;
         this._MessagingQueue = (String)v;
         this._postSet(11, oldVal, this._MessagingQueue);
      } else if (name.equals("MessagingQueueMDBRunAsPrincipalName")) {
         oldVal = this._MessagingQueueMDBRunAsPrincipalName;
         this._MessagingQueueMDBRunAsPrincipalName = (String)v;
         this._postSet(12, oldVal, this._MessagingQueueMDBRunAsPrincipalName);
      } else if (name.equals("WebServiceBuffering")) {
         WebServiceBufferingMBean oldVal = this._WebServiceBuffering;
         this._WebServiceBuffering = (WebServiceBufferingMBean)v;
         this._postSet(16, oldVal, this._WebServiceBuffering);
      } else if (name.equals("WebServicePersistence")) {
         WebServicePersistenceMBean oldVal = this._WebServicePersistence;
         this._WebServicePersistence = (WebServicePersistenceMBean)v;
         this._postSet(15, oldVal, this._WebServicePersistence);
      } else if (name.equals("WebServiceReliability")) {
         WebServiceReliabilityMBean oldVal = this._WebServiceReliability;
         this._WebServiceReliability = (WebServiceReliabilityMBean)v;
         this._postSet(17, oldVal, this._WebServiceReliability);
      } else if (name.equals("WebServiceResiliency")) {
         WebServiceResiliencyMBean oldVal = this._WebServiceResiliency;
         this._WebServiceResiliency = (WebServiceResiliencyMBean)v;
         this._postSet(18, oldVal, this._WebServiceResiliency);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("CallbackQueue")) {
         return this._CallbackQueue;
      } else if (name.equals("CallbackQueueMDBRunAsPrincipalName")) {
         return this._CallbackQueueMDBRunAsPrincipalName;
      } else if (name.equals("JmsConnectionFactory")) {
         return this._JmsConnectionFactory;
      } else if (name.equals("MessagingQueue")) {
         return this._MessagingQueue;
      } else if (name.equals("MessagingQueueMDBRunAsPrincipalName")) {
         return this._MessagingQueueMDBRunAsPrincipalName;
      } else if (name.equals("WebServiceBuffering")) {
         return this._WebServiceBuffering;
      } else if (name.equals("WebServicePersistence")) {
         return this._WebServicePersistence;
      } else if (name.equals("WebServiceReliability")) {
         return this._WebServiceReliability;
      } else {
         return name.equals("WebServiceResiliency") ? this._WebServiceResiliency : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 14:
               if (s.equals("callback-queue")) {
                  return 13;
               }
               break;
            case 15:
               if (s.equals("messaging-queue")) {
                  return 11;
               }
               break;
            case 21:
               if (s.equals("web-service-buffering")) {
                  return 16;
               }
               break;
            case 22:
               if (s.equals("jms-connection-factory")) {
                  return 10;
               }

               if (s.equals("web-service-resiliency")) {
                  return 18;
               }
               break;
            case 23:
               if (s.equals("web-service-persistence")) {
                  return 15;
               }

               if (s.equals("web-service-reliability")) {
                  return 17;
               }
               break;
            case 39:
               if (s.equals("callback-queuemdb-run-as-principal-name")) {
                  return 14;
               }
               break;
            case 40:
               if (s.equals("messaging-queuemdb-run-as-principal-name")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 15:
               return new WebServicePersistenceMBeanImpl.SchemaHelper2();
            case 16:
               return new WebServiceBufferingMBeanImpl.SchemaHelper2();
            case 17:
               return new WebServiceReliabilityMBeanImpl.SchemaHelper2();
            case 18:
               return new WebServiceResiliencyMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "jms-connection-factory";
            case 11:
               return "messaging-queue";
            case 12:
               return "messaging-queuemdb-run-as-principal-name";
            case 13:
               return "callback-queue";
            case 14:
               return "callback-queuemdb-run-as-principal-name";
            case 15:
               return "web-service-persistence";
            case 16:
               return "web-service-buffering";
            case 17:
               return "web-service-reliability";
            case 18:
               return "web-service-resiliency";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WebServiceMBeanImpl bean;

      protected Helper(WebServiceMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "JmsConnectionFactory";
            case 11:
               return "MessagingQueue";
            case 12:
               return "MessagingQueueMDBRunAsPrincipalName";
            case 13:
               return "CallbackQueue";
            case 14:
               return "CallbackQueueMDBRunAsPrincipalName";
            case 15:
               return "WebServicePersistence";
            case 16:
               return "WebServiceBuffering";
            case 17:
               return "WebServiceReliability";
            case 18:
               return "WebServiceResiliency";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CallbackQueue")) {
            return 13;
         } else if (propName.equals("CallbackQueueMDBRunAsPrincipalName")) {
            return 14;
         } else if (propName.equals("JmsConnectionFactory")) {
            return 10;
         } else if (propName.equals("MessagingQueue")) {
            return 11;
         } else if (propName.equals("MessagingQueueMDBRunAsPrincipalName")) {
            return 12;
         } else if (propName.equals("WebServiceBuffering")) {
            return 16;
         } else if (propName.equals("WebServicePersistence")) {
            return 15;
         } else if (propName.equals("WebServiceReliability")) {
            return 17;
         } else {
            return propName.equals("WebServiceResiliency") ? 18 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getWebServiceBuffering() != null) {
            iterators.add(new ArrayIterator(new WebServiceBufferingMBean[]{this.bean.getWebServiceBuffering()}));
         }

         if (this.bean.getWebServicePersistence() != null) {
            iterators.add(new ArrayIterator(new WebServicePersistenceMBean[]{this.bean.getWebServicePersistence()}));
         }

         if (this.bean.getWebServiceReliability() != null) {
            iterators.add(new ArrayIterator(new WebServiceReliabilityMBean[]{this.bean.getWebServiceReliability()}));
         }

         if (this.bean.getWebServiceResiliency() != null) {
            iterators.add(new ArrayIterator(new WebServiceResiliencyMBean[]{this.bean.getWebServiceResiliency()}));
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
            if (this.bean.isCallbackQueueSet()) {
               buf.append("CallbackQueue");
               buf.append(String.valueOf(this.bean.getCallbackQueue()));
            }

            if (this.bean.isCallbackQueueMDBRunAsPrincipalNameSet()) {
               buf.append("CallbackQueueMDBRunAsPrincipalName");
               buf.append(String.valueOf(this.bean.getCallbackQueueMDBRunAsPrincipalName()));
            }

            if (this.bean.isJmsConnectionFactorySet()) {
               buf.append("JmsConnectionFactory");
               buf.append(String.valueOf(this.bean.getJmsConnectionFactory()));
            }

            if (this.bean.isMessagingQueueSet()) {
               buf.append("MessagingQueue");
               buf.append(String.valueOf(this.bean.getMessagingQueue()));
            }

            if (this.bean.isMessagingQueueMDBRunAsPrincipalNameSet()) {
               buf.append("MessagingQueueMDBRunAsPrincipalName");
               buf.append(String.valueOf(this.bean.getMessagingQueueMDBRunAsPrincipalName()));
            }

            childValue = this.computeChildHashValue(this.bean.getWebServiceBuffering());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebServicePersistence());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebServiceReliability());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebServiceResiliency());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            WebServiceMBeanImpl otherTyped = (WebServiceMBeanImpl)other;
            this.computeDiff("CallbackQueue", this.bean.getCallbackQueue(), otherTyped.getCallbackQueue(), true);
            this.computeDiff("CallbackQueueMDBRunAsPrincipalName", this.bean.getCallbackQueueMDBRunAsPrincipalName(), otherTyped.getCallbackQueueMDBRunAsPrincipalName(), true);
            this.computeDiff("JmsConnectionFactory", this.bean.getJmsConnectionFactory(), otherTyped.getJmsConnectionFactory(), true);
            this.computeDiff("MessagingQueue", this.bean.getMessagingQueue(), otherTyped.getMessagingQueue(), true);
            this.computeDiff("MessagingQueueMDBRunAsPrincipalName", this.bean.getMessagingQueueMDBRunAsPrincipalName(), otherTyped.getMessagingQueueMDBRunAsPrincipalName(), true);
            this.computeSubDiff("WebServiceBuffering", this.bean.getWebServiceBuffering(), otherTyped.getWebServiceBuffering());
            this.computeSubDiff("WebServicePersistence", this.bean.getWebServicePersistence(), otherTyped.getWebServicePersistence());
            this.computeSubDiff("WebServiceReliability", this.bean.getWebServiceReliability(), otherTyped.getWebServiceReliability());
            this.computeSubDiff("WebServiceResiliency", this.bean.getWebServiceResiliency(), otherTyped.getWebServiceResiliency());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebServiceMBeanImpl original = (WebServiceMBeanImpl)event.getSourceBean();
            WebServiceMBeanImpl proposed = (WebServiceMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CallbackQueue")) {
                  original.setCallbackQueue(proposed.getCallbackQueue());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("CallbackQueueMDBRunAsPrincipalName")) {
                  original.setCallbackQueueMDBRunAsPrincipalName(proposed.getCallbackQueueMDBRunAsPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("JmsConnectionFactory")) {
                  original.setJmsConnectionFactory(proposed.getJmsConnectionFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MessagingQueue")) {
                  original.setMessagingQueue(proposed.getMessagingQueue());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MessagingQueueMDBRunAsPrincipalName")) {
                  original.setMessagingQueueMDBRunAsPrincipalName(proposed.getMessagingQueueMDBRunAsPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("WebServiceBuffering")) {
                  if (type == 2) {
                     original.setWebServiceBuffering((WebServiceBufferingMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebServiceBuffering()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WebServiceBuffering", original.getWebServiceBuffering());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("WebServicePersistence")) {
                  if (type == 2) {
                     original.setWebServicePersistence((WebServicePersistenceMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebServicePersistence()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WebServicePersistence", original.getWebServicePersistence());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("WebServiceReliability")) {
                  if (type == 2) {
                     original.setWebServiceReliability((WebServiceReliabilityMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebServiceReliability()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WebServiceReliability", original.getWebServiceReliability());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("WebServiceResiliency")) {
                  if (type == 2) {
                     original.setWebServiceResiliency((WebServiceResiliencyMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebServiceResiliency()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WebServiceResiliency", original.getWebServiceResiliency());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 18);
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
            WebServiceMBeanImpl copy = (WebServiceMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CallbackQueue")) && this.bean.isCallbackQueueSet()) {
               copy.setCallbackQueue(this.bean.getCallbackQueue());
            }

            if ((excludeProps == null || !excludeProps.contains("CallbackQueueMDBRunAsPrincipalName")) && this.bean.isCallbackQueueMDBRunAsPrincipalNameSet()) {
               copy.setCallbackQueueMDBRunAsPrincipalName(this.bean.getCallbackQueueMDBRunAsPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("JmsConnectionFactory")) && this.bean.isJmsConnectionFactorySet()) {
               copy.setJmsConnectionFactory(this.bean.getJmsConnectionFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagingQueue")) && this.bean.isMessagingQueueSet()) {
               copy.setMessagingQueue(this.bean.getMessagingQueue());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagingQueueMDBRunAsPrincipalName")) && this.bean.isMessagingQueueMDBRunAsPrincipalNameSet()) {
               copy.setMessagingQueueMDBRunAsPrincipalName(this.bean.getMessagingQueueMDBRunAsPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("WebServiceBuffering")) && this.bean.isWebServiceBufferingSet() && !copy._isSet(16)) {
               Object o = this.bean.getWebServiceBuffering();
               copy.setWebServiceBuffering((WebServiceBufferingMBean)null);
               copy.setWebServiceBuffering(o == null ? null : (WebServiceBufferingMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WebServicePersistence")) && this.bean.isWebServicePersistenceSet() && !copy._isSet(15)) {
               Object o = this.bean.getWebServicePersistence();
               copy.setWebServicePersistence((WebServicePersistenceMBean)null);
               copy.setWebServicePersistence(o == null ? null : (WebServicePersistenceMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WebServiceReliability")) && this.bean.isWebServiceReliabilitySet() && !copy._isSet(17)) {
               Object o = this.bean.getWebServiceReliability();
               copy.setWebServiceReliability((WebServiceReliabilityMBean)null);
               copy.setWebServiceReliability(o == null ? null : (WebServiceReliabilityMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WebServiceResiliency")) && this.bean.isWebServiceResiliencySet() && !copy._isSet(18)) {
               Object o = this.bean.getWebServiceResiliency();
               copy.setWebServiceResiliency((WebServiceResiliencyMBean)null);
               copy.setWebServiceResiliency(o == null ? null : (WebServiceResiliencyMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getWebServiceBuffering(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServicePersistence(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServiceReliability(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServiceResiliency(), clazz, annotation);
      }
   }
}
