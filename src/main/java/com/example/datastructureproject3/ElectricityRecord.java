package com.example.datastructureproject3;

/* this class fore store the field in each electricity
data  record  obtained from OCHA in a csv file
(Electricity.csv attached). Each line
in this file contains a daily electricity record (Date, Israeli_Lines_MWs, Gaza_Power_Plant_MWs,
Egyptian_Lines_MWs, Total_daily_Supply_available_in_MWs, Overall_demand_in_MWs,
Power_Cuts_hours_day_400mg, and Temp) separated by comma (,).
so in this class we store each field as floateger value except temp store as flout
 */
public class ElectricityRecord {


    private String date;
    private float israeliLinesMWs;
    private float gazaPowerPlantMWs;
    private float egyptianLinesMWs;
    private float totalDailySupplyAvailableInMWs;
    private float overallDemandInMWs;
    private float powerCutsHoursDay400mg;
    private float temp;

    // Constructors
    public ElectricityRecord(String date, float israeliLinesMWs, float gazaPowerPlantMWs, float egyptianLinesMWs,
                             float totalDailySupplyAvailableInMWs, float overallDemandInMWs,
                             float powerCutsHoursDay400mg, float temp) {
        this.date = date;
        this.israeliLinesMWs = israeliLinesMWs;
        this.gazaPowerPlantMWs = gazaPowerPlantMWs;
        this.egyptianLinesMWs = egyptianLinesMWs;
        this.totalDailySupplyAvailableInMWs = totalDailySupplyAvailableInMWs;
        this.overallDemandInMWs = overallDemandInMWs;
        this.powerCutsHoursDay400mg = powerCutsHoursDay400mg;
        this.temp = temp;
    }

    // default constructor
    public ElectricityRecord(){

    }

    // Getter methods
    public String getDate() {
        return date;
    }

    public float getIsraeliLinesMWs() {
        return israeliLinesMWs;
    }

    public float getGazaPowerPlantMWs() {
        return gazaPowerPlantMWs;
    }

    public float getEgyptianLinesMWs() {
        return egyptianLinesMWs;
    }

    public float getTotalDailySupplyAvailableInMWs() {
        return totalDailySupplyAvailableInMWs;
    }

    public float getOverallDemandInMWs() {
        return overallDemandInMWs;
    }

    public float getPowerCutsHoursDay400mg() {
        return powerCutsHoursDay400mg;
    }

    public float getTemp() {
        return temp;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIsraeliLinesMWs(float israeliLinesMWs) {
        this.israeliLinesMWs = israeliLinesMWs;
    }

    public void setGazaPowerPlantMWs(float gazaPowerPlantMWs) {
        this.gazaPowerPlantMWs = gazaPowerPlantMWs;
    }

    public void setEgyptianLinesMWs(float egyptianLinesMWs) {
        this.egyptianLinesMWs = egyptianLinesMWs;
    }

    public void setTotalDailySupplyAvailableInMWs(float totalDailySupplyAvailableInMWs) {
        this.totalDailySupplyAvailableInMWs = totalDailySupplyAvailableInMWs;
    }

    public void setOverallDemandInMWs(float overallDemandInMWs) {
        this.overallDemandInMWs = overallDemandInMWs;
    }

    public void setPowerCutsHoursDay400mg(float powerCutsHoursDay400mg) {
        this.powerCutsHoursDay400mg = powerCutsHoursDay400mg;
    }


    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String toString() {
        return  date + ","
                + israeliLinesMWs + ","
                + gazaPowerPlantMWs + ","
                + egyptianLinesMWs + ","
                + totalDailySupplyAvailableInMWs + ","
                + overallDemandInMWs + ","
                + powerCutsHoursDay400mg +","
                +temp;
    }



}
