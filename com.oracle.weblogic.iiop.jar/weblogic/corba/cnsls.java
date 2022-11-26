package weblogic.corba;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.InitialContext;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import weblogic.corba.j2ee.naming.ORBHelper;
import weblogic.corba.j2ee.naming.Utils;
import weblogic.utils.Getopt2;

public final class cnsls {
   private static String objRefFileName = null;
   private static String globalResolveName = null;
   private static boolean dumpStringRef = false;
   private static boolean dumpRecursive = false;
   private static ORB theORB = null;
   private static String user = null;
   private static String password = null;
   private static InitialContext ctx = null;
   private static String insUrl = "iiop://localhost:7001";
   private static final String CONTEXT_DESIGNATOR = "[context] ";
   private static final String OBJECT_DESIGNATOR = "[object]  ";
   private static final String INDENT = "    ";
   private static final String PROGRAM = "weblogic.corba.cnsls";

   public static void main(String[] args) {
      processCLOpts(args);
      if (System.getProperty("weblogic.system.iiop.enableClient") == null) {
         System.setProperty("weblogic.system.iiop.enableClient", "false");
      }

      NamingContextExt root = null;

      try {
         if (objRefFileName != null) {
            FileReader file = new FileReader(objRefFileName);
            StringBuilder sb = new StringBuilder();

            int c;
            while((c = file.read()) >= 48) {
               sb.append((char)c);
            }

            theORB = ORB.init(new String[0], (Properties)null);
            getInitialContext();
            Object obj = theORB.string_to_object(sb.toString());
            root = NamingContextExtHelper.narrow(obj);
         } else {
            theORB = ORBHelper.getORBHelper().getORB(insUrl, (Hashtable)null);
            getInitialContext();
            Object obj = theORB.resolve_initial_references("NameService");
            root = NamingContextExtHelper.narrow(obj);
         }
      } catch (Exception var13) {
         System.err.println("Error retrieving name service root object reference: " + var13.getMessage());
         System.exit(1);
      }

      try {
         root.to_name("");
      } catch (Exception var8) {
         System.err.println("Error connecting to name service, please make sure the name service is running.");
         System.exit(1);
      }

      NameComponent[] resolveName = null;
      Object obj = null;
      String err;
      if (globalResolveName != null && globalResolveName.length() != 0) {
         try {
            resolveName = root.to_name(globalResolveName);
         } catch (Exception var7) {
            System.err.println("Error, invalid name: " + globalResolveName);
            System.exit(1);
         }

         try {
            obj = root.resolve(resolveName);
         } catch (NotFound var9) {
            err = "Error, ";
            switch (var9.why.value()) {
               case 0:
                  err = err + "missing node: ";
                  break;
               case 1:
                  err = err + "not context: ";
                  break;
               case 2:
                  err = err + "not object: ";
            }

            err = err + Arrays.toString(var9.rest_of_name);
            System.out.println(err);
            System.exit(1);
         } catch (InvalidName var10) {
            System.out.println("Error, invalid name");
            System.exit(1);
         } catch (CannotProceed var11) {
            System.out.println("Error, cannot proceed: " + Arrays.toString(var11.rest_of_name));
            System.exit(1);
         } catch (Exception var12) {
            var12.printStackTrace();
            System.err.println("CORBA Exception: " + var12.getMessage());
            System.exit(1);
         }
      } else {
         obj = root._duplicate();
         resolveName = new NameComponent[]{new NameComponent("<root>", "")};
      }

      try {
         if (obj._is_a(NamingContextHelper.id())) {
            NamingContext nc = NamingContextHelper.narrow(obj);
            err = root.to_string(resolveName);
            System.out.println("\n[context] " + err);
            if (dumpStringRef) {
               dumpStringRef(theORB, obj);
            }

            dumpBindings(nc, 1);
         } else {
            String name = root.to_string(resolveName);
            System.out.println("\n[object]  " + name);
            if (dumpStringRef) {
               dumpStringRef(theORB, obj);
            }
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         System.err.println("CORBA Exception: " + var6.getMessage());
         System.exit(1);
      }

   }

   private static void processCLOpts(String[] args) {
      Getopt2 getopt = new Getopt2();

      try {
         getopt.addFlag("h", "Help");
         getopt.addFlag("s", "Dump stringified IORs");
         getopt.addFlag("R", "Dump contexts recursively");
         getopt.addOption("f", "filename", "IOR filename");
         getopt.addOption("u", "url", "Server URL");
         getopt.addOption("n", "user", "User");
         getopt.addOption("p", "password", "Password");
         getopt.setUsageArgs("[name]");
         if (args.length == 0) {
            getopt.usageAndExit("weblogic.corba.cnsls");
         }

         getopt.grok(args);
         if (getopt.args().length > 0) {
            globalResolveName = getopt.args()[0];
         }

         if (getopt.hasOption("h")) {
            getopt.usageAndExit("weblogic.corba.cnsls");
         }

         objRefFileName = getopt.getOption("f");
         dumpStringRef = getopt.hasOption("s");
         dumpRecursive = getopt.hasOption("R");
         if (getopt.hasOption("u")) {
            insUrl = getopt.getOption("u");
         }

         if (getopt.hasOption("n")) {
            user = getopt.getOption("n");
         }

         if (getopt.hasOption("p")) {
            password = getopt.getOption("p");
         }
      } catch (IllegalArgumentException var3) {
         System.out.println("Invalid option: " + var3.getMessage());
         getopt.usageAndExit("weblogic.corba.cnsls");
      }

   }

   private static void dumpBindings(NamingContext nc, int indentLevel) {
      BindingListHolder bindings = new BindingListHolder();
      BindingIteratorHolder iter = new BindingIteratorHolder();
      nc.list(20, bindings, iter);
      dumpBindingList(bindings, nc, indentLevel);
      if (iter.value != null) {
         int retrieved = bindings.value.length;

         boolean more;
         do {
            try {
               more = iter.value.next_n(20, bindings);
               retrieved += bindings.value.length;
               dumpBindingList(bindings, nc, indentLevel);
            } catch (OBJECT_NOT_EXIST var8) {
               nc.list(retrieved, bindings, iter);
               more = true;
            }
         } while(iter.value != null && more);

         try {
            if (iter.value != null) {
               iter.value.destroy();
            }
         } catch (Exception var7) {
         }
      }

   }

   private static void dumpBindingList(BindingListHolder bindings, NamingContext nc, int indentLevel) {
      for(int i = 0; i < bindings.value.length; ++i) {
         String indent = "";

         for(int j = 0; j < indentLevel; ++j) {
            indent = indent + "    ";
         }

         String name = Utils.nameComponentToString(bindings.value[i].binding_name);
         Object obj = null;
         if (dumpStringRef || dumpRecursive) {
            try {
               obj = nc.resolve(bindings.value[i].binding_name);
            } catch (Exception var11) {
            }
         }

         if (bindings.value[i].binding_type.value() == 0) {
            System.out.println(indent + "[object]  " + name);
            if (dumpStringRef) {
               dumpStringRef(theORB, obj);
            }
         } else {
            System.out.println(indent + "[context] " + name);
            if (dumpStringRef) {
               dumpStringRef(theORB, obj);
            }

            if (dumpRecursive) {
               NamingContext ctx = null;

               try {
                  ctx = NamingContextHelper.narrow(obj);
               } catch (Exception var10) {
               }

               try {
                  dumpBindings(ctx, indentLevel + 1);
               } catch (Exception var9) {
                  System.out.println("Error dumping bindings for " + bindings.value[i].binding_name[0].id);
               }
            }
         }
      }

   }

   private static void dumpStringRef(ORB orb, Object objRef) {
      String str;
      if (objRef != null) {
         str = orb.object_to_string(objRef);
      } else {
         str = "<nil>";
      }

      System.out.println("\n" + str);
   }

   private static void getInitialContext() {
      if (user != null && password != null) {
         Properties p = new Properties();
         p.put("java.naming.security.principal", user);
         p.put("java.naming.security.credentials", password);
         p.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         p.put("java.naming.provider.url", insUrl);

         try {
            ctx = new InitialContext(p);
         } catch (Exception var2) {
            System.err.println("Error creating initial context, please make sure the username, password is correct: " + var2.getMessage());
            System.exit(1);
         }
      }

   }
}
