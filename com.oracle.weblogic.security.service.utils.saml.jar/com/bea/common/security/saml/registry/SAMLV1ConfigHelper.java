package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.saml.utils.SAMLProfile;
import com.bea.common.security.saml.utils.SAMLUtil;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class SAMLV1ConfigHelper {
   public static final String PROP_ASSERTION_NAMES = "Assertions";
   private static final String PROP_TYPE = "AssertionType";
   private static final String PROP_TARGET = "Target";
   private static final String PROP_AUDIENCE = "AudienceURI";
   private static final String PROP_SIGNED = "Signed";
   private static final String PROP_NAMEMAPPER = "NameMapperClass";
   private static final String PROP_CONSUMER = "ConsumerURL";
   private static final String PROP_POSTFORM = "PostForm";
   private static final String PROP_DONOTCACHE = "DoNotCache";
   private static final String PROP_TTL = "TimeToLive";
   private static final String PROP_TTL_DELTA = "TTLDelta";
   private static final String PROP_INCLUDE_CERTS = "SigIncludeCerts";
   private static final String PROP_INCLUDE_GROUPS = "IncludeGroups";
   private static final String PROP_ISSUER = "IssuerURI";
   private static final String PROP_SOURCE_SITE = "SourceSiteURL";
   private static final String PROP_SOURCEID_HEX = "SourceIdHex";
   private static final String PROP_SOURCEID_B64 = "SourceIdBase64";
   private static final String PROP_RETRIEVAL = "RetrievalURL";
   private static final String PROP_TRUSTED_SENDER = "TrustedSender";
   private static final String PROP_ALLOW_VIRTUAL = "AllowVirtualUser";
   private static final String PROP_PROCESS_GROUPS = "ProcessGroups";
   public static final String PROP_REDIRECT_NAMES = "Redirects";
   private static final String PROP_TARGET_URI = "TargetURI";
   private static final String PROP_REDIRECT_URL = "RedirectURL";
   protected static final String V1_COMPAT_TRUSTED_SENDER = "V1_COMPAT_TRUSTED_SENDER";
   private static final String PROP_ITS_URL = "IntersiteTransferURL";
   private static final String PROP_ASSN_SIGN_ALIAS = "AssnSignCertAlias";
   private static final String PROP_PRTCL_SIGN_ALIAS = "PrtclSignCertAlias";
   private static HashMap v1AttributeMap = null;
   private LoggerSpi LOGGER = null;
   private Properties props = null;
   private String nameAttr = null;
   private List nameList = null;
   private Iterator nameIterator = null;

   private void logDebug(String method, String msg) {
      if (this.LOGGER.isDebugEnabled()) {
         this.LOGGER.debug("SAMLV1ConfigHelper: " + method + ": " + msg);
      }

   }

   private static void logDebug(LoggerSpi logger, String method, String msg) {
      if (logger.isDebugEnabled()) {
         logger.debug("SAMLV1ConfigHelper: " + method + ": " + msg);
      }

   }

   public SAMLV1ConfigHelper(LoggerSpi logger, Properties props, String nameAttr) {
      this.LOGGER = logger;
      this.props = props;
      this.nameAttr = nameAttr;
      this.logDebug("constructor", "nameAttr='" + nameAttr + "', props is " + (props == null ? "(null)" : "(not null)"));
   }

   private boolean setupIterator() {
      if (this.props != null && this.nameAttr != null && this.nameAttr.length() != 0) {
         if (this.nameList == null) {
            this.nameList = getEnabledEntries(this.LOGGER, this.props, this.nameAttr);
            if (this.nameList == null) {
               this.logDebug("setupIterator", "Found no enabled entries");
               return false;
            }
         }

         if (this.nameIterator == null) {
            this.nameIterator = this.nameList.iterator();
            if (this.nameIterator == null) {
               this.logDebug("setupIterator", "nameIterator is null");
               return false;
            }
         }

         this.logDebug("setupIterator", "Set up iterator");
         return true;
      } else {
         this.logDebug("setupIterator", "Invalid nameAttr or props");
         return false;
      }
   }

   public boolean hasMoreEntries() {
      return this.setupIterator() ? this.nameIterator.hasNext() : false;
   }

   public Map getNextEntry() {
      if (this.setupIterator() && this.nameIterator.hasNext()) {
         String entryName = (String)this.nameIterator.next();
         Map entry = getEntryAttributes(this.LOGGER, this.props, entryName);
         return entry;
      } else {
         return null;
      }
   }

   private static String mapV1AttributeName(String attr) {
      return (String)v1AttributeMap.get(attr);
   }

   private static List getEnabledEntries(LoggerSpi logger, Properties props, String enabledEntryProp) {
      if (props != null && enabledEntryProp != null && enabledEntryProp.length() != 0) {
         ArrayList enabledList = new ArrayList();
         String entryNames = props.getProperty(enabledEntryProp);
         logDebug(logger, "getEnabledEntries", "Names prop is: " + entryNames);
         if (entryNames != null) {
            String[] namesArray = entryNames.split("[,\\s]");

            for(int i = 0; namesArray != null && i < namesArray.length; ++i) {
               if (namesArray[i] != null && namesArray[i].length() > 0) {
                  enabledList.add(namesArray[i]);
               }
            }
         }

         logDebug(logger, "getEnabledEntries", "Returning " + enabledList.size() + " entry names");
         return enabledList.size() > 0 ? enabledList : null;
      } else {
         logDebug(logger, "getEnabledEntries", "Invalid parameters, returning null");
         return null;
      }
   }

   private static Map getEntryAttributes(LoggerSpi logger, Properties props, String entryName) {
      if (props != null && entryName != null && entryName.length() != 0) {
         logDebug(logger, "getEntryAttributes", "Getting attributes for name '" + entryName + "'");
         String prefix = entryName + ".";
         int prefixLen = prefix.length();
         HashMap attrMap = new HashMap();
         attrMap.put("cn", entryName);
         logDebug(logger, "getEntryAttributes", "Set 'cn' to '" + entryName + "'");
         attrMap.put("beaSAMLKeyinfoIncluded", "true");
         logDebug(logger, "getEntryAttributes", "Set 'beaSAMLKeyinfoIncluded' to 'true'");
         attrMap.put("beaSAMLVirtualUserEnabled", "true");
         logDebug(logger, "getEntryAttributes", "Set 'beaSAMLVirtualUserEnabled' to 'true'");
         Enumeration e = props.propertyNames();

         String propName;
         String attrName;
         while(e != null && e.hasMoreElements()) {
            propName = (String)e.nextElement();
            if (propName.startsWith(prefix) && propName.length() > prefixLen) {
               attrName = propName.substring(prefixLen);
               logDebug(logger, "getEntryAttributes", "Found attribute '" + propName + "'");
               String mappedName = mapV1AttributeName(attrName);
               if (mappedName != null) {
                  String attrValue = SAMLUtil.trimString(props.getProperty(propName));
                  if (attrName.equals("AssertionType")) {
                     String longName = SAMLProfile.mapConfMethodNameToProfileName(attrValue);
                     logDebug(logger, "getEntryAttributes", "Mapping profile short name '" + attrValue + "' to long name '" + longName + "'");
                     attrValue = longName;
                  }

                  attrMap.put(mappedName, attrValue);
                  logDebug(logger, "getEntryAttributes", "Mapped '" + attrName + "' to '" + mappedName + "', value is: " + attrValue);
               } else {
                  logDebug(logger, "getEntryAttributes", "No mapping for '" + attrName + "'");
               }
            }
         }

         propName = (String)attrMap.get("beaSAMLProfile");
         if (propName != null && propName.equals("WSS/Holder-of-Key")) {
            attrName = (String)attrMap.get("beaSAMLSignedAssertions");
            if (attrName == null) {
               attrMap.put("beaSAMLSignedAssertions", "true");
            }
         }

         logDebug(logger, "getEntryAttributes", "Returning " + attrMap.size() + " mapped attributes");
         return attrMap.size() > 0 ? attrMap : null;
      } else {
         logDebug(logger, "getEntryAttributes", "Invalid parameters, returning null");
         return null;
      }
   }

   static {
      v1AttributeMap = new HashMap();
      v1AttributeMap.put("AssertionType", "beaSAMLProfile");
      v1AttributeMap.put("Target", "beaSAMLTargetURL");
      v1AttributeMap.put("AudienceURI", "beaSAMLAudienceURI");
      v1AttributeMap.put("Signed", "beaSAMLSignedAssertions");
      v1AttributeMap.put("NameMapperClass", "beaSAMLNameMapperClass");
      v1AttributeMap.put("ConsumerURL", "beaSAMLAssertionConsumerURL");
      v1AttributeMap.put("PostForm", "beaSAMLPostForm");
      v1AttributeMap.put("DoNotCache", "beaSAMLDoNotCacheCondition");
      v1AttributeMap.put("TimeToLive", "beaSAMLTimeToLive");
      v1AttributeMap.put("TTLDelta", "beaSAMLTimeToLiveOffset");
      v1AttributeMap.put("SigIncludeCerts", "beaSAMLKeyinfoIncluded");
      v1AttributeMap.put("IncludeGroups", "beaSAMLGroupsAttributeEnabled");
      v1AttributeMap.put("IssuerURI", "beaSAMLIssuerURI");
      v1AttributeMap.put("SourceSiteURL", "beaSAMLSourceId");
      v1AttributeMap.put("SourceIdHex", "beaSAMLSourceId");
      v1AttributeMap.put("SourceIdBase64", "beaSAMLSourceId");
      v1AttributeMap.put("RetrievalURL", "beaSAMLAssertionRetrievalURL");
      v1AttributeMap.put("TrustedSender", "V1_COMPAT_TRUSTED_SENDER");
      v1AttributeMap.put("AllowVirtualUser", "beaSAMLVirtualUserEnabled");
      v1AttributeMap.put("ProcessGroups", "beaSAMLGroupsAttributeEnabled");
      v1AttributeMap.put("TargetURI", "beaSAMLRedirectURIs");
      v1AttributeMap.put("RedirectURL", "beaSAMLIntersiteTransferURL");
      v1AttributeMap.put("IntersiteTransferURL", "beaSAMLIntersiteTransferURL");
      v1AttributeMap.put("AssnSignCertAlias", "beaSAMLAssertionSigningCertAlias");
      v1AttributeMap.put("PrtclSignCertAlias", "beaSAMLProtocolSigningCertAlias");
   }
}
