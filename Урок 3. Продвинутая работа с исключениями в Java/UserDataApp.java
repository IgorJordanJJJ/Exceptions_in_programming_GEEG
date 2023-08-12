import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество датарождения номертелефона пол");
        String input = scanner.nextLine();
        scanner.close();

        String[] data = input.split("\\s+");
        if (data.length != 6) {
            System.err.println("Ошибка: введено недостаточно данных");
            return;
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String birthDate = data[3];
        String phoneNumber = data[4];
        String gender = data[5];

        try {
            validateData(birthDate, phoneNumber, gender);
            String output = lastName + " " + firstName + " " + middleName + " " + birthDate + " " + phoneNumber + " " + gender;
            writeToFile(lastName, output);
            System.out.println("Данные успешно записаны в файл.");
        } catch (IllegalArgumentException | ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void validateData(String birthDate, String phoneNumber, String gender) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(birthDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Ошибка: некорректный формат даты рождения");
        }

        if (!gender.equals("f") && !gender.equals("m")) {
            throw new IllegalArgumentException("Ошибка: некорректный пол");
        }

        if (!phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Ошибка: некорректный формат номера телефона");
        }
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        try (FileWriter writer = new FileWriter(fileName + ".txt", true)) {
            writer.write(content + System.lineSeparator());
        }
    }
}