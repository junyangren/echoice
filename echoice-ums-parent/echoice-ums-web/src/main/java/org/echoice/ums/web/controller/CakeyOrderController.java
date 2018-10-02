package org.echoice.ums.web.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.echoice.modules.web.MsgTip;
import org.echoice.ums.domain.CakeyOrder;
import org.echoice.ums.domain.CakeyOrderDetail;
import org.echoice.ums.service.CakeyOrderDetailService;
import org.echoice.ums.service.CakeyOrderService;
import org.echoice.ums.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
/**
* 描述：caKey操作工单 控制层
* @author wujy
* @date 2018/10/01
*/
@Controller
@RequestMapping(value = "/cakeyOrder")
public class CakeyOrderController{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private static final String PAGE_SIZE = "20";
	@Autowired
	private CakeyOrderService cakeyOrderService;
	
	@Autowired
	private CakeyOrderDetailService cakeyOrderDetailService;
	
	@RequestMapping(value="index")
	public String index(){
		return "/cakeyOrder/index";
	}

    @RequestMapping(value = "searchJSON")
    @ResponseBody
    public String searchJSON(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(value = "rows", defaultValue = PAGE_SIZE) int pageSize,CakeyOrder searchForm,ServletRequest request) {
        Page<CakeyOrder> page=cakeyOrderService.getCakeyOrderDao().findPageList(searchForm, pageNumber, pageSize);
        String respStr=JSONUtil.getGridFastJSON(page.getTotalElements(), page.getContent(), null, null);
        logger.debug("respStr:{}",respStr);
        return respStr;
    }

	
	@RequestMapping(value = "downPdf")
	public String downPdf(CakeyOrder cakeyOrder,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String orderId=request.getParameter("orderId");
		response.setContentType("application/x-msdownload"); 
		response.setHeader("Content-Disposition", "attachment; filename=\""+ URLEncoder.encode(orderId + ".pdf", "UTF-8") + "\"");
		
		List<CakeyOrderDetail> list=this.cakeyOrderDetailService.getCakeyOrderDetailDao().findByOrderId(orderId);
		
		OutputStream os = response.getOutputStream();
		BufferedOutputStream osbf=new BufferedOutputStream(os);
		
		
		Document document=new Document();
		PdfWriter.getInstance(document,osbf);
        document.open(); 
        
        BaseFont bfChinese=BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font keyfont = new Font(bfChinese, 8, Font.BOLD);
        Font textfont = new Font(bfChinese, 8, Font.NORMAL);
        
        PdfPTable table = new PdfPTable(4);
        table.setTotalWidth(520); 
        table.setLockedWidth(true); 
        table.setHorizontalAlignment(Element.ALIGN_CENTER);      
        table.getDefaultCell().setBorder(1);
        
        table.addCell(createCell("工单号："+orderId, keyfont,Element.ALIGN_LEFT,4,false)); 
        
        table.addCell(createCell("姓名", keyfont, Element.ALIGN_CENTER)); 
        table.addCell(createCell("身份证号", keyfont, Element.ALIGN_CENTER)); 
        table.addCell(createCell("硬件介质SN", keyfont, Element.ALIGN_CENTER)); 
        table.addCell(createCell("办理时间", keyfont, Element.ALIGN_CENTER));
		String dft="yyyy-MM-dd";

		for (CakeyOrderDetail cakeyOrderDetail : list) {
            table.addCell(createCell(cakeyOrderDetail.getName(), textfont)); 
            table.addCell(createCell(cakeyOrderDetail.getIdcard(), textfont)); 
            table.addCell(createCell(cakeyOrderDetail.getHardwareSn(), textfont)); 
            table.addCell(createCell(DateFormatUtils.format(cakeyOrderDetail.getCreateTime(), dft), textfont));
		}

        document.add(table);
        document.close();
        
        
		return null;
	}
	
    private PdfPCell createCell(String value,Font font){ 
        PdfPCell cell = new PdfPCell(); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);  
        cell.setPhrase(new Phrase(value,font)); 
       return cell; 
   }
	
    private PdfPCell createCell(String value,Font font,int align){ 
        PdfPCell cell = new PdfPCell(); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);         
        cell.setHorizontalAlignment(align);     
        cell.setPhrase(new Phrase(value,font)); 
       return cell; 
   }
	
    private PdfPCell createCell(String value,Font font,int align,int colspan,boolean boderFlag){ 
        PdfPCell cell = new PdfPCell(); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
        cell.setHorizontalAlignment(align);     
        cell.setColspan(colspan); 
        cell.setPhrase(new Phrase(value,font)); 
        cell.setPadding(3.0f); 
        if(!boderFlag){ 
            cell.setBorder(0); 
            cell.setPaddingTop(15.0f); 
            cell.setPaddingBottom(8.0f); 
        } 
       return cell; 
   } 
	
	@RequestMapping(value = "uploadPdf")
	@ResponseBody
	public MsgTip uploadPdf(MultipartFile file,HttpServletRequest request) {
		MsgTip msgTip=new MsgTip();
		return msgTip;
	}
}
