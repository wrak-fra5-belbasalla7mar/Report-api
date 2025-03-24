package org.fawry.reportapi.service;

import org.fawry.reportapi.model.Cycle;
import org.fawry.reportapi.model.Kpi;
import org.fawry.reportapi.model.Objective;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ExcelReportService {

    public byte[] generateExcel(Cycle cycle) throws IOException {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Evaluation Report");
            int rowIndex =0;

            Row header = sheet.createRow(rowIndex++);
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Start Date");
            header.createCell(3).setCellValue("End Date");
            header.createCell(4).setCellValue("State");
            header.createCell(5).setCellValue("Deadline");


            Row row = sheet.createRow(rowIndex++);
            row.createCell(1).setCellValue(cycle.getName());
            row.createCell(2).setCellValue(cycle.getStartDate().toString());
            row.createCell(3).setCellValue(cycle.getEndDate().toString());
            row.createCell(4).setCellValue(cycle.getState());


            sheet.createRow(rowIndex++).createCell(0).setCellValue("KPIs:");
            for (Kpi kpi : cycle.getKpis()) {
                Row kpiRow = sheet.createRow(rowIndex++);
                kpiRow.createCell(1).setCellValue(kpi.getName());
            }

            rowIndex++;
            sheet.createRow(rowIndex++).createCell(0).setCellValue("Objectives:");
            for (Objective objective : cycle.getObjectives()) {
                Row objRow = sheet.createRow(rowIndex++);
                objRow.createCell(1).setCellValue(objective.getState());
               // objRow.createCell(2).setCellValue(objective.getAssignedUserId());
                objRow.createCell(3).setCellValue(objective.getTitle());
                objRow.createCell(4).setCellValue(objective.getDescription());
                objRow.createCell(5).setCellValue(objective.getDeadline().toString());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
    }
}

