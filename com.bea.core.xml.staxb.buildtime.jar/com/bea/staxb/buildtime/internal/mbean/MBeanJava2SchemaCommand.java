package com.bea.staxb.buildtime.internal.mbean;

import com.bea.staxb.buildtime.Java2SchemaTask;
import com.bea.staxb.buildtime.Namespace;
import java.io.File;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;

public class MBeanJava2SchemaCommand extends Java2SchemaTask {
   Project proj = null;

   public MBeanJava2SchemaCommand() {
      super(new MBeanJava2Schema());
   }

   public static void main(String[] args) throws Exception {
      MBeanJava2SchemaCommand task = new MBeanJava2SchemaCommand();
      task.parseArgs(args);
      task.execute();
   }

   public void parseArgs(String[] args) throws Exception {
      String uri = null;
      String schemaLocation = null;

      for(int i = 0; i < args.length; ++i) {
         String version;
         Path p1;
         if (args[i].equals("-s")) {
            ++i;
            version = args[i];
            p1 = new Path(new Project(), version);
            this.setSrcdir(p1);
         } else if (args[i].equals("-d")) {
            ++i;
            version = args[i];
            this.setDestDir(new File(version));
         } else if (args[i].equals("-e")) {
            ++i;
            version = args[i];
            if (version.equalsIgnoreCase("false")) {
               this.setElementFormDefaultQualified(false);
            } else {
               this.setElementFormDefaultQualified(true);
            }
         } else if (args[i].equals("-a")) {
            ++i;
            version = args[i];
            if (version.equalsIgnoreCase("false")) {
               this.setAttributeFormDefaultQualified(false);
            } else {
               this.setAttributeFormDefaultQualified(true);
            }
         } else if (args[i].equals("-v")) {
            ++i;
            version = args[i];
            if (version.equalsIgnoreCase("false")) {
               this.setVerbose(false);
            } else {
               this.setVerbose(true);
            }
         } else if (args[i].equals("-g")) {
            ++i;
            version = args[i];
            if (version.equalsIgnoreCase("false")) {
               this.setGenerateDocumentation(false);
            } else {
               this.setGenerateDocumentation(true);
            }
         } else if (args[i].equals("-c")) {
            ++i;
            version = args[i];
            p1 = new Path(new Project(), version);
            this.setClasspath(p1);
         } else if (args[i].equals("-i")) {
            ++i;
            version = args[i];
            this.setIncludes(version);
         } else if (args[i].equals("-u")) {
            ++i;
            uri = args[i];
         } else if (args[i].equals("-l")) {
            ++i;
            schemaLocation = args[i];
         } else if (args[i].equals("-t")) {
            ++i;
            version = args[i];
            this.setTypeSystemName(version);
         } else if (args[i].equals("-n")) {
            ++i;
            version = args[i];
            this.setVersion(version);
         }
      }

      if (uri != null || schemaLocation != null) {
         Namespace ns = new Namespace();
         if (uri != null) {
            ns.setUri(uri);
         }

         if (schemaLocation != null) {
            ns.setSchemaLocation(schemaLocation);
         }

         this.addNamespace(ns);
      }

   }

   public Project getProject() {
      if (this.proj == null) {
         this.proj = new Project();
      }

      return this.proj;
   }
}
