package com.oracle.weblogic.lifecycle.provisioning.core.handlers.buildxml;

import com.oracle.weblogic.lifecycle.provisioning.ant.JavaUtilLoggingLogger;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.DeprovisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Handler;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.InitialProvisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.DemuxInputStream;
import org.apache.tools.ant.DemuxOutputStream;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.ProjectHelperRepository;
import org.apache.tools.ant.PropertyHelper;
import org.apache.tools.ant.types.resources.URLResource;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.api.messaging.MessageReceiver;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;

@Component("Ant")
@Handler
@MessageReceiver({ProvisioningEvent.class})
@ProvisioningOperationScoped
@Service
public class BuildXmlHandler {
   private final ClassLoader antCoreLoader;
   private final Iterable buildListeners;
   private final Iterable propertyEvaluators;
   private final IterableProvider projectHelpers;
   private final Logger logger;

   public BuildXmlHandler() {
      this((ClassLoader)null, (IterableProvider)null, (IterableProvider)null, (IterableProvider)null);
   }

   @Inject
   public BuildXmlHandler(@Optional @Named("ant.coreLoader") ClassLoader antCoreLoader, @Optional IterableProvider buildListeners, @Optional IterableProvider projectHelpers, @Optional IterableProvider propertyEvaluators) {
      this.logger = Logger.getLogger(this.getClass().getName());
      this.antCoreLoader = antCoreLoader;
      this.buildListeners = buildListeners;
      this.projectHelpers = projectHelpers;
      this.propertyEvaluators = propertyEvaluators;
   }

   protected Logger getLogger() {
      return this.logger;
   }

