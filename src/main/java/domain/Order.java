package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Order
{
    private int orderNr;
    private boolean isStudentOrder;
    private boolean isWeekend;
    private ArrayList<MovieTicket> tickets;

    public Order(int orderNr, boolean isStudentOrder)
    {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;

        tickets = new ArrayList<MovieTicket>();
    }

    public int getOrderNr()
    {
        return orderNr;
    }

    public void addSeatReservation(MovieTicket ticket)
    {
        tickets.add(ticket);
    }

    public double calculatePrice()
    {
        //Arraylist for storing ticket prices
        ArrayList<Double> ticketPrices = new ArrayList<Double>();

        //Get base ticket price
        double ticketBasePrice= tickets.get(0).getPrice();

        //Add appropriate premium, add each ticket price to prices list
        double ticketPrice;
        for (MovieTicket movieTicket: tickets) {
            ticketPrice=ticketBasePrice;

            if(movieTicket.isPremiumTicket()) {
                if (isStudentOrder){
                    ticketPrice += 2;
                }
                else ticketPrice += 3;
            }
            ticketPrices.add(ticketPrice);
        }

        //Functionality for getting day of the week for the screening.
        DayOfWeek screeningDay = tickets.get(0).getScreeningDate().getDayOfWeek();
        //TODO: Find a better alternative to this if statement
        if (screeningDay==DayOfWeek.FRIDAY ||
            screeningDay==DayOfWeek.SATURDAY ||
            screeningDay==DayOfWeek.SUNDAY )
        {
            isWeekend=true;
        }

        if(isStudentOrder || !isWeekend) {
            //Sort prices list by price(low to high) & remove cheapest 2nd free tickets
            Collections.sort(ticketPrices);
            int secondFree = ticketPrices.size() / 2;
            ticketPrices.subList(0, secondFree).clear();
        }

        //Calculate total price
        double total=0;
        for (Double d : ticketPrices) {
            total+=d;
        }

        //10% group discount for non-students during the weekends
        //IMPORTANT: Ik ga er van uit dat de groepskorting niet samengaat met het 2e kaartje gratis.
        //Hierom worden studenten niet meegenomen in de korting, en niet-studenten alleen in het weekend.
        //Dit is hoe ik de opdracht heb begrepen. Verander de onderstaande functie als dit niet het geval is.
        if (!isStudentOrder && isWeekend && tickets.size()>5){
            total*=0.9;
        }

        return total;
    }

    public void export(TicketExportFormat exportFormat)
    {
        // Bases on the string respresentations of the tickets (toString), write
        // the ticket to a file with naming convention Order_<orderNr>.txt of
        // Order_<orderNr>.json
    }
}
