import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * @author ErenGulbahar
 * @version 1.0.0
 * @since 2022-02-28
 */

public class UserInterface extends Records implements AddSmt, Books
{
    /**
     * Burada ilk önce giriş paneli oluşturuyoruz. Bunu makeInterface ile yapıyoruz. Ardından kullanıcının textField'ları doldurması ile
     * yazdığı kullanıcı adı ve şifrenin kayıtlı olup olmadığına bakarak buna göre işlem yapıyoruz. Eğer ki doğruysa ve admin ile giriş yapmışsa
     * adminin görebileceği bir panele yönlendiriyoruz. Admin panelindenden addBook'a basarak kitap ekliyoruz, deleteBook'a basarak kitap çıkartıyoruz,
     * addUser'a basarak da kullanıcı ekliyoruz. Son olarak da logout'a basarak çıkış yapıyoruz.
     * Kullanıcı panelinde takeBook'a basarak kitabı ödünç alıyoruz, giveBook'a basarak da kitabı iade ediyoruz. Son olarak da logout'a basarak da çıkış yapıyoruz.
     *
     * @param addBook ile kitap ekliyoruz.
     * @param deleteBook ile kitap çıkartıyoruz.
     * @param addUser ile kullanıcı ekliyoruz.
     * @param takeBook ile kitabı ödünç alıyoruz alıyoruz.
     * @param giveBook ile kitabı geri veriyoruz.
     * @param logout ile çıkış yapıyoruz.
     * @param admin ile admin girişi için kullanıcı adı belirliyoruz.
     * @param passwordAdmin ile admin için şifre belirliyoruz.
     * @param info giriş yaparken bir yanlışlık yapıldığında hata mesajını vermemize yarıyor.
     * @param frame, adminWindow, userWindow, addBooksWindow, deleteBooksWindow, addUserWindow, takenBooks, givenBooks ile arayüzlerimizi oluşturuyoruz.
     * @param usernames sistemdeki kayıtlı kullanıcı adlarını alıyoruz.
     * @param passwords sistemdeki kayıtlı kullanıcının şifresini alıyoruz.
     * @param namess ile giriş yapan kullanıcının kullanıcı adını alarak ona özel text dosyası oluşturuyoruz.
     */

    final String admin = "admin";
    final String passwordAdmin = "admin";
    JFrame frame = new JFrame("Ödev 1");
    JFrame adminWindow = new JFrame("Admin Paneli");
    JFrame userWindow = new JFrame("Kullanıcı Paneli");
    JFrame addBooksWindow = new JFrame("Kitap Ekleme Paneli");
    JFrame deleteBooksWindow = new JFrame("Kitap Silme Paneli");
    JFrame addUserWindow = new JFrame("Kullanıcı Ekleme Paneli");
    JFrame takenBooks = new JFrame("Kitap Alma Paneli");
    JFrame givenBooks = new JFrame("Kitap Verme Paneli");

    ArrayList<String> usernames = new ArrayList<>();
    ArrayList<String> passwords = new ArrayList<>();

    ArrayList<String> namess = new ArrayList<>();

    public void makeInterface()
    {
        Label name = new Label("Kullanıcı adı");
        TextField nameField = new TextField();
        Label password = new Label("Sifre");
        TextField passwordField = new TextField();
        Button login = new Button("Giriş");
        Label info = new Label("Kullanıcı adı veya şifre hatalı girilmiştir!");

        passwordField.setEchoChar('*');

        name.setBounds(170,50,70,20);
        nameField.setBounds(160,70,100,20);
        password.setBounds(190,100,100,20);
        passwordField.setBounds(160,120,100,20);
        login.setBounds(160,150,100,20);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    FileReader reader = new FileReader("Users.txt");
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line1;

                    FileReader reader1 = new FileReader("Password.txt");
                    BufferedReader bufferedReader1 = new BufferedReader(reader1);
                    String line2;

                    while((line1=bufferedReader.readLine()) != null)
                    {
                        usernames.add(line1);
                    }

                    reader.close();

                    while((line2=bufferedReader1.readLine()) != null)
                    {
                        passwords.add(line2);
                    }

                    reader1.close();
                }

                catch (IOException exception)
                {
                    exception.printStackTrace();
                }

                for(int i=0;i<usernames.size();i++)
                {
                    if(usernames.get(i).equals(nameField.getText()) && passwords.get(i).equals(passwordField.getText()))
                    {
                        info.setVisible(false);
                        frame.dispose();

                        namess.add(0,nameField.getText());

                        nameField.setText("");
                        passwordField.setText("");

                        userPanel();
                    }

                    else
                    {
                        info.setVisible(true);
                        info.setBounds(110,180,220,20);
                        info.setBackground(Color.RED);
                    }
                }

