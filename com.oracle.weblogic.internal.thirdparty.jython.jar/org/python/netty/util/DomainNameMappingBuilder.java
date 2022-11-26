package org.python.netty.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.python.netty.util.internal.ObjectUtil;

public final class DomainNameMappingBuilder {
   private final Object defaultValue;
   private final Map map;

   public DomainNameMappingBuilder(Object defaultValue) {
      this(4, defaultValue);
   }

   public DomainNameMappingBuilder(int initialCapacity, Object defaultValue) {
      this.defaultValue = ObjectUtil.checkNotNull(defaultValue, "defaultValue");
      this.map = new LinkedHashMap(initialCapacity);
   }

   public DomainNameMappingBuilder add(String hostname, Object output) {
      this.map.put(ObjectUtil.checkNotNull(hostname, "hostname"), ObjectUtil.checkNotNull(output, "output"));
      return this;
   }

   public DomainNameMapping build() {
      return new ImmutableDomainNameMapping(this.defaultValue, this.map);
   }

   private static final class ImmutableDomainNameMapping extends DomainNameMapping {
      private static final String REPR_HEADER = "ImmutableDomainNameMapping(default: ";
      private static final String REPR_MAP_OPENING = ", map: {";
      private static final String REPR_MAP_CLOSING = "})";
      private static final int REPR_CONST_PART_LENGTH = "ImmutableDomainNameMapping(default: ".length() + ", map: {".length() + "})".length();
      private final String[] domainNamePatterns;
      private final Object[] values;
      private final Map map;

      private ImmutableDomainNameMapping(Object defaultValue, Map map) {
         super((Map)null, defaultValue);
         Set mappings = map.entrySet();
         int numberOfMappings = mappings.size();
         this.domainNamePatterns = new String[numberOfMappings];
         this.values = (Object[])(new Object[numberOfMappings]);
         Map mapCopy = new LinkedHashMap(map.size());
         int index = 0;

         for(Iterator var7 = mappings.iterator(); var7.hasNext(); ++index) {
            Map.Entry mapping = (Map.Entry)var7.next();
            String hostname = normalizeHostname((String)mapping.getKey());
            Object value = mapping.getValue();
            this.domainNamePatterns[index] = hostname;
            this.values[index] = value;
            mapCopy.put(hostname, value);
         }

         this.map = Collections.unmodifiableMap(mapCopy);
      }

      /** @deprecated */
      @Deprecated
      public DomainNameMapping add(String hostname, Object output) {
         throw new UnsupportedOperationException("Immutable DomainNameMapping does not support modification after initial creation");
      }

      public Object map(String hostname) {
         if (hostname != null) {
            hostname = normalizeHostname(hostname);
            int length = this.domainNamePatterns.length;

            for(int index = 0; index < length; ++index) {
               if (matches(this.domainNamePatterns[index], hostname)) {
                  return this.values[index];
               }
            }
         }

         return this.defaultValue;
      }

      public Map asMap() {
         return this.map;
      }

      public String toString() {
         String defaultValueStr = this.defaultValue.toString();
         int numberOfMappings = this.domainNamePatterns.length;
         if (numberOfMappings == 0) {
            return "ImmutableDomainNameMapping(default: " + defaultValueStr + ", map: {" + "})";
         } else {
            String pattern0 = this.domainNamePatterns[0];
            String value0 = this.values[0].toString();
            int oneMappingLength = pattern0.length() + value0.length() + 3;
            int estimatedBufferSize = estimateBufferSize(defaultValueStr.length(), numberOfMappings, oneMappingLength);
            StringBuilder sb = (new StringBuilder(estimatedBufferSize)).append("ImmutableDomainNameMapping(default: ").append(defaultValueStr).append(", map: {");
            appendMapping(sb, pattern0, value0);

            for(int index = 1; index < numberOfMappings; ++index) {
               sb.append(", ");
               this.appendMapping(sb, index);
            }

            return sb.append("})").toString();
         }
      }

      private static int estimateBufferSize(int defaultValueLength, int numberOfMappings, int estimatedMappingLength) {
         return REPR_CONST_PART_LENGTH + defaultValueLength + (int)((double)(estimatedMappingLength * numberOfMappings) * 1.1);
      }

      private StringBuilder appendMapping(StringBuilder sb, int mappingIndex) {
         return appendMapping(sb, this.domainNamePatterns[mappingIndex], this.values[mappingIndex].toString());
      }

      private static StringBuilder appendMapping(StringBuilder sb, String domainNamePattern, String value) {
         return sb.append(domainNamePattern).append('=').append(value);
      }

      // $FF: synthetic method
      ImmutableDomainNameMapping(Object x0, Map x1, Object x2) {
         this(x0, x1);
      }
   }
}
