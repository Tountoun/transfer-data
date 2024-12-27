package com.gofar.batch_demo.config;

import com.gofar.batch_demo.model.DataRow;
import org.apache.poi.ss.usermodel.*;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe en charge de la lecture des informations depuis un fichier excel
 */
public class ExcelItemReader implements ItemReader<DataRow> {

    private final Resource resource;
    private Workbook workbook;
    private Sheet sheet;
    private int rowNumber = 1;
    public static final String NUM = "NUMERIC";

    public ExcelItemReader(Resource resource) {
        this.resource = resource;
    }

    @Override
    public DataRow read() throws IOException {
        if (Objects.isNull(this.workbook))
            init();
        Row row = this.sheet.getRow(this.rowNumber);
        if (Objects.isNull(row))
            return null;

        DataRow dataRow = new DataRow();
        dataRow.setClientCode(getCellValueAsString(row.getCell(0)));
        dataRow.setClientName(getCellValueAsString(row.getCell(1)));
        dataRow.setClientEmail(getCellValueAsString(row.getCell(2)));
        dataRow.setClientAddress(getCellValueAsString(row.getCell(3)));
        dataRow.setOrderNumber(getCellValueAsString(row.getCell(4)));
        dataRow.setOrderDate(getCellValueAsLocalDate(row.getCell(5)));
        dataRow.setProductCode(getCellValueAsString(row.getCell(6)));
        dataRow.setProductName(getCellValueAsString(row.getCell(7)));
        dataRow.setQuantity(getCellValueAsInteger(row.getCell(8)));
        dataRow.setUnitPrice(getCellValueAsDouble(row.getCell(9)));

        this.rowNumber++;
        return dataRow;
    }

    private void init() throws IOException {
        InputStream stream = this.resource.getInputStream();
        workbook = WorkbookFactory.create(stream);
        sheet = workbook.getSheetAt(0);
    }

    private String getCellValueAsString(Cell cell) {
        if (Objects.isNull(cell))
            return null;
        if (cell.getCellType().name().equals("STRING")) {
            return cell.getStringCellValue();
        }
        return null;
    }

    private Integer getCellValueAsInteger(Cell cell) {
        if (Objects.isNull(cell))
            return null;
        if (cell.getCellType().name().equals(NUM)) {
            return (int) cell.getNumericCellValue();
        }
        return null;
    }

    private Double getCellValueAsDouble(Cell cell) {
        if (Objects.isNull(cell))
            return null;
        if (cell.getCellType().name().equals("STRING")) {
            return Double.valueOf(cell.getStringCellValue());
        }
        return null;
    }

    private LocalDate getCellValueAsLocalDate(Cell cell) {
        if (Objects.isNull(cell))
            return null;
        if (cell.getCellType().name().equals(NUM) && DateUtil.isCellDateFormatted(cell))
                return cell.getLocalDateTimeCellValue().toLocalDate();

        return null;
    }
}
