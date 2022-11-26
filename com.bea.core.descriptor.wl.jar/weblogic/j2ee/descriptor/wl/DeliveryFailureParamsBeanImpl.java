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
import weblogic.j2ee.descriptor.wl.customizers.DeliveryFailureParamsBeanCustomizer;
import weblogic.utils.collections.CombinedIterator;

public class DeliveryFailureParamsBeanImpl extends SettableBeanImpl implements DeliveryFailureParamsBean, Serializable {
   private DestinationBean _ErrorDestination;
   private String _ExpirationLoggingPolicy;
   private String _ExpirationPolicy;
   private int _RedeliveryLimit;
   private TemplateBean _TemplateBean;
   private transient DeliveryFailureParamsBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public DeliveryFailureParamsBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.DeliveryFailureParamsBeanCustomizerFactory");
         this._customizer = (DeliveryFailureParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public DeliveryFailureParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.DeliveryFailureParamsBeanCustomizerFactory");
         this._customizer = (DeliveryFailureParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public DeliveryFailureParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.DeliveryFailureParamsBeanCustomizerFactory");
         this._customizer = (DeliveryFailureParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public DestinationBean getErrorDestination() {
      if (!this._isSet(0)) {
         try {
            return this.getTemplateBean().findErrorDestination(this.findSubDeploymentName());
         } catch (NullPointerException var2) {
         }
      }

      return this._ErrorDestination;
   }

   public String getErrorDestinationAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getErrorDestination();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isErrorDestinationInherited() {
      return false;
   }

   public boolean isErrorDestinationSet() {
      return this._isSet(0);
   }

   public void setErrorDestinationAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, DestinationBean.class, new ReferenceManager.Resolver(this, 0) {
            public void resolveReference(Object value) {
               try {
                  DeliveryFailureParamsBeanImpl.this.setErrorDestination((DestinationBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         DestinationBean _oldVal = this._ErrorDestination;
         this._initializeProperty(0);
         this._postSet(0, _oldVal, this._ErrorDestination);
      }

   }

   public void setErrorDestination(DestinationBean param0) throws IllegalArgumentException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 0, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return DeliveryFailureParamsBeanImpl.this.getErrorDestination();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      DestinationBean _oldVal = this._ErrorDestination;
      this._ErrorDestination = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getRedeliveryLimit() {
      if (!this._isSet(1)) {
         try {
            return this.getTemplateBean().getDeliveryFailureParams().getRedeliveryLimit();
         } catch (NullPointerException var2) {
         }
      }

      return this._RedeliveryLimit;
   }

   public boolean isRedeliveryLimitInherited() {
      return false;
   }

   public boolean isRedeliveryLimitSet() {
      return this._isSet(1);
   }

   public void setRedeliveryLimit(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("RedeliveryLimit", (long)param0, -1L, 2147483647L);
      int _oldVal = this._RedeliveryLimit;
      this._RedeliveryLimit = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getExpirationPolicy() {
      if (!this._isSet(2)) {
         try {
            return this.getTemplateBean().getDeliveryFailureParams().getExpirationPolicy();
         } catch (NullPointerException var2) {
         }
      }

      return this._ExpirationPolicy;
   }

   public boolean isExpirationPolicyInherited() {
      return false;
   }

   public boolean isExpirationPolicySet() {
      return this._isSet(2);
   }

   public void setExpirationPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Discard", "Log", "Redirect"};
      param0 = LegalChecks.checkInEnum("ExpirationPolicy", param0, _set);
      String _oldVal = this._ExpirationPolicy;
      this._ExpirationPolicy = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getExpirationLoggingPolicy() {
      if (!this._isSet(3)) {
         try {
            return this.getTemplateBean().getDeliveryFailureParams().getExpirationLoggingPolicy();
         } catch (NullPointerException var2) {
         }
      }

      return this._ExpirationLoggingPolicy;
   }

   public boolean isExpirationLoggingPolicyInherited() {
      return false;
   }

   public boolean isExpirationLoggingPolicySet() {
      return this._isSet(3);
   }

   public void setExpirationLoggingPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ExpirationLoggingPolicy;
      this._ExpirationLoggingPolicy = param0;
      this._postSet(3, _oldVal, param0);
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
      return this._isSet(4);
   }

   public void setTemplateBeanAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, TemplateBean.class, new ReferenceManager.Resolver(this, 4) {
            public void resolveReference(Object value) {
               try {
                  DeliveryFailureParamsBeanImpl.this.setTemplateBean((TemplateBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         TemplateBean _oldVal = this._TemplateBean;
         this._initializeProperty(4);
         this._postSet(4, _oldVal, this._TemplateBean);
      }

   }

   public void setTemplateBean(TemplateBean param0) throws InvalidAttributeValueException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 4, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return DeliveryFailureParamsBeanImpl.this.getTemplateBean();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      TemplateBean _oldVal = this._TemplateBean;
      this._TemplateBean = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String findSubDeploymentName() {
      return this._customizer.findSubDeploymentName();
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
               this._ErrorDestination = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ExpirationLoggingPolicy = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ExpirationPolicy = "Discard";
               if (initOne) {
                  break;
               }
            case 1:
               this._RedeliveryLimit = -1;
               if (initOne) {
                  break;
               }
            case 4:
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
                  return 4;
               }
               break;
            case 16:
               if (s.equals("redelivery-limit")) {
                  return 1;
               }
               break;
            case 17:
               if (s.equals("error-destination")) {
                  return 0;
               }

               if (s.equals("expiration-policy")) {
                  return 2;
               }
               break;
            case 25:
               if (s.equals("expiration-logging-policy")) {
                  return 3;
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
               return "error-destination";
            case 1:
               return "redelivery-limit";
            case 2:
               return "expiration-policy";
            case 3:
               return "expiration-logging-policy";
            case 4:
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private DeliveryFailureParamsBeanImpl bean;

      protected Helper(DeliveryFailureParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ErrorDestination";
            case 1:
               return "RedeliveryLimit";
            case 2:
               return "ExpirationPolicy";
            case 3:
               return "ExpirationLoggingPolicy";
            case 4:
               return "TemplateBean";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ErrorDestination")) {
            return 0;
         } else if (propName.equals("ExpirationLoggingPolicy")) {
            return 3;
         } else if (propName.equals("ExpirationPolicy")) {
            return 2;
         } else if (propName.equals("RedeliveryLimit")) {
            return 1;
         } else {
            return propName.equals("TemplateBean") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isErrorDestinationSet()) {
               buf.append("ErrorDestination");
               buf.append(String.valueOf(this.bean.getErrorDestination()));
            }

            if (this.bean.isExpirationLoggingPolicySet()) {
               buf.append("ExpirationLoggingPolicy");
               buf.append(String.valueOf(this.bean.getExpirationLoggingPolicy()));
            }

            if (this.bean.isExpirationPolicySet()) {
               buf.append("ExpirationPolicy");
               buf.append(String.valueOf(this.bean.getExpirationPolicy()));
            }

            if (this.bean.isRedeliveryLimitSet()) {
               buf.append("RedeliveryLimit");
               buf.append(String.valueOf(this.bean.getRedeliveryLimit()));
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
            DeliveryFailureParamsBeanImpl otherTyped = (DeliveryFailureParamsBeanImpl)other;
            this.computeDiff("ErrorDestination", this.bean.getErrorDestination(), otherTyped.getErrorDestination(), true);
            this.computeDiff("ExpirationLoggingPolicy", this.bean.getExpirationLoggingPolicy(), otherTyped.getExpirationLoggingPolicy(), true);
            this.computeDiff("ExpirationPolicy", this.bean.getExpirationPolicy(), otherTyped.getExpirationPolicy(), true);
            this.computeDiff("RedeliveryLimit", this.bean.getRedeliveryLimit(), otherTyped.getRedeliveryLimit(), true);
            this.computeDiff("TemplateBean", this.bean.getTemplateBean(), otherTyped.getTemplateBean(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DeliveryFailureParamsBeanImpl original = (DeliveryFailureParamsBeanImpl)event.getSourceBean();
            DeliveryFailureParamsBeanImpl proposed = (DeliveryFailureParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ErrorDestination")) {
                  original.setErrorDestinationAsString(proposed.getErrorDestinationAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ExpirationLoggingPolicy")) {
                  original.setExpirationLoggingPolicy(proposed.getExpirationLoggingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ExpirationPolicy")) {
                  original.setExpirationPolicy(proposed.getExpirationPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RedeliveryLimit")) {
                  original.setRedeliveryLimit(proposed.getRedeliveryLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("TemplateBean")) {
                  original.setTemplateBeanAsString(proposed.getTemplateBeanAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            DeliveryFailureParamsBeanImpl copy = (DeliveryFailureParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ErrorDestination")) && this.bean.isErrorDestinationSet()) {
               copy._unSet(copy, 0);
               copy.setErrorDestinationAsString(this.bean.getErrorDestinationAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ExpirationLoggingPolicy")) && this.bean.isExpirationLoggingPolicySet()) {
               copy.setExpirationLoggingPolicy(this.bean.getExpirationLoggingPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("ExpirationPolicy")) && this.bean.isExpirationPolicySet()) {
               copy.setExpirationPolicy(this.bean.getExpirationPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("RedeliveryLimit")) && this.bean.isRedeliveryLimitSet()) {
               copy.setRedeliveryLimit(this.bean.getRedeliveryLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("TemplateBean")) && this.bean.isTemplateBeanSet()) {
               copy._unSet(copy, 4);
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
         this.inferSubTree(this.bean.getErrorDestination(), clazz, annotation);
         this.inferSubTree(this.bean.getTemplateBean(), clazz, annotation);
      }
   }
}
