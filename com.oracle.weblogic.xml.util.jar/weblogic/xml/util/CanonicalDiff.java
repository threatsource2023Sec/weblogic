package weblogic.xml.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import weblogic.xml.babel.stream.CanonicalInputStream;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLOutputStreamFactory;
import weblogic.xml.stream.XMLStreamException;

public class CanonicalDiff {
   private static final boolean debug = false;

   public static byte[] getXMLAsArray(String fileName) throws XMLStreamException, IOException {
      XMLInputStreamFactory inFactory = XMLInputStreamFactory.newInstance();
      XMLInputStream streamIn = inFactory.newDTDAwareInputStream(new FileInputStream(fileName));
      XMLOutputStreamFactory outFactory = XMLOutputStreamFactory.newInstance();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      XMLOutputStream streamOut = outFactory.newCanonicalOutputStream(outputStream);
      streamOut.add(streamIn);
      streamOut.flush();
      return outputStream.toByteArray();
   }

   public static XMLInputStream getXMLAsStream(String fileName) throws XMLStreamException, IOException {
      XMLInputStreamFactory inFactory = XMLInputStreamFactory.newInstance();
      XMLInputStream streamIn = inFactory.newDTDAwareInputStream(new FileInputStream(fileName));
      return new CanonicalInputStream(streamIn);
   }

   public static boolean equals(XMLInputStream s1, XMLInputStream s2) throws XMLStreamException, IOException {
      while(true) {
         if (s1.hasNext()) {
            if (!s2.hasNext()) {
               return false;
            }

            if (s1.next().equals(s2.next())) {
               continue;
            }

            return false;
         }

         return !s2.hasNext();
      }
   }

   public static boolean equals(byte[] a1, byte[] a2) {
      if (a1.length != a2.length) {
         return false;
      } else {
         for(int i = 0; i < a1.length; ++i) {
            if (a1[i] != a2[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean compare(String f1, String f2) throws XMLStreamException, IOException {
      byte[] a1 = getXMLAsArray(f1);
      byte[] a2 = getXMLAsArray(f2);
      return equals(a1, a2);
   }

   public static boolean compare2(String f1, String f2) throws XMLStreamException, IOException {
      XMLInputStream a1 = getXMLAsStream(f1);
      XMLInputStream a2 = getXMLAsStream(f2);
      if (equals(a1, a2)) {
         return true;
      } else {
         debug2(f1, f2);
         return false;
      }
   }

   public static void debug2(String f1, String f2) throws XMLStreamException, IOException {
      XMLInputStream s1 = getXMLAsStream(f1);

      XMLInputStream s2;
      XMLEvent e2;
      for(s2 = getXMLAsStream(f2); s1.hasNext(); System.out.println("e2[" + e2 + "]")) {
         if (!s2.hasNext()) {
            System.out.println("Stream 2 has no more elements");
            return;
         }

         XMLEvent e1 = s1.next();
         e2 = s2.next();
         System.out.print("e1[" + e1 + "]");
         if (!e1.equals(e2)) {
            System.out.print(" != ");
         } else {
            System.out.print("  = ");
         }
      }

      if (s2.hasNext()) {
         System.out.println("Stream 2 has more elements");
      }

   }

   public static void debug(byte[] a1, byte[] a2) {
      for(int i = 0; i < a1.length; ++i) {
         if (a1[i] == a2[i]) {
            System.out.print("[equal]");
         } else {
            System.out.print("[diff ]");
         }

         System.out.println("byte[" + i + "]:\t\t" + a1[i] + ":" + a2[i] + "\t--\t" + (char)a1[i] + ":" + (char)a2[i]);
         if (a1[i] != a2[i]) {
            break;
         }
      }

   }

   public static void print(String f1) throws XMLStreamException, IOException {
      byte[] out = getXMLAsArray(f1);
      System.out.println("---------------- [ As String ] -------------");
      System.out.print(new String(out));
      System.out.println("---------------- [ As Bytes  ] -------------");

      for(int i = 0; i < out.length; ++i) {
         System.out.print(out[i]);
      }

   }

   public static void main(String[] args) throws Exception {
      if (compare(args[0], args[1])) {
         System.out.println(args[0] + " = " + args[1]);
      } else {
         System.out.println(args[0] + " != " + args[1]);
      }

      if (compare2(args[0], args[1])) {
         System.out.println(args[0] + " s= " + args[1]);
      } else {
         System.out.println(args[0] + " s!= " + args[1]);
      }

      if (args.length == 3) {
         System.out.println("---------------- [ Debug ] -------------");
         debug(getXMLAsArray(args[0]), getXMLAsArray(args[1]));
      }

   }
}
