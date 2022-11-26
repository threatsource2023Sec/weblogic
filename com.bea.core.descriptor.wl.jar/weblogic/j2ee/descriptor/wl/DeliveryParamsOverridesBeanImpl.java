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
import weblogic.j2ee.descriptor.wl.customizers.DeliveryParamsOverridesBeanCustomizer;
import weblogic.j2ee.descriptor.wl.validators.JMSModuleValidator;
import weblogic.utils.collections.CombinedIterator;

public class DeliveryParamsOverridesBeanImpl extends SettableBeanImpl implements DeliveryParamsOverridesBean, Serializable {
   private String _DeliveryMode;
   private int _Priority;
   private long _RedeliveryDelay;
   private TemplateBean _TemplateBean;
   private String _TimeToDeliver;
   private long _TimeToLive;
   private transient DeliveryParamsOverridesBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public DeliveryParamsOverridesBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.DeliveryParamsOverridesBeanCustomizerFactory");
         this._customizer = (DeliveryParamsOverridesBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public DeliveryParamsOverridesBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.DeliveryParamsOverridesBeanCustomizerFactory");
         this._customizer = (DeliveryParamsOverridesBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public DeliveryParamsOverridesBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.DeliveryParamsOverridesBeanCustomizerFactory");
         this._customizer = (DeliveryParamsOverridesBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getDeliveryMode() {
      if (!this._isSet(0)) {
         try {
            return this.getTemplateBean().getDeliveryParamsOverrides().getDeliveryMode();
         } catch (NullPointerException var2) {
         }
      }

      return this._DeliveryMode;
   }

   public boolean isDeliveryModeInherited() {
      return false;
   }

   public boolean isDeliveryModeSet() {
      return this._isSet(0);
   }

   public void setDeliveryMode(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Persistent", "Non-Persistent", "No-Delivery"};
      param0 = LegalChecks.checkInEnum("DeliveryMode", param0, _set);
      String _oldVal = this._DeliveryMode;
      this._DeliveryMode = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getTimeToDeliver() {
      if (!this._isSet(1)) {
         try {
            return this.getTemplateBean().getDeliveryParamsOverrides().getTimeToDeliver();
         } catch (NullPointerException var2) {
         }
      }

      return this._TimeToDeliver;
   }

   public boolean isTimeToDeliverInherited() {
      return false;
   }

   public boolean isTimeToDeliverSet() {
      return this._isSet(1);
   }

   public void setTimeToDeliver(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      JMSModuleValidator.validateTimeToDeliverOverride(param0);
      String _oldVal = this._TimeToDeliver;
      this._TimeToDeliver = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getTimeToLive() {
      if (!this._isSet(2)) {
         try {
            return this.getTemplateBean().getDeliveryParamsOverrides().getTimeToLive();
         } catch (NullPointerException var2) {
         }
      }

      return this._TimeToLive;
   }

   public boolean isTimeToLiveInherited() {
      return false;
   }

   public boolean isTimeToLiveSet() {
      return this._isSet(2);
   }

   public void setTimeToLive(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("TimeToLive", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._TimeToLive;
      this._TimeToLive = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getPriority() {
      if (!this._isSet(3)) {
         try {
            return this.getTemplateBean().getDeliveryParamsOverrides().getPriority();
         } catch (NullPointerException var2) {
         }
      }

      return this._Priority;
   }

   public boolean isPriorityInherited() {
      return false;
   }

   public boolean isPrioritySet() {
      return this._isSet(3);
   }

   public void setPriority(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("Priority", (long)param0, -1L, 9L);
      int _oldVal = this._Priority;
      this._Priority = param0;
      this._postSet(3, _oldVal, param0);
   }

   public long getRedeliveryDelay() {
      if (!this._isSet(4)) {
         try {
            return this.getTemplateBean().getDeliveryParamsOverrides().getRedeliveryDelay();
         } catch (NullPointerException var2) {
         }
      }

      return this._RedeliveryDelay;
   }

   public boolean isRedeliveryDelayInherited() {
      return false;
   }

   public boolean isRedeliveryDelaySet() {
      return this._isSet(4);
   }

   public void setRedeliveryDelay(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("RedeliveryDelay", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._RedeliveryDelay;
      this._RedeliveryDelay = param0;
      this._postSet(4, _oldVal, param0);
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
      return this._isSet(5);
   }

   public void setTemplateBeanAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, TemplateBean.class, new ReferenceManager.Resolver(this, 5) {
            public void resolveReference(Object value) {
               try {
                  DeliveryParamsOverridesBeanImpl.this.setTemplateBean((TemplateBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         TemplateBean _oldVal = this._TemplateBean;
         this._initializeProperty(5);
         this._postSet(5, _oldVal, this._TemplateBean);
      }

   }

   public void setTemplateBean(TemplateBean param0) throws InvalidAttributeValueException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 5, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return DeliveryParamsOverridesBeanImpl.this.getTemplateBean();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      TemplateBean _oldVal = this._TemplateBean;
      this._TemplateBean = param0;
      this._postSet(5, _oldVal, param0);
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
               this._DeliveryMode = "No-Delivery";
               if (initOne) {
                  break;
               }
            case 3:
               this._Priority = -1;
               if (initOne) {
                  break;
               }
            case 4:
               this._RedeliveryDelay = -1L;
               if (initOne) {
                  break;
               }
            case 5:
               this._TemplateBean = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._TimeToDeliver = "-1";
               if (initOne) {
                  break;
               }
            case 2:
               this._TimeToLive = -1L;
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
            case 8:
               if (s.equals("priority")) {
                  return 3;
               }
            case 9:
            case 10:
            case 11:
            case 14:
            default:
               break;
            case 12:
               if (s.equals("time-to-live")) {
                  return 2;
               }
               break;
            case 13:
               if (s.equals("delivery-mode")) {
                  return 0;
               }

               if (s.equals("template-bean")) {
                  return 5;
               }
               break;
            case 15:
               if (s.equals("time-to-deliver")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("redelivery-delay")) {
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
               return "delivery-mode";
            case 1:
               return "time-to-deliver";
            case 2:
               return "time-to-live";
            case 3:
               return "priority";
            case 4:
               return "redelivery-delay";
            case 5:
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
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private DeliveryParamsOverridesBeanImpl bean;

      protected Helper(DeliveryParamsOverridesBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DeliveryMode";
            case 1:
               return "TimeToDeliver";
            case 2:
               return "TimeToLive";
            case 3:
               return "Priority";
            case 4:
               return "RedeliveryDelay";
            case 5:
               return "TemplateBean";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DeliveryMode")) {
            return 0;
         } else if (propName.equals("Priority")) {
            return 3;
         } else if (propName.equals("RedeliveryDelay")) {
            return 4;
         } else if (propName.equals("TemplateBean")) {
            return 5;
         } else if (propName.equals("TimeToDeliver")) {
            return 1;
         } else {
            return propName.equals("TimeToLive") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isDeliveryModeSet()) {
               buf.append("DeliveryMode");
               buf.append(String.valueOf(this.bean.getDeliveryMode()));
            }

            if (this.bean.isPrioritySet()) {
               buf.append("Priority");
               buf.append(String.valueOf(this.bean.getPriority()));
            }

            if (this.bean.isRedeliveryDelaySet()) {
               buf.append("RedeliveryDelay");
               buf.append(String.valueOf(this.bean.getRedeliveryDelay()));
            }

            if (this.bean.isTemplateBeanSet()) {
               buf.append("TemplateBean");
               buf.append(String.valueOf(this.bean.getTemplateBean()));
            }

            if (this.bean.isTimeToDeliverSet()) {
               buf.append("TimeToDeliver");
               buf.append(String.valueOf(this.bean.getTimeToDeliver()));
            }

            if (this.bean.isTimeToLiveSet()) {
               buf.append("TimeToLive");
               buf.append(String.valueOf(this.bean.getTimeToLive()));
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
            DeliveryParamsOverridesBeanImpl otherTyped = (DeliveryParamsOverridesBeanImpl)other;
            this.computeDiff("DeliveryMode", this.bean.getDeliveryMode(), otherTyped.getDeliveryMode(), true);
            this.computeDiff("Priority", this.bean.getPriority(), otherTyped.getPriority(), true);
            this.computeDiff("RedeliveryDelay", this.bean.getRedeliveryDelay(), otherTyped.getRedeliveryDelay(), true);
            this.computeDiff("TemplateBean", this.bean.getTemplateBean(), otherTyped.getTemplateBean(), false);
            this.computeDiff("TimeToDeliver", this.bean.getTimeToDeliver(), otherTyped.getTimeToDeliver(), true);
            this.computeDiff("TimeToLive", this.bean.getTimeToLive(), otherTyped.getTimeToLive(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DeliveryParamsOverridesBeanImpl original = (DeliveryParamsOverridesBeanImpl)event.getSourceBean();
            DeliveryParamsOverridesBeanImpl proposed = (DeliveryParamsOverridesBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DeliveryMode")) {
                  original.setDeliveryMode(proposed.getDeliveryMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Priority")) {
                  original.setPriority(proposed.getPriority());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RedeliveryDelay")) {
                  original.setRedeliveryDelay(proposed.getRedeliveryDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TemplateBean")) {
                  original.setTemplateBeanAsString(proposed.getTemplateBeanAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("TimeToDeliver")) {
                  original.setTimeToDeliver(proposed.getTimeToDeliver());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("TimeToLive")) {
                  original.setTimeToLive(proposed.getTimeToLive());
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
            DeliveryParamsOverridesBeanImpl copy = (DeliveryParamsOverridesBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DeliveryMode")) && this.bean.isDeliveryModeSet()) {
               copy.setDeliveryMode(this.bean.getDeliveryMode());
            }

            if ((excludeProps == null || !excludeProps.contains("Priority")) && this.bean.isPrioritySet()) {
               copy.setPriority(this.bean.getPriority());
            }

            if ((excludeProps == null || !excludeProps.contains("RedeliveryDelay")) && this.bean.isRedeliveryDelaySet()) {
               copy.setRedeliveryDelay(this.bean.getRedeliveryDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("TemplateBean")) && this.bean.isTemplateBeanSet()) {
               copy._unSet(copy, 5);
               copy.setTemplateBeanAsString(this.bean.getTemplateBeanAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeToDeliver")) && this.bean.isTimeToDeliverSet()) {
               copy.setTimeToDeliver(this.bean.getTimeToDeliver());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeToLive")) && this.bean.isTimeToLiveSet()) {
               copy.setTimeToLive(this.bean.getTimeToLive());
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
