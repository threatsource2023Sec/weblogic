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
import weblogic.utils.collections.NumericKeyHashtable;
import weblogic.utils.collections.NumericValueHashtable;

public class DynamicFldTbl implements FldTbl {
   private static final int FNMASK32 = 33554431;
   private static final int FTMASK32 = 63;
   private static final int FTSHIFT32 = 25;
   private static final int FNMASK = 8191;
   private static final int FTMASK = 7;
   private static final int FTSHIFT = 13;
   private NumericValueHashtable nameToFieldHashtable;
   private NumericKeyHashtable fieldToNameHashtable;
   private Vector fieldNameSet;
   private int currSpot;
   private int lineNo;
   private int inputStringLength;
   private char[] inputChars;

   public DynamicFldTbl(String fieldFileName, boolean hdrType32) {
      this(fieldFileName, hdrType32, 101, 0.75F);
   }

   public DynamicFldTbl(String fieldFileName, boolean hdrType32, int initialCapacity, float loadFactor) {
      this.lineNo = 0;
      BufferedReader inputStream = null;

      try {
         int currentBase = 0;
         boolean traceEnabled = ntrace.isTraceEnabled(4);
         if (traceEnabled) {
            ntrace.doTrace("[/DynamicFldTbl/Startup/Initial Capacity = " + initialCapacity + "/Load Factor = " + loadFactor);
         }

         File inputFile = new File(fieldFileName);

         try {
            inputStream = new BufferedReader(new FileReader(inputFile));
         } catch (FileNotFoundException var36) {
            if (traceEnabled) {
               ntrace.doTrace("]/DynamicFldTbl/Startup/Could not find file " + fieldFileName + "/20");
            }

            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream is = loader.getResourceAsStream(fieldFileName);
            if (is == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/DynamicFldTbl/Startup/Could not find resource " + fieldFileName + "/25");
               }

               return;
            }

            inputStream = new BufferedReader(new InputStreamReader(is));
         }

         NumericValueHashtable nameToField = new NumericValueHashtable(initialCapacity, loadFactor);
         NumericKeyHashtable fieldToName = new NumericKeyHashtable(initialCapacity, loadFactor);
         Vector fieldNames = new Vector();

         while(true) {
            String inputString;
            try {
               if ((inputString = inputStream.readLine()) == null) {
                  break;
               }

               inputString = inputString.trim();
               ++this.lineNo;
               this.currSpot = 0;
            } catch (IOException var37) {
               if (traceEnabled) {
                  ntrace.doTrace("]/DynamicFldTbl/Startup/Finished!/30");
               }
               break;
            }

            this.inputStringLength = inputString.length();
            if (this.inputStringLength != 0 && !inputString.startsWith("#") && !inputString.startsWith("$")) {
               this.inputChars = inputString.toCharArray();
               if (inputString.startsWith("*base")) {
                  if (this.inputStringLength < 7 || !Character.isWhitespace(inputString.charAt(5))) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/DynamicFldTbl/Startup/base must be followed by white space/40");
                     }

                     return;
                  }

                  this.currSpot = 6;
                  if (!this.skipWhiteSpace()) {
                     return;
                  }

                  if (!Character.isDigit(inputString.charAt(this.currSpot))) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/DynamicFldTbl/Startup/base must be followed by a numeric value/50");
                     }

                     return;
                  }

