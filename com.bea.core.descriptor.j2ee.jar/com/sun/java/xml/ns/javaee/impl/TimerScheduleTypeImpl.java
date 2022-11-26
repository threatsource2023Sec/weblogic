package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.TimerScheduleType;
import javax.xml.namespace.QName;

public class TimerScheduleTypeImpl extends XmlComplexContentImpl implements TimerScheduleType {
   private static final long serialVersionUID = 1L;
   private static final QName SECOND$0 = new QName("http://java.sun.com/xml/ns/javaee", "second");
   private static final QName MINUTE$2 = new QName("http://java.sun.com/xml/ns/javaee", "minute");
   private static final QName HOUR$4 = new QName("http://java.sun.com/xml/ns/javaee", "hour");
   private static final QName DAYOFMONTH$6 = new QName("http://java.sun.com/xml/ns/javaee", "day-of-month");
   private static final QName MONTH$8 = new QName("http://java.sun.com/xml/ns/javaee", "month");
   private static final QName DAYOFWEEK$10 = new QName("http://java.sun.com/xml/ns/javaee", "day-of-week");
   private static final QName YEAR$12 = new QName("http://java.sun.com/xml/ns/javaee", "year");
   private static final QName ID$14 = new QName("", "id");

   public TimerScheduleTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getSecond() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(SECOND$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecond() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECOND$0) != 0;
      }
   }

   public void setSecond(String second) {
      this.generatedSetterHelperImpl(second, SECOND$0, 0, (short)1);
   }

   public String addNewSecond() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(SECOND$0);
         return target;
      }
   }

   public void unsetSecond() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECOND$0, 0);
      }
   }

   public String getMinute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(MINUTE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMinute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINUTE$2) != 0;
      }
   }

   public void setMinute(String minute) {
      this.generatedSetterHelperImpl(minute, MINUTE$2, 0, (short)1);
   }

   public String addNewMinute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(MINUTE$2);
         return target;
      }
   }

   public void unsetMinute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINUTE$2, 0);
      }
   }

   public String getHour() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(HOUR$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHour() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HOUR$4) != 0;
      }
   }

   public void setHour(String hour) {
      this.generatedSetterHelperImpl(hour, HOUR$4, 0, (short)1);
   }

   public String addNewHour() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(HOUR$4);
         return target;
      }
   }

   public void unsetHour() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HOUR$4, 0);
      }
   }

   public String getDayOfMonth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DAYOFMONTH$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDayOfMonth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DAYOFMONTH$6) != 0;
      }
   }

   public void setDayOfMonth(String dayOfMonth) {
      this.generatedSetterHelperImpl(dayOfMonth, DAYOFMONTH$6, 0, (short)1);
   }

   public String addNewDayOfMonth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DAYOFMONTH$6);
         return target;
      }
   }

   public void unsetDayOfMonth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DAYOFMONTH$6, 0);
      }
   }

   public String getMonth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(MONTH$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMonth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MONTH$8) != 0;
      }
   }

   public void setMonth(String month) {
      this.generatedSetterHelperImpl(month, MONTH$8, 0, (short)1);
   }

   public String addNewMonth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(MONTH$8);
         return target;
      }
   }

   public void unsetMonth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MONTH$8, 0);
      }
   }

   public String getDayOfWeek() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DAYOFWEEK$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDayOfWeek() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DAYOFWEEK$10) != 0;
      }
   }

   public void setDayOfWeek(String dayOfWeek) {
      this.generatedSetterHelperImpl(dayOfWeek, DAYOFWEEK$10, 0, (short)1);
   }

   public String addNewDayOfWeek() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DAYOFWEEK$10);
         return target;
      }
   }

   public void unsetDayOfWeek() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DAYOFWEEK$10, 0);
      }
   }

   public String getYear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(YEAR$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetYear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(YEAR$12) != 0;
      }
   }

   public void setYear(String year) {
      this.generatedSetterHelperImpl(year, YEAR$12, 0, (short)1);
   }

   public String addNewYear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(YEAR$12);
         return target;
      }
   }

   public void unsetYear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(YEAR$12, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
