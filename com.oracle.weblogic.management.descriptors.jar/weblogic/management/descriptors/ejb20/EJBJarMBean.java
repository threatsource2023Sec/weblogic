package weblogic.management.descriptors.ejb20;

public interface EJBJarMBean extends weblogic.management.descriptors.ejb11.EJBJarMBean {
   RelationshipsMBean getRelationships();

   void setRelationships(RelationshipsMBean var1);

   weblogic.management.descriptors.ejb11.AssemblyDescriptorMBean getAssemblyDescriptor();

   void setAssemblyDescriptor(weblogic.management.descriptors.ejb11.AssemblyDescriptorMBean var1);

   String getEJBClientJar();

   void setEJBClientJar(String var1);
}
