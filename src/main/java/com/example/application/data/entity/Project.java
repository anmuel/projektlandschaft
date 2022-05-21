package com.example.application.data.entity;


import com.example.application.data.AbstractEntity;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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

    public @Nullable Company getAuftragGeber() {
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

    @ManyToMany(mappedBy = "projects")
    private List<FachlicheKompetenz> fachlicheKompetenz = new LinkedList<>();

    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private List<MethodischeKompetenz> methodischeKompetenz = new LinkedList<>();

    @ManyToMany(mappedBy = "projects")
    private List<TechnologischeKompetenz> technologischeKompetenz = new LinkedList<>();

    private String kostenTraeger;
    @ManyToOne
    @NotNull
    private Contact projektLeiter;

    @ManyToOne
    @Nullable
    private Contact projektleiterStellvertreter;

    @Nullable
    private LocalDate projektLaufzeitVon;
    @Nullable
    private LocalDate getProjektLaufzeitBis;

    @ManyToOne
    @Nullable
    private Project uebergeordnetesProjekt;

    @OneToMany(mappedBy = "uebergeordnetesProjekt")
    private final List<Project> untergeordneteProjekte = new LinkedList<>();

    private boolean istAktiv = true;

    public @NonNull Contact getProjektLeiter() {
        return projektLeiter;
    }

    public void setProjektLeiter(@NonNull Contact projektLeiter) {
        this.projektLeiter = projektLeiter;
    }

    public @Nullable Contact getProjektleiterStellvertreter() {
        return projektleiterStellvertreter;
    }

    public void setProjektleiterStellvertreter(@Nullable Contact projektleiterStellvertreter) {
        this.projektleiterStellvertreter = projektleiterStellvertreter;
    }

    public @Nullable LocalDate getProjektLaufzeitVon() {
        return projektLaufzeitVon;
    }

    public void setProjektLaufzeitVon(@Nullable LocalDate projektLaufzeitVon) {
        this.projektLaufzeitVon = projektLaufzeitVon;
    }

    public @Nullable LocalDate getProjektLaufzeitBis() {
        return getProjektLaufzeitBis;
    }

    public void setProjektLaufzeitBis(LocalDate getProjektLaufzeitBis) {
        this.getProjektLaufzeitBis = getProjektLaufzeitBis;
    }

    public @Nullable Project getUebergeordnetesProjekt() {
        return uebergeordnetesProjekt;
    }

    public void setUebergeordnetesProjekt(@Nullable Project uebergeordnetesProjekt) {
        this.uebergeordnetesProjekt = uebergeordnetesProjekt;
    }

    public boolean istAktiv() {
        return istAktiv;
    }

    public void setIstAktiv(boolean istAktiv) {
        this.istAktiv = istAktiv;
    }

    public List<Project> getAbhängigeProjekte() {
        return abhängigeProjekte;
    }

    public void setAbhängigeProjekte(List<Project> abhängigeProjekte) {
        this.abhängigeProjekte = abhängigeProjekte;
    }

    @ManyToMany
    private List<Project> abhängigeProjekte = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Project project = (Project) o;
        return istAktiv == project.istAktiv && title.equals(project.title) && Objects.equals(description,
            project.description) && Objects.equals(auftragGeber, project.auftragGeber)
            && Objects.equals(auftragNehmer, project.auftragNehmer) && Objects.equals(
            strategischesProjektziel, project.strategischesProjektziel) && Objects.equals(fachlichesThemengebiet,
            project.fachlichesThemengebiet) && Objects.equals(fachlicheKompetenz, project.fachlicheKompetenz)
            && Objects.equals(methodischeKompetenz, project.methodischeKompetenz) && Objects.equals(
            technologischeKompetenz, project.technologischeKompetenz) && Objects.equals(kostenTraeger,
            project.kostenTraeger) && projektLeiter.equals(project.projektLeiter) && Objects.equals(
            projektleiterStellvertreter, project.projektleiterStellvertreter) && Objects.equals(
            projektLaufzeitVon, project.projektLaufzeitVon) && Objects.equals(getProjektLaufzeitBis,
            project.getProjektLaufzeitBis) && Objects.equals(uebergeordnetesProjekt,
            project.uebergeordnetesProjekt) && Objects.equals(untergeordneteProjekte,
            project.untergeordneteProjekte) && Objects.equals(abhängigeProjekte, project.abhängigeProjekte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, description, auftragGeber, auftragNehmer, strategischesProjektziel,
            fachlichesThemengebiet, fachlicheKompetenz, methodischeKompetenz, technologischeKompetenz, kostenTraeger,
            projektLeiter, projektleiterStellvertreter, projektLaufzeitVon, getProjektLaufzeitBis,
            uebergeordnetesProjekt,
            untergeordneteProjekte, istAktiv, abhängigeProjekte);
    }
}
