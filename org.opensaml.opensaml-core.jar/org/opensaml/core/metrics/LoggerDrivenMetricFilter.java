package org.opensaml.core.metrics;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerDrivenMetricFilter implements MetricFilter {
   @Nonnull
   @NotEmpty
   private final String loggerPrefix;
   @Nonnull
   @NonnullElements
   private final Map levelMap;

   public LoggerDrivenMetricFilter(@Nonnull @ParameterName(name = "prefix") @NotEmpty String prefix) {
      this(prefix, Collections.emptyMap());
   }

   public LoggerDrivenMetricFilter(@Nonnull @ParameterName(name = "prefix") @NotEmpty String prefix, @Nullable @ParameterName(name = "map") @NonnullElements Map map) {
      this.loggerPrefix = (String)Constraint.isNotNull(StringSupport.trimOrNull(prefix), "Prefix cannot be null or empty.");
      if (map != null && !map.isEmpty()) {
         this.levelMap = new HashMap(map.size());
         Iterator var3 = map.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            String trimmed = StringSupport.trimOrNull((String)entry.getKey());
            if (trimmed != null && entry.getValue() != null) {
               this.levelMap.put(trimmed, entry.getValue());
            }
         }
      } else {
         this.levelMap = Collections.emptyMap();
      }

   }

   public boolean matches(String name, Metric metric) {
      Logger logger = LoggerFactory.getLogger(this.loggerPrefix + name);
      Level level = (Level)this.levelMap.get(logger.getName());
      if (level == null) {
         return logger.isInfoEnabled();
      } else {
         switch (level) {
            case TRACE:
               return logger.isTraceEnabled();
            case DEBUG:
               return logger.isDebugEnabled();
            case INFO:
               return logger.isInfoEnabled();
            case WARN:
               return logger.isWarnEnabled();
            case ERROR:
               return logger.isErrorEnabled();
            default:
               return logger.isInfoEnabled();
         }
      }
   }

   public static enum Level {
      TRACE,
      DEBUG,
      INFO,
      WARN,
      ERROR;
   }
}
