package weblogic.sca.descriptor;

public interface CompositeBean {
   String getName();

   void setName(String var1);

   ComponentBean[] getComponents();

   ComponentBean createComponent();

   void destroyComponent(ComponentBean var1);
}
