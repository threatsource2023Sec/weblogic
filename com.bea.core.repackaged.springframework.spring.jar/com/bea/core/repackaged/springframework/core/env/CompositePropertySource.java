package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CompositePropertySource extends EnumerablePropertySource {
   private final Set propertySources = new LinkedHashSet();

   public CompositePropertySource(String name) {
      super(name);
   }

   @Nullable
   public Object getProperty(String name) {
      Iterator var2 = this.propertySources.iterator();

      Object candidate;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         PropertySource propertySource = (PropertySource)var2.next();
         candidate = propertySource.getProperty(name);
      } while(candidate == null);

      return candidate;
   }

   public boolean containsProperty(String name) {
      Iterator var2 = this.propertySources.iterator();

      PropertySource propertySource;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         propertySource = (PropertySource)var2.next();
      } while(!propertySource.containsProperty(name));

      return true;
   }

   public String[] getPropertyNames() {
      Set names = new LinkedHashSet();
      Iterator var2 = this.propertySources.iterator();

      while(var2.hasNext()) {
         PropertySource propertySource = (PropertySource)var2.next();
         if (!(propertySource instanceof EnumerablePropertySource)) {
            throw new IllegalStateException("Failed to enumerate property names due to non-enumerable property source: " + propertySource);
         }

         names.addAll(Arrays.asList(((EnumerablePropertySource)propertySource).getPropertyNames()));
      }

      return StringUtils.toStringArray((Collection)names);
   }

   public void addPropertySource(PropertySource propertySource) {
      this.propertySources.add(propertySource);
   }

   public void addFirstPropertySource(PropertySource propertySource) {
      List existing = new ArrayList(this.propertySources);
      this.propertySources.clear();
      this.propertySources.add(propertySource);
      this.propertySources.addAll(existing);
   }

   public Collection getPropertySources() {
      return this.propertySources;
   }

   public String toString() {
      return this.getClass().getSimpleName() + " {name='" + this.name + "', propertySources=" + this.propertySources + "}";
   }
}
