package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.transaction.PlatformTransactionManager;

public interface ResourceTransactionManager extends PlatformTransactionManager {
   Object getResourceFactory();
}
