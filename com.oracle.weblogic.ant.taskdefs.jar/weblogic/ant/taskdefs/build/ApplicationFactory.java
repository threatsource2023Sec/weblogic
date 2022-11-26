package weblogic.ant.taskdefs.build;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

public abstract class ApplicationFactory {
   private static ApplicationFactory[] factories = new ApplicationFactory[]{new EARApplicationFactory()};

   abstract Application claim(BuildCtx var1) throws BuildException;

   private static void logVerbose(Project project, String str) {
      project.log(str, 3);
   }

   public static Application newApplication(BuildCtx ctx) throws BuildException {
      Application app = null;
      Project project = ctx.getProject();

      for(int i = 0; i < factories.length; ++i) {
         logVerbose(project, "Trying factory: " + factories[i].getClass().getName());
         app = factories[i].claim(ctx);
         if (app != null) {
            logVerbose(project, "Factory: " + factories[i].getClass().getName() + " claimed application");
            return app;
         }

         logVerbose(project, "Factory: " + factories[i].getClass().getName() + " did not claim application.");
      }

      throw new BuildException("Unable to recognize directory: " + ctx.getSrcDir().getAbsolutePath() + " as a valid application.  An application should have a META-INF/application.xml, META-INF/ejb-jar.xml, WEB-INF/web.xml, or META-INF/ra.xml file to be recognized.");
   }
}
