package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.CookieConfigType;
import org.jcp.xmlns.xml.ns.javaee.SessionConfigType;
import org.jcp.xmlns.xml.ns.javaee.TrackingModeType;
import org.jcp.xmlns.xml.ns.javaee.XsdIntegerType;

public class SessionConfigTypeImpl extends XmlComplexContentImpl implements SessionConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName SESSIONTIMEOUT$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "session-timeout");
   private static final QName COOKIECONFIG$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "cookie-config");
   private static final QName TRACKINGMODE$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "tracking-mode");
   private static final QName ID$6 = new QName("", "id");

   public SessionConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdIntegerType getSessionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(SESSIONTIMEOUT$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSessionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SESSIONTIMEOUT$0) != 0;
      }
   }

   public void setSessionTimeout(XsdIntegerType sessionTimeout) {
      this.generatedSetterHelperImpl(sessionTimeout, SESSIONTIMEOUT$0, 0, (short)1);
   }

   public XsdIntegerType addNewSessionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(SESSIONTIMEOUT$0);
         return target;
      }
   }

   public void unsetSessionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSIONTIMEOUT$0, 0);
      }
   }

   public CookieConfigType getCookieConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookieConfigType target = null;
         target = (CookieConfigType)this.get_store().find_element_user(COOKIECONFIG$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCookieConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COOKIECONFIG$2) != 0;
      }
   }

   public void setCookieConfig(CookieConfigType cookieConfig) {
      this.generatedSetterHelperImpl(cookieConfig, COOKIECONFIG$2, 0, (short)1);
   }

   public CookieConfigType addNewCookieConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookieConfigType target = null;
         target = (CookieConfigType)this.get_store().add_element_user(COOKIECONFIG$2);
         return target;
      }
   }

   public void unsetCookieConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COOKIECONFIG$2, 0);
      }
   }

   public TrackingModeType[] getTrackingModeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TRACKINGMODE$4, targetList);
         TrackingModeType[] result = new TrackingModeType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TrackingModeType getTrackingModeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrackingModeType target = null;
         target = (TrackingModeType)this.get_store().find_element_user(TRACKINGMODE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTrackingModeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRACKINGMODE$4);
      }
   }

   public void setTrackingModeArray(TrackingModeType[] trackingModeArray) {
      this.check_orphaned();
      this.arraySetterHelper(trackingModeArray, TRACKINGMODE$4);
   }

   public void setTrackingModeArray(int i, TrackingModeType trackingMode) {
      this.generatedSetterHelperImpl(trackingMode, TRACKINGMODE$4, i, (short)2);
   }

   public TrackingModeType insertNewTrackingMode(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrackingModeType target = null;
         target = (TrackingModeType)this.get_store().insert_element_user(TRACKINGMODE$4, i);
         return target;
      }
   }

   public TrackingModeType addNewTrackingMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrackingModeType target = null;
         target = (TrackingModeType)this.get_store().add_element_user(TRACKINGMODE$4);
         return target;
      }
   }

   public void removeTrackingMode(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRACKINGMODE$4, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
