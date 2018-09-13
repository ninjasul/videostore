
public class Rental
{
	public Rental (Movie movie, int daysRented) {
		this.movie 		= movie;
		this.daysRented = daysRented;
	}

	public int getDaysRented () {
		return daysRented;
	}

	public Movie getMovie () {
		return movie;
	}

	private final Movie movie;
	private final int daysRented;
}