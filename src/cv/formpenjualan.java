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
public class formpenjualan extends javax.swing.JInternalFrame {

    /**
     * Creates new form formpenjualan
     */
    public formpenjualan() {
        initComponents();
         setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        caribarang();
 
        kodetransaksi();
     
       
        tampil_tb_penjualan();
    }
 public void kodetransaksi(){
        try {
            Connection con = new koneksi().koneksiDatabase();
            java.sql.Statement stat = con.createStatement();
                
                
                stat=con.createStatement();
                String sql="select * from penjualan order by idtransaksi desc";

            ResultSet rs=stat.executeQuery(sql);
            if (rs.next()) {

                String nofak = rs.getString("idtransaksi").substring(2);

                String AN = "" + (Integer.parseInt(nofak) + 1);

                String Nol = "";
                
                if(AN.length()==1)
                {Nol = "00";}
                else if(AN.length()==2)
                {Nol = "0";}
                else if(AN.length()==3)
                {Nol = "";}
               tfidtransaksi.setText("PO" + Nol + AN);

            } else {
                tfidtransaksi.setText("PO001");
            }}catch(Exception e){

           JOptionPane.showMessageDialog(null, e);

           }
    }
 private void refresh(){
        DefaultTableModel tabelModel;
        tabelModel = (DefaultTableModel)tbpenjualan.getModel();
        int jumlahBaris = tbpenjualan.getRowCount();
        for (int i = 0; 1 < jumlahBaris; i++){
    }
        tampil_tb_penjualan();
    }
 
