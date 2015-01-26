package com.hanze.ticketcenter.ticketservice.general;
 
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
public class TicketGenerator {
 
    /** The resulting PDF. */
    private final static String FILE_NAME = "C:\\Users\\Jorian\\School\\Jaar 4\\Gedistribueerde Applicaties\\TicketCenter\\ticket.pdf";
    private String concert, artist, location, date, time, userName, userDate, userEmail;
    
    public TicketGenerator(String concert, String artist, String location, String date, String time, String userName, String userDate, String userEmail) {
    	this.concert = concert;
    	this.artist = artist;
    	this.location = location;
    	this.date = date;
    	this.time = time;
    	this.userName = userName;
    	this.userDate = userDate;
    	this.userEmail = userEmail;
    }
 
    /**
     * Generate the ticket
     * @throws    DocumentException 
     * @throws    java.io.IOException
     */
    public ByteArrayOutputStream generateTicket() throws IOException, DocumentException {
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	
        Document document = new Document(new Rectangle(340, 842));
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
 
        // Title 
        Paragraph titleParagraph = new Paragraph("TicketCenter - Uw Ticket");
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        document.add(new Paragraph(" "));       
                
        // Concert information
        PdfPTable table = createConcertTable();
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        document.add(table);
        
        // User information
        table = createUserTable();
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        document.add(table);        
        
        // The barcode
        BarcodeEAN codeEAN = new BarcodeEAN();
        codeEAN.setCode("4512345678906");    
        codeEAN.setExtended(true);
        document.add(codeEAN.createImageWithBarcode(cb, null, null));
 
        document.close();
        
        return outputStream;
    }
    
    public PdfPTable createConcertTable() throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Concert:"));
        table.addCell(cell);
        table.addCell(this.concert);
        
        cell = new PdfPCell(new Phrase("Artiest:"));
        table.addCell(cell);
        table.addCell(this.artist);
        
        cell = new PdfPCell(new Phrase("Locatie:"));
        table.addCell(cell);
        table.addCell(this.location);        

        cell = new PdfPCell(new Phrase("Datum:"));
        table.addCell(cell);
        table.addCell(this.date);        
        
        cell = new PdfPCell(new Phrase("Aanvang:"));
        table.addCell(cell);
        table.addCell(this.time);          
        
        return table;
    }
    
    public PdfPTable createUserTable() throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Tickethouder:"));
        table.addCell(cell);
        table.addCell(this.userName);
        
        cell = new PdfPCell(new Phrase("Geboortedatum:"));
        table.addCell(cell);
        table.addCell(this.userDate);
        
        cell = new PdfPCell(new Phrase("Email:"));
        table.addCell(cell);
        table.addCell(this.userEmail);             
        
        return table;
    }    
    
}