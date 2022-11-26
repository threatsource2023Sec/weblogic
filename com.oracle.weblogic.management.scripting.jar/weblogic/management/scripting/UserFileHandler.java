package weblogic.management.scripting;

import java.util.Hashtable;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import weblogic.deploy.api.internal.utils.JMXDeployerHelper;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;

public class UserFileHandler {
   WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;
   private static final String NONE = "None";
   private static final String FALSE = "false";
   private static final String TRUE = "true";

   public UserFileHandler(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   public String uploadUserFileWait(String uploadPath, String filePath, String partitionName, boolean overwrite) throws ScriptException {
      this.ctx.commandType = "uploadUserFileWait";
      String block = "true";

      try {
         boolean doOverwrite = new Boolean(overwrite);
         DeploymentOptions depOptions = new DeploymentOptions();
         if (partitionName != null) {
            depOptions.setPartition(partitionName);
         }

         depOptions.setUploadPath(uploadPath);
         depOptions.setOverwriteFile(doOverwrite);
         String username = new String(this.ctx.username_bytes);
         String password = new String(this.ctx.password_bytes);
         JMXConnector jmx = this.getJMXConnector();
         JMXDeployerHelper helper = new JMXDeployerHelper(jmx);
         String file = helper.uploadPartitionUserFile(this.ctx.url, username, password, filePath, (String)null, depOptions);
         return file;
      } catch (Throwable var13) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorDeployingApp(var13.getMessage()), var13);
         return null;
      }
   }

   private JMXConnector getJMXConnector() throws Exception {
      String jndiName = "weblogic.management.mbeanservers.domainruntime";
      String username = new String(this.ctx.username_bytes);
      String password = new String(this.ctx.password_bytes);
      Hashtable h = new Hashtable();
      h.put("java.naming.security.principal", username);
      h.put("java.naming.security.credentials", password);
      h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      String protocol = this.ctx.getProtocol(this.ctx.url);
      String host = this.ctx.getListenAddress(this.ctx.url);
      int port = Integer.parseInt(this.ctx.getListenPort(this.ctx.url));
      String path = this.ctx.getPath(this.ctx.url);
      if (path == null) {
         path = "";
      }

      JMXServiceURL jmxs = new JMXServiceURL(protocol, host, port, path + "/jndi/" + jndiName);
      return JMXConnectorFactory.connect(jmxs, h);
   }
}
