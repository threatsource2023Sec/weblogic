package com.bea.common.security.internal.utils;

import com.bea.common.logger.spi.LoggerSpi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import weblogic.security.auth.callback.GroupCallback;
import weblogic.security.auth.callback.IdentityDomainGroupCallback;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.auth.callback.IdentityDomainUserCallback;
import weblogic.security.spi.VirtualUserHandler;

public class CallbackUtils {
   public static IdentityDomainNames getUser(CallbackHandler callbackHandler, LoggerSpi logger) {
      boolean debug = logger == null ? false : logger.isDebugEnabled();
      String method = debug ? CallbackUtils.class.getName() + ".getUser" : null;
      if (debug) {
         logger.debug(method);
      }

      Callback[] callbacks = new Callback[1];

      try {
         IdentityDomainUserCallback iddCallback = new IdentityDomainUserCallback("username");
         callbacks[0] = iddCallback;
         callbackHandler.handle(callbacks);
         IdentityDomainNames user = iddCallback.getUser();
         if (debug) {
            logger.debug(method + " returning " + user);
         }

         return user;
      } catch (IOException var9) {
         if (debug) {
            logger.debug(method + " returning null user since received IOException");
         }

         return null;
      } catch (UnsupportedCallbackException var10) {
         if (debug) {
            logger.debug(method + " received UnsupportedCallbackException for IdentityDomainUserCallback, trying NameCallback");
         }

         try {
            NameCallback nameCallback = new NameCallback("username");
            callbacks[0] = nameCallback;
            callbackHandler.handle(callbacks);
            String username = nameCallback.getName();
            if (debug) {
               logger.debug(method + " returning " + username);
            }

            return new IdentityDomainNames(username, (String)null);
         } catch (IOException var7) {
            if (debug) {
               logger.debug(method + " returning null user since received IOException");
            }

            return null;
         } catch (UnsupportedCallbackException var8) {
            if (debug) {
               logger.debug(method + " returning null user since received UnsupportedCallbackException");
            }

            return null;
         }
      }
   }

   public static Collection getGroups(CallbackHandler callbackHandler, LoggerSpi logger) {
      boolean debug = logger == null ? false : logger.isDebugEnabled();
      String method = debug ? CallbackUtils.class.getName() + ".getUser" : null;
      if (debug) {
         logger.debug(method);
      }

      Callback[] callbacks = new Callback[1];

      Collection groups;
      try {
         IdentityDomainGroupCallback iddCallback = new IdentityDomainGroupCallback("groups: ");
         callbacks[0] = iddCallback;
         callbackHandler.handle(callbacks);
         groups = iddCallback.getGroups();
         if (debug) {
            logger.debug(method + " returning " + groups);
         }

         return groups;
      } catch (IOException var12) {
         if (debug) {
            logger.debug(method + " returning null groups since received IOException");
         }

         return null;
      } catch (UnsupportedCallbackException var13) {
         if (debug) {
            logger.debug(method + " received UnsupportedCallbackException for IdentityDomainGroupCallback, trying GroupCallback");
         }

         try {
            GroupCallback groupCallback = new GroupCallback("groups: ");
            callbacks[0] = groupCallback;
            callbackHandler.handle(callbacks);
            groups = groupCallback.getValue();
            if (debug) {
               logger.debug(method + " returning " + groups);
            }

            Collection iddGroups = new ArrayList();
            Iterator var8 = groups.iterator();

            while(var8.hasNext()) {
               String groupName = (String)var8.next();
               iddGroups.add(new IdentityDomainNames(groupName, (String)null));
            }

            return iddGroups;
         } catch (IOException var10) {
            if (debug) {
               logger.debug(method + " returning null groups since received IOException");
            }

            return null;
         } catch (UnsupportedCallbackException var11) {
            if (debug) {
               logger.debug(method + " returning null groups since received UnsupportedCallbackException");
            }

            return null;
         }
      }
   }

   public static boolean isVirtualUserAllowed(CallbackHandler callbackHandler) {
      return callbackHandler instanceof VirtualUserHandler ? ((VirtualUserHandler)callbackHandler).isVirtualUserAllowed() : false;
   }
}
