package javax.security.jacc;

import java.util.Arrays;

class URLPatternSpec extends URLPattern {
   private static String EMPTY_STRING = "";
   private transient int hashCodeValue;
   private String canonicalSpec;
   private final String urlPatternList;
   private URLPattern[] urlPatternArray;

   public URLPatternSpec(String urlPatternSpec) {
      super(getFirstPattern(urlPatternSpec));
      int colon = urlPatternSpec.indexOf(":");
      if (colon >= 0) {
         this.urlPatternList = urlPatternSpec.substring(colon + 1);
         this.setURLPatternArray();
      } else {
         this.urlPatternList = null;
      }

   }

   public String getURLPattern() {
      return super.toString();
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof URLPatternSpec) {
         URLPatternSpec that = (URLPatternSpec)o;
         return this.toString().equals(that.toString());
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this.hashCodeValue == 0) {
         this.hashCodeValue = this.toString().hashCode();
      }

      return this.hashCodeValue;
   }

   public boolean implies(URLPatternSpec that) {
      if (that == null) {
         return false;
      } else if (!super.implies(that)) {
         return false;
      } else {
         for(int i = 0; this.urlPatternArray != null && i < this.urlPatternArray.length; ++i) {
            if (this.urlPatternArray[i] != null && this.urlPatternArray[i].implies(that)) {
               return false;
            }
         }

         if (this.urlPatternArray != null && that.implies(this)) {
            if (that.urlPatternArray == null) {
               return false;
            } else {
               boolean[] flags = new boolean[this.urlPatternArray.length];

               int count;
               for(count = 0; count < flags.length; ++count) {
                  flags[count] = false;
               }

               count = 0;

               for(int j = 0; j < that.urlPatternArray.length; ++j) {
                  for(int i = 0; i < flags.length; ++i) {
                     if (!flags[i] && (this.urlPatternArray[i] == null || that.urlPatternArray[j] != null && that.urlPatternArray[j].implies(this.urlPatternArray[i]))) {
                        ++count;
                        flags[i] = true;
                        if (count == flags.length) {
                           return true;
                        }
                     }
                  }
               }

               return count == flags.length;
            }
         } else {
            return true;
         }
      }
   }

   public String toString() {
      if (this.canonicalSpec == null) {
         if (this.urlPatternList == null) {
            this.canonicalSpec = super.toString();
         } else {
            StringBuilder specBuilder = null;
            URLPattern[] var2 = this.urlPatternArray;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               URLPattern urlPattern = var2[var4];
               if (urlPattern != null) {
                  if (specBuilder == null) {
                     specBuilder = new StringBuilder(urlPattern.toString());
                  } else {
                     specBuilder.append(":" + urlPattern.toString());
                  }
               }
            }

            if (specBuilder == null) {
               this.canonicalSpec = super.toString();
            } else {
               this.canonicalSpec = super.toString() + ":" + specBuilder.toString();
            }
         }
      }

      return this.canonicalSpec;
   }

   private static String getFirstPattern(String urlPatternSpec) {
      if (urlPatternSpec == null) {
         throw new IllegalArgumentException("Invalid URLPatternSpec");
      } else {
         int colon = urlPatternSpec.indexOf(":");
         if (colon < 0) {
            return urlPatternSpec;
         } else if (colon > 0) {
            return urlPatternSpec.substring(0, colon);
         } else if (colon == 0) {
            return EMPTY_STRING;
         } else {
            throw new IllegalArgumentException("Invalid URLPatternSpec");
         }
      }
   }

   private void setURLPatternArray() {
      if (this.urlPatternArray == null && this.urlPatternList != null) {
         String[] tokens = this.urlPatternList.split(":", -1);
         int count = tokens.length;
         if (count == 0) {
            throw new IllegalArgumentException("colon followed by empty URLPatternList");
         } else {
            this.urlPatternArray = new URLPattern[count];
            int firstType = this.patternType();

            int i;
            for(i = 0; i < count; ++i) {
               this.urlPatternArray[i] = new URLPattern(tokens[i]);
               if (this.urlPatternArray[i].implies(this)) {
                  throw new IllegalArgumentException("pattern in URLPatternList implies first pattern");
               }

               label81:
               switch (firstType) {
                  case 0:
                     break;
                  case 1:
                  case 2:
                     switch (this.urlPatternArray[i].patternType()) {
                        case 2:
                           if (firstType != 2 || !super.equals(this.urlPatternArray[i]) && super.implies(this.urlPatternArray[i])) {
                              break label81;
                           }

                           throw new IllegalArgumentException("Invalid prefix pattern in URLPatternList");
                        case 3:
                           if (!super.implies(this.urlPatternArray[i])) {
                              throw new IllegalArgumentException("Invalid exact pattern in URLPatternList");
                           }
                           break label81;
                        default:
                           throw new IllegalArgumentException("Invalid pattern type in URLPatternList");
                     }
                  case 3:
                     throw new IllegalArgumentException("invalid URLPatternSpec");
                  default:
                     throw new IllegalArgumentException("Invalid pattern type in URLPatternList");
               }

               if (super.equals(this.urlPatternArray[i])) {
                  throw new IllegalArgumentException("Invalid default pattern in URLPatternList");
               }
            }

            Arrays.sort(this.urlPatternArray);

            for(i = 0; i < this.urlPatternArray.length; ++i) {
               if (this.urlPatternArray[i] != null) {
                  switch (this.urlPatternArray[i].patternType()) {
                     case 2:
                        for(int j = i + 1; j < this.urlPatternArray.length; ++j) {
                           if (this.urlPatternArray[i].implies(this.urlPatternArray[j])) {
                              this.urlPatternArray[j] = null;
                           }
                        }
                  }
               }
            }

         }
      }
   }
}
