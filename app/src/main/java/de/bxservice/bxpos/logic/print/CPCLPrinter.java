package de.bxservice.bxpos.logic.print;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.bxservice.bxpos.logic.model.pos.POSOrder;
import de.bxservice.bxpos.logic.model.pos.POSOrderLine;

/**
 * Created by Diego Ruiz on 21/04/16.
 */
public class CPCLPrinter extends AbstractPOSPrinter {

    public CPCLPrinter(POSOrder order) {
        super(order);
    }


    @Override
    public String print() {

        /*return String.format("! 0 200 200 600 1\r\n" +
                "PW 550\r\n" +
                "TONE 0\r\n" +
                "SPEED 2\r\n" +
                "ON-FEED IGNORE\r\n" +
                "NO-PACE\r\n" +
                "POSTFEED 50\r\n" +
                "JOURNAL\r\n" +
                //"BOX 0 0 376 797 5\r\n" +
                "T 5 1 25 20 Type:\r\n" +
                "T 5 1 90 20 %s\r\n" +
                "T 5 1 25 75 Server:\r\n" +
                "T 5 1 110 75 %s\r\n" +
                "T 5 1 25 130 Guest:\r\n" +
                "T 5 1 100 130 %s\r\n" +
                "T 5 1 300 20 Table\r\n" +
                "T 5 3 300 75 %s\r\n" +
                "LINE 25 175 530 175 1\r\n" +
                "T 5 0 35 421 Address:\r\n" +
                "T 5 0 35 342 Last Name:\r\n" +
                "T 5 0 35 257 First Name:\r\n" +
                "T 5 0 35 175 Plate #:\r\n" +
                "T 5 0 64 290 %s\r\n" +
                "T 5 0 64 374 %s\r\n" +
                "T 5 0 64 466 %s\r\n" +
                "T 5 0 64 558 %s\r\n" +
                "FORM \r\n\r\n"+
                "PRINT\r\n", new Object[] { "Dine-in", "Garden", "5", "Table 1","Carlos", "Ruiz", "AAA 2356", "None" });

                /*return "! U1 JOURNAL\r\n" +
                "! U1 SETLP 5 2 24\r\n" +
                "Order #: "+ order.getOrderId() +"\r\n" +
                "Type: Dine-in\r\n" +
                "Server: Garden\r\n" +
                "Guests: "+ order.getGuestNumber() +"\r\n" +
                //"! U1 LINE 25 175 530 175 1\r\n" +
                "! U1 SETLP 5 0 24\r\n" +

                "! U1 SETLP 7 0 24\r\n" +
                order.getOrderedLines().get(0).getQtyOrdered() + "  " + order.getOrderedLines().get(0).getProduct().getProductName() + "\r\n" +
                order.getOrderedLines().get(1).getQtyOrdered() + "  " + order.getOrderedLines().get(1).getProduct().getProductName() + "\r\n" +
                "! U1 PRESENT-AT\r\n" +
                "! U1 PRINT\r\n";*/

        return null;
    }

    /**
     * Print the kitchen receipt
     * Returns a string with %s
     * 1 - Order string
     * 2 - Table
     * 3 - Table Number
     * 3 - Server
     * 4 - Guests
     */
    @Override
    public String printKitchen() {
        //TODO: Filter only kitchen items
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        StringBuilder ticket = new StringBuilder();

        ticket.append("! U1 JOURNAL\r\n");
        ticket.append("! U1 SETLP 5 3 86\r\n");
        ticket.append("\r\n");
        ticket.append("%s #: "+ order.getOrderId() +"\r\n");
        ticket.append( "! U1 SETLP 7 0 24\r\n");
        ticket.append( "%s: %s\r\n");
        ticket.append("%s: " + order.getServerName() +"\r\n");
        ticket.append( "%s: "+ order.getGuestNumber() +"\r\n");
        ticket.append("\r\n");
        ticket.append( "! U1 SETLP 5 2 46\r\n");
        for(POSOrderLine line : order.getOrderedLines()) {
            ticket.append(line.getQtyOrdered() + "  " + line.getProduct().getProductName() + "\r\n");
            if(line.getProductRemark() != null && !line.getProductRemark().isEmpty())
                ticket.append("    " + line.getProductRemark() + "\r\n");
        }
        ticket.append("\r\n");
        ticket.append( "! U1 SETLP 7 0 24\r\n");
        ticket.append(dateFormat.format(cal.getTime())+"\r\n");//2014/08/06 16:00:22
        ticket.append("! U1 PRESENT-AT\r\n");
        ticket.append("! U1 PRINT\r\n");

        return ticket.toString();

    }

    @Override
    public String printBar() {
        return null;
    }

    @Override
    public String printReceipt() {
        StringBuilder ticket = new StringBuilder();

        //header
        String label = String.format("! 0 200 200 240 1\r\n" +
                "PW 550\r\n" +
                "COUNTRY GERMANY\r\n" +
                "CENTER\r\n" +
                "T 4 0 25 20 %s\r\n" + //Restaurant name
                "T 5 1 25 66 %s\r\n" + //Restaurant address
                "T 5 1 25 112 %s\r\n" + //Restaurant city
                "LEFT\r\n" +
                "T 7 0 10 158 Bon: 1010101 \r\n" + //Receipt Number
                "RIGHT\r\n" +
                "T 7 0 400 158 Table: T3\r\n" + //Table Number
                "LEFT\r\n" +
                "T 7 0 10 182 Server: Steve \r\n" + //Server name
                "RIGHT\r\n" +
                "T 7 0 400 182 Guests: 5\r\n" +  //# of guests
                "RIGHT\r\n" +
                "T 0 2 10 206 24/25/2016 15:06;24\r\n" +  //Date
                "LINE 0 235 550 235 1\r\n" +
                "POSTFEED 0\r\n" +
                "FORM \r\n\r\n"+
                "PRINT\r\n", new Object[] { "Bx Service GmbH", "Bleichpfad 20", "47799 Krefeld"});

        ticket.append(label);

        ticket.append( "! U1 SETLP 7 0 24\r\n");
        for(POSOrderLine line : order.getOrderedLines()) {
            ticket.append(line.getQtyOrdered() + "  " + line.getProduct().getProductName() + "            "+
                    line.getLineTotalAmt() + " EUR\r\n");
            if(line.getProductRemark() != null && !line.getProductRemark().isEmpty())
                ticket.append("    " + line.getProductRemark() + "\r\n");
        }
        //ticket.append("! U1 PRINT\r\n");

        //footer
        label = String.format("! 0 200 200 180 1\r\n" +
                "LEFT\r\n" +
                "LINE 0 0 550 0 2\r\n" +
                "T 7 1 25 15 Total\r\n" + //Total label
                "RIGHT\r\n" +
                "T 7 1 400 15 11,15\r\n" + //Total value
                "LEFT\r\n" +
                "T 7 1 25 55 Cash\r\n" + //Received
                "RIGHT\r\n" +
                "T 7 1 400 55 15,15\r\n" + //Total value
                "LEFT\r\n" +
                "T 7 1 25 95 Back\r\n" + //back label
                "RIGHT\r\n" +
                "T 7 1 400 95 4,15\r\n" + //back
                "CENTER\r\n" +
                "T 7 1 10 125 We hope to see you soon again\r\n" + //Server name
                "POSTFEED 20\r\n" +
                "FORM \r\n\r\n"+
                "PRINT\r\n", new Object[] { "Bx Service GmbH", "Bleichpfad 20", "47799 Krefeld"});

        ticket.append(label);

        return ticket.toString();
    }
}