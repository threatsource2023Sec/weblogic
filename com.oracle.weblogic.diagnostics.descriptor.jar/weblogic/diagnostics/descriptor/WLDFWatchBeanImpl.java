package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.diagnostics.descriptor.validation.WatchNotificationValidators;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WLDFWatchBeanImpl extends WLDFBeanImpl implements WLDFWatchBean, Serializable {
   private int _AlarmResetPeriod;
   private String _AlarmType;
   private boolean _Enabled;
   private String _ExpressionLanguage;
   private WLDFNotificationBean[] _Notifications;
   private String _RuleExpression;
   private String _RuleType;
   private WLDFScheduleBean _Schedule;
   private String _Severity;
   private transient WLDFWatchCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public WLDFWatchBeanImpl() {
      try {
         this._customizer = new WLDFWatchCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WLDFWatchBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new WLDFWatchCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WLDFWatchBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new WLDFWatchCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(2);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getRuleType() {
      return this._RuleType;
   }

   public boolean isRuleTypeInherited() {
      return false;
   }

   public boolean isRuleTypeSet() {
      return this._isSet(3);
   }

   public void setRuleType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Harvester", "Log", "DomainLog", "EventData"};
      param0 = LegalChecks.checkInEnum("RuleType", param0, _set);
      String _oldVal = this._RuleType;
      this._RuleType = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getRuleExpression() {
      return this._RuleExpression;
   }

   public boolean isRuleExpressionInherited() {
      return false;
   }

   public boolean isRuleExpressionSet() {
      return this._isSet(4);
   }

   public void setRuleExpression(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RuleExpression;
      this._RuleExpression = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getExpressionLanguage() {
      if (!this._isSet(5)) {
         try {
            return WatchNotificationValidators.getExpressionLanguageDefault(this);
         } catch (NullPointerException var2) {
         }
      }

      return this._ExpressionLanguage;
   }

   public boolean isExpressionLanguageInherited() {
      return false;
   }

   public boolean isExpressionLanguageSet() {
      return this._isSet(5);
   }

   public void setExpressionLanguage(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WatchNotificationValidators.validateExpressionLanguage(this, param0);
      String _oldVal = this._ExpressionLanguage;
      this._ExpressionLanguage = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getSeverity() {
      return this._customizer.getSeverity();
   }

   public boolean isSeverityInherited() {
      return false;
   }

   public boolean isSeveritySet() {
      return this._isSet(6);
   }

   public void setSeverity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency"};
      param0 = LegalChecks.checkInEnum("Severity", param0, _set);
      String _oldVal = this.getSeverity();
      this._customizer.setSeverity(param0);
      this._postSet(6, _oldVal, param0);
   }

   public String getAlarmType() {
      return this._AlarmType;
   }

   public boolean isAlarmTypeInherited() {
      return false;
   }

   public boolean isAlarmTypeSet() {
      return this._isSet(7);
   }

   public void setAlarmType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"None", "ManualReset", "AutomaticReset"};
      param0 = LegalChecks.checkInEnum("AlarmType", param0, _set);
      String _oldVal = this._AlarmType;
      this._AlarmType = param0;
      this._postSet(7, _oldVal, param0);
   }

   public WLDFScheduleBean getSchedule() {
      return this._Schedule;
   }

   public boolean isScheduleInherited() {
      return false;
   }

   public boolean isScheduleSet() {
      return this._isSet(8) || this._isAnythingSet((AbstractDescriptorBean)this.getSchedule());
   }

   public void setSchedule(WLDFScheduleBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 8)) {
         this._postCreate(_child);
      }

      WLDFScheduleBean _oldVal = this._Schedule;
      this._Schedule = param0;
      this._postSet(8, _oldVal, param0);
   }

   public int getAlarmResetPeriod() {
      return this._AlarmResetPeriod;
   }

   public boolean isAlarmResetPeriodInherited() {
      return false;
   }

   public boolean isAlarmResetPeriodSet() {
      return this._isSet(9);
   }

   public void setAlarmResetPeriod(int param0) {
      LegalChecks.checkMin("AlarmResetPeriod", param0, 1000);
      int _oldVal = this._AlarmResetPeriod;
      this._AlarmResetPeriod = param0;
      this._postSet(9, _oldVal, param0);
   }

   public WLDFNotificationBean[] getNotifications() {
      return this._Notifications;
   }

   public String getNotificationsAsString() {
      return this._getHelper()._serializeKeyList(this.getNotifications());
   }

   public boolean isNotificationsInherited() {
      return false;
   }

   public boolean isNotificationsSet() {
      return this._isSet(10);
   }

   public void setNotificationsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Notifications);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, WLDFNotificationBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        WLDFWatchBeanImpl.this.addNotification((WLDFNotificationBean)value);
                        WLDFWatchBeanImpl.this._getHelper().reorderArrayObjects((Object[])WLDFWatchBeanImpl.this._Notifications, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               WLDFNotificationBean[] var6 = this._Notifications;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  WLDFNotificationBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeNotification(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         WLDFNotificationBean[] _oldVal = this._Notifications;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Notifications);
      }
   }

   public void setNotifications(WLDFNotificationBean[] param0) {
      WLDFNotificationBean[] param0 = param0 == null ? new WLDFNotificationBeanImpl[0] : param0;
      param0 = (WLDFNotificationBean[])((WLDFNotificationBean[])this._getHelper()._cleanAndValidateArray(param0, WLDFNotificationBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return WLDFWatchBeanImpl.this.getNotifications();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      WLDFNotificationBean[] _oldVal = this._Notifications;
      this._Notifications = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean addNotification(WLDFNotificationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         WLDFNotificationBean[] _new;
         if (this._isSet(10)) {
            _new = (WLDFNotificationBean[])((WLDFNotificationBean[])this._getHelper()._extendArray(this.getNotifications(), WLDFNotificationBean.class, param0));
         } else {
            _new = new WLDFNotificationBean[]{param0};
         }

         try {
            this.setNotifications(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeNotification(WLDFNotificationBean param0) {
      WLDFNotificationBean[] _old = this.getNotifications();
      WLDFNotificationBean[] _new = (WLDFNotificationBean[])((WLDFNotificationBean[])this._getHelper()._removeElement(_old, WLDFNotificationBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setNotifications(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WatchNotificationValidators.validateWatch(this);
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
      return super._isAnythingSet() || this.isScheduleSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 9;
      }

      try {
         switch (idx) {
            case 9:
               this._AlarmResetPeriod = 60000;
               if (initOne) {
                  break;
               }
            case 7:
               this._AlarmType = "None";
               if (initOne) {
                  break;
               }
            case 5:
               this._ExpressionLanguage = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Notifications = new WLDFNotificationBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._RuleExpression = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._RuleType = "Harvester";
               if (initOne) {
                  break;
               }
            case 8:
               this._Schedule = new WLDFScheduleBeanImpl(this, 8);
               this._postCreate((AbstractDescriptorBean)this._Schedule);
               if (initOne) {
                  break;
               }
            case 6:
               this._customizer.setSeverity("Notice");
               if (initOne) {
                  break;
               }
            case 2:
               this._Enabled = true;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/2.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("enabled")) {
                  return 2;
               }
               break;
            case 8:
               if (s.equals("schedule")) {
                  return 8;
               }

               if (s.equals("severity")) {
                  return 6;
               }
               break;
            case 9:
               if (s.equals("rule-type")) {
                  return 3;
               }
               break;
            case 10:
               if (s.equals("alarm-type")) {
                  return 7;
               }
            case 11:
            case 13:
            case 14:
            case 16:
            case 17:
            default:
               break;
            case 12:
               if (s.equals("notification")) {
                  return 10;
               }
               break;
            case 15:
               if (s.equals("rule-expression")) {
                  return 4;
               }
               break;
            case 18:
               if (s.equals("alarm-reset-period")) {
                  return 9;
               }
               break;
            case 19:
               if (s.equals("expression-language")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 8:
               return new WLDFScheduleBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "enabled";
            case 3:
               return "rule-type";
            case 4:
               return "rule-expression";
            case 5:
               return "expression-language";
            case 6:
               return "severity";
            case 7:
               return "alarm-type";
            case 8:
               return "schedule";
            case 9:
               return "alarm-reset-period";
            case 10:
               return "notification";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 8:
               return true;
            default:
               return super.isBean(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends WLDFBeanImpl.Helper {
      private WLDFWatchBeanImpl bean;

      protected Helper(WLDFWatchBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Enabled";
            case 3:
               return "RuleType";
            case 4:
               return "RuleExpression";
            case 5:
               return "ExpressionLanguage";
            case 6:
               return "Severity";
            case 7:
               return "AlarmType";
            case 8:
               return "Schedule";
            case 9:
               return "AlarmResetPeriod";
            case 10:
               return "Notifications";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AlarmResetPeriod")) {
            return 9;
         } else if (propName.equals("AlarmType")) {
            return 7;
         } else if (propName.equals("ExpressionLanguage")) {
            return 5;
         } else if (propName.equals("Notifications")) {
            return 10;
         } else if (propName.equals("RuleExpression")) {
            return 4;
         } else if (propName.equals("RuleType")) {
            return 3;
         } else if (propName.equals("Schedule")) {
            return 8;
         } else if (propName.equals("Severity")) {
            return 6;
         } else {
            return propName.equals("Enabled") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getSchedule() != null) {
            iterators.add(new ArrayIterator(new WLDFScheduleBean[]{this.bean.getSchedule()}));
         }

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
            if (this.bean.isAlarmResetPeriodSet()) {
               buf.append("AlarmResetPeriod");
               buf.append(String.valueOf(this.bean.getAlarmResetPeriod()));
            }

            if (this.bean.isAlarmTypeSet()) {
               buf.append("AlarmType");
               buf.append(String.valueOf(this.bean.getAlarmType()));
            }

            if (this.bean.isExpressionLanguageSet()) {
               buf.append("ExpressionLanguage");
               buf.append(String.valueOf(this.bean.getExpressionLanguage()));
            }

            if (this.bean.isNotificationsSet()) {
               buf.append("Notifications");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getNotifications())));
            }

            if (this.bean.isRuleExpressionSet()) {
               buf.append("RuleExpression");
               buf.append(String.valueOf(this.bean.getRuleExpression()));
            }

            if (this.bean.isRuleTypeSet()) {
               buf.append("RuleType");
               buf.append(String.valueOf(this.bean.getRuleType()));
            }

            childValue = this.computeChildHashValue(this.bean.getSchedule());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSeveritySet()) {
               buf.append("Severity");
               buf.append(String.valueOf(this.bean.getSeverity()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            WLDFWatchBeanImpl otherTyped = (WLDFWatchBeanImpl)other;
            this.computeDiff("AlarmResetPeriod", this.bean.getAlarmResetPeriod(), otherTyped.getAlarmResetPeriod(), true);
            this.computeDiff("AlarmType", this.bean.getAlarmType(), otherTyped.getAlarmType(), true);
            this.computeDiff("ExpressionLanguage", this.bean.getExpressionLanguage(), otherTyped.getExpressionLanguage(), true);
            this.computeDiff("Notifications", this.bean.getNotifications(), otherTyped.getNotifications(), true);
            this.computeDiff("RuleExpression", this.bean.getRuleExpression(), otherTyped.getRuleExpression(), true);
            this.computeDiff("RuleType", this.bean.getRuleType(), otherTyped.getRuleType(), true);
            this.computeSubDiff("Schedule", this.bean.getSchedule(), otherTyped.getSchedule());
            this.computeDiff("Severity", this.bean.getSeverity(), otherTyped.getSeverity(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFWatchBeanImpl original = (WLDFWatchBeanImpl)event.getSourceBean();
            WLDFWatchBeanImpl proposed = (WLDFWatchBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AlarmResetPeriod")) {
                  original.setAlarmResetPeriod(proposed.getAlarmResetPeriod());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("AlarmType")) {
                  original.setAlarmType(proposed.getAlarmType());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("ExpressionLanguage")) {
                  original.setExpressionLanguage(proposed.getExpressionLanguage());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Notifications")) {
                  original.setNotificationsAsString(proposed.getNotificationsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("RuleExpression")) {
                  original.setRuleExpression(proposed.getRuleExpression());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("RuleType")) {
                  original.setRuleType(proposed.getRuleType());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Schedule")) {
                  if (type == 2) {
                     original.setSchedule((WLDFScheduleBean)this.createCopy((AbstractDescriptorBean)proposed.getSchedule()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Schedule", (DescriptorBean)original.getSchedule());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("Severity")) {
                  original.setSeverity(proposed.getSeverity());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
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
            WLDFWatchBeanImpl copy = (WLDFWatchBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AlarmResetPeriod")) && this.bean.isAlarmResetPeriodSet()) {
               copy.setAlarmResetPeriod(this.bean.getAlarmResetPeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("AlarmType")) && this.bean.isAlarmTypeSet()) {
               copy.setAlarmType(this.bean.getAlarmType());
            }

            if ((excludeProps == null || !excludeProps.contains("ExpressionLanguage")) && this.bean.isExpressionLanguageSet()) {
               copy.setExpressionLanguage(this.bean.getExpressionLanguage());
            }

            if ((excludeProps == null || !excludeProps.contains("Notifications")) && this.bean.isNotificationsSet()) {
               copy._unSet(copy, 10);
               copy.setNotificationsAsString(this.bean.getNotificationsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("RuleExpression")) && this.bean.isRuleExpressionSet()) {
               copy.setRuleExpression(this.bean.getRuleExpression());
            }

            if ((excludeProps == null || !excludeProps.contains("RuleType")) && this.bean.isRuleTypeSet()) {
               copy.setRuleType(this.bean.getRuleType());
            }

            if ((excludeProps == null || !excludeProps.contains("Schedule")) && this.bean.isScheduleSet() && !copy._isSet(8)) {
               Object o = this.bean.getSchedule();
               copy.setSchedule((WLDFScheduleBean)null);
               copy.setSchedule(o == null ? null : (WLDFScheduleBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Severity")) && this.bean.isSeveritySet()) {
               copy.setSeverity(this.bean.getSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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
         this.inferSubTree(this.bean.getNotifications(), clazz, annotation);
         this.inferSubTree(this.bean.getSchedule(), clazz, annotation);
      }
   }
}
