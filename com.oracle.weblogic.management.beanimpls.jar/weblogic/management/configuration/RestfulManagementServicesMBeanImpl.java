package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class RestfulManagementServicesMBeanImpl extends ConfigurationMBeanImpl implements RestfulManagementServicesMBean, Serializable {
   private boolean _CORSAllowedCredentials;
   private String _CORSAllowedHeaders;
   private String _CORSAllowedMethods;
   private String[] _CORSAllowedOrigins;
   private boolean _CORSEnabled;
   private String _CORSExposedHeaders;
   private String _CORSMaxAge;
   private int _DelegatedRequestConnectTimeoutMillis;
   private int _DelegatedRequestMaxWaitMillis;
   private int _DelegatedRequestMinThreads;
   private int _DelegatedRequestReadTimeoutMillis;
   private boolean _Enabled;
   private int _FannedOutRequestMaxWaitMillis;
   private boolean _JavaServiceResourcesEnabled;
   private static SchemaHelper2 _schemaHelper;

   public RestfulManagementServicesMBeanImpl() {
      this._initializeProperty(-1);
   }

   public RestfulManagementServicesMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public RestfulManagementServicesMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(10);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isJavaServiceResourcesEnabled() {
      if (!this._isSet(11)) {
         try {
            return ((DomainMBean)this.getParent()).isJavaServiceEnabled();
         } catch (NullPointerException var2) {
         }
      }

      return this._JavaServiceResourcesEnabled;
   }

   public boolean isJavaServiceResourcesEnabledInherited() {
      return false;
   }

   public boolean isJavaServiceResourcesEnabledSet() {
      return this._isSet(11);
   }

   public void setJavaServiceResourcesEnabled(boolean param0) {
      boolean _oldVal = this._JavaServiceResourcesEnabled;
      this._JavaServiceResourcesEnabled = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getFannedOutRequestMaxWaitMillis() {
      return this._FannedOutRequestMaxWaitMillis;
   }

   public boolean isFannedOutRequestMaxWaitMillisInherited() {
      return false;
   }

   public boolean isFannedOutRequestMaxWaitMillisSet() {
      return this._isSet(12);
   }

   public void setFannedOutRequestMaxWaitMillis(int param0) {
      LegalChecks.checkMin("FannedOutRequestMaxWaitMillis", param0, 0);
      int _oldVal = this._FannedOutRequestMaxWaitMillis;
      this._FannedOutRequestMaxWaitMillis = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getDelegatedRequestMaxWaitMillis() {
      return this._DelegatedRequestMaxWaitMillis;
   }

   public boolean isDelegatedRequestMaxWaitMillisInherited() {
      return false;
   }

   public boolean isDelegatedRequestMaxWaitMillisSet() {
      return this._isSet(13);
   }

   public void setDelegatedRequestMaxWaitMillis(int param0) {
      LegalChecks.checkMin("DelegatedRequestMaxWaitMillis", param0, 0);
      int _oldVal = this._DelegatedRequestMaxWaitMillis;
      this._DelegatedRequestMaxWaitMillis = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getDelegatedRequestConnectTimeoutMillis() {
      return this._DelegatedRequestConnectTimeoutMillis;
   }

   public boolean isDelegatedRequestConnectTimeoutMillisInherited() {
      return false;
   }

   public boolean isDelegatedRequestConnectTimeoutMillisSet() {
      return this._isSet(14);
   }

   public void setDelegatedRequestConnectTimeoutMillis(int param0) {
      LegalChecks.checkMin("DelegatedRequestConnectTimeoutMillis", param0, 0);
      int _oldVal = this._DelegatedRequestConnectTimeoutMillis;
      this._DelegatedRequestConnectTimeoutMillis = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getDelegatedRequestReadTimeoutMillis() {
      return this._DelegatedRequestReadTimeoutMillis;
   }

   public boolean isDelegatedRequestReadTimeoutMillisInherited() {
      return false;
   }

   public boolean isDelegatedRequestReadTimeoutMillisSet() {
      return this._isSet(15);
   }

   public void setDelegatedRequestReadTimeoutMillis(int param0) {
      LegalChecks.checkMin("DelegatedRequestReadTimeoutMillis", param0, 0);
      int _oldVal = this._DelegatedRequestReadTimeoutMillis;
      this._DelegatedRequestReadTimeoutMillis = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getDelegatedRequestMinThreads() {
      return this._DelegatedRequestMinThreads;
   }

   public boolean isDelegatedRequestMinThreadsInherited() {
      return false;
   }

   public boolean isDelegatedRequestMinThreadsSet() {
      return this._isSet(16);
   }

   public void setDelegatedRequestMinThreads(int param0) {
      LegalChecks.checkMin("DelegatedRequestMinThreads", param0, 1);
      int _oldVal = this._DelegatedRequestMinThreads;
      this._DelegatedRequestMinThreads = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean isCORSEnabled() {
      return this._CORSEnabled;
   }

   public boolean isCORSEnabledInherited() {
      return false;
   }

   public boolean isCORSEnabledSet() {
      return this._isSet(17);
   }

   public void setCORSEnabled(boolean param0) {
      boolean _oldVal = this._CORSEnabled;
      this._CORSEnabled = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String[] getCORSAllowedOrigins() {
      return this._CORSAllowedOrigins;
   }

   public boolean isCORSAllowedOriginsInherited() {
      return false;
   }

   public boolean isCORSAllowedOriginsSet() {
      return this._isSet(18);
   }

   public void setCORSAllowedOrigins(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._CORSAllowedOrigins;
      this._CORSAllowedOrigins = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isCORSAllowedCredentials() {
      return this._CORSAllowedCredentials;
   }

   public boolean isCORSAllowedCredentialsInherited() {
      return false;
   }

   public boolean isCORSAllowedCredentialsSet() {
      return this._isSet(19);
   }

   public void setCORSAllowedCredentials(boolean param0) {
      boolean _oldVal = this._CORSAllowedCredentials;
      this._CORSAllowedCredentials = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String getCORSAllowedMethods() {
      return this._CORSAllowedMethods;
   }

   public boolean isCORSAllowedMethodsInherited() {
      return false;
   }

   public boolean isCORSAllowedMethodsSet() {
      return this._isSet(20);
   }

   public void setCORSAllowedMethods(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CORSAllowedMethods;
      this._CORSAllowedMethods = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getCORSAllowedHeaders() {
      return this._CORSAllowedHeaders;
   }

   public boolean isCORSAllowedHeadersInherited() {
      return false;
   }

   public boolean isCORSAllowedHeadersSet() {
      return this._isSet(21);
   }

   public void setCORSAllowedHeaders(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CORSAllowedHeaders;
      this._CORSAllowedHeaders = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getCORSExposedHeaders() {
      return this._CORSExposedHeaders;
   }

   public boolean isCORSExposedHeadersInherited() {
      return false;
   }

   public boolean isCORSExposedHeadersSet() {
      return this._isSet(22);
   }

   public void setCORSExposedHeaders(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CORSExposedHeaders;
      this._CORSExposedHeaders = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getCORSMaxAge() {
      return this._CORSMaxAge;
   }

   public boolean isCORSMaxAgeInherited() {
      return false;
   }

   public boolean isCORSMaxAgeSet() {
      return this._isSet(23);
   }

   public void setCORSMaxAge(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CORSMaxAge;
      this._CORSMaxAge = param0;
      this._postSet(23, _oldVal, param0);
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
         idx = 21;
      }

      try {
         switch (idx) {
            case 21:
               this._CORSAllowedHeaders = "*";
               if (initOne) {
                  break;
               }
            case 20:
               this._CORSAllowedMethods = "*";
               if (initOne) {
                  break;
               }
            case 18:
               this._CORSAllowedOrigins = new String[0];
               if (initOne) {
                  break;
               }
            case 22:
               this._CORSExposedHeaders = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._CORSMaxAge = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._DelegatedRequestConnectTimeoutMillis = 30000;
               if (initOne) {
                  break;
               }
            case 13:
               this._DelegatedRequestMaxWaitMillis = 0;
               if (initOne) {
                  break;
               }
            case 16:
               this._DelegatedRequestMinThreads = 25;
               if (initOne) {
                  break;
               }
            case 15:
               this._DelegatedRequestReadTimeoutMillis = 10000;
               if (initOne) {
                  break;
               }
            case 12:
               this._FannedOutRequestMaxWaitMillis = 180000;
               if (initOne) {
                  break;
               }
            case 19:
               this._CORSAllowedCredentials = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._CORSEnabled = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._Enabled = true;
               if (initOne) {
                  break;
               }
            case 11:
               this._JavaServiceResourcesEnabled = false;
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
      return "RestfulManagementServices";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("CORSAllowedCredentials")) {
         oldVal = this._CORSAllowedCredentials;
         this._CORSAllowedCredentials = (Boolean)v;
         this._postSet(19, oldVal, this._CORSAllowedCredentials);
      } else {
         String oldVal;
         if (name.equals("CORSAllowedHeaders")) {
            oldVal = this._CORSAllowedHeaders;
            this._CORSAllowedHeaders = (String)v;
            this._postSet(21, oldVal, this._CORSAllowedHeaders);
         } else if (name.equals("CORSAllowedMethods")) {
            oldVal = this._CORSAllowedMethods;
            this._CORSAllowedMethods = (String)v;
            this._postSet(20, oldVal, this._CORSAllowedMethods);
         } else if (name.equals("CORSAllowedOrigins")) {
            String[] oldVal = this._CORSAllowedOrigins;
            this._CORSAllowedOrigins = (String[])((String[])v);
            this._postSet(18, oldVal, this._CORSAllowedOrigins);
         } else if (name.equals("CORSEnabled")) {
            oldVal = this._CORSEnabled;
            this._CORSEnabled = (Boolean)v;
            this._postSet(17, oldVal, this._CORSEnabled);
         } else if (name.equals("CORSExposedHeaders")) {
            oldVal = this._CORSExposedHeaders;
            this._CORSExposedHeaders = (String)v;
            this._postSet(22, oldVal, this._CORSExposedHeaders);
         } else if (name.equals("CORSMaxAge")) {
            oldVal = this._CORSMaxAge;
            this._CORSMaxAge = (String)v;
            this._postSet(23, oldVal, this._CORSMaxAge);
         } else {
            int oldVal;
            if (name.equals("DelegatedRequestConnectTimeoutMillis")) {
               oldVal = this._DelegatedRequestConnectTimeoutMillis;
               this._DelegatedRequestConnectTimeoutMillis = (Integer)v;
               this._postSet(14, oldVal, this._DelegatedRequestConnectTimeoutMillis);
            } else if (name.equals("DelegatedRequestMaxWaitMillis")) {
               oldVal = this._DelegatedRequestMaxWaitMillis;
               this._DelegatedRequestMaxWaitMillis = (Integer)v;
               this._postSet(13, oldVal, this._DelegatedRequestMaxWaitMillis);
            } else if (name.equals("DelegatedRequestMinThreads")) {
               oldVal = this._DelegatedRequestMinThreads;
               this._DelegatedRequestMinThreads = (Integer)v;
               this._postSet(16, oldVal, this._DelegatedRequestMinThreads);
            } else if (name.equals("DelegatedRequestReadTimeoutMillis")) {
               oldVal = this._DelegatedRequestReadTimeoutMillis;
               this._DelegatedRequestReadTimeoutMillis = (Integer)v;
               this._postSet(15, oldVal, this._DelegatedRequestReadTimeoutMillis);
            } else if (name.equals("Enabled")) {
               oldVal = this._Enabled;
               this._Enabled = (Boolean)v;
               this._postSet(10, oldVal, this._Enabled);
            } else if (name.equals("FannedOutRequestMaxWaitMillis")) {
               oldVal = this._FannedOutRequestMaxWaitMillis;
               this._FannedOutRequestMaxWaitMillis = (Integer)v;
               this._postSet(12, oldVal, this._FannedOutRequestMaxWaitMillis);
            } else if (name.equals("JavaServiceResourcesEnabled")) {
               oldVal = this._JavaServiceResourcesEnabled;
               this._JavaServiceResourcesEnabled = (Boolean)v;
               this._postSet(11, oldVal, this._JavaServiceResourcesEnabled);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CORSAllowedCredentials")) {
         return new Boolean(this._CORSAllowedCredentials);
      } else if (name.equals("CORSAllowedHeaders")) {
         return this._CORSAllowedHeaders;
      } else if (name.equals("CORSAllowedMethods")) {
         return this._CORSAllowedMethods;
      } else if (name.equals("CORSAllowedOrigins")) {
         return this._CORSAllowedOrigins;
      } else if (name.equals("CORSEnabled")) {
         return new Boolean(this._CORSEnabled);
      } else if (name.equals("CORSExposedHeaders")) {
         return this._CORSExposedHeaders;
      } else if (name.equals("CORSMaxAge")) {
         return this._CORSMaxAge;
      } else if (name.equals("DelegatedRequestConnectTimeoutMillis")) {
         return new Integer(this._DelegatedRequestConnectTimeoutMillis);
      } else if (name.equals("DelegatedRequestMaxWaitMillis")) {
         return new Integer(this._DelegatedRequestMaxWaitMillis);
      } else if (name.equals("DelegatedRequestMinThreads")) {
         return new Integer(this._DelegatedRequestMinThreads);
      } else if (name.equals("DelegatedRequestReadTimeoutMillis")) {
         return new Integer(this._DelegatedRequestReadTimeoutMillis);
      } else if (name.equals("Enabled")) {
         return new Boolean(this._Enabled);
      } else if (name.equals("FannedOutRequestMaxWaitMillis")) {
         return new Integer(this._FannedOutRequestMaxWaitMillis);
      } else {
         return name.equals("JavaServiceResourcesEnabled") ? new Boolean(this._JavaServiceResourcesEnabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("enabled")) {
                  return 10;
               }
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 31:
            case 32:
            case 35:
            case 36:
            case 38:
            case 39:
            default:
               break;
            case 12:
               if (s.equals("cors-max-age")) {
                  return 23;
               }

               if (s.equals("cors-enabled")) {
                  return 17;
               }
               break;
            case 19:
               if (s.equals("cors-allowed-origin")) {
                  return 18;
               }
               break;
            case 20:
               if (s.equals("cors-allowed-headers")) {
                  return 21;
               }

               if (s.equals("cors-allowed-methods")) {
                  return 20;
               }

               if (s.equals("cors-exposed-headers")) {
                  return 22;
               }
               break;
            case 24:
               if (s.equals("cors-allowed-credentials")) {
                  return 19;
               }
               break;
            case 29:
               if (s.equals("delegated-request-min-threads")) {
                  return 16;
               }
               break;
            case 30:
               if (s.equals("java-service-resources-enabled")) {
                  return 11;
               }
               break;
            case 33:
               if (s.equals("delegated-request-max-wait-millis")) {
                  return 13;
               }
               break;
            case 34:
               if (s.equals("fanned-out-request-max-wait-millis")) {
                  return 12;
               }
               break;
            case 37:
               if (s.equals("delegated-request-read-timeout-millis")) {
                  return 15;
               }
               break;
            case 40:
               if (s.equals("delegated-request-connect-timeout-millis")) {
                  return 14;
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
               return "enabled";
            case 11:
               return "java-service-resources-enabled";
            case 12:
               return "fanned-out-request-max-wait-millis";
            case 13:
               return "delegated-request-max-wait-millis";
            case 14:
               return "delegated-request-connect-timeout-millis";
            case 15:
               return "delegated-request-read-timeout-millis";
            case 16:
               return "delegated-request-min-threads";
            case 17:
               return "cors-enabled";
            case 18:
               return "cors-allowed-origin";
            case 19:
               return "cors-allowed-credentials";
            case 20:
               return "cors-allowed-methods";
            case 21:
               return "cors-allowed-headers";
            case 22:
               return "cors-exposed-headers";
            case 23:
               return "cors-max-age";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 18:
               return true;
            default:
               return super.isArray(propIndex);
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
      private RestfulManagementServicesMBeanImpl bean;

      protected Helper(RestfulManagementServicesMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Enabled";
            case 11:
               return "JavaServiceResourcesEnabled";
            case 12:
               return "FannedOutRequestMaxWaitMillis";
            case 13:
               return "DelegatedRequestMaxWaitMillis";
            case 14:
               return "DelegatedRequestConnectTimeoutMillis";
            case 15:
               return "DelegatedRequestReadTimeoutMillis";
            case 16:
               return "DelegatedRequestMinThreads";
            case 17:
               return "CORSEnabled";
            case 18:
               return "CORSAllowedOrigins";
            case 19:
               return "CORSAllowedCredentials";
            case 20:
               return "CORSAllowedMethods";
            case 21:
               return "CORSAllowedHeaders";
            case 22:
               return "CORSExposedHeaders";
            case 23:
               return "CORSMaxAge";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CORSAllowedHeaders")) {
            return 21;
         } else if (propName.equals("CORSAllowedMethods")) {
            return 20;
         } else if (propName.equals("CORSAllowedOrigins")) {
            return 18;
         } else if (propName.equals("CORSExposedHeaders")) {
            return 22;
         } else if (propName.equals("CORSMaxAge")) {
            return 23;
         } else if (propName.equals("DelegatedRequestConnectTimeoutMillis")) {
            return 14;
         } else if (propName.equals("DelegatedRequestMaxWaitMillis")) {
            return 13;
         } else if (propName.equals("DelegatedRequestMinThreads")) {
            return 16;
         } else if (propName.equals("DelegatedRequestReadTimeoutMillis")) {
            return 15;
         } else if (propName.equals("FannedOutRequestMaxWaitMillis")) {
            return 12;
         } else if (propName.equals("CORSAllowedCredentials")) {
            return 19;
         } else if (propName.equals("CORSEnabled")) {
            return 17;
         } else if (propName.equals("Enabled")) {
            return 10;
         } else {
            return propName.equals("JavaServiceResourcesEnabled") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isCORSAllowedHeadersSet()) {
               buf.append("CORSAllowedHeaders");
               buf.append(String.valueOf(this.bean.getCORSAllowedHeaders()));
            }

            if (this.bean.isCORSAllowedMethodsSet()) {
               buf.append("CORSAllowedMethods");
               buf.append(String.valueOf(this.bean.getCORSAllowedMethods()));
            }

            if (this.bean.isCORSAllowedOriginsSet()) {
               buf.append("CORSAllowedOrigins");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCORSAllowedOrigins())));
            }

            if (this.bean.isCORSExposedHeadersSet()) {
               buf.append("CORSExposedHeaders");
               buf.append(String.valueOf(this.bean.getCORSExposedHeaders()));
            }

            if (this.bean.isCORSMaxAgeSet()) {
               buf.append("CORSMaxAge");
               buf.append(String.valueOf(this.bean.getCORSMaxAge()));
            }

            if (this.bean.isDelegatedRequestConnectTimeoutMillisSet()) {
               buf.append("DelegatedRequestConnectTimeoutMillis");
               buf.append(String.valueOf(this.bean.getDelegatedRequestConnectTimeoutMillis()));
            }

            if (this.bean.isDelegatedRequestMaxWaitMillisSet()) {
               buf.append("DelegatedRequestMaxWaitMillis");
               buf.append(String.valueOf(this.bean.getDelegatedRequestMaxWaitMillis()));
            }

            if (this.bean.isDelegatedRequestMinThreadsSet()) {
               buf.append("DelegatedRequestMinThreads");
               buf.append(String.valueOf(this.bean.getDelegatedRequestMinThreads()));
            }

            if (this.bean.isDelegatedRequestReadTimeoutMillisSet()) {
               buf.append("DelegatedRequestReadTimeoutMillis");
               buf.append(String.valueOf(this.bean.getDelegatedRequestReadTimeoutMillis()));
            }

            if (this.bean.isFannedOutRequestMaxWaitMillisSet()) {
               buf.append("FannedOutRequestMaxWaitMillis");
               buf.append(String.valueOf(this.bean.getFannedOutRequestMaxWaitMillis()));
            }

            if (this.bean.isCORSAllowedCredentialsSet()) {
               buf.append("CORSAllowedCredentials");
               buf.append(String.valueOf(this.bean.isCORSAllowedCredentials()));
            }

            if (this.bean.isCORSEnabledSet()) {
               buf.append("CORSEnabled");
               buf.append(String.valueOf(this.bean.isCORSEnabled()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
            }

            if (this.bean.isJavaServiceResourcesEnabledSet()) {
               buf.append("JavaServiceResourcesEnabled");
               buf.append(String.valueOf(this.bean.isJavaServiceResourcesEnabled()));
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
            RestfulManagementServicesMBeanImpl otherTyped = (RestfulManagementServicesMBeanImpl)other;
            this.computeDiff("CORSAllowedHeaders", this.bean.getCORSAllowedHeaders(), otherTyped.getCORSAllowedHeaders(), true);
            this.computeDiff("CORSAllowedMethods", this.bean.getCORSAllowedMethods(), otherTyped.getCORSAllowedMethods(), true);
            this.computeDiff("CORSAllowedOrigins", this.bean.getCORSAllowedOrigins(), otherTyped.getCORSAllowedOrigins(), true);
            this.computeDiff("CORSExposedHeaders", this.bean.getCORSExposedHeaders(), otherTyped.getCORSExposedHeaders(), true);
            this.computeDiff("CORSMaxAge", this.bean.getCORSMaxAge(), otherTyped.getCORSMaxAge(), true);
            this.computeDiff("DelegatedRequestConnectTimeoutMillis", this.bean.getDelegatedRequestConnectTimeoutMillis(), otherTyped.getDelegatedRequestConnectTimeoutMillis(), true);
            this.computeDiff("DelegatedRequestMaxWaitMillis", this.bean.getDelegatedRequestMaxWaitMillis(), otherTyped.getDelegatedRequestMaxWaitMillis(), true);
            this.computeDiff("DelegatedRequestMinThreads", this.bean.getDelegatedRequestMinThreads(), otherTyped.getDelegatedRequestMinThreads(), true);
            this.computeDiff("DelegatedRequestReadTimeoutMillis", this.bean.getDelegatedRequestReadTimeoutMillis(), otherTyped.getDelegatedRequestReadTimeoutMillis(), true);
            this.computeDiff("FannedOutRequestMaxWaitMillis", this.bean.getFannedOutRequestMaxWaitMillis(), otherTyped.getFannedOutRequestMaxWaitMillis(), true);
            this.computeDiff("CORSAllowedCredentials", this.bean.isCORSAllowedCredentials(), otherTyped.isCORSAllowedCredentials(), true);
            this.computeDiff("CORSEnabled", this.bean.isCORSEnabled(), otherTyped.isCORSEnabled(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), false);
            this.computeDiff("JavaServiceResourcesEnabled", this.bean.isJavaServiceResourcesEnabled(), otherTyped.isJavaServiceResourcesEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            RestfulManagementServicesMBeanImpl original = (RestfulManagementServicesMBeanImpl)event.getSourceBean();
            RestfulManagementServicesMBeanImpl proposed = (RestfulManagementServicesMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CORSAllowedHeaders")) {
                  original.setCORSAllowedHeaders(proposed.getCORSAllowedHeaders());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("CORSAllowedMethods")) {
                  original.setCORSAllowedMethods(proposed.getCORSAllowedMethods());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("CORSAllowedOrigins")) {
                  original.setCORSAllowedOrigins(proposed.getCORSAllowedOrigins());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("CORSExposedHeaders")) {
                  original.setCORSExposedHeaders(proposed.getCORSExposedHeaders());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("CORSMaxAge")) {
                  original.setCORSMaxAge(proposed.getCORSMaxAge());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("DelegatedRequestConnectTimeoutMillis")) {
                  original.setDelegatedRequestConnectTimeoutMillis(proposed.getDelegatedRequestConnectTimeoutMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("DelegatedRequestMaxWaitMillis")) {
                  original.setDelegatedRequestMaxWaitMillis(proposed.getDelegatedRequestMaxWaitMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DelegatedRequestMinThreads")) {
                  original.setDelegatedRequestMinThreads(proposed.getDelegatedRequestMinThreads());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("DelegatedRequestReadTimeoutMillis")) {
                  original.setDelegatedRequestReadTimeoutMillis(proposed.getDelegatedRequestReadTimeoutMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("FannedOutRequestMaxWaitMillis")) {
                  original.setFannedOutRequestMaxWaitMillis(proposed.getFannedOutRequestMaxWaitMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("CORSAllowedCredentials")) {
                  original.setCORSAllowedCredentials(proposed.isCORSAllowedCredentials());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("CORSEnabled")) {
                  original.setCORSEnabled(proposed.isCORSEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("JavaServiceResourcesEnabled")) {
                  original.setJavaServiceResourcesEnabled(proposed.isJavaServiceResourcesEnabled());
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
            RestfulManagementServicesMBeanImpl copy = (RestfulManagementServicesMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CORSAllowedHeaders")) && this.bean.isCORSAllowedHeadersSet()) {
               copy.setCORSAllowedHeaders(this.bean.getCORSAllowedHeaders());
            }

            if ((excludeProps == null || !excludeProps.contains("CORSAllowedMethods")) && this.bean.isCORSAllowedMethodsSet()) {
               copy.setCORSAllowedMethods(this.bean.getCORSAllowedMethods());
            }

            if ((excludeProps == null || !excludeProps.contains("CORSAllowedOrigins")) && this.bean.isCORSAllowedOriginsSet()) {
               Object o = this.bean.getCORSAllowedOrigins();
               copy.setCORSAllowedOrigins(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("CORSExposedHeaders")) && this.bean.isCORSExposedHeadersSet()) {
               copy.setCORSExposedHeaders(this.bean.getCORSExposedHeaders());
            }

            if ((excludeProps == null || !excludeProps.contains("CORSMaxAge")) && this.bean.isCORSMaxAgeSet()) {
               copy.setCORSMaxAge(this.bean.getCORSMaxAge());
            }

            if ((excludeProps == null || !excludeProps.contains("DelegatedRequestConnectTimeoutMillis")) && this.bean.isDelegatedRequestConnectTimeoutMillisSet()) {
               copy.setDelegatedRequestConnectTimeoutMillis(this.bean.getDelegatedRequestConnectTimeoutMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("DelegatedRequestMaxWaitMillis")) && this.bean.isDelegatedRequestMaxWaitMillisSet()) {
               copy.setDelegatedRequestMaxWaitMillis(this.bean.getDelegatedRequestMaxWaitMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("DelegatedRequestMinThreads")) && this.bean.isDelegatedRequestMinThreadsSet()) {
               copy.setDelegatedRequestMinThreads(this.bean.getDelegatedRequestMinThreads());
            }

            if ((excludeProps == null || !excludeProps.contains("DelegatedRequestReadTimeoutMillis")) && this.bean.isDelegatedRequestReadTimeoutMillisSet()) {
               copy.setDelegatedRequestReadTimeoutMillis(this.bean.getDelegatedRequestReadTimeoutMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("FannedOutRequestMaxWaitMillis")) && this.bean.isFannedOutRequestMaxWaitMillisSet()) {
               copy.setFannedOutRequestMaxWaitMillis(this.bean.getFannedOutRequestMaxWaitMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("CORSAllowedCredentials")) && this.bean.isCORSAllowedCredentialsSet()) {
               copy.setCORSAllowedCredentials(this.bean.isCORSAllowedCredentials());
            }

            if ((excludeProps == null || !excludeProps.contains("CORSEnabled")) && this.bean.isCORSEnabledSet()) {
               copy.setCORSEnabled(this.bean.isCORSEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaServiceResourcesEnabled")) && this.bean.isJavaServiceResourcesEnabledSet()) {
               copy.setJavaServiceResourcesEnabled(this.bean.isJavaServiceResourcesEnabled());
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
