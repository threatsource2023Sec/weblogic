package com.bea.core.repackaged.springframework.context.index;

import com.bea.core.repackaged.springframework.util.AntPathMatcher;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.LinkedMultiValueMap;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class CandidateComponentsIndex {
   private static final AntPathMatcher pathMatcher = new AntPathMatcher(".");
   private final MultiValueMap index;

   CandidateComponentsIndex(List content) {
      this.index = parseIndex(content);
   }

   public Set getCandidateTypes(String basePackage, String stereotype) {
      List candidates = (List)this.index.get(stereotype);
      return candidates != null ? (Set)candidates.parallelStream().filter((t) -> {
         return t.match(basePackage);
      }).map((t) -> {
         return t.type;
      }).collect(Collectors.toSet()) : Collections.emptySet();
   }

   private static MultiValueMap parseIndex(List content) {
      MultiValueMap index = new LinkedMultiValueMap();
      Iterator var2 = content.iterator();

      while(var2.hasNext()) {
         Properties entry = (Properties)var2.next();
         entry.forEach((type, values) -> {
            String[] stereotypes = ((String)values).split(",");
            String[] var4 = stereotypes;
            int var5 = stereotypes.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String stereotype = var4[var6];
               index.add(stereotype, new Entry((String)type));
            }

         });
      }

      return index;
   }

   private static class Entry {
      private final String type;
      private final String packageName;

      Entry(String type) {
         this.type = type;
         this.packageName = ClassUtils.getPackageName(type);
      }

      public boolean match(String basePackage) {
         return CandidateComponentsIndex.pathMatcher.isPattern(basePackage) ? CandidateComponentsIndex.pathMatcher.match(basePackage, this.packageName) : this.type.startsWith(basePackage);
      }
   }
}
