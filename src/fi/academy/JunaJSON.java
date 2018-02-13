package fi.academy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.*;

/*
Vaatii Jackson kirjaston:
File | Project Structure
Libraries >> Add >> Maven
Etsi "jackson-databind", valitse versio 2.0.5
Asentuu Jacksonin databind, sekä core ja annotations
 */

public class JunaJSON {
    public static void main(String[] args) {
        lueJunanJSONData();
    }


    private static void lueJunanJSONData() {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        Scanner lukija = new Scanner(System.in);
        System.out.println("Anna lähtöasema:");
        String lahtoAsema = lukija.nextLine();
        System.out.println("Anna pääteasema:");
        String paateAsema = lukija.nextLine();

        try {
            URL url = new URL(baseurl+"/live-trains/station/" + lahtoAsema + "/" + paateAsema);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            int lahtevaJuna = 1;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < junat.get(i).getTimeTableRows().size() ; j++) {
                  if (junat.get(i).getTimeTableRows().get(j).getStationShortCode().equals(lahtoAsema)){
                      System.out.println("Mahdolliset lähdöt: " + junat.get(i).getTimeTableRows().get(j).getScheduledTime());
                  }

                }
            }

//            for (int i = 0; i < junat.size() ; i++) {
//                if(junat.get(i).getTimeTableRows().get)
//            }
//
            // ottaa talteen saapuvan juna-aseman indeksin ja tulostaa tarvittavat tiedot
            for (int i = 0; i < junat.get(lahtevaJuna).getTimeTableRows().size(); i++) {
                if (junat.get(lahtevaJuna).getTimeTableRows().get(i).getStationShortCode().equals(paateAsema)) {
                    System.out.println("Olet saapunut paikkaan: " + junat.get(lahtevaJuna).getTimeTableRows().get(i).getStationShortCode());
                    System.out.println("Saapumisaika: " + junat.get(lahtevaJuna).getTimeTableRows().get(i).getScheduledTime());
                    break;
                }
//                System.out.println("Kokeillaas: " + junat.get(0).getTimeTableRows().get(i).getStationShortCode());
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

}


class Juna {
    boolean cancelled;
    String commuterLineID;
    //LocalDate departureDate;  // Jackson ei oikein pidä Java 8 päivistä oletuksena
    Date departureDate;
    String operatorShortCode;
    int operatorUICCode;
    boolean runningCurrently;
    List<TimeTableRow> timeTableRows;
    Date timetableAcceptanceDate;
    String timetableType;
    String trainCategory;
    int trainNumber;
    String trainType;
    long version;

    @Override
    public String toString() {
        return "Juna{" + "cancelled=" + cancelled + ", commuterLineID='" + commuterLineID + '\'' + ", departureDate=" + departureDate + ", operatorShortCode='" + operatorShortCode + '\'' + ", operatorUICCode=" + operatorUICCode + ", runningCurrently=" + runningCurrently + ", timeTableRows=" + timeTableRows + ", timetableAcceptanceDate=" + timetableAcceptanceDate + ", timetableType='" + timetableType + '\'' + ", trainCategory='" + trainCategory + '\'' + ", trainNumber=" + trainNumber + ", trainType='" + trainType + '\'' + ", version=" + version + '}';
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getCommuterLineID() {
        return commuterLineID;
    }

    public void setCommuterLineID(String commuterLineID) {
        this.commuterLineID = commuterLineID;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getOperatorShortCode() {
        return operatorShortCode;
    }

    public void setOperatorShortCode(String operatorShortCode) {
        this.operatorShortCode = operatorShortCode;
    }

    public int getOperatorUICCode() {
        return operatorUICCode;
    }

    public void setOperatorUICCode(int operatorUICCode) {
        this.operatorUICCode = operatorUICCode;
    }

    public boolean isRunningCurrently() {
        return runningCurrently;
    }

    public void setRunningCurrently(boolean runningCurrently) {
        this.runningCurrently = runningCurrently;
    }

    public List<TimeTableRow> getTimeTableRows() {
        return timeTableRows;
    }

    public void setTimeTableRows(List<TimeTableRow> timeTableRows) {
        this.timeTableRows = timeTableRows;
    }

    public Date getTimetableAcceptanceDate() {
        return timetableAcceptanceDate;
    }

    public void setTimetableAcceptanceDate(Date timetableAcceptanceDate) {
        this.timetableAcceptanceDate = timetableAcceptanceDate;
    }

    public String getTimetableType() {
        return timetableType;
    }

    public void setTimetableType(String timetableType) {
        this.timetableType = timetableType;
    }

    public String getTrainCategory() {
        return trainCategory;
    }

    public void setTrainCategory(String trainCategory) {
        this.trainCategory = trainCategory;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class TimeTableRow {
    private String stationShortCode;
    private Integer stationUICCode;
    private String countryCode;
    private String type;
    private Boolean trainStopping;
    private Boolean commercialStop;
    private String commercialTrack;
    private Boolean cancelled;
    private String scheduledTime;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getStationShortCode() {
        return stationShortCode;
    }
    public void setStationShortCode(String stationShortCode) {
        this.stationShortCode = stationShortCode;
    }
    public Integer getStationUICCode() {
        return stationUICCode;
    }
    public void setStationUICCode(Integer stationUICCode) {
        this.stationUICCode = stationUICCode;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Boolean getTrainStopping() {
        return trainStopping;
    }
    public void setTrainStopping(Boolean trainStopping) {
        this.trainStopping = trainStopping;
    }
    public Boolean getCommercialStop() {
        return commercialStop;
    }
    public void setCommercialStop(Boolean commercialStop) {
        this.commercialStop = commercialStop;
    }
    public String getCommercialTrack() {
        return commercialTrack;
    }
    public void setCommercialTrack(String commercialTrack) {
        this.commercialTrack = commercialTrack;
    }
    public Boolean getCancelled() {
        return cancelled;
    }
    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }
    public String getScheduledTime() {
        return scheduledTime;
    }
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
