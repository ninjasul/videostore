import java.util.List;
import java.util.stream.Collectors;

class PricePlan {
    private final Tariff tariff;
    private final LoyaltyPlan loyaltyPlan;
    private final DiscountCalculator discountCalculator = new DiscountCalculator();

    PricePlan(Tariff tariff, LoyaltyPlan loyaltyPlan) {
        this.tariff = tariff;
        this.loyaltyPlan = loyaltyPlan;
    }

    public StatementRenderer.Statement statement(String name, List<Rental> rentals) {
        List<PricedRental> pricedRentalList = rentals.stream().map(r -> new PricedRental(
                tariff.calculateRentalPrice(r),
                loyaltyPlan.calculatePoints(r),
                r.getMovie().getTitle()
        )).collect(Collectors.toList());

        double discountPercentage = discountCalculator.calculateDiscountFor(pricedRentalList);

        return new StatementRenderer.Statement(name, pricedRentalList, discountPercentage);
    }

    private static class DiscountCalculator {
        public DiscountCalculator() {
        }

        public double calculateDiscountFor(List<PricedRental> pricedRentalList) {
            return pricedRentalList.size() >= 5 ? 0.05 : 0.00;
        }
    }
}