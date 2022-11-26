package com.octetstring.vde.operation;

import com.asn1c.codec.DataDecoder;
import com.asn1c.codec.DataEncoder;
import com.asn1c.core.BitString;
import com.octetstring.ldapv3.controls.ASN1BERDecoder;
import com.octetstring.ldapv3.controls.ASN1BEREncoder;
import com.octetstring.ldapv3.controls.ASN1Decoder;
import com.octetstring.ldapv3.controls.ASN1Encoder;
import com.octetstring.ldapv3.controls.EntryChangeNotification;
import com.octetstring.ldapv3.controls.PersistentSearch;
import com.octetstring.ldapv3.controls.SortKeyList;
import com.octetstring.ldapv3.controls.SortResult;
import com.octetstring.ldapv3.controls.VLVControlValue;

public class ControlHandler {
   private ASN1Encoder berEncoder = null;
   ASN1Decoder berDecoder = null;
   private DataEncoder de = null;
   private DataDecoder dd = null;
   private int number = 0;
   private static ControlHandler instance = null;

   private ControlHandler() {
      this.de = new DataEncoder();
      this.dd = new DataDecoder();
      this.berEncoder = new ASN1BEREncoder(this.de);
      this.berDecoder = new ASN1BERDecoder(this.dd);
   }

   public static ControlHandler getInstance() {
      synchronized(instance) {
         if (instance == null) {
            instance = new ControlHandler();
         }
      }

      return instance;
   }

   public ASN1Decoder getBERDecoder() {
      return this.berDecoder;
   }

   public ASN1Encoder getBEREncoder() {
      return this.berEncoder;
   }

   public PersistentSearch getPersistentSearch(byte[] val) throws Exception {
      synchronized(this.dd) {
         this.dd.setEncodedData(new BitString(val));
         return this.getBERDecoder().readPersistentSearch();
      }
   }

   public byte[] getPersistentSearchBytes(PersistentSearch ps) throws Exception {
      synchronized(this.de) {
         this.getBEREncoder().writePersistentSearch(ps);
         return this.de.getAndClearEncodedData().toByteArray();
      }
   }

   public EntryChangeNotification getEntryChangeNotification(byte[] val) throws Exception {
      synchronized(this.dd) {
         this.dd.setEncodedData(new BitString(val));
         return this.getBERDecoder().readEntryChangeNotification();
      }
   }

   public byte[] getEntryChangeNotificationBytes(EntryChangeNotification ecn) throws Exception {
      synchronized(this.de) {
         this.getBEREncoder().writeEntryChangeNotification(ecn);
         return this.de.getAndClearEncodedData().toByteArray();
      }
   }

   public SortKeyList getSortKeyList(byte[] val) throws Exception {
      synchronized(this.dd) {
         this.dd.setEncodedData(new BitString(val));
         return this.getBERDecoder().readSortKeyList();
      }
   }

   public byte[] getSortKeyListBytes(SortKeyList skl) throws Exception {
      synchronized(this.de) {
         this.getBEREncoder().writeSortKeyList(skl);
         return this.de.getAndClearEncodedData().toByteArray();
      }
   }

   public SortResult getSortResult(byte[] val) throws Exception {
      synchronized(this.dd) {
         this.dd.setEncodedData(new BitString(val));
         return this.getBERDecoder().readSortResult();
      }
   }

   public byte[] getSortResultBytes(SortResult sr) throws Exception {
      synchronized(this.de) {
         this.getBEREncoder().writeSortResult(sr);
         return this.de.getAndClearEncodedData().toByteArray();
      }
   }

   public VLVControlValue getVLVControlValue(byte[] val) throws Exception {
      synchronized(this.dd) {
         this.dd.setEncodedData(new BitString(val));
         return this.getBERDecoder().readVLVControlValue();
      }
   }

   public byte[] getVLVControlValueBytes(VLVControlValue vlvcv) throws Exception {
      synchronized(this.de) {
         this.getBEREncoder().writeVLVControlValue(vlvcv);
         return this.de.getAndClearEncodedData().toByteArray();
      }
   }
}
