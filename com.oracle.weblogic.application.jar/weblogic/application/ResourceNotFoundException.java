package weblogic.application;

public class ResourceNotFoundException extends ResourceException {
   public ResourceNotFoundException(String errorMessage, String resourceId, String moduleId, String ddPath, String servername, String location) {
      super(errorMessage, resourceId, moduleId, ddPath, servername, location);
   }
}
