
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
public class subkriteria extends javax.swing.JInternalFrame {
 private Connection conn =koneksi.koneksiDatabase();
    private DefaultTableModel tabmode;
    /**
     * Creates new form subkriteria
     */
    public subkriteria() {
        initComponents();
     
         setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        updateDataTabel();
    }
protected void kosong(){
        cbSubPendidikan1.setSelectedIndex(0);
        cbSubPendidikan2.setSelectedIndex(0);
        cbSubPendidikan3.setSelectedIndex(0);
        cbSubPendidikan4.setSelectedIndex(0);
        cbSubWawancara1.setSelectedIndex(0);
        cbSubWawancara2.setSelectedIndex(0);
        cbSubWawancara3.setSelectedIndex(0);
        cbSubWaktu1.setSelectedIndex(0);
        cbSubWaktu2.setSelectedIndex(0);
        cbSubWaktu3.setSelectedIndex(0);
        cbSubSikap1.setSelectedIndex(0);
        cbSubSikap2.setSelectedIndex(0);
        cbSubSikap3.setSelectedIndex(0);
        cbSubSikap4.setSelectedIndex(0);
    }
    
    protected void updateDataTabel(){
        Object[] Baris = {
            "Kode Kriteria",
            "Nama Kriteria",
            "Nama SubKriteria",
            "Kepentingan SubKriteria"
        };
        tabmode = new DefaultTableModel(null, Baris);
        tabelSubKriteria.setModel(tabmode);
        String sql = "SELECT * FROM sub_kriteria ORDER BY kd_kriteria, no_sub";
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String a = hasil.getString("kd_kriteria");
                String b = hasil.getString("nama_kriteria");
                String c = hasil.getString("nama_sub_kriteria");
                String d = hasil.getString("prioritas_kepentingan");
                
                String[] data={a, b, c, d};
                tabmode.addRow(data);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    protected void getDataTabel(){
        String sql = "SELECT nama_sub_kriteria FROM sub_kriteria ORDER BY kd_kriteria, no_sub";
        int n = 1;
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String a = hasil.getString("nama_sub_kriteria");
                if(n==1){
                    cbSubPendidikan1.setSelectedItem(a);
                }else if(n==2){
                    cbSubPendidikan2.setSelectedItem(a);
                }else if(n==3){
                    cbSubPendidikan3.setSelectedItem(a);
                }else if(n==4){
                    cbSubPendidikan4.setSelectedItem(a);
                }else if(n==5){
                    cbSubWawancara1.setSelectedItem(a);
                }else if(n==6){
                    cbSubWawancara2.setSelectedItem(a);
                }else if(n==7){
                    cbSubWawancara3.setSelectedItem(a);
                }else if(n==8){
                    cbSubWaktu1.setSelectedItem(a);
                }else if(n==9){
                    cbSubWaktu2.setSelectedItem(a);
                }else if(n==10){
                    cbSubWaktu3.setSelectedItem(a);
                }else if(n==11){
                    cbSubSikap1.setSelectedItem(a);
                }else if(n==12){
                    cbSubSikap2.setSelectedItem(a);
                }else if(n==13){
                    cbSubSikap3.setSelectedItem(a);
                }else if(n==14){
                    cbSubSikap4.setSelectedItem(a);
                } 
                n++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    //masukan data subkriteria
    protected void insertDataSubKriteria(){
        try{
        int n=1, nPendidikan=1, nWawancara=1, nWaktu=1, nSikap=1, i=1;
            do{
                String kepentingan;
                String sql = "INSERT INTO sub_kriteria VALUES (?,?,?,?,?)";
                PreparedStatement stat = conn.prepareStatement(sql);
                java.sql.Statement statCek = conn.createStatement();
                String sqlSub = "SELECT kd_kriteria, nama_kriteria FROM kriteria";
                String sqlharga = "SELECT kd_kriteria, nama_kriteria FROM kriteria WHERE nama_kriteria='Harga Sparepart'";
                String sqlbrand = "SELECT kd_kriteria, nama_kriteria FROM kriteria WHERE nama_kriteria='Brand'";
                String sqlgaransi = "SELECT kd_kriteria, nama_kriteria FROM kriteria WHERE nama_kriteria='Garansi Sparepart'";
                String sqlkualitas = "SELECT kd_kriteria, nama_kriteria FROM kriteria WHERE nama_kriteria='Kualitas Sparepart'";
                ResultSet hasil = statCek.executeQuery(sqlSub);
                if(n==1){
                    hasil = statCek.executeQuery(sqlharga);
                    stat.setString(1, Integer.toString(i));
                    i++;
                    while(hasil.next()){
                        String a = hasil.getString("kd_kriteria");
                        String b = hasil.getString("nama_kriteria");
                        stat.setString(2, a);
                        stat.setString(3, b);
                    }
                    if(nPendidikan == 1){
                        stat.setString(4, cbSubPendidikan1.getSelectedItem().toString());
                        kepentingan="Sangat Penting ke-1";
                    }else if(nPendidikan == 2){
                        stat.setString(4, cbSubPendidikan2.getSelectedItem().toString());
                        kepentingan="Penting ke-2";
                    }else if(nPendidikan == 3){
                        stat.setString(4, cbSubPendidikan3.getSelectedItem().toString());
                        kepentingan="Cukup Penting ke-3";
                    }else{
                        stat.setString(4, cbSubPendidikan4.getSelectedItem().toString());
                        kepentingan="Biasa ke-4";
                        n++;
                    }
                    stat.setString(5, kepentingan);
                    stat.executeUpdate();
                    nPendidikan++;
                }else if(n==2){
                    hasil = statCek.executeQuery(sqlbrand);
                    stat.setString(1, Integer.toString(i));
                    i++;
                    while(hasil.next()){    
                        String a = hasil.getString("kd_kriteria");
                        String b = hasil.getString("nama_kriteria");
                        stat.setString(2, a);
                        stat.setString(3, b);
                    }
                    if(nWawancara == 1){
                        stat.setString(4, cbSubWawancara1.getSelectedItem().toString());
                        kepentingan="Sangat Penting ke-1";
                    }else if(nWawancara == 2){
                        stat.setString(4, cbSubWawancara2.getSelectedItem().toString());
                        kepentingan="Cukup Penting ke-2";
                    }else{
                        stat.setString(4, cbSubWawancara3.getSelectedItem().toString());
                        kepentingan="Biasa ke-3";
                        n++;
                    }
                    stat.setString(5, kepentingan);
                    stat.executeUpdate();
                    nWawancara++;
                }else if(n==3){
                    hasil = statCek.executeQuery(sqlgaransi);
                    stat.setString(1, Integer.toString(i));
                    i++;
                    while(hasil.next()){    
                        String a = hasil.getString("kd_kriteria");
                        String b = hasil.getString("nama_kriteria");
                        stat.setString(2, a);
                        stat.setString(3, b);
                    }
                    if(nWaktu == 1){
                        stat.setString(4, cbSubWaktu1.getSelectedItem().toString());
                        kepentingan="Sangat Penting ke-1";
                    }else if(nWaktu == 2){
                        stat.setString(4, cbSubWaktu2.getSelectedItem().toString());
                        kepentingan="Cukup Penting ke-2";
                    }else{
                        stat.setString(4, cbSubWaktu3.getSelectedItem().toString());
                        kepentingan="Biasa ke-3";
                        n++;
                    }
                    stat.setString(5, kepentingan);
                    stat.executeUpdate();
                    nWaktu++;
                }else{    
                    hasil = statCek.executeQuery(sqlkualitas);
                    stat.setString(1, Integer.toString(i));
                    i++;
                    while(hasil.next()){
                        String a = hasil.getString("kd_kriteria");
                        String b = hasil.getString("nama_kriteria");
                        stat.setString(2, a);
                        stat.setString(3, b);
                    }
                    if(nSikap == 1){
                        stat.setString(4, cbSubSikap1.getSelectedItem().toString());
                        kepentingan="Sangat Penting ke-1";
                    }else if(nSikap == 2){
                        stat.setString(4, cbSubSikap2.getSelectedItem().toString());
                        kepentingan="Penting ke-2";
                    }else if(nSikap == 3){
                        stat.setString(4, cbSubSikap3.getSelectedItem().toString());
                        kepentingan="Cukup Penting ke-3";
                    }else{
                        stat.setString(4, cbSubSikap4.getSelectedItem().toString());
                        kepentingan="Biasa ke-4";
                        n++;
                    }
                    stat.setString(5, kepentingan);
                    stat.executeUpdate();
                    nSikap++;
                }
            }while(n<=4);   
            JOptionPane.showMessageDialog(null, "DATA Berhasil Disimpan");
            kosong();
            updateDataTabel();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan "+e);
        }
    }
    
    protected void hapusDataSubKriteria(){
        int ok = JOptionPane.showConfirmDialog(null,"hapus","Konfirmasi Dialog",JOptionPane.YES_NO_OPTION);
        if(ok == 0){
            String sql = "DELETE FROM sub_kriteria";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil diHapus ");
                kosong();
                updateDataTabel();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Data Gagal diHapus " + e);
            }
        }
    }
    
    protected void editDataSubKriteria(){
        try{
        int n=1, nPendidikan=1, nWawancara=1, nWaktu=1, nSikap=1, i=1;
            do{
                String kepentingan;
                String sql = "UPDATE sub_kriteria set kd_kriteria=?, nama_kriteria=?, nama_sub_kriteria=?, prioritas_kepentingan=? WHERE no_sub=?";
                PreparedStatement stat = conn.prepareStatement(sql);
                java.sql.Statement statCek = conn.createStatement();
                String sqlSub = "SELECT kd_kriteria, nama_kriteria FROM kriteria";
                String sqlPendidikan = "SELECT kd_kriteria, nama_kriteria FROM kriteria WHERE nama_kriteria='Pendidikan Terakhir'";
                String sqlWawancara = "SELECT kd_kriteria, nama_kriteria FROM kriteria WHERE nama_kriteria='Wawancara'";
                String sqlWaktu = "SELECT kd_kriteria, nama_kriteria FROM kriteria WHERE nama_kriteria='Waktu Luang'";
                String sqlSikap = "SELECT kd_kriteria, nama_kriteria FROM kriteria WHERE nama_kriteria='Sikap'";
                ResultSet hasil = statCek.executeQuery(sqlSub);
                if(n==1){
                    hasil = statCek.executeQuery(sqlPendidikan);
                    stat.setString(5, Integer.toString(i));
                    i++;
                    while(hasil.next()){
                        String a = hasil.getString("kd_kriteria");
                        String b = hasil.getString("nama_kriteria");
                        stat.setString(1, a);
                        stat.setString(2, b);
                    }
                    if(nPendidikan == 1){
                        stat.setString(3, cbSubPendidikan1.getSelectedItem().toString());
                        kepentingan="Sangat Penting ke-1";
                    }else if(nPendidikan == 2){
                        stat.setString(3, cbSubPendidikan2.getSelectedItem().toString());
                        kepentingan="Penting ke-2";
                    }else if(nPendidikan == 3){
                        stat.setString(3, cbSubPendidikan3.getSelectedItem().toString());
                        kepentingan="Cukup Penting ke-3";
                    }else{
                        stat.setString(3, cbSubPendidikan4.getSelectedItem().toString());
                        kepentingan="Biasa ke-4";
                        n++;
                    }
                    stat.setString(4, kepentingan);
                    stat.executeUpdate();
                    nPendidikan++;
                }else if(n==2){
                    hasil = statCek.executeQuery(sqlWawancara);
                    stat.setString(5, Integer.toString(i));
                    i++;
                    while(hasil.next()){    
                        String a = hasil.getString("kd_kriteria");
                        String b = hasil.getString("nama_kriteria");
                        stat.setString(1, a);
                        stat.setString(2, b);
                    }
                    if(nWawancara == 1){
                        stat.setString(3, cbSubWawancara1.getSelectedItem().toString());
                        kepentingan="Sangat Penting ke-1";
                    }else if(nWawancara == 2){
                        stat.setString(3, cbSubWawancara2.getSelectedItem().toString());
                        kepentingan="Cukup Penting ke-2";
                    }else{
                        stat.setString(3, cbSubWawancara3.getSelectedItem().toString());
                        kepentingan="Biasa ke-3";
                        n++;
                    }
                    stat.setString(4, kepentingan);
                    stat.executeUpdate();
                    nWawancara++;
                }else if(n==3){
                    hasil = statCek.executeQuery(sqlWaktu);
                    stat.setString(5, Integer.toString(i));
                    i++;
                    while(hasil.next()){    
                        String a = hasil.getString("kd_kriteria");
                        String b = hasil.getString("nama_kriteria");
                        stat.setString(1, a);
                        stat.setString(2, b);
                    }
                    if(nWaktu == 1){
                        stat.setString(3, cbSubWaktu1.getSelectedItem().toString());
                        kepentingan="Sangat Penting ke-1";
                    }else if(nWaktu == 2){
                        stat.setString(3, cbSubWaktu2.getSelectedItem().toString());
                        kepentingan="Cukup Penting ke-2";
                    }else{
                        stat.setString(3, cbSubWaktu3.getSelectedItem().toString());
                        kepentingan="Biasa ke-3";
                        n++;
                    }
                    stat.setString(4, kepentingan);
                    stat.executeUpdate();
                    nWaktu++;
                }else{    
                    hasil = statCek.executeQuery(sqlSikap);
                    stat.setString(5, Integer.toString(i));
                    i++;
                    while(hasil.next()){
                        String a = hasil.getString("kd_kriteria");
                        String b = hasil.getString("nama_kriteria");
                        stat.setString(1, a);
                        stat.setString(2, b);
                    }
                    if(nSikap == 1){
                        stat.setString(3, cbSubSikap1.getSelectedItem().toString());
                        kepentingan="Sangat Penting ke-1";
                    }else if(nSikap == 2){
                        stat.setString(3, cbSubSikap2.getSelectedItem().toString());
                        kepentingan="Penting ke-2";
                    }else if(nSikap == 3){
                        stat.setString(3, cbSubSikap3.getSelectedItem().toString());
                        kepentingan="Cukup Penting ke-3";
                    }else{
                        stat.setString(3, cbSubSikap4.getSelectedItem().toString());
                        kepentingan="Biasa ke-4";
                        n++;
                    }
                    stat.setString(4, kepentingan);
                    stat.executeUpdate();
                    nSikap++;
                }
            }while(n<=4);   
            JOptionPane.showMessageDialog(null, "DATA Berhasil DiUbah");
            kosong();
            updateDataTabel();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal DiUbah "+e);
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

        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelSubKriteria = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        tombolSimpan = new javax.swing.JButton();
        tombolEdit = new javax.swing.JButton();
        tombolHapus = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbSubPendidikan4 = new javax.swing.JComboBox<>();
        cbSubPendidikan3 = new javax.swing.JComboBox<>();
        cbSubPendidikan2 = new javax.swing.JComboBox<>();
        cbSubPendidikan1 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbSubWawancara3 = new javax.swing.JComboBox<>();
        cbSubWawancara2 = new javax.swing.JComboBox<>();
        cbSubWawancara1 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cbSubSikap4 = new javax.swing.JComboBox<>();
        cbSubSikap3 = new javax.swing.JComboBox<>();
        cbSubSikap2 = new javax.swing.JComboBox<>();
        cbSubSikap1 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cbSubWaktu3 = new javax.swing.JComboBox<>();
        cbSubWaktu2 = new javax.swing.JComboBox<>();
        cbSubWaktu1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(14, 41, 84));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel7.setBackground(new java.awt.Color(14, 41, 84));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabelSubKriteria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode Kriteria", "Nama Kriteria", "Nama SubKriteria", "Kepentingan SubKriteria"
            }
        ));
        tabelSubKriteria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSubKriteriaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelSubKriteria);

        jLabel1.setText("Catatan : Edit data, klik data pada tabel terlebih dahulu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(396, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tombolSimpan.setBackground(new java.awt.Color(0, 51, 102));
        tombolSimpan.setForeground(new java.awt.Color(255, 255, 255));
        tombolSimpan.setText("Simpan");
        tombolSimpan.setBorder(null);
        tombolSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolSimpanActionPerformed(evt);
            }
        });

        tombolEdit.setBackground(new java.awt.Color(0, 51, 102));
        tombolEdit.setForeground(new java.awt.Color(255, 255, 255));
        tombolEdit.setText("Edit");
        tombolEdit.setBorder(null);
        tombolEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolEditActionPerformed(evt);
            }
        });

        tombolHapus.setBackground(new java.awt.Color(102, 0, 0));
        tombolHapus.setForeground(new java.awt.Color(255, 255, 255));
        tombolHapus.setText("Hapus");
        tombolHapus.setBorder(null);
        tombolHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolHapusActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Harga"));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Harga Yang Sangat Penting ke-1");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Harga Penting ke-2");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Harga Cukup Penting ke-3");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Harga biasa ke-4");

        cbSubPendidikan4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Harga -", "Harga Standar", "Harga Lumayan Tinggi", "Harga Rendah", "Harga Sangat Tinggi" }));

        cbSubPendidikan3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Harga -", "Harga Standar", "Harga Lumayan Tinggi", "Harga Rendah", "Harga Sangat Tinggi" }));

        cbSubPendidikan2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Harga -", "Harga Standar", "Harga Lumayan Tinggi", "Harga Rendah", "Harga Sangat Tinggi" }));

        cbSubPendidikan1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Harga -", "Harga Standar", "Harga Lumayan Tinggi", "Harga Rendah", "Harga Sangat Tinggi" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbSubPendidikan3, 0, 186, Short.MAX_VALUE)
                        .addComponent(cbSubPendidikan4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbSubPendidikan2, 0, 186, Short.MAX_VALUE)
                        .addComponent(cbSubPendidikan1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cbSubPendidikan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cbSubPendidikan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cbSubPendidikan3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cbSubPendidikan4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Brand"));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Brand Yang Sangat Penting ke-1");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Brand Cukup Penting ke-3");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Brand biasa ke-3");

        cbSubWawancara3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SubKriteria Nilai Brand -", "Brand Sangat Terkenal", "Brand Terkenal", "Brand Tidak Terkenal" }));

        cbSubWawancara2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SubKriteria Nilai Brand -", "Brand Sangat Terkenal", "Brand Terkenal", "Brand Tidak Terkenal" }));

        cbSubWawancara1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SubKriteria Nilai Brand -", "Brand Sangat Terkenal", "Brand Terkenal", "Brand Tidak Terkenal" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbSubWawancara3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbSubWawancara2, javax.swing.GroupLayout.Alignment.TRAILING, 0, 183, Short.MAX_VALUE)
                        .addComponent(cbSubWawancara1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbSubWawancara1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(cbSubWawancara2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(cbSubWawancara3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Kualitas"));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Kualtias Yang Sangat Penting ke-1");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setText("Kualitas Penting ke-2");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setText("Kualitas Cukup Penting ke-3");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText("Kualitas biasa ke-4");

        cbSubSikap4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Kualitas -", "Kualitas Sangat Bagus", "Kualitas Bagus", "Kualitas  standar", "Kualitas dibawah standar" }));

        cbSubSikap3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Kualitas -", "Kualitas Sangat Bagus", "Kualitas Bagus", "Kualitas  standar", "Kualitas dibawah standar" }));

        cbSubSikap2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Kualitas -", "Kualitas Sangat Bagus", "Kualitas Bagus", "Kualitas  standar", "Kualitas dibawah standar" }));

        cbSubSikap1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Kualitas -", "Kualitas Sangat Bagus", "Kualitas Bagus", "Kualitas  standar", "Kualitas dibawah standar" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addGap(59, 59, 59)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbSubSikap1, javax.swing.GroupLayout.Alignment.LEADING, 0, 178, Short.MAX_VALUE)
                    .addComponent(cbSubSikap4, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbSubSikap3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbSubSikap2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cbSubSikap1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cbSubSikap2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cbSubSikap3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(cbSubSikap4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Garansi"));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Garansi  Yang Sangat Penting ke-1");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setText("Garansi  Cukup Penting ke-3");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setText("Garansi  biasa ke-3");

        cbSubWaktu3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Garansi -", "12 bulan", "6 bulan", "1 bulan" }));

        cbSubWaktu2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Garansi -", "12 bulan", "6 bulan", "1 bulan" }));

        cbSubWaktu1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih SubKriteria Garansi -", "12 bulan", "6 bulan", "1 bulan" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbSubWaktu3, 0, 184, Short.MAX_VALUE)
                    .addComponent(cbSubWaktu2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbSubWaktu1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbSubWaktu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSubWaktu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSubWaktu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(tombolSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tombolEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tombolHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tombolSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tombolEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tombolHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(230, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel2);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Sub Kriteria");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(357, 357, 357))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelSubKriteriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSubKriteriaMouseClicked
        // TODO add your handling code here:
        getDataTabel();
    }//GEN-LAST:event_tabelSubKriteriaMouseClicked

    private void tombolSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolSimpanActionPerformed
        // TODO add your handling code here:
        insertDataSubKriteria();
    }//GEN-LAST:event_tombolSimpanActionPerformed

    private void tombolEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolEditActionPerformed
        // TODO add your handling code here:
        editDataSubKriteria();
    }//GEN-LAST:event_tombolEditActionPerformed

    private void tombolHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolHapusActionPerformed
        // TODO add your handling code here:
        hapusDataSubKriteria();
    }//GEN-LAST:event_tombolHapusActionPerformed

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
            java.util.logging.Logger.getLogger(subkriteria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(subkriteria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(subkriteria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(subkriteria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new subkriteria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbSubPendidikan1;
    private javax.swing.JComboBox<String> cbSubPendidikan2;
    private javax.swing.JComboBox<String> cbSubPendidikan3;
    private javax.swing.JComboBox<String> cbSubPendidikan4;
    private javax.swing.JComboBox<String> cbSubSikap1;
    private javax.swing.JComboBox<String> cbSubSikap2;
    private javax.swing.JComboBox<String> cbSubSikap3;
    private javax.swing.JComboBox<String> cbSubSikap4;
    private javax.swing.JComboBox<String> cbSubWaktu1;
    private javax.swing.JComboBox<String> cbSubWaktu2;
    private javax.swing.JComboBox<String> cbSubWaktu3;
    private javax.swing.JComboBox<String> cbSubWawancara1;
    private javax.swing.JComboBox<String> cbSubWawancara2;
    private javax.swing.JComboBox<String> cbSubWawancara3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelSubKriteria;
    private javax.swing.JButton tombolEdit;
    private javax.swing.JButton tombolHapus;
    private javax.swing.JButton tombolSimpan;
    // End of variables declaration//GEN-END:variables
}
