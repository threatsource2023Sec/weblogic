package weblogic.application;

public class ResourceException extends Exception {
   private final String resourceId;
   private final String moduleId;
   private final String servername;
   private final String descriptorURI;
   private final String xpath;

   public ResourceException(String errorMessage, String resourceId, String moduleId, String descriptorURI, String targetServername, String propertyXPath) {
      super(errorMessage);
      this.resourceId = resourceId;
      this.moduleId = moduleId;
      this.descriptorURI = descriptorURI;
      this.xpath = propertyXPath;
      this.servername = targetServername;
   }

   public String getResourceName() {
      return this.resourceId;
   }

   public String getTargetServerName() {
      return this.servername;
   }

   public String getModuleId() {
      return this.moduleId;
   }

   public String getDescriptorURI() {
      return this.descriptorURI;
   }

   public String getPropertyXPath() {
      return this.xpath;
   }
}
