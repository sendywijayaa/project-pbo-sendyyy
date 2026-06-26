package com.mycompany.aplikasipelunasan.view;

import com.mycompany.aplikasipelunasan.dao.PelunasanDAO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MainMenuView extends JFrame {

    private DefaultTableModel tableModel;
    private JTable tablePelunasan;
    private PelunasanDAO pelunasanDAO = new PelunasanDAO();

    // Komponen CardLayout (Untuk ganti halaman)
    private JPanel panelKontenUtama;
    private CardLayout cardLayout;

    // Komponen Form Kasir
    private JTextField txtIdTransaksi, txtNamaPelanggan, txtSisaHutang, txtNominalBayar;
    private JButton btnBayar;

    public MainMenuView() {
        setTitle("Sistem Pelunasan - Dashboard Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setLocationRelativeTo(null); // Tengah layar
        setLayout(new BorderLayout());

        // ==========================================
        // 1. MEMBUAT SIDEBAR MENU (KIRI)
        // ==========================================
        JPanel panelSidebar = new JPanel();
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS));
        panelSidebar.setBackground(new Color(20, 30, 50)); // Warna Biru Dongker / Navy gelap
        panelSidebar.setPreferredSize(new Dimension(220, getHeight()));
        panelSidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        // Judul Aplikasi di Sidebar
        JLabel lblJudul = new JLabel("Admin Panel");
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 22));
        lblJudul.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubJudul = new JLabel("Sistem Pelunasan");
        lblSubJudul.setForeground(new Color(150, 150, 150));
        lblSubJudul.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubJudul.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tombol-tombol Sidebar
        JButton btnDashboard = buatTombolMenu("Dashboard");
        JButton btnTabel = buatTombolMenu("Tabel Data");
        JButton btnKasir = buatTombolMenu("Form Kasir");
        JButton btnLogout = buatTombolMenu("Logout");

        // Menyusun elemen ke dalam Sidebar
        panelSidebar.add(lblJudul);
        panelSidebar.add(lblSubJudul);
        panelSidebar.add(Box.createRigidArea(new Dimension(0, 40))); // Spasi kosong
        panelSidebar.add(btnDashboard);
        panelSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSidebar.add(btnTabel);
        panelSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSidebar.add(btnKasir);
        panelSidebar.add(Box.createVerticalGlue()); // Mendorong logout ke paling bawah
        panelSidebar.add(btnLogout);

        // ==========================================
        // 2. MEMBUAT KONTEN UTAMA (KANAN - CARDLAYOUT)
        // ==========================================
        cardLayout = new CardLayout();
        panelKontenUtama = new JPanel(cardLayout);
        panelKontenUtama.setBackground(new Color(245, 245, 245));

        // --- HALAMAN 1: DASHBOARD ---
        JPanel halDashboard = new JPanel(new BorderLayout(20, 20));
        halDashboard.setBackground(new Color(240, 242, 245));
        halDashboard.setBorder(new EmptyBorder(30, 30, 30, 30));

// TAMBAHKAN HEADER DI SINI
        // 1. Buat panel pembungkus (wrapper) untuk header
        JPanel panelHeaderContainer = new JPanel(new BorderLayout());
        panelHeaderContainer.setOpaque(false);

// 2. Judul di sisi kiri (WEST)
        JPanel headerDashboard = buatHeader("Dashboard", "Ringkasan data buku, anggota, transaksi, dan status operasional.");
        panelHeaderContainer.add(headerDashboard, BorderLayout.WEST);

// 3. Tombol Refresh (dengan pengaturan warna & ukuran)
        JButton btnRefresh = new JButton("Refresh"); // Judul disingkat agar lebih rapi
        btnRefresh.setBackground(new Color(0, 150, 136)); // Warna teal/hijau
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(false); // Menghilangkan garis tepi agar terlihat modern
        btnRefresh.setContentAreaFilled(true); // PENTING: Agar warna background muncul
        btnRefresh.setOpaque(true);
        btnRefresh.setPreferredSize(new Dimension(100, 30)); // Ukuran diperkecil

        // --- Tambahan Efek Hover ---
        Color originalColor = new Color(0, 150, 136); // Sesuaikan dengan warna di baris 94
        Color hoverColor = new Color(0, 170, 150);    // Warna hijau yang sedikit lebih terang

        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRefresh.setBackground(hoverColor); // Warna berubah saat mouse masuk
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRefresh.setBackground(originalColor); // Kembali ke warna awal saat mouse keluar
            }
        });

