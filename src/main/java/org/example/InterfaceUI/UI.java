package org.example.InterfaceUI;
import jakarta.persistence.NoResultException;
import org.example.Entity.Weather;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static java.awt.AWTEventMulticaster.add;

public class UI extends JFrame{
    private JButton findButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextArea dataTextArea;
    private  JPanel buttonPanel = new JPanel();

    public UI() {



            // Устанавливаем настройки окна
            setTitle("Weather App");
            buttonPanel.setLayout(null);

            // Создаем кнопки
            findButton = new JButton("Найти город");
            addButton = new JButton("Добавить город");
            editButton = new JButton("Редактировать информацию о городе");
            deleteButton = new JButton("Удалить город");
            findButton.setSize(150,30);
            addButton.setSize(150,30);
            editButton.setSize(300,30);
            deleteButton.setSize(150,30);

            findButton.setLocation(100,30);
            addButton.setLocation(260,30);
            editButton.setLocation(420,30);
            deleteButton.setLocation(730,30);
            // Добавляем кнопки на главную панель окна
            buttonPanel.add(findButton);
            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);

            dataTextArea = new JTextArea();

            dataTextArea.setEditable(false);

            // Размещаем JTextArea внутри JScrollPane для прокрутки содержимого
            JScrollPane scrollPane = new JScrollPane(dataTextArea);
            scrollPane.setLocation(0,100);
            scrollPane.setSize(1000,620);
            buttonPanel.add(scrollPane);
            // Добавляем JScrollPane в главное окно



            setContentPane(buttonPanel);
            setSize(1000,720);
            // Вызываем метод для обновления данных в JTextArea


