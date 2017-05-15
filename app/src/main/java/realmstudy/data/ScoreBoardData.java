package realmstudy.data;

import java.util.ArrayList;

/**
 * Created by developer on 9/12/16.
 */
public class ScoreBoardData {
    int totalRuns;
    int totalBalls;
    int firstIinningsWicket = 0;
    int firstInningsTotal = 0;
    private String total_over="0.0";

   public Batsman striker=new Batsman();
    public Batsman nonStriker=new Batsman();
    public Bowlers curr_bowlers =new Bowlers();
    public Bowlers next_bowlers =new Bowlers();



    public String getShotAt() {
        return shotAt;
    }

    public void setShotAt(String shotAt) {
        this.shotAt = shotAt;
    }

    public String getFirstIinningsOver() {
        return firstIinningsOver;
    }

    String shotAt;

    public void setMatchQuote(String matchQuote) {
        this.matchQuote = matchQuote;
    }

    private String matchQuote;

    public String getFirstInningsOver() {
        return firstIinningsOver;
    }

    public void setFirstIinningsOver(String firstIinningsOver) {
        this.firstIinningsOver = firstIinningsOver;
    }

    private String firstIinningsOver = "0";

    public boolean isHomeTeamBatting() {
        return isHomeTeamBatting;
    }

    public void setHomeTeamBatting(boolean homeTeamBatting) {
        isHomeTeamBatting = homeTeamBatting;
    }

    public int getFirstIinningsWicket() {
        return firstIinningsWicket;
    }

    public void setFirstIinningsWicket(int firstIinningsWicket) {
        this.firstIinningsWicket = firstIinningsWicket;
    }

    boolean isHomeTeamBatting;



//    int NonStrikerRun;
//    int NonStrikerBalls;
//    int currentBowlerBalls, currentBowlerRuns;
//    int nextBowlerBalls, nextBowlerRuns;
//    String StrikerName, NonStrikerName;
//    String nextBatsmanName;
//    int nextBatsmanRun;
//    int nextBatsmanBalls;
    String wicket;

    int total_wicket;
   // String CurrentBowlerName, NextBowlerName;
    ArrayList<String> lastThreeOvers;
    String awayTeam;
    String homeTeam;


    public int getFirstInningsTotal() {
        return firstInningsTotal;
    }

    public void setFirstInningsTotal(int firstInningsTotal) {
        this.firstInningsTotal = firstInningsTotal;
    }


    public int getfirstIinningsWicket() {
        return firstIinningsWicket;
    }


    public void setfirstInningsWicket(int firstIinningsWicket) {
        this.firstIinningsWicket = firstIinningsWicket;
    }


    public ArrayList<String> getLastThreeOvers() {
        return lastThreeOvers;
    }

    public void setLastThreeOvers(ArrayList lastTwelveBalls) {
        this.lastThreeOvers = lastTwelveBalls;
    }


    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }


    public int getTotal_wicket() {
        return total_wicket;
    }

    public void setTotal_wicket(int total_wicket) {
        this.total_wicket = total_wicket;
    }

    public void setBowlerSwitched(boolean bowlerSwitched) {
        this.bowlerSwitched = bowlerSwitched;
    }


//    public String getNextBatsmanName() {
//        return nextBatsmanName;
//    }
//
//    public void setNextBatsmanName(String nextBatsmanName) {
//        this.nextBatsmanName = nextBatsmanName;
//    }
//
//    public int getNextBatsmanRun() {
//        return nextBatsmanRun;
//    }
//
//    public void setNextBatsmanRun(int nextBatsmanRun) {
//        this.nextBatsmanRun = nextBatsmanRun;
//    }
//
//    public int getNextBatsmanBalls() {
//        return nextBatsmanBalls;
//    }
//
//    public void setNextBatsmanBalls(int nextBatsmanBalls) {
//        this.nextBatsmanBalls = nextBatsmanBalls;
//    }


    public String getWicket() {
        return wicket;
    }

    public void setWicket(String wicket) {
        this.wicket = wicket;
    }


    boolean batsmanSwitched;

    public boolean isBowlerSwitched() {
        return bowlerSwitched;
    }

    public void setAskNextBowler(boolean bowlerSwitched) {
        this.bowlerSwitched = bowlerSwitched;
    }

    boolean bowlerSwitched;

//    public int getCurrentBowlerBalls() {
//        return currentBowlerBalls;
//    }
//
//    public void setCurrentBowlerLegalBalls(int currentBowlerLegalBalls) {
//        this.currentBowlerBalls = currentBowlerLegalBalls;
//    }
//
//    public int getCurrentBowlerRuns() {
//        return currentBowlerRuns;
//    }
//
//    public void setCurrentBowlerRuns(int currentBowlerRuns) {
//        this.currentBowlerRuns = currentBowlerRuns;
//    }
//
//    public int getNextBowlerBalls() {
//        return nextBowlerBalls;
//    }
//
//    public void setNextBowlerBalls(int nextBowlerBalls) {
//        this.nextBowlerBalls = nextBowlerBalls;
//    }
//
//    public int getNextBowlerRuns() {
//        return nextBowlerRuns;
//    }
//
//    public void setNextBowlerRuns(int nextBowlerRuns) {
//        this.nextBowlerRuns = nextBowlerRuns;
//    }


