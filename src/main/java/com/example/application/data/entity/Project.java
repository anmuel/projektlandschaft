package com.example.application.data.entity;


import com.example.application.data.AbstractEntity;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity()
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

    public List<FachlicheKompetenz> getFachlicheKompetenz() {
        return fachlicheKompetenz;
    }

    public void setFachlicheKompetenz(List<FachlicheKompetenz> fachlicheKompetenz) {
        this.fachlicheKompetenz = fachlicheKompetenz;
    }

    public List<MethodischeKompetenz> getMethodischeKompetenz() {
        return methodischeKompetenz;
    }

    public void setMethodischeKompetenz(List<MethodischeKompetenz> methodischeKompetenz) {
        this.methodischeKompetenz = methodischeKompetenz;
    }

    public List<TechnologischeKompetenz> getTechnologischeKompetenz() {
        return technologischeKompetenz;
    }

    public void setTechnologischeKompetenz(
        List<TechnologischeKompetenz> technologischeKompetenz) {
        this.technologischeKompetenz = technologischeKompetenz;
    }

    public String getKostenTraeger() {
        return kostenTraeger;
    }

    public void setKostenTraeger(String kostenTraeger) {
        this.kostenTraeger = kostenTraeger;
    }

    private String description;

    @ManyToOne()
    private Company auftragGeber;

    @ManyToOne
    private Company auftragNehmer;

    private String strategischesProjektziel;

    private String fachlichesThemengebiet;

    @ManyToMany(mappedBy="projects")
    private List<FachlicheKompetenz> fachlicheKompetenz = new LinkedList<>();

    @ManyToMany(mappedBy="projects", fetch = FetchType.LAZY)
    private List<MethodischeKompetenz> methodischeKompetenz = new LinkedList<>();

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    private List<TechnologischeKompetenz> technologischeKompetenz = new LinkedList<>();

    private String kostenTraeger;
    @ManyToOne
    private Contact projektLeiter;

    @ManyToOne
    private Contact projektleiterStellvertreter;

    private Date projektLaufzeitVon = new Date();
    private Date getProjektLaufzeitBis = new Date();

    @ManyToOne
    private Project uebergeordnetesProjekt;

    @OneToMany(mappedBy = "uebergeordnetesProjekt")
    private final List<Project> untergeordneteProjekte = new LinkedList<>();

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
