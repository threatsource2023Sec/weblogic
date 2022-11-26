package com.bea.core.repackaged.springframework.core.task;

@FunctionalInterface
public interface TaskDecorator {
   Runnable decorate(Runnable var1);
}