//    public String getCurrentBowlerName() {
//        return CurrentBowlerName;
//    }
//
//    public void setCurrentBowlerName(String currentBowlerName) {
//        CurrentBowlerName = currentBowlerName;
//    }
//
//    public String getNextBowlerName() {
//        return NextBowlerName;
//    }
//
//    public void setNextBowlerName(String nextBowlerName) {
//        NextBowlerName = nextBowlerName;
//    }


    public boolean isBatsmanSwitched() {
        return batsmanSwitched;
    }

    public void setBatsmanSwitched(boolean batsmanSwitched) {
        this.batsmanSwitched = batsmanSwitched;
    }


//    public String getStrikerName() {
//        return StrikerName;
//    }
//
//    public void setStrikerName(String StrikerName) {
//        this.StrikerName = StrikerName;
//    }
//
//    public String getNonStrikerName() {
//        return NonStrikerName;
//    }
//
//    public void setNonStrikerName(String NonStrikerName) {
//        this.NonStrikerName = NonStrikerName;
//    }

//    public int getStrikerRun() {
//        return StrikerRun;
//    }
//
//    public void setStrikerRun(int StrikerRun) {
//        this.StrikerRun = StrikerRun;
//    }
//
//    public int getStrikerBalls() {
//        return StrikerBalls;
//    }
//
//    public void setStrikerBalls(int StrikerBalls) {
//        this.StrikerBalls = StrikerBalls;
//    }
//
//    public int getNonStrikerRun() {
//        return NonStrikerRun;
//    }
//
//    public void setNonStrikerRun(int NonStrikerRun) {
//        this.NonStrikerRun = NonStrikerRun;
//    }
//
//    public int getNonStrikerBalls() {
//        return NonStrikerBalls;
//    }
//
//    public void setNonStrikerBalls(int NonStrikerBalls) {
//        this.NonStrikerBalls = NonStrikerBalls;
//    }


    public int getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(int totalRuns) {
        this.totalRuns = totalRuns;
    }

    public int getTotalBalls() {
        return totalBalls;
    }

    public void setTotalBalls(int totalBalls) {
        this.totalBalls = totalBalls;
    }

    public void setTotalOver(String total_over) {
        this.total_over = total_over;
    }

    public String getTotalOver() {
        return total_over;
    }

//    public String getCurrentBowlerOver() {
//        return CommanData.ballsToOver(currentBowlerBalls);
//    }
//
//    public String getNextBowlerOver() {
//        return CommanData.ballsToOver(nextBowlerBalls);
//    }



    public String getMatchQuote() {
        return matchQuote;
    }





    public class Bowlers{

//        public String getOvers() {
//            return overs;
//        }
//
//        public void setOvers(String overs) {
//            this.overs = overs;
//        }

        public int getMaiden() {
            return maiden;
        }

        public void setMaiden(int maiden) {
            this.maiden = maiden;
        }

        public int getRuns() {
            return runs;
        }

        public void setRuns(int runs) {
            this.runs = runs;
        }

        public int getWicket() {
            return wicket;
        }

        public void setWicket(int wicket) {
            this.wicket = wicket;
        }

        public int getEconomic_rate() {
            return economic_rate;
        }

        public void setEconomic_rate(int economic_rate) {
            this.economic_rate = economic_rate;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        String Name;
       // String overs;
        int maiden;
        int runs;
        int wicket;

        public int getBalls() {
            return balls;
        }

        public void setBalls(int balls) {
            this.balls = balls;
        }

        public String getOvers() {
        return CommanData.ballsToOver(getBalls());
    }


        int balls;
        int economic_rate;}








    //Batsman
    public class Batsman{


        String Name;
        int runs;
        int balls;
        int fours;
        int sixes;
        int strikeRate;
        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
        public int getRuns() {
            return runs;
        }

        public void setRuns(int runs) {
            this.runs = runs;
        }

        public int getBalls() {
            return balls;
        }

        public void setBalls(int balls) {
            this.balls = balls;
        }

        public int getFours() {
            return fours;
        }

        public void setFours(int fours) {
            this.fours = fours;
        }

        public int getSixes() {
            return sixes;
        }

        public void setSixes(int sixes) {
            this.sixes = sixes;
        }

        public int getStrikeRate() {
            return strikeRate;
        }

        public void setStrikeRate(int strikeRate) {
            this.strikeRate = strikeRate;
        }
    }
}
