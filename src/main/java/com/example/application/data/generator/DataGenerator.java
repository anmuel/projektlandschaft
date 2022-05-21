package com.example.application.data.generator;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.FachlicheKompetenz;
import com.example.application.data.entity.MethodischeKompetenz;
import com.example.application.data.entity.Project;
import com.example.application.data.entity.Status;
import com.example.application.data.repository.CompanyRepository;
import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.FachlicheKompetenzRepository;
import com.example.application.data.repository.MethodischeKompetenzRepository;
import com.example.application.data.repository.ProjectRepository;
import com.example.application.data.repository.StatusRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(ContactRepository contactRepository, CompanyRepository companyRepository,
        StatusRepository statusRepository, ProjectRepository projectRepository,
        FachlicheKompetenzRepository fachlicheKompetenzRepository,
        MethodischeKompetenzRepository methodischeKompetenzRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (contactRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
            ExampleDataGenerator<Company> companyGenerator = new ExampleDataGenerator<>(Company.class,
                LocalDateTime.now());
            companyGenerator.setData(Company::setName, DataType.COMPANY_NAME);
            List<Company> companies = companyRepository.saveAll(companyGenerator.create(5, seed));

            List<Status> statuses = statusRepository
                .saveAll(Stream.of("Imported lead", "Not contacted", "Contacted", "Customer", "Closed (lost)")
                    .map(Status::new).collect(Collectors.toList()));

            logger.info("... generating 50 Contact entities...");
            ExampleDataGenerator<Contact> contactGenerator = new ExampleDataGenerator<>(Contact.class,
                LocalDateTime.now());
            contactGenerator.setData(Contact::setFirstName, DataType.FIRST_NAME);
            contactGenerator.setData(Contact::setLastName, DataType.LAST_NAME);
            contactGenerator.setData(Contact::setEmail, DataType.EMAIL);

            Random r = new Random(seed);
            List<Contact> contacts = contactGenerator.create(50, seed).stream().peek(contact -> {
                contact.setCompany(companies.get(r.nextInt(companies.size())));
                contact.setStatus(statuses.get(r.nextInt(statuses.size())));
            }).collect(Collectors.toList());

            contactRepository.saveAll(contacts);

            ExampleDataGenerator<FachlicheKompetenz> fachlicheKompetenzGenerator = new ExampleDataGenerator<>(
                FachlicheKompetenz.class, LocalDateTime.now());
            fachlicheKompetenzGenerator.setData(FachlicheKompetenz::setName, DataType.OCCUPATION);
            List<FachlicheKompetenz> fachlicheKompetenzen = fachlicheKompetenzRepository.saveAll(
                fachlicheKompetenzGenerator.create(5, seed));

            ExampleDataGenerator<MethodischeKompetenz> methodischeKompetenzGenerator = new ExampleDataGenerator<>(
                MethodischeKompetenz.class, LocalDateTime.now());
            methodischeKompetenzGenerator.setData(MethodischeKompetenz::setName, DataType.OCCUPATION);
            List<MethodischeKompetenz> methodischeKompetenzen = methodischeKompetenzRepository.saveAll(
                methodischeKompetenzGenerator.create(5, seed));

            logger.info("... generating 10 Project entities ...");
            ExampleDataGenerator<Project> projectGenerator = new ExampleDataGenerator<>(Project.class,
                LocalDateTime.now());
            projectGenerator.setData(Project::setTitle, DataType.BOOK_TITLE);
            projectGenerator.setData(Project::setDescription, DataType.SENTENCE);
            projectGenerator.setData(Project::setIstAktiv, DataType.BOOLEAN_50_50);
            projectGenerator.setData(Project::setProjektLaufzeitBis, DataType.DATE_NEXT_10_YEARS);
            List<Project> projects = projectGenerator.create(10, seed).stream().peek(project -> {
                project.setAuftragGeber(companies.get(r.nextInt(companies.size())));
                project.setProjektLeiter(contacts.get(r.nextInt(contacts.size())));
            }).collect(Collectors.toList());

            projectRepository.saveAll(projects);

            logger.info("Generated demo data");
        };
    }

}
