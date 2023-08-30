/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv;
import com.sun.glass.events.KeyEvent;
import koneksi.koneksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle.Control;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hrayh
 */
public class formdatabarang extends javax.swing.JInternalFrame {

    /**
     * Creates new form formdatabarang
     */
    public formdatabarang() {
         initComponents();
         setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        kodedatabarang();
        kodesupplier();
        tampil_tb_seleksi();
    }
      private void kosong(){
          tfnamabarang.setText("");
    cbbrand.setSelectedIndex(0);
    cbkualitas.setSelectedIndex(0);
    cbjenisbarang.setSelectedIndex(0);
    cbgaransi.setSelectedIndex(0);
    tfjumlahbarang.setText("");
    tfjumlahbarang.setText("");
    tfharga.setText("");
      }
      
    public void kodedatabarang(){
        try {
            Connection con = new koneksi().koneksiDatabase();
            java.sql.Statement stat = con.createStatement();
                
                
                stat=con.createStatement();
                String sql="select * from databarang order by idbarang desc";

            ResultSet rs=stat.executeQuery(sql);
            if (rs.next()) {

                String nofak = rs.getString("idbarang").substring(2);

                String AN = "" + (Integer.parseInt(nofak) + 1);

                String Nol = "";
                
                if(AN.length()==1)
                {Nol = "00";}
                else if(AN.length()==2)
                {Nol = "0";}
                else if(AN.length()==3)
                {Nol = "";}
               tfidbarang.setText("BR" + Nol + AN);

            } else {
                tfidbarang.setText("BR001");
            }}catch(Exception e){

           JOptionPane.showMessageDialog(null, e);

           }
    }
    
    
    
    public void kodesupplier(){
        try {
            Connection con = new koneksi().koneksiDatabase();
            java.sql.Statement stat = con.createStatement();
                
                
                stat=con.createStatement();
                String sql="select * from supplier order by idsupplier desc";

            ResultSet rs=stat.executeQuery(sql);
            if (rs.next()) {

                String nofak = rs.getString("idsupplier").substring(2);

                String AN = "" + (Integer.parseInt(nofak) + 1);

                String Nol = "";
                
                if(AN.length()==1)
                {Nol = "0";}
              
                else if(AN.length()==2)
                {Nol = "";}
               tfidsupplier.setText("SP" + Nol );

            } else {
                tfidsupplier.setText("SP");
            }}catch(Exception e){

           JOptionPane.showMessageDialog(null, e);

           }
    }

private void simpan(){
    String query;
        query = "INSERT INTO databarang(idbarang,idsupplier,namabarang,brand,kualitas,jenisbarang,garansi,jumlahbarang,harga)"
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement;
        Connection connection;
        try {
        connection = koneksi.koneksiDatabase();
        statement = connection.prepareStatement(query);
        statement.setString(1, tfidbarang.getText());
        statement.setString(2, tfidsupplier.getText());
        statement.setString(3, tfnamabarang.getText());
        statement.setString(4, cbbrand.getSelectedItem().toString());
        statement.setString(5, cbkualitas.getSelectedItem().toString());
        statement.setString(6, cbjenisbarang.getSelectedItem().toString());
        statement.setString(7, cbgaransi.getSelectedItem().toString());
        statement.setString(8, tfjumlahbarang.getText());
        statement.setString(9, tfharga.getText());
        
        int hasil = statement.executeUpdate();
        if(hasil==1){
            JOptionPane.showMessageDialog(this,"DATA BARANG TERSIMPAN");
        } }catch (SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
            }

    }

