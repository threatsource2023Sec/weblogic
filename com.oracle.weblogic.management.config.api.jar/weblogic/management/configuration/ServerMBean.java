package weblogic.management.configuration;

public interface ServerMBean extends ServerTemplateMBean {
   ServerTemplateMBean getServerTemplate();

   void setServerTemplate(ServerTemplateMBean var1);
}
