package com.czxy.bos.controller.print;

import com.czxy.bos.domain.report.HighChart;
import com.czxy.bos.domain.take_delivery.WayBill;
import com.czxy.bos.service.take_delivery.WayBillService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Resource
    private WayBillService wayBillService;

    @GetMapping("/exportPdf")
    public void exportPdf(HttpServletResponse response) throws Exception {
        String fileName = new String("运单数据".getBytes(), "iso-8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".pdf");

        // 查询出 满足当前条件 结果数据
        List<WayBill> wayBills = wayBillService.findAll();

        // 生成PDF文件
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        // 写PDF数据
        // 向document 生成pdf表格
        Table table = new Table(7);//创建7列的表格
        table.setWidth(80); // 宽度
        table.setBorder(1); // 边框
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); // 水平对齐方式
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP); // 垂直对齐方式



        // 写表头
        table.addCell(buildCell("运单号"));
        table.addCell(buildCell("寄件人"));
        table.addCell(buildCell("寄件人电话"));
        table.addCell(buildCell("寄件人地址"));
        table.addCell(buildCell("收件人"));
        table.addCell(buildCell("收件人电话"));
        table.addCell(buildCell("收件人地址"));
        // 写数据
        for (WayBill wayBill : wayBills) {
            table.addCell(buildCell(wayBill.getWayBillNum()));
            table.addCell(buildCell(wayBill.getSendName()));
            table.addCell(buildCell(wayBill.getSendMobile()));
            table.addCell(buildCell(wayBill.getSendAddress()));
            table.addCell(buildCell(wayBill.getRecName()));
            table.addCell(buildCell(wayBill.getRecMobile()));
            table.addCell(buildCell(wayBill.getRecAddress()));
        }
        // 将表格加入文档
        document.add(table);

        document.close();

    }

    private Cell buildCell(String content) throws Exception {
        // 设置表格字体
        BaseFont cn = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",false);
        Font font = new Font(cn, 10, Font.NORMAL);

        Phrase phrase = new Phrase(content, font);
        return new Cell(phrase);
    }

    @RequestMapping(value = "/exportHighcharts", method = RequestMethod.GET)
    public ResponseEntity<List<HighChart>> exportHighcharts() {
        // 查询数据库，返回List<Object[]>
        List<HighChart> list = wayBillService.chartWayBill();

        return new ResponseEntity<List<HighChart>>(list, HttpStatus.OK);
    }
}
