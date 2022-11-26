package weblogic.jndi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.omg.PortableServer.Servant;
import weblogic.common.internal.PassivationUtils;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.diagnostics.image.PartitionAwareImageSource;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.utils.classloaders.GenericClassLoader;

public final class JNDIImageSource implements ImageSource, JNDIImageSourceConstants, PartitionAwareImageSource {
   private boolean timedOutCreatingImage;
   private final Map usedCLs;

   public void createDiagnosticImage(String partitionName, OutputStream out) throws ImageSourceCreationException {
      Context partitionCtx = null;

      try {
         partitionCtx = getContext(partitionName);
      } catch (NamingException var5) {
         throw new ImageSourceCreationException("Can't get partition context for " + partitionName, var5);
      }

      this.createDiagnosticImageWith(out, partitionCtx);
   }

   public static final ImageSource getJNDIImageSource() {
      return JNDIImageSource.SingletonMaker.singleton;
   }

   private JNDIImageSource() {
      this.usedCLs = new HashMap();
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      Context ctx = null;

      try {
         ctx = getContext((String)null);
      } catch (NamingException var4) {
         var4.printStackTrace();
         throw new ImageSourceCreationException("Can't get Context", var4);
      }

      this.createDiagnosticImageWith(out, ctx);
   }

   private void createDiagnosticImageWith(OutputStream out, Context ctx) {
      ClassLoader oldCl = Thread.currentThread().getContextClassLoader();

      try {
         GenericClassLoader tempCL = (GenericClassLoader)this.usedCLs.get(oldCl);
         if (tempCL == null) {
            tempCL = new GenericClassLoader(oldCl);
            this.usedCLs.put(oldCl, tempCL);
         }

         Thread.currentThread().setContextClassLoader(tempCL);
         PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
         this.writeContextInfo(writer, ctx.getNameInNamespace());
         Set visitedContextNames = new HashSet();
         this.printContextInfo(writer, ctx, visitedContextNames);
         if (!this.timedOutCreatingImage && !writer.checkError()) {
            writer.flush();
         }
      } catch (NamingException var11) {
      } catch (IOException var12) {
      } finally {
         this.closeContext(ctx);
         Thread.currentThread().setContextClassLoader(oldCl);
      }

   }

   private void printContextInfo(PrintWriter writer, Context ctx, Set visitedContextNames) throws NamingException, IOException {
      String parentName = ctx.getNameInNamespace();
      visitedContextNames.add(parentName);
      NamingEnumeration enumerator = ctx.list("");

      while(enumerator.hasMoreElements()) {
         NameClassPair pair = (NameClassPair)enumerator.nextElement();
         String name = pair.getName();
         String className = pair.getClassName();

         try {
            Object bound = ctx.lookup(name);
            if (bound instanceof Context) {
               Context context = (Context)bound;
               String childName = context.getNameInNamespace();
               if (visitedContextNames.contains(childName)) {
                  writer.println("WARNING: Found cycle involving parent=" + parentName + " child=" + childName);
                  if (NamingDebugLogger.isDebugEnabled()) {
                     NamingDebugLogger.debug("A cycle is found in the JNDI tree involving the parent node " + parentName + " and child node " + childName);
                  }
               } else {
                  this.writeContextInfo(writer, childName);
                  this.printContextInfo(writer, context, visitedContextNames);
               }
            } else {
               this.writeBindingInfo(writer, new Binding(name, pair.getClassName(), bound));
            }
         } catch (Exception var12) {
         }
      }

      writer.println("</context>");
      visitedContextNames.remove(parentName);
   }

   private void closeContext(Context ctx) {
      if (ctx != null) {
         try {
            ctx.close();
         } catch (NamingException var3) {
         }
      }

   }

   public void timeoutImageCreation() {
      this.timedOutCreatingImage = true;
   }

   private void writeContextInfo(PrintWriter writer, String name) {
      writer.println();
      writer.print("<context");
      writer.println(">");
      writer.print("name=\"");
      writer.print(name);
      writer.println("\"");
   }

   private void writeBindingInfo(PrintWriter writer, Binding binding) throws IOException {
      Object bound = binding.getObject();
      short type = this.getObjectType(bound);
      writer.println("<binding");
      writer.print("jndi-name=\"");
      writer.print(binding.getName());
      writer.println("\"");
      writer.print("class-name=\"");
      writer.print(binding.getClassName());
      writer.println("\"");
      writer.print("size=\"");
      printObjectSize(writer, type, bound);
      writer.println("\"");
      writer.print("type=\"");
      getTypeAsString(writer, type);
      writer.println("\"");
      writer.print("clusterable=\"");
      if (type == 1) {
         if (ServerHelper.isClusterable((Remote)bound)) {
            writer.print("true");
         } else {
            writer.print("false");
         }
      } else {
         writer.print("false");
      }

      writer.println("\"");
      writer.print("string-representation=\"");
      writer.print(bound.toString());
      writer.print("\"");
      writer.println(">");
      writer.println("</binding>");
   }

   private short getObjectType(Object object) {
      if (isCorbaObject(object)) {
         return 0;
      } else if (object instanceof Remote) {
         return 1;
      } else if (object instanceof Externalizable) {
         return 2;
      } else if (object == "non-serializable") {
         return 4;
      } else {
         return (short)(object instanceof Serializable ? 3 : 4);
      }
   }

   private static boolean isCorbaObject(Object object) {
      return object instanceof org.omg.CORBA.Object || object instanceof Servant;
   }

   private static void getTypeAsString(PrintWriter writer, short type) {
      switch (type) {
         case 0:
            writer.print("corba");
            break;
         case 1:
            writer.print("remote");
            break;
         case 2:
            writer.print("externalizable");
            break;
         case 3:
            writer.print("serializable");
            break;
         case 4:
            writer.print("non-serializable");
            break;
         default:
            throw new AssertionError("Unexpected type " + type);
      }

   }

   private static void printObjectSize(PrintWriter writer, short type, Object o) throws IOException {
      switch (type) {
         case 0:
            writer.print(0);
            break;
         case 1:
            writer.print(0);
            break;
         case 2:
            writer.print(PassivationUtils.sizeOf(o));
            break;
         case 3:
            writer.print(PassivationUtils.sizeOf(o));
            break;
         case 4:
            writer.print(-1);
            break;
         default:
            throw new AssertionError("Unexpected type " + type);
      }

   }

   static Context getContext(String name) throws NamingException {
      Context ctx = new InitialContext();
      if (name != null) {
         ctx = (Context)((Context)ctx).lookup("partition:" + name);
      }

      return (Context)ctx;
   }

   // $FF: synthetic method
   JNDIImageSource(Object x0) {
      this();
   }

   private static class SingletonMaker {
      static final ImageSource singleton = new JNDIImageSource();
   }
}
