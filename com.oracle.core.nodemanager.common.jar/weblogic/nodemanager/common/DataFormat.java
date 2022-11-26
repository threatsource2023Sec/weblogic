package weblogic.nodemanager.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import weblogic.admin.plugin.ChangeList;
import weblogic.admin.plugin.NMComponentTypeChangeList;
import weblogic.admin.plugin.ChangeList.Change.Type;
import weblogic.utils.StringUtils;

public class DataFormat {
   public static final char EOS_CHAR = '.';
   public static final String EOL = "\r\n";
   public static final String OK = "+OK ";
   public static final String ERR = "-ERR ";
   public static final char FIELD_SEP = '|';

   public static void readProperties(NMInputOutput ioHandler, Properties props) throws IOException {
      String line;
      while((line = readLine(ioHandler)) != null) {
         int i = line.indexOf(61);
         if (i == -1) {
            throw new IOException("Bad properties data format");
         }

         props.setProperty(line.substring(0, i), line.substring(i + 1));
      }

   }

   public static void readPropMap(NMInputOutput ioHandler, Map props) throws IOException {
      String line;
      while((line = readLine(ioHandler)) != null) {
         int i = line.indexOf(61);
         if (i == -1) {
            throw new IOException("Bad properties data format");
         }

         props.put(line.substring(0, i), line.substring(i + 1));
      }

   }

   public static void writePropMap(NMInputOutput ioHandler, Map propMap) throws IOException {
      Map.Entry e;
      String value;
      for(Iterator var2 = propMap.entrySet().iterator(); var2.hasNext(); writeLine(ioHandler, (String)e.getKey() + '=' + value)) {
         e = (Map.Entry)var2.next();
         value = (String)e.getValue();
         if (value.indexOf("\n") > -1) {
            value = value.replace('\n', ' ');
         }
      }

      writeEOS(ioHandler);
   }

   public static void writeProperties(NMInputOutput ioHandler, Properties props) throws IOException {
      Map.Entry e;
      String value;
      for(Iterator var2 = props.entrySet().iterator(); var2.hasNext(); writeLine(ioHandler, (String)e.getKey() + '=' + value)) {
         e = (Map.Entry)var2.next();
         value = (String)e.getValue();
         if (value.indexOf("\n") > -1) {
            value = value.replace('\n', ' ');
         }
      }

      writeEOS(ioHandler);
   }

   public static void readV2NMComponentTypeChangeList(NMInputOutput ioHandler, NMComponentTypeChangeList nmChangeList) throws IOException {
      String line;
      while((line = readLine(ioHandler)) != null) {
         nmChangeList.addComponentName(line);
      }

      while((line = readLine(ioHandler)) != null) {
         int i1 = line.indexOf(124);
         if (i1 == -1) {
            throw new IOException("Bad properties data format");
         }

         int i2 = line.indexOf(124, i1 + 1);
         if (i2 == -1) {
            throw new IOException("Bad properties data format");
         }

         String type = line.substring(0, i1);
         String version = line.substring(i1 + 1, i2);
         String path = line.substring(i2 + 1);

         try {
            nmChangeList.getComponentTypeChanges().addChange(Type.valueOf(type), path, Long.parseLong(version));
         } catch (IllegalArgumentException var9) {
            throw new IOException("Bad properties data format");
         }
      }

   }

   public static void writeV2NMComponentTypeChangeList(NMInputOutput ioHandler, NMComponentTypeChangeList nmChangeList) throws IOException {
      String[] var2 = nmChangeList.getComponentNames();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String name = var2[var4];
         writeLine(ioHandler, name);
      }

      writeEOS(ioHandler);
      Iterator var6 = nmChangeList.getComponentTypeChanges().getChanges().values().iterator();

      while(var6.hasNext()) {
         ChangeList.Change c = (ChangeList.Change)var6.next();
         StringBuilder sb = new StringBuilder();
         sb.append(c.getType()).append('|');
         sb.append(c.getVersion()).append('|');
         sb.append(c.getRelativePath());
         writeLine(ioHandler, sb.toString());
      }