// 4. WRAPPER UNTUK TOMBOL: Mencegah tombol melar karena BorderLayout
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelTombol.setOpaque(false);
        panelTombol.add(btnRefresh);

// 5. Masukkan panelTombol ke sisi kanan (EAST)
        panelHeaderContainer.add(panelTombol, BorderLayout.EAST);

// 6. Masukkan container ke halDashboard
        halDashboard.add(panelHeaderContainer, BorderLayout.NORTH);

// Panel Statistik (yang sudah kita buat sebelumnya)
        JPanel panelStats = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // FlowLayout Left agar rata kiri
        panelStats.setOpaque(false);
        panelStats.add(buatKotakStatistik("Total Transaksi", "7", new Color(52, 152, 219)));
        panelStats.add(buatKotakStatistik("Sudah Lunas", "4", new Color(46, 204, 113)));
        panelStats.add(buatKotakStatistik("Belum Lunas", "3", new Color(231, 76, 60)));

        halDashboard.add(panelStats, BorderLayout.CENTER);

        // -- HALAMAN 2: TABEL DATA --
        JPanel halTabel = new JPanel(new BorderLayout(10, 10));
        halTabel.setBorder(new EmptyBorder(15, 15, 15, 15));
        halTabel.setBackground(Color.WHITE);

        JLabel lblJudulTabel = new JLabel("Data Log Ringkasan Pelunasan");
        lblJudulTabel.setFont(new Font("Arial", Font.BOLD, 18));

        String[] columns = {"ID Transaksi", "Nama Pelanggan", "Tanggal Transaksi", "No VA", "Total Hutang", "Total Bayar", "Sisa Hutang", "Status"};
        tableModel = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePelunasan = new JTable(tableModel);
        tablePelunasan.setRowHeight(28);
        tablePelunasan.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        halTabel.add(lblJudulTabel, BorderLayout.NORTH);
        halTabel.add(new JScrollPane(tablePelunasan), BorderLayout.CENTER);

        JLabel lblPetunjuk = new JLabel("*Klik 2x pada baris pelanggan untuk otomatis pindah ke Form Kasir");
        lblPetunjuk.setForeground(Color.BLUE);
        halTabel.add(lblPetunjuk, BorderLayout.SOUTH);
        // 1. Buat panel tombol
        JPanel panelTombolTabel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTombolTabel.setOpaque(false);

// 2. Buat tombol
        // --- Tombol Tambah (Hijau) ---
        JButton btnTambah = new JButton("Tambah");
        btnTambah.setBackground(new Color(40, 167, 69)); // Hijau
        btnTambah.setForeground(Color.WHITE);            // Teks putih lebih kontras di hijau
        btnTambah.setOpaque(true);                       // PENTING: Agar warna background muncul
        btnTambah.setBorderPainted(false);               // Menghilangkan border bawaan Windows
        btnTambah.setContentAreaFilled(true);            // Memastikan area tombol terisi warna

        // --- Tombol Hapus (Merah) ---
        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBackground(new Color(220, 53, 69));  // Merah
        btnHapus.setForeground(Color.WHITE);             // Teks putih lebih kontras di merah
        btnHapus.setOpaque(true);                        // PENTING: Agar warna background muncul
        btnHapus.setBorderPainted(false);                // Menghilangkan border bawaan Windows
        btnHapus.setContentAreaFilled(true);             // Memastikan area tombol terisi warna

        // --- 3. Tambahkan Logika Tombol ---
        btnTambah.addActionListener(e -> {
    JTextField fieldId = new JTextField();
    JTextField fieldNama = new JTextField();
    JTextField fieldHutang = new JTextField();
    
    Object[] message = {
        "ID Transaksi:", fieldId,
        "Nama Pelanggan:", fieldNama,
        "Total Hutang (Rp):", fieldHutang
    };
    
    int option = JOptionPane.showConfirmDialog(null, message, "Tambah Data Hutang", JOptionPane.OK_CANCEL_OPTION);
    
    if (option == JOptionPane.OK_OPTION) {
        try {
            // 1. Ambil input
            String id = fieldId.getText();
            String nama = fieldNama.getText();
            // Konversi string ke double untuk perhitungan
            double totalHutang = Double.parseDouble(fieldHutang.getText());
            double totalBayar = 0.0; // Asumsi hutang baru, bayar awal 0
            
            // 2. Kalkulasi Otomatis
            double sisaHutang = totalHutang - totalBayar;
            String status = (sisaHutang == 0) ? "Sudah Lunas" : "Belum Lunas";
            
            // 3. Masukkan ke tabel
            Object[] dataBaru = {
                id, 
                nama, 
                "2026-05-27", 
                "888812345678", // Anda bisa membuat ini dinamis nanti
                String.format("%.2f", totalHutang),
                String.format("%.2f", totalBayar),
                String.format("%.2f", sisaHutang),
                status
            };
            
            tableModel.addRow(dataBaru);
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Input Hutang harus berupa angka!");
        }
    }
});
// Aksi untuk tombol Hapus
        btnHapus.addActionListener(e -> {
            int selectedRow = tablePelunasan.getSelectedRow(); // Mengambil baris yang dipilih

            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedRow); // Menghapus baris dari model
                }
            } else {
                JOptionPane.showMessageDialog(null, "Silakan pilih baris di tabel yang ingin dihapus terlebih dahulu.");
            }
        });

