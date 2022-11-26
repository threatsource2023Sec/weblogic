package weblogic.j2ee.descriptor;

public interface WebFragmentBean extends WebAppBaseBean {
   String[] getNames();

   OrderingBean[] getOrderings();

   OrderingBean createOrdering();

   void destroyOrdering(OrderingBean var1);

   String getVersion();

   void setVersion(String var1);
}
