package vu_lib;

// Import necessary classes
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;

public class VU_LIBRARY {

    // Declare main frame and components
    private JFrame VU_APP_FRAME;
    private JTable dataViewTable;
    private JTextField bookIdTextField;
    private JTextField bookTitleTextField;
    private JTextField bookAuthorTextField;
    private JTextField bookYearTextField;

    // Database connection object
    private Connection connection;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        // Run the GUI creation in the Event Dispatch Thread
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VU_LIBRARY window = new VU_LIBRARY();
                    window.VU_APP_FRAME.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the appl ication.
     */
    public VU_LIBRARY() {
        // Initialize the GUI components and connect to the database
        initialize();
        connectToDatabase();
        refreshTable();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        // Initialize the main frame
        VU_APP_FRAME = new JFrame();
        VU_APP_FRAME.setTitle("VU LIBRARY");
        VU_APP_FRAME.setResizable(false);
        VU_APP_FRAME.setBounds(100, 100, 947, 654);
        VU_APP_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VU_APP_FRAME.getContentPane().setLayout(null);

        // Create and configure panels
        JPanel panel = new JPanel();
        panel.setBounds(10, 11, 913, 255);
        VU_APP_FRAME.getContentPane().add(panel);
        panel.setLayout(new GridLayout(0, 1, 0, 0));

        JPanel panel_2 = new JPanel();
        panel.add(panel_2);

        // Add "BOOK ID" label and text field
        JLabel lblNewLabel = new JLabel("BOOK ID");
        lblNewLabel.setBounds(109, 6, 158, 44);
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        panel_2.setLayout(null);
        panel_2.add(lblNewLabel);

        bookIdTextField = new JTextField();
        bookIdTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    bookTitleTextField.requestFocus();
                }
            }
        });
        bookIdTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bookIdTextField.setBounds(291, 11, 518, 39);
        panel_2.add(bookIdTextField);
        bookIdTextField.setColumns(10);

        // Add "BOOK TITLE" label and text field
        JPanel panel_2_1 = new JPanel();
        panel.add(panel_2_1);
        panel_2_1.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("BOOK TITLE");
        lblNewLabel_1.setBounds(107, 11, 158, 44);
        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 20));
        panel_2_1.add(lblNewLabel_1);

        bookTitleTextField = new JTextField();
        bookTitleTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    bookAuthorTextField.requestFocus();
                }
            }
        });
        bookTitleTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bookTitleTextField.setColumns(10);
        bookTitleTextField.setBounds(293, 11, 516, 39);
        panel_2_1.add(bookTitleTextField);

        // Add "BOOK AUTHOR" label and text field
        JPanel panel_2_2 = new JPanel();
        panel.add(panel_2_2);
        panel_2_2.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("BOOK AUTHOR");
        lblNewLabel_2.setBounds(110, 11, 158, 44);
        lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 20));
        panel_2_2.add(lblNewLabel_2);

        bookAuthorTextField = new JTextField();
        bookAuthorTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    bookYearTextField.requestFocus();
                }
            }
        });
        bookAuthorTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bookAuthorTextField.setColumns(10);
        bookAuthorTextField.setBounds(292, 11, 517, 39);
        panel_2_2.add(bookAuthorTextField);

        // Add "YEAR" label and text field
        JPanel panel_2_3 = new JPanel();
        panel.add(panel_2_3);
        panel_2_3.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("YEAR");
        lblNewLabel_3.setBounds(113, 11, 158, 44);
        lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 20));
        panel_2_3.add(lblNewLabel_3);

        bookYearTextField = new JTextField();
        bookYearTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bookYearTextField.setColumns(10);
        bookYearTextField.setBounds(293, 11, 516, 39);
        panel_2_3.add(bookYearTextField);

        // Configure data view table
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 341, 913, 265);
        VU_APP_FRAME.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        dataViewTable = new JTable();
        dataViewTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
        dataViewTable.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"BOOK ID", "BOOK TITLE", "BOOK AUTHOR", "YEAR"}
        ));
        JScrollPane scrollPane = new JScrollPane(dataViewTable);
        scrollPane.setBounds(10, 11, 893, 243);
        panel_1.add(scrollPane);

        // Configure control buttons
        JPanel panel_1_1 = new JPanel();
        panel_1_1.setBounds(10, 277, 913, 53);
        VU_APP_FRAME.getContentPane().add(panel_1_1);
        panel_1_1.setLayout(null);

        JButton addBookButton = new JButton("ADD NEW BOOK");
        addBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        addBookButton.setBackground(new Color(50, 205, 50));
        addBookButton.setForeground(new Color(255, 255, 255));
        addBookButton.setFont(new Font("Dialog", Font.BOLD, 20));
        addBookButton.setBounds(10, 0, 219, 53);
        panel_1_1.add(addBookButton);

        JButton deleteBookButton = new JButton("DELETE SELECTED BOOK");
        deleteBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedBook();
            }
        });
        deleteBookButton.setBackground(new Color(255, 0, 0));
        deleteBookButton.setForeground(new Color(255, 255, 255));
        deleteBookButton.setFont(new Font("Dialog", Font.BOLD, 20));
        deleteBookButton.setBounds(317, 0, 277, 53);
        panel_1_1.add(deleteBookButton);

        JButton refreshButton = new JButton("REFRESH TABLE");
        refreshButton.setBackground(new Color(0, 0, 255));
        refreshButton.setForeground(new Color(255, 255, 255));
        refreshButton.setFont(new Font("Dialog", Font.BOLD, 20));
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        refreshButton.setBounds(684, 0, 219, 53);
        panel_1_1.add(refreshButton);
    }

    /**
     * Connect to the database.
     */
    private void connectToDatabase() {
        try {
            // Load the JDBC-ODBC bridge driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            // Establish the connection
            connection = DriverManager.getConnection("jdbc:ucanaccess://C://Users/ssebb/eclipse-workspace/VU_LIBRARY/src/VU_LIB.accdb");
        } catch (Exception e) {
            e.printStackTrace();
            // Show error message if connection fails
            JOptionPane.showMessageDialog(VU_APP_FRAME, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Add a new book to the database.
     */
    private void addBook() {
        // Get input values from text fields
        String bookId = bookIdTextField.getText();
        String bookTitle = bookTitleTextField.getText();
        String bookAuthor = bookAuthorTextField.getText();
        String bookYear = bookYearTextField.getText();

        // Validate that no fields are empty
        if (bookId.isEmpty() || bookTitle.isEmpty() || bookAuthor.isEmpty() || bookYear.isEmpty()) {
            JOptionPane.showMessageDialog(VU_APP_FRAME, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate that Book ID is a number
        try {
            Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(VU_APP_FRAME, "Book ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate that Year is a number
        try {
            Integer.parseInt(bookYear);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(VU_APP_FRAME, "Year must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert the new book into the database
        try {
            String query = "INSERT INTO BOOKS (ID, TITLE, AUTHOR, YEAR) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, bookId);
            pst.setString(2, bookTitle);
            pst.setString(3, bookAuthor);
            pst.setString(4, bookYear);
            pst.executeUpdate();
            // Show success message and refresh table
            JOptionPane.showMessageDialog(VU_APP_FRAME, "Book added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
            // Show error message if insertion fails
            JOptionPane.showMessageDialog(VU_APP_FRAME, "Failed to add the book.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delete the selected book from the database.
     */
    private void deleteSelectedBook() {
        // Get the selected row from the table
        int selectedRow = dataViewTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(VU_APP_FRAME, "No book selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the book ID of the selected row
        String bookId = dataViewTable.getValueAt(selectedRow, 0).toString();

        // Delete the book from the database
        try {
            String query = "DELETE FROM BOOKS WHERE ID = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, bookId);
            pst.executeUpdate();
            // Show success message and refresh table
            JOptionPane.showMessageDialog(VU_APP_FRAME, "Book deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
            // Show error message if deletion fails
            JOptionPane.showMessageDialog(VU_APP_FRAME, "Failed to delete the book.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Refresh the table to show the latest data.
     */
    private void refreshTable() {
        // Get the table model and clear existing data
        DefaultTableModel model = (DefaultTableModel) dataViewTable.getModel();
        model.setRowCount(0);

        // Fetch data from the database and add to the table
        try {
            String query = "SELECT * FROM BOOKS";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("ID"), rs.getString("Title"), rs.getString("Author"), rs.getString("Year")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Show error message if data fetch fails
            JOptionPane.showMessageDialog(VU_APP_FRAME, "Failed to refresh the table.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
