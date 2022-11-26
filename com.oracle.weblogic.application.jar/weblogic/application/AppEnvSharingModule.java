package weblogic.application;

public interface AppEnvSharingModule extends Module {
   boolean needsAppEnvContextCopy();
}
