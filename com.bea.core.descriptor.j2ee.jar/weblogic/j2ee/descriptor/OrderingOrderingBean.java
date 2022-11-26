package weblogic.j2ee.descriptor;

public interface OrderingOrderingBean {
   String[] getNames();

   void addName(String var1);

   void removeName(String var1);

   void setNames(String[] var1);

   OrderingOthersBean getOthers();

   OrderingOthersBean createOther();

   void destroyOther(OrderingOthersBean var1);
}
