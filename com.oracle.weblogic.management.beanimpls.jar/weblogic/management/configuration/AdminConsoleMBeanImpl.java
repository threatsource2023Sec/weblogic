package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class AdminConsoleMBeanImpl extends ConfigurationMBeanImpl implements AdminConsoleMBean, Serializable {
   private String _CookieName;
   private int _MinThreads;
   private boolean _ProtectedCookieEnabled;
   private String _SSOLogoutURL;
   private int _SessionTimeout;
   private static SchemaHelper2 _schemaHelper;

   public AdminConsoleMBeanImpl() {
      this._initializeProperty(-1);
   }

   public AdminConsoleMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AdminConsoleMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getCookieName() {
      return this._CookieName;
   }

   public boolean isCookieNameInherited() {
      return false;
   }

   public boolean isCookieNameSet() {
      return this._isSet(10);
   }

   public void setCookieName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CookieName;
      this._CookieName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isProtectedCookieEnabled() {
      return this._ProtectedCookieEnabled;
   }

   public boolean isProtectedCookieEnabledInherited() {
      return false;
   }

   public boolean isProtectedCookieEnabledSet() {
      return this._isSet(11);
   }

   public void setProtectedCookieEnabled(boolean param0) {
      boolean _oldVal = this._ProtectedCookieEnabled;
      this._ProtectedCookieEnabled = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getSessionTimeout() {
      return this._SessionTimeout;
   }

   public boolean isSessionTimeoutInherited() {
      return false;
   }

   public boolean isSessionTimeoutSet() {
      return this._isSet(12);
   }

   public void setSessionTimeout(int param0) {
      int _oldVal = this._SessionTimeout;
      this._SessionTimeout = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getSSOLogoutURL() {
      return this._SSOLogoutURL;
   }

   public boolean isSSOLogoutURLInherited() {
      return false;
   }

   public boolean isSSOLogoutURLSet() {
      return this._isSet(13);
   }

   public void setSSOLogoutURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SSOLogoutURL;
      this._SSOLogoutURL = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getMinThreads() {
      return this._MinThreads;
   }

   public boolean isMinThreadsInherited() {
      return false;
   }

   public boolean isMinThreadsSet() {
      return this._isSet(14);
   }

   public void setMinThreads(int param0) {
      LegalChecks.checkMin("MinThreads", param0, 5);
      int _oldVal = this._MinThreads;
      this._MinThreads = param0;
      this._postSet(14, _oldVal, param0);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._CookieName = "ADMINCONSOLESESSION";
               if (initOne) {
                  break;
               }
            case 14:
               this._MinThreads = 101;
               if (initOne) {
                  break;
               }
            case 13:
               this._SSOLogoutURL = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._SessionTimeout = 3600;
               if (initOne) {
                  break;
               }
            case 11:
               this._ProtectedCookieEnabled = true;
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "AdminConsole";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("CookieName")) {
         oldVal = this._CookieName;
         this._CookieName = (String)v;
         this._postSet(10, oldVal, this._CookieName);
      } else {
         int oldVal;
         if (name.equals("MinThreads")) {
            oldVal = this._MinThreads;
            this._MinThreads = (Integer)v;
            this._postSet(14, oldVal, this._MinThreads);
         } else if (name.equals("ProtectedCookieEnabled")) {
            boolean oldVal = this._ProtectedCookieEnabled;
            this._ProtectedCookieEnabled = (Boolean)v;
            this._postSet(11, oldVal, this._ProtectedCookieEnabled);
         } else if (name.equals("SSOLogoutURL")) {
            oldVal = this._SSOLogoutURL;
            this._SSOLogoutURL = (String)v;
            this._postSet(13, oldVal, this._SSOLogoutURL);
         } else if (name.equals("SessionTimeout")) {
            oldVal = this._SessionTimeout;
            this._SessionTimeout = (Integer)v;
            this._postSet(12, oldVal, this._SessionTimeout);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CookieName")) {
         return this._CookieName;
      } else if (name.equals("MinThreads")) {
         return new Integer(this._MinThreads);
      } else if (name.equals("ProtectedCookieEnabled")) {
         return new Boolean(this._ProtectedCookieEnabled);
      } else if (name.equals("SSOLogoutURL")) {
         return this._SSOLogoutURL;
      } else {
         return name.equals("SessionTimeout") ? new Integer(this._SessionTimeout) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("cookie-name")) {
                  return 10;
               }

               if (s.equals("min-threads")) {
                  return 14;
               }
               break;
            case 14:
               if (s.equals("sso-logout-url")) {
                  return 13;
               }
               break;
            case 15:
               if (s.equals("session-timeout")) {
                  return 12;
               }
               break;
            case 24:
               if (s.equals("protected-cookie-enabled")) {
                  return 11;
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
            case 10:
               return "cookie-name";
            case 11:
               return "protected-cookie-enabled";
            case 12:
               return "session-timeout";
            case 13:
               return "sso-logout-url";
            case 14:
               return "min-threads";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private AdminConsoleMBeanImpl bean;

      protected Helper(AdminConsoleMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "CookieName";
            case 11:
               return "ProtectedCookieEnabled";
            case 12:
               return "SessionTimeout";
            case 13:
               return "SSOLogoutURL";
            case 14:
               return "MinThreads";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CookieName")) {
            return 10;
         } else if (propName.equals("MinThreads")) {
            return 14;
         } else if (propName.equals("SSOLogoutURL")) {
            return 13;
         } else if (propName.equals("SessionTimeout")) {
            return 12;
         } else {
            return propName.equals("ProtectedCookieEnabled") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isCookieNameSet()) {
               buf.append("CookieName");
               buf.append(String.valueOf(this.bean.getCookieName()));
            }

            if (this.bean.isMinThreadsSet()) {
               buf.append("MinThreads");
               buf.append(String.valueOf(this.bean.getMinThreads()));
            }

            if (this.bean.isSSOLogoutURLSet()) {
               buf.append("SSOLogoutURL");
               buf.append(String.valueOf(this.bean.getSSOLogoutURL()));
            }

            if (this.bean.isSessionTimeoutSet()) {
               buf.append("SessionTimeout");
               buf.append(String.valueOf(this.bean.getSessionTimeout()));
            }

            if (this.bean.isProtectedCookieEnabledSet()) {
               buf.append("ProtectedCookieEnabled");
               buf.append(String.valueOf(this.bean.isProtectedCookieEnabled()));
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
            AdminConsoleMBeanImpl otherTyped = (AdminConsoleMBeanImpl)other;
            this.computeDiff("CookieName", this.bean.getCookieName(), otherTyped.getCookieName(), false);
            this.computeDiff("MinThreads", this.bean.getMinThreads(), otherTyped.getMinThreads(), true);
            this.computeDiff("SSOLogoutURL", this.bean.getSSOLogoutURL(), otherTyped.getSSOLogoutURL(), true);
            this.computeDiff("SessionTimeout", this.bean.getSessionTimeout(), otherTyped.getSessionTimeout(), false);
            this.computeDiff("ProtectedCookieEnabled", this.bean.isProtectedCookieEnabled(), otherTyped.isProtectedCookieEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AdminConsoleMBeanImpl original = (AdminConsoleMBeanImpl)event.getSourceBean();
            AdminConsoleMBeanImpl proposed = (AdminConsoleMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CookieName")) {
                  original.setCookieName(proposed.getCookieName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MinThreads")) {
                  original.setMinThreads(proposed.getMinThreads());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("SSOLogoutURL")) {
                  original.setSSOLogoutURL(proposed.getSSOLogoutURL());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("SessionTimeout")) {
                  original.setSessionTimeout(proposed.getSessionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ProtectedCookieEnabled")) {
                  original.setProtectedCookieEnabled(proposed.isProtectedCookieEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            AdminConsoleMBeanImpl copy = (AdminConsoleMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CookieName")) && this.bean.isCookieNameSet()) {
               copy.setCookieName(this.bean.getCookieName());
            }

            if ((excludeProps == null || !excludeProps.contains("MinThreads")) && this.bean.isMinThreadsSet()) {
               copy.setMinThreads(this.bean.getMinThreads());
            }

            if ((excludeProps == null || !excludeProps.contains("SSOLogoutURL")) && this.bean.isSSOLogoutURLSet()) {
               copy.setSSOLogoutURL(this.bean.getSSOLogoutURL());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionTimeout")) && this.bean.isSessionTimeoutSet()) {
               copy.setSessionTimeout(this.bean.getSessionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("ProtectedCookieEnabled")) && this.bean.isProtectedCookieEnabledSet()) {
               copy.setProtectedCookieEnabled(this.bean.isProtectedCookieEnabled());
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
      }
   }
}