                if(nameField.getText().equals(admin) && passwordField.getText().equals(passwordAdmin))
                {
                    info.setVisible(false);
                    frame.dispose();

                    nameField.setText("");
                    passwordField.setText("");

                    addNewSmt();
                }

                else
                {
                    info.setVisible(true);
                    info.setBounds(110,180,220,20);
                    info.setBackground(Color.RED);
                }
            }
        });

        frame.add(name);
        frame.add(nameField);
        frame.add(password);
        frame.add(passwordField);
        frame.add(login);
        frame.add(info);

        frame.setBounds(700,200,500,500);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                System.exit(1);
            }
        });

        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void addNewSmt()
    {
        Button addBook = new Button("Yeni Kitap Ekle");
        Button deleteBook = new Button("Kitap Sil");
        Button addUser = new Button("Kullanıcı Ekle");
        Button logout = new Button("Çıkış Yap");

        addBook.setBounds(180,100,100,30);
        deleteBook.setBounds(180,140,100,30);
        addUser.setBounds(180,180,100,30);
        logout.setBounds(180,220,100,30);

        addBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                adminWindow.dispose();
                addBooks();
            }
        });

        deleteBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                adminWindow.dispose();
                deleteBooks();
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                adminWindow.dispose();
                addUsers();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                adminWindow.dispose();
                frame.setVisible(true);
            }
        });

        adminWindow.setBounds(700,200,500,500);
        adminWindow.setLayout(null);
        adminWindow.setVisible(true);

        adminWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(1);
            }
        });

        adminWindow.add(addBook);
        adminWindow.add(deleteBook);
        adminWindow.add(addUser);
        adminWindow.add(logout);
    }

    @Override
    public void addBooks()
    {
        Label nameBook = new Label("Kitap İsmi");
        TextField nameField = new TextField();
        Button addBook = new Button("Ekle");
        Button cancel = new Button("İptal");

        nameBook.setBounds(200,80,70,20);
        nameField.setBounds(180,100,100,20);
        addBook.setBounds(140,130,70,20);
        cancel.setBounds(250,130,70,20);

        addBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    FileWriter writer = new FileWriter("Books.txt",true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);

                    bufferedWriter.write(nameField.getText());
                    bufferedWriter.newLine();
                    bufferedWriter.close();

                    addBooksWindow.dispose();
                    adminWindow.setVisible(true);

                    nameField.setText("");
                }

                catch (IOException exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addBooksWindow.dispose();
                adminWindow.setVisible(true);
            }
        });

        addBooksWindow.setBounds(700,200,500,500);
        addBooksWindow.setLayout(null);
        addBooksWindow.setVisible(true);

        addBooksWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(1);
            }
        });

        addBooksWindow.add(nameBook);
        addBooksWindow.add(nameField);
        addBooksWindow.add(addBook);
        addBooksWindow.add(cancel);
    }

    @Override
    public void deleteBooks()
    {
        Label info = new Label("Silinecek Kitap İsmi");
        TextField numberBook = new TextField();
        Button confirm = new Button("Onayla");
        Button refresh = new Button("Yenile");
        Button cancel = new Button("İptal");
        TextArea textArea = new TextArea();

        try
        {
            FileReader reader = new FileReader("Books.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            String text = "";

            while((line = bufferedReader.readLine()) != null)
            {
                text += line + "\n";
            }

            reader.close();

            textArea.setText(text);
        }

        catch (IOException exception)
        {
            exception.printStackTrace();
        }

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    FileReader reader = new FileReader("Books.txt");
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line;
                    String text = "";

                    while((line = bufferedReader.readLine()) != null)
                    {
                        text += line + "\n";
                    }

                    reader.close();

                    textArea.setText(text);
                }

                catch (IOException exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String nameBook = numberBook.getText();

                try
                {
                    FileReader reader1 = new FileReader("Books.txt");
                    BufferedReader bufferedReader1 = new BufferedReader(reader1);
                    String line2;
                    String text2 = "";

                    while((line2 = bufferedReader1.readLine()) != null)
                    {
                        if(!nameBook.equals(line2))
                        {
                            text2 += line2 + "\n";
                        }
                    }

                    reader1.close();

                    FileWriter writer = new FileWriter("Books.txt",false);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);

                    bufferedWriter.write(text2);
                    bufferedWriter.close();

                    textArea.setText(text2);
                    numberBook.setText("");

                    deleteBooksWindow.dispose();
                    adminWindow.setVisible(true);
                }

                catch (IOException exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                deleteBooksWindow.dispose();
                adminWindow.setVisible(true);
            }
        });

        info.setBounds(190,80,140,20);
        numberBook.setBounds(200,100,100,20);
        confirm.setBounds(120,130,80,20);
        refresh.setBounds(220,130,80,20);
        cancel.setBounds(320,130,80,20);
        textArea.setBounds(140,160,250,200);

        deleteBooksWindow.setBounds(700,200,500,500);
        deleteBooksWindow.setLayout(null);
        deleteBooksWindow.setVisible(true);

        deleteBooksWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(1);
            }
        });

        deleteBooksWindow.add(info);
        deleteBooksWindow.add(numberBook);
        deleteBooksWindow.add(textArea);
        deleteBooksWindow.add(confirm);
        deleteBooksWindow.add(refresh);
        deleteBooksWindow.add(cancel);
    }

    @Override
    public void addUsers()
    {
        try
        {
            File file = new File("Users.txt");
            file.createNewFile();
        }

        catch (IOException exception)
        {
            exception.printStackTrace();
        }

        Label username = new Label("Kullanıcı Adı");
        TextField userField = new TextField();
        Label userPassword = new Label("Sifre");
        TextField passwordField = new TextField();
        Button confirm = new Button("Ekle");
        Button cancel = new Button("İptal");
        Label info = new Label("Böyle bir kullanıcı mevcut!");

        passwordField.setEchoChar('*');

        username.setBounds(170,50,70,20);
        userField.setBounds(160,70,100,20);
        userPassword.setBounds(190,100,100,20);
        passwordField.setBounds(160,120,100,20);
        confirm.setBounds(160,150,100,20);
        cancel.setBounds(160,180,100,20);
        info.setBounds(150,180,145,20);

        info.setVisible(false);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String user = userField.getText();
                String pass = passwordField.getText();

                if(user.equals("") && pass.equals(""))
                {
                    info.setVisible(true);
                    info.setText("Kullanıcı adı veya şifre boş bırakılamaz!");
                    info.setBounds(110,180,220,20);
                    info.setBackground(Color.RED);
                }

                else
                {
                    try
                    {
                        FileReader reader = new FileReader("Users.txt");
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        String line;
                        int counter = 0;

                        while((line=bufferedReader.readLine()) != null)
                        {
                            if(line.equals(userField.getText()))
                            {
                                info.setVisible(true);
                                info.setText("Böyle bir kullanıcı mevcut!");
                                info.setBounds(150,180,145,20);
                                info.setBackground(Color.RED);

                                counter = 1;
                            }
                        }

                        reader.close();

                        if(counter == 0)
                        {
                            FileWriter username = new FileWriter("Users.txt",true);
                            BufferedWriter bufferedWriter = new BufferedWriter(username);

                            FileWriter password = new FileWriter("Password.txt",true);
                            BufferedWriter bufferedWriter1 = new BufferedWriter(password);

                            bufferedWriter.write(userField.getText());
                            bufferedWriter.newLine();
                            bufferedWriter.close();

                            bufferedWriter1.write(passwordField.getText());
                            bufferedWriter1.newLine();
                            bufferedWriter1.close();

                            addUserWindow.dispose();
                            adminWindow.setVisible(true);

                            userField.setText("");
                            passwordField.setText("");

                            info.setVisible(false);
                        }

                    }

                    catch (IOException exception)
                    {
                        exception.printStackTrace();
                    }
                }

            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addUserWindow.dispose();
                adminWindow.setVisible(true);
            }
        });

        addUserWindow.setBounds(700,200,500,500);

        addUserWindow.setLayout(null);
        addUserWindow.setVisible(true);

        addUserWindow.add(username);
        addUserWindow.add(userField);
        addUserWindow.add(userPassword);
        addUserWindow.add(passwordField);
        addUserWindow.add(confirm);
        addUserWindow.add(cancel);
        addUserWindow.add(info);
    }

    @Override
    public void userPanel()
    {
        Button takeBook = new Button("Kitap Al");
        Button giveBook = new Button("Kitap İade Et");
        Button logout = new Button("Çıkış Yap");

        takeBook.setBounds(180,100,100,30);
        giveBook.setBounds(180,140,100,30);
        logout.setBounds(180,180,100,30);

        takeBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                userWindow.dispose();
                takesBooks();
            }
        });

        giveBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                userWindow.dispose();
                givesBooks();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                userWindow.dispose();
                frame.setVisible(true);
            }
        });

        userWindow.setBounds(700,200,500,500);

        userWindow.setLayout(null);
        userWindow.setVisible(true);

        userWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                System.exit(1);
            }
        });

        userWindow.add(takeBook);
        userWindow.add(giveBook);
        userWindow.add(logout);
    }

    @Override
    public void takesBooks()
    {
        Label nameBook = new Label("Kitap İsmi Girin");
        TextField takeField = new TextField();
        Button confirm = new Button("Onayla");
        Button refresh = new Button("Yenile");
        Button cancel = new Button("İptal");
        TextArea textArea = new TextArea();

        nameBook.setBounds(225,80,140,20);
        takeField.setBounds(220,100,100,20);
        confirm.setBounds(140,130,80,20);
        textArea.setBounds(140,160,250,200);
        refresh.setBounds(250,130,80,20);
        cancel.setBounds(360,130,80,20);

        try
        {
            FileReader reader = new FileReader("Books.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line2;
            String text = "";

            while((line2=bufferedReader.readLine()) != null)
            {
                text += line2 + "\n";
            }

            textArea.setText(text);

            reader.close();
        }

        catch (IOException exception)
        {
            exception.printStackTrace();
        }

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    FileReader reader = new FileReader("Books.txt");
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line2;
                    String text = "";

                    while((line2=bufferedReader.readLine()) != null)
                    {
                        text += line2 + "\n";
                    }

                    textArea.setText(text);

                    reader.close();
                }

                catch (IOException exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String nameBook = takeField.getText();

                try
                {
                    FileReader reader = new FileReader("Books.txt");
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line;
                    String text = "";
                    String text2 = "";

                    while((line=bufferedReader.readLine()) != null)
                    {
                        if(!nameBook.equals(line))
                        {
                            text += line + "\n";
                        }

                        else
                        {
                            text2 = line;
                        }
                    }

                    reader.close();

                    String userName = namess.get(0) + ".txt";

                    FileWriter writer = new FileWriter(userName,true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);

                    FileWriter writer1 = new FileWriter("Books.txt",false);
                    BufferedWriter bufferedWriter1 = new BufferedWriter(writer1);

                    bufferedWriter.write(text2);
                    bufferedWriter.newLine();
                    bufferedWriter.close();

                    bufferedWriter1.write(text);
                    bufferedWriter1.close();

                    textArea.setText(text);

                    takenBooks.dispose();
                    userWindow.setVisible(true);

                    takeField.setText("");
                }

                catch (IOException exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                takenBooks.dispose();
                userWindow.setVisible(true);
            }
        });

        takenBooks.setBounds(700,200,500,500);

        takenBooks.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                System.exit(1);
            }
        });

        takenBooks.add(nameBook);
        takenBooks.add(takeField);
        takenBooks.add(confirm);
        takenBooks.add(textArea);
        takenBooks.add(refresh);
        takenBooks.add(cancel);

        takenBooks.setLayout(null);
        takenBooks.setVisible(true);
    }

    @Override
    public void givesBooks()
    {
        Label info = new Label("Verilecek Kitap İsmi");
        TextField nameBook = new TextField();
        Button confirm = new Button("Onayla");
        Button refresh = new Button("Yenile");
        Button cancel = new Button("İptal");
        TextArea textArea = new TextArea();

        info.setBounds(215,80,140,20);
        nameBook.setBounds(220,100,100,20);
        confirm.setBounds(140,130,80,20);
        textArea.setBounds(140,160,250,200);
        refresh.setBounds(250,130,80,20);
        cancel.setBounds(360,130,80,20);

        String userName = namess.get(0) + ".txt";

        try
        {
            FileReader reader = new FileReader(userName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            String text = "";

            while((line=bufferedReader.readLine()) != null)
            {
                text += line + "\n";
            }

            reader.close();

            textArea.setText(text);
        }

        catch (IOException exception)
        {
            exception.printStackTrace();
        }

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    FileReader reader = new FileReader(userName);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line;
                    String text = "";

                    while((line=bufferedReader.readLine()) != null)
                    {
                        text += line + "\n";
                    }

                    reader.close();

                    textArea.setText(text);
                }

                catch (IOException exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String bookText = nameBook.getText();

                try
                {
                    FileReader reader = new FileReader(userName);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line;
                    String text = "";

                    while((line=bufferedReader.readLine()) != null)
                    {
                        if(!bookText.equals(line))
                        {
                            text += line + "\n";
                        }
                    }

                    reader.close();

                    FileWriter writer = new FileWriter(userName,false);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);

                    FileWriter writer1 = new FileWriter("Books.txt",true);
                    BufferedWriter bufferedWriter1 = new BufferedWriter(writer1);

                    bufferedWriter.write(text);
                    bufferedWriter.close();

                    bufferedWriter1.write(bookText);
                    bufferedWriter1.close();

                    textArea.setText(text);

                    givenBooks.dispose();
                    userWindow.setVisible(true);

                    nameBook.setText("");
                }

                catch (IOException exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                givenBooks.dispose();
                userWindow.setVisible(true);
            }
        });

        givenBooks.setBounds(700,200,500,500);

        givenBooks.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                System.exit(1);
            }
        });

        givenBooks.setLayout(null);
        givenBooks.setVisible(true);

        givenBooks.add(info);
        givenBooks.add(nameBook);
        givenBooks.add(confirm);
        givenBooks.add(textArea);
        givenBooks.add(refresh);
        givenBooks.add(cancel);
    }
}