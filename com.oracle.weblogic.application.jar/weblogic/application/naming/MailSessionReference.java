package weblogic.application.naming;

import java.util.Properties;
import javax.naming.Reference;

public class MailSessionReference extends Reference {
   private static final String MAIL_SESSION_OBJECT_FACTORY = "weblogic.deployment.MailSessionObjectFactory";
   private static final String MAIL_SESSION_REF_TYPE = "javax.mail.Session";
   private String user;
   private String password;
   private Properties props;

   public MailSessionReference(String user, String password, Properties props) {
      super("javax.mail.Session", "weblogic.deployment.MailSessionObjectFactory", (String)null);
      this.user = user;
      this.password = password;
      this.props = props;
   }

   public String getUser() {
      return this.user;
   }

   public String getPassword() {
      return this.password;
   }

   public String getPropertyValue(String name) {
      return this.props.getProperty(name);
   }

   public Properties getProperties() {
      return this.props;
   }
}
