package controller;
import model.*;
import repository.*;
import view.*;
import java.awt.event.*;
import java.util.*;
public class PersonnelController {
    private Personnel model;
    private PersonnelView view;
    private PersonnelRepository repository;

    public PersonnelController(Personnel model, PersonnelView view) {
        this.model = model;
        this.view = view;
        this.repository = new PersonnelRepository();

        // Add button listeners
        view.addButtonListener(new AddButtonListener());
        view.addUpdateButtonListener(new UpdateButtonListener());
        view.addSearchButtonListener(new SearchButtonListener());

        // Load initial table data
        loadTableData(repository.getAllPersonnel());
    }

    // Load personnel data into the table
    private void loadTableData(List<Personnel> personnelList) {
        view.clearTable(); // Clear existing data in the table
        for (Personnel personnel : personnelList) {
            Object[] rowData = {
                    "Edit",
                    "Delete",
                    personnel.getName(),
                    personnel.getSurname(),
                    personnel.getPersonID(),
                    personnel.getBirthDate(),
                    personnel.getHireDate(),
                    personnel.getDepartmentID(),
                    personnel.getSalary()
            };
            view.addRowToTable(rowData);
        }
    }

    // Add Personnel Listener
    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Get data from view
                String name = view.getNameField();
                String surname = view.getSurnameField();
                int personID = view.getPersonIDField();
                String birthDate = view.getBirthDateField();
                String hireDate = view.getHireDateField();
                int departmentID = view.getDepartmentIDField();
                double salary = view.getSalaryField();

                if (name.isEmpty() || surname.isEmpty() || birthDate.isEmpty() || hireDate.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled!");
                }

                // Create a new Personnel object
                Personnel newPersonnel = new Personnel(name, surname, personID, birthDate, hireDate, departmentID, salary);

                // Add to database
                repository.addPersonnel(newPersonnel);

                // Refresh table
                loadTableData(repository.getAllPersonnel());
                view.enablePersonIDField(true); // Enable Person ID field for new entry
            } catch (Exception ex) {
                System.out.println("Error adding personnel: " + ex.getMessage());
            }
        }
    }

    // Update Personnel Listener
    class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Get updated data from view
                String name = view.getNameField();
                String surname = view.getSurnameField();
                int personID = view.getPersonIDField(); // Person ID is the key for the update
                String birthDate = view.getBirthDateField();
                String hireDate = view.getHireDateField();
                int departmentID = view.getDepartmentIDField();
                double salary = view.getSalaryField();

                if (name.isEmpty() || surname.isEmpty() || birthDate.isEmpty() || hireDate.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled!");
                }

                // Update the Personnel object
                Personnel updatedPersonnel = new Personnel(name, surname, personID, birthDate, hireDate, departmentID, salary);

                // Update in database
                repository.updatePersonnel(updatedPersonnel);

                // Refresh table
                loadTableData(repository.getAllPersonnel());
                view.enablePersonIDField(true); // Enable Person ID field for new entry
            } catch (Exception ex) {
                System.out.println("Error updating personnel: " + ex.getMessage());
            }
        }
    }

    // Search Personnel Listener
    class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String searchText = view.getSearchFieldText();

                if (searchText.isEmpty()) {
                    // If search field is empty, load all data
                    loadTableData(repository.getAllPersonnel());
                }
                else {
                    int searchID = Integer.parseInt(searchText);
                    // A new thread is being started by using PersonnelSearch
                    PersonnelSearch searchTask = new PersonnelSearch(searchID);
                    Thread t1 = new Thread(searchTask);
                    t1.start();
                    t1.join();

                    // Get the list and update the table
                    List<Personnel> resultList = searchTask.getPersonnelList();
                    if (!resultList.isEmpty()) {
                        loadTableData(resultList);
                    }

                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Person ID format.");
            } catch (Exception ex) {
                System.out.println("Error searching personnel: " + ex.getMessage());
            }
        }
    }
}