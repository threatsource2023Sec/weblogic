package com.bea.xml_.impl.jam;

import com.bea.xml_.impl.jam.annotation.JavadocTagParser;
import com.bea.xml_.impl.jam.provider.JamClassBuilder;
import com.bea.xml_.impl.jam.provider.JamLogger;
import com.bea.xml_.impl.jam.visitor.MVisitor;
import java.io.File;
import java.io.PrintWriter;

public interface JamServiceParams {
   void includeSourceFile(File var1);

   void includeSourcePattern(File[] var1, String var2);

   void excludeSourcePattern(File[] var1, String var2);

   void includeClassPattern(File[] var1, String var2);

   void excludeClassPattern(File[] var1, String var2);

   void includeSourceFile(File[] var1, File var2);

   void excludeSourceFile(File[] var1, File var2);

   void includeClassFile(File[] var1, File var2);

   void excludeClassFile(File[] var1, File var2);

   void includeClass(String var1);

   void excludeClass(String var1);

   void addSourcepath(File var1);

   void addClasspath(File var1);

   void setLoggerWriter(PrintWriter var1);

   void setJamLogger(JamLogger var1);

   void setVerbose(Class var1);

   void setShowWarnings(boolean var1);

   void setParentClassLoader(JamClassLoader var1);

   void addToolClasspath(File var1);

   void setPropertyInitializer(MVisitor var1);

   void addInitializer(MVisitor var1);

   void setJavadocTagParser(JavadocTagParser var1);

   void setUseSystemClasspath(boolean var1);

   void addClassBuilder(JamClassBuilder var1);

   void addClassLoader(ClassLoader var1);

   void setProperty(String var1, String var2);

   void set14WarningsEnabled(boolean var1);

   /** @deprecated */
   void setVerbose(boolean var1);
}
