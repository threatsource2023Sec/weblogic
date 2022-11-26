package org.apache.xmlbeans.impl.jam.internal.javadoc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.xmlbeans.impl.jam.annotation.JavadocTagParser;
import org.apache.xmlbeans.impl.jam.internal.JamServiceContextImpl;
import org.apache.xmlbeans.impl.jam.internal.elements.ElementContext;
import org.apache.xmlbeans.impl.jam.internal.elements.PrimitiveClassImpl;
import org.apache.xmlbeans.impl.jam.mutable.MAnnotatedElement;
import org.apache.xmlbeans.impl.jam.mutable.MClass;
import org.apache.xmlbeans.impl.jam.mutable.MElement;
import org.apache.xmlbeans.impl.jam.mutable.MField;
import org.apache.xmlbeans.impl.jam.mutable.MInvokable;
import org.apache.xmlbeans.impl.jam.mutable.MMethod;
import org.apache.xmlbeans.impl.jam.mutable.MParameter;
import org.apache.xmlbeans.impl.jam.mutable.MSourcePosition;
import org.apache.xmlbeans.impl.jam.provider.JamClassBuilder;
import org.apache.xmlbeans.impl.jam.provider.JamClassPopulator;
import org.apache.xmlbeans.impl.jam.provider.JamServiceContext;

public class JavadocClassBuilder extends JamClassBuilder implements JamClassPopulator {
   public static final String ARGS_PROPERTY = "javadoc.args";
   public static final String PARSETAGS_PROPERTY = "javadoc.parsetags";
   private RootDoc mRootDoc = null;
   private JavadocTigerDelegate mTigerDelegate = null;
   private JavadocTagParser mTagParser = null;
   private boolean mParseTags = true;

   public void init(ElementContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException("null context");
      } else {
         super.init(ctx);
         this.getLogger().verbose((String)"init()", this);
         this.initDelegate(ctx);
         this.initJavadoc((JamServiceContext)ctx);
      }
   }

   public MClass build(String packageName, String className) {
      this.assertInitialized();
      if (this.getLogger().isVerbose((Object)this)) {
         this.getLogger().verbose("trying to build '" + packageName + "' '" + className + "'");
      }

      String loadme = packageName.trim().length() > 0 ? packageName + '.' + className : className;
      ClassDoc cd = this.mRootDoc.classNamed(loadme);
      if (cd == null) {
         if (this.getLogger().isVerbose((Object)this)) {
            this.getLogger().verbose("no ClassDoc for " + loadme);
         }

         return null;
      } else {
         List importSpecs = null;
         ClassDoc[] imported = cd.importedClasses();
         int i;
         if (imported != null) {
            importSpecs = new ArrayList();

            for(i = 0; i < imported.length; ++i) {
               importSpecs.add(getFdFor(imported[i]));
            }
         }

         PackageDoc[] imported = cd.importedPackages();
         if (imported != null) {
            if (importSpecs == null) {
               importSpecs = new ArrayList();
            }

            for(i = 0; i < imported.length; ++i) {
               importSpecs.add(imported[i].name() + ".*");
            }
         }

         String[] importSpecsArray = null;
         if (importSpecs != null) {
            importSpecsArray = new String[importSpecs.size()];
            importSpecs.toArray(importSpecsArray);
         }

         MClass out = this.createClassToBuild(packageName, className, importSpecsArray, this);
         out.setArtifact(cd);
         return out;
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

      File[] files;
      try {
         files = serviceContext.getSourceFiles();
      } catch (IOException var10) {
         this.getLogger().error((Throwable)var10);
         return;
      }

      if (files != null && files.length != 0) {
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

            this.mRootDoc = jdr.run(files, out, sourcePath, classPath, this.getJavadocArgs(serviceContext), this.getLogger());
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
         } catch (FileNotFoundException var11) {
            this.getLogger().error((Throwable)var11);
         } catch (IOException var12) {
            this.getLogger().error((Throwable)var12);
         }

      } else {
         throw new IllegalArgumentException("No source files in context.");
      }
   }

   private void populate(MField dest, FieldDoc src) {
      dest.setArtifact(src);
      dest.setSimpleName(src.name());
      dest.setType(getFdFor(src.type()));
      dest.setModifiers(src.modifierSpecifier());
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

         for(int i = 0; i < params.length; ++i) {
            this.populate(dest.addNewParameter(), src, params[i]);
         }

         this.addAnnotations(dest, src);
         addSourcePosition(dest, (Doc)src);
      }
   }

   private void populate(MParameter dest, ExecutableMemberDoc method, Parameter src) {
      dest.setArtifact(src);
      dest.setSimpleName(src.name());
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
                  out.write(t.asClassDoc().qualifiedName());
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
                  String simpleName = cd.name();
                  simpleName = simpleName.substring(simpleName.lastIndexOf(46) + 1);
                  return outer.qualifiedName() + '$' + simpleName;
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
}
