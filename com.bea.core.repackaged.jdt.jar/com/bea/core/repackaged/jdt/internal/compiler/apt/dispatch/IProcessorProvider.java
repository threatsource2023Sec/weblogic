package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import java.util.List;
import javax.annotation.processing.Processor;

public interface IProcessorProvider {
   ProcessorInfo discoverNextProcessor();

   List getDiscoveredProcessors();

   void reportProcessorException(Processor var1, Exception var2);
}
