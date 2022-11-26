package com.bea.util.jam.internal.javadoc;

import com.bea.util.jam.JImport;
import com.bea.util.jam.annotation.JavadocTagParser;
import com.bea.util.jam.internal.JamServiceContextImpl;
import com.bea.util.jam.internal.elements.ClassImpl;
import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.internal.elements.ImportImpl;
import com.bea.util.jam.internal.elements.PrimitiveClassImpl;
import com.bea.util.jam.mutable.MAnnotatedElement;
import com.bea.util.jam.mutable.MClass;
import com.bea.util.jam.mutable.MElement;
import com.bea.util.jam.mutable.MField;
import com.bea.util.jam.mutable.MInvokable;
import com.bea.util.jam.mutable.MMethod;
import com.bea.util.jam.mutable.MParameter;
import com.bea.util.jam.mutable.MSourcePosition;
import com.bea.util.jam.provider.JamClassBuilder;
import com.bea.util.jam.provider.JamClassPopulator;
import com.bea.util.jam.provider.JamServiceContext;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaFileObject.Kind;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.util.JavaEnvUtils;

public class JavadocClassBuilder extends JamClassBuilder implements JamClassPopulator {
   public static final String ARGS_PROPERTY = "javadoc.args";
   public static final String PARSETAGS_PROPERTY = "javadoc.parsetags";
   private static DiagnosticListener diagListener = null;
   private static JavaCompiler compiler = null;
   private static StandardJavaFileManager fileManager = null;
   private ElementContext eContext = null;
   private RootDoc mRootDoc = null;
   private JavadocTigerDelegate mTigerDelegate = null;
   private JavadocTagParser mTagParser = null;
   private boolean mParseTags = true;