            // Добавляем обработчики событий для кнопок
            findButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openFindCityWindow();
                }
            });

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openAddCityWindow();
                }
            });

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openEditCityWindow();
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openDeleteCityWindow();
                }
            });
        }
            private void openFindCityWindow () {
            JFrame findCityFrame = new JFrame("Найти город");
            findCityFrame.setSize(300, 200);
            findCityFrame.setLayout(new BorderLayout());

            JPanel inputPanel = new JPanel(new GridLayout(1, 2));
            JLabel cityNameLabel = new JLabel("Название города:");
            JTextField cityNameField = new JTextField();
            inputPanel.add(cityNameLabel);
            inputPanel.add(cityNameField);

            JButton searchButton = new JButton("Поиск");
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String Name = cityNameField.getText();
                    //  Поиск города
                    try (SessionFactory factory= new Configuration()
                            .configure()
                            .addAnnotatedClass(Weather.class)
                            .buildSessionFactory();
                    ) {
                        Session session = factory.getCurrentSession();
                        session.beginTransaction();
                        Weather weatherEntity = (Weather) session.createQuery("from Weather where cityName ="+"'"+Name+"'").getSingleResultOrNull();
                        if (weatherEntity != null){
                            JOptionPane.showMessageDialog(null,weatherEntity + "\nЗаглядывай почаеще! (◕‿◕)");
                        }
                        else {
                            JOptionPane.showMessageDialog(null,"К сожалению, я ничегошеньки не нашел! (╥﹏╥)");
                        }

                        session.getTransaction().commit();
                    }
                    catch (Exception exception){
                        JOptionPane.showMessageDialog(null, "Упс, я упал! ╚(ಠ_ಠ)=┐\n" + exception.getMessage());
                    }

                }
            });

            findCityFrame.add(inputPanel, BorderLayout.CENTER);
            findCityFrame.add(searchButton, BorderLayout.SOUTH);
            findCityFrame.setVisible(true);
        }

            private void openAddCityWindow() {
                JFrame addCityFrame = new JFrame("Добавить город");
                addCityFrame.setSize(300, 200);
                addCityFrame.setLayout(new BorderLayout());

                JPanel inputPanel = new JPanel(new GridLayout(6, 2));
                JLabel cityNameLabel = new JLabel("Название города:");
                JTextField cityNameField = new JTextField();
                JLabel weatherLabel = new JLabel("Погода:");
                JTextField weatherField = new JTextField();
                JLabel temperatureLabel = new JLabel("Температура воздуха:");
                JTextField temperatureField = new JTextField();
                JLabel windDirectionLabel = new JLabel("Направление ветра:");
                JTextField windDirectionField = new JTextField();
                JLabel windSpeedLabel = new JLabel("Скорость ветра:");
                JTextField windSpeedField = new JTextField();
                JLabel atmosphericLabel = new JLabel("Атмосферное давление:");
                JTextField atmosphericField = new JTextField();

                inputPanel.add(cityNameLabel);
                inputPanel.add(cityNameField);
                inputPanel.add(weatherLabel);
                inputPanel.add(weatherField);
                inputPanel.add(temperatureLabel);
                inputPanel.add(temperatureField);
                inputPanel.add(windDirectionLabel);
                inputPanel.add(windDirectionField);
                inputPanel.add(windSpeedLabel);
                inputPanel.add(windSpeedField);
                inputPanel.add(atmosphericLabel);
                inputPanel.add(atmosphericField);

                JButton addButton = new JButton("Добавить");
                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {


                        try (SessionFactory factory= new Configuration()
                                .configure()
                                .addAnnotatedClass(Weather.class)
                                .buildSessionFactory();

                        ) {
                            String Name = cityNameField.getText();
                            String weather = weatherField.getText();
                            int temperature = Integer.parseInt(temperatureField.getText());
                            String windDirection = windDirectionField.getText();
                            int windSpeed = Integer.parseInt(windSpeedField.getText());
                            int atmospheric = Integer.parseInt(atmosphericField.getText());
                            // добавление города
                            Session session = factory.getCurrentSession();
                            session.beginTransaction();
                            Weather weather1 = (Weather) session.createQuery("from Weather where cityName ="+"'"+Name+"'").getSingleResultOrNull();
                            if (weather1 == null) {
                                Weather weatherEntity = new Weather(Name, weather, temperature, windSpeed, windDirection, atmospheric);
                                session.persist(weatherEntity);
                           }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Опа ,даже не думай , такой город уже есть, я все вижу!! (⌐■_■)");
                            }
                            session.getTransaction().commit();
                            JOptionPane.showMessageDialog(null, "Наша база расширяется, УРА еще больше работы! (o˘◡˘o)");
                        }
                        catch (NumberFormatException exception){
                            JOptionPane.showMessageDialog(null, "Так-с, буковки с циферками не путаем и пробуем еще раз!! (͡° ͜ʖ ͡°)");
                        }
                        catch (Exception exception){
                            JOptionPane.showMessageDialog(null, "Упс, я упал! ╚(ಠ_ಠ)=┐\n" + exception.getMessage());
                        }

                    }
                });

                addCityFrame.add(inputPanel, BorderLayout.CENTER);
                addCityFrame.add(addButton, BorderLayout.SOUTH);
                addCityFrame.setVisible(true);
            }

            private void openEditCityWindow() {
                        String enteredText = JOptionPane.showInputDialog(null, "Только заснул! ლ(ಠ益ಠლ) \n Чего изволите найти? (⊙_◎)");
                        String nullPointer = "";

                        if (enteredText!=null ) {
                            if (!enteredText.equals(nullPointer)) {
                                try (SessionFactory factory = new Configuration()
                                        .configure("hibernate.cfg.xml")
                                        .addAnnotatedClass(Weather.class)
                                        .buildSessionFactory();
                                     Session session = factory.getCurrentSession();
                                ) {

                                    JFrame editCityFrame = new JFrame("Редактировать информацию о городе");
                                    editCityFrame.setSize(400, 400);
                                    editCityFrame.setLayout(new BorderLayout());
                                    session.beginTransaction();

                                    Weather weatherEntity = (Weather) session.createQuery("from Weather where cityName = " +"'"+ enteredText+"'").getSingleResult();
                                    System.out.println(weatherEntity);

                                    JPanel inputPanel = new JPanel(new GridLayout(6, 2));
                                    JLabel cityNameLabel = new JLabel("Название города:");
                                    JTextField cityNameField = new JTextField(weatherEntity.getCityName());
                                    JLabel weatherLabel = new JLabel("Погода:");
                                    JTextField weatherField = new JTextField(weatherEntity.getWeatherStatus());
                                    JLabel temperatureLabel = new JLabel("Температура воздуха:");
                                    JTextField temperatureField = new JTextField(String.valueOf(weatherEntity.getTemprature()));
                                    JLabel windDirectionLabel = new JLabel("Направление ветра:");
                                    JTextField windDirectionField = new JTextField(weatherEntity.getWindDirection());
                                    JLabel windSpeedLabel = new JLabel("Скорость ветра:");
                                    JTextField windSpeedField = new JTextField(String.valueOf(weatherEntity.getWindSpeed()));
                                    JLabel atmosphericLabel = new JLabel("Атмосферное давление:");
                                    JTextField atmosphericField = new JTextField(String.valueOf(weatherEntity.getAtmospheric()));

                                    inputPanel.add(cityNameLabel);
                                    inputPanel.add(cityNameField);
                                    inputPanel.add(weatherLabel);
                                    inputPanel.add(weatherField);
                                    inputPanel.add(temperatureLabel);
                                    inputPanel.add(temperatureField);
                                    inputPanel.add(windDirectionLabel);
                                    inputPanel.add(windDirectionField);
                                    inputPanel.add(windSpeedLabel);
                                    inputPanel.add(windSpeedField);
                                    inputPanel.add(atmosphericLabel);
                                    inputPanel.add(atmosphericField);

                                    //      Обработка изменения данных
                                    JButton editButton = new JButton("Редактировать");
                                    editButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {

                                            try (SessionFactory factory = new Configuration()
                                                    .configure("hibernate.cfg.xml")
                                                    .addAnnotatedClass(Weather.class)
                                                    .buildSessionFactory();
                                                 Session session = factory.getCurrentSession();
                                            ) {

                                                session.beginTransaction();
                                                String cityName = cityNameField.getText();
                                                String weather = weatherField.getText();
                                                int temperature = Integer.parseInt(temperatureField.getText());
                                                String windDirection = windDirectionField.getText();
                                                int windSpeed = Integer.parseInt(windSpeedField.getText());
                                                int atmospheric = Integer.parseInt(atmosphericField.getText());

                                                Weather updateWeather = session.get(Weather.class, weatherEntity.getId());
                                                Weather weatherName = (Weather) session.createQuery("from Weather where cityName = " +"'"+ enteredText+"'").getSingleResultOrNull();
                                                if (weatherName !=null) {
                                                    updateWeather.setCityName(cityName);
                                                }
                                                else {
                                                    JOptionPane.showMessageDialog(null, "Опа ,даже не думай , такой город уже есть, я все вижу!!\n p.s.Обновится все, кроме названия города (⌐■_■)");
                                                }
                                                updateWeather.setWeatherStatus(weather);
                                                updateWeather.setTemprature(temperature);
                                                updateWeather.setWindSpeed(windSpeed);
                                                updateWeather.setWindDirection(windDirection);
                                                updateWeather.setAtmospheric(atmospheric);
                                                session.getTransaction().commit();
                                                JOptionPane.showMessageDialog(null, "Опа и данные обновились,магия!!! (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ ");
                                            }
                                            catch (NumberFormatException exception){
                                                JOptionPane.showMessageDialog(null, "Так-с, буковки с циферками не путаем и пробуем еще раз!! (͡° ͜ʖ ͡°)");
                                            }
                                            catch (Exception exception) {
                                                JOptionPane.showMessageDialog(null, "Упс, я упал! ╚(ಠ_ಠ)=┐\n" + exception.getMessage());
                                            }


                                        }
                                    });

                                    session.getTransaction().commit();

                                    editCityFrame.add(inputPanel, BorderLayout.CENTER);
                                    editCityFrame.add(editButton, BorderLayout.SOUTH);
                                    editCityFrame.setVisible(true);

                                }

                                catch (NoResultException e)
                                {
                                    JOptionPane.showMessageDialog(null, "Попробуйте целиться по клавишам лучше , такого города нема! (¬‿¬)");
                                }
                                catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, "Упс, я упал! ╚(ಠ_ಠ)=┐\n" + e.getMessage());
                                }


                            }
                        }

            }

            private void openDeleteCityWindow() {
        JFrame deleteCityFrame = new JFrame("Удалить город");
        deleteCityFrame.setSize(300, 200);
        deleteCityFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        JLabel cityNameLabel = new JLabel("Какой город сотрем с лица земли сегодня? (⊙_◎)");
        JTextField cityNameField = new JTextField();
        inputPanel.add(cityNameLabel);
        inputPanel.add(cityNameField);

        JButton deleteButton = new JButton("Boooom!\uD83D\uDCA5");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = cityNameField.getText();
                // Обработать удаление города с указанным названием
                try (SessionFactory factory= new Configuration()
                        .configure()
                        .addAnnotatedClass(Weather.class)
                        .buildSessionFactory();
                     Session session = factory.getCurrentSession()
                ) {

                    session.beginTransaction();
                    Weather weatherEntity = (Weather) session.createQuery("from Weather where cityName = " +"'"+ name+"'").getSingleResult();
                    session.remove(weatherEntity);

                    session.getTransaction().commit();
                    JOptionPane.showMessageDialog(null,"Город "+name+" стерт с лица земли! (◣_◢)");
                }
                catch (NoResultException exception){
                    JOptionPane.showMessageDialog(null, "Попробуйте целиться по клавишам лучше , такого города нема! (¬‿¬)");
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(null, "Упс, я упал! ╚(ಠ_ಠ)=┐\n" + exception.getMessage());
                }
            }
        });

        deleteCityFrame.add(inputPanel, BorderLayout.CENTER);
        deleteCityFrame.add(deleteButton, BorderLayout.SOUTH);
        deleteCityFrame.setVisible(true);
    }


    }


