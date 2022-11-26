package com.bea.util.jam;

import com.bea.util.jam.internal.JamPrinter;
import com.bea.util.jam.provider.JamServiceFactoryImpl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class JamServiceFactory {
   private static final JamServiceFactory DEFAULT = new JamServiceFactoryImpl();

   public static JamServiceFactory getInstance() {
      return DEFAULT;
   }

   protected JamServiceFactory() {
   }

   public abstract JamServiceParams createServiceParams();

   public abstract JamService createService(JamServiceParams var1) throws IOException;

   public abstract JamClassLoader createSystemJamClassLoader();

   public abstract JamClassLoader createJamClassLoader(ClassLoader var1);

   public static void main(String[] args) {
      try {
         JamServiceParams sp = getInstance().createServiceParams();

         for(int i = 0; i < args.length; ++i) {
            sp.includeSourcePattern(new File[]{new File(".")}, args[i]);
         }

         JamService service = getInstance().createService(sp);
         JamPrinter jp = JamPrinter.newInstance();
         PrintWriter out = new PrintWriter(System.out);
         JamClassIterator i = service.getClasses();

         while(i.hasNext()) {
            out.println("-------- ");
            jp.print((JElement)i.nextClass(), out);
         }

         out.flush();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      System.out.flush();
      System.err.flush();
   }
}
