package com.mycompany.aplikasipelunasan;

import com.mycompany.aplikasipelunasan.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AplikasiPelunasan {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Mengubah desain form agar mengikuti tema bawaan Windows Anda (biar lebih rapi)
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Memanggil dan memunculkan Form Login pertama kali
            new LoginView().setVisible(true);
        });
    }
}