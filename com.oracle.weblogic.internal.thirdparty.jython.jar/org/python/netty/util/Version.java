package org.python.netty.util;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import org.python.netty.util.internal.PlatformDependent;

public final class Version {
   private static final String PROP_VERSION = ".version";
   private static final String PROP_BUILD_DATE = ".buildDate";
   private static final String PROP_COMMIT_DATE = ".commitDate";
   private static final String PROP_SHORT_COMMIT_HASH = ".shortCommitHash";
   private static final String PROP_LONG_COMMIT_HASH = ".longCommitHash";
   private static final String PROP_REPO_STATUS = ".repoStatus";
   private final String artifactId;
   private final String artifactVersion;
   private final long buildTimeMillis;
   private final long commitTimeMillis;
   private final String shortCommitHash;
   private final String longCommitHash;
   private final String repositoryStatus;

   public static Map identify() {
      return identify((ClassLoader)null);
   }

   public static Map identify(ClassLoader classLoader) {
      if (classLoader == null) {
         classLoader = PlatformDependent.getContextClassLoader();
      }

      Properties props = new Properties();

      try {
         Enumeration resources = classLoader.getResources("META-INF/io.netty.versions.properties");

         while(resources.hasMoreElements()) {
            URL url = (URL)resources.nextElement();
            InputStream in = url.openStream();

            try {
               props.load(in);
            } finally {
               try {
                  in.close();
               } catch (Exception var12) {
               }

            }
         }
      } catch (Exception var14) {
      }

      Set artifactIds = new HashSet();
      Iterator var16 = props.keySet().iterator();

      String artifactId;
      while(var16.hasNext()) {
         Object o = var16.next();
         artifactId = (String)o;
         int dotIndex = artifactId.indexOf(46);
         if (dotIndex > 0) {
            String artifactId = artifactId.substring(0, dotIndex);
            if (props.containsKey(artifactId + ".version") && props.containsKey(artifactId + ".buildDate") && props.containsKey(artifactId + ".commitDate") && props.containsKey(artifactId + ".shortCommitHash") && props.containsKey(artifactId + ".longCommitHash") && props.containsKey(artifactId + ".repoStatus")) {
               artifactIds.add(artifactId);
            }
         }
      }

      Map versions = new TreeMap();
      Iterator var19 = artifactIds.iterator();

      while(var19.hasNext()) {
         artifactId = (String)var19.next();
         versions.put(artifactId, new Version(artifactId, props.getProperty(artifactId + ".version"), parseIso8601(props.getProperty(artifactId + ".buildDate")), parseIso8601(props.getProperty(artifactId + ".commitDate")), props.getProperty(artifactId + ".shortCommitHash"), props.getProperty(artifactId + ".longCommitHash"), props.getProperty(artifactId + ".repoStatus")));
      }

      return versions;
   }

   private static long parseIso8601(String value) {
      try {
         return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z")).parse(value).getTime();
      } catch (ParseException var2) {
         return 0L;
      }
   }

   public static void main(String[] args) {
      Iterator var1 = identify().values().iterator();

      while(var1.hasNext()) {
         Version v = (Version)var1.next();
         System.err.println(v);
      }

   }

   private Version(String artifactId, String artifactVersion, long buildTimeMillis, long commitTimeMillis, String shortCommitHash, String longCommitHash, String repositoryStatus) {
      this.artifactId = artifactId;
      this.artifactVersion = artifactVersion;
      this.buildTimeMillis = buildTimeMillis;
      this.commitTimeMillis = commitTimeMillis;
      this.shortCommitHash = shortCommitHash;
      this.longCommitHash = longCommitHash;
      this.repositoryStatus = repositoryStatus;
   }

   public String artifactId() {
      return this.artifactId;
   }

   public String artifactVersion() {
      return this.artifactVersion;
   }

   public long buildTimeMillis() {
      return this.buildTimeMillis;
   }

   public long commitTimeMillis() {
      return this.commitTimeMillis;
   }

   public String shortCommitHash() {
      return this.shortCommitHash;
   }

   public String longCommitHash() {
      return this.longCommitHash;
   }

   public String repositoryStatus() {
      return this.repositoryStatus;
   }

   public String toString() {
      return this.artifactId + '-' + this.artifactVersion + '.' + this.shortCommitHash + ("clean".equals(this.repositoryStatus) ? "" : " (repository: " + this.repositoryStatus + ')');
   }
}
