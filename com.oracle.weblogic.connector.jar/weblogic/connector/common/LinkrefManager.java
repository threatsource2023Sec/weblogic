package weblogic.connector.common;

import java.security.AccessController;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import weblogic.connector.deploy.ClassLoaderUtil;
import weblogic.connector.deploy.DeployerUtil;
import weblogic.connector.exception.RAException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.classloaders.GenericClassLoader;

public class LinkrefManager {
   private static Hashtable baseRAs = new Hashtable();
   private static Hashtable linkrefRAs = new Hashtable();
   private static final String CLASS_NAME = "weblogic.connector.common.LinkrefManager";

   public static synchronized void addBaseRA(RAInstanceManager baseRA) {
      Debug.enter("weblogic.connector.common.LinkrefManager", "addBaseRA(...)");

      try {
         String connectionFactoryName = baseRA.getRAInfo().getConnectionFactoryName();
         Debug.println((Object)"weblogic.connector.common.LinkrefManager", (String)(".addBaseRA() Connection factory name : " + connectionFactoryName));
         if (connectionFactoryName != null && connectionFactoryName.length() > 0) {
            Debug.println((Object)"weblogic.connector.common.LinkrefManager", (String)".addBaseRA() Adding the base RA to the hashtable");
            baseRAs.put(connectionFactoryName, baseRA);
         }
      } finally {
         Debug.exit("weblogic.connector.common.LinkrefManager", "addBaseRA(...)");
      }

   }

   public static synchronized void removeBaseRA(RAInstanceManager baseRA, RAException rae) {
      Debug.enter("weblogic.connector.common.LinkrefManager", "removeBaseRA(...)");

      try {
         String connectionFactoryName = baseRA.getRAInfo().getConnectionFactoryName();
         if (connectionFactoryName != null && connectionFactoryName.length() > 0) {
            Debug.println((Object)"weblogic.connector.common.LinkrefManager", (String)(".removeBaseRA() Removing base RA with connection factory name : " + connectionFactoryName));
            baseRAs.remove(connectionFactoryName);
         }
      } catch (Throwable var6) {
         rae.addError(var6);
      } finally {
         Debug.exit("weblogic.connector.common.LinkrefManager", "removeBaseRA(...)");
      }

   }

   public static synchronized void addLinkrefRA(RAInstanceManager linkrefRA) {
      Debug.enter("weblogic.connector.common.LinkrefManager", "addLinkrefRA(...)");

      try {
         String connectionFactoryName = linkrefRA.getRAInfo().getLinkref();
         Debug.println((Object)"weblogic.connector.common.LinkrefManager", (String)(".addLinkrefRA() Adding linkref with connectionFactory : " + connectionFactoryName));
         if (connectionFactoryName != null && connectionFactoryName.length() > 0) {
            List linkrefList = (List)linkrefRAs.get(connectionFactoryName);
            if (linkrefList == null) {
               Debug.println((Object)"weblogic.connector.common.LinkrefManager", (String)".addLinkrefRA() This is the first linkref RA being added under the connection factory name");
               linkrefList = new Vector(10);
               linkrefRAs.put(connectionFactoryName, linkrefList);
            }

            if (!((List)linkrefList).contains(linkrefRA)) {
               Debug.println((Object)"weblogic.connector.common.LinkrefManager", (String)".addLinkrefRA() Adding to the list");
               ((List)linkrefList).add(linkrefRA);
            }
         } else {
            Debug.throwAssertionError("The linkref does not have a connection factory defined.");
         }
      } finally {
         Debug.exit("weblogic.connector.common.LinkrefManager", "addLinkrefRA(...)");
      }

   }

   public static synchronized void removeLinkrefRA(RAInstanceManager linkrefRA, RAException rae) {
      Debug.enter("weblogic.connector.common.LinkrefManager", "removeLinkrefRA(...)");

      try {
         String connectionFactoryName = linkrefRA.getRAInfo().getLinkref();
         if (connectionFactoryName != null && connectionFactoryName.length() > 0) {
            List linkrefList = (List)linkrefRAs.get(connectionFactoryName);
            if (linkrefList != null) {
               linkrefList.remove(linkrefRA);
               if (linkrefList.size() == 0) {
                  linkrefRAs.remove(connectionFactoryName);
               }
            }
         } else {
            Debug.throwAssertionError("The connection factory for the linkref has not been specified.");
         }
      } catch (Throwable var7) {
         rae.addError(var7);
      } finally {
         Debug.exit("weblogic.connector.common.LinkrefManager", "removeLinkrefRA(...)");
      }

   }

