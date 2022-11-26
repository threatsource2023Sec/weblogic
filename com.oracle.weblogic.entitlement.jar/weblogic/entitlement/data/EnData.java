package weblogic.entitlement.data;

import java.util.Collection;
import java.util.List;
import weblogic.entitlement.util.TextFilter;

public interface EnData {
   Collection fetchRoleIds(String var1, TextFilter var2);

   Collection fetchResourceRoleIds(TextFilter var1);

   Collection fetchResourceNames(TextFilter var1);

   Collection fetchRoles(String var1, TextFilter var2);

   Collection fetchResources(TextFilter var1);

   Collection fetchRoles(String var1);

   Collection fetchGlobalRoles();

   ERole[] fetchRoles(ERoleId[] var1, boolean var2) throws EnFinderException;

   EResource[] fetchResources(String[] var1, boolean var2) throws EnFinderException;

   void update(ERole[] var1, boolean var2) throws EnFinderException;

   void updateAuxiliary(ERole[] var1, boolean var2) throws EnFinderException;

   void update(EResource[] var1, boolean var2) throws EnFinderException;

   void create(ERole[] var1, boolean var2) throws EnDuplicateKeyException, EnCreateException;

   void create(EResource[] var1, boolean var2) throws EnDuplicateKeyException, EnCreateException;

   void removeRoles(ERoleId[] var1) throws EnFinderException;

   void removeResources(String[] var1) throws EnFinderException;

   void createPredicate(String var1) throws EnDuplicateKeyException;

   void removePredicate(String var1) throws EnFinderException;

   boolean predicateExists(String var1);

   Collection fetchPredicates(TextFilter var1);

   void setDataChangeListener(EnDataChangeListener var1);

   void applicationDeletedResources(String var1, int var2, String var3) throws EnFinderException, EnRemoveException;

   void cleanupAfterCollectionResources(String var1, long var2, List var4) throws EnFinderException, EnRemoveException;

   void cleanupAfterCollectionRoles(String var1, long var2, List var4) throws EnFinderException, EnRemoveException;

   void cleanupAfterDeployResources(String var1, int var2, String var3, long var4) throws EnFinderException, EnRemoveException;

   void applicationCopyResources(String var1, String var2) throws EnCreateException;

   void applicationDeletedRoles(String var1, int var2, String var3) throws EnFinderException, EnRemoveException;

   void cleanupAfterDeployRoles(String var1, int var2, String var3, long var4) throws EnFinderException, EnRemoveException;

   void applicationCopyRoles(String var1, String var2) throws EnCreateException;

   EnResourceCursor findResources(TextFilter var1, int var2, EnCursorResourceFilter var3);

   EnRoleCursor findRoles(TextFilter var1, TextFilter var2, int var3, EnCursorRoleFilter var4);

   void createForCollection(EResource[] var1) throws EnConflictException, EnDuplicateKeyException, EnCreateException;

   void createPolicyCollectionInfo(String var1, String var2, String var3) throws EnCreateException, EnConflictException;

   void createRoleCollectionInfo(String var1, String var2, String var3) throws EnCreateException, EnConflictException;

   EPolicyCollectionInfo fetchPolicyCollectionInfo(String var1);

   void createForCollection(ERole[] var1) throws EnConflictException, EnDuplicateKeyException, EnCreateException;

   ERoleCollectionInfo fetchRoleCollectionInfo(String var1);
}
