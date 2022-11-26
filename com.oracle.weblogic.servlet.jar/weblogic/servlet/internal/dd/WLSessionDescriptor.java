package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.SessionDescriptorMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class WLSessionDescriptor extends BaseServletDescriptor implements ToXML, SessionDescriptorMBean {
   private static final long serialVersionUID = 6160172527197013767L;
   private static final String SESSION_PARAM = "session-param";
   private List sessionParams;
   public static final String IDLEN = (new Integer(52)).toString();
   public static final String SESS_TIMEOUT = (new Integer(3600)).toString();
   public static final String INV_INTERVAL_SECS = (new Integer(60)).toString();

   public WLSessionDescriptor() {
      this.sessionParams = new ArrayList();
   }

   public WLSessionDescriptor(List sp) {
      this.sessionParams = sp;
   }

   public WLSessionDescriptor(SessionDescriptorMBean mbean) {
      this();
      if (mbean != null) {
         this.setURLRewritingEnabled(mbean.isURLRewritingEnabled());
         this.setIDLength(mbean.getIDLength());
         this.setCacheSize(mbean.getCacheSize());
         this.setCookieComment(mbean.getCookieComment());
         this.setCookieDomain(mbean.getCookieDomain());
         this.setCookieMaxAgeSecs(mbean.getCookieMaxAgeSecs());
         this.setCookieName(mbean.getCookieName());
         this.setEncodeSessionIdInQueryParams(mbean.isEncodeSessionIdInQueryParams());
         this.setCacheSessionCookie(mbean.isCacheSessionCookie());
         this.setCookiePath(mbean.getCookiePath());
         this.setInvalidationIntervalSecs(mbean.getInvalidationIntervalSecs());
         this.setJDBCConnectionTimeoutSecs(mbean.getJDBCConnectionTimeoutSecs());
         this.setPersistentStoreCookieName(mbean.getPersistentStoreCookieName());
         this.setPersistentStoreDir(mbean.getPersistentStoreDir());
         this.setPersistentStorePool(mbean.getPersistentStorePool());
         this.setPersistentStoreType(mbean.getPersistentStoreType());
         this.setPersistentStoreTable(mbean.getPersistentStoreTable());
         this.setPersistentDataSourceJNDIName(mbean.getPersistentDataSourceJNDIName());
         this.setJDBCColumnName_MaxInactiveInterval(mbean.getJDBCColumnName_MaxInactiveInterval());
         this.setCookiesEnabled(mbean.isCookiesEnabled());
         this.setCookieSecure(mbean.isCookieSecure());
         this.setTrackingEnabled(mbean.isTrackingEnabled());
         this.setPersistentStoreShared(mbean.isPersistentStoreShared());
         this.setSwapIntervalSecs(mbean.getSwapIntervalSecs());
         this.setTimeoutSecs(mbean.getTimeoutSecs());
         this.setConsoleMainAttribute(mbean.getConsoleMainAttribute());
         if (!this.checkMinIDLength(this.getIDLength())) {
            this.setIDLength(8);
         }
      }

   }

   public WLSessionDescriptor(Element parentElement) throws DOMProcessingException {
      List elts = DOMUtils.getOptionalElementsByTagName(parentElement, "session-param");
      Iterator i = elts.iterator();
      this.sessionParams = new ArrayList(elts.size());

      while(i.hasNext()) {
         ParameterDescriptor pd = new ParameterDescriptor((Element)i.next());
         String paramName = pd.getParamName();
         if (isValidParam(paramName)) {
            this.sessionParams.add(pd);
         } else {
            if (!isDeprecatedParam(paramName)) {
               if (paramName != null && paramName.length() == 0) {
                  HTTPLogger.logEmptySessionParamName();
               } else {
                  HTTPLogger.logInvalidSessionParamName(paramName);
               }

               throw new DOMProcessingException();
            }

            HTTPLogger.logDeprecatedSessionParamName(paramName);
         }
      }

      if (!this.checkMinIDLength(this.getIDLength())) {
         this.setIDLength(8);
      }

   }

   private static boolean isValidParam(String name) {
      if (name == null) {
         return false;
      } else {
         return name.equalsIgnoreCase("CacheSize") || name.equalsIgnoreCase("ConsoleMainAttribute") || name.equalsIgnoreCase("CookieComment") || name.equalsIgnoreCase("CookieDomain") || name.equalsIgnoreCase("CookieMaxAgeSecs") || name.equalsIgnoreCase("CookieName") || name.equalsIgnoreCase("EncodeSessionIdInQueryParams") || name.equalsIgnoreCase("CacheSessionCookie") || name.equalsIgnoreCase("CookiePath") || name.equalsIgnoreCase("CookiesEnabled") || name.equalsIgnoreCase("CookieSecure") || name.equalsIgnoreCase("IDLength") || name.equalsIgnoreCase("InvalidationIntervalSecs") || name.equalsIgnoreCase("JDBCConnectionTimeoutSecs") || name.equalsIgnoreCase("JDBCColumnName_MaxInactiveInterval") || name.equalsIgnoreCase("PersistentStoreCookieName") || name.equalsIgnoreCase("PersistentStoreDir") || name.equalsIgnoreCase("PersistentStorePool") || name.equalsIgnoreCase("PersistentDataSourceJNDIName") || name.equalsIgnoreCase("PersistentStoreTable") || name.equalsIgnoreCase("PersistentStoreType") || name.equalsIgnoreCase("SessionDebuggable") || name.equalsIgnoreCase("SwapIntervalSecs") || name.equalsIgnoreCase("TimeoutSecs") || name.equalsIgnoreCase("TrackingEnabled") || name.equalsIgnoreCase("URLRewritingEnabled");
      }
   }

   private static boolean isDefaultValue(String name, String value) {
      if (name == null) {
         return false;
      } else {
         return name.equalsIgnoreCase("CacheSize") && value.equals("SessionConstants.DEFAULT_CACHE_SIZE") || name.equalsIgnoreCase("CookieComment") && value.equals("WebLogic Server Session Tracking Cookie") || name.equalsIgnoreCase("CookieMaxAgeSecs") && value.equals("-1") || name.equalsIgnoreCase("CookieName") && value.equals("JSESSIONID") || name.equalsIgnoreCase("CookiePath") && value.equals("/") || name.equalsIgnoreCase("CookiesEnabled") && value.equals("true") || name.equalsIgnoreCase("IDLength") && value.equals(IDLEN) || name.equalsIgnoreCase("InvalidationIntervalSecs") && value.equals(INV_INTERVAL_SECS) || name.equalsIgnoreCase("JDBCConnectionTimeoutSecs") && value.equals("120") || name.equalsIgnoreCase("PersistentStoreCookieName") && value.equals("WLCOOKIE") || name.equalsIgnoreCase("PersistentStoreDir") && value.equals("session_db") || name.equalsIgnoreCase("PersistentStoreType") && value.equals("memory") || name.equalsIgnoreCase("TimeoutSecs") && value.equals(SESS_TIMEOUT) || name.equalsIgnoreCase("TrackingEnabled") && value.equals("true") || name.equalsIgnoreCase("URLRewritingEnabled") && value.equals("true");
      }
   }

   private static boolean isDeprecatedParam(String name) {
      return name.equalsIgnoreCase("PersistentStoreShared");
   }

   public boolean isURLRewritingEnabled() {
      return "true".equalsIgnoreCase(this.getProp("URLRewritingEnabled", "true"));
   }

   public void setURLRewritingEnabled(boolean b) {
      if (b != this.isURLRewritingEnabled()) {
         this.setProp("URLRewritingEnabled", "" + b);
         this.firePropertyChange("URLRewritingEnabled", new Boolean(!b), new Boolean(b));
      }

   }

   public String getCookiePath() {
      return this.getProp("CookiePath", "/");
   }

   public void setCookiePath(String s) {
      s = isNull(s);
      if (s != null) {
         if (s.charAt(0) != '/') {
            s = '/' + s;
         }

         String old = this.getCookiePath();
         this.setProp("CookiePath", s);
         if (!comp(old, s)) {
            this.firePropertyChange("CookiePath", old, s);
         }

      }
   }

   public int getIDLength() {
      return this.getIntProp("IDLength", 52);
   }

   public void setIDLength(int i) {
      int old = this.getIDLength();
      if (old != i) {
         this.setProp("IDLength", "" + i);
         this.firePropertyChange("IDLength", new Integer(old), new Integer(i));
      }

   }

   private boolean checkMinIDLength(int len) {
      if (len < 8) {
         HTTPLogger.logError("ERROR", "IDLength cannot be less than 8 as declared in deployment descriptor. Initializing with minimum value 8.");
         return false;
      } else {
         return true;
      }
   }

   public int getCacheSize() {
      return this.getIntProp("CacheSize", 1024);
   }

   public void setCacheSize(int i) {
      int old = this.getCacheSize();
      this.setProp("CacheSize", "" + i);
      if (old != i) {
         this.firePropertyChange("CacheSize", new Integer(old), new Integer(i));
      }

   }

   public String getCookieComment() {
      return this.getProp("CookieComment", "WebLogic Server Session Tracking Cookie");
   }

   public void setCookieComment(String s) {
      String old = this.getCookieComment();
      this.setProp("CookieComment", s);
      if (!comp(old, s)) {
         this.firePropertyChange("CookieComment", old, s);
      }

   }

   public String getCookieDomain() {
      return this.getProp("CookieDomain", (String)null);
   }

   public void setCookieDomain(String s) {
      s = isNull(s);
      if (s != null) {
         String old = this.getCookieDomain();
         if (!comp(old, s)) {
            this.setProp("CookieDomain", s);
            this.firePropertyChange("CookieDomain", old, s);
         }
      }

   }

   public int getCookieMaxAgeSecs() {
      return this.getIntProp("CookieMaxAgeSecs", -1);
   }

   public void setCookieMaxAgeSecs(int i) {
      int old = this.getCookieMaxAgeSecs();
      if (i != old) {
         this.setProp("CookieMaxAgeSecs", "" + i);
         this.firePropertyChange("CookieMaxAgeSecs", new Integer(old), new Integer(i));
      }

   }

   public String getCookieName() {
      return this.getProp("CookieName", "JSESSIONID", true);
   }

   public void setCookieName(String s) {
      s = isNull(s);
      if (s == null) {
         s = "JSESSIONID";
      }

      String old = this.getCookieName();
      if (!comp(old, s)) {
         this.setProp("CookieName", s);
         this.firePropertyChange("CookieName", old, s);
      }

   }

   public boolean isEncodeSessionIdInQueryParams() {
      return "true".equalsIgnoreCase(this.getProp("EncodeSessionIdInQueryParams", "false"));
   }

   public void setEncodeSessionIdInQueryParams(boolean b) {
      this.setProp("EncodeSessionIdInQueryParams", "" + b);
   }

   public void setCacheSessionCookie(boolean b) {
      this.setProp("CacheSessionCookie", "" + b);
   }

   public boolean isCacheSessionCookie() {
      return "true".equalsIgnoreCase(this.getProp("CacheSessionCookie", "true"));
   }

   public void setCookieSecure(boolean b) {
      if (b != this.isCookieSecure()) {
         this.setProp("CookieSecure", "" + b);
         this.firePropertyChange("CookieSecure", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isCookieSecure() {
      return "true".equalsIgnoreCase(this.getProp("CookieSecure", "false"));
   }

   public int getInvalidationIntervalSecs() {
      return this.getIntProp("InvalidationIntervalSecs", 60);
   }

   public void setInvalidationIntervalSecs(int i) {
      int old = this.getInvalidationIntervalSecs();
      if (old != i) {
         this.setProp("InvalidationIntervalSecs", "" + i);
         this.firePropertyChange("InvalidationIntervalSecs", new Integer(old), new Integer(i));
      }

   }

   public int getJDBCConnectionTimeoutSecs() {
      return this.getIntProp("JDBCConnectionTimeoutSecs", 120);
   }

   public void setJDBCConnectionTimeoutSecs(int i) {
      i = Math.max(i, 0);
      int old = this.getJDBCConnectionTimeoutSecs();
      if (i != old) {
         this.setProp("JDBCConnectionTimeoutSecs", "" + i);
         this.firePropertyChange("JDBCConnectionTimeoutSecs", new Integer(old), new Integer(i));
      }

   }

   public void setPersistentStoreCookieName(String n) {
      n = isNull(n);
      if (n != null) {
         String old = this.getPersistentStoreCookieName();
         this.setProp("PersistentStoreCookieName", n);
         if (!comp(old, n)) {
            this.firePropertyChange("PersistentStoreCookieName", old, n);
         }
      }

   }

   public String getPersistentStoreCookieName() {
      return this.getProp("PersistentStoreCookieName", "WLCOOKIE", true);
   }

   public String getPersistentStoreDir() {
      return this.getProp("PersistentStoreDir", "session_db", true);
   }

   public void setPersistentStoreDir(String s) {
      s = isNull(s);
      if (s != null) {
         String old = this.getPersistentStoreDir();
         this.setProp("PersistentStoreDir", s);
         if (!comp(old, s)) {
            this.firePropertyChange("PersistentStoreDir", old, s);
         }
      }

   }

   public String getPersistentStorePool() {
      return this.getProp("PersistentStorePool", (String)null);
   }

   public void setPersistentStorePool(String s) {
      s = isNull(s);
      if (s != null) {
         String old = this.getPersistentStorePool();
         this.setProp("PersistentStorePool", s);
         if (!comp(old, s)) {
            this.firePropertyChange("PersistentStorePool", old, s);
         }
      }

   }

   public String getPersistentDataSourceJNDIName() {
      return this.getProp("PersistentDataSourceJNDIName", (String)null);
   }

   public void setPersistentDataSourceJNDIName(String s) {
      s = isNull(s);
      if (s != null) {
         String old = this.getPersistentDataSourceJNDIName();
         this.setProp("PersistentDataSourceJNDIName", s);
         if (!comp(old, s)) {
            this.firePropertyChange("PersistentDataSourceJNDIName", old, s);
         }
      }

   }

   public String getPersistentStoreTable() {
      return this.getProp("PersistentStoreTable", "wl_servlet_sessions", true);
   }

   public void setPersistentStoreTable(String s) {
      s = isNull(s);
      if (s != null) {
         String old = this.getPersistentStoreTable();
         this.setProp("PersistentStoreTable", s);
         if (!comp(old, s)) {
            this.firePropertyChange("PersistentStoreTable", old, s);
         }
      }

   }

   public String getJDBCColumnName_MaxInactiveInterval() {
      return this.getProp("JDBCColumnName_MaxInactiveInterval", "wl_max_inactive_interval", true);
   }

   public void setJDBCColumnName_MaxInactiveInterval(String s) {
      s = isNull(s);
      if (s != null) {
         this.setProp("JDBCColumnName_MaxInactiveInterval", s);
      }

   }

   public boolean isPersistentStoreShared() {
      return "true".equalsIgnoreCase(this.getProp("PersistentStoreShared", "false"));
   }

   public void setPersistentStoreShared(boolean b) {
      if (b != this.isPersistentStoreShared()) {
         this.setProp("PersistentStoreShared", "" + b);
         this.firePropertyChange("PersistentStoreShared", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isDebugEnabled() {
      return "true".equalsIgnoreCase(this.getProp("SessionDebuggable", "false"));
   }

   public void setDebugEnabled(boolean b) {
      if (b != this.isDebugEnabled()) {
         this.setProp("SessionDebuggable", "" + b);
         this.firePropertyChange("SessionDebuggable", new Boolean(!b), new Boolean(b));
      }

   }

   private static boolean validStoreType(String s) {
      return "memory".equals(s) || "file".equals(s) || "replicated".equals(s) || "replicated_if_clustered".equals(s) || "cookie".equals(s) || "jdbc".equals(s);
   }

   public String getPersistentStoreType() {
      String s = this.getProp("PersistentStoreType", "memory");
      return !validStoreType(s) ? "memory" : s;
   }

   public void setPersistentStoreType(String s) {
      s = isNull(s);
      if (s != null && validStoreType(s)) {
         String old = this.getPersistentStoreType();
         this.setProp("PersistentStoreType", s);
         if (!comp(old, s)) {
            this.firePropertyChange("PersistentStoreType", old, s);
         }
      }

   }

   public int getSwapIntervalSecs() {
      return this.getIntProp("SwapIntervalSecs", 10);
   }

   public void setSwapIntervalSecs(int i) {
      int old = this.getSwapIntervalSecs();
      if (old != i) {
         this.setProp("SwapIntervalSecs", "" + i);
         this.firePropertyChange("SwapIntervalSecs", new Integer(old), new Integer(i));
      }

   }

   public int getTimeoutSecs() {
      return this.getIntProp("TimeoutSecs", 3600);
   }

   public void setTimeoutSecs(int i) {
      int old = this.getTimeoutSecs();
      if (i != old) {
         this.setProp("TimeoutSecs", "" + i);
         this.firePropertyChange("TimeoutSecs", new Integer(old), new Integer(i));
      }

   }

   public String getConsoleMainAttribute() {
      return this.getProp("ConsoleMainAttribute", (String)null);
   }

   public void setConsoleMainAttribute(String s) {
      s = isNull(s);
      if (s != null) {
         String old = this.getConsoleMainAttribute();
         if (!comp(old, s)) {
            this.setProp("ConsoleMainAttribute", s);
            this.firePropertyChange("ConsoleMainAttribute", old, s);
         }
      }

   }

   public void setCookiesEnabled(boolean b) {
      if (b != this.isCookiesEnabled()) {
         this.setProp("CookiesEnabled", "" + b);
         this.firePropertyChange("CookiesEnabled", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isCookiesEnabled() {
      return "true".equalsIgnoreCase(this.getProp("CookiesEnabled", "true"));
   }

   public void setTrackingEnabled(boolean b) {
      if (b != this.isTrackingEnabled()) {
         this.setProp("TrackingEnabled", "" + b);
         this.firePropertyChange("TrackingEnabled", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isTrackingEnabled() {
      return "true".equalsIgnoreCase(this.getProp("TrackingEnabled", "true"));
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   private String getProp(String name, String defalt) {
      return this.getProp(name, defalt, false);
   }

   private String getProp(String name, String defalt, boolean notEmpty) {
      Iterator i = this.sessionParams.iterator();

      ParameterDescriptor pd;
      do {
         if (!i.hasNext()) {
            if (defalt != null && defalt.length() > 0) {
               pd = new ParameterDescriptor();
               pd.setParamName(name);
               pd.setParamValue(defalt);
               this.sessionParams.add(pd);
            }

            return defalt;
         }

         pd = (ParameterDescriptor)i.next();
      } while(!name.equalsIgnoreCase(pd.getParamName()));

      pd.setParamName(name);
      if (!notEmpty) {
         return pd.getParamValue();
      } else {
         String ret = pd.getParamValue();
         ret = isNull(ret);
         if (ret != null) {
            return ret;
         } else {
            if (defalt != null) {
               pd.setParamValue(defalt);
            }

            return defalt;
         }
      }
   }

   private void setProp(String name, String val) {
      Iterator i = this.sessionParams.iterator();

      ParameterDescriptor pd;
      do {
         if (!i.hasNext()) {
            pd = new ParameterDescriptor();
            pd.setParamName(name);
            pd.setParamValue(val);
            this.sessionParams.add(pd);
            return;
         }

         pd = (ParameterDescriptor)i.next();
      } while(!name.equalsIgnoreCase(pd.getParamName()));

      pd.setParamName(name);
      pd.setParamValue(val);
   }

   private int getIntProp(String name, int defalt) {
      try {
         String s = this.getProp(name, "" + defalt);
         s = s.trim();
         return Integer.parseInt(s);
      } catch (NumberFormatException var4) {
         return defalt;
      }
   }

   private static String isNull(String s) {
      if (s == null) {
         return null;
      } else {
         s = s.trim();
         return s.length() > 0 ? s : null;
      }
   }

   public String toXML(int indent) {
      boolean found = false;
      String result = "";
      if (this.sessionParams != null && this.sessionParams.size() > 0) {
         Iterator i = this.sessionParams.iterator();

         while(i.hasNext()) {
            ParameterDescriptor p = (ParameterDescriptor)i.next();
            if (!isDefaultValue(p.getParamName(), p.getParamValue())) {
               if (!found) {
                  result = result + this.indentStr(indent) + "<session-descriptor>\n";
                  indent += 2;
                  found = true;
               }

               result = result + this.indentStr(indent) + "<session-param>\n";
               indent += 2;
               result = result + this.indentStr(indent) + "<param-name>" + p.getParamName() + "</param-name>\n";
               result = result + this.indentStr(indent) + "<param-value>" + p.getParamValue() + "</param-value>\n";
               indent -= 2;
               result = result + this.indentStr(indent) + "</session-param>\n";
            }
         }

         if (found) {
            indent -= 2;
            result = result + this.indentStr(indent) + "</session-descriptor>\n";
         }
      }

      return result;
   }
}
