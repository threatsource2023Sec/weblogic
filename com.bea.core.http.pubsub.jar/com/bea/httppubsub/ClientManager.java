package com.bea.httppubsub;

public interface ClientManager {
   void init();

   void destroy();

   Client createClient();

   LocalClient createLocalClient();

   Client findClient(String var1);

   void addClient(Client var1);

   void removeClient(Client var1);
}
