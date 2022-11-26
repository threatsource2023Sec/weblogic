package weblogic.jms.extensions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.wl.ClientSAFBean;
import weblogic.j2ee.descriptor.wl.DistributedQueueBean;
import weblogic.j2ee.descriptor.wl.DistributedTopicBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.j2ee.descriptor.wl.SAFLoginContextBean;
import weblogic.j2ee.descriptor.wl.SAFQueueBean;
import weblogic.j2ee.descriptor.wl.SAFRemoteContextBean;
import weblogic.j2ee.descriptor.wl.SAFTopicBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.jms.common.BeanHelper;
import weblogic.jms.module.JMSParser;
import weblogic.management.ManagementException;
import weblogic.utils.Getopt2;

public class ClientSAFGenerate {
   private static final String URL_OPT = "url";
   private static final String URL_PARAM = "admin-server-url";
   private static final String URL_DOC = "The URL of the admin server to connect to. If not specified then this utility will run in off-line mode";
   private static final String UN_OPT = "username";
   private static final String UN_PARAM = "name-of-user";
   private static final String UN_DOC = "The username of a user with read access to the Domain. Only used in connected mode";
   private static final String ECF_OPT = "existingClientFile";
   private static final String ECF_PARAM = "file-path";
   private static final String ECF_DOC = "The name of an existing client Store & Forward configuration file. If this parameter is specified then the existing file will be read and new entries will be added. If there are any conflicts detected between items being added and items already in the client configuration file a warning will be emitted and the new item will not be added. If this is specified but the file cannot be found an error is printed and the utility exits";
   private static final String MF_OPT = "moduleFile";
   private static final String MF_PARAM = "module-file-path ['@' plan-path ]";
   private static final String MF_DOC = "The name of a JMS module file. This file is not associated with any specific deployment. Only a few of the fields in the client configuration file can be filled in. This parameter can be used in both connected and off-line mode";
   private static final String OF_OPT = "outputFile";
   private static final String OF_PARAM = "output-file-path";
   private static final String OF_DOC = "The path to the output file";
   private static final String PROGRAM = "weblogic.jms.extensions.ClientSAFGenerate";
   private Getopt2 optionsParser = new Getopt2();
   private ClientSAFBean config;
   private static final String ALL = "All";

   public ClientSAFGenerate() {
      this.optionsParser.addOption("url", "admin-server-url", "The URL of the admin server to connect to. If not specified then this utility will run in off-line mode");
      this.optionsParser.addOption("username", "name-of-user", "The username of a user with read access to the Domain. Only used in connected mode");
      this.optionsParser.addOption("existingClientFile", "file-path", "The name of an existing client Store & Forward configuration file. If this parameter is specified then the existing file will be read and new entries will be added. If there are any conflicts detected between items being added and items already in the client configuration file a warning will be emitted and the new item will not be added. If this is specified but the file cannot be found an error is printed and the utility exits");
      this.optionsParser.addMultiOption("moduleFile", "module-file-path ['@' plan-path ]", "The name of a JMS module file. This file is not associated with any specific deployment. Only a few of the fields in the client configuration file can be filled in. This parameter can be used in both connected and off-line mode");
      this.optionsParser.addOption("outputFile", "output-file-path", "The path to the output file");
      this.optionsParser.setFailOnUnrecognizedOpts(true);
   }

   private void initFile() {
   }

