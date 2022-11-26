package weblogic.corba.j2ee.naming.url.corbaname;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import weblogic.corba.j2ee.naming.ContextImpl;
import weblogic.corba.j2ee.naming.InitialContextFactoryImpl;
import weblogic.corba.j2ee.naming.NameParser;

public class corbanameURLContextFactory implements ObjectFactory {
   private static final boolean DEBUG = false;

   private static void p(String s) {
      System.err.println("<corbanameURLContextFactory> " + s);
   }

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable env) throws Exception {
      if (obj == null) {
         return ContextImpl.createCorbanameUrlHandlingContext(env);
      } else {
         String url = null;
         Object ret = null;
         if (obj instanceof String) {
            url = (String)obj;
         } else if (obj instanceof Reference) {
            RefAddr refinfo = ((Reference)obj).get("nns");
            if (refinfo == null) {
               refinfo = ((Reference)obj).get("URL");
            }

            if (refinfo instanceof StringRefAddr) {
               url = (String)refinfo.getContent();
            }
         }

         if (NameParser.getProtocol(url) != null) {
            ret = InitialContextFactoryImpl.getInitialContext(env, url);
         }

         return ret;
      }
   }
}
