package com.bea.httppubsub.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class JmsHandlerBeanImpl extends AbstractDescriptorBean implements JmsHandlerBean, Serializable {
   private String _ConnectionFactoryJndiName;
   private String _JmsProviderUrl;
   private String _TopicJndiName;
   private static SchemaHelper2 _schemaHelper;

   public JmsHandlerBeanImpl() {
      this._initializeProperty(-1);
   }

   public JmsHandlerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JmsHandlerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getJmsProviderUrl() {
      return this._JmsProviderUrl;
   }

   public boolean isJmsProviderUrlInherited() {
      return false;
   }

   public boolean isJmsProviderUrlSet() {
      return this._isSet(0);
   }

   public void setJmsProviderUrl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JmsProviderUrl;
      this._JmsProviderUrl = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getConnectionFactoryJndiName() {
      return this._ConnectionFactoryJndiName;
   }

   public boolean isConnectionFactoryJndiNameInherited() {
      return false;
   }

   public boolean isConnectionFactoryJndiNameSet() {
      return this._isSet(1);
   }

   public void setConnectionFactoryJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactoryJndiName;
      this._ConnectionFactoryJndiName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getTopicJndiName() {
      return this._TopicJndiName;
   }

   public boolean isTopicJndiNameInherited() {
      return false;
   }

   public boolean isTopicJndiNameSet() {
      return this._isSet(2);
   }

   public void setTopicJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TopicJndiName;
      this._TopicJndiName = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._ConnectionFactoryJndiName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._JmsProviderUrl = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._TopicJndiName = null;
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
            case 15:
               if (s.equals("topic-jndi-name")) {
                  return 2;
               }
               break;
            case 16:
               if (s.equals("jms-provider-url")) {
                  return 0;
               }
               break;
            case 28:
               if (s.equals("connection-factory-jndi-name")) {
                  return 1;
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
               return "jms-provider-url";
            case 1:
               return "connection-factory-jndi-name";
            case 2:
               return "topic-jndi-name";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JmsHandlerBeanImpl bean;

      protected Helper(JmsHandlerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "JmsProviderUrl";
            case 1:
               return "ConnectionFactoryJndiName";
            case 2:
               return "TopicJndiName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactoryJndiName")) {
            return 1;
         } else if (propName.equals("JmsProviderUrl")) {
            return 0;
         } else {
            return propName.equals("TopicJndiName") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionFactoryJndiNameSet()) {
               buf.append("ConnectionFactoryJndiName");
               buf.append(String.valueOf(this.bean.getConnectionFactoryJndiName()));
            }

            if (this.bean.isJmsProviderUrlSet()) {
               buf.append("JmsProviderUrl");
               buf.append(String.valueOf(this.bean.getJmsProviderUrl()));
            }

            if (this.bean.isTopicJndiNameSet()) {
               buf.append("TopicJndiName");
               buf.append(String.valueOf(this.bean.getTopicJndiName()));
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
            JmsHandlerBeanImpl otherTyped = (JmsHandlerBeanImpl)other;
            this.computeDiff("ConnectionFactoryJndiName", this.bean.getConnectionFactoryJndiName(), otherTyped.getConnectionFactoryJndiName(), false);
            this.computeDiff("JmsProviderUrl", this.bean.getJmsProviderUrl(), otherTyped.getJmsProviderUrl(), false);
            this.computeDiff("TopicJndiName", this.bean.getTopicJndiName(), otherTyped.getTopicJndiName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JmsHandlerBeanImpl original = (JmsHandlerBeanImpl)event.getSourceBean();
            JmsHandlerBeanImpl proposed = (JmsHandlerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionFactoryJndiName")) {
                  original.setConnectionFactoryJndiName(proposed.getConnectionFactoryJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("JmsProviderUrl")) {
                  original.setJmsProviderUrl(proposed.getJmsProviderUrl());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TopicJndiName")) {
                  original.setTopicJndiName(proposed.getTopicJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            JmsHandlerBeanImpl copy = (JmsHandlerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryJndiName")) && this.bean.isConnectionFactoryJndiNameSet()) {
               copy.setConnectionFactoryJndiName(this.bean.getConnectionFactoryJndiName());
            }

            if ((excludeProps == null || !excludeProps.contains("JmsProviderUrl")) && this.bean.isJmsProviderUrlSet()) {
               copy.setJmsProviderUrl(this.bean.getJmsProviderUrl());
            }

            if ((excludeProps == null || !excludeProps.contains("TopicJndiName")) && this.bean.isTopicJndiNameSet()) {
               copy.setTopicJndiName(this.bean.getTopicJndiName());
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
