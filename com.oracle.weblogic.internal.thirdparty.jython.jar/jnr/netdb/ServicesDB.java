package jnr.netdb;

import java.util.Collection;

interface ServicesDB {
   Service getServiceByName(String var1, String var2);

   Service getServiceByPort(Integer var1, String var2);

   Collection getAllServices();
}
