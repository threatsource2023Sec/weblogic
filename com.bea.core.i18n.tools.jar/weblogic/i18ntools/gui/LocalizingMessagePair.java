package weblogic.i18ntools.gui;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.ImageIcon;
import weblogic.i18n.tools.BasicMessage;
import weblogic.i18n.tools.DuplicateElementException;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18n.tools.MessageNotFoundException;
import weblogic.i18n.tools.WrongTypeException;
import weblogic.i18ntools.parser.LocaleLogMessage;
import weblogic.i18ntools.parser.LocaleMessage;
import weblogic.i18ntools.parser.LocaleMessageCatalog;

public final class LocalizingMessagePair {
   private static final boolean debug = false;
   private MsgEditorTextFormatter fmt = MsgEditorTextFormatter.getInstance();
   private MessageCatalog myMasterCatalog;
   private LocaleMessageCatalog myLocaleCatalog;
   private MessageViewer myMasterViewer;
   private MessageViewer myLocaleViewer;
   private MessageCatalogEditor myParent;
   private ImageIcon myGoodIcon;
   private ImageIcon myOldIcon;
   private ImageIcon myNotThereIcon;

   public ImageIcon getGoodIcon() {
      return this.myGoodIcon;
   }

   public ImageIcon getOldIcon() {
      return this.myOldIcon;
   }

   public ImageIcon getNotThereIcon() {
      return this.myNotThereIcon;
   }

   public MessageCatalog getMasterCatalog() {
      return this.myMasterCatalog;
   }

   public void setMasterCatalog(MessageCatalog master) {
      this.myMasterCatalog = master;
   }

   public void setMasterCatalog(MessageCatalog master, String filename) {
      this.myMasterCatalog = master;
      this.setCatalogNames(filename);
   }

   public LocaleMessageCatalog getLocaleCatalog() {
      return this.myLocaleCatalog;
   }

   public void setLocaleCatalog(LocaleMessageCatalog local) {
      this.myLocaleCatalog = local;
   }

   public MessageViewer[] getMessageViewers() {
      MessageViewer[] msgViewers = new MessageViewer[]{this.myMasterViewer, this.myLocaleViewer};
      return msgViewers;
   }

   public LocalizingMessagePair(MessageCatalogEditor parent, MessageCatalog master, LocaleMessageCatalog local) {
      this.myParent = parent;
      this.myMasterCatalog = master;
      this.myLocaleCatalog = local;
      this.myGoodIcon = new ImageIcon(this.myParent.loadImage("/weblogic/i18ntools/gui/images/yellowHappy.gif"), "Locale message has a later date than master message");
      this.myOldIcon = new ImageIcon(this.myParent.loadImage("/weblogic/i18ntools/gui/images/greenGrumpy.gif"), this.fmt.iconMsgStale());
      this.myNotThereIcon = new ImageIcon(this.myParent.loadImage("/weblogic/i18ntools/gui/images/blueShock.gif"), this.fmt.iconNoMsg());
   }

   private void setCatalogNames(String pathAndFile) {
      File fil = new File(pathAndFile);
      String catalogFileName = fil.getName();
      this.myMasterCatalog.setPath(fil.getAbsolutePath());
      this.myMasterCatalog.set("filename", catalogFileName);
   }

   public void addMessageViewers(String masterTitle, String localeTitle) {
      if (this.myMasterViewer != null) {
         this.myMasterViewer.setVisible(false);
      }

      this.myMasterViewer = new MessageViewer(this.myParent, this.myMasterCatalog, this.myMasterCatalog.getPath(), masterTitle);
      if (this.myLocaleViewer != null) {
         this.myLocaleViewer.setVisible(false);
      }

      if (this.myLocaleCatalog != null) {
         this.myLocaleViewer = new MessageViewer(this.myParent, this.myLocaleCatalog, this.myLocaleCatalog.getPath(), localeTitle);
         this.setDateColors();
      }

   }

   public void removeViewer(MessageViewer vwr) {
      if (this.myMasterViewer == vwr) {
         this.myMasterViewer = null;
      } else if (this.myLocaleViewer == vwr) {
         this.myLocaleViewer = null;
      }

   }

