package tests;

import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.codeborne.pdftest.PDF;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import static org.junit.jupiter.api.Assertions.*;

public class FileTests {
    private final ClassLoader classLoader = FileTests.class.getClassLoader();

    @Test
    @DisplayName("Проверяем PDF файл из ZIP архива")
    public void readPDFFileFromZipArchiveTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("TestArchive.zip")) {
            assert inputStream != null;
            try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
                ZipEntry zipEntry;
                while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                    if (zipEntry.getName().endsWith(".pdf")) {
                        PDF pdf = new PDF(zipInputStream);
                        String fileName = zipEntry.getName();
                        assertEquals("PrimerPDF.pdf", fileName);
                        // в моём файле нет метаданных в title, поэтому имя файла читаю так
                        assertEquals(1, pdf.numberOfPages);
                        assertTrue(pdf.text.contains("Пример PDF файла"));
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Проверяем XLSX файл из ZIP архива")
    public void readXLSXFileFromZipArchivTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("TestArchive.zip")) {
            assert inputStream != null;
            try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
                ZipEntry zipEntry;
                while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                    if (zipEntry.getName().endsWith(".xlsx")) {
                        XLS xls = new XLS(zipInputStream);
                        assertEquals("example.xlsx", zipEntry.getName());
                        assertEquals("Тестовое значение", xls.excel.getSheetAt(0).getRow(5).getCell(4).getStringCellValue());
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Проверяем CSV файл из ZIP архива")
    public void readCSVFileFromZipArchiveTest() throws Exception {

        List<String[]> dataToCompare = Arrays.asList(
                new String[]{"Yandex", "https://ya.ru"},
                new String[]{"Wikipedia", "https://wikipedia.org"}
        );
        try (InputStream inputStream = classLoader.getResourceAsStream("TestArchive.zip")) {
            assert inputStream != null;
            try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
                ZipEntry zipEntry;
                while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                    if (zipEntry.getName().endsWith(".csv")) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zipInputStream));
                        String fileName = zipEntry.getName();
                        assertEquals("example.csv", fileName);
                        List<String[]> allData = csvReader.readAll();
                        assertEquals(dataToCompare.size(), allData.size());
                        assertEquals("Yandex", allData.get(0)[0]);
                    }
                }
            }
        }
    }
    @Test
    @DisplayName("Проверяем JSON файл")
    public void readJSONWithJacksonTest() throws Exception {
        File json = new File("src/test/resources/widget.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readValue(json, JsonNode.class);
        assertEquals("orange", jsonNode.get("color").asText());
        assertTrue(jsonNode.get("isPaid").asBoolean());
        assertEquals("left", jsonNode.get("place").asText());
        assertEquals(100,jsonNode.get("height").asInt());
    }
}

