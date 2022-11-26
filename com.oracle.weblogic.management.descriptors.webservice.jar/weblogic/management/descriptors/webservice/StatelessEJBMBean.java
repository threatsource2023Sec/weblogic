package weblogic.management.descriptors.webservice;

public interface StatelessEJBMBean extends ComponentMBean {
   EJBLinkMBean getEJBLink();

   void setEJBLink(EJBLinkMBean var1);

   JNDINameMBean getJNDIName();

   void setJNDIName(JNDINameMBean var1);
}
