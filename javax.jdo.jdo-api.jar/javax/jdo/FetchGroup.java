package javax.jdo;

import java.util.Set;

public interface FetchGroup {
   String DEFAULT = "default";
   String RELATIONSHIP = "relationship";
   String MULTIVALUED = "multivalued";
   String BASIC = "basic";
   String ALL = "all";

   int hashCode();

   boolean equals(Object var1);

   String getName();

   Class getType();

   boolean getPostLoad();

   FetchGroup setPostLoad(boolean var1);

   FetchGroup addMember(String var1);

   FetchGroup addMembers(String... var1);

   FetchGroup removeMember(String var1);

   FetchGroup removeMembers(String... var1);

   FetchGroup addCategory(String var1);

   FetchGroup removeCategory(String var1);

   FetchGroup setRecursionDepth(String var1, int var2);

   int getRecursionDepth(String var1);

   Set getMembers();

   FetchGroup setUnmodifiable();

   boolean isUnmodifiable();
}
