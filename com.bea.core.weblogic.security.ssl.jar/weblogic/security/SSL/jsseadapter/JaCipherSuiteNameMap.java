package weblogic.security.SSL.jsseadapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class JaCipherSuiteNameMap {
   private static final String SYSPROPNAME_DISABLE_CIPHERSUITE_ALIASES = "weblogic.security.SSL.disableJsseCipherSuiteAliases";
   private static final boolean DISABLE_CIPHERSUITE_ALIASES = Boolean.getBoolean("weblogic.security.SSL.disableJsseCipherSuiteAliases");
   private static final Map nameMap_toJsse;
   private static final Map nameMap_fromJsse;
   private static final int NAMEMAP_FROMJSSE_SIZE;

   static final String[] toJsse(String[] cipherSuiteNames) {
      if (DISABLE_CIPHERSUITE_ALIASES) {
         return cipherSuiteNames;
      } else if (null == cipherSuiteNames) {
         throw new IllegalArgumentException("Non-null cipherSuiteNames expected.");
      } else if (0 == cipherSuiteNames.length) {
         return new String[0];
      } else {
         ArrayList output = new ArrayList(cipherSuiteNames.length);
         String[] outputArr = cipherSuiteNames;
         int var3 = cipherSuiteNames.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String name = outputArr[var4];
            if (null != name) {
               String trimmedName = name.trim();
               if (0 != trimmedName.length()) {
                  String alias = (String)nameMap_toJsse.get(trimmedName);
                  String addName;
                  if (null == alias) {
                     addName = trimmedName;
                  } else {
                     addName = alias;
                  }

                  if (!output.contains(addName)) {
                     output.add(addName);
                  }
               }
            }
         }

         outputArr = (String[])output.toArray(new String[0]);
         return outputArr;
      }
   }

   static final String[] fromJsse(String[] jsseCipherSuiteNames) {
      if (DISABLE_CIPHERSUITE_ALIASES) {
         return jsseCipherSuiteNames;
      } else if (null == jsseCipherSuiteNames) {
         throw new IllegalArgumentException("Non-null jsseCipherSuiteNames expected.");
      } else if (0 == jsseCipherSuiteNames.length) {
         return new String[0];
      } else {
         ArrayList output = new ArrayList(jsseCipherSuiteNames.length + NAMEMAP_FROMJSSE_SIZE);
         String[] outputArr = jsseCipherSuiteNames;
         int var3 = jsseCipherSuiteNames.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String name = outputArr[var4];
            if (null != name) {
               String trimmedName = name.trim();
               if (0 != trimmedName.length()) {
                  if (!output.contains(trimmedName)) {
                     output.add(trimmedName);
                  }

                  String alias = (String)nameMap_fromJsse.get(trimmedName);
                  if (null != alias && !output.contains(alias)) {
                     output.add(alias);
                  }
               }
            }
         }

         outputArr = (String[])output.toArray(new String[0]);
         return outputArr;
      }
   }

   static {
      HashMap tempToJsseMap = new HashMap();
      tempToJsseMap.put("TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");
      tempToJsseMap.put("TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA");
      tempToJsseMap.put("TLS_DHE_DSS_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA");
      tempToJsseMap.put("TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA");
      tempToJsseMap.put("TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA");
      tempToJsseMap.put("TLS_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_RSA_WITH_DES_CBC_SHA");
      tempToJsseMap.put("TLS_DH_anon_EXPORT_WITH_DES40_CBC_SHA", "SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA");
      tempToJsseMap.put("TLS_DH_anon_EXPORT_WITH_RC4_40_MD5", "SSL_DH_anon_EXPORT_WITH_RC4_40_MD5");
      tempToJsseMap.put("TLS_DH_anon_WITH_3DES_EDE_CBC_SHA", "SSL_DH_anon_WITH_3DES_EDE_CBC_SHA");
      tempToJsseMap.put("TLS_DH_anon_WITH_DES_CBC_SHA", "SSL_DH_anon_WITH_DES_CBC_SHA");
      tempToJsseMap.put("TLS_DH_anon_WITH_RC4_128_MD5", "SSL_DH_anon_WITH_RC4_128_MD5");
      tempToJsseMap.put("TLS_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA");
      tempToJsseMap.put("TLS_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_RC4_40_MD5");
      tempToJsseMap.put("TLS_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA");
      tempToJsseMap.put("TLS_RSA_WITH_DES_CBC_SHA", "SSL_RSA_WITH_DES_CBC_SHA");
      tempToJsseMap.put("TLS_RSA_WITH_RC4_128_MD5", "SSL_RSA_WITH_RC4_128_MD5");
      tempToJsseMap.put("TLS_RSA_WITH_RC4_128_SHA", "SSL_RSA_WITH_RC4_128_SHA");
      nameMap_toJsse = Collections.unmodifiableMap(tempToJsseMap);
      HashMap tempFromJsseMap = new HashMap();
      Iterator var2 = nameMap_toJsse.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         tempFromJsseMap.put(entry.getValue(), entry.getKey());
      }

      nameMap_fromJsse = Collections.unmodifiableMap(tempFromJsseMap);
      NAMEMAP_FROMJSSE_SIZE = nameMap_fromJsse.size();
   }
}
