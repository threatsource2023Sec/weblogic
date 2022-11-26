package weblogic.j2ee.descriptor.wl;

public interface DeploymentListenerListBean {
   String[] getDeploymentListeners();

   void addDeploymentListener(String var1);

   void removeDeploymentListener(String var1);

   void setDeploymentListeners(String[] var1);
}
