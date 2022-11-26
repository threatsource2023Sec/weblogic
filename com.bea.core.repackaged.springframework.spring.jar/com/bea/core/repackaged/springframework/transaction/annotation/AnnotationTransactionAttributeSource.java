package com.bea.core.repackaged.springframework.transaction.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.interceptor.AbstractFallbackTransactionAttributeSource;
import com.bea.core.repackaged.springframework.transaction.interceptor.TransactionAttribute;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class AnnotationTransactionAttributeSource extends AbstractFallbackTransactionAttributeSource implements Serializable {
   private static final boolean jta12Present;
   private static final boolean ejb3Present;
   private final boolean publicMethodsOnly;
   private final Set annotationParsers;

   public AnnotationTransactionAttributeSource() {
      this(true);
   }

   public AnnotationTransactionAttributeSource(boolean publicMethodsOnly) {
      this.publicMethodsOnly = publicMethodsOnly;
      if (!jta12Present && !ejb3Present) {
         this.annotationParsers = Collections.singleton(new SpringTransactionAnnotationParser());
      } else {
         this.annotationParsers = new LinkedHashSet(4);
         this.annotationParsers.add(new SpringTransactionAnnotationParser());
         if (jta12Present) {
            this.annotationParsers.add(new JtaTransactionAnnotationParser());
         }

         if (ejb3Present) {
            this.annotationParsers.add(new Ejb3TransactionAnnotationParser());
         }
      }

   }

   public AnnotationTransactionAttributeSource(TransactionAnnotationParser annotationParser) {
      this.publicMethodsOnly = true;
      Assert.notNull(annotationParser, (String)"TransactionAnnotationParser must not be null");
      this.annotationParsers = Collections.singleton(annotationParser);
   }

   public AnnotationTransactionAttributeSource(TransactionAnnotationParser... annotationParsers) {
      this.publicMethodsOnly = true;
      Assert.notEmpty((Object[])annotationParsers, (String)"At least one TransactionAnnotationParser needs to be specified");
      this.annotationParsers = new LinkedHashSet(Arrays.asList(annotationParsers));
   }

   public AnnotationTransactionAttributeSource(Set annotationParsers) {
      this.publicMethodsOnly = true;
      Assert.notEmpty((Collection)annotationParsers, (String)"At least one TransactionAnnotationParser needs to be specified");
      this.annotationParsers = annotationParsers;
   }

   @Nullable
   protected TransactionAttribute findTransactionAttribute(Class clazz) {
      return this.determineTransactionAttribute(clazz);
   }

   @Nullable
   protected TransactionAttribute findTransactionAttribute(Method method) {
      return this.determineTransactionAttribute(method);
   }

   @Nullable
   protected TransactionAttribute determineTransactionAttribute(AnnotatedElement element) {
      Iterator var2 = this.annotationParsers.iterator();

      TransactionAttribute attr;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         TransactionAnnotationParser annotationParser = (TransactionAnnotationParser)var2.next();
         attr = annotationParser.parseTransactionAnnotation(element);
      } while(attr == null);

      return attr;
   }

   protected boolean allowPublicMethodsOnly() {
      return this.publicMethodsOnly;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AnnotationTransactionAttributeSource)) {
         return false;
      } else {
         AnnotationTransactionAttributeSource otherTas = (AnnotationTransactionAttributeSource)other;
         return this.annotationParsers.equals(otherTas.annotationParsers) && this.publicMethodsOnly == otherTas.publicMethodsOnly;
      }
   }

   public int hashCode() {
      return this.annotationParsers.hashCode();
   }

   static {
      ClassLoader classLoader = AnnotationTransactionAttributeSource.class.getClassLoader();
      jta12Present = ClassUtils.isPresent("javax.transaction.Transactional", classLoader);
      ejb3Present = ClassUtils.isPresent("javax.ejb.TransactionAttribute", classLoader);
   }
}