  public void caribarang(){
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
                {Nol = "0";}
              
                else if(AN.length()==2)
                {Nol = "";}
               tfkodebarang.setText("BR" + Nol );

            } else {
                tfkodebarang.setText("BR001");
            }}catch(Exception e){

           JOptionPane.showMessageDialog(null, e);

           }
    }

  public void simpan(){
   String query;
        query = "INSERT INTO penjualan(idtransaksi,tanggal,namapembeli,notel,kodebarang,namabarang,jenisbarang,jumlahbarang,garansi,hargasatuan,totalharga,jumlahbayar,sisabayar)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement;
        Connection connection;
        try {
        connection = koneksi.koneksiDatabase();
        statement = connection.prepareStatement(query);
        statement.setString(1, tfidtransaksi.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(tanggal.getDate());
        statement.setString(2, date);
        statement.setString(3, tfnamapembeli.getText());
        statement.setString(4, tfnotel.getText());
        statement.setString(5, tfkodebarang.getText());
        statement.setString(6, tfnamabarang.getText());
        statement.setString(7, cbjenisbarang.getSelectedItem().toString());
        statement.setString(8, tfjumlahbarang.getText());
        statement.setString(9, cbgaransi.getSelectedItem().toString());
        statement.setString(10, tfhargasatuan.getText());
        statement.setString(11, tfhargatotal.getText());
        statement.setString(12, tfjumlahbayar.getText());
        statement.setString(13, tfsisabayar.getText());
        int hasil = statement.executeUpdate();
        if(hasil==1){
            kurangistok(tfjumlahbarang.getText(),tfkodebarang.getText());
          
            JOptionPane.showMessageDialog(this,"DATA PEMESANAN TERSIMPAN");
        }
        
            }catch (SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
            }  
    }
  private void kurangistok(String jumlah,String id){
        
         String query ;
        query = "UPDATE databarang SET jumlahbarang=jumlahbarang-? WHERE  idbarang=?";
    
    PreparedStatement statement;
        Connection connection;
        try {
        connection = koneksi.koneksiDatabase();
        statement = connection.prepareStatement(query);
   
        statement.setString(1, jumlah);
        statement.setString(2, id);
       
        int hasil = statement.executeUpdate();
        if(hasil==1){
             tampil_tb_penjualan();
        }
        
            }catch (SQLException e){
           
            }
        
    }
    public void hitung(){
        int jumlah =Integer.parseInt(tfjumlahbarang.getText());
        int harga=Integer.parseInt(tfhargasatuan.getText());
        int total=jumlah*harga;
        tfhargatotal.setText(String.valueOf(total));         
    }
         
        public void hitungkembali(){
        int bayar =Integer.parseInt(tfjumlahbayar.getText());  
        int totalharga=Integer.parseInt(tfhargatotal.getText());
        int sisa=Integer.parseInt(tfjumlahbayar.getText())- Integer.parseInt(tfhargatotal.getText());;  
       tfsisabayar.setText(String.valueOf(sisa));
    }
    
     public void cariid(){
            String id=tfkodebarang.getText();
        String query="select * from databarang where idbarang=? ";
         PreparedStatement statement;
    Connection connection;
    
    try {
        connection = koneksi.koneksiDatabase();
        statement = connection.prepareStatement(query);
        statement.setString(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
               tfnamabarang.setText(rs.getString("namabarang"));
               cbjenisbarang.setSelectedItem(rs.getString("jenisbarang"));
                cbgaransi.setSelectedItem(rs.getString("garansi"));
               tfhargasatuan.setText(rs.getString("harga"));
             
        }
        }catch(Exception e){
            
        }
    }
     
      private void kosong(){
    
    tanggal.setCalendar(null); 
    tfnamapembeli.setText("");
    tfnotel.setText("");
    tfkodebarang.setText("");
    tfnamabarang.setText("");
    cbjenisbarang.setSelectedIndex(0);
    tfjumlahbarang.setText("");
    cbgaransi.setSelectedIndex(0);
    tfhargasatuan.setText("");
    tfhargatotal.setText("");
    tfjumlahbayar.setText("");   
    tfsisabayar.setText("");

    }
      
        private DefaultTableModel tabmode;
     public void tampil_tb_penjualan(){
        Object []baris = {"ID Transaksi","Tanggal","Nama Pembeli","Nomor telepon","Kode Barang","Nama Barang","Jenis barang","Jumlah Barang","garansi","Harga satuan","Total Harga ","Jumlah Bayar","Sisa Bayar"};
        tabmode = new DefaultTableModel (null, baris);
        tbpenjualan.setModel(tabmode);
        Connection con = new koneksi().koneksiDatabase();
        try{
            java.sql.Statement stat = con.createStatement();
            
            String sql ="SELECT * from penjualan ";
            java.sql.ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String a = hasil.getString("idtransaksi"); 
                String b = hasil.getString("tanggal");
                String c = hasil.getString("namapembeli");
                String d = hasil.getString("notel");
                String e= hasil.getString("kodebarang");
                String f = hasil.getString("namabarang");
                String g = hasil.getString("jenisbarang");
                String h = hasil.getString("jumlahbarang");
                String i= hasil.getString("garansi");
                String j= hasil.getString("hargasatuan");
                String k= hasil.getString("totalharga");
                String l= hasil.getString("jumlahbayar");
                String m = hasil.getString("sisabayar");
               
                String [] data = {a,b,c,d,e,f,g,h,i,j,k,l,m};
                tabmode.addRow(data);
                        }    
            }catch (SQLException e){
            JOptionPane.showMessageDialog(null ,"menampilkan data gagal ","INFORMASI ",
                    JOptionPane.INFORMATION_MESSAGE);
            }
         }
  /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfidtransaksi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfnamapembeli = new javax.swing.JTextField();
        tanggal = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        tfnamabarang = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfnotel = new javax.swing.JTextField();
        cbjenisbarang = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        tfjumlahbarang = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfhargasatuan = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfhargatotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tfjumlahbayar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfsisabayar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbgaransi = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbpenjualan = new javax.swing.JTable();
        tfkodebarang = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(14, 41, 84));
        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(14, 41, 84));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Transaksi");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Pembeli");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tanggal");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("No telfon");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Kode Barang");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nama Barang");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jenis Barang");

        cbjenisbarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Processor", "Ram", "Motherboard", "Vga", "SSD" }));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Jumlah Barang");

        tfjumlahbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfjumlahbarangKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Harga Satuan");

        tfhargasatuan.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Total Harga");

        tfhargatotal.setEditable(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Jumlah Bayar");

        tfjumlahbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfjumlahbayarKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Sisa Bayar");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Garansi");

        cbgaransi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Bulan", "6 Bulan", "12 Bulan" }));

        jButton1.setText("simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tbpenjualan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbpenjualan);

        tfkodebarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfkodebarangKeyReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Penjualan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(407, 407, 407))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfkodebarang, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfidtransaksi, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfnamapembeli, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tanggal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfnotel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbjenisbarang, javax.swing.GroupLayout.Alignment.LEADING, 0, 98, Short.MAX_VALUE)
                                    .addComponent(tfnamabarang, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(53, 53, 53)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(58, 58, 58)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfjumlahbarang)
                                    .addComponent(tfsisabayar)
                                    .addComponent(tfhargatotal, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tfhargasatuan, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tfjumlahbayar)
                                    .addComponent(cbgaransi, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(210, 210, 210))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfjumlahbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(cbgaransi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfidtransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(tfnamapembeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfnotel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(tfkodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(tfnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(cbjenisbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(tfsisabayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tfhargasatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(tfhargatotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfjumlahbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(49, 49, 49)))))
                .addGap(54, 54, 54)
                .addComponent(jButton1)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
simpan();
caribarang();
tampil_tb_penjualan();
kosong();// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tfjumlahbarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfjumlahbarangKeyReleased
hitung();        // TODO add your handling code here:
    }//GEN-LAST:event_tfjumlahbarangKeyReleased

    private void tfjumlahbayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfjumlahbayarKeyReleased
hitungkembali();        // TODO add your handling code here:
    }//GEN-LAST:event_tfjumlahbayarKeyReleased

    private void tfkodebarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfkodebarangKeyReleased
cariid();        // TODO add your handling code here:
    }//GEN-LAST:event_tfkodebarangKeyReleased

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
            java.util.logging.Logger.getLogger(formpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formpenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbgaransi;
    private javax.swing.JComboBox<String> cbjenisbarang;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private com.toedter.calendar.JDateChooser tanggal;
    private javax.swing.JTable tbpenjualan;
    private javax.swing.JTextField tfhargasatuan;
    private javax.swing.JTextField tfhargatotal;
    private javax.swing.JTextField tfidtransaksi;
    private javax.swing.JTextField tfjumlahbarang;
    private javax.swing.JTextField tfjumlahbayar;
    private javax.swing.JTextField tfkodebarang;
    private javax.swing.JTextField tfnamabarang;
    private javax.swing.JTextField tfnamapembeli;
    private javax.swing.JTextField tfnotel;
    private javax.swing.JTextField tfsisabayar;
    // End of variables declaration//GEN-END:variables
}
