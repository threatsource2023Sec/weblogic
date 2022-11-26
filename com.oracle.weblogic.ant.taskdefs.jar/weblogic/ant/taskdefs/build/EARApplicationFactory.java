package weblogic.ant.taskdefs.build;

public final class EARApplicationFactory extends ApplicationFactory {
   protected Application claim(BuildCtx ctx) {
      return new Application(ctx);
   }
}
