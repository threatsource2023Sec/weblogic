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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SAFImportedDestinationsBeanImpl extends TargetableBeanImpl implements SAFImportedDestinationsBean, Serializable {
   private String _ExactlyOnceLoadBalancingPolicy;
   private String _JNDIPrefix;
   private MessageLoggingParamsBean _MessageLoggingParams;
   private SAFErrorHandlingBean _SAFErrorHandling;
   private SAFQueueBean[] _SAFQueues;
   private SAFRemoteContextBean _SAFRemoteContext;
   private SAFTopicBean[] _SAFTopics;
   private long _TimeToLiveDefault;
   private String _UnitOfOrderRouting;
   private boolean _UseSAFTimeToLiveDefault;
   private static SchemaHelper2 _schemaHelper;

   public SAFImportedDestinationsBeanImpl() {
      this._initializeProperty(-1);
   }

   public SAFImportedDestinationsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SAFImportedDestinationsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addSAFQueue(SAFQueueBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         SAFQueueBean[] _new;
         if (this._isSet(5)) {
            _new = (SAFQueueBean[])((SAFQueueBean[])this._getHelper()._extendArray(this.getSAFQueues(), SAFQueueBean.class, param0));
         } else {
            _new = new SAFQueueBean[]{param0};
         }

         try {
            this.setSAFQueues(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SAFQueueBean[] getSAFQueues() {
      return this._SAFQueues;
   }

   public boolean isSAFQueuesInherited() {
      return false;
   }

   public boolean isSAFQueuesSet() {
      return this._isSet(5);
   }

   public void removeSAFQueue(SAFQueueBean param0) {
      this.destroySAFQueue(param0);
   }

   public void setSAFQueues(SAFQueueBean[] param0) throws InvalidAttributeValueException {
      SAFQueueBean[] param0 = param0 == null ? new SAFQueueBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SAFQueueBean[] _oldVal = this._SAFQueues;
      this._SAFQueues = (SAFQueueBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public SAFQueueBean createSAFQueue(String param0) {
      SAFQueueBeanImpl lookup = (SAFQueueBeanImpl)this.lookupSAFQueue(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SAFQueueBeanImpl _val = new SAFQueueBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSAFQueue(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroySAFQueue(SAFQueueBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         SAFQueueBean[] _old = this.getSAFQueues();
         SAFQueueBean[] _new = (SAFQueueBean[])((SAFQueueBean[])this._getHelper()._removeElement(_old, SAFQueueBean.class, param0));
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
               this.setSAFQueues(_new);
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

   public SAFQueueBean lookupSAFQueue(String param0) {
      Object[] aary = (Object[])this._SAFQueues;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SAFQueueBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SAFQueueBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addSAFTopic(SAFTopicBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         SAFTopicBean[] _new;
         if (this._isSet(6)) {
            _new = (SAFTopicBean[])((SAFTopicBean[])this._getHelper()._extendArray(this.getSAFTopics(), SAFTopicBean.class, param0));
         } else {
            _new = new SAFTopicBean[]{param0};
         }

         try {
            this.setSAFTopics(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SAFTopicBean[] getSAFTopics() {
      return this._SAFTopics;
   }

   public boolean isSAFTopicsInherited() {
      return false;
   }

   public boolean isSAFTopicsSet() {
      return this._isSet(6);
   }

   public void removeSAFTopic(SAFTopicBean param0) {
      this.destroySAFTopic(param0);
   }

   public void setSAFTopics(SAFTopicBean[] param0) throws InvalidAttributeValueException {
      SAFTopicBean[] param0 = param0 == null ? new SAFTopicBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SAFTopicBean[] _oldVal = this._SAFTopics;
      this._SAFTopics = (SAFTopicBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public SAFTopicBean createSAFTopic(String param0) {
      SAFTopicBeanImpl lookup = (SAFTopicBeanImpl)this.lookupSAFTopic(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SAFTopicBeanImpl _val = new SAFTopicBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSAFTopic(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroySAFTopic(SAFTopicBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         SAFTopicBean[] _old = this.getSAFTopics();
         SAFTopicBean[] _new = (SAFTopicBean[])((SAFTopicBean[])this._getHelper()._removeElement(_old, SAFTopicBean.class, param0));
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
               this.setSAFTopics(_new);
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

   public SAFTopicBean lookupSAFTopic(String param0) {
      Object[] aary = (Object[])this._SAFTopics;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SAFTopicBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SAFTopicBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public String getJNDIPrefix() {
      return this._JNDIPrefix;
   }

   public boolean isJNDIPrefixInherited() {
      return false;
   }

   public boolean isJNDIPrefixSet() {
      return this._isSet(7);
   }

   public void setJNDIPrefix(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JNDIPrefix;
      this._JNDIPrefix = param0;
      this._postSet(7, _oldVal, param0);
   }

   public SAFRemoteContextBean getSAFRemoteContext() {
      return this._SAFRemoteContext;
   }

   public String getSAFRemoteContextAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getSAFRemoteContext();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isSAFRemoteContextInherited() {
      return false;
   }

   public boolean isSAFRemoteContextSet() {
      return this._isSet(8);
   }

   public void setSAFRemoteContextAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, SAFRemoteContextBean.class, new ReferenceManager.Resolver(this, 8) {
            public void resolveReference(Object value) {
               try {
                  SAFImportedDestinationsBeanImpl.this.setSAFRemoteContext((SAFRemoteContextBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         SAFRemoteContextBean _oldVal = this._SAFRemoteContext;
         this._initializeProperty(8);
         this._postSet(8, _oldVal, this._SAFRemoteContext);
      }

   }

   public void setSAFRemoteContext(SAFRemoteContextBean param0) throws IllegalArgumentException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 8, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SAFImportedDestinationsBeanImpl.this.getSAFRemoteContext();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      SAFRemoteContextBean _oldVal = this._SAFRemoteContext;
      this._SAFRemoteContext = param0;
      this._postSet(8, _oldVal, param0);
   }

   public SAFErrorHandlingBean getSAFErrorHandling() {
      return this._SAFErrorHandling;
   }

   public String getSAFErrorHandlingAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getSAFErrorHandling();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isSAFErrorHandlingInherited() {
      return false;
   }

   public boolean isSAFErrorHandlingSet() {
      return this._isSet(9);
   }

   public void setSAFErrorHandlingAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, SAFErrorHandlingBean.class, new ReferenceManager.Resolver(this, 9) {
            public void resolveReference(Object value) {
               try {
                  SAFImportedDestinationsBeanImpl.this.setSAFErrorHandling((SAFErrorHandlingBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         SAFErrorHandlingBean _oldVal = this._SAFErrorHandling;
         this._initializeProperty(9);
         this._postSet(9, _oldVal, this._SAFErrorHandling);
      }

   }

   public void setSAFErrorHandling(SAFErrorHandlingBean param0) throws IllegalArgumentException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 9, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SAFImportedDestinationsBeanImpl.this.getSAFErrorHandling();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      SAFErrorHandlingBean _oldVal = this._SAFErrorHandling;
      this._SAFErrorHandling = param0;
      this._postSet(9, _oldVal, param0);
   }

   public long getTimeToLiveDefault() {
      return this._TimeToLiveDefault;
   }

   public boolean isTimeToLiveDefaultInherited() {
      return false;
   }

   public boolean isTimeToLiveDefaultSet() {
      return this._isSet(10);
   }

   public void setTimeToLiveDefault(long param0) throws IllegalArgumentException {
      LegalChecks.checkMin("TimeToLiveDefault", param0, -1L);
      long _oldVal = this._TimeToLiveDefault;
      this._TimeToLiveDefault = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isUseSAFTimeToLiveDefault() {
      return this._UseSAFTimeToLiveDefault;
   }

   public boolean isUseSAFTimeToLiveDefaultInherited() {
      return false;
   }

   public boolean isUseSAFTimeToLiveDefaultSet() {
      return this._isSet(11);
   }

   public void setUseSAFTimeToLiveDefault(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._UseSAFTimeToLiveDefault;
      this._UseSAFTimeToLiveDefault = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getUnitOfOrderRouting() {
      return this._UnitOfOrderRouting;
   }

   public boolean isUnitOfOrderRoutingInherited() {
      return false;
   }

   public boolean isUnitOfOrderRoutingSet() {
      return this._isSet(12);
   }

   public void setUnitOfOrderRouting(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Hash", "PathService"};
      param0 = LegalChecks.checkInEnum("UnitOfOrderRouting", param0, _set);
      String _oldVal = this._UnitOfOrderRouting;
      this._UnitOfOrderRouting = param0;
      this._postSet(12, _oldVal, param0);
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      return this._MessageLoggingParams;
   }

   public boolean isMessageLoggingParamsInherited() {
      return false;
   }

   public boolean isMessageLoggingParamsSet() {
      return this._isSet(13) || this._isAnythingSet((AbstractDescriptorBean)this.getMessageLoggingParams());
   }

   public void setMessageLoggingParams(MessageLoggingParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 13)) {
         this._postCreate(_child);
      }

      MessageLoggingParamsBean _oldVal = this._MessageLoggingParams;
      this._MessageLoggingParams = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getExactlyOnceLoadBalancingPolicy() {
      return this._ExactlyOnceLoadBalancingPolicy;
   }

   public boolean isExactlyOnceLoadBalancingPolicyInherited() {
      return false;
   }

   public boolean isExactlyOnceLoadBalancingPolicySet() {
      return this._isSet(14);
   }

   public void setExactlyOnceLoadBalancingPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER, JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_JVM};
      param0 = LegalChecks.checkInEnum("ExactlyOnceLoadBalancingPolicy", param0, _set);
      String _oldVal = this._ExactlyOnceLoadBalancingPolicy;
      this._ExactlyOnceLoadBalancingPolicy = param0;
      this._postSet(14, _oldVal, param0);
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
      return super._isAnythingSet() || this.isMessageLoggingParamsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._ExactlyOnceLoadBalancingPolicy = JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER;
               if (initOne) {
                  break;
               }
            case 7:
               this._JNDIPrefix = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._MessageLoggingParams = new MessageLoggingParamsBeanImpl(this, 13);
               this._postCreate((AbstractDescriptorBean)this._MessageLoggingParams);
               if (initOne) {
                  break;
               }
            case 9:
               this._SAFErrorHandling = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._SAFQueues = new SAFQueueBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._SAFRemoteContext = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._SAFTopics = new SAFTopicBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._TimeToLiveDefault = 3600000L;
               if (initOne) {
                  break;
               }
            case 12:
               this._UnitOfOrderRouting = "Hash";
               if (initOne) {
                  break;
               }
            case 11:
               this._UseSAFTimeToLiveDefault = false;
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

   public static class SchemaHelper2 extends TargetableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("saf-queue")) {
                  return 5;
               }

               if (s.equals("saf-topic")) {
                  return 6;
               }
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            default:
               break;
            case 11:
               if (s.equals("jndi-prefix")) {
                  return 7;
               }
               break;
            case 18:
               if (s.equals("saf-error-handling")) {
                  return 9;
               }

               if (s.equals("saf-remote-context")) {
                  return 8;
               }
               break;
            case 20:
               if (s.equals("time-to-live-default")) {
                  return 10;
               }
               break;
            case 21:
               if (s.equals("unit-of-order-routing")) {
                  return 12;
               }
               break;
            case 22:
               if (s.equals("message-logging-params")) {
                  return 13;
               }
               break;
            case 28:
               if (s.equals("use-saf-time-to-live-default")) {
                  return 11;
               }
               break;
            case 34:
               if (s.equals("exactly-once-load-balancing-policy")) {
                  return 14;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new SAFQueueBeanImpl.SchemaHelper2();
            case 6:
               return new SAFTopicBeanImpl.SchemaHelper2();
            case 13:
               return new MessageLoggingParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 5:
               return "saf-queue";
            case 6:
               return "saf-topic";
            case 7:
               return "jndi-prefix";
            case 8:
               return "saf-remote-context";
            case 9:
               return "saf-error-handling";
            case 10:
               return "time-to-live-default";
            case 11:
               return "use-saf-time-to-live-default";
            case 12:
               return "unit-of-order-routing";
            case 13:
               return "message-logging-params";
            case 14:
               return "exactly-once-load-balancing-policy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
               return true;
            case 13:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends TargetableBeanImpl.Helper {
      private SAFImportedDestinationsBeanImpl bean;

      protected Helper(SAFImportedDestinationsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 5:
               return "SAFQueues";
            case 6:
               return "SAFTopics";
            case 7:
               return "JNDIPrefix";
            case 8:
               return "SAFRemoteContext";
            case 9:
               return "SAFErrorHandling";
            case 10:
               return "TimeToLiveDefault";
            case 11:
               return "UseSAFTimeToLiveDefault";
            case 12:
               return "UnitOfOrderRouting";
            case 13:
               return "MessageLoggingParams";
            case 14:
               return "ExactlyOnceLoadBalancingPolicy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ExactlyOnceLoadBalancingPolicy")) {
            return 14;
         } else if (propName.equals("JNDIPrefix")) {
            return 7;
         } else if (propName.equals("MessageLoggingParams")) {
            return 13;
         } else if (propName.equals("SAFErrorHandling")) {
            return 9;
         } else if (propName.equals("SAFQueues")) {
            return 5;
         } else if (propName.equals("SAFRemoteContext")) {
            return 8;
         } else if (propName.equals("SAFTopics")) {
            return 6;
         } else if (propName.equals("TimeToLiveDefault")) {
            return 10;
         } else if (propName.equals("UnitOfOrderRouting")) {
            return 12;
         } else {
            return propName.equals("UseSAFTimeToLiveDefault") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getMessageLoggingParams() != null) {
            iterators.add(new ArrayIterator(new MessageLoggingParamsBean[]{this.bean.getMessageLoggingParams()}));
         }

         iterators.add(new ArrayIterator(this.bean.getSAFQueues()));
         iterators.add(new ArrayIterator(this.bean.getSAFTopics()));
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
            if (this.bean.isExactlyOnceLoadBalancingPolicySet()) {
               buf.append("ExactlyOnceLoadBalancingPolicy");
               buf.append(String.valueOf(this.bean.getExactlyOnceLoadBalancingPolicy()));
            }

            if (this.bean.isJNDIPrefixSet()) {
               buf.append("JNDIPrefix");
               buf.append(String.valueOf(this.bean.getJNDIPrefix()));
            }

            childValue = this.computeChildHashValue(this.bean.getMessageLoggingParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSAFErrorHandlingSet()) {
               buf.append("SAFErrorHandling");
               buf.append(String.valueOf(this.bean.getSAFErrorHandling()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getSAFQueues().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSAFQueues()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSAFRemoteContextSet()) {
               buf.append("SAFRemoteContext");
               buf.append(String.valueOf(this.bean.getSAFRemoteContext()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSAFTopics().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSAFTopics()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTimeToLiveDefaultSet()) {
               buf.append("TimeToLiveDefault");
               buf.append(String.valueOf(this.bean.getTimeToLiveDefault()));
            }

            if (this.bean.isUnitOfOrderRoutingSet()) {
               buf.append("UnitOfOrderRouting");
               buf.append(String.valueOf(this.bean.getUnitOfOrderRouting()));
            }

            if (this.bean.isUseSAFTimeToLiveDefaultSet()) {
               buf.append("UseSAFTimeToLiveDefault");
               buf.append(String.valueOf(this.bean.isUseSAFTimeToLiveDefault()));
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
            SAFImportedDestinationsBeanImpl otherTyped = (SAFImportedDestinationsBeanImpl)other;
            this.computeDiff("ExactlyOnceLoadBalancingPolicy", this.bean.getExactlyOnceLoadBalancingPolicy(), otherTyped.getExactlyOnceLoadBalancingPolicy(), false);
            this.computeDiff("JNDIPrefix", this.bean.getJNDIPrefix(), otherTyped.getJNDIPrefix(), true);
            this.computeSubDiff("MessageLoggingParams", this.bean.getMessageLoggingParams(), otherTyped.getMessageLoggingParams());
            this.computeDiff("SAFErrorHandling", this.bean.getSAFErrorHandling(), otherTyped.getSAFErrorHandling(), false);
            this.computeChildDiff("SAFQueues", this.bean.getSAFQueues(), otherTyped.getSAFQueues(), true);
            this.computeDiff("SAFRemoteContext", this.bean.getSAFRemoteContext(), otherTyped.getSAFRemoteContext(), true);
            this.computeChildDiff("SAFTopics", this.bean.getSAFTopics(), otherTyped.getSAFTopics(), true);
            this.computeDiff("TimeToLiveDefault", this.bean.getTimeToLiveDefault(), otherTyped.getTimeToLiveDefault(), true);
            this.computeDiff("UnitOfOrderRouting", this.bean.getUnitOfOrderRouting(), otherTyped.getUnitOfOrderRouting(), false);
            this.computeDiff("UseSAFTimeToLiveDefault", this.bean.isUseSAFTimeToLiveDefault(), otherTyped.isUseSAFTimeToLiveDefault(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SAFImportedDestinationsBeanImpl original = (SAFImportedDestinationsBeanImpl)event.getSourceBean();
            SAFImportedDestinationsBeanImpl proposed = (SAFImportedDestinationsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ExactlyOnceLoadBalancingPolicy")) {
                  original.setExactlyOnceLoadBalancingPolicy(proposed.getExactlyOnceLoadBalancingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("JNDIPrefix")) {
                  original.setJNDIPrefix(proposed.getJNDIPrefix());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("MessageLoggingParams")) {
                  if (type == 2) {
                     original.setMessageLoggingParams((MessageLoggingParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getMessageLoggingParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MessageLoggingParams", (DescriptorBean)original.getMessageLoggingParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("SAFErrorHandling")) {
                  original.setSAFErrorHandlingAsString(proposed.getSAFErrorHandlingAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("SAFQueues")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSAFQueue((SAFQueueBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSAFQueue((SAFQueueBean)update.getRemovedObject());
                  }

                  if (original.getSAFQueues() == null || original.getSAFQueues().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("SAFRemoteContext")) {
                  original.setSAFRemoteContextAsString(proposed.getSAFRemoteContextAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("SAFTopics")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSAFTopic((SAFTopicBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSAFTopic((SAFTopicBean)update.getRemovedObject());
                  }

                  if (original.getSAFTopics() == null || original.getSAFTopics().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("TimeToLiveDefault")) {
                  original.setTimeToLiveDefault(proposed.getTimeToLiveDefault());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("UnitOfOrderRouting")) {
                  original.setUnitOfOrderRouting(proposed.getUnitOfOrderRouting());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("UseSAFTimeToLiveDefault")) {
                  original.setUseSAFTimeToLiveDefault(proposed.isUseSAFTimeToLiveDefault());
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
            SAFImportedDestinationsBeanImpl copy = (SAFImportedDestinationsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ExactlyOnceLoadBalancingPolicy")) && this.bean.isExactlyOnceLoadBalancingPolicySet()) {
               copy.setExactlyOnceLoadBalancingPolicy(this.bean.getExactlyOnceLoadBalancingPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIPrefix")) && this.bean.isJNDIPrefixSet()) {
               copy.setJNDIPrefix(this.bean.getJNDIPrefix());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageLoggingParams")) && this.bean.isMessageLoggingParamsSet() && !copy._isSet(13)) {
               Object o = this.bean.getMessageLoggingParams();
               copy.setMessageLoggingParams((MessageLoggingParamsBean)null);
               copy.setMessageLoggingParams(o == null ? null : (MessageLoggingParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SAFErrorHandling")) && this.bean.isSAFErrorHandlingSet()) {
               copy._unSet(copy, 9);
               copy.setSAFErrorHandlingAsString(this.bean.getSAFErrorHandlingAsString());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("SAFQueues")) && this.bean.isSAFQueuesSet() && !copy._isSet(5)) {
               SAFQueueBean[] oldSAFQueues = this.bean.getSAFQueues();
               SAFQueueBean[] newSAFQueues = new SAFQueueBean[oldSAFQueues.length];

               for(i = 0; i < newSAFQueues.length; ++i) {
                  newSAFQueues[i] = (SAFQueueBean)((SAFQueueBean)this.createCopy((AbstractDescriptorBean)oldSAFQueues[i], includeObsolete));
               }

               copy.setSAFQueues(newSAFQueues);
            }

            if ((excludeProps == null || !excludeProps.contains("SAFRemoteContext")) && this.bean.isSAFRemoteContextSet()) {
               copy._unSet(copy, 8);
               copy.setSAFRemoteContextAsString(this.bean.getSAFRemoteContextAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("SAFTopics")) && this.bean.isSAFTopicsSet() && !copy._isSet(6)) {
               SAFTopicBean[] oldSAFTopics = this.bean.getSAFTopics();
               SAFTopicBean[] newSAFTopics = new SAFTopicBean[oldSAFTopics.length];

               for(i = 0; i < newSAFTopics.length; ++i) {
                  newSAFTopics[i] = (SAFTopicBean)((SAFTopicBean)this.createCopy((AbstractDescriptorBean)oldSAFTopics[i], includeObsolete));
               }

               copy.setSAFTopics(newSAFTopics);
            }

            if ((excludeProps == null || !excludeProps.contains("TimeToLiveDefault")) && this.bean.isTimeToLiveDefaultSet()) {
               copy.setTimeToLiveDefault(this.bean.getTimeToLiveDefault());
            }

            if ((excludeProps == null || !excludeProps.contains("UnitOfOrderRouting")) && this.bean.isUnitOfOrderRoutingSet()) {
               copy.setUnitOfOrderRouting(this.bean.getUnitOfOrderRouting());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSAFTimeToLiveDefault")) && this.bean.isUseSAFTimeToLiveDefaultSet()) {
               copy.setUseSAFTimeToLiveDefault(this.bean.isUseSAFTimeToLiveDefault());
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
         this.inferSubTree(this.bean.getMessageLoggingParams(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFErrorHandling(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFQueues(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFRemoteContext(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFTopics(), clazz, annotation);
      }
   }
}
