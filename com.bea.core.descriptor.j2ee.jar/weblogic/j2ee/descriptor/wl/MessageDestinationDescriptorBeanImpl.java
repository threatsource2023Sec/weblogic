package weblogic.j2ee.descriptor.wl;

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

public class MessageDestinationDescriptorBeanImpl extends AbstractDescriptorBean implements MessageDestinationDescriptorBean, Serializable {
   private String _DestinationJNDIName;
   private String _DestinationResourceLink;
   private String _Id;
   private String _InitialContextFactory;
   private String _MessageDestinationName;
   private String _ProviderUrl;
   private static SchemaHelper2 _schemaHelper;

   public MessageDestinationDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public MessageDestinationDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MessageDestinationDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getMessageDestinationName() {
      return this._MessageDestinationName;
   }

   public boolean isMessageDestinationNameInherited() {
      return false;
   }

   public boolean isMessageDestinationNameSet() {
      return this._isSet(0);
   }

   public void setMessageDestinationName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MessageDestinationName;
      this._MessageDestinationName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDestinationJNDIName() {
      return this._DestinationJNDIName;
   }

   public boolean isDestinationJNDINameInherited() {
      return false;
   }

   public boolean isDestinationJNDINameSet() {
      return this._isSet(1);
   }

   public void setDestinationJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DestinationJNDIName;
      this._DestinationJNDIName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getInitialContextFactory() {
      return this._InitialContextFactory;
   }

   public boolean isInitialContextFactoryInherited() {
      return false;
   }

   public boolean isInitialContextFactorySet() {
      return this._isSet(2);
   }

   public void setInitialContextFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InitialContextFactory;
      this._InitialContextFactory = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getProviderUrl() {
      return this._ProviderUrl;
   }

   public boolean isProviderUrlInherited() {
      return false;
   }

   public boolean isProviderUrlSet() {
      return this._isSet(3);
   }

   public void setProviderUrl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ProviderUrl;
      this._ProviderUrl = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getDestinationResourceLink() {
      return this._DestinationResourceLink;
   }

   public boolean isDestinationResourceLinkInherited() {
      return false;
   }

   public boolean isDestinationResourceLinkSet() {
      return this._isSet(4);
   }

   public void setDestinationResourceLink(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DestinationResourceLink;
      this._DestinationResourceLink = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getMessageDestinationName();
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
         case 24:
            if (s.equals("message-destination-name")) {
               return info.compareXpaths(this._getPropertyXpath("message-destination-name"));
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._DestinationJNDIName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._DestinationResourceLink = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._InitialContextFactory = "weblogic.jndi.WLInitialContextFactory";
               if (initOne) {
                  break;
               }
            case 0:
               this._MessageDestinationName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ProviderUrl = null;
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
            case 2:
               if (s.equals("id")) {
                  return 5;
               }
               break;
            case 12:
               if (s.equals("provider-url")) {
                  return 3;
               }
               break;
            case 21:
               if (s.equals("destination-jndi-name")) {
                  return 1;
               }
               break;
            case 23:
               if (s.equals("initial-context-factory")) {
                  return 2;
               }
               break;
            case 24:
               if (s.equals("message-destination-name")) {
                  return 0;
               }
               break;
            case 25:
               if (s.equals("destination-resource-link")) {
                  return 4;
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
               return "message-destination-name";
            case 1:
               return "destination-jndi-name";
            case 2:
               return "initial-context-factory";
            case 3:
               return "provider-url";
            case 4:
               return "destination-resource-link";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
         indices.add("message-destination-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MessageDestinationDescriptorBeanImpl bean;

      protected Helper(MessageDestinationDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MessageDestinationName";
            case 1:
               return "DestinationJNDIName";
            case 2:
               return "InitialContextFactory";
            case 3:
               return "ProviderUrl";
            case 4:
               return "DestinationResourceLink";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DestinationJNDIName")) {
            return 1;
         } else if (propName.equals("DestinationResourceLink")) {
            return 4;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("InitialContextFactory")) {
            return 2;
         } else if (propName.equals("MessageDestinationName")) {
            return 0;
         } else {
            return propName.equals("ProviderUrl") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isDestinationJNDINameSet()) {
               buf.append("DestinationJNDIName");
               buf.append(String.valueOf(this.bean.getDestinationJNDIName()));
            }

            if (this.bean.isDestinationResourceLinkSet()) {
               buf.append("DestinationResourceLink");
               buf.append(String.valueOf(this.bean.getDestinationResourceLink()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInitialContextFactorySet()) {
               buf.append("InitialContextFactory");
               buf.append(String.valueOf(this.bean.getInitialContextFactory()));
            }

            if (this.bean.isMessageDestinationNameSet()) {
               buf.append("MessageDestinationName");
               buf.append(String.valueOf(this.bean.getMessageDestinationName()));
            }

            if (this.bean.isProviderUrlSet()) {
               buf.append("ProviderUrl");
               buf.append(String.valueOf(this.bean.getProviderUrl()));
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
            MessageDestinationDescriptorBeanImpl otherTyped = (MessageDestinationDescriptorBeanImpl)other;
            this.computeDiff("DestinationJNDIName", this.bean.getDestinationJNDIName(), otherTyped.getDestinationJNDIName(), false);
            this.computeDiff("DestinationResourceLink", this.bean.getDestinationResourceLink(), otherTyped.getDestinationResourceLink(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("InitialContextFactory", this.bean.getInitialContextFactory(), otherTyped.getInitialContextFactory(), false);
            this.computeDiff("MessageDestinationName", this.bean.getMessageDestinationName(), otherTyped.getMessageDestinationName(), false);
            this.computeDiff("ProviderUrl", this.bean.getProviderUrl(), otherTyped.getProviderUrl(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MessageDestinationDescriptorBeanImpl original = (MessageDestinationDescriptorBeanImpl)event.getSourceBean();
            MessageDestinationDescriptorBeanImpl proposed = (MessageDestinationDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DestinationJNDIName")) {
                  original.setDestinationJNDIName(proposed.getDestinationJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DestinationResourceLink")) {
                  original.setDestinationResourceLink(proposed.getDestinationResourceLink());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("InitialContextFactory")) {
                  original.setInitialContextFactory(proposed.getInitialContextFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MessageDestinationName")) {
                  original.setMessageDestinationName(proposed.getMessageDestinationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ProviderUrl")) {
                  original.setProviderUrl(proposed.getProviderUrl());
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
            MessageDestinationDescriptorBeanImpl copy = (MessageDestinationDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DestinationJNDIName")) && this.bean.isDestinationJNDINameSet()) {
               copy.setDestinationJNDIName(this.bean.getDestinationJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationResourceLink")) && this.bean.isDestinationResourceLinkSet()) {
               copy.setDestinationResourceLink(this.bean.getDestinationResourceLink());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialContextFactory")) && this.bean.isInitialContextFactorySet()) {
               copy.setInitialContextFactory(this.bean.getInitialContextFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationName")) && this.bean.isMessageDestinationNameSet()) {
               copy.setMessageDestinationName(this.bean.getMessageDestinationName());
            }

            if ((excludeProps == null || !excludeProps.contains("ProviderUrl")) && this.bean.isProviderUrlSet()) {
               copy.setProviderUrl(this.bean.getProviderUrl());
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
