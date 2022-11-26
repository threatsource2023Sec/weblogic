package com.bea.security.providers.xacml;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.security.auth.Subject;

public class SubjectConverterRegistry {
   private Map registry = new ConcurrentHashMap();

   public void register(SubjectConverterFactory converterFactory, Class subjectClass) {
      this.registry.put(subjectClass, converterFactory);
   }

   public SubjectConverter getConverter(Subject s) {
      SubjectConverterFactory rcf = (SubjectConverterFactory)this.registry.get(s.getClass());
      return rcf != null ? rcf.getConverter(s) : null;
   }
}
