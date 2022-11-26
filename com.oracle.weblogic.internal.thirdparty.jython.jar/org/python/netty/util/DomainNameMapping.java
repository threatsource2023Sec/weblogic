package org.python.netty.util;

import java.net.IDN;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.StringUtil;

public class DomainNameMapping implements Mapping {
   final Object defaultValue;
   private final Map map;
   private final Map unmodifiableMap;

   /** @deprecated */
   @Deprecated
   public DomainNameMapping(Object defaultValue) {
      this(4, defaultValue);
   }

   /** @deprecated */
   @Deprecated
   public DomainNameMapping(int initialCapacity, Object defaultValue) {
      this(new LinkedHashMap(initialCapacity), defaultValue);
   }

   DomainNameMapping(Map map, Object defaultValue) {
      this.defaultValue = ObjectUtil.checkNotNull(defaultValue, "defaultValue");
      this.map = map;
      this.unmodifiableMap = map != null ? Collections.unmodifiableMap(map) : null;
   }

   /** @deprecated */
   @Deprecated
   public DomainNameMapping add(String hostname, Object output) {
      this.map.put(normalizeHostname((String)ObjectUtil.checkNotNull(hostname, "hostname")), ObjectUtil.checkNotNull(output, "output"));
      return this;
   }

   static boolean matches(String template, String hostName) {
      if (!template.startsWith("*.")) {
         return template.equals(hostName);
      } else {
         return template.regionMatches(2, hostName, 0, hostName.length()) || StringUtil.commonSuffixOfLength(hostName, template, template.length() - 1);
      }
   }

   static String normalizeHostname(String hostname) {
      if (needsNormalization(hostname)) {
         hostname = IDN.toASCII(hostname, 1);
      }

      return hostname.toLowerCase(Locale.US);
   }

   private static boolean needsNormalization(String hostname) {
      int length = hostname.length();

      for(int i = 0; i < length; ++i) {
         int c = hostname.charAt(i);
         if (c > 127) {
            return true;
         }
      }

      return false;
   }

   public Object map(String hostname) {
      if (hostname != null) {
         hostname = normalizeHostname(hostname);
         Iterator var2 = this.map.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            if (matches((String)entry.getKey(), hostname)) {
               return entry.getValue();
            }
         }
      }

      return this.defaultValue;
   }

   public Map asMap() {
      return this.unmodifiableMap;
   }

   public String toString() {
      return StringUtil.simpleClassName((Object)this) + "(default: " + this.defaultValue + ", map: " + this.map + ')';
   }
}
