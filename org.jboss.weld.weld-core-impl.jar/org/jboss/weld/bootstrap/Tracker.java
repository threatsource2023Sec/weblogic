package org.jboss.weld.bootstrap;

interface Tracker extends AutoCloseable {
   String OP_BOOTSTRAP = "bootstrap";
   String OP_START_CONTAINER = "startContainer";
   String OP_INIT_SERVICES = "initServices";
   String OP_CONTEXTS = "builtinContexts";
   String OP_READ_DEPLOYMENT = "readDeploymentStructure";
   String OP_START_INIT = "startInitialization";
   String OP_DEPLOY_BEANS = "deployBeans";
   String OP_VALIDATE_BEANS = "validateBeans";
   String OP_END_INIT = "endInitialization";
   String OP_BBD = "BeforeBeanDiscovery";
   String OP_ATD = "AfterTypeDiscovery";
   String OP_ABD = "AfterBeanDiscovery";
   String OP_ADV = "AfterDeploymentValidation";

   Tracker start(String var1);

   Tracker end();

   void split(String var1);

   void close();
}
