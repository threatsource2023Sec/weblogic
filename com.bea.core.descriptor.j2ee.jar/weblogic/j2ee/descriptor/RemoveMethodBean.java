package weblogic.j2ee.descriptor;

public interface RemoveMethodBean {
   NamedMethodBean getBeanMethod();

   NamedMethodBean createBeanMethod();

   void destroyBeanMethod(NamedMethodBean var1);

   boolean isRetainIfException();

   void setRetainIfException(boolean var1);

   String getId();

   void setId(String var1);
}
