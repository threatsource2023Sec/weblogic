package com.oracle.xmlns.weblogic.weblogicApplicationClient.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.SessionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdPositiveIntegerType;
import javax.xml.namespace.QName;

public class SessionDescriptorTypeImpl extends XmlComplexContentImpl implements SessionDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName TIMEOUTSECS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "timeout-secs");
   private static final QName INVALIDATIONINTERVALSECS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "invalidation-interval-secs");
   private static final QName MAXSAVEPOSTSIZE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "max-save-post-size");
   private static final QName SAVEPOSTTIMEOUTSECS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "save-post-timeout-secs");
   private static final QName SAVEPOSTTIMEOUTINTERVALSECS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "save-post-timeout-interval-secs");
   private static final QName DEBUGENABLED$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "debug-enabled");
   private static final QName IDLENGTH$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "id-length");
   private static final QName AUTHCOOKIEIDLENGTH$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "auth-cookie-id-length");
   private static final QName TRACKINGENABLED$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "tracking-enabled");
   private static final QName CACHESIZE$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cache-size");
   private static final QName MAXINMEMORYSESSIONS$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "max-in-memory-sessions");
   private static final QName COOKIESENABLED$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cookies-enabled");
   private static final QName COOKIENAME$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cookie-name");
   private static final QName COOKIEPATH$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cookie-path");
   private static final QName COOKIEDOMAIN$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cookie-domain");
   private static final QName COOKIECOMMENT$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cookie-comment");
   private static final QName COOKIESECURE$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cookie-secure");
   private static final QName COOKIEMAXAGESECS$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cookie-max-age-secs");
   private static final QName COOKIEHTTPONLY$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "cookie-http-only");
   private static final QName PERSISTENTSTORETYPE$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "persistent-store-type");
   private static final QName PERSISTENTSTORECOOKIENAME$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "persistent-store-cookie-name");
   private static final QName PERSISTENTSTOREDIR$42 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "persistent-store-dir");
   private static final QName PERSISTENTSTOREPOOL$44 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "persistent-store-pool");
   private static final QName PERSISTENTDATASOURCEJNDINAME$46 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "persistent-data-source-jndi-name");
   private static final QName PERSISTENTSESSIONFLUSHINTERVAL$48 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "persistent-session-flush-interval");
   private static final QName PERSISTENTSESSIONFLUSHTHRESHOLD$50 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "persistent-session-flush-threshold");
   private static final QName PERSISTENTASYNCQUEUETIMEOUT$52 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "persistent-async-queue-timeout");
   private static final QName PERSISTENTSTORETABLE$54 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "persistent-store-table");
   private static final QName JDBCCOLUMNNAMEMAXINACTIVEINTERVAL$56 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "jdbc-column-name-max-inactive-interval");
   private static final QName URLREWRITINGENABLED$58 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "url-rewriting-enabled");
   private static final QName HTTPPROXYCACHINGOFCOOKIES$60 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "http-proxy-caching-of-cookies");
   private static final QName ENCODESESSIONIDINQUERYPARAMS$62 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "encode-session-id-in-query-params");
   private static final QName MONITORINGATTRIBUTENAME$64 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "monitoring-attribute-name");
   private static final QName SHARINGENABLED$66 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "sharing-enabled");
   private static final QName INVALIDATEONRELOGIN$68 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "invalidate-on-relogin");
   private static final QName ID$70 = new QName("", "id");

   public SessionDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdIntegerType getTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(TIMEOUTSECS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMEOUTSECS$0) != 0;
      }
   }

   public void setTimeoutSecs(XsdIntegerType timeoutSecs) {
      this.generatedSetterHelperImpl(timeoutSecs, TIMEOUTSECS$0, 0, (short)1);
   }

   public XsdIntegerType addNewTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(TIMEOUTSECS$0);
         return target;
      }
   }

   public void unsetTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMEOUTSECS$0, 0);
      }
   }

   public XsdIntegerType getInvalidationIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(INVALIDATIONINTERVALSECS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInvalidationIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INVALIDATIONINTERVALSECS$2) != 0;
      }
   }

   public void setInvalidationIntervalSecs(XsdIntegerType invalidationIntervalSecs) {
      this.generatedSetterHelperImpl(invalidationIntervalSecs, INVALIDATIONINTERVALSECS$2, 0, (short)1);
   }

   public XsdIntegerType addNewInvalidationIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(INVALIDATIONINTERVALSECS$2);
         return target;
      }
   }

   public void unsetInvalidationIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INVALIDATIONINTERVALSECS$2, 0);
      }
   }

   public XsdIntegerType getMaxSavePostSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXSAVEPOSTSIZE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxSavePostSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXSAVEPOSTSIZE$4) != 0;
      }
   }

   public void setMaxSavePostSize(XsdIntegerType maxSavePostSize) {
      this.generatedSetterHelperImpl(maxSavePostSize, MAXSAVEPOSTSIZE$4, 0, (short)1);
   }

   public XsdIntegerType addNewMaxSavePostSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXSAVEPOSTSIZE$4);
         return target;
      }
   }

   public void unsetMaxSavePostSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXSAVEPOSTSIZE$4, 0);
      }
   }

   public XsdIntegerType getSavePostTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(SAVEPOSTTIMEOUTSECS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSavePostTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAVEPOSTTIMEOUTSECS$6) != 0;
      }
   }

   public void setSavePostTimeoutSecs(XsdIntegerType savePostTimeoutSecs) {
      this.generatedSetterHelperImpl(savePostTimeoutSecs, SAVEPOSTTIMEOUTSECS$6, 0, (short)1);
   }

   public XsdIntegerType addNewSavePostTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(SAVEPOSTTIMEOUTSECS$6);
         return target;
      }
   }

   public void unsetSavePostTimeoutSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAVEPOSTTIMEOUTSECS$6, 0);
      }
   }

   public XsdIntegerType getSavePostTimeoutIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(SAVEPOSTTIMEOUTINTERVALSECS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSavePostTimeoutIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAVEPOSTTIMEOUTINTERVALSECS$8) != 0;
      }
   }

   public void setSavePostTimeoutIntervalSecs(XsdIntegerType savePostTimeoutIntervalSecs) {
      this.generatedSetterHelperImpl(savePostTimeoutIntervalSecs, SAVEPOSTTIMEOUTINTERVALSECS$8, 0, (short)1);
   }

   public XsdIntegerType addNewSavePostTimeoutIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(SAVEPOSTTIMEOUTINTERVALSECS$8);
         return target;
      }
   }

   public void unsetSavePostTimeoutIntervalSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAVEPOSTTIMEOUTINTERVALSECS$8, 0);
      }
   }

   public TrueFalseType getDebugEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(DEBUGENABLED$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDebugEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEBUGENABLED$10) != 0;
      }
   }

   public void setDebugEnabled(TrueFalseType debugEnabled) {
      this.generatedSetterHelperImpl(debugEnabled, DEBUGENABLED$10, 0, (short)1);
   }

   public TrueFalseType addNewDebugEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(DEBUGENABLED$10);
         return target;
      }
   }

   public void unsetDebugEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEBUGENABLED$10, 0);
      }
   }

   public XsdIntegerType getIdLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(IDLENGTH$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIdLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IDLENGTH$12) != 0;
      }
   }

   public void setIdLength(XsdIntegerType idLength) {
      this.generatedSetterHelperImpl(idLength, IDLENGTH$12, 0, (short)1);
   }

   public XsdIntegerType addNewIdLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(IDLENGTH$12);
         return target;
      }
   }

   public void unsetIdLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IDLENGTH$12, 0);
      }
   }

   public XsdIntegerType getAuthCookieIdLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(AUTHCOOKIEIDLENGTH$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAuthCookieIdLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTHCOOKIEIDLENGTH$14) != 0;
      }
   }

   public void setAuthCookieIdLength(XsdIntegerType authCookieIdLength) {
      this.generatedSetterHelperImpl(authCookieIdLength, AUTHCOOKIEIDLENGTH$14, 0, (short)1);
   }

   public XsdIntegerType addNewAuthCookieIdLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(AUTHCOOKIEIDLENGTH$14);
         return target;
      }
   }

   public void unsetAuthCookieIdLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTHCOOKIEIDLENGTH$14, 0);
      }
   }

   public TrueFalseType getTrackingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(TRACKINGENABLED$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTrackingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRACKINGENABLED$16) != 0;
      }
   }

   public void setTrackingEnabled(TrueFalseType trackingEnabled) {
      this.generatedSetterHelperImpl(trackingEnabled, TRACKINGENABLED$16, 0, (short)1);
   }

   public TrueFalseType addNewTrackingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(TRACKINGENABLED$16);
         return target;
      }
   }

   public void unsetTrackingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRACKINGENABLED$16, 0);
      }
   }

   public XsdIntegerType getCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(CACHESIZE$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHESIZE$18) != 0;
      }
   }

   public void setCacheSize(XsdIntegerType cacheSize) {
      this.generatedSetterHelperImpl(cacheSize, CACHESIZE$18, 0, (short)1);
   }

   public XsdIntegerType addNewCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(CACHESIZE$18);
         return target;
      }
   }

   public void unsetCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHESIZE$18, 0);
      }
   }

   public XsdIntegerType getMaxInMemorySessions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXINMEMORYSESSIONS$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxInMemorySessions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXINMEMORYSESSIONS$20) != 0;
      }
   }

   public void setMaxInMemorySessions(XsdIntegerType maxInMemorySessions) {
      this.generatedSetterHelperImpl(maxInMemorySessions, MAXINMEMORYSESSIONS$20, 0, (short)1);
   }

   public XsdIntegerType addNewMaxInMemorySessions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXINMEMORYSESSIONS$20);
         return target;
      }
   }

   public void unsetMaxInMemorySessions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXINMEMORYSESSIONS$20, 0);
      }
   }

   public TrueFalseType getCookiesEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(COOKIESENABLED$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCookiesEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COOKIESENABLED$22) != 0;
      }
   }

   public void setCookiesEnabled(TrueFalseType cookiesEnabled) {
      this.generatedSetterHelperImpl(cookiesEnabled, COOKIESENABLED$22, 0, (short)1);
   }

   public TrueFalseType addNewCookiesEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(COOKIESENABLED$22);
         return target;
      }
   }

   public void unsetCookiesEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COOKIESENABLED$22, 0);
      }
   }

   public String getCookieName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COOKIENAME$24, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCookieName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COOKIENAME$24, 0);
         return target;
      }
   }

   public boolean isSetCookieName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COOKIENAME$24) != 0;
      }
   }

   public void setCookieName(String cookieName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COOKIENAME$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COOKIENAME$24);
         }

         target.setStringValue(cookieName);
      }
   }

   public void xsetCookieName(XmlString cookieName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COOKIENAME$24, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COOKIENAME$24);
         }

         target.set(cookieName);
      }
   }

   public void unsetCookieName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COOKIENAME$24, 0);
      }
   }

   public String getCookiePath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COOKIEPATH$26, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCookiePath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COOKIEPATH$26, 0);
         return target;
      }
   }

   public boolean isSetCookiePath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COOKIEPATH$26) != 0;
      }
   }

   public void setCookiePath(String cookiePath) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COOKIEPATH$26, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COOKIEPATH$26);
         }

         target.setStringValue(cookiePath);
      }
   }

   public void xsetCookiePath(XmlString cookiePath) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COOKIEPATH$26, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COOKIEPATH$26);
         }

         target.set(cookiePath);
      }
   }

   public void unsetCookiePath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COOKIEPATH$26, 0);
      }
   }

   public String getCookieDomain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COOKIEDOMAIN$28, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCookieDomain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COOKIEDOMAIN$28, 0);
         return target;
      }
   }

   public boolean isSetCookieDomain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COOKIEDOMAIN$28) != 0;
      }
   }

   public void setCookieDomain(String cookieDomain) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COOKIEDOMAIN$28, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COOKIEDOMAIN$28);
         }

         target.setStringValue(cookieDomain);
      }
   }

   public void xsetCookieDomain(XmlString cookieDomain) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COOKIEDOMAIN$28, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COOKIEDOMAIN$28);
         }

         target.set(cookieDomain);
      }
   }

   public void unsetCookieDomain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COOKIEDOMAIN$28, 0);
      }
   }

   public String getCookieComment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COOKIECOMMENT$30, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCookieComment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COOKIECOMMENT$30, 0);
         return target;
      }
   }

   public boolean isSetCookieComment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COOKIECOMMENT$30) != 0;
      }
   }

   public void setCookieComment(String cookieComment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COOKIECOMMENT$30, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COOKIECOMMENT$30);
         }

         target.setStringValue(cookieComment);
      }
   }

   public void xsetCookieComment(XmlString cookieComment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COOKIECOMMENT$30, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COOKIECOMMENT$30);
         }

         target.set(cookieComment);
      }
   }

   public void unsetCookieComment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COOKIECOMMENT$30, 0);
      }
   }

   public TrueFalseType getCookieSecure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(COOKIESECURE$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCookieSecure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COOKIESECURE$32) != 0;
      }
   }

   public void setCookieSecure(TrueFalseType cookieSecure) {
      this.generatedSetterHelperImpl(cookieSecure, COOKIESECURE$32, 0, (short)1);
   }

   public TrueFalseType addNewCookieSecure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(COOKIESECURE$32);
         return target;
      }
   }

   public void unsetCookieSecure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COOKIESECURE$32, 0);
      }
   }

   public XsdIntegerType getCookieMaxAgeSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(COOKIEMAXAGESECS$34, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCookieMaxAgeSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COOKIEMAXAGESECS$34) != 0;
      }
   }

   public void setCookieMaxAgeSecs(XsdIntegerType cookieMaxAgeSecs) {
      this.generatedSetterHelperImpl(cookieMaxAgeSecs, COOKIEMAXAGESECS$34, 0, (short)1);
   }

   public XsdIntegerType addNewCookieMaxAgeSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(COOKIEMAXAGESECS$34);
         return target;
      }
   }

   public void unsetCookieMaxAgeSecs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COOKIEMAXAGESECS$34, 0);
      }
   }

   public TrueFalseType getCookieHttpOnly() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(COOKIEHTTPONLY$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCookieHttpOnly() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COOKIEHTTPONLY$36) != 0;
      }
   }

   public void setCookieHttpOnly(TrueFalseType cookieHttpOnly) {
      this.generatedSetterHelperImpl(cookieHttpOnly, COOKIEHTTPONLY$36, 0, (short)1);
   }

   public TrueFalseType addNewCookieHttpOnly() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(COOKIEHTTPONLY$36);
         return target;
      }
   }

   public void unsetCookieHttpOnly() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COOKIEHTTPONLY$36, 0);
      }
   }

   public String getPersistentStoreType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTORETYPE$38, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPersistentStoreType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTORETYPE$38, 0);
         return target;
      }
   }

   public boolean isSetPersistentStoreType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSTORETYPE$38) != 0;
      }
   }

   public void setPersistentStoreType(String persistentStoreType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTORETYPE$38, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PERSISTENTSTORETYPE$38);
         }

         target.setStringValue(persistentStoreType);
      }
   }

   public void xsetPersistentStoreType(XmlString persistentStoreType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTORETYPE$38, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PERSISTENTSTORETYPE$38);
         }

         target.set(persistentStoreType);
      }
   }

   public void unsetPersistentStoreType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSTORETYPE$38, 0);
      }
   }

   public String getPersistentStoreCookieName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTORECOOKIENAME$40, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPersistentStoreCookieName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTORECOOKIENAME$40, 0);
         return target;
      }
   }

   public boolean isSetPersistentStoreCookieName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSTORECOOKIENAME$40) != 0;
      }
   }

   public void setPersistentStoreCookieName(String persistentStoreCookieName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTORECOOKIENAME$40, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PERSISTENTSTORECOOKIENAME$40);
         }

         target.setStringValue(persistentStoreCookieName);
      }
   }

   public void xsetPersistentStoreCookieName(XmlString persistentStoreCookieName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTORECOOKIENAME$40, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PERSISTENTSTORECOOKIENAME$40);
         }

         target.set(persistentStoreCookieName);
      }
   }

   public void unsetPersistentStoreCookieName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSTORECOOKIENAME$40, 0);
      }
   }

   public String getPersistentStoreDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTOREDIR$42, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPersistentStoreDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTOREDIR$42, 0);
         return target;
      }
   }

   public boolean isSetPersistentStoreDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSTOREDIR$42) != 0;
      }
   }

   public void setPersistentStoreDir(String persistentStoreDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTOREDIR$42, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PERSISTENTSTOREDIR$42);
         }

         target.setStringValue(persistentStoreDir);
      }
   }

   public void xsetPersistentStoreDir(XmlString persistentStoreDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTOREDIR$42, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PERSISTENTSTOREDIR$42);
         }

         target.set(persistentStoreDir);
      }
   }

   public void unsetPersistentStoreDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSTOREDIR$42, 0);
      }
   }

   public String getPersistentStorePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTOREPOOL$44, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPersistentStorePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTOREPOOL$44, 0);
         return target;
      }
   }

   public boolean isSetPersistentStorePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSTOREPOOL$44) != 0;
      }
   }

   public void setPersistentStorePool(String persistentStorePool) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTOREPOOL$44, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PERSISTENTSTOREPOOL$44);
         }

         target.setStringValue(persistentStorePool);
      }
   }

   public void xsetPersistentStorePool(XmlString persistentStorePool) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTOREPOOL$44, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PERSISTENTSTOREPOOL$44);
         }

         target.set(persistentStorePool);
      }
   }

   public void unsetPersistentStorePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSTOREPOOL$44, 0);
      }
   }

   public String getPersistentDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTDATASOURCEJNDINAME$46, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPersistentDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTDATASOURCEJNDINAME$46, 0);
         return target;
      }
   }

   public boolean isSetPersistentDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTDATASOURCEJNDINAME$46) != 0;
      }
   }

   public void setPersistentDataSourceJndiName(String persistentDataSourceJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTDATASOURCEJNDINAME$46, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PERSISTENTDATASOURCEJNDINAME$46);
         }

         target.setStringValue(persistentDataSourceJndiName);
      }
   }

   public void xsetPersistentDataSourceJndiName(XmlString persistentDataSourceJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTDATASOURCEJNDINAME$46, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PERSISTENTDATASOURCEJNDINAME$46);
         }

         target.set(persistentDataSourceJndiName);
      }
   }

   public void unsetPersistentDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTDATASOURCEJNDINAME$46, 0);
      }
   }

   public XsdIntegerType getPersistentSessionFlushInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(PERSISTENTSESSIONFLUSHINTERVAL$48, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistentSessionFlushInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSESSIONFLUSHINTERVAL$48) != 0;
      }
   }

   public void setPersistentSessionFlushInterval(XsdIntegerType persistentSessionFlushInterval) {
      this.generatedSetterHelperImpl(persistentSessionFlushInterval, PERSISTENTSESSIONFLUSHINTERVAL$48, 0, (short)1);
   }

   public XsdIntegerType addNewPersistentSessionFlushInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(PERSISTENTSESSIONFLUSHINTERVAL$48);
         return target;
      }
   }

   public void unsetPersistentSessionFlushInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSESSIONFLUSHINTERVAL$48, 0);
      }
   }

   public XsdPositiveIntegerType getPersistentSessionFlushThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(PERSISTENTSESSIONFLUSHTHRESHOLD$50, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistentSessionFlushThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSESSIONFLUSHTHRESHOLD$50) != 0;
      }
   }

   public void setPersistentSessionFlushThreshold(XsdPositiveIntegerType persistentSessionFlushThreshold) {
      this.generatedSetterHelperImpl(persistentSessionFlushThreshold, PERSISTENTSESSIONFLUSHTHRESHOLD$50, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewPersistentSessionFlushThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(PERSISTENTSESSIONFLUSHTHRESHOLD$50);
         return target;
      }
   }

   public void unsetPersistentSessionFlushThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSESSIONFLUSHTHRESHOLD$50, 0);
      }
   }

   public XsdPositiveIntegerType getPersistentAsyncQueueTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(PERSISTENTASYNCQUEUETIMEOUT$52, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistentAsyncQueueTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTASYNCQUEUETIMEOUT$52) != 0;
      }
   }

   public void setPersistentAsyncQueueTimeout(XsdPositiveIntegerType persistentAsyncQueueTimeout) {
      this.generatedSetterHelperImpl(persistentAsyncQueueTimeout, PERSISTENTASYNCQUEUETIMEOUT$52, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewPersistentAsyncQueueTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(PERSISTENTASYNCQUEUETIMEOUT$52);
         return target;
      }
   }

   public void unsetPersistentAsyncQueueTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTASYNCQUEUETIMEOUT$52, 0);
      }
   }

   public String getPersistentStoreTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTORETABLE$54, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPersistentStoreTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTORETABLE$54, 0);
         return target;
      }
   }

   public boolean isSetPersistentStoreTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSTORETABLE$54) != 0;
      }
   }

   public void setPersistentStoreTable(String persistentStoreTable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTSTORETABLE$54, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PERSISTENTSTORETABLE$54);
         }

         target.setStringValue(persistentStoreTable);
      }
   }

   public void xsetPersistentStoreTable(XmlString persistentStoreTable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PERSISTENTSTORETABLE$54, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PERSISTENTSTORETABLE$54);
         }

         target.set(persistentStoreTable);
      }
   }

   public void unsetPersistentStoreTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSTORETABLE$54, 0);
      }
   }

   public String getJdbcColumnNameMaxInactiveInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JDBCCOLUMNNAMEMAXINACTIVEINTERVAL$56, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJdbcColumnNameMaxInactiveInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JDBCCOLUMNNAMEMAXINACTIVEINTERVAL$56, 0);
         return target;
      }
   }

   public boolean isSetJdbcColumnNameMaxInactiveInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JDBCCOLUMNNAMEMAXINACTIVEINTERVAL$56) != 0;
      }
   }

   public void setJdbcColumnNameMaxInactiveInterval(String jdbcColumnNameMaxInactiveInterval) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JDBCCOLUMNNAMEMAXINACTIVEINTERVAL$56, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JDBCCOLUMNNAMEMAXINACTIVEINTERVAL$56);
         }

         target.setStringValue(jdbcColumnNameMaxInactiveInterval);
      }
   }

   public void xsetJdbcColumnNameMaxInactiveInterval(XmlString jdbcColumnNameMaxInactiveInterval) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JDBCCOLUMNNAMEMAXINACTIVEINTERVAL$56, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JDBCCOLUMNNAMEMAXINACTIVEINTERVAL$56);
         }

         target.set(jdbcColumnNameMaxInactiveInterval);
      }
   }

   public void unsetJdbcColumnNameMaxInactiveInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JDBCCOLUMNNAMEMAXINACTIVEINTERVAL$56, 0);
      }
   }

   public TrueFalseType getUrlRewritingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(URLREWRITINGENABLED$58, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUrlRewritingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(URLREWRITINGENABLED$58) != 0;
      }
   }

   public void setUrlRewritingEnabled(TrueFalseType urlRewritingEnabled) {
      this.generatedSetterHelperImpl(urlRewritingEnabled, URLREWRITINGENABLED$58, 0, (short)1);
   }

   public TrueFalseType addNewUrlRewritingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(URLREWRITINGENABLED$58);
         return target;
      }
   }

   public void unsetUrlRewritingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(URLREWRITINGENABLED$58, 0);
      }
   }

   public TrueFalseType getHttpProxyCachingOfCookies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(HTTPPROXYCACHINGOFCOOKIES$60, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHttpProxyCachingOfCookies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HTTPPROXYCACHINGOFCOOKIES$60) != 0;
      }
   }

   public void setHttpProxyCachingOfCookies(TrueFalseType httpProxyCachingOfCookies) {
      this.generatedSetterHelperImpl(httpProxyCachingOfCookies, HTTPPROXYCACHINGOFCOOKIES$60, 0, (short)1);
   }

   public TrueFalseType addNewHttpProxyCachingOfCookies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(HTTPPROXYCACHINGOFCOOKIES$60);
         return target;
      }
   }

   public void unsetHttpProxyCachingOfCookies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HTTPPROXYCACHINGOFCOOKIES$60, 0);
      }
   }

   public TrueFalseType getEncodeSessionIdInQueryParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENCODESESSIONIDINQUERYPARAMS$62, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEncodeSessionIdInQueryParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENCODESESSIONIDINQUERYPARAMS$62) != 0;
      }
   }

   public void setEncodeSessionIdInQueryParams(TrueFalseType encodeSessionIdInQueryParams) {
      this.generatedSetterHelperImpl(encodeSessionIdInQueryParams, ENCODESESSIONIDINQUERYPARAMS$62, 0, (short)1);
   }

   public TrueFalseType addNewEncodeSessionIdInQueryParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENCODESESSIONIDINQUERYPARAMS$62);
         return target;
      }
   }

   public void unsetEncodeSessionIdInQueryParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENCODESESSIONIDINQUERYPARAMS$62, 0);
      }
   }

   public String getMonitoringAttributeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MONITORINGATTRIBUTENAME$64, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMonitoringAttributeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MONITORINGATTRIBUTENAME$64, 0);
         return target;
      }
   }

   public boolean isSetMonitoringAttributeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MONITORINGATTRIBUTENAME$64) != 0;
      }
   }

   public void setMonitoringAttributeName(String monitoringAttributeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MONITORINGATTRIBUTENAME$64, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MONITORINGATTRIBUTENAME$64);
         }

         target.setStringValue(monitoringAttributeName);
      }
   }

   public void xsetMonitoringAttributeName(XmlString monitoringAttributeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MONITORINGATTRIBUTENAME$64, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MONITORINGATTRIBUTENAME$64);
         }

         target.set(monitoringAttributeName);
      }
   }

   public void unsetMonitoringAttributeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MONITORINGATTRIBUTENAME$64, 0);
      }
   }

   public TrueFalseType getSharingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SHARINGENABLED$66, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSharingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHARINGENABLED$66) != 0;
      }
   }

   public void setSharingEnabled(TrueFalseType sharingEnabled) {
      this.generatedSetterHelperImpl(sharingEnabled, SHARINGENABLED$66, 0, (short)1);
   }

   public TrueFalseType addNewSharingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SHARINGENABLED$66);
         return target;
      }
   }

   public void unsetSharingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHARINGENABLED$66, 0);
      }
   }

   public TrueFalseType getInvalidateOnRelogin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(INVALIDATEONRELOGIN$68, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInvalidateOnRelogin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INVALIDATEONRELOGIN$68) != 0;
      }
   }

   public void setInvalidateOnRelogin(TrueFalseType invalidateOnRelogin) {
      this.generatedSetterHelperImpl(invalidateOnRelogin, INVALIDATEONRELOGIN$68, 0, (short)1);
   }

   public TrueFalseType addNewInvalidateOnRelogin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(INVALIDATEONRELOGIN$68);
         return target;
      }
   }

   public void unsetInvalidateOnRelogin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INVALIDATEONRELOGIN$68, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$70);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$70);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$70) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$70);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$70);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$70);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$70);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$70);
      }
   }
}
