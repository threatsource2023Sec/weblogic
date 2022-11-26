package org.python.icu.text;

abstract class CharsetRecog_Unicode extends CharsetRecognizer {
   abstract String getName();

   abstract CharsetMatch match(CharsetDetector var1);

   static int codeUnit16FromBytes(byte hi, byte lo) {
      return (hi & 255) << 8 | lo & 255;
   }

   static int adjustConfidence(int codeUnit, int confidence) {
      if (codeUnit == 0) {
         confidence -= 10;
      } else if (codeUnit >= 32 && codeUnit <= 255 || codeUnit == 10) {
         confidence += 10;
      }

      if (confidence < 0) {
         confidence = 0;
      } else if (confidence > 100) {
         confidence = 100;
      }

      return confidence;
   }

   static class CharsetRecog_UTF_32_LE extends CharsetRecog_UTF_32 {
      int getChar(byte[] input, int index) {
         return (input[index + 3] & 255) << 24 | (input[index + 2] & 255) << 16 | (input[index + 1] & 255) << 8 | input[index + 0] & 255;
      }

      String getName() {
         return "UTF-32LE";
      }
   }

   static class CharsetRecog_UTF_32_BE extends CharsetRecog_UTF_32 {
      int getChar(byte[] input, int index) {
         return (input[index + 0] & 255) << 24 | (input[index + 1] & 255) << 16 | (input[index + 2] & 255) << 8 | input[index + 3] & 255;
      }

      String getName() {
         return "UTF-32BE";
      }
   }

   abstract static class CharsetRecog_UTF_32 extends CharsetRecog_Unicode {
      abstract int getChar(byte[] var1, int var2);

      abstract String getName();

      CharsetMatch match(CharsetDetector det) {
         byte[] input = det.fRawInput;
         int limit = det.fRawLength / 4 * 4;
         int numValid = 0;
         int numInvalid = 0;
         boolean hasBOM = false;
         int confidence = 0;
         if (limit == 0) {
            return null;
         } else {
            if (this.getChar(input, 0) == 65279) {
               hasBOM = true;
            }

            for(int i = 0; i < limit; i += 4) {
               int ch = this.getChar(input, i);
               if (ch >= 0 && ch < 1114111 && (ch < 55296 || ch > 57343)) {
                  ++numValid;
               } else {
                  ++numInvalid;
               }
            }

            if (hasBOM && numInvalid == 0) {
               confidence = 100;
            } else if (hasBOM && numValid > numInvalid * 10) {
               confidence = 80;
            } else if (numValid > 3 && numInvalid == 0) {
               confidence = 100;
            } else if (numValid > 0 && numInvalid == 0) {
               confidence = 80;
            } else if (numValid > numInvalid * 10) {
               confidence = 25;
            }

            return confidence == 0 ? null : new CharsetMatch(det, this, confidence);
         }
      }
   }

   static class CharsetRecog_UTF_16_LE extends CharsetRecog_Unicode {
      String getName() {
         return "UTF-16LE";
      }

      CharsetMatch match(CharsetDetector det) {
         byte[] input = det.fRawInput;
         int confidence = 10;
         int bytesToCheck = Math.min(input.length, 30);

         for(int charIndex = 0; charIndex < bytesToCheck - 1; charIndex += 2) {
            int codeUnit = codeUnit16FromBytes(input[charIndex + 1], input[charIndex]);
            if (charIndex == 0 && codeUnit == 65279) {
               confidence = 100;
               break;
            }

            confidence = adjustConfidence(codeUnit, confidence);
            if (confidence == 0 || confidence == 100) {
               break;
            }
         }

         if (bytesToCheck < 4 && confidence < 100) {
            confidence = 0;
         }

         return confidence > 0 ? new CharsetMatch(det, this, confidence) : null;
      }
   }

   static class CharsetRecog_UTF_16_BE extends CharsetRecog_Unicode {
      String getName() {
         return "UTF-16BE";
      }

      CharsetMatch match(CharsetDetector det) {
         byte[] input = det.fRawInput;
         int confidence = 10;
         int bytesToCheck = Math.min(input.length, 30);

         for(int charIndex = 0; charIndex < bytesToCheck - 1; charIndex += 2) {
            int codeUnit = codeUnit16FromBytes(input[charIndex], input[charIndex + 1]);
            if (charIndex == 0 && codeUnit == 65279) {
               confidence = 100;
               break;
            }

            confidence = adjustConfidence(codeUnit, confidence);
            if (confidence == 0 || confidence == 100) {
               break;
            }
         }

         if (bytesToCheck < 4 && confidence < 100) {
            confidence = 0;
         }

         return confidence > 0 ? new CharsetMatch(det, this, confidence) : null;
      }
   }
}
