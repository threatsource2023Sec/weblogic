package weblogic.i18ntools.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import weblogic.i18n.tools.BasicLogMessage;
import weblogic.i18n.tools.BasicMessage;
import weblogic.i18n.tools.BasicMessageCatalog;
import weblogic.i18n.tools.LogMessage;
import weblogic.i18n.tools.Message;
import weblogic.i18n.tools.MessageCatalog;
import weblogic.i18ntools.parser.LocaleMessageCatalog;

public final class MessageViewer extends JFrame {
   private static final boolean debug = false;
   private static final int STATUS_COLUMN = 0;
   private MsgEditorTextFormatter fmt = MsgEditorTextFormatter.getInstance();
   protected final String[] myLogTitles;
   protected final String[] myStatusLogTitles;
   protected final String[] mySimpleTitles;
   protected final String[] myStatusSimpleTitles;
   protected Object[][] myData;
   private JTable myTableView;
   private AbstractTableModel myMessageModel;
   private String myFilePath;
   private MessageCatalogEditor myParent;
   private BasicMessageCatalog myCatalog;
   private boolean myDoingLogMessages;
   private boolean myDoingSimpleMessages;
   private String myTitle;
   private String[] myUsingTitles;
   private boolean myIsLocaleViewer;
   private ImageIcon myEmptyIcon;
   private int myIdCol;
   private int myStatusCol;

   public String getFilePath() {
      return this.myFilePath;
   }

   public boolean isDoingLogMessages() {
      return this.myDoingLogMessages;
   }

   public boolean isDoingSimpleMessages() {
      return this.myDoingSimpleMessages;
   }

   public MessageViewer(MessageCatalogEditor parent, BasicMessageCatalog cat, String fileName, String title) {
      this.myLogTitles = new String[]{this.fmt.titleId(), this.fmt.titleMethod(), this.fmt.titleMethodType(), this.fmt.titleComment(), this.fmt.titleSeverity(), this.fmt.titleBody(), this.fmt.titleDetail(), this.fmt.titleAction(), this.fmt.titleCause(), this.fmt.titleRetired()};
      this.myStatusLogTitles = new String[]{this.fmt.titleStatus(), this.fmt.titleId(), this.fmt.titleComment(), this.fmt.titleBody(), this.fmt.titleDetail(), this.fmt.titleAction(), this.fmt.titleCause()};
      this.mySimpleTitles = new String[]{this.fmt.titleId(), this.fmt.titleComment(), this.fmt.titleMethod(), this.fmt.titleBody()};
      this.myStatusSimpleTitles = new String[]{this.fmt.titleStatus(), this.fmt.titleId(), this.fmt.titleComment(), this.fmt.titleBody()};
      this.setTitle(this.fmt.titleMsgViewer() + title);
      this.myTitle = title;
      this.myFilePath = fileName;
      this.myParent = parent;
      this.myCatalog = cat;
      this.myDoingSimpleMessages = false;
      this.myDoingLogMessages = false;
      this.myEmptyIcon = new ImageIcon(this.myParent.loadImage("/weblogic/i18ntools/gui/images/empty.gif"), (String)null);
      if (this.myParent instanceof MessageLocalizer) {
         this.myIdCol = 1;
      } else {
         this.myIdCol = 0;
      }

      this.myStatusCol = 0;
      this.addWindowListener(this.myParent);
      Image icon = this.myParent.loadImage("/weblogic/i18ntools/gui/images/W.gif");
      this.setIconImage(icon);
      if (cat == null) {
         JOptionPane.showMessageDialog(this.myParent, this.fmt.msgChooseCatToView());
      } else {
         if (cat instanceof LocaleMessageCatalog) {
            this.myIsLocaleViewer = true;
         } else {
            this.myIsLocaleViewer = false;
         }

         if (!this.showFirstMessages()) {
            String catType;
            if (this.myCatalog instanceof LocaleMessageCatalog) {
               catType = "Locale";
            } else if (this.myParent instanceof MessageLocalizer) {
               catType = "Master";
            } else {
               catType = "";
            }

            JOptionPane.showMessageDialog(this.myParent, this.fmt.msgEmptyCat(catType));
         }
      }

   }