   public static void deployDependentLinkrefs(RAInstanceManager baseRA) {
      Debug.enter("weblogic.connector.common.LinkrefManager", "deployDependentLinkrefs(...)");

      try {
         String connectionFactoryName = baseRA.getRAInfo().getConnectionFactoryName();
         Debug.println("Connection factory name : " + connectionFactoryName);
         if (connectionFactoryName != null && connectionFactoryName.length() > 0) {
            Debug.println("Get the list of linkrefs waiting to be deployed");
            List linkrefList = (List)linkrefRAs.get(connectionFactoryName);
            List doneList = new Vector(10);
            List errorList = new Vector(10);
            if (linkrefList != null) {
               Debug.println("Number of linkref to be deployed : " + linkrefList.size());
               Iterator iterator = linkrefList.iterator();

               while(iterator.hasNext()) {
                  RAInstanceManager linkrefRA = (RAInstanceManager)iterator.next();

                  try {
                     Debug.println("Update the classloader with the base jar");
                     GenericClassLoader gcl = ClassLoaderUtil.getClassloader4RA(linkrefRA.getRAInfo(), linkrefRA.getAppContext(), (GenericClassLoader)linkrefRA.getClassloader());
                     DeployerUtil.updateClassFinder(gcl, baseRA.getRarArchive(), linkrefRA.getClassFinders());
                     Debug.println("Update the RAInfo of this linkref with the base raInfo");
                     linkrefRA.getRAInfo().setBaseRA(baseRA.getRAInfo());
                     Debug.println("Set the late deploy flag to false");
                     linkrefRA.setLateDeploy(false);
                     Debug.println("Initialize the ra");
                     if (Debug.isDeploymentEnabled()) {
                        Debug.deployment("Starting to deploy the link-ref RA module '" + linkrefRA.getModuleName() + "' with base RA module '" + baseRA.getModuleName() + "'");
                     }

                     AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                     linkrefRA.initialize();
                     Debug.println("prepare");
                     linkrefRA.prepare();
                     Debug.println("activate");
                     linkrefRA.activate();
                     doneList.add(linkrefRA);
                     if (Debug.isDeploymentEnabled()) {
                        Debug.deployment("Succeeded in deploying the link-ref RA module '" + linkrefRA.getModuleName() + "' with base RA module '" + baseRA.getModuleName() + "'");
                     }
                  } catch (Throwable var13) {
                     errorList.add(linkrefRA);
                     String msgId = Debug.logFailedToDeployLinkRef(linkrefRA.getModuleName(), baseRA.getModuleName(), var13.toString());
                     Debug.logStackTrace(msgId, var13);
                     if (Debug.isDeploymentEnabled()) {
                        AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                        Debug.deployment("Failed in deploying the link-ref RA module '" + linkrefRA.getModuleName() + "' with base RA module '" + baseRA.getModuleName() + "'. Caught exception with stack trace:\n" + linkrefRA.getAdapterLayer().throwable2StackTrace(var13, kernelId));
                     }
                  }
               }

               linkrefRAs.remove(connectionFactoryName);
            } else {
               Debug.println("No linkrefs to deploy");
            }
         } else {
            Debug.println("Connection factory name is null or empty. No dependent linkrefs to deploy");
         }
      } finally {
         Debug.exit("weblogic.connector.common.LinkrefManager", "deployDependentLinkrefs(...)");
      }

   }

   public static synchronized RAInstanceManager getBaseRA(String connectionFactoryName) {
      Debug.enter("weblogic.connector.common.LinkrefManager", "getBaseRA(...)");
      RAInstanceManager returnRA = null;
      if (connectionFactoryName != null && connectionFactoryName.length() > 0) {
         Debug.println("Trying to get the base RA for connection factory : " + connectionFactoryName);
         returnRA = (RAInstanceManager)baseRAs.get(connectionFactoryName);
      }

      Debug.exit("weblogic.connector.common.LinkrefManager", "getBaseRA(...)");
      return returnRA;
   }
}
