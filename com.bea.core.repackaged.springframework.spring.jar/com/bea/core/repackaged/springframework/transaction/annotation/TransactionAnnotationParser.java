package com.bea.core.repackaged.springframework.transaction.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.interceptor.TransactionAttribute;
import java.lang.reflect.AnnotatedElement;

public interface TransactionAnnotationParser {
   @Nullable
   TransactionAttribute parseTransactionAnnotation(AnnotatedElement var1);
}
