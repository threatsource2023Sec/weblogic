package weblogic.xml.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public final class ParsingUtils implements XMLConstants {
   private static final boolean debug = System.getProperty("weblogic.xml.debug") != null;

   public static boolean readWS(PushbackReader r) throws IOException {
      int c;
      do {
         c = r.read();
         if (c < 0) {
            throw new EOFException();
         }
      } while(Character.isWhitespace((char)c));

      r.unread(c);
      return true;
   }

   public static String read(PushbackReader r, String stopString, boolean inclusive) throws IOException {
      char[] stop = stopString.toCharArray();
      int stopIndex = 0;
      boolean done = false;

      StringBuffer sbuf;
      int l;
      for(sbuf = new StringBuffer(); !done; done = true) {
         do {
            l = r.read();
            if (l < 0) {
               throw new EOFException();
            }

            char ch = (char)l;
            sbuf.append(ch);
            if (ch == stop[stopIndex]) {
               ++stopIndex;
            } else {
               stopIndex = 0;
            }
         } while(stopIndex != stopString.length());
      }

      if (!inclusive) {
         r.unread(stop);
         l = sbuf.length();
         sbuf.delete(l - stop.length, l);
      }

      return sbuf.toString();
   }

   public static String read(PushbackReader r, String[] stopStrings, boolean inclusive) throws IOException {
      weblogic.utils.Debug.assertion(stopStrings != null);
      weblogic.utils.Debug.assertion(stopStrings.length > 0);
      int[] stopIndexes = new int[stopStrings.length];
      List stops = new ArrayList(stopStrings.length);

      for(int i = 0; i < stopStrings.length; ++i) {
         stops.add(stopStrings[i].toCharArray());
      }

      StringBuffer sbuf = new StringBuffer();
      int matchedStringIndex = true;

      while(true) {
         int stoplen = r.read();
         if (stoplen < 0) {
            throw new EOFException();
         }

         char l = (char)stoplen;
         sbuf.append((char)l);

         for(int i = 0; i < stopStrings.length; ++i) {
            char[] stop = (char[])((char[])stops.get(i));
            if (l == stop[stopIndexes[i]]) {
               int var10002 = stopIndexes[i]++;
            } else {
               stopIndexes[i] = 0;
            }

            if (stopIndexes[i] >= stop.length) {
               if (!inclusive) {
                  stoplen = ((char[])((char[])stops.get(i))).length;
                  r.unread((char[])((char[])stops.get(i)));
                  l = sbuf.length();
                  sbuf.delete(l - stoplen, l);
               }

               return sbuf.toString();
            }
         }
      }
   }

   public static String readUntilWS(PushbackReader r) throws IOException {
      StringBuffer sbuf = new StringBuffer();

      while(true) {
         int c = r.read();
         if (c < 0) {
            throw new EOFException();
         }

         if (Character.isWhitespace((char)c)) {
            r.unread(c);
            return sbuf.toString();
         }

         sbuf.append((char)c);
      }
   }

   public static String peek(PushbackReader r, int nchars) throws IOException {
      if (debug) {
         System.out.println("ParsingUtils.peek(" + nchars + ") = ");
      }

      char[] buf = new char[nchars];
      r.read(buf, 0, nchars);
      r.unread(buf, 0, nchars);
      String rv = new String(buf);
      if (debug) {
         System.out.println("\"" + rv + "\"");
      }

      return new String(buf);
   }

   public static void main(String[] args) throws Exception {
      if (debug) {
         if (args.length == 0) {
            System.err.println("Usage: ParsingUtils <string to parse> <stop1> <stop2> ...");
            System.exit(1);
         }

         PushbackReader r = new PushbackReader(new StringReader(args[0]));
         String[] stops = new String[args.length - 1];

         for(int i = 1; i < args.length; ++i) {
            stops[i - 1] = args[i];
         }

         System.out.println(read(r, stops, true));
      }

   }
}
