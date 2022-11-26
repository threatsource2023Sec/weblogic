package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class JMSDestinationMBeanImpl extends JMSDestCommonMBeanImpl implements JMSDestinationMBean, Serializable {
   private String _BytesPagingEnabled;
   private String _JNDIName;
   private boolean _JNDINameReplicated;
   private String _MessagesPagingEnabled;
   private String _StoreEnabled;
   private JMSTemplateMBean _Template;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private JMSDestinationMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(JMSDestinationMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(JMSDestinationMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public JMSDestinationMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(JMSDestinationMBeanImpl delegate) {
      JMSDestinationMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._Template instanceof JMSTemplateMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getTemplate() != null) {
            this._getReferenceManager().unregisterBean((JMSTemplateMBeanImpl)oldDelegate.getTemplate());
         }

         if (delegate != null && delegate.getTemplate() != null) {
            this._getReferenceManager().registerBean((JMSTemplateMBeanImpl)delegate.getTemplate(), false);
         }

         ((JMSTemplateMBeanImpl)this._Template)._setDelegateBean((JMSTemplateMBeanImpl)((JMSTemplateMBeanImpl)(delegate == null ? null : delegate.getTemplate())));
      }

   }

   public JMSDestinationMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JMSDestinationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JMSDestinationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public JMSTemplateMBean getTemplate() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getTemplate() : this._Template;
   }

   public String getTemplateAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getTemplate();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isTemplateInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isTemplateSet() {
      return this._isSet(28);
   }

   public void setTemplateAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JMSTemplateMBean.class, new ReferenceManager.Resolver(this, 28) {
            public void resolveReference(Object value) {
               try {
                  JMSDestinationMBeanImpl.this.setTemplate((JMSTemplateMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JMSTemplateMBean _oldVal = this._Template;
         this._initializeProperty(28);
         this._postSet(28, _oldVal, this._Template);
      }

   }

   public void setTemplate(JMSTemplateMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      JMSTemplateMBean _oldVal = this._Template;
      this._Template = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSDestinationMBeanImpl source = (JMSDestinationMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public String getJNDIName() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._performMacroSubstitution(this._getDelegateBean().getJNDIName(), this) : this._JNDIName;
   }

   public boolean isJNDINameInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isJNDINameSet() {
      return this._isSet(29);
   }

   public void setJNDIName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSDestinationMBeanImpl source = (JMSDestinationMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isJNDINameReplicated() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().isJNDINameReplicated() : this._JNDINameReplicated;
   }

   public boolean isJNDINameReplicatedInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isJNDINameReplicatedSet() {
      return this._isSet(30);
   }

   public void setJNDINameReplicated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      boolean _oldVal = this._JNDINameReplicated;
      this._JNDINameReplicated = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSDestinationMBeanImpl source = (JMSDestinationMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public String getStoreEnabled() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._performMacroSubstitution(this._getDelegateBean().getStoreEnabled(), this) : this._StoreEnabled;
   }

   public boolean isStoreEnabledInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isStoreEnabledSet() {
      return this._isSet(31);
   }

   public void setStoreEnabled(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"default", "false", "true"};
      param0 = LegalChecks.checkInEnum("StoreEnabled", param0, _set);
      boolean wasSet = this._isSet(31);
      String _oldVal = this._StoreEnabled;
      this._StoreEnabled = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSDestinationMBeanImpl source = (JMSDestinationMBeanImpl)var5.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMessagesPagingEnabled() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._performMacroSubstitution(this._getDelegateBean().getMessagesPagingEnabled(), this) : this._MessagesPagingEnabled;
   }

   public boolean isMessagesPagingEnabledInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isMessagesPagingEnabledSet() {
      return this._isSet(32);
   }

   public void setMessagesPagingEnabled(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"default", "false", "true"};
      param0 = LegalChecks.checkInEnum("MessagesPagingEnabled", param0, _set);
      boolean wasSet = this._isSet(32);
      String _oldVal = this._MessagesPagingEnabled;
      this._MessagesPagingEnabled = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSDestinationMBeanImpl source = (JMSDestinationMBeanImpl)var5.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public String getBytesPagingEnabled() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._performMacroSubstitution(this._getDelegateBean().getBytesPagingEnabled(), this) : this._BytesPagingEnabled;
   }

   public boolean isBytesPagingEnabledInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isBytesPagingEnabledSet() {
      return this._isSet(33);
   }

   public void setBytesPagingEnabled(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"default", "false", "true"};
      param0 = LegalChecks.checkInEnum("BytesPagingEnabled", param0, _set);
      boolean wasSet = this._isSet(33);
      String _oldVal = this._BytesPagingEnabled;
      this._BytesPagingEnabled = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSDestinationMBeanImpl source = (JMSDestinationMBeanImpl)var5.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
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
         idx = 33;
      }

      try {
         switch (idx) {
            case 33:
               this._BytesPagingEnabled = "default";
               if (initOne) {
                  break;
               }
            case 29:
               this._JNDIName = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._MessagesPagingEnabled = "default";
               if (initOne) {
                  break;
               }
            case 31:
               this._StoreEnabled = "default";
               if (initOne) {
                  break;
               }
            case 28:
               this._Template = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._JNDINameReplicated = true;
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
      return "JMSDestination";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("BytesPagingEnabled")) {
         oldVal = this._BytesPagingEnabled;
         this._BytesPagingEnabled = (String)v;
         this._postSet(33, oldVal, this._BytesPagingEnabled);
      } else if (name.equals("JNDIName")) {
         oldVal = this._JNDIName;
         this._JNDIName = (String)v;
         this._postSet(29, oldVal, this._JNDIName);
      } else if (name.equals("JNDINameReplicated")) {
         boolean oldVal = this._JNDINameReplicated;
         this._JNDINameReplicated = (Boolean)v;
         this._postSet(30, oldVal, this._JNDINameReplicated);
      } else if (name.equals("MessagesPagingEnabled")) {
         oldVal = this._MessagesPagingEnabled;
         this._MessagesPagingEnabled = (String)v;
         this._postSet(32, oldVal, this._MessagesPagingEnabled);
      } else if (name.equals("StoreEnabled")) {
         oldVal = this._StoreEnabled;
         this._StoreEnabled = (String)v;
         this._postSet(31, oldVal, this._StoreEnabled);
      } else if (name.equals("Template")) {
         JMSTemplateMBean oldVal = this._Template;
         this._Template = (JMSTemplateMBean)v;
         this._postSet(28, oldVal, this._Template);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("BytesPagingEnabled")) {
         return this._BytesPagingEnabled;
      } else if (name.equals("JNDIName")) {
         return this._JNDIName;
      } else if (name.equals("JNDINameReplicated")) {
         return new Boolean(this._JNDINameReplicated);
      } else if (name.equals("MessagesPagingEnabled")) {
         return this._MessagesPagingEnabled;
      } else if (name.equals("StoreEnabled")) {
         return this._StoreEnabled;
      } else {
         return name.equals("Template") ? this._Template : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends JMSDestCommonMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("template")) {
                  return 28;
               }
               break;
            case 9:
               if (s.equals("jndi-name")) {
                  return 29;
               }
               break;
            case 13:
               if (s.equals("store-enabled")) {
                  return 31;
               }
               break;
            case 20:
               if (s.equals("bytes-paging-enabled")) {
                  return 33;
               }

               if (s.equals("jndi-name-replicated")) {
                  return 30;
               }
               break;
            case 23:
               if (s.equals("messages-paging-enabled")) {
                  return 32;
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
            case 28:
               return "template";
            case 29:
               return "jndi-name";
            case 30:
               return "jndi-name-replicated";
            case 31:
               return "store-enabled";
            case 32:
               return "messages-paging-enabled";
            case 33:
               return "bytes-paging-enabled";
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

   protected static class Helper extends JMSDestCommonMBeanImpl.Helper {
      private JMSDestinationMBeanImpl bean;

      protected Helper(JMSDestinationMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 28:
               return "Template";
            case 29:
               return "JNDIName";
            case 30:
               return "JNDINameReplicated";
            case 31:
               return "StoreEnabled";
            case 32:
               return "MessagesPagingEnabled";
            case 33:
               return "BytesPagingEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BytesPagingEnabled")) {
            return 33;
         } else if (propName.equals("JNDIName")) {
            return 29;
         } else if (propName.equals("MessagesPagingEnabled")) {
            return 32;
         } else if (propName.equals("StoreEnabled")) {
            return 31;
         } else if (propName.equals("Template")) {
            return 28;
         } else {
            return propName.equals("JNDINameReplicated") ? 30 : super.getPropertyIndex(propName);
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
            if (this.bean.isBytesPagingEnabledSet()) {
               buf.append("BytesPagingEnabled");
               buf.append(String.valueOf(this.bean.getBytesPagingEnabled()));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            if (this.bean.isMessagesPagingEnabledSet()) {
               buf.append("MessagesPagingEnabled");
               buf.append(String.valueOf(this.bean.getMessagesPagingEnabled()));
            }

            if (this.bean.isStoreEnabledSet()) {
               buf.append("StoreEnabled");
               buf.append(String.valueOf(this.bean.getStoreEnabled()));
            }

            if (this.bean.isTemplateSet()) {
               buf.append("Template");
               buf.append(String.valueOf(this.bean.getTemplate()));
            }

            if (this.bean.isJNDINameReplicatedSet()) {
               buf.append("JNDINameReplicated");
               buf.append(String.valueOf(this.bean.isJNDINameReplicated()));
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
            JMSDestinationMBeanImpl otherTyped = (JMSDestinationMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("BytesPagingEnabled", this.bean.getBytesPagingEnabled(), otherTyped.getBytesPagingEnabled(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MessagesPagingEnabled", this.bean.getMessagesPagingEnabled(), otherTyped.getMessagesPagingEnabled(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StoreEnabled", this.bean.getStoreEnabled(), otherTyped.getStoreEnabled(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Template", this.bean.getTemplate(), otherTyped.getTemplate(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("JNDINameReplicated", this.bean.isJNDINameReplicated(), otherTyped.isJNDINameReplicated(), false);
            }

         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSDestinationMBeanImpl original = (JMSDestinationMBeanImpl)event.getSourceBean();
            JMSDestinationMBeanImpl proposed = (JMSDestinationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BytesPagingEnabled")) {
                  original.setBytesPagingEnabled(proposed.getBytesPagingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("MessagesPagingEnabled")) {
                  original.setMessagesPagingEnabled(proposed.getMessagesPagingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("StoreEnabled")) {
                  original.setStoreEnabled(proposed.getStoreEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("Template")) {
                  original.setTemplateAsString(proposed.getTemplateAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("JNDINameReplicated")) {
                  original.setJNDINameReplicated(proposed.isJNDINameReplicated());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
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
            JMSDestinationMBeanImpl copy = (JMSDestinationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("BytesPagingEnabled")) && this.bean.isBytesPagingEnabledSet()) {
               copy.setBytesPagingEnabled(this.bean.getBytesPagingEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MessagesPagingEnabled")) && this.bean.isMessagesPagingEnabledSet()) {
               copy.setMessagesPagingEnabled(this.bean.getMessagesPagingEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StoreEnabled")) && this.bean.isStoreEnabledSet()) {
               copy.setStoreEnabled(this.bean.getStoreEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Template")) && this.bean.isTemplateSet()) {
               copy._unSet(copy, 28);
               copy.setTemplateAsString(this.bean.getTemplateAsString());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JNDINameReplicated")) && this.bean.isJNDINameReplicatedSet()) {
               copy.setJNDINameReplicated(this.bean.isJNDINameReplicated());
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
         this.inferSubTree(this.bean.getTemplate(), clazz, annotation);
      }
   }
}
