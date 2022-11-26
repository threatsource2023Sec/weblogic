package weblogic.t3.srvr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.inject.Singleton;
import org.glassfish.hk2.runlevel.Sorter;
import weblogic.kernel.T3Srvr2Logger;

@Singleton
public class RandomServiceSorter implements Sorter {
   private static final String DO_RANDOM_SEED_FILE = "com.oracle.weblogic.debug.RandomizeServerServices.filename";
   private static final String DO_RANDOM_SEED_DEFAULT_FILE = "RandomServerServiceSeed";
   private static final String DO_RANDOM_SEED = "com.oracle.weblogic.debug.RandomizeServerServices.seed";
   private static final String USE_FILE_FLAG = "com.oracle.weblogic.debug.RandomizeServerServices.usefile";
   private final Random random;

   RandomServiceSorter() {
      String randomFileString = System.getProperty("com.oracle.weblogic.debug.RandomizeServerServices.filename", "RandomServerServiceSeed");
      boolean useFile = Boolean.parseBoolean(System.getProperty("com.oracle.weblogic.debug.RandomizeServerServices.usefile", Boolean.TRUE.toString()));
      File randomFile = new File(randomFileString);
      boolean writeOutputFile = useFile;
      String userPropertySeed = System.getProperty("com.oracle.weblogic.debug.RandomizeServerServices.seed");
      long randomSeed;
      if (userPropertySeed != null) {
         try {
            randomSeed = Long.parseLong(userPropertySeed);
         } catch (NumberFormatException var11) {
            randomSeed = System.currentTimeMillis();
            T3Srvr2Logger.logCantParseRandomSeed(userPropertySeed, randomSeed);
         }
      } else if (useFile && randomFile.exists()) {
         try {
            randomSeed = readRandomFile(randomFile);
            writeOutputFile = false;
         } catch (IOException var10) {
            randomSeed = System.currentTimeMillis();
            T3Srvr2Logger.logCantParseRandomSeedFile(randomFile.getAbsolutePath(), randomSeed);
         }
      } else {
         randomSeed = System.currentTimeMillis();
      }

      this.random = new Random(randomSeed);
      T3Srvr2Logger.logStartupServicesRandomized(randomSeed, useFile ? ".  The seed can be found in file " + randomFile.getAbsolutePath() : ".");
      if (writeOutputFile) {
         try {
            writeNewRandomFile(randomFile, randomSeed);
         } catch (IOException var9) {
            T3Srvr2Logger.logCantWriteRandomSeedFile(randomFile.getAbsolutePath(), randomSeed);
         }

      }
   }

   private static long readRandomFile(File randomFile) throws IOException {
      long retVal;
      if (!randomFile.canRead()) {
         retVal = System.currentTimeMillis();
         T3Srvr2Logger.logRandomizerFileNotReadable(randomFile.getAbsolutePath(), retVal);
         return retVal;
      } else {
         BufferedReader reader = new BufferedReader(new FileReader(randomFile));

         try {
            String line = reader.readLine();

            try {
               retVal = Long.parseLong(line);
            } catch (NumberFormatException var9) {
               retVal = System.currentTimeMillis();
               T3Srvr2Logger.logRandomizerFileDataNotReadable(randomFile.getAbsolutePath(), retVal);
            }
         } finally {
            reader.close();
         }

         return retVal;
      }
   }

   private static void writeNewRandomFile(File randomFile, long seed) throws IOException {
      if (randomFile.exists()) {
         randomFile.delete();
      }

      randomFile.createNewFile();
      PrintWriter writer = null;

      try {
         writer = new PrintWriter(randomFile);
         writer.println("" + seed);
      } finally {
         if (writer != null) {
            writer.close();
         }

      }

   }

   public List sort(List arg0) {
      if (arg0 != null && arg0.size() > 0) {
         ArrayList retVal = new ArrayList(arg0);
         Collections.shuffle(retVal, this.random);
         return retVal;
      } else {
         return arg0;
      }
   }
}
