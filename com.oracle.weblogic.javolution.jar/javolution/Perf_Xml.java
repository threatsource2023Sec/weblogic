package javolution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import javolution.lang.MathLib;
import javolution.lang.Text;
import javolution.lang.TextBuilder;
import javolution.util.FastComparator;
import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import javolution.util.FastTable;
import javolution.xml.CharacterData;
import javolution.xml.ObjectReader;
import javolution.xml.ObjectWriter;

final class Perf_Xml extends Javolution implements Runnable {
   private static final int OBJECT_SIZE = 1000;
   private static final int BYTE_BUFFER_SIZE = 1200000;

   public void run() {
      println("/////////////////////////////");
      println("// Package: javolution.xml //");
      println("/////////////////////////////");
      println("");
      println("-- Java(TM) Serialization --");
      setOutputStream((PrintStream)null);

      int var1;
      for(var1 = 0; var1 < 10; ++var1) {
         this.benchmarkJavaSerialization();
      }

      setOutputStream(System.out);
      this.benchmarkJavaSerialization();
      println("");
      println("-- XML Serialization (I/O Stream) --");
      setOutputStream((PrintStream)null);

      for(var1 = 0; var1 < 10; ++var1) {
         this.benchmarkXmlIoSerialization();
      }

      setOutputStream(System.out);
      this.benchmarkXmlIoSerialization();
      println("");
      println("-- XML Serialization (NIO ByteBuffer) --");
      setOutputStream((PrintStream)null);

      for(var1 = 0; var1 < 10; ++var1) {
         this.benchmarkXmlNioSerialization();
      }

      setOutputStream(System.out);
      this.benchmarkXmlNioSerialization();
      println("");
   }

   private void benchmarkJavaSerialization() {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream(1200000);
         ObjectOutputStream var2 = new ObjectOutputStream(var1);
         Object var3 = newData();
         print("Write Time: ");
         startTime();
         var2.writeObject(var3);
         var2.close();
         println(endTime(1));
         ByteArrayInputStream var4 = new ByteArrayInputStream(var1.toByteArray());
         ObjectInputStream var5 = new ObjectInputStream(var4);
         print("Read Time: ");
         startTime();
         Object var6 = var5.readObject();
         var5.close();
         println(endTime(1));
         if (!var3.equals(var6)) {
            throw new Error("SERIALIZATION ERROR");
         }
      } catch (UnsupportedOperationException var7) {
         println("NOT SUPPORTED (J2SE 1.4+ build required)");
      } catch (Throwable var8) {
         throw new JavolutionError(var8);
      }

   }

   private void benchmarkXmlIoSerialization() {
      try {
         ObjectWriter var1 = new ObjectWriter();
         var1.setPackagePrefix("", "java.lang");
         ByteArrayOutputStream var2 = new ByteArrayOutputStream(1200000);
         Object var3 = newData();
         print("Write Time: ");
         startTime();
         var1.write(var3, (OutputStream)var2);
         println(endTime(1));
         ObjectReader var4 = new ObjectReader();
         ByteArrayInputStream var5 = new ByteArrayInputStream(var2.toByteArray());
         print("Read Time: ");
         startTime();
         Object var6 = var4.read((InputStream)var5);
         println(endTime(1));
         if (!var3.equals(var6)) {
            throw new Error("SERIALIZATION ERROR");
         }
      } catch (UnsupportedOperationException var7) {
         println("NOT SUPPORTED (J2SE 1.4+ build required)");
      } catch (Throwable var8) {
         throw new JavolutionError(var8);
      }

   }

   private void benchmarkXmlNioSerialization() {
      try {
         ObjectWriter var1 = new ObjectWriter();
         ByteBuffer var2 = ByteBuffer.allocateDirect(1200000);
         var1.setPackagePrefix("", "java.lang");
         Object var3 = newData();
         print("Write Time: ");
         startTime();
         var1.write(var3, var2);
         println(endTime(1));
         ObjectReader var4 = new ObjectReader();
         var2.flip();
         print("Read Time: ");
         startTime();
         Object var5 = var4.read(var2);
         println(endTime(1));
         if (!var3.equals(var5)) {
            throw new Error("SERIALIZATION ERROR");
         }
      } catch (UnsupportedOperationException var6) {
         println("NOT SUPPORTED (J2SE 1.4+ build required)");
      } catch (Throwable var7) {
         throw new JavolutionError(var7);
      }

   }

   private static Object newData() {
      FastTable var0 = new FastTable(1000);

      for(int var1 = 0; var1 < 1000; ++var1) {
         FastTable var2 = new FastTable();
         var2.add("This is the first String (" + var1 + ")");
         var2.add("This is the second String (" + var1 + ")");
         var2.add("This is the third String (" + var1 + ")");
         var0.add(var2);
         var0.add((Object)null);
         var0.add(TextBuilder.newInstance().append(Long.MAX_VALUE));
         var0.add(Text.valueOf(Long.MAX_VALUE, 16));
         String var3 = "<<< Some character data " + MathLib.randomInt(0, 9) + " >>>";
         CharacterData var4 = CharacterData.valueOf(Text.valueOf((Object)var3));
         var0.add(var4);
         FastMap var5 = new FastMap();
         var5.setKeyComparator(FastComparator.REHASH);
         var5.setValueComparator(FastComparator.IDENTITY);
         var5.put(new String("ONE"), Text.valueOf((int)1));
         var5.put(new String("TWO"), Text.valueOf((int)2));
         var5.put(new String("THREE"), Text.valueOf((int)3));
         var0.add(var5);
         FastList var6 = new FastList();
         var6.add("FIRST");
         var6.add("SECOND");
         var6.add("THIRD");
         var6.add("...");
         var0.add(var6);
         FastSet var7 = new FastSet();
         var7.add("ALPHA");
         var7.add("BETA");
         var7.add("GAMMA");
         var0.add(var7);
      }

      return var0;
   }
}
