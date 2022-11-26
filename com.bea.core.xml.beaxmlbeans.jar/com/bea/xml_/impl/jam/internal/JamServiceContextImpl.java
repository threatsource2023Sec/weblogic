package com.bea.xml_.impl.jam.internal;

import com.bea.xml_.impl.jam.JamClassLoader;
import com.bea.xml_.impl.jam.JamServiceParams;
import com.bea.xml_.impl.jam.annotation.AnnotationProxy;
import com.bea.xml_.impl.jam.annotation.DefaultAnnotationProxy;
import com.bea.xml_.impl.jam.annotation.JavadocTagParser;
import com.bea.xml_.impl.jam.annotation.WhitespaceDelimitedTagParser;
import com.bea.xml_.impl.jam.internal.elements.ElementContext;
import com.bea.xml_.impl.jam.provider.CompositeJamClassBuilder;
import com.bea.xml_.impl.jam.provider.JamClassBuilder;
import com.bea.xml_.impl.jam.provider.JamLogger;
import com.bea.xml_.impl.jam.provider.JamServiceContext;
import com.bea.xml_.impl.jam.provider.ResourcePath;
import com.bea.xml_.impl.jam.visitor.CompositeMVisitor;
import com.bea.xml_.impl.jam.visitor.MVisitor;
import com.bea.xml_.impl.jam.visitor.PropertyInitializer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JamServiceContextImpl extends JamLoggerImpl implements JamServiceContext, JamServiceParams, ElementContext {
   private static final char INNER_CLASS_SEPARATOR = '$';
   private boolean m14WarningsEnabled = false;
   private Properties mProperties = null;
   private Map mSourceRoot2Scanner = null;
   private Map mClassRoot2Scanner = null;
   private List mClasspath = null;
   private List mSourcepath = null;
   private List mToolClasspath = null;
   private List mIncludeClasses = null;
   private List mExcludeClasses = null;
   private boolean mUseSystemClasspath = true;
   private JavadocTagParser mTagParser = null;
   private MVisitor mCommentInitializer = null;
   private MVisitor mPropertyInitializer = new PropertyInitializer();
   private List mOtherInitializers = null;
   private List mUnstructuredSourceFiles = null;
   private List mClassLoaders = null;
   private List mBaseBuilders = null;
   private JamClassLoader mLoader = null;
   private static final String PREFIX = "[JamServiceContextImpl] ";

   public void setClassLoader(JamClassLoader loader) {
      this.mLoader = loader;
   }

   public JamClassBuilder getBaseBuilder() {
      if (this.mBaseBuilders != null && this.mBaseBuilders.size() != 0) {
         if (this.mBaseBuilders.size() == 1) {
            return (JamClassBuilder)this.mBaseBuilders.get(0);
         } else {
            JamClassBuilder[] comp = new JamClassBuilder[this.mBaseBuilders.size()];
            this.mBaseBuilders.toArray(comp);
            return new CompositeJamClassBuilder(comp);
         }
      } else {
         return null;
      }
   }

   public JavadocTagParser getTagParser() {
      if (this.mTagParser == null) {
         this.mTagParser = new WhitespaceDelimitedTagParser();
         this.mTagParser.init(this);
      }

      return this.mTagParser;
   }

   public String[] getAllClassnames() throws IOException {
      Set all = new HashSet();
      if (this.mIncludeClasses != null) {
         all.addAll(this.mIncludeClasses);
      }

      Iterator i = this.getAllDirectoryScanners();

      while(i.hasNext()) {
         DirectoryScanner ds = (DirectoryScanner)i.next();
         String[] files = ds.getIncludedFiles();

         for(int j = 0; j < files.length; ++j) {
            if (files[j].indexOf(36) == -1) {
               all.add(filename2classname(files[j]));
            }
         }
      }

      if (this.mExcludeClasses != null) {
         all.removeAll(this.mExcludeClasses);
      }

      String[] out = new String[all.size()];
      all.toArray(out);
      return out;
   }

   public JamLogger getLogger() {
      return this;
   }

   public File[] getSourceFiles() throws IOException {
      Set set = new HashSet();
      if (this.mSourceRoot2Scanner != null) {
         Iterator i = this.mSourceRoot2Scanner.values().iterator();

         while(i.hasNext()) {
            DirectoryScanner ds = (DirectoryScanner)i.next();
            if (this.isVerbose(this)) {
               this.verbose("[JamServiceContextImpl]  checking scanner for dir" + ds.getRoot());
            }

            String[] files = ds.getIncludedFiles();

            for(int j = 0; j < files.length; ++j) {
               if (this.isVerbose(this)) {
                  this.verbose("[JamServiceContextImpl]  ...including a source file " + files[j]);
               }

               set.add(new File(ds.getRoot(), files[j]));
            }
         }
      }

      if (this.mUnstructuredSourceFiles != null) {
         if (this.isVerbose(this)) {
            this.verbose("[JamServiceContextImpl] adding " + this.mUnstructuredSourceFiles.size() + " other source files");
         }

         set.addAll(this.mUnstructuredSourceFiles);
      }

      File[] out = new File[set.size()];
      set.toArray(out);
      return out;
   }

   public File[] getUnstructuredSourceFiles() {
      if (this.mUnstructuredSourceFiles == null) {
         return null;
      } else {
         File[] out = new File[this.mUnstructuredSourceFiles.size()];
         this.mUnstructuredSourceFiles.toArray(out);
         return out;
      }
   }

   public ResourcePath getInputClasspath() {
      return createJPath(this.mClasspath);
   }

   public ResourcePath getInputSourcepath() {
      return createJPath(this.mSourcepath);
   }

   public ResourcePath getToolClasspath() {
      return createJPath(this.mToolClasspath);
   }

   public String getProperty(String name) {
      return this.mProperties == null ? null : this.mProperties.getProperty(name);
   }

   public MVisitor getInitializer() {
      List initers = new ArrayList();
      if (this.mCommentInitializer != null) {
         initers.add(this.mCommentInitializer);
      }

      if (this.mPropertyInitializer != null) {
         initers.add(this.mPropertyInitializer);
      }

      if (this.mOtherInitializers != null) {
         initers.addAll(this.mOtherInitializers);
      }

      MVisitor[] inits = new MVisitor[initers.size()];
      initers.toArray(inits);
      return new CompositeMVisitor(inits);
   }

   public void addClassBuilder(JamClassBuilder builder) {
      if (this.mBaseBuilders == null) {
         this.mBaseBuilders = new ArrayList();
      }

      this.mBaseBuilders.add(builder);
   }

   public void setCommentInitializer(MVisitor initializer) {
      this.mCommentInitializer = initializer;
   }

   public void setPropertyInitializer(MVisitor initializer) {
      this.mPropertyInitializer = initializer;
   }

   public void addInitializer(MVisitor initializer) {
      if (this.mOtherInitializers == null) {
         this.mOtherInitializers = new ArrayList();
      }

      this.mOtherInitializers.add(initializer);
   }

   public void setJavadocTagParser(JavadocTagParser tp) {
      this.mTagParser = tp;
      tp.init(this);
   }

   public void includeSourceFile(File file) {
      if (file == null) {
         throw new IllegalArgumentException("null file");
      } else {
         file = file.getAbsoluteFile();
         if (this.isVerbose(this)) {
            this.verbose("[JamServiceContextImpl] adding source ");
         }

         if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
         } else if (file.isDirectory()) {
            throw new IllegalArgumentException(file + " cannot be included as a source file because it is a directory.");
         } else {
            if (this.mUnstructuredSourceFiles == null) {
               this.mUnstructuredSourceFiles = new ArrayList();
            }

            this.mUnstructuredSourceFiles.add(file.getAbsoluteFile());
         }
      }
   }

   public void includeSourcePattern(File[] sourcepath, String pattern) {
      if (sourcepath == null) {
         throw new IllegalArgumentException("null sourcepath");
      } else if (sourcepath.length == 0) {
         throw new IllegalArgumentException("empty sourcepath");
      } else if (pattern == null) {
         throw new IllegalArgumentException("null pattern");
      } else {
         pattern = pattern.trim();
         if (pattern.length() == 0) {
            throw new IllegalArgumentException("empty pattern");
         } else {
            for(int i = 0; i < sourcepath.length; ++i) {
               if (this.isVerbose(this)) {
                  this.verbose("[JamServiceContextImpl] including '" + pattern + "' under " + sourcepath[i]);
               }

               this.addSourcepath(sourcepath[i]);
               this.getSourceScanner(sourcepath[i]).include(pattern);
            }

         }
      }
   }

   public void includeClassPattern(File[] classpath, String pattern) {
      if (classpath == null) {
         throw new IllegalArgumentException("null classpath");
      } else if (classpath.length == 0) {
         throw new IllegalArgumentException("empty classpath");
      } else if (pattern == null) {
         throw new IllegalArgumentException("null pattern");
      } else {
         pattern = pattern.trim();
         if (pattern.length() == 0) {
            throw new IllegalArgumentException("empty pattern");
         } else {
            for(int i = 0; i < classpath.length; ++i) {
               if (this.isVerbose(this)) {
                  this.verbose("[JamServiceContextImpl] including '" + pattern + "' under " + classpath[i]);
               }

               this.addClasspath(classpath[i]);
               this.getClassScanner(classpath[i]).include(pattern);
            }

         }
      }
   }

   public void excludeSourcePattern(File[] sourcepath, String pattern) {
      if (sourcepath == null) {
         throw new IllegalArgumentException("null sourcepath");
      } else if (sourcepath.length == 0) {
         throw new IllegalArgumentException("empty sourcepath");
      } else if (pattern == null) {
         throw new IllegalArgumentException("null pattern");
      } else {
         pattern = pattern.trim();
         if (pattern.length() == 0) {
            throw new IllegalArgumentException("empty pattern");
         } else {
            for(int i = 0; i < sourcepath.length; ++i) {
               if (this.isVerbose(this)) {
                  this.verbose("[JamServiceContextImpl] EXCLUDING '" + pattern + "' under " + sourcepath[i]);
               }

               this.addSourcepath(sourcepath[i]);
               this.getSourceScanner(sourcepath[i]).exclude(pattern);
            }

         }
      }
   }

   public void excludeClassPattern(File[] classpath, String pattern) {
      if (classpath == null) {
         throw new IllegalArgumentException("null classpath");
      } else if (classpath.length == 0) {
         throw new IllegalArgumentException("empty classpath");
      } else if (pattern == null) {
         throw new IllegalArgumentException("null pattern");
      } else {
         pattern = pattern.trim();
         if (pattern.length() == 0) {
            throw new IllegalArgumentException("empty pattern");
         } else {
            for(int i = 0; i < classpath.length; ++i) {
               if (this.isVerbose(this)) {
                  this.verbose("[JamServiceContextImpl] EXCLUDING '" + pattern + "' under " + classpath[i]);
               }

               this.addClasspath(classpath[i]);
               this.getClassScanner(classpath[i]).exclude(pattern);
            }

         }
      }
   }

   public void includeSourceFile(File[] sourcepath, File sourceFile) {
      File root = this.getPathRootForFile(sourcepath, sourceFile);
      this.includeSourcePattern(new File[]{root}, this.source2pattern(root, sourceFile));
   }

   public void excludeSourceFile(File[] sourcepath, File sourceFile) {
      File root = this.getPathRootForFile(sourcepath, sourceFile);
      this.excludeSourcePattern(new File[]{root}, this.source2pattern(root, sourceFile));
   }

   public void includeClassFile(File[] classpath, File classFile) {
      File root = this.getPathRootForFile(classpath, classFile);
      this.includeClassPattern(new File[]{root}, this.source2pattern(root, classFile));
   }

   public void excludeClassFile(File[] classpath, File classFile) {
      File root = this.getPathRootForFile(classpath, classFile);
      this.excludeClassPattern(new File[]{root}, this.source2pattern(root, classFile));
   }

   public void includeClass(String qualifiedClassname) {
      if (this.mIncludeClasses == null) {
         this.mIncludeClasses = new ArrayList();
      }

      this.mIncludeClasses.add(qualifiedClassname);
   }

   public void excludeClass(String qualifiedClassname) {
      if (this.mExcludeClasses == null) {
         this.mExcludeClasses = new ArrayList();
      }

      this.mExcludeClasses.add(qualifiedClassname);
   }

   public void addClasspath(File classpathElement) {
      if (this.mClasspath == null) {
         this.mClasspath = new ArrayList();
      } else if (this.mClasspath.contains(classpathElement)) {
         return;
      }

      this.mClasspath.add(classpathElement);
   }

   public void setLoggerWriter(PrintWriter out) {
      super.setOut(out);
   }

   public void setJamLogger(JamLogger logger) {
      throw new IllegalStateException("NYI");
   }

   public void addSourcepath(File sourcepathElement) {
      if (this.mSourcepath == null) {
         this.mSourcepath = new ArrayList();
      } else if (this.mSourcepath.contains(sourcepathElement)) {
         return;
      }

      this.mSourcepath.add(sourcepathElement);
   }

   public void addToolClasspath(File classpathElement) {
      if (this.mToolClasspath == null) {
         this.mToolClasspath = new ArrayList();
      } else if (this.mToolClasspath.contains(classpathElement)) {
         return;
      }

      this.mToolClasspath.add(classpathElement);
   }

   public void setProperty(String name, String value) {
      if (this.mProperties == null) {
         this.mProperties = new Properties();
      }

      this.mProperties.setProperty(name, value);
   }

   public void set14WarningsEnabled(boolean b) {
      this.m14WarningsEnabled = b;
   }

   public void setParentClassLoader(JamClassLoader loader) {
      throw new IllegalStateException("NYI");
   }

   public void setUseSystemClasspath(boolean use) {
      this.mUseSystemClasspath = use;
   }

   public void addClassLoader(ClassLoader cl) {
      if (this.mClassLoaders == null) {
         this.mClassLoaders = new ArrayList();
      }

      this.mClassLoaders.add(cl);
   }

   public ClassLoader[] getReflectionClassLoaders() {
      if (this.mClassLoaders == null) {
         return this.mUseSystemClasspath ? new ClassLoader[]{ClassLoader.getSystemClassLoader()} : new ClassLoader[0];
      } else {
         ClassLoader[] out = new ClassLoader[this.mClassLoaders.size() + (this.mUseSystemClasspath ? 1 : 0)];

         for(int i = 0; i < this.mClassLoaders.size(); ++i) {
            out[i] = (ClassLoader)this.mClassLoaders.get(i);
         }

         if (this.mUseSystemClasspath) {
            out[out.length - 1] = ClassLoader.getSystemClassLoader();
         }

         return out;
      }
   }

   public boolean is14WarningsEnabled() {
      return this.m14WarningsEnabled;
   }

   public JamClassLoader getClassLoader() {
      return this.mLoader;
   }

   public AnnotationProxy createAnnotationProxy(String jsr175typename) {
      AnnotationProxy out = new DefaultAnnotationProxy();
      out.init(this);
      return out;
   }

   private File getPathRootForFile(File[] sourcepath, File sourceFile) {
      if (sourcepath == null) {
         throw new IllegalArgumentException("null sourcepath");
      } else if (sourcepath.length == 0) {
         throw new IllegalArgumentException("empty sourcepath");
      } else if (sourceFile == null) {
         throw new IllegalArgumentException("null sourceFile");
      } else {
         sourceFile = sourceFile.getAbsoluteFile();
         if (this.isVerbose(this)) {
            this.verbose("[JamServiceContextImpl] Getting root for " + sourceFile + "...");
         }

         for(int i = 0; i < sourcepath.length; ++i) {
            if (this.isVerbose(this)) {
               this.verbose("[JamServiceContextImpl] ...looking in " + sourcepath[i]);
            }

            if (this.isContainingDir(sourcepath[i].getAbsoluteFile(), sourceFile)) {
               if (this.isVerbose(this)) {
                  this.verbose("[JamServiceContextImpl] ...found it!");
               }

               return sourcepath[i].getAbsoluteFile();
            }
         }

         throw new IllegalArgumentException(sourceFile + " is not in the given path.");
      }
   }

   private boolean isContainingDir(File dir, File file) {
      if (this.isVerbose(this)) {
         this.verbose("[JamServiceContextImpl] ... ...isContainingDir " + dir + "  " + file);
      }

      if (file == null) {
         return false;
      } else if (dir.equals(file)) {
         if (this.isVerbose(this)) {
            this.verbose("[JamServiceContextImpl] ... ...yes!");
         }

         return true;
      } else {
         return this.isContainingDir(dir, file.getParentFile());
      }
   }

   private String source2pattern(File root, File sourceFile) {
      if (this.isVerbose(this)) {
         this.verbose("[JamServiceContextImpl] source2pattern " + root + "  " + sourceFile);
      }

      String r = root.getAbsolutePath();
      String s = sourceFile.getAbsolutePath();
      if (this.isVerbose(this)) {
         this.verbose("[JamServiceContextImpl] source2pattern returning " + s.substring(r.length() + 1));
      }

      return s.substring(r.length() + 1);
   }

   private static String filename2classname(String filename) {
      int extDot = filename.lastIndexOf(46);
      if (extDot != -1) {
         filename = filename.substring(0, extDot);
      }

      filename = filename.replace('/', '.');
      filename = filename.replace('\\', '.');
      return filename;
   }

   private Iterator getAllDirectoryScanners() {
      Collection out = new ArrayList();
      if (this.mSourceRoot2Scanner != null) {
         out.addAll(this.mSourceRoot2Scanner.values());
      }

      if (this.mClassRoot2Scanner != null) {
         out.addAll(this.mClassRoot2Scanner.values());
      }

      return out.iterator();
   }

   private static ResourcePath createJPath(Collection filelist) {
      if (filelist != null && filelist.size() != 0) {
         File[] files = new File[filelist.size()];
         filelist.toArray(files);
         return ResourcePath.forFiles(files);
      } else {
         return null;
      }
   }

   private DirectoryScanner getSourceScanner(File srcRoot) {
      if (this.mSourceRoot2Scanner == null) {
         this.mSourceRoot2Scanner = new HashMap();
      }

      DirectoryScanner out = (DirectoryScanner)this.mSourceRoot2Scanner.get(srcRoot);
      if (out == null) {
         this.mSourceRoot2Scanner.put(srcRoot, out = new DirectoryScanner(srcRoot, this));
      }

      return out;
   }

   private DirectoryScanner getClassScanner(File clsRoot) {
      if (this.mClassRoot2Scanner == null) {
         this.mClassRoot2Scanner = new HashMap();
      }

      DirectoryScanner out = (DirectoryScanner)this.mClassRoot2Scanner.get(clsRoot);
      if (out == null) {
         this.mClassRoot2Scanner.put(clsRoot, out = new DirectoryScanner(clsRoot, this));
      }

      return out;
   }
}