   public void provision(@InitialProvisioningOperation @SubscribeTo @BuildXml ProvisioningEvent provisioningEvent) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "provision";
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "provision", new Object[]{provisioningEvent});
      }

      if (provisioningEvent != null) {
         Document document = provisioningEvent.getDocument();
         if (document != null) {
            String documentURI = document.getDocumentURI();
            if (documentURI == null) {
               throw new IllegalArgumentException("provisioningEvent", new NullPointerException("provisioningEvent.getDocument().getDocumentURI()"));
            }

            try {
               this.runBuild((new URI(documentURI)).toURL(), "provision");
            } catch (URISyntaxException | MalformedURLException var8) {
               throw new ProvisioningException(var8.getMessage(), var8);
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "provision");
      }

   }

   public void deprovision(@DeprovisioningOperation @SubscribeTo @BuildXml ProvisioningEvent provisioningEvent) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "deprovision";
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "deprovision", new Object[]{provisioningEvent});
      }

      if (provisioningEvent != null) {
         Document document = provisioningEvent.getDocument();
         if (document != null) {
            String documentURI = document.getDocumentURI();
            if (documentURI == null) {
               throw new IllegalArgumentException("provisioningEvent", new NullPointerException("provisioningEvent.getDocument().getDocumentURI()"));
            }

            try {
               this.runBuild((new URI(documentURI)).toURL(), "deprovision");
            } catch (URISyntaxException | MalformedURLException var8) {
               throw new ProvisioningException(var8.getMessage(), var8);
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "deprovision");
      }

   }

   public void runBuild(URL buildFileURL, String target) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "runBuild";
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "runBuild", new Object[]{buildFileURL, target});
      }

      Objects.requireNonNull(buildFileURL, "buildFileURL == null");
      Objects.requireNonNull(target, "target == null");
      Project project = new Project();
      ClassLoader antCoreLoader;
      if (this.antCoreLoader == null) {
         antCoreLoader = this.getClass().getClassLoader();
      } else {
         antCoreLoader = this.antCoreLoader;
      }

      project.setCoreLoader(antCoreLoader);
      ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
      PrintStream savedErr = System.err;
      PrintStream savedOut = System.out;
      InputStream savedIn = System.in;
      Object throwMe = null;
      boolean var26 = false;

      try {
         var26 = true;
         project.addBuildListener(new JavaUtilLoggingLogger(logger));
         if (this.buildListeners != null) {
            Iterator var13 = this.buildListeners.iterator();

            while(var13.hasNext()) {
               BuildListener buildListener = (BuildListener)var13.next();
               if (buildListener != null) {
                  project.addBuildListener(buildListener);
               }
            }
         }

         project.setBasedir(".");
         System.setIn(new DemuxInputStream(project));
         System.setOut(new AlwaysOpenPrintStream(project, false));
         System.setErr(new AlwaysOpenPrintStream(project, true));

         try {
            project.fireBuildStarted();
            project.init();
            PropertyHelper propertyHelper = PropertyHelper.getPropertyHelper(project);

            assert propertyHelper != null;

            assert propertyHelper.getProject() == project;

            assert project.getReference("ant.PropertyHelper") == propertyHelper;

            if (this.propertyEvaluators != null) {
               Iterator var41 = this.propertyEvaluators.iterator();

               while(var41.hasNext()) {
                  PropertyHelper.PropertyEvaluator propertyEvaluator = (PropertyHelper.PropertyEvaluator)var41.next();
                  if (propertyEvaluator != null) {
                     propertyHelper.add(propertyEvaluator);
                  }
               }
            }

            project.setUserProperty("ant.file", buildFileURL.toString());
            project.setUserProperty("ant.file.type", "url");
            project.setUserProperty("ant.project.invoked-targets", target);
            ProjectHelper projectHelper = this.getProjectHelper(buildFileURL);
            if (projectHelper == null) {
               throw new IllegalStateException("getProjectHelper(buildFileURL) == null");
            }

            if (!projectHelper.canParseBuildFile(new URLResource(buildFileURL))) {
               throw new IllegalStateException("!getProjectHelper(buildFileURL).canParseBuildFile(resource)");
            }

            project.addReference("ant.projectHelper", projectHelper);
            projectHelper.parse(project, buildFileURL);
            project.executeTarget(target);
         } finally {
            System.setIn(savedIn);

            assert System.out instanceof AlwaysOpenPrintStream;

            System.out.close();
            System.setOut(savedOut);

            assert System.err instanceof AlwaysOpenPrintStream;

            System.err.close();
            System.setErr(savedErr);
            Thread.currentThread().setContextClassLoader(contextClassLoader);
         }

         var26 = false;
      } catch (BuildException var36) {
         ProvisioningException provisioningException = new ProvisioningException(var36.getMessage(), var36);
         throwMe = provisioningException;
         throw provisioningException;
      } catch (Error | RuntimeException var37) {
         throwMe = var37;
         throw var37;
      } finally {
         if (var26) {
            try {
               project.fireBuildFinished((Throwable)throwMe);
            } catch (Error | RuntimeException var34) {
               BuildException buildException = new BuildException(var34);
               if (throwMe != null) {
                  assert !(throwMe instanceof BuildException);

                  ((Throwable)throwMe).addSuppressed(buildException);
                  if (throwMe instanceof ProvisioningException) {
                     throw (ProvisioningException)throwMe;
                  }

                  if (throwMe instanceof RuntimeException) {
                     throw (RuntimeException)throwMe;
                  }

                  if (throwMe instanceof Error) {
                     throw (Error)throwMe;
                  }

                  throw new IllegalStateException((Throwable)throwMe);
               }

               throw buildException;
            }

            Thread.currentThread().setContextClassLoader(contextClassLoader);
         }
      }

      try {
         project.fireBuildFinished((Throwable)throwMe);
      } catch (Error | RuntimeException var33) {
         BuildException buildException = new BuildException(var33);
         if (throwMe != null) {
            assert !(throwMe instanceof BuildException);

            ((Throwable)throwMe).addSuppressed(buildException);
            if (throwMe instanceof ProvisioningException) {
               throw (ProvisioningException)throwMe;
            }

            if (throwMe instanceof RuntimeException) {
               throw (RuntimeException)throwMe;
            }

            if (throwMe instanceof Error) {
               throw (Error)throwMe;
            }

            throw new IllegalStateException((Throwable)throwMe);
         }

         throw buildException;
      }

      Thread.currentThread().setContextClassLoader(contextClassLoader);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "runBuild");
      }

   }

   protected ProjectHelper getProjectHelper(URL buildFileURL) {
      Objects.requireNonNull(buildFileURL, "buildFileURL == null");
      ProjectHelper returnValue = null;
      URLResource resource = new URLResource(buildFileURL);
      if (System.getProperty("org.apache.tools.ant.ProjectHelper") == null && this.projectHelpers != null) {
         Iterator var4 = this.projectHelpers.iterator();

         while(var4.hasNext()) {
            ProjectHelper projectHelper = (ProjectHelper)var4.next();
            if (projectHelper != null && projectHelper.canParseBuildFile(resource)) {
               returnValue = projectHelper;
               break;
            }
         }
      }

      if (returnValue == null) {
         returnValue = ProjectHelperRepository.getInstance().getProjectHelperForBuildFile(resource);

         assert returnValue != null : "ProjectHelperRepository.getInstance().getProjectHelperForBuildFile(\"" + buildFileURL + "\") == null";
      }

      return returnValue;
   }

   private static final class AlwaysOpenPrintStream extends PrintStream {
      private AlwaysOpenPrintStream(Project project, boolean isErrorStream) {
         super(new DemuxOutputStream(project, isErrorStream));
      }

      public final void close() {
         this.flush();
      }

      // $FF: synthetic method
      AlwaysOpenPrintStream(Project x0, boolean x1, Object x2) {
         this(x0, x1);
      }
   }
}
