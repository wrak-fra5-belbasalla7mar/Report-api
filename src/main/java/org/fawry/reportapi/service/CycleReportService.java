package org.fawry.reportapi.service;

import org.apache.poi.ss.util.CellRangeAddress;
import org.fawry.reportapi.model.Cycle;
import org.fawry.reportapi.model.Kpi;
import org.fawry.reportapi.model.Team;
import org.fawry.reportapi.model.User;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class CycleReportService {

    private final EvaluationService evaluationService;
    private final TeamReportService teamReportService;

    public CycleReportService(EvaluationService evaluationService, TeamReportService teamReportService) {
        this.evaluationService = evaluationService;
        this.teamReportService = teamReportService;
    }

    public Map<String, Map<String, Double>> getTeamMemberRatings(Long cycleId) {
        Map<Long, Double> avgRatings = evaluationService.avgRating(cycleId);
        Map<String, Map<String, Double>> teamMemberRatings = new HashMap<>();

        avgRatings.forEach((userId, rating) -> {
            User user = evaluationService.getUserById(userId);
            Team team = teamReportService.getTeamByMemberId(userId);

            if (team != null && user != null) {
                String teamName = team.getTeamName();

                teamMemberRatings.putIfAbsent(teamName, new HashMap<>());
                teamMemberRatings.get(teamName).put(user.getName(), rating);
            }
        });
        return teamMemberRatings;
    }

    public byte[] generateExcel(Cycle cycle) throws IOException {
        if (cycle == null) {
            throw new IllegalArgumentException("Cycle data is required");
        }

        Map<String, Map<String, Double>> teamMemberRatings = getTeamMemberRatings(cycle.getId());

        System.out.println(teamMemberRatings.toString());
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cycle Report");
        int rowIndex = 0;


        Row header = sheet.createRow(rowIndex++);
        Cell headerCell = header.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        headerCell.setCellValue(cycle.getName() != null ? cycle.getName() : "N/A");


        CellStyle headerStyle = createCellStyle(workbook, true, IndexedColors.GREY_25_PERCENT.getIndex());
        headerCell.setCellStyle(headerStyle);


        rowIndex++;


        Row row = sheet.createRow(rowIndex++);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        createStyledCell(row, 0, "Start Date", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), false);
        createStyledCell(row, 1, "End Date", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), false);
        createStyledCell(row, 2, "Status", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), false);
        createStyledCell(row, 3, "KPI", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), true);

        Row cycleRow = sheet.createRow(rowIndex++);
        createStyledCell(cycleRow, 0, cycle.getStartDate() != null ? cycle.getStartDate().format(dateFormatter) : "N/A", workbook, false, IndexedColors.WHITE.getIndex(), false);
        createStyledCell(cycleRow, 1, cycle.getEndDate() != null ? cycle.getEndDate().format(dateFormatter) : "N/A", workbook, false, IndexedColors.WHITE.getIndex(), false);
        createStyledCell(cycleRow, 2, cycle.getState() != null ? cycle.getState() : "N/A", workbook, false, IndexedColors.WHITE.getIndex(), false);

        String kpis = cycle.getKpis() != null && !cycle.getKpis().isEmpty() ?
                String.join("\n", cycle.getKpis().stream().map(Kpi::getName).toArray(String[]::new)) :
                "No KPIs available";
        createStyledCell(cycleRow, 3, kpis, workbook, false, IndexedColors.WHITE.getIndex(), true);


        for (String teamName : teamMemberRatings.keySet()) {
            System.out.println(teamName);
            Row teamHeader = sheet.createRow(rowIndex++);
            createStyledCell(teamHeader, 0, teamName + " ", workbook, true, IndexedColors.LIGHT_BLUE.getIndex(), false);
            createStyledCell(teamHeader, 1, "Username", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), false);
            createStyledCell(teamHeader, 2, "Rating", workbook, true, IndexedColors.GREY_25_PERCENT.getIndex(), false);


            Map<String, Double> teamMembers = teamMemberRatings.get(teamName);
            for (Map.Entry<String, Double> entry : teamMembers.entrySet()) {
                Row teamRow = sheet.createRow(rowIndex++);
                createStyledCell(teamRow, 0, "", workbook, false, IndexedColors.WHITE.getIndex(), false); // Empty cell for spacing
                createStyledCell(teamRow, 1, entry.getKey(), workbook, false, IndexedColors.WHITE.getIndex(), false); // Username
                createStyledCell(teamRow, 2, entry.getValue().toString(), workbook, false, IndexedColors.WHITE.getIndex(), false); // Rating
            }
        }


        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }


        sheet.setColumnWidth(4, 6000);
        Row firstRow = sheet.getRow(0);
        if (firstRow != null) {
            Cell firstRowCell = firstRow.createCell(4);
            firstRowCell.setCellStyle(firstRowCell.getCellStyle());
            firstRowCell.getCellStyle().setWrapText(true);
        }


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    private void createStyledCell(Row row, int columnIndex, String value, Workbook workbook, boolean isHeader, short bgColorIndex, boolean wrapText) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);

        CellStyle style = createCellStyle(workbook, isHeader, bgColorIndex);
        style.setWrapText(wrapText);
        cell.setCellStyle(style);
    }

    private CellStyle createCellStyle(Workbook workbook, boolean isHeader, short bgColorIndex) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        if (isHeader) {
            font.setBold(true);
            style.setFont(font);
            style.setFillForegroundColor(bgColorIndex);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        } else {
            font.setBold(false);
            style.setFont(font);
        }
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }
}
