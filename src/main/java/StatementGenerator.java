import java.util.List;
import java.util.stream.Collectors;

public class StatementGenerator {

    public String generate(String name, List<Rental> rentals, PricePlan plan) {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + name + "\n");

        List<PricedRental> pricedRentalList = rentals.stream().map(plan).collect(Collectors.toList());

        for (PricedRental pricedRental : pricedRentalList) {
            double rentalPrice = pricedRental.price;

            frequentRenterPoints += pricedRental.points;

            result.append("\t").append(pricedRental.title).append("\t").append(String.valueOf(rentalPrice)).append("\n");
            totalAmount += rentalPrice;
        }

        result.append("You owed ").append(String.valueOf(totalAmount)).append("\n");
        result.append("You earned ").append(String.valueOf(frequentRenterPoints)).append(" frequent renter points\n");

        return result.toString();
    }
}