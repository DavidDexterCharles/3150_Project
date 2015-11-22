
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class ClientUI extends javax.swing.JFrame {

    boolean clicked=false;
    boolean IpStat=false;
    boolean PortStat=false;
    String snd="";
    String dat="";
    
    public ClientUI() {
        initComponents();
        UIManager.put("TextArea.margin", new Insets(10,10,10,10));
        this.getContentPane().setBackground(new Color(33,33,33));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        SubBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        MainDis = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        ipBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        portBox = new javax.swing.JTextField();
        conn = new javax.swing.JButton();
        disconn = new javax.swing.JButton();
        Status = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PROJECT: CLIENT");
        setBackground(new java.awt.Color(33, 33, 33));
        setPreferredSize(new java.awt.Dimension(1024, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(650, 450));
        getContentPane().setLayout(null);
        getContentPane().add(jTextField1);
        jTextField1.setBounds(390, 630, 321, 25);

        SubBtn.setText("OK");
        SubBtn.setToolTipText("");
        SubBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubBtnActionPerformed(evt);
            }
        });
        getContentPane().add(SubBtn);
        SubBtn.setBounds(720, 630, 68, 23);

        MainDis.setEditable(false);
        MainDis.setColumns(20);
        MainDis.setLineWrap(true);
        MainDis.setRows(5);
        MainDis.setBorder(javax.swing.BorderFactory.createMatteBorder(10, 10, 10, 10, new javax.swing.ImageIcon(getClass().getResource("/woodtile.png")))); // NOI18N
        jScrollPane1.setViewportView(MainDis);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(270, 20, 620, 590);

        jLabel1.setText("Host IP:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 400, 60, 14);
        getContentPane().add(ipBox);
        ipBox.setBounds(60, 400, 131, 20);

        jLabel2.setText("Port:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 440, 50, 14);
        getContentPane().add(portBox);
        portBox.setBounds(60, 440, 131, 20);

        conn.setText("Connect");
        conn.setMaximumSize(new java.awt.Dimension(85, 23));
        conn.setMinimumSize(new java.awt.Dimension(85, 23));
        conn.setPreferredSize(new java.awt.Dimension(85, 23));
        conn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connActionPerformed(evt);
            }
        });
        getContentPane().add(conn);
        conn.setBounds(10, 490, 122, 55);

        disconn.setText("Disconnect");
        disconn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnActionPerformed(evt);
            }
        });
        getContentPane().add(disconn);
        disconn.setBounds(140, 490, 115, 55);

        Status.setText("Offline");
        getContentPane().add(Status);
        Status.setBounds(220, 70, 50, 14);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.png"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(870, 0, 150, 150);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(0, 0, 270, 380);

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg.png"))); // NOI18N
        bg.setText("jLabel4");
        getContentPane().add(bg);
        bg.setBounds(270, 0, 1020, 730);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void SubBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubBtnActionPerformed
        // TODO add your handling code here:
        if(this.jTextField1.getText().equals(""))return;
        else 
        {
            snd=this.jTextField1.getText();
            clicked=true;
        }
    }//GEN-LAST:event_SubBtnActionPerformed

    private void disconnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_disconnActionPerformed

    private void connActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connActionPerformed
        // TODO add your handling code here:
        if(this.ipBox.getText().equals("")||this.portBox.getText().equals(""))return;
        else 
        {
            dat=""+this.ipBox.getText()+" "+this.portBox.getText();
            IpStat=true;
            PortStat=true;
            this.Status.setText("Online");
        }
    }//GEN-LAST:event_connActionPerformed

 
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try
                {
                 Thread.sleep(10000);
                }
                catch(Exception e)
                {
                    
                }
                new ClientUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea MainDis;
    private javax.swing.JLabel Status;
    public javax.swing.JButton SubBtn;
    private javax.swing.JLabel bg;
    private javax.swing.JButton conn;
    private javax.swing.JButton disconn;
    private javax.swing.JTextField ipBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextField jTextField1;
    private javax.swing.JTextField portBox;
    // End of variables declaration//GEN-END:variables

    public void setDisplay(String val)
    {
        this.MainDis.append("  "+val+"\n");
    }
    
    public String getInput()
    {
       
        while(!clicked)
        {
            try {
                Thread.sleep(200);
             } 
            catch(InterruptedException e) {
             }
        }
        clicked=false;
        return snd;
    }
    
    public String getInfo()
    {
        while(!IpStat&&!PortStat)
        {
             try {
                Thread.sleep(200);
             } 
            catch(InterruptedException e) {
             }
        }
        IpStat=false;
        PortStat=false;
        return dat;
    }
    
}