                  currentBase = this.getInteger();
               } else {
                  String fldName = this.getString();
                  if (!this.skipWhiteSpace()) {
                     return;
                  }

                  if (!Character.isDigit(this.inputChars[this.currSpot])) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/DynamicFldTbl/Startup/" + fldName + " must be followed by a numeric value/60");
                     }

                     return;
                  }

                  int fldNo = this.getInteger() + currentBase;
                  if (hdrType32) {
                     if (fldNo > 33554431) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/DynamicFldTbl/Startup/" + fldNo + " must be less than or equal to " + 33554431 + "/70");
                        }

                        return;
                     }
                  } else if (fldNo > 8191) {
                     if (traceEnabled) {
                        ntrace.doTrace("]/DynamicFldTbl/Startup/" + fldNo + " must be less than or equal to " + 8191 + "/80");
                     }

                     return;
                  }

                  if (!this.skipWhiteSpace()) {
                     return;
                  }

                  String typeString = this.getString();
                  byte fldType;
                  if (typeString.equals("char")) {
                     fldType = 2;
                  } else if (typeString.equals("string")) {
                     fldType = 5;
                  } else if (typeString.equals("short")) {
                     fldType = 0;
                  } else if (typeString.equals("long")) {
                     fldType = 1;
                  } else if (typeString.equals("float")) {
                     fldType = 3;
                  } else if (typeString.equals("double")) {
                     fldType = 4;
                  } else if (typeString.equals("carray")) {
                     fldType = 6;
                  } else if (hdrType32 && typeString.equals("ptr")) {
                     fldType = 9;
                  } else if (hdrType32 && typeString.equals("fml32")) {
                     fldType = 10;
                  } else {
                     if (!hdrType32 || !typeString.equals("view32")) {
                        if (traceEnabled) {
                           ntrace.doTrace("]/DynamicFldTbl/Startup/" + typeString + " is invalid/90");
                        }

                        return;
                     }

                     fldType = 11;
                  }

                  int fldId;
                  if (hdrType32) {
                     fldId = (fldType & 63) << 25 | fldNo & 33554431;
                  } else {
                     fldId = (fldType & 7) << 13 | fldNo & 8191;
                  }

                  fieldToName.put((long)fldId, fldName);
                  nameToField.put(fldName, (long)fldId);
                  fieldNames.add(fldName);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/DynamicFldTbl/Startup/Loaded/100");
         }

         this.nameToFieldHashtable = nameToField;
         this.fieldToNameHashtable = fieldToName;
         this.fieldNameSet = fieldNames;
      } finally {
         if (inputStream != null) {
            try {
               inputStream.close();
            } catch (IOException var35) {
            }
         }

      }
   }

   private int getInteger() {
      int firstDigit;
      for(firstDigit = this.currSpot; this.currSpot < this.inputStringLength && Character.isDigit(this.inputChars[this.currSpot]); ++this.currSpot) {
      }

      int retVal = Integer.parseInt(new String(this.inputChars, firstDigit, this.currSpot - firstDigit));
      return retVal;
   }

   private String getString() {
      int firstChar;
      for(firstChar = this.currSpot; this.currSpot < this.inputStringLength && !Character.isWhitespace(this.inputChars[this.currSpot]); ++this.currSpot) {
      }

      String retVal = new String(this.inputChars, firstChar, this.currSpot - firstChar);
      return retVal;
   }

   private boolean skipWhiteSpace() {
      while(this.currSpot < this.inputStringLength && Character.isWhitespace(this.inputChars[this.currSpot])) {
         ++this.currSpot;
      }

      if (this.currSpot < this.inputStringLength) {
         return true;
      } else {
         if (ntrace.isTraceEnabled(4)) {
            ntrace.doTrace("]/DynamicFldTbl/Startup/malformed field definition at line " + this.lineNo);
         }

         return false;
      }
   }

   public String Fldid_to_name(int fldId) {
      return this.fieldToNameHashtable == null ? null : (String)this.fieldToNameHashtable.get((long)fldId);
   }

   public String[] getFldNames() {
      if (this.fieldNameSet == null) {
         return new String[0];
      } else {
         String[] names = new String[this.fieldNameSet.size()];
         names = (String[])((String[])this.fieldNameSet.toArray(names));
         return names;
      }
   }

   public int name_to_Fldid(String name) {
      if (this.nameToFieldHashtable == null) {
         return -1;
      } else {
         int fldId = (int)this.nameToFieldHashtable.get(name);
         return fldId == 0 ? -1 : fldId;
      }
   }
}
