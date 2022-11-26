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
import weblogic.j2ee.descriptor.wl.customizers.ThresholdParamsBeanCustomizer;
import weblogic.utils.collections.CombinedIterator;

public class ThresholdParamsBeanImpl extends SettableBeanImpl implements ThresholdParamsBean, Serializable {
   private long _BytesHigh;
   private long _BytesLow;
   private long _MessagesHigh;
   private long _MessagesLow;
   private TemplateBean _TemplateBean;
   private transient ThresholdParamsBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ThresholdParamsBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.ThresholdParamsBeanCustomizerFactory");
         this._customizer = (ThresholdParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ThresholdParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.ThresholdParamsBeanCustomizerFactory");
         this._customizer = (ThresholdParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ThresholdParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.ThresholdParamsBeanCustomizerFactory");
         this._customizer = (ThresholdParamsBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public long getBytesHigh() {
      if (!this._isSet(0)) {
         try {
            return this.getTemplateBean().getThresholds().getBytesHigh();
         } catch (NullPointerException var2) {
         }
      }

      return this._BytesHigh;
   }

   public boolean isBytesHighInherited() {
      return false;
   }

   public boolean isBytesHighSet() {
      return this._isSet(0);
   }

   public void setBytesHigh(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("BytesHigh", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._BytesHigh;
      this._BytesHigh = param0;
      this._postSet(0, _oldVal, param0);
   }

   public long getBytesLow() {
      if (!this._isSet(1)) {
         try {
            return this.getTemplateBean().getThresholds().getBytesLow();
         } catch (NullPointerException var2) {
         }
      }

      return this._BytesLow;
   }

   public boolean isBytesLowInherited() {
      return false;
   }

   public boolean isBytesLowSet() {
      return this._isSet(1);
   }

   public void setBytesLow(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("BytesLow", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._BytesLow;
      this._BytesLow = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getMessagesHigh() {
      if (!this._isSet(2)) {
         try {
            return this.getTemplateBean().getThresholds().getMessagesHigh();
         } catch (NullPointerException var2) {
         }
      }

      return this._MessagesHigh;
   }

   public boolean isMessagesHighInherited() {
      return false;
   }

   public boolean isMessagesHighSet() {
      return this._isSet(2);
   }

   public void setMessagesHigh(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MessagesHigh", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._MessagesHigh;
      this._MessagesHigh = param0;
      this._postSet(2, _oldVal, param0);
   }

   public long getMessagesLow() {
      if (!this._isSet(3)) {
         try {
            return this.getTemplateBean().getThresholds().getMessagesLow();
         } catch (NullPointerException var2) {
         }
      }

      return this._MessagesLow;
   }

   public boolean isMessagesLowInherited() {
      return false;
   }

   public boolean isMessagesLowSet() {
      return this._isSet(3);
   }

   public void setMessagesLow(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MessagesLow", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._MessagesLow;
      this._MessagesLow = param0;
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
                  ThresholdParamsBeanImpl.this.setTemplateBean((TemplateBean)value);
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
               return ThresholdParamsBeanImpl.this.getTemplateBean();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      TemplateBean _oldVal = this._TemplateBean;
      this._TemplateBean = param0;
      this._postSet(4, _oldVal, param0);
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
               this._BytesHigh = Long.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 1:
               this._BytesLow = Long.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 2:
               this._MessagesHigh = Long.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 3:
               this._MessagesLow = Long.MAX_VALUE;
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
            case 9:
               if (s.equals("bytes-low")) {
                  return 1;
               }
               break;
            case 10:
               if (s.equals("bytes-high")) {
                  return 0;
               }
            case 11:
            default:
               break;
            case 12:
               if (s.equals("messages-low")) {
                  return 3;
               }
               break;
            case 13:
               if (s.equals("messages-high")) {
                  return 2;
               }

               if (s.equals("template-bean")) {
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
               return "bytes-high";
            case 1:
               return "bytes-low";
            case 2:
               return "messages-high";
            case 3:
               return "messages-low";
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
      private ThresholdParamsBeanImpl bean;

      protected Helper(ThresholdParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "BytesHigh";
            case 1:
               return "BytesLow";
            case 2:
               return "MessagesHigh";
            case 3:
               return "MessagesLow";
            case 4:
               return "TemplateBean";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BytesHigh")) {
            return 0;
         } else if (propName.equals("BytesLow")) {
            return 1;
         } else if (propName.equals("MessagesHigh")) {
            return 2;
         } else if (propName.equals("MessagesLow")) {
            return 3;
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
            if (this.bean.isBytesHighSet()) {
               buf.append("BytesHigh");
               buf.append(String.valueOf(this.bean.getBytesHigh()));
            }

            if (this.bean.isBytesLowSet()) {
               buf.append("BytesLow");
               buf.append(String.valueOf(this.bean.getBytesLow()));
            }

            if (this.bean.isMessagesHighSet()) {
               buf.append("MessagesHigh");
               buf.append(String.valueOf(this.bean.getMessagesHigh()));
            }

            if (this.bean.isMessagesLowSet()) {
               buf.append("MessagesLow");
               buf.append(String.valueOf(this.bean.getMessagesLow()));
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
            ThresholdParamsBeanImpl otherTyped = (ThresholdParamsBeanImpl)other;
            this.computeDiff("BytesHigh", this.bean.getBytesHigh(), otherTyped.getBytesHigh(), true);
            this.computeDiff("BytesLow", this.bean.getBytesLow(), otherTyped.getBytesLow(), true);
            this.computeDiff("MessagesHigh", this.bean.getMessagesHigh(), otherTyped.getMessagesHigh(), true);
            this.computeDiff("MessagesLow", this.bean.getMessagesLow(), otherTyped.getMessagesLow(), true);
            this.computeDiff("TemplateBean", this.bean.getTemplateBean(), otherTyped.getTemplateBean(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ThresholdParamsBeanImpl original = (ThresholdParamsBeanImpl)event.getSourceBean();
            ThresholdParamsBeanImpl proposed = (ThresholdParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BytesHigh")) {
                  original.setBytesHigh(proposed.getBytesHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("BytesLow")) {
                  original.setBytesLow(proposed.getBytesLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MessagesHigh")) {
                  original.setMessagesHigh(proposed.getMessagesHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MessagesLow")) {
                  original.setMessagesLow(proposed.getMessagesLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            ThresholdParamsBeanImpl copy = (ThresholdParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BytesHigh")) && this.bean.isBytesHighSet()) {
               copy.setBytesHigh(this.bean.getBytesHigh());
            }

            if ((excludeProps == null || !excludeProps.contains("BytesLow")) && this.bean.isBytesLowSet()) {
               copy.setBytesLow(this.bean.getBytesLow());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesHigh")) && this.bean.isMessagesHighSet()) {
               copy.setMessagesHigh(this.bean.getMessagesHigh());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesLow")) && this.bean.isMessagesLowSet()) {
               copy.setMessagesLow(this.bean.getMessagesLow());
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
         this.inferSubTree(this.bean.getTemplateBean(), clazz, annotation);
      }
   }
}