   private void extractFromJMSBean(String name, String rcName, JMSBean jmsBean) throws ManagementException {
      JMSConnectionFactoryBean[] conFacBeans = jmsBean.getConnectionFactories();

      for(int lcv = 0; lcv < conFacBeans.length; ++lcv) {
         JMSConnectionFactoryBean conFacBean = conFacBeans[lcv];
         if (this.config.lookupConnectionFactory(conFacBean.getName()) != null) {
            System.err.println("Warning: The connection factory " + conFacBean.getName() + "already exists in the client SAF configuration file.  Skipping...");
         } else {
            JMSConnectionFactoryBean to = this.config.createConnectionFactory(conFacBean.getName());
            BeanHelper.copyConnectionFactory(to, conFacBean);
         }
      }

      SAFImportedDestinationsBean ids = this.config.lookupSAFImportedDestinations(name);
      if (ids == null) {
         ids = this.config.createSAFImportedDestinations(name);
      }

      if (rcName != null) {
         ids.setSAFRemoteContext(this.config.lookupSAFRemoteContext(rcName));
      }

      QueueBean[] queues = jmsBean.getQueues();

      String jndiName;
      for(int lcv = 0; lcv < queues.length; ++lcv) {
         QueueBean queue = queues[lcv];
         String safExportPolicy = queue.getSAFExportPolicy();
         if (safExportPolicy == null || "All".equals(safExportPolicy)) {
            jndiName = queue.getJNDIName();
            if (jndiName == null) {
               jndiName = queue.getLocalJNDIName();
            }

            SAFQueueBean safQueue = ids.lookupSAFQueue(queue.getName());
            if (safQueue != null) {
               System.err.println("Warning: The queue " + queue.getName() + " in module " + name + " already exists in the client SAF configuration file.  Skipping...");
            } else {
               safQueue = ids.createSAFQueue(queue.getName());
               if (jndiName != null) {
                  safQueue.setLocalJNDIName(jndiName);
                  safQueue.setRemoteJNDIName(jndiName);
               }
            }
         }
      }

      TopicBean[] topics = jmsBean.getTopics();

      String jndiName;
      for(int lcv = 0; lcv < topics.length; ++lcv) {
         TopicBean topic = topics[lcv];
         jndiName = topic.getSAFExportPolicy();
         if (jndiName == null || "All".equals(jndiName)) {
            jndiName = topic.getJNDIName();
            if (jndiName == null) {
               jndiName = topic.getLocalJNDIName();
            }

            SAFTopicBean safTopic = ids.lookupSAFTopic(topic.getName());
            if (safTopic != null) {
               System.err.println("Warning: The topic " + topic.getName() + " in module " + name + " already exists in the client SAF configuration file.  Skipping...");
            } else {
               safTopic = ids.createSAFTopic(topic.getName());
               if (jndiName != null) {
                  safTopic.setLocalJNDIName(jndiName);
                  safTopic.setRemoteJNDIName(jndiName);
               }
            }
         }
      }

      DistributedQueueBean[] dQueues = jmsBean.getDistributedQueues();

      String jndiName;
      for(int lcv = 0; lcv < dQueues.length; ++lcv) {
         DistributedQueueBean dQueue = dQueues[lcv];
         jndiName = dQueue.getSAFExportPolicy();
         if (jndiName == null || "All".equals(jndiName)) {
            jndiName = dQueue.getJNDIName();
            SAFQueueBean safQueue = ids.lookupSAFQueue(dQueue.getName());
            if (safQueue != null) {
               System.err.println("Warning: The distributed queue " + dQueue.getName() + " in module " + name + " already exists in the client SAF configuration file.  Skipping...");
            } else {
               safQueue = ids.createSAFQueue(dQueue.getName());
               if (jndiName != null) {
                  safQueue.setLocalJNDIName(jndiName);
                  safQueue.setRemoteJNDIName(jndiName);
               }
            }
         }
      }

      DistributedTopicBean[] dTopics = jmsBean.getDistributedTopics();

      String jndiName;
      for(int lcv = 0; lcv < dTopics.length; ++lcv) {
         DistributedTopicBean dTopic = dTopics[lcv];
         jndiName = dTopic.getSAFExportPolicy();
         if (jndiName == null || "All".equals(jndiName)) {
            jndiName = dTopic.getJNDIName();
            SAFQueueBean safQueue = ids.lookupSAFQueue(dTopic.getName());
            if (safQueue != null) {
               System.err.println("Warning: The distributed queue " + dTopic.getName() + " in module " + name + " already exists in the client SAF configuration file.  Skipping...");
            } else {
               safQueue = ids.createSAFQueue(dTopic.getName());
               if (jndiName != null) {
                  safQueue.setLocalJNDIName(jndiName);
                  safQueue.setRemoteJNDIName(jndiName);
               }
            }
         }
      }

      UniformDistributedQueueBean[] udQueues = jmsBean.getUniformDistributedQueues();

      String jndiName;
      for(int lcv = 0; lcv < udQueues.length; ++lcv) {
         UniformDistributedQueueBean udQueue = udQueues[lcv];
         jndiName = udQueue.getSAFExportPolicy();
         if (jndiName == null || "All".equals(jndiName)) {
            jndiName = udQueue.getJNDIName();
            SAFQueueBean safQueue = ids.lookupSAFQueue(udQueue.getName());
            if (safQueue != null) {
               System.err.println("Warning: The distributed queue " + udQueue.getName() + " in module " + name + " already exists in the client SAF configuration file.  Skipping...");
            } else {
               safQueue = ids.createSAFQueue(udQueue.getName());
               if (jndiName != null) {
                  safQueue.setLocalJNDIName(jndiName);
                  safQueue.setRemoteJNDIName(jndiName);
               }
            }
         }
      }

      UniformDistributedTopicBean[] udTopics = jmsBean.getUniformDistributedTopics();

      for(int lcv = 0; lcv < udTopics.length; ++lcv) {
         UniformDistributedTopicBean udTopic = udTopics[lcv];
         jndiName = udTopic.getSAFExportPolicy();
         if (jndiName == null || "All".equals(jndiName)) {
            String jndiName = udTopic.getJNDIName();
            SAFQueueBean safQueue = ids.lookupSAFQueue(udTopic.getName());
            if (safQueue != null) {
               System.err.println("Warning: The uniform distributed queue " + udTopic.getName() + " in module " + name + " already exists in the client SAF configuration file.  Skipping...");
            } else {
               safQueue = ids.createSAFQueue(udTopic.getName());
               if (jndiName != null) {
                  safQueue.setLocalJNDIName(jndiName);
                  safQueue.setRemoteJNDIName(jndiName);
               }
            }
         }
      }

   }

