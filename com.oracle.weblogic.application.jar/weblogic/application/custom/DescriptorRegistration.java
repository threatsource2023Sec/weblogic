package weblogic.application.custom;

public final class DescriptorRegistration {
   private final String rootElementName;
   private final String namespace;
   private Class rootBeanType;
   private String uri;
   private String stringRep;

   public static DescriptorRegistration create(String rootElementName, String namespace) {
      return new DescriptorRegistration(rootElementName, namespace);
   }

   private DescriptorRegistration(String rootElementName, String namespace) {
      this.rootElementName = rootElementName;
      this.namespace = namespace;
      this.stringRep = this.setStringRepresentaion();
   }

   private String setStringRepresentaion() {
      String stringRep = "ElementName=" + this.rootElementName + ":Namespace=" + this.namespace;
      if (this.rootBeanType != null) {
         stringRep = stringRep + ":BeanName=" + this.rootBeanType.getName();
      }

      if (this.uri != null) {
         stringRep = stringRep + ":URI=" + this.uri;
      }

      return stringRep;
   }

   String getRootElementName() {
      return this.rootElementName;
   }

   String getNamespace() {
      return this.namespace;
   }

   void setRootBeanType(Class rootBeanType) {
      this.rootBeanType = rootBeanType;
      this.stringRep = this.setStringRepresentaion();
   }

   Class getRootBeanType() {
      return this.rootBeanType;
   }

   String getUri() {
      return this.uri;
   }

   void setUri(String uri) {
      this.uri = uri;
   }

   public String toString() {
      return this.stringRep;
   }
}
