package com.bea.staxb.buildtime.internal.mbean;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.logger.BindingLogger;
import com.bea.util.jam.JamClassLoader;
import com.bea.xml.SchemaTypeLoader;

public interface TypeMatcherContext {
   BindingLogger getLogger();

   BindingLoader getBaseBindingLoader();

   SchemaTypeLoader getBaseSchemaTypeLoader();

   JamClassLoader getBaseJavaTypeLoader();
}
