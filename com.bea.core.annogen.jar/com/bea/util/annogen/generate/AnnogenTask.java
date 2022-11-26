package com.bea.util.annogen.generate;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JamService;
import com.bea.util.jam.JamServiceFactory;
import com.bea.util.jam.JamServiceParams;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

public class AnnogenTask extends Task {
   private Annogen mAnnogen = new Annogen();
   private Path mSrcDir = null;
   private Path mToolpath = null;
   private Path mClasspath = null;
   private String mIncludes = "**/*.java";
   private List mMappings = null;
   private String mInputEncoding = null;

   public void setDestDir(File f) {
      this.mAnnogen.setOutputDir(f);
   }

   public void setSrcdir(Path srcDir) {
      if (this.mSrcDir == null) {
         this.mSrcDir = srcDir;
      } else {
         this.mSrcDir.append(srcDir);
      }

   }

   public void setIncludes(String includes) {
      if (includes == null) {
         throw new IllegalArgumentException("null includes");
      } else {
         this.mIncludes = includes;
      }
   }

   public void setImplementAnnotationTypes(boolean b) {
      this.mAnnogen.setImplementAnnotationTypes(b);
   }

   public void setToolpath(Path path) {
      if (this.mToolpath == null) {
         this.mToolpath = path;
      } else {
         this.mToolpath.append(path);
      }

   }

   public void setToolpathRef(Reference r) {
      this.createToolpath().setRefid(r);
   }

   public Path createToolpath() {
      if (this.mToolpath == null) {
         this.mToolpath = new Path(this.getProject());
      }

      return this.mToolpath.createPath();
   }

   public void setClasspath(Path path) {
      if (this.mClasspath == null) {
         this.mClasspath = path;
      } else {
         this.mClasspath.append(path);
      }

   }

   public void setClasspathRef(Reference r) {
      this.createClasspath().setRefid(r);
   }

   public Path createClasspath() {
      if (this.mClasspath == null) {
         this.mClasspath = new Path(this.getProject());
      }

      return this.mClasspath.createPath();
   }

   public void addMapping(AnnoBeanMapping m) {
      if (this.mMappings == null) {
         this.mMappings = new ArrayList();
      }

      this.mMappings.add(m);
   }

   public void setKeepGenerated(boolean b) {
      this.mAnnogen.setKeepGenerated(b);
   }

   public void setInputEncoding(String encoding) {
      this.mInputEncoding = encoding;
   }

   public void setOutputEncoding(String encoding) {
      this.mAnnogen.setOutputEncoding(encoding);
   }

   public void setJavacTarget(String js) {
      this.mAnnogen.setJavacTarget(js);
   }

   public void setJavacSource(String js) {
      this.mAnnogen.setJavacSource(js);
   }

   public void execute() throws BuildException {
      if (this.mSrcDir == null) {
         throw new BuildException("'srcDir' must be specified");
      } else {
         JamServiceFactory jsf = JamServiceFactory.getInstance();
         JamServiceParams p = jsf.createServiceParams();
         if (this.mInputEncoding != null) {
            p.setCharacterEncoding(this.mInputEncoding);
         }

         File[] cp;
         int i;
         if (this.mToolpath != null) {
            cp = this.path2files(this.mToolpath);

            for(i = 0; i < cp.length; ++i) {
               p.addToolClasspath(cp[i]);
            }
         }

         if (this.mClasspath != null) {
            cp = this.path2files(this.mClasspath);

            for(i = 0; i < cp.length; ++i) {
               p.addClasspath(cp[i]);
            }

            this.mAnnogen.setClasspath(cp);
         }

         p.includeSourcePattern(this.path2files(this.mSrcDir), this.mIncludes);

         try {
            JamService js = jsf.createService(p);
            JClass[] classes = js.getAllClasses();
            this.mAnnogen.addAnnotationClasses(classes);
            if (this.mMappings != null) {
               AnnoBeanMapping[] mappings = new AnnoBeanMapping[this.mMappings.size()];
               this.mMappings.toArray(mappings);
               this.mAnnogen.setMappings(mappings);
            }

            this.log("Generating annotation impls for the following classes:");

            for(int i = 0; i < classes.length; ++i) {
               this.log("  " + classes[i].getQualifiedName());
            }

            this.mAnnogen.doCodegen();
            this.log("...done.");
         } catch (IOException var6) {
            throw new BuildException(var6);
         }
      }
   }

   private File[] path2files(Path path) {
      String[] list = path.list();
      File[] out = new File[list.length];

      for(int i = 0; i < out.length; ++i) {
         out[i] = (new File(list[i])).getAbsoluteFile();
      }

      return out;
   }

   public static void main(String[] args) throws Exception {
      AnnogenTask task = new AnnogenTask();
      task.parseArgs(args);
      task.execute();
   }

   private void parseArgs(String[] args) throws Exception {
      for(int i = 0; i < args.length; ++i) {
         String typePattern;
         if (args[i].equals("-js")) {
            ++i;
            typePattern = args[i];
            this.setJavacSource(typePattern);
         } else if (args[i].equals("-jt")) {
            ++i;
            typePattern = args[i];
            this.setJavacTarget(typePattern);
         } else if (args[i].equals("-d")) {
            ++i;
            typePattern = args[i];
            this.setDestDir(new File(typePattern));
         } else if (args[i].equals("-s")) {
            ++i;
            typePattern = args[i];
            Path p1 = new Path(new Project(), typePattern);
            this.setSrcdir(p1);
         } else if (args[i].equals("-i")) {
            ++i;
            typePattern = args[i];
            this.setIncludes(typePattern);
         } else if (args[i].equals("-k")) {
            ++i;
            typePattern = args[i];
            if (typePattern.equals("false")) {
               this.setKeepGenerated(false);
            } else {
               this.setKeepGenerated(true);
            }
         } else if (args[i].equals("-a")) {
            ++i;
            typePattern = args[i];
            if (typePattern.equals("false")) {
               this.setImplementAnnotationTypes(false);
            } else {
               this.setImplementAnnotationTypes(true);
            }
         } else if (args[i].equals("-m")) {
            ++i;
            typePattern = args[i];
            ++i;
            String beanPattern = args[i];
            this.addMapping(new AnnoBeanMapping(typePattern, beanPattern));
         }
      }

   }
}
