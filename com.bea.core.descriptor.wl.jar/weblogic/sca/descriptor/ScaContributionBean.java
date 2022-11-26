package weblogic.sca.descriptor;

public interface ScaContributionBean {
   CompositeBean[] getComposites();

   CompositeBean createComposite();

   void destroyComposite(CompositeBean var1);
}
