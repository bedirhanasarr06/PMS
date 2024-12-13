package view;
import repository.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
public class PersonnelView extends JFrame {
    private JTextField nameField, surnameField, personIDField, birthDateField, hireDateField, departmentIDField, salaryField, searchField;
    private JButton addButton, updateButton, searchButton;
    private JTable personnelTable;
    private DefaultTableModel tableModel;

    public PersonnelView() {
        setTitle("Personnel Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Fields
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        formPanel.add(new JLabel("Surname:"), gbc);

        gbc.gridx = 3; gbc.gridy = 0;
        surnameField = new JTextField(15);
        formPanel.add(surnameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Person ID:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        personIDField = new JTextField(15);
        personIDField.setEditable(true);
        formPanel.add(personIDField, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        formPanel.add(new JLabel("Department ID:"), gbc);

        gbc.gridx = 3; gbc.gridy = 1;
        departmentIDField = new JTextField(15);
        formPanel.add(departmentIDField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Date of Birth:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        birthDateField = new JTextField(15);
        formPanel.add(birthDateField, gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        formPanel.add(new JLabel("Hire Date:"), gbc);

        gbc.gridx = 3; gbc.gridy = 2;
        hireDateField = new JTextField(15);
        formPanel.add(hireDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Salary:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        salaryField = new JTextField(15);
        formPanel.add(salaryField, gbc);

        // Buttons
        gbc.gridx = 2; gbc.gridy = 3;
        addButton = new JButton("Add Personnel");
        formPanel.add(addButton, gbc);

        gbc.gridx = 3; gbc.gridy = 3;
        updateButton = new JButton("Update Personnel");
        formPanel.add(updateButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        searchButton = new JButton("Search Personnel");
        formPanel.add(searchButton, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        searchField = new JTextField(15);
        formPanel.add(searchField, gbc);


        add(formPanel, BorderLayout.NORTH);

        // Table Model and JTable
        String[] columnNames = {"Edit", "Delete", "Name", "Surname", "Person ID", "Date of Birth", "Hire Date", "Department ID", "Salary"};
        tableModel = new DefaultTableModel(columnNames, 0);
        personnelTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1; // Only Edit and Delete columns are editable
            }
        };

        // Add Button Editors and Renderers
        personnelTable.getColumn("Edit").setCellRenderer(new ButtonRenderer("Edit"));
        personnelTable.getColumn("Edit").setCellEditor(new EditButtonEditor(new JCheckBox()));

        personnelTable.getColumn("Delete").setCellRenderer(new ButtonRenderer("Delete"));
        personnelTable.getColumn("Delete").setCellEditor(new DeleteButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(personnelTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Add button listeners
    public void addButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateButtonListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    // Getter and Setter methods for form fields
    public String getNameField() {
        return nameField.getText();
    }

    public void setNameField(String value) {
        nameField.setText(value);
    }

    public String getSurnameField() {
        return surnameField.getText();
    }

    public void setSurnameField(String value) {
        surnameField.setText(value);
    }

    public int getPersonIDField() {
        return Integer.parseInt(personIDField.getText());
    }

    public void setPersonIDField(int value) {
        personIDField.setText(String.valueOf(value));
    }

    public void enablePersonIDField(boolean enable) {
        personIDField.setEditable(enable);
    }

    public String getBirthDateField() {
        return birthDateField.getText();
    }

    public void setBirthDateField(String value) {
        birthDateField.setText(value);
    }

    public String getHireDateField() {
        return hireDateField.getText();
    }

    public void setHireDateField(String value) {
        hireDateField.setText(value);
    }

    public int getDepartmentIDField() {
        return Integer.parseInt(departmentIDField.getText());
    }

    public void setDepartmentIDField(int value) {
        departmentIDField.setText(String.valueOf(value));
    }
    public double getSalaryField(){return Double.parseDouble(salaryField.getText());}
    public void setSalaryField(double value){salaryField.setText(String.valueOf(value));}

    public String getSearchFieldText() {
        return searchField.getText().trim();
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getPersonnelTable() {
        return personnelTable;
    }

    public void addRowToTable(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    // Button Renderer Class
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Button Editor Class for "Edit"
    class EditButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int row;

        public EditButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Edit");
            button.setOpaque(true);

            button.addActionListener(e -> {
                row = personnelTable.getSelectedRow();
                // Populate fields with selected row data
                setNameField((String) personnelTable.getValueAt(row, 2));
                setSurnameField((String) personnelTable.getValueAt(row, 3));
                setPersonIDField((int) personnelTable.getValueAt(row, 4));
                setBirthDateField((String) personnelTable.getValueAt(row, 5));
                setHireDateField((String) personnelTable.getValueAt(row, 6));
                setDepartmentIDField((int) personnelTable.getValueAt(row, 7));
                setSalaryField((double) personnelTable.getValueAt(row,8));
                enablePersonIDField(false); // Disable Person ID for editing
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    // Button Editor Class for "Delete"
    class DeleteButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int row;

        public DeleteButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Delete");
            button.setOpaque(true);

            button.addActionListener(e -> {
                row = personnelTable.getSelectedRow();
                if (row >= 0) { // Check if a valid row is selected
                    try {
                        int personID = (int) personnelTable.getValueAt(row, 4); // Get person ID

                        // Perform delete operation
                        // Assuming PersonnelRepository instance is accessible
                        PersonnelRepository repository = new PersonnelRepository();
                        repository.deletePersonnelById(personID);

                        // Remove the row from the table model
                        ((DefaultTableModel) personnelTable.getModel()).removeRow(row);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(button, "Failed to delete personnel: " + ex.getMessage());
                    }
                }
                fireEditingStopped(); // Stop editing to refresh the table
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
}