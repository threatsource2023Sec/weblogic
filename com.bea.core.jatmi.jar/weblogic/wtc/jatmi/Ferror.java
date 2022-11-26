package weblogic.wtc.jatmi;

public final class Ferror extends Exception {
   private int errno;
   public static final int FMINVAL = 0;
   public static final int FALIGNERR = 1;
   public static final int FNOTFLD = 2;
   public static final int FNOSPACE = 3;
   public static final int FNOTPRES = 4;
   public static final int FBADFLD = 5;
   public static final int FTYPERR = 6;
   public static final int FEUNIX = 7;
   public static final int FBADNAME = 8;
   public static final int FMALLOC = 9;
   public static final int FSYNTAX = 10;
   public static final int FFTOPEN = 11;
   public static final int FFTSYNTAX = 12;
   public static final int FEINVAL = 13;
   public static final int FBADTBL = 14;
   public static final int FBADVIEW = 15;
   public static final int FVFSYNTAX = 16;
   public static final int FVFOPEN = 17;
   public static final int FBADACM = 18;
   public static final int FNOCNAME = 19;
   public static final int FEBADOP = 20;
   public static final int FMAXVAL = 21;

   public Ferror() {
      this.errno = 0;
   }

   public Ferror(int errno) {
      this.errno = errno;
   }

   public Ferror(int errno, String explain) {
      super(explain);
      this.errno = errno;
   }

   public int getFerror() {
      return this.errno;
   }

   public static String Fstrerror(int Ferror) {
      switch (Ferror) {
         case 0:
         case 21:
         default:
            return new String("Unknonw Ferror=(" + Ferror + ")");
         case 1:
            return new String("Fielded buffer not aligned");
         case 2:
            return new String("Buffer not fielded");
         case 3:
            return new String("No space in fielded buffer");
         case 4:
            return new String("Field not present");
         case 5:
            return new String("Unknown field number or type");
         case 6:
            return new String("Invalid field type");
         case 7:
            return new String("UNIX system call error");
         case 8:
            return new String("Unknown field name");
         case 9:
            return new String("Memory allocation failed");
         case 10:
         case 12:
            return new String("Bad syntax in boolean expression");
         case 11:
            return new String("Cannot find or open field table");
         case 13:
            return new String("Invalid argument to function");
         case 14:
            return new String("Destructive concurrent access to field table");
         case 15:
            return new String("Cannot find or get view");
         case 16:
            return new String("Syntax error in viewfile");
         case 17:
            return new String("Cannot find or open view file");
         case 18:
            return new String("ACM contains negative value");
         case 19:
            return new String("Cname not found");
         case 20:
            return new String("Invalid operation type");
      }
   }

   public String toString() {
      switch (this.errno) {
         case 0:
            return new String(this.errno + " (FMINVAL)");
         case 1:
            return new String(this.errno + " (FALIGNERR)");
         case 2:
            return new String(this.errno + " (FNOTFLD)");
         case 3:
            return new String(this.errno + " (FNOSPACE)");
         case 4:
            return new String(this.errno + " (FNOTPRES)");
         case 5:
            return new String(this.errno + " (FBADFLD)");
         case 6:
            return new String(this.errno + " (FTYPERR)");
         case 7:
            return new String(this.errno + " (FEUNIX)");
         case 8:
            return new String(this.errno + " (FBADNAME)");
         case 9:
            return new String(this.errno + " (FMALLOC)");
         case 10:
            return new String(this.errno + " (FSYNTAX)");
         case 11:
            return new String(this.errno + " (FFTOPEN)");
         case 12:
            return new String(this.errno + " (FFTSYNTAX)");
         case 13:
            return new String(this.errno + " (FEINVAL)");
         case 14:
            return new String(this.errno + " (FBADTBL)");
         case 15:
            return new String(this.errno + " (FBADVIEW)");
         case 16:
            return new String(this.errno + " (FVFSYNTAX)");
         case 17:
            return new String(this.errno + " (FVFOPEN)");
         case 18:
            return new String(this.errno + " (FBADACM)");
         case 19:
            return new String(this.errno + " (FNOCNAME)");
         case 20:
            return new String(this.errno + " (FEBADOP)");
         case 21:
            return new String(this.errno + " (FMAXVAL)");
         default:
            return String.valueOf(this.errno);
      }
   }
}