   public void cariidsupplier(){
        String kode=tfidsupplier.getText();
        String query="select * from supplier where idsupplier=?";
        PreparedStatement statement;
        Connection connection;
    
    try {
        connection = koneksi.koneksiDatabase();
        statement = connection.prepareStatement(query);
        statement.setString(1,kode);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            tfnamabarang.setText(rs.getString("namabarang"));
            
            cbjenisbarang.setSelectedItem(rs.getString("jenisbarang"));
            tfjumlahbarang.setText(rs.getString("jumlahbarang"));
        }
        }catch(Exception e){
            
        }
    }
   
    private DefaultTableModel tabmode;
     public void tampil_tb_seleksi(){
        Object []baris = {"NO","ID Barang","ID Supplier","Nama Barang","Brand","kualitas","Jenis Barang","Garansi","Jumlah barang","Harga"};
        tabmode = new DefaultTableModel (null, baris);
        tbdatabarang.setModel(tabmode);
        Connection con = new koneksi().koneksiDatabase();
        try{
          
            java.sql.Statement stat = con.createStatement();
            //String sql ="SELECT databarangkeluar.*,databarangmasuk.* FROM databarangkeluar INNER JOIN databarangmasuk ON databarangkeluar.kodebarang=databarangmasuk.kodebarang ";
            String sql ="SELECT * FROM databarang";
            java.sql.ResultSet hasil = stat.executeQuery(sql);
            int i = 1;
            while(hasil.next()){
                String a = Integer.toString(i);
                String b = hasil.getString("idbarang"); 
                String c = hasil.getString("idsupplier");
                String d = hasil.getString("namabarang");
                String e = hasil.getString("brand");
                String f = hasil.getString("kualitas");
                String g = hasil.getString("jenisbarang");
                String h = hasil.getString("garansi");
                String j = hasil.getString("jumlahbarang");
                String k = hasil.getString("harga");
                String [] data = {a,b,c,d,e,f,g,h,j,k};
                tabmode.addRow(data);
                 i++;
                        }    
            }catch (SQLException e){
            JOptionPane.showMessageDialog(null ,"menampilkan data gagal ","INFORMASI ",
                    JOptionPane.INFORMATION_MESSAGE);
            }
         }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfnamabarang = new javax.swing.JTextField();
        tfidbarang = new javax.swing.JTextField();
        tfidsupplier = new javax.swing.JTextField();
        tfjumlahbarang = new javax.swing.JTextField();
        tfharga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Simpan = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cbjenisbarang = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbgaransi = new javax.swing.JComboBox<>();
        cbbrand = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbkualitas = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbdatabarang = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(14, 41, 84));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID Barang");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID Supplier");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama Barang");

        tfidsupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfidsupplierKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Brand ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Jumlah Barang");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Harga");

        Simpan.setText("Simpan");
        Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimpanActionPerformed(evt);
            }
        });

        jButton2.setText("Keluar");

        cbjenisbarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Processor", "Ram", "Motherboard", "Vga", "SSD" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jenis barang");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Garansi");

        cbgaransi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Bulan", "6 Bulan", "12 Bulan" }));

        cbbrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brand Sangat Terkenal", "Brand Terkenal", "Brand Tidak Terkenal" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Kualitas");

        cbkualitas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kualitas Sangat Bagus", "Kualitas Bagus", "Kualitas  standar", "Kualitas dibawah standar" }));

        tbdatabarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbdatabarang);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Tabel Data Barang");

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Data Barang");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(288, 288, 288)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbkualitas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfidbarang, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfnamabarang, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfidsupplier, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbrand, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel7))
                                            .addGap(47, 47, 47))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(56, 56, 56)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(61, 61, 61)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfharga)
                                    .addComponent(tfjumlahbarang)
                                    .addComponent(cbgaransi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbjenisbarang, 0, 116, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(287, 287, 287)
                                .addComponent(Simpan)
                                .addGap(72, 72, 72)
                                .addComponent(jButton2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(324, 324, 324)
                                .addComponent(jLabel11)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(tfidsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tfnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbbrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tfidbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbjenisbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbgaransi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfjumlahbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(cbkualitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Simpan)
                    .addComponent(jButton2))
                .addGap(10, 10, 10)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SimpanActionPerformed
simpan();
kodedatabarang();
kodesupplier();
kosong();
tampil_tb_seleksi();// TODO add your handling code here:
    }//GEN-LAST:event_SimpanActionPerformed

    private void tfidsupplierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfidsupplierKeyReleased
  cariidsupplier();      // TODO add your handling code here:
    }//GEN-LAST:event_tfidsupplierKeyReleased

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(formdatabarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formdatabarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formdatabarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formdatabarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formdatabarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Simpan;
    private javax.swing.JComboBox<String> cbbrand;
    private javax.swing.JComboBox<String> cbgaransi;
    private javax.swing.JComboBox<String> cbjenisbarang;
    private javax.swing.JComboBox<String> cbkualitas;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbdatabarang;
    private javax.swing.JTextField tfharga;
    private javax.swing.JTextField tfidbarang;
    private javax.swing.JTextField tfidsupplier;
    private javax.swing.JTextField tfjumlahbarang;
    private javax.swing.JTextField tfnamabarang;
    // End of variables declaration//GEN-END:variables
}
