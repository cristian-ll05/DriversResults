
public class DriverStanding {
    private int driversStanding;
    private int raceId;
    private int points;
    private int position;
    private String positionText;
    private int wins;

    public DriverStanding(int driversStanding, int raceId, int points, int position, String positionText, int wins) {
        this.driversStanding = driversStanding;
        this.raceId = raceId;
        this.points = points;
        this.position = position;
        this.positionText = positionText;
        this.wins = wins;
    }

    public int getDriversStanding() {
        return driversStanding;
    }

    public int getRaceId() {
        return raceId;
    }

    public int getPoints() {
        return points;
    }

    public int getPosition() {
        return position;
    }

    public String getPositionText() {
        return positionText;
    }

    public int getWins() {
        return wins;
    }
}