// 3. Masukkan tombol ke panel
        panelTombolTabel.add(btnTambah);
        panelTombolTabel.add(btnHapus);

// 4. Masukkan panel tombol ke halTabel (posisikan di bawah)
        halTabel.add(panelTombolTabel, BorderLayout.SOUTH);

        // -- HALAMAN 3: FORM KASIR --
        JPanel halKasir = new JPanel(new GridBagLayout());
        halKasir.setBackground(Color.WHITE);
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder("Kasir - Pembayaran Tunai Langsung"));
        formPanel.setPreferredSize(new Dimension(400, 250));
        formPanel.setBackground(Color.WHITE);

        txtIdTransaksi = new JTextField();
        txtIdTransaksi.setEditable(false);
        txtNamaPelanggan = new JTextField();
        txtNamaPelanggan.setEditable(false);
        txtSisaHutang = new JTextField();
        txtSisaHutang.setEditable(false);
        txtNominalBayar = new JTextField();
        btnBayar = new JButton("Simpan Pembayaran");
        btnBayar.setBackground(new Color(46, 204, 113));
        btnBayar.setForeground(Color.WHITE);
        btnBayar.setFocusPainted(false);    // Menghilangkan kotak fokus standar
        btnBayar.setBorderPainted(false);   // Menghilangkan border bawaan Windows
        btnBayar.setOpaque(true);           // Memaksa warna hijau muncul
        btnBayar.setFont(new Font("Arial", Font.BOLD, 14)); // Menebalkan tulisan
        btnBayar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Kursor jadi bentuk tangan saat diarahkan

        formPanel.add(new JLabel("ID Transaksi :"));
        formPanel.add(txtIdTransaksi);
        formPanel.add(new JLabel("Nama Pelanggan :"));
        formPanel.add(txtNamaPelanggan);
        formPanel.add(new JLabel("Sisa Hutang :"));
        formPanel.add(txtSisaHutang);
        formPanel.add(new JLabel("Nominal Uang :"));
        formPanel.add(txtNominalBayar);
        formPanel.add(new JLabel(""));
        formPanel.add(btnBayar);

        halKasir.add(formPanel); // Memasukkan form ke tengah halaman kasir

        // Menambahkan ketiga halaman ke dalam CardLayout
        panelKontenUtama.add(halDashboard, "DASHBOARD");
        panelKontenUtama.add(halTabel, "TABEL");
        panelKontenUtama.add(halKasir, "KASIR");

        // Menyatukan Sidebar dan Konten Utama ke Frame
        add(panelSidebar, BorderLayout.WEST);
        add(panelKontenUtama, BorderLayout.CENTER);

        // ==========================================
        // 3. EVENT LISTENER (LOGIKA KLIK)
        // ==========================================
        // Logika Navigasi Sidebar
        btnDashboard.addActionListener(e -> cardLayout.show(panelKontenUtama, "DASHBOARD"));
        btnTabel.addActionListener(e -> cardLayout.show(panelKontenUtama, "TABEL"));
        btnKasir.addActionListener(e -> cardLayout.show(panelKontenUtama, "KASIR"));
        btnLogout.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar?", "Logout", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                this.dispose(); // Menutup layar ini
                new LoginView().setVisible(true); // Kembali ke form login (pastikan LoginView.java Anda siap)
            }
        });

        // Logika Tabel diklik 2x pindah ke Kasir otomatis
        tablePelunasan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Deteksi Double Click
                    int row = tablePelunasan.getSelectedRow();
                    if (row != -1) {
                        txtIdTransaksi.setText(tableModel.getValueAt(row, 0).toString());
                        txtNamaPelanggan.setText(tableModel.getValueAt(row, 1).toString());
                        txtSisaHutang.setText(tableModel.getValueAt(row, 6).toString());
                        txtNominalBayar.setText("");

                        // Otomatis pindah halaman ke Kasir
                        cardLayout.show(panelKontenUtama, "KASIR");
                        txtNominalBayar.requestFocus();
                    }
                }
            }
        });

        // Logika Tombol Bayar
        btnBayar.addActionListener(e -> prosesPembayaran());

        // Load data saat aplikasi dibuka
        loadDataTabel();
    }

    // --- FUNGSI ALAT BANTU ---
    // Fungsi untuk mempercantik desain tombol Sidebar
    private JButton buatTombolMenu(String teks) {
        JButton btn = new JButton(teks);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(30, 45, 75)); // Warna dasar tombol
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void prosesPembayaran() {
        if (txtIdTransaksi.getText().isEmpty() || txtNominalBayar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel dulu & masukkan nominal!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int idTransaksi = Integer.parseInt(txtIdTransaksi.getText());
            double nominalBayar = Double.parseDouble(txtNominalBayar.getText());

            boolean berhasil = pelunasanDAO.tambahPembayaran(idTransaksi, nominalBayar);
            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Pembayaran sukses disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadDataTabel(); // Refresh tabel

                // Bersihkan form & kembalikan layar ke Tabel
                txtIdTransaksi.setText("");
                txtNamaPelanggan.setText("");
                txtSisaHutang.setText("");
                txtNominalBayar.setText("");
                cardLayout.show(panelKontenUtama, "TABEL");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan ke database.", "Eror", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nominal harus angka bulat/desimal (tanpa titik ribuan)!", "Input Salah", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDataTabel() {
        tableModel.setRowCount(0);
        List<Object[]> data = pelunasanDAO.getRingkasanPelunasan();
        if (data != null) {
            for (Object[] row : data) {
                tableModel.addRow(row);
            }
        }
    }

    private JPanel buatKotakStatistik(String judul, String angka, Color warna) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 120));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)));

        JLabel lblJudul = new JLabel(judul);
        lblJudul.setFont(new Font("Arial", Font.PLAIN, 14));
        lblJudul.setForeground(Color.GRAY);
        lblJudul.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAngka = new JLabel(angka);
        lblAngka.setFont(new Font("Arial", Font.BOLD, 32));
        lblAngka.setForeground(warna);
        lblAngka.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblJudul);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblAngka);
        return panel;
    }

    private JPanel buatHeader(String judul, String deskripsi) {
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));
        panelHeader.setOpaque(false); // Transparan agar menyatu dengan background

        JLabel lblJudul = new JLabel(judul);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 24));
        lblJudul.setForeground(new Color(44, 62, 80)); // Warna gelap agar kontras

        JLabel lblDeskripsi = new JLabel(deskripsi);
        lblDeskripsi.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDeskripsi.setForeground(Color.GRAY);

        panelHeader.add(lblJudul);
        panelHeader.add(Box.createRigidArea(new Dimension(0, 5))); // Jarak antar judul & deskripsi
        panelHeader.add(lblDeskripsi);
        panelHeader.add(Box.createRigidArea(new Dimension(0, 20))); // Jarak bawah header ke konten

        return panelHeader;
    }
}