   private boolean showFirstMessages() {
      Vector logMessagesV = this.myCatalog.getLogMessages();
      Vector simpleMessagesV = this.myCatalog.getMessages();
      boolean success = true;
      if (logMessagesV != null && logMessagesV.size() > 0) {
         if (this.countValidMessages(logMessagesV) <= 0) {
            return false;
         }

         this.setupLogData(logMessagesV);
      } else {
         if (simpleMessagesV == null || simpleMessagesV.size() <= 0) {
            return false;
         }

         if (this.countValidMessages(simpleMessagesV) <= 0) {
            return false;
         }

         this.setupSimpleData(simpleMessagesV);
      }

      if (success) {
         this.myMessageModel = new ViewerTableModel(this, this.myUsingTitles);
         this.myTableView = new JTable(this.myMessageModel);

         try {
            TableColumn tableCol = this.myTableView.getColumn(this.fmt.titleStatus());
            if (tableCol != null) {
               tableCol.setPreferredWidth(16);
            }
         } catch (IllegalArgumentException var16) {
         }

         this.myTableView.setSelectionMode(0);
         MouseAdapter clickListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
               int index = MessageViewer.this.myTableView.getSelectedRow();
               MessageViewer.this.myParent.setCatalog(MessageViewer.this.myCatalog, MessageViewer.this.myFilePath);
               String id = (String)MessageViewer.this.myMessageModel.getValueAt(index, MessageViewer.this.myIdCol);
               BasicMessage msg;
               if (MessageViewer.this.myDoingLogMessages) {
                  msg = MessageViewer.this.myCatalog.findMessage(id);
                  MessageViewer.this.myParent.setLogMessageFields(msg, true);
               } else if (MessageViewer.this.myDoingSimpleMessages) {
                  msg = MessageViewer.this.myCatalog.findMessage(id);
                  MessageViewer.this.myParent.setSimpleMessageFields(msg, true);
               }

            }
         };
         this.myTableView.addMouseListener(clickListener);
         JScrollPane scrollpane = new JScrollPane(this.myTableView);
         scrollpane.setPreferredSize(new Dimension(700, 300));
         String title = this.myTitle + (new File(this.myFilePath)).getName();
         this.setTitle(this.fmt.titleMsgViewer() + title);
         JLabel viewerTitle = new JLabel(this.fmt.labelCatName(title));
         Font fnt = viewerTitle.getFont();
         fnt = new Font(fnt.getName(), fnt.getStyle(), fnt.getSize() + 4);
         viewerTitle.setFont(fnt);
         JLabel messageType;
         if (this.myDoingLogMessages) {
            messageType = new JLabel(this.fmt.logMessageTitle());
         } else {
            messageType = new JLabel(MessageEditor.fmt.simpleMessageTitle());
         }

         fnt = messageType.getFont();
         fnt = new Font(fnt.getName(), fnt.getStyle(), fnt.getSize() + 4);
         messageType.setFont(fnt);
         Color clr = messageType.getForeground();
         clr = clr.darker().darker();
         messageType.setForeground(clr);
         Container contPane = this.getContentPane();
         Box typeAndLegendBox = Box.createHorizontalBox();
         if (this.myParent instanceof MessageLocalizer) {
            typeAndLegendBox.add(messageType);
            JPanel legendBox = new JPanel();
            legendBox.setLayout(new GridBagLayout());
            GridBagConstraints legendGBC = new GridBagConstraints();
            legendGBC.anchor = 11;
            legendGBC.fill = 2;
            legendGBC.gridx = legendGBC.gridy = 0;
            legendGBC.weightx = 0.0;
            legendGBC.gridwidth = 1;
            LocalizingMessagePair tempLoc = new LocalizingMessagePair(this.myParent, (MessageCatalog)null, (LocaleMessageCatalog)null);
            legendGBC.ipadx = 10;
            legendBox.add(new JLabel(tempLoc.getGoodIcon()), legendGBC);
            ++legendGBC.gridx;
            legendBox.add(new JLabel(this.fmt.labelUpToDate()), legendGBC);
            ++legendGBC.gridy;
            legendGBC.gridx = 0;
            legendBox.add(new JLabel(tempLoc.getOldIcon()), legendGBC);
            ++legendGBC.gridx;
            legendBox.add(new JLabel(this.fmt.labelStale()), legendGBC);
            ++legendGBC.gridy;
            legendGBC.gridx = 0;
            legendBox.add(new JLabel(tempLoc.getNotThereIcon()), legendGBC);
            ++legendGBC.gridx;
            legendBox.add(new JLabel(this.fmt.labelNoLocaleMsg()), legendGBC);
            typeAndLegendBox.add(legendBox);
         }

