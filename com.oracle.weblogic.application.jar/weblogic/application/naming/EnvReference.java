package weblogic.application.naming;

import javax.naming.Reference;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class EnvReference extends Reference {
   private final transient Environment environment;
   protected String jndiName;
   private transient AuthenticatedSubject runAs;
   private transient ResourceRefBean resRef;
   private transient ClassLoader classloader;

   public EnvReference(Environment env, String type, String factory) {
      super(type, factory, (String)null);
      this.environment = env;
   }

   public Environment getEnvironment() {
      return this.environment;
   }

   public void setJndiName(String name) {
      this.jndiName = name;
   }

   public String getJndiName() {
      return this.jndiName;
   }

   public ResourceRefBean getResourceRefBean() {
      return this.resRef;
   }

   public void setResourceRefBean(ResourceRefBean bean) {
      this.resRef = bean;
   }

   public ClassLoader getClassloader() {
      return this.classloader;
   }

   public void setClassloader(ClassLoader classloader) {
      this.classloader = classloader;
   }

   public AuthenticatedSubject getRunAs() {
      return this.runAs;
   }

   public void setRunAs(AuthenticatedSubject runAs) {
      this.runAs = runAs;
   }
}
