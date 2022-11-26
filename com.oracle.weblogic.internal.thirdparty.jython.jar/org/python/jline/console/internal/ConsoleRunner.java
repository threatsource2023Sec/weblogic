package org.python.jline.console.internal;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import org.python.jline.console.ConsoleReader;
import org.python.jline.console.completer.ArgumentCompleter;
import org.python.jline.console.completer.Completer;
import org.python.jline.console.history.FileHistory;
import org.python.jline.console.history.PersistentHistory;
import org.python.jline.internal.Configuration;

public class ConsoleRunner {
   public static final String property = "org.python.jline.history";

   public static void main(String[] args) throws Exception {
      List argList = new ArrayList(Arrays.asList(args));
      if (argList.size() == 0) {
         usage();
      } else {
         String historyFileName = System.getProperty("org.python.jline.history", (String)null);
         String mainClass = (String)argList.remove(0);
         ConsoleReader reader = new ConsoleReader();
         if (historyFileName != null) {
            reader.setHistory(new FileHistory(new File(Configuration.getUserHome(), String.format(".jline-%s.%s.history", mainClass, historyFileName))));
         } else {
            reader.setHistory(new FileHistory(new File(Configuration.getUserHome(), String.format(".jline-%s.history", mainClass))));
         }

         String completors = System.getProperty(ConsoleRunner.class.getName() + ".completers", "");
         List completorList = new ArrayList();
         StringTokenizer tok = new StringTokenizer(completors, ",");

         while(tok.hasMoreTokens()) {
            Object obj = Class.forName(tok.nextToken()).newInstance();
            completorList.add((Completer)obj);
         }

         if (completorList.size() > 0) {
            reader.addCompleter(new ArgumentCompleter(completorList));
         }

         ConsoleReaderInputStream.setIn(reader);

         try {
            Class type = Class.forName(mainClass);
            Method method = type.getMethod("main", String[].class);
            String[] mainArgs = (String[])argList.toArray(new String[argList.size()]);
            method.invoke((Object)null, mainArgs);
         } finally {
            ConsoleReaderInputStream.restoreIn();
            if (reader.getHistory() instanceof PersistentHistory) {
               ((PersistentHistory)reader.getHistory()).flush();
            }

         }

      }
   }

   private static void usage() {
      System.out.println("Usage: \n   java [-Djline.history='name'] " + ConsoleRunner.class.getName() + " <target class name> [args]\n\nThe -Djline.history option will avoid history\nmangling when running ConsoleRunner on the same application.\n\nargs will be passed directly to the target class name.");
   }
}