   public void changeLogMessage(LocaleLogMessage logMsg) throws WrongTypeException, MessageNotFoundException {
      this.myLocaleCatalog.changeLogMessage(logMsg, logMsg.getMessageId());
      if (this.myLocaleViewer != null) {
         this.myLocaleViewer.changeLogMessage(logMsg, logMsg.getMessageId());
         this.setDateColors();
      }

   }

   public void addLogMessage(LocaleLogMessage logMsg) throws WrongTypeException, DuplicateElementException {
      this.myLocaleCatalog.addLogMessage(logMsg);
      if (this.myLocaleViewer != null) {
         this.myLocaleViewer.addLogMessage(logMsg);
         this.setDateColors();
      }

   }

   public void changeMessage(LocaleMessage msg) throws WrongTypeException, MessageNotFoundException {
      this.myLocaleCatalog.changeMessage(msg, msg.getMessageId());
      if (this.myLocaleViewer != null) {
         this.myLocaleViewer.changeSimpleMessage(msg, msg.getMessageId());
         this.setDateColors();
      }

   }

   public void addMessage(LocaleMessage msg) throws WrongTypeException, DuplicateElementException {
      this.myLocaleCatalog.addMessage(msg);
      if (this.myLocaleViewer != null) {
         this.myLocaleViewer.addMessage(msg);
         this.setDateColors();
      }

   }

   public void showMessages(BasicMessage masterLogMsg, BasicMessage locLogMsg) {
      if (this.myMasterViewer != null) {
         this.myMasterViewer.showMessage(masterLogMsg);
      }

      if (this.myLocaleViewer != null) {
         this.myLocaleViewer.showMessage(locLogMsg);
         this.setDateColors();
      }

   }

   private void setDateColors() {
      Vector masterMessages;
      if (this.myMasterCatalog.getCatType() == 2) {
         masterMessages = this.myMasterCatalog.getLogMessages();
      } else {
         masterMessages = this.myMasterCatalog.getMessages();
      }

      String masterFileDate = String.valueOf((new File(this.myMasterCatalog.getPath())).lastModified());
      Enumeration e = masterMessages.elements();

      while(true) {
         while(e.hasMoreElements()) {
            BasicMessage msg = (BasicMessage)e.nextElement();
            BasicMessage locMsg = this.myLocaleCatalog.findMessage(msg.getMessageId());
            if (locMsg == null) {
               this.colorNoLocaleMessage(msg.getMessageId());
            } else {
               String mdlc = msg.getDateLastChanged();
               if (mdlc == null || !msg.hashesOK()) {
                  mdlc = masterFileDate;
               }

               String ldlc = locMsg.getDateLastChanged();
               if (mdlc != null && !mdlc.equals("") && ldlc != null && !ldlc.equals("")) {
                  long mLongDate = Long.decode(mdlc);
                  long lLongDate = Long.decode(ldlc);
                  if ((new Date(lLongDate)).compareTo(new Date(mLongDate)) < 0) {
                     this.colorLocaleOld(msg.getMessageId(), locMsg.getMessageId());
                  } else {
                     this.colorLocaleOK(msg.getMessageId(), locMsg.getMessageId());
                  }
               } else {
                  this.colorLocaleOld(msg.getMessageId(), locMsg.getMessageId());
               }
            }
         }

         return;
      }
   }

   private void colorNoLocaleMessage(String id) {
      this.myMasterViewer.setMessageStatus(id, this.myNotThereIcon);
   }

   private void colorLocaleOld(String masterId, String localeId) {
      this.myMasterViewer.setMessageStatus(masterId, this.myOldIcon);
      this.myLocaleViewer.setMessageStatus(localeId, this.myOldIcon);
   }

   private void colorLocaleOK(String masterId, String localeId) {
      this.myMasterViewer.setMessageStatus(masterId, this.myGoodIcon);
      this.myLocaleViewer.setMessageStatus(localeId, this.myGoodIcon);
   }
}
