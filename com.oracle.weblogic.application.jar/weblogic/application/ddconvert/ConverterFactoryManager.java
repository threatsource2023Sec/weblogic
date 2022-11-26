package weblogic.application.ddconvert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.utils.jars.VirtualJarFile;

public enum ConverterFactoryManager {
   instance;

   private List factories = new ArrayList();

   public void addConverterFactory(ConverterFactory factory) {
      this.factories.add(factory);
   }

   private ModuleType identifyType(ConvertCtx ctx) throws IOException {
      ModuleType type = null;
      Iterator var3 = this.factories.iterator();

      do {
         if (!var3.hasNext()) {
            return type;
         }

         ConverterFactory factory = (ConverterFactory)var3.next();
         if (ctx.hasApplicationArchive()) {
            type = factory.identifyType(ctx.getApplicationArchive());
            if (type == null) {
               type = factory.identifyType(ctx.getAppVJF());
            }
         } else {
            type = factory.identifyType(ctx.getAppVJF());
         }
      } while(type == null);

      return type;
   }

   public Converter[] findConverters(ConvertCtx ctx, VirtualJarFile vJar) throws DDConvertException, IOException {
      ModuleType type = this.identifyType(vJar);
      List matchingConverters = new ArrayList();
      Iterator var5 = this.factories.iterator();

      while(var5.hasNext()) {
         ConverterFactory factory = (ConverterFactory)var5.next();
         if (ctx.isVerbose()) {
            ConvertCtx.debug("Trying factory " + factory.getClass().getName());
         }

         Converter c = factory.newConverter(type);
         if (ctx.isVerbose()) {
            ConvertCtx.debug("Called Factory " + factory.getClass().getName() + " recognized application: " + (c != null));
         }

         if (c != null) {
            matchingConverters.add(c);
         }
      }

      return (Converter[])matchingConverters.toArray(new Converter[0]);
   }

   private ModuleType identifyType(VirtualJarFile vJar) throws IOException {
      ModuleType type = null;
      Iterator var3 = this.factories.iterator();

      do {
         if (!var3.hasNext()) {
            return null;
         }

         ConverterFactory factory = (ConverterFactory)var3.next();
         type = factory.identifyType(vJar);
      } while(type == null);

      return type;
   }
}
