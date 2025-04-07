package org.fawry.reportapi.service;

import org.fawry.reportapi.model.Cycle;
import org.fawry.reportapi.model.Kpi;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class ExcelReportService {

    public byte[] generateExcel(Cycle cycle) throws IOException {
        if (cycle == null) {
            throw new IllegalArgumentException("Cycle data is required");
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cycle Report");
        int rowIndex = 0;

        // Create header row
        Row header = sheet.createRow(rowIndex++);
        createStyledCell(header, 0, "Cycle Name", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), false);
        createStyledCell(header, 1, "Start Date", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), false);
        createStyledCell(header, 2, "End Date", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), false);
        createStyledCell(header, 3, "Status", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), false);
        createStyledCell(header, 4, "KPI", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), true);

        // Create data row
        Row row = sheet.createRow(rowIndex++);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        createStyledCell(row, 0, cycle.getName() != null ? cycle.getName() : "N/A", workbook, false, IndexedColors.WHITE.getIndex(), false);
        createStyledCell(row, 1, cycle.getStartDate() != null ? cycle.getStartDate().format(dateFormatter) : "N/A", workbook, false, IndexedColors.WHITE.getIndex(), false);
        createStyledCell(row, 2, cycle.getEndDate() != null ? cycle.getEndDate().format(dateFormatter) : "N/A", workbook, false, IndexedColors.WHITE.getIndex(), false);
        createStyledCell(row, 3, cycle.getState() != null ? cycle.getState() : "N/A", workbook, false, IndexedColors.WHITE.getIndex(), false);

        StringBuilder kpis = new StringBuilder();
        if (cycle.getKpis() != null && !cycle.getKpis().isEmpty()) {
            for (Kpi kpi : cycle.getKpis()) {
                if (kpis.length() > 0) {
                    kpis.append("\n");  // Use newline character to separate each KPI
                }
                kpis.append(kpi.getName());
            }
        } else {
            kpis.append("No KPIs available");
        }
        createStyledCell(row, 4, kpis.toString(), workbook, false, IndexedColors.WHITE.getIndex(), true);

        // Auto size other columns (excluding KPI column)
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        // Set specific column width for KPI column and enable wrap text only for the KPI column
        sheet.setColumnWidth(4, 6000);  // Set a wider column width for the KPI column
        sheet.getRow(0).getCell(4).getCellStyle().setWrapText(true);  // Enable wrap text for KPI column only

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    private void createStyledCell(Row row, int columnIndex, String value, Workbook workbook, boolean isHeader, short bgColorIndex, boolean wrapText) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        // Apply bold font if it's a header
        if (isHeader) {
            font.setBold(true);
            style.setFont(font);
            style.setFillForegroundColor(bgColorIndex);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        } else {
            font.setBold(false);
            style.setFont(font);
        }

        // Apply borders to all cells
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // Center align text
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Enable wrap text for KPI column
        if (wrapText) {
            style.setWrapText(true);
        }

        cell.setCellStyle(style);
    }
}
