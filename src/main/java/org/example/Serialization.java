package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Serialization {
    private ArrayList<Program> programs;
    private String fileName;

    public Serialization(ArrayList<Program> programs, String fileName) {
        this.programs = programs;
        this.fileName = fileName;
    }

    // Статический метод для сохранения списка программ в Excel файл
    public static void saveToExcel(ArrayList<Program> programs, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("тв-программы");

        // Создание заголовка таблицы
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Канал");
        headerRow.createCell(1).setCellValue("Время");
        headerRow.createCell(2).setCellValue("Название");

        // Заполнение таблицы данными
        int rowNum = 1;
        for (Program program : programs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(program.getChannel());
            row.createCell(1).setCellValue(program.getTime().toString());
            row.createCell(2).setCellValue(program.getName());
        }

        // Автоматическое изменение ширины столбцов для корректного отображения данных
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Запись данных в файл
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        }

        // Вывод сообщения о том, что данные сохранены
        System.out.println("\nДанные сохранены в файл " + fileName);
    }
}