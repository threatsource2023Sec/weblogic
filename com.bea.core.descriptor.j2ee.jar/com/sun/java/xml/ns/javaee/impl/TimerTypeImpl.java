package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlDateTime;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.NamedMethodType;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.TimerScheduleType;
import com.sun.java.xml.ns.javaee.TimerType;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.namespace.QName;

public class TimerTypeImpl extends XmlComplexContentImpl implements TimerType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/javaee", "description");
   private static final QName SCHEDULE$2 = new QName("http://java.sun.com/xml/ns/javaee", "schedule");
   private static final QName START$4 = new QName("http://java.sun.com/xml/ns/javaee", "start");
   private static final QName END$6 = new QName("http://java.sun.com/xml/ns/javaee", "end");
   private static final QName TIMEOUTMETHOD$8 = new QName("http://java.sun.com/xml/ns/javaee", "timeout-method");
   private static final QName PERSISTENT$10 = new QName("http://java.sun.com/xml/ns/javaee", "persistent");
   private static final QName TIMEZONE$12 = new QName("http://java.sun.com/xml/ns/javaee", "timezone");
   private static final QName INFO$14 = new QName("http://java.sun.com/xml/ns/javaee", "info");
   private static final QName ID$16 = new QName("", "id");

   public TimerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public TimerScheduleType getSchedule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerScheduleType target = null;
         target = (TimerScheduleType)this.get_store().find_element_user(SCHEDULE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setSchedule(TimerScheduleType schedule) {
      this.generatedSetterHelperImpl(schedule, SCHEDULE$2, 0, (short)1);
   }

   public TimerScheduleType addNewSchedule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerScheduleType target = null;
         target = (TimerScheduleType)this.get_store().add_element_user(SCHEDULE$2);
         return target;
      }
   }

   public Calendar getStart() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(START$4, 0);
         return target == null ? null : target.getCalendarValue();
      }
   }

   public XmlDateTime xgetStart() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlDateTime target = null;
         target = (XmlDateTime)this.get_store().find_element_user(START$4, 0);
         return target;
      }
   }

   public boolean isSetStart() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(START$4) != 0;
      }
   }

   public void setStart(Calendar start) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(START$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(START$4);
         }

         target.setCalendarValue(start);
      }
   }

   public void xsetStart(XmlDateTime start) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlDateTime target = null;
         target = (XmlDateTime)this.get_store().find_element_user(START$4, 0);
         if (target == null) {
            target = (XmlDateTime)this.get_store().add_element_user(START$4);
         }

         target.set(start);
      }
   }

   public void unsetStart() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(START$4, 0);
      }
   }

   public Calendar getEnd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(END$6, 0);
         return target == null ? null : target.getCalendarValue();
      }
   }

   public XmlDateTime xgetEnd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlDateTime target = null;
         target = (XmlDateTime)this.get_store().find_element_user(END$6, 0);
         return target;
      }
   }

   public boolean isSetEnd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(END$6) != 0;
      }
   }

   public void setEnd(Calendar end) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(END$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(END$6);
         }

         target.setCalendarValue(end);
      }
   }

   public void xsetEnd(XmlDateTime end) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlDateTime target = null;
         target = (XmlDateTime)this.get_store().find_element_user(END$6, 0);
         if (target == null) {
            target = (XmlDateTime)this.get_store().add_element_user(END$6);
         }

         target.set(end);
      }
   }

   public void unsetEnd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(END$6, 0);
      }
   }

   public NamedMethodType getTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(TIMEOUTMETHOD$8, 0);
         return target == null ? null : target;
      }
   }

   public void setTimeoutMethod(NamedMethodType timeoutMethod) {
      this.generatedSetterHelperImpl(timeoutMethod, TIMEOUTMETHOD$8, 0, (short)1);
   }

   public NamedMethodType addNewTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(TIMEOUTMETHOD$8);
         return target;
      }
   }

   public TrueFalseType getPersistent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(PERSISTENT$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENT$10) != 0;
      }
   }

   public void setPersistent(TrueFalseType persistent) {
      this.generatedSetterHelperImpl(persistent, PERSISTENT$10, 0, (short)1);
   }

   public TrueFalseType addNewPersistent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(PERSISTENT$10);
         return target;
      }
   }

   public void unsetPersistent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENT$10, 0);
      }
   }

   public String getTimezone() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(TIMEZONE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimezone() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMEZONE$12) != 0;
      }
   }

   public void setTimezone(String timezone) {
      this.generatedSetterHelperImpl(timezone, TIMEZONE$12, 0, (short)1);
   }

   public String addNewTimezone() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(TIMEZONE$12);
         return target;
      }
   }

   public void unsetTimezone() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMEZONE$12, 0);
      }
   }

   public String getInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(INFO$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INFO$14) != 0;
      }
   }

   public void setInfo(String info) {
      this.generatedSetterHelperImpl(info, INFO$14, 0, (short)1);
   }

   public String addNewInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(INFO$14);
         return target;
      }
   }

   public void unsetInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INFO$14, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$16) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$16);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$16);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$16);
      }
   }
}