   public void init(ElementContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException("null context");
      } else {
         super.init(ctx);
         this.eContext = ctx;
         this.getLogger().verbose((String)"init()", this);
         this.initDelegate(ctx);
         this.initJavadoc((JamServiceContext)ctx);
      }
   }

   public MClass build(String packageName, String className) {
      return this.build((Javac)null, packageName, className);
   }

   public MClass build(Javac javacTask, String packageName, String className) {
      this.assertInitialized();
      if (this.getLogger().isVerbose((Object)this)) {
         this.getLogger().verbose("trying to build '" + packageName + "' '" + className + "'");
      }

      String fqClassNameToProcess = packageName.trim().length() > 0 ? packageName + '.' + className : className;
      ClassDoc classDocToProcess = this.mRootDoc.classNamed(fqClassNameToProcess);
      if (classDocToProcess == null) {
         if (this.getLogger().isVerbose((Object)this)) {
            this.getLogger().verbose("no ClassDoc for " + fqClassNameToProcess);
         }

         return null;
      } else {
         List importSpecs = null;
         File[] javaFiles = null;
         List importsFromJava = null;
         JImport[] imports = null;

         try {
            javaFiles = ((JamServiceContext)this.eContext).getSourceFiles();
            importsFromJava = this.getImports(javacTask, packageName, className, javaFiles);
         } catch (IOException var16) {
            throw new RuntimeException(var16);
         }

         int numImports = importsFromJava != null ? importsFromJava.size() : 0;
         if (numImports > 0) {
            imports = new JImport[numImports];
            int importIndex = 0;
            importSpecs = new ArrayList();

            String qName;
            boolean isStatic;
            for(Iterator var12 = importsFromJava.iterator(); var12.hasNext(); imports[importIndex++] = new ImportImpl(this.eContext, qName, isStatic)) {
               ImportTree imp = (ImportTree)var12.next();
               qName = imp.getQualifiedIdentifier().toString();
               isStatic = imp.isStatic();
               importSpecs.add(qName);
            }
         }

         String[] importSpecsArray = null;
         if (importSpecs != null) {
            importSpecsArray = new String[importSpecs.size()];
            importSpecs.toArray(importSpecsArray);
         }

         MClass out = this.createClassToBuild(packageName, className, importSpecsArray, this);
         ((ClassImpl)out).setImports(imports);
         out.setArtifact(classDocToProcess);
         out.setSourceAvailable(true);
         return out;
      }
   }

   private List getImports(String packageName, String simpleClassName, File[] javaFilesToProcess) throws IOException {
      return this.getImports((Javac)null, packageName, simpleClassName, javaFilesToProcess);
   }

   private List getImports(Javac javacTask, String packageName, String simpleClassName, File[] javaFilesToProcess) throws IOException {
      if (diagListener == null) {
         diagListener = new DiagnosticCollector();
         compiler = ToolProvider.getSystemJavaCompiler();
         fileManager = compiler.getStandardFileManager(diagListener, (Locale)null, (Charset)null);
      }

      List javaFileList = Arrays.asList(javaFilesToProcess);
      Iterable compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFileList);
      List filteredCUs = new ArrayList();
      Iterator classes = compilationUnits.iterator();

      while(classes.hasNext()) {
         JavaFileObject cu = (JavaFileObject)classes.next();
         if (cu.isNameCompatible(simpleClassName, Kind.SOURCE)) {
            filteredCUs.add(cu);
         }
      }

      if (filteredCUs.isEmpty()) {
         return null;
      } else {
         classes = null;
         String javaVersion = System.getProperty("java.version");
         Iterable asts = null;
         Iterable javacArgs = null;
         if (javacTask != null) {
            javacArgs = this.getJavacArgs(javacTask);
         }

         JavacTask javac;
         if (!javaVersion.startsWith("1.")) {
            javac = (JavacTask)compiler.getTask((Writer)null, fileManager, diagListener, javacArgs, classes, filteredCUs);
         } else {
            JavacTool tool = JavacTool.create();
            javac = tool.getTask((Writer)null, fileManager, diagListener, javacArgs, classes, filteredCUs);
         }

         asts = javac.parse();
         Iterator var18 = asts.iterator();

         CompilationUnitTree ast;
         String astPkgName;
         do {
            if (!var18.hasNext()) {
               return null;
            }

            ast = (CompilationUnitTree)var18.next();
            ExpressionTree astPkgNameExpr = ast.getPackageName();
            astPkgName = astPkgNameExpr != null ? astPkgNameExpr.toString() : "";
         } while(!astPkgName.equals(packageName));

         return ast.getImports();
      }
   }

   private File[] getSourceFiles(JamServiceContext serviceContext) {
      File[] files = null;

      try {
         files = serviceContext.getSourceFiles();
      } catch (IOException var4) {
         this.getLogger().error((Throwable)var4);
      }

      if (files != null && files.length != 0) {
         return files;
      } else {
         throw new IllegalArgumentException("No source files in context.");
      }
   }

   public void populate(MClass dest) {
      if (dest == null) {
         throw new IllegalArgumentException("null dest");
      } else {
         this.assertInitialized();
         ClassDoc src = (ClassDoc)dest.getArtifact();
         if (src == null) {
            throw new IllegalStateException("null artifact");
         } else {
            dest.setModifiers(src.modifierSpecifier());
            dest.setIsInterface(src.isInterface());
            if (this.mTigerDelegate != null) {
               dest.setIsEnumType(this.mTigerDelegate.isEnum(src));
            }

            ClassDoc s = src.superclass();
            if (s != null) {
               dest.setSuperclass(getFdFor(s));
            }

            ClassDoc[] ints = src.interfaces();

            for(int i = 0; i < ints.length; ++i) {
               dest.addInterface(getFdFor(ints[i]));
            }

            FieldDoc[] fields = src.fields();

            for(int i = 0; i < fields.length; ++i) {
               this.populate(dest.addNewField(), fields[i]);
            }

            ConstructorDoc[] ctors = src.constructors();

            for(int i = 0; i < ctors.length; ++i) {
               this.populate((MInvokable)dest.addNewConstructor(), (ExecutableMemberDoc)ctors[i]);
            }

            MethodDoc[] methods = src.methods();

            for(int i = 0; i < methods.length; ++i) {
               this.populate(dest.addNewMethod(), methods[i]);
            }

            if (this.mTigerDelegate != null) {
               this.mTigerDelegate.populateAnnotationTypeIfNecessary(src, dest, this);
            }

            this.addAnnotations(dest, src);
            addSourcePosition(dest, (Doc)src);
            ClassDoc[] inners = src.innerClasses();
            if (inners != null) {
               for(int i = 0; i < inners.length; ++i) {
                  MClass inner = dest.addNewInnerClass(inners[i].typeName());
                  inner.setArtifact(inners[i]);
                  this.populate(inner);
               }
            }

         }
      }
   }

   public MMethod addMethod(MClass dest, MethodDoc doc) {
      MMethod out = dest.addNewMethod();
      this.populate(out, doc);
      return out;
   }

   private void initDelegate(ElementContext ctx) {
      this.mTigerDelegate = JavadocTigerDelegate.create(ctx);
   }

   private void initJavadoc(JamServiceContext serviceContext) {
      this.mTagParser = serviceContext.getTagParser();
      String pct = serviceContext.getProperty("javadoc.parsetags");
      if (pct != null) {
         this.mParseTags = Boolean.valueOf(pct);
         this.getLogger().verbose((String)("mParseTags=" + this.mParseTags), this);
      }

      File[] files = this.getSourceFiles(serviceContext);
      String sourcePath = serviceContext.getInputSourcepath() == null ? null : serviceContext.getInputSourcepath().toString();
      String classPath = serviceContext.getInputClasspath() == null ? null : serviceContext.getInputClasspath().toString();
      if (this.getLogger().isVerbose((Object)this)) {
         this.getLogger().verbose("sourcePath =" + sourcePath);
         this.getLogger().verbose("classPath =" + classPath);

         for(int i = 0; i < files.length; ++i) {
            this.getLogger().verbose("including '" + files[i] + "'");
         }
      }

      JavadocRunner jdr = JavadocRunner.newInstance();

      try {
         PrintWriter out = null;
         if (this.getLogger().isVerbose((Object)this)) {
            out = new PrintWriter(System.out);
         }

         this.mRootDoc = jdr.run(files, out, sourcePath, classPath, serviceContext.getCharacterEncoding(), this.getJavadocArgs(serviceContext), this.getLogger());
         if (this.mRootDoc == null) {
            this.getLogger().error("Javadoc returned a null root");
         } else {
            if (this.getLogger().isVerbose((Object)this)) {
               this.getLogger().verbose(" received " + this.mRootDoc.classes().length + " ClassDocs from javadoc: ");
            }

            ClassDoc[] classes = this.mRootDoc.classes();

            for(int i = 0; i < classes.length; ++i) {
               if (classes[i].containingClass() == null) {
                  if (this.getLogger().isVerbose((Object)this)) {
                     this.getLogger().verbose("..." + classes[i].qualifiedName());
                  }

                  ((JamServiceContextImpl)serviceContext).includeClass(getFdFor(classes[i]));
               }
            }
         }
      } catch (FileNotFoundException var10) {
         this.getLogger().error((Throwable)var10);
      } catch (IOException var11) {
         this.getLogger().error((Throwable)var11);
      }

   }

   private void populate(MField dest, FieldDoc src) {
      dest.setArtifact(src);
      dest.setSimpleName(src.name());
      dest.setType(getFdFor(src.type()));
      dest.setModifiers(src.modifierSpecifier());
      dest.setConstantValue(src.constantValue());
      dest.setConstantValueExpression(src.constantValueExpression());
      this.addAnnotations(dest, src);
      addSourcePosition(dest, (Doc)src);
   }

   private void populate(MMethod dest, MethodDoc src) {
      if (dest == null) {
         throw new IllegalArgumentException("null dest");
      } else if (src == null) {
         throw new IllegalArgumentException("null src");
      } else {
         this.populate((MInvokable)dest, (ExecutableMemberDoc)src);
         dest.setReturnType(getFdFor(src.returnType()));
      }
   }

   private void populate(MInvokable dest, ExecutableMemberDoc src) {
      if (dest == null) {
         throw new IllegalArgumentException("null dest");
      } else if (src == null) {
         throw new IllegalArgumentException("null src");
      } else {
         dest.setArtifact(src);
         dest.setSimpleName(src.name());
         dest.setModifiers(src.modifierSpecifier());
         ClassDoc[] exceptions = src.thrownExceptions();

         for(int i = 0; i < exceptions.length; ++i) {
            dest.addException(getFdFor(exceptions[i]));
         }

         Parameter[] params = src.parameters();
         int i;
         if (params != null && params.length >= 2 && params[0].name().equals(params[1].name())) {
            for(i = 0; i < params.length; ++i) {
               this.populate(dest.addNewParameter(), src, params[i], "param" + i);
            }
         } else {
            for(i = 0; i < params.length; ++i) {
               this.populate(dest.addNewParameter(), src, params[i], params[i].name());
            }
         }

         this.addAnnotations(dest, src);
         addSourcePosition(dest, (Doc)src);
      }
   }

   private void populate(MParameter dest, ExecutableMemberDoc method, Parameter src, String paramNameToUse) {
      dest.setArtifact(src);
      dest.setSimpleName(paramNameToUse);
      dest.setType(getFdFor(src.type()));
      if (this.mTigerDelegate != null) {
         this.mTigerDelegate.extractAnnotations(dest, method, src);
      }

   }

   private String[] getJavadocArgs(JamServiceContext ctx) {
      String prop = ctx.getProperty("javadoc.args");
      if (prop == null) {
         return null;
      } else {
         StringTokenizer t = new StringTokenizer(prop);
         String[] out = new String[t.countTokens()];

         for(int i = 0; t.hasMoreTokens(); out[i++] = t.nextToken()) {
         }

         return out;
      }
   }

   private void addAnnotations(MAnnotatedElement dest, ProgramElementDoc src) {
      String comments = src.commentText();
      if (comments != null) {
         dest.createComment().setText(comments);
      }

      Tag[] tags = src.tags();

      for(int i = 0; i < tags.length; ++i) {
         if (this.getLogger().isVerbose((Object)this)) {
            this.getLogger().verbose("...'" + tags[i].name() + "' ' " + tags[i].text());
         }

         this.mTagParser.parse(dest, tags[i]);
      }

      if (this.mTigerDelegate != null) {
         this.mTigerDelegate.extractAnnotations(dest, src);
      }

   }

   private static String outerSimpleName(ClassDoc outer) {
      String simpleName = outer.name();
      simpleName = simpleName.substring(simpleName.lastIndexOf(46) + 1);
      ClassDoc outer1 = outer.containingClass();
      return outer1 == null ? simpleName : outerSimpleName(outer1) + '.' + simpleName;
   }

   private static String getOuterQualifiedName(ClassDoc outer, Type t) {
      String osn = outerSimpleName(outer);

      String tmp;
      for(tmp = osn; osn.indexOf(".") >= 0; osn = osn.replace('.', '$')) {
      }

      int iPos = t.qualifiedTypeName().indexOf(tmp);
      if (iPos <= 0) {
         return null;
      } else {
         StringBuffer qn = new StringBuffer();
         qn.append(t.qualifiedTypeName().substring(0, iPos)).append(osn).append("$").append(t.simpleTypeName());
         return qn.toString();
      }
   }

   public static String getFdFor(Type t) {
      if (t == null) {
         throw new IllegalArgumentException("null type");
      } else {
         String dim = t.dimension();
         if (dim != null && dim.length() != 0) {
            StringWriter out = new StringWriter();
            int i = 0;

            for(int iL = dim.length() / 2; i < iL; ++i) {
               out.write("[");
            }

            String primFd = PrimitiveClassImpl.getPrimitiveClassForName(t.qualifiedTypeName());
            if (primFd != null) {
               out.write(primFd);
            } else {
               out.write("L");
               if (t.asClassDoc() != null) {
                  ClassDoc cd = t.asClassDoc();
                  ClassDoc outer = cd.containingClass();
                  String qualifiedName;
                  if (outer == null) {
                     qualifiedName = cd.qualifiedName();
                  } else {
                     qualifiedName = getOuterQualifiedName(outer, t);
                     if (qualifiedName == null) {
                        String simpleName = cd.name();
                        simpleName = simpleName.substring(simpleName.lastIndexOf(46) + 1);
                        qualifiedName = outer.qualifiedName() + '$' + simpleName;
                     }
                  }

                  out.write(qualifiedName);
               } else {
                  out.write(t.qualifiedTypeName());
               }

               out.write(";");
            }

            return out.toString();
         } else {
            ClassDoc cd = t.asClassDoc();
            if (cd != null) {
               ClassDoc outer = cd.containingClass();
               if (outer == null) {
                  return cd.qualifiedName();
               } else {
                  String qname = getOuterQualifiedName(outer, t);
                  if (qname != null) {
                     return qname;
                  } else {
                     String simpleName = cd.name();
                     simpleName = simpleName.substring(simpleName.lastIndexOf(46) + 1);
                     return outer.qualifiedName() + '$' + simpleName;
                  }
               }
            } else {
               return t.qualifiedTypeName();
            }
         }
      }
   }

   public static void addSourcePosition(MElement dest, Doc src) {
      SourcePosition pos = src.position();
      if (pos != null) {
         addSourcePosition(dest, pos);
      }

   }

   public static void addSourcePosition(MElement dest, SourcePosition pos) {
      MSourcePosition sp = dest.createSourcePosition();
      sp.setColumn(pos.column());
      sp.setLine(pos.line());
      File f = pos.file();
      if (f != null) {
         sp.setSourceURI(f.toURI());
      }

   }

   private boolean assumeJavaXY(Javac attributes, String javacXY, String javaEnvVersionXY) {
      return javacXY.equals(attributes.getCompilerVersion()) || JavaEnvUtils.isJavaVersion(javaEnvVersionXY) && ("classic".equals(attributes.getCompilerVersion()) || "modern".equals(attributes.getCompilerVersion()) || "extJavac".equals(attributes.getCompilerVersion()));
   }

   private Iterable getJavacArgs(Javac javacTask) {
      Iterable args = null;
      if (javacTask != null) {
         String compilerImpl = javacTask.getCompiler();
         return Arrays.asList(this.getJavacCommandlineSwitches(javacTask).getCommandline());
      } else {
         return null;
      }
   }

   protected Commandline getJavacCommandlineSwitches(Javac attributes) {
      Commandline cmd = new Commandline();
      this.setupJavacCommandlineSwitches(cmd, true, attributes);
      return cmd;
   }

   protected Commandline setupJavacCommandlineSwitches(Commandline cmd, boolean useDebugLevel, Javac attributes) {
      String memoryParameterPrefix = "-J-X";
      if (attributes.getMemoryInitialSize() != null) {
         if (!attributes.isForkedJavac()) {
            attributes.log("Since fork is false, ignoring memoryInitialSize setting.", 1);
         } else {
            cmd.createArgument().setValue("-J-Xms" + attributes.getMemoryInitialSize());
         }
      }

      if (attributes.getMemoryMaximumSize() != null) {
         if (!attributes.isForkedJavac()) {
            attributes.log("Since fork is false, ignoring memoryMaximumSize setting.", 1);
         } else {
            cmd.createArgument().setValue("-J-Xmx" + attributes.getMemoryMaximumSize());
         }
      }

      if (attributes.getNowarn()) {
         cmd.createArgument().setValue("-nowarn");
      }

      if (attributes.getDeprecation()) {
         cmd.createArgument().setValue("-deprecation");
      }

      if (attributes.getClasspath() != null) {
         cmd.createArgument().setValue("-classpath");
         cmd.createArgument().setPath(attributes.getClasspath());
      }

      if (attributes.getSourcepath() != null && attributes.getSourcepath().size() > 0) {
         cmd.createArgument().setValue("-sourcepath");
         cmd.createArgument().setPath(attributes.getSourcepath());
      }

      Path bp;
      if (attributes.getRelease() == null || !this.assumeJavaXY(attributes, "javac1.9", "9") && !this.assumeJavaXY(attributes, "javac9", "9")) {
         if (attributes.getTarget() != null) {
            cmd.createArgument().setValue("-source");
            cmd.createArgument().setValue(attributes.getTarget());
            cmd.createArgument().setValue("-target");
            cmd.createArgument().setValue(attributes.getTarget());
         }

         bp = attributes.getBootclasspath();
         if (bp != null && bp.size() > 0) {
            cmd.createArgument().setValue("-bootclasspath");
            cmd.createArgument().setPath(bp);
         }
      }

      bp = attributes.getExtdirs();
      if (bp != null && bp.size() > 0) {
         cmd.createArgument().setValue("-extdirs");
         cmd.createArgument().setPath(bp);
      }

      if (attributes.getEncoding() != null) {
         cmd.createArgument().setValue("-encoding");
         cmd.createArgument().setValue(attributes.getEncoding());
      }

      if (attributes.getDebug()) {
         if (useDebugLevel) {
            String debugLevel = attributes.getDebugLevel();
            if (debugLevel != null) {
               cmd.createArgument().setValue("-g:" + debugLevel);
            } else {
               cmd.createArgument().setValue("-g");
            }
         } else {
            cmd.createArgument().setValue("-g");
         }
      } else {
         cmd.createArgument().setValue("-g:none");
      }

      if (attributes.getOptimize()) {
         cmd.createArgument().setValue("-O");
      }

      if (attributes.getDepend()) {
         attributes.log("depend attribute is not supported by the modern compiler", 1);
      }

      if (attributes.getVerbose()) {
         cmd.createArgument().setValue("-verbose");
      }

      cmd.addArguments(attributes.getCurrentCompilerArgs());
      return cmd;
   }
}
