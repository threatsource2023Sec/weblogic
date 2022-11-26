package weblogic.j2ee.descriptor.wl;

public interface RestWebservicesBean {
   RestWebserviceDescriptionBean[] getRestWebserviceDescriptions();

   RestWebserviceDescriptionBean createRestWebserviceDescription();

   void destroyRestWebserviceDescription(RestWebserviceDescriptionBean var1);
}
