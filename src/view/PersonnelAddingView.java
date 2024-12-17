package view;
import model.*;
import repository.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PersonnelAddingView extends JFrame {

    private PersonnelDAO personnelDAO = new PersonnelDAO(); // Veritabanı ile etkileşim

    public PersonnelAddingView() {
        // JFrame ayarları
        setTitle("Personel Ekle");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel ve layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10)); // 7 satır, 2 sütun

        // Form elemanları
        JLabel nameLabel = new JLabel("Ad:");
        JTextField nameField = new JTextField(20);

        JLabel surnameLabel = new JLabel("Soyad:");
        JTextField surnameField = new JTextField(20);

        JLabel birthDateLabel = new JLabel("Doğum Tarihi (YYYY-MM-DD):");
        JTextField birthDateField = new JTextField(20);

        JLabel hireDateLabel = new JLabel("İşe Başlama Tarihi (YYYY-MM-DD):");
        JTextField hireDateField = new JTextField(20);

        JLabel departmentIdLabel = new JLabel("Departman ID:");
        JTextField departmentIdField = new JTextField(20);

        JLabel salaryLabel = new JLabel("Maaş:");
        JTextField salaryField = new JTextField(20);

        JButton addButton = new JButton("Ekle");
        JButton cancelButton = new JButton("İptal");

        // Formu panellere ekleyelim
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(surnameLabel);
        panel.add(surnameField);
        panel.add(birthDateLabel);
        panel.add(birthDateField);
        panel.add(hireDateLabel);
        panel.add(hireDateField);
        panel.add(departmentIdLabel);
        panel.add(departmentIdField);
        panel.add(salaryLabel);
        panel.add(salaryField);
        panel.add(addButton);
        panel.add(cancelButton);

        // Pencereyi göster
        add(panel);
        setVisible(true);

        // Ekleme butonuna tıklama işlemi
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String surname = surnameField.getText();
                    String birthDate = birthDateField.getText();
                    String hireDate = hireDateField.getText();
                    int departmentId = Integer.parseInt(departmentIdField.getText());
                    double salary = Double.parseDouble(salaryField.getText());

                    // Yeni personel oluştur
                    Personnel personnel = new Personnel(name, surname, 0, birthDate, hireDate, departmentId, salary);

                    // Personeli veritabanına ekle
                    personnelDAO.addPersonnel(personnel);

                    // Kullanıcıyı bilgilendir
                    JOptionPane.showMessageDialog(null, "Personel başarıyla eklendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

                    // Alanları temizle
                    nameField.setText("");
                    surnameField.setText("");
                    birthDateField.setText("");
                    hireDateField.setText("");
                    departmentIdField.setText("");
                    salaryField.setText("");

                } catch (Exception ex) {
                    // Hata mesajı
                    JOptionPane.showMessageDialog(null, "Giriş hatalı! Lütfen tüm alanları doğru doldurun.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // İptal butonuna tıklama işlemi
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Pencereyi kapat
            }
        });
    }

    public static void main(String[] args) {
        new PersonnelAddingView();
    }
}