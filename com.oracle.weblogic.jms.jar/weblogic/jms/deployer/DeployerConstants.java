package weblogic.jms.deployer;

public interface DeployerConstants {
   public static enum DefaultNames {
      DEFAULT("DefaultConnectionFactory", "weblogic.jms.ConnectionFactory"),
      XA("DefaultXAConnectionFactory", "weblogic.jms.XAConnectionFactory"),
      XA0("DefaultXAConnectionFactory0", "weblogic.jms.XAConnectionFactory0"),
      XA1("DefaultXAConnectionFactory1", "weblogic.jms.XAConnectionFactory1"),
      XA2("DefaultXAConnectionFactory2", "weblogic.jms.XAConnectionFactory2"),
      MDB("MessageDrivenBeanConnectionFactory", "weblogic.jms.MessageDrivenBeanConnectionFactory"),
      QUEUE("QueueConnectionFactory", "javax.jms.QueueConnectionFactory"),
      TOPIC("TopicConnectionFactory", "javax.jms.TopicConnectionFactory"),
      PLATFORM_DEFAULT("PlatformDefaultConnectionFactory", "weblogic.jms.DefaultConnectionFactory");

      private final String cfname;
      private final String jndiName;

      private DefaultNames(String cfname, String jndiName) {
         this.cfname = cfname;
         this.jndiName = jndiName;
      }

      public String getCFName() {
         return this.cfname;
      }

      public String getJndiName() {
         return this.jndiName;
      }
   }
}
