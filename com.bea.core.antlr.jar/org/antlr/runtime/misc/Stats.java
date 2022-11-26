package org.antlr.runtime.misc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class Stats {
   public static final String ANTLRWORKS_DIR = "antlrworks";

   public static double stddev(int[] X) {
      int m = X.length;
      if (m <= 1) {
         return 0.0;
      } else {
         double xbar = avg(X);
         double s2 = 0.0;

         for(int i = 0; i < m; ++i) {
            s2 += ((double)X[i] - xbar) * ((double)X[i] - xbar);
         }

         s2 /= (double)(m - 1);
         return Math.sqrt(s2);
      }
   }

   public static double avg(int[] X) {
      double xbar = 0.0;
      int m = X.length;
      if (m == 0) {
         return 0.0;
      } else {
         for(int i = 0; i < m; ++i) {
            xbar += (double)X[i];
         }

         return xbar >= 0.0 ? xbar / (double)m : 0.0;
      }
   }

   public static int min(int[] X) {
      int min = Integer.MAX_VALUE;
      int m = X.length;
      if (m == 0) {
         return 0;
      } else {
         for(int i = 0; i < m; ++i) {
            if (X[i] < min) {
               min = X[i];
            }
         }

         return min;
      }
   }

   public static int max(int[] X) {
      int max = Integer.MIN_VALUE;
      int m = X.length;
      if (m == 0) {
         return 0;
      } else {
         for(int i = 0; i < m; ++i) {
            if (X[i] > max) {
               max = X[i];
            }
         }

         return max;
      }
   }

   public static double avg(List X) {
      double xbar = 0.0;
      int m = X.size();
      if (m == 0) {
         return 0.0;
      } else {
         for(int i = 0; i < m; ++i) {
            xbar += (double)(Integer)X.get(i);
         }

         return xbar >= 0.0 ? xbar / (double)m : 0.0;
      }
   }

   public static int min(List X) {
      int min = Integer.MAX_VALUE;
      int m = X.size();
      if (m == 0) {
         return 0;
      } else {
         for(int i = 0; i < m; ++i) {
            if ((Integer)X.get(i) < min) {
               min = (Integer)X.get(i);
            }
         }

         return min;
      }
   }

   public static int max(List X) {
      int max = Integer.MIN_VALUE;
      int m = X.size();
      if (m == 0) {
         return 0;
      } else {
         for(int i = 0; i < m; ++i) {
            if ((Integer)X.get(i) > max) {
               max = (Integer)X.get(i);
            }
         }

         return max;
      }
   }

   public static int sum(int[] X) {
      int s = 0;
      int m = X.length;
      if (m == 0) {
         return 0;
      } else {
         for(int i = 0; i < m; ++i) {
            s += X[i];
         }

         return s;
      }
   }

   public static void writeReport(String filename, String data) throws IOException {
      String absoluteFilename = getAbsoluteFileName(filename);
      File f = new File(absoluteFilename);
      File parent = f.getParentFile();
      parent.mkdirs();
      FileOutputStream fos = new FileOutputStream(f, true);
      BufferedOutputStream bos = new BufferedOutputStream(fos);
      PrintStream ps = new PrintStream(bos);
      ps.println(data);
      ps.close();
      bos.close();
      fos.close();
   }

   public static String getAbsoluteFileName(String filename) {
      return System.getProperty("user.home") + File.separator + "antlrworks" + File.separator + filename;
   }
}
