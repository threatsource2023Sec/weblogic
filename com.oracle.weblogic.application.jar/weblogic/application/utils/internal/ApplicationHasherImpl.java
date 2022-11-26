package weblogic.application.utils.internal;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import weblogic.application.utils.ApplicationHasher;

public class ApplicationHasherImpl implements ApplicationHasher {
   private static final String DEFAULT_ALGORITHM = "SHA-256";
   private static final int BUFFER_SIZE = 10485760;
   private final MessageDigest digest;
   private final String algorithm;
   private final FileFilter filter;
   private final LinkedList unexploded = new LinkedList();
   private final LinkedList exploded = new LinkedList();
   private final Map individualDigests = new LinkedHashMap();
   private boolean finished = false;
   private IllegalStateException failure;
   private String finalResult;
   private BASE64URLEncoder encoder = new BASE64URLEncoder(false);
   private static final Map algorithmCodes = new HashMap();

   public ApplicationHasherImpl(String algorithm, FileFilter filter) {
      if (algorithm == null) {
         this.algorithm = "SHA-256";
      } else {
         if (!algorithmCodes.containsKey(algorithm)) {
            throw new IllegalArgumentException("Unknown algorithm " + algorithm + ". Only the following algorithms supported: " + algorithmCodes.keySet());
         }

         this.algorithm = algorithm;
      }

      this.filter = filter;

      try {
         this.digest = MessageDigest.getInstance(this.algorithm);
      } catch (NoSuchAlgorithmException var4) {
         throw new IllegalArgumentException(var4);
      }
   }

   private boolean addMe(File toFilter) {
      return this.filter == null ? true : this.filter.accept(toFilter);
   }

   public synchronized void addLibrariesOrApplication(File file, File... others) throws IllegalStateException, IllegalArgumentException {
      if (this.finished) {
         throw new IllegalStateException("Hash is already finished " + this);
      } else if (this.failure != null) {
         throw this.failure;
      } else if (file == null) {
         throw new IllegalArgumentException("Must add at least one file");
      } else {
         if (others == null) {
            others = new File[0];
         }

         LinkedList toAddUnexploded = new LinkedList();
         if (this.addMe(file)) {
            toAddUnexploded.add(file);
         }

         File[] var4 = others;
         int var5 = others.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File other = var4[var6];
            if (other == null) {
               throw new IllegalArgumentException("The others array contained a null entry");
            }

            if (this.addMe(other)) {
               toAddUnexploded.add(other);
            }
         }

         List addInToExploded = this.explode(toAddUnexploded);

         try {
            this.addInAllToTheHash(addInToExploded);
         } catch (IOException var8) {
            this.failure = new IllegalStateException(var8);
            throw this.failure;
         } catch (DigestException var9) {
            this.failure = new IllegalStateException(var9);
            throw this.failure;
         }

         this.unexploded.addAll(toAddUnexploded);
         this.exploded.addAll(addInToExploded);
      }
   }

   private void addInAllToTheHash(List allToAdd) throws IOException, DigestException {
      byte[] data = new byte[10485760];
      Iterator var3 = allToAdd.iterator();

      while(var3.hasNext()) {
         File toAdd = (File)var3.next();

         MessageDigest iDigest;
         try {
            iDigest = MessageDigest.getInstance(this.algorithm);
         } catch (NoSuchAlgorithmException var13) {
            throw new IOException(var13);
         }

         FileInputStream fis = new FileInputStream(toAdd);

         try {
            int bytesRead;
            while((bytesRead = fis.read(data)) > 0) {
               this.digest.update(data, 0, bytesRead);
               iDigest.update(data, 0, bytesRead);
            }

            byte[] rawDigest = iDigest.digest();
            String encoded = this.encoder.encodeBuffer(rawDigest);
            this.individualDigests.put(toAdd.getPath(), encoded);
         } finally {
            fis.close();
         }
      }

   }

   private List explode(List explodeMes) {
      LinkedList retVal = new LinkedList();
      Iterator var3 = explodeMes.iterator();

      while(var3.hasNext()) {
         File explodeMe = (File)var3.next();
         if (!explodeMe.exists()) {
            throw new IllegalArgumentException("File " + explodeMe.getAbsolutePath() + " does not exist");
         }

         if (!explodeMe.canRead()) {
            throw new IllegalArgumentException("File " + explodeMe.getAbsolutePath() + " cannot be read");
         }

         if (!explodeMe.isDirectory()) {
            retVal.add(explodeMe);
         } else {
            try {
               this.explodeDirectory(explodeMe, retVal);
            } catch (IOException var6) {
               throw new IllegalArgumentException(var6);
            }
         }
      }

      return retVal;
   }

   private void explodeDirectory(File directory, List retVal) throws IOException {
      Path dirAsPath = directory.toPath();
      final TreeMap sorter = new TreeMap();
      Files.walkFileTree(dirAsPath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor() {
         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Objects.requireNonNull(file);
            if (Files.isDirectory(file, new LinkOption[0])) {
               return FileVisitResult.CONTINUE;
            } else {
               File val = file.toFile();
               if (!ApplicationHasherImpl.this.addMe(val)) {
                  return FileVisitResult.CONTINUE;
               } else {
                  sorter.put(val.getPath(), val);
                  return FileVisitResult.CONTINUE;
               }
            }
         }
      });
      Iterator var5 = sorter.keySet().iterator();

      while(var5.hasNext()) {
         String key = (String)var5.next();
         retVal.add(sorter.get(key));
      }

   }

   public synchronized List getFilesInHash() {
      return Collections.unmodifiableList(this.unexploded);
   }

   public synchronized List getExplodedFilesInHash() {
      return Collections.unmodifiableList(this.exploded);
   }

   public synchronized String finishHash() {
      if (this.failure != null) {
         throw this.failure;
      } else if (this.finalResult != null) {
         return this.finalResult;
      } else {
         byte[] rawDigest = this.digest.digest();
         String encoded = this.encoder.encodeBuffer(rawDigest);
         return encoded;
      }
   }

   public synchronized boolean isFinished() {
      if (this.failure != null) {
         throw this.failure;
      } else {
         return this.finished;
      }
   }

   public Map getIndividualHashes() {
      return Collections.unmodifiableMap(this.individualDigests);
   }

   public String toString() {
      return "ApplicationHasherImpl(" + this.algorithm + "," + this.filter + "," + System.identityHashCode(this) + ")";
   }

   static {
      algorithmCodes.put("MD5", "00");
      algorithmCodes.put("SHA-1", "01");
      algorithmCodes.put("SHA-256", "02");
   }
}