      writeEOS(ioHandler);
   }

   public static void readNMComponentTypeChangeList(NMInputOutput ioHandler, NMComponentTypeChangeList nmChangeList) throws IOException {
      if (ioHandler.getNMProtocol().equals(NMProtocol.v2)) {
         readV2NMComponentTypeChangeList(ioHandler, nmChangeList);
      } else {
         readV25NMComponentTypeChangeList(ioHandler, nmChangeList);
      }

   }

   public static void readV25NMComponentTypeChangeList(NMInputOutput ioHandler, NMComponentTypeChangeList nmChangeList) throws IOException {
      String line;
      while((line = readLine(ioHandler)) != null) {
         nmChangeList.addComponentName(line);
      }

      while((line = readLine(ioHandler)) != null) {
         int i1 = line.indexOf(124);
         int i2 = i1 == -1 ? -1 : line.indexOf(124, i1 + 1);
         int i3 = i2 == -1 ? -1 : line.indexOf(124, i2 + 1);
         int i4 = i3 == -1 ? -1 : line.indexOf(124, i3 + 1);
         if (i1 == -1 || i2 == -1 || i3 == -1 || i4 == -1) {
            throw new IOException("Bad properties data format: " + line);
         }

         String type = line.substring(0, i1);
         String compname = line.substring(i1 + 1, i2);
         String path = line.substring(i2 + 1, i3);
         String version = line.substring(i3 + 1, i4);
         String lastModified = line.substring(i4 + 1);

         try {
            nmChangeList.getComponentTypeChanges().addChange(Type.valueOf(type), compname, path, Long.parseLong(version), Long.parseLong(lastModified));
         } catch (IllegalArgumentException var13) {
            throw new IOException("Bad properties data format: " + line);
         }
      }

      String force = readLine(ioHandler);
      if (force == null) {
         throw new IOException("Bad properties data format: " + line);
      } else {
         nmChangeList.getComponentTypeChanges().setForceOverride(Boolean.parseBoolean(force));
      }
   }

   public static void writeNMComponentTypeChangeList(NMInputOutput ioHandler, NMComponentTypeChangeList nmChangeList) throws IOException {
      if (ioHandler.getNMProtocol().equals(NMProtocol.v2)) {
         writeV2NMComponentTypeChangeList(ioHandler, nmChangeList);
      } else {
         writeV25NMComponentTypeChangeList(ioHandler, nmChangeList);
      }

   }

   public static void writeV25NMComponentTypeChangeList(NMInputOutput ioHandler, NMComponentTypeChangeList nmChangeList) throws IOException {
      String[] var2 = nmChangeList.getComponentNames();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String name = var2[var4];
         writeLine(ioHandler, name);
      }

      writeEOS(ioHandler);
      Iterator var6 = nmChangeList.getComponentTypeChanges().getChanges().values().iterator();

      while(var6.hasNext()) {
         ChangeList.Change c = (ChangeList.Change)var6.next();
         StringBuilder sb = new StringBuilder();
         sb.append(c.getType()).append('|');
         sb.append(c.getComponentName()).append('|');
         sb.append(c.getRelativePath()).append('|');
         sb.append(c.getVersion()).append('|');
         sb.append(c.getLastModifiedTime());
         writeLine(ioHandler, sb.toString());
      }

      writeEOS(ioHandler);
      writeLine(ioHandler, Boolean.toString(nmChangeList.getComponentTypeChanges().isForceOverride()));
   }

   private static void readV2ChangeList(NMInputOutput ioHandler, ChangeList cl, List componentNames) throws IOException {
      String line;
      while((line = readLine(ioHandler)) != null) {
         int i1 = line.indexOf(124);
         if (i1 == -1) {
            throw new IOException("Bad properties data format");
         }

         int i2 = line.indexOf(124, i1 + 1);
         if (i2 == -1) {
            throw new IOException("Bad properties data format");
         }

         String type = line.substring(0, i1);
         String version = line.substring(i1 + 1, i2);
         String path = line.substring(i2 + 1);

         try {
            cl.addChange(Type.valueOf(type), path, Long.parseLong(version));
         } catch (IllegalArgumentException var10) {
            throw new IOException("Bad properties data format");
         }
      }

      while((line = readLine(ioHandler)) != null) {
         componentNames.add(line);
      }

   }

   private static void writeV2ChangeList(NMInputOutput ioHandler, ChangeList cl, String[] componentNames) throws IOException {
      Iterator var3 = cl.getChanges().values().iterator();

      while(var3.hasNext()) {
         ChangeList.Change c = (ChangeList.Change)var3.next();
         StringBuilder sb = new StringBuilder();
         sb.append(c.getType()).append('|');
         sb.append(c.getVersion()).append('|');
         sb.append(c.getRelativePath());
         writeLine(ioHandler, sb.toString());
      }

      writeEOS(ioHandler);
      if (componentNames != null) {
         String[] var7 = componentNames;
         int var8 = componentNames.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String name = var7[var9];
            writeLine(ioHandler, name);
         }
      }

      writeEOS(ioHandler);
   }

   public static void readChangeList(NMInputOutput ioHandler, ChangeList cl, List componentNames) throws IOException {
      if (ioHandler.getNMProtocol().equals(NMProtocol.v2)) {
         readV2ChangeList(ioHandler, cl, componentNames);
      } else {
         readV25ChangeList(ioHandler, cl, componentNames);
      }

   }

   private static void readV25ChangeList(NMInputOutput ioHandler, ChangeList cl, List componentNames) throws IOException {
      while(true) {
         String line;
         if ((line = readLine(ioHandler)) != null) {
            int i1 = line.indexOf(124);
            int i2 = i1 == -1 ? -1 : line.indexOf(124, i1 + 1);
            int i3 = i2 == -1 ? -1 : line.indexOf(124, i2 + 1);
            int i4 = i3 == -1 ? -1 : line.indexOf(124, i3 + 1);
            if (i1 != -1 && i2 != -1 && i3 != -1 && i4 != -1) {
               String type = line.substring(0, i1);
               String compname = line.substring(i1 + 1, i2);
               String path = line.substring(i2 + 1, i3);
               String version = line.substring(i3 + 1, i4);
               String lastModified = line.substring(i4 + 1);

               try {
                  cl.addChange(Type.valueOf(type), compname, path, Long.parseLong(version), Long.parseLong(lastModified));
                  continue;
               } catch (IllegalArgumentException var14) {
                  throw new IOException("Bad properties data format: " + line);
               }
            }

            throw new IOException("Bad properties data format: " + line);
         }

         String force = readLine(ioHandler);
         if (force == null) {
            throw new IOException("Bad properties data format: " + line);
         }

         cl.setForceOverride(Boolean.parseBoolean(force));

         while((line = readLine(ioHandler)) != null) {
            componentNames.add(line);
         }

         return;
      }
   }

   public static void writeChangeList(NMInputOutput ioHandler, ChangeList cl, String[] componentNames) throws IOException {
      if (ioHandler.getNMProtocol().ordinal() >= NMProtocol.v2_5.ordinal()) {
         writeV25ChangeList(ioHandler, cl, componentNames);
      } else {
         writeV2ChangeList(ioHandler, cl, componentNames);
      }

   }

   public static void writeV25ChangeList(NMInputOutput ioHandler, ChangeList cl, String[] componentNames) throws IOException {
      Iterator var3 = cl.getChanges().values().iterator();

      while(var3.hasNext()) {
         ChangeList.Change c = (ChangeList.Change)var3.next();
         StringBuilder sb = new StringBuilder();
         sb.append(c.getType()).append('|');
         sb.append(c.getComponentName()).append('|');
         sb.append(c.getRelativePath()).append('|');
         sb.append(c.getVersion()).append('|');
         sb.append(c.getLastModifiedTime());
         writeLine(ioHandler, sb.toString());
      }

      writeEOS(ioHandler);
      writeLine(ioHandler, Boolean.toString(cl.isForceOverride()));
      if (componentNames != null) {
         String[] var7 = componentNames;
         int var8 = componentNames.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String name = var7[var9];
            writeLine(ioHandler, name);
         }
      }

      writeEOS(ioHandler);
   }

   public static void copy(NMInputOutput ioHandler, Writer out) throws IOException {
      ioHandler.copy(out);
   }

   public static void copy(NMInputOutput ioHandler, OutputStream out) throws IOException {
      ioHandler.copy(out);
   }

   public static void copy(InputStream is, NMInputOutput ioHandler) throws IOException {
      ioHandler.copy(is);
   }

   public static String readLine(NMInputOutput ioHandler) throws IOException {
      String line = ioHandler.readLine();
      if (line != null) {
         int len = line.length();
         if (len > 0 && line.charAt(0) == '.') {
            line = len == 1 ? null : line.substring(1);
         }
      }

      return line;
   }

   public static void writeLine(NMInputOutput ioHandler, String line) throws IOException {
      if (line.length() > 0 && line.charAt(0) == '.') {
         line = '.' + line;
      }

      ioHandler.writeLine(line);
   }

   public static void writeEOS(NMInputOutput ioHandler) throws IOException {
      ioHandler.writeLine(".");
   }

   public static void writeOK(NMInputOutput ioHandler, String msg) throws IOException {
      ioHandler.writeLine("+OK " + msg);
   }

   public static void writeERR(NMInputOutput ioHandler, String msg) throws IOException {
      ioHandler.writeLine("-ERR " + msg);
   }

   public static String readResponse(NMInputOutput ioHandler) throws IOException {
      return ioHandler.readLine();
   }

   public static String parseOK(String line) {
      return line.startsWith("+OK ") ? line.substring("+OK ".length()) : null;
   }

   public static String parseERR(String line) {
      return line.startsWith("-ERR ") ? line.substring("-ERR ".length()) : null;
   }

   public static int parseScriptERR(String line) {
      if (line.startsWith("-ERR ")) {
         try {
            return Integer.parseInt(line.substring("-ERR ".length()));
         } catch (NumberFormatException var2) {
         }
      }

      return 0;
   }

   public static void writeCommand(NMInputOutput ioHandler, Command cmd, String[] args) throws IOException {
      String line = cmd.toString() + " ";
      if (args != null) {
         line = line + StringUtils.join(args, " ");
      }

      ioHandler.writeLine(line);
   }

   public static String readCommand(NMInputOutput ioHandler) throws IOException {
      return ioHandler.readLine();
   }

   public static void writeMultiLineCommand(NMInputOutput ioHandler, String[] command) throws IOException {
      String[] var2 = command;
      int var3 = command.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String line = var2[var4];
         writeLine(ioHandler, line);
      }

      writeEOS(ioHandler);
   }

   public static String[] readMultiLineCommand(NMInputOutput ioHandler) throws IOException {
      ArrayList commands = new ArrayList();

      String line;
      while((line = readLine(ioHandler)) != null) {
         commands.add(line);
      }

      return (String[])commands.toArray(new String[commands.size()]);
   }
}
