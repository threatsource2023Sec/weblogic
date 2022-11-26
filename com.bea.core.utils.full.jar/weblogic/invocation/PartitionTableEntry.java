package weblogic.invocation;

public class PartitionTableEntry {
   private String entryName;
   private String entryID;
   private String entryRoot;
   private String entryUserRoot;

   public PartitionTableEntry(String name, String id, String root, String userRoot) {
      this.entryName = name;
      this.entryID = id;
      this.entryRoot = root;
      this.entryUserRoot = userRoot;
   }

   public String getPartitionName() {
      return this.entryName;
   }

   public String getPartitionID() {
      return this.entryID;
   }

   public String getPartitionRoot() {
      return this.entryRoot;
   }

   public String getPartitionUserRoot() {
      return this.entryUserRoot;
   }
}
