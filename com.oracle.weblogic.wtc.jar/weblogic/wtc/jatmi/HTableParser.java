package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import weblogic.utils.collections.NumericHashtable;
import weblogic.utils.collections.NumericValueHashtable;

public final class HTableParser {
   private static HTableParser _instance;
   private static final int FLD_SHORT = 0;
   private static final int FLD_LONG = 1;
   private static final int FLD_CHAR = 2;
   private static final int FLD_FLOAT = 3;
   private static final int FLD_DOUBLE = 4;
   private static final int FLD_STRING = 5;
   private static final int FLD_CARRAY = 6;
   private static final int FLD_INT = 7;
   private static final int FLD_DECIMAL = 8;
   private static final int FLD_PTR = 9;
   private static final int FLD_FML32 = 10;
   private static final int FLD_VIEW32 = 11;
   private static final int FNMASK32 = 33554431;
   private static final int FTMASK32 = 63;
   private static final int FTSHIFT32 = 25;
   private static final int FNMASK = 8191;
   private static final int FTMASK = 7;
   private static final int FTSHIFT = 13;
   private NumericValueHashtable nametofieldHashTable;
   private NumericHashtable fieldtonameHashTable;
   private Vector fieldNameSet;

   public static HTableParser getInstance(String fieldfilename, boolean hdrtype32) {
      return getInstance(fieldfilename, hdrtype32, 101, 0.75F);
   }

   public static synchronized HTableParser getInstance(String fieldfilename, boolean hdrtype32, int initialCapacity, float loadFactor) {
      if (_instance == null) {
         _instance = new HTableParser(fieldfilename, hdrtype32, initialCapacity, loadFactor);
      }

      return _instance;
   }

   public HTableParser(String fieldfilename, boolean hdrtype32) {
      this(fieldfilename, hdrtype32, 101, 0.75F);
   }

   public HTableParser(String fieldfilename, boolean hdrtype32, int initialCapacity, float loadFactor) {
      BufferedReader input_stream = null;
      int current_base = 0;
      int lineno = 0;
      int fldno = false;
      int fldid = false;
      int fldtype = false;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/HTableParser/Startup/Initial Capacity = " + initialCapacity + "/Load Factor = " + loadFactor);
      }

      File input_file = new File(fieldfilename);

      try {
         input_stream = new BufferedReader(new FileReader(input_file));
      } catch (FileNotFoundException var23) {
         if (traceEnabled) {
            ntrace.doTrace("]/HTableParser/Startup/Could not find file " + fieldfilename + "/20");
         }

         ClassLoader loader = Thread.currentThread().getContextClassLoader();
         InputStream is = loader.getResourceAsStream(fieldfilename);
         if (is == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/HTableParser/Startup/Could not find resource " + fieldfilename + "/25");
            }

            return;
         }

         input_stream = new BufferedReader(new InputStreamReader(is));
      }

      this.nametofieldHashTable = new NumericValueHashtable(initialCapacity, loadFactor);
      this.fieldtonameHashTable = new NumericHashtable(initialCapacity, loadFactor);
      this.fieldNameSet = new Vector();

      while(true) {
         String input_string;
         int currspot;
         try {
            if ((input_string = input_stream.readLine()) == null) {
               break;
            }

            input_string = input_string.trim();
            ++lineno;
            currspot = 0;
         } catch (IOException var22) {
            if (traceEnabled) {
               ntrace.doTrace("]/HTableParser/Startup/Finished!/30");
            }
            break;
         }

         int input_string_length = input_string.length();
         if (input_string_length != 0 && !input_string.startsWith("#") && !input_string.startsWith("$")) {
            int first_digit;
            if (input_string.startsWith("*base")) {
               if (!Character.isWhitespace(input_string.charAt(5))) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/HTableParser/Startup/*base must be followed by white space/40");
                  }

                  return;
               }

               for(currspot = 6; Character.isWhitespace(input_string.charAt(currspot)); ++currspot) {
               }

               if (!Character.isDigit(input_string.charAt(currspot))) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/HTableParser/Startup/*base must be followed by a numeric value/50");
                  }

                  return;
               }

               for(first_digit = currspot++; currspot < input_string.length() && Character.isDigit(input_string.charAt(currspot)); ++currspot) {
               }

               current_base = Integer.parseInt(input_string.substring(first_digit, currspot));
            } else {
               while(!Character.isWhitespace(input_string.charAt(currspot))) {
                  ++currspot;
               }

               String fldname;
               for(fldname = input_string.substring(0, currspot); Character.isWhitespace(input_string.charAt(currspot)); ++currspot) {
               }

               if (!Character.isDigit(input_string.charAt(currspot))) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/HTableParser/Startup/" + fldname + " must be followed by a numeric value/60");
                  }

                  return;
               }

               for(first_digit = currspot++; Character.isDigit(input_string.charAt(currspot)); ++currspot) {
               }

               int fldno = Integer.parseInt(input_string.substring(first_digit, currspot)) + current_base;
               if (hdrtype32) {
                  if (fldno > 33554431) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/HTableParser/Startup/" + fldno + " must be less than or equal to " + 33554431 + "/70");
                     }

                     return;
                  }
               } else if (fldno > 8191) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/HTableParser/Startup/" + fldno + " must be less than or equal to " + 8191 + "/80");
                  }

                  return;
               }

               while(Character.isWhitespace(input_string.charAt(currspot))) {
                  ++currspot;
               }

               first_digit = currspot;

               while(!Character.isWhitespace(input_string.charAt(currspot)) && input_string.charAt(currspot) != '\n') {
                  ++currspot;
                  if (currspot >= input_string_length) {
                     currspot = input_string_length;
                     break;
                  }
               }

               String typestring = input_string.substring(first_digit, currspot);
               byte fldtype;
               if (typestring.equals("char")) {
                  fldtype = 2;
               } else if (typestring.equals("string")) {
                  fldtype = 5;
               } else if (typestring.equals("short")) {
                  fldtype = 0;
               } else if (typestring.equals("long")) {
                  fldtype = 1;
               } else if (typestring.equals("float")) {
                  fldtype = 3;
               } else if (typestring.equals("double")) {
                  fldtype = 4;
               } else if (typestring.equals("carray")) {
                  fldtype = 6;
               } else if (hdrtype32 && typestring.equals("ptr")) {
                  fldtype = 9;
               } else if (hdrtype32 && typestring.equals("fml32")) {
                  fldtype = 10;
               } else {
                  if (!hdrtype32 || !typestring.equals("view32")) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/HTableParser/Startup/" + typestring + " is invalid/90");
                     }

                     return;
                  }

                  fldtype = 11;
               }

               int fldid;
               if (hdrtype32) {
                  fldid = (fldtype & 63) << 25 | fldno & 33554431;
               } else {
                  fldid = (fldtype & 7) << 13 | fldno & 8191;
               }

               this.fieldtonameHashTable.put((long)fldid, fldname);
               this.nametofieldHashTable.put(fldname, (long)fldid);
               this.fieldNameSet.add(fldname);
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/HTableParser/Startup/Loaded/100");
      }

   }

   public NumericValueHashtable getnametofieldHashTable() {
      return this.nametofieldHashTable;
   }

   public NumericHashtable getfieldtonameHashTable() {
      return this.fieldtonameHashTable;
   }

   public Vector getfieldNameSet() {
      return this.fieldNameSet;
   }
}
