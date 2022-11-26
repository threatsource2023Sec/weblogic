package com.bea.httppubsub.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicPubsubBeanImpl extends AbstractDescriptorBean implements WeblogicPubsubBean, Serializable {
   private ChannelConstraintBean[] _ChannelConstraints;
   private ChannelBean[] _Channels;
   private JmsHandlerMappingBean[] _JmsHandlerMappings;
   private MessageFilterBean[] _MessageFilters;
   private ServerConfigBean _ServerConfig;
   private ServiceBean[] _Services;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicPubsubBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicPubsubBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicPubsubBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ServerConfigBean getServerConfig() {
      return this._ServerConfig;
   }

   public boolean isServerConfigInherited() {
      return false;
   }

   public boolean isServerConfigSet() {
      return this._isSet(0);
   }

   public void setServerConfig(ServerConfigBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getServerConfig() != null && param0 != this.getServerConfig()) {
         throw new BeanAlreadyExistsException(this.getServerConfig() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ServerConfigBean _oldVal = this._ServerConfig;
         this._ServerConfig = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public ServerConfigBean createServerConfig() {
      ServerConfigBeanImpl _val = new ServerConfigBeanImpl(this, -1);

      try {
         this.setServerConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addJmsHandlerMapping(JmsHandlerMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         JmsHandlerMappingBean[] _new;
         if (this._isSet(1)) {
            _new = (JmsHandlerMappingBean[])((JmsHandlerMappingBean[])this._getHelper()._extendArray(this.getJmsHandlerMappings(), JmsHandlerMappingBean.class, param0));
         } else {
            _new = new JmsHandlerMappingBean[]{param0};
         }

         try {
            this.setJmsHandlerMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JmsHandlerMappingBean[] getJmsHandlerMappings() {
      return this._JmsHandlerMappings;
   }

   public boolean isJmsHandlerMappingsInherited() {
      return false;
   }

   public boolean isJmsHandlerMappingsSet() {
      return this._isSet(1);
   }

   public void removeJmsHandlerMapping(JmsHandlerMappingBean param0) {
      JmsHandlerMappingBean[] _old = this.getJmsHandlerMappings();
      JmsHandlerMappingBean[] _new = (JmsHandlerMappingBean[])((JmsHandlerMappingBean[])this._getHelper()._removeElement(_old, JmsHandlerMappingBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setJmsHandlerMappings(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setJmsHandlerMappings(JmsHandlerMappingBean[] param0) throws InvalidAttributeValueException {
      JmsHandlerMappingBean[] param0 = param0 == null ? new JmsHandlerMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JmsHandlerMappingBean[] _oldVal = this._JmsHandlerMappings;
      this._JmsHandlerMappings = (JmsHandlerMappingBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public JmsHandlerMappingBean createJmsHandlerMapping() {
      JmsHandlerMappingBeanImpl _val = new JmsHandlerMappingBeanImpl(this, -1);

      try {
         this.addJmsHandlerMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addChannel(ChannelBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         ChannelBean[] _new;
         if (this._isSet(2)) {
            _new = (ChannelBean[])((ChannelBean[])this._getHelper()._extendArray(this.getChannels(), ChannelBean.class, param0));
         } else {
            _new = new ChannelBean[]{param0};
         }

         try {
            this.setChannels(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ChannelBean[] getChannels() {
      return this._Channels;
   }

   public boolean isChannelsInherited() {
      return false;
   }

   public boolean isChannelsSet() {
      return this._isSet(2);
   }

   public void removeChannel(ChannelBean param0) {
      ChannelBean[] _old = this.getChannels();
      ChannelBean[] _new = (ChannelBean[])((ChannelBean[])this._getHelper()._removeElement(_old, ChannelBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setChannels(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setChannels(ChannelBean[] param0) throws InvalidAttributeValueException {
      ChannelBean[] param0 = param0 == null ? new ChannelBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ChannelBean[] _oldVal = this._Channels;
      this._Channels = (ChannelBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public ChannelBean createChannel() {
      ChannelBeanImpl _val = new ChannelBeanImpl(this, -1);

      try {
         this.addChannel(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addChannelConstraint(ChannelConstraintBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         ChannelConstraintBean[] _new;
         if (this._isSet(3)) {
            _new = (ChannelConstraintBean[])((ChannelConstraintBean[])this._getHelper()._extendArray(this.getChannelConstraints(), ChannelConstraintBean.class, param0));
         } else {
            _new = new ChannelConstraintBean[]{param0};
         }

         try {
            this.setChannelConstraints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ChannelConstraintBean[] getChannelConstraints() {
      return this._ChannelConstraints;
   }

   public boolean isChannelConstraintsInherited() {
      return false;
   }

   public boolean isChannelConstraintsSet() {
      return this._isSet(3);
   }

   public void removeChannelConstraint(ChannelConstraintBean param0) {
      ChannelConstraintBean[] _old = this.getChannelConstraints();
      ChannelConstraintBean[] _new = (ChannelConstraintBean[])((ChannelConstraintBean[])this._getHelper()._removeElement(_old, ChannelConstraintBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setChannelConstraints(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setChannelConstraints(ChannelConstraintBean[] param0) throws InvalidAttributeValueException {
      ChannelConstraintBean[] param0 = param0 == null ? new ChannelConstraintBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ChannelConstraintBean[] _oldVal = this._ChannelConstraints;
      this._ChannelConstraints = (ChannelConstraintBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public ChannelConstraintBean createChannelConstraint() {
      ChannelConstraintBeanImpl _val = new ChannelConstraintBeanImpl(this, -1);

      try {
         this.addChannelConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addMessageFilter(MessageFilterBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         MessageFilterBean[] _new;
         if (this._isSet(4)) {
            _new = (MessageFilterBean[])((MessageFilterBean[])this._getHelper()._extendArray(this.getMessageFilters(), MessageFilterBean.class, param0));
         } else {
            _new = new MessageFilterBean[]{param0};
         }

         try {
            this.setMessageFilters(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MessageFilterBean[] getMessageFilters() {
      return this._MessageFilters;
   }

   public boolean isMessageFiltersInherited() {
      return false;
   }

   public boolean isMessageFiltersSet() {
      return this._isSet(4);
   }

   public void removeMessageFilter(MessageFilterBean param0) {
      MessageFilterBean[] _old = this.getMessageFilters();
      MessageFilterBean[] _new = (MessageFilterBean[])((MessageFilterBean[])this._getHelper()._removeElement(_old, MessageFilterBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMessageFilters(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMessageFilters(MessageFilterBean[] param0) throws InvalidAttributeValueException {
      MessageFilterBean[] param0 = param0 == null ? new MessageFilterBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageFilterBean[] _oldVal = this._MessageFilters;
      this._MessageFilters = (MessageFilterBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public MessageFilterBean createMessageFilter() {
      MessageFilterBeanImpl _val = new MessageFilterBeanImpl(this, -1);

      try {
         this.addMessageFilter(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addService(ServiceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         ServiceBean[] _new;
         if (this._isSet(5)) {
            _new = (ServiceBean[])((ServiceBean[])this._getHelper()._extendArray(this.getServices(), ServiceBean.class, param0));
         } else {
            _new = new ServiceBean[]{param0};
         }

         try {
            this.setServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceBean[] getServices() {
      return this._Services;
   }

   public boolean isServicesInherited() {
      return false;
   }

   public boolean isServicesSet() {
      return this._isSet(5);
   }

   public void removeService(ServiceBean param0) {
      ServiceBean[] _old = this.getServices();
      ServiceBean[] _new = (ServiceBean[])((ServiceBean[])this._getHelper()._removeElement(_old, ServiceBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setServices(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setServices(ServiceBean[] param0) throws InvalidAttributeValueException {
      ServiceBean[] param0 = param0 == null ? new ServiceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceBean[] _oldVal = this._Services;
      this._Services = (ServiceBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public ServiceBean createService() {
      ServiceBeanImpl _val = new ServiceBeanImpl(this, -1);

      try {
         this.addService(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._ChannelConstraints = new ChannelConstraintBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Channels = new ChannelBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._JmsHandlerMappings = new JmsHandlerMappingBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._MessageFilters = new MessageFilterBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ServerConfig = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Services = new ServiceBean[0];
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
            case 7:
               if (s.equals("channel")) {
                  return 2;
               }

               if (s.equals("service")) {
                  return 5;
               }
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            default:
               break;
            case 13:
               if (s.equals("server-config")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("message-filter")) {
                  return 4;
               }
               break;
            case 18:
               if (s.equals("channel-constraint")) {
                  return 3;
               }
               break;
            case 19:
               if (s.equals("jms-handler-mapping")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new ServerConfigBeanImpl.SchemaHelper2();
            case 1:
               return new JmsHandlerMappingBeanImpl.SchemaHelper2();
            case 2:
               return new ChannelBeanImpl.SchemaHelper2();
            case 3:
               return new ChannelConstraintBeanImpl.SchemaHelper2();
            case 4:
               return new MessageFilterBeanImpl.SchemaHelper2();
            case 5:
               return new ServiceBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "weblogic-pubsub";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "server-config";
            case 1:
               return "jms-handler-mapping";
            case 2:
               return "channel";
            case 3:
               return "channel-constraint";
            case 4:
               return "message-filter";
            case 5:
               return "service";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
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
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
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
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicPubsubBeanImpl bean;

      protected Helper(WeblogicPubsubBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServerConfig";
            case 1:
               return "JmsHandlerMappings";
            case 2:
               return "Channels";
            case 3:
               return "ChannelConstraints";
            case 4:
               return "MessageFilters";
            case 5:
               return "Services";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ChannelConstraints")) {
            return 3;
         } else if (propName.equals("Channels")) {
            return 2;
         } else if (propName.equals("JmsHandlerMappings")) {
            return 1;
         } else if (propName.equals("MessageFilters")) {
            return 4;
         } else if (propName.equals("ServerConfig")) {
            return 0;
         } else {
            return propName.equals("Services") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getChannelConstraints()));
         iterators.add(new ArrayIterator(this.bean.getChannels()));
         iterators.add(new ArrayIterator(this.bean.getJmsHandlerMappings()));
         iterators.add(new ArrayIterator(this.bean.getMessageFilters()));
         if (this.bean.getServerConfig() != null) {
            iterators.add(new ArrayIterator(new ServerConfigBean[]{this.bean.getServerConfig()}));
         }

         iterators.add(new ArrayIterator(this.bean.getServices()));
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
            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getChannelConstraints().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getChannelConstraints()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getChannels().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getChannels()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJmsHandlerMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJmsHandlerMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageFilters().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageFilters()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getServerConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            WeblogicPubsubBeanImpl otherTyped = (WeblogicPubsubBeanImpl)other;
            this.computeChildDiff("ChannelConstraints", this.bean.getChannelConstraints(), otherTyped.getChannelConstraints(), false);
            this.computeChildDiff("Channels", this.bean.getChannels(), otherTyped.getChannels(), false);
            this.computeChildDiff("JmsHandlerMappings", this.bean.getJmsHandlerMappings(), otherTyped.getJmsHandlerMappings(), false);
            this.computeChildDiff("MessageFilters", this.bean.getMessageFilters(), otherTyped.getMessageFilters(), false);
            this.computeChildDiff("ServerConfig", this.bean.getServerConfig(), otherTyped.getServerConfig(), false);
            this.computeChildDiff("Services", this.bean.getServices(), otherTyped.getServices(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicPubsubBeanImpl original = (WeblogicPubsubBeanImpl)event.getSourceBean();
            WeblogicPubsubBeanImpl proposed = (WeblogicPubsubBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ChannelConstraints")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addChannelConstraint((ChannelConstraintBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeChannelConstraint((ChannelConstraintBean)update.getRemovedObject());
                  }

                  if (original.getChannelConstraints() == null || original.getChannelConstraints().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Channels")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addChannel((ChannelBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeChannel((ChannelBean)update.getRemovedObject());
                  }

                  if (original.getChannels() == null || original.getChannels().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("JmsHandlerMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJmsHandlerMapping((JmsHandlerMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJmsHandlerMapping((JmsHandlerMappingBean)update.getRemovedObject());
                  }

                  if (original.getJmsHandlerMappings() == null || original.getJmsHandlerMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("MessageFilters")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMessageFilter((MessageFilterBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageFilter((MessageFilterBean)update.getRemovedObject());
                  }

                  if (original.getMessageFilters() == null || original.getMessageFilters().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("ServerConfig")) {
                  if (type == 2) {
                     original.setServerConfig((ServerConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getServerConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ServerConfig", (DescriptorBean)original.getServerConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Services")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addService((ServiceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeService((ServiceBean)update.getRemovedObject());
                  }

                  if (original.getServices() == null || original.getServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
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
            WeblogicPubsubBeanImpl copy = (WeblogicPubsubBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("ChannelConstraints")) && this.bean.isChannelConstraintsSet() && !copy._isSet(3)) {
               ChannelConstraintBean[] oldChannelConstraints = this.bean.getChannelConstraints();
               ChannelConstraintBean[] newChannelConstraints = new ChannelConstraintBean[oldChannelConstraints.length];

               for(i = 0; i < newChannelConstraints.length; ++i) {
                  newChannelConstraints[i] = (ChannelConstraintBean)((ChannelConstraintBean)this.createCopy((AbstractDescriptorBean)oldChannelConstraints[i], includeObsolete));
               }

               copy.setChannelConstraints(newChannelConstraints);
            }

            if ((excludeProps == null || !excludeProps.contains("Channels")) && this.bean.isChannelsSet() && !copy._isSet(2)) {
               ChannelBean[] oldChannels = this.bean.getChannels();
               ChannelBean[] newChannels = new ChannelBean[oldChannels.length];

               for(i = 0; i < newChannels.length; ++i) {
                  newChannels[i] = (ChannelBean)((ChannelBean)this.createCopy((AbstractDescriptorBean)oldChannels[i], includeObsolete));
               }

               copy.setChannels(newChannels);
            }

            if ((excludeProps == null || !excludeProps.contains("JmsHandlerMappings")) && this.bean.isJmsHandlerMappingsSet() && !copy._isSet(1)) {
               JmsHandlerMappingBean[] oldJmsHandlerMappings = this.bean.getJmsHandlerMappings();
               JmsHandlerMappingBean[] newJmsHandlerMappings = new JmsHandlerMappingBean[oldJmsHandlerMappings.length];

               for(i = 0; i < newJmsHandlerMappings.length; ++i) {
                  newJmsHandlerMappings[i] = (JmsHandlerMappingBean)((JmsHandlerMappingBean)this.createCopy((AbstractDescriptorBean)oldJmsHandlerMappings[i], includeObsolete));
               }

               copy.setJmsHandlerMappings(newJmsHandlerMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageFilters")) && this.bean.isMessageFiltersSet() && !copy._isSet(4)) {
               MessageFilterBean[] oldMessageFilters = this.bean.getMessageFilters();
               MessageFilterBean[] newMessageFilters = new MessageFilterBean[oldMessageFilters.length];

               for(i = 0; i < newMessageFilters.length; ++i) {
                  newMessageFilters[i] = (MessageFilterBean)((MessageFilterBean)this.createCopy((AbstractDescriptorBean)oldMessageFilters[i], includeObsolete));
               }

               copy.setMessageFilters(newMessageFilters);
            }

            if ((excludeProps == null || !excludeProps.contains("ServerConfig")) && this.bean.isServerConfigSet() && !copy._isSet(0)) {
               Object o = this.bean.getServerConfig();
               copy.setServerConfig((ServerConfigBean)null);
               copy.setServerConfig(o == null ? null : (ServerConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Services")) && this.bean.isServicesSet() && !copy._isSet(5)) {
               ServiceBean[] oldServices = this.bean.getServices();
               ServiceBean[] newServices = new ServiceBean[oldServices.length];

               for(i = 0; i < newServices.length; ++i) {
                  newServices[i] = (ServiceBean)((ServiceBean)this.createCopy((AbstractDescriptorBean)oldServices[i], includeObsolete));
               }

               copy.setServices(newServices);
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
         this.inferSubTree(this.bean.getChannelConstraints(), clazz, annotation);
         this.inferSubTree(this.bean.getChannels(), clazz, annotation);
         this.inferSubTree(this.bean.getJmsHandlerMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageFilters(), clazz, annotation);
         this.inferSubTree(this.bean.getServerConfig(), clazz, annotation);
         this.inferSubTree(this.bean.getServices(), clazz, annotation);
      }
   }
}
