package weblogic.ant.taskdefs.management;

import weblogic.Deployer;

public class DeployerWrapper {
   public static void main(String[] args) throws Exception {
      Deployer.mainWithExceptions(args);
   }
}
