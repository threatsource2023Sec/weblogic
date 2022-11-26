package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {
   void setApplicationContext(ApplicationContext var1) throws BeansException;
}
