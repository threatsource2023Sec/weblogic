package kodo.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class JMSRemoteCommitProviderBeanImpl extends RemoteCommitProviderBeanImpl implements JMSRemoteCommitProviderBean, Serializable {
   private int _ExceptionReconnectAttempts;
   private String _Topic;
   private String _TopicConnectionFactory;
   private static SchemaHelper2 _schemaHelper;

   public JMSRemoteCommitProviderBeanImpl() {
      this._initializeProperty(-1);
   }

   public JMSRemoteCommitProviderBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JMSRemoteCommitProviderBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getTopic() {
      return this._Topic;
   }

   public boolean isTopicInherited() {
      return false;
   }

   public boolean isTopicSet() {
      return this._isSet(1);
   }

   public void setTopic(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Topic;
      this._Topic = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getExceptionReconnectAttempts() {
      return this._ExceptionReconnectAttempts;
   }

   public boolean isExceptionReconnectAttemptsInherited() {
      return false;
   }

   public boolean isExceptionReconnectAttemptsSet() {
      return this._isSet(2);
   }

   public void setExceptionReconnectAttempts(int param0) {
      int _oldVal = this._ExceptionReconnectAttempts;
      this._ExceptionReconnectAttempts = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getTopicConnectionFactory() {
      return this._TopicConnectionFactory;
   }

   public boolean isTopicConnectionFactoryInherited() {
      return false;
   }

   public boolean isTopicConnectionFactorySet() {
      return this._isSet(3);
   }

   public void setTopicConnectionFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TopicConnectionFactory;
      this._TopicConnectionFactory = param0;
      this._postSet(3, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ExceptionReconnectAttempts = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._Topic = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._TopicConnectionFactory = null;
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

   public static class SchemaHelper2 extends RemoteCommitProviderBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("topic")) {
                  return 1;
               }
               break;
            case 24:
               if (s.equals("topic-connection-factory")) {
                  return 3;
               }
               break;
            case 28:
               if (s.equals("exception-reconnect-attempts")) {
                  return 2;
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
               return "topic";
            case 2:
               return "exception-reconnect-attempts";
            case 3:
               return "topic-connection-factory";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends RemoteCommitProviderBeanImpl.Helper {
      private JMSRemoteCommitProviderBeanImpl bean;

      protected Helper(JMSRemoteCommitProviderBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "Topic";
            case 2:
               return "ExceptionReconnectAttempts";
            case 3:
               return "TopicConnectionFactory";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ExceptionReconnectAttempts")) {
            return 2;
         } else if (propName.equals("Topic")) {
            return 1;
         } else {
            return propName.equals("TopicConnectionFactory") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isExceptionReconnectAttemptsSet()) {
               buf.append("ExceptionReconnectAttempts");
               buf.append(String.valueOf(this.bean.getExceptionReconnectAttempts()));
            }

            if (this.bean.isTopicSet()) {
               buf.append("Topic");
               buf.append(String.valueOf(this.bean.getTopic()));
            }

            if (this.bean.isTopicConnectionFactorySet()) {
               buf.append("TopicConnectionFactory");
               buf.append(String.valueOf(this.bean.getTopicConnectionFactory()));
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
            JMSRemoteCommitProviderBeanImpl otherTyped = (JMSRemoteCommitProviderBeanImpl)other;
            this.computeDiff("ExceptionReconnectAttempts", this.bean.getExceptionReconnectAttempts(), otherTyped.getExceptionReconnectAttempts(), false);
            this.computeDiff("Topic", this.bean.getTopic(), otherTyped.getTopic(), false);
            this.computeDiff("TopicConnectionFactory", this.bean.getTopicConnectionFactory(), otherTyped.getTopicConnectionFactory(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSRemoteCommitProviderBeanImpl original = (JMSRemoteCommitProviderBeanImpl)event.getSourceBean();
            JMSRemoteCommitProviderBeanImpl proposed = (JMSRemoteCommitProviderBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ExceptionReconnectAttempts")) {
                  original.setExceptionReconnectAttempts(proposed.getExceptionReconnectAttempts());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Topic")) {
                  original.setTopic(proposed.getTopic());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("TopicConnectionFactory")) {
                  original.setTopicConnectionFactory(proposed.getTopicConnectionFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            JMSRemoteCommitProviderBeanImpl copy = (JMSRemoteCommitProviderBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ExceptionReconnectAttempts")) && this.bean.isExceptionReconnectAttemptsSet()) {
               copy.setExceptionReconnectAttempts(this.bean.getExceptionReconnectAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("Topic")) && this.bean.isTopicSet()) {
               copy.setTopic(this.bean.getTopic());
            }

            if ((excludeProps == null || !excludeProps.contains("TopicConnectionFactory")) && this.bean.isTopicConnectionFactorySet()) {
               copy.setTopicConnectionFactory(this.bean.getTopicConnectionFactory());
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
