package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.BeansException;

@FunctionalInterface
public interface ObjectFactory {
   Object getObject() throws BeansException;
}
