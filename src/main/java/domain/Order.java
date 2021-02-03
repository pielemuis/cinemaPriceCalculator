package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order
{
    private int orderNr;
    private boolean isStudentOrder;

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
        double totalPrice = 0;

        //TODO 1: Add functionality for 2nd ticket free
        //In de winkel is bij 2e gratis het goedkoopste product gratis
        //Dus ik gok dat wanneer je 3 normale en 3 premium tickets hebt de normale tickets gratis zijn
        //Als dit het geval is moeten we misschien een aparte Arraylist met ticketprijzen maken,
        // en hier de goedkoopste tickets uit halen wanneer de korting van toepassing is

        //Functionality for getting day of the week for the screening.
        DayOfWeek screeningDay = DayOfWeek.from(tickets.get(0).getScreeningDate());

        //FUNCTIONALITY FOR GETTING BASE TICKET PRICE
        //& ADDING THE APPROPRIATE PREMIUM PRICE TO THAT BASE WHERE APPLICABLE
        double ticketBasePrice= tickets.get(0).getPrice();
        double ticketPrice;
        for (MovieTicket movieTicket: tickets) {
            ticketPrice=ticketBasePrice;

            if(movieTicket.isPremiumTicket()) {
                if (isStudentOrder){
                    ticketPrice += 2;
                }
                else ticketPrice += 3;
            }
        //TODO 2: process this price, bearing in mind 2nd ticket free

        }


        return totalPrice;
    }

    public void export(TicketExportFormat exportFormat)
    {
        // Bases on the string respresentations of the tickets (toString), write
        // the ticket to a file with naming convention Order_<orderNr>.txt of
        // Order_<orderNr>.json
    }
}