         contPane.setLayout(new GridBagLayout());
         GridBagConstraints cnst = new GridBagConstraints();
         cnst.anchor = 11;
         cnst.fill = 2;
         cnst.gridx = cnst.gridy = 0;
         cnst.weightx = 0.0;
         cnst.gridwidth = 1;
         cnst.ipady = 10;
         cnst.insets = new Insets(1, 5, 1, 5);
         ++cnst.gridy;
         contPane.add(viewerTitle, cnst);
         ++cnst.gridy;
         contPane.add(typeAndLegendBox, cnst);
         ++cnst.gridy;
         contPane.add(scrollpane, cnst);
         ++cnst.gridy;
         this.pack();
         Point loc = this.myParent.getLocation();
         loc.x += 300;
         if (this.myIsLocaleViewer) {
            loc.y += 20;
            loc.x += 20;
         }

         this.setLocation(loc);
         this.setVisible(true);
      }

      return success;
   }

   private int countValidMessages(Vector logMessagesV) {
      int count = 0;
      if (logMessagesV != null) {
         Enumeration e = logMessagesV.elements();

         while(e.hasMoreElements()) {
            Object elem = e.nextElement();
            if (elem instanceof BasicMessage) {
               BasicMessage bm = (BasicMessage)elem;
               if (!bm.isRetired()) {
                  ++count;
               }
            }
         }
      }

      return count;
   }

   private void setupLogData(Vector logMessagesV) {
      if (this.myParent instanceof MessageLocalizer) {
         this.myUsingTitles = this.myStatusLogTitles;
      } else {
         this.myUsingTitles = this.myLogTitles;
      }

      BasicLogMessage[] logMessages = new BasicLogMessage[logMessagesV.size()];
      logMessages = (BasicLogMessage[])((BasicLogMessage[])logMessagesV.toArray(logMessages));
      this.myDoingLogMessages = true;
      int numRows = logMessages.length;
      this.myData = new Object[numRows][this.myUsingTitles.length];

      for(int row = 0; row < numRows; ++row) {
         this.fillDataFromLogMessage(row, logMessages[row]);
      }

   }

   private void setupSimpleData(Vector simpleMessagesV) {
      if (this.myParent instanceof MessageLocalizer) {
         this.myUsingTitles = this.myStatusSimpleTitles;
      } else {
         this.myUsingTitles = this.mySimpleTitles;
      }

      BasicMessage[] simpleMessages = new BasicMessage[simpleMessagesV.size()];
      simpleMessages = (BasicMessage[])((BasicMessage[])simpleMessagesV.toArray(simpleMessages));
      this.myDoingSimpleMessages = true;
      int numRows = simpleMessages.length;
      this.myData = new Object[numRows][this.myUsingTitles.length];

      for(int row = 0; row < numRows; ++row) {
         this.fillDataFromSimpleMessage(row, simpleMessages[row]);
      }

   }

   private void fillDataFromLogMessage(int row, BasicLogMessage msg) {
      LogMessage logMsg;
      int column;
      if (this.myParent instanceof MessageLocalizer) {
         logMsg = ((MessageLocalizer)this.myParent).getMasterLogMessage(msg.getMessageId());
         if (logMsg != null && !logMsg.isRetired()) {
            for(column = 0; column < this.myUsingTitles.length; ++column) {
               switch (column) {
                  case 0:
                     this.myData[row][column] = this.myEmptyIcon;
                     break;
                  case 1:
                     this.myData[row][column] = msg.getMessageId();
                     break;
                  case 2:
                     if (msg.getComment() != null) {
                        this.myData[row][column] = msg.getComment();
                     } else {
                        this.myData[row][column] = "";
                     }
                     break;
                  case 3:
                     if (msg.getMessageBody() != null) {
                        this.myData[row][column] = msg.getMessageBody().getCdata();
                     } else {
                        this.myData[row][column] = "";
                     }
                     break;
                  case 4:
                     if (msg.getMessageDetail() != null) {
                        this.myData[row][column] = msg.getMessageDetail().getCdata();
                     } else {
                        this.myData[row][column] = "";
                     }
                     break;
                  case 5:
                     if (msg.getAction() != null) {
                        this.myData[row][column] = msg.getAction().getCdata();
                     } else {
                        this.myData[row][column] = "";
                     }
                     break;
                  case 6:
                     if (msg.getCause() != null) {
                        this.myData[row][column] = msg.getCause().getCdata();
                     } else {
                        this.myData[row][column] = "";
                     }
               }
            }
         }
      } else {
         logMsg = (LogMessage)msg;

         for(column = 0; column < this.myUsingTitles.length; ++column) {
            switch (column) {
               case 0:
                  this.myData[row][column] = logMsg.getMessageId();
                  break;
               case 1:
                  this.myData[row][column] = logMsg.getMethod();
                  break;
               case 2:
                  this.myData[row][column] = logMsg.getMethodType();
                  break;
               case 3:
                  if (logMsg.getComment() != null) {
                     this.myData[row][column] = logMsg.getComment();
                  } else {
                     this.myData[row][column] = "";
                  }
                  break;
               case 4:
                  this.myData[row][column] = logMsg.getSeverity();
                  break;
               case 5:
                  if (logMsg.getMessageBody() != null) {
                     this.myData[row][column] = logMsg.getMessageBody().getCdata();
                  } else {
                     this.myData[row][column] = "";
                  }
                  break;
               case 6:
                  if (logMsg.getMessageDetail() != null) {
                     this.myData[row][column] = logMsg.getMessageDetail().getCdata();
                  } else {
                     this.myData[row][column] = "";
                  }
                  break;
               case 7:
                  if (logMsg.getAction() != null) {
                     this.myData[row][column] = logMsg.getAction().getCdata();
                  } else {
                     this.myData[row][column] = "";
                  }
                  break;
               case 8:
                  if (logMsg.getCause() != null) {
                     this.myData[row][column] = logMsg.getCause().getCdata();
                  } else {
                     this.myData[row][column] = "";
                  }
                  break;
               case 9:
                  this.myData[row][column] = String.valueOf(msg.isRetired());
            }
         }
      }

   }

   private void fillDataFromSimpleMessage(int row, BasicMessage msg) {
      int column;
      if (this.myParent instanceof MessageLocalizer) {
         for(column = 0; column < this.myUsingTitles.length; ++column) {
            switch (column) {
               case 0:
                  this.myData[row][column] = this.myEmptyIcon;
                  break;
               case 1:
                  this.myData[row][column] = msg.getMessageId();
                  break;
               case 2:
                  if (msg.getComment() != null) {
                     this.myData[row][column] = msg.getComment();
                  } else {
                     this.myData[row][column] = "";
                  }
                  break;
               case 3:
                  if (msg.getMessageBody() != null) {
                     this.myData[row][column] = msg.getMessageBody().getCdata();
                  } else {
                     this.myData[row][column] = "";
                  }
            }
         }
      } else {
         for(column = 0; column < this.myUsingTitles.length; ++column) {
            switch (column) {
               case 0:
                  this.myData[row][column] = msg.getMessageId();
                  break;
               case 1:
                  if (msg.getComment() != null) {
                     this.myData[row][column] = msg.getComment();
                  } else {
                     this.myData[row][column] = "";
                  }
                  break;
               case 2:
                  if (((Message)msg).getMethod() != null) {
                     this.myData[row][column] = ((Message)msg).getMethod();
                  } else {
                     this.myData[row][column] = "";
                  }
                  break;
               case 3:
                  if (msg.getMessageBody() != null) {
                     this.myData[row][column] = msg.getMessageBody().getCdata();
                  } else {
                     this.myData[row][column] = "";
                  }
            }
         }
      }

   }

   public void changeLogMessage(BasicLogMessage logMsg, String savedId) {
      int row = this.findRow(savedId);
      if (row > -1) {
         this.fillDataFromLogMessage(row, logMsg);
         this.myMessageModel.fireTableRowsUpdated(row, row);
      }

   }

   private int findRow(String id) {
      if (this.myData == null) {
         return -1;
      } else {
         for(int row = 0; row < this.myData.length; ++row) {
            Object value = this.myData[row][this.myIdCol];
            if (value != null && value.equals(id)) {
               return row;
            }
         }

         return -1;
      }
   }

   public void changeSimpleMessage(BasicMessage msg, String savedId) {
      int row = this.findRow(savedId);
      if (row > -1) {
         this.fillDataFromSimpleMessage(row, msg);
         this.myMessageModel.fireTableRowsUpdated(row, row);
      }

   }

   public void addLogMessage(BasicLogMessage logMsg) {
      if (this.myTableView == null) {
         this.showFirstMessages();
      } else {
         int oldNumRows = this.myTableView.getRowCount();
         Object[][] newData = new Object[oldNumRows + 1][this.myUsingTitles.length];

         for(int i = 0; i < oldNumRows; ++i) {
            System.arraycopy(this.myData[i], 0, newData[i], 0, this.myUsingTitles.length);
         }

         this.myData = newData;
         this.fillDataFromLogMessage(oldNumRows, logMsg);
         this.myMessageModel.fireTableRowsInserted(oldNumRows, oldNumRows);
      }

   }

   public void addMessage(BasicMessage simpleMsg) {
      if (this.myTableView == null) {
         this.showFirstMessages();
      } else {
         int oldNumRows = this.myTableView.getRowCount();
         Object[][] newData = new Object[oldNumRows + 1][this.myUsingTitles.length];

         for(int i = 0; i < oldNumRows; ++i) {
            System.arraycopy(this.myData[i], 0, newData[i], 0, this.myUsingTitles.length);
         }

         this.myData = newData;
         this.fillDataFromSimpleMessage(oldNumRows, simpleMsg);
         this.myMessageModel.fireTableRowsInserted(oldNumRows, oldNumRows);
      }

   }

   public void showMessage(BasicMessage msg) {
      if (msg != null) {
         String id = msg.getMessageId();

         for(int row = 0; row < this.myMessageModel.getRowCount(); ++row) {
            if (id.equals(this.myMessageModel.getValueAt(row, this.myIdCol))) {
               Container parent;
               for(parent = this.myTableView.getParent(); !(parent instanceof MessageViewer) && !(parent instanceof JScrollPane); parent = parent.getParent()) {
               }

               if (parent instanceof JScrollPane) {
                  JScrollBar bar = ((JScrollPane)parent).getVerticalScrollBar();
                  if (bar != null) {
                     bar.setValue((bar.getMaximum() - bar.getMinimum()) / this.myMessageModel.getRowCount() * row);
                  }
               }

               this.myTableView.getSelectionModel().setSelectionInterval(row, row);
               break;
            }
         }
      }

   }

   public void setMessageStatus(String id, ImageIcon img) {
      int row = this.findRow(id);
      if (row > -1) {
         this.myData[row][this.myStatusCol] = img;
      }

   }
}
