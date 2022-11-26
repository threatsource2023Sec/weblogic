package weblogic.application;

public class MisconfiguredResourceException extends ResourceException {
   public MisconfiguredResourceException(String errorMessage, String resourceId, String moduleId, String ddPath, String servername, String location) {
      super(errorMessage, resourceId, moduleId, ddPath, servername, location);
   }
}
