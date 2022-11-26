package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.descriptor.beangen.CustomizerFactoryBuilder;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.customizers.TopicSubscriptionParamsBeanCustomizer;
import weblogic.j2ee.descriptor.wl.validators.JMSModuleValidator;
import weblogic.utils.collections.CombinedIterator;

public class TopicSubscriptionParamsBeanImpl extends SettableBeanImpl implements TopicSubscriptionParamsBean, Serializable {
   private long _MessagesLimitOverride;
   private TemplateBean _TemplateBean;
   private transient TopicSubscriptionParamsBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public TopicSubscriptionParamsBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.TopicSubscriptionParamsBeanCustomizerFactory");
         this._customizer = (TopicSubscriptionParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public TopicSubscriptionParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.TopicSubscriptionParamsBeanCustomizerFactory");
         this._customizer = (TopicSubscriptionParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public TopicSubscriptionParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.TopicSubscriptionParamsBeanCustomizerFactory");
         this._customizer = (TopicSubscriptionParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public long getMessagesLimitOverride() {
      if (!this._isSet(0)) {
         try {
            return this.getTemplateBean().getTopicSubscriptionParams().getMessagesLimitOverride();
         } catch (NullPointerException var2) {
         }
      }

      return this._MessagesLimitOverride;
   }

   public boolean isMessagesLimitOverrideInherited() {
      return false;
   }

   public boolean isMessagesLimitOverrideSet() {
      return this._isSet(0);
   }

   public void setMessagesLimitOverride(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MessagesLimitOverride", param0, -1L, Long.MAX_VALUE);
      JMSModuleValidator.validateTopicSubscriptionMessagesLimit(param0);
      long _oldVal = this._MessagesLimitOverride;
      this._MessagesLimitOverride = param0;
      this._postSet(0, _oldVal, param0);
   }

   public TemplateBean getTemplateBean() {
      return this._customizer.getTemplateBean();
   }

   public String getTemplateBeanAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getTemplateBean();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isTemplateBeanInherited() {
      return false;
   }

   public boolean isTemplateBeanSet() {
      return this._isSet(1);
   }

   public void setTemplateBeanAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, TemplateBean.class, new ReferenceManager.Resolver(this, 1) {
            public void resolveReference(Object value) {
               try {
                  TopicSubscriptionParamsBeanImpl.this.setTemplateBean((TemplateBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         TemplateBean _oldVal = this._TemplateBean;
         this._initializeProperty(1);
         this._postSet(1, _oldVal, this._TemplateBean);
      }

   }

   public void setTemplateBean(TemplateBean param0) throws InvalidAttributeValueException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 1, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return TopicSubscriptionParamsBeanImpl.this.getTemplateBean();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      TemplateBean _oldVal = this._TemplateBean;
      this._TemplateBean = param0;
      this._postSet(1, _oldVal, param0);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._MessagesLimitOverride = -1L;
               if (initOne) {
                  break;
               }
            case 1:
               this._TemplateBean = null;
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("template-bean")) {
                  return 1;
               }
               break;
            case 23:
               if (s.equals("messages-limit-override")) {
                  return 0;
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
               return "messages-limit-override";
            case 1:
               return "template-bean";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private TopicSubscriptionParamsBeanImpl bean;

      protected Helper(TopicSubscriptionParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MessagesLimitOverride";
            case 1:
               return "TemplateBean";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MessagesLimitOverride")) {
            return 0;
         } else {
            return propName.equals("TemplateBean") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isMessagesLimitOverrideSet()) {
               buf.append("MessagesLimitOverride");
               buf.append(String.valueOf(this.bean.getMessagesLimitOverride()));
            }

            if (this.bean.isTemplateBeanSet()) {
               buf.append("TemplateBean");
               buf.append(String.valueOf(this.bean.getTemplateBean()));
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
            TopicSubscriptionParamsBeanImpl otherTyped = (TopicSubscriptionParamsBeanImpl)other;
            this.computeDiff("MessagesLimitOverride", this.bean.getMessagesLimitOverride(), otherTyped.getMessagesLimitOverride(), true);
            this.computeDiff("TemplateBean", this.bean.getTemplateBean(), otherTyped.getTemplateBean(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TopicSubscriptionParamsBeanImpl original = (TopicSubscriptionParamsBeanImpl)event.getSourceBean();
            TopicSubscriptionParamsBeanImpl proposed = (TopicSubscriptionParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MessagesLimitOverride")) {
                  original.setMessagesLimitOverride(proposed.getMessagesLimitOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TemplateBean")) {
                  original.setTemplateBeanAsString(proposed.getTemplateBeanAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            TopicSubscriptionParamsBeanImpl copy = (TopicSubscriptionParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MessagesLimitOverride")) && this.bean.isMessagesLimitOverrideSet()) {
               copy.setMessagesLimitOverride(this.bean.getMessagesLimitOverride());
            }

            if ((excludeProps == null || !excludeProps.contains("TemplateBean")) && this.bean.isTemplateBeanSet()) {
               copy._unSet(copy, 1);
               copy.setTemplateBeanAsString(this.bean.getTemplateBeanAsString());
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
         this.inferSubTree(this.bean.getTemplateBean(), clazz, annotation);
      }
   }
}
