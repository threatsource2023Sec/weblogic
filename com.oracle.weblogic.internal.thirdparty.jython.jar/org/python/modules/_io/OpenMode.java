package org.python.modules._io;

import org.python.core.Py;
import org.python.core.PyException;

public class OpenMode {
   public final String originalModeString;
   public boolean reading;
   public boolean writing;
   public boolean appending;
   public boolean updating;
   public boolean binary;
   public boolean text;
   public boolean universal;
   public boolean other;
   public boolean invalid;
   public String message;

   public OpenMode(String mode) {
      this.originalModeString = mode;
      int n = mode.length();
      boolean duplicate = false;

      for(int i = 0; i < n; ++i) {
         char c = mode.charAt(i);
         switch (c) {
            case '+':
               duplicate = this.updating;
               this.updating = true;
               break;
            case 'U':
               duplicate = this.universal;
               this.universal = true;
               break;
            case 'a':
               duplicate = this.appending;
               this.appending = true;
               break;
            case 'b':
               duplicate = this.binary;
               this.binary = true;
               break;
            case 'r':
               duplicate = this.reading;
               this.reading = true;
               break;
            case 't':
               duplicate = this.text;
               this.text = true;
               break;
            case 'w':
               duplicate = this.writing;
               this.writing = true;
               break;
            default:
               this.other = true;
         }

         if (duplicate) {
            this.invalid = true;
            break;
         }
      }

   }

   public void validate() {
      this.reading |= this.universal;
      if (!this.invalid) {
         if (!this.universal || !this.writing && !this.appending) {
            if (this.text && this.binary) {
               this.message = "can't have text and binary mode at once";
            } else {
               int rwa = 0;
               if (this.reading) {
                  ++rwa;
               }

               if (this.writing) {
                  ++rwa;
               }

               if (this.appending) {
                  ++rwa;
               }

               if (rwa != 1) {
                  this.message = "must have exactly one of read/write/append mode";
               }
            }
         } else {
            this.message = "can't use U and writing mode at once";
         }

         this.invalid |= this.message != null;
      }

   }

   public void validate(String encoding, String errors, String newline) {
      if (!this.invalid && this.binary) {
         if (encoding != null) {
            this.message = "binary mode doesn't take an encoding argument";
         } else if (errors != null) {
            this.message = "binary mode doesn't take an errors argument";
         } else if (newline != null) {
            this.message = "binary mode doesn't take a newline argument";
         }

         this.invalid = this.message != null;
      }

   }

   public void checkValid() throws PyException {
      this.validate();
      this.invalid |= this.other;
      if (this.invalid) {
         if (this.message == null) {
            this.message = String.format("invalid mode: '%.20s'", this.originalModeString);
         }

         throw Py.ValueError(this.message);
      }
   }

   public String forFileIO() {
      StringBuilder m = new StringBuilder(2);
      if (this.appending) {
         m.append('a');
      } else if (this.writing) {
         m.append('w');
      } else {
         m.append('r');
      }

      if (this.updating) {
         m.append('+');
      }

      return m.toString();
   }

   public String text() {
      return this.universal ? "U" : "";
   }

   public String toString() {
      StringBuilder m = new StringBuilder(4);
      if (this.appending) {
         m.append('a');
      } else if (this.writing) {
         m.append('w');
      } else {
         m.append('r');
      }

      if (this.updating) {
         m.append('+');
      }

      if (this.text) {
         m.append('t');
      } else if (this.binary) {
         m.append('b');
      }

      if (this.universal) {
         m.append('U');
      }

      return m.toString();
   }
}
