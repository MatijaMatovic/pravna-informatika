package com.pravna_informatika.projekat.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequiredArgsConstructor
public class DocumentController {
    @GetMapping("/documents/verdicts")
    ResponseEntity<List<String>> verdicts() throws URISyntaxException {
        ClassLoader classLoader = DocumentController.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("static/presude_an/");
        File cases = new File(resourceUrl.toURI());
        var files = cases.listFiles();
        List<String> fileNames = Arrays.stream(files)
                .map(File::getName)
                .map(name -> name.substring(0, name.lastIndexOf(".")))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return ResponseEntity.ok(fileNames);
    }

    @GetMapping("/documents/verdict/xml/{id}")
    public ResponseEntity<?> verdictXML(@PathVariable String id) throws IOException, URISyntaxException {
        ClassLoader classLoader = DocumentController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("static/presude_an/" + id + ".xml");

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(Files.readAllBytes(Path.of(resourceUrl.toURI())));
    }

    @GetMapping("/documents/law/xml")
    public ResponseEntity<?> lawXML() throws IOException, URISyntaxException {
        ClassLoader classLoader = DocumentController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("static/zakoni_an/Clan 300.xml");

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(Files.readAllBytes(Path.of(resourceUrl.toURI())));
    }

    @GetMapping("/documents/verdict/pdf/{id}")
    public ResponseEntity<?> verdictPDF(@PathVariable String id) throws IOException, URISyntaxException {
        id = id.replace("presuda", "K");
        System.out.println(id);
        ClassLoader classLoader = DocumentController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("static/Presude_pdf/" + id + ".pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(Files.readAllBytes(Path.of(resourceUrl.toURI())));
    }

    @GetMapping("/documents/law/pdf")
    public ResponseEntity<?> lawPDF() throws IOException, URISyntaxException {
        ClassLoader classLoader = DocumentController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("static/Zakoni_pdf/zakon.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(Files.readAllBytes(Path.of(resourceUrl.toURI())));
    }

    @GetMapping("/documents/features")
    public ResponseEntity<?> features() throws IOException, URISyntaxException {
        ClassLoader classLoader = DocumentController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("static/metadata.txt");

        CSVParser parser = CSVFormat.DEFAULT.withHeader().withDelimiter(';').parse(new FileReader(Path.of(resourceUrl.toURI()).toFile()));
        StringWriter stringWriter = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();

        stringWriter.write("[");

        var iter = parser.iterator();
        while (iter.hasNext()) {
            var currentRecord = iter.next();
            String json = mapper.writeValueAsString(currentRecord.toMap());
            stringWriter.write(json);
            if (iter.hasNext()) {
                stringWriter.write(",");
            }
        }
        parser.close();

        stringWriter.write("]");

        String jsonString = stringWriter.toString();

        return ResponseEntity.ok().body(jsonString);
    }
}
