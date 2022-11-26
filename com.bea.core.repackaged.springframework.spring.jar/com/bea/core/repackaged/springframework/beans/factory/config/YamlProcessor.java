package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.CollectionFactory;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.UnicodeReader;

public abstract class YamlProcessor {
   private final Log logger = LogFactory.getLog(this.getClass());
   private ResolutionMethod resolutionMethod;
   private Resource[] resources;
   private List documentMatchers;
   private boolean matchDefault;

   public YamlProcessor() {
      this.resolutionMethod = YamlProcessor.ResolutionMethod.OVERRIDE;
      this.resources = new Resource[0];
      this.documentMatchers = Collections.emptyList();
      this.matchDefault = true;
   }

   public void setDocumentMatchers(DocumentMatcher... matchers) {
      this.documentMatchers = Arrays.asList(matchers);
   }

   public void setMatchDefault(boolean matchDefault) {
      this.matchDefault = matchDefault;
   }

   public void setResolutionMethod(ResolutionMethod resolutionMethod) {
      Assert.notNull(resolutionMethod, (String)"ResolutionMethod must not be null");
      this.resolutionMethod = resolutionMethod;
   }

   public void setResources(Resource... resources) {
      this.resources = resources;
   }

   protected void process(MatchCallback callback) {
      Yaml yaml = this.createYaml();
      Resource[] var3 = this.resources;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Resource resource = var3[var5];
         boolean found = this.process(callback, yaml, resource);
         if (this.resolutionMethod == YamlProcessor.ResolutionMethod.FIRST_FOUND && found) {
            return;
         }
      }

   }

   protected Yaml createYaml() {
      LoaderOptions options = new LoaderOptions();
      options.setAllowDuplicateKeys(false);
      return new Yaml(options);
   }

   private boolean process(MatchCallback callback, Yaml yaml, Resource resource) {
      int count = 0;

      try {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Loading from YAML: " + resource);
         }

         Reader reader = new UnicodeReader(resource.getInputStream());
         Throwable var6 = null;

         try {
            Iterator var7 = yaml.loadAll(reader).iterator();

            while(var7.hasNext()) {
               Object object = var7.next();
               if (object != null && this.process(this.asMap(object), callback)) {
                  ++count;
                  if (this.resolutionMethod == YamlProcessor.ResolutionMethod.FIRST_FOUND) {
                     break;
                  }
               }
            }

            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Loaded " + count + " document" + (count > 1 ? "s" : "") + " from YAML resource: " + resource);
            }
         } catch (Throwable var17) {
            var6 = var17;
            throw var17;
         } finally {
            if (reader != null) {
               if (var6 != null) {
                  try {
                     reader.close();
                  } catch (Throwable var16) {
                     var6.addSuppressed(var16);
                  }
               } else {
                  reader.close();
               }
            }

         }
      } catch (IOException var19) {
         this.handleProcessError(resource, var19);
      }

      return count > 0;
   }

   private void handleProcessError(Resource resource, IOException ex) {
      if (this.resolutionMethod != YamlProcessor.ResolutionMethod.FIRST_FOUND && this.resolutionMethod != YamlProcessor.ResolutionMethod.OVERRIDE_AND_IGNORE) {
         throw new IllegalStateException(ex);
      } else {
         if (this.logger.isWarnEnabled()) {
            this.logger.warn("Could not load map from " + resource + ": " + ex.getMessage());
         }

      }
   }

   private Map asMap(Object object) {
      Map result = new LinkedHashMap();
      if (!(object instanceof Map)) {
         result.put("document", object);
         return result;
      } else {
         Map map = (Map)object;
         map.forEach((key, value) -> {
            if (value instanceof Map) {
               value = this.asMap(value);
            }

            if (key instanceof CharSequence) {
               result.put(key.toString(), value);
            } else {
               result.put("[" + key.toString() + "]", value);
            }

         });
         return result;
      }
   }

   private boolean process(Map map, MatchCallback callback) {
      Properties properties = CollectionFactory.createStringAdaptingProperties();
      properties.putAll(this.getFlattenedMap(map));
      if (this.documentMatchers.isEmpty()) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Merging document (no matchers set): " + map);
         }

         callback.process(properties, map);
         return true;
      } else {
         MatchStatus result = YamlProcessor.MatchStatus.ABSTAIN;
         Iterator var5 = this.documentMatchers.iterator();

         MatchStatus match;
         do {
            if (!var5.hasNext()) {
               if (result == YamlProcessor.MatchStatus.ABSTAIN && this.matchDefault) {
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("Matched document with default matcher: " + map);
                  }

                  callback.process(properties, map);
                  return true;
               }

               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Unmatched document: " + map);
               }

               return false;
            }

            DocumentMatcher matcher = (DocumentMatcher)var5.next();
            match = matcher.matches(properties);
            result = YamlProcessor.MatchStatus.getMostSpecific(match, result);
         } while(match != YamlProcessor.MatchStatus.FOUND);

         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Matched document with document matcher: " + properties);
         }

         callback.process(properties, map);
         return true;
      }
   }

   protected final Map getFlattenedMap(Map source) {
      Map result = new LinkedHashMap();
      this.buildFlattenedMap(result, source, (String)null);
      return result;
   }

   private void buildFlattenedMap(Map result, Map source, @Nullable String path) {
      source.forEach((key, value) -> {
         if (StringUtils.hasText(path)) {
            if (key.startsWith("[")) {
               key = path + key;
            } else {
               key = path + '.' + key;
            }
         }

         if (value instanceof String) {
            result.put(key, value);
         } else if (value instanceof Map) {
            Map map = (Map)value;
            this.buildFlattenedMap(result, map, key);
         } else if (value instanceof Collection) {
            Collection collection = (Collection)value;
            if (collection.isEmpty()) {
               result.put(key, "");
            } else {
               int count = 0;
               Iterator var7 = collection.iterator();

               while(var7.hasNext()) {
                  Object object = var7.next();
                  this.buildFlattenedMap(result, Collections.singletonMap("[" + count++ + "]", object), key);
               }
            }
         } else {
            result.put(key, value != null ? value : "");
         }

      });
   }

   public static enum ResolutionMethod {
      OVERRIDE,
      OVERRIDE_AND_IGNORE,
      FIRST_FOUND;
   }

   public static enum MatchStatus {
      FOUND,
      NOT_FOUND,
      ABSTAIN;

      public static MatchStatus getMostSpecific(MatchStatus a, MatchStatus b) {
         return a.ordinal() < b.ordinal() ? a : b;
      }
   }

   public interface DocumentMatcher {
      MatchStatus matches(Properties var1);
   }

   public interface MatchCallback {
      void process(Properties var1, Map var2);
   }
}
