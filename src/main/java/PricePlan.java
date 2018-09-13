import java.util.function.Function;

class PricePlan implements Function<Rental, PricedRental> {
    private final Tariff tariff;
    private final LoyaltyPlan loyaltyPlan;

    PricePlan(Tariff tariff, LoyaltyPlan loyaltyPlan) {
        this.tariff = tariff;
        this.loyaltyPlan = loyaltyPlan;
    }

    @Override
    public PricedRental apply(Rental r) {
        return new PricedRental(
                tariff.calculateRentalPrice(r),
                loyaltyPlan.calculatePoints(r),
                r.getMovie().getTitle()
        );
    }
}