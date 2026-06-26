package com.mycompany.aplikasipelunasan.dao;

import com.mycompany.aplikasipelunasan.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PelunasanDAO {

    public List<Object[]> getRingkasanPelunasan() {
        List<Object[]> listData = new ArrayList<>();
        
        // Menggunakan query hitung-hitungan cerdas Anda (dengan tambahan kolom no_va)
        String query = "SELECT "
                     + "t.id_transaksi, "
                     + "p.nama AS nama_pelanggan, "
                     + "t.tanggal, "
                     + "MAX(pl.no_va) AS no_va, "
                     + "t.total_hutang, "
                     + "COALESCE(SUM(pl.jumlah_bayar), 0) AS total_bayar, "
                     + "(t.total_hutang - COALESCE(SUM(pl.jumlah_bayar), 0)) AS sisa_hutang, "
                     + "IF((t.total_hutang - COALESCE(SUM(pl.jumlah_bayar), 0)) <= 0, 'Sudah Lunas', 'Belum Lunas') AS status_pembayaran "
                     + "FROM transaksi t "
                     + "JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan "
                     + "LEFT JOIN pelunasan pl ON t.id_transaksi = pl.id_transaksi "
                     + "GROUP BY t.id_transaksi, p.nama, t.tanggal, t.total_hutang";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                // Menyiapkan 8 slot kolom untuk dimasukkan ke tabel aplikasi
                Object[] row = new Object[8];
                
                // Menarik data berdasarkan nama asli atau "nama samaran" (AS) yang ada di query
                row[0] = rs.getObject("id_transaksi");
                row[1] = rs.getObject("nama_pelanggan"); 
                row[2] = rs.getObject("tanggal");
                
                // Cek aman untuk No Virtual Account (Jika kosong diubah jadi strip "-")
                Object noVa = rs.getObject("no_va");
                row[3] = (noVa != null) ? noVa : "-";
                
                row[4] = rs.getObject("total_hutang");
                row[5] = rs.getObject("total_bayar");
                row[6] = rs.getObject("sisa_hutang");
                row[7] = rs.getObject("status_pembayaran");
                
                listData.add(row);
            }
        } catch (SQLException e) {
            // Alarm pelacak eror tetap diaktifkan untuk berjaga-jaga
            System.err.println("=== INFO DETAIL EROR DATABASE ===");
            System.err.println("Kode Eror  : " + e.getErrorCode());
            System.err.println("Pesan Eror : " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    // --- FITUR BARU: MENYIMPAN PEMBAYARAN KASIR ---
    public boolean tambahPembayaran(int idTransaksi, double jumlahBayar) {
        // Kita hanya perlu menyuntikkan ID dan jumlah uang ke tabel pelunasan
        String query = "INSERT INTO pelunasan (id_transaksi, jumlah_bayar) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            ps.setInt(1, idTransaksi);
            ps.setDouble(2, jumlahBayar);
            
            // Eksekusi suntikan ke database
            int barisBerubah = ps.executeUpdate();
            return barisBerubah > 0; // Mengembalikan nilai 'true' jika berhasil
            
        } catch (SQLException e) {
            System.err.println("=== GAGAL MENYIMPAN PEMBAYARAN ===");
            System.err.println("Pesan Eror : " + e.getMessage());
            return false;
        }
    }
    
}