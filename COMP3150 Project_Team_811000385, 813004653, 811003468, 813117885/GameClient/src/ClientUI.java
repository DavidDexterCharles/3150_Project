
import java.awt.Color;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ClientUI extends javax.swing.JFrame {

    boolean clicked=false;
    boolean IpStat=false;
    
    String snd="";
    String dat="";
    
    public ClientUI() {
        initComponents();
        setIcon();
        
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        SubBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        MainDis = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        scoreVal = new javax.swing.JLabel();
        usrPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        usrName = new javax.swing.JLabel();
        usrPic = new javax.swing.JLabel();
        logoIn = new javax.swing.JLabel();
        Status = new javax.swing.JLabel();
        statPic = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        ipBox = new javax.swing.JTextField();
        conn = new javax.swing.JButton();
        disconn = new javax.swing.JButton();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PROJECT: CLIENT");
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(33, 33, 33));
        setPreferredSize(new java.awt.Dimension(1024, 720));
        setResizable(false);
        setSize(new java.awt.Dimension(650, 450));
        getContentPane().setLayout(null);

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(370, 610, 520, 40);

        SubBtn.setText("OK");
        SubBtn.setToolTipText("");
        SubBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubBtnActionPerformed(evt);
            }
        });
        getContentPane().add(SubBtn);
        SubBtn.setBounds(910, 610, 80, 40);

        MainDis.setBackground(new java.awt.Color(21, 21, 21));
        MainDis.setColumns(20);
        MainDis.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        MainDis.setForeground(new java.awt.Color(255, 255, 255));
        MainDis.setLineWrap(true);
        MainDis.setRows(5);
        MainDis.setBorder(javax.swing.BorderFactory.createMatteBorder(10, 10, 10, 10, new javax.swing.ImageIcon(getClass().getResource("/tile1.png")))); // NOI18N
        MainDis.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(MainDis);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(370, 70, 620, 530);
        getContentPane().add(jLabel3);
        jLabel3.setBounds(870, 0, 0, 0);

        scoreLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/score.png"))); // NOI18N
        scoreLabel.setText("jLabel2");
        getContentPane().add(scoreLabel);
        scoreLabel.setBounds(830, 30, 100, 30);

        scoreVal.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        scoreVal.setForeground(new java.awt.Color(226, 140, 10));
        scoreVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreVal.setText("0");
        getContentPane().add(scoreVal);
        scoreVal.setBounds(930, 30, 50, 30);

        usrPanel.setBackground(new java.awt.Color(21, 21, 21));
        usrPanel.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(null);

        usrName.setFont(new java.awt.Font("OCR A Extended", 0, 20)); // NOI18N
        usrName.setForeground(new java.awt.Color(204, 204, 204));
        usrName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usrName.setText("Placeholder");
        usrName.setToolTipText("");
        jPanel1.add(usrName);
        usrName.setBounds(0, 10, 240, 40);

        usrPanel.add(jPanel1);
        jPanel1.setBounds(0, 50, 240, 60);

        usrPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dispic.png"))); // NOI18N
        usrPanel.add(usrPic);
        usrPic.setBounds(240, 50, 90, 60);

        logoIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.png"))); // NOI18N
        logoIn.setText("jLabel2");
        usrPanel.add(logoIn);
        logoIn.setBounds(90, 340, 150, 130);

        Status.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Status.setForeground(new java.awt.Color(255, 255, 255));
        Status.setText("Offline");
        usrPanel.add(Status);
        Status.setBounds(260, 110, 50, 30);

        statPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/offline.png"))); // NOI18N
        usrPanel.add(statPic);
        statPic.setBounds(310, 120, 15, 15);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hostip.png"))); // NOI18N
        usrPanel.add(jLabel1);
        jLabel1.setBounds(120, 510, 100, 30);

        ipBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ipBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ipBoxKeyPressed(evt);
            }
        });
        usrPanel.add(ipBox);
        ipBox.setBounds(90, 540, 170, 40);

        conn.setText("Connect");
        conn.setMaximumSize(new java.awt.Dimension(85, 23));
        conn.setMinimumSize(new java.awt.Dimension(85, 23));
        conn.setPreferredSize(new java.awt.Dimension(85, 23));
        conn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connActionPerformed(evt);
            }
        });
        usrPanel.add(conn);
        conn.setBounds(50, 590, 122, 55);

        disconn.setText("Disconnect");
        disconn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnActionPerformed(evt);
            }
        });
        usrPanel.add(disconn);
        disconn.setBounds(170, 590, 115, 55);

        getContentPane().add(usrPanel);
        usrPanel.setBounds(0, 0, 330, 730);

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bg.png"))); // NOI18N
        bg.setText("jLabel4");
        getContentPane().add(bg);
        bg.setBounds(0, 0, 1020, 730);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void SubBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubBtnActionPerformed
        // TODO add your handling code here:
        if(this.jTextField1.getText().equals(""))return;
        else 
        {
            snd=this.jTextField1.getText();
            this.jTextField1.setText("");
            clicked=true;
        }
    }//GEN-LAST:event_SubBtnActionPerformed

    private void disconnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_disconnActionPerformed

    private void connActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connActionPerformed
        // TODO add your handling code here:
        if(this.ipBox.getText().equals(""))return;
        else 
        {
            dat=""+this.ipBox.getText();
            IpStat=true;
            this.Status.setText("Online");
            this.statPic.setIcon(new ImageIcon(getClass().getResource("online.png")));
            conn.setEnabled(false);
        }
    }//GEN-LAST:event_connActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
           if(this.jTextField1.getText().equals(""))return;
                else 
                {
                    snd=this.jTextField1.getText();
                    this.jTextField1.setText("");
                    clicked=true;
                }
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void ipBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ipBoxKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            if(this.ipBox.getText().equals(""))return;
            else 
            {
                dat=""+this.ipBox.getText();
                IpStat=true;
                this.Status.setText("Online");
                this.statPic.setIcon(new ImageIcon(getClass().getResource("online.png")));
                conn.setEnabled(false);
            }
        }
        
    }//GEN-LAST:event_ipBoxKeyPressed
    
   
    
 
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextField jTextField1;
    private javax.swing.JLabel logoIn;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel scoreVal;
    private javax.swing.JLabel statPic;
    private javax.swing.JLabel usrName;
    private javax.swing.JPanel usrPanel;
    private javax.swing.JLabel usrPic;
    // End of variables declaration//GEN-END:variables

    public void setDisplay(String val)
    {
        this.MainDis.append("  "+val+"\n");
    }
    
    public void setScore(String val)
    {
        this.scoreVal.setText(val);
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
    
    public void setName(String val)
    {
        this.usrName.setText(val);
    }
    
    public String getInfo()
    {
        while(!IpStat)
        {
             try {
                Thread.sleep(200);
             } 
            catch(InterruptedException e) {
             }
        }
        IpStat=false;
        return dat;
    }

    private void setIcon()
    {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
    }
    
}
