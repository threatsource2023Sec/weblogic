package com.bea.httppubsub.internal;

import com.bea.httppubsub.Channel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class ChannelId implements Comparable {
   private static final ChannelIdFactory factory = ChannelIdFactory.getInstance();
   private static final Pattern regexPattern = Pattern.compile("/|(/[a-zA-Z0-9\\*\\-_\\.!~\\(\\)\\$@]+)+");
   private static final String[] ROOT = new String[0];
   private final String[] segments;
   private final Wild wild;
   private final String url;
   private final String name;
   private final Channel.ChannelPattern pattern;
   private static final char[] validChannelChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_', '!', '~', '(', ')', '$', '@'};

   ChannelId(String url) {
      if (url.endsWith("/") && !"/".equals(url)) {
         throw new InvalidChannelException("Non-root channel URL cannot end with \"/\".");
      } else if (!validatePath(url)) {
         throw new InvalidChannelException("Invalid channel URL.");
      } else {
         this.segments = this.splitNameIntoSegments(url);
         if (this.containsWildInSegments(this.segments)) {
            throw new InvalidChannelException("Cannot contain \"*\" or \"**\" in channel URL (not at the end).");
         } else if (this.containsInvalidCharacter(this.segments)) {
            throw new InvalidChannelException("Contains invalid characters in channel URL.");
         } else {
            this.wild = this.generateWild(this.segments);
            this.url = url;
            String urlForUse = url;
            Channel.ChannelPattern type = Channel.ChannelPattern.ITSELF;
            if (url.equals("/*")) {
               urlForUse = "/";
               type = Channel.ChannelPattern.IMMEDIATE_SUBCHANNELS;
            } else if (url.equals("/**")) {
               urlForUse = "/";
               type = Channel.ChannelPattern.ALL_SUBCHANNELS;
            } else if (url.endsWith("/*")) {
               urlForUse = url.substring(0, url.length() - 2);
               type = Channel.ChannelPattern.IMMEDIATE_SUBCHANNELS;
            } else if (url.endsWith("/**")) {
               urlForUse = url.substring(0, url.length() - 3);
               type = Channel.ChannelPattern.ALL_SUBCHANNELS;
            }

            this.name = urlForUse;
            this.pattern = type;
            this.containsInvalidCharacter(this.segments);
         }
      }
   }

   private boolean containsInvalidCharacter(String[] segments) {
      for(int i = 0; i < segments.length; ++i) {
         boolean foundInvalid;
         if (i == segments.length - 1) {
            if (StringUtils.equals(segments[i], "*") || StringUtils.equals(segments[i], "**")) {
               continue;
            }

            foundInvalid = !StringUtils.containsOnly(segments[i], validChannelChars);
         } else {
            foundInvalid = !StringUtils.containsOnly(segments[i], validChannelChars);
         }

         if (foundInvalid) {
            return true;
         }
      }

      return false;
   }

   public static ChannelId newInstance(String url) {
      if (StringUtils.isEmpty(url)) {
         throw new InvalidChannelException("Channel URL cannot be empty.");
      } else {
         return factory.build(url);
      }
   }

   public String toUrl() {
      return this.url;
   }

   public String getChannelName() {
      return this.name;
   }

   public Channel.ChannelPattern getChannelPattern() {
      return this.pattern;
   }

   public boolean isWild() {
      return this.wild != ChannelId.Wild.NONE;
   }

   public boolean isSingleWild() {
      return this.wild == ChannelId.Wild.SINGLE;
   }

   public boolean isDoubleWild() {
      return this.wild == ChannelId.Wild.DOUBLE;
   }

   public boolean matches(String url) {
      return this.wild == ChannelId.Wild.NONE ? this.url.equals(url) : this.matches(newInstance(url));
   }

   public boolean matches(ChannelId name) {
      if (name.isWild()) {
         return this.equals(name);
      } else {
         int i;
         switch (this.wild) {
            case NONE:
               return this.equals(name);
            case SINGLE:
               if (name.segments.length != this.segments.length) {
                  return false;
               } else {
                  i = this.segments.length - 1;

                  do {
                     if (i-- <= 0) {
                        return true;
                     }
                  } while(this.segments[i].equals(name.segments[i]));

                  return false;
               }
            case DOUBLE:
               if (name.segments.length < this.segments.length) {
                  return false;
               } else {
                  i = this.segments.length - 1;

                  do {
                     if (i-- <= 0) {
                        return true;
                     }
                  } while(this.segments[i].equals(name.segments[i]));

                  return false;
               }
            default:
               return false;
         }
      }
   }

   public boolean isParentOf(ChannelId id) {
      if (!this.isWild() && this.depth() < id.depth()) {
         for(int i = this.segments.length - 1; i >= 0; --i) {
            if (!this.segments[i].equals(id.segments[i])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public int depth() {
      return this.segments.length;
   }

   public List getSegments() {
      return Arrays.asList(this.segments);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof ChannelId) {
         ChannelId other = (ChannelId)obj;
         if (this.isWild()) {
            return other.isWild() ? this.url.equals(other.url) : this.matches(other);
         } else {
            return other.isWild() ? other.matches(this) : this.url.equals(other.url);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.url.hashCode();
   }

   public String toString() {
      return "Channel [" + this.url + "]";
   }

   public boolean contains(ChannelId cid) {
      if (cid == null) {
         return false;
      } else if (this == cid) {
         return true;
      } else if (this.url.equals(cid.url)) {
         return true;
      } else {
         if (this.wild == ChannelId.Wild.SINGLE) {
            List segs = cid.getSegments();
            if (this.segments.length == segs.size() && !((String)segs.get(segs.size() - 1)).equals("*") && !((String)segs.get(segs.size() - 1)).equals("**")) {
               for(int i = this.segments.length - 2; i >= 0; --i) {
                  if (!this.segments[i].equals(cid.segments[i])) {
                     return false;
                  }
               }

               return true;
            }
         }

         if (this.wild == ChannelId.Wild.DOUBLE) {
            if (this.depth() > cid.depth()) {
               return false;
            } else {
               for(int i = this.segments.length - 2; i >= 0; --i) {
                  if (!this.segments[i].equals(cid.segments[i])) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   String getSegment(int i) {
      return i < this.segments.length && i >= 0 ? this.segments[i] : null;
   }

   static boolean validatePath(String path) {
      Matcher matcher = regexPattern.matcher(path);
      return matcher.matches();
   }

   private String[] splitNameIntoSegments(String name) {
      String[] result;
      if ("/".equals(name)) {
         result = ROOT;
      } else {
         if (name.charAt(name.length() - 1) == '/') {
            throw new IllegalArgumentException("Non-root channel name cannot end with '/'.");
         }

         result = name.substring(1).split("/");
      }

      return result;
   }

   private boolean containsWildInSegments(String[] segments) {
      for(int i = 0; i < segments.length; ++i) {
         String segment = segments[i];
         if (("*".equals(segment) || "**".equals(segment)) && i != segments.length - 1) {
            return true;
         }
      }

      return false;
   }

   private Wild generateWild(String[] segments) {
      Wild result = ChannelId.Wild.NONE;
      if (segments.length == 0) {
         result = ChannelId.Wild.NONE;
      } else if (ChannelId.Wild.SINGLE.toString().equals(segments[segments.length - 1])) {
         result = ChannelId.Wild.SINGLE;
      } else if (ChannelId.Wild.DOUBLE.toString().equals(segments[segments.length - 1])) {
         result = ChannelId.Wild.DOUBLE;
      }

      return result;
   }

   public int compareTo(ChannelId o) {
      if (this.contains(o) && o.contains(this)) {
         return 0;
      } else if (!this.contains(o) && !o.contains(this)) {
         return 1;
      } else {
         return this.contains(o) ? 1 : -1;
      }
   }

   public static ChannelId[] sort(ChannelId[] cids) {
      if (cids != null && cids.length > 1) {
         List list = new LinkedList();
         list.add(cids[0]);

         for(int i = 1; i < cids.length; ++i) {
            ChannelId cid = cids[i];
            boolean inserted = false;

            for(int j = 0; j < list.size(); ++j) {
               ChannelId cidInList = (ChannelId)list.get(j);
               if (cidInList.contains(cid)) {
                  list.add(j, cid);
                  inserted = true;
                  break;
               }
            }

            if (!inserted) {
               list.add(cid);
            }
         }

         return (ChannelId[])list.toArray(new ChannelId[list.size()]);
      } else {
         return cids;
      }
   }

   public static enum Wild {
      DOUBLE("**"),
      SINGLE("*"),
      NONE("");

      private String rep;

      private Wild(String rep) {
         this.rep = rep;
      }

      public String toString() {
         return this.rep;
      }
   }
}
