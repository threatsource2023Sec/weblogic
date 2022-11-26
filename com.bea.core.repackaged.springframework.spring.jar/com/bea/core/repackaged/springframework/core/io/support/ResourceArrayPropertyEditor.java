package com.bea.core.repackaged.springframework.core.io.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.env.PropertyResolver;
import com.bea.core.repackaged.springframework.core.env.StandardEnvironment;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ResourceArrayPropertyEditor extends PropertyEditorSupport {
   private static final Log logger = LogFactory.getLog(ResourceArrayPropertyEditor.class);
   private final ResourcePatternResolver resourcePatternResolver;
   @Nullable
   private PropertyResolver propertyResolver;
   private final boolean ignoreUnresolvablePlaceholders;

   public ResourceArrayPropertyEditor() {
      this(new PathMatchingResourcePatternResolver(), (PropertyResolver)null, true);
   }

   public ResourceArrayPropertyEditor(ResourcePatternResolver resourcePatternResolver, @Nullable PropertyResolver propertyResolver) {
      this(resourcePatternResolver, propertyResolver, true);
   }

   public ResourceArrayPropertyEditor(ResourcePatternResolver resourcePatternResolver, @Nullable PropertyResolver propertyResolver, boolean ignoreUnresolvablePlaceholders) {
      Assert.notNull(resourcePatternResolver, (String)"ResourcePatternResolver must not be null");
      this.resourcePatternResolver = resourcePatternResolver;
      this.propertyResolver = propertyResolver;
      this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
   }

   public void setAsText(String text) {
      String pattern = this.resolvePath(text).trim();

      try {
         this.setValue(this.resourcePatternResolver.getResources(pattern));
      } catch (IOException var4) {
         throw new IllegalArgumentException("Could not resolve resource location pattern [" + pattern + "]: " + var4.getMessage());
      }
   }

   public void setValue(Object value) throws IllegalArgumentException {
      if (value instanceof Collection || value instanceof Object[] && !(value instanceof Resource[])) {
         Collection input = value instanceof Collection ? (Collection)value : Arrays.asList((Object[])((Object[])value));
         List merged = new ArrayList();
         Iterator var4 = ((Collection)input).iterator();

         while(true) {
            while(var4.hasNext()) {
               Object element = var4.next();
               if (element instanceof String) {
                  String pattern = this.resolvePath((String)element).trim();

                  try {
                     Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                     Resource[] var8 = resources;
                     int var9 = resources.length;

                     for(int var10 = 0; var10 < var9; ++var10) {
                        Resource resource = var8[var10];
                        if (!merged.contains(resource)) {
                           merged.add(resource);
                        }
                     }
                  } catch (IOException var12) {
                     if (logger.isDebugEnabled()) {
                        logger.debug("Could not retrieve resources for pattern '" + pattern + "'", var12);
                     }
                  }
               } else {
                  if (!(element instanceof Resource)) {
                     throw new IllegalArgumentException("Cannot convert element [" + element + "] to [" + Resource.class.getName() + "]: only location String and Resource object supported");
                  }

                  Resource resource = (Resource)element;
                  if (!merged.contains(resource)) {
                     merged.add(resource);
                  }
               }
            }

            super.setValue(merged.toArray(new Resource[0]));
            break;
         }
      } else {
         super.setValue(value);
      }

   }

   protected String resolvePath(String path) {
      if (this.propertyResolver == null) {
         this.propertyResolver = new StandardEnvironment();
      }

      return this.ignoreUnresolvablePlaceholders ? this.propertyResolver.resolvePlaceholders(path) : this.propertyResolver.resolveRequiredPlaceholders(path);
   }
}
