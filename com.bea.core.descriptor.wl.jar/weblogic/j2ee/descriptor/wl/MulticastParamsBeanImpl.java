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
import weblogic.j2ee.descriptor.wl.customizers.MulticastParamsBeanCustomizer;
import weblogic.j2ee.descriptor.wl.validators.JMSModuleValidator;
import weblogic.utils.collections.CombinedIterator;

public class MulticastParamsBeanImpl extends SettableBeanImpl implements MulticastParamsBean, Serializable {
   private String _MulticastAddress;
   private int _MulticastPort;
   private int _MulticastTimeToLive;
   private TemplateBean _TemplateBean;
   private transient MulticastParamsBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public MulticastParamsBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.MulticastParamsBeanCustomizerFactory");
         this._customizer = (MulticastParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public MulticastParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.MulticastParamsBeanCustomizerFactory");
         this._customizer = (MulticastParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public MulticastParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.MulticastParamsBeanCustomizerFactory");
         this._customizer = (MulticastParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getMulticastAddress() {
      if (!this._isSet(0)) {
         try {
            return this.getTemplateBean().getMulticast().getMulticastAddress();
         } catch (NullPointerException var2) {
         }
      }

      return this._MulticastAddress;
   }

   public boolean isMulticastAddressInherited() {
      return false;
   }

   public boolean isMulticastAddressSet() {
      return this._isSet(0);
   }

   public void setMulticastAddress(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      JMSModuleValidator.validateMulticastAddress(param0);
      String _oldVal = this._MulticastAddress;
      this._MulticastAddress = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getMulticastPort() {
      if (!this._isSet(1)) {
         try {
            return this.getTemplateBean().getMulticast().getMulticastPort();
         } catch (NullPointerException var2) {
         }
      }

      return this._MulticastPort;
   }

   public boolean isMulticastPortInherited() {
      return false;
   }

   public boolean isMulticastPortSet() {
      return this._isSet(1);
   }

   public void setMulticastPort(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MulticastPort", (long)param0, 1L, 65535L);
      int _oldVal = this._MulticastPort;
      this._MulticastPort = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getMulticastTimeToLive() {
      if (!this._isSet(2)) {
         try {
            return this.getTemplateBean().getMulticast().getMulticastTimeToLive();
         } catch (NullPointerException var2) {
         }
      }

      return this._MulticastTimeToLive;
   }

   public boolean isMulticastTimeToLiveInherited() {
      return false;
   }

   public boolean isMulticastTimeToLiveSet() {
      return this._isSet(2);
   }

   public void setMulticastTimeToLive(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MulticastTimeToLive", (long)param0, 0L, 255L);
      int _oldVal = this._MulticastTimeToLive;
      this._MulticastTimeToLive = param0;
      this._postSet(2, _oldVal, param0);
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
      return this._isSet(3);
   }

   public void setTemplateBeanAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, TemplateBean.class, new ReferenceManager.Resolver(this, 3) {
            public void resolveReference(Object value) {
               try {
                  MulticastParamsBeanImpl.this.setTemplateBean((TemplateBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         TemplateBean _oldVal = this._TemplateBean;
         this._initializeProperty(3);
         this._postSet(3, _oldVal, this._TemplateBean);
      }

   }

   public void setTemplateBean(TemplateBean param0) throws InvalidAttributeValueException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 3, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return MulticastParamsBeanImpl.this.getTemplateBean();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      TemplateBean _oldVal = this._TemplateBean;
      this._TemplateBean = param0;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._MulticastAddress = "";
               if (initOne) {
                  break;
               }
            case 1:
               this._MulticastPort = 6001;
               if (initOne) {
                  break;
               }
            case 2:
               this._MulticastTimeToLive = 1;
               if (initOne) {
                  break;
               }
            case 3:
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
                  return 3;
               }
               break;
            case 14:
               if (s.equals("multicast-port")) {
                  return 1;
               }
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            default:
               break;
            case 17:
               if (s.equals("multicast-address")) {
                  return 0;
               }
               break;
            case 22:
               if (s.equals("multicast-time-to-live")) {
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
            case 0:
               return "multicast-address";
            case 1:
               return "multicast-port";
            case 2:
               return "multicast-time-to-live";
            case 3:
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private MulticastParamsBeanImpl bean;

      protected Helper(MulticastParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MulticastAddress";
            case 1:
               return "MulticastPort";
            case 2:
               return "MulticastTimeToLive";
            case 3:
               return "TemplateBean";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MulticastAddress")) {
            return 0;
         } else if (propName.equals("MulticastPort")) {
            return 1;
         } else if (propName.equals("MulticastTimeToLive")) {
            return 2;
         } else {
            return propName.equals("TemplateBean") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isMulticastAddressSet()) {
               buf.append("MulticastAddress");
               buf.append(String.valueOf(this.bean.getMulticastAddress()));
            }

            if (this.bean.isMulticastPortSet()) {
               buf.append("MulticastPort");
               buf.append(String.valueOf(this.bean.getMulticastPort()));
            }

            if (this.bean.isMulticastTimeToLiveSet()) {
               buf.append("MulticastTimeToLive");
               buf.append(String.valueOf(this.bean.getMulticastTimeToLive()));
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
            MulticastParamsBeanImpl otherTyped = (MulticastParamsBeanImpl)other;
            this.computeDiff("MulticastAddress", this.bean.getMulticastAddress(), otherTyped.getMulticastAddress(), false);
            this.computeDiff("MulticastPort", this.bean.getMulticastPort(), otherTyped.getMulticastPort(), false);
            this.computeDiff("MulticastTimeToLive", this.bean.getMulticastTimeToLive(), otherTyped.getMulticastTimeToLive(), false);
            this.computeDiff("TemplateBean", this.bean.getTemplateBean(), otherTyped.getTemplateBean(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MulticastParamsBeanImpl original = (MulticastParamsBeanImpl)event.getSourceBean();
            MulticastParamsBeanImpl proposed = (MulticastParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MulticastAddress")) {
                  original.setMulticastAddress(proposed.getMulticastAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MulticastPort")) {
                  original.setMulticastPort(proposed.getMulticastPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MulticastTimeToLive")) {
                  original.setMulticastTimeToLive(proposed.getMulticastTimeToLive());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TemplateBean")) {
                  original.setTemplateBeanAsString(proposed.getTemplateBeanAsString());
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
            MulticastParamsBeanImpl copy = (MulticastParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MulticastAddress")) && this.bean.isMulticastAddressSet()) {
               copy.setMulticastAddress(this.bean.getMulticastAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastPort")) && this.bean.isMulticastPortSet()) {
               copy.setMulticastPort(this.bean.getMulticastPort());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastTimeToLive")) && this.bean.isMulticastTimeToLiveSet()) {
               copy.setMulticastTimeToLive(this.bean.getMulticastTimeToLive());
            }

            if ((excludeProps == null || !excludeProps.contains("TemplateBean")) && this.bean.isTemplateBeanSet()) {
               copy._unSet(copy, 3);
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
