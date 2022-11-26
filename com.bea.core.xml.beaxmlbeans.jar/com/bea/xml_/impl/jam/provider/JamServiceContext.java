package com.bea.xml_.impl.jam.provider;

import com.bea.xml_.impl.jam.annotation.JavadocTagParser;
import com.bea.xml_.impl.jam.visitor.MVisitor;
import java.io.File;
import java.io.IOException;

public interface JamServiceContext extends JamLogger {
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

   boolean is14WarningsEnabled();
}
