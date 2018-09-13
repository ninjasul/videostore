import static sun.swing.MenuItemLayoutHelper.max;

public class VipTariff implements Tariff {

    @Override
    public double calculateRentalPrice(Rental rental) {

        // determines the amount for each line
        int daysRented = rental.getDaysRented();
        int priceCode = rental.getMovie().getPriceCode();

        switch (priceCode) {
            case Movie.REGULAR:
                return 1.0 * daysRented;

            case Movie.NEW_RELEASE:
                return 2.0 * daysRented;

            case Movie.CHILDRENS:
                return max(0, daysRented - 2) * 1.0;

            default:
                throw new IllegalArgumentException("Unhandled price code " + priceCode );
        }
    }
}