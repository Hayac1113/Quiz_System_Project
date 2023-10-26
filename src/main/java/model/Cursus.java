package model;

//Create a class Course and the attributes:
public class Cursus {
    private String cursusNaam;
    private String coordinator;
    private String niveau;

    // Constructor
    public Cursus(String cursusNaam, String coordinator, String niveau) {
        this.cursusNaam = cursusNaam;
        this.coordinator = coordinator;
        this.niveau = niveau;
    }

    public Cursus() {
        this("Onbekend", "Onbekend", "Onbekend");
    }

    // Getters and Setters for courseName
    public String getCursusNaam() {
        return cursusNaam;
    }

    public void setCursusNaam(String cursusNaam) {
        this.cursusNaam = cursusNaam;
    }

    // Getters and Setters for coordinator
    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    // Getters and Setters for level
    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s\n", cursusNaam, coordinator, niveau);
    }
}