   private String generateRemoteContext(ClientSAFBean config, String url, String username) {
      if (url == null) {
         return null;
      } else {
         SAFRemoteContextBean[] remoteContexts = config.getSAFRemoteContexts();

         int counter;
         String proposedContextName;
         for(counter = 0; counter < remoteContexts.length; ++counter) {
            SAFRemoteContextBean remoteContext = remoteContexts[counter];
            SAFLoginContextBean loginContext = remoteContext.getSAFLoginContext();
            proposedContextName = loginContext.getLoginURL();
            if (url.equals(proposedContextName)) {
               return remoteContext.getName();
            }
         }

         counter = 0;
         boolean found = false;
         String finalName = null;

         while(!found) {
            proposedContextName = "RemoteContext" + counter++;
            if (config.lookupSAFRemoteContext(proposedContextName) == null) {
               finalName = proposedContextName;
               found = true;
            }
         }

         SAFRemoteContextBean remoteContext = config.createSAFRemoteContext(finalName);
         SAFLoginContextBean loginContext = remoteContext.getSAFLoginContext();
         loginContext.setLoginURL(url);
         if (username != null) {
            loginContext.setUsername(username);
         }

         return finalName;
      }
   }

   private void go(String[] args) throws Throwable {
      try {
         this.optionsParser.grok(args);
      } catch (IllegalArgumentException var18) {
         this.optionsParser.usageError("weblogic.jms.extensions.ClientSAFGenerate");
         return;
      }

      String existingFile = this.optionsParser.getOption("existingClientFile");
      if (existingFile != null) {
         this.config = ClientSAFParser.createClientSAFDescriptor(existingFile);
      } else {
         this.config = (ClientSAFBean)(new DescriptorManager()).createDescriptorRoot(ClientSAFBean.class).getRootBean();
         this.initFile();
      }

      String url = this.optionsParser.getOption("url");
      String username = this.optionsParser.getOption("username");
      String remoteContextName = this.generateRemoteContext(this.config, url, username);
      String[] localFiles = this.optionsParser.getMultiOption("moduleFile", new String[100]);

      for(int lcv = 0; lcv < localFiles.length; ++lcv) {
         String localFile = localFiles[lcv];
         StringTokenizer tokenizer = new StringTokenizer(localFile, "@");
         int numTokens = tokenizer.countTokens();
         String fileName = tokenizer.nextToken();
         String planName = null;
         if (numTokens > 1) {
            planName = tokenizer.nextToken();
         }

         File fileFile = new File(fileName);
         String shortName = fileFile.getName().toLowerCase();
         JMSBean jmsBean = JMSParser.createJMSDescriptor(fileName, planName);
         int index = shortName.indexOf("-jms.xml");
         String idName = shortName.substring(0, index);
         this.extractFromJMSBean(idName, remoteContextName, jmsBean);
      }

      String outFileName = this.optionsParser.getOption("outputFile");
      if (outFileName == null) {
         DescriptorUtils.writeAsXML((DescriptorBean)this.config);
      } else {
         File outFile = new File(outFileName);
         OutputStream outStream = new FileOutputStream(outFile);
         Descriptor desc = ((DescriptorBean)this.config).getDescriptor();
         (new EditableDescriptorManager()).writeDescriptorAsXML(desc, outStream);
      }
   }

   public static void main(String[] args) {
      ClientSAFGenerate gen = new ClientSAFGenerate();

      try {
         gen.go(args);
      } catch (Throwable var5) {
         int lcv = 0;

         for(Throwable cause = var5; cause != null; cause = cause.getCause()) {
            System.err.println("\nERROR: run threw an exception: level " + lcv);
            ++lcv;
            cause.printStackTrace();
         }
      }

   }
}
