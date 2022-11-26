package weblogic.messaging.path;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.naming.NamingException;
import weblogic.common.CompletionRequest;
import weblogic.jms.backend.BEUOOMember;
import weblogic.jms.frontend.FEProducer;
import weblogic.management.ManagementException;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.messaging.runtime.ArrayCursorDelegate;
import weblogic.messaging.runtime.CursorRuntimeImpl;
import weblogic.messaging.runtime.OpenDataConverter;

public class PSEntryCursorDelegate extends ArrayCursorDelegate {
   private final PSEntryInfo[] PSEntryArray;
   private final PathServiceMap pathService;

   public PSEntryCursorDelegate(CursorRuntimeImpl runtimeDelegate, OpenDataConverter converter, int timeout, Key[] keys, PathServiceMap pathService) {
      super(runtimeDelegate, converter, timeout);
      this.pathService = pathService;
      this.entryArray = this.PSEntryArray = new PSEntryInfo[keys.length];

      for(int i = 0; i < keys.length; ++i) {
         this.PSEntryArray[i] = new PSEntryInfo(keys[i], i);
      }

   }

   public void remove(int itemHandle) throws ManagementException {
      this.updateAccessTime();

      try {
         Key key = this.PSEntryArray[itemHandle].getKey();
         this.pathService.getPathHelper().findOrCreateServerInfo(key).cachedRemove(key, (Member)null, 32776);
      } catch (NamingException var3) {
         throw new ManagementException("remove operation failed on item handle " + itemHandle, var3);
      } catch (PathHelper.PathServiceException var4) {
         throw new ManagementException("remove operation failed on item handle " + itemHandle, var4);
      }
   }

   public void update(int itemHandle, CompositeData newMember) throws ManagementException {
      this.updateAccessTime();

      Member member;
      try {
         MemberInfo info = new MemberInfo(newMember);
         member = info.getMember();
      } catch (OpenDataException var10) {
         throw new ManagementException("update operation failed on item handle " + itemHandle, var10);
      }

      CompletionRequest cr = new CompletionRequest();

      try {
         this.pathService.getPathHelper().findOrCreateServerInfo(this.PSEntryArray[itemHandle].getKey()).update(this.PSEntryArray[itemHandle].getKey(), member, cr);
      } catch (NamingException var9) {
         throw new ManagementException("update operation failed on item handle " + itemHandle, var9);
      }

      try {
         cr.getResult();
      } catch (RuntimeException var6) {
         throw var6;
      } catch (Error var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new ManagementException("update operation failed on item handle " + itemHandle, var8);
      }
   }

   public CompositeData getMember(int itemHandle) throws ManagementException {
      this.updateAccessTime();
      CompletionRequest cr = new CompletionRequest();

      try {
         this.pathService.getPathHelper().findOrCreateServerInfo(this.PSEntryArray[itemHandle].getKey()).cachedGet(this.PSEntryArray[itemHandle].getKey(), 33280, cr);
      } catch (NamingException var8) {
         throw new ManagementException("remove operation failed on item handle " + itemHandle, var8);
      }

      Object member;
      try {
         member = (Member)cr.getResult();
      } catch (Throwable var7) {
         throw new ManagementException("getMember operation failed on item handle " + itemHandle, var7);
      }

      if (member instanceof FEProducer.ExtendedBEUOOMember) {
         FEProducer.ExtendedBEUOOMember feMmbr = (FEProducer.ExtendedBEUOOMember)member;
         BEUOOMember beuooMember = new BEUOOMember(feMmbr.getStringId(), ((Member)member).getWLServerName(), feMmbr.getDynamic());
         beuooMember.setTimestamp(feMmbr.getTimeStamp());
         beuooMember.setGeneration(feMmbr.getGeneration());
         member = beuooMember;
      }

      MemberInfo info = new MemberInfo((Member)member);

      try {
         return info.toCompositeData();
      } catch (OpenDataException var6) {
         throw new ManagementException("getMember operation failed on item handle " + itemHandle, var6);
      }
   }
}
