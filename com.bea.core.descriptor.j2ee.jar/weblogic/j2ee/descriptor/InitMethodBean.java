package weblogic.j2ee.descriptor;

public interface InitMethodBean {
   NamedMethodBean getCreateMethod();

   NamedMethodBean createCreateMethod();

   void destroyCreateMethod(NamedMethodBean var1);

   NamedMethodBean getBeanMethod();

   NamedMethodBean createBeanMethod();

   void destroyBeanMethod(NamedMethodBean var1);

   String getId();

   void setId(String var1);
}
