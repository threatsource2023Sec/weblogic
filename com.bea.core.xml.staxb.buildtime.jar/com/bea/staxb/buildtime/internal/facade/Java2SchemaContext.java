package com.bea.staxb.buildtime.internal.facade;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.logger.BindingLogger;
import com.bea.util.jam.JClass;

public interface Java2SchemaContext {
   BindingLogger getLogger();

   BindingLoader getBindingLoader();

   BindingType findOrCreateBindingTypeFor(JClass var1);

   void checkNsForImport(String var1, String var2);

   boolean isElementFormDefaultQualified();
}
