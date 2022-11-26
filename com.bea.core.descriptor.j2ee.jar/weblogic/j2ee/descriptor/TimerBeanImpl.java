package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class TimerBeanImpl extends AbstractDescriptorBean implements TimerBean, Serializable {
   private String[] _Descriptions;
   private Date _End;
   private String _Id;
   private String _Info;
   private boolean _Persistent;
   private TimerScheduleBean _Schedule;
   private Date _Start;
   private NamedMethodBean _TimeoutMethod;
   private String _Timezone;
   private static SchemaHelper2 _schemaHelper;

   public TimerBeanImpl() {
      this._initializeProperty(-1);
   }

   public TimerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TimerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(0);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(0, _oldVal, param0);
   }

   public TimerScheduleBean getSchedule() {
      return this._Schedule;
   }

   public boolean isScheduleInherited() {
      return false;
   }

   public boolean isScheduleSet() {
      return this._isSet(1);
   }

   public void setSchedule(TimerScheduleBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSchedule() != null && param0 != this.getSchedule()) {
         throw new BeanAlreadyExistsException(this.getSchedule() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TimerScheduleBean _oldVal = this._Schedule;
         this._Schedule = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public TimerScheduleBean createSchedule() {
      TimerScheduleBeanImpl _val = new TimerScheduleBeanImpl(this, -1);

      try {
         this.setSchedule(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySchedule(TimerScheduleBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Schedule;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSchedule((TimerScheduleBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public Date getStart() {
      return this._Start;
   }

   public boolean isStartInherited() {
      return false;
   }

   public boolean isStartSet() {
      return this._isSet(2);
   }

   public void setStart(Date param0) {
      Date _oldVal = this._Start;
      this._Start = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Date getEnd() {
      return this._End;
   }

   public boolean isEndInherited() {
      return false;
   }

   public boolean isEndSet() {
      return this._isSet(3);
   }

   public void setEnd(Date param0) {
      Date _oldVal = this._End;
      this._End = param0;
      this._postSet(3, _oldVal, param0);
   }

   public NamedMethodBean getTimeoutMethod() {
      return this._TimeoutMethod;
   }

   public boolean isTimeoutMethodInherited() {
      return false;
   }

   public boolean isTimeoutMethodSet() {
      return this._isSet(4);
   }

   public void setTimeoutMethod(NamedMethodBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTimeoutMethod() != null && param0 != this.getTimeoutMethod()) {
         throw new BeanAlreadyExistsException(this.getTimeoutMethod() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NamedMethodBean _oldVal = this._TimeoutMethod;
         this._TimeoutMethod = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public NamedMethodBean createTimeoutMethod() {
      NamedMethodBeanImpl _val = new NamedMethodBeanImpl(this, -1);

      try {
         this.setTimeoutMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTimeoutMethod(NamedMethodBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TimeoutMethod;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTimeoutMethod((NamedMethodBean)null);
               this._unSet(4);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean getPersistent() {
      return this._Persistent;
   }

   public boolean isPersistentInherited() {
      return false;
   }

   public boolean isPersistentSet() {
      return this._isSet(5);
   }

   public void setPersistent(boolean param0) {
      boolean _oldVal = this._Persistent;
      this._Persistent = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getTimezone() {
      return this._Timezone;
   }

   public boolean isTimezoneInherited() {
      return false;
   }

   public boolean isTimezoneSet() {
      return this._isSet(6);
   }

   public void setTimezone(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Timezone;
      this._Timezone = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getInfo() {
      return this._Info;
   }

   public boolean isInfoInherited() {
      return false;
   }

   public boolean isInfoSet() {
      return this._isSet(7);
   }

   public void setInfo(String param0) {
      String _oldVal = this._Info;
      this._Info = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(8);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(8, _oldVal, param0);
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
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._End = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Info = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Persistent = false;
               if (initOne) {
                  break;
               }
            case 1:
               this._Schedule = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Start = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._TimeoutMethod = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Timezone = null;
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
                  return 8;
               }
               break;
            case 3:
               if (s.equals("end")) {
                  return 3;
               }
               break;
            case 4:
               if (s.equals("info")) {
                  return 7;
               }
               break;
            case 5:
               if (s.equals("start")) {
                  return 2;
               }
            case 6:
            case 7:
            case 9:
            case 12:
            case 13:
            default:
               break;
            case 8:
               if (s.equals("schedule")) {
                  return 1;
               }

               if (s.equals("timezone")) {
                  return 6;
               }
               break;
            case 10:
               if (s.equals("persistent")) {
                  return 5;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("timeout-method")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new TimerScheduleBeanImpl.SchemaHelper2();
            case 4:
               return new NamedMethodBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "schedule";
            case 2:
               return "start";
            case 3:
               return "end";
            case 4:
               return "timeout-method";
            case 5:
               return "persistent";
            case 6:
               return "timezone";
            case 7:
               return "info";
            case 8:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 4:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private TimerBeanImpl bean;

      protected Helper(TimerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "Schedule";
            case 2:
               return "Start";
            case 3:
               return "End";
            case 4:
               return "TimeoutMethod";
            case 5:
               return "Persistent";
            case 6:
               return "Timezone";
            case 7:
               return "Info";
            case 8:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("End")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 8;
         } else if (propName.equals("Info")) {
            return 7;
         } else if (propName.equals("Persistent")) {
            return 5;
         } else if (propName.equals("Schedule")) {
            return 1;
         } else if (propName.equals("Start")) {
            return 2;
         } else if (propName.equals("TimeoutMethod")) {
            return 4;
         } else {
            return propName.equals("Timezone") ? 6 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getSchedule() != null) {
            iterators.add(new ArrayIterator(new TimerScheduleBean[]{this.bean.getSchedule()}));
         }

         if (this.bean.getTimeoutMethod() != null) {
            iterators.add(new ArrayIterator(new NamedMethodBean[]{this.bean.getTimeoutMethod()}));
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
            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isEndSet()) {
               buf.append("End");
               buf.append(String.valueOf(this.bean.getEnd()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInfoSet()) {
               buf.append("Info");
               buf.append(String.valueOf(this.bean.getInfo()));
            }

            if (this.bean.isPersistentSet()) {
               buf.append("Persistent");
               buf.append(String.valueOf(this.bean.getPersistent()));
            }

            childValue = this.computeChildHashValue(this.bean.getSchedule());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isStartSet()) {
               buf.append("Start");
               buf.append(String.valueOf(this.bean.getStart()));
            }

            childValue = this.computeChildHashValue(this.bean.getTimeoutMethod());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTimezoneSet()) {
               buf.append("Timezone");
               buf.append(String.valueOf(this.bean.getTimezone()));
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
            TimerBeanImpl otherTyped = (TimerBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("End", this.bean.getEnd(), otherTyped.getEnd(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Info", this.bean.getInfo(), otherTyped.getInfo(), false);
            this.computeDiff("Persistent", this.bean.getPersistent(), otherTyped.getPersistent(), false);
            this.computeChildDiff("Schedule", this.bean.getSchedule(), otherTyped.getSchedule(), false);
            this.computeDiff("Start", this.bean.getStart(), otherTyped.getStart(), false);
            this.computeChildDiff("TimeoutMethod", this.bean.getTimeoutMethod(), otherTyped.getTimeoutMethod(), false);
            this.computeDiff("Timezone", this.bean.getTimezone(), otherTyped.getTimezone(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TimerBeanImpl original = (TimerBeanImpl)event.getSourceBean();
            TimerBeanImpl proposed = (TimerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("End")) {
                  original.setEnd(proposed.getEnd() == null ? null : (Date)proposed.getEnd().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("Info")) {
                  original.setInfo(proposed.getInfo());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Persistent")) {
                  original.setPersistent(proposed.getPersistent());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Schedule")) {
                  if (type == 2) {
                     original.setSchedule((TimerScheduleBean)this.createCopy((AbstractDescriptorBean)proposed.getSchedule()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Schedule", (DescriptorBean)original.getSchedule());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Start")) {
                  original.setStart(proposed.getStart() == null ? null : (Date)proposed.getStart().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TimeoutMethod")) {
                  if (type == 2) {
                     original.setTimeoutMethod((NamedMethodBean)this.createCopy((AbstractDescriptorBean)proposed.getTimeoutMethod()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TimeoutMethod", (DescriptorBean)original.getTimeoutMethod());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Timezone")) {
                  original.setTimezone(proposed.getTimezone());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            TimerBeanImpl copy = (TimerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               Object o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("End")) && this.bean.isEndSet()) {
               copy.setEnd(this.bean.getEnd());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Info")) && this.bean.isInfoSet()) {
               copy.setInfo(this.bean.getInfo());
            }

            if ((excludeProps == null || !excludeProps.contains("Persistent")) && this.bean.isPersistentSet()) {
               copy.setPersistent(this.bean.getPersistent());
            }

            if ((excludeProps == null || !excludeProps.contains("Schedule")) && this.bean.isScheduleSet() && !copy._isSet(1)) {
               Object o = this.bean.getSchedule();
               copy.setSchedule((TimerScheduleBean)null);
               copy.setSchedule(o == null ? null : (TimerScheduleBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Start")) && this.bean.isStartSet()) {
               copy.setStart(this.bean.getStart());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeoutMethod")) && this.bean.isTimeoutMethodSet() && !copy._isSet(4)) {
               Object o = this.bean.getTimeoutMethod();
               copy.setTimeoutMethod((NamedMethodBean)null);
               copy.setTimeoutMethod(o == null ? null : (NamedMethodBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Timezone")) && this.bean.isTimezoneSet()) {
               copy.setTimezone(this.bean.getTimezone());
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
         this.inferSubTree(this.bean.getSchedule(), clazz, annotation);
         this.inferSubTree(this.bean.getTimeoutMethod(), clazz, annotation);
      }
   }
}
