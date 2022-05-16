package com.example.application.data.entity;


import com.example.application.data.AbstractEntity;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

public class Project extends AbstractEntity {

    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getAuftragGeber() {
        return auftragGeber;
    }

    public void setAuftragGeber(Company auftragGeber) {
        this.auftragGeber = auftragGeber;
    }

    public Company getAuftragNehmer() {
        return auftragNehmer;
    }

    public void setAuftragNehmer(Company auftragNehmer) {
        this.auftragNehmer = auftragNehmer;
    }

    public String getStrategischesProjektziel() {
        return strategischesProjektziel;
    }

    public void setStrategischesProjektziel(String strategischesProjektziel) {
        this.strategischesProjektziel = strategischesProjektziel;
    }

    public String getFachlichesThemengebiet() {
        return fachlichesThemengebiet;
    }

    public void setFachlichesThemengebiet(String fachlichesThemengebiet) {
        this.fachlichesThemengebiet = fachlichesThemengebiet;
    }

    public List<Competence> getFachlicheKompetenz() {
        return fachlicheKompetenz;
    }

    public void setFachlicheKompetenz(List<Competence> fachlicheKompetenz) {
        this.fachlicheKompetenz = fachlicheKompetenz;
    }

    public List<Competence> getMethodischeKompetenz() {
        return methodischeKompetenz;
    }

    public void setMethodischeKompetenz(List<Competence> methodischeKompetenz) {
        this.methodischeKompetenz = methodischeKompetenz;
    }

    public List<Competence> getTechnologischeKompetenz() {
        return technologischeKompetenz;
    }

    public void setTechnologischeKompetenz(
        List<Competence> technologischeKompetenz) {
        this.technologischeKompetenz = technologischeKompetenz;
    }

    public String getKostenTraeger() {
        return kostenTraeger;
    }

    public void setKostenTraeger(String kostenTraeger) {
        this.kostenTraeger = kostenTraeger;
    }

    @NotBlank
    private String description;

    @NotBlank
    private Company auftragGeber;

    private Company auftragNehmer;

    private String strategischesProjektziel;

    private String fachlichesThemengebiet;

    @ManyToMany
    private List<Competence> fachlicheKompetenz = new LinkedList<>();

    @ManyToMany
    private List<Competence> methodischeKompetenz = new LinkedList<>();

    @ManyToMany
    private List<Competence> technologischeKompetenz = new LinkedList<>();

    private String kostenTraeger;
    @OneToMany
    private Contact projektLeiter;
    @OneToMany
    private Contact projektleiterStellvertreter;

    private Date projektLaufzeitVon = new Date();
    private Date getProjektLaufzeitBis = new Date();

    @OneToMany
    private Project uebergeordnetesProjekt;

    private boolean istAktiv;

    public Contact getProjektLeiter() {
        return projektLeiter;
    }

    public void setProjektLeiter(Contact projektLeiter) {
        this.projektLeiter = projektLeiter;
    }

    public Contact getProjektleiterStellvertreter() {
        return projektleiterStellvertreter;
    }

    public void setProjektleiterStellvertreter(Contact projektleiterStellvertreter) {
        this.projektleiterStellvertreter = projektleiterStellvertreter;
    }

    public Date getProjektLaufzeitVon() {
        return projektLaufzeitVon;
    }

    public void setProjektLaufzeitVon(Date projektLaufzeitVon) {
        this.projektLaufzeitVon = projektLaufzeitVon;
    }

    public Date getProjektLaufzeitBis() {
        return getProjektLaufzeitBis;
    }

    public void setProjektLaufzeitBis(Date getProjektLaufzeitBis) {
        this.getProjektLaufzeitBis = getProjektLaufzeitBis;
    }

    public Project getUebergeordnetesProjekt() {
        return uebergeordnetesProjekt;
    }

    public void setUebergeordnetesProjekt(Project uebergeordnetesProjekt) {
        this.uebergeordnetesProjekt = uebergeordnetesProjekt;
    }

    public boolean isIstAktiv() {
        return istAktiv;
    }

    public void setIstAktiv(boolean istAktiv) {
        this.istAktiv = istAktiv;
    }

    public List<Project> getAbhaengigeProjekte() {
        return abhaengigeProjekte;
    }

    public void setAbhaengigeProjekte(List<Project> abhaengigeProjekte) {
        this.abhaengigeProjekte = abhaengigeProjekte;
    }

    @ManyToMany
    private List<Project> abhaengigeProjekte = new LinkedList<>();
}
