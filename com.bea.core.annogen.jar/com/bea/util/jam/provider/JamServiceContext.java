package com.bea.util.jam.provider;

import com.bea.util.jam.annotation.JavadocTagParser;
import com.bea.util.jam.visitor.MVisitor;
import java.io.File;
import java.io.IOException;

public interface JamServiceContext {
   ResourcePath getInputClasspath();

   ResourcePath getInputSourcepath();

   ResourcePath getToolClasspath();

   String getProperty(String var1);

   MVisitor getInitializer();

   ClassLoader[] getReflectionClassLoaders();

   File[] getSourceFiles() throws IOException;

   String[] getAllClassnames() throws IOException;

   JamLogger getLogger();

   JamClassBuilder getBaseBuilder();

   JavadocTagParser getTagParser();

   String getCharacterEncoding();

   boolean is14WarningsEnabled();
}
