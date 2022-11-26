package weblogic.ejb.container.dd;

public final class ClusteringDescriptor {
   private boolean homeIsClusterable = true;
   private String homeLoadAlgorithm;
   private String homeCallRouterClassName;
   private boolean beanIsClusterable = true;
   private String beanLoadAlgorithm;
   private String beanCallRouterClassName;
   private boolean useServersideStubs;

   public void setHomeIsClusterable(boolean val) {
      this.homeIsClusterable = val;
   }

   public boolean getHomeIsClusterable() {
      return this.homeIsClusterable;
   }

   public void setHomeLoadAlgorithm(String algoStr) {
      this.homeLoadAlgorithm = algoStr;
   }

   public String getHomeLoadAlgorithm() {
      return this.homeLoadAlgorithm;
   }

   public void setHomeCallRouterClassName(String name) {
      this.homeCallRouterClassName = name;
   }

   public String getHomeCallRouterClassName() {
      return this.homeCallRouterClassName;
   }

   public void setBeanIsClusterable(boolean val) {
      this.beanIsClusterable = val;
   }

   public boolean getBeanIsClusterable() {
      return this.beanIsClusterable;
   }

   public void setBeanLoadAlgorithm(String algoStr) {
      this.beanLoadAlgorithm = algoStr;
   }

   public String getBeanLoadAlgorithm() {
      return this.beanLoadAlgorithm;
   }

   public void setBeanCallRouterClassName(String name) {
      this.beanCallRouterClassName = name;
   }

   public String getBeanCallRouterClassName() {
      return this.beanCallRouterClassName;
   }

   public boolean getUseServersideStubs() {
      return this.useServersideStubs;
   }

   public void setUseServersideStubs(boolean flag) {
      this.useServersideStubs = flag;
   }
}
