package com.mycompany.aplikasipelunasan.view;

import com.mycompany.aplikasipelunasan.dao.PenggunaDao;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnShowPassword; 
    private JButton btnLogin;
    private PenggunaDao penggunaDAO;

    private boolean isPasswordVisible = false;

    // Objek Gambar Ikon
    private ImageIcon iconOpenEye;
    private ImageIcon iconBlindEye;

    // Tema Warna Sesuai Identitas Aplikasi Anda
    private final Color warnaBgKiri      = new Color(25, 118, 210);  // Biru Tua Utama
    private final Color warnaBgKanan     = new Color(245, 249, 255); // Biru Sangat Muda (Bersih)
    private final Color warnaTeksUtama   = new Color(25, 118, 210);  
    private final Color warnaTombolHover = new Color(21, 101, 192);

    public LoginView() {
        penggunaDAO = new PenggunaDao(); 
        buatIkonGambarOtomatis(); 
        initComponent();
    }

    private void buatIkonGambarOtomatis() {
        // 1. Ikon Mata Terbuka (Open Eye)
        BufferedImage imgOpen = new BufferedImage(22, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gOpen = imgOpen.createGraphics();
        gOpen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gOpen.setColor(Color.GRAY);
        gOpen.drawOval(1, 2, 18, 12); 
        gOpen.fillOval(7, 5, 6, 6);   
        gOpen.dispose();
        iconOpenEye = new ImageIcon(imgOpen);

        // 2. Ikon Mata Dicoret (Blind Eye)
        BufferedImage imgBlind = new BufferedImage(22, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBlind = imgBlind.createGraphics();
        gBlind.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gBlind.setColor(warnaBgKiri);
        gBlind.drawOval(1, 2, 18, 12); 
        gBlind.fillOval(7, 5, 6, 6);   
        gBlind.setStroke(new BasicStroke(2f));
        gBlind.setColor(Color.RED);    
        gBlind.drawLine(2, 14, 18, 2); 
        gBlind.dispose();
        iconBlindEye = new ImageIcon(imgBlind);
    }

    private void initComponent() {
        setTitle("Form Login Aplikasi");
        setSize(750, 450); // Ukuran besar, luas, dan premium (Split-Screen)
        setResizable(false); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Menggunakan GridLayout 1 Baris, 2 Kolom (Kiri & Kanan Berbagi Rata)
        getContentPane().setLayout(new GridLayout(1, 2)); 

        // =================================================================
        // --- SISI KIRI: PANEL DEKORASI / INFORMASI ---
        // =================================================================
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(warnaBgKiri);
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        gbcLeft.anchor = GridBagConstraints.WEST;

        // Teks Judul Utama
        JLabel lblTitleLeft = new JLabel("Aplikasi Pelunasan");
        lblTitleLeft.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitleLeft.setForeground(Color.WHITE);
        gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(0, 0, 8, 0);
        leftPanel.add(lblTitleLeft, gbcLeft);

        // Teks Sub-Deskripsi Sistem
        JLabel lblDescLeft = new JLabel("<html>Sistem Informasi Manajemen &<br>Monitoring Pelunasan Pembayaran</html>");
        lblDescLeft.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDescLeft.setForeground(new Color(218, 233, 255)); // Putih agak kebiruan lembut
        gbcLeft.gridy = 1;
        gbcLeft.insets = new Insets(0, 0, 40, 0);
        leftPanel.add(lblDescLeft, gbcLeft);

        // Teks Catatan Hak Akses di Bagian Bawah
        JLabel lblFooterLeft = new JLabel("Silakan masukkan akun admin atau petugas.");
        lblFooterLeft.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblFooterLeft.setForeground(new Color(180, 210, 255));
        gbcLeft.gridy = 2;
        gbcLeft.insets = new Insets(20, 0, 0, 0);
        leftPanel.add(lblFooterLeft, gbcLeft);


        // =================================================================
        // --- SISI KANAN: PANEL FORM LOGIN INPUT ---
        // =================================================================
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(warnaBgKanan);
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.fill = GridBagConstraints.HORIZONTAL;
        gbcRight.insets = new Insets(8, 0, 8, 0);

        // Label Header Masuk ke Sistem
        JLabel lblHeaderRight = new JLabel("Masuk ke Sistem");
        lblHeaderRight.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeaderRight.setForeground(new Color(51, 51, 51));
        gbcRight.gridx = 0; gbcRight.gridy = 0;
        gbcRight.gridwidth = 2;
        gbcRight.insets = new Insets(0, 0, 25, 0);
        rightPanel.add(lblHeaderRight, gbcRight);

        // Reset gridwidth untuk form baris bawahnya
        gbcRight.gridwidth = 1; 

        // --- INPUT USERNAME ---
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsername.setForeground(Color.DARK_GRAY);
        gbcRight.gridx = 0; gbcRight.gridy = 1;
        gbcRight.insets = new Insets(5, 0, 2, 0);
        rightPanel.add(lblUsername, gbcRight);

        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(txtUsername.getPreferredSize().width, 32));
        gbcRight.gridx = 0; gbcRight.gridy = 2;
        gbcRight.insets = new Insets(0, 0, 15, 0);
        rightPanel.add(txtUsername, gbcRight);

        // --- INPUT PASSWORD ---
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setForeground(Color.DARK_GRAY);
        gbcRight.gridx = 0; gbcRight.gridy = 3;
        gbcRight.insets = new Insets(5, 0, 2, 0);
        rightPanel.add(lblPassword, gbcRight);

        // Kotak Wadah Gabungan Password + Tombol Mata
        JPanel passwordContainer = new JPanel(new BorderLayout());
        passwordContainer.setBackground(Color.WHITE);
        passwordContainer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        passwordContainer.setPreferredSize(new Dimension(passwordContainer.getPreferredSize().width, 32));

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(new EmptyBorder(0, 6, 0, 0));

        btnShowPassword = new JButton();
        btnShowPassword.setIcon(iconBlindEye);
        btnShowPassword.setPreferredSize(new Dimension(40, 30));
        btnShowPassword.setBorderPainted(false);
        btnShowPassword.setContentAreaFilled(false);
        btnShowPassword.setFocusPainted(false);
        btnShowPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));

        passwordContainer.add(txtPassword, BorderLayout.CENTER);
        passwordContainer.add(btnShowPassword, BorderLayout.EAST);

        gbcRight.gridx = 0; gbcRight.gridy = 4;
        gbcRight.insets = new Insets(0, 0, 25, 0);
        rightPanel.add(passwordContainer, gbcRight);

        // --- TOMBOL LOGIN MODERN ---
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(warnaBgKiri);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setOpaque(true);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setPreferredSize(new Dimension(btnLogin.getPreferredSize().width, 38));

        gbcRight.gridx = 0; gbcRight.gridy = 5;
        gbcRight.insets = new Insets(10, 0, 0, 0);
        rightPanel.add(btnLogin, gbcRight);


        // =================================================================
        // --- GABUNGKAN KEDUA PANEL UTAMA KE WINDOWS FRAME ---
        // =================================================================
        add(leftPanel);
        add(rightPanel);

        // --- AKSI TOMBOL MATA (SHOW/HIDE PASSWORD) ---
        btnShowPassword.addActionListener(e -> {
            if (!isPasswordVisible) {
                txtPassword.setEchoChar((char) 0); 
                btnShowPassword.setIcon(iconOpenEye); 
                isPasswordVisible = true;
            } else {
                txtPassword.setEchoChar('•'); 
                btnShowPassword.setIcon(iconBlindEye); 
                isPasswordVisible = false;
            }
            txtPassword.requestFocus(); 
        });

        // --- AKSI TOMBOL LOGIN ---
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            if (penggunaDAO.login(username, password)) {
                JOptionPane.showMessageDialog(this, "Selamat Datang, " + username + "!");
                new MainMenuView().setVisible(true);
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Gagal Login", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}