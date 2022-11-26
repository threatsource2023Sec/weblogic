package weblogic.j2ee.descriptor.wl;

public interface WeblogicConnectorExtensionBean extends WeblogicConnectorBean {
   LinkRefBean getLinkRef();

   LinkRefBean createLinkRef();

   void destroyLinkRef(LinkRefBean var1);

   ProxyBean getProxy();

   ProxyBean createProxy();

   void destroyProxy(ProxyBean var1);
}